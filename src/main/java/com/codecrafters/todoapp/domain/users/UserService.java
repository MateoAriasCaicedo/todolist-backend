package com.codecrafters.todoapp.domain.users;

import com.codecrafters.todoapp.domain.regularExpressions.RegularExpressions;
import com.codecrafters.todoapp.exceptions.InvalidEmailException;
import com.codecrafters.todoapp.exceptions.InvalidPasswordException;
import com.codecrafters.todoapp.exceptions.UserDoesNotExistsException;

public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  String singIn(String username, String password)
      throws UserDoesNotExistsException, IncorrectPasswordException {
    return userRepository.findUser(username, password);
  }

  String signUpUser(UserCreationDTO user) throws UserAlreadyExistsException {

    if (!user.password().matches(RegularExpressions.PASSWORD_VALIDATION.validationString)) {
      throw new InvalidPasswordException(user.password());
    } else if (!user.email().matches(RegularExpressions.EMAIL_VALIDATION.validationString)) {
      throw new InvalidEmailException(user.email());
    }

    return userRepository.createUser(user);
  }
}
