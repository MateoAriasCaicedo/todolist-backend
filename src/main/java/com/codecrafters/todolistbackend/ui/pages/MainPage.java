package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.domain.categories.CategoryController;
import com.codecrafters.todolistbackend.domain.tasks.Task;
import com.codecrafters.todolistbackend.domain.tasks.TaskController;
import com.codecrafters.todolistbackend.domain.tasks.TaskCreationDTO;
import com.codecrafters.todolistbackend.ui.input.InputReader;
import com.codecrafters.todolistbackend.ui.pages.exceptions.UnexpectedInputException;
import java.util.ArrayList;
import java.util.Arrays;
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
  public void createTask() {
    System.out.println("Crear nueva tarea:");
    System.out.println("Titulo:");
    String title = InputReader.readString();
    System.out.println("Descripcion:");
    String description = InputReader.readString();
    System.out.println("Fecha limite:");
    String dueDate = InputReader.readString();
    System.out.println("Categoria:");
    String category = InputReader.readString();
    System.out.println("Etiquetas:");
    String tag = InputReader.readString().toLowerCase();
    List<String> tags = new ArrayList<>(Arrays.asList(tag.split(" ")));

    try {
      TaskCreationDTO task = new TaskCreationDTO(title, description, dueDate, category, false, tags);
      taskController.addUserTask(userID, task);
    } catch (Exception exception) {
      System.out.println("ERROR al crear tarea: " + exception.getMessage());
    }
  }

  public void deleteTask() {
    System.out.println("Ingrese el ID de la tarea que quiere eliminar:");
    String taskID = InputReader.readString();
    try {
      taskController.deleteUserTask(userID, new ObjectId(taskID));
    } catch (Exception exception) {
      System.out.println("ERROR al eliminar tarea: " + exception.getMessage());
    }
  }

  public void setTaskAsComplete() {
    System.out.println("Que tarea quiere establecer como completada (ingrese ID):");
    String taskID = InputReader.readString();
    try {
      taskController.setTaskAsComplete(userID, new ObjectId(taskID));
    } catch (Exception exception) {
      System.out.println("ERROR al encontrar la tarea: " + exception.getMessage());
    }
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

  private void printOverdueTasks() {
    List<Task> tasks = taskController.getOverdueTasks(userID);
    int index = 1;
    for (Task task : tasks) {
      System.out.println("Tarea: " + index);
      System.out.println(task.toString());
      System.out.println("---------------------------");
      index++;
    }
  }

  private void printTasksForToday() {
    List<Task> tasks = taskController.getTodayTasks(userID);
    int index = 1;
    for (Task task : tasks) {
      System.out.println("Tarea: " + index);
      System.out.println(task.toString());
      System.out.println("---------------------------");
      index++;
    }
  }

  private void printCompletedTasks() {
    List<Task> tasks = taskController.getCompleteTasks(userID);
    int index = 1;
    for (Task task : tasks) {
      System.out.println("Tarea: " + index);
      System.out.println(task.toString());
      System.out.println("---------------------------");
      index++;
    }
  }

  private void printAllTasks() {
    List<Task> tasks = taskController.getAllUserTasks(userID);
    int index = 1;
    for (Task task : tasks) {
      System.out.println("Tarea: " + index);
      System.out.println(task.toString());
      System.out.println("---------------------------");
      index++;
    }
  }

  private void updateTask() {
    System.out.println("Que tarea quiere actualizar (ingrese ID):");
    String taskID = InputReader.readString();
    System.out.println("Que campo quiere actualizar:");
    System.out.println("1. Titulo.");
    System.out.println("2. Descripcion.");
    System.out.println("3. Fecha limite.");
    System.out.println("4. Categoria.");
    int field = InputReader.readIntBetween(1, 4);
    switch (field) {
      case 1:
        updateTaskTitle(taskID);
      case 2:
        updateTaskDescription(taskID);
      case 3:
        updateTaskDueDate(taskID);
      case 4:
        updateTaskCategory(taskID);
    }
  }

  private void updateTaskTitle(String taskID) {
    System.out.println("Ingrese el nuevo titulo:");
    String title = InputReader.readString();
    try {
      taskController.updateUserTaskTitle(userID, new ObjectId(taskID), title);
    } catch (Exception exception) {
      System.out.println("ERROR: " + exception.getMessage());
    }
  }

  private void updateTaskDescription(String taskID) {
    System.out.println("Ingrese la nuevo descripcion:");
    String description = InputReader.readString();
    try {
      taskController.updateUserTaskDescription(userID, new ObjectId(taskID), description);
    } catch (Exception exception) {
      System.out.println("ERROR: " + exception.getMessage());
    }
  }

  private void updateTaskDueDate(String taskID) {
    System.out.println("Ingrese la nueva fecha limite descripcion:");
    String dueDate = InputReader.readString();
    try {
      taskController.updateUserTaskDueDate(userID, new ObjectId(taskID), dueDate);
    } catch (Exception exception) {
      System.out.println("ERROR: " + exception.getMessage());
    }
  }

  private void updateTaskCategory(String taskID) {
    System.out.println("Ingrese la categoria:");
    String category = InputReader.readString();
    try {
      taskController.updateUserTaskCategory(userID, new ObjectId(taskID), category);
    } catch (Exception exception) {
      System.out.println("ERROR: " + exception.getMessage());
    }
  }

  private void createCategory() {
    System.out.println("Ingrese el nombre de la nueva categoria:");
    String categoryName = InputReader.readString();
    try {
      categoryController.createCategory(userID, categoryName);
    } catch (Exception exception) {
      System.out.println("ERROR al crear categoria: " + exception.getMessage());
    }
  }

  private void deleteCategory() {
    System.out.println("Ingrese el nombre de la categoria a eliminar:");
    String categoryName = InputReader.readString();
    try {
      categoryController.deleteCategory(userID, categoryName);
    } catch (Exception exception) {
      System.out.println("ERROR al eliminar categoria: " + exception.getMessage());
    }
  }

  private void printTasksByCategory() {
    System.out.println("Ingrese el nombre de la categoria:");
    String categoryName = InputReader.readString();
    try {
      List<Task> tasks = taskController.getTasksByCategory(userID, categoryName);
      int index = 1;
      for (Task task : tasks) {
        System.out.println("Tarea: " + index);
        System.out.println(task.toString());
        System.out.println("---------------------------");
        index++;
      }
    } catch (Exception exception) {
      System.out.println("ERROR: " + exception.getMessage());
    }
  }

  private void printCategories() {
    System.out.println("Categorias: ");
    List<String> categories = categoryController.getAllUserCategories(userID);
    System.out.println(String.join("\n", categories));
  }
}
