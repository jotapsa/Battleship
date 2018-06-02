package com.battleship.networking.msg;

import com.battleship.model.aux.CellType;

public class HitMessage extends Message{
    private CellType hit;

    public HitMessage(CellType hit){
        super(MsgType.HIT);
        this.hit = hit;
    }

    @Override
    public String toString() {
        String str = String.format("%s %s",
                type,
                hit
        );

        return str;
    }
}
