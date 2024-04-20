package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.domain.users.UserController;
import com.codecrafters.todolistbackend.domain.users.UserRepository;
import com.codecrafters.todolistbackend.domain.users.UserService;
import com.codecrafters.todolistbackend.ui.input.InputReader;

public class LoginPage implements Page {

    @Override
    public void render() {
        System.out.println("Inicia sesión en tu cuenta");
        System.out.println("¿Cuál es tu nombre de usuario?");
        String userName = InputReader.readString();
        System.out.println("¿Cuál es tu contraseña?");
        String password = InputReader.readString();

        boolean validation = validateValues(userName, password);

        if (validation) {
            System.out.println("Correcto, iniciaste sesión");
            goToMainPage();
        } else {
            goToFailedValidationPage();
        }
    }

    private void goToFailedValidationPage() {
        FailedValidationPage failedValidationPage = new FailedValidationPage();
        failedValidationPage.render();
    }

    private boolean validateValues(String userName, String password) {
        UserController
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService();
    }

    private void goToMainPage() {
        MainPage mainPage = new MainPage();
        mainPage.render();
    }
}
