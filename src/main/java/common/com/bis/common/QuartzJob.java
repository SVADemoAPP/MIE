package com.bis.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.bis.common.conf.Params;
import com.bis.dao.LocationDao;
import com.bis.dao.MapMngDao;
import com.bis.dao.MarketDao;
import com.bis.dao.ShopDao;
import com.bis.dao.StatisticsDao;
import com.bis.dao.VisitorDao;
import com.bis.model.AreaModel;
import com.bis.model.GlobalModel;
import com.bis.model.IvasSvaModel;
import com.bis.model.LocationModel;
import com.bis.model.MapBorderModel;
import com.bis.model.MarketModel;
import com.bis.model.NewUserModel;
import com.bis.model.ShopModel;
import com.bis.model.SvaModel;
import com.bis.model.TrendAllModel;
import com.bis.model.TrendMapModel;
import com.bis.model.TrendShopModel;
import com.bis.model.VisitTimeModel;
import com.bis.service.CleanTableService;
import com.bis.service.DataAnalysisService;
import com.bis.web.auth.PeripheryService;

import net.sf.json.JSONObject;

public class QuartzJob {

    @Value("${mysql.db}")
    private String db;

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private MapMngDao mapMngDao;

    @Autowired
    private MarketDao marketDao;

    @Autowired
    private DataAnalysisService analysisService;

    @Autowired
    private CleanTableService cleanTableService;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private VisitorDao visitorDao;

    @Autowired
    private SubscriptionService service;

    @Autowired
    private PeripheryService peripheryService;

    @Value("${sva.subscriptionType}")
    private int subscriptionType;

    @Value("${sva.subscriptionUrl}")
    private int subscriptionUrl;

    @Value("${sva.subivasurl1}")
    private String subivasurl1;

    @Value("${sva.subivasurl2}")
    private String subivasurl2;

    @Value("${sva.ftpIp}")
    private String ftpIp;

    @Value("${sva.ftpPort}")
    private int ftpPort;

    @Value("${sva.ftpUserName}")
    private String ftpUserName;

    @Value("${sva.ftpPassWord}")
    private String ftpPassWord;

    @Value("${sva.ftpRemotePath}")
    private String ftpRemotePath;

    @Value("${sva.ftpFileNameHeader}")
    private String ftpFileNameHeader;

    @Value("${sva.ftpType}")
    private int ftpType;

    @Value("${sva.subTimes}")
    private int subTimes;
    
    @Autowired
    private GlobalModel globalModel;

    private static final Logger LOG = Logger.getLogger(QuartzJob.class);

    /**
     * 
     * @Title: initMapBorders 
     * @Description: 初始化map边界配置文件
     */
    private void initMapBorders(){
        String path = getClass().getResource("/").getPath();
        try {
            File f = new File(path + "mapBorder.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            NodeList nodeList = doc.getElementsByTagName("Border");
            int x1, x2, y1, y2,mapId;
            for (int i = 0; i < nodeList.getLength(); i++) {
                try {
                    x1 = (int) (10*Float.parseFloat(doc.getElementsByTagName("x1").item(i).getFirstChild().getNodeValue()));
                    x2 = (int) (10*Float.parseFloat(doc.getElementsByTagName("x2").item(i).getFirstChild().getNodeValue()));
                    y1 = (int) (10*Float.parseFloat(doc.getElementsByTagName("y1").item(i).getFirstChild().getNodeValue()));
                    y2 = (int) (10*Float.parseFloat(doc.getElementsByTagName("y2").item(i).getFirstChild().getNodeValue()));
                    mapId=Integer.parseInt(doc.getElementsByTagName("mapId").item(i).getFirstChild().getNodeValue());
                    MapBorderModel mapBorder = new MapBorderModel();
                    mapBorder.setMinX(x1<x2?x1:x2);
                    mapBorder.setMaxX(x1>x2?x1:x2);
                    mapBorder.setMinY(y1<y2?y1:y2);
                    mapBorder.setMaxY(y1>y2?y1:y2);
                    globalModel.getMapBorderMap().put(mapId, mapBorder);
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(e.toString());
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    /**
     * @Title: doCreateTable
     * @Description: 动态创建位置数据表任务处理器
     */
    public void doCreateTable() {
        //初始化map边界配置文件
        initMapBorders();
        // System.out.println("定时任务：启动服务器时doCreateTable");
        String tableName = "bi_location_" + Util.dateFormat(new Date(), "yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.add(5, 1);
        String time = Util.dateFormat(cal.getTime(), "yyyyMMdd");
        String tableNameTommorrow = "bi_location_" + time;
        try {
            if (statisticsDao.isTableExist(tableName, this.db) < 1) {
                LOG.info("create table today:" + tableName);

                statisticsDao.createTable(tableName);
            }

            if (this.statisticsDao.isTableExist(tableNameTommorrow, this.db) < 1) {
                LOG.info("create table tommorrow:" + tableName);

                statisticsDao.createTable(tableNameTommorrow);
            }
        } catch (Exception e) {
            LOG.error(e);
        }
        // 店铺表
        String nowMouths = Util.dateFormat(new Date(), Params.YYYYMM);
        String shopTableName = Params.SHOPLOCATION + nowMouths;
        try {
            // 创建shop表
            if (statisticsDao.isTableExist(shopTableName, db) < 1) {
                LOG.info("create" + shopTableName);
                // 动态创建表
                statisticsDao.createShopTable(shopTableName);
            }
        } catch (Exception e) {
            LOG.error(e);
        }
        // 商场表
        String storeTableName = Params.STORELOCATION + nowMouths;
        try {
            // 创建shop表
            if (statisticsDao.isTableExist(storeTableName, db) < 1) {
                LOG.info("create" + storeTableName);
                // 动态创建表
                statisticsDao.createStoreTable(storeTableName);
            }
        } catch (Exception e) {
            LOG.error(e);
        }

        String localPath = getClass().getResource("/").getPath();
        // String localPath = System.getProperty("user.dir");
        // localPath = localPath.substring(0,
        // localPath.indexOf("bin"))+"webapps/SVAProject/WEB-INF";
        localPath = localPath.substring(0, localPath.indexOf("/classes"));
        System.out.println(localPath);
        localPath = localPath + File.separator + "ftp" + File.separator;
        File file = new File(localPath, "lac.txt");
        if (file.exists()) {
            List<JSONObject> list = new ArrayList<>();
            String[] names = Params.LAC_COLUMNS;
            BufferedReader reader = null;
            InputStreamReader isr = null;
            try {
                isr = new InputStreamReader(new FileInputStream(file), "gbk");
                reader = new BufferedReader(isr);
                String tempString = null;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    JSONObject jsonObject = new JSONObject();
                    String[] values = tempString.replace("|", "_").split("_");
                    for (int i = 0; i < names.length; i++) {
                        jsonObject.put(names[i], values[i]);
                    }
                    jsonObject.put("time", time);
                    list.add(jsonObject);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (IOException e1) {
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
            visitorDao.clearData1();
            visitorDao.saveData1(list);
        }

        // 数据库清理
        cleanTableService.cleanTableInDb();
    }

    public void doStatistics() {
        // System.out.println("定时任务：doStatistics");
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String nowMouth = Util.dateFormat(new Date(), Params.YYYYMM00);
        String nowMouths = Util.dateFormat(new Date(), Params.YYYYMM);
        String tableName = Params.LOCATION + nowDay;
        String shopTableName = Params.SHOPLOCATION + nowMouths;
        // 商场数据集合
        List<Map<String, Object>> list = statisticsDao.getStoreStatistics(tableName);
        LOG.debug("tableName:" + tableName + " siteList:" + list);
        String storeDay = "replace into bi_static_store_delay(time,delaytime,allcount,storeId,type) values";
        String visitTime = null;
        for (Map<String, Object> m : list) {
            int allcount = Integer.parseInt(m.get("allcount").toString());
            int usercount = Integer.parseInt(m.get("usercount").toString());
            int storeId = Integer.parseInt(m.get("storeId").toString());
            visitTime = Util.getMinute(Long.valueOf((allcount * 2000)), usercount);
            storeDay += "('" + nowDay + "','" + visitTime + "','" + usercount + "','" + storeId + "','" + 0 + "'),";
        }
        if (list.size() > 0) {
            storeDay = storeDay.substring(0, storeDay.length() - 1);
            int result = statisticsDao.doUpdate(storeDay);
            LOG.debug("storeDay result:" + result);
        }
        // 查询所有的区域
        List<AreaModel> listAreaData = statisticsDao.getAreaData();
        String userVisitTime = null;
        int areaSize = listAreaData.size();
        LOG.debug("storeDay listAreaDataSize:" + areaSize);
        // System.out.println("定时shopTableName:" + shopTableName + "
        // listAreaDataSize:" + areaSize);
        for (int i = 0; i < areaSize; i++) {
            String userDay = "replace into " + shopTableName + "(time,delaytime,userId,shopId,type) values";
            AreaModel areaModel = listAreaData.get(i);
            List<Map<String, Object>> listArea = statisticsDao.getUserDataByArea(tableName, areaModel);
            int shopId = 0;
            for (Map<String, Object> m : listArea) {
                int allcount = Integer.parseInt(m.get("allcount").toString());
                String userId = m.get("userId").toString();
                shopId = Integer.parseInt(m.get("shopId").toString());
                userVisitTime = Util.getMinute(Long.valueOf((allcount * 2000)), 1);
                userDay += "('" + nowDay + "','" + userVisitTime + "','" + userId + "','" + shopId + "','" + 0 + "'),";
            }
            if (listArea.size() > 0) {
                userDay = userDay.substring(0, userDay.length() - 1);
                int areaResult = statisticsDao.doUpdate(userDay);
                LOG.debug("storeDay result:" + areaResult + " shopId:" + shopId);
            }

        }

        // 楼层每天驻留时长人数统计
        // List<String> mapList = mapMngDao.getAllMapId();
        List<Map<String, Object>> maps = statisticsDao.getAllcountByMapId(tableName);
        String userDay = "replace into bi_static_floor (time,delaytime,allcount,mapId) values";
        for (int j = 0; j < maps.size(); j++) {
            String chaMapIds = String.valueOf(maps.get(j).get("mapId"));
            String allcount = String.valueOf(maps.get(j).get("allcount"));
            int count = Integer.parseInt(String.valueOf(maps.get(j).get("count")));
            String floorVisit = Util.getMinute(Long.valueOf((Integer.parseInt(allcount) * 2000)), count);
            userDay += "('" + nowDay + "','" + floorVisit + "','" + count + "','" + chaMapIds + "'),";

        }
        if (maps.size() > 0) {
            userDay = userDay.substring(0, userDay.length() - 1);
            int areaResult = statisticsDao.doUpdate(userDay);
            LOG.debug("floorDay result:" + areaResult);
        }

        // 商场按月统计
        String storeMouth = "replace into bi_static_store_delay(time,delaytime,allcount,storeId,type) values";
        List<Map<String, Object>> storeListMouth = statisticsDao.getStoreByMouth(nowMouth);
        double storeVisitTime = 0;
        for (Map<String, Object> m : storeListMouth) {
            double allTime = Double.valueOf(m.get("allTime").toString());
            int allcount = Integer.parseInt(m.get("allcount").toString());
            int storeId = Integer.parseInt(m.get("storeId").toString());
            storeVisitTime = allTime / allcount;
            storeMouth += "('" + nowMouth + "','" + storeVisitTime + "','" + allcount + "','" + storeId + "','" + 1
                    + "'),";
        }
        if (storeListMouth.size() > 0) {
            storeMouth = storeMouth.substring(0, storeMouth.length() - 1);
            int areaResult = statisticsDao.doUpdate(storeMouth);
            LOG.debug("storeMouth result:" + areaResult);
        }

    }

    public void saveVisitTime() {
        try {
            String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
            // String nowDay1 = Util.dateFormat(new Date(), Params.YYYYMMDD2);
            String nowMouth = Util.dateFormat(new Date(), Params.YYYYMMddHH00);
            String tableName = Params.LOCATION + nowDay;
            long endTime = System.currentTimeMillis();
            long beginTime = endTime - 60 * 60 * 1000;
            String insertFloor = "replace into bi_static_floor_visitTime(time,delaytime,allcount,mapId) values";
            String insertStore = "replace into bi_static_store_visitTime(time,delaytime,allcount,storeId) values";
            String insertShop = "replace into bi_static_shop_visitTime(time,delaytime,allcount,shopId) values";
            List<VisitTimeModel> list = locationDao.getMapVisitTime(tableName, beginTime, endTime);
            List<VisitTimeModel> mapCount = locationDao.getCountGroupByMapId(tableName);
            List<VisitTimeModel> storeCount = locationDao.getCountGroupByStoreId(tableName);
            List<VisitTimeModel> shopCount = locationDao.getCountGroupByShopId(tableName);
            Map<String, Long> map = getFloorVisitTime(list);
            Map<String, Long> mapIdS = getListToMap(mapCount);
            Map<String, Long> stores = getListToMap(storeCount);
            Map<String, Long> shops = getListToMap(shopCount);
            Set<String> set = map.keySet();
            Map<String, Long> storeVisitTime = new HashMap<String, Long>();
            for (String s : set) {
                String mapId = s.split("-")[0];
                String storeId = s.split("-")[1];
                long visitTime = map.get(s);
                long allCount = mapIdS.get(mapId);
                String floorVisit = Util.getMinute(visitTime, 1);
                if (storeVisitTime.containsKey(storeId)) {
                    long times = storeVisitTime.get(storeId);
                    storeVisitTime.put(storeId, (times + visitTime) / 2);
                } else {
                    storeVisitTime.put(storeId, visitTime);
                }
                insertFloor += "('" + nowMouth + "','" + floorVisit + "','" + allCount + "','" + mapId + "'),";
            }
            if (set.size() > 0) {
                insertFloor = insertFloor.substring(0, insertFloor.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertFloor);
                LOG.debug("saveVisitTime-floor result:" + areaResult);
            }

            Set<String> storeSet = storeVisitTime.keySet();
            for (String s : storeSet) {
                long visitTime = storeVisitTime.get(s);
                long allCount = stores.get(s);
                String storeVisit = Util.getMinute(visitTime, 1);
                insertStore += "('" + nowMouth + "','" + storeVisit + "','" + allCount + "','" + s + "'),";
            }
            if (storeSet.size() > 0) {
                insertStore = insertStore.substring(0, insertStore.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertStore);
                LOG.debug("saveVisitTime-store result:" + areaResult);
            }
            List<VisitTimeModel> shopList = locationDao.getShopVisitTime(tableName, beginTime, endTime);
            Map<String, Long> shopMap = getShopVisitTime(shopList);
            Set<String> shopSet = shopMap.keySet();
            for (String s : shopSet) {
                long visitTime = shopMap.get(s);
                long allCount = shops.get(s);
                String shopVisit = Util.getMinute(visitTime, 1);
                insertShop += "('" + nowMouth + "','" + shopVisit + "','" + allCount + "','" + s + "'),";
            }
            if (shopSet.size() > 0) {
                insertShop = insertShop.substring(0, insertShop.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertShop);
                LOG.debug("saveVisitTime-shop result:" + areaResult);
            }
            // List<WeekTotalModel> shopXYCount =
            // locationDao.getCountGroupByShopIdAndXY(tableName);
            // String insertShopCount = "replace into
            // bi_static_shop_count(time,shopName,allcount,shopId) values";
            // for (int i = 0; i < shopXYCount.size(); i++) {
            // WeekTotalModel model = shopXYCount.get(i);
            // String shopName = model.getMyTime();
            // int allcount = model.getAllCount();
            // int myId= model.getMyId();
            // insertShopCount += "('" + nowDay1 + "','" + shopName + "','" +
            // allcount + "','" + myId + "'),";
            // }
            // if (shopXYCount.size()>0) {
            // insertShopCount = insertShopCount.substring(0,
            // insertShopCount.length() - 1);
            // int areaResult = statisticsDao.doUpdate(insertShopCount);
            // LOG.debug("saveVisitTime-shopXY result:" + areaResult);
            // }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.out.println(1);
        }

    }

    public void newSaveVisitTime() {
        try {
            String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
            String nowMouth = Util.dateFormat(new Date(), Params.YYYYMMddHH00);
            String nowDays = Util.dateFormat(new Date(), Params.YYYYMMDD2);
            String tableName = Params.LOCATION + nowDay;
            long endTime = System.currentTimeMillis();
            long beginTime = endTime - 60 * 60 * 1000;
            String insertFloor = "replace into bi_static_floor_visitTime(time,delaytime,allcount,mapId,allcounts) values";
            String insertStore = "replace into bi_static_store_visitTime(time,delaytime,allcount,storeId,allcounts) values";
            String insertShop = "replace into bi_static_shop_visitTime(time,delaytime,allcount,shopId,allcounts) values";

            List<VisitTimeModel> listMapId = locationDao.getMapVisitTime(tableName, beginTime, endTime);
            Map<String, String> mapFloor = getNewMap(listMapId);
            // List<VisitTimeModel> mapCount =
            // locationDao.getCountGroupByMapId(tableName);
            // 6Map<String, Long> mapIdS = getListToMap(mapCount);
            Set<String> mapSet = mapFloor.keySet();
            for (String s : mapSet) {
                String mapId = s.split("-")[0];
                if (mapId != null && mapId != "null" && mapId != "") {
                    String allcount = s.split("-")[1];
                    String visitTime = mapFloor.get(s);
                    int allCounts = locationDao.getOneDayData(mapId, tableName);
                    insertFloor += "('" + nowMouth + "','" + visitTime + "','" + allcount + "','" + mapId + "','"
                            + allCounts + "'),";
                }
            }
            if (mapSet.size() > 0) {
                insertFloor = insertFloor.substring(0, insertFloor.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertFloor);
                LOG.debug("saveVisitTime-floor result:" + areaResult);
            }
            List<VisitTimeModel> listStore = locationDao.getStoreVisitTime(tableName, beginTime, endTime);
            Map<String, String> mapStore = getNewMap(listStore);
            // List<VisitTimeModel> storeCount =
            // locationDao.getCountGroupByStoreId(tableName);
            // Map<String, Long> stores = getListToMap(storeCount);
            Set<String> storeSet = mapStore.keySet();
            for (String s : storeSet) {
                String storeId = s.split("-")[0];
                if (storeId != null && storeId != "null" && storeId != "") {
                    String allcount = s.split("-")[1];
                    String visitTime = mapStore.get(s);
                    int allCounts = locationDao.getStoreAllCount(storeId, tableName);
                    insertStore += "('" + nowMouth + "','" + visitTime + "','" + allcount + "','" + storeId + "','"
                            + allCounts + "'),";
                }
            }
            if (storeSet.size() > 0) {
                insertStore = insertStore.substring(0, insertStore.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertStore);
                LOG.debug("saveVisitTime-store result:" + areaResult);
            }
            String insertEntersql = "insert into bi_static_shop_enter(time,allcount,shopId,allcounts) values";
            List<VisitTimeModel> shopList = locationDao.getShopVisitTime(tableName, beginTime, endTime);
            Map<String, String> mapShop = getNewMap(shopList);
            // List<VisitTimeModel> shopCount =
            // locationDao.getCountGroupByShopId(tableName);
            // Map<String, Long> shops = getListToMap(shopCount);
            Set<String> shopSet = mapShop.keySet();
            String endTimes = nowMouth.split(" ")[1];
            for (String s : shopSet) {
                String shopId = s.split("-")[0];
                if (shopId != null && shopId != "null" && shopId != "") {
                    String allcount = s.split("-")[1];
                    String visitTime = mapShop.get(s);
                    long allCounts = locationDao.getShopAllCount(shopId, tableName);
                    insertShop += "('" + nowMouth + "','" + visitTime + "','" + allcount + "','" + shopId + "','"
                            + allCounts + "'),";
                    if (endTimes.equals("23:00:00")) {
                        long allCountss = locationDao.getShopAllCount2(shopId, tableName);
                        insertEntersql += "('" + nowDays + "','" + allCounts + "','" + shopId + "','" + allCountss
                                + "'),";
                    }
                }
            }
            if (shopSet.size() > 0) {
                insertShop = insertShop.substring(0, insertShop.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertShop);
                LOG.debug("saveVisitTime-shop result:" + areaResult);
            }
            if (endTimes.equals("23:00:00")) {
                insertEntersql = insertEntersql.substring(0, insertEntersql.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertEntersql);
                LOG.debug("saveVisitTime-shop-enter result:" + areaResult);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.out.println(1);
        }

    }

    public void saveUserShop() {
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String userDay = Util.dateFormat(new Date(), Params.YYYYMMDD2);
        String tableName = Params.LOCATION + nowDay;
        String nowMouths = Util.dateFormat(new Date(), Params.YYYYMM);
        String shopTableName = Params.SHOPLOCATION + nowMouths;
        String storeTableName = Params.STORELOCATION + nowMouths;
        String insertStoreUserid = "replace into " + storeTableName + "(userId,time,storeId) values";
        String insertUserid = "replace into " + shopTableName + "(userId,time,delaytime,shopId,type) values";
        List<VisitTimeModel> storeUserListModel = locationDao.getStoreUserList(tableName);
        for (VisitTimeModel sva : storeUserListModel) {
            String userId = sva.getUserId();
            int storeId = sva.getStoreId();
            insertStoreUserid += "('" + userId + "','" + userDay + "','" + storeId + "'),";
        }
        if (storeUserListModel.size() > 0) {
            insertStoreUserid = insertStoreUserid.substring(0, insertStoreUserid.length() - 1);
            int areaResult = statisticsDao.doUpdate(insertStoreUserid);
            LOG.debug("saveUserShop-store-userid result:" + areaResult);
        }
        List<VisitTimeModel> userListModel = locationDao.getUserList(tableName);
        for (VisitTimeModel sva : userListModel) {
            String userId = sva.getUserId();
            long visiTime = sva.getMaxTime() - sva.getMinTime();
            int shopId = sva.getId();
            // int storeId = sva.getStoreId();
            // int mapId = sva.getMapId();
            String shopVisit = Util.getMinute(visiTime, 1);
            insertUserid += "('" + userId + "','" + userDay + "','" + shopVisit + "','" + shopId + "','" + 0 + "'),";
        }
        if (userListModel.size() > 0) {
            insertUserid = insertUserid.substring(0, insertUserid.length() - 1);
            int areaResult = statisticsDao.doUpdate(insertUserid);
            LOG.debug("saveUserShop-shop-userid :" + areaResult);
        }
    }

    /**
     * @Title: calcPeopleRoute
     * @Description: 预处理，获取客户的店铺轨迹信息
     */
    public void calcPeopleRoute() {
        LOG.debug("calcPeopleRoute start:" + new Date().getTime());
        // 今日表
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        analysisService.calcPeopleRoute(tableName);
        LOG.debug("calcPeopleRoute end:" + new Date().getTime());
    }

    public void saveNewPeople() {
        LOG.debug("saveNewPeople start:" + new Date().getTime());
        // 今日表
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        String nowDays = Util.dateFormat(new Date(), Params.YYYYMMDD2);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        String yesDay = Util.dateFormat(c.getTimeInMillis(), Params.YYYYMMDD);
        String yesTableName = Params.LOCATION + yesDay;

        List<ShopModel> list = shopDao.getAllShopData();
        for (int i = 0; i < list.size(); i++) {
            ShopModel model = list.get(i);
            List<String> todayList = new ArrayList<>();
            List<String> yesdayList = new ArrayList<>();
            int newUser = 0;
            int toDayUser = 0;
            int yesDayUser = 0;
            String shopId = model.getId();
            NewUserModel userModel = new NewUserModel();
            todayList = locationDao.getNowAllCount(tableName, model);
            toDayUser = todayList.size();
            yesdayList = locationDao.getNowAllCount(yesTableName, model);
            yesDayUser = yesdayList.size();
            todayList.removeAll(yesdayList);
            newUser = todayList.size();
            userModel.setNewUser(newUser);
            userModel.setShopId(shopId);
            userModel.setTime(nowDays);
            userModel.setTodayUser(toDayUser);
            userModel.setYesdayUser(yesDayUser);
            int result = statisticsDao.saveNewUser(userModel);
            LOG.debug("saveNewPeople shopId:" + shopId + ",reslut:" + result);
        }
        List<MarketModel> marketList = marketDao.getAllStore();
        for (int i = 0; i < marketList.size(); i++) {
            String storeId = marketList.get(i).getId();
            List<String> todayList = new ArrayList<>();
            List<String> yesdayList = new ArrayList<>();
            int newUser = 0;
            int toDayUser = 0;
            int yesDayUser = 0;
            NewUserModel userModel = new NewUserModel();
            todayList = statisticsDao.getStroeUserById(storeId, tableName);
            toDayUser = todayList.size();
            yesdayList = statisticsDao.getStroeUserById(storeId, yesTableName);
            yesDayUser = yesdayList.size();
            todayList.removeAll(yesdayList);
            newUser = todayList.size();
            userModel.setNewUser(newUser);
            userModel.setStoreId(storeId);
            userModel.setTime(nowDays);
            userModel.setTodayUser(toDayUser);
            userModel.setYesdayUser(yesDayUser);
            int result = statisticsDao.saveNewUser(userModel);
            LOG.debug("saveNewPeople storeId:" + storeId + ",reslut:" + result);
        }

        List<String> mapIdList = mapMngDao.getAllMapId();
        for (int i = 0; i < mapIdList.size(); i++) {
            String mapId = mapIdList.get(i);
            List<String> todayList = new ArrayList<>();
            List<String> yesdayList = new ArrayList<>();
            int newUser = 0;
            int toDayUser = 0;
            int yesDayUser = 0;
            NewUserModel userModel = new NewUserModel();
            todayList = statisticsDao.getMapIdUser(tableName, mapId);
            toDayUser = todayList.size();
            yesdayList = statisticsDao.getMapIdUser(yesTableName, mapId);
            yesDayUser = yesdayList.size();
            todayList.removeAll(yesdayList);
            newUser = todayList.size();
            userModel.setNewUser(newUser);
            userModel.setMapId(mapId);
            userModel.setTime(nowDays);
            userModel.setTodayUser(toDayUser);
            userModel.setYesdayUser(yesDayUser);
            int result = statisticsDao.saveNewUser(userModel);
            LOG.debug("saveNewPeople floor:" + mapId + ",reslut:" + result);
        }
        LOG.debug("saveNewPeople end:" + new Date().getTime());
    }

    public void SubscriptionSva() {
        LOG.debug("QuartzJob 订阅类型：" + subscriptionType);
        boolean result = verificationSva();
        if (result) {
            if (subscriptionType == 0) {
                Map<String, SvaModel> map = getSvaModel();
                SvaModel nonAnonymousModel = map.get("nonAnonymousModel");
                SvaModel SpecifiesModel = map.get("SpecifiesModel");
                try {
                    if (nonAnonymousModel != null) {
                        for (int i = 0; i < subTimes; i++) {
                            nonAnonymousModel.setId(String.valueOf(i + 1));
                            service.subscribeSva(nonAnonymousModel);
                        }
                    }
                    if (SpecifiesModel != null) {
                        SpecifiesModel.setId(String.valueOf(subTimes + 1));
                        service.subscribeSva(SpecifiesModel);
                    }
                } catch (Exception e) {
                    LOG.debug("QuartzJob sva 订阅出错：" + e.getMessage());
                }
            } else {
                String baseUrl = null;
                String url1 = subivasurl1;
                String url2 = subivasurl2;
                if (subscriptionUrl == 0) {
                    baseUrl = url1;
                } else {
                    baseUrl = url2;
                }
                String url = null;
                String subUrl = null;
                String sign = null;
                String subSign = null;
                String token = null;
                MySHA256 my = new MySHA256();
                List<IvasSvaModel> list = statisticsDao.getIvasList();
                for (int i = 0; i < list.size(); i++) {
                    SvaModel sva = new SvaModel();
                    IvasSvaModel model = list.get(i);
                    sva.setId(String.valueOf(model.getId()));
                    String myKey = model.getMyKey();
                    String mySecret = model.getMySecret();
                    String getTokenUrl1 = model.getGetTokenUrl();
                    String getTokenUrl = "/api" + model.getGetTokenUrl();
                    String appName = model.getAppName();
                    String appPassWord = model.getAppPassWord();
                    String subscriptionUrl1 = model.getSubscriptionUrl();
                    String subscriptionUrl = "/api" + model.getSubscriptionUrl();
                    sva.setUsername(appName);
                    url = baseUrl + getTokenUrl1;
                    String getTokenParam = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                            + appName + "\",\"password\": \"" + appPassWord + "\"}}},\"iplist\":[]}}";
                    sign = my.JavaSHA256(mySecret + getTokenUrl + getTokenParam).toUpperCase();
                    token = service.getIvasToken(url, myKey, sign, getTokenParam);
                    LOG.debug("QuartzJob ivas getToken:" + token);
                    subUrl = baseUrl + subscriptionUrl1;
                    String subParam = "{\"APPID\":\"" + appName + "\"}";
                    // JSONObject param = new JSONObject();
                    // param.put("appid",appName);
                    subSign = my.JavaSHA256(mySecret + subscriptionUrl + subParam).toUpperCase();
                    service.subIvasSva(subUrl, myKey, subSign, subParam, token, sva);

                }
            }
        }

    }

    public void savePeripheryService() {

        peripheryService.readPeripheralService();
    }

    public Map<String, SvaModel> getSvaModel() {
        Map<String, SvaModel> map = new HashMap<String, SvaModel>();
        SvaModel nonAnonymousModel = new SvaModel();
        SvaModel SpecifiesModel = new SvaModel();
        String path = getClass().getResource("/").getPath();
        LOG.debug("SubscriptionSva Filepath:" + path);
        try {
            File f = new File(path + "svaConfigue.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            NodeList nl2 = doc.getElementsByTagName("Configuration");
            for (int i = 0; i < nl2.getLength(); i++) {
                nonAnonymousModel.setId(doc.getElementsByTagName("id").item(i).getFirstChild().getNodeValue());
                nonAnonymousModel.setIp(doc.getElementsByTagName("ip").item(i).getFirstChild().getNodeValue());
                nonAnonymousModel
                        .setTokenPort(doc.getElementsByTagName("token_port").item(i).getFirstChild().getNodeValue());
                nonAnonymousModel
                        .setBrokerPort(doc.getElementsByTagName("broker_port").item(i).getFirstChild().getNodeValue());
                nonAnonymousModel.setUsername(doc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue());
                nonAnonymousModel
                        .setPassword(doc.getElementsByTagName("password").item(i).getFirstChild().getNodeValue());
                nonAnonymousModel.setStatus(
                        Integer.parseInt(doc.getElementsByTagName("status").item(i).getFirstChild().getNodeValue()));
                nonAnonymousModel.setType(Integer
                        .parseInt(doc.getElementsByTagName("subscribe_type").item(i).getFirstChild().getNodeValue()));
                nonAnonymousModel.setIdType(doc.getElementsByTagName("ip_type").item(i).getFirstChild().getNodeValue());
                // 指定用户信息
                SpecifiesModel.setId(doc.getElementsByTagName("id1").item(i).getFirstChild().getNodeValue());
                SpecifiesModel.setIp(doc.getElementsByTagName("ip1").item(i).getFirstChild().getNodeValue());
                SpecifiesModel
                        .setTokenPort(doc.getElementsByTagName("token_port1").item(i).getFirstChild().getNodeValue());
                SpecifiesModel
                        .setBrokerPort(doc.getElementsByTagName("broker_port1").item(i).getFirstChild().getNodeValue());
                SpecifiesModel.setUsername(doc.getElementsByTagName("name1").item(i).getFirstChild().getNodeValue());
                SpecifiesModel
                        .setPassword(doc.getElementsByTagName("password1").item(i).getFirstChild().getNodeValue());
                SpecifiesModel.setStatus(
                        Integer.parseInt(doc.getElementsByTagName("status1").item(i).getFirstChild().getNodeValue()));
                SpecifiesModel.setType(Integer
                        .parseInt(doc.getElementsByTagName("subscribe_type1").item(i).getFirstChild().getNodeValue()));
                SpecifiesModel.setIdType(doc.getElementsByTagName("ip_type1").item(i).getFirstChild().getNodeValue());
            }

        } catch (Exception e) {
            LOG.debug("QuartzJob:解析配置出错!");
        }
        map.put("nonAnonymousModel", nonAnonymousModel);
        map.put("SpecifiesModel", SpecifiesModel);
        return map;
    }

    /**
     * 
     * @Title: getTrend
     * @Description: 楼层和店铺关联动向统计预处理
     */
    public void getTrend() {
        // System.out.println("楼层和店铺动向预处理调用了...");
        try {

            LOG.info("QuartzJob ~ getTrend 楼层和店铺关联动向预处理");
            Calendar calendar = Calendar.getInstance();
            // 防止跑到下一天，往前推30分钟取时间值
            calendar.add(Calendar.MINUTE, -30);
            Date nowDate = calendar.getTime();
            String nowDay = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD);
            int hour = calendar.get(Calendar.HOUR_OF_DAY) + 1;
            String tableName = Params.LOCATION + nowDay;
            // 根据楼层计算动向预处理
            List<String> mapIdList = mapMngDao.getAllMapId();
            LocationModel model1;
            for (String mapId : mapIdList) {
                // 用户来源map
                Map<String, Integer> trendMap = new HashMap<>();
                List<String> userIdList = locationDao.queryAllUserIdByMapId(mapId, tableName);
                for (String userId : userIdList) {
                    model1 = locationDao.getOtherMapIdByMaxTime(mapId, userId, tableName);
                    if (model1 == null) {
                        if (trendMap.get("other") == null) {
                            trendMap.put("other", 1);
                        } else {
                            trendMap.put("other", trendMap.get("other") + 1);
                        }
                    } else {
                        String resultMapId = model1.getMapId();
                        if (trendMap.get(resultMapId) == null) {
                            trendMap.put(resultMapId, 1);
                        } else {
                            trendMap.put(resultMapId, trendMap.get(resultMapId) + 1);
                        }
                    }
                    model1 = null;
                }
                TrendMapModel trendMapModel = new TrendMapModel(mapId, hour, nowDate);
                // 按小时保存
                for (Map.Entry<String, Integer> entry : trendMap.entrySet()) {
                    if ("other".equals(entry.getKey())) {
                        trendMapModel.setFromMapId(null);
                    } else {
                        trendMapModel.setFromMapId(entry.getKey());
                    }
                    trendMapModel.setVisitorCount(entry.getValue());
                    locationDao.saveTrendMapByHour(trendMapModel);
                }
                // 如果hour为24同时按日保存
                if (hour == 24) {
                    int day = calendar.get(Calendar.DATE);
                    trendMapModel.setSign(day);
                    for (Map.Entry<String, Integer> entry : trendMap.entrySet()) {
                        if ("other".equals(entry.getKey())) {
                            trendMapModel.setFromMapId(null);
                        } else {
                            trendMapModel.setFromMapId(entry.getKey());
                        }
                        trendMapModel.setVisitorCount(entry.getValue());
                        locationDao.saveTrendMapByDay(trendMapModel);
                    }
                }
            }
            // 根据店铺计算动向预处理
            List<Integer> shopIdList = shopDao.getAllShopId();
            LocationModel model2;
            for (int shopId : shopIdList) {
                Map<Integer, Integer> trendMap = new HashMap<>(); // 用户来源map
                List<String> userIdList = locationDao.queryAllUserIdByShopId(shopId, tableName);
                for (String userId : userIdList) {
                    model2 = locationDao.getOtherShopIdByMaxTime(shopId, userId, tableName);
                    if (model2 == null) {
                        if (trendMap.get(-1) == null) {
                            trendMap.put(-1, 1);
                        } else {
                            trendMap.put(-1, trendMap.get(-1) + 1);
                        }
                    } else {
                        int resultMapId = model2.getId();
                        if (trendMap.get(resultMapId) == null) {
                            trendMap.put(resultMapId, 1);
                        } else {
                            trendMap.put(resultMapId, trendMap.get(resultMapId) + 1);
                        }
                    }
                    model2 = null;
                }
                TrendShopModel trendShopModel = new TrendShopModel(shopId, hour, nowDate);
                // 按小时保存
                for (Map.Entry<Integer, Integer> entry : trendMap.entrySet()) {
                    trendShopModel.setFromShopId(entry.getKey());
                    trendShopModel.setVisitorCount(entry.getValue());
                    locationDao.saveTrendShopByHour(trendShopModel);
                }
                // 如果hour为24同时按日保存
                if (hour == 24) {
                    int day = calendar.get(Calendar.DATE);
                    trendShopModel.setSign(day);
                    for (Map.Entry<Integer, Integer> entry : trendMap.entrySet()) {
                        trendShopModel.setFromShopId(entry.getKey());
                        trendShopModel.setVisitorCount(entry.getValue());
                        locationDao.saveTrendShopByDay(trendShopModel);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void saveTrend() {
        Calendar calendar = Calendar.getInstance();
        // 防止跑到下一天，往前推30分钟取时间值
        calendar.add(Calendar.MINUTE, -30);
        String nowDay = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD);
        int hour = calendar.get(Calendar.HOUR_OF_DAY) + 1;
        String tableName = Params.LOCATION + nowDay;
        List<TrendAllModel> list = locationDao.getAllTrendData(tableName);
        String sql = "INSERT INTO bi_trend_map_hour( mapId,fromMapId,visitorCount,hour,time)VALUES";
        String sqlDay = "INSERT INTO bi_trend_map_day( mapId,fromMapId,visitorCount,day,time)VALUES";
        Map<String, Integer> map = calculationTrend(list);
        Set<String> set = map.keySet();
        int mapId = 0;
        String fromMapId = null;
        int count = 0;
        for (String s : set) {
            String[] arrayStr = s.split("-");
            mapId = Integer.parseInt(arrayStr[0]);
            fromMapId = arrayStr[1];
            count = map.get(s);
            sql += "('" + mapId + "'," + fromMapId + ",'" + count + "','" + hour + "','" + nowDay + "'),";
        }
        if (set.size() > 0) {
            String insertsql = sql.substring(0, sql.length() - 1);
            statisticsDao.doUpdate(insertsql);
        }
        // 如果hour为24同时按日保存
        if (hour == 24) {
            int day = calendar.get(Calendar.DATE);
            for (String s : set) {
                String[] arrayStr = s.split("-");
                mapId = Integer.parseInt(arrayStr[0]);
                fromMapId = arrayStr[1];
                count = map.get(s);
                sqlDay += "('" + mapId + "'," + fromMapId + ",'" + count + "','" + day + "','" + nowDay + "'),";
            }
            if (set.size() > 0) {
                String insertsql = sqlDay.substring(0, sqlDay.length() - 1);
                statisticsDao.doUpdate(insertsql);
            }
        }
        String sqlshop = "INSERT INTO bi_trend_shop_hour( shopId,fromShopId,visitorCount,hour,time)VALUES";
        String sqlShopDay = "INSERT INTO bi_trend_shop_day( shopId,fromShopId,visitorCount,day,time)VALUES";

        List<TrendAllModel> shopList = locationDao.getAllTrendShopData(tableName);
        Map<String, Integer> shopMap = calculationTrend(shopList);

        Set<String> shopset = shopMap.keySet();
        int shopId = 0;
        String fromShopId = null;
        int shopCount = 0;
        for (String s : shopset) {
            String[] arrayStr = s.split("-");
            shopId = Integer.parseInt(arrayStr[0]);
            fromShopId = arrayStr[1];
            shopCount = shopMap.get(s);
            sqlshop += "('" + shopId + "'," + fromShopId + ",'" + shopCount + "','" + hour + "','" + nowDay + "'),";
        }
        if (shopset.size() > 0) {
            String insertsql = sqlshop.substring(0, sqlshop.length() - 1);
            statisticsDao.doUpdate(insertsql);
        }
        // 如果hour为24同时按日保存
        if (hour == 24) {
            int day = calendar.get(Calendar.DATE);
            for (String s : shopset) {
                String[] arrayStr = s.split("-");
                shopId = Integer.parseInt(arrayStr[0]);
                fromShopId = arrayStr[1];
                shopCount = shopMap.get(s);
                sqlShopDay += "('" + shopId + "'," + fromShopId + ",'" + shopCount + "','" + day + "','" + nowDay
                        + "'),";
            }
            if (set.size() > 0) {
                String insertsql = sqlShopDay.substring(0, sqlShopDay.length() - 1);
                statisticsDao.doUpdate(insertsql);
            }
        }

    }

    private static Map<String, Integer> calculationTrend(List<TrendAllModel> list) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String user = list.get(0).getUserid();
        String newUuser;
        int mapId = list.get(0).getMapId();
        int fromMapId;
        String key;
        for (TrendAllModel sva : list) {
            newUuser = sva.getUserid();
            fromMapId = sva.getMapId();
            if (newUuser.equals(user)) {
                if (fromMapId == mapId) {
                    continue;
                } else {
                    key = mapId + "-" + fromMapId;
                    if (map.containsKey(key)) {
                        map.put(key, map.get(key) + 1);
                    } else {
                        map.put(key, 1);
                    }
                    mapId = sva.getMapId();
                }
            } else {
                key = mapId + "-" + "null";
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + 1);
                } else {
                    map.put(key, 1);
                }
                mapId = sva.getMapId();
                user = sva.getUserid();
            }

        }
        return map;
    }

    /**
     * @Title: verificationSva
     * @Description: sva验证
     * @return
     */
    private boolean verificationSva() {
        long nowTime = System.currentTimeMillis() - 10000;
        String tableName = Params.LOCATION + Util.dateFormat(new Date(), "yyyyMMdd");
        int count = 0;
        count = locationDao.getCountByTimestamp(nowTime, tableName);
        boolean result = false;
        if (count == 0) {
            result = true;
        }
        LOG.debug("verificationSva result:" + result);
        return result;
    }

    public void doFtpData() {
        // System.out.println("定时任务：解析visitor文件");
        String localPath = getClass().getResource("/").getPath();
        // String localPath = System.getProperty("user.dir");
        // localPath = localPath.substring(0,
        // localPath.indexOf("bin"))+"webapps/SVAProject/WEB-INF";
        localPath = localPath.substring(0, localPath.indexOf("/classes"));
        localPath = localPath + File.separator + "ftp" + File.separator;
        LOG.debug("doFtpData localPath" + localPath);
        // 解析前一天的visitor
        Date date = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
        String dateKey = Util.dateFormat(date, Params.YYYYMMDD);
        String fileName = ftpFileNameHeader + dateKey + ".txt";
        boolean ftpResult = Util.downFtpFile(ftpIp, ftpPort, ftpUserName, ftpPassWord, ftpRemotePath, fileName,
                localPath, ftpType);
        if (ftpResult) {

            // String time = Util.dateFormat(date, Params.YYYYMMDDHHMMSS);
            String filePath = localPath;
            File file = new File(filePath, fileName);
            if (file.exists()) {
                // System.out.println("信息文件存在");
                List<JSONObject> list = new ArrayList<>();
                String[] names = Params.VISITOR_COLUMNS;
                BufferedReader reader = null;
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
                    reader = new BufferedReader(isr);
                    String tempString = null;
                    // 一次读入一行，直到读入null为文件结束
                    while ((tempString = reader.readLine()) != null) {
                        JSONObject jsonObject = new JSONObject();
                        String[] values = (tempString + "|end").replace("|", "_").split("_");
                        for (int i = 0; i < names.length; i++) {
                            // {"date","ipv4","ipv6","acr","eci","gender","age","localAddress","homeAddress",
                            // "homeAddressCI","workAddress","workAddressCI","expendAbility"};
                            if (Util.isIp(values[1])) {
                                switch (i) {
                                case 1: // ipv4
                                    jsonObject.put(names[i], Util.convertIp(values[i]));
                                    break;
                                case 3: // acr
                                case 4: // eci
                                    jsonObject.put(names[i], values[i].length() < 200 ? values[i] : "error");
                                    break;
                                case 5: // gender
                                case 6: // age
                                case 12: // expendAbility
                                    jsonObject.put(names[i], "".equals(values[i]) ? "不详" : values[i]);
                                    break;
                                // case 7: // localAddress
                                // break;
                                case 8: // homeAddress
                                case 10: // workAddress
                                    jsonObject.put(names[i], values[i] + "_" + values[i + 1]);
                                    break;
                                default:
                                    jsonObject.put(names[i], values[i]);
                                    break;
                                }
                            }
                        }
                        jsonObject.put("time", dateKey);
                        if (values.length >= 14 && Util.isIp(values[1])) {
                            list.add(jsonObject);
                        }

                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (isr != null) {
                        try {
                            isr.close();
                        } catch (IOException e1) {
                        }
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                        }
                    }
                }
                if (list.size() > 0) {
                    visitorDao.saveData(list);
                }
            }
            // LOG.debug("VisitorController~插入Visitor数据条数:" + num);
        } else {
            LOG.debug("doFtpData downFtpFile failed result " + ftpResult);
        }
    }

    private static Map<String, Long> getFloorVisitTime(List<VisitTimeModel> list) {
        HashMap<String, Long> map = new HashMap<String, Long>();
        if (list.size() > 0) {
            int id = list.get(0).getId();
            int newId;
            for (VisitTimeModel sva : list) {
                newId = sva.getId();
                if (newId == id) {
                    long visitTime = sva.getMaxTime() - sva.getMinTime();
                    int storeId = sva.getStoreId();
                    String key = newId + "-" + storeId;
                    // 小於2分鐘忽略
                    if (visitTime < 120000) {
                        continue;
                    }
                    if (map.containsKey(key)) {
                        long visitTimes = (map.get(key) + visitTime) / 2;
                        map.put(key, visitTimes);
                    } else {
                        map.put(key, visitTime);
                    }
                    id = sva.getId();
                } else {
                    long visitTime = sva.getMaxTime() - sva.getMinTime();
                    int storeId = sva.getStoreId();
                    String key = newId + "-" + storeId;
                    // 小於2分鐘忽略
                    if (visitTime < 120000) {
                        continue;
                    }
                    if (map.containsKey(key)) {
                        long visitTimes = (map.get(key) + visitTime) / 2;
                        map.put(key, visitTimes);
                    } else {
                        map.put(key, visitTime);
                    }
                    id = sva.getId();
                }
            }
            return map;
        } else {
            return null;
        }
    }

    private static Map<String, Long> getShopVisitTime(List<VisitTimeModel> list) {
        HashMap<String, Long> map = new HashMap<String, Long>();
        if (list.size() > 0) {
            int id = list.get(0).getId();
            int newId;
            for (VisitTimeModel sva : list) {
                newId = sva.getId();
                if (newId == id) {
                    long visitTime = sva.getMaxTime() - sva.getMinTime();
                    String key = newId + "";
                    // 小於2分鐘忽略
                    if (visitTime < 120000) {
                        continue;
                    }
                    if (map.containsKey(key)) {
                        long visitTimes = (map.get(key) + visitTime) / 2;
                        map.put(key, visitTimes);
                    } else {
                        map.put(key, visitTime);
                    }
                    id = sva.getId();
                } else {
                    long visitTime = sva.getMaxTime() - sva.getMinTime();
                    String key = newId + "";
                    // 小於2分鐘忽略
                    if (visitTime < 300000) {
                        continue;
                    }
                    if (map.containsKey(key)) {
                        long visitTimes = (map.get(key) + visitTime) / 2;
                        map.put(key, visitTimes);
                    } else {
                        map.put(key, visitTime);
                    }
                    id = sva.getId();
                }
            }
            return map;
        } else {
            return null;
        }
    }

    private static Map<String, Long> getListToMap(List<VisitTimeModel> list) {
        HashMap<String, Long> map = new HashMap<String, Long>();
        if (list.size() > 0) {
            for (VisitTimeModel sva : list) {
                String id = String.valueOf(sva.getId());
                long count = sva.getMaxTime();
                map.put(id, count);
            }
            return map;
        } else {
            return null;
        }
    }

    private static Map<String, String> getNewMap(List<VisitTimeModel> list) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (list.size() > 0) {
            int id = list.get(0).getId();
            int newId;
            int count = 0;
            int allCount = 0;
            long tempVistTime = 0;
            String key = null;
            for (VisitTimeModel sva : list) {
                newId = sva.getId();
                if (newId == id) {
                    allCount = allCount + 1;
                    long visitTime = sva.getMaxTime() - sva.getMinTime();
                    key = newId + "";
                    // 小於2分鐘忽略
                    if (visitTime > 300000) {
                        count = count + 1;
                        tempVistTime = tempVistTime + visitTime;
                    }
                    id = sva.getId();
                } else {
                    key = key + "-" + allCount;
                    map.put(key, Util.getMinute(tempVistTime, count));
                    count = 0;
                    allCount = 0;
                    tempVistTime = 0;
                    key = null;
                    id = sva.getId();
                }
            }
            if (count != 0) {
                key = key + "-" + allCount;
                map.put(key, Util.getMinute(tempVistTime, count));
            }
            return map;
        } else {
            return null;
        }
    }

    public void deleteTable() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        String time = Util.dateFormat(calendar.getTime(), Params.YYYYMMddHH00);
        String deletSql = "delete from bi_static_floor_visittime where time <  '" + time + "'";
        String deletSql1 = "delete from bi_static_shop_visittime where time <  '" + time + "'";
        String deletSql2 = "delete from bi_static_store_visittime where time <  '" + time + "'";
        statisticsDao.doUpdate(deletSql);
        statisticsDao.doUpdate(deletSql1);
        statisticsDao.doUpdate(deletSql2);
    }

}
