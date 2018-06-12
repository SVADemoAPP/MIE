/**   
 * @Title: MapController.java 
 * @Package com.bis.web.controllers 
 * @Description: 地图controller
 * @author labelCS   
 * @date 2017年6月22日 下午4:26:02 
 * @version V1.0   
 */
package com.bis.web.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

import com.bis.common.conf.Params;
import com.bis.dao.MapMngDao;
import com.bis.model.MapMngModel;

/**
 * @ClassName: MapController
 * @Description: 地图controller
 * @author labelCS
 * @date 2017年6月22日 下午4:26:02
 * 
 */
@Controller
@RequestMapping(value = "/map")
public class MapController {
    /**
     * @Fields service : 用户店铺轨迹service
     */
    @Autowired
    private MapMngDao dao;

    /**
     * @Fields LOG : 日志句柄
     */
    private static final Logger LOG = Logger.getLogger(MapController.class);

    /**
     * @Title: getMapInfoOfFirstStore
     * @Description: 获取第一个商场的所有地图信息
     * @return
     */
    @RequestMapping(value = "/api/getMapInfoOfFirstStore", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMapInfoOfFirstStore() {
        LOG.debug("getMapInfoOfFirstStore in:null");
        List<MapMngModel> resultList = dao.doquery();
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", resultList);
        LOG.debug("getMapInfoOfFirstStore out:" + resultList.size());
        return modelMap;
    }
    
    @RequestMapping(value = "/api/getMapInfoOfFirstStores", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMapInfoOfFirstStores(@RequestParam("mapId") String mapId) {
        LOG.debug("getMapInfoOfFirstStore in:null");
        List<MapMngModel> resultList = dao.getMapDataByMapId(mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", resultList);
        LOG.debug("getMapInfoOfFirstStore out:" + resultList.size());
        return modelMap;
    }
    
    @RequestMapping(value = "/api/getMapInfoByMapId", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMapInfoByMapId(@RequestParam("mapId") String mapId) {
        LOG.debug("getMapInfoOfFirstStore in:null");
        List<MapMngModel> resultList = dao.doqueryss(mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", resultList);
        LOG.debug("getMapInfoOfFirstStore out:" + resultList.size());
        return modelMap;
    }

    /**
     * @Title: getTableData
     * @Description: 获取所有地图信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/getTableData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request) {
        LOG.debug("getTableData:in");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<MapMngModel> resultList = dao.doquery();
        modelMap.put("error", null);
        modelMap.put("data", resultList);
        LOG.debug("getTableData:out->data size:" + resultList.size());
        return modelMap;
    }

    /**
     * @Title: getAllTableData
     * @Description: 获取所有地图信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/getAllTableData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getAllTableData(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<MapMngModel> resultList = dao.doallquery();
        modelMap.put("error", null);
        modelMap.put("data", resultList);
        LOG.info("MapController ~ getAllTableData 取得所有楼层信息,size:"+resultList.size());
        return modelMap;
    }

    /**
     * 
     * @Title: getMapDataByStore
     * @Description: 根据storeId查询出对应商场下的地图信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/getMapDataByStore", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMapDataByStore(String id) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<MapMngModel> resultList = dao.queryMapByStore(id);
        modelMap.put("error", null);
        modelMap.put("data", resultList);
        LOG.info("MapController ~ getMapDataByStore 取得对应商场下楼层信息,size:"+resultList.size());
        return modelMap;
    }

    /**
     * 
     * @Title: deleteTableData
     * @Description: 删除地图
     * @param request
     * @param mapId
     * @return
     */
    @RequestMapping(value = "/api/deleteByFloor", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> deleteTableData(HttpServletRequest request, @RequestParam("mapId") String mapId) {
        LOG.info("MapController ~ deleteTableData 删除楼层,mapId:"+mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try {
            // 删除图片
            String name = dao.getMapNameByMapId(mapId);
            if (StringUtils.isNotEmpty(name)) {
                // 文件路径
                String filedir = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/map");
                File file = new File(filedir, name);
                if (!file.exists()) {
                    // 文件不存在
                    modelMap.put("error", false);
                } else if (file.isFile()) {
                    file.delete();
                } else {
                    modelMap.put("error", false);
                }
            }
            // 删除图片
            String svgName = dao.getSvgNameByMapId(mapId);
            if (StringUtils.isNotEmpty(svgName)) {
                // 文件路径
                String svgFiledir = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/svg");
                File svgFile = new File(svgFiledir, svgName);
                if (!svgFile.exists()) {
                    // 文件不存在
                    modelMap.put("error", false);
                } else if (svgFile.isFile()) {
                    svgFile.delete();
                } else {
                    modelMap.put("error", false);
                }
            }
            // 删除导航文件
            String naviName = dao.getNaviNameByMapId(mapId);
            if (StringUtils.isNotEmpty(naviName)) {
                // 文件路径
                String naviFiledir = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/navi");
                File naviFile = new File(naviFiledir, naviName);
                if (!naviFile.exists()) {
                    // 文件不存在
                    modelMap.put("error", false);
                } else if (naviFile.isFile()) {
                    naviFile.delete();
                } else {
                    modelMap.put("error", false);
                }
            }
            // 删除数据
            result = dao.deleteMapByMapId(mapId);
            if (result == 1) {
                modelMap.put("error", true);
            } else {
                modelMap.put("error", false);
            }
        } catch (Exception e) {
            LOG.error("MapController ~ deleteTableData error:"+e);
            modelMap.put("error", false);
        }
//        LOG.debug("deleteTableData:out->error :" + modelMap.get("error"));
        return modelMap;
    }

    /**
     * 
     * @Title: upload
     * @Description: 上传地图信息，修改或添加
     * @param file
     * @param svgFile
     * @param naviFile
     * @param request
     * @param mapMngModel
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/api/upload", method = { RequestMethod.POST })
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "svgFile", required = false) MultipartFile svgFile,
            @RequestParam(value = "naviFile", required = false) MultipartFile naviFile, HttpServletRequest request,
            MapMngModel mapMngModel) throws SQLException {
        LOG.info("MapController ~ upload 编辑楼层");
        
        mapMngModel.setUpdateTime(String.valueOf(System.currentTimeMillis()));
        String oldFileName = "";
        String oldSvgName = "";
        String oldNaviName = "";
        if (StringUtils.isNotEmpty(mapMngModel.getId())) {
            oldFileName = dao.getMapNameById(mapMngModel.getId());
            oldSvgName = dao.getSvgNameById(mapMngModel.getId());
            oldNaviName = dao.getNaviNameById(mapMngModel.getId());
        }
        boolean fileRename = true;
        boolean svgRename = true;
        boolean naviRename = true;
        String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/map");
        String svgPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/svg");
        String naviPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/navi");
        File fileAll = new File(filePath);
        if (!fileAll.exists()) {
            fileAll.mkdirs();
        }
        File svgAll = new File(svgPath);
        if (!svgAll.exists()) {
            svgAll.mkdirs();
        }
        File naviAll = new File(naviPath);
        if (!naviAll.exists()) {
            naviAll.mkdirs();
        }
        File oldFile = new File(filePath, oldFileName);
        File oldSvg = null;
        File oldNavi = null;
        if (StringUtils.isNotEmpty(oldSvgName)) {
            oldSvg = new File(svgPath, oldSvgName);
        }
        if (StringUtils.isNotEmpty(oldNaviName)) {
            oldNavi = new File(naviPath, oldNaviName);
        }
        // 地图添加
        String fileName = file.getOriginalFilename();
        if (StringUtils.isNotEmpty(fileName)) {

            String ext = fileName.substring(fileName.lastIndexOf('.'));
            fileName = mapMngModel.getMapId() + "_" + mapMngModel.getFloor() + ext;
            mapMngModel.setPath(fileName);
            File targetFile = new File(filePath, fileName);
            // 修改
            try {
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                }
                BufferedImage sourceImg = javax.imageio.ImageIO.read(file.getInputStream());
                file.transferTo(targetFile);

                mapMngModel.setImgWidth(sourceImg.getWidth());
                mapMngModel.setImgHeight(sourceImg.getHeight());
            } catch (Exception e) {
                LOG.error(e);
            }
            if (!oldFileName.equals(fileName)) {

                if (oldFile.exists()) {
                    oldFile.delete();
                }
                fileRename = false;
            }
        }

        // svg添加
        String svgName = svgFile.getOriginalFilename();
        if (StringUtils.isNotEmpty(svgName)) {

            String ext = svgName.substring(svgName.lastIndexOf('.'));
            svgName = mapMngModel.getMapId() + "_" + mapMngModel.getFloor() + ext;
            mapMngModel.setSvg(svgName);
            File targetFile = new File(svgPath, svgName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            // 修改
            try {
                svgFile.transferTo(targetFile);
            } catch (Exception e) {
                LOG.error(e);
            }
            if (StringUtils.isNotEmpty(oldSvgName) && !oldSvgName.equals(svgName)) {

                if (oldSvg.exists()) {
                    oldSvg.delete();
                }
                svgRename = false;
            }
        }
        // navi添加
        String naviName = naviFile.getOriginalFilename();
        if (StringUtils.isNotEmpty(naviName)) {

            String ext = naviName.substring(naviName.lastIndexOf('.'));
            naviName = mapMngModel.getMapId() + "_" + mapMngModel.getFloor() + ext;
            mapMngModel.setNavi(naviName);
            File targetFile = new File(naviPath, naviName);
            if (!targetFile.exists()) {
                targetFile.mkdirs(); 
            }
            // 修改
            try {
                naviFile.transferTo(targetFile);
            } catch (Exception e) {
                LOG.error(e);
            }
            if (StringUtils.isNotEmpty(oldNaviName) && !oldNaviName.equals(naviName)) {

                if (oldNavi.exists()) {
                    oldNavi.delete();
                }
                naviRename = false;
            }
        }

        // 编辑的场合
        if (StringUtils.isNotEmpty(mapMngModel.getId())) {
            if (fileRename) {
                // String newFileName = file.getOriginalFilename();
                // String ext =
                // newFileName.substring(newFileName.lastIndexOf('.'));
                String newFileName = mapMngModel.getMapId() + "_" + mapMngModel.getFloor()
                        + oldFileName.substring(oldFileName.lastIndexOf('.'));
                mapMngModel.setPath(newFileName);
                if (oldFile.exists()) {
                    oldFile.renameTo(new File(filePath, newFileName));
                }
            }
            ;
            if (StringUtils.isNotEmpty(oldSvgName) && svgRename) {
                // String newSvgName = svgFile.getOriginalFilename();
                // String ext =
                // newSvgName.substring(newSvgName.lastIndexOf('.'));
                String newSvgName = mapMngModel.getMapId() + "_" + mapMngModel.getFloor()
                        + oldSvgName.substring(oldSvgName.lastIndexOf('.'));
                mapMngModel.setSvg(newSvgName);
                if (oldSvg.exists()) {
                    oldSvg.renameTo(new File(svgPath, newSvgName));
                }
            }
            ;
            if (StringUtils.isNotEmpty(oldNaviName) && naviRename) {
                // String newSvgName = svgFile.getOriginalFilename();
                // String ext =
                // newSvgName.substring(newSvgName.lastIndexOf('.'));
                String newNaviName = mapMngModel.getMapId() + "_" + mapMngModel.getFloor()
                        + oldNaviName.substring(oldNaviName.lastIndexOf('.'));
                mapMngModel.setNavi(newNaviName);
                if (oldNavi.exists()) {
                    oldNavi.renameTo(new File(naviPath, newNaviName));
                }
            }
            dao.updateMap(mapMngModel);
            LOG.info("MapController ~ upload 修改楼层");
        }
        // 添加的场合
        else {
            mapMngModel.setCreateTime(new Date());
            dao.saveMapInfo(mapMngModel);
            LOG.info("MapController ~ upload 新建楼层");
        }
        return "redirect:/home/mapMng";
    }

    /**
     * @Title: getStoreIdByMapId
     * @Description: 根据mapId获取其对应的storeId
     * @param mapId
     * @return
     */
    @RequestMapping(value = "/api/getStoreIdByMapId", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getStoreIdByMapId(String mapId) {
        LOG.info("MapController ~ getStoreIdByMapId 根据mapId找到storeId");
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Integer storeId = dao.getStoreIdByMapId(mapId);
        modelMap.put("status", Params.RETURN_CODE_200);
        modelMap.put("data", storeId);
        return modelMap;
    }

    /**
     * @Title: check
     * @Description: 检查mapId是否重复
     * @param id
     * @param mapId
     * @return
     */
    @RequestMapping(value = "/api/check", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> check(@RequestParam(value = "id", required = false) String id,
            @RequestParam("mapId") String mapId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int i = dao.checkMapIdIsExisted(mapId, id);
        if (i > 0) {
            modelMap.put("data", false);
            LOG.debug("MapController ~ check 重复mapId:"+mapId);
        } else {
            modelMap.put("data", true);
        }
        return modelMap;
    }

    /**
     * 
     * @Title: getMapTrend 
     * @Description: 根据时刻或日期天查询楼层来源动向
     * @param type 0为时刻查找，1为日期天查找
     * @param mapId
     * @param sign 整点数或日期天数
     * @param time
     * @return
     */
    @RequestMapping(value = "/api/getMapTrend", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMapTrend(@RequestParam("type") int type, @RequestParam("mapId") String mapId,
            @RequestParam("sign") int sign, @RequestParam("time") String time) {
        LOG.info("MapController ~ getMapTrend 楼层关联统计,mapId:"+mapId);
        List<Map<String, Object>> resultList = new ArrayList<>();
        Integer visitorCount = null;
        if (type == 0) {
            resultList = dao.getMapTrendByHour(mapId, sign, time);
            visitorCount = dao.getMapTrendByHourOther(mapId, sign, time);
        } else if (type == 1) {
            resultList = dao.getMapTrendByDay(mapId, sign, time);
            visitorCount = dao.getMapTrendByDayOther(mapId, sign, time);
        }
        if (visitorCount != null) {
            Map<String, Object> otherMap = new HashMap<>();
            otherMap.put("name", "other");
            otherMap.put("value", visitorCount);
            resultList.add(otherMap);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("error", null);
        modelMap.put("data", resultList);
        return modelMap;
    }

  
}
