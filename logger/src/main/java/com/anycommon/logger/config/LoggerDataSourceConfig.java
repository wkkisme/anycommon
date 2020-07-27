package com.anycommon.logger.config;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 日志数据源配置
 *
 * @author wangkai
 * @date 2020/7/23
 */

@Configuration
@PropertySource(value = {"classpath:logger-${spring.profiles.active}.properties"})
@MapperScan(basePackages = "com.anycommon.logger.mapper", sqlSessionFactoryRef = "loggerSqlSessionFactory")
@ComponentScan("com.anycommon.logger")
public class LoggerDataSourceConfig {

    @Resource
    private ConfigurableEnvironment environment;



    public DataSource getDataSource()  {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(environment.getProperty("platform.logger.datasource.druid.url"));
//        hikariDataSource.setDataSourceClassName(environment.getProperty("platform.logger.datasource.druid.type"));
        hikariDataSource.setUsername(environment.getProperty("platform.logger.datasource.druid.username"));
        hikariDataSource.setPassword(environment.getProperty("platform.logger.datasource.druid.password"));
        hikariDataSource.setDriverClassName(environment.getProperty("platform.logger.datasource.druid.driverClassName"));
        return hikariDataSource;
    }



    @Bean("loggerSqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());  // 设置数据源bean
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:loggermapper/*.xml"));  // 设置mapper文件路径
        return sessionFactory.getObject();
    }


}
