package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.battleship.Battleship;

public class MultiplayerView extends ScreenAdapter{
    private Battleship game;
    private Stage stage;
    private Table table;
    private Skin skin;
    private Skin skinDialog;

    private TextButton joinRoomBtn;
    private TextButton createRoomBtn;

    private Dialog joinRoomDialog;
    private Dialog createRoomDialog;

    private Texture background;

    public MultiplayerView(Battleship game){
        this.game = game;

        this.stage = new Stage();
        this.stage.clear();

        this.background = new Texture("background.png");

        this.skin = new Skin(Gdx.files.internal("skin/quantum-horizon/quantum-horizon-ui.json"));
        Gdx.input.setInputProcessor(stage);

        this.table = new Table(skin);
        table.setFillParent(true);
        this.stage.addActor(table);

        setupButtons();
        addButtons();


    }

    private void setupButtons() {
        this.createRoomBtn = new TextButton("Create Room", skin , "default");
        createRoomBtn.setColor(0, 0, 1, 1);
        createRoomBtn.getLabel().setFontScale(2);
        createRoomBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y) {
                showCreateRoomDialog();
            }
        });

        this.joinRoomBtn = new TextButton("Join Room", skin, "default");
        joinRoomBtn.setColor(0, 0, 1, 1);
        joinRoomBtn.getLabel().setFontScale(2);
        joinRoomBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                showJoinRoomDialog();
            }
        });
    }

    private void addButtons() {
        table.setFillParent(true);
        stage.addActor(table);

        table.row();
        table.add(createRoomBtn).width(Gdx.graphics.getWidth() / 2).height(100);

        table.row();
        table.add(joinRoomBtn).width(Gdx.graphics.getWidth() / 2).height(100);
    }

    private void showCreateRoomDialog() {
        final Dialog createRoomDialog = new Dialog("Create Room", skin, "default");
        createRoomDialog.setColor(0, 0, 1, 1);

        TextButton createBtn = new TextButton("Create", skin, "default");
        createBtn.setColor(0,0,1,1);
        createBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                //go to roomView where u show only IP to user.
                dispose();
                game.showRoom();
            }
        });
        createRoomDialog.add(createBtn);

        TextButton backBtn = new TextButton("Back", skin, "default");
        backBtn.setColor(0,0,1,1);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                createRoomDialog.hide();
            }
        });
        createRoomDialog.add(backBtn);

        createRoomDialog.show(stage);
    }

    private void showJoinRoomDialog() {
        final Dialog joinRoomDialog = new Dialog("Join Room", skin, "default");
        joinRoomDialog.setColor(0, 0, 1, 1);

        final TextField ipInput = new TextField("0.0.0.0", skin, "default");
        ipInput.setColor(0,0,1,1);
        joinRoomDialog.add(ipInput);

        final TextField portInput = new TextField("7777", skin, "default");
        portInput.setColor(0,0,1,1);
        joinRoomDialog.add(portInput);

        TextButton joinBtn = new TextButton("Join", skin, "default");
        joinBtn.setColor(0,0,1,1);
        joinBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                String ip = ipInput.getText();
                String portText = portInput.getText();

                //validate IP & port

                //join room and go to game
            }
        });
        joinRoomDialog.add(joinBtn);

        TextButton backBtn = new TextButton("Back", skin, "default");
        backBtn.setColor(0,0,1,1);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                joinRoomDialog.hide();
            }
        });
        joinRoomDialog.add(backBtn);
        joinRoomDialog.show(stage);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        update(delta);

        Gdx.gl.glClearColor(1,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();

        stage.draw();
    }

    private void update(float delta) {
        handleInputs(delta);
    }

    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.showMenu();
            dispose();
        }
    }


    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        background.dispose();
        skin.dispose();
    }
}
