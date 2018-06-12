package com.bis.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @ClassName: SkipController
 * @Description: 页面跳转controller
 * @author gyr
 * @date 2017年6月20日 上午10:47:26
 *
 */
@Controller
@RequestMapping(value = "")
public class SkipController {

    private static final Logger LOG = Logger.getLogger(SkipController.class);

    @RequestMapping(value = "/user", method = { RequestMethod.GET })
    public ModelAndView showUser() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/user");

        return mv;
    }
}
