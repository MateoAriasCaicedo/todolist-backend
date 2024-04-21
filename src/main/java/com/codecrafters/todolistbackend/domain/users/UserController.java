package com.codecrafters.todolistbackend.domain.users;

import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import org.springframework.stereotype.Controller;

public class UserController {

  private final UserService userService;

  public UserController() {
    this.userService = new UserService(new UserRepository());
  }

  public CreatedUserDTO signUpUser(UserCreationDTO user) {
    return userService.signUpUser(user);
  }

  public String singInUser(String username, String password)
      throws UserDoesNotExistsException, IncorrectPasswordException {
    return userService.singIn(username, password);
  }
}
