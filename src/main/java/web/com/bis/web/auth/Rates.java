package com.bis.web.auth;

import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.RateDao;
import com.bis.dao.StatisticsDao;
import com.bis.model.ShopModel;

@Service
public class Rates {
	@Autowired
	private RateDao rateDao;
	
	@Value("${mysql.db}")
	private String db;

    @Autowired
    private StatisticsDao statisticsDao;
	
	//计算进店率,参数为shopId
	public int getEnter(ShopModel model){
		
		Calendar calendar = Calendar.getInstance();
	    // 获得前一天的日期
	    calendar.add(Calendar.DATE,-1);
//		calendar.getTimeInMillis()
	    String nowDay = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD);
	    String tableName = Params.LOCATION + nowDay;
	    try {
			if(statisticsDao.isTableExist(tableName, this.db) < 1) {
//				System.out.println("表不存在："+tableName);
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	    //统计进入店内的游客
//	    double visitorIn = rateDao.selectCountByShop(tableName, id);
//	    Integer x = rateDao.selectXByCategoryId(id);
//	    Integer y = rateDao.selectYByCategoryId(id);
	    int shopCount  = rateDao.getShopCountByShopId(tableName, model);
	    double x = Double.valueOf(model.getX())/2;
	    double y = Double.valueOf(model.getY())/2;
	    model.setxSpot(String.valueOf(Double.valueOf(model.getxSpot())-x));
	    model.setX1Spot(String.valueOf(Double.valueOf(model.getX1Spot())+x));
	    model.setySpot(String.valueOf(Double.valueOf(model.getySpot())-y));
	    model.setY1Spot(String.valueOf(Double.valueOf(model.getY1Spot())+y));
	    int allCount = rateDao.getShopCountByShopId(tableName, model);
	    //统计经过店门的游客
//	    double visitorAll = rateDao.selectAllCountByShop(tableName,id,x,y);
	    double enterRateDouble = 0;
	    int enterRate;
	    if(x==0||y==0||allCount==0){
//	    	 System.out.println("xxx");
	    	 return 0;
//	    }else if(visitorAll==0){
////	    	System.out.println("visitorAll=0");
//	    	return 0;
	    }else{
	    	//进店率
		    BigDecimal b = new BigDecimal(shopCount / allCount);  
		    enterRateDouble = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
		    enterRate = (int)enterRateDouble;
		    
	    }
//	    System.out.println(visitorIn);
//	    System.out.println(visitorAll);
//	    System.out.println("进店率："+enterRate);
	    return enterRate;
	}
	//参数为shopId,向前推的天数
	public int getEnter(int id,int i){
		
		Calendar calendar = Calendar.getInstance();
	    // 获得前一天的日期
	    calendar.add(Calendar.DATE,-i);
//		calendar.getTimeInMillis()
	    String nowDay = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD);
	    String tableName = Params.LOCATION + nowDay;
	    try {
			if(statisticsDao.isTableExist(tableName, this.db) < 1) {
//				System.out.println("表不存在："+tableName);
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	    //统计进入店内的游客
	    double visitorIn = rateDao.selectCountByShop(tableName, id);
	    Integer x = rateDao.selectXByCategoryId(id);
	    Integer y = rateDao.selectYByCategoryId(id);
	    //统计经过店门的游客
	    double visitorAll = rateDao.selectAllCountByShop(tableName,id,x,y);
	    double enterRateDouble = 0;
	    int enterRate;
	    if(x==null||y==null){
//	    	 System.out.println("xxx");
	    	 return 0;
	    }else if(visitorAll==0){
//	    	System.out.println("visitorAll=0");
	    	return 0;
	    }else{
	    	//进店率
		    BigDecimal b = new BigDecimal(visitorIn / visitorAll);  
		    enterRateDouble = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
		    enterRate = (int)enterRateDouble;
		    
	    }
//	    System.out.println(visitorIn);
//	    System.out.println(visitorAll);
//	    System.out.println("进店率："+enterRate);
	    return enterRate;
	}
	
	
	
	//计算溢出率,参数为shopId
	public int getOverflow(int id){
		Calendar calendar = Calendar.getInstance();
	    // 获得前一天的日期
	    calendar.add(Calendar.DATE,-1);
//		calendar.getTimeInMillis()
	    String nowDay = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD);
	    String tableName = Params.LOCATION + nowDay;
	    try {
			if(statisticsDao.isTableExist(tableName, this.db) < 1) {
				System.out.println("表不存在："+tableName);
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
//	    System.out.println("表名："+tableName);
	    //进店的人
	    double visitorIn = rateDao.selectCountByShop(tableName,id);
	    //溢出率的基础值
	    double visitorNumber = rateDao.selectVisitorById(id);
	    int overflowRate ;
	    if(visitorIn<=visitorNumber||visitorNumber == 0){
//	    	System.out.println("没有溢出");
	    	return 0;
	    }else{
	    	BigDecimal b = new BigDecimal((visitorIn - visitorNumber)/visitorNumber);
	    	double r = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
	    	overflowRate = (int) r; 
	    }
//	    System.out.println("溢出率："+overflowRate);
	    return overflowRate;
	}
	//参数为shopId,向前推的天数
	public int getOverflow(int id,int i){
		Calendar calendar = Calendar.getInstance();
	    // 获得前一天的日期
	    calendar.add(Calendar.DATE,-i);
//		calendar.getTimeInMillis()
	    String nowDay = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD);
	    String tableName = Params.LOCATION + nowDay;
//	    System.out.println("表名："+tableName);
	    try {
			if(statisticsDao.isTableExist(tableName, this.db) < 1) {
//				System.out.println("表不存在"+tableName);
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	    //进店的人
	    double visitorIn = rateDao.selectCountByShop(tableName,id);
	    //溢出率的基础值
	    double visitorNumber = rateDao.selectVisitorById(id);
	    int overflowRate ;
	    if(visitorIn<=visitorNumber||visitorNumber == 0){
//	    	System.out.println("没有溢出");
	    	return 0;
	    }else{
	    	BigDecimal b = new BigDecimal((visitorIn - visitorNumber)/visitorNumber);
	    	double r = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
	    	overflowRate = (int) r; 
	    }
//	    System.out.println("溢出率："+overflowRate);
	    return overflowRate;
	}
	
	   //计算溢出率,参数为shopId
    public int getOverflow1(int id){
        Calendar calendar = Calendar.getInstance();
        // 获得前一天的日期
        calendar.add(Calendar.DATE,-1);
//      calendar.getTimeInMillis()
        String nowMouths = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMM);
        String nowDay = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD2);
        String tableName = Params.SHOPLOCATION + nowMouths;
        try {
            if(statisticsDao.isTableExist(tableName, this.db) < 1) {
//              System.out.println("表不存在："+tableName);
                return 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        //进店时间超过基本时间的人数
        double visitorDeep = rateDao.selectCountDelayTime1(tableName, id,nowDay);
        //进店的总人数
        double visitorIn = rateDao.selectCount(tableName, id,nowDay);
        int deepRate;
        if(visitorIn==0){
//          System.out.println("深访率：进店人数为零");
            return 0;
        }else{
            BigDecimal b = new BigDecimal(visitorDeep / visitorIn);
            double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
            deepRate = (int) d;
        }
//      System.out.println("深访率："+deepRate);
        return deepRate;
    }

	//计算深访率,参数为shopId
	public int getDeep(int id){
		Calendar calendar = Calendar.getInstance();
	    // 获得前一天的日期
	    calendar.add(Calendar.DATE,-1);
//		calendar.getTimeInMillis()
	    String nowMouths = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMM);
	    String nowDay = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD2);
	    String tableName = Params.SHOPLOCATION + nowMouths;
	    try {
			if(statisticsDao.isTableExist(tableName, this.db) < 1) {
//				System.out.println("表不存在："+tableName);
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	    //进店时间超过基本时间的人数
	    double visitorDeep = rateDao.selectCountDelayTime(tableName, id,nowDay);
	    //进店的总人数
	    double visitorIn = rateDao.selectCount(tableName, id,nowDay);
	    int deepRate;
	    if(visitorIn==0){
//	    	System.out.println("深访率：进店人数为零");
	    	return 0;
	    }else{
	    	BigDecimal b = new BigDecimal(visitorDeep / visitorIn);
		    double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
		    deepRate = (int) d;
	    }
//	    System.out.println("深访率："+deepRate);
		return deepRate;
	}
	
	//参数为shopId,向前推的天数
	public int getDeep(int id,int i){
		Calendar calendar = Calendar.getInstance();
	    // 获得前一天的日期
	    calendar.add(Calendar.DATE,-i);
//		calendar.getTimeInMillis()
	    String nowMouths = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMM);
	    String nowDay = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD2);
	    String tableName = Params.SHOPLOCATION + nowMouths;
	    try {
			if(statisticsDao.isTableExist(tableName, this.db) < 1) {
//				System.out.println("表不存在"+tableName);
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	    //进店时间超过基本时间的人数
	    double visitorDeep = rateDao.selectCountDelayTime(tableName, id,nowDay);
	    //进店的总人数
	    double visitorIn = rateDao.selectCount(tableName, id,nowDay);
	    int deepRate;
	    if(visitorIn==0){
//	    	System.out.println("深访率：进店人数为零");
	    	return 0;
	    }else{
	    	BigDecimal b = new BigDecimal(visitorDeep / visitorIn);
		    double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
		    deepRate = (int) d;
	    }
//	    System.out.println("深访率："+deepRate);
		return deepRate;
	}
	
	   //参数为shopId,向前推的天数
    public int getOverflow1(int id,int i){
        Calendar calendar = Calendar.getInstance();
        // 获得前一天的日期
        calendar.add(Calendar.DATE,-i);
//      calendar.getTimeInMillis()
        String nowMouths = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMM);
        String nowDay = Util.dateFormat(calendar.getTimeInMillis(), Params.YYYYMMDD2);
        String tableName = Params.SHOPLOCATION + nowMouths;
        try {
            if(statisticsDao.isTableExist(tableName, this.db) < 1) {
//              System.out.println("表不存在"+tableName);
                return 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        //进店时间超过基本时间的人数
        double visitorDeep = rateDao.selectCountDelayTime1(tableName, id,nowDay);
        //进店的总人数
        double visitorIn = rateDao.selectCount(tableName, id,nowDay);
        int deepRate;
        if(visitorIn==0){
//          System.out.println("深访率：进店人数为零");
            return 0;
        }else{
            BigDecimal b = new BigDecimal(visitorDeep / visitorIn);
            double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
            deepRate = (int) d;
        }
//      System.out.println("深访率："+deepRate);
        return deepRate;
    }
	
}
