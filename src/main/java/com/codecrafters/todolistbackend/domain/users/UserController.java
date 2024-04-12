package com.codecrafters.todolistbackend.domain.users;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
class UserController {

  private final UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/singp")
  String signUpUser(@RequestBody UserCreationDTO user) {
    return userService.signUpUser(user);
  }

  @GetMapping("/singin/{username}/{password}")
  String singInUser(@PathVariable String username, @PathVariable String password) {
    return userService.singIn(username, password);
  }
}