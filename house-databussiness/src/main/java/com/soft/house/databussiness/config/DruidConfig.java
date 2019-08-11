package com.soft.house.databussiness.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

    /**
     * 配置Druid数据源
     * @return
     */
    @ConfigurationProperties(prefix = "spring.druid")
    @Bean(initMethod = "init",destroyMethod = "close")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return dataSource;
    }

    /**
     * 统计相关监控信息配置
     * @return
     */
    @Bean
    public Filter statFilter(){
        StatFilter filter = new StatFilter();
        //设置慢SQL的时间，单位：毫秒
        filter.setSlowSqlMillis(5000);
        //slowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢
        filter.setLogSlowSql(true);
        //SQL合并配置
        filter.setMergeSql(true);
        return filter;
    }

    /**
     * 配置Druid监控页面访问地址
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }
}
