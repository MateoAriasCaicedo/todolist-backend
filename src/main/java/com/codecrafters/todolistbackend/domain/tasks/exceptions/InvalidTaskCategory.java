package com.codecrafters.todolistbackend.domain.tasks.exceptions;

/** Thrown when the category assigned to a task is not valid or does not exist for the user. */
public class InvalidTaskCategory extends RuntimeException {

  /**
   * Creates a new exception with the category that was invalid.
   *
   * @param category The invalid category.
   */
  public InvalidTaskCategory(String category) {
    super("The user has not the specified task category: " + category);
  }
}
