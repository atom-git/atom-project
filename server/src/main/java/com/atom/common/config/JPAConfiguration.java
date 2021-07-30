package com.atom.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author zr
 * @description 数据源及事务配置
 * @date 2020/3/10
 */
@Configuration
public class JPAConfiguration {

    @Resource
    private Environment environment;

    /**
     * 数据源
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(environment.getProperty("spring.datasource.url"));
        druidDataSource.setUsername(environment.getProperty("spring.datasource.username"));
        druidDataSource.setPassword(environment.getProperty("spring.datasource.password"));
        druidDataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        return druidDataSource;
    }

    /**
     * session工厂
     * @param dataSource 数据源
     * @return 返回session工厂
     */
    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionFactoryBuilder.scanPackages("com.atom.server");
        sessionFactoryBuilder.setProperty(AvailableSettings.SHOW_SQL, environment.getProperty("spring.jpa.show-sql"));
        return sessionFactoryBuilder.buildSessionFactory();
    }

    /**
     * 配置hibernate事务管理器
     * @return 返回事务管理器
     */
    @Bean
    public JpaTransactionManager transactionManager(DataSource dataSource) {
        return new JpaTransactionManager(sessionFactory(dataSource));
    }

}
