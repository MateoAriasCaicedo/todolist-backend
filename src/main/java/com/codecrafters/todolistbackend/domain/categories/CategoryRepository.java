package com.codecrafters.todolistbackend.domain.categories;

import com.codecrafters.todolistbackend.database.CollectionsProvider;
import com.codecrafters.todolistbackend.database.FiltersProvider;
import com.codecrafters.todolistbackend.database.PullsProvider;
import com.codecrafters.todolistbackend.database.fields.DefaultCategoriesFields;
import com.codecrafters.todolistbackend.database.fields.TaskFields;
import com.codecrafters.todolistbackend.database.fields.UserFields;
import com.codecrafters.todolistbackend.domain.categories.exceptions.CategoryDoesNotExistsException;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

class CategoryRepository {

  private static List<String> defaultCategoriesFromDocument( Document defaultCategories)
      throws DefaultCategoriesNotFoundException {

    List<String> output =
        defaultCategories.getList(DefaultCategoriesFields.DEFAULT_CATEGORIES, String.class);

    if (output == null) {
      throw new DefaultCategoriesNotFoundException();
    }

    return output;
  }

  private static List<String> userCategoriesFromDocument( Document user)
      throws UserDoesNotContainsCategoriesFieldException {

    List<String> userCategories = user.getList(UserFields.CATEGORIES, String.class);

    if (userCategories == null) {
      ObjectId userID = user.getObjectId(UserFields.ID);
      throw new UserDoesNotContainsCategoriesFieldException(userID);
    }

    return userCategories;
  }

  private static Optional<Document> getUser(ObjectId userID) {
    Document user = CollectionsProvider.users().find(FiltersProvider.equalUserID(userID)).first();
    return user != null ? Optional.of(user) : Optional.empty();
  }

  private static void deleteTasksByCategory(String category) {
    CollectionsProvider.users()
        .updateOne(
            FiltersProvider.tasksCategoryFilter(category),
            Updates.pull(UserFields.TASKS, new Document(TaskFields.CATEGORY, category)));
  }

  void createUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {

    UpdateResult modifiedUsers =
        CollectionsProvider.users()
            .updateOne(
                FiltersProvider.equalUserID(userID), Updates.push(UserFields.CATEGORIES, category));

    if (modifiedUsers.getModifiedCount() == 0) {
      throw new UserDoesNotExistsException(userID);
    }
  }

  void deleteUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {

    if (getUser(userID).isEmpty()) {
      throw new UserDoesNotExistsException(userID);
    }

    UpdateResult modifiedUsers =
        CollectionsProvider.users()
            .updateOne(
                FiltersProvider.equalUserID(userID), PullsProvider.pullUserCategory(category));

    if (modifiedUsers.getModifiedCount() == 0) {
      throw new CategoryDoesNotExistsException(category);
    }

    deleteTasksByCategory(category);
  }

  List<String> getAllUserCategories(ObjectId userID)
      throws UserDoesNotExistsException,
          DefaultCategoriesNotFoundException,
          UserDoesNotContainsCategoriesFieldException {


    Document defaultCategoriesDocument = CollectionsProvider.defaultCategories().find().first();

    if (defaultCategoriesDocument == null) {
      throw new DefaultCategoriesNotFoundException();
    }

    List<String> defaultCategories = defaultCategoriesFromDocument(defaultCategoriesDocument);
    List<String> userCategories = getUserCategories(userID);

    int allCategoriesCount = userCategories.size() + defaultCategories.size();
    List<String> allCategories = new ArrayList<>(allCategoriesCount);

    allCategories.addAll(defaultCategories);
    allCategories.addAll(userCategories);

    return allCategories;
  }

  public List<String> getUserCategories(ObjectId userID) {

    Optional<Document> user = getUser(userID);

    if (user.isEmpty()) {
      throw new UserDoesNotExistsException(userID);
    }

    return userCategoriesFromDocument(user.get());
  }
}
