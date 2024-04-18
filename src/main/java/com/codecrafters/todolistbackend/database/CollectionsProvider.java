package com.codecrafters.todolistbackend.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

/** The {@link Component} that provides the database collections. */
@Component
public class CollectionsProvider {

  private static final String TODO_DB = "todo";

  private static final String USERS_COLLECTION = "users";

  private static final String DEFAULT_CATEGORIES_COLLECTION = "defaultCategories";

  private final MongoClient mongoClient;

  public CollectionsProvider(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  /**
   * Returns the {@link MongoCollection} corresponding to the {@code users} collection in the {@code
   * todo} database.
   *
   * @return The {@code users} collection.
   */
  public MongoCollection<Document> users() {
    return mongoClient.getDatabase(TODO_DB).getCollection(USERS_COLLECTION);
  }

  /**
   * Returns the {@link MongoCollection} corresponding to the {@code defaultCategories} collection
   * in the {@code todo} database.
   *
   * @return The {@code defaultCategories} collection.
   */
  public MongoCollection<Document> defaultCategories() {
    return mongoClient.getDatabase(TODO_DB).getCollection(DEFAULT_CATEGORIES_COLLECTION);
  }
}
