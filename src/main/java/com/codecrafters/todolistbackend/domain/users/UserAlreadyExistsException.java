package com.codecrafters.todolistbackend.domain.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.BAD_REQUEST,
    reason = "There is already a ser with the specified email or userName")
class UserAlreadyExistsException extends RuntimeException {

  UserAlreadyExistsException(String email, String userName) {
    super("There is already an user with email: " + email + " or with userNme: " + userName);
  }
}
