package ui;

import sf.ServerFacade;
import java.util.Scanner;

public class PreLogin {
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade serverFacade = new ServerFacade();

    public static void main(String[] args) {
        displayPreLoginUI();
    }

    private void displayPreLoginUI() {
        System.out.print("Welcome to CS 240 Chess!");

        while(true) {
            System.out.print("Select an option: ");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.print("3. Quit");
            System.out.print("4. Help");

            System.out.print("Enter: ");

            int choice = scanner.nextInt();

            if (choice == 1){

                System.out.print("Enter username: ");
                String username = scanner.next();

                System.out.print("Enter password: ");
                String password = scanner.next();

                System.out.print("Enter email: ");
                String email = scanner.next();

                serverFacade.register(username, password, email);
            }
        }
    }

}
