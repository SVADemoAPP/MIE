package com.bis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bis.model.ParkinginformationModel;

/** 
 * @ClassName: ParkingDao 
 * @Description: 找车位dao
 * @author JunWang 
 * @date 2016年11月10日 下午3:28:08 
 *  
 */
public interface ParkingDao
{

    /** 
     * @Title: saveParking 
     * @Description: 保存车位号
     * @param userName
     * @param parkingNumber
     * @return 
     */
//    public int saveParking(@Param("time")long time,@Param("x")double x,@RequestParam("y") double y,@Param("parkingNumber")int parkingNumber,@Param("floorNo")int floorNo,@RequestParam("userName") String userName);


    public int saveParking(ParkinginformationModel model1);
    /** 
     * @Title: getParking 
     * @Description: 查询车位号
     * @param userName
     * @return 
     */
    public List<ParkinginformationModel> getParking(@Param("userName")String userName);


 
    /** 
     * @Title: clearParkingNumber 
     * @Description: 取车成功接口
     * @param userName
     * @param parkingNumber
     * @return 
     */
    public int clearParkingNumber(ParkinginformationModel model1);
    
    
    public int clearParkingNumber1(ParkinginformationModel model1);
}
