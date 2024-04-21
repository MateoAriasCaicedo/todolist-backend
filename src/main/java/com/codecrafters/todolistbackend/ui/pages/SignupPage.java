package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.domain.users.UserController;
import com.codecrafters.todolistbackend.domain.users.UserCreationDTO;
import com.codecrafters.todolistbackend.ui.input.InputReader;

public class SignupPage implements Page{

  @Override
  public void render() {
    System.out.println("Registrarse en la aplicación.");
    System.out.println("Primer nombre:");
    String firstName = InputReader.readString();
    System.out.println("Primer apellido:");
    String lastName = InputReader.readString();
    System.out.println("Nombre de usuario:");
    String userName = InputReader.readString();
    System.out.println("Correo:");
    String email = InputReader.readString();
    System.out.println("Contraseña:");
    String password = InputReader.readString();

    UserCreationDTO newUser = new UserCreationDTO(firstName, lastName, userName, email, password);
    validateValues(newUser);
  }

  private void goToFailedValidationPage() {
    FailedValidationPage failedValidationPage = new FailedValidationPage();
    failedValidationPage.render();
  }

  private void validateValues(UserCreationDTO newUser) {
    UserController userController = new UserController();

    try {
      String userID = userController.signUpUser(newUser);
      System.out.println("Usuario creado.");
      goToMainPage(userID);
    } catch (Exception exception) {
      System.out.println("ERROR: " + exception.getMessage());
      goToFailedValidationPage();
    }
  }

  private void goToMainPage(String userID) {
    MainPage mainPage = new MainPage(userID);
    mainPage.render();
  }
}
