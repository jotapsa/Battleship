package com.battleship.model;

import com.battleship.controller.BoardController;
import com.battleship.model.aux.GameType;
import com.battleship.model.aux.Turn;

/**
 * A model representing a game.
 */

public class GameModel {
    private static int boardSize = 10;

    private com.battleship.model.aux.GameType gameType;

    private Player playerBlue;
    private Player playerRed;

    private Board playerBlueBoard;
    private Board playerRedBoard;

    private Turn turn;

    private Turn player;

    private boolean gameOver;

    public GameModel(com.battleship.model.aux.GameType gameType){
        this.gameType = gameType;

        this.playerBlueBoard = new Board(boardSize);
        this.playerRedBoard = new Board(boardSize);

        switch (this.gameType){
            case SinglePlayer:
                this.playerBlue = new Human(Turn.Blue);
                this.playerRed = new Computer(Turn.Red);
                BoardController.getInstance().setBoard(playerRedBoard);
                BoardController.getInstance().placeShipsRandomly();
                break;
            case Multiplayer:
                this.playerBlue = new Human(Turn.Blue);
                this.playerRed = new Human(Turn.Red);
                break;
            case Multiplayer_local:
                this.playerBlue = new Human(Turn.Blue);
                this.playerRed = new Human(Turn.Red);
                break;
            default:
                break;
        }

        this.turn = Turn.Blue;

        setGameOver(false);
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

    public Turn getPlayerTurn() { return this.player;}

    public void setPlayerTurn(Turn playerTurn) { this.player = playerTurn;}

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
