package com.battleship.networking.msg;

import com.battleship.model.Coord;

public class MoveMessage extends Message{
    public Coord target;

    public MoveMessage(Coord target){
        super(MsgType.MOVE);
        this.target = target;
    }

    @Override
    public String toString() {
        String str = String.format("%s %s",
                type,
                target
        );

        return str;
    }
}
