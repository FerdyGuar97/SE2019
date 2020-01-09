package com.gdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author Armando
 */
public class PlayAnimation extends GameAction {

    private float stateTime = 0;
    private final Animation<TextureRegion> defaultAnimation;

    public PlayAnimation(Animation<TextureRegion> defaultAnimation) {
        this.defaultAnimation = defaultAnimation;
    }

    @Override
    public boolean act(float delta) {
        if (defaultAnimation == null) {
            return true;
        }

        stateTime += delta;
        TextureRegion nextRegion = defaultAnimation.getKeyFrame(stateTime);
        if (nextRegion == null) {
            return false;
        }
        this.getEntity().setRegionToDraw(nextRegion);
        return false;
    }
}
