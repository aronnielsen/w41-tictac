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
    /**
     * Returns 0 for player 0, 1 for player 1.
     *
     * @return int Id of the next player.
     */
    public int getNextPlayer()
    {
        currentPlayer = (currentPlayer == 1) ? 0 : 1;
        return currentPlayer;
    }

    /**
     * Attempts to let the current player play at the given coordinates. It the
     * attempt is succesfull the current player has ended his turn and it is the
     * next players turn.
     *
     * @param col column to place a marker in.
     * @param row row to place a marker in.
     * @return true if the move is accepted, otherwise false. If gameOver == true
     * this method will always return false.
     */
    public boolean play(int col, int row)
    {
        if (isGameOver()) {
            return false;
        }

        System.out.println(gameBoardArray[row][col]);
        if (Objects.equals(gameBoardArray[row][col], null)) {
            if (currentPlayer == 0) {
                gameBoardArray[row][col] = "X";
            } else {
                gameBoardArray[row][col] = "O";
            }

            if (isGameOver()) {
                return true;
            }

            getNextPlayer();

            return true;
        } else {
            return false;
        }
    }

    public boolean isGameOver()
    {
        if (getWinner() > -2) {
            return true;
        }

        return false;
    }

    /**
     * Gets the id of the winner, -1 if its a draw.
     *
     * @return int id of winner, or -1 if draw.
     */
    public int getWinner()
    {
        for (int i = 0; i < 3; i++) {
            System.out.println(checkRow(i));
            if (checkRow(i) != -1) {
                return checkRow(i);
            } else if (checkCol(i) != -1) {
                return checkCol(i);
            }
        }

        if (checkDiagonals() > -1) {
            return checkDiagonals();
        }

        if (!checkIfMovesLeft()) {
            return -1;
        }

        return -2;
    }

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

    private int checkDiagonals() {
        String center = gameBoardArray[1][1];

        if (Objects.equals(center, null)) {
            return -1;
        }

        if (Objects.equals(gameBoardArray[0][0], center) && Objects.equals(center, gameBoardArray[2][2])) {
            return (gameBoardArray[0][0].equals("X")) ? 0 : 1;
        } else if (Objects.equals(gameBoardArray[0][2], center) && Objects.equals(center, gameBoardArray[2][0])) {
            return (gameBoardArray[0][0].equals("X")) ? 0 : 1;
        }

        return -1;
    }

    private int checkRow(int row) {
        String symbol = "";

        for (int i = 0; i < 3; i++) {
            if (Objects.equals(gameBoardArray[row][i], null) || (!Objects.equals(symbol, "") && !Objects.equals(symbol, gameBoardArray[row][i]))) {
                return -1;
            } else {
                symbol = gameBoardArray[row][i];
            }
        }

        return (symbol.equals("X")) ? 0 : 1;
    }

    private int checkCol(int col) {
        String symbol = "";

        for (int i = 0; i < 3; i++) {
            if (Objects.equals(gameBoardArray[i][col], null) || (!Objects.equals(symbol, "") && !Objects.equals(symbol, gameBoardArray[i][col]))) {
                return -1;
            } else {
                symbol = gameBoardArray[i][col];
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
    }
}
