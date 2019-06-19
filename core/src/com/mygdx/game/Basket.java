package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.game.Const.*;
import static com.mygdx.game.Assets.*;
public class Basket {

    SpriteBatch batch;
    float x;
    float y;
    int width;
    int height;
    BasketEdge leftEdge;
    BasketEdge rightEdge;

    public Basket (SpriteBatch batch, float x, float y) {
        this.batch = batch;
        this.x = x;
        this.y = y;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        leftEdge = new BasketEdge(batch, (x - BASKET_DIAMETER*DIM/2),y);
        rightEdge = new BasketEdge(batch, (x + BASKET_DIAMETER*DIM/2),y);
    }

    public void render (float dt) {
//        batch.draw(Assets.instance.basketAssets.ballTexture,x, height -  y,BALL_DIAMETER*DIM,BALL_DIAMETER*DIM);
        leftEdge.render(dt);
        rightEdge.render(dt);
    }

    public Vector2 getBasketCenter() {
        return new Vector2(x,y);
    }

    public Vector2 getBasketCenterInM() {
        return new Vector2(x/DIM,y/DIM);
    }


}
