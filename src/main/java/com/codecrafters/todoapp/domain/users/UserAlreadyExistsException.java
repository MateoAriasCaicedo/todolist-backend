package com.codecrafters.todoapp.domain.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.BAD_REQUEST,
    reason = "There is already a ser with the specified email or userName")
public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(String email, String userName) {
    super("There is already an user with email: " + email + " or with userNme: " + userName);
  }
}
