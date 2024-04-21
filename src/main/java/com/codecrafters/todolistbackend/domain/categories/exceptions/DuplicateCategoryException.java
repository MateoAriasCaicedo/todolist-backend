package com.codecrafters.todolistbackend.domain.categories.exceptions;

/** Thrown when there is an attempt to create a duplicated category. */
public class DuplicateCategoryException extends RuntimeException {

  /**
   * Creates a new exception with the category that was duplicated.
   *
   * @param category The duplicated category.
   */
  public DuplicateCategoryException(String category) {
    super("The given category " + category + " already exists in the database");
  }
}
