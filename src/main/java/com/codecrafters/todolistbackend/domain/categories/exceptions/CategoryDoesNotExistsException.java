package com.codecrafters.todolistbackend.domain.categories.exceptions;

/** Thrown when the given category does not exist. */
public class CategoryDoesNotExistsException extends RuntimeException {

  /**
   * Creates a new exception with the given category that does not exist.
   *
   * @param category The non-existing category.
   */
  public CategoryDoesNotExistsException(String category) {
    super("The given category does not exist: " + category);
  }
}
