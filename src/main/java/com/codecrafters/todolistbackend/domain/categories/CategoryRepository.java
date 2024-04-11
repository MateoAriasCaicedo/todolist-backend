package com.codecrafters.todolistbackend.domain.categories;

import com.codecrafters.todolistbackend.db.DBCollections;
import com.codecrafters.todolistbackend.db.DBNames;
import com.codecrafters.todolistbackend.db.collections.fields.UserFields;
import com.codecrafters.todolistbackend.db.filters.DBFilters;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

  private final MongoClient mongoClient;

  public CategoryRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  void createUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {
    var modifiedUsers =
        mongoClient
            .getDatabase(DBNames.TODO)
            .getCollection(DBCollections.USERS)
            .updateOne(
                DBFilters.equalUserIDFilter(userID), Updates.push(UserFields.CATEGORIES, category));

    if (modifiedUsers.getModifiedCount() == 0) throw new UserDoesNotExistsException(userID);
  }

  void deleteUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {
    var modifiedUsers =
        mongoClient
            .getDatabase(DBNames.TODO)
            .getCollection(DBCollections.USERS)
            .updateOne(
                DBFilters.equalUserIDFilter(userID), Updates.pull(UserFields.CATEGORIES, category));

    if (modifiedUsers.getModifiedCount() == 0) throw new UserDoesNotExistsException(userID);
  }
}
