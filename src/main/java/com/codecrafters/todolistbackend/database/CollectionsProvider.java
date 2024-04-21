package com.codecrafters.todolistbackend.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class CollectionsProvider {

  private static final MongoClient mongoClient =
      MongoClients.create(DatabaseConfiguration.databaseURL);

  private CollectionsProvider() {}

  public static MongoCollection<Document> users() {
    return mongoClient.getDatabase("todo").getCollection("users");
  }

  public static MongoCollection<Document> defaultCategories() {
    return mongoClient.getDatabase("todo").getCollection("defaultCategories");
  }
}
