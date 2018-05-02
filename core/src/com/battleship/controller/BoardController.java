package com.battleship.controller;

import com.battleship.model.Board;
import com.battleship.model.Coord;
import com.battleship.model.Ship;

public class BoardController {
    private Board board;

    public BoardController(Board board){
        this.board = board;
    }

    public void placeShip(Ship ship, Coord pos){

    }

    public boolean canPlaceShip(Ship ship, Coord pos){
        if (!isValidCoord(pos)){
            return false;
        }

        
        return true;
    }

    public boolean isValidCoord(Coord pos){
        return (pos.getX() >= 0 && pos.getX() < board.getSize()) && (pos.getY() >= 0 && pos.getY() < board.getSize())
    }
}
