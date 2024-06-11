package ui;

import model.AuthData;
import sf.ServerFacade;
import java.util.Scanner;

public class PreLogin {
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade serverFacade = new ServerFacade();

    public static void main(String[] args) {
        PreLogin preLogin = new PreLogin();
        AuthData authData = preLogin.displayPreLoginUI();
    }
    public AuthData displayPreLoginUI() {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "Welcome to CS 240 Chess!\n");

        while(true) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW + "Select an option: \n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW + "1. Register\n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW +"2. Login\n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW +"3. Quit\n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW +"4. Help\n");

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
                    AuthData authData = serverFacade.register(username, password, email);
                    System.out.println("Registration successful!");
                    return authData;
                } catch (Exception e) {
                    System.out.println("Registration failed: " + e.getMessage());
                }
            }
            else if(choice == 2){
                System.out.print("Enter username: ");
                String username = scanner.next();

                System.out.print("Enter password: ");
                String password = scanner.next();

                try {
                    AuthData authData = serverFacade.login(username, password);
                    System.out.println("Login successful!");
                    return authData;
                } catch (Exception e) {
                    System.out.println("Login failed: " + e.getMessage());
                }
            }

            else if(choice == 3){
                System.out.print("Quitting, Thank You for Playing!");
                System.exit(0);
            }

            else if(choice == 4){
                System.out.print("Enter 1 to register a new user, 2 to login an existing user, and 3 to quit\n");
            }
        }
    }
}
