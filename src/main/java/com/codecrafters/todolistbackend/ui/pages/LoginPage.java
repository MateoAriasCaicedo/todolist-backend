package com.codecrafters.todolistbackend.ui.pages;

import com.codecrafters.todolistbackend.domain.users.UserController;
import com.codecrafters.todolistbackend.ui.input.InputReader;

public class LoginPage implements Page {

    @Override
    public void render() {
        System.out.println("Inicia sesión en tu cuenta");
        System.out.println("¿Cuál es tu nombre de usuario?");
        String userName = InputReader.readString();
        System.out.println("¿Cuál es tu contraseña?");
        String password = InputReader.readString();

        validateValues(userName, password);
    }

    private void goToFailedValidationPage() {
        FailedValidationPage failedValidationPage = new FailedValidationPage();
        failedValidationPage.render();
    }

    private void validateValues(String userName, String password) {
        UserController userController = new UserController();
        try {
            String userID = userController.singInUser(userName, password);
            System.out.println("Correcto, iniciaste sesión");
            goToMainPage(userID);
        } catch (Exception exception) {
            System.out.println("ERROR: " + exception.getMessage());
            goToFailedValidationPage();
        }
    }

    private void goToMainPage(String userID) {
        MainPage mainPage = new MainPage(userID);
        mainPage.render();
    }
}
