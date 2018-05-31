package com.battleship.controller;

import com.battleship.model.Board;
import com.battleship.model.CellType;
import com.battleship.model.Coord;
import com.battleship.model.Move;
import com.battleship.model.Orientation;
import com.battleship.model.Ship;

public class BoardController {
    private Board board;

    public BoardController(){
    }

    public BoardController(Board board){
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void placeShip(Ship ship, Coord pos){
        if(!canPlaceShip(ship, pos)){
            return;
        }

        if(ship.getOrientation() == Orientation.Horizontal){
            for(int i=0; i<ship.getShipType().getSize(); i++){
                this.board.setCell(new Coord(pos.getX()+i, pos.getY()), CellType.Ship);
            }
        }

        if(ship.getOrientation() == Orientation.Vertical){
            for(int i=0; i<ship.getShipType().getSize(); i++){
                this.board.setCell(new Coord(pos.getX(), pos.getY()+i), CellType.Ship);
            }
        }

        ship.setIsPlaced(true);
        board.addShip(ship);
    }

    public boolean canPlaceShip(Ship ship, Coord pos){
        if (!isValidCoord(pos)){
            return false;
        }

        if(ship.getOrientation() == Orientation.Vertical){
            for(int i=0; i<ship.getShipType().getSize(); i++){
                if(this.board.getCell(new Coord(pos.getX(), pos.getY()+i)) != CellType.Free){
                    return false;
                }
            }
        } else if(ship.getOrientation() == Orientation.Horizontal){
            for(int i=0; i<ship.getShipType().getSize(); i++){
                if(this.board.getCell(new Coord(pos.getX()+i, pos.getY())) != CellType.Free){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isValidCoord(Coord pos){
        return (pos.getX() >= 0 && pos.getX() < board.getSize()) && (pos.getY() >= 0 && pos.getY() < board.getSize());
    }

    public boolean isValidTarget(Coord target){
        if(!isValidCoord(target)){
            return false;
        }

        if(this.board.getCell(target) == CellType.FreeHit || this.board.getCell(target) == CellType.ShipHit){
            return false;
        }

        return true;
    }

    /* Shoot at */
    public void doMove(Move move){
        switch(this.board.getCell(move.getTarget())){
            case Free:
                this.board.setCell(move.getTarget(), CellType.FreeHit);
                move.setHitShip(false);
                break;
            case Ship:
                this.board.setCell(move.getTarget(), CellType.ShipHit);
                move.setHitShip(true);
                break;
            default:
                break;
        }
    }
}
