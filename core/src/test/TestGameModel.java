package test;

import com.battleship.controller.GameController;
import com.battleship.model.GameModel;
import com.battleship.model.aux.GameType;

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
    }


}
