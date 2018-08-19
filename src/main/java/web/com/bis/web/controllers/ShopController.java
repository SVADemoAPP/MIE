package com.bis.web.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.common.Util;
import com.bis.common.area.Point;
import com.bis.common.conf.Params;
import com.bis.dao.LocationDao;
import com.bis.dao.ShopDao;
import com.bis.dao.StatisticsDao;
import com.bis.model.LocModel;
import com.bis.model.ShopCostModel;
import com.bis.model.ShopModel;
import com.bis.model.StatisticsModel;
import com.bis.model.UserModel;
import com.bis.model.WeekTotalModel;

import net.sf.json.JSONObject;

/**
 * @ClassName: ShopController
 * @Description: 店铺相关api入口
 * @author gyr
 * @date 2017年6月16日 上午10:57:15
 * 
 */
@Controller
@RequestMapping(value = "/shop")
public class ShopController {
    @Autowired
    private ShopDao dao;

    @Value("${mysql.db}")
    private String db;

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private LocationDao locationDao;
    
    @Autowired
    private ShopDao shopDao;

    private static final Logger LOG = Logger.getLogger(ShopController.class);
    
    @Value("${sva.coefficientSwitch}")
    private int coefficientSwitch;   
    
    @Value("${sva.coefficient}")
    private double coefficient;  
    
    @Value("${sva.coefficientSwitch2}")
    private int coefficientSwitch2;   
    
    @Value("${sva.coefficient2}")
    private double coefficient2;  
    
    @Value("${sva.durationOfLocation}")
    private int durationOfLocation;


    /**
     * 
     * @Title: getShopByUserName
     * @Description: 通过店铺名查询店铺信息
     * @param shopName
     * @return
     */
    @RequestMapping(value = "/getShopByShopName", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getShopByUserName(@RequestParam("shopName") String shopName) {
        LOG.info("ShopController ~ getShopByShopName 通过店铺名查询店铺信息");
        ShopModel shopModel = dao.doquery(shopName);
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("status", Params.RETURN_CODE_200);
        modelMap.put("data", shopModel);

        return modelMap;
    }

    /**
     * 
     * @Title: getShopNameByKey
     * @Description: 关键字模糊查询店铺名
     * @param key
     * @return
     */
    @RequestMapping(value = "/getShopNameByKey", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getShopNameByKey(@RequestParam("key") String key) {
        LOG.info("ShopController ~ getShopNameByKey 关键字模糊查询店铺名");

        List<String> shopNameList = dao.likequery(key);
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("status", Params.RETURN_CODE_200);
        modelMap.put("data", shopNameList);

        return modelMap;
    }
    
    /**
     * 
     * @Title: getShopDataByStore
     * @Description: 根据storeId和类别查询出对应商场下的店铺信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getShopDataByStore", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getShopDataByStore(@RequestParam("id")String id,
            @RequestParam(value="categoryId",required=false)String categoryId) {
        if("".equals(categoryId)){
            categoryId=null;
        }
        Map<String, Object> modelShop = new HashMap<String, Object>();
        List<ShopModel> resultList=new ArrayList<>();
        resultList = dao.queryShopByStore(id,categoryId);
        modelShop.put("error", null);
        modelShop.put("data", resultList);
        LOG.info("ShopController ~ getShopDataByStore 取得对应商场，类别下店铺信息,size:"+resultList.size());
        return modelShop;
    }
    
    

    /**
     * @param shopName
     *            店铺名称
     * @param type
     *            统计类型
     * @return
     */
    @RequestMapping(value = "/getLineData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getLineData(@RequestParam("shopName") String shopName, @RequestParam("type") int type) {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        List<StatisticsModel> reslutList = new ArrayList<StatisticsModel>();
        String startTime = null;
        String endTime = null;
        Map<String, Object> timeMap = new HashMap<>();
        Map<String, Object> allcountMap = new HashMap<>();
        // 获取表名
        String nowMouths = Util.dateFormat(new Date(), Params.YYYYMM);
        String shopTableName = Params.SHOPLOCATION + nowMouths;
        // 获取shopId
        int id = dao.getShopIdByshopName(shopName);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (id > 0) {
            if (type == 0) {
                cal.add(Calendar.MONTH, -1);
                startTime = Util.dateFormat(cal.getTime(), Params.YYYYMMdd0000);
                cal.setTime(new Date());
                cal.add(Calendar.HOUR, -24);
                endTime = Util.dateFormat(cal.getTime(), Params.YYYYMMdd0000);
                timeMap = Util.getPeriodList(startTime, endTime, 1);
                allcountMap = Util.getPeriodList(startTime, endTime, 1);
                for (int i = 0; i < 2; i++) {
                    list = new ArrayList<Map<String, Object>>();
                    if (i == 0) {
                        list = dao.getShopDataByShopId(shopTableName, id);

                    } else {
                        cal.setTime(new Date());
                        cal.add(Calendar.MONTH, -1);
                        String riqi = Util.dateFormat(cal.getTime(), Params.YYYYMM);
                        shopTableName = Params.SHOPLOCATION + riqi;
                        // 创建shop表
                        if (statisticsDao.isTableExist(shopTableName, db) >= 1) {
                            list = dao.getShopDataByShopId(shopTableName, id);
                        }
                    }
                    for (Map<String, Object> map : list) {
                        String keyVal = map.get("times").toString();
                        String allTimes = map.get("alltimes").toString();
                        int allSize = Integer.parseInt(map.get("allcount").toString());
                        double vistiTime = Double.valueOf(allTimes) / allSize;
                        timeMap.remove(keyVal);
                        allcountMap.remove(keyVal);
                        timeMap.put(keyVal, vistiTime);
                        allcountMap.put(keyVal, allSize);
                    }
                }
            } else {
                cal.setTime(new Date());
                cal.add(Calendar.YEAR, -1);
                startTime = Util.dateFormat(cal.getTime(), Params.YYYYMMdd0000);
                endTime = Util.dateFormat(new Date(), Params.YYYYMMdd0000);
                timeMap = Util.getPeriodList(startTime, endTime, 2);
                allcountMap = Util.getPeriodList(startTime, endTime, 2);
                for (int i = 0; i < 12; i++) {
                    list = new ArrayList<Map<String, Object>>();
                    if (i == 0) {
                        list = dao.getShopDataByShopId1(shopTableName, id);
                    } else {
                        cal.setTime(new Date());
                        cal.add(Calendar.MONTH, -1);
                        String riqi = Util.dateFormat(cal.getTime(), Params.YYYYMM);
                        shopTableName = Params.SHOPLOCATION + riqi;
                        // 创建shop表
                        if (statisticsDao.isTableExist(shopTableName, db) >= 1) {
                            list = dao.getShopDataByShopId1(shopTableName, id);
                        }
                    }
                    for (Map<String, Object> map : list) {
                        String keyVal = map.get("times").toString().substring(0, 7);
                        String allTimes = map.get("alltimes").toString();
                        int allSize = Integer.parseInt(map.get("allcount").toString());
                        double vistiTime = Double.valueOf(allTimes) / allSize;
                        timeMap.remove(keyVal);
                        allcountMap.remove(keyVal);
                        timeMap.put(keyVal, vistiTime);
                        allcountMap.put(keyVal, allSize);
                    }

                }
            }
        }
        Set<String> mapKey = timeMap.keySet();
        List<String> lisMap = new ArrayList<String>(mapKey);
        Collections.sort(lisMap);
        for (String i : lisMap) {
            StatisticsModel model = new StatisticsModel();
            model.setTime(i);
            model.setVisitTime(timeMap.get(i).toString());
            model.setUserCount(Integer.parseInt(allcountMap.get(i).toString()));
            reslutList.add(model);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("status", Params.RETURN_CODE_200);
        modelMap.put("data", reslutList);

        return modelMap;
    }

    /**
     * @param shopName
     * @param type
     * @return
     */
    @RequestMapping(value = "/getBarData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getBarData(@RequestParam("shopName") String shopName, @RequestParam("type") int type) {
        String nowMouths = Util.dateFormat(new Date(), Params.YYYYMM);
        String nowShopTableName = Params.SHOPLOCATION + nowMouths;
        int shopId = dao.getShopIdByshopName(shopName);
        List<ShopCostModel> list = new ArrayList<ShopCostModel>();
        if (shopId > 0) {
            if (type == 0) {
                list = dao.getShopCostTp10(shopId);
            } else {
                list = dao.getShopVisitimeTp10(nowShopTableName, shopId);
            }
        }
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("status", Params.RETURN_CODE_200);
        modelMap.put("data", list);

        return modelMap;
    }

    /**
     * @Title: getShopInfo
     * @Description: 获取所有店铺信息
     * @return
     */
    @RequestMapping(value = "/getShopInfo", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getShopInfo() {
        LOG.info("ShopController ~ getShopInfo 获取所有店铺信息");

        List<ShopModel> shopList = dao.queryAllShop();
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put(Params.RETURN_KEY_ERROR, Params.RETURN_CODE_200);
        modelMap.put(Params.RETURN_KEY_DATA, shopList);

        return modelMap;
    }

    @RequestMapping(value = "/api/getShopInfoByMapId", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getArea(@RequestParam("mapId") String mapId) {
        LOG.info("ShopController ~ getArea 根据mapId 获取店铺信息");
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<ShopModel> shops = dao.getShopInfoByMapId(mapId);
        modelMap.put(Params.RETURN_KEY_ERROR, null);
        modelMap.put(Params.RETURN_KEY_DATA, shops);
        return modelMap;
    }
    
    @RequestMapping(value = "/api/getPointsArrayById", method = { RequestMethod.POST })
    @ResponseBody
    public Object getPointsArrayById(@RequestParam("id") String id) {
        return dao.getPointsArrayById(id);
    }

    /**
     * @Title: getGenderAndProfession
     * @Description: 根据店铺名称查询性别比例以及职业比例
     * @param shopName
     * @return
     */
    @RequestMapping(value = "/getGenderAndProfession", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getGenderAndProfession(@RequestParam("shopName") String shopName) {
        LOG.info("ShopController ~ getGenderAndProfession 根据店铺名称查询性别比例以及职业比例");
        int shopId = dao.getShopIdByshopName(shopName);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<UserModel> listP = new ArrayList<UserModel>();
        int manCount = 0;
        int wumanCount = 0;
        if (shopId > 0) {
            list = dao.getGenderByShopId(shopId);
            listP = dao.getprofessionByShopId(shopId);
            for (int i = 0; i < list.size(); i++) {
                if (Integer.parseInt(list.get(i).get("gender").toString()) == 0) {
                    manCount = Integer.parseInt(list.get(i).get("allcount").toString());
                }
                if (Integer.parseInt(list.get(i).get("gender").toString()) == 1) {
                    wumanCount = Integer.parseInt(list.get(i).get("allcount").toString());
                }
            }
        }
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("status", Params.RETURN_CODE_200);
        modelMap.put("man", manCount);
        modelMap.put("wuman", wumanCount);
        modelMap.put("data", listP);

        return modelMap;
    }

    /**
     * @Title: saveShopData
     * @Description: 保存店铺信息
     * @param inputModel
     * @return
     */
    @RequestMapping(value = "/api/saveData", method = { RequestMethod.POST })
    public String saveShopData(ShopModel inputModel) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Date current = new Date();
        inputModel.setUpdateTime(current);
        // 保存
        if (StringUtils.isEmpty(inputModel.getId())) {
            // 保存
            try {
                inputModel.setCreateTime(new Date());
                dao.saveShopInfo(inputModel);
                LOG.info("ShopController ~ saveShopData 新增店铺");
            } catch (Exception e) {
                LOG.error("ShopController ~ saveShopData error:" + e);
                modelMap.put(Params.RETURN_KEY_ERROR, e);
            }
        } else {
            dao.updateShopInfo(inputModel);
            LOG.info("ShopController ~ saveShopData 修改店铺信息");
        }
        return "redirect:/home/shopMng";
    }

    /**
     * @Title: deleteShopData
     * @Description: 删除店铺信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/deleteData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> deleteShopData(@RequestParam("id") String id) {
        LOG.info("ShopController ~ deleteShopData 删除店铺信息");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int result = 0;
        try {
            result = dao.deleteShopById(id);
        } catch (Exception e) {
            LOG.error("ShopController ~ deleteShopData error:" + e);
            modelMap.put("error", e);
        }
        if (result == 1) {
            modelMap.put(Params.RETURN_KEY_ERROR, true);
        } else {
            modelMap.put(Params.RETURN_KEY_ERROR, false);
        }
        return modelMap;
    }

    /**
     * @Title: checkName
     * @Description: 判断指定的店铺名称是否已存在（同一商场）
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/api/checkName", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> checkName(@RequestParam("id") String id, @RequestParam("name") String name,
            @RequestParam("storeId") String storeId) {
        LOG.info("ShopController ~ checkName 店铺名查重");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int i = dao.checkByName(name, id, storeId);
        if (i > 0) {
            modelMap.put(Params.RETURN_KEY_ERROR, true);
        } else {
            modelMap.put(Params.RETURN_KEY_ERROR, false);
        }
        return modelMap;
    }

    /**
     * 
     * @Title: getShopDataByMapId
     * @Description: 根据mapId查询店铺信息
     * @param mapId
     * @return
     */
    @RequestMapping(value = "/getShopDataByMapId", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getShopDataByMapId(@RequestParam("mapId") String mapId) {
        LOG.info("ShopController ~ getShopDataByMapId 根据mapId获取店铺信息 ");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopModel> list = dao.getShopInfoByMapId(mapId);
        if (list.size() > 0) {
            modelMap.put("data", list);
            modelMap.put(Params.RETURN_KEY_ERROR, false);
        } else {
            modelMap.put("data", list);
            modelMap.put(Params.RETURN_KEY_ERROR, true);
        }
        return modelMap;
    }

    /**
     * @Title: getAllShopData
     * @Description: 获取所有的店铺信息
     * @return
     */
    @RequestMapping(value = "/getAllShopData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getAllShopData() {
        LOG.info("ShopController ~ getAllShopData 获取所有店铺信息 ");

        List<ShopModel> shopList = dao.getAllShop();
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put(Params.RETURN_KEY_ERROR, Params.RETURN_CODE_200);
        modelMap.put(Params.RETURN_KEY_DATA, shopList);

        return modelMap;
    }

    
    @RequestMapping(value = "/getNewTotal", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getNewTotal(@RequestParam("shopId") String shopId) {
        int nowUserCount = 0;
        double nowAverageTime = 0;
        int yesUserCount = 0;
        double yesAverageTime = 0;
        int allWeekCount = 0;
        float allWeekTime = 0;
        JSONObject weekUsercount = new JSONObject();
        JSONObject weekDelaytime = new JSONObject();
        Calendar calendar = Calendar.getInstance();
        long nowTimes = calendar.getTimeInMillis();
        long times = nowTimes - 600*1000;
        String endTime = Util.dateFormat(calendar.getTime(), Params.YYYYMMdd0000);
        String endTime1 = Util.dateFormat(calendar.getTime(), Params.YYYYMMddHH00);
        calendar.add(Calendar.DATE, -1);
        long yesEndTimel =calendar.getTimeInMillis();
        long yesStartTimel = yesEndTimel - 600*1000;
        String yesTbaleName =Params.LOCATION+ Util.dateFormat(calendar.getTime(), Params.YYYYMMDD);
        String yesStartTime = Util.dateFormat(calendar.getTime(), Params.YYYYMMdd0000);
        String yesEndTime = Util.dateFormat(calendar.getTime(), Params.YYYYMMddHH00);
        
        calendar.add(Calendar.DATE, -6);
        String bigenTime = Util.dateFormat(calendar.getTime(), Params.YYYYMMdd0000);
        String[] weeks = Util.getLastNumDays(7, Params.YYMMDD);
        List<WeekTotalModel> list = locationDao.getWeekDataByShopId(shopId, bigenTime, endTime);
        List<WeekTotalModel> list1 = locationDao.getWeekDataByShopId(shopId, endTime, endTime1);
        List<WeekTotalModel> list2 = locationDao.getWeekDataByShopId(shopId, yesStartTime, yesEndTime);
        for (int i = 0; i < weeks.length; i++) {
            weekUsercount.put(weeks[i], 0);
            weekDelaytime.put(String.valueOf(Util.dateFormatStringtoLong(weeks[i], Params.YYMMDD)), 0);
        }
        WeekTotalModel model = null;
        for (int i = 0; i < list.size(); i++) {
            model = list.get(i);
            int allCount = coefficientData2(model.getAllCount());
            double averageTime = model.getAverageTime();
            allWeekCount += allCount;
            allWeekTime +=averageTime;
            model = list.get(i);
            String myTime = model.getMyTime().replace("-", "/");
            weekUsercount.put(myTime, allCount);
            weekDelaytime.put(String.valueOf(Util.dateFormatStringtoLong(myTime, Params.YYMMDD)), averageTime);  
        }
        if (list1.size()>0) {
            nowUserCount = list1.get(0).getAllCount();
            nowAverageTime = list1.get(0).getAverageTime();
        }
        if (list2.size()>0) {
            yesUserCount = list2.get(0).getAllCount();
            yesAverageTime = list2.get(0).getAverageTime();
        }
        List<ShopModel> listShopModel = shopDao.getShopDataById(shopId);
        ShopModel shopModel = listShopModel.get(0);
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        Point tempPoint=new Point(0D, 0D);
        String pointsArray=shopModel.getPointsArray();
        Set<String> userIdSet=new HashSet<String>();
        List<LocModel> locModelsToday=locationDao.getNowCountsNew(times,nowTimes, tableName, shopModel);
        for(LocModel loc:locModelsToday){
            tempPoint.setX(loc.getX()/10.0D);
            tempPoint.setY(loc.getY()/10.0D);
            if(Util.isInArea(tempPoint, pointsArray)){
                userIdSet.add(loc.getUserId());
            }
        }
        int count =userIdSet.size();
        userIdSet.clear();
        List<LocModel> locModelsYes=locationDao.getNowCountsNew(yesStartTimel,yesEndTimel,yesTbaleName, shopModel);
        for(LocModel loc:locModelsYes){
            tempPoint.setX(loc.getX()/10.0D);
            tempPoint.setY(loc.getY()/10.0D);
            if(Util.isInArea(tempPoint, pointsArray)){
                userIdSet.add(loc.getUserId());
            }
        }
        int yesCount=userIdSet.size();
        
        
//        int count = locationDao.getNowCountsNew(times,nowTimes, tableName, shopModel);//辜义睿getNowCounts
//        int yesCount = locationDao.getNowCountsNew(yesStartTimel,yesEndTimel,yesTbaleName, shopModel);//辜义睿getNowCounts
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("allData", weekUsercount);
        modelMap.put("timeData", weekDelaytime);
        modelMap.put("nowPeople",coefficientData(count));
        modelMap.put("yesPeople",coefficientData(yesCount));
//        modelMap.put("yesPeople", coefficientData(yesCount));
        
        modelMap.put("yesAllPeople", coefficientData2(yesUserCount));
        modelMap.put("yesTime", yesAverageTime);        
        modelMap.put("nowAllPeople", coefficientData2(nowUserCount));
//        modelMap.put("yesAllPeople", weekUsercount);
        modelMap.put("nowTime", nowAverageTime);
//        modelMap.put("yesTime", yesAverageTime);
//        modelMap.put("yesTime1", visitMap.get(String.valueOf(Util.dateFormatStringtoLong(yesDays, Params.YYMMDD))));
        modelMap.put(Params.RETURN_KEY_ERROR, Params.RETURN_CODE_200);
        modelMap.put("allWeekCount", allWeekCount);
        modelMap.put("allWeekAvgDelay", allWeekTime/7);
        return modelMap;
    }

    /**
     * 
     * @Title: getShopTrend
     * @Description: 根据时刻或日期天查询店铺关联动向
     * @param type
     *            0为时刻查找，1为日期天查找
     * @param shopId
     * @param sign
     *            整点数或日期天数
     * @param time
     * @return
     */
    @RequestMapping(value = "/getShopTrend", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getShopTrend(@RequestParam("type") int type, @RequestParam("shopId") int shopId,
            @RequestParam("sign") int sign, @RequestParam("time") String time) {

        List<Map<String, Object>> resultList = new ArrayList<>();
        Integer visitorCount = null;
        if (type == 0) {
            LOG.info("ShopController ~ getShopTrend 根据时刻查询店铺关联动向");
            resultList = dao.getShopTrendByHour(shopId, sign, time);
            visitorCount = dao.getShopTrendByHourOther(shopId, sign, time);
        } else if (type == 1) {
            LOG.info("ShopController ~ getShopTrend 根据日期天查询店铺关联动向");
            resultList = dao.getShopTrendByDay(shopId, sign, time);
            visitorCount = dao.getShopTrendByDayOther(shopId, sign, time);
        }
        if (visitorCount != null) {
            Map<String, Object> otherMap = new HashMap<>();
            otherMap.put("name", "other");
            otherMap.put("value", visitorCount);
            resultList.add(otherMap);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("error", null);
        modelMap.put("data", resultList);
        return modelMap;
    }

    private int coefficientData(int data){
        if(coefficientSwitch==0)
        {
            return data;
        }else
        {
            return (int) (data*coefficient);
        }
        
    }
    
    private int coefficientData2(int data){
        if(coefficientSwitch2==0)
        {
            return data;
        }else
        {
            return (int) (data*coefficient2);
        }
        
    }
}
