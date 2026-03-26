/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.node.metric;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.alibaba.csp.sentinel.Constants;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.node.ClusterNode;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.clusterbuilder.ClusterBuilderSlot;

/**
 * @author jialiang.linjl
 * 负责将资源指标数据输出到metric日志文件中的任务
 */
public class MetricTimerListener implements Runnable {

    private static final MetricWriter metricWriter = new MetricWriter(SentinelConfig.singleMetricFileSize(),
        SentinelConfig.totalMetricFileCount());

    @Override
    public void run() {
        Map<Long, List<MetricNode>> maps = new TreeMap<>();
        for (Entry<ResourceWrapper, ClusterNode> e : ClusterBuilderSlot.getClusterNodeMap().entrySet()) {
            ClusterNode node = e.getValue();
            Map<Long, MetricNode> metrics = node.metrics();
            aggregate(maps, metrics, node);
        }
        // ENTRY_NODE统计的是整个应用所有资源的指标数据
        aggregate(maps, Constants.ENTRY_NODE.metrics(), Constants.ENTRY_NODE);
        if (!maps.isEmpty()) {
            for (Entry<Long, List<MetricNode>> entry : maps.entrySet()) {
                try {
                    metricWriter.write(entry.getKey(), entry.getValue());
                } catch (Exception e) {
                    RecordLog.warn("[MetricTimerListener] Write metric error", e);
                }
            }
        }
    }
    // 实现按照时间戳聚合资源指标数据，并将数据写入局部maps中，一般maps只会有一个于元素
    private void aggregate(Map<Long, List<MetricNode>> maps, Map<Long, MetricNode> metrics, ClusterNode node) {
        for (Entry<Long, MetricNode> entry : metrics.entrySet()) {
            long time = entry.getKey();
            MetricNode metricNode = entry.getValue();
            metricNode.setResource(node.getName());
            metricNode.setClassification(node.getResourceType());
            maps.computeIfAbsent(time, k -> new ArrayList<MetricNode>());
            List<MetricNode> nodes = maps.get(time);
            nodes.add(entry.getValue());
        }
    }

}
