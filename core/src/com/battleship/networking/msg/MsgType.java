package com.battleship.networking.msg;

public enum MsgType {
    JOIN("JOIN"),
    ACCEPT("ACCEPT"),
    MOVE("MOVE"),
    HIT("HIT"),
    GAMEOVER("GAMEOVER");


    private String type;

    MsgType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return this.type;
    }
}
