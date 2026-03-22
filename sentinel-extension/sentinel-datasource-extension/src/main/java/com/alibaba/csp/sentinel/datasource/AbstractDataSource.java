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
package com.alibaba.csp.sentinel.datasource;

import com.alibaba.csp.sentinel.property.DynamicSentinelProperty;
import com.alibaba.csp.sentinel.property.SentinelProperty;

/**
 * The abstract readable data source provides basic functionality for loading and parsing config.
 *
 * @param <S> source data type
 * @param <T> target data type
 * @author Carpenter Lee
 * @author Eric Zhao
 * 要求子类必须提供一个Converter接口。将S类型的实例转为T类型的实例；例如将装载yaml配置的FlowRuleProps实例转为FlowRule集合
 */
public abstract class AbstractDataSource<S, T> implements ReadableDataSource<S, T> {

    protected final Converter<S, T> parser;
    protected final SentinelProperty<T> property;

    public AbstractDataSource(Converter<S, T> parser) {
        if (parser == null) {
            throw new IllegalArgumentException("parser can't be null");
        }
        this.parser = parser;
        // 构造方法中创建DynamicSentinelProperty实例，子类无需再构造方法中创建
        this.property = new DynamicSentinelProperty<T>();
    }

    @Override
    public T loadConfig() throws Exception {
        return loadConfig(readSource());
    }

    public T loadConfig(S conf) throws Exception {
        T value = parser.convert(conf);
        return value;
    }

    @Override
    public SentinelProperty<T> getProperty() {
        return property;
    }
}
