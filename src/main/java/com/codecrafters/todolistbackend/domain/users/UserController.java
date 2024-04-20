package com.codecrafters.todolistbackend.domain.users;

import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
class UserController {

  private final UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/singup")
  CreatedUserDTO signUpUser(@RequestBody UserCreationDTO user) {
    return userService.signUpUser(user);
  }

  @GetMapping("/singin/{username}/{password}")
  String singInUser(@PathVariable String username, @PathVariable String password)
      throws UserDoesNotExistsException, IncorrectPasswordException {
    return userService.singIn(username, password);
  }
}
