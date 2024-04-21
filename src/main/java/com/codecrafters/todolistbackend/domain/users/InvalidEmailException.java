package com.codecrafters.todolistbackend.domain.users;

public class InvalidEmailException extends RuntimeException {

  public InvalidEmailException(String email) {
    super("Invalid email: " + email);
  }
}
