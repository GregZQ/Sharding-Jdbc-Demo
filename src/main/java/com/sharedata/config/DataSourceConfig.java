package com.sharedata.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.sharedata.config.support.DataBaseSharding;
import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.api.config.TableRuleConfiguration;
import io.shardingsphere.core.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.core.api.config.strategy.StandardShardingStrategyConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@MapperScan(basePackages = {"com.sharedata.mapper"},sqlSessionFactoryRef = "userSqlSessionFactory")
public class DataSourceConfig {

    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean("db")
    @ConfigurationProperties(prefix = "datasource.db-test")
    public DataSource db(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean("db0")
    @ConfigurationProperties(prefix = "datasource.db-test0")
    public DataSource db1(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean("db1")
    @ConfigurationProperties(prefix = "datasource.db-test1")
    public DataSource db2(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean("shardingDataSource")
    public DataSource dataSource(@Qualifier("db")DataSource db,@Qualifier("db0") DataSource db0,
                                 @Qualifier("db1")DataSource db1) throws SQLException {

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemIndexRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderIndexRuleConfiguration());
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
                new StandardShardingStrategyConfiguration("user_id", new DataBaseSharding()));
        shardingRuleConfig.getBindingTableGroups().add("order_index,order_item_index,order,order_item");

        Map<String ,DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("db",db);
        dataSourceMap.put("db0", db0);
        dataSourceMap.put("db1", db1);

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap<>(), new Properties());
    }

    @Bean("userSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("shardingDataSource")DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("userDataSourceTransactionManage")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("shardingDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    TableRuleConfiguration getOrderIndexRuleConfiguration(){
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("order_index");
        result.setActualDataNodes("db.order_index");
        return result;
    }

    TableRuleConfiguration getOrderItemIndexRuleConfiguration(){
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("order_item_index");
        result.setActualDataNodes("db.order_item_index");
        return result;
    }

    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("order");
        result.setActualDataNodes("db${0..1}.order_${0..1}");
        result.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration(
                "user_id","order_$->{user_id%2}"));
        return result;
    }

    TableRuleConfiguration getOrderItemTableRuleConfiguration(){
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("order_item");
        result.setActualDataNodes("db${0..1}.order_item_${0..1}");
        result.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration(
                "user_id","order_item_$->{user_id%2}"));
        return result;
    }
}
