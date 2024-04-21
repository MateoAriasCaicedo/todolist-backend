package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.domain.categories.CategoryController;
import com.codecrafters.todolistbackend.domain.tasks.Task;
import com.codecrafters.todolistbackend.domain.tasks.TaskController;
import com.codecrafters.todolistbackend.domain.tasks.TaskCreationDTO;
import com.codecrafters.todolistbackend.domain.tasks.exceptions.EmptyTaskTitleException;
import com.codecrafters.todolistbackend.domain.tasks.exceptions.InvalidDateFormatException;
import com.codecrafters.todolistbackend.domain.tasks.exceptions.InvalidTaskCategory;
import com.codecrafters.todolistbackend.domain.tasks.exceptions.TaskDoesNotExistsException;
import com.codecrafters.todolistbackend.ui.input.InputReader;
import com.codecrafters.todolistbackend.ui.pages.exceptions.UnexpectedInputException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class MainPage implements Page {

  private final TaskController taskController = new TaskController();

  private final CategoryController categoryController = new CategoryController();

  private final ObjectId userID;

  public MainPage(ObjectId userID) {
    this.userID = userID;
  }

  private static void printMainPageMessage() {
    System.out.println("\n*---------------------------*");
    System.out.println("Bienvenido a la pantalla principal");
    System.out.println("*---------------------------*\n");
  }

  private static void printTasksOptions() {
    System.out.println("\nTareas:");
    System.out.println("1. Crear tarea.");
    System.out.println("2. Ver tareas retrasadas.");
    System.out.println("3. Ver tareas para hoy.");
    System.out.println("4. Ver tareas completadas.");
    System.out.println("5. Ver todas las tarea.");
    System.out.println("6. Marcar tarea como completa.");
    System.out.println("7. Actualizar tarea.");
    System.out.println("8. Eliminar tarea.\n");
  }

  private static void printCategoriesOptions() {
    System.out.println("\nCategorías:");
    System.out.println("9. Crear categoría.");
    System.out.println("10. Eliminar categoría.");
    System.out.println("11. Buscar tareas por categoría.");
    System.out.println("12. Ver categorías.\n");
  }

  private static void printOtherOptions() {
    System.out.println("\nOtros:");
    System.out.println("13. Cerrara sesion.");
    System.out.println("14. Salir.\n");
  }

  private static void printTasks(List<Task> tasks) {
    for (Task task : tasks) {
      System.out.println("\n*---------------------------*");

      System.out.println("ID: " + task.id());
      System.out.println("Título: " + task.title());
      System.out.println("Completada: " + (task.completed() ? "Completada" : "En progreso"));
      System.out.println("Descripción: " + task.description());
      System.out.println("Fecha vencimiento: " + task.dueDate());
      System.out.println("Categoría: " + task.category());
      System.out.println("Etiquetas: " + task.tags().toString());

      System.out.println("*---------------------------*\n");
    }
  }

  private static void printUpdateTaskMessage() {
    System.out.println("\n*---------------------------*");

    System.out.println("Que campo quiere actualizar:");
    System.out.println("1. Titulo.");
    System.out.println("2. Descripcion.");
    System.out.println("3. Fecha limite.");
    System.out.println("4. Categoria.");

    System.out.println("*---------------------------*\n");
  }

  private static String readTaskID() {
    return InputReader.readWithMessage("Que tarea quiere actualizar (ingrese ID):");
  }

  @Override
  public PageExitCode render() {

    while (true) {
      printMainPageMessage();
      printTasksOptions();
      printCategoriesOptions();
      printOtherOptions();

      int input = InputReader.readIntBetween(1, 14);

      switch (input) {
        case 1 -> createTask();
        case 2 -> printOverdueTasks();
        case 3 -> printTasksForToday();
        case 4 -> printCompletedTasks();
        case 5 -> printAllTasks();
        case 6 -> setTaskAsComplete();
        case 7 -> updateTask();
        case 8 -> deleteTask();
        case 9 -> createCategory();
        case 10 -> deleteCategory();
        case 11 -> printTasksByCategory();
        case 12 -> printCategories();
        case 13 -> {
          return PageExitCode.LOGOUT;
        }
        case 14 -> {
          return PageExitCode.EXIT;
        }
        default -> throw new UnexpectedInputException(input);
      }
    }
  }

  public void createTask() {
    System.out.println("Crear nueva tarea:");

    String title = InputReader.readWithMessage("Título:");
    String description = InputReader.readWithMessage("Descripcion");
    String dueDate = InputReader.readWithMessage("Fecha de vencimiento:");
    String category = InputReader.readWithMessage("Categoría");
    String tag = InputReader.readWithMessage("Etiquetas").toLowerCase();
    List<String> tags = new ArrayList<>(List.of(tag.split(" ")));

    try {
      TaskCreationDTO task =
          new TaskCreationDTO(title, description, dueDate, category, false, tags);
      taskController.addUserTask(userID, task);
    } catch (InvalidDateFormatException exception) {
      System.out.println("La fecha tiene el formato incorrecto, por favor usa 'YYYY-MM-DD'");
    } catch (EmptyTaskTitleException exception) {
      System.out.println("Toda tarea debe tener un título, por favor inténtalo de nuevo");
    } catch (InvalidTaskCategory exception) {
      System.out.println("La categoría que especificaste no existe, por favor inténtalo de nuevo");
    }
  }

  public void deleteTask() {
    System.out.println("Ingrese el ID de la tarea que quiere eliminar:");
    String taskID = InputReader.readString();
    try {
      taskController.deleteUserTask(userID, new ObjectId(taskID));
    } catch (TaskDoesNotExistsException exception) {
      System.out.println("La tarea que especificaste no existe, por favor inténtalo de nuevo");
    }
  }

  public void setTaskAsComplete() {
    try {
      taskController.setTaskAsComplete(
          userID,
          new ObjectId(
              InputReader.readWithMessage(
                  "Que tarea quiere establecer como completada (ingrese ID):")));
    } catch (TaskDoesNotExistsException exception) {
      System.out.println("La tarea que especificaste no existe, por favor inténtalo de nuevo");
    }
  }

  private void printOverdueTasks() {
    printTasks(taskController.getOverdueTasks(userID));
  }

  private void printTasksForToday() {
    printTasks(taskController.getTodayTasks(userID));
  }

  private void printCompletedTasks() {
    printTasks(taskController.getCompleteTasks(userID));
  }

  private void printAllTasks() {
    printTasks(taskController.getAllUserTasks(userID));
  }

  private void updateTask() {
    String taskID = readTaskID();
    printUpdateTaskMessage();

    int input = InputReader.readIntBetween(1, 4);

    switch (input) {
      case 1 -> updateTaskTitle(taskID);
      case 2 -> updateTaskDescription(taskID);
      case 3 -> updateTaskDueDate(taskID);
      case 4 -> updateTaskCategory(taskID);
      default -> throw new UnexpectedInputException(input);
    }
  }

  private void updateTaskTitle(String taskID) {
    try {
      taskController.updateUserTaskTitle(
          userID, new ObjectId(taskID), InputReader.readWithMessage("Ingrese el nuevo titulo:"));
    } catch (TaskDoesNotExistsException exception) {
      System.out.println("La tarea que especificaste no existe, por favor inténtalo de nuevo");
    }
  }

  private void updateTaskDescription(String taskID) {
    try {
      taskController.updateUserTaskDescription(
          userID,
          new ObjectId(taskID),
          InputReader.readWithMessage("Ingrese la nuevo descripcion:"));
    } catch (TaskDoesNotExistsException exception) {
      System.out.println("La tarea que especificaste no existe, por favor inténtalo de nuevo");
    }
  }

  private void updateTaskDueDate(String taskID) {
    try {
      taskController.updateUserTaskDueDate(
          userID,
          new ObjectId(taskID),
          InputReader.readWithMessage("Ingrese la nueva fecha limite descripcion:"));
    } catch (TaskDoesNotExistsException exception) {
      System.out.println("La tarea que especificaste no existe, por favor inténtalo de nuevo");
    }
  }

  private void updateTaskCategory(String taskID) {
    try {
      taskController.updateUserTaskCategory(
          userID, new ObjectId(taskID), InputReader.readWithMessage("Ingrese la categoria:"));
    } catch (TaskDoesNotExistsException exception) {
      System.out.println("La tarea que especificaste no existe, por favor inténtalo de nuevo");
    }
  }

  private void createCategory() {
    try {
      categoryController.createCategory(
          userID, InputReader.readWithMessage("\"Ingrese el nombre de la nueva categoria:"));
    } catch (TaskDoesNotExistsException exception) {
      System.out.println("La tarea que especificaste no existe, por favor inténtalo de nuevo");
    }
  }

  private void deleteCategory() {
    try {
      categoryController.deleteCategory(
          userID, InputReader.readWithMessage("Ingrese el nombre de la categoria a eliminar:"));
    } catch (TaskDoesNotExistsException exception) {
      System.out.println("La tarea que especificaste no existe, por favor inténtalo de nuevo");
    }
  }

  private void printTasksByCategory() {
    try {
      printTasks(
          taskController.getTasksByCategory(
              userID, InputReader.readWithMessage("Ingrese el nombre de la categoria:")));
    } catch (Exception exception) {
      System.out.println("ERROR: " + exception.getMessage());
    }
  }

  private void printCategories() {
    System.out.println("Categorias: ");
    System.out.println(String.join("\n", categoryController.getAllUserCategories(userID)));
  }
}
