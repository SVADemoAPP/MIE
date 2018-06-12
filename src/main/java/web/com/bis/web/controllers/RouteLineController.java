/**   
 * @Title: RouteLineController.java 
 * @Package com.bis.web.controllers 
 * @Description: 动线图controller
 * @author labelCS   
 * @date 2017年6月21日 下午4:26:02 
 * @version V1.0   
 */
package com.bis.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bis.model.StatisticsModel;
import com.bis.service.DataAnalysisService;

/**
 * @ClassName: RouteLineController
 * @Description: 动线图controller
 * @author labelCS
 * @date 2017年6月21日 下午4:26:02 
 * 
 */
@Controller
@RequestMapping(value = "/route")
public class RouteLineController
{
    /** 
     * @Fields service : 用户店铺轨迹service
     */ 
    @Autowired
    private DataAnalysisService service;
   
    /** 
     * @Fields LOG : 日志句柄
     */ 
    private static final Logger LOG = Logger.getLogger(RouteLineController.class);

    /** 
     * @Title: getRouteData 
     * @Description: 获取用户店铺轨迹
     * @param startTime
     * @param endTime
     * @return 
     */
    @RequestMapping(value = "/api/getRouteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getRouteData(String mapId, String startTime, String endTime)
    {
        LOG.debug("getRouteData in:"+startTime+","+endTime);
        List<StatisticsModel> resultList = service.getOrderedPeopleRoute(mapId, startTime, endTime);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", resultList);
        LOG.debug("getRouteData out:"+resultList.size());
        return modelMap;
    }
}
