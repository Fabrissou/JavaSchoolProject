package org.javaschool.data.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.javaschool.data")
@PropertySource("classpath:/DataBase.properties")
@EnableTransactionManagement
@EnableJpaRepositories("org.javaschool.data.repository")
public class DataBaseConfig {
    private DataBaseProperties dataBaseProperties;

    public DataBaseConfig(DataBaseProperties dataBaseProperties) {
        this.dataBaseProperties = dataBaseProperties;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl(dataBaseProperties.getUrl() + dataBaseProperties.getName());
        dataSource.setUsername(dataBaseProperties.getUser());
        dataSource.setPassword(dataBaseProperties.getPass());
        dataSource.setMinIdle(10);
        dataSource.setMaxIdle(15);
        dataSource.setInitialSize(15);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean managerFactory = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        managerFactory.setDataSource(dataSource());
        managerFactory.setPackagesToScan("org.javaschool.data.model");
        managerFactory.setJpaProperties(jpaProperties());
        managerFactory.setJpaVendorAdapter(adapter);

        return managerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties jpaProperties() {
        Properties hibernateProp = new Properties();

        hibernateProp.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        hibernateProp.setProperty("hibernate.connection.release_mode", "after_transactional");
        hibernateProp.setProperty("hibernate.hbm2ddl.auto", "validate");
        hibernateProp.setProperty("hibernate.show_sql", "true");
        hibernateProp.setProperty("hibernate.format_sql", "true");

        return hibernateProp;
    }
}
