package com.gdx.game.entities;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 * @author Armando
 */
public abstract class GameAction extends Action{

    public Entity getTargetEntity() {
        return (Entity) getTarget();
    }

    @Override
    public void setTarget(Actor target) {
        if(target instanceof Entity)
            super.setTarget(target);
        else
            throw new IllegalArgumentException("GameAction can target only Entity");
    }

    public Entity getEntity() {
        return (Entity) getActor();
    }

    @Override
    public void setActor(Actor actor) {
        if(actor instanceof Entity)
            super.setActor(actor);
        else
            throw new IllegalArgumentException("GameAction can be attached only to Entity.");
    }
    
}
