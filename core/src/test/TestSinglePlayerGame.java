package test;

import com.battleship.controller.BoardController;
import com.battleship.controller.GameController;
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
import static org.junit.Assert.assertTrue;

public class TestSinglePlayerGame {

    private GameModel gameModel;
    private GameController gameController;
    private BoardController boardController;
    private Random generator = new Random(System.currentTimeMillis());

    @Test
    public void testSingleplayerGame(){
        gameModel = new GameModel(GameType.SinglePlayer);
        gameController = new GameController(gameModel, null);
        boardController = BoardController.getInstance();
        boardController.setBoard(gameModel.getPlayerBlueBoard());
        boardController.placeShipsRandomly();


        while(!gameController.isGameOver()){
            if(gameModel.getTurn() == Turn.Blue){
                int x = generator.nextInt(10);
                int y = generator.nextInt(10);
                gameController.handleClick(new Coord(x, y));
            }
            else{
                gameController.update(0);
            }
        }

        if(gameController.getWinner() == Turn.Blue){
            boardController.setBoard(gameModel.getPlayerRedBoard());
            assertTrue(boardController.allSank());
        }
        else{
            boardController.setBoard(gameModel.getPlayerBlueBoard());
            assertTrue(boardController.allSank());
        }
    }


}
