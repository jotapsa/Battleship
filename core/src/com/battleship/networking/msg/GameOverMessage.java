package com.battleship.networking.msg;

public class GameOverMessage extends Message {

    public GameOverMessage(){
        super(MsgType.GAMEOVER);
    }

    @Override
    public String toString() {
        String str = String.format("%s\n",
                type
        );

        return str;
    }
}
