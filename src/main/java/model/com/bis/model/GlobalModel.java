package com.bis.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: GlobalModel
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author gyr
 * @date 2018年7月24日 下午3:27:15
 * 
 */
public class GlobalModel {
    private Map<Integer, MapBorderModel> mapBorderMap = new HashMap<>();

    public Map<Integer, MapBorderModel> getMapBorderMap() {
        return mapBorderMap;
    }

    public void setMapBorderMap(Map<Integer, MapBorderModel> mapBorderMap) {
        this.mapBorderMap = mapBorderMap;
    }

}
