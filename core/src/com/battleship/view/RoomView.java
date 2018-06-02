package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.battleship.Battleship;

public class RoomView extends ScreenAdapter{
    private Battleship game;

    private Stage stage;
    private Table table;
    private Table roomsTable;
    private Skin skin;

    private TextButton createRoomBtn;

    public RoomView(Battleship game){
        this.game = game;

        this.stage = new Stage();
        this.stage.clear();

        this.skin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));
        Gdx.input.setInputProcessor(stage);

        this.table = new Table(skin);
        table.setFillParent(true);
        this.stage.addActor(table);

        //info about game

        //show rooms available.
        this.roomsTable = new Table(skin);
        table.add(roomsTable);
        table.row();

        //setup roomsTable
        updateRoomsTable();

        // setup start game button
        this.createRoomBtn = new TextButton("Create Room", skin, "default");
        createRoomBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
//                game.setGameScreen(gamePackage);
            }
        });
        table.add(createRoomBtn);
    }

    private void updateRoomsTable() {
        //for number of rooms
        for(int i=0; i<4; i++){
            Label roomLabel = new Label("Room " + i + " IP: 122.23.2.3" + "Port: 7223", skin ,"default");

            roomsTable.add(roomLabel);
            roomsTable.row();
        }
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
