package com.bis.web.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import com.bis.common.RSAUtils;
import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.LocationDao;
import com.bis.dao.StatisticsDao;
import com.bis.dao.VisitorDao;
import com.bis.model.AreaModel;
import com.bis.model.GlobalModel;
import com.bis.model.VisitTimeModel;
import com.bis.web.auth.AuthPassport;

import net.sf.json.JSONObject;

/**
 * @ClassName: HomeController
 * @Description: 页面跳转controller
 * @author labelCS
 * @date 2017年6月26日 上午9:13:34
 * 
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController {

    private static final Logger LOG = Logger.getLogger(HomeController.class);

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * @Title: showSvaMng
     * @Description: 商场管理页面
     * @param model
     * @return
     */
    @AuthPassport
    @RequestMapping(value = "/storeMng", method = { RequestMethod.GET })
    public String showSvaMng(Model model) {
        // SystemInfo si = new SystemInfo();
        // int r = dao.selectCount();
        // long b = System.currentTimeMillis();
        // if(r==0){
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }else {
        // long a = dao.selectTimeById()+30000;
        // if(b<=a){
        // int c = dao.selectParameter();
        // model.addAttribute("cpu", c);
        // }else{
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }
        // }
        // if(r>2){
        // long e = dao.selectMinId();
        // dao.deleteCount(e);
        // }
        // model.addAttribute("memory", si.getEMS());
        // model.addAttribute("diskspace",si.getDisk());
        model.addAttribute("infoMng", true);
        model.addAttribute("storeMng", true);
        // model.addAttribute("cpu", SystemInfo.cpu());
        // model.addAttribute("memory", SystemInfo.memory());
        // model.addAttribute("diskspace", SystemInfo.file());
        return "config/storeConfig";
    }

    /**
     * @Title: showMapMng
     * @Description: 地图管理页面
     * @param model
     * @param info
     * @return
     */
    @AuthPassport
    @RequestMapping(value = "/mapMng", method = { RequestMethod.GET })
    public String showMapMng(Model model, @RequestParam(value = "info", required = false) String info) {
        // SystemInfo si = new SystemInfo();
        // int r = dao.selectCount();
        // long b = System.currentTimeMillis();
        // if(r==0){
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }else {
        // long a = dao.selectTimeById()+30000;
        // if(b<=a){
        // int c = dao.selectParameter();
        // model.addAttribute("cpu", c);
        // }else{
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }
        // }
        // if(r>2){
        // long e = dao.selectMinId();
        // dao.deleteCount(e);
        // }
        // model.addAttribute("memory", si.getEMS());
        // model.addAttribute("diskspace",si.getDisk());
        model.addAttribute("mapMngModel", true);
        model.addAttribute("infoMng", true);
        model.addAttribute("mapMng", true);
        model.addAttribute("info", info);
        // model.addAttribute("cpu", SystemInfo.cpu());
        // model.addAttribute("memory", SystemInfo.memory());
        // model.addAttribute("diskspace", SystemInfo.file());
        return "config/mapConfig";
    }

    /**
     * @Title: showCategoryMng
     * @Description: 店铺分类管理页面
     * @param model
     * @return
     */
    @AuthPassport
    @RequestMapping(value = "/categoryMng", method = { RequestMethod.GET })
    public String showCategoryMng(Model model) {
        // SystemInfo si = new SystemInfo();
        // int r = dao.selectCount();
        // long b = System.currentTimeMillis();
        // if(r==0){
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }else {
        // long a = dao.selectTimeById()+30000;
        // if(b<=a){
        // int c = dao.selectParameter();
        // model.addAttribute("cpu", c);
        // }else{
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }
        // }
        // if(r>2){
        // long e = dao.selectMinId();
        // dao.deleteCount(e);
        // }
        // model.addAttribute("memory", si.getEMS());
        // model.addAttribute("diskspace",si.getDisk());
        model.addAttribute("infoMng", true);
        model.addAttribute("categoryMng", true);
        // model.addAttribute("cpu", SystemInfo.cpu());
        // model.addAttribute("memory", SystemInfo.memory());
        // model.addAttribute("diskspace", SystemInfo.file());
        return "config/categoryConfig";
    }

    /**
     * @Title: showShopMng
     * @Description: 店铺管理页面
     * @param model
     * @return
     */
    @AuthPassport
    @RequestMapping(value = "/shopMng", method = { RequestMethod.GET })
    public String showShopMng(Model model) {
        // SystemInfo si = new SystemInfo();
        // int r = dao.selectCount();
        // long b = System.currentTimeMillis();
        // if(r==0){
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }else {
        // long a = dao.selectTimeById()+30000;
        // if(b<=a){
        // int c = dao.selectParameter();
        // model.addAttribute("cpu", c);
        // }else{
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }
        // }
        // if(r>2){
        // long e = dao.selectMinId();
        // dao.deleteCount(e);
        // }
        // model.addAttribute("memory", si.getEMS());
        // model.addAttribute("diskspace",si.getDisk());
        model.addAttribute("infoMng", true);
        model.addAttribute("InputMng", true);
        // model.addAttribute("cpu", SystemInfo.cpu());
        // model.addAttribute("memory", SystemInfo.memory());
        // model.addAttribute("diskspace", SystemInfo.file());
        return "config/shopConfig";
    }

    /**
     * @Title: showShopMng
     * @Description: 店铺管理页面
     * @param model
     * @return
     */
    @AuthPassport
    @RequestMapping(value = "/ticketMng", method = { RequestMethod.GET })
    public String showTicket(Model model) {
        // SystemInfo si = new SystemInfo();
        // int r = dao.selectCount();
        // long b = System.currentTimeMillis();
        // if(r==0){
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }else {
        // long a = dao.selectTimeById()+30000;
        // if(b<=a){
        // int c = dao.selectParameter();
        // model.addAttribute("cpu", c);
        // }else{
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }
        // }
        // if(r>2){
        // long e = dao.selectMinId();
        // dao.deleteCount(e);
        // }
        // model.addAttribute("memory", si.getEMS());
        // model.addAttribute("diskspace",si.getDisk());
        model.addAttribute("infoMng", true);
        model.addAttribute("InputMng", true);
        // model.addAttribute("cpu", SystemInfo.cpu());
        // model.addAttribute("memory", SystemInfo.memory());
        // model.addAttribute("diskspace", SystemInfo.file());
        return "config/ticketConfig";
    }

    @AuthPassport
    @RequestMapping(value = "/mixingMng", method = { RequestMethod.GET })
    public String showMixing(Model model) {
        // SystemInfo si = new SystemInfo();
        // int r = dao.selectCount();
        // long b = System.currentTimeMillis();
        // if(r==0){
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }else {
        // long a = dao.selectTimeById()+30000;
        // if(b<=a){
        // int c = dao.selectParameter();
        // model.addAttribute("cpu", c);
        // }else{
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }
        // }
        // if(r>2){
        // long e = dao.selectMinId();
        // dao.deleteCount(e);
        // }
        // model.addAttribute("memory", si.getEMS());
        // model.addAttribute("diskspace",si.getDisk());
        model.addAttribute("infoMng", true);
        model.addAttribute("InputMng", true);
        // model.addAttribute("cpu", SystemInfo.cpu());
        // model.addAttribute("memory", SystemInfo.memory());
        // model.addAttribute("diskspace", SystemInfo.file());
        return "config/mixingConfig";
    }

    /**
     * @Title: showShop
     * @Description: 店铺分析数据展示页面
     * @param response
     * @return
     */
    @AuthPassport
    @RequestMapping(value = "/shop", method = { RequestMethod.GET })
    public String showShop(Model model) {
        // SystemInfo si = new SystemInfo();
        // int r = dao.selectCount();
        // long b = System.currentTimeMillis();
        // if(r==0){
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }else {
        // long a = dao.selectTimeById()+30000;
        // if(b<=a){
        // int c = dao.selectParameter();
        // model.addAttribute("cpu", c);
        // }else{
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }
        // }
        // if(r>2){
        // long e = dao.selectMinId();
        // dao.deleteCount(e);
        // }
        // model.addAttribute("infoMng", true);
        // model.addAttribute("InputMng", true);
        // model.addAttribute("cpu",si.getCpuRatioForWindows());
        // model.addAttribute("memory", si.getEMS());
        // model.addAttribute("diskspace",si.getDisk());
        // model.addAttribute("cpu", SystemInfo.cpu());
        // model.addAttribute("memory", SystemInfo.memory());
        // model.addAttribute("diskspace", SystemInfo.file());
        return "shop";
    }

    @AuthPassport
    @RequestMapping(value = "/floor", method = { RequestMethod.GET })
    public String showFloor(Model model) {
        // SystemInfo si = new SystemInfo();
        // int r = dao.selectCount();
        // long b = System.currentTimeMillis();
        // if(r==0){
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }else {
        // long a = dao.selectTimeById()+30000;
        // if(b<=a){
        // int c = dao.selectParameter();
        // model.addAttribute("cpu", c);
        // }else{
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }
        // }
        // if(r>2){
        // long e = dao.selectMinId();
        // dao.deleteCount(e);
        // }
        model.addAttribute("infoMng", true);
        model.addAttribute("InputMng", true);
        // model.addAttribute("cpu", SystemInfo.cpu());
        // model.addAttribute("memory", SystemInfo.memory());
        // model.addAttribute("diskspace", SystemInfo.file());
        // model.addAttribute("cpu",si.getCpuRatioForWindows());
        // model.addAttribute("memory", si.getEMS());
        // model.addAttribute("diskspace",si.getDisk());
        return "floor";
    }

    /**
     * @Title: showUser
     * @Description: 用户画像页面
     * @return
     */
    @AuthPassport
    @RequestMapping(value = "/operation", method = { RequestMethod.GET })
    public String showUser(Model model) {
        // SystemInfo si = new SystemInfo();
        // int r = dao.selectCount();
        // long b = System.currentTimeMillis();
        // if(r==0){
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }else {
        // long a = dao.selectTimeById()+30000;
        // if(b<=a){
        // int c = dao.selectParameter();
        // model.addAttribute("cpu", c);
        // }else{
        // int d = si.getCpuRatioForWindows();
        // model.addAttribute("cpu", d);
        // dao.saveCpu(d,b);
        // }
        // }
        // if(r>2){
        // long e = dao.selectMinId();
        // dao.deleteCount(e);
        // }
        // model.addAttribute("memory", si.getEMS());
        // model.addAttribute("diskspace",si.getDisk());
        // model.addAttribute("cpu", SystemInfo.cpu());
        // model.addAttribute("memory", SystemInfo.memory());
        // model.addAttribute("diskspace", SystemInfo.file());
        return "operation";
    }

    @AuthPassport
    @RequestMapping(value = "/changeLocal", method = { RequestMethod.GET })
    public String changeLocal(HttpServletRequest request, String local, HttpServletResponse response) {
        if ("zh".equals(local)) {
            localeResolver.setLocale(request, response, Locale.CHINA);
        } else if ("en".equals(local)) {
            localeResolver.setLocale(request, response, Locale.ENGLISH);
        }
        String lastUrl = request.getHeader("Referer");
        String str;
        if (lastUrl.indexOf("?") != -1) {
            str = lastUrl.substring(0, lastUrl.lastIndexOf("?"));
        } else {
            str = lastUrl;
        }
        RequestContext requestContext = new RequestContext(request);

        Locale myLocale = requestContext.getLocale();

        LOG.info(myLocale);

        return "redirect:" + str;
    }

    @RequestMapping(value = "/notfound")
    public ModelAndView notfound() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("404");

        return mv;
    }

    @AuthPassport
    @RequestMapping(value = "/market2", method = { RequestMethod.GET })
    public String showMarket(Model model) {
        return "market2";
    }

    @RequestMapping(value = "/login", method = { RequestMethod.GET })
    public String login(HttpServletRequest request, Model model) {
        // 获取公钥
        String publicKey = RSAUtils.getBase64PublicKey();
        model.addAttribute("publicKey", publicKey);
        // 生成随机值
        String rand =  RandomStringUtils.randomAlphabetic(6);
        model.addAttribute("rand", rand);
        
        // 将生成的随机值存到session中，实际使用可以存到第三方缓存中,并设置失效时间
        request.getSession().setAttribute("rand", rand);
        return "login";
    }

    @RequestMapping(value = "/login1", method = { RequestMethod.GET })
    public String login1(Model model) {
        return "market";
    }

    @Value("${sva.ftpIp}")
    private String ftpIp;

    @Value("${sva.ftpPort}")
    private int ftpPort;

    @Value("${sva.ftpUserName}")
    private String ftpUserName;

    @Value("${sva.ftpPassWord}")
    private String ftpPassWord;

    @Value("${sva.ftpRemotePath}")
    private String ftpRemotePath;

    @Value("${sva.ftpFileNameHeader}")
    private String ftpFileNameHeader;

    @Value("${sva.ftpType}")
    private int ftpType;

    @Autowired
    private VisitorDao visitorDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private StatisticsDao statisticsDao;

    @RequestMapping(value = "/doStoreUserData", method = { RequestMethod.GET })
    @ResponseBody
    public Object doStoreUserData(String dateKey) {

        boolean result = false;
        if (dateKey != null) {
            String doDay = Util.dateFormat(Util.dateFormatStringtoLong(dateKey, Params.YYYYMMDD), Params.YYYYMMDD2);
            String tableName = Params.LOCATION + dateKey;
            String storeTableName = Params.STORELOCATION + dateKey.substring(0, dateKey.length() - 2);
            String insertStoreUserid = "replace into " + storeTableName + "(userId,time,storeId) values";
            List<VisitTimeModel> storeUserListModel = locationDao.getStoreUserList(tableName);
            for (VisitTimeModel sva : storeUserListModel) {
                String userId = sva.getUserId();
                int storeId = sva.getStoreId();
                insertStoreUserid += "('" + userId + "','" + doDay + "','" + storeId + "'),";
            }
            if (storeUserListModel.size() > 0) {
                insertStoreUserid = insertStoreUserid.substring(0, insertStoreUserid.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertStoreUserid);
                result = true;
                LOG.debug("saveUserShop-store-userid result:" + areaResult);
            }
        } else {
            return result;
        }

        return result;
    }

    @RequestMapping(value = "/doShopUserData", method = { RequestMethod.GET })
    @ResponseBody
    public Object doShopUserData(String dateKey) {

        boolean result = false;
        if (dateKey != null) {
            String doDay = Util.dateFormat(Util.dateFormatStringtoLong(dateKey, Params.YYYYMMDD), Params.YYYYMMDD2);
            String tableName = Params.LOCATION + dateKey;
            String shopTableName = Params.SHOPLOCATION + dateKey.substring(0, dateKey.length() - 2);
            String insertUserid = "replace into " + shopTableName + "(userId,time,delaytime,shopId,type) values";
            List<VisitTimeModel> userListModel = locationDao.getUserList(tableName);
            for (VisitTimeModel sva : userListModel) {
                String userId = sva.getUserId();
                long visiTime = sva.getMaxTime() - sva.getMinTime();
                int shopId = sva.getId();
                // int storeId = sva.getStoreId();
                // int mapId = sva.getMapId();
                String shopVisit = Util.getMinute(visiTime, 1);
                insertUserid += "('" + userId + "','" + doDay + "','" + shopVisit + "','" + shopId + "','" + 0 + "'),";
            }
            if (userListModel.size() > 0) {
                insertUserid = insertUserid.substring(0, insertUserid.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertUserid);
                LOG.debug("saveUserShop-shop-userid :" + areaResult);
            }
        } else {
            return result;
        }

        return result;
    }

    @RequestMapping(value = "/testFtpOneDay", method = { RequestMethod.GET })
    @ResponseBody
    public Object testFtpOneDay(String dateKey) {
        // System.out.println("定时任务：解析visitor文件");
        String localPath = getClass().getResource("/").getPath();
        // String localPath = System.getProperty("user.dir");
        // localPath = localPath.substring(0,
        // localPath.indexOf("bin"))+"webapps/SVAProject/WEB-INF";
        localPath = localPath.substring(0, localPath.indexOf("/classes"));
        localPath = localPath + "/" + "ftp" + "/";
        LOG.debug("doFtpData localPath" + localPath);
        // 解析前一天的visitor
        // Date date = new Date();
        // String dateKey = Util.dateFormat(date, Params.YYYYMMDD);
        String fileName = ftpFileNameHeader + dateKey + ".txt";
        boolean ftpResult = Util.downFtpFile(ftpIp, ftpPort, ftpUserName, ftpPassWord, ftpRemotePath, fileName,
                localPath, ftpType);
        if (ftpResult) {

            // String time = Util.dateFormat(date, Params.YYYYMMDDHHMMSS);
            String filePath = localPath;
            File file = new File(filePath, fileName);
            if (file.exists()) {
                // System.out.println("信息文件存在");
                List<JSONObject> list = new ArrayList<>();
                String[] names = Params.VISITOR_COLUMNS;
                BufferedReader reader = null;
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
                    reader = new BufferedReader(isr);
                    String tempString = null;
                    // 一次读入一行，直到读入null为文件结束
                    while ((tempString = reader.readLine()) != null) {
                        JSONObject jsonObject = new JSONObject();
                        String[] values = (tempString + "|end").replace("|", "_").split("_");
                        for (int i = 0; i < names.length; i++) {
                            // {"date","ipv4","ipv6","acr","eci","gender","age","localAddress","homeAddress",
                            // "homeAddressCI","workAddress","workAddressCI","expendAbility"};
                            if (Util.isIp(values[1])) {
                                switch (i) {
                                case 1: // ipv4
                                    jsonObject.put(names[i], Util.isIp(values[i]) ? Util.convertIp(values[i]) : "");
                                    break;
                                case 3: // acr
                                case 4: // eci
                                    jsonObject.put(names[i], values[i].length() < 200 ? values[i] : "error");
                                    break;
                                case 5: // gender
                                case 6: // age
                                case 12: // expendAbility
                                    jsonObject.put(names[i], "".equals(values[i]) ? "不详" : values[i]);
                                    break;
                                // case 7: // localAddress
                                // break;
                                case 8: // homeAddress
                                case 10: // workAddress
                                    jsonObject.put(names[i], values[i] + "_" + values[i + 1]);
                                    break;
                                default:
                                    jsonObject.put(names[i], values[i]);
                                    break;
                                }
                            }
                        }
                        jsonObject.put("time", dateKey);
                        if (values.length >= 14 && Util.isIp(values[1])) {
                            list.add(jsonObject);
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (isr != null) {
                        try {
                            isr.close();
                        } catch (IOException e1) {
                        }
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                        }
                    }
                }
                if (list.size() > 0) {
                    visitorDao.saveData(list);

                }
            }
            // LOG.debug("VisitorController~插入Visitor数据条数:" + num);
        } else {
            LOG.debug("doFtpData downFtpFile failed result " + ftpResult);
        }
        return "today:" + ftpResult;
    }
    
    
    @RequestMapping(value = "/testSaveUserShop", method = { RequestMethod.GET })
    @ResponseBody
    public Object testSaveUserShop(String dateKey) {
        Date selectDate=Util.dateStringFormat(dateKey, Params.YYYYMMDD);
        String nowDay = Util.dateFormat(selectDate, Params.YYYYMMDD);
        String userDay1 = Util.dateFormat(selectDate, Params.YYYYMMDD2);
        String tableName = Params.LOCATION + nowDay;
        String nowMouths = Util.dateFormat(selectDate, Params.YYYYMM);
        String shopTableName = Params.SHOPLOCATION + nowMouths;
        String storeTableName = Params.STORELOCATION + nowMouths;
        String insertStoreUserid = "replace into " + storeTableName + "(userId,time,storeId) values";
        String insertUserid = "replace into " + shopTableName + "(userId,time,delaytime,shopId,type) values";
        List<VisitTimeModel> storeUserListModel = locationDao.getStoreUserList(tableName);
        for (VisitTimeModel sva : storeUserListModel) {
            String userId = sva.getUserId();
            int storeId = sva.getStoreId();
            insertStoreUserid += "('" + userId + "','" + userDay1 + "','" + storeId + "'),";
        }
        if (storeUserListModel.size() > 0) {
            insertStoreUserid = insertStoreUserid.substring(0, insertStoreUserid.length() - 1);
            int areaResult = statisticsDao.doUpdate(insertStoreUserid);
            LOG.debug("saveUserShop-store-userid result:" + areaResult);
        }
        List<VisitTimeModel> userListModel = locationDao.getUserList(tableName);
        for (VisitTimeModel sva : userListModel) {
            String userId = sva.getUserId();
            long visiTime = sva.getMaxTime() - sva.getMinTime();
            int shopId = sva.getId();
            // int storeId = sva.getStoreId();
            // int mapId = sva.getMapId();
            String shopVisit = Util.getMinute(visiTime, 1);
            insertUserid += "('" + userId + "','" + userDay1 + "','" + shopVisit + "','" + shopId + "','" + 0 + "'),";
        }
        if (userListModel.size() > 0) {
            insertUserid = insertUserid.substring(0, insertUserid.length() - 1);
            int areaResult = statisticsDao.doUpdate(insertUserid);
            LOG.debug("saveUserShop-shop-userid :" + areaResult);
        }
        try {
//            String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
            String nowMouth = Util.dateFormat(selectDate, Params.YYYYMMddHH00);
            String nowDays = Util.dateFormat(selectDate, Params.YYYYMMDD2);
//            String tableName = Params.LOCATION + nowDay;
            long endTime = System.currentTimeMillis();
            long beginTime = endTime - 60 * 60 * 1000;
            String insertFloor = "replace into bi_static_floor_visitTime(time,delaytime,allcount,mapId,allcounts) values";
            String insertStore = "replace into bi_static_store_visitTime(time,delaytime,allcount,storeId,allcounts) values";
            String insertShop = "replace into bi_static_shop_visitTime(time,delaytime,allcount,shopId,allcounts) values";

            List<VisitTimeModel> listMapId = locationDao.getMapVisitTime(tableName, beginTime, endTime);
            Map<String, String> mapFloor = getNewMap(listMapId);
            // List<VisitTimeModel> mapCount =
            // locationDao.getCountGroupByMapId(tableName);
            // 6Map<String, Long> mapIdS = getListToMap(mapCount);
            Set<String> mapSet = mapFloor.keySet();
            for (String s : mapSet) {
                String mapId = s.split("-")[0];
                if (mapId != null && mapId != "null" && mapId != "") {
                    String allcount = s.split("-")[1];
                    String visitTime = mapFloor.get(s);
                    int allCounts = locationDao.getOneDayData(mapId, tableName);
                    insertFloor += "('" + nowMouth + "','" + visitTime + "','" + allcount + "','" + mapId + "','"
                            + allCounts + "'),";
                }
            }
            if (mapSet.size() > 0) {
                insertFloor = insertFloor.substring(0, insertFloor.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertFloor);
                LOG.debug("saveVisitTime-floor result:" + areaResult);
            }
            List<VisitTimeModel> listStore = locationDao.getStoreVisitTime(tableName, beginTime, endTime);
            Map<String, String> mapStore = getNewMap(listStore);
            // List<VisitTimeModel> storeCount =
            // locationDao.getCountGroupByStoreId(tableName);
            // Map<String, Long> stores = getListToMap(storeCount);
            Set<String> storeSet = mapStore.keySet();
            for (String s : storeSet) {
                String storeId = s.split("-")[0];
                if (storeId != null && storeId != "null" && storeId != "") {
                    String allcount = s.split("-")[1];
                    String visitTime = mapStore.get(s);
                    int allCounts = locationDao.getStoreAllCount(storeId, tableName);
                    insertStore += "('" + nowMouth + "','" + visitTime + "','" + allcount + "','" + storeId + "','"
                            + allCounts + "'),";
                }
            }
            if (storeSet.size() > 0) {
                insertStore = insertStore.substring(0, insertStore.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertStore);
                LOG.debug("saveVisitTime-store result:" + areaResult);
            }
            String insertEntersql = "insert into bi_static_shop_enter(time,allcount,shopId,allcounts) values";
            List<VisitTimeModel> shopList = locationDao.getShopVisitTime(tableName, beginTime, endTime);
            Map<String, String> mapShop = getNewMap(shopList);
            // List<VisitTimeModel> shopCount =
            // locationDao.getCountGroupByShopId(tableName);
            // Map<String, Long> shops = getListToMap(shopCount);
            Set<String> shopSet = mapShop.keySet();
            String endTimes = nowMouth.split(" ")[1];
            for (String s : shopSet) {
                String shopId = s.split("-")[0];
                if (shopId != null && shopId != "null" && shopId != "") {
                    String allcount = s.split("-")[1];
                    String visitTime = mapShop.get(s);
                    long allCounts = locationDao.getShopAllCount(shopId, tableName);
                    insertShop += "('" + nowMouth + "','" + visitTime + "','" + allcount + "','" + shopId + "','"
                            + allCounts + "'),";
                    if (endTimes.equals("23:00:00")) {
                        long allCountss = locationDao.getShopAllCount2(shopId, tableName);
                        insertEntersql += "('" + nowDays + "','" + allCounts + "','" + shopId + "','" + allCountss
                                + "'),";
                    }
                }
            }
            if (shopSet.size() > 0) {
                insertShop = insertShop.substring(0, insertShop.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertShop);
                LOG.debug("saveVisitTime-shop result:" + areaResult);
            }
            if (endTimes.equals("23:00:00")) {
                insertEntersql = insertEntersql.substring(0, insertEntersql.length() - 1);
                int areaResult = statisticsDao.doUpdate(insertEntersql);
                LOG.debug("saveVisitTime-shop-enter result:" + areaResult);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.out.println(1);
        }
     // System.out.println("定时任务：doStatistics");
        // 表名
//        String nowDay = Util.dateFormat(new Date(), Params.YYYYMMDD);
        String nowMouth = Util.dateFormat(selectDate, Params.YYYYMM00);
//        String nowMouths = Util.dateFormat(new Date(), Params.YYYYMM);
//        String tableName = Params.LOCATION + nowDay;
//        String shopTableName = Params.SHOPLOCATION + nowMouths;
        // 商场数据集合
        List<Map<String, Object>> list = statisticsDao.getStoreStatistics(tableName);
        LOG.debug("tableName:" + tableName + " siteList:" + list);
        String storeDay = "replace into bi_static_store_delay(time,delaytime,allcount,storeId,type) values";
        String visitTime = null;
        for (Map<String, Object> m : list) {
            int allcount = Integer.parseInt(m.get("allcount").toString());
            int usercount = Integer.parseInt(m.get("usercount").toString());
            int storeId = Integer.parseInt(m.get("storeId").toString());
            visitTime = Util.getMinute(Long.valueOf((allcount * 2000)), usercount);
            storeDay += "('" + nowDay + "','" + visitTime + "','" + usercount + "','" + storeId + "','" + 0 + "'),";
        }
        if (list.size() > 0) {
            storeDay = storeDay.substring(0, storeDay.length() - 1);
            int result = statisticsDao.doUpdate(storeDay);
            LOG.debug("storeDay result:" + result);
        }
        // 查询所有的区域
        List<AreaModel> listAreaData = statisticsDao.getAreaData();
        String userVisitTime = null;
        int areaSize = listAreaData.size();
        LOG.debug("storeDay listAreaDataSize:" + areaSize);
        // System.out.println("定时shopTableName:" + shopTableName + "
        // listAreaDataSize:" + areaSize);
        for (int i = 0; i < areaSize; i++) {
            String userDay = "replace into " + shopTableName + "(time,delaytime,userId,shopId,type) values";
            AreaModel areaModel = listAreaData.get(i);
            List<Map<String, Object>> listArea = statisticsDao.getUserDataByArea(tableName, areaModel);
            int shopId = 0;
            for (Map<String, Object> m : listArea) {
                int allcount = Integer.parseInt(m.get("allcount").toString());
                String userId = m.get("userId").toString();
                shopId = Integer.parseInt(m.get("shopId").toString());
                userVisitTime = Util.getMinute(Long.valueOf((allcount * 2000)), 1);
                userDay += "('" + nowDay + "','" + userVisitTime + "','" + userId + "','" + shopId + "','" + 0 + "'),";
            }
            if (listArea.size() > 0) {
                userDay = userDay.substring(0, userDay.length() - 1);
                int areaResult = statisticsDao.doUpdate(userDay);
                LOG.debug("storeDay result:" + areaResult + " shopId:" + shopId);
            }

        }

        // 楼层每天驻留时长人数统计
        // List<String> mapList = mapMngDao.getAllMapId();
        List<Map<String, Object>> maps = statisticsDao.getAllcountByMapId(tableName);
        String userDay = "replace into bi_static_floor (time,delaytime,allcount,mapId) values";
        for (int j = 0; j < maps.size(); j++) {
            String chaMapIds = String.valueOf(maps.get(j).get("mapId"));
            String allcount = String.valueOf(maps.get(j).get("allcount"));
            int count = Integer.parseInt(String.valueOf(maps.get(j).get("count")));
            String floorVisit = Util.getMinute(Long.valueOf((Integer.parseInt(allcount) * 2000)), count);
            userDay += "('" + nowDay + "','" + floorVisit + "','" + count + "','" + chaMapIds + "'),";

        }
        if (maps.size() > 0) {
            userDay = userDay.substring(0, userDay.length() - 1);
            int areaResult = statisticsDao.doUpdate(userDay);
            LOG.debug("floorDay result:" + areaResult);
        }

        // 商场按月统计
        String storeMouth = "replace into bi_static_store_delay(time,delaytime,allcount,storeId,type) values";
        List<Map<String, Object>> storeListMouth = statisticsDao.getStoreByMouth(nowMouth);
        double storeVisitTime = 0;
        for (Map<String, Object> m : storeListMouth) {
            double allTime = Double.valueOf(m.get("allTime").toString());
            int allcount = Integer.parseInt(m.get("allcount").toString());
            int storeId = Integer.parseInt(m.get("storeId").toString());
            storeVisitTime = allTime / allcount;
            storeMouth += "('" + nowMouth + "','" + storeVisitTime + "','" + allcount + "','" + storeId + "','" + 1
                    + "'),";
        }
        if (storeListMouth.size() > 0) {
            storeMouth = storeMouth.substring(0, storeMouth.length() - 1);
            int areaResult = statisticsDao.doUpdate(storeMouth);
            LOG.debug("storeMouth result:" + areaResult);
        }
        return "testSaveUserShop:OK";
    }
    
    private static Map<String, String> getNewMap(List<VisitTimeModel> list) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (list.size() > 0) {
            int id = list.get(0).getId();
            int newId;
            int count = 0;
            int allCount = 0;
            long tempVistTime = 0;
            String key = null;
            for (VisitTimeModel sva : list) {
                newId = sva.getId();
                if (newId == id) {
                    allCount = allCount + 1;
                    long visitTime = sva.getMaxTime() - sva.getMinTime();
                    key = newId + "";
                    // 小於2分鐘忽略
                    if (visitTime > 300000) {
                        count = count + 1;
                        tempVistTime = tempVistTime + visitTime;
                    }
                    id = sva.getId();
                } else {
                    key = key + "-" + allCount;
                    map.put(key, Util.getMinute(tempVistTime, count));
                    count = 0;
                    allCount = 0;
                    tempVistTime = 0;
                    key = null;
                    id = sva.getId();
                }
            }
            if (count != 0) {
                key = key + "-" + allCount;
                map.put(key, Util.getMinute(tempVistTime, count));
            }
            return map;
        } else {
            return null;
        }
    }
    
    @Autowired
    private GlobalModel globalModel;
    
    @RequestMapping(value = "/testMapBorder", method = { RequestMethod.GET })
    @ResponseBody
    public Object testMapBorder() {
        return globalModel;
    }
}
