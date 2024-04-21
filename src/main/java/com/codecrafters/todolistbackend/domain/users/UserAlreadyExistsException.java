package com.codecrafters.todolistbackend.domain.users;

class UserAlreadyExistsException extends RuntimeException {

  UserAlreadyExistsException(String email, String userName) {
    super("There is already an user with email: " + email + " or with userNme: " + userName);
  }
}
