/**   
 * @Title: AmqpThread.java 
 * @Package com.sva.service 
 * @Description: 执行java版amqp对接线程
 * @author labelCS   
 * @date 2016年8月31日 下午4:46:31 
 * @version V1.0   
 */
package com.bis.common;

import java.io.File;
import java.net.URISyntaxException;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.qpid.QpidException;
import org.apache.qpid.client.AMQAnyDestination;
import org.apache.qpid.client.AMQConnection;
import org.apache.qpid.url.URLSyntaxException;

import com.bis.dao.AmqpDao;
import com.bis.model.LocationModel;
import com.bis.model.SvaModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @ClassName: AmqpThread 
 * @Description: 执行java版amqp对接线程 
 * @author labelCS 
 * @date 2016年8月31日 下午4:46:31 
 *  
 */
public class AmqpThread extends Thread {

    /** 
     * @Fields log : 日志处理类 
     */ 
    private static final Logger LOG = Logger.getLogger(AmqpThread.class);
    
    /** 
     * @Fields sva : sva信息 
     */ 
    private SvaModel sva;
    /** 
     * @Fields dao : 数据库处理句柄 
     */ 
    private AmqpDao dao;
    /** 
     * @Fields queueId : 队列id
     */ 
    private String queueId;
    /** 
     * @Fields isStop : 是否停止线程标志 
     */ 
    private boolean isStop = false;
    private AMQConnection conn;
    
    /** 
     * <p>Title: </p> 
     * <p>Description: 构造函数</p> 
     * @param sm
     * @param dao
     * @param queue 
     */
    public AmqpThread(SvaModel sm, AmqpDao dao, String queue){
        this.sva = sm;
        this.dao = dao;
        this.queueId = queue;
    }
    
    /** 
     * @Title: stopThread 
     * @Description: 通过改变标志位，停止线程
     */
    public void stopThread()
    {
        this.isStop = true;
    }
    
    /* (非 Javadoc) 
     * <p>Title: run</p> 
     * <p>Description: 实现sva数据订阅</p>  
     * @see java.lang.Thread#run() 
     */
    public void run()
    {        
        String ip = sva.getIp();
        String id = sva.getId();
        String userId = sva.getUsername();
        String port = sva.getBrokerPort();
        LOG.debug("amqp started:"
                + "userId:" + userId 
                + ",queueId:" + queueId
                + ",ip:" + ip
                + ",port:" + port
                + ",id:" + id);

        String classPath = AmqpThread.class.getClassLoader().getResource("/").getPath();  
        String rootPath = "";  
        //windows下  
        if("\\".equals(File.separator)){  
            System.out.println("windows");  
        rootPath = classPath.substring(1,classPath.indexOf("/classes"));  
        rootPath = rootPath.replace("/", "\\");  
        }  
        //linux下  
        if("/".equals(File.separator)){  
            System.out.println("linux");  
        rootPath = classPath.substring(0,classPath.indexOf("/classes"));  
        rootPath = rootPath.replace("\\", "/");  
        }  
        rootPath = rootPath + File.separator + "java_keystore" + File.separator;
        LOG.debug("get keystore path:"+rootPath + "keystore.jks");
        LOG.debug("get keystore path:"+rootPath + "mykeystore.jks");
        // 设置系统环境jvm参数
        System.setProperty("javax.net.ssl.keyStore", rootPath + "keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "importkey");
        System.setProperty("javax.net.ssl.trustStore", rootPath + "mykeystore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "importkey");

        
//        // 获取keystore的路径
////      String path = getClass().getResource("/").getPath();
//      String path = System.getProperty("user.dir");
//      path = path.substring(0, path.indexOf("bin"))+"webapps/SVAProject/WEB-INF";
////      path = path.substring(1, path.indexOf("/classes"));
//      path = path + File.separator + "java_keystore" + File.separator;
//      LOG.debug("get keystore path:"+path);
//      // 设置系统环境jvm参数
//      System.setProperty("javax.net.ssl.keyStore", path + "keystore.jks");
//      System.setProperty("javax.net.ssl.keyStorePassword", "importkey");
//      System.setProperty("javax.net.ssl.trustStore", path + "mykeystore.jks");
//      System.setProperty("javax.net.ssl.trustStorePassword", "importkey");
        
        // 地址变量  
        String brokerOpts = "?brokerlist='tcp://"+ip+":"+port+"?ssl='true'&ssl_verify_hostname='false''";
        String connectionString = "amqp://"+userId+":"+"xxxx@xxxx/"+brokerOpts;
        LOG.debug("connection string:" + connectionString);
        // 建立连接
        try {
            conn = new AMQConnection(connectionString);
            conn.start();
            LOG.debug("connection started!");
            // 获取session
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            LOG.debug("session created!");
            // 获取队列
            Destination queue = new AMQAnyDestination("ADDR:"+queueId+";{create:sender}");
            MessageConsumer consumer = session.createConsumer(queue);
            LOG.debug("consumer created!");
            
            while(!isStop  && !this.isInterrupted())
            {
                Message m = consumer.receive(60000);
                // message为空的情况,
                if(m == null){
                    LOG.debug("Get NULL message, pause for 1 miniute!");
//                    sleep(60000);
                    continue;
                }
                // message格式是否正确
                if(m instanceof BytesMessage){
                    BytesMessage tm = (BytesMessage)m;
                    int length = new Long(tm.getBodyLength()).intValue();
                    if(length > 0){
                        byte[] keyBytes = new byte[length];
                        tm.readBytes(keyBytes);
                        String messages = new String(keyBytes);
                        LOG.debug("sva data:"+messages);
                        saveSvaData(messages, sva.getType(), sva.getIp(), sva.getId());
                    }else{
                        LOG.debug("Get zero length message");
                    }
                }else{
                    LOG.warn("Message is not in Byte format!");
                }
            }
        } catch (URLSyntaxException e) {
            LOG.error(e);
        } catch (QpidException e) {
            LOG.error(e);
        } catch (JMSException e) {
            LOG.error(e);
        } catch (URISyntaxException e) {
            LOG.error(e);
        } catch (Exception e){
            LOG.error(e);
        } finally{
            try {
                if(conn != null)
                {
                    conn.close();
                }
            } catch (JMSException e) {
                LOG.error(e);
            }
            LOG.error("[AMQP]No data from SVA,connection closed!");
        }
    }
    
    /**   
     * @Title: saveSvaData   
     * @Description: 将从sva获取的数据解析并保存到数据库   
     * @param jsonStr：待解析的字符串      
     * @param storeId: 商场id
     * @param type: 订阅类型
     * @throws   
     */ 
    private void saveSvaData(String jsonStr, int type, String ip, String svaId){
        LOG.debug("SVA start!");
        if(StringUtils.isEmpty(jsonStr.trim())){
            LOG.debug("AMQPThread No data from SVA!");
        }else{
            JSONObject result = JSONObject.fromObject(jsonStr);
            // 非匿名化订阅
            if(result.containsKey("locationstream")){
                saveLocationstream(result, type, svaId);
            }
            // 匿名化订阅
            else if(result.containsKey("locationstreamanonymous")){
                saveLocationstreamAnonymous(result, svaId);
            }
            // 网络信息订阅
            else if(result.containsKey("networkinfo")){
                saveNetworkinfo(result, ip);
            }
            // 电子围栏订阅
            else if(result.containsKey("geofencing")){
                saveGeofencing(result);
            }else{
                LOG.warn("Unknow message from SVA!");
            }
        }
        LOG.debug("SVA end!");
    }
    
    /** 
     * @Title: svaLocationstream 
     * @Description: 非匿名化位置信息入库逻辑
     * @param result
     * @param storeId
     * @param type 
     * @param svaId
     */
    private void saveLocationstream(JSONObject result, int type, String svaId)
    {
        JSONArray list = result.getJSONArray("locationstream");
        String tableName = null;
        if(type == 0)
        {
            tableName = "bi_location_" + Util.dateFormat(System.currentTimeMillis(), "yyyyMMdd");
            String insertSql = "insert into "+tableName+"(userId,mapId,x,y,timestamp) values";
            int sqlLength = insertSql.length();
            for(int i = 0; i<list.size();i++){
                LocationModel lm = new LocationModel();
                JSONObject loc = list.getJSONObject(i);
                if(!parseLocation(loc, lm, svaId)){
                    continue;
                }
                insertSql = insertSql+"('" + lm.getUserID()+ "','" + lm.getMapId() + "','" + lm.getX() + "','" + lm.getY() + "','" + lm.getTimestamp() + "'),";
            }
            if (list.size() > 0&&sqlLength<insertSql.length()) {
                insertSql = insertSql.substring(0, insertSql.length() - 1);
                LOG.debug("saveLocationstream insert:" + insertSql);
                int areaResult = dao.insertSql(insertSql);
                LOG.debug("saveLocationstream insert result:" + areaResult);
            }
        }else
        {
            for(int i = 0; i<list.size();i++){
                LocationModel lm = new LocationModel();
                JSONObject loc = list.getJSONObject(i);
                if(!parseLocation(loc, lm, svaId)){
                    continue;
                }
                //检查该用户是否已经存在
                int count = dao.checkPhoneIsExisted(lm.getUserID());
                if(count > 0){
                    dao.updatePhoneLocation(lm);
                }else{
                    dao.saveAmqpData(lm, "bi_locationphone");
                }
            }

        }
    }

    /** 
     * @Title: saveLocationstreamAnonymous 
     * @Description: 匿名化订阅位置信息入库
     * @param result
     * @param storeId 
     * @param svaId
     */
    private void saveLocationstreamAnonymous(JSONObject result, String svaId)
    {
        JSONArray list = result.getJSONArray("locationstreamanonymous");
        String tableName = "bi_location_" + Util.dateFormat(System.currentTimeMillis(), "yyyyMMdd");
        String insertSql = "insert into "+tableName+"(userId,mapId,x,y,timestamp) values";
        for(int i = 0; i<list.size();i++){
            LocationModel lm = new LocationModel();
            JSONObject loc = list.getJSONObject(i);
            if(!parseLocation(loc, lm, svaId)){
                continue;
            }
            insertSql = insertSql+"('" + lm.getUserID()+ "','" + lm.getMapId() + "','" + lm.getX() + "','" + lm.getY() + "','" + lm.getTimestamp() + "'),";
        }
        if (list.size() > 0) {
            insertSql = insertSql.substring(0, insertSql.length() - 1);
            LOG.debug("locationstreamanonymous insert:" + insertSql);
            int areaResult = dao.insertSql(insertSql);
            LOG.debug("locationstreamanonymous insert result:" + areaResult);
        }
//        for(int i = 0; i<list.size();i++){
//            LocationModel lm = new LocationModel();
//            JSONObject loc = list.getJSONObject(i);
//            if(!parseLocation(loc, lm, svaId)){
//                continue;
//            }
//            
//            // 获取当天的日期字符串
//            String tableName = "bi_location_" + Util.dateFormat(lm.getTimestamp(), "yyyyMMdd");
//            // 执行数据库保存逻辑
//            dao.saveAmqpData(lm,tableName);
//            
//        }
    }
    
    /** 
     * @Title: saveNetworkinfo 
     * @Description: 网络信息入库
     * @param result
     * @param ip 
     */
    private void saveNetworkinfo(JSONObject result,String ip)
    {
        JSONObject jsonAll = result.getJSONArray("networkinfo").getJSONObject(0);
        String userid = jsonAll.getString("userid");
        String enbid = jsonAll.getJSONObject("lampsiteinfo").getString("enbid");
        JSONArray jsonList = jsonAll.getJSONObject("lampsiteinfo").getJSONArray("prrusignal");
        long timestamp = jsonAll.getLong("timestamp");
        for(int i = 0; i<jsonList.size(); i++){
            JSONObject temp = jsonList.getJSONObject(i);
            String gpp = temp.getString("gpp");
            String rsrp = temp.getString("rsrp");
            dao.svaPrru(enbid,userid,gpp,rsrp,ip,timestamp);
        }
    }
    
    /** 
     * @Title: saveGeofencing 
     * @Description: 电子围栏信息入库
     * @param result 
     */
    private void saveGeofencing(JSONObject result)
    {
        JSONArray jsonList = result.getJSONArray("geofencing");
        for(int i = 0; i<jsonList.size(); i++){
            JSONObject temp = jsonList.getJSONObject(i);
            // 用户存在多个的情况，目前只取第一个；若用户为空则不作处理
            JSONArray useridList = temp.getJSONArray("userid");
            String userid = "";
            if(useridList.size()>0){
                userid = useridList.getString(0);
            }else{
                continue;
            }
            // 当前时间
            long timeLocal = System.currentTimeMillis();
            
            dao.svaGeofencing(temp.getString("IdType")
                    ,userid
                    ,temp.getString("mapid")
                    ,temp.getString("zoneid")
                    ,temp.getString("zone_event")
                    ,temp.getString("Timestamp")
                    ,timeLocal);
        }
    }
    
    /**   
     * @Title: parseLocation   
     * @Description: 将json数据转换为LocationModel  
     * @param loc
     * @param storeId
     * @param lm
     * @param svaId
     * @return：boolean       
     * @throws   
     */ 
    private boolean parseLocation(JSONObject loc, LocationModel lm, String svaId){
        // 当前时间戳
        long timeLocal = System.currentTimeMillis();
        lm.setTimestamp(timeLocal);
        // 设置LocationModel
        if(loc.containsKey("location"))
        {
            JSONObject location = loc.getJSONObject("location");
            lm.setX(Double.valueOf(location.getInt("x")));
            lm.setY(Double.valueOf(location.getInt("y"))); 
            JSONArray useridList = loc.getJSONArray("userid");
            // 用户存在多个的情况，目前只取第一个；若用户为空则不作处理
            if(useridList.size()>0){
                lm.setUserID(useridList.getString(0));
            }else{
                return false;
            }
            if(loc.containsKey("map"))
            {
                JSONObject mapList = loc.getJSONObject("map");
                String mapId = mapList.getString("mapid");
                lm.setMapId((mapId));  
            } 
        }else
        {
            return false;
        }
//        lm.setIdType(loc.getString("IdType"));
//        lm.setTime_sva(Long.valueOf(loc.getLong("Timestamp")));
//        lm.setDataType(loc.getString("datatype"));

       
        // 楼层号转换
       
        return true;
    }
}
