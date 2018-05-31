package com.battleship.model;


import com.battleship.controller.BoardController;

import java.util.Random;

/**
 * A model representing a computer AI.
 */

public class Computer extends Player{
    public Random generator;

    public Computer(Turn turnToPlay){
        super(turnToPlay);
        this.generator = new Random(System.currentTimeMillis());

    }

    public Move createMove(Board enemyBoard){
        int x, y;
        Coord target;
        Move move = null;

        BoardController boardController = BoardController.getInstance();
        boardController.setBoard(enemyBoard);

        do{
            x = generator.nextInt(enemyBoard.getSize());
            y = generator.nextInt(enemyBoard.getSize());

            target = new Coord(x, y);
        }while(!boardController.isValidTarget(target));

        move = new Move(target, getTurnToPlay());
        return move;
    }

}
