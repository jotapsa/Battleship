package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.battleship.Battleship;

public class ConnectionView extends ScreenAdapter {
    private Battleship game;

    private int DISPLAY_HEIGHT = Gdx.graphics.getHeight();
    private int DISPLAY_WIDTH = Gdx.graphics.getWidth();


    /**
     * The width of the viewport in cells.
     */
    private static final float VIEWPORT_WIDTH = 10;

    /**
     * The width of the viewport in cells.
     */
    private static final float VIEWPORT_HEIGHT = 21;

    /**
     * Pixels per cell (square cell)
     */
    private static final float PPC = 32;

    /**
     * The camera used to show the viewport.
     */
    private OrthographicCamera camera;
    private ExtendViewport gamePort;

    public ConnectionView(Battleship game){
        this.game = game;

    }

    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private void createCamera() {
        this.camera = new OrthographicCamera(VIEWPORT_WIDTH * PPC, VIEWPORT_HEIGHT*PPC);

        this.gamePort = new ExtendViewport(VIEWPORT_WIDTH * PPC, VIEWPORT_HEIGHT*PPC, camera);

//        camera.position.set(gamePort.getWorldWidth() / 2f, gamePort.getWorldHeight() / 2f, 0);
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    public void render(float delta){
        super.render(delta);
        update(delta);

        Gdx.gl.glClearColor( 1f, 1f, 1f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();

        game.getBatch().end();
    }

    private void update(float delta) {
        handleInputs(delta);

        camera.update();
    }

    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {
        if(Gdx.input.justTouched()){
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePos); // mousePos is now in world coordinates
            int x = (int)mousePos.x/32 - 1;
            int y = (int)mousePos.y/32 - 1;
            Gdx.app.log("Battleship", "x - " + x + " y - " + y);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.showMenu();
            dispose();
        }
    }

    public void dispose() {
        super.dispose();
    }
}
