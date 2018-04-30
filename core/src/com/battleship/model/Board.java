package com.battleship.model;

public class Board {

    private final int size;
    int[][] map;

    Board(int size){
        this.size = size;
        this.map = new int[size][size];
    }
}
