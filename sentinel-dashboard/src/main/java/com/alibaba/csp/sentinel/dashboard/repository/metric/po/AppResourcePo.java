package com.alibaba.csp.sentinel.dashboard.repository.metric.po;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;

import java.util.Date;

/**
 * @author suming
 * @since 2026/3/26 21:26
 */
public class AppResourcePo {
    private Long id;
    private String app;
    private String resource;
    private Date createDateTime;

    public static AppResourcePo createBy(MetricEntity metric) {
        AppResourcePo appResourcePo = new AppResourcePo();
        // TODO 2026/3/26 mapping
        return appResourcePo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
}
