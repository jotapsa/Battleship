package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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

    private TextButton joinRoomBtn;
    private TextButton createRoomBtn;

    private Dialog joinRoomDialog;
    private Dialog createRoomDialog;

    public MultiplayerView(Battleship game){
        this.game = game;

        this.stage = new Stage();
        this.stage.clear();

        this.skin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));
        Gdx.input.setInputProcessor(stage);

        this.table = new Table(skin);
        table.setFillParent(true);
        this.stage.addActor(table);

        setupButtons();
        addButtons();


    }

    private void setupButtons() {
        this.joinRoomBtn = new TextButton("Join Room", skin, "default");
        joinRoomBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                showJoinRoomDialog();
            }
        });

        this.createRoomBtn = new TextButton("Create Room", skin , "default");
        createRoomBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y) {
                showCreateRoomDialog();
            }
        });
    }

    private void addButtons() {
        table.add(joinRoomBtn).width(100).height(40).pad(20);
        table.row();

        table.add(createRoomBtn).width(100).height(40).pad(20);
        table.row();
    }

    private void showJoinRoomDialog() {
        joinRoomDialog = new Dialog("Join Room", skin, "default");

        final TextField ipInput = new TextField("0.0.0.0", skin, "default");
        joinRoomDialog.add(ipInput);

        final TextField portInput = new TextField("7777", skin, "default");
        joinRoomDialog.add(portInput);

        TextButton joinBtn = new TextButton("Join", skin, "default");
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
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                joinRoomDialog.hide();
            }
        });
        joinRoomDialog.add(backBtn);
        joinRoomDialog.show(stage);
    }

    private void showCreateRoomDialog() {
        createRoomDialog = new Dialog("Create Room", skin, "default");

        final TextField roomNameInput = new TextField("Name", skin, "default");
        createRoomDialog.add(roomNameInput);

        TextButton createBtn = new TextButton("Create", skin, "default");
        createBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                // Create room with room name
                String roomName = roomNameInput.getText();
                //validate roomName

                //go to roomView where u show only IP to user.
                dispose();
                game.showRoom();
            }
        });
        createRoomDialog.add(createBtn);

        TextButton backBtn = new TextButton("Back", skin, "default");
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                createRoomDialog.hide();
            }
        });
        createRoomDialog.add(backBtn);
        createRoomDialog.show(stage);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta){
        super.render(delta);

        Gdx.gl.glClearColor(1,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.draw();
    }


    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }
}
