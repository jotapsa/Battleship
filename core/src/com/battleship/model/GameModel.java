package com.battleship.model;

import com.battleship.controller.BoardController;
import com.battleship.model.aux.GameType;
import com.battleship.model.aux.Turn;

/**
 * A model representing a Game.
 */

public class GameModel {
    private static int boardSize = 10;

    private GameType gameType;

    private Player playerBlue;
    private Player playerRed;

    private Board playerBlueBoard;
    private Board playerRedBoard;

    private Turn turn;

    private Turn player;

    /**
     * Instantiates a new Game Model.
     *
     * @param gameType the game type
     */
    public GameModel(GameType gameType){
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
    }

    /**
     * Gets Game Type.
     *
     * @return GameType game type
     */
    public GameType getGameType() {
        return gameType;
    }

    /**
     * Gets actual turn.
     *
     * @return Turn turn
     */
    public Turn getTurn(){
        return this.turn;
    }

    /**
     * Gets player blue.
     *
     * @return Player blue player
     */
    public Player getPlayerBlue(){
        return this.playerBlue;
    }

    /**
     * Gets player red.
     *
     * @return Player red player
     */
    public Player getPlayerRed(){
        return this.playerRed;
    }

    /**
     * Gets actual player.
     *
     * @return Turn actual player
     */
    public Turn getPlayerTurn() { return this.player;}

    /**
     * Sets actual player.
     *
     * @param playerTurn Turn
     */
    public void setPlayerTurn(Turn playerTurn) { this.player = playerTurn;}

    /**
     * Next turn.
     */
    public void nextTurn(){
        if (this.turn == Turn.Blue){
            this.turn = Turn.Red;
        } else if (this.turn == Turn.Red){
            this.turn = Turn.Blue;
        }
    }


    /**
     * Gets player blue board.
     *
     * @return Board blue board
     */
    public Board getPlayerBlueBoard(){
        return this.playerBlueBoard;
    }

    /**
     * Gets player red board.
     *
     * @return Board red board
     */
    public Board getPlayerRedBoard(){
        return this.playerRedBoard;
    }


}
