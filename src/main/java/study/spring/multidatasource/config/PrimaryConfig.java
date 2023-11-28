package study.spring.multidatasource.config;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@MapperScan(
	value = "study.spring.multidatasource",
	sqlSessionFactoryRef = "primarySqlSessionFactory",
	annotationClass = Mapper.class)
@Slf4j
public class PrimaryConfig {

	@Value("${mybatis.type-aliases-package}")
	private String typeAliasesPackage;

	@Value("${mybatis.mapper-locations-primary}")
	private String mapperLocations;

	@Value("${mybatis.configuration.map-underscore-to-camel-case}")
	private boolean mapUnderscoreToCamelCase;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "database.primary")
	public DataSource primaryDataSource() {
		log.debug("create bean primaryDataSource start");
		DataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
		log.debug("create bean primaryDataSource end");

		return dataSource;
	}

	@Bean
	@Primary
	public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource)
			throws Exception {
		log.debug("create bean primarySqlSessionFactory start");
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

		Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
		sqlSessionFactoryBean.setMapperLocations(resources);

		org.apache.ibatis.session.Configuration mybatisConfig = new org.apache.ibatis.session.Configuration();
		// database column (snake-case) -> java field (camelCase)
		mybatisConfig.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
		sqlSessionFactoryBean.setConfiguration(mybatisConfig);

		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		log.debug("create bean primarySqlSessionFactory end");

		return sqlSessionFactory;
	}

	@Bean
	@Primary
	public SqlSessionTemplate primarySqlSessionTemplate(
			@Qualifier("primarySqlSessionFactory") SqlSessionFactory SqlSessionFactory) {
		log.debug("create bean primarySqlSessionTemplate start");
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(SqlSessionFactory);
		log.debug("create bean primarySqlSessionTemplate end");

		return sqlSessionTemplate;
	}

	@Bean
	@Primary
	public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
		log.debug("create bean primaryTransactionManager start");
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		log.debug("create bean primaryTransactionManager end");

		return transactionManager;
	}

}
