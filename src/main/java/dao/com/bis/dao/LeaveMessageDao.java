/**   
 * @Title: CategoryDao.java 
 * @Package com.sva.dao 
 * @Description: CategoryDao接口类 
 * @author labelCS   
 * @date 2017年6月28日 下午3:20:04 
 * @version V1.0   
 */
package com.bis.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bis.model.LeaveMessageModel;
import com.bis.model.ShopModel;

/** 
 * @ClassName: LeaveMessageDao 
 * @Description: 留言model 
 * @author JunWang 
 * @date 2017年7月13日 下午5:03:25 
 *  
 */
public interface LeaveMessageDao {

    /** 
     * @Title: saveInfo 
     * @Description: 保存留言数据
     * @param sm
     * @return 
     */
    public int saveInfo(LeaveMessageModel sm);

    /** 
     * @Title: saveFootprints 
     * @Description: 保存足迹数据 
     * @param sm
     * @return 
     */
    public int saveFootprints(LeaveMessageModel sm);
    
    public String getFootCountByShopId(@Param("shopId")String shopId);
    
    public List<LeaveMessageModel> getLeaveMessage(@Param("mapId")String mapId);
    
    public List<LeaveMessageModel> getFootprints(@Param("mapId")String mapId);
    
    public List<ShopModel> getLeaveMessageAndFootprints(@Param("mapId")String mapId);
    
    
    public List<LeaveMessageModel> getFootprintsPathByShopId(@Param("shopId")String shopId);
}
