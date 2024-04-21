package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.ui.input.InputReader;
import com.codecrafters.todolistbackend.ui.pages.exceptions.UnexpectedInputException;

public class WelcomePage implements Page {

  private static void printWelcomeMessage() {
    System.out.println("Hola! Bienvendo a la aplicación");
  }

  private static void printWelcomeOptions() {
    System.out.println("¿Qué quieres hacer?\n");
    System.out.println("1. Iniciar sesión");
    System.out.println("2. Registrarse");
    System.out.println("3. Salir");
  }

  private static void printGoodbyeMessage() {
    System.out.println("Hasta pronto!");
  }

  @Override
  public PageExitCode render() {
    printWelcomeMessage();

    while (true) {
      printWelcomeOptions();

      int input = InputReader.readIntBetween(1, 3);

      PageExitCode exitCode;
      if (input == 1) {
        exitCode = new LoginPage().render();
      } else if (input == 2) {
        exitCode = new SignupPage().render();
      } else if (input == 3) {
        exitCode = PageExitCode.EXIT;
      } else {
        throw new UnexpectedInputException(input);
      }

      if (exitCode == PageExitCode.EXIT) {
        break;
      }
    }

    printGoodbyeMessage();
    return PageExitCode.EXIT;
  }
}
