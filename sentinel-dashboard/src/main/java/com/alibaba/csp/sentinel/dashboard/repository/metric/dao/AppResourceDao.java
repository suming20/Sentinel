package com.alibaba.csp.sentinel.dashboard.repository.metric.dao;

import com.alibaba.csp.sentinel.dashboard.repository.metric.po.AppResourcePo;

import java.util.List;

/**
 * @author suming
 * @since 2026/3/26 21:31
 */
public interface AppResourceDao {
    AppResourcePo findIdByAppAndResource(String app, String resource);

    AppResourcePo save(AppResourcePo by);

    List<AppResourcePo> findAllResourceByApp(String app);
}
