package com.battleship.model;


import com.battleship.controller.BoardController;
import com.battleship.model.aux.Orientation;
import com.battleship.model.aux.Turn;

import java.util.ArrayList;
import java.util.Random;

/**
 * A model representing a computer AI.
 */

public class Computer extends Player{
    private Random generator;

    BoardController boardController;

    private ArrayList<Move> moves;


    public Computer(Turn turnToPlay) {
        super(turnToPlay);

        this.generator = new Random(System.currentTimeMillis());

        this.boardController = BoardController.getInstance();

        this.moves = new ArrayList<Move>();
    }

    public Move createMove(Board enemyBoard){
        int x, y;
        Orientation orientation;
        Coord target;
        Move move = null;
        int distanceFromTarget;
        boolean freeHit;

        BoardController boardController = BoardController.getInstance();
        boardController.setBoard(enemyBoard);

        for(Move m : moves){
            if(m.getHitShip()){
                while(!m.isProcessed()){
                    //ellaborate on guess
                    distanceFromTarget = 0;
                    freeHit = false;

                    switch(m.getOrientation()){
                        case Horizontal:
                            while(!freeHit){
                                distanceFromTarget++;
                                if(!boardController.isValidCoord(new Coord(m.getTarget().getX()+distanceFromTarget*m.getWayMultiplier(), m.getTarget().getY()))){
                                    m.flip();
                                    break;
                                }
                                switch (boardController.getBoard().getCell(new Coord(m.getTarget().getX()+distanceFromTarget*m.getWayMultiplier(), m.getTarget().getY()))){
                                    case ShipHit:
                                        break;
                                    case FreeHit:
                                        freeHit = true;
                                        m.flip();
                                        break;
                                    default:
                                        move = new Move(new Coord(m.getTarget().getX()+distanceFromTarget*m.getWayMultiplier(), m.getTarget().getY()), getTurnToPlay());
                                        moves.add(move);
                                        return move;
                                }
                            }
                            break;
                        case Vertical:
                            while(!freeHit){
                                distanceFromTarget++;
                                if(!boardController.isValidCoord(new Coord(m.getTarget().getX(), m.getTarget().getY()+distanceFromTarget*m.getWayMultiplier()))){
                                    m.flip();
                                    break;
                                }
                                switch (boardController.getBoard().getCell(new Coord(m.getTarget().getX(), m.getTarget().getY()+distanceFromTarget*m.getWayMultiplier()))){
                                    case ShipHit:
                                        break;
                                    case FreeHit:
                                        freeHit = true;
                                        m.flip();
                                        break;
                                    default:
                                        move = new Move(new Coord(m.getTarget().getX(), m.getTarget().getY()+distanceFromTarget*m.getWayMultiplier()), getTurnToPlay());
                                        moves.add(move);
                                        return move;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        do{
            x = generator.nextInt(enemyBoard.getSize());
            y = generator.nextInt(enemyBoard.getSize());

            target = new Coord(x, y);
        }while(!boardController.isValidTarget(target));

        move = new Move(target, getTurnToPlay(), false);

        moves.add(move);

        return move;
    }
}
