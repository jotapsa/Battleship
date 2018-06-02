package com.battleship.networking.msg;

public class AcceptMessage extends Message{
    public AcceptMessage(){
        super(MsgType.ACCEPT);
    }

    @Override
    public String toString() {
        String str = String.format("%s",
                type
        );

        return str;
    }
}
