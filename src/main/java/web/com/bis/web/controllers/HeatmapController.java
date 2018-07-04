package com.bis.web.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.LocationDao;
import com.bis.dao.MapMngDao;
import com.bis.dao.RateDao;
import com.bis.dao.ShopDao;
import com.bis.model.LocationModel;
import com.bis.model.MapMngModel;
import com.bis.model.ShopModel;
import com.bis.web.auth.Rates;

@Controller
@RequestMapping(value = "/heatmap")
public class HeatmapController {
    @Autowired
    private MapMngDao mapMngDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private ShopDao shopDao;
    
    @Autowired
    private Rates rates;
    
    @Autowired
    private RateDao rateDao;
    
    @Value("${sva.durationOfLocation}")
    private int durationOfLocation;
    
    @Value("${sva.coefficientSwitch}")
    private int coefficientSwitch;   
    
    @Value("${sva.coefficient}")
    private double coefficient;  
    
    private static final Logger LOG = Logger.getLogger(HeatmapController.class);

    @RequestMapping(value = "/api/getMapInfoByPosition", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getHeatmapInfoData(@RequestParam("mapId") String mapId) {

        List<MapMngModel> resultList = mapMngDao.getMapDataByMapId(mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>(8);
        modelMap.put("data", resultList);
        return modelMap;
    }

    @RequestMapping(value = "/api/getData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getHeatmapData(@RequestParam("mapId") String mapId) {
        LOG.info("HeatmapController ~ getHeatmapData 楼层实时热力图");
        // 查询截至时间
        long time = System.currentTimeMillis() - durationOfLocation * 1000;
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        List<LocationModel> resultList = locationDao.queryHeatmap(mapId, time, tableName);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("data", resultList);

        return modelMap;
    }

    /**
     * @Title: getShopData
     * @Description:获取店铺热力图
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/api/getShopData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getShopData(@RequestParam("shopId") String shopId) {
        LOG.info("HeatmapController ~ getShopData 店铺实时热力图");
        List<ShopModel> list = shopDao.getShopDataById(shopId);
        ShopModel model = new ShopModel();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<LocationModel> resultList = new ArrayList<LocationModel>();
        if (list.size() > 0) {
            model = list.get(0);
        } else {
            modelMap.put("data", resultList);
            return modelMap;
        }
        // 查询截至时间
        long time = System.currentTimeMillis() - durationOfLocation * 1000;
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        resultList = locationDao.getShopHeatMapByShopId(time, tableName, model);
        modelMap.put("data", resultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/getPeriodShopData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getPeriodShopData(@RequestParam("type") int type, @RequestParam("shopId") String shopId,
            @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        LOG.info("HeatmapController ~ getPeriodShopData 店铺时间段热力图");
        List<ShopModel> list = shopDao.getShopDataById(shopId);
        ShopModel model = new ShopModel();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<LocationModel> resultList = new ArrayList<LocationModel>();
        if (list.size() > 0) {
            model = list.get(0);
        } else {
            modelMap.put("data", resultList);
            return modelMap;
        }
        // 查询截至时间
        long startTimestamp = 0;
        long endTimestamp = 0;
        if (type == 1) {
            startTimestamp = Long.valueOf(startTime);
            endTimestamp = Long.valueOf(endTime);
        } else {
            startTimestamp = Util.dateFormatStringtoLong(startTime, Params.YYYYMMDDHHMMSS);
            endTimestamp = Util.dateFormatStringtoLong(endTime, Params.YYYYMMDDHHMMSS);
        }
        // 表名
        String nowDay = Util.dateFormat(startTimestamp, Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        resultList = locationDao.getMapPeriodHeatMapById(startTimestamp, endTimestamp, tableName, model);
        modelMap.put("data", resultList);

        return modelMap;
    }

    public static String getMinute(long time, int size) {
        if (size == 0) {
            return "0";
        } else {

            float b = (time / 1000) / 60;
            float averageTime = b / size;
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(averageTime);
        }

    }

    @RequestMapping(value = "/api/getShopHeatMap", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getShopHeatMap(@RequestParam("shopId") String shopId) {
        LOG.info("HeatmapController ~ getShopHeatMap 店铺前10分钟客流量");
        List<ShopModel> list = shopDao.getShopDataById(shopId);
        ShopModel model = new ShopModel();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Map<Long, Long> timeMap = new TreeMap<Long, Long>();
        List<LocationModel> resultList = new ArrayList<LocationModel>();
        if (list.size() > 0) {
            model = list.get(0);
        } else {
            modelMap.put("data", resultList);
            return modelMap;
        }
     // 查询截至时间
        long nowTime = System.currentTimeMillis();
        nowTime = nowTime / 4000 * 4000; // 取4000的倍数
        // timeMap.put(nowTime, (long) 0);
        long startTime = nowTime -durationOfLocation*1000;
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        resultList = locationDao.getTenminitShopData(startTime, nowTime, tableName, model);
        int startIndex=0; //开始遍历resultList的位置
        Set<String> userIdSet=new HashSet<>();
        LocationModel eachModel;
        for (int i = 0; i < 900; i++) {
//            timeMap.put(nowTime - i * 4000, (long) 0);
            long thisTime=nowTime-(899-i)*4000;
          //是否第一次进入时间段
            boolean firstBetween=true; 
            for(int j=startIndex;j<resultList.size();j++){
                eachModel=resultList.get(j);
                //超出时间段
                if(eachModel.getTimestamp()>thisTime){
                    break;
                }
                //在时间段之内
                if(eachModel.getTimestamp()>thisTime-durationOfLocation*1000){
                    if(firstBetween){
                        firstBetween=false;
                        startIndex=j; //下次开始遍历的位置，从此索引开始
                    }
                    userIdSet.add(eachModel.getUserID());
                }
            }
            //遍历完过后存数目
            timeMap.put(thisTime,  (long)coefficientData(userIdSet.size()));
            userIdSet.clear();
        }
        modelMap.put("data", timeMap);

        return modelMap;
    }

    @RequestMapping(value = "/api/getMallHeatMap", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMallHeatMap(@RequestParam("storeId") String storeId) {
        LOG.info("HeatmapController ~ getMallHeatMap 商场前10分钟客流量");
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Map<Long, Long> timeMap = new TreeMap<Long, Long>();
        List<LocationModel> resultList = new ArrayList<LocationModel>();
        // 查询截至时间
        long nowTime = System.currentTimeMillis();
        nowTime = nowTime / 4000 * 4000; // 取4000的倍数
        // timeMap.put(nowTime, (long) 0);
        long startTime = nowTime -durationOfLocation*1000;
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        resultList = locationDao.getTenminitMallData(startTime, nowTime, tableName, storeId);
        int startIndex=0; //开始遍历resultList的位置
        Set<String> userIdSet=new HashSet<>();
        LocationModel eachModel;
        for (int i = 0; i < 900; i++) {
//            timeMap.put(nowTime - i * 4000, (long) 0);
            long thisTime=nowTime-(899-i)*4000;
          //是否第一次进入时间段
            boolean firstBetween=true; 
            for(int j=startIndex;j<resultList.size();j++){
                eachModel=resultList.get(j);
                //超出时间段
                if(eachModel.getTimestamp()>thisTime){
                    break;
                }
                //在时间段之内
                if(eachModel.getTimestamp()>thisTime-durationOfLocation*1000){
                    if(firstBetween){
                        firstBetween=false;
                        startIndex=j; //下次开始遍历的位置，从此索引开始
                    }
                    userIdSet.add(eachModel.getUserID());
                }
            }
            //遍历完过后存数目
            timeMap.put(thisTime,  (long)coefficientData(userIdSet.size()));
            userIdSet.clear();
        }
//        // 分时间段的userId集合
//        Set<String> existUserIdSet = new HashSet<>();
//        for (int i = 0; i < resultList.size(); i++) {
//            LocationModel localModel = resultList.get(i);
//            if (localModel.getTimestamp() <= startTime + 60000) {
//                // 存放满足时间戳的userId
//                existUserIdSet.add(localModel.getUserID());
//            } else {
//                // 150个刻度点用户量的存放，然后清空userId集合在下个刻度点重新计数
//                startTime += 60000;
//                timeMap.put(startTime, (long) existUserIdSet.size());
//                existUserIdSet.clear();
//                // 如果下一个时间戳中没有位置信息，则startTime往后加，直到进入小于等于startTime的区间
//                while (localModel.getTimestamp() > startTime + 60000) {
//                    startTime += 60000;
//                }
//                // 添加userId到下一个存在的数据中去
//                existUserIdSet.add(localModel.getUserID());
//            }
//        }
//        // 最后剩下的就是当前4秒的，再put一次
//        timeMap.put(startTime + 60000, (long) existUserIdSet.size());
//        modelMap.put("timestamp", startTime);
        modelMap.put("data", timeMap);

        return modelMap;
    }
    
    @RequestMapping(value = "/api/getFloorHeatMap", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getFloorHeatMap(@RequestParam("mapId") String mapId) {
        LOG.info("HeatmapController ~ getMallHeatMap 商场前10分钟客流量");
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Map<Long, Long> timeMap = new TreeMap<Long, Long>();
        List<LocationModel> resultList = new ArrayList<LocationModel>();
        // 查询截至时间
        long nowTime = System.currentTimeMillis();
        nowTime = nowTime / 4000 * 4000; // 取4000的倍数
        // timeMap.put(nowTime, (long) 0);
        long startTime = nowTime-durationOfLocation*1000;
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        resultList = locationDao.getTenminitFloorData(startTime, nowTime, tableName, mapId);
        int startIndex=0; //开始遍历resultList的位置
        Set<String> userIdSet=new HashSet<>();
        LocationModel eachModel;
        for (int i = 0; i < 900; i++) {
//            timeMap.put(nowTime - i * 4000, (long) 0);
            long thisTime=nowTime-(899-i)*4000;
          //是否第一次进入时间段
            boolean firstBetween=true; 
            for(int j=startIndex;j<resultList.size();j++){
                eachModel=resultList.get(j);
                //超出时间段
                if(eachModel.getTimestamp()>thisTime){
                    break;
                }
                //在时间段之内
                if(eachModel.getTimestamp()>thisTime-durationOfLocation*1000){
                    if(firstBetween){
                        firstBetween=false;
                        startIndex=j; //下次开始遍历的位置，从此索引开始
                    }
                    userIdSet.add(eachModel.getUserID());
                }
            }
            //遍历完过后存数目
            timeMap.put(thisTime, (long)coefficientData(userIdSet.size()));
            userIdSet.clear();
        }
        modelMap.put("data", timeMap);
        return modelMap;
    }    


    @RequestMapping(value = "/api/getShopChartsData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getShopChartsData(@RequestParam("shopId") String shopId,
            @RequestParam("endTime") long endTime) {
        List<ShopModel> list = shopDao.getShopDataById(shopId);
        ShopModel model = new ShopModel();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int resultList = 0;
        if (list.size() > 0) {
            model = list.get(0);
        } else {
            modelMap.put("data", resultList);
            return modelMap;
        }
        // 查询截至时间
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        long startTime = endTime - durationOfLocation*1000 - 28800000;
        long endTimes = endTime - 28800000;
        resultList = locationDao.getNowCounts(startTime, endTimes, tableName, model);
        modelMap.put("data", coefficientData(resultList));

        return modelMap;
    }

    @RequestMapping(value = "/api/getMapHeatMap", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMapHeatMap(@RequestParam("mapId") String mapId) {
        LOG.info("HeatmapController ~ getMapHeatMap 楼层实时热力图");
        List<LocationModel> resultList = new ArrayList<>();
        // 查询截至时间
        long nowTime = System.currentTimeMillis();
        long time = nowTime - durationOfLocation*1000;
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        resultList = locationDao.getMapHeatMapById(time, tableName, mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("data", resultList);

        return modelMap;
    }
    
    
    @RequestMapping(value = "/api/getStoreHeatMap", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getStoreHeatMap(@RequestParam("storeId") String storeId) {
        // 查询截至时间
        long nowTime = System.currentTimeMillis();
        long time = nowTime - durationOfLocation*1000;
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        long count = locationDao.getMallTotal1(time, tableName, storeId);
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("data", coefficientData((int)count));

        return modelMap;
    }    

    @RequestMapping(value = "/api/getPeriodMapHeatMap", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getPeriodMapHeatMap(@RequestParam("mapId") String mapId,
            @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        LOG.info("HeatmapController ~ getPeriodMapHeatMap 楼层时间段热力图");
        List<LocationModel> resultList = new ArrayList<>();
        // // 查询截至时间
        // long nowTime = System.currentTimeMillis();
        // long time = nowTime - 4000;
        long startLong = Util.dateFormatStringtoLong(startTime, Params.YYYYMMDDHHMMSS) - 4000;
        long endLong = Util.dateFormatStringtoLong(endTime, Params.YYYYMMDDHHMMSS);
        // 表名
        String nowDay = Util.dateFormat(endLong, Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        resultList = locationDao.getPeriodMapHeatMap(startLong, endLong, tableName, mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("data", resultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/getFiveMuniteMapHeatMap", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getFiveMuniteMapHeatMap(@RequestParam("mapId") String mapId) {
        LOG.info("HeatmapController ~ getFiveMuniteMapHeatMap 楼层前5分钟热力图");
        List<LocationModel> resultList = new ArrayList<>();
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        resultList = locationDao.getMapHeatMapByIds(tableName, mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("data", resultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/getStoreMomentCount", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getStoreMomentCount(@RequestParam("storeId") String storeId,
            @RequestParam("endTime") long endTime) {
        LOG.info("HeatmapController ~ getStoreMomentCount 商场指定时间客流量");
        long startTime = endTime - durationOfLocation*1000;
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        long userCount = locationDao.getStoreMomentCount(startTime, endTime, tableName, storeId);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", coefficientData((int)userCount));
        return modelMap;
    }
    
    @RequestMapping(value = "/api/getFloorMomentCount", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getFloorMomentCount(@RequestParam("mapId") String mapId,
            @RequestParam("endTime") long endTime) {
        LOG.info("HeatmapController ~ getStoreMomentCount 商场指定时间客流量");
        long startTime = endTime - durationOfLocation*1000;
        // 表名
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        long userCount = locationDao.getFloorMomentCount(startTime, endTime, tableName, mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", coefficientData((int)userCount));
        return modelMap;
    }    
    
    @RequestMapping(value = "/api/getRates", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String,Object> getRates(@RequestParam("id") int id){
        Map<String,Object> map = new HashMap<String, Object>();
        List<String> listDate = new ArrayList<>();
         List<String> listEnterRate = new ArrayList<>();
        List<String> listOverRate = new ArrayList<>();
         List<String> listDeepRate = new ArrayList<>();
//      Calendar calendar = Calendar.getInstance();
        // 获得前一天的日期
//      calendar.add(Calendar.DATE,-i);
//      calendar.getTimeInMillis()
//           for(int i=1;i<8;i++){
//               listDate.add("");
//               listEnterRate.add("");
//               listOverRate.add("");
//               listDeepRate.add("");
//           }
//       Thread[] threadList=new Thread[7];
//       ExecutorService executorService = Executors.newFixedThreadPool(7);
//       final CountDownLatch end=new CountDownLatch(7);
       ShopModel shopModel=rateDao.getShopInfoById(id);
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
//            final int j=i;
//            threadList[i]=new Thread(new Runnable() {
//                
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.add(Calendar.DATE,-j-1);
//                    listDate.set(j,Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD2));
//                    listEnterRate.set(j,String.valueOf(rates.getEnter(shopModel,calendar)));
//                    listOverRate.set(j,String.valueOf(rates.getOverflow1(shopModel,calendar)));
//                    listDeepRate.set(j,String.valueOf(rates.getDeep(shopModel, calendar))); 
//                }
//            });
//           threadList[i].start();
//           executorService.submit(new Runnable() {
//               
//               @Override
//               public void run() {
                   // TODO Auto-generated method stub
//                   Calendar calendar = Calendar.getInstance();
                   calendar.add(Calendar.DATE,-1);
                   listDate.add(Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD2));
                   listEnterRate.add(String.valueOf(rates.getEnter(shopModel,calendar)));
                   listOverRate.add(String.valueOf(rates.getOverflow1(shopModel,calendar)));
                   listDeepRate.add(String.valueOf(rates.getDeep(shopModel, calendar))); 
//                   end.countDown();
//               }
//           });
        }
//        try {
//            end.await();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        executorService.shutdown();
        map.put("date", listDate);
        map.put("eRate", listEnterRate);
        map.put("oRate", listOverRate);
        map.put("dRate", listDeepRate);
        return map;
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
