package com.bis.web.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.TicketDao;
import com.bis.model.TicketModel;

/**
 * @ClassName: TicketController
 * @Description: 摇一摇
 * @author JunWang
 * @date 2017年7月10日 下午3:29:36
 * 
 */
@Controller
@RequestMapping(value = "/ticket")
public class TicketController {

    @Autowired
    private TicketDao dao;
    /**
     * @Fields LOG : 日志句柄
     */
    private static final Logger LOG = Logger.getLogger(MapController.class);

    /** 
     * @Title: upload 
     * @Description: 保存奖券 
     * @param file  文件
     * @param request
     * @param ticketModel 奖券model
     * @return 
     */
    @RequestMapping(value = "/api/saveData", method = { RequestMethod.POST })
    public String upload(@RequestParam(value = "tickeFile", required = false) MultipartFile file,
            HttpServletRequest request, TicketModel ticketModel) {
        long times = System.currentTimeMillis();
        String creatTime = Util.dateFormat(times, Params.YYYYMMDDHHMMSS);
        ticketModel.setCreatTime(creatTime);
        String fileName = null;
        if (file!=null) {
            fileName =  file.getOriginalFilename();
        }
        if (StringUtils.isNotEmpty(fileName)) {
            String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
            String ext = fileName.substring(fileName.lastIndexOf('.'));
            fileName = ticketModel.getFloorName() + "_" + times + ext;
            ticketModel.setTicketName(fileName);
            File targetFile = new File(path, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            // 修改
            try {
                BufferedImage sourceImg = javax.imageio.ImageIO.read(file.getInputStream());
                file.transferTo(targetFile);

                ticketModel.setImageWidth(sourceImg.getWidth());
                ticketModel.setImageHigth(sourceImg.getHeight());
            } catch (Exception e) {
                LOG.error(e);
            }
        }

        // 编辑的场合
        if (StringUtils.isNotEmpty(ticketModel.getId())) {
            dao.updateData(ticketModel);
        }
        // 添加的场合
        else {
            dao.saveData(ticketModel);
        }

        return "redirect:/home/ticketMng";
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
        List<TicketModel> list = dao.getData();

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
