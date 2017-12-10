package com.erui.comm.util.io;



/**
 * 文件实体类
 * 
 * @author Xiongyan
 * @date 2015年7月14日 上午8:32:01
 * @version 0.0.1
 */
public class FileModel {
	private String id;
	private String fileName;//上传后的文件名字
	private String url; //上传后的文件URL
	private String contentType; //文件的内容类型
	private String fileFormat; //文件的格式，通过文件名的后缀判断
	private Long fileSize; //文件内容大小
	private Integer status; // -1 上传失败     0 上传成功     1 上传文件类型不正确     2 上传文件超过指定大小
	
	private String ip; //上传IP
	private Integer isDelete; //删除状态
	private Integer operateType; //操作类型
	
	private String createId; //上传者
	private String createDate; //上传时间
	public String updateId;
	public String updateDate;
	public Integer sort;
	public String code;//外键code
	private String name;//文件原始名
	
	private String group;
	
	
	public String getFileName() {
		return fileName;
	}
	public FileModel setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}
	public String getUrl() {
		return url;
	}
	public FileModel setUrl(String url) {
		this.url = url;
		return this;
	}
	public String getContentType() {
		return contentType;
	}
	public FileModel setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public FileModel setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
		return this;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public FileModel setFileSize(Long fileSize) {
		this.fileSize = fileSize;
		return this;
	}
	public Integer getStatus() {
		return status;
	}
	public FileModel setStatus(Integer status) {
		this.status = status;
		return this;
	}
	public String getId() {
		return id;
	}
	public FileModel setId(String id) {
		this.id = id;
		return this;
	}
	public String getIp() {
		return ip;
	}
	public FileModel setIp(String ip) {
		this.ip = ip;
		return this;
	}
	public String getCreateId() {
		return createId;
	}
	public FileModel setCreateId(String createId) {
		this.createId = createId;
		return this;
	}
	public String getCreateDate() {
		return createDate;
	}
	public FileModel setCreateDate(String createDate) {
		this.createDate = createDate;
		return this;
	}
	public String getUpdateId() {
		return updateId;
	}
	public FileModel setUpdateId(String updateId) {
		this.updateId = updateId;
		return this;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public FileModel setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
		return this;
	}
	public Integer getSort() {
		return sort;
	}
	public FileModel setSort(Integer sort) {
		this.sort = sort;
		return this;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public FileModel setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
		return this;
	}
	public Integer getOperateType() {
		return operateType;
	}
	public FileModel setOperateType(Integer operateType) {
		this.operateType = operateType;
		return this;
	}
	public String getCode() {
		return code;
	}
	public FileModel setCode(String code) {
		this.code = code;
		return this;
	}
	public String getName() {
		return name;
	}
	public FileModel setName(String name) {
		this.name = name;
		return this;
	}
	public String getGroup() {
		return group;
	}
	public FileModel setGroup(String group) {
		this.group = group;
		return this;
	}
	
}
