/**   
 * @Title: DataAnalysisService.java 
 * @Package com.sva.service 
 * @Description: 数据分析预处理服务
 * @author labelCS   
 * @date 2017年6月20日 上午9:41:55 
 * @version V1.0   
 */
package com.bis.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bis.common.Util;
import com.bis.common.area.Point;
import com.bis.common.conf.Params;
import com.bis.dao.LocationDao;
import com.bis.dao.ShopDao;
import com.bis.dao.StatisticsDao;
import com.bis.model.LocationModel;
import com.bis.model.ShopModel;
import com.bis.model.StatisticsModel;

/**
 * @ClassName: DataAnalysisService
 * @Description: 数据分析预处理服务
 * @author labelCS
 * @date 2017年6月20日 上午9:41:55
 * 
 */
@Service
public class DataAnalysisService {
    /**
     * @Fields LOG : 日志句柄
     */
    private static final Logger LOG = Logger.getLogger(DataAnalysisService.class);

    /**
     * @Fields locDao : 位置数据访问dao
     */
    @Autowired
    private LocationDao locDao;

    /**
     * @Fields shopDao : 店铺信息访问dao
     */
    @Autowired
    private ShopDao shopDao;

    /**
     * @Fields statDao : 预处理信息访问dao
     */
    @Autowired
    private StatisticsDao statDao;

    public void calcPeopleRoute(String tableName) {
        // 计算结果列表
        List<StatisticsModel> list = new ArrayList<StatisticsModel>();
        // 获取所有的店铺信息
        List<ShopModel> shopList = shopDao.queryAllShop();
        // 获取所有的人
        List<String> allUserId = locDao.queryAllUserId(tableName);
        int cou = 0;
        List<LocationModel> locList = new ArrayList<>();
        String lastShopId = "";
        String newShopId = "";
        // 遍历用户，获取其店铺轨迹
        for (String userId : allUserId) {
            // 获取用户的位置信息
            locList = locDao.queryByUserId(userId, tableName);
            // 遍历每条位置信息，判断其所处的店铺
            for (LocationModel lm : locList) {
                newShopId = checkIsInShop(lm, shopList);
                // 判断用户是否进入新的店铺，是在加入结果队列，否则略过
                if (StringUtils.isNotEmpty(newShopId) && !newShopId.equals(lastShopId)) {
                    StatisticsModel temp = new StatisticsModel();
                    temp.setShopId(Integer.parseInt(newShopId));
                    temp.setUserId(userId);
                    temp.setTime(Util.dateFormat(lm.getTimestamp(), Params.YYYYMMDDHHMMSS));
                    list.add(temp);
                    lastShopId = newShopId;
                    if (list.size() > 99) {
                        statDao.savePeopleRoute(list);
                        list.clear();
                        LOG.debug("插入100条次数：" + (++cou));
                    }
                }
            }
        }
        // // 将结果列表写入数据库
        // if(list.size()>0){
        // statDao.savePeopleRoute(list);
        // }
        if (list.size() > 0) {
            statDao.savePeopleRoute(list);
            LOG.debug("插入剩余零头长度：" + list.size());
        }
        LOG.debug("人数：" + allUserId.size() + "，店铺数：" + shopList.size() + "，总入库记录数：" + ((cou * 100) + list.size()));
    }

    /**
     * @Title: getOrderedPeopleRoute
     * @Description: 获取指定时间段内的排序后的用户店铺轨迹
     * @param mapId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<StatisticsModel> getOrderedPeopleRoute(String mapId, String startTime, String endTime) {
        return statDao.getOrderedPeopleRoute(mapId, startTime, endTime);
    }

    /**
     * @Title: checkIsInShop
     * @Description: 判断给定位置是否在某个店铺内，是则返回店铺id，否则返回空字符串
     * @param lm
     * @param shopList
     * @return
     */
    private String checkIsInShop(LocationModel lm, List<ShopModel> shopList) {
        String shopId = "";
        // 遍历店铺，判断当前位置在哪个店铺内
        Point tempPoint=new Point(0D, 0D);
        for (ShopModel sm : shopList) {
            tempPoint.setX(lm.getX()/10.0D);
            tempPoint.setY(lm.getY()/10.0D);
            if (lm.getMapId().equals(sm.getMapId()) && Util.isInArea(tempPoint, sm.getPointsArray())) {
                shopId = String.valueOf(sm.getId());
                break;
            }
        }
        return shopId;
    }
}
