package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.battleship.Battleship;
import com.battleship.model.aux.Turn;
import com.battleship.networking.msg.JoinMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class MultiplayerView extends ScreenAdapter{
    private Battleship game;
    private Stage stage;
    private Table table;
    private Skin skin;
    private Skin skinDialog;

    private TextButton joinRoomBtn;
    private TextButton createRoomBtn;

    private Dialog joinRoomDialog;

    private Texture background;

    public MultiplayerView(Battleship game){
        this.game = game;

        this.stage = new Stage();
        this.stage.clear();

        this.background = new Texture("background.png");

        this.skin = new Skin(Gdx.files.internal("skin/quantum-horizon/quantum-horizon-ui.json"));
        this.skinDialog = new Skin(Gdx.files.internal("skin/neon/neon-ui.json"));
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
                dispose();
                game.showRoom();
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


    private void showJoinRoomDialog() {
        this.joinRoomDialog = new Dialog("Join Room", skin, "default");
        joinRoomDialog.getTitleLabel().setColor(1,1,1,1);
        joinRoomDialog.setColor(0, 0, (float) 0.7, 1);

        final TextField ipInput = new TextField("0.0.0.0", skinDialog, "default");
        ipInput.setColor(1,1,1,1);
        joinRoomDialog.getContentTable().add(ipInput).size(200, 50);

        TextButton joinBtn = new TextButton("Join", skin, "default");
        joinBtn.setColor(0,0,1,1);
        joinBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                String ip = ipInput.getText();
                game.setIpEnemy(ip);
                Socket socket;

                //join room and go to game
                JoinMessage msg = new JoinMessage(game.getIP().get(1));
                SocketHints socketHints = new SocketHints();
                socketHints.connectTimeout = 0;

                try {
                    socket = Gdx.net.newClientSocket(Protocol.TCP, ip, game.defaultPort, socketHints);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.write(msg.toString());
                    out.flush();

                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String response = inFromServer.readLine();
                    System.out.println(response);

                    String[] msgArgs = response.split(" ");

                    if(msgArgs[0].equals("ACCEPT")){
                        dispose();
                        game.startPlacingView(Turn.Red);
                    }

                    inFromServer.close();
                    out.close();

                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                catch(GdxRuntimeException ex){
                    System.out.println("Fail to open " + ip);
                    stage.setKeyboardFocus(ipInput);
                }

            }
        });
        joinRoomDialog.getButtonTable().add(joinBtn);

        TextButton backBtn = new TextButton("Back", skin, "default");
        backBtn.setColor(0,0,1,1);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                joinRoomDialog.setVisible(false);
            }
        });

        joinRoomDialog.getButtonTable().add(backBtn);

        joinRoomDialog.setScale(2);
        joinRoomDialog.setPosition(100, Gdx.graphics.getHeight()/3);
        joinRoomDialog.show(stage, sequence(Actions.alpha(0), Actions.fadeIn(0.4f, Interpolation.fade)));
        stage.setKeyboardFocus(ipInput);
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
