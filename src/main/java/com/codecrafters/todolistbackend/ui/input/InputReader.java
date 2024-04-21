package com.codecrafters.todolistbackend.ui.input;

import java.util.Scanner;

public class InputReader {

  private static final Scanner scanner = new Scanner(System.in);

  private InputReader() {}

  public static int readIntBetween(int start, int end) {
    while (true) {
      try {

        int readValue = Integer.parseInt(scanner.nextLine());
        if (readValue < start || readValue > end) {
          System.out.println("El input está fuera de rango, vuelve a intentarlo");
          continue;
        }
        return readValue;
      } catch (NumberFormatException ignored) {
      }
    }
  }

  public static String readString() {
    return scanner.nextLine();
  }

  public static String readWithMessage(String message) {
    System.out.println(message);
    return scanner.nextLine();
  }
}
