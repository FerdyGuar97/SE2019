package com.gdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.Map;

/**
 *
 * @author Armando
 */
public abstract class EntityDef {

    public abstract BodyDef getBodyDef();

    public abstract ObjectMap<String, FixtureDef> getFixtureDefs();

    protected abstract void setCustomScale(float scale);

    protected abstract float getCustomScale();

    public abstract Map<String, Animation<TextureRegion>> getAnimations();

    public abstract void setWidth(float width);

    public abstract void setHeight(float height);

    public abstract float getWidth();

    public abstract float getHeight();

}
