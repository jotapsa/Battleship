package com.battleship.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.battleship.model.entities.EntityModel;

public class ShipController extends EntityBody {
    /**
     * Constructs a body representing a model in a certain world.
     *
     * @param world The world this body lives on.
     * @param model The model representing the body.
     */
    ShipController(World world, EntityModel model) {
        super(world, model);
    }
}