package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.battleship.Battleship;
import com.battleship.model.Turn;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class RoomView extends ScreenAdapter{
    private Battleship game;

    private Stage stage;
    private Group group;
    private Skin skin;

    private Label helpLbl;
    private Label ipLbl;

    public RoomView(Battleship game){
        this.game = game;

        this.stage = new Stage();
        this.stage.clear();

        this.skin = new Skin(Gdx.files.internal("skin/quantum-horizon/quantum-horizon-ui.json"));
        Gdx.input.setInputProcessor(stage);

        this.group = new Group();
        this.stage.addActor(group);


        // The following code loops through the available network interfaces
        // Keep in mind, there can be multiple interfaces per device, for example
        // one per NIC, one per active wireless and the loopback
        // In this case we only care about IPv4 address ( x.x.x.x format )
        List<String> addresses = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // Print the contents of our array to a string.  Yeah, should have used StringBuilder
        String ipAddress = new String("");
        for(String str:addresses) {
            ipAddress = ipAddress + str + "\n\n";
        }

        ipLbl = new Label(ipAddress,skin, "title", "white");

        group.addActor(ipLbl);
        group.setScale((float) 2.5);
        group.setPosition(50, Gdx.graphics.getHeight()/3);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        update(delta);

        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.draw();
    }

    public void update(float delta){
        handleInputs(delta);
    }

    public void handleInputs(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.startMultiplayer();
            dispose();
        }
    }


    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }
}
