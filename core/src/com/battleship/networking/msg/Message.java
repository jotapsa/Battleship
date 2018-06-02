package com.battleship.networking.msg;

public abstract class Message {
    protected MsgType type;

    public Message(MsgType type){
        this.type = type;
    }

    public abstract String toString();

    public byte[] getBytes(){
        return toString().getBytes();
    }
}
