package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
    List<Ball> poolInBasket;
    List<ExperResultAngle> angleResult;

    SpriteBatch batch;
    ShapeRenderer shape;
    float x;
    float y;
    int width;
    int height;
    boolean seriaIsStart;
    float idealAngle;
    float dAngle;


    public Player(SpriteBatch batch, float x, float y, TekerBasket parent, ShapeRenderer shape) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        this.batch = batch;
        this.parent = parent;
        this.shape  = shape;
        this.x = x;
        this.y = y;
        pool = new ArrayList<Ball>();
        angleResult = new ArrayList<ExperResultAngle>();
    }

    public Vector2 getBallCenter() {
        Vector2 pos = new Vector2(x , y + PLAYER_HEIGHT*DIM  );
        return pos;
    }

    public Vector2 getBallCenterInM() {
        Vector2 pos = new Vector2((x )/DIM, (y + PLAYER_HEIGHT*DIM)/DIM );
        return pos;
    }

    public void throwBall(Vector2 velocity, float alfa, float idealAlfa, float dAngle) {
        idealAngle = idealAlfa;
        ball = new Ball(batch,getBallCenter().x,getBallCenter().y,velocity ,alfa, parent.basket, shape, idealAlfa);
        pool.add(ball);
        seriaIsStart = true;
        this.dAngle = dAngle;
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
        poolInBasket = new ArrayList<Ball>();
        int i = 0;
        for (int j = 0; j < pool.size(); j++) {
            if ( pool.get(j).ballIsScore)  {
                poolInBasket.add(pool.get(j));
                i++;
            }
        }

        float dfiMin;
        float dfiMax;
        if (poolInBasket.size() >0) {
            dfiMin = poolInBasket.get(0).initAlfa;
            dfiMax = poolInBasket.get(poolInBasket.size() - 1).initAlfa;
        } else {
            dfiMin =0;
            dfiMax = 0;
        }

        float dfi =0;
        for (int j = 0; j < pool.size(); j++) {
            if(pool.get(j).ballIsScore ) {
                dfi += dAngle;
            }
        }

        angleResult.add(new ExperResultAngle(idealAngle,dfiMin,dfiMax, dfi));





        float accuracy = i/pool.size();
        pool = new ArrayList<Ball>();
        poolInBasket = new ArrayList<Ball>();
        seriaIsStart = false;
        parent.initAngleStep();
    }

    public boolean checkEndSerie() {
        if(pool.size() == 0) {
            return false;
        } else {
            boolean end = true;
            for (int i = 0; i < pool.size(); i++) {
                end &= pool.get(i).ballIsOut;
            }
            return end;
        }
    }

}
