package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.battleship.Battleship;

import javax.xml.soap.Text;

public class MainMenuView extends ScreenAdapter {

    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 1f;

    /**
     * The width of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float VIEWPORT_WIDTH = 30;

    /**
     * The game this screen belongs to.
     */
    private Battleship game;

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    private Stage stage;
    private Table table;
    private Skin skin;


    /**
     * Creates this screen.
     */
    public MainMenuView(Battleship game){
        this.game = game;
        this.camera = createCamera();

        this.stage = new Stage();
        this.stage.clear();
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));
        this.table = new Table();

        createButtons();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER,
                VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
    }

    private void createButtons(){
        table.setFillParent(true);
        stage.addActor(table);

        table.row();
        TextButton singleplayerBtn  = new TextButton("SinglePlayer", skin, "default");
        table.add(singleplayerBtn);

        table.row();
        TextButton multiplayerBtn = new TextButton("Multiplayer", skin, "default");
        table.add(multiplayerBtn);

        table.row();
        TextButton exitBtn = new TextButton("Exit", skin, "default");
        table.add(exitBtn);
    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    public void render(float delta){
        super.render(delta);

        handleInputs(delta);

        Gdx.gl.glClearColor( 0, 0, 0f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

//        stage.act();
        stage.draw();
    }

    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {


    }
}
