package com.codecrafters.todolistbackend;

import org.junit.jupiter.api.Assertions;

class Test {

    @org.junit.jupiter.api.Test
    void test() {
    Assertions.assertTrue("Contraseña123456".matches("^[A-Za-z0-9]{8,}$"));
    }
}
