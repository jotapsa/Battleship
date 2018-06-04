package test;

import com.battleship.controller.BoardController;
import com.battleship.controller.GameController;
import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.aux.GameType;
import com.battleship.model.aux.Turn;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class TestLocalMultiplayerGame {

    private GameModel gameModel;
    private GameController gameController;
    private BoardController boardController;
    private Random generator = new Random(System.currentTimeMillis());

    @Test
    public void testLocalMultiplayerGame(){
        gameModel = new GameModel(GameType.Multiplayer_local);
        gameController = new GameController(gameModel, null);
        boardController = BoardController.getInstance();

        boardController.setBoard(gameModel.getPlayerBlueBoard());
        boardController.placeShipsRandomly();

        boardController.setBoard(gameModel.getPlayerRedBoard());
        boardController.placeShipsRandomly();


        while(!gameController.isGameOver()){
            int x = generator.nextInt(10);
            int y = generator.nextInt(10);

            gameController.handleClick(new Coord(x, y));
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
