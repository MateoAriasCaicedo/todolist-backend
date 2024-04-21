package com.codecrafters.todolistbackend.domain.validations;

import com.codecrafters.todolistbackend.domain.categories.CategoryController;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class TodoValidator {

  private final CategoryController categoryController;

  public TodoValidator(CategoryController categoryController) {
    this.categoryController = categoryController;
  }

  /**
   * Checks if the specified user has the given category.
   *
   * @param userID The ID of the user to check.
   * @param category The category that is being checked.
   * @return True if the user has the given category, false otherwise.
   */
  public boolean userHasCategory(ObjectId userID, String category) {
    return categoryController.getAllUserCategories(userID).contains(category);
  }
}
