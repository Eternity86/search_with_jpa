package ru.eternity074.search.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = { "ru.eternity074.search" })
@EnableTransactionManagement
public class AppConfig {
	
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		
		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3307/test");
		hikariConfig.setUsername("test");
		hikariConfig.setPassword("password");
		hikariConfig.setMinimumIdle(2);
		hikariConfig.setMaximumPoolSize(4);
		hikariConfig.setIdleTimeout(30_000);
		hikariConfig.setConnectionTestQuery("SELECT 1 FROM DUAL");
		
		return new HikariDataSource(hikariConfig);
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//		emf.setPersistenceUnitName("TestDB");
		
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setJpaDialect(new HibernateJpaDialect());
		emf.setPackagesToScan("ru.eternity074.search.model");
		emf.setJpaPropertyMap(hibernateJpaProperties());		
		
		return emf;
	}
	
	private Map<String, ?> hibernateJpaProperties() {
		Map<String, String> props = new HashMap<>();
		
		props.put("hibernate.hbm2ddl.auto", "create");
	    props.put("hibernate.show_sql", "true");
	    props.put("hibernate.format_sql", "true");
	    props.put("hibernate.hbm2ddl.import_files", "insert-data.sql");
	    
	    return props;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager jtm = new JpaTransactionManager();
		
		jtm.setEntityManagerFactory(emf);
		
		return jtm;
	}

}
