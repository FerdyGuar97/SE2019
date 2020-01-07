package com.gdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 *
 * @author ammanas
 */
public class MapLimits extends Entity {

    public MapLimits(float width, float height, Vector2 initialPosition) {
        super(width, height, initialPosition);
        initPhysics();
    }

    @Override
    protected void initPhysics() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(getPosition());

        this.body = this.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 10f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(body);
        shape.dispose();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void initGraphics() {
    }

    @Override
    public void setBody(Body b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
