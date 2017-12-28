package com.erui.comm.middle.mongo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.erui.comm.util.data.string.StringUtils;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

@SuppressWarnings("deprecation")
public class MongoManager {
		private final static ThreadLocal<MongoClient> mongos = new ThreadLocal<MongoClient>();
		
		public static DB getdb(){
			return getMongos().getDB("syslog");
		}

		public static MongoClient getMongos() {
			MongoClient mongo = mongos.get();
			if (mongo == null) {
				try {
					Properties pro = new Properties();  
			        InputStream in = new BufferedInputStream(MongoManager.class.getResourceAsStream("/comm.properties"));
			        try {
						pro.load(in);
					} catch (IOException e) {
						e.printStackTrace();
					}
			        
					String servers [] 		= pro.getProperty("db.mongo.server").split(",");
					String usernames [] 	= pro.getProperty("db.mongo.username").split(",");
					String passwords [] 	= pro.getProperty("db.mongo.password").split(",");
					String database = pro.getProperty("db.mongo.database");
					
					MongoClientOptions options = new MongoClientOptions.Builder()
							.socketKeepAlive(true) // 是否保持长链接
							.connectTimeout(5000) // 链接超时时间
							.socketTimeout(5000) // read数据超时时间
							.readPreference(ReadPreference.primary()) // 最近优先策略
							.connectionsPerHost(Integer.parseInt(pro.getProperty("db.mongo.connectionsPerHost"))) // 每个地址最大请求数
							.maxWaitTime(1000 * 60 * 2) // 长链接的最大等待时间
							.threadsAllowedToBlockForConnectionMultiplier(Integer.parseInt(pro.getProperty("db.mongo.threads"))) // 一个socket最大的等待请求数
							.writeConcern(WriteConcern.NORMAL).build();
					
					List<ServerAddress> replicaSetSeeds = new ArrayList<ServerAddress>();
					List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
					for (int i = 0; i < servers.length; i++) {
						String [] server = servers[i].split(":");
						replicaSetSeeds.add(new ServerAddress(server[0], Integer.parseInt(server[1])));
						
						//加验证
						if (i >= usernames.length || i >= passwords.length) continue;
						MongoCredential credentials = MongoCredential.createCredential(usernames[i], database, (passwords[i]).toCharArray()); 
			            credentialsList.add(credentials);  
					}
					
					if (usernames == null || (usernames.length ==1 && StringUtils.isEmpty(usernames[0]))) {
						//无验证
						mongo = new MongoClient(replicaSetSeeds, options);
					} else {
						mongo = new MongoClient(replicaSetSeeds, credentialsList, options);
					}
					
					mongos.set(mongo);
				} catch (MongoException e) {
					e.printStackTrace();
				}
			}
			return mongo;
		}

		public static void close(){
			MongoClient mongo = mongos.get();
			if(mongo!=null){
				mongo.close();
				mongos.remove();
			}
		}
		
		
		/**
		 * 获取集合（表）
		 * 
		 * @param collection
		 */
		public static DBCollection getCollection(String collection) {
			return getdb().getCollection(collection);
		}
		
		/**
		 * 插入
		 * @param o 插入
		 *    
		 */
		public static void insert(String collection, DBObject o) {
			getCollection(collection).insert(o);
		}

		/**
		 * 批量插入
		 * 
		 * @param collection
		 * @param list
		 *            插入的列表
		 */
		public void insertBatch(String collection, List<DBObject> list) {
			if (list == null || list.isEmpty()) {
				return;
			}
			getCollection(collection).insert(list);
		}
		
		/**
		 * 
		 * 更新记录
		 * @param collection
		 * @param updateCondition 更新条件
		 */
		public static void update(String collection, DBObject updateCondition, DBObject updatedValue) {
			DBObject updateSetValue=new BasicDBObject("$set", updatedValue);  
			getCollection(collection).update(updateCondition, updateSetValue);
		}
		
		
}
