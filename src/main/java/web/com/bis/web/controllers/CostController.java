package com.bis.web.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.bis.dao.CostDao;
import com.bis.dao.StatisticsDao;

/**
 * @ClassName: CostController
 * @Description: 消费统计controller
 * @author gyr
 * @date 2017年6月20日 下午2:31:45
 * 
 */
@Controller
@RequestMapping(value = "/cost")
public class CostController {
    @Autowired
    private CostDao dao;

    @Value("${mysql.db}")
    private String db;

    @Autowired
    private StatisticsDao statisticsDao;

    private static final Logger LOG = Logger.getLogger(CostController.class);

    /**
     * 
     * @Title: getUserAttrNowDay
     * @Description: 当天--个人属性
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserAttrNowDay", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserCostNowDay(@RequestParam("userId") String userId) {
        LOG.info("CostController:getUserAttrNowDay");

        try {
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_200);

            int totalSum = (int) (dao.getUserCostSumNowDay(userId) == null ? 0 : dao.getUserCostSumNowDay(userId));
            modelMap.put("totalSum", totalSum);

            int totalCount = dao.getUserCostCountNowDay(userId) == null ? 0 : dao.getUserCostCountNowDay(userId);
            modelMap.put("totalCount", totalCount);

            List<Map<String, Object>> listSumByCategory = dao.getUserCostSumNowDayByCategory(userId);
            modelMap.put("sumByCategory", listSumByCategory);
            List<Map<String, Object>> listCountByCategory = dao.getUserCostCountNowDayByCategory(userId);
            modelMap.put("countByCategory", listCountByCategory);

            List<Map<String, Object>> listSumByShop = dao.getUserCostSumNowDayByShop(userId);
            modelMap.put("sumByShop", listSumByShop);
            List<Map<String, Object>> listCountByShop = dao.getUserCostCountNowDayByShop(userId);
            modelMap.put("countByShop", listCountByShop);

            String tableName = Params.LOCATION + Util.dateFormat(new Date(), Params.YYYYMMDD);
            if (statisticsDao.isTableExist(tableName, db) >= 1) {
                int locationCount = dao.getUserLocationCountNowDay(tableName, userId) == null ? 0
                        : dao.getUserLocationCountNowDay(tableName, userId);
                modelMap.put("delayTime", (float) (locationCount) / 30);
            } else {
                modelMap.put("delayTime", 0);
            }
            return modelMap;
        } catch (Exception e) {
            // 异常，可能是没数据等
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_500);
            return modelMap;
        }

    }

    /**
     * 
     * @Title: getUserAttrLastMonth
     * @Description: 近30天--个人属性
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserAttrLastMonth", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserCostLastMonth(@RequestParam("userId") String userId) {
        LOG.info("CostController:getUserAttrLastMonth");

        try {
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_200);

            // float totalSum = dao.getUserCostSumLastMonth(userId) == null ? 0
            // : dao.getUserCostSumLastMonth(userId);
            // modelMap.put("totalSum", totalSum);
            //
            // int totalCount = dao.getUserCostCountLastMonth(userId) == null ?
            // 0 : dao.getUserCostCountLastMonth(userId);
            // modelMap.put("totalCount", totalCount);
            //
            // List<Map<String, Object>> listSumByCategory =
            // dao.getUserCostSumLastMonthByCategory(userId);
            // modelMap.put("sumByCategory", listSumByCategory);
            // List<Map<String, Object>> listCountByCategory =
            // dao.getUserCostCountLastMonthByCategory(userId);
            // modelMap.put("countByCategory", listCountByCategory);
            //
            // List<Map<String, Object>> listSumByShop =
            // dao.getUserCostSumLastMonthByShop(userId);
            // modelMap.put("sumByShop", listSumByShop);
            // List<Map<String, Object>> listCountByShop =
            // dao.getUserCostCountLastMonthByShop(userId);
            // modelMap.put("countByShop", listCountByShop);

            // 过去30天的一定格式日期数组
            String[] days = Util.getLastNumDays(30, Params.YYYYMMDD2);
            List<Map<String, Object>> listSumEveryDay = dao.getUserCostSumLastMonthEveryDay(userId);
            fillZeroTo30Days(listSumEveryDay, days);
            modelMap.put("sumEveryDay", listSumEveryDay);
            List<Map<String, Object>> listCountEveryDay = dao.getUserCostCountLastMonthEveryDay(userId);
            fillZeroTo30Days(listCountEveryDay, days);
            modelMap.put("countEveryDay", listCountEveryDay);

            // 判断要查的表
            String[] queryMonths;
            String nowDate = Util.dateFormat(new Date(), Params.YYYYMMDD2); // 当天日期
            String[] str = nowDate.split("-");
            if ("03".equals(str[1]) && "02".equals(str[2])) {
                queryMonths = new String[3];
                String[] str1 = Util.getLastNumMonths(2, Params.YYYYMM);
                queryMonths[0] = str1[0];
                queryMonths[1] = str1[1];
                queryMonths[2] = str[0] + str[1];
            } else if ("01".equals(str[2])) {
                queryMonths = Util.getLastNumMonths(2, Params.YYYYMM);
            } else {
                queryMonths = new String[2];
                String[] str1 = Util.getLastNumMonths(1, Params.YYYYMM);
                queryMonths[0] = str1[0];
                queryMonths[1] = str[0] + str[1];
            }
            // 初始化要存放结果的list
            List<Map<String, Object>> resultList = new ArrayList<>();
            // 需要查找的日期
            String[] queryDates = Util.getLastNumDays(30, Params.YYYYMMDD2);
            // 依次查询需要查的表
            for (int i = 0; i < queryMonths.length; i++) {
                // 每个表查找结果
                if (statisticsDao.isTableExist(Params.SHOPLOCATION + queryMonths[i], db) >= 1) {
                    List<Map<String, Object>> queryList = dao.getUserDelayByDate(Params.SHOPLOCATION + queryMonths[i],
                            userId);
                    for (int j = 0; j < queryList.size(); j++) {
                        Map<String, Object> dateData = queryList.get(j);
                        // 遍历如果是需要查找的日期，才放进结果list
                        for (String u : queryDates) {
                            if (u.equals(dateData.get("name"))) {
                                resultList.add(dateData);
                                break;
                            }
                        }
                    }
                }
            }
            fillZeroTo30Days(resultList, days);
            modelMap.put("delayTimeEveryDay", resultList);
            return modelMap;
        } catch (Exception e) {
            // 异常，可能是没数据等
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_500);
            return modelMap;
        }

    }

    /**
     * 
     * @Title: getUserAttrLastYear
     * @Description: 近12月--个人属性
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserAttrLastYear", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserCostLastYear(@RequestParam("userId") String userId) {
        LOG.info("CostController:getUserAttrLastYear");

        try {
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_200);

            // 过去12月的一定格式日期数组
            String[] months = Util.getLastNumMonths(12, Params.YYYYMMM);
            List<Map<String, Object>> listSumEveryDay = dao.getUserCostSumLastYearEveryMonth(userId);
            fillZero12Months(listSumEveryDay, months);
            modelMap.put("sumEveryMonth", listSumEveryDay);
            List<Map<String, Object>> listCountEveryDay = dao.getUserCostCountLastYearEveryMonth(userId);
            fillZero12Months(listCountEveryDay, months);
            modelMap.put("countEveryMonth", listCountEveryDay);
            List<Map<String, Object>> stayTimeList = new ArrayList<>();
            List<Map<String, Object>> stayCountList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                if (statisticsDao.isTableExist(Params.SHOPLOCATION + months[i].replace("-", ""), db) >= 1) {
                    Map<String, Object> result = dao
                            .getUserDelayByYear(Params.SHOPLOCATION + months[i].replace("-", ""), userId);
                    if (result == null) {
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("name", months[i]);
                        map1.put("value", 0);
                        stayTimeList.add(map1);
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("name", months[i]);
                        map2.put("value", 0);
                        stayCountList.add(map1);
                    } else {
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("name", months[i]);
                        map1.put("value", result.get("timeDelay") == null ? 0 : result.get("timeDelay"));
                        stayTimeList.add(map1);
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("name", months[i]);
                        map2.put("value", result.get("countDelay") == null ? 0 : result.get("countDelay"));
                        stayCountList.add(map2);
                    }
                } else {
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("name", months[i]);
                    map1.put("value", 0);
                    stayTimeList.add(map1);
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("name", months[i]);
                    map2.put("value", 0);
                    stayCountList.add(map1);
                }
            }
            modelMap.put("delayTimeEveryMonth", stayTimeList);
            modelMap.put("delayCountEveryMonth", stayCountList);
            return modelMap;
        } catch (Exception e) {
            // 异常，可能是没数据等
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_500);
            return modelMap;
        }

    }

    /**
     * 
     * @Title: fillZeroTo30Days
     * @Description: 填充数据到30天，不存在的补0
     * @param list
     * @param days
     */
    private void fillZeroTo30Days(List<Map<String, Object>> list, String[] days) {
        for (int i = 0; i < days.length; i++) {
            if (i >= list.size()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", days[i]);
                map.put("value", 0);
                list.add(i, map);
            } else if (!days[i].equals(list.get(i).get("name"))) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", days[i]);
                map.put("value", 0);
                list.add(i, map);
            }
        }

    }

    /**
     * 
     * @Title: fillZeroTo12Months
     * @Description: 填充数据到12月，不存在的补0
     * @param list
     * @param days
     */
    private void fillZero12Months(List<Map<String, Object>> list, String[] months) {
        for (int i = 0; i < months.length; i++) {
            if (i >= list.size()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", months[i]);
                map.put("value", 0);
                list.add(i, map);
            } else if (!months[i].equals(list.get(i).get("name"))) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", months[i]);
                map.put("value", 0);
                list.add(i, map);
            }
        }

    }

    /**
     * 
     * @Title: getUserHobby
     * @Description: 个人习惯，类别和店铺统计等
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserHobby", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserHobby(@RequestParam("userId") String userId) {
        LOG.info("CostController:getUserHobby");

        try {
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_200);

            BigDecimal monthSum = (BigDecimal) (dao.getUserCostSumLastMonth(userId) == null ? 0
                    : dao.getUserCostSumLastMonth(userId));
            modelMap.put("monthSum", monthSum);

            int monthCount = dao.getUserCostCountLastMonth(userId) == null ? 0 : dao.getUserCostCountLastMonth(userId);
            modelMap.put("monthCount", monthCount);

            List<Map<String, Object>> monthSumByCategory = dao.getUserCostSumLastMonthByCategory(userId);
            fillSurplusByOther(monthSumByCategory, monthSum);
            modelMap.put("monthSumByCategory", monthSumByCategory);

            List<Map<String, Object>> monthCountByCategory = dao.getUserCostCountLastMonthByCategory(userId);
            fillSurplusByOther(monthCountByCategory, monthCount);
            modelMap.put("monthCountByCategory", monthCountByCategory);

//            if (monthSumByCategory != null && monthSumByCategory.size() != 0) {
//                List<Map<String, Object>> monthSumByShop = dao.getUserCostSumLastMonthByShop(userId,
//                        (String) monthSumByCategory.get(0).get("name"));
//                fillSurplusByOther(monthSumByShop, monthSumByCategory.get(0).get("value"));
//                modelMap.put("monthSumByShop", monthSumByShop);
//            } else {
//                List<Map<String, Object>> monthSumByShop = new ArrayList<>();
//                modelMap.put("monthSumByShop", monthSumByShop);
//            }
//            if (monthCountByCategory != null && monthCountByCategory.size() != 0) {
//                List<Map<String, Object>> monthCountByShop = dao.getUserCostCountLastMonthByShop(userId,
//                        (String) monthCountByCategory.get(0).get("name"));
//                fillSurplusByOther(monthCountByShop, monthCountByCategory.get(0).get("value"));
//                modelMap.put("monthCountByShop", monthCountByShop);
//            } else {
//                List<Map<String, Object>> monthCountByShop = new ArrayList<>();
//                modelMap.put("monthCountByShop", monthCountByShop);
//            }
            BigDecimal yearSum = (BigDecimal) (dao.getUserCostSumLastYear(userId) == null ? 0
                    : dao.getUserCostSumLastYear(userId));
            modelMap.put("yearSum", yearSum);

            int yearCount = dao.getUserCostCountLastYear(userId) == null ? 0 : dao.getUserCostCountLastYear(userId);
            modelMap.put("yearCount", yearCount);

            List<Map<String, Object>> yearSumByCategory = dao.getUserCostSumLastYearByCategory(userId);
            fillSurplusByOther(yearSumByCategory, yearSum);
            modelMap.put("yearSumByCategory", yearSumByCategory);

            List<Map<String, Object>> yearCountByCategory = dao.getUserCostCountLastYearByCategory(userId);
            fillSurplusByOther(yearCountByCategory, yearCount);
            modelMap.put("yearCountByCategory", yearCountByCategory);

//            if (yearSumByCategory != null && yearSumByCategory.size() != 0) {
//                List<Map<String, Object>> yearSumByShop = dao.getUserCostSumLastYearByShop(userId,
//                        (String) yearSumByCategory.get(0).get("name"));
//                fillSurplusByOther(yearSumByShop, yearSumByCategory.get(0).get("value"));
//                modelMap.put("yearSumByShop", yearSumByShop);
//            } else {
//                List<Map<String, Object>> yearSumByShop = new ArrayList<>();
//                modelMap.put("yearSumByShop", yearSumByShop);
//            }
//            if (yearCountByCategory != null && yearCountByCategory.size() != 0) {
//                List<Map<String, Object>> yearCountByShop = dao.getUserCostCountLastYearByShop(userId,
//                        (String) yearCountByCategory.get(0).get("name"));
//                fillSurplusByOther(yearCountByShop, yearCountByCategory.get(0).get("value"));
//                modelMap.put("yearCountByShop", yearCountByShop);
//            } else {
//                List<Map<String, Object>> yearCountByShop = new ArrayList<>();
//                modelMap.put("yearCountByShop", yearCountByShop);
//            }
            // 初始化要存放结果的list
            List<Map<String, Object>> resultList1 = new ArrayList<>(); // 类别的驻留时间
            List<Map<String, Object>> resultList2 = new ArrayList<>(); // 类别的驻留次数
            // 过去12月的一定格式日期数组
            String[] months = Util.getLastNumMonths(12, Params.YYYYMMM);
            for (int i = 0; i < 12; i++) {
                if (statisticsDao.isTableExist(Params.SHOPLOCATION + months[i].replace("-", ""), db) >= 1) {
                    List<Map<String, Object>> result = dao
                            .getUserYearDelayByCategory(Params.SHOPLOCATION + months[i].replace("-", ""), userId);
                    if (result == null) {
                    } else {
                        for (Map<String, Object> map1 : result) {
                            boolean flag = false;
                            for (int j = 0; j < resultList1.size(); j++) {
                                Map<String, Object> map2 = resultList1.get(j);
                                if (map2.get("name").equals(map1.get("name"))) {
                                    map2.put("value",
                                            ((BigDecimal) map2.get("value")).add((BigDecimal) map1.get("timeDelay")));
                                    Map<String, Object> map3 = resultList2.get(j);
                                    map3.put("value", (long) map3.get("value") + (long) map1.get("countDelay"));
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                Map<String, Object> tempMap1 = new HashMap<>();
                                tempMap1.put("name", (String) map1.get("name"));
                                tempMap1.put("value", map1.get("timeDelay"));
                                resultList1.add(tempMap1);
                                Map<String, Object> tempMap2 = new HashMap<>();
                                tempMap2.put("name", (String) map1.get("name"));
                                tempMap2.put("value", map1.get("countDelay"));
                                resultList2.add(tempMap2);
                            }
                        }

                    }
                } else {
                }
            }
            transformToResponse(resultList1);
            transformToResponse(resultList2);
            modelMap.put("yearStaySumByCategory", resultList1);
            modelMap.put("yearStayCountByCategory", resultList2);


            // 判断要查的表
            String[] queryMonths;
            String nowDate = Util.dateFormat(new Date(), Params.YYYYMMDD2); // 当天日期
            String[] str = nowDate.split("-");
            if ("03".equals(str[1]) && "02".equals(str[2])) {
                queryMonths = new String[3];
                String[] str1 = Util.getLastNumMonths(2, Params.YYYYMM);
                queryMonths[0] = str1[0];
                queryMonths[1] = str1[1];
                queryMonths[2] = str[0] + str[1];
            } else if ("01".equals(str[2])) {
                queryMonths = Util.getLastNumMonths(2, Params.YYYYMM);
            } else {
                queryMonths = new String[2];
                String[] str1 = Util.getLastNumMonths(1, Params.YYYYMM);
                queryMonths[0] = str1[0];
                queryMonths[1] = str[0] + str[1];
            }

            // 初始化要存放结果的list
            List<Map<String, Object>> resultList3 = new ArrayList<>(); // 类别的驻留时间
            List<Map<String, Object>> resultList4 = new ArrayList<>(); // 类别的驻留次数

            for (int i = 0; i < queryMonths.length; i++) {
                // 每个表查找结果
                if (statisticsDao.isTableExist(Params.SHOPLOCATION + queryMonths[i], db) >= 1) {
                    List<Map<String, Object>> result = dao
                            .getUserMonthDelayByCategory(Params.SHOPLOCATION + queryMonths[i], userId);
                    if (result == null) {
                    } else {
                        for (Map<String, Object> map1 : result) {
                            boolean flag = false;
                            for (int j = 0; j < resultList3.size(); j++) {
                                Map<String, Object> map2 = resultList3.get(j);
                                if (map2.get("name").equals(map1.get("name"))) {
                                    map2.put("value",
                                            ((BigDecimal) map2.get("value")).add((BigDecimal) map1.get("timeDelay")));
                                    Map<String, Object> map3 = resultList4.get(j);
                                    map3.put("value", (long) map3.get("value") + (long) map1.get("countDelay"));
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                Map<String, Object> tempMap1 = new HashMap<>();
                                tempMap1.put("name", (String) map1.get("name"));
                                tempMap1.put("value", map1.get("timeDelay"));
                                resultList3.add(tempMap1);
                                Map<String, Object> tempMap2 = new HashMap<>();
                                tempMap2.put("name", (String) map1.get("name"));
                                tempMap2.put("value", map1.get("countDelay"));
                                resultList4.add(tempMap2);
                            }
                        }

                    }
                }
            }

            transformToResponse(resultList3);
            transformToResponse(resultList4);
            modelMap.put("monthStaySumByCategory", resultList3);
            modelMap.put("monthStayCountByCategory", resultList4);


            return modelMap;
        } catch (Exception e) {
            // 异常，可能是没数据等
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_500);
            return modelMap;
        }

    }

    /**
     * 
     * @Title: transformToResponse
     * @Description: 将按类别或者店铺汇总的数据转化成需要返回的集合
     * @param list
     */

    private void transformToResponse(List<Map<String, Object>> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        // 按value大小排序
        Collections.sort(list, new Comparator<Map<String, Object>>() {

            @Override
            public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                // TODO Auto-generated method stub
                if (map1.get("value") instanceof BigDecimal) {
                    return -((BigDecimal) map1.get("value")).compareTo((BigDecimal) map2.get("value"));
                } else {
                    if ((long) map1.get("value") < (long) map2.get("value")) {
                        return 1;
                    } else if ((long) map1.get("value") > (long) map2.get("value")) {
                        return -1;
                    } else {
                        return 0;
                    }
                }

            }
        });
        // 长度大于4的时候，超出4的部分用other填充
        if (list.size() > 4) {

            if (list.get(0).get("value") instanceof BigDecimal) {
                BigDecimal surplus = new BigDecimal(0);
                for (int i = list.size() - 1; i >= 4; i--) {
                    surplus = surplus.add((BigDecimal) list.get(i).get("value"));
                    list.remove(i);
                }
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("name", "other");
                tempMap.put("value", surplus);
                list.add(tempMap);
            } else {
                long surplus = 0;
                for (int i = list.size() - 1; i >= 4; i--) {
                    surplus += (long) list.get(i).get("value");
                    list.remove(i);
                }
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("name", "other");
                tempMap.put("value", surplus);
                list.add(tempMap);
            }
        }
    }

    /**
     * 
     * @Title: fillSurplusByOther
     * @Description: 用其它填充剩余部分
     * @param list
     * @param sum
     */
    private void fillSurplusByOther(List<Map<String, Object>> list, Object sum) {
        if (sum instanceof BigDecimal) {
            BigDecimal surplus = (BigDecimal) sum;
            for (int i = 0; i < list.size(); i++) {
                surplus = surplus.subtract((BigDecimal) list.get(i).get("value"));
            }
            if (surplus.compareTo(BigDecimal.ZERO) > 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", "other");
                map.put("value", surplus);
                list.add(map);
            }
        } else if (sum instanceof Integer) {
            int surplus = (int) sum;
            for (int i = 0; i < list.size(); i++) {
                surplus -= (long) list.get(i).get("value");
            }
            if (surplus > 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", "other");
                map.put("value", surplus);
                list.add(map);
            }
        } else if (sum instanceof Long) {
            long surplus = (long) sum;
            for (int i = 0; i < list.size(); i++) {
                surplus -= (long) list.get(i).get("value");
            }
            if (surplus > 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", "other");
                map.put("value", surplus);
                list.add(map);
            }
        }
    }

    /**
     * 
     * @Title: getUserHobbyByCategory
     * @Description: 根据类别取对应商铺分布情况,selectType 0,1,2,3对应四种情况
     * @param userId
     * @param categoryName
     * @param selectType
     * @return
     */
    @RequestMapping(value = "/getUserHobbyByCategory", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserHobbyByCategory(@RequestParam("userId") String userId,
            @RequestParam("categoryName") String categoryName, @RequestParam("selectType") int selectType) {
        LOG.info("CostController:getUserHobbyByCategory");

        try {
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_200);

            switch (selectType) {
            case 0:
                List<Map<String, Object>> monthSumByShop = dao.getUserCostSumLastMonthByShop(userId, categoryName);
                transformToResponse(monthSumByShop);
                modelMap.put("data", monthSumByShop);
                break;
            case 1:
                List<Map<String, Object>> monthCountByShop = dao.getUserCostCountLastMonthByShop(userId, categoryName);
                transformToResponse(monthCountByShop);
                modelMap.put("data", monthCountByShop);
                break;
            case 2:
                List<Map<String, Object>> yearSumByShop = dao.getUserCostSumLastYearByShop(userId, categoryName);
                transformToResponse(yearSumByShop);
                modelMap.put("data", yearSumByShop);
                break;
            case 3:
                List<Map<String, Object>> yearCountByShop = dao.getUserCostCountLastYearByShop(userId, categoryName);
                transformToResponse(yearCountByShop);
                modelMap.put("data", yearCountByShop);
                break;
            default:
                break;
            }

            return modelMap;
        } catch (Exception e) {
            // 异常，可能是没数据等
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_500);
            return modelMap;
        }

    }

    /**
     * 
     * @Title: getUserStayByCategory
     * @Description: 根据类别取对应商铺分布情况,selectType 0,1,2,3对应四种情况
     * @param userId
     * @param categoryName
     * @param selectType
     * @return
     */
    @RequestMapping(value = "/getUserStayByCategory", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserStayByCategory(@RequestParam("userId") String userId,
            @RequestParam("categoryName") String categoryName, @RequestParam("selectType") int selectType) {
        LOG.info("CostController:getUserStayByCategory");

        try {
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_200);
            if (selectType == 0 || selectType == 1) {
                // 判断要查的表
                String[] queryMonths;
                String nowDate = Util.dateFormat(new Date(), Params.YYYYMMDD2); // 当天日期
                String[] str = nowDate.split("-");
                if ("03".equals(str[1]) && "02".equals(str[2])) {
                    queryMonths = new String[3];
                    String[] str1 = Util.getLastNumMonths(2, Params.YYYYMM);
                    queryMonths[0] = str1[0];
                    queryMonths[1] = str1[1];
                    queryMonths[2] = str[0] + str[1];
                } else if ("01".equals(str[2])) {
                    queryMonths = Util.getLastNumMonths(2, Params.YYYYMM);
                } else {
                    queryMonths = new String[2];
                    String[] str1 = Util.getLastNumMonths(1, Params.YYYYMM);
                    queryMonths[0] = str1[0];
                    queryMonths[1] = str[0] + str[1];
                }
                // 初始化要存放结果的list
                List<Map<String, Object>> monthStaySumByShop = new ArrayList<>(); // 店铺的驻留时间或次数
                for (int i = 0; i < queryMonths.length; i++) {
                    if (statisticsDao.isTableExist(Params.SHOPLOCATION + queryMonths[i], db) >= 1) {
                        List<Map<String, Object>> result = null;
                        if (selectType == 0) {
                            result = dao.getUserStaySumLastMonthByShop(Params.SHOPLOCATION + queryMonths[i], userId,
                                    categoryName);
                        } else if (selectType == 1) {
                            result = dao.getUserStayCountLastMonthByShop(Params.SHOPLOCATION + queryMonths[i], userId,
                                    categoryName);
                        }
                        if (result == null) {
                        } else {
                            for (Map<String, Object> map1 : result) {
                                boolean flag = false;
                                for (int j = 0; j < monthStaySumByShop.size(); j++) {
                                    Map<String, Object> map2 = monthStaySumByShop.get(j);
                                    if (map2.get("name").equals(map1.get("name"))) {
                                        if (selectType == 0) {
                                            map2.put("value", ((BigDecimal) map2.get("value"))
                                                    .add((BigDecimal) map1.get("timeDelay")));
                                        } else if (selectType == 1) {
                                            map2.put("value", (long) map2.get("value") + (long) map1.get("countDelay"));
                                        }
                                        flag = true;
                                        break;
                                    }
                                }
                                if (!flag) {
                                    Map<String, Object> tempMap1 = new HashMap<>();
                                    tempMap1.put("name", (String) map1.get("name"));
                                    tempMap1.put("value", map1.get("timeDelay"));
                                    if (selectType == 0) {
                                        tempMap1.put("value", map1.get("timeDelay"));
                                    } else if (selectType == 1) {
                                        tempMap1.put("value", map1.get("countDelay"));
                                    }
                                    monthStaySumByShop.add(tempMap1);
                                }
                            }

                        }
                    } else {
                    }
                }
                transformToResponse(monthStaySumByShop);
                modelMap.put("data", monthStaySumByShop);
            } else if (selectType == 2 || selectType == 3) {
                // 过去12月的一定格式日期数组
                String[] months = Util.getLastNumMonths(12, Params.YYYYMMM);
                // 初始化要存放结果的list
                List<Map<String, Object>> yearStaySumByShop = new ArrayList<>(); // 店铺的驻留时间
                for (int i = 0; i < 12; i++) {
                    if (statisticsDao.isTableExist(Params.SHOPLOCATION + months[i].replace("-", ""), db) >= 1) {
                        List<Map<String, Object>> result = null;
                        if (selectType == 2) {
                            result = dao.getUserStaySumLastYearByShop(Params.SHOPLOCATION + months[i].replace("-", ""),
                                    userId, categoryName);
                        } else if (selectType == 3) {
                            result = dao.getUserStayCountLastYearByShop(
                                    Params.SHOPLOCATION + months[i].replace("-", ""), userId, categoryName);
                        }
                        if (result == null) {
                        } else {
                            for (Map<String, Object> map1 : result) {
                                boolean flag = false;
                                for (int j = 0; j < yearStaySumByShop.size(); j++) {
                                    Map<String, Object> map2 = yearStaySumByShop.get(j);
                                    if (map2.get("name").equals(map1.get("name"))) {
                                        if (selectType == 2) {
                                            map2.put("value", ((BigDecimal) map2.get("value"))
                                                    .add((BigDecimal) map1.get("timeDelay")));
                                        } else if (selectType == 3) {
                                            map2.put("value", (long) map2.get("value") + (long) map1.get("countDelay"));
                                        }

                                        flag = true;
                                        break;
                                    }
                                }
                                if (!flag) {
                                    Map<String, Object> tempMap1 = new HashMap<>();
                                    tempMap1.put("name", (String) map1.get("name"));
                                   
                                    if (selectType == 2) {
                                        tempMap1.put("value", map1.get("timeDelay"));
                                    } else if (selectType == 3) {
                                        tempMap1.put("value", map1.get("countDelay"));
                                    }
                                    yearStaySumByShop.add(tempMap1);
                                }
                            }

                        }
                    } else {
                    }
                }
                transformToResponse(yearStaySumByShop);
                modelMap.put("data", yearStaySumByShop);
            }

            return modelMap;
        } catch (Exception e) {
            // 异常，可能是没数据等
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("status", Params.RETURN_CODE_500);
            return modelMap;
        }

    }
}
