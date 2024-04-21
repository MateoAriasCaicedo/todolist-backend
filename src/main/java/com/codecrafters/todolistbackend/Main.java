package com.codecrafters.todolistbackend;

import com.codecrafters.todolistbackend.ui.pages.Page;
import com.codecrafters.todolistbackend.ui.pages.WelcomePage;

public class Main {

  public static void main(String[] args) {
    Page welcomePage = new WelcomePage();
    welcomePage.render();
  }
}
