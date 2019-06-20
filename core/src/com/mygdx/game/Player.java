package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Const.*;
import static com.mygdx.game.Assets.*;

public class Player {

    Ball ball;
    TekerBasket parent;
    List<Ball> pool;

    SpriteBatch batch;
    float x;
    float y;
    int width;
    int height;

    public Player(SpriteBatch batch, float x, float y, TekerBasket parent) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        this.batch = batch;
        this.parent = parent;
        this.x = x;
        this.y = y;
        pool = new ArrayList<Ball>();
    }

    public Vector2 getBallCenter() {
        Vector2 pos = new Vector2(x , y + PLAYER_HEIGHT*DIM  );
        return pos;
    }

    public Vector2 getBallCenterInM() {
        Vector2 pos = new Vector2((x )/DIM, (y + PLAYER_HEIGHT*DIM)/DIM );
        return pos;
    }

    public void throwBall(Vector2 velocity, float alfa) {
        ball = new Ball(batch,getBallCenter().x,getBallCenter().y,velocity ,alfa, parent.basket);
        pool.add(ball);
    }

    public void render (float dt) {
//        ball.render(dt);
        for (int i = 0; i < pool.size(); i++) {
            pool.get(i).render(dt);
        }
        if(pool.get(0).yCenter < 0) {
             Gdx.app.log("ball","ball is on floor");
        }
    }

}
