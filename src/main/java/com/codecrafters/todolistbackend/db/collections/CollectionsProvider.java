package com.codecrafters.todolistbackend.db.collections;

import com.codecrafters.todolistbackend.db.DBCollections;
import com.codecrafters.todolistbackend.db.DBNames;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class CollectionsProvider {

  private final MongoClient mongoClient;

  public CollectionsProvider(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public MongoCollection<Document> usersCollection() {
    return mongoClient.getDatabase(DBNames.TODO).getCollection(DBCollections.USERS);
  }

  public MongoCollection<Document> getDefaultCategoriesCollection() {
    return mongoClient.getDatabase(DBNames.TODO).getCollection(DBCollections.DEFAULT_CATEGORIES);
  }
}
