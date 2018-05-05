package com.battleship.view;

import com.badlogic.gdx.ScreenAdapter;
import com.battleship.Battleship;

public class PlacingView extends ScreenAdapter{

    /**
     * The game this screen belongs to.
     */
    private Battleship game;

    /**
     * Creates this screen.
     */
    public PlacingView(Battleship game){
        this.game = game;
    }
}
