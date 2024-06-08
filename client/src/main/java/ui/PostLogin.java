package ui;

import model.GameData;
import sf.ServerFacade;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PostLogin {
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade serverFacade = new ServerFacade();
    private String authToken;


    public PostLogin(String authToken) {
        this.authToken = authToken;
    }
    public void displayPostLoginUI() {

        System.out.print("Logged in\n");
        while (true) {
            System.out.print("Select an option: \n");
            System.out.print("1. Create Game\n");
            System.out.print("2. List Games\n");
            System.out.print("3. Join Game\n");
            System.out.print("4. Observe Game\n");
            System.out.print("5. Logout\n");
            System.out.print("6. Quit\n");
            System.out.print("7. Help\n");

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

            else if (choice == 2){
                try {
                    List<GameData> games = serverFacade.listGames(authToken);
                    if (games.isEmpty()) {
                        System.out.println("No games found!");
                    } else {
                        System.out.println("Games:");
                        for (GameData gameData : games) {
                            System.out.println("Game ID: " + gameData.gameID());
                            System.out.println("Game Name: " + gameData.gameName());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Game List failed: " + e.getMessage());
                }
            }

            else if (choice == 5){
                try {
                    serverFacade.logout(name);
                    System.out.println("Logged out Successfully!");
                } catch (Exception e) {
                    System.out.println("Logout Failed: " + e.getMessage());
            }
        }
    }
}
