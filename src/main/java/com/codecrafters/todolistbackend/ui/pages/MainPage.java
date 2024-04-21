package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.domain.categories.CategoryController;
import com.codecrafters.todolistbackend.domain.tasks.Task;
import com.codecrafters.todolistbackend.domain.tasks.TaskController;
import com.codecrafters.todolistbackend.domain.tasks.TaskCreationDTO;
import com.codecrafters.todolistbackend.ui.input.InputReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;

public class MainPage implements Page {

  private final TaskController taskController = new TaskController();
  private final CategoryController categoryController = new CategoryController();
  private final ObjectId userID;

  public MainPage(String userID) {
    this.userID = new ObjectId(userID);
  }

  @Override
  public void render() {
    System.out.println("Bienvenido a la pantalla principal");
    System.out.println("Tareas:");
    System.out.println("1. Crear tarea.");
    System.out.println("2. Ver tareas retrasadas.");
    System.out.println("3. Ver tareas para hoy.");
    System.out.println("4. Ver tareas completadas.");
    System.out.println("5. Ver todas las tarea.");
    System.out.println("6. Marcar tarea como completa.");
    System.out.println("7. Actualizar tarea.");
    System.out.println("8. Eliminar tarea.\n");
    System.out.println("Categorías:");
    System.out.println("9. Crear categoría.");
    System.out.println("10. Eliminar categoría.");
    System.out.println("11. Buscar tareas por categoría.");
    System.out.println("12. Ver categorías.");
    System.out.println("13. Cerrara sesion.");

    int value = InputReader.readIntBetween(1, 9);

    switch (value) {
      case 1:
        createTask();
      case 2:
        printOverdueTasks();
      case 3:
        printTasksForToday();
      case 4:
        printCompletedTasks();
      case 5:
        printAllTasks();
      case 6:
        setTaskAsComplete();
      case 7:
        updateTask();
      case 8:
        deleteTask();
      case 9:
        createCategory();
      case 10:
        deleteCategory();
      case 11:
        printTasksByCategory();
      case 12:
        printCategories();
      case 13:
        goBackToWelcomePage();
    }
    render();
  }

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

  public void goBackToWelcomePage() {
    WelcomePage welcomePage = new WelcomePage();
    welcomePage.render();
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
