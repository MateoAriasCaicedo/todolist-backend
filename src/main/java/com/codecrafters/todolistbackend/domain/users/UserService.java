package com.codecrafters.todolistbackend.domain.users;

import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;

class UserService {

  private final UserRepository userRepository;

  UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  String singIn(String username, String password)
      throws UserDoesNotExistsException, IncorrectPasswordException {

    return userRepository.findUser(username, password);
  }

  String signUpUser(UserCreationDTO user)
      throws InvalidPasswordException,
          InvalidEmailException,
          UsernameAlreadyExists,
          EmailAlreadyExists {

    if (!user.password().matches(UserValidationRegex.PASSWORD_VALIDATION)) {
      throw new InvalidPasswordException(user.password());
    }

    if (!user.email().matches(UserValidationRegex.EMAIL_VALIDATION)) {
      throw new InvalidEmailException(user.email());
    }

    return userRepository.createUser(user);
  }
}
