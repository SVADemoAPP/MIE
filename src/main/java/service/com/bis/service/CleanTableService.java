/**   
 * @Title: CleanTableService.java 
 * @Package com.sva.service 
 * @Description: 清理数据库服务
 * @author labelCS   
 * @date 2018年7月6日 上午9:41:55 
 * @version V1.0   
 */
package com.bis.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.common.Util;
import com.bis.common.conf.Params;
import com.bis.dao.StatisticsDao;

/**
 * @ClassName: CleanTableService
 * @Description: 清理数据库服务
 * @author labelCS
 * @date 2018年7月6日 上午9:41:55
 * 
 */
@Service
public class CleanTableService {
    /**
     * @Fields LOG : 日志句柄
     */
    private static final Logger LOG = Logger.getLogger(CleanTableService.class);

    /**
     * @Fields statDao : 预处理信息访问dao
     */
    @Autowired
    private StatisticsDao statDao;
    
    @Value("${sva.period}")
    private int period;
    
    @Value("${mysql.db}")
    private String schema;

    public void cleanTableInDb() {
    	// 获取需要删除的最早日期
    	Date current = new Date();
    	Calendar c = Calendar.getInstance();
        c.setTime(current);
        c.add(Calendar.DATE, 0 - period);
        String deleteFrom = Params.LOCATION + Util.dateFormat(c.getTime(), Params.YYYYMMDD);
    	
        // 一、清除location表
        List<String> tbList = statDao.findTableForDel(deleteFrom, schema);
        for(String tableName : tbList){
        	String sql = "drop table " + tableName + ";";
        	statDao.doUpdate(sql);
        }
        
    	// 二、清除visitor表
        String deleteDate = Util.dateFormat(c.getTime(), Params.YYYYMMDD2);
        String tmpSql = "DELETE FROM bi_visitor WHERE time < '" + deleteDate + "';";
		statDao.doUpdate(tmpSql);
        LOG.debug("删除成功：数据表数->" + tbList.size());
    }
}
