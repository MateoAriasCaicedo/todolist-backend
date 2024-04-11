package com.codecrafters.todolistbackend.domain.categories;

public class EmptyCategoryException extends RuntimeException {

  public EmptyCategoryException(String category) {
    super("The category cannot be empty");
  }
}
