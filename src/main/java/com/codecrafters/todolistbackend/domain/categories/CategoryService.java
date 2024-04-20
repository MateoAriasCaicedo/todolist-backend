package com.codecrafters.todolistbackend.domain.categories;

import com.codecrafters.todolistbackend.domain.categories.exceptions.CategoryDoesNotExistsException;
import com.codecrafters.todolistbackend.domain.categories.exceptions.DuplicateCategoryException;
import com.codecrafters.todolistbackend.domain.categories.exceptions.EmptyCategoryIdentifierException;
import com.codecrafters.todolistbackend.exceptions.ServerSideErrorException;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class CategoryService {

  private final CategoryRepository categoryRepository;

  CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  void createUserCategory(ObjectId userID, String category)
      throws UserDoesNotExistsException, EmptyCategoryIdentifierException {
    log.debug("Creating category {} for user with ID {}", category, userID);

    if (category.isEmpty()) {
      log.warn("A user with ID {} tried to create a category with empty name", userID);
      throw new EmptyCategoryIdentifierException();
    }

    if (getUserCategories(userID).contains(category)) {
      log.warn("A user with ID {} tried to create a duplicate category {}", userID, category);
      throw new DuplicateCategoryException(category);
    }

    categoryRepository.createUserCategory(userID, category);
  }

  List<String> getUserCategories(ObjectId userID) {
    return categoryRepository.getUserCategories(userID);
  }

  boolean userHasCategory(ObjectId userID, String category) throws UserDoesNotExistsException {
    log.debug("Retrieving information of weather user {} has the category {}", userID, category);
    try {
      return getAllUserCategories(userID).contains(category);
    } catch (UserDoesNotExistsException exception) {
      log.warn("The query failed because the user with ID {} does not exist", userID);
      throw exception;
    }
  }

  void deleteUserCategory(ObjectId userID, String category)
      throws UserDoesNotExistsException, CategoryDoesNotExistsException {

    log.debug("Deleting user category {} for the user with ID {}", category, userID);

    if (!userHasCategory(userID, category)) {
      log.warn("Attempt to delete {} failed, as for user {} does not exists", category, userID);
      throw new CategoryDoesNotExistsException(category);
    }

    try {
      categoryRepository.deleteUserCategory(userID, category);
    } catch (UserDoesNotExistsException exception) {
      log.warn("Attempt to delete category {} failed, as user {} doesn't exists", category, userID);
      throw exception;
    }
  }

  List<String> getAllUserCategories(ObjectId userID) throws UserDoesNotExistsException {
    log.debug("Retrieving all categories for the user {}", userID);

    try {
      // When the user does not exist, the exception re-thrown.
      return categoryRepository.getAllUserCategories(userID);
    } catch (DefaultCategoriesNotFoundException
        | UserDoesNotContainsCategoriesFieldException exception) {
      log.error("An error on the database was encountered: {}", exception.getMessage());
      throw new ServerSideErrorException(exception);
    }
  }
}
