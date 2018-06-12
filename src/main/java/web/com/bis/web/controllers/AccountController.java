package com.bis.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.StatisticsDao;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private static final Logger LOG = Logger.getLogger(AccountController.class);
    
    @Autowired
    private StatisticsDao statisticsDao;

    @RequestMapping(value = "/main", method = { RequestMethod.GET })
    public String main() {
        return "account/main";
    }
    
    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    public String login(HttpServletRequest request,RedirectAttributes redirectAttributes,@RequestParam("userName")String userName,@RequestParam("password")String password) {
        int result = statisticsDao.login(userName, password);
        if (result==1) {
            request.getSession().setAttribute("userName", userName);
            long loginTime = System.currentTimeMillis();
            try {
                statisticsDao.updateLogin(Util.dateFormat(loginTime, Params.YYYYMMDDHHMMSS));
            } catch (Exception e) {
                LOG.error("updateLogin error:"+e.getMessage());
            }
            return "redirect:/market/getMarketInfo";
        }else
        {
            redirectAttributes.addAttribute("info", "error");
            redirectAttributes.addAttribute("user",userName );
            return "redirect:/home/login";
        }
    }
    
    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public String logout(HttpServletRequest request)
    {
        request.getSession().setAttribute("userName", null);
        return "redirect:/home/login";
    }
}