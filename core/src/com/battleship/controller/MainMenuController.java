package com.battleship.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class MainMenuController implements ContactListener{
    /**
     * The singleton instance of this controller
     */
    private static MainMenuController instance;

    /**
     * The arena width in meters.
     */
    public static final int ARENA_WIDTH = 100;

    /**
     * The arena height in meters.
     */
    public static final int ARENA_HEIGHT = 50;

    /**
     * The physics world controlled by this controller.
     */
    private final World world;

    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     */
    private MainMenuController() {
        world = new World(new Vector2(0, 0), true);

        world.setContactListener(this);
    }

    /**
     * Returns a singleton instance of a main menu controller
     *
     * @return the singleton instance
     */
    public static MainMenuController getInstance() {
        if (instance == null){
            instance = new MainMenuController();
        }
        return instance;
    }



    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
