package com.sharedata.config.support;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

//数据表分片策略
public class TableSharding implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        for (String s: collection) {
            if (s.endsWith(preciseShardingValue.getValue()%2 +"")){
                return s;
            }
        }
        throw new IllegalArgumentException();
    }
}
