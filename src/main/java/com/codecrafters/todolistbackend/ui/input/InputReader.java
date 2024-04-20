package com.codecrafters.todolistbackend.ui.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputReader {

  private static Scanner scanner = new Scanner(System.in);

  public static int readIntBetween(int start, int end) {
    while (true) {
      try {
        int readValue = scanner.nextInt();
        if (readValue < start || readValue > end) {
          System.out.println("El input está fuera de rango, vuelve a intentarlo");
          continue;
        }
        return readValue;
      } catch (InputMismatchException _ignored) {
      }
    }
  }
}
