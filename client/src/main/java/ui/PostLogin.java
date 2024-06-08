package ui;

import sf.ServerFacade;

import java.util.Scanner;

public class PostLogin {
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade serverFacade = new ServerFacade();

    public static void main(String[] args) {
        PostLogin postLogin = new PostLogin();
        PostLogin.displayPostLoginUI();
    }

    private static void displayPostLoginUI() {
    }
}
