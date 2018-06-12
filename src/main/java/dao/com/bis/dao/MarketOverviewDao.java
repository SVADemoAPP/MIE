package com.bis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bis.model.MarketOverviewModel;
import com.bis.model.MarketShopSquareModel;
import com.bis.model.MarketShopTopModel;
import com.bis.model.ShopSalesUserInfoModel;
import com.bis.model.ShopTypeModel;
import com.bis.model.StringIntModel;
import com.bis.model.MarketMonthDataModel;
public interface MarketOverviewDao {
    public MarketOverviewModel doquery(@Param("tableName") String tableName);
    
    
    public List<ShopTypeModel> getShopType();
    
    //查询近30天的商场用户驻留时长情况，只返回有数据的天数，调用者需要保证前端显示的无数据日期正确性
    public List<MarketMonthDataModel> getMarketMonthdailyDelay();
    
    
  //查询近30天的商场用户访问商场数情况，只返回有数据的天数，调用者需要保证前端显示的无数据日期正确性
    public List<MarketMonthDataModel> getMarketMonthDailyVisitorCnt();
    
    public List<MarketShopTopModel> getMarketSalesInfoByType(@Param("typeSeq") List<String> typeSeq,@Param("timeDiffDay") int timeDiffDay);          
    
    public List<MarketShopTopModel> getMarketVisitorCntInfoByType(@Param("typeSeq") List<String> typeSeq,@Param("timeDiffDay") int timeDiffDay);        
    
    public List<MarketShopTopModel> getMarketDelayInfoByType(@Param("typeSeq") List<String> typeSeq,@Param("timeDiffDay") int timeDiffDay);        
    
    public List<MarketShopSquareModel> getMarketShopSquare();
    
    public List<StringIntModel> getMarketShopType();
    
    public List<ShopSalesUserInfoModel> getShopUserSalesInfoByShopName(@Param("shopName") String shopName,@Param("timeDiffDay") int timeDiffDay);
    
}
