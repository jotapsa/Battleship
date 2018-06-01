package com.battleship.model;


import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.battleship.controller.BoardController;

import java.util.ArrayList;
import java.util.Random;

/**
 * A model representing a computer AI.
 */

public class Computer extends Player{
    private Random generator;
    Orientation orientation = Orientation.randomOrientation(); //inclined to always go with the same dir.

    private ArrayList<Move> moves;


    public Computer(Turn turnToPlay) {
        super(turnToPlay);

        this.generator = new Random(System.currentTimeMillis());

        this.moves = new ArrayList<Move>();
    }

    public Move createMove(Board enemyBoard){
        int x, y;
        Coord target;
        Move move = null;
        int distanceFromTarget;

        BoardController boardController = BoardController.getInstance();
        boardController.setBoard(enemyBoard);

        for(int i=0; i<moves.size(); i++){
            if(moves.get(i).getHitShip()){
                target = moves.get(i).getTarget();
                distanceFromTarget = 0;
                do{
                    if(generator.nextBoolean()){
                        distanceFromTarget +=1;
                    }
                    else{
                        distanceFromTarget -=1;
                    }
                }while(boardController.getBoard().getCell(target) != CellType.ShipHit && boardController.getBoard().getCell(target) != CellType.FreeHit);
            }
        }

        do{
            x = generator.nextInt(enemyBoard.getSize());
            y = generator.nextInt(enemyBoard.getSize());

            target = new Coord(x, y);
        }while(!boardController.isValidTarget(target));

        move = new Move(target, getTurnToPlay());

        moves.add(move);

        return move;
    }
}
