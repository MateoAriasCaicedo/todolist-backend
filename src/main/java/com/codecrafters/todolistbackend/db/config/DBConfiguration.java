package com.codecrafters.todolistbackend.db.config;

import com.codecrafters.todolistbackend.db.DBCredentials;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
class DBConfiguration {

  @Bean
  MongoClient mongoClient() {
    return MongoClients.create(DBCredentials.DB_URL);
  }
}
