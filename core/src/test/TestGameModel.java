package test;

import com.battleship.controller.BoardController;
import com.battleship.controller.GameController;
import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.Orientation;
import com.battleship.model.Ship;
import com.battleship.model.aux.CellType;
import com.battleship.model.aux.GameType;
import com.battleship.model.Turn;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class TestGameModel {

    private GameModel gameModel;
    private GameController gameController;
    private BoardController boardController;

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
    public void testFillBoard(){
        gameModel = new GameModel(GameType.SinglePlayer);
        gameController = new GameController(gameModel, null);
        boardController = BoardController.getInstance();

        boardController.setBoard(gameModel.getPlayerBlueBoard());
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

    @Test
    public void testSingleplayerGame(){
        gameModel = new GameModel(GameType.SinglePlayer);
        gameController = new GameController(gameModel, null);
        boardController = BoardController.getInstance();
        boardController.setBoard(gameModel.getPlayerBlueBoard());
        boardController.placeShipsRandomly();

        gameController.handleClick(new Coord(0, 5));
    }


}
