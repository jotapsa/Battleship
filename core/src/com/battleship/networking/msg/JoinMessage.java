package com.battleship.networking.msg;

public class JoinMessage extends Message{
    private String ip;

    public JoinMessage(String ip) {
        super(MsgType.JOIN);
        this.ip = ip;
    }

    @Override
    public String toString() {
        String str = String.format("%s %s\n",
                type, ip
        );

        return str;
    }
}
