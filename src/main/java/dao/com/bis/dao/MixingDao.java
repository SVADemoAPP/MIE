package com.bis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bis.model.MixingModel;

/** 
 * @ClassName: TicketDao 
 * @Description: 摇一摇Dao
 * @author JunWang 
 * @date 2017年7月10日 下午3:30:35 
 *  
 */
public interface MixingDao {
    
    /** 
     * @Title: saveData 
     * @Description: 保存奖券dao
     * @param model
     * @return 
     */
    public int saveData(MixingModel model);
    
    /** 
     * @Title: updateData 
     * @Description: 修改奖券dao 
     * @param model
     * @return 
     */
    public int updateData(MixingModel model);
    
    /** 
     * @Title: getData 
     * @Description: 获取所有奖券dao 
     * @return 
     */
    public List<MixingModel> getData();
    
    /** 
     * @Title: deleteDataById 
     * @Description: 根据id删除奖券
     * @param id
     * @return 
     */
    public int deleteDataById(@Param("id") int id);
    
    
}
