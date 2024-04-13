package com.codecrafters.todolistbackend.domain.validations;

import com.codecrafters.todolistbackend.domain.categories.CategoryService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class TodoValidator {

  private final CategoryService categoryService;

  public TodoValidator(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  /**
   * Checks if the specified user has the given category.
   *
   * @param userID The ID of the user to check.
   * @param category The category that is being checked.
   * @return True if the user has the given category, false otherwise.
   */
  public boolean userHasCategory(ObjectId userID, String category) {
    return categoryService.getAllUserCategories(userID).contains(category);
  }
}
