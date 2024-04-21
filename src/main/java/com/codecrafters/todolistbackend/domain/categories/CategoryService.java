package com.codecrafters.todolistbackend.domain.categories;

import com.codecrafters.todolistbackend.domain.categories.exceptions.CategoryDoesNotExistsException;
import com.codecrafters.todolistbackend.domain.categories.exceptions.DuplicateCategoryException;
import com.codecrafters.todolistbackend.domain.categories.exceptions.EmptyCategoryIdentifierException;
import com.codecrafters.todolistbackend.exceptions.ServerSideErrorException;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import java.util.List;
import org.bson.types.ObjectId;

class CategoryService {

  private final CategoryRepository categoryRepository;

  CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  void createUserCategory(ObjectId userID, String category)
      throws UserDoesNotExistsException, EmptyCategoryIdentifierException {

    if (category.isEmpty()) {
      throw new EmptyCategoryIdentifierException();
    }

    if (getUserCategories(userID).contains(category)) {
      throw new DuplicateCategoryException(category);
    }

    categoryRepository.createUserCategory(userID, category);
  }

  List<String> getUserCategories(ObjectId userID) {
    return categoryRepository.getUserCategories(userID);
  }

  boolean userHasCategory(ObjectId userID, String category) throws UserDoesNotExistsException {
    return getAllUserCategories(userID).contains(category);
  }

  void deleteUserCategory(ObjectId userID, String category)
      throws UserDoesNotExistsException, CategoryDoesNotExistsException {

    if (!userHasCategory(userID, category)) {
      throw new CategoryDoesNotExistsException(category);
    }

    categoryRepository.deleteUserCategory(userID, category);
  }

  List<String> getAllUserCategories(ObjectId userID) throws UserDoesNotExistsException {

    try {
      // When the user does not exist, the exception re-thrown.
      return categoryRepository.getAllUserCategories(userID);
    } catch (DefaultCategoriesNotFoundException
        | UserDoesNotContainsCategoriesFieldException exception) {
      throw new ServerSideErrorException(exception);
    }
  }
}
