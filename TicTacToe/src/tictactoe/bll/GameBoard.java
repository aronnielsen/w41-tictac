/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.bll;

import java.util.Objects;

/**
 *
 * @author Stegger
 */
public class GameBoard implements IGameModel
{
    private int currentPlayer = 0;
    private String[][] gameBoardArray = new String[3][3];
    private boolean isGameOver = false;
    private int winner = -1;
    /**
     * Returns 0 for player 0, 1 for player 1.
     *
     * @return int id of the next player.
     */
    public int getNextPlayer()
    {
        currentPlayer = (currentPlayer == 1) ? 0 : 1;
        return currentPlayer;
    }

    /**
     * Attempts to let the current player play at the given coordinates. It the
     * attempt is successful the current player has ended his turn, and it is the
     * next players turn.
     *
     * @param col column to place a marker in.
     * @param row row to place a marker in.
     * @return true if the move is accepted, otherwise false. If gameOver == true
     * this method will always return false.
     */
    public boolean play(int col, int row)
    {
        if (Objects.equals(gameBoardArray[row][col], null) && !isGameOver()) {
            if (currentPlayer == 0) {
                gameBoardArray[row][col] = "X";
            } else {
                gameBoardArray[row][col] = "O";
            }

            winner = checkIfWinner();

            if (winner > -2) {
                isGameOver = true;
            }

            getNextPlayer();

            //printBoardToConsole();

            return true;
        } else {
            return false;
        }
    }

    /**
        Checks the rows, columns and diagonals, and checks if any symbol has a complete line.
        @return 0 if X has a line and is the winner
                1 if O has a line and is the winner
               -1 if no one has a line and there are no moves left resulting in a draw
               -2 if no one has a line and there are still moves left
    */
    private int checkIfWinner() {
        if (checkDiagonals() > -1) {
            return checkDiagonals();
        }

        for (int i = 0; i < 3; i++) {
            int rowCheck = checkRowOrColumn(i, true);
            int colCheck = checkRowOrColumn(i, false);

            if (rowCheck != -1) {
                return rowCheck;
            } else if (colCheck != -1) {
                return colCheck;
            }
        }

        if (!checkIfMovesLeft()) {
            return -1;
        }

        return -2;
    }

    public boolean isGameOver()
    {
        return isGameOver;
    }
    public int getWinner()
    {
        return winner;
    }

    /**
     * Loops through the multidimensional array that stores the game state
     * and checks if there are any null/empty spots left
     * @return true if there is a null/empty spot left in the multidimensional array, otherwise false
     */
    private boolean checkIfMovesLeft() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoardArray[i][j] == null) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks both diagonals 'manually' to see if a diagonal has the same symbol in the whole diagonal
     * @return -1 if no symbol has a complete diagonal
     *          0 if X has a complete diagonal
     *          1 if O has a complte diagonal
     */
    private int checkDiagonals() {
        String first = gameBoardArray[0][0];
        String third = gameBoardArray[0][2];
        String center = gameBoardArray[1][1];


        if (Objects.equals(center, null)) {
            return -1;
        }

        if (Objects.equals(first, center) && Objects.equals(center, gameBoardArray[2][2])) {
            return (Objects.equals(first, "X")) ? 0 : 1;
        } else if (Objects.equals(third, center) && Objects.equals(center, gameBoardArray[2][0])) {
            return (Objects.equals(third, "X")) ? 0 : 1;
        }

        return -1;
    }

    /**
     * Loops through the given row/column and checks if a symbol covers the entire row/column
     * @param index index of the row/column to check
     * @param row is it checking a row or not
     * @return -1 if no symbol has a complete line
     *          0 if X has a complete line
     *          1 if O has a complete line
     */
    private int checkRowOrColumn(int index, boolean row) {
        String symbol = "";

        for (int i = 0; i < 3; i++) {
            String item = (row) ? gameBoardArray[index][i] : gameBoardArray[i][index];

            if (Objects.equals(item, null) || (!Objects.equals(symbol, "") && !Objects.equals(symbol, item))) {
                return -1;
            } else {
                symbol = item;
            }
        }

        return (symbol.equals("X")) ? 0 : 1;
    }

    /**
     * Resets the game to a new game state.
     */
    public void newGame()
    {
        gameBoardArray = new String[3][3];
        currentPlayer = 0;
        isGameOver = false;
        winner = -2;
    }

    /**
     * A debug method to print the game-board to the console
     */
    private void printBoardToConsole() {
        for (int i = 0; i < 3; i++) {
            String line = "";
            for (int j = 0; j < 3; j++) {
                String element = gameBoardArray[i][j];
                line += (element == null) ? "- " : gameBoardArray[i][j] + " ";
            }
            System.out.println(line);
        }
    }
}