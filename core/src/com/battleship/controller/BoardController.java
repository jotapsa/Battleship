package com.battleship.controller;

import com.battleship.model.Board;
import com.battleship.model.aux.CellType;
import com.battleship.model.Coord;
import com.battleship.model.Move;
import com.battleship.model.Orientation;
import com.battleship.model.Ship;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BoardController {
    /**
     * The singleton instance of this controller
     */
    private static BoardController instance;

    private Random generator;

    private Board board;

    private BoardController(){
        this.generator = new Random(System.currentTimeMillis());
    }

    /**
     * Returns a singleton instance of a board controller
     *
     * @return the singleton instance
     */
    public static BoardController getInstance() {
        if (instance == null)
            instance = new BoardController();
        return instance;
    }

    public Board getBoard() {
        return board;
    }

    public HashMap<Ship, Coord> getPlacedShips(){
        return board.getPlacedShips();
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public boolean placeShip(Ship ship, Coord pos){
        if(ship == null){
            return false;
        }

        if(!canPlaceShip(ship, pos)){
            return false;
        }

        for(int i=0; i<ship.getShipType().getSize(); i++){
            if(ship.getOrientation() == Orientation.Vertical){
                this.board.setCell(new Coord(pos.getX(), pos.getY()-i), CellType.Ship);
            }
            else{
                this.board.setCell(new Coord(pos.getX()+i, pos.getY()), CellType.Ship);
            }
        }

        ship.setIsPlaced(true);
        board.addShip(ship, pos);
        return true;
    }

    public boolean canPlaceShip(Ship ship, Coord pos){
        if (!isValidCoord(pos)){
            return false;
        }

        Coord shipPos;

        for(int i=0; i<ship.getShipType().getSize(); i++){
            if(ship.getOrientation() == Orientation.Vertical){
                shipPos = new Coord(pos.getX(), pos.getY()-i);
            }
            else{
                shipPos = new Coord(pos.getX()+i, pos.getY());
            }

            if(!isValidCoord(shipPos) || this.board.getCell(shipPos) != CellType.Free){
                return false;
            }
        }

        return true;
    }

    public void removeShip(Ship ship){
        if(ship == null || !this.board.getPlacedShips().containsKey(ship)){
            return;
        }

        Coord shipPos = this.board.getPlacedShips().get(ship);

        for(int i=0; i<ship.getShipType().getSize(); i++){
            if(ship.getOrientation() == Orientation.Vertical){
                this.board.setCell(new Coord(shipPos.getX(), shipPos.getY()-i), CellType.Free);
            }
            else{
                this.board.setCell(new Coord(shipPos.getX()+i, shipPos.getY()), CellType.Free);
            }
        }

        this.board.getPlacedShips().remove(ship);
    }

    public boolean isValidCoord(Coord pos){
        return (pos.getX() >= 0 && pos.getX() < board.getSize()) && (pos.getY() >= 0 && pos.getY() < board.getSize());
    }

    public void placeShipsRandomly(){
        Orientation orientation;
        int x, y;
        Coord pos;

        for(Ship s : board.getShips()){
            do {
                orientation = Orientation.randomOrientation();
                s.setOrientation(orientation);

                x = generator.nextInt(10);
                y = generator.nextInt(10);

                pos = new Coord(x, y);
            }while(!placeShip(s, pos));
        }
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

    public boolean insideShip(Ship ship, Coord shipPos, Coord pos){
        for(int i=0; i<ship.getShipType().getSize(); i++){
            if(ship.getOrientation() == Orientation.Vertical && pos.equals(new Coord(shipPos.getX(), shipPos.getY()-i))){
                return true;
            }
            else if(pos.equals(new Coord(shipPos.getX()+i, shipPos.getY()))){
                return true;
            }
        }
        return false;
    }

    public Ship getShip(Coord pos){
        for(Map.Entry<Ship, Coord> shipPlaced : getPlacedShips().entrySet()){
            if(insideShip(shipPlaced.getKey(), shipPlaced.getValue() ,pos)){
                return shipPlaced.getKey();
            }
        }
        return null;
    }

    /* Shoot at */
    public CellType doMove(Move move){
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

        if(move.getHitShip() && getShip(move.getTarget()) != null){
            getShip(move.getTarget()).hit();
        }

        return this.board.getCell(move.getTarget());
    }

    public boolean allSank(){
        boolean sank = true;

        for(Ship ship : this.board.getShips()){
            sank &= ship.isSank();
        }

        return sank;
    }
}
