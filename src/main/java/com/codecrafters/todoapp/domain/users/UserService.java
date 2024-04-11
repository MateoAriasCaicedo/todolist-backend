package com.codecrafters.todoapp.domain.users;

import com.codecrafters.todoapp.exceptions.UserDoesNotExistsException;

public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  String singIn(String username, String password)
      throws UserDoesNotExistsException, IncorrectPasswordException {
    return userRepository.findUser(username, password); // TODO: implement this method.
  }

  String signUpUser(UserCreationDTO user) throws UserAlreadyExistsException {
    return userRepository.createUser(user); // TODO: implement this method.
  }
}
