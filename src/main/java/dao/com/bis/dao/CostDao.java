package com.bis.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: CostDao
 * @Description: 消费统计dao
 * @author gyr
 * @date 2017年6月20日 下午2:33:16
 * 
 */
public interface CostDao {

    /**
     * 
     * @Title: getUserCostSumNowDay
     * @Description: 日--个人消费总额
     * @param userId
     * @return
     */
    public BigDecimal getUserCostSumNowDay(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostCountNowDay
     * @Description: 日--个人消费总次数
     * @param userId
     * @return
     */
    public Integer getUserCostCountNowDay(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostSumNowDayByCategory
     * @Description: 日--个人消费额--按类别
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostSumNowDayByCategory(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostCountNowDayByCategory
     * @Description: 日--个人消费次数--按类别
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostCountNowDayByCategory(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostSumNowDayByShop
     * @Description: 日--个人消费额--按店铺
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostSumNowDayByShop(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostCountNowDayByShop
     * @Description: 日--个人消费次数--按店铺
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostCountNowDayByShop(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostSumLastMonth
     * @Description: 30天--个人消费总额
     * @param userId
     * @return
     */
    public BigDecimal getUserCostSumLastMonth(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostCountLastMonth
     * @Description: 30天--个人消费总次数
     * @param userId
     * @return
     */
    public Integer getUserCostCountLastMonth(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostSumLastMonthByCategory
     * @Description: 30天--个人消费额--按类别
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostSumLastMonthByCategory(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostCountLastMonthByCategory
     * @Description: 30天--个人消费次数--按类别
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostCountLastMonthByCategory(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostSumLastMonthByShop
     * @Description: 30天--个人消费额--类别下按店铺
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostSumLastMonthByShop(@Param("userId") String userId,
            @Param("category") String category);

    /**
     * 
     * @Title: getUserCostCountLastMonthByShop
     * @Description: 30天--个人消费次数--类别下按店铺
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostCountLastMonthByShop(@Param("userId") String userId,
            @Param("category") String category);

    /**
     * 
     * @Title: getUserCostSumLastMonthEveryDay
     * @Description: 30天--个人消费额--每天
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostSumLastMonthEveryDay(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostCountLastMonthEveryDay
     * @Description: 30天--个人消费次数--每天
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostCountLastMonthEveryDay(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostSumLastYear
     * @Description: 12月--个人消费总额
     * @param userId
     * @return
     */
    public BigDecimal getUserCostSumLastYear(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostCountLastYear
     * @Description: 12月--个人消费总次数
     * @param userId
     * @return
     */
    public Integer getUserCostCountLastYear(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostSumLastYearByCategory
     * @Description: 12月--个人消费额--按类别
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostSumLastYearByCategory(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostCountLastYearByCategory
     * @Description: 12月--个人消费次数--按类别
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostCountLastYearByCategory(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostSumLastYearByShop
     * @Description: 12月--个人消费额--类别下按店铺
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostSumLastYearByShop(@Param("userId") String userId,
            @Param("category") String category);

    /**
     * 
     * @Title: getUserCostCountLastYearByShop
     * @Description: 12月--个人消费次数--类别下按店铺
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostCountLastYearByShop(@Param("userId") String userId,
            @Param("category") String category);

    /**
     * 
     * @Title: getUserCostSumLastYearEveryDay
     * @Description: 12月--个人消费额--每月
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostSumLastYearEveryMonth(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserCostCountLastYearEveryDay
     * @Description: 12月--个人消费次数--每月
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCostCountLastYearEveryMonth(@Param("userId") String userId);

    /**
     * 
     * @Title: getUserLocationCountNowDay
     * @Description: 日--定位条数
     * @param tableName
     * @param userId
     * @return
     */
    public Integer getUserLocationCountNowDay(@Param("tableName") String tableName, @Param("userId") String userId);

    /**
     * 
     * @Title: getUserDelayByDate
     * @Description: 30天--日期--驻留时长
     * @param tableName
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserDelayByDate(@Param("tableName") String tableName,
            @Param("userId") String userId);

    /**
     * 
     * @Title: getUserDelayByYear
     * @Description: 年--驻留时长和次数--每次查一个月的表,共12次
     * @param tableName
     * @param userId
     * @return
     */
    public Map<String, Object> getUserDelayByYear(@Param("tableName") String tableName, @Param("userId") String userId);

    /**
     * 
     * @Title: getUserYearDelayByCategory
     * @Description: 年--按类别统计驻留时长和次数--每次查一个月的表,共12次
     * @param tableName
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserYearDelayByCategory(@Param("tableName") String tableName,
            @Param("userId") String userId);
    
    /**
     * 
     * @Title: getUserCostSumLastYearByShop
     * @Description: 12月--个人驻留分钟--类别下按店铺
     * @param tableName
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserStaySumLastYearByShop(@Param("tableName") String tableName,@Param("userId") String userId,
            @Param("category") String category);

    /**
     * 
     * @Title: getUserCostCountLastYearByShop
     * @Description: 12月--个人驻留次数--类别下按店铺
     * @param tableName
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserStayCountLastYearByShop(@Param("tableName") String tableName,@Param("userId") String userId,
            @Param("category") String category);
    
    /**
     * 
     * @Title: getUserMonthDelayByCategory
     * @Description: 30天--按类别统计驻留时长和次数--每次查一个月的表,共12次
     * @param tableName
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserMonthDelayByCategory(@Param("tableName") String tableName,
            @Param("userId") String userId);
    
    /**
     * 
     * @Title: getUserCostSumLastMonthByShop
     * @Description: 30天--个人驻留分钟--类别下按店铺
     * @param tableName
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserStaySumLastMonthByShop(@Param("tableName") String tableName,@Param("userId") String userId,
            @Param("category") String category);

    /**
     * 
     * @Title: getUserCostCountLastMonthByShop
     * @Description: 30天--个人驻留次数--类别下按店铺
     * @param tableName
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserStayCountLastMonthByShop(@Param("tableName") String tableName,@Param("userId") String userId,
            @Param("category") String category);
    
    

}
