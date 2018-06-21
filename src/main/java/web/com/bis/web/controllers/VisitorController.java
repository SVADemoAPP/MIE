package com.bis.web.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.LocationDao;
import com.bis.dao.StatisticsDao;
import com.bis.dao.VisitorDao;
import com.bis.model.TextModel;
import com.google.gson.Gson;
import com.jayway.jsonpath.internal.Utils;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: VisitorController
 * @Description: 大数据统计
 * @author gyr
 * @date 2017年8月9日 下午16:01:02
 *
 */
@Controller
@RequestMapping(value = "/visitor")
public class VisitorController {

    private static final Logger LOG = Logger.getLogger(VisitorController.class);

    @Autowired
    private VisitorDao dao;

    @Value("${mysql.db}")
    private String db;

    @Autowired
    private StatisticsDao statisticsDao;
    
    @Autowired
    private LocationDao locationDao;
    
    @Value("${sva.coefficientSwitch}")
    private int coefficientSwitch;   
    
    @Value("${sva.coefficient}")
    private double coefficient;  

    /**
     * 
     * @Title: testGson
     * @Description: Json格式解析
     * @param request
     * @return
     */
    @RequestMapping(value = "/testGson", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> testGson(HttpServletRequest request) {
        String txt = "{data:[{name:a,age:1},{name:b,age:2},{name:c,age:3}]}";
        TextModel textModel = new Gson().fromJson(txt, TextModel.class);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", textModel.getData());
        return modelMap;
    }

    /**
     * 
     * @Title: testTxt
     * @Description: 大数据文件解析
     * @param request
     * @return
     */
    @RequestMapping(value = "/testTxt", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> testTxt(HttpServletRequest request) {
        Date date = new Date();
        String time = Util.dateFormat(date, Params.YYYYMMDDHHMMSS);
        String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/visitors");
        File file = new File(filePath, "visitors.txt");
        if (file.exists()) {
            System.out.println("信息文件存在");
        }
        List<JSONObject> list = new ArrayList<>();
        String[] names = Params.VISITOR_COLUMNS;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                JSONObject jsonObject = new JSONObject();
                String[] values = tempString.replace("|", "_").split("_");
                for (int i = 0; i < names.length; i++) {
                    jsonObject.put(names[i], values[i + 1]);
                }
                jsonObject.put("time", time);
                list.add(jsonObject);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        int num = dao.saveData(list);
        LOG.debug("VisitorController~插入Visitor数据条数:" + num);

        String filePath1 = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/visitors");
        File file1 = new File(filePath1, "relevance.txt");
        if (file1.exists()) {
            System.out.println("关联文件存在");
        }
        List<JSONObject> list1 = new ArrayList<>();
        String[] names1 = { "userId", "imsi", "eNBid", "cellId" };
        BufferedReader reader1 = null;
        try {
            reader1 = new BufferedReader(new FileReader(file1));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader1.readLine()) != null) {
                JSONObject jsonObject1 = new JSONObject();
                String[] values1 = tempString.replace("|", "_").split("_");
                for (int i = 0; i < names1.length; i++) {
                    jsonObject1.put(names1[i], values1[i + 1]);
                }
                list1.add(jsonObject1);
            }
            reader1.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader1 != null) {
                try {
                    reader1.close();
                } catch (IOException e1) {
                }
            }
        }
        dao.saveData1(list1);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", "");
        return modelMap;
    }

    /**
     * 
     * @Title: getData
     * @Description: 获取性别统计，来源地统计，工作地统计
     * @param request
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/getData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request, @RequestParam("field") String field,
            @RequestParam("id") String id, @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "storeId", required = false) String storeId) {

        List<Map<String, Object>> genderList = new ArrayList<>();
        List<Map<String, Object>> abilityList = new ArrayList<>();
        List<Map<String, Object>> localAddressList = new ArrayList<>();
        List<Map<String, Object>> homeAddressList = new ArrayList<>();
        List<Map<String, Object>> workAddressList = new ArrayList<>();
        List<Map<String, Object>> ageList = new ArrayList<>();
//        String[] agePeriod = { "00后", "90后", "80后", "70后", "69前" };
//        String[] abilityPeriod = { "50以内", "50到100", "100到200", "200到500", "500以上" };
//        for (int i = 0; i < agePeriod.length; i++) {
//            Map<String, Object> newMap = new HashMap<>();
//            newMap.put("name", agePeriod[i]);
//            newMap.put("value", 0);
//            ageList.add(newMap);
//        }
//        for (int i = 0; i < abilityPeriod.length; i++) {
//            Map<String, Object> newMap = new HashMap<>();
//            newMap.put("name", abilityPeriod[i]);
//            newMap.put("value", 0);
//            abilityList.add(newMap);
//        }
        Calendar calendar = Calendar.getInstance();
        // 不传endTime则默认查3个月之内的
        if (StringUtils.isEmpty(endTime)) {
            endTime = Util.dateFormat(calendar.getTime(), Params.YYYYMMDD2);
            calendar.add(Calendar.MONTH, -2);
            startTime = Util.dateFormat(calendar.getTime(), Params.YYYYMMDD2);
        }
        List<String> tableNameList = Util.getPeriodMonthList(startTime, endTime);
//        calendar.setTime(new Date());
        for (String tableName : tableNameList) {
            // 表存在
            if (statisticsDao.isTableExist(tableName, this.db) > 0) {
                if ("shopId".equals(field)) {
                    // 从00后的岁数开始查
                    merge(ageList,
                            dao.getAgeByShopId(id, startTime, endTime, tableName));
                    merge(genderList, dao.getDataByShopId("gender", id, startTime, endTime, tableName));
                    merge(localAddressList, dao.getData2ByShopId("localAddress", id, startTime, endTime, tableName));
                    merge(homeAddressList, dao.getData3ByShopId("homeAddress", id, startTime, endTime, tableName));
                    merge(workAddressList, dao.getData3ByShopId("workAddress", id, startTime, endTime, tableName));
                    merge(abilityList,
                            dao.getAbilityByShopId( id, startTime, endTime, tableName));
                } else if ("categoryId".equals(field)) {
                    // 保证在规定商场，所以要加storeId限制
                    merge(ageList, dao.getAgeByCategoryId(id, startTime, endTime,
                            tableName, storeId));
                    merge(genderList, dao.getDataByCategoryId("gender", id, startTime, endTime, tableName, storeId));
                    merge(localAddressList,
                            dao.getData2ByCategoryId("localAddress", id, startTime, endTime, tableName, storeId));
                    merge(homeAddressList,
                            dao.getData3ByCategoryId("homeAddress", id, startTime, endTime, tableName, storeId));
                    merge(workAddressList,
                            dao.getData3ByCategoryId("workAddress", id, startTime, endTime, tableName, storeId));
                    merge(abilityList, dao.getAbilityByCategoryId( id, startTime, endTime,
                            tableName, storeId));
                } else if ("storeId".equals(field)) {
                    merge(ageList,
                            dao.getAgeByStoreId(id, startTime, endTime, tableName));
                    merge(genderList, dao.getDataByStoreId("gender", id, startTime, endTime, tableName));
                    merge(localAddressList, dao.getData2ByStoreId("localAddress", id, startTime, endTime, tableName));
                    merge(homeAddressList, dao.getData3ByStoreId("homeAddress", id, startTime, endTime, tableName));
                    merge(workAddressList, dao.getData3ByStoreId("workAddress", id, startTime, endTime, tableName));
                    merge(abilityList,
                            dao.getAbilityByStoreId(id, startTime, endTime, tableName));
                }
            }
        }

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("genderList", addRation(genderList));
        modelMap.put("localAddressList", sortTop10(addRation(localAddressList)));
        modelMap.put("homeAddressList", sortTop10(addRation(homeAddressList)));
        modelMap.put("workAddressList", sortTop10(addRation(workAddressList)));
        modelMap.put("ageList", addRation(ageList));
        modelMap.put("abilityList", addRation(abilityList));
        modelMap.put("status", Params.RETURN_CODE_200);
        return modelMap;
    }

    /**
     * 
     * @Title: getTodayTop
     * @Description: 获取商场当天楼层客流量排名和店铺客流量排名
     * @param request
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/getTodayTop", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getTodayTop(HttpServletRequest request, @RequestParam("storeId") String storeId) {
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int sum = locationDao.getMallTotal1(1,tableName, storeId);
        if (statisticsDao.isTableExist(tableName, this.db) > 0) {
            modelMap.put("mapData", addRation(dao.getMapVisitorCount(tableName, storeId)));
            modelMap.put("shopData",sortTop10(addRation( dao.getShopVisitorCount(tableName, storeId),sum)));
            modelMap.put("status", Params.RETURN_CODE_200);
        } else {
            modelMap.put("status", Params.RETURN_CODE_400);
        }
        return modelMap;
    }

    /**
     * 
     * @Title: sortTop10
     * @Description: 按value从大到小排序并取前10名
     * @param list
     * @return
     */
    private List<Map<String, Object>> sortTop10(List<Map<String, Object>> list) {
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                // TODO Auto-generated method stub
                String str1=String.valueOf(o1.get("value"));
                String str2=String.valueOf(o2.get("value"));
                long l1=Long.parseLong(str1);
                long l2=Long.parseLong(str2);
                if (l1 >l2) {
                    return -1;
                } else if (l1< l2) {
                    return 1;
                } else {
                    return 0;
                }
                
            }
        });
        if (list.size() > 10) {
            return list.subList(0, 10);
        } else {
            return list;
        }
    }

    /**
     * 
     * @Title: merge
     * @Description: 通过key合并value求和
     * @param list1
     * @param list2
     */
    private void merge(List<Map<String, Object>> allList, List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            String name = (String) map.get("name");
            boolean flag = true;
            for (Map<String, Object> allMap : allList) {
                if (name.equals(allMap.get("name"))) {
                    allMap.put("value", (long) map.get("value") + (int) allMap.get("value"));
                    flag = false;
                }
            }
            if (flag) {
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("name", name);
                newMap.put("value", Integer.parseInt(map.get("value").toString()));
                allList.add(newMap);
            }
        }
    }

    /**
     * 
     * @Title: addRation
     * @Description: 为数据添加比例再返回
     * @param mapList
     * @return
     */
    private List<Map<String, Object>> addRation(List<Map<String, Object>> mapList) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        long sum = 0;
        for (Map<String, Object> map : mapList) {
            if (map.get("value") instanceof Long) {
                sum += (long) map.get("value");
            } else if (map.get("value") instanceof Integer) {
                sum += (int) map.get("value");
            }
        }
        float totalRation = 100;
        if (sum == 0) {
            return returnList;
        }
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, Object> map = mapList.get(i);
            float temp = 0;
            if (i != mapList.size() - 1) {
                if (map.get("value") instanceof Long) {
                    temp = Util.getTwoPointNumber((float) ((long) map.get("value") * 100) / sum);
                } else if (map.get("value") instanceof Integer) {
                    temp = Util.getTwoPointNumber((float) ((int) map.get("value") * 100) / sum);
                    ;
                }
                totalRation -= temp;
            } else {
                temp = Util.getTwoPointNumber(totalRation);
            }
            Map<String, Object> newMap = new HashMap<>();
            newMap.put("name", map.get("name") + ":" + temp + "%");
            newMap.put("value", coefficientData(Integer.parseInt(map.get("value").toString())));
            returnList.add(newMap);
        }
        return returnList;
    }
    
    private List<Map<String, Object>> addRation(List<Map<String, Object>> mapList,int sum) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        float totalRation = 100;
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, Object> map = mapList.get(i);
            float temp = 0;
            if (i != mapList.size() - 1) {
                if (map.get("value") instanceof Long) {
                    temp = Util.getTwoPointNumber((float) ((long) map.get("value") * 100) / sum);
                } else if (map.get("value") instanceof Integer) {
                    temp = Util.getTwoPointNumber((float) ((int) map.get("value") * 100) / sum);
                    ;
                }
                totalRation -= temp;
            } else {
                temp = Util.getTwoPointNumber(totalRation);
            }
            Map<String, Object> newMap = new HashMap<>();
            newMap.put("name", map.get("name") + ":" + temp + "%");
            newMap.put("value", coefficientData(Integer.parseInt(map.get("value").toString())));
            returnList.add(newMap);
        }
        return returnList;
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
}
