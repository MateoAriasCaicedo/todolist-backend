package com.codecrafters.todolistbackend.domain.categories.exceptions;

/** Thrown when there is an attempt to create a category with an empty identifier. */
public class EmptyCategoryIdentifierException extends RuntimeException {

  /** Creates a new exception specifying that the given category identifier was empty. */
  public EmptyCategoryIdentifierException() {
    super("A category identified cannot be empty");
  }
}
