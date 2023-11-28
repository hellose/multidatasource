package study.spring.multidatasource.config;

import javax.sql.DataSource;

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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@MapperScan(value = "study.spring.multidatasource", sqlSessionFactoryRef = "secondarySqlSessionFactory", annotationClass = MapperSecondary.class)
@Slf4j
public class SecondaryConfig {

    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${mybatis.mapper-locations-secondary}")
    private String mapperLocations;

    @Value("${mybatis.configuration.map-underscore-to-camel-case}")
    private boolean mapUnderscoreToCamelCase;

    @Bean
    @ConfigurationProperties(prefix = "database.secondary")
    public DataSource secondaryDataSource() {
        log.debug("create bean secondaryDataSource start");
        DataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        log.debug("create bean secondaryDataSource end");

        return dataSource;
    }

    @Bean
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondaryDataSource") DataSource dataSource) throws Exception {
        log.debug("create bean secondarySqlSessionFactory start");
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
        log.debug("create bean secondarySqlSessionFactory end");

        return sqlSessionFactory;
    }

    @Bean
    public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("secondarySqlSessionFactory") SqlSessionFactory SqlSessionFactory) {
        log.debug("create bean secondarySqlSessionTemplate start");
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(SqlSessionFactory);
        log.debug("create bean secondarySqlSessionTemplate end");

        return sqlSessionTemplate;
    }

    @Bean
    public DataSourceTransactionManager secondaryTransactionManager(@Qualifier("secondaryDataSource") DataSource dataSource) {
        log.debug("create bean secondaryTransactionManager start");
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        log.debug("create bean secondaryTransactionManager end");

        return transactionManager;
    }

}
