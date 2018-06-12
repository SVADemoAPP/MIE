/**   
 * @Title: ApiPhoneController.java 
 * @Package com.sva.web.controllers 
 * @Description: app对接使用的Controller
 * @author labelCS   
 * @date 2016年9月29日 下午4:53:26 
 * @version V1.0   
 */
package com.bis.web.controllers;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bis.common.QuartzJob;
import com.bis.common.SubscriptionService;
import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.CategoryDao;
import com.bis.dao.LeaveMessageDao;
import com.bis.dao.LocationDao;
import com.bis.dao.MapMngDao;
import com.bis.dao.MarketDao;
import com.bis.dao.ParkingDao;
import com.bis.dao.ShopDao;
import com.bis.dao.TicketDao;
import com.bis.dao.UserDao;
import com.bis.model.LeaveMessageModel;
import com.bis.model.LocationModel;
import com.bis.model.MapMngModel;
import com.bis.model.MarketModel;
import com.bis.model.ParkinginformationModel;
import com.bis.model.PeripheryCategoryModel;
import com.bis.model.ShopModel;
import com.bis.model.SvaModel;
import com.bis.model.UserModel;
import com.bis.web.auth.AppPassport;
import com.bis.web.models.ApiRequestModel;

/**
 * @ClassName: ApiPhoneController
 * @Description: app对接使用的Controller
 * @author labelCS
 * @date 2016年9月29日 下午4:53:26
 * 
 */
@Controller
@RequestMapping(value = "/api/app")
public class ApiPhoneController {
    private static final Logger LOG = Logger.getLogger(ApiPhoneController.class);

    @Autowired
    private LocationDao dao;

    @Autowired
    private MarketDao marketDao;

    @Autowired
    private MapMngDao daoMaps;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private ParkingDao parkDao;

    @Autowired
    private LeaveMessageDao leaveMessageDao;

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private SubscriptionService service;

    @RequestMapping(value = "/getStoreData")
    @ResponseBody
    public Map<String, Object> getStoreData() {
        LOG.debug("getStoreData");
        List<String> resultList = marketDao.getStoreName();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        if (resultList.size() > 0) {
            modelMap.put("error", null);
            modelMap.put("data", resultList);
        } else {
            modelMap.put("error", "no store data");
            modelMap.put("data", null);
        }
        return modelMap;
    }

    @RequestMapping(value = "/getMapDataByStoreName", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getMapDataByStoreName(@RequestParam("name") String name) {
        LOG.debug("getMapDataByStoreName");
        List<MapMngModel> resultList = daoMaps.getMapDataByStoreName(name);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        if (resultList.size() > 0) {
            modelMap.put("error", null);
            modelMap.put("data", resultList);
        } else {
            modelMap.put("error", "no map data");
            modelMap.put("data", null);
        }
        return modelMap;
    }

    /**
     * @Title: getMapDataByIp
     * @Description: 获取地图信息
     * @return
     */
    @RequestMapping(value = "/getMapData")
    @ResponseBody
    public Map<String, Object> getMapDataByIp() {
        LOG.debug("getMapDataByIp");
        List<MapMngModel> resultList = daoMaps.doallquery();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        if (resultList.size() > 0) {
            modelMap.put("error", null);
            modelMap.put("data", resultList);
        } else {
            modelMap.put("error", "no map data");
            modelMap.put("data", null);
        }
        return modelMap;
    }

    /**
     * @Title: getData
     * @Description: 获取定位信息
     * @param requestModel
     * @return
     */
    @RequestMapping(value = "/getData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getData(@RequestBody ApiRequestModel requestModel) {

        LOG.debug("api getData.ip:" + requestModel.getIp());

        if (StringUtils.isEmpty(requestModel.getIp())) {
            return null;
        }
        String userId = Util.convertMacOrIp(requestModel.getIp());
        List<LocationModel> resultList = dao.queryLocationByUseId(userId);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        if (!resultList.isEmpty()) {
            LocationModel loc = resultList.get(0);
            modelMap.put("error", null);
            modelMap.put("data", loc);

        } else {
            LOG.debug("no data from server! userId:" + userId);
            modelMap.put("error", "no data from server! userId:" + userId);
            modelMap.put("data", null);
        }
        return modelMap;
    }

    @RequestMapping(value = "/passenjer", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getPassenjer(@RequestParam("mapId") String mapId) {

        LOG.debug("api passenjer.mapId:" + mapId);

        // 查询截至时间
        long time = System.currentTimeMillis() - 60 * 1000;
        // 表名
        long hourTime = time - 60 * 60 * 1000;
        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + nowDay;
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try {
            List<LocationModel> resultList = dao.getMapHeatMapById(time, tableName, mapId);
            int oneHourNumber = dao.getOneHourData(mapId, tableName, hourTime);
            int oneDayNumber = dao.getOneDayData(mapId, tableName);
            int allCount = dao.getAllCount(mapId, tableName);
            modelMap.put("heatMapData", resultList);
            modelMap.put("nowPeople", resultList.size());
            modelMap.put("oneHourPeople", oneHourNumber);
            modelMap.put("oneDayPeople", oneDayNumber);
            modelMap.put("averageTime", Util.getDouble(oneDayNumber, allCount));
            modelMap.put("error", null);

        } catch (Exception e) {
            LOG.debug("get passenjer error:" + e.getMessage());
            modelMap.put("error", e.getMessage());
        }
        return modelMap;
    }

    /**
     * @Title: getPeriphery
     * @Description: 获取周边服务数据
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/getPeriphery", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getPeriphery(@RequestBody MapMngModel model) {

        LOG.debug("api getPeriphery.storeId:" + model.getStoreId());
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<PeripheryCategoryModel> list = categoryDao.getPeripheryCategoryDataByStoreId(model.getStoreId());
        TreeSet<String> neibei = new TreeSet<String>();
        for (int i = 0; i < list.size(); i++) {
            neibei.add(list.get(i).getCategory());
        }
        if (list.size() > 0) {
            modelMap.put("data1", neibei);
            modelMap.put("data", list);
            modelMap.put("error", null);
        } else {
            modelMap.put("data1", neibei);
            modelMap.put("data", null);
            modelMap.put("error", "no data!");
        }
        return modelMap;
    }

    /**
     * @Title: getShake
     * @Description: 获取摇一摇
     * @param model
     * @return
     */
    @RequestMapping(value = "/getShake", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getShake(@RequestBody LocationModel model) {

        String mapId = model.getMapId();
        double x = model.getX();
        double y = model.getY();
        LOG.debug("api getShake.mapId:" + mapId + ",x:" + x + ",y:" + y);
        if ("".equals(x) || "".equals(y) || "".equals(mapId)) {
            LOG.debug("getShake param error!");
            return null;
        }
        String tikePath = null;
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<String> lists = shopDao.getTicketByLocation(mapId, x, y);
        for (int j = 0; j < lists.size(); j++) {
            String shopId = lists.get(j);
            List<Map<String, Object>> list = ticketDao.getTiketByShopId(shopId);
            int allSize = 0;
            int suiji = (int) (Math.random() * 100 + 1);
            if (list.isEmpty()) {
                modelMap.put("data", tikePath);
                modelMap.put("error", "no ticket data");
                return modelMap;
            }

            for (int i = 0; i < list.size(); i++) {
                String ticke = list.get(i).get("ticketPath").toString();
                int chance = Integer.parseInt(list.get(i).get("chances").toString());
                if (i == 0) {
                    allSize = allSize + chance;
                    if (suiji <= chance) {
                        tikePath = ticke;
                        modelMap.put("data", tikePath);
                        modelMap.put("error", null);
                        return modelMap;
                    }
                } else {
                    boolean temp = allSize < suiji && suiji <= allSize + chance;
                    if (temp) {
                        tikePath = ticke;
                        modelMap.put("data", tikePath);
                        modelMap.put("error", null);
                        return modelMap;
                    } else {
                        allSize = allSize + chance;
                    }
                }
            }
        }
        LOG.debug("getShake tickePath:" + tikePath);
        modelMap.put("data", tikePath);
        modelMap.put("error", null);
        return modelMap;
    }

    /**
     * @Title: saveLeaveMessage
     * @Description: 留言保存接口
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveLeaveMessage", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> saveLeaveMessage(@RequestBody LeaveMessageModel model) {
        long times = System.currentTimeMillis();
        model.setUploadTime(Util.dateFormat(times, Params.YYYYMMDDHHMMSS));
        LOG.debug("api saveLeaveMessage.id:" + model.getId() + ",msg:" + model.getMsg());
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = leaveMessageDao.saveInfo(model);
        if (result > 0) {
            modelMap.put("data", true);
            modelMap.put("error", null);
        } else {
            modelMap.put("data", false);
            modelMap.put("error", "save failed");
        }
        return modelMap;
    }

    /**
     * @Title: saveFootprints
     * @Description: 足迹保存接口
     * @param file
     * @param model
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/saveFootprints", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> saveFootprints(@RequestParam(value = "file", required = false) MultipartFile file,
            LeaveMessageModel model, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        long times = System.currentTimeMillis();
        model.setUploadTime(Util.dateFormat(times, Params.YYYYMMDDHHMMSS));
        String fileName = null;
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        if (file != null) {
            fileName = file.getOriginalFilename();
        }
        if (StringUtils.isNotEmpty(fileName)) {
            String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/Footprints");
            String ext = fileName.substring(fileName.lastIndexOf('.'));
            fileName = model.getMapId() + "_" + times + ext;
            model.setPath(fileName);
            File targetFile = new File(path, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            // 修改
            try {
                // BufferedImage sourceImg =
                // javax.imageio.ImageIO.read(file.getInputStream());
                file.transferTo(targetFile);
                //
                // ticketModel.setImageWidth(sourceImg.getWidth());
                // ticketModel.setImageHigth(sourceImg.getHeight());
            } catch (Exception e) {
                modelMap.put("data", false);
                modelMap.put("error", e.getMessage());
                LOG.error(e);
            }
        }
        String msg = model.getMsg();
        String message = null;
        String[] msgList = null;
        String mapId = null;
        String x = null;
        String y = null;
        if (msg != null) {
            msgList = msg.split("_");
            mapId = msgList[0];
            x = msgList[1];
            y = msgList[2];
            if (msgList.length > 3) {
                message = msgList[3];
            } else {
                message = "";
            }
            model.setMapId(mapId);
            model.setMsg(message);
            model.setX(x);
            model.setY(y);
            model.setShopId(mapId + "_0");
        }
        int result = 0;
        if (mapId != null && x != null && y != null) {
            List<ShopModel> list = shopDao.getShopInfoByMapId(model.getMapId());
            for (int i = 0; i < list.size(); i++) {
                String shopId = list.get(i).getId();
                double xSpot = Double.valueOf(list.get(i).getxSpot());
                double x1Spot = Double.valueOf(list.get(i).getX1Spot());
                double ySpot = Double.valueOf(list.get(i).getySpot());
                double y1Spot = Double.valueOf(list.get(i).getY1Spot());
                double doublex = Double.valueOf(x);
                double doubley = Double.valueOf(y);
                if (doublex > (10*xSpot) && doublex < (10*x1Spot) && doubley > (10*ySpot) && doubley < (10*y1Spot)) {
                    model.setShopId(shopId);
                    break;
                }
            }

        }
        try {
            result = leaveMessageDao.saveFootprints(model);
        } catch (Exception e) {
            modelMap.put("data", false);
            modelMap.put("error", e.getMessage());
            LOG.error(e);
        }
        if (result != 0) {
            modelMap.put("data", true);
            modelMap.put("error", null);
        }

        return modelMap;
    }

    /**
     * @Title: getLeaveMessageAndFootprints
     * @Description: 获取足迹留言接口
     * @param model
     * @return
     */
    @RequestMapping(value = "/getLeaveMessageAndFootprints", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getLeaveMessageAndFootprints(@RequestParam("mapId") String mapId) {

        LOG.debug("api getLeaveMessageAndFootprints.mapID:" + mapId);
        List<MapMngModel> lisMap = daoMaps.getMapDataByMapId(mapId);
       String mapName="";
        if(lisMap!=null&&lisMap.size()!=0){
            mapName=lisMap.get(0).getFloor();
        }
        List<ShopModel> list = new ArrayList<ShopModel>();
        List<ShopModel> list1 = shopDao.getShopInfoByMapId1(mapId);
        ShopModel shop = null;
        double x = 0;
        double x1 = 0;
        double y = 0;
        double y1 = 0;
        DecimalFormat df = new DecimalFormat("######0.00");
        for (int i = 0; i < list1.size(); i++) {
            shop = list1.get(i);
            String id = shop.getShopId();
            String count = leaveMessageDao.getFootCountByShopId(id);
            shop.setAllcount(count);
            list.add(shop);
        }
        if (list1.size() > 0) {
            MapMngModel map = lisMap.get(0);
            String scale = map.getScale();
            int imageWidth = map.getImgWidth();
            int imageHeight = map.getImgHeight();
            x = ((imageWidth / 2) - 50) / Double.valueOf(scale);
            x1 = ((imageWidth / 2) + 50) / Double.valueOf(scale);
            y = ((imageHeight / 2) - 50) / Double.valueOf(scale);
            y1 = ((imageHeight / 2) + 50) / Double.valueOf(scale);
            String count1 = leaveMessageDao.getFootCountByShopId(mapId + "_0");
            ShopModel shop1 = new ShopModel();
            shop1.setxSpot(df.format(x));
            shop1.setX1Spot(df.format(x1));
            shop1.setySpot(df.format(y));
            shop1.setY1Spot(df.format(y1));
            shop1.setShopId(mapId + "_0");
            shop1.setAllcount(count1);
            shop1.setShopName(mapName);
            list.add(shop1);
        }
        List<LeaveMessageModel> list2 = leaveMessageDao.getLeaveMessage(mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("data1", list2);
        modelMap.put("data", list);
        modelMap.put("error", null);
        return modelMap;
    }

    /**
     * @Title: getFootprintsDataByShopId
     * @Description: 根据shopId获取图片
     * @param model
     * @return
     */
    @RequestMapping(value = "/getFootprintsDataByShopId", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getFootprintsDataByShopId(@RequestParam("shopId") String shopId) {

        LOG.debug("api getFootprintsDataByShopId.shopId:" + shopId);
        List<LeaveMessageModel> list = leaveMessageDao.getFootprintsPathByShopId(shopId);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        if (list.size() > 0) {
            modelMap.put("data", list);
            modelMap.put("error", null);
        } else {
            modelMap.put("data", null);
            modelMap.put("error", "no bi_footprints data");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getCarMap", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getCarMap(@RequestParam("storeId") String storeId) {
        // 停车场地图
        MapMngModel model = new MapMngModel();
        // 空车位号
        List<String> parkingNumber = null;
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> maplist = null;
        List<Object> list = new ArrayList<>();
        List<String> floorNoList = daoMaps.getFloorByPlaceId(storeId);
        for (int i = 0; i < floorNoList.size(); i++) {
            maplist = new HashMap<String, Object>();
            model = daoMaps.getMapCarData(floorNoList.get(i));
            parkingNumber = daoMaps.getParkingNumber(floorNoList.get(i));
            if (model != null) {
                maplist.put("map", model);
                maplist.put("parkingNumber", parkingNumber);
                list.add(maplist);
            }

        }
        map.put("data", list);
        return map;

    }

    @RequestMapping(value = "/getParkingPlace", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getParkingPlace(@RequestParam("userName") String userName) {
        // 车位号集合
        List<ParkinginformationModel> parkingNumberList = null;
        String errorResult = null;
        try {
            parkingNumberList = parkDao.getParking(userName);
        } catch (Exception e) {
            LOG.error("getParkingPlace error:" + e);
            errorResult = e.getMessage();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", parkingNumberList);
        map.put("error", errorResult);

        return map;

    }

    @RequestMapping(value = "/clearParkingNumber", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> clearParkingNumber(@RequestBody ParkinginformationModel model1) {
        // 取车成功
        int result = 0;
        String errorResult = null;
        try {
            result = parkDao.clearParkingNumber(model1);

        } catch (Exception e) {
            LOG.error("saveParkingPlace error:" + e);
            errorResult = e.getMessage();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);
        map.put("error", errorResult);

        return map;

    }

    @RequestMapping(value = "/saveParkingPlace", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> saveParkingPlace(@RequestBody ParkinginformationModel model1) {
        // 停车时间
        long time = System.currentTimeMillis();
        model1.setEntryTime(time);
        // 保存结果0失败，1成功
        int result = 0;
        // 错误信息
        String errorResult = null;
        try {
            result = parkDao.saveParking(model1);

        } catch (Exception e) {
            LOG.error("saveParkingPlace error:" + e);
            errorResult = e.getMessage();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);
        map.put("error", errorResult);

        return map;

    }

    @RequestMapping(value = "/subscription", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> subscription(@RequestParam("storeId") String storeId, @RequestParam("ip") String ip) {
        SvaModel sva = new QuartzJob().getSvaModel().get("SpecifiesModel");
        service.subscribeSvaPhone(sva, ip);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("error", null);
        modelMap.put("data", 1);

        return modelMap;
    }

    @RequestMapping(value = "/unsubscribe", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> unsubscribe(@RequestParam("storeId") String storeId, @RequestParam("ip") String ip) {
        SvaModel sva = new QuartzJob().getSvaModel().get("SpecifiesModel");
        service.unsubscribeSvaPhone(sva, ip);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("error", null);
        modelMap.put("data", 1);
        return modelMap;
    }

    /**
     * 
     * @Title: register
     * @Description: 用户注册
     * @param request
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/register", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> register(HttpServletRequest request, @RequestParam("userName") String userName,
            @RequestParam("password") String password) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            modelMap.put("status", Params.RETURN_CODE_300);
            return modelMap;
        }
        UserModel userModel = new UserModel();
        // 注册时间
        userModel.setCreateTime(new Date());
        userModel.setUserName(userName);
        userModel.setPassword(password);
        userModel.setNickName(userName);
        try {
            // 上次登录时间
            userModel.setLastLoginTime(new Date());
            // 注册的时候昵称初始成和用户名相同
            userDao.register(userModel);
            int id = userModel.getId();
            modelMap.put("status", Params.RETURN_CODE_200);
            request.getSession().setMaxInactiveInterval(Params.SESSION_SAVA_TIME);
            request.getSession().setAttribute("id", id);
            // 用户信息带上返回
            modelMap.put("data", userModel);
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("status", Params.RETURN_CODE_400);
        }
        return modelMap;
    }

    /**
     * 
     * @Title: login
     * @Description: 普通登录或授权登录
     * @param request
     * @param userName
     * @param password
     * @param openid
     * @return
     */
    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "openid", required = false) String openid) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        if (StringUtils.isEmpty(openid)) {
            // 普通登录方式，用户名、手机号、邮箱
            if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userName)) {
                modelMap.put("status", Params.RETURN_CODE_300);
                return modelMap;
            }
            String columeName;
            if (userName.contains("@")) {
                // 邮箱登录
                columeName = "email";
            } else if (userName.charAt(0) >= '0' && userName.charAt(0) <= '9') {
                // 手机号登录
                columeName = "phone";
            } else {
                // 账户名登录
                columeName = "userName";
            }
            Integer id = userDao.login(userName, password, columeName);
            if (id == null) {
                // 登录失败
                modelMap.put("status", Params.RETURN_CODE_400);
            } else {
                // 登录成功
                modelMap.put("status", Params.RETURN_CODE_200);
                request.getSession().setMaxInactiveInterval(Params.SESSION_SAVA_TIME);
                request.getSession().setAttribute("id", id);
                // 用户信息带上返回
                modelMap.put("data", userDao.getUserInfo(id));
            }
        } else {
            // 三方授权登录方式
            Integer id = userDao.loginByOpenid(openid);
            if (id == null) {
                // 第一次授权
                UserModel userModel = new UserModel();
                // 注册时间
                userModel.setCreateTime(new Date());
                userModel.setOpenid(openid);
                try {
                    // 上次登录时间
                    userModel.setLastLoginTime(new Date());
                    userDao.registerByOpenid(userModel);
                    id = userModel.getId();
                    // 告诉客户端识别码201，第一次授权让它传授权的用户信息到服务端
                    modelMap.put("status", Params.RETURN_CODE_201);
                    request.getSession().setMaxInactiveInterval(Params.SESSION_SAVA_TIME);
                    request.getSession().setAttribute("id", id);
                    modelMap.put("id", id);
                } catch (Exception e) {
                    // TODO: handle exception
                    modelMap.put("status", Params.RETURN_CODE_400);
                }
            } else {
                // 登录成功
                modelMap.put("status", Params.RETURN_CODE_200);
                request.getSession().setMaxInactiveInterval(60);
                request.getSession().setAttribute("id", id);
                // 用户信息带上返回
                modelMap.put("data", userDao.getUserInfo(id));
            }
        }
        return modelMap;
    }

    /**
     * 
     * @Title: getUserInfo
     * @Description: 获取用户信息
     * @param request
     * @return
     */
    @AppPassport
    @RequestMapping(value = "/getUserInfo", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        int id = (int) request.getSession().getAttribute("id");
        UserModel userModel = userDao.getUserInfo(id);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("status", Params.RETURN_CODE_200);
        modelMap.put("data", userModel);
        return modelMap;
    }

    /**
     * 
     * @Title: modifyPassword
     * @Description: 修改密码
     * @param request
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @AppPassport
    @RequestMapping(value = "/modifyPassword", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> modifyPassword(HttpServletRequest request,
            @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        int id = (int) request.getSession().getAttribute("id");
        int result = userDao.modifyPassword(id, oldPassword, newPassword);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("status", result == 0 ? Params.RETURN_CODE_400 : Params.RETURN_CODE_200);
        return modelMap;
    }

    /**
     * 
     * @Title: modifyPassword
     * @Description: 退出登录
     * @param request
     * @return
     */
    @AppPassport
    @RequestMapping(value = "/logout", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> modifyPassword(HttpServletRequest request) {
        request.getSession().invalidate();
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("status", Params.RETURN_CODE_200);
        return modelMap;
    }

    /**
     * 修改用户信息
     * 
     * @Title: modifyUserInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param request
     * @param userModel
     * @return
     */
    @AppPassport
    @RequestMapping(value = "/modifyUserInfo", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> modifyUserInfo(HttpServletRequest request,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar, UserModel userModel) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int id = (int) request.getSession().getAttribute("id");
        Date nowDate = new Date();
        userModel.setId(id);
        userModel.setUpdateTime(nowDate);
        //如果avatar不为null，进行头像上传
        if (avatar != null) {
            String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/avatars");
            File fileAll = new File(filePath);
            if (!fileAll.exists()) {
                fileAll.mkdirs(); 
            }
            // 头像
            String fileName = id + ".png";
            File targetFile = new File(filePath, fileName);
            // 修改
            try {
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                }
                avatar.transferTo(targetFile);
            } catch (Exception e) {
                LOG.error(e);
            }
        }
        try {
            int result = userDao.modifyUserInfo(userModel);
            modelMap.put("status", result == 0 ? Params.RETURN_CODE_400 : Params.RETURN_CODE_200);
            modelMap.put("updateTime", nowDate);
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("status", Params.RETURN_CODE_302);
        }
        return modelMap;
    }
    /**
     * 修改用户信息
     * 
     * @Title: modifyUserInfo1
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param request
     * @param userModel
     * @return
     */
    @AppPassport
    @RequestMapping(value = "/modifyUserInfo1", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> modifyUserInfo1(HttpServletRequest request, UserModel userModel) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int id = (int) request.getSession().getAttribute("id");
        Date nowDate = new Date();
        userModel.setId(id);
        userModel.setUpdateTime(nowDate);
        try {
            int result = userDao.modifyUserInfo(userModel);
            modelMap.put("status", result == 0 ? Params.RETURN_CODE_400 : Params.RETURN_CODE_200);
            modelMap.put("updateTime", nowDate);
        } catch (Exception e) {
            // TODO: handle exception
            modelMap.put("status", Params.RETURN_CODE_302);
        }
        return modelMap;
    }
    

    @RequestMapping(value = "/getPositionMapData", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getPositionMapData(@RequestParam("name") String name) {
        LOG.debug("getPositionData name:"+name);
        List<MapMngModel> resultList = daoMaps.getMapDataByStoreName1(name);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        if (resultList.size() > 0) {
            modelMap.put("error", null);
            modelMap.put("data", resultList);
        } else {
            modelMap.put("error", "no map data");
            modelMap.put("data", null);
        }
        return modelMap;
    }
    
    @RequestMapping(value = "/getStoreInfoByMapId", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getStoreInfoByMapId(@RequestParam("mapId") String mapId) {
        LOG.debug("getStoreInfoByMapId mapId:"+mapId);
        List<MarketModel> resultList = daoMaps.getStoreData(mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        if (resultList.size() > 0) {
            modelMap.put("error", null);
            modelMap.put("data", resultList);
        } else {
            modelMap.put("error", "no map data");
            modelMap.put("data", null);
        }
        return modelMap;
    }
    
}
