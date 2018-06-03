package com.battleship.model;

import com.battleship.model.aux.Turn;

public class Human extends Player{
    private boolean online;

    public Human(Turn turnToPlay){
        super(turnToPlay);
        this.online = false;
    }

    public boolean isOnlinePlayer(){
        return this.online;
    }

    public void setOnlinePlayer(boolean online){
        this.online = online;
    }

    public void requestMove(){

    }
}
