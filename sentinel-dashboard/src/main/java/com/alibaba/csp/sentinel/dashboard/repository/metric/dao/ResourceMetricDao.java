package com.alibaba.csp.sentinel.dashboard.repository.metric.dao;

import com.alibaba.csp.sentinel.dashboard.repository.metric.po.ResourceMetricPo;

import java.util.List;

/**
 * @author suming
 * @since 2026/3/26 21:32
 */
public interface ResourceMetricDao {
    void save(ResourceMetricPo resourceMetric);

    List<ResourceMetricPo> find(Long id, long startTime, long endTime);
}
