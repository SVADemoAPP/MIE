package com.bis.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.MixingDao;
import com.bis.model.MixingModel;
import com.bis.model.TicketModel;

/**
 * @ClassName: TicketController
 * @Description: 摇一摇
 * @author JunWang
 * @date 2017年7月10日 下午3:29:36
 * 
 */
@Controller
@RequestMapping(value = "/mixing")
public class MixingController {

    @Autowired
    private MixingDao dao;
    /**
     * @Fields LOG : 日志句柄
     */
//    private static final Logger LOG = Logger.getLogger(MapController.class);

    /** 
     * @Title: upload 
     * @Description: 保存奖券 
     * @param file  文件
     * @param request
     * @param ticketModel 奖券model
     * @return 
     */
    @RequestMapping(value = "/api/saveData", method = { RequestMethod.POST })
    public String upload(HttpServletRequest request, MixingModel model) {
        long times = System.currentTimeMillis();
        String creatTime = Util.dateFormat(times, Params.YYYYMMDDHHMMSS);
        model.setCreatTime(creatTime);
        String codeToMapId = model.getStoreCode()+"-"+model.getFloorCode();
        model.setCodeToMapId(codeToMapId);
        // 编辑的场合
        if (StringUtils.isNotEmpty(model.getId())) {
            dao.updateData(model);
        }
        // 添加的场合
        else {
            dao.saveData(model);
        }

        return "redirect:/home/mixingMng";
    }
    
    /** 
     * @Title: getData 
     * @Description: 获取表格数据
     * @return 
     */
    @RequestMapping(value = "/api/getData",method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getData()
    {
        Map<String, Object> map = new HashMap<>();
        List<MixingModel> list = dao.getData();
        map.put("data", list);
        map.put("error", false);

        return map;
        
    }
    
    /** 
     * @Title: deleteData 
     * @Description:删除数据
     * @param id 奖券id
     * @return 
     */
    @RequestMapping(value = "/api/deleteData",method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> deleteData(@RequestParam("id") int id)
    {
        Map<String, Object> map = new HashMap<>();
        int reslut = dao.deleteDataById(id);
        map.put("reslut", reslut);

        return map;
        
    }
}
