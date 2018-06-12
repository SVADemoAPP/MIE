package com.bis.web.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.dao.CategoryDao;
import com.bis.model.CategoryModel;
import com.bis.model.ShopModel;
import com.bis.web.auth.Rates;

/**
 * @ClassName: CategoryController
 * @Description:
 * @author
 * @date 2017年6月16日 上午10:57:15
 * 
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    private CategoryDao dao;

    @Autowired
    private Rates rates;

    private static final Logger LOG = Logger.getLogger(CategoryController.class);
    
    /**
     * 
     * @Title: getShopDataByStore
     * @Description: 根据storeId和类别查询出对应商场下的类别信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCategoryDataByStore", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getCategoryDataByStore(@RequestParam("id")String id) {
        Map<String, Object> modelShop = new HashMap<String, Object>();
        List<CategoryModel> resultList=new ArrayList<>();
        resultList = dao.queryCategoryByStore(id);
        modelShop.put("error", null);
        modelShop.put("data", resultList);
        LOG.info("CategoryController ~ getCategoryDataByStore 取得对应商场下类别信息,size:"+resultList.size());
        return modelShop;
    }

    /**
     * @Title: saveCategory
     * @Description: 添加类别
     * @author ZhuYongchao
     * @date 2017年7月13日 下午4:55:31
     * @param name
     * @return
     */
    @RequestMapping(value = "/api/saveCategory", method = { RequestMethod.POST })
    public String saveCategory(CategoryModel categoryModel) {
//        Map<String, Object> map = new HashMap<String, Object>();
        Date date = new Date();
        try {
            if (StringUtils.isNotEmpty(categoryModel.getId())) {
                // 编辑的场合
                categoryModel.setUpdateTime(date);
                dao.updateCategory(categoryModel);
            } else {
                // 添加的场合
                categoryModel.setCreateTime(date);
                dao.saveCategory(categoryModel);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "redirect:/home/categoryMng";
    }

    /**
     *    
     * @Title: check
     * @Description: 检查name是否重复
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/api/check", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> check(@RequestParam(value = "id", required = false) String id,
            @RequestParam("categoryName") String name) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int i = dao.checkNameIsExisted(name, id);
        if (i > 0) {
            modelMap.put("data", false);
            LOG.debug("CategoryController ~ check 重复name:" + name);
        } else {
            modelMap.put("data", true);
        }
        return modelMap;
    }

    /**
     * @Title: doquery
     * @Description: 显示类别
     * @author ZhuYongchao
     * @date 2017年7月13日 下午4:55:56
     * @return
     */
    @RequestMapping(value = "/api/doquery", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> doquery() {
        Map<String, Object> map = new HashMap<>();
        List<CategoryModel> list = dao.doquery();
        map.put("data", list);
        return map;

    }

    /**
     * @Title: deleteData
     * @Description: 删除类别
     * @author ZhuYongchao
     * @date 2017年7月13日 下午4:56:33
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/deleteData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> deleteData(@RequestParam("id") int id) {
        Map<String, Object> map = new HashMap<>();
        int result = dao.deleteById(id);
        map.put("result", result);
        return map;
    }

}
