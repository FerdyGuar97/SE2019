package com.gdx.game.entities;

import com.gdx.game.entities.bullets.Bullet;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.gdx.game.listeners.event.events.DeathEvent;

/**
 *
 * @author ammanas
 */
public abstract class MortalEntity extends Entity {

    public String name;
    public Integer healthPoints;
    public Stats stats;

    public MortalEntity(Action defaultAction, EntityDef entityDef, Stats stats) {
        super(defaultAction, entityDef);
        this.healthPoints = stats.getBasicHP();
    }

    /**
     * Correctly manage the damage due to a bullet
     *
     * @param bullet
     */
    public abstract void isHitBy(Bullet bullet);

    /**
     * Dispose all the involved bodies for deletion, that is adding their
     * references to the GdxGame.game.bodiesToRemove structure
     */
    public void kill() {
        dispose();
        fire(new DeathEvent());
    }

    public Integer getLife() {
        return healthPoints;
    }
    
}
