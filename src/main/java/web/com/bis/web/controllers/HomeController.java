package com.bis.web.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.CpuDao;
import com.bis.dao.VisitorDao;
import com.bis.web.auth.AuthPassport;
import com.bis.web.auth.SystemInfo;

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
    @Autowired
    private CpuDao dao;
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
        model.addAttribute("cpu", 89);
        model.addAttribute("memory", 65);
        model.addAttribute("diskspace", 30);
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
        model.addAttribute("cpu", 90);
        model.addAttribute("memory", 55);
        model.addAttribute("diskspace", 30);
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
        model.addAttribute("cpu", 92);
        model.addAttribute("memory", 65);
        model.addAttribute("diskspace", 30);
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
        model.addAttribute("cpu", 95);
        model.addAttribute("memory", 75);
        model.addAttribute("diskspace", 30);
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
        model.addAttribute("cpu", 90);
        model.addAttribute("memory", 85);
        model.addAttribute("diskspace", 30);
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
        model.addAttribute("cpu", 99);
        model.addAttribute("memory", 69);
        model.addAttribute("diskspace", 30);
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
        model.addAttribute("cpu", 98);
        model.addAttribute("memory", 78);
        model.addAttribute("diskspace", 30);
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
        model.addAttribute("cpu", 96);
        model.addAttribute("memory", 65);
        model.addAttribute("diskspace", 30);
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
        model.addAttribute("cpu", 95);
        model.addAttribute("memory", 75);
        model.addAttribute("diskspace", 30);
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
    public String login(Model model) {
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

    @RequestMapping(value = "/testFtpYesterday", method = { RequestMethod.GET })
    @ResponseBody
    public Object testFtpYesterday() {
        // System.out.println("定时任务：解析visitor文件");
        String localPath = getClass().getResource("/").getPath();
        // String localPath = System.getProperty("user.dir");
        // localPath = localPath.substring(0,
        // localPath.indexOf("bin"))+"webapps/SVAProject/WEB-INF";
        localPath = localPath.substring(0, localPath.indexOf("/classes"));
        localPath = localPath + "/" + "ftp" + "/";
        LOG.debug("doFtpData localPath" + localPath);
        // 解析前一天的visitor
        Date date = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
        String dateKey = Util.dateFormat(date, Params.YYYYMMDD);
        String fileName = ftpFileNameHeader + dateKey + ".txt";
        System.out.println(localPath);
        boolean ftpResult = Util.downFtpFile(ftpIp, ftpPort, ftpUserName, ftpPassWord, ftpRemotePath, fileName,
                localPath, ftpType);
        if (ftpResult) {
            // String time = Util.dateFormat(date, Params.YYYYMMDDHHMMSS);
            String filePath = localPath;
            File file = new File(filePath, fileName);
            System.out.println(fileName+" 是否存在："+file.exists());
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
                        String[] values = (tempString+"|end").replace("|", "_").split("_");
                        for (int i = 0; i < names.length; i++) {
                            // {"date","ipv4","ipv6","acr","eci","gender","age","localAddress","homeAddress",
                            // "homeAddressCI","workAddress","workAddressCI","expendAbility"};
                            switch (i) {
                            case 1: // ipv4
                                jsonObject.put(names[i], Util.isIp(values[i])?values[i]:"");
                                break;
                            case 3: // acr
                            case 4: // eci
                                jsonObject.put(names[i], values[i].length()<200?values[i]:"error");
                                break;
                            case 5: // gender
                            case 6: // age
                            case 12: // expendAbility
                                jsonObject.put(names[i], "".equals(values[i])?"不详":values[i]);
                                break;
//                            case 7: // localAddress
//                                break;
//                            case 8: // homeAddress
//                            case 10: // workAddress
//                                break;
                            default:
                                
                                jsonObject.put(names[i], values[i]);
                                break;
                            }
                        }
                        jsonObject.put("time", dateKey);
                        list.add(jsonObject);
                    }
                    reader.close();
                } catch (IOException e) {
                    System.out.println("解析文件错误:"+e.toString());
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
                System.out.println("存bi_visitor前list长度："+list.size());
                visitorDao.saveData(list);
                System.out.println("存bi_visitor完毕");
            }
            // LOG.debug("VisitorController~插入Visitor数据条数:" + num);
        } else {
            LOG.debug("doFtpData downFtpFile failed result " + ftpResult);
        }
        return "yesterday:" + ftpResult;
    }

    @RequestMapping(value = "/testFtpToday", method = { RequestMethod.GET })
    @ResponseBody
    public Object testFtpToday() {
        // System.out.println("定时任务：解析visitor文件");
        String localPath = getClass().getResource("/").getPath();
        // String localPath = System.getProperty("user.dir");
        // localPath = localPath.substring(0,
        // localPath.indexOf("bin"))+"webapps/SVAProject/WEB-INF";
        localPath = localPath.substring(0, localPath.indexOf("/classes"));
        localPath = localPath + "/" + "ftp" + "/";
        LOG.debug("doFtpData localPath" + localPath);
        // 解析前一天的visitor
        Date date = new Date();
        String dateKey = Util.dateFormat(date, Params.YYYYMMDD);
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
                        String[] values = (tempString+"|end").replace("|", "_").split("_");
                        for (int i = 0; i < names.length; i++) {
                            // {"date","ipv4","ipv6","acr","eci","gender","age","localAddress","homeAddress",
                            // "homeAddressCI","workAddress","workAddressCI","expendAbility"};
                            switch (i) {
                            case 1: // ipv4
                                jsonObject.put(names[i], Util.isIp(values[i])?values[i]:"");
                                break;
                            case 3: // acr
                            case 4: // eci
                                jsonObject.put(names[i], values[i].length()<200?values[i]:"error");
                                break;
                            case 5: // gender
                            case 6: // age
                            case 12: // expendAbility
                                jsonObject.put(names[i], "".equals(values[i])?"不详":values[i]);
                                break;
//                            case 7: // localAddress
//                                break;
//                            case 8: // homeAddress
//                            case 10: // workAddress
//                                break;
                            default:
                                jsonObject.put(names[i], values[i]);
                                break;
                            }
                        }
                        jsonObject.put("time", dateKey);
                        list.add(jsonObject);
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
                visitorDao.saveData(list);
            }
            // LOG.debug("VisitorController~插入Visitor数据条数:" + num);
        } else {
            LOG.debug("doFtpData downFtpFile failed result " + ftpResult);
        }
        return "today:" + ftpResult;
    }

}
