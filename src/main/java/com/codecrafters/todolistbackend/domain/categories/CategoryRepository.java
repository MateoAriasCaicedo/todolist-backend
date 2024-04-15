package com.codecrafters.todolistbackend.domain.categories;

import static com.mongodb.client.model.Filters.eq;

import com.codecrafters.todolistbackend.database.CollectionsProvider;
import com.codecrafters.todolistbackend.database.fields.DefaultCategoriesFields;
import com.codecrafters.todolistbackend.database.fields.TaskFields;
import com.codecrafters.todolistbackend.database.fields.UserFields;
import com.codecrafters.todolistbackend.database.FiltersProvider;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
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

  private final CollectionsProvider CollectionsProvider;

  void createUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {
    var modifiedUsers =
        CollectionsProvider
            .users()
            .updateOne(
                FiltersProvider.equalUserID(userID), Updates.push(UserFields.CATEGORIES, category));

    if (modifiedUsers.getModifiedCount() == 0) throw new UserDoesNotExistsException(userID);
  }

  void deleteUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {

    var modifiedUsers =
        CollectionsProvider
            .users()
            .updateOne(
                FiltersProvider.equalUserID(userID), Updates.pull(UserFields.CATEGORIES, category));

    if (modifiedUsers.getModifiedCount() == 0) throw new UserDoesNotExistsException(userID);

    deleteTasksByCategory(category);
  }

  void deleteTasksByCategory(String category) throws UserDoesNotExistsException {

    CollectionsProvider
        .users()
        .updateOne(
            FiltersProvider.tasksCategoryFilter(category),
            Updates.pull(UserFields.TASKS, new Document(TaskFields.CATEGORY, category)));
  }

  List<String> getAllUserCategories(ObjectId userID) throws UserDoesNotExistsException {
    List<String> userCategories = new LinkedList<>();

    var user =
        CollectionsProvider
            .users()
            .find(FiltersProvider.equalUserID(userID))
            .first();

    if (user == null) {
      throw new UserDoesNotExistsException(userID);
    }

    userCategories.addAll(user.getList(UserFields.CATEGORIES, String.class));

    var defaultCategories =
        CollectionsProvider
            .defaultCategories()
            .find()
            .first();

    userCategories.addAll(
        defaultCategories.getList(DefaultCategoriesFields.DEFAULT_CATEGORIES, String.class));

    return userCategories;
  }
}
