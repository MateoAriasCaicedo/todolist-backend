package com.codecrafters.todolistbackend.domain.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Email already exists")
public class EmailAlreadyExists extends RuntimeException {}