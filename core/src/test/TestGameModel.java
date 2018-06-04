package test;

import com.battleship.controller.BoardController;
import com.battleship.controller.GameController;
import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.aux.Orientation;
import com.battleship.model.Ship;
import com.battleship.model.aux.CellType;
import com.battleship.model.aux.GameType;
import com.battleship.model.aux.Turn;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class TestGameModel {

    private GameModel gameModel;
    private GameController gameController;
    private BoardController boardController;
    private Random generator = new Random(System.currentTimeMillis());

    @Test
    public void testGameModel(){
        gameModel = new GameModel(GameType.SinglePlayer);
        gameController = new GameController(gameModel, null);

        assertEquals(gameModel.getGameType(), GameType.SinglePlayer);

        assertEquals(gameModel.getTurn(), Turn.Blue);

        gameController.handleClick(new Coord(-1, 5));
        assertEquals(gameModel.getTurn(), Turn.Blue);

        gameController.handleClick(new Coord(0, 5));
        assertEquals(gameModel.getTurn(), Turn.Red);
    }

    @Test
    public void testHitShip(){
        gameModel = new GameModel(GameType.SinglePlayer);
        gameController = new GameController(gameModel, null);
        boardController = BoardController.getInstance();

        boardController.setBoard(gameModel.getPlayerBlueBoard());
        boardController.placeShipsRandomly();

        for(Map.Entry<Ship, Coord> shipBoard : gameModel.getPlayerRedBoard().getPlacedShips().entrySet()){
            Ship ship = shipBoard.getKey();
            Coord pos = shipBoard.getValue(), shipPos;

            for(int i=0; i < ship.getShipType().getSize(); i++){
                if(ship.getOrientation() == Orientation.Vertical){
                    shipPos = new Coord(pos.getX(), pos.getY()-i);
                }
                else{
                    shipPos = new Coord(pos.getX()+i, pos.getY());
                }

                gameController.handleClick(shipPos);

                assertEquals(CellType.ShipHit, gameModel.getPlayerRedBoard().getCell(shipPos));
            }

            assertTrue(ship.isSank());
        }

        assertTrue(gameController.isGameOver());
    }

    @Test
    public void testFreeHit(){
        Coord pos;
        gameModel = new GameModel(GameType.SinglePlayer);
        gameController = new GameController(gameModel, null);
        boardController = BoardController.getInstance();

        boardController.setBoard(gameModel.getPlayerBlueBoard());
        boardController.placeShipsRandomly();

        do{
            int x = generator.nextInt(10);
            int y = generator.nextInt(10);
            pos = new Coord(x, y);

        }while(boardController.getBoard().getCell(pos) != CellType.Free);

        gameController.handleClick(pos);

        assertEquals(CellType.FreeHit, gameModel.getPlayerRedBoard().getCell(pos));

    }


}
