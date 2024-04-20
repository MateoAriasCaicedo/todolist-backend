package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.ui.input.InputReader;

public class MainPage implements Page{

  @Override
  public void render() {
    System.out.println("Bienvenido a la pantalla principal");
    System.out.println("Tareas:");
    System.out.println("1. Ver tareas retrasadas.");
    System.out.println("2. Ver tareas para hoy.");
    System.out.println("3. Ver tareas completadas.");
    System.out.println("4. Ver todas las tareas.\n");
    System.out.println("Categorías:");
    System.out.println("5. Crear categoría.");
    System.out.println("6. Eliminar categoría.");
    System.out.println("7. Ver tareas de categoría.");
    System.out.println("8. Ver categorías.");
    System.out.println("9. Cerrara sesion.");

    int value = InputReader.readIntBetween(1, 9);

    switch (value) {
      case 1: printOverdueTasks();
      case 2: printTasksForToday();
      case 3: printCompletedTasks();
      case 4: printAllTasks();
      case 5: createCategory();
      case 6: deleteCategory();
      case 7: printCategoryTasks();
      case 8: printCategories();
      case 9: goBackToWelcomePage();
    }
  }

  public void goBackToWelcomePage() {
    WelcomePage welcomePage = new WelcomePage();
    welcomePage.render();
  }

  private void printOverdueTasks() {}

  private void printTasksForToday() {}

  private void printCompletedTasks() {}

  private void printAllTasks() {}

  private void createCategory() {}

  private void deleteCategory() {}

  private void printCategoryTasks() {}

  private void printCategories() {}


}
