package com.battleship.model;


import java.util.Random;

/**
 * A model representing a computer AI.
 */

public class Computer extends Player{
    public Random generator;

    public Computer(){
        super();
        this.generator = new Random(System.currentTimeMillis());

    }

    public Move createMove(Board enemyBoard){
        Move move = null;

//        do{
//
//        }while()
        return null;
    }

}
