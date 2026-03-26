package com.alibaba.csp.sentinel.dashboard.repository.metric;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.alibaba.csp.sentinel.dashboard.repository.metric.dao.AppResourceDao;
import com.alibaba.csp.sentinel.dashboard.repository.metric.dao.ResourceMetricDao;
import com.alibaba.csp.sentinel.dashboard.repository.metric.po.AppResourcePo;
import com.alibaba.csp.sentinel.dashboard.repository.metric.po.ResourceMetricPo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author suming
 * @since 2026/3/26 21:25
 */
// @Component
public class MysqlMetricRepository implements MetricsRepository<MetricEntity> {

    @Resource
    private AppResourceDao appResourceDao;

    @Resource
    private ResourceMetricDao resourceMetricDao;

    private AppResourcePo ensureAppResourceExist(MetricEntity metric) {
        AppResourcePo appResource = appResourceDao.findIdByAppAndResource(metric.getApp(), metric.getResource());
        if (appResource == null) {
            return appResourceDao.save(AppResourcePo.createBy(metric));
        }
        return appResource;
    }

    @Override
    public void save(MetricEntity metric) {
        AppResourcePo appResource = ensureAppResourceExist(metric);
        ResourceMetricPo resourceMetric = ResourceMetricPo.createBy(metric);
        resourceMetric.setResourceId(appResource.getId());
        resourceMetricDao.save(resourceMetric);
    }

    @Override
    public void saveAll(Iterable<MetricEntity> metrics) {
        for (MetricEntity metric : metrics) {
            save(metric);
        }
    }

    @Override
    public List<MetricEntity> queryByAppAndResourceBetween(String app, String resource, long startTime, long endTime) {
        AppResourcePo appResource = appResourceDao.findIdByAppAndResource(app, resource);
        List<ResourceMetricPo> metricPos = resourceMetricDao.find(appResource.getId(), startTime, endTime);
        return metricPos.parallelStream().map(ResourceMetricPo::toDto)
                .peek(item -> {
                    item.setApp(app);
                    item.setResource(resource);
                }).collect(Collectors.toList());
    }

    @Override
    public List<String> listResourcesOfApp(String app) {
        List<AppResourcePo> appResourcePos = appResourceDao.findAllResourceByApp(app);
        return appResourcePos.parallelStream()
                .map(AppResourcePo::getResource)
                .collect(Collectors.toList());
    }
}
