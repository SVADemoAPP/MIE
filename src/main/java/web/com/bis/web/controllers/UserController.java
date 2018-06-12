package com.bis.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.common.conf.Params;
import com.bis.dao.UserDao;
import com.bis.model.UserModel;

/**
 * 
 * @ClassName: UserController
 * @Description: 用户信息controller
 * @author gyr
 * @date 2017年6月20日 上午10:46:01
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserDao dao;

    private static final Logger LOG = Logger.getLogger(AccountController.class);

    /**
     * 
     * @Title: getUserByUserName
     * @Description: 根据用户名查询信息
     * @param request
     * @param userName
     * @return
     */
    @RequestMapping(value = "/getUserByUserName", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserByUserName(HttpServletRequest request,
            @RequestParam("userName") String userName) {
        LOG.info("UserController:getUserByUserName");

        UserModel userModel = dao.doquery(userName);
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("status", Params.RETURN_CODE_200);
        modelMap.put("data", userModel);
        if (userModel != null) {
            request.getSession().setAttribute("searchUserName", userName);
        }
        return modelMap;
    }

    /**
     * 
     * @Title: getUserNameByKey
     * @Description: 根据关键字模糊查询用户名
     * @param key
     * @return
     */
    @RequestMapping(value = "/getUserNameByKey", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserNameByKey(@RequestParam("key") String key) {
        LOG.info("UserController:getUserNameByKey");

        List<String> userNameList = dao.likequery(key);
        Map<String, Object> modelMap = new HashMap<String, Object>();

        modelMap.put("status", Params.RETURN_CODE_200);
        modelMap.put("data", userNameList);

        return modelMap;
    }
}
