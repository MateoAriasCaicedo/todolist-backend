package com.codecrafters.todolistbackend.exceptions;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Thrown when the given user credentials does not exist in the database. */
@ResponseStatus(
    value = HttpStatus.BAD_REQUEST,
    reason = "There is no user with the specified credentials")
public class UserDoesNotExistsException extends RuntimeException {

  /**
   * Create a new exception when the username does not exist in the database.
   *
   * @param username The non-existent username.
   */
  public UserDoesNotExistsException(String username) {
    super("There is no user with username: " + username);
  }

  /**
   * Creates a new exception when the user ID does not exist in the database.
   *
   * @param userID The non-existent ID.
   */
  public UserDoesNotExistsException(ObjectId userID) {
    super("There is no user with ID: " + userID);
  }
}
