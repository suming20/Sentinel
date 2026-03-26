package com.alibaba.csp.sentinel.dashboard.repository.metric.po;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;

/**
 * @author suming
 * @since 2026/3/26 21:28
 */
public class ResourceMetricPo {
    private Long id;
    private Long resourceId;
    private long timestamp;
    private Long passQps;
    private Long successQps;
    private Long blockQps;
    private Long exceptionQps;
    private double rt;

    public static ResourceMetricPo createBy(MetricEntity metric) {
    }

    public Long getId() {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getPassQps() {
        return passQps;
    }

    public void setPassQps(Long passQps) {
        this.passQps = passQps;
    }

    public Long getSuccessQps() {
        return successQps;
    }

    public void setSuccessQps(Long successQps) {
        this.successQps = successQps;
    }

    public Long getBlockQps() {
        return blockQps;
    }

    public void setBlockQps(Long blockQps) {
        this.blockQps = blockQps;
    }

    public Long getExceptionQps() {
        return exceptionQps;
    }

    public void setExceptionQps(Long exceptionQps) {
        this.exceptionQps = exceptionQps;
    }

    public double getRt() {
        return rt;
    }

    public void setRt(double rt) {
        this.rt = rt;
    }

    public MetricEntity toDto() {
        MetricEntity metricEntity = new MetricEntity();
        // TODO 2026/3/26 mapping
        return metricEntity;
    }
}
