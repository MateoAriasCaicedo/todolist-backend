package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.ui.input.InputReader;

public class FailedValidationPage implements Page{


  @Override
  public void render() {
    System.out.println("El nombre de usuario o la contraseña estan errados.");
    System.out.println("Por favor intenta de nuevo y regresa al menu de inicio:");
    System.out.println("1. Volver a intentar.");
    System.out.println("2. Volver al menu de inicio.");
    int value = InputReader.readIntBetween(1, 2);
    switch (value) {
      case 1:
        this.render();
      case 2:
        goBackToWelcomePage();
    }
  }

  public void goBackToWelcomePage() {
    WelcomePage welcomePage = new WelcomePage();
    welcomePage.render();
  }
}
