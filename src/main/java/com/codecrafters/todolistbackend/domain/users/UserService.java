package com.codecrafters.todolistbackend.domain.users;

import com.codecrafters.todolistbackend.exceptions.InvalidEmailException;
import com.codecrafters.todolistbackend.exceptions.UserDoesNotExistsException;
import org.springframework.stereotype.Service;

@Service
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

    if (!user.password().matches(UserValidationRegex.PASSWORD_VALIDATION.validationString)) {
      throw new InvalidPasswordException(user.password());
    } else if (!user.email().matches(UserValidationRegex.EMAIL_VALIDATION.validationString)) {
      throw new InvalidEmailException(user.email());
    }

    return userRepository.createUser(user);
  }
}
