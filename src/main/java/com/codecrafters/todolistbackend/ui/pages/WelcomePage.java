package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.ui.input.InputReader;

public class WelcomePage implements Page {

  private final LoginPage loginPage;

  public WelcomePage(LoginPage loginPage) {
    this.loginPage = loginPage;
  }

  @Override
  public void render() {
    System.out.println("Hola! Bienvendo a la aplicación");
    System.out.println("¿Qué quieres hacer?");
    System.out.println();
    System.out.println("1. Iniciar sesión");
    System.out.println("2. Registrarse");
    System.out.println("3. Salir");

    int value = InputReader.readIntBetween(1, 3);

    switch (value) {
      case 1:
        loginPage.render();
      case 2:
        System.out.println();
    }
  }
}
