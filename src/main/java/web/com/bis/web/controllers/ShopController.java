package com.bis.web.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.LocationDao;
import com.bis.dao.ShopDao;
import com.bis.dao.StatisticsDao;
import com.bis.model.MapMngModel;
import com.bis.model.NewUserModel;
import com.bis.model.ShopCostModel;
import com.bis.model.ShopModel;
import com.bis.model.StatisticsModel;
import com.bis.model.UserModel;

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

    private static final Logger LOG = Logger.getLogger(ShopController.class);

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

    /**
     * 
     * @Title: getTotal
     * @Description: 获取今天和昨天的相关数据
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/getTotal", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getTotal(@RequestParam("shopId") String shopId) {
        LOG.info("ShopController ~ getTotal");

        // 当前人数
        List<String> nowList = new ArrayList<String>();
        // 昨天人数
        List<String> yesList = new ArrayList<String>();
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        // 当前实时人数
        int count = 0;
        // 昨天实时人数
        int yesCount = 0;
        // 新增人数
        int newUser = 0;
        // 今日累计人数
        int allcount = 0;
        // 昨日累计人数
        int yesAllCount = 0;
        // 今天条数
        int allTiaoshu = 0;
        // 昨日条数
        int yesAllTiaoshu = 0;
        // 今日平均驻留时长
        String averageTime = null;
        // 昨日平均驻留时长
        String yesAverageTime = null;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String nows = Util.dateFormat(new Date(), Params.DD);
        String yesDay = Util.dateFormat(c.getTimeInMillis(), Params.YYYYMMDD);
        String yesDays = Util.dateFormat(c.getTimeInMillis(), Params.YYMMDD);
        String tableName = Params.LOCATION + nowDay;
        String yesTableName = Params.LOCATION + yesDay;
        long nowTimes = System.currentTimeMillis();
        long times = nowTimes - 4000;
        long yesTimes = c.getTimeInMillis();
        long yesTimesBegin = yesTimes - 4000;
        List<ShopModel> shopList = dao.getShopDataById(shopId);
        String nowMouths = Util.dateFormat(new Date(), Params.YYYYMM);
        String shopTableName = Params.SHOPLOCATION + nowMouths;
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        String riqi = Util.dateFormat(c.getTime(), Params.YYYYMM);
        String shopTableName1 = Params.SHOPLOCATION + riqi;
        ShopModel shopModel = new ShopModel();
        if (shopList.size() > 0) {
            shopModel = shopList.get(0);
            count = locationDao.getNowCount(times, tableName, shopModel);
            if (statisticsDao.isTableExist(yesTableName, this.db) < 1) {
                statisticsDao.createTable(yesTableName);
            }
            yesCount = locationDao.getYesCount(yesTimesBegin, yesTimes, yesTableName, shopModel);
            nowList = locationDao.getNowAllCount(tableName, shopModel);
            yesList = locationDao.getYesNowCount(yesTableName, shopModel, yesTimes);
            allcount = nowList.size();
            yesAllCount = yesList.size();
            allTiaoshu = locationDao.getAllTiaoshu(tableName, shopModel);
            yesAllTiaoshu = locationDao.getYesAllTiaoshu(yesTableName, shopModel, yesTimes);
            averageTime = Util.getMinute(allTiaoshu * 2000, allcount);
            yesAverageTime = Util.getMinute(yesAllTiaoshu * 2000, yesAllCount);
            nowList.removeAll(yesList);
            newUser = nowList.size();
        } else {
            return null;
        }
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        String sevenDay = Util.dateFormat(c.getTimeInMillis(), Params.YYYYMMDD);
        String sevenDays = Util.dateFormat(c.getTimeInMillis(), Params.YYMMDD);
        if (Integer.parseInt(nows) >= 7) {
            lists = dao.getShopWeekData(shopTableName, shopId, sevenDay, nowDay);
        } else {
            if (statisticsDao.isTableExist(shopTableName1, db) < 1) {
                // 动态创建表
                statisticsDao.createShopTable(shopTableName1);
            }
            List<Map<String, Object>> lists1 = dao.getShopWeekData1(shopTableName1, shopId, sevenDay);
            List<Map<String, Object>> lists2 = dao.getShopWeekData2(shopTableName, shopId, nowDay);
            lists.addAll(lists1);
            lists.addAll(lists2);
        }
        // 表格驻留时长于人数
        Map<String, Object> visitMap = new TreeMap<String, Object>();
        Map<String, Object> countMap = new TreeMap<String, Object>();
        Map<String, Object> nowMap = new TreeMap<String, Object>();
        Map<String, Object> newUserMap = new TreeMap<String, Object>();
        nowMap.put(yesDays, yesCount);
        visitMap.put(String.valueOf(Util.dateFormatStringtoLong(yesDays, Params.YYMMDD)), 0);
        countMap.put(yesDays, 0);
        newUserMap.put(yesDays, 0);

        for (int i = 2; i < 8; i++) {
            c.setTime(new Date());
            c.add(Calendar.DATE, -i);
            String newRiqi = Util.dateFormat(c.getTimeInMillis(), Params.YYMMDD);
            String yesDayss = Util.dateFormat(c.getTimeInMillis(), Params.YYYYMMDD);
            String yesTableNames = Params.LOCATION + yesDayss;
            long yesTimess = c.getTimeInMillis();
            long yesTimesBegins = yesTimes - 4000;
            if (statisticsDao.isTableExist(yesTableNames, this.db) < 1) {
                statisticsDao.createTable(yesTableNames);
            }
            int yesCounts = locationDao.getYesCount(yesTimesBegins, yesTimess, yesTableNames, shopModel);
            nowMap.put(newRiqi, yesCounts);
            visitMap.put(String.valueOf(Util.dateFormatStringtoLong(newRiqi, Params.YYMMDD)), 0);
            countMap.put(newRiqi, 0);
            newUserMap.put(newRiqi, 0);
        }
        for (int i = 0; i < lists.size(); i++) {
            String keyVal = lists.get(i).get("times").toString();
            String allTimes = lists.get(i).get("alltimes").toString();
            int allSize = Integer.parseInt(lists.get(i).get("allcount").toString());
            double vistiTime = Double.valueOf(allTimes) / allSize;

            visitMap.put(String.valueOf(Util.dateFormatStringtoLong(keyVal, Params.YYYYMMDD2)), vistiTime);
            countMap.put(keyVal, allSize);
        }
        List<NewUserModel> list = dao.getAllNewDataByShopId(shopId, sevenDays, yesDays);
        for (int i = 0; i < list.size(); i++) {
            NewUserModel model = list.get(i);
            // String userTime = model.getTime();
            int newUsers = model.getNewUser();
            newUserMap.put(yesDays, newUsers);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // visitMap.put(String.valueOf(Util.dateFormatStringtoLong(yesDays,
        // Params.YYMMDD)), yesAverageTime);
        nowMap.put(yesDays, yesAllCount);
        modelMap.put("nowData", nowMap);
        modelMap.put("newData", newUserMap);
        modelMap.put("allData", countMap);
        modelMap.put("timeData", visitMap);
        modelMap.put("nowPeople", count);
        modelMap.put("yesPeople", yesCount);
        modelMap.put("newPeople", newUser);
        modelMap.put("yesNewPeople", newUserMap.get(yesDays));
        modelMap.put("nowAllPeople", allcount);
        modelMap.put("yesAllPeople", yesAllCount);
        modelMap.put("nowTime", averageTime);
        modelMap.put("yesTime", yesAverageTime);
        modelMap.put("yesTime1", visitMap.get(String.valueOf(Util.dateFormatStringtoLong(yesDays, Params.YYMMDD))));
        modelMap.put("newAllPeople", allcount);
        modelMap.put(Params.RETURN_KEY_ERROR, Params.RETURN_CODE_200);
        modelMap.put(Params.RETURN_KEY_DATA, shopList);

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

}
