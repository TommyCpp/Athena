package com.athena.config;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

/**
 * Created by Tommy on 2017/8/21.
 */
@Configuration
@Profile("fakeMongo")
public class TestMongoApplication {
    private static final String DATABASE_NAME = "logs";

    public @Bean Mongo mongo() throws UnknownHostException, MongoException {
        Mongo mongo = new Mongo("localhost");
        return mongo;
    }

    public @Bean
    MongoTemplate mongoTemplate() throws UnknownHostException, MongoException {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), DATABASE_NAME);
        return mongoTemplate;
    }

}
