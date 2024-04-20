package com.codecrafters.todolistbackend.domain.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Username already exists")
public class UsernameAlreadyExists extends RuntimeException {}
