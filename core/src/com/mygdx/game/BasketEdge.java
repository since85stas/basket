package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

import static com.mygdx.game.Const.*;
import static com.mygdx.game.Assets.*;

public class BasketEdge {

    SpriteBatch batch;
    float x;
    float y;
    int width;
    int height;
    Circle hitBox;
    float xCenter;
    float yCenter;

    public BasketEdge (SpriteBatch batch, float x, float y) {
        this.batch = batch;
        this.x = x;
        this.y = y;
        xCenter = x;
        yCenter = y;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

//        hitBox = new Circle(x - BASKET_EDGE_DIAMETER/2*DIM, y -  BASKET_EDGE_DIAMETER/2*DIM, BASKET_EDGE_DIAMETER/2*DIM  );
        hitBox = new Circle(xCenter -BASKET_EDGE_DIAMETER*DIM/2,yCenter-BASKET_EDGE_DIAMETER*DIM/2 ,BASKET_EDGE_DIAMETER/2*DIM);
    }

    public void render (float dt) {
        batch.draw(Assets.instance.basketAssets.basketEdgeTexture,
                xCenter -BASKET_EDGE_DIAMETER*DIM/2,
                yCenter -BASKET_EDGE_DIAMETER*DIM/2,
                BASKET_EDGE_DIAMETER*DIM,BASKET_EDGE_DIAMETER*DIM);
    }

}
