package com.codecrafters.todoapp.domain.tasks;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping("/add/{userID}")
  void addUserTask(@PathVariable ObjectId userID, @RequestBody TaskCreationDTO task) {
    taskService.addUserTask(userID, task);
  }

  @PutMapping("/complete/{userID}/{taskID}")
  void completeUserTask(@PathVariable ObjectId userID, @PathVariable ObjectId taskID) {
    taskService.completeUserTask(userID, taskID);
  }

  @DeleteMapping("/delete/{userID}/{taskID}")
  void deleteUserTask(@PathVariable ObjectId userID, @PathVariable ObjectId taskID) {
    taskService.deleteUserTask(userID, taskID);
  }

  @GetMapping("/all/{userID}")
  List<Task> getAllUserTasks(@PathVariable ObjectId userID) {
    return taskService.getAllUserTasks(userID);
  }
}
