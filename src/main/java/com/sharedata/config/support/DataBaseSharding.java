package com.sharedata.config.support;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

//数据库分片策略
public class DataBaseSharding implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        for (String s : collection) {
            Long value = preciseShardingValue.getValue()%4;
            if (value <= 1){
                value = Long.valueOf(0);
            }else{
                value = Long.valueOf(1);
            }
            if(s.endsWith(value+"")){
                return s;
            }

        }
        throw new IllegalArgumentException();
    }
}
