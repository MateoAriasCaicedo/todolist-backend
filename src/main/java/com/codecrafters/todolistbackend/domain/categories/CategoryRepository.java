package com.codecrafters.todolistbackend.domain.categories;

import static com.mongodb.client.model.Filters.eq;

import com.codecrafters.todolistbackend.db.DBCollections;
import com.codecrafters.todolistbackend.db.DBNames;
import com.codecrafters.todolistbackend.db.collections.CollectionsProvider;
import com.codecrafters.todolistbackend.db.collections.fields.DefaultCategoriesFields;
import com.codecrafters.todolistbackend.db.collections.fields.TaskFields;
import com.codecrafters.todolistbackend.db.collections.fields.UserFields;
import com.codecrafters.todolistbackend.db.filters.DBFilters;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
class CategoryRepository {

  private final CollectionsProvider collectionsProvider;

  private final MongoClient mongoClient;

  void createUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {
    var modifiedUsers =
        mongoClient
            .getDatabase(DBNames.TODO)
            .getCollection(DBCollections.USERS)
            .updateOne(
                DBFilters.equalUserID(userID), Updates.push(UserFields.CATEGORIES, category));

    if (modifiedUsers.getModifiedCount() == 0) throw new UserDoesNotExistsException(userID);
  }

  void deleteUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {

    var modifiedUsers =
        collectionsProvider
            .usersCollection()
            .updateOne(
                DBFilters.equalUserID(userID), Updates.pull(UserFields.CATEGORIES, category));

    collectionsProvider
        .usersCollection()
        .updateOne(
            Filters.and(
                Filters.eq(UserFields.ID, userID),
                Filters.eq(TaskFields.FULLY_QUALIFIED_CATEGORY, category)),
            Updates.pull(UserFields.TASKS, new Document(TaskFields.CATEGORY, category)));

    if (modifiedUsers.getModifiedCount() == 0) throw new UserDoesNotExistsException(userID);
  }

  List<String> getAllUserCategories(ObjectId userID) throws UserDoesNotExistsException {
    List<String> userCategories = new LinkedList<>();

    var user =
        mongoClient
            .getDatabase(DBNames.TODO)
            .getCollection(DBCollections.USERS)
            .find(DBFilters.equalUserID(userID))
            .first();

    if (user == null) {
      throw new UserDoesNotExistsException(userID);
    }

    userCategories.addAll(user.getList(UserFields.CATEGORIES, String.class));

    var defaultCategories =
        mongoClient
            .getDatabase(DBNames.TODO)
            .getCollection(DBCollections.DEFAULT_CATEGORIES)
            .find()
            .first();

    userCategories.addAll(
        defaultCategories.getList(DefaultCategoriesFields.DEFAULT_CATEGORIES, String.class));

    return userCategories;
  }
}
