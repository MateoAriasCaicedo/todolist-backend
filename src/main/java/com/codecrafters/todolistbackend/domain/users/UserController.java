package com.codecrafters.todolistbackend.domain.users;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/singup")
  String signUpUser(@RequestBody UserCreationDTO user) {
    return userService.signUpUser(user);
  }

  @GetMapping("/singin/{username}/{password}")
  String singInUser(@PathVariable String username, @PathVariable String password) {
    return userService.singIn(username, password);
  }
}
