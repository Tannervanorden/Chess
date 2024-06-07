package ui;


public class ChessBoard {

    private static final int BOARD_SIZE = 8;

    public static void main(String[] args) {
        drawChessBoard();
    }

    private static void drawChessBoard() {
        System.out.print("   a  b  c  d  e  f  g  h\n");

        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print(8 - row + " ");

            for (int col = 0; col < BOARD_SIZE; col++) {
                // Work on squares
                String color = (row + col) % 2 == 0 ? EscapeSequences.SET_BG_COLOR_WHITE : EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
                System.out.print(color);
                System.out.print(EscapeSequences.EMPTY);
            }
            System.out.println(EscapeSequences.RESET_BG_COLOR);
        }
    }
}
