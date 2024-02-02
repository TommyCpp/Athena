package com.athena;

import com.athena.util.interceptor.RateLimitInterceptor;
import com.athena.util.publication.search.PublicationSearchParamResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.List;


@Configuration
@EnableJpaRepositories(basePackages = "com.athena.repository.jpa")
@SpringBootApplication
@PropertySource("classpath:/config.properties")
//@PropertySource("classpath:/application.properties")
@EnableMongoRepositories(basePackages = "com.athena.repository.mongo")
public class AthenaApplication extends WebMvcConfigurerAdapter {

    private final Environment env;

    private final RateLimitInterceptor rateLimitInterceptor;
    private final ObjectMapper objectMapper;

    @Autowired
    public AthenaApplication(Environment env, RateLimitInterceptor rateLimitInterceptor, ObjectMapper objectMapper) {
        this.env = env;
        this.rateLimitInterceptor = rateLimitInterceptor;
        this.objectMapper = objectMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(AthenaApplication.class, args);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setPackagesToScan("com.athena.model", "com.athena.security.model");
        return emfb;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        return adapter;
    }

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(env.getProperty("spring.datasource.url") + "?useUnicode=yes&characterEncoding=UTF-8");
        ds.setUsername(env.getProperty("spring.datasource.username"));
        ds.setPassword(env.getProperty("spring.datasource.password"));
        return ds;
    }


    @Bean
    static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MongoClientOptions mongoClientOptions() {
        MongoClientOptions options = new MongoClientOptions.Builder().socketKeepAlive(true).build();
        return options;
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("*"); //allow any origins.
//            }
//        };
//    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.rateLimitInterceptor).addPathPatterns(this.env.getProperty("web.url.prefix") + "/books");
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        Integer searchCount = Integer.valueOf(this.env.getProperty("search.default.count"));
        argumentResolvers.add(new PublicationSearchParamResolver(searchCount, this.objectMapper));
    }
}
