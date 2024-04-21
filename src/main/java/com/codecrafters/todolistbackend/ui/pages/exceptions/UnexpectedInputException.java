package com.codecrafters.todolistbackend.ui.pages.exceptions;

/** Thrown when the given user input is invalid or not supported by the page. */
public class UnexpectedInputException extends RuntimeException {

  /**
   * Creates a new exception indicating that the given input is not a valid option.
   *
   * @param input The input that was invalid.
   */
  public UnexpectedInputException(int input) {
    super("The given input " + input + " is not a valid option");
  }
}
