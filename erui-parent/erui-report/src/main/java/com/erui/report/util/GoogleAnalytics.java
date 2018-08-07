package com.erui.report.util;

import java.io.IOException;
import com.google.gdata.client.analytics.AnalyticsService;
import com.google.gdata.client.analytics.DataQuery;
import com.google.gdata.data.analytics.AccountEntry;
import com.google.gdata.data.analytics.AccountFeed;
import com.google.gdata.data.analytics.DataEntry;
import com.google.gdata.data.analytics.DataFeed;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.net.MalformedURLException;
import java.net.URL;


public class GoogleAnalytics {

    // 使用ClientLogin 方法访问Google Analytics。其中，两个常量分别存储用户名和密码。
    private static final String CLIENT_USERNAME = "lf670236133@gmail.com"; //Google 帐号
    private static final String CLIENT_PASS = "liu89062192";  //Google 密码
    private static final String TABLE_ID = "ga:123168185"; //此帐号有权访问的Google Analytics配置文件的TABLE ID

    public static void main(String [] args){
        myTest();
    }

    public static void myTest() {
        try {
            /*
             * 系统创建服务对象。服务对象的参数是一个代表应用程序名称的字符串。随后，系统将采用 setUserCredentials 方法来处理
             * Google Analytics（分析）授权。
             */
            // Service Object to work with the Google Analytics Data Export API.
            AnalyticsService analyticsService = new AnalyticsService("My Project");//gaExportAPI_acctSample_v2.0
            // Client Login Authorization.
            analyticsService.setUserCredentials(CLIENT_USERNAME, CLIENT_PASS);

            // Get data from the Account Feed.
            getAccountFeed(analyticsService);  //获取帐号信息

            // Access the Data Feed if the Table Id has been set.
            if (!TABLE_ID.isEmpty()) {
                // Get profile data from the Data Feed.
                getDataFeed(analyticsService);  //获取数据信息（包括"指标"和"维度"）
            }

        } catch (AuthenticationException e) {
            System.err.println("Authentication failed : " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Network error trying to retrieve feed: "
                    + e.getMessage());
            return;
        } catch (ServiceException e) {
            System.err.println("Analytics API responded with an error message: "
                    + e.getMessage());
            return;
        }

    }

    /**
     * 获取帐号feed
     * @param analyticsService
     * @throws IOException
     * @throws MalformedURLException
     * @throws ServiceException
     */
    private static void getAccountFeed(AnalyticsService analyticsService)
            throws IOException, MalformedURLException, ServiceException {

        // Construct query from a string.
        URL queryUrl = new URL("https://www.google.com/analytics/feeds/accounts/default?max-results=50");

        // Make request to the API.
        AccountFeed accountFeed = analyticsService.getFeed(queryUrl, AccountFeed.class);

        // Output the data to the screen.
        System.out.println("-------- Account Feed Results --------");
        for (AccountEntry entry : accountFeed.getEntries()) {
            System.out.println("\nAccount Name  = "
                    + entry.getProperty("ga:accountName")
                    + "\nProfile Name  = " + entry.getTitle().getPlainText()  //配置文件名称
                    + "\nProfile Id    = " + entry.getProperty("ga:profileId")  //配置文件编号
                    + "\nTable Id      = " + entry.getTableId().getValue());   //配置文件的Table Id
        }
    }

    /**
     * 获取指标和维度信息
     * @param analyticsService
     * @throws IOException
     * @throws MalformedURLException
     * @throws ServiceException
     */
    private static void getDataFeed(AnalyticsService analyticsService)
            throws IOException, MalformedURLException, ServiceException {

        // Create a query using the DataQuery Object.
        DataQuery query = new DataQuery(new URL("https://www.google.com/analytics/feeds/data"));
        query.setStartDate("2018-01-01");  //要统计的数据的起始时间
        query.setEndDate("2018-10-30");  //要统计的数据的结束时间
        query.setDimensions("ga:pageTitle,ga:pagePath");   //要统计的维度信息
        query.setMetrics("ga:pageviews,ga:bounces,ga:visits,ga:visitors");  //要统计的指标信息
        query.setSort("-ga:pageviews");
        query.setMaxResults(10);
        query.setIds(TABLE_ID);

        // Make a request to the API.
        DataFeed dataFeed = analyticsService.getFeed(query.getUrl(),
                DataFeed.class);

        // Output data to the screen.
        System.out.println("----------- Data Feed Results ----------");
        for (DataEntry entry : dataFeed.getEntries()) {
            System.out.println("\nPage Title = "
                    + entry.stringValueOf("ga:pageTitle") + "\nPage Path  = "
                    + entry.stringValueOf("ga:pagePath") + "\nPageviews浏览量  = "
                    + entry.stringValueOf("ga:pageviews") + "\nga:bounces = "
                    + entry.stringValueOf("ga:bounces") + "\nga:visits访问次数 = "
                    + entry.stringValueOf("ga:visits") + "\nga:visitors访问人数 = "
                    + entry.stringValueOf("ga:visitors"));
        }
    }



}
