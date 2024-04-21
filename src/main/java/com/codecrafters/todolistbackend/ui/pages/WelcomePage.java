package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.ui.input.InputReader;

public class WelcomePage implements Page {

  @Override
  public void render() {
    System.out.println("Hola! Bienvendo a la aplicación");
    System.out.println("¿Qué quieres hacer?\n");
    System.out.println("1. Iniciar sesión");
    System.out.println("2. Registrarse");
    System.out.println("3. Salir");

    int value = InputReader.readIntBetween(1, 3);

    switch (value) {
      case 1: goToLoginPage();
      case 2: goToSignupPage();
      case 3:
    }
  }

  private void goToLoginPage() {
    LoginPage loginPage = new LoginPage();
    loginPage.render();
  }

  private void goToSignupPage() {
    SignupPage signupPage = new SignupPage();
    signupPage.render();
  }
}
