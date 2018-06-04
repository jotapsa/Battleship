package test;

import com.battleship.controller.BoardController;
import com.battleship.controller.GameController;
import com.battleship.model.Board;
import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.Ship;
import com.battleship.model.aux.CellType;
import com.battleship.model.aux.GameType;
import com.battleship.model.aux.Orientation;
import com.battleship.model.aux.Turn;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBoardModel {

    private Board board;
    private BoardController boardController;
    private Random generator = new Random(System.currentTimeMillis());

    @Test
    public void TestBoardModel(){
        int x=0, y=0;
        Coord pos, shipPos;
        board = new Board(10);
        boardController = BoardController.getInstance();
        boardController.setBoard(board);

        //place ships
        for(int i=0; i < board.getShips().size(); i++){
            Ship ship = board.getShips().get(i);

            do{
                x = generator.nextInt(10);
                y = generator.nextInt(10);

                pos = new Coord(x,y);

                Orientation orientation = Orientation.randomOrientation();
                ship.setOrientation(orientation);
            } while(!boardController.canPlaceShip(ship, pos));

            boardController.placeShip(ship,pos);

            for(int j=0; j < ship.getShipType().getSize(); j++){
                if(ship.getOrientation() == Orientation.Vertical){
                    shipPos = new Coord(pos.getX(), pos.getY()-j);
                }
                else{
                    shipPos = new Coord(pos.getX()+j, pos.getY());
                }

                assertEquals(CellType.Ship, board.getCell(shipPos));
            }

            assertEquals(i+1, boardController.getPlacedShips().size());
            assertFalse(ship.isSank());
            assertTrue(ship.IsPlaced());
        }

        //remove ships
        for(Ship ship : board.getShips()){
            pos = board.getPlacedShips().get(ship);

            boardController.removeShip(ship);

            for(int j=0; j < ship.getShipType().getSize(); j++){
                if(ship.getOrientation() == Orientation.Vertical){
                    shipPos = new Coord(pos.getX(), pos.getY()-j);
                }
                else{
                    shipPos = new Coord(pos.getX()+j, pos.getY());
                }

                assertFalse(ship.IsPlaced());
                assertEquals(CellType.Free, board.getCell(shipPos));
            }
        }
    }

    @Test
    public void testFillBoardRandomly(){
        board = new Board(10);
        boardController = BoardController.getInstance();

        boardController.setBoard(board);
        assertEquals(6, boardController.getBoard().getShips().size());
        assertEquals(0, boardController.getPlacedShips().size());

        boardController.placeShipsRandomly();

        assertEquals(6, boardController.getPlacedShips().size());

        for(Map.Entry<Ship, Coord> shipBoard : boardController.getPlacedShips().entrySet() ){
            Ship ship = shipBoard.getKey();
            Coord shipPos = shipBoard.getValue();
            Coord pos;

            for(int i=0; i<ship.getShipType().getSize(); i++){
                if(ship.getOrientation() == Orientation.Vertical){
                    pos = new Coord(shipPos.getX(), shipPos.getY()-i);
                }
                else{
                    pos = new Coord(shipPos.getX()+i, shipPos.getY());
                }

                assertEquals(boardController.getBoard().getCell(pos), CellType.Ship);
            }
        }
    }
}
