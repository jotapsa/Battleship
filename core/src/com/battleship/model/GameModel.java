package com.battleship.model;

import com.battleship.BattleShipState;

/**
 * A model representing a game.
 */

public class GameModel {
    private static int boardSize = 10;

    private GameType gameType;

    private Player p1;
    private Player p2;

    private Board player1Board;
    private Board player2Board;

    private Turn turn;

    private boolean gameOver;

    public GameModel(GameType gameType){
        this.gameType = gameType;

        this.turn = Turn.Blue;
        setGameOver(false);

        this.p1 = new Player();

        if(gameType == GameType.SinglePlayer){
            this.p2 = new Computer();
        }
        else{
            this.p2 = new Player();
        }

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

    public Player getPlayer1(){
        return this.p1;
    }

    public Player getPlayer2(){
        return this.p2;
    }

    public Board getPlayer1Board(){
        return this.player1Board;
    }

    public Board getPlayer2Board(){
        return this.player2Board;
    }


}
