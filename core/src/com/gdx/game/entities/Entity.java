package com.gdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import com.gdx.game.GameStage;
import com.gdx.game.GdxGame;

public class Entity extends Actor implements Disposable {

    private Body body;
    private final EntityDef entityDef;
    private TextureRegion textureRegion;
    protected final Action defaultAction;

    public Entity(Action defaultAction, EntityDef entityDef) {
        if(entityDef == null) throw new IllegalArgumentException("entityDef must be non-null.");
        this.entityDef = entityDef;
        
        if(defaultAction == null)
            this.defaultAction = new PlayAnimation(entityDef.getAnimations().get("default"));
        else
            this.defaultAction = defaultAction;
    }

    @Override
    protected void rotationChanged() {
        super.rotationChanged(); //ToDo
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged(); //ToDo
    }

    @Override
    protected void positionChanged() {
        super.positionChanged(); //ToDo
    }

    @Override
    protected void setStage(Stage stage) {
        if (stage instanceof GameStage) {
            super.setStage(stage);
        } else {
            throw new IllegalArgumentException("An Entity can only be assigned to a GameStage");
        }
    }

    @Override
    protected void setParent(Group parent) {
        if (parent == null) {
            GdxGame.game.bodyToRemove.add(body);
            body = null;
        } else {
            clear();
            addAction(new PhysicsLoading());
            addAction(defaultAction);
            super.setParent(parent);
        }
    }

    public GameStage getGameStage() {
        return (GameStage) getStage();
    }

    public Body getBody() {
        return body;
    }

    protected void setBody(Body b) {
        body = b;
    }

    public EntityDef getEntityDef() {
        return entityDef;
    }

    public TextureRegion getRegionToDraw() {
        return textureRegion;
    }

    public void setRegionToDraw(TextureRegion txtRegion) {
        this.textureRegion = txtRegion;
    }
    
    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public void setPosition(Vector2 pos) {
        setPosition(pos.x, pos.y);
    }

    @Override
    public void dispose() {
        this.clear();
        defaultAction.reset();
        Action removeActor = Actions.removeActor(this);
        if (getStage() != null) {
            getStage().addAction(removeActor);
        }
    }

    @Override
    public final void act(float delta) {
        if (body != null) {
            super.setPosition(body.getPosition().x, body.getPosition().y);
        }
        super.act(delta);
    }

    @Override
    public final void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    /**
     * An entity-specific action which duty is to load all the physics defined
     * in EntityDef in the World object specified by GameStage.
     */
    protected class PhysicsLoading extends GameAction {

        @Override
        public boolean act(float delta) {
            if (entityDef == null) {
                return false;
            }
            body = getEntity().getGameStage().getWorld().createBody(entityDef.getBodyDef());
            body.setUserData(Entity.this);

            for (FixtureDef fixDef : getEntity().getEntityDef().getFixtureDefs().values()) {
                Fixture fix = body.createFixture(fixDef);
                fix.setUserData(Entity.this);
            }
            return true;
        }
    }

}
