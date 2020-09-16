package ru.eternity074.search.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@PropertySource(value = {"classpath:application.properties"})
public class AppConfig {

	private Environment env;

	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();

		hikariConfig.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		hikariConfig.setJdbcUrl(env.getRequiredProperty("jdbc.url"));
		hikariConfig.setUsername(env.getRequiredProperty("jdbc.username"));
		hikariConfig.setPassword(env.getRequiredProperty("jdbc.password"));
		hikariConfig.setMinimumIdle(Integer.parseInt(env.getRequiredProperty("hikari.minimumIdle")));
		hikariConfig.setMaximumPoolSize(Integer.parseInt(env.getRequiredProperty("hikari.maximumPoolSize")));
		hikariConfig.setIdleTimeout(Integer.parseInt(env.getRequiredProperty("hikari.idleTimeout")));
		hikariConfig.setConnectionTestQuery(env.getRequiredProperty("hikari.connectionTestQuery"));

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

		props.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		props.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		props.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
		props.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
		props.put("hibernate.hbm2ddl.import_files", env.getRequiredProperty("hibernate.hbm2ddl.import_files"));

		return props;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager jtm = new JpaTransactionManager();

		jtm.setEntityManagerFactory(emf);

		return jtm;
	}

	@Autowired
	public void setEnv(Environment env) {
		this.env = env;
	}

}
