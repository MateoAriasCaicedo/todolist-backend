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
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
class CategoryRepository {

  private static List<String> defaultCategoriesFromDocument(Document defaultCategories)
      throws DefaultCategoriesNotFoundException {

    List<String> output =
        defaultCategories.getList(DefaultCategoriesFields.DEFAULT_CATEGORIES, String.class);

    if (output == null) {
      log.error("The default categories document does not contains the default categories field");
      throw new DefaultCategoriesNotFoundException();
    }

    return output;
  }

  private static List<String> userCategoriesFromDocument(Document user)
      throws UserDoesNotContainsCategoriesFieldException {

    List<String> userCategories = user.getList(UserFields.CATEGORIES, String.class);

    if (userCategories == null) {
      ObjectId userID = user.getObjectId(UserFields.ID);
      log.error("The user with ID {} does not contains the categories list", userID);
      throw new UserDoesNotContainsCategoriesFieldException(userID);
    }

    return userCategories;
  }

  private static Optional<Document> getUser(ObjectId userID) {
    Document user = CollectionsProvider.users().find(FiltersProvider.equalUserID(userID)).first();
    return user != null ? Optional.of(user) : Optional.empty();
  }

  void createUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {
    log.debug("Creating category {} for user with ID {}", category, userID);

    UpdateResult modifiedUsers =
        CollectionsProvider.users()
            .updateOne(
                FiltersProvider.equalUserID(userID), Updates.push(UserFields.CATEGORIES, category));

    if (modifiedUsers.getModifiedCount() == 0) {
      log.warn("The category creation failed because the user ID {} does not exist", userID);
      throw new UserDoesNotExistsException(userID);
    }
  }

  void deleteUserCategory(ObjectId userID, String category) throws UserDoesNotExistsException {
    log.debug("Deleting category {} for user with ID {}", category, userID);

    if (getUser(userID).isEmpty()) {
      log.warn("The category deletion failed because the user ID {} does not exist", userID);
      throw new UserDoesNotExistsException(userID);
    }

    UpdateResult modifiedUsers =
        CollectionsProvider.users()
            .updateOne(
                FiltersProvider.equalUserID(userID), PullsProvider.pullUserCategory(category));

    if (modifiedUsers.getModifiedCount() == 0) {
      log.warn("Attempt to delete nonexistent category {} was done by user {}", category, userID);
      throw new CategoryDoesNotExistsException(category);
    }

    log.info("Deleting the user tasks that are associated with the deleted category {}", category);
    deleteTasksByCategory(category);
  }

  void deleteTasksByCategory(String category) throws UserDoesNotExistsException {

    CollectionsProvider.users()
        .updateOne(
            FiltersProvider.tasksCategoryFilter(category),
            Updates.pull(UserFields.TASKS, new Document(TaskFields.CATEGORY, category)));
  }

  List<String> getAllUserCategories(ObjectId userID)
      throws UserDoesNotExistsException,
          DefaultCategoriesNotFoundException,
          UserDoesNotContainsCategoriesFieldException {

    log.debug("Retrieving all categories for user with ID {}", userID);

    Document defaultCategoriesDocument = CollectionsProvider.defaultCategories().find().first();

    if (defaultCategoriesDocument == null) {
      log.error("The defaultCategories collection does not exists or is empty");
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
    log.debug("Retrieving user categories for user with ID {}", userID);

    Optional<Document> user = getUser(userID);

    if (user.isEmpty()) {
      log.warn("The categories retrieval failed because the user ID {} does not exist", userID);
      throw new UserDoesNotExistsException(userID);
    }

    return userCategoriesFromDocument(user.get());
  }
}
