package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.battleship.Battleship;


public class MainMenuView extends ScreenAdapter {

    /**
     * The game this screen belongs to.
     */
    private Battleship game;

    /**
     * The camera used to show the viewport.
     */
//    private final OrthographicCamera camera;

    private Stage stage;
    private Table table;
    private Skin skin;


    private TextButton singleplayerBtn;
    private TextButton multiplayerBtn;
    private TextButton exitBtn;

    /**
     * Creates this screen.
     */
    public MainMenuView(Battleship game){
        this.game = game;

        this.stage = new Stage();
        this.stage.clear();
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));
        this.table = new Table();

        setUpButtons();
        createButtons();
    }

    private void setUpButtons(){
        singleplayerBtn  = new TextButton("SinglePlayer", skin, "default");
        singleplayerBtn.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                game.startSingleplayer();
            }
        });
        multiplayerBtn = new TextButton("Multiplayer", skin, "default");
        multiplayerBtn.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                game.startMultiplayer();
            }
        });
        exitBtn = new TextButton("Exit", skin, "default");
        exitBtn.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                //TODO: Exit stuff
                System.exit(0);
            }
        });
    }

    private void createButtons(){
        table.setFillParent(true);
        stage.addActor(table);

        table.row();
        table.add(singleplayerBtn);

        table.row();
        table.add(multiplayerBtn);

        table.row();
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

        Gdx.gl.glClearColor( 0, 0, 1f, 1 );
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
