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
import org.springframework.web.bind.annotation.RequestParam;

import com.bis.model.CategoryModel;
import com.bis.model.PeripheryCategoryModel;
import com.bis.model.ShopModel;

/** 
 * @ClassName: CategoryDao 
 * @Description: CategoryDao接口类 
 * @author labelCS 
 * @date 2017年6月28日 下午3:20:04 
 *  
 */
public interface CategoryDao {
    /** 
     * @Title: doquery 
     * @Description: 查询所有配置 
     * @return 
     */
    public List<CategoryModel> doquery();

    /** 
     * @Title: selectCategorySame 
     * @Description: 查询类别名称是否相同
     * @param name
     * @param id
     * @return 
     */
    public int selectCategorySame(@Param("name")String name, @Param("id")int id);
    
    
    /**
     * 
     * @Title: queryCategoryByStore 
     * @Description: 获取指定商场的类别信息 
     * @param id
     * @return
     */
    public List<CategoryModel> queryCategoryByStore(String id);
    
    /** 
     * @Title: getPeripheryCategoryDataByStoreId 
     * @Description: 获取周边服务数据
     * @param storeId
     * @return 
     */
    public List<PeripheryCategoryModel> getPeripheryCategoryDataByStoreId(@Param("storeId")int storeId);

    /** 
     * @Title: saveInfo 
     * @Description: 保存信息
     * @param sm 
     */
    public int saveInfo(CategoryModel sm);

    /** 
     * @Title: updateInfo 
     * @Description: 更新信息
     * @param sm 
     */
    public int updateInfo(CategoryModel sm);

    /** 
     * @Title: deleteById 
     * @Description: 删除信息 
     * @param id 
     */
    public int deleteById(@RequestParam("id") int id);
    
    /**
     * 
     * @Title: saveCategory 
     * @Description: 添加类别
     * @param categoryModel
     * @return
     */
    public void saveCategory(CategoryModel categoryModel);
    
    /**
     * 
     * @Title: updateCategory 
     * @Description: 修改类别信息
     * @param categoryModel
     * @return
     */
    public int updateCategory(CategoryModel categoryModel);
    
    /**
     * 
     * @Title: checkNameIsExisted 
     * @Description: 检验name是否已存在
     * @param name
     * @param id
     * @return
     */
    public int checkNameIsExisted(@Param("name") String name, @Param("id") String id);
    
    /** 
     * @Title: selectByName 
     * @Description: 通过name查找id 
     * @author 	ZhuYongchao  
     * @date 2017年7月13日 下午4:48:04 
     * @param name
     * @return 
     */
    public int selectByName(@Param("name") String name);
    public int selectByName1(@Param("name") String name,@Param("id") int id);
}
