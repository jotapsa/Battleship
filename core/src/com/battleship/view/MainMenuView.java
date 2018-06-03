package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

    private Stage stage;
    private Table table;
    private Skin skin;


    private TextButton singleplayerBtn;
    private TextButton multiplayerLocalBtn;
    private TextButton multiplayerBtn;
    private TextButton exitBtn;

    private Texture background;
    private Texture title;

    public MainMenuView(Battleship game) {
        this.game = game;

        this.stage = new Stage();
        this.stage.clear();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        this.background = new Texture("background.png");
        this.title = new Texture("battleship.png");

        this.skin = new Skin(Gdx.files.internal("skin/quantum-horizon/quantum-horizon-ui.json"));
        this.table = new Table();

        setUpButtons();
        createButtons();
    }

    private void setUpButtons() {
        singleplayerBtn = new TextButton("SinglePlayer", skin, "default");
        singleplayerBtn.setColor(0, 0, 1, 1);
        singleplayerBtn.getLabel().setFontScale(2);
        singleplayerBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                dispose();
                game.startSingleplayerGame();
            }
        });

        multiplayerLocalBtn = new TextButton("Local Multiplayer", skin, "default");
        multiplayerLocalBtn.setColor(0, 0, 1, 1);
        multiplayerLocalBtn.getLabel().setFontScale(2);
        multiplayerLocalBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                dispose();
                game.startMultiplayerLocal();
            }
        });

        multiplayerBtn = new TextButton("Multiplayer", skin, "default");
        multiplayerBtn.setColor(0, 0, 1, 1);
        multiplayerBtn.getLabel().setFontScale(2);
        multiplayerBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if(game.getIP().size() < 2){
                    return;
                }

                dispose();
                game.startMultiplayer();
            }
        });

        exitBtn = new TextButton("Exit", skin, "default");
        exitBtn.setColor(0, 0, 1, 1);
        exitBtn.getLabel().setFontScale(2);
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                //TODO: Exit stuff
                System.exit(0);
            }
        });
    }

    private void createButtons() {
        table.setFillParent(true);
        stage.addActor(table);

        table.row();
        table.add(singleplayerBtn).width(Gdx.graphics.getWidth() / 2).height(100);

        table.row();
        table.add(multiplayerLocalBtn).width(Gdx.graphics.getWidth() / 2).height(100);

        table.row();
        table.add(multiplayerBtn).width(Gdx.graphics.getWidth() / 2).height(100);

        table.row();
        table.add(exitBtn).width(Gdx.graphics.getWidth() / 2).height(100);
    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0, 0, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().draw(title, 200, 1200, 700, 400);
        game.getBatch().end();

        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();

        stage.dispose();

        background.dispose();
        title.dispose();

        skin.dispose();
    }
}
