package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import static com.mygdx.game.Const.*;
import static com.mygdx.game.Assets.*;

public class Player {

    SpriteBatch batch;
    float x;
    float y;
    int width;
    int height;

    public Player(SpriteBatch batch, float x, float y) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        this.batch = batch;
        this.x = x;
        this.y = y;
    }

    public Vector2 getBallCenter() {
        Vector2 pos = new Vector2(x , y + PLAYER_HEIGHT*DIM  );
        return pos;
    }

    public Vector2 getBallCenterInM() {
        Vector2 pos = new Vector2((x )/DIM, (y + PLAYER_HEIGHT*DIM)/DIM );
        return pos;
    }

}
