package com.bis.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.CpuDao;
import com.bis.dao.LocationDao;
import com.bis.dao.MapMngDao;
import com.bis.dao.MarketDao;
import com.bis.dao.MarketOverviewDao;
import com.bis.dao.RateDao;
import com.bis.dao.StatisticsDao;
import com.bis.model.MapMngModel;
import com.bis.model.MarketModel;
import com.bis.model.MarketMonthDataModel;
import com.bis.model.MarketShopSquareModel;
import com.bis.model.MarketShopTopModel;
import com.bis.model.NewUserModel;
import com.bis.model.ShopCostModel;
import com.bis.model.ShopModel;
import com.bis.model.StatisticsModel;
import com.bis.model.StringIntModel;
import com.bis.web.auth.AuthPassport;
import com.bis.web.auth.Rates;
import com.bis.web.auth.SystemInfo;
import com.bis.web.auth.WeatherInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName: marketController
 * @Description: 商场api入口
 * @author fwx219758
 * @date 2017年6月16日 上午10:57:15
 * 
 */
@Controller
@RequestMapping(value = "/market")
public class MarketController {
 
    @Autowired
    private MarketOverviewDao dao;
    
    @Autowired
	private CpuDao daoCpu; 

    @Value("${mysql.db}")
    private String db;
    @Autowired
    private StatisticsDao statisticsDao;
    
    @Autowired
   	private RateDao rateDao;
    
    @Autowired
    private Rates rates;
    
    /**
     * @Fields daoMarket : 商场信息dao
     */
    @Autowired
    private MarketDao daoMarket;
    
    @Autowired
    private MapMngDao mapMngDao;
    
    @Autowired
    private LocationDao locationDao;
    private static final Logger LOG = Logger.getLogger(MarketController.class);

    /**
     * 
     * @Title: getMarketInfo
     * @Description: 呈现商场信息和系统信息
     * @param shopName
     * @return
     */
    @AuthPassport
    @RequestMapping(value = "/getMarketInfo", method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView getMarketInfo() {
        LOG.info("marketController:getMarketInfo");
        ModelAndView modelAndView = new ModelAndView();
//        SystemInfo si = new SystemInfo();
//        int r = daoCpu.selectCount();
//    	long b = System.currentTimeMillis();
//    	if(r==0){
//    		int d = si.getCpuRatioForWindows();
//    		modelAndView.addObject("cpu",d);
//    		daoCpu.saveCpu(d,b);
//    	}else {
//    		long a = daoCpu.selectTimeById()+30000;
//    		if(b<=a){
//        		int c = daoCpu.selectParameter();
//        		modelAndView.addObject("cpu",c);
//        	}else{
//        		int d = si.getCpuRatioForWindows();
//        		modelAndView.addObject("cpu",d);
//        		daoCpu.saveCpu(d,b);
//        	}
//    	}
//    	if(r>2){
//    		long e = daoCpu.selectMinId();
//    		daoCpu.deleteCount(e);
//    	}
//        modelAndView.addObject("memory", si.getEMS());
//        modelAndView.addObject("diskspace", si.getDisk());
        modelAndView.addObject("cpu", 98);
        modelAndView.addObject("memory", 78);
        modelAndView.addObject("diskspace", 30);
        modelAndView.setViewName("/market2");
        return modelAndView;
    }

    @RequestMapping(value = "/getChartData", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject getChartData(@RequestParam("types") String types) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("staticVistor", GetDailyVisitorCntDataForAMonth());
        modelMap.put("staticDelay", GetDailyDelayDataForAMonth());
        List<String> param = Arrays.asList(types.split(","));
        modelMap.put("topNByVistor", getMarketTop10VisitorCntByTypeAndDayPeriod(param, 30));
        modelMap.put("topNByDelay", getMarketTop10SalesByTypeAndDayPeriod(param, 30));

        return JSONObject.fromObject(modelMap);
    }

    @RequestMapping(value = "/getTopNShopInfo", method = { RequestMethod.POST })
    @ResponseBody
    public String getTopNShopInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCostModel> shopCostList = new ArrayList<ShopCostModel>();
        for (int i = 0; i < 23; i++) {
            ShopCostModel shopCost = new ShopCostModel();
            shopCost.setUserName("user" + i);
            shopCost.setCreateTime("2017-06-25");
            shopCost.setSpending("235" + i);
            shopCost.setVisitTime("2" + i);
            shopCostList.add(shopCost);
        }
        modelMap.put("shopCostList", shopCostList);
        return JSONArray.fromObject(shopCostList).toString();
    }

    @RequestMapping(value = "/getShopInfo", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getShopInfo(@RequestParam("types") String types) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("staticVistor", GetDailyVisitorCntDataForAMonth());
        modelMap.put("staticDelay", GetDailyDelayDataForAMonth());
        List<String> param = Arrays.asList(types.split(","));
        modelMap.put("topNByDelay", getMarketTop10VisitorCntByTypeAndDayPeriod(param, 30));
        modelMap.put("topNByDelay", getMarketTop10SalesByTypeAndDayPeriod(param, 30));

        return modelMap;
    }

    // 近一个月每天的驻留时长排名 日期+驻留时长
    public List<MarketMonthDataModel> GetDailyDelayDataForAMonth() {
        List<MarketMonthDataModel> delayDataMonth = dao.getMarketMonthdailyDelay();
        AddValueZeroDateDayForLast30Days(delayDataMonth);
        Collections.sort(delayDataMonth);
        return delayDataMonth;
    }

    // 近一个月每天的访客数排名 日期+访客人数
    public List<MarketMonthDataModel> GetDailyVisitorCntDataForAMonth() {
        List<MarketMonthDataModel> CntDataMonth = dao.getMarketMonthDailyVisitorCnt();
        AddValueZeroDateDayForLast30Days(CntDataMonth);
        Collections.sort(CntDataMonth);
        return CntDataMonth;
    }

    // 获取商场销售额排名前十，不足10个只返回有数据的店铺
    // 输入:商铺类别列表List typeSeq，查询时间范围，从今天开始计算倒推dayBeforeToday天
    // 输出:排序后的店铺名+销售额列表
    public List<MarketShopTopModel> getMarketTop10SalesByTypeAndDayPeriod(List<String> typeSeq, int dayBeforeToday) {
        List<MarketShopTopModel> topSpend = dao.getMarketSalesInfoByType(typeSeq, dayBeforeToday);
        List<MarketShopTopModel> topSpendRet = new ArrayList<MarketShopTopModel>();
        // 只取前10
        Iterator<MarketShopTopModel> it = topSpend.iterator();
        int cnt = 0;
        while (it.hasNext()) {
            MarketShopTopModel tmp = it.next();
            topSpendRet.add(tmp);
            cnt++;
            if (cnt == 10)
                break;
        }
        return topSpendRet;
    }

    // 获取店铺访客数排名前十，不足10个只返回有数据的店铺
    // 输入:商铺类别列表List typeSeq，查询时间范围，从今天开始计算倒推dayBeforeToday天
    // 输出:排序后的店铺名+店铺访客数列表
    public List<MarketShopTopModel> getMarketTop10VisitorCntByTypeAndDayPeriod(List<String> typeSeq,
            int dayBeforeToday) {
        List<MarketShopTopModel> topSpend = dao.getMarketVisitorCntInfoByType(typeSeq, dayBeforeToday);
        List<MarketShopTopModel> topSpendRet = new ArrayList<MarketShopTopModel>();
        // 只取前10
        Iterator<MarketShopTopModel> it = topSpend.iterator();
        int cnt = 0;
        while (it.hasNext()) {
            MarketShopTopModel tmp = it.next();
            topSpendRet.add(tmp);
            cnt++;
            if (cnt == 10)
                break;
        }
        return topSpendRet;
    }

    // 获取商场驻留时长排名前十，不足10个只返回有数据的店铺
    // 输入:商铺类别列表List typeSeq，查询时间范围，从今天开始计算倒推dayBeforeToday天
    // 输出:排序后的店铺名+驻留时长列表
    public List<MarketShopTopModel> getMarketTop10DealyByTypeAndDayPeriod(List<String> typeSeq, int dayBeforeToday) {
        List<MarketShopTopModel> topSpend = dao.getMarketDelayInfoByType(typeSeq, dayBeforeToday);
        if (topSpend != null && topSpend.size() > 9) {
            topSpend = topSpend.subList(0, 9);
        }
        return topSpend;
    }

    // 获取商场店铺的面积统计情况，返回的MarketShopTopModel使用shopName字段作为区间信息
    public List<MarketShopTopModel> getMarketShopSquare() {
        List<MarketShopTopModel> shopSqrInfo = new ArrayList<MarketShopTopModel>();
        MarketShopTopModel sqr30 = new MarketShopTopModel();
        sqr30.setShopName("[0,30)");
        sqr30.setValue(0);
        MarketShopTopModel sqr60 = new MarketShopTopModel();
        sqr60.setShopName("[30,60)");
        sqr60.setValue(0);
        MarketShopTopModel sqr90 = new MarketShopTopModel();
        sqr90.setShopName("[60,90)");
        sqr90.setValue(0);
        MarketShopTopModel sqr120 = new MarketShopTopModel();
        sqr120.setShopName("[90,120)");
        sqr120.setValue(0);
        MarketShopTopModel sqrOther = new MarketShopTopModel();
        sqrOther.setValue(0);
        sqrOther.setShopName("other");
        List<MarketShopSquareModel> sqrOriInfo = dao.getMarketShopSquare();
        Iterator<MarketShopSquareModel> it = sqrOriInfo.iterator();
        while (it.hasNext()) {
            MarketShopSquareModel tmp = it.next();
            int square = Math.abs(tmp.getX1() - tmp.getX()) * Math.abs(tmp.getY1() - tmp.getY());
            if (square < 30) {
                sqr30.setValue(sqr30.getValue() + 1);
            } else if (square >= 30 && square < 60) {
                sqr60.setValue(sqr60.getValue() + 1);
            } else if (square >= 60 && square < 90) {
                sqr90.setValue(sqr90.getValue() + 1);
            } else if (square >= 90 && square < 120) {
                sqr120.setValue(sqr120.getValue() + 1);
            } else {
                sqrOther.setValue(sqrOther.getValue() + 1);
            }
        }
        shopSqrInfo.add(sqr30);
        shopSqrInfo.add(sqr60);
        shopSqrInfo.add(sqr90);
        shopSqrInfo.add(sqr120);
        shopSqrInfo.add(sqrOther);
        return shopSqrInfo;
    }

    // 获取商场的类型数据，只返回前80%类型，剩下的合并为other
    public List<StringIntModel> getMarketShopType() {
        List<StringIntModel> oriData = dao.getMarketShopType();
        List<StringIntModel> retData = new ArrayList<StringIntModel>();
        Iterator<StringIntModel> it = oriData.iterator();
        int allTypeCnt = 0;
        while (it.hasNext()) {
            StringIntModel tmp = it.next();
            allTypeCnt += tmp.getEleValue();
        }
        int alreadyGetcnt = 0;
        it = oriData.iterator();
        while (it.hasNext()) {
            StringIntModel tmp = it.next();
            if (alreadyGetcnt < allTypeCnt * 0.8) {
                StringIntModel needAdd = new StringIntModel();
                needAdd.setEleName(tmp.getEleName());
                needAdd.setEleValue(tmp.getEleValue());
                retData.add(needAdd);
                alreadyGetcnt += tmp.getEleValue();
            } else {
                StringIntModel needAdd = new StringIntModel();
                needAdd.setEleName("other");
                needAdd.setEleValue(allTypeCnt - alreadyGetcnt);
                retData.add(needAdd);
                break;
            }
        }
        return retData;
    }

    public void daoTester() {
        List<StringIntModel> topSpend = getMarketShopType();
        Iterator<StringIntModel> it = topSpend.iterator();
        while (it.hasNext()) {
            StringIntModel tmp = it.next();
            System.out.print("shop name[" + tmp.getEleName() + "] value[" + tmp.getEleValue() + "]\n");
        }

    }

    public boolean isDateIndateValueList(String date, List<MarketMonthDataModel> dataValueList) {
        Iterator<MarketMonthDataModel> it = dataValueList.iterator();
        while (it.hasNext()) {
            MarketMonthDataModel tmp = it.next();
            if (tmp.GetDate() == date) {
                return true;
            }
        }
        return false;

    }

    public void AddValueZeroDateDayForLast30Days(List<MarketMonthDataModel> dataValueList) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(calendar.getTime());
        calendar.add(Calendar.DATE, -30);
        System.out.println(calendar.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 1; i <= 30; ++i) {
            String dateStrting = sdf.format(calendar.getTime());
            if (!isDateIndateValueList(dateStrting, dataValueList)) {
                MarketMonthDataModel needAdd = new MarketMonthDataModel();
                needAdd.SetDate(dateStrting);
                needAdd.SetValue(0);
                dataValueList.add(needAdd);
            }
            calendar.add(Calendar.DATE, 1);
        }

    }

    /**
     * @Title: getTableData
     * @Description: 获取所有的商场信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/getData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request) {
        LOG.debug("getTableData:in");
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<MarketModel> resultList = daoMarket.getAllStore();
        modelMap.put("status", Params.RETURN_CODE_200);
        modelMap.put("data", resultList);
        LOG.debug("getTableData:out->data size:" + resultList.size());
        return modelMap;
    }

    /**
     * @Title: saveData
     * @Description: 添加商场信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/api/saveData", method = { RequestMethod.POST })
    public String saveData(MarketModel model) {
        LOG.debug("store saveData:in");
        model.setName(model.getName().trim());
        Date current = new Date();
        model.setUpdateTime(current);

        // 保存
        try {
            if (StringUtils.isEmpty(model.getId())) {
                model.setCreateTime(current);
                daoMarket.saveInfo(model);
            } else {
                daoMarket.updateInfo(model);
            }
        } catch (DuplicateKeyException e) {
            LOG.error(e);
        } catch (Exception e) {
            LOG.error(e);
        }
        LOG.debug("store saveData:out");
        return "redirect:/home/storeMng";
    }

    /**
     * @Title: deleteData
     * @Description: 删除指定商场
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/deleteData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> deleteData(@RequestParam("id") String id) {
        LOG.debug("deleteData:in->id:" + id);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            daoMarket.deleteById(id);
        } catch (Exception e) {
            LOG.error(e);
            modelMap.put("error", e.getStackTrace());
        }
        LOG.debug("deleteData:out");
        return modelMap;
    }

    @RequestMapping(value = "/api/checkName", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> checkName(@RequestParam("name") String name,@RequestParam("id") String id) {
        LOG.debug("store checkName");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (id!="") {
        	int check = daoMarket.checkName1(name,id);
			if (check != 0) {
				modelMap.put("status", 0);
			} else {
				modelMap.put("status", 1);
			}
		}else
		{
			int check = daoMarket.checkName(name);
			if (check != 0) {
				modelMap.put("status", 0);
			} else {
				modelMap.put("status", 1);
			}
			
		}
        LOG.debug("store saveData:out");
        return modelMap;
    }

    @RequestMapping(value = "/getMomentTotal", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMomentTotal(@RequestParam("mapId") String mapId) {
        Calendar c = Calendar.getInstance();
        Date nowDate = new Date();
       long timestamp=c.getTimeInMillis();
        int[] momentCounts = new int[7];
        int todayMomentCount = 0;
        for (int i = 0; i < 8; i++) {
            c.setTime(nowDate);
            c.add(Calendar.DATE, -i);
//            String newRiqi = Util.dateFormat(c.getTimeInMillis(), Params.YYMMDD);
            String yesDayss = Util.dateFormat(c.getTimeInMillis(), Params.YYYYMMDD);
            String yesTableNames = Params.LOCATION + yesDayss;
            long yesTimess = c.getTimeInMillis();
            long yesTimesBegins = yesTimess - 4000;
            int yesCounts = 0;
//            System.out.println("start:"+Util.dateFormat(System.currentTimeMillis(), Params.YYYYMMDDHHMMSS));
            if (statisticsDao.isTableExist(yesTableNames, db) >= 1) {
                
                yesCounts = locationDao.getMomentTotal(yesTimesBegins, yesTimess, yesTableNames, mapId);
            }
//            System.out.println("end:"+Util.dateFormat(System.currentTimeMillis(), Params.YYYYMMDDHHMMSS));
            if (i == 0) {
                todayMomentCount = yesCounts;
            } else {
                momentCounts[7 - i] = yesCounts;
            }

        }
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put(Params.RETURN_KEY_ERROR, Params.RETURN_CODE_200);
        modelMap.put("todayMomentCount", todayMomentCount);
        modelMap.put("weekMomentCounts", momentCounts);
        modelMap.put("timestamp", timestamp);
        return modelMap;
    }
    
    @RequestMapping(value = "/getMallTotal", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMallTotal(@RequestParam("storeId") String storeId) {
        Calendar c = Calendar.getInstance();
        Date nowDate = new Date();
       long timestamp=c.getTimeInMillis();
        int[] momentCounts = new int[7];
        int todayMomentCount = 0;
        for (int i = 0; i < 8; i++) {
            c.setTime(nowDate);
            c.add(Calendar.DATE, -i);
//            String newRiqi = Util.dateFormat(c.getTimeInMillis(), Params.YYMMDD);
            String yesDayss = Util.dateFormat(c.getTimeInMillis(), Params.YYYYMMDD);
            String yesTableNames = Params.LOCATION + yesDayss;
            long yesTimess = c.getTimeInMillis();
            long yesTimesBegins = yesTimess - 4000;
            int yesCounts = 0;
//            System.out.println("start:"+Util.dateFormat(System.currentTimeMillis(), Params.YYYYMMDDHHMMSS));
            if (statisticsDao.isTableExist(yesTableNames, db) >= 1) {
                
                yesCounts = locationDao.getMallTotal(yesTimesBegins, yesTimess, yesTableNames, storeId);
            }
//            System.out.println("end:"+Util.dateFormat(System.currentTimeMillis(), Params.YYYYMMDDHHMMSS));
            if (i == 0) {
                todayMomentCount = yesCounts;
            } else {
                momentCounts[7 - i] = yesCounts;
            }

        }
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put(Params.RETURN_KEY_ERROR, Params.RETURN_CODE_200);
        modelMap.put("todayMomentCount", todayMomentCount);
        modelMap.put("weekMomentCounts", momentCounts);
        modelMap.put("timestamp", timestamp);
        return modelMap;
    }    
    

    @RequestMapping(value = "/getWeekTotal", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getWeekTotal(@RequestParam("storeId") String storeId) {
        List<List<String>> userIdAll = new ArrayList<>();
        List<Float> delaytimeAll = new ArrayList<>();
        List<String> todayUserIdList = new ArrayList<>(); // 今天的userId
        long todayDelayCount = 0; // 今天的位置条数

        // 过去7天的一定格式日期数组
        String[] weeks = Util.getLastNumDays(7, Params.YYYYMMDD2);
        String todayDate = Util.dateFormat(new Date(), Params.YYYYMMDD2); // 当天日期
        String todayTable = Params.LOCATION + todayDate.replace("-", "");
        if (statisticsDao.isTableExist(todayTable, db) >= 1) {
            todayUserIdList = locationDao.getTodayUserIdList(todayTable, storeId);
            Long temp = locationDao.getTodayDelayCount(todayTable, storeId);
            if (temp != null) {
                todayDelayCount = temp;
            }
        }

        for (int i = 0; i < weeks.length; i++) {
            List<String> userIdList = new ArrayList<>();
            float delaytimeSum = 0;
            String tableName = Params.SHOPLOCATION
                    + weeks[i].replace("-", "").substring(0, weeks[i].replace("-", "").length() - 2);
            if (statisticsDao.isTableExist(tableName, db) >= 1) {
                userIdList = daoMarket.getUserIdList(tableName, storeId, weeks[i]);
                Float temp = daoMarket.getDelaytimeSum(tableName, storeId, weeks[i]);
                if (temp != null) {
                    delaytimeSum = temp;
                }
            }
            userIdAll.add(userIdList);
            delaytimeAll.add(delaytimeSum);
        }
        int todayUser = todayUserIdList.size();
        todayUserIdList.removeAll(userIdAll.get(weeks.length - 1));
        int todayNewUser = todayUserIdList.size();
        float avgDelay = 0;
        if (todayUser != 0) {
            avgDelay = Util.sToM(todayDelayCount * 2 / todayUser);
        }
        JSONObject weekUsercount = new JSONObject();
        JSONObject weekDelaytime = new JSONObject();
        JSONObject weekNewUsercount = new JSONObject();
        long allWeekCount = 0;
        float allWeekTime = 0;
        // 过去7天的一定格式日期数组
        String[] weeks2 = Util.getLastNumDays(7, Params.YYMMDD);
        for (int i = 0; i < weeks2.length; i++) {
            List<String> userIdList = userIdAll.get(i);
            float delaytimeSum = delaytimeAll.get(i);
            allWeekCount += userIdList.size();
            allWeekTime += delaytimeSum;
            // JSONObject jsonObject1 = new JSONObject();
            // jsonObject1.put("name", weeks2[i]);
            // jsonObject1.put("value", userIdList.size());
            // weekUsercount.add(jsonObject1);
            weekUsercount.put(weeks2[i], userIdList.size());

            float avgDelaytime = 0;
            if (userIdList.size() != 0) {
                avgDelaytime = Util.sToM((delaytimeSum * 60 / userIdList.size()));
            }
            weekDelaytime.put(weeks2[i], avgDelaytime);
        }
        for (int j = 6; j > 0; j--) {
            List<String> userIdList = userIdAll.get(j);
            List<String> lastIdList = userIdAll.get(j - 1);
            userIdList.removeAll(lastIdList);
            // JSONObject jsonObject3 = new JSONObject();
            // jsonObject3.put("name", weeks2[j]);
            // jsonObject3.put("value", userIdList.size());
            weekNewUsercount.put(weeks2[j], userIdList.size());
        }
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put(Params.RETURN_KEY_ERROR, Params.RETURN_CODE_200);
        // modelMap.put(Params.RETURN_KEY_DATA, shopList);
        modelMap.put("todayUser", todayUser);
        modelMap.put("todayNewUser", todayNewUser);
        modelMap.put("todayAvgDelay", avgDelay);
        modelMap.put("weekUsercount", weekUsercount);
        modelMap.put("weekDelaytime", weekDelaytime);
        modelMap.put("weekNewUsercount", weekNewUsercount);

        modelMap.put("allWeekCount", allWeekCount);
        modelMap.put("allWeekAvgDelay", allWeekCount == 0 ? 0 : Util.sToM((allWeekTime * 60 / allWeekCount)));
        return modelMap;
    }
    
    @RequestMapping(value = "/getWeekTotalByFloor", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getWeekTotalByFloor(@RequestParam("mapId") String mapId) {
        TreeMap<String, String> weekUsercount = new TreeMap<>();
        TreeMap<String, String> weekDelaytime = new TreeMap<>();
        TreeMap<String, String> weekNewUsercount = new TreeMap<>();
        List<String> todayUserIdList = new ArrayList<>(); // 今天的userId
        long todayDelayCount = 0; // 今天的位置条数
        List<String> listYesUser = new ArrayList<>();//昨天的user
        int todayNewUser = 0; 

        // 过去7天的一定格式日期数组
        String[] weeks = Util.getLastNumDays(7, Params.YYYYMMDD2);
        String todayDate = Util.dateFormat(new Date(), Params.YYYYMMDD2); // 当天日期
        String todayTable = Params.LOCATION + todayDate.replace("-", "");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        long yesTimestamp = c.getTimeInMillis();
        String yesDay = Util.dateFormat(yesTimestamp, Params.YYYYMMDD);
        String yesTableName = Params.LOCATION + yesDay;
        
        if (statisticsDao.isTableExist(todayTable, db) >= 1) {
            todayUserIdList = locationDao.getTodayUserIdListByMapId(todayTable, mapId);
            Long temp = locationDao.getTodayDelayCountByMapId(todayTable, mapId);
            if (temp != null) {
                todayDelayCount = temp;
            }
        }
        if (statisticsDao.isTableExist(yesTableName, db) >= 1) {
            listYesUser = locationDao.getTodayUserIdListByMapId(yesTableName, mapId);
        }
        int todayUser = todayUserIdList.size();
        todayUserIdList.removeAll(listYesUser);
        todayNewUser = todayUserIdList.size();
        String yesDays = weeks[0];
        String  sevenDays = weeks[weeks.length-1];
        float allWeekCount = 0;
        float allWeekTime = 0;
        for (int i = 0; i < weeks.length; i++) {
            weekNewUsercount.put(weeks[i], "0");
            List<StatisticsModel> userIdList =  daoMarket.getUserIdListByMapId(mapId, weeks[i]);
            if (userIdList.size()>0) {
                allWeekCount += userIdList.get(0).getUserCount();
                allWeekTime +=  Double.valueOf(userIdList.get(0).getVisitTime())*userIdList.get(0).getUserCount();
                weekUsercount.put(weeks[i], String.valueOf(userIdList.get(0).getUserCount()));
                weekDelaytime.put(weeks[i], userIdList.get(0).getVisitTime());
            }else
            {
                weekUsercount.put(weeks[i], "0");
                weekDelaytime.put(weeks[i], "0"); 
            }
        }
        List<NewUserModel> list = statisticsDao.getNewUserByMapId(mapId,yesDays,sevenDays);
        for (int i = 0; i < list.size(); i++) {
            weekNewUsercount.put(list.get(i).getTime(),String.valueOf(list.get(i).getNewUser()));
        }
        float avgDelay = 0;
        if (todayUser != 0) {
            avgDelay = Util.sToM(todayDelayCount * 2 / todayUser);
        }

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put(Params.RETURN_KEY_ERROR, Params.RETURN_CODE_200);
        // modelMap.put(Params.RETURN_KEY_DATA, shopList);
        modelMap.put("todayUser", todayUser);
        modelMap.put("todayNewUser", todayNewUser);
        modelMap.put("todayAvgDelay", avgDelay);
        modelMap.put("weekUsercount", weekUsercount);
        modelMap.put("weekDelaytime", weekDelaytime);
        modelMap.put("weekNewUsercount", weekNewUsercount);

        modelMap.put("allWeekCount", allWeekCount);
        modelMap.put("allWeekAvgDelay", allWeekCount == 0 ? 0 : Util.sToM((allWeekTime * 60 / allWeekCount)));
        return modelMap;
    }    
    
    /** 
     * @Title: getWeatherInfo 
     * @Description: 获取天气信息 
     * @author 	ZhuYongchao  
     * @date 2017年7月27日 下午3:33:11 
     * @param name
     * @return 
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/getWeatherInfo", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String,List<String>> getWeatherInfo(@RequestParam("name") String name) {
    	Map<String,List<String>> map = new HashMap<>();
    	List<String> listWeek = new ArrayList<>();
    	List<String> listDay = new ArrayList<>();
    	List<String> listTem = new ArrayList<>();
    	List<String> listCity = new ArrayList<>();
    	WeatherInfo wi = new WeatherInfo();
    	Date date = new Date();
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	SimpleDateFormat formatter = new SimpleDateFormat(Params.MMDD);
    	SimpleDateFormat weekFm = new SimpleDateFormat(Params.EEEE);
     	try {
         	JSONObject jsonObject = JSONObject.fromObject(wi.json(name));
         	JSONObject obj = jsonObject.getJSONObject("data");
         	JSONArray array = obj.getJSONArray("forecast");
         	String city;
         	if (jsonObject.getString("city")==null) {
         		city=" ";
			}else{
				city = jsonObject.getString("city");
			}
         	listCity.add(city);
         	map.put("city", listCity);
         	String high;
         	String type;
         	String low;
         	String tem;
         	String day;
         	String week;
         	for (int i = 0; i < 5; i++) {
         		JSONObject obj1 =(JSONObject) array.get(i);
         		if (obj1.getString("high")==null ||obj1.getString("type")==null
         				||obj1.getString("low")==null) {
					tem=" ";
				}else{
					high = obj1.getString("high").substring(2);
	             	type = obj1.getString("type");
	             	low = obj1.getString("low").substring(2);
	             	tem = type+" "+low+"~"+high;
				}	
             	if (i==0) {
             		day = formatter.format(date);
                 	week = weekFm.format(date);
				}else{
					//把日期往后增加一天.整数往后推,负数往前移动
					calendar.add(calendar.DATE,1);
	             	date=calendar.getTime();
	             	day = formatter.format(date);
	             	week = weekFm.format(date);
				}
             	listWeek.add(week);
             	listDay.add(day);
             	listTem.add(tem);
			}
         	map.put("date", listWeek);
         	map.put("week", listDay);
         	map.put("temperature", listTem);
         	
 		} catch (Exception e) {
 			LOG.error(e);
 			// TODO: handle exception
 			
 		}

        return map;
    }
    
    
    @RequestMapping(value = "/getRates", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String,List<String>> getRates(@RequestParam("storeId")String storeId){
    	Map<String,List<String>> map = new HashMap<String, List<String>>();
//    	List<Integer> list = new ArrayList<>();
        List<ShopModel> listModel = rateDao.getShopInfoByStore(storeId);
//    	list = rateDao.selectShopIdByStoreId(storeId);
    	List<String> listEnterRate = new ArrayList<>();
    	List<String> listShopName = new ArrayList<>();
    	List<String> listOverRate = new ArrayList<>();
    	List<String> listDeepRate = new ArrayList<>();
    	String enterRate;
    	String shopName;
    	String overflowRate;
    	String deepRate;
    	for (int i = 0; i < listModel.size(); i++) {
            enterRate=String.valueOf(rates.getEnter(listModel.get(i)));
            overflowRate = String.valueOf(rates.getOverflow1(Integer.parseInt(listModel.get(i).getId())));
            deepRate = String.valueOf(rates.getDeep(Integer.parseInt(listModel.get(i).getId())));
            shopName = rateDao.selectShopNameById(Integer.parseInt(listModel.get(i).getId()));
    		listEnterRate.add(enterRate);
    		listShopName.add(shopName);
    		listOverRate.add(overflowRate);
    		listDeepRate.add(deepRate);
		}
    	map.put("shopName", listShopName);
    	map.put("eRate", listEnterRate);
    	map.put("oRate", listOverRate);
    	map.put("dRate", listDeepRate);
    	return map;
    }
    
    /** 
     * @Title: getAllFloors 
     * @Description: 获取所有地图信息
     * @return 
     */
    @RequestMapping(value = "/api/getAllFloors", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String,Object> getAllFloors(){
        Map<String,Object> map = new HashMap<String,Object>();
        List<MapMngModel> list = mapMngDao.doallquery();
        map.put("data", list);
        map.put("status", Params.RETURN_CODE_200);
        return map;
    }
    
    @RequestMapping(value = "/getRatesByMapId", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String,List<String>> getRatesByMapId(@RequestParam("mapId") String mapId){
    	Map<String,List<String>> map = new HashMap<String, List<String>>();
//    	List<Integer> list = new ArrayList<>();
    	List<ShopModel> listModel = rateDao.getShopInfoByMapId(mapId);
//    	list = rateDao.selectShopIdByMapId(mapId);
    	List<String> listEnterRate = new ArrayList<>();
    	List<String> listShopName = new ArrayList<>();
    	List<String> listOverRate = new ArrayList<>();
    	List<String> listDeepRate = new ArrayList<>();
    	String enterRate;
    	String shopName;
    	String overflowRate;
    	String deepRate;
    	for (int i = 0; i < listModel.size(); i++) {
    		enterRate=String.valueOf(rates.getEnter(listModel.get(i)));
    		overflowRate = String.valueOf(rates.getOverflow1(Integer.parseInt(listModel.get(i).getId())));
    		deepRate = String.valueOf(rates.getDeep(Integer.parseInt(listModel.get(i).getId())));
    		shopName = rateDao.selectShopNameById(Integer.parseInt(listModel.get(i).getId()));
    		listEnterRate.add(enterRate);
    		listShopName.add(shopName);
    		listOverRate.add(overflowRate);
    		listDeepRate.add(deepRate);
		}
    	map.put("shopName", listShopName);
    	map.put("eRate", listEnterRate);
    	map.put("oRate", listOverRate);
    	map.put("dRate", listDeepRate);
    	return map;
    }
    
}
