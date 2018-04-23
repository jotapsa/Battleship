package com.battleship.view;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.battleship.Battleship;

public class MainMenuView extends ScreenAdapter {
    private Battleship game;

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    /**
     * Creates this screen.
     */
    public MainMenuView(Battleship game){
        this.game = game;

        loadAssets();

        this.camera = createCamera();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(200, 200);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
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
        super.render(delta);


    }
}
