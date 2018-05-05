package com.battleship.model;

/**
 * A model representing a game.
 */

public class GameModel {
    private static int boardSize = 10;

    private GameType gameType;

    private Player p1;
    private Player p2;

    private GameState gameState;

    private Board player1Board;
    private Board player2Board;

    private Turn turn;

    private boolean gameOver;

    GameModel(GameType gameType){
        this.gameType = gameType;

        this.turn = Turn.Blue;
        this.gameState = GameState.Player1_Placing;
        setGameOver(false);

        this.player1Board = new Board(boardSize);
        this.player2Board = new Board(boardSize);
    }

    public GameType getGameType() {
        return gameType;
    }

    public Turn getTurn(){
        return this.turn;
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public void nextTurn(){
        if (this.turn == Turn.Blue){
            this.turn = Turn.Red;
        } else if (this.turn == Turn.Red){
            this.turn = Turn.Blue;
        }
    }
}
