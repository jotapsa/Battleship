package test;

import com.battleship.controller.GameController;
import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.GameType;
import com.battleship.model.Turn;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestGameModel {

    private GameModel gameModel;
    private GameController gameController;

    @Test
    public void testGameModel(){
        gameModel = new GameModel(GameType.SinglePlayer);
        gameController = new GameController(gameModel);

        assertEquals(gameModel.getGameType(), GameType.SinglePlayer);

        assertEquals(gameModel.getTurn(), Turn.Blue);

        gameController.handleClick(new Coord(-1, 5));
    }


}
