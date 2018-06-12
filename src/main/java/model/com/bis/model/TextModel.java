package com.bis.model;

import java.util.List;

/**
 * @ClassName: TextModel
 * @Description: text文档解析类
 * @author gyr
 * @date 2017年8月9日 下午3:03:08
 * 
 */
public class TextModel {
    private List<TextModel2> data;
    public List<TextModel2> getData() {
        return data;
    }
    public void setData(List<TextModel2> data) {
        this.data = data;
    }
    public class TextModel2 {
        private String name;
        private int age;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
    }
}
