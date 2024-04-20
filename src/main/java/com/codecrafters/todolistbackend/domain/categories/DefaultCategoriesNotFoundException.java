package com.codecrafters.todolistbackend.domain.categories;

class DefaultCategoriesNotFoundException extends RuntimeException {

  DefaultCategoriesNotFoundException() {
    super("The defaultCategories document could not be found in the database");
  }
}
