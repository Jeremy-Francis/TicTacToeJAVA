/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tictactoewithai;

import java.util.Scanner;

public class TicTacToeWithAI {
    private static char[][] board = new char[3][3];
    private static char player = 'X';
    private static char computer = 'O';

    public static void main(String[] args) {
        initializeBoard();
        printBoard();

        while (true) {
            playerMove();
            if (isGameOver()) break;
            printBoard();

            computerMove();
            if (isGameOver()) break;
            printBoard();
        }
    }

    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    private static void playerMove() {
        Scanner scanner = new Scanner(System.in);
        int row, col;

        while (true) {
            System.out.print("Enter your move (row and column, e.g., 1 2): ");
            row = scanner.nextInt() - 1;
            col = scanner.nextInt() - 1;

            if (isValidMove(row, col)) {
                board[row][col] = player;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    private static void computerMove() {
        int[] bestMove = minimax(board, computer);
        board[bestMove[0]][bestMove[1]] = computer;
        System.out.println("Computer chooses row " + (bestMove[0] + 1) + " and column " + (bestMove[1] + 1));
    }

    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    private static boolean isGameOver() {
        if (checkWin(player)) {
            printBoard();
            System.out.println("You win! Congratulations!");
            return true;
        } else if (checkWin(computer)) {
            printBoard();
            System.out.println("Computer wins. Better luck next time!");
            return true;
        } else if (isBoardFull()) {
            printBoard();
            System.out.println("It's a draw!");
            return true;
        }
        return false;
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    private static int[] minimax(char[][] board, char player) {
        int[] bestMove = new int[] { -1, -1 };
        int bestScore = (player == computer) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimaxHelper(board, 0, false);
                    board[i][j] = ' ';

                    if ((player == computer && score > bestScore) || (player == player && score < bestScore)) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    private static int minimaxHelper(char[][] board, int depth, boolean isMaximizing) {
        if (checkWin(player)) {
            return -1;
        }
        if (checkWin(computer)) {
            return 1;
        }
        if (isBoardFull()) {
            return 0;
        }

        int score = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = isMaximizing ? computer : player;
                    int currentScore = minimaxHelper(board, depth + 1, !isMaximizing);
                    board[i][j] = ' ';
                    score = isMaximizing ? Math.max(score, currentScore) : Math.min(score, currentScore);
                }
            }
        }

        return score;
    }
}

