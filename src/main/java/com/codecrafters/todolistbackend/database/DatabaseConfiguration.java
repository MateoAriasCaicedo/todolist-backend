package com.codecrafters.todolistbackend.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

  @Value("${application.database.url}")
  private static String dbUrl;

  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create(dbUrl);
  }
}
