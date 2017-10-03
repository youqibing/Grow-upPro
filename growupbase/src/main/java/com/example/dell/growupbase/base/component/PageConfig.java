package com.example.dell.growupbase.base.component;

import com.example.dell.growupbase.base.component.ComponentConfig;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dell on 2017/9/15.
 */

public class PageConfig {
    int pageId;
    String pageName;
    final Map<String, ComponentConfig> configs = new LinkedHashMap<>(); // String表示组件的type，根据type查询组件信息

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

}
