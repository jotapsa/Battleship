package com.battleship.view;

import com.badlogic.gdx.ScreenAdapter;
import com.battleship.Battleship;

public class GameScreen extends ScreenAdapter {
    private Battleship game;

    /**
     * Creates this screen.
     *
     * @param game The game this screen belongs to
     */
    public GameScreen(Battleship game){
        this.game = game;

        loadAssets();
    }

    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets(){

    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    public void render(float delta){

    }
}