package com.battleship.controller;

import com.battleship.model.Board;
import com.battleship.model.aux.CellType;
import com.battleship.model.Coord;
import com.battleship.model.Move;
import com.battleship.model.aux.Orientation;
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
     * @return singleton instance
     */
    public static BoardController getInstance() {
        if (instance == null)
            instance = new BoardController();
        return instance;
    }

    /**
     * Returns board of BoardController
     *
     * @return Board board
     */
    public Board getBoard() {
        return board;
    }


    /**
     * Returns placesShips of board
     *
     * @return HashMap placedShips
     */
    public HashMap<Ship, Coord> getPlacedShips(){
        return board.getPlacedShips();
    }


    /**
     * Set board of BoardController
     *
     * @param board Board
     */
    public void setBoard(Board board){
        this.board = board;
    }

    /**
     * Place Ship in board if possible.
     * @param ship Ship
     * @param pos Coord
     * @return boolean true if successful, false otherwise.
     */
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

    /**
     * Check if is possible to place Ship in Board
     * @param ship Ship
     * @param pos Coord
     * @return boolean true if successful, false otherwise.
     */
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

    /**
     * Remove Ship from board's placedShips
     * @param ship Ship
     */
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

    /**
     * Check if Coord is valid.
     * @param pos Coord
     * @return boolean true if successful, false otherwise.
     */
    public boolean isValidCoord(Coord pos){
        return (pos.getX() >= 0 && pos.getX() < board.getSize()) && (pos.getY() >= 0 && pos.getY() < board.getSize());
    }

    /**
     * Place Random Ships on board.
     */
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

    /**
     * Check if target is valid.
     * @param target Coord
     * @return boolean true if successful, false otherwise.
     */
    public boolean isValidTarget(Coord target){
        if(!isValidCoord(target)){
            return false;
        }

        if(this.board.getCell(target) == CellType.FreeHit || this.board.getCell(target) == CellType.ShipHit){
            return false;
        }

        return true;
    }

    /**
     * Check if pos is inside any ship.
     * @param ship Ship
     * @param shipPos Coord
     * @param pos Coord
     * @return true if sucessful, false otherwise.
     */
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

    /**
     * Returns ship if pos is inside any ship.
     * @param pos Coord
     * @return Ship
     */
    public Ship getShip(Coord pos){
        for(Map.Entry<Ship, Coord> shipPlaced : getPlacedShips().entrySet()){
            if(insideShip(shipPlaced.getKey(), shipPlaced.getValue() ,pos)){
                return shipPlaced.getKey();
            }
        }
        return null;
    }

    /* Shoot at */

    /**
     * Register move on board and returns CellType of board's cell.
     * @param move Move
     * @return CellType cellType
     */
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

    /**
     * Check if all ships are sunk.
     * @return true if sucessful, false otherwise.
     */
    public boolean allSank(){
        boolean sank = true;

        for(Ship ship : this.board.getShips()){
            sank &= ship.isSank();
        }

        return sank;
    }
}
