package com.codecrafters.todolistbackend.domain.tasks.exceptions;

/** Thrown when the given date for a task has not a valid format. */
public class InvalidDateFormatException extends RuntimeException {

  /**
   * Creates a new exception with the given date that had not a valid format.
   *
   * @param date The date that had not a valid format
   */
  public InvalidDateFormatException(String date) {
    super("Invalid date format: " + date);
  }
}
