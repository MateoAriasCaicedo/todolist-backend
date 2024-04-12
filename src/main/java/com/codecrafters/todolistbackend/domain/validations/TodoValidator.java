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

  public boolean userHasCategory(ObjectId userID, String category) {
    return categoryController.getAllUserCategories(userID).contains(category);
  }
}
