package com.codecrafters.todolistbackend.domain.users;

class InvalidPasswordException extends RuntimeException {

  InvalidPasswordException(String password) {
    super("The given password is invalid: " + password);
  }
}
