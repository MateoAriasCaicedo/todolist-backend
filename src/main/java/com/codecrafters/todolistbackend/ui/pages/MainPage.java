package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.domain.tasks.Task;
import com.codecrafters.todolistbackend.domain.tasks.TaskController;
import com.codecrafters.todolistbackend.ui.input.InputReader;
import com.codecrafters.todolistbackend.ui.pages.exceptions.UnexpectedInputException;
import java.util.List;
import org.bson.types.ObjectId;

public class MainPage implements Page {

  private final TaskController taskController = new TaskController();
  private final ObjectId userID;

  public MainPage(ObjectId userID) {
    this.userID = userID;
  }

  private static void printMainPageMessage() {
    System.out.println("Bienvenido a la pantalla principal");
  }

  private static void printTasksOptions() {
    System.out.println("Tareas:");
    System.out.println("1. Ver tareas retrasadas.");
    System.out.println("2. Ver tareas para hoy.");
    System.out.println("3. Ver tareas completadas.");
    System.out.println("4. Ver todas las tareas.\n");
  }

  private static void printCategoriesOptions() {
    System.out.println("Categorías:");
    System.out.println("5. Crear categoría.");
    System.out.println("6. Eliminar categoría.");
    System.out.println("7. Ver tareas de categoría.");
    System.out.println("8. Ver categorías.");
  }

  private static void printOtherOptions() {
    System.out.println("Otros:");
    System.out.println("9. Cerrara sesion.");
    System.out.println("10. Salir.");
  }

  @Override
  public PageExitCode render() {
    printMainPageMessage();

    while (true) {
      printTasksOptions();
      printCategoriesOptions();
      printOtherOptions();

      int value = InputReader.readIntBetween(1, 10);

      if (value == 1) {
        printOverdueTasks();
      } else if (value == 2) {
        printTasksForToday();
      } else if (value == 3) {
        printCompletedTasks();
      } else if (value == 4) {
        printAllTasks();
      } else if (value == 5) {
        createCategory();
      } else if (value == 6) {
        deleteCategory();
      } else if (value == 7) {
        printCategoryTasks();
      } else if (value == 8) {
        printCategories();
      } else if (value == 9) {
        return PageExitCode.LOGOUT;
      } else if (value == 10) {
        return PageExitCode.EXIT;
      } else throw new UnexpectedInputException(value);
    }
  }

  private void printOverdueTasks() {}

  private void printTasksForToday() {}

  private void printCompletedTasks() {
    List<Task> tasks = taskController.getCompleteTasks(userID);

    for (Task task : tasks) {
      System.out.println(task.toString());
    }
  }

  private void printAllTasks() {}

  private void createCategory() {}

  private void deleteCategory() {}

  private void printCategoryTasks() {}

  private void printCategories() {}
}
