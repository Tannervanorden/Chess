package ui;
import chess.ChessGame;
import model.AuthData;
import model.GameData;
import sf.ServerFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
public class PostLogin {
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade serverFacade;
    private String authToken;
    private HashMap<Integer, Integer> map = new HashMap<>();
    public PostLogin(String authToken, ServerFacade serverFacade) {
        this.authToken = authToken;
        this.map = new HashMap<>();
        this.serverFacade = serverFacade;
    }
    public void displayPostLoginUI() {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "Logged in\n");
        while (true) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW + "Select an option: \n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW +"1. Create Game\n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW +"2. List Games\n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW +"3. Play Game\n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW +"4. Observe Game\n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW +"5. Logout\n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW +"6. Quit\n");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW +"7. Help\n");

            System.out.print("Enter: ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter a Name:");
                String name = scanner.next();

                try {
                    serverFacade.createGame(name, authToken);
                    System.out.println("Game Created Successfully!");
                } catch (Exception e) {
                    System.out.println("Game Creation failed: " + e.getMessage());
                }
            } else if (choice == 2) {
                try {
                    List<GameData> games = serverFacade.listGames(authToken);
                    if (games.isEmpty()) {
                        System.out.println("No games found!");
                    } else {
                        System.out.println("Games:");
                        int i = 0;
                        for (GameData gameData : games) {
                            if (gameData.whiteUsername() != null) {
                                System.out.print("White useername: ");
                                System.out.print("\t" + gameData.whiteUsername() + "\n");
                            }
                            if (gameData.blackUsername() != null) {
                                System.out.print("Black useername: ");
                                System.out.print("\t" + gameData.blackUsername() + "\n");
                            }
                            i ++;
                            map.put(i,gameData.gameID());
                            System.out.println("Game ID: " + i);
                            System.out.println("Game Name: " + gameData.gameName());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Game List failed: " + e.getMessage());
                }
            } else if (choice == 3) {
                try {
                    System.out.print("Enter a game ID\n");
                    int gameId = scanner.nextInt();
                    int realGameId = map.get(gameId);
                    System.out.print("GameID" + gameId + "\n");
                    System.out.print("Which color? White or Black?\n");
                    String color = scanner.next();
                    GameData gamedata = serverFacade.joinGame(realGameId, color, authToken);
                    System.out.println("Game Joined Successfully!");
                    ChessGame game = gamedata.game();
                    GamePlay gamePlayUI = new GamePlay(game, realGameId, authToken);
                    gamePlayUI.displayChessBoard(color);
                    return;
                } catch (Exception e) {
                    System.out.println("Join Game Failed: " + e.getMessage());
                }
            }
            else if (choice == 5) {
                try {
                    serverFacade.logout(authToken);
                    System.out.println("Logged out Successfully!");
                    PreLogin preLogin = new PreLogin(serverFacade);
                    AuthData authData = preLogin.displayPreLoginUI();
                } catch (Exception e) {
                    System.out.println("Logout Failed: " + e.getMessage());
                }
            }
            else if(choice == 6){
                System.out.print("Quitting, Thank You for Playing!");
                System.exit(0);
            }
            else if(choice == 7){
                System.out.print("Enter 1 to create a game, 2 to list games, 3 to play games, 4 to observe\n5 to logout, and 6 to quit: ");
            }
        }
    }
}
