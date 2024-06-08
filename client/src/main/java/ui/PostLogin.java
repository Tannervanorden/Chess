package ui;

import sf.ServerFacade;

import java.util.Scanner;

public class PostLogin {
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade serverFacade = new ServerFacade();

    public static void main(String[] args) {
        PostLogin postLogin = new PostLogin();
        postLogin.displayPostLoginUI();
    }

    public void displayPostLoginUI() {

        while (true) {
            System.out.print("Logged in\n");
            System.out.print("Select an option: \n");
            System.out.print("1. Register\n");
            System.out.print("2. Login\n");
            System.out.print("3. Quit\n");
            System.out.print("4. Help\n");

            System.out.print("Enter: ");

            int choice = scanner.nextInt();

            if (choice == 1){

                System.out.print("Enter a Name:");
                String name = scanner.next();

                try {
                    serverFacade.createGame(name, authToken);
                    System.out.println("Game Created Successfully!");
                } catch (Exception e) {
                    System.out.println("Game Creation failed: " + e.getMessage());
                }
            }
        }
    }
}
