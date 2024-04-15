package com.codecrafters.todolistbackend.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

  /** The database client for the todoapp database. */
  @Bean(name = "todoMongoClient")
  public MongoClient mongoClient() {
    return MongoClients.create("todo");
  }
}
