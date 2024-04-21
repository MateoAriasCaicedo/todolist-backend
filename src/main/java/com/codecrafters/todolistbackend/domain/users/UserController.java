package com.codecrafters.todolistbackend.domain.users;

import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  public CreatedUserDTO signUpUser(UserCreationDTO user) {
    return userService.signUpUser(user);
  }

  public String singInUser(String username, String password)
      throws UserDoesNotExistsException, IncorrectPasswordException {
    return userService.singIn(username, password);
  }
}
