package oop.encapsulation;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
    private static final char EMPTY_BOX = ' ';
    private static final char PLAYER_ONE_SYMBOL = 'X';
    private static final char PLAYER_TWO_SYMBOL = 'O';
    // Instance field — each game has its own board (not shared across instances)
    private final char[][] gameBoard = new char[3][3];
    private final Scanner scanner = new Scanner(System.in);
    private String playerOne;
    private String playerTwo;
    private String currentPlayer;
    private String whoOwnTheGame;

    public void startGame() {
        initializeTheBoard();
        askForUserName();

        while (!isGameOver()) {
            drawBoard();
            printPlayerTurn();
            askForManeuver();
        }
        printGameOver();
    }

    private void initializeTheBoard() {
        for (char[] chars : gameBoard) {
            Arrays.fill(chars, EMPTY_BOX);
        }
    }

    private void askForUserName() {
        System.out.println("Welcome to TicTacToe..!");
        System.out.println("What's your name ?");
        playerOne = scanner.nextLine();
        System.out.println("Who are playing with you  ?");
        playerTwo = scanner.nextLine();
        System.out.println("Who is playing first ? press \n1 for:\n" + playerOne + "\n2 for " + playerTwo);
        int player = scanner.nextInt();
        currentPlayer = player == 1 ? playerOne : playerTwo;
    }

    private boolean isGameOver() {
        return isBoardIsFull() || hasAnyPlayerOwn();
    }

    private boolean isBoardIsFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == EMPTY_BOX)
                    return false;
            }
        }
        return true;
    }

    private void drawBoard() {
        System.out.println("|---|---|---|");
        for (char[] chars : gameBoard) {
            System.out.printf("| %c | %c | %c |\n", chars[0], chars[1], chars[2]);
            System.out.println("|---|---|---|");
        }
    }

    private void printPlayerTurn() {
        System.out.println(currentPlayer + "'s turn");
    }

    private void askForManeuver() {
        int row;
        int col;
        do {
            System.out.println("Enter a row number (0, 1,or 2):");
            row = scanner.nextInt();
            System.out.println("Enter a col number (0, 1,or 2):");
            col = scanner.nextInt();
        } while (!validateInput(row, col));
        if (currentPlayer.equals(playerOne)) {
            gameBoard[row][col] = PLAYER_ONE_SYMBOL;
            currentPlayer = playerTwo;
        } else {
            gameBoard[row][col] = PLAYER_TWO_SYMBOL;
            currentPlayer = playerOne;
        }
    }

    private void printGameOver() {
        drawBoard();
        System.out.println("\uD83C\uDFAE Game Over! \uD83C\uDFAE");
        if (whoOwnTheGame != null) {
            System.out.println(whoOwnTheGame + " wins the game — Congratulations! \uD83C\uDF8A\uD83C\uDF89");
        } else {
            System.out.println("It's a tie! Play again!");
        }
    }

    private boolean hasAnyPlayerOwn() {
        char cross = ' ';
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2] && gameBoard[i][0] != EMPTY_BOX) {
                cross = gameBoard[i][0];
            }
        }
        for (int i = 0; i < 3; i++) {
            if (gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i] == gameBoard[2][i] && gameBoard[0][i] != EMPTY_BOX) {
                cross = gameBoard[0][i];
            }
        }
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] && gameBoard[0][0] != EMPTY_BOX) {
            cross = gameBoard[0][0];
        }
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[0][2] && gameBoard[2][0] != EMPTY_BOX) {
            cross = gameBoard[2][0];
        }
        if (cross == PLAYER_ONE_SYMBOL) {
            whoOwnTheGame = playerOne;
        } else if (cross == PLAYER_TWO_SYMBOL) {
            whoOwnTheGame = playerTwo;
        }
        return whoOwnTheGame != null;
    }

    private boolean validateInput(int row, int col) {
        boolean result = false;
        if (row < 0 || col < 0 || row > 2 || col > 2) {
            System.out.println("The position is off the bounds of the board try again");
        } else if (gameBoard[row][col] != EMPTY_BOX) {
            System.out.println("Someone has already made a move at this position ,try again");
        } else {
            result = true;
        }
        return result;
    }

}
