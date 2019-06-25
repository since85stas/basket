package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    ShapeRenderer shape;
    float x;
    float y;
    int width;
    int height;
    boolean seriaIsStart;


    public Player(SpriteBatch batch, float x, float y, TekerBasket parent, ShapeRenderer shape) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        this.batch = batch;
        this.parent = parent;
        this.shape  = shape;
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
        ball = new Ball(batch,getBallCenter().x,getBallCenter().y,velocity ,alfa, parent.basket, shape);
        pool.add(ball);
        seriaIsStart = true;
    }

    public void render (float dt) {
//        ball.render(dt);
        for (int i = 0; i < pool.size(); i++) {
            pool.get(i).render(dt);
        }
        if(checkEndSerie() && seriaIsStart) {
             Gdx.app.log("ball","ball is on floor");
             endSereie();
        }
    }

    public void endSereie() {
        int i = 0;
        for (int j = 0; j < pool.size(); j++) {
            if ( pool.get(j).ballIsScore) i++;
        }

        float accuracy = i/pool.size();
        pool = new ArrayList<Ball>();
        seriaIsStart = false;
    }

    public boolean checkEndSerie() {
        boolean end= true;
        for (int i = 0; i < pool.size(); i++) {
            end &= pool.get(i).ballIsOut;
        }
        return end;
    }

}
