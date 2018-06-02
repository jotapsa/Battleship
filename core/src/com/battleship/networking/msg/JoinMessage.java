package com.battleship.networking.msg;

public class JoinMessage extends Message{
    public JoinMessage() {
        super(MsgType.JOIN);
    }

    @Override
    public String toString() {
        String str = String.format("%s\n",
                type
        );

        return str;
    }
}
