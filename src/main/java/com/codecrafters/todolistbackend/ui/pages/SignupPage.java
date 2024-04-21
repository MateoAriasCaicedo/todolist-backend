package com.codecrafters.todolistbackend.ui.pages;

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


    boolean validation = validateValues(firstName, lastName, userName, email, password);

    if (validation) {
      System.out.println("Usuario creado.");
      goToMainPage();
    } else {
      goToFailedValidationPage();
    }
  }

  private void goToFailedValidationPage() {
    FailedValidationPage failedValidationPage = new FailedValidationPage();
    failedValidationPage.render();
  }

  private boolean validateValues(String firstName, String lastName, String userName, String email, String password) {
    return true;
  }

  private void goToMainPage() {
    MainPage mainPage = new MainPage();
    mainPage.render();
  }
}
