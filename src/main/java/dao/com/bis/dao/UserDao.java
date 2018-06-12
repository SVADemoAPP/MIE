package com.bis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bis.model.UserModel;

/**
 * 
 * @ClassName: UserDao
 * @Description: 用户dao
 * @author gyr
 * @date 2017年6月16日 上午10:09:12
 *
 */
public interface UserDao {

    /**
     * 
     * @Title: doquery
     * @Description: 通过用户名查找用户
     * @param userName
     * @return
     */
    public UserModel doquery(String userName);

    /**
     * 
     * @Title: likequery
     * @Description: 模糊查询用户名
     * @param key
     * @return
     */
    public List<String> likequery(String key);

    /**
     * 
     * @Title: register
     * @Description: 用户名和密码注册
     * @param userModel
     */
    public void register(UserModel userModel);

    /**
     * 
     * @Title: login
     * @Description: 登录
     * @param userModel
     * @param key
     * @return
     */
    public Integer login(@Param("userName") String userName, @Param("password") String password,
            @Param("columeName") String columeName);
    
    /**
     * 
     * @Title: loginByOpenid 
     * @Description: 通过openid登录
     * @param openid
     * @return
     */
    public Integer loginByOpenid(@Param("openid") String openid);

    /**
     * 
     * @Title: registerByOpenid 
     * @Description: 通过openid注册
     * @param openid
     * @return
     */
    public Integer registerByOpenid(UserModel userModel);
    
    /**
     * 
     * @Title: getUserInfo 
     * @Description: 获取用户信息
     * @param id
     * @return
     */
    public UserModel getUserInfo(int id);
    
    /**
     * 
     * @Title: modifyPassword 
     * @Description: 修改密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public int modifyPassword(@Param("id") int id,@Param("oldPassword") String oldPassword,@Param("newPassword") String newPassword);
    
    /**
     * 
     * @Title: modifyUserInfo 
     * @Description: 修改用户信息
     * @param id
     * @param userModel
     * @return
     */
    public int modifyUserInfo(UserModel userModel);
}
