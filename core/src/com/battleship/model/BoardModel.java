package com.battleship.model;

public class BoardModel {

    private final int size;
    int[][] map;

    BoardModel(int size){
        this.size = size;
        this.map = new int[size][size];
    }
}
