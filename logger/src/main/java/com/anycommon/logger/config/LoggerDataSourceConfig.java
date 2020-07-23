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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 日志数据源配置
 *
 * @author wangkai
 * @date 2020/7/23
 */

@Configuration
@PropertySource(value = {"classpath:logger-${spring.profiles.active}.properties"})
@MapperScan(basePackages = "com.anycommon.logger.mapper", sqlSessionFactoryRef = "loggerSqlSessionFactory")
public class LoggerDataSourceConfig {



    @Bean("loggerDataSource")
    @ConfigurationProperties(prefix = "platform.logger.datasource")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "loggerTransactionManager")
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("loggerDataSource") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("loggerSqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("loggerDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);  // 设置数据源bean
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:loggermapper/*.xml"));  // 设置mapper文件路径

        return sessionFactory.getObject();
    }


}
