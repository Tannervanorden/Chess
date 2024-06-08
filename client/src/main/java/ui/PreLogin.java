package ui;

import sf.ServerFacade;
import java.util.Scanner;

public class PreLogin {
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade serverFacade = new ServerFacade();

    public static void main(String[] args) {
        PreLogin preLogin = new PreLogin();
        preLogin.displayPreLoginUI();
    }

    private void displayPreLoginUI() {
        System.out.print("Welcome to CS 240 Chess!\n");

        while(true) {
            System.out.print("Select an option: \n");
            System.out.print("1. Register\n");
            System.out.print("2. Login\n");
            System.out.print("3. Quit\n");
            System.out.print("4. Help\n");

            System.out.print("Enter: ");

            int choice = scanner.nextInt();

            if (choice == 1){

                System.out.print("Enter username: ");
                String username = scanner.next();

                System.out.print("Enter password: ");
                String password = scanner.next();

                System.out.print("Enter email: ");
                String email = scanner.next();

                try {
                    serverFacade.register(username, password, email);
                    System.out.println("Registration successful!");
                } catch (Exception e) {
                    System.out.println("Registration failed: " + e.getMessage());
                }
            }
        }
    }
}
