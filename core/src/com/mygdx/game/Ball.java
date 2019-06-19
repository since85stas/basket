package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.game.Const.*;
import static com.mygdx.game.Assets.*;

public class Ball {

    SpriteBatch batch;
    float x;
    float y;
    int width;
    int height;
    float xCenter;
    float yCenter;
    public Circle hitBox;
    Vector2 velocity;
    float timeSum;
    float xDistance;
    Basket basket;


    public Ball (SpriteBatch batch, float x, float y, Vector2 velocity, Basket basket) {
        this.batch = batch;
        this.x = x;
        this.y = y;
        xCenter = x;
        yCenter = y;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        this.velocity = velocity;
        this.basket = basket;
        float xB = basket.getBasketCenter().x - x;
    }

    public void render (float dt) {
        update(dt);
        batch.draw(Assets.instance.basketAssets.ballTexture,
                xCenter - BALL_DIAMETER*DIM/2,
                yCenter - BALL_DIAMETER*DIM/2,
                BALL_DIAMETER*DIM,BALL_DIAMETER*DIM);

    }

    public void update (float dt) {
        timeSum += dt;
        float dx = velocity.x*DIM*dt;
        float dy = velocity.y*DIM*dt;
//        float dy = 0;
        xDistance += dx;
        xCenter   += dx;
        yCenter   += dy;
        Gdx.app.log("baall","dt=" + dt + " dx= " + dx + " time=" + timeSum + " dist=" + xDistance  );
        Gdx.app.log("baall","fps=" + 1/dt);
        double dv = 9.8*dt;
        velocity.y -= dv;
        if (xCenter > basket.getBasketCenter().x) {
            Gdx.app.log("baall","fff");
        }
        hitBox = new Circle(xCenter,yCenter,BALL_DIAMETER/2*DIM);
        checkEdgeInters(basket.leftEdge);
        checkEdgeInters(basket.rightEdge);
    }

    private boolean checkEdgeInters(BasketEdge edge) {
        if (hitBox.overlaps(edge.hitBox)) {
            velocity = isCollision(edge);
            return true;
        } else {
            return false;
        }
    }

    private Vector2 isCollision(BasketEdge edge) {
        Vector2 newVel;
        float dyDx = (edge.xCenter - xCenter)/(edge.yCenter - yCenter);
        float beta = (float) Math.atan(dyDx);
        float velMod = (float) Math.sqrt(velocity.x*velocity.x + velocity.y*velocity.y);
        float vT     = -1*(float) Math.cos(beta)*velMod;
        float vN     = (float) Math.sin(beta)*velMod;
        newVel = new Vector2(vT,vN);
        return newVel;
    }

}
