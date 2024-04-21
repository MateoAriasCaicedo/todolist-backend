package com.codecrafters.todolistbackend.domain.categories;

import org.bson.types.ObjectId;

class UserDoesNotContainsCategoriesFieldException extends RuntimeException {

  UserDoesNotContainsCategoriesFieldException(ObjectId userID) {
    super("The user with ID " + userID + " does not contains the categories field");
  }
}
