package com.codecrafters.todoapp.domain.users;

import com.codecrafters.todoapp.domain.tasks.Task;
import java.util.List;
import org.bson.types.ObjectId;

public record User(
    ObjectId id,
    String firstName,
    String lastName,
    String username,
    String email,
    String password,
    List<Task> tasks,
    List<String> categories) {}
