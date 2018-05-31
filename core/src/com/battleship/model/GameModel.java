package com.battleship.model;

/**
 * A model representing a game.
 */

public class GameModel {
    private static int boardSize = 10;

    private GameType gameType;

    private Player playerBlue;
    private Player playerRed;

    private Board playerBlueBoard;
    private Board playerRedBoard;

    private Turn turn;

    private boolean gameOver;

    public GameModel(GameType gameType){
        this.gameType = gameType;

        switch (this.gameType){
            case SinglePlayer:
                this.playerBlue = new Player();
                this.playerRed = new Computer();
                break;
            case Multiplayer:
                this.playerBlue = new Player();
                this.playerRed = new Player();
                break;
            case Multiplayer_local:
                this.playerBlue = new Player();
                this.playerRed = new Player();
                break;
            default:
                break;
        }

        this.turn = Turn.Blue;
        setGameOver(false);

        this.playerBlueBoard = new Board(boardSize);
        this.playerRedBoard = new Board(boardSize);
    }

    public GameType getGameType() {
        return gameType;
    }

    public Turn getTurn(){
        return this.turn;
    }

    public Player getPlayerBlue(){
        return this.playerBlue;
    }

    public Player getPlayerRed(){
        return this.playerRed;
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


    public Board getPlayerBlueBoard(){
        return this.playerBlueBoard;
    }

    public Board getPlayerRedBoard(){
        return this.playerRedBoard;
    }


}
