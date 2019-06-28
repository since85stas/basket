package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.game.Const.*;
import static com.mygdx.game.Assets.*;

public class Ball {

    SpriteBatch batch;
    ShapeRenderer shape;
    float x;
    float y;
    int width;
    int height;
    float xCenter;
    float yCenter;
    public Circle hitBox;
    Vector2 velocity;
    float initVelocityLen;
    float initAlfa;
    float idealAlfa;
    float timeSum;
    float xDistance;
    Basket basket;
    public boolean ballIsScore = false;
    public boolean ballIsOut;
    public boolean isAboveBasket;
    public boolean isCollision;


    public Ball (SpriteBatch batch, float x, float y, Vector2 velocity, float alfa, Basket basket, ShapeRenderer shape, float idealAlfa) {
        this.batch = batch;
        this.shape = shape;
        this.x = x;
        this.y = y;
        xCenter = x;
        yCenter = y;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        this.velocity = velocity;
        this.initVelocityLen = velocity.len();
        initAlfa = alfa;
        this.idealAlfa = idealAlfa;
        this.basket = basket;
        float xB = basket.getBasketCenter().x - x;

//        shape.begin(ShapeRenderer.ShapeType.Line);
    }

    public void render (float dt) {
//                Gdx.app.log("baall","dt=" + dt +  " time=" + timeSum + " dist=" + xDistance  );
//        Gdx.app.log("baall","fps=" + 1/dt);
        if (true) {
            for (int i = 0; i < 1000; i++) {
                update(dt / 1000);
            }
        }
//        update(dt);
        batch.draw(Assets.instance.basketAssets.ballTexture,
                xCenter -BALL_DIAMETER*DIM/2,
                yCenter -BALL_DIAMETER*DIM/2,
                BALL_DIAMETER*DIM,BALL_DIAMETER*DIM);

//        batch.draw(Assets.instance.basketAssets.ballTexture,
//                xCenter - BALL_DIAMETER*DIM/2,
//                yCenter - BALL_DIAMETER*DIM/2,
//                BALL_DIAMETER*DIM/10,BALL_DIAMETER*DIM/10);

    }

    public void update (float dt) {
        if (xCenter > basket.getBasketCenter().x) {
//            Gdx.app.log("baall","fff");
        }
        if (yCenter > basket.getBasketCenter().y)  isAboveBasket = true;
        hitBox = new Circle(xCenter,yCenter,BALL_DIAMETER/2*DIM);
        checkEdgeIntersFalse(basket.leftEdge);
        checkEdgeIntersFalse(basket.rightEdge);
//        checkEdgeInters(basket.leftEdge);
//        checkEdgeInters(basket.rightEdge);
        checkBallIsScore();
        if (yCenter < 0) {
            ballIsOut = true;
        }

        timeSum += dt;
        float dx = velocity.x*DIM*dt;
        float dy = velocity.y*DIM*dt;
//        float dy = 0;
        xDistance += dx;
//        shape.line(new Vector2(xCenter,yCenter),new Vector2(xCenter+dx,yCenter+dy));
        xCenter   += dx;
        yCenter   += dy;
//        Gdx.app.log("baall","dt=" + dt + " dx= " + dx + " time=" + timeSum + " dist=" + xDistance  );
//        Gdx.app.log("baall","fps=" + 1/dt);
        double dv = 9.8*dt;
        velocity.y -= dv;

    }

    private boolean checkEdgeInters(BasketEdge edge) {
        if (hitBox.overlaps(edge.hitBox)) {
            velocity = isCollisionVect(edge);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEdgeIntersFalse(BasketEdge edge) {
        if (hitBox.overlaps(edge.hitBox)) {
            isCollision = true;
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEdgeIntersSquare(BasketEdge edge) {
        float dx2 = (edge.xCenter-xCenter)*(edge.xCenter-xCenter);
        float dy2 = (edge.yCenter-yCenter)*(edge.yCenter-yCenter);
        float cond2 = BALL_DIAMETER/2 *DIM + BASKET_EDGE_DIAMETER/2*DIM;
        if ( Math.sqrt(dx2 + dy2) < cond2 ) {
            velocity = isCollisionVect(edge);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEdgeIntersSquareFalse(BasketEdge edge) {
        float dx2 = (edge.xCenter-xCenter)*(edge.xCenter-xCenter);
        float dy2 = (edge.yCenter-yCenter)*(edge.yCenter-yCenter);
        float cond2 = BALL_DIAMETER/2 *DIM + BASKET_EDGE_DIAMETER/2*DIM;
        if ( Math.sqrt(dx2 + dy2) < cond2 ) {
            isCollision = true;
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

    private Vector2 isCollisionNew(BasketEdge edge) {
        Vector2 newVel;
        float dyDx = (edge.yCenter - yCenter)/(edge.xCenter - xCenter);
        float gamma = (float) Math.atan(dyDx);
        if (gamma < 0) {
            gamma = 3.14159f - gamma;
        }
//        float alfa  = (float) Math.atan(velocity.x/velocity.y);
        float alfa  = (float) Math.atan(velocity.x/velocity.y);
//        float alfa  = (float) Math.atan(velocity.x/velocity.y);
//        float beta  = gamma - alfa;
        float beta   =  -3.14159f/2 + alfa + gamma;
        float velMod = velAfterCollision((float) Math.sqrt(velocity.x*velocity.x + velocity.y*velocity.y));
        float vT     = -1*(float) Math.cos(beta)*velMod;
        float vN     = (float) Math.sin(beta)*velMod;

        newVel = new Vector2(vT,vN);
        Gdx.app.log("collision","end");
        return newVel;
    }

    private Vector2 isCollisionVect(BasketEdge edge) {
        float dx = (edge.xCenter - xCenter)/DIM;
        float dy = (edge.yCenter - yCenter)/DIM;
        float modC = (float) Math.sqrt(dx*dx + dy*dy);
        Vector2 vT = new Vector2(dx*(velocity.x*dx + velocity.y*dy)/modC/modC,dy*(velocity.x*dx + velocity.y*dy)/modC/modC);
//        Vector2 vT = new Vector2 (velocity.x*dx/modC,velocity.y*dy/modC);
//        Vector2 vN = new Vector2(velocity.x - vT.x,velocity.y -vT.y);
        Vector2 vN = new Vector2(velocity.x - vT.x,velocity.y - vT.y);
        Vector2 vTperp = new Vector2(vT.x*-1,vT.y*-1);
        Vector2 velocNew = new Vector2(vN.x+vTperp.x,vN.y+vTperp.y);
        Vector2 velocNew2 = new Vector2(velocNew.x*0.7f,velocNew.y*0.7f);
        return velocNew;
    }


    private float velAfterCollision(float vel) {

        return vel*0.4f;
    }


    private void checkBallIsScore () {
        if ( !isCollision && !ballIsScore && (isAboveBasket) && (yCenter < (basket.y - BASKET_EDGE_DIAMETER*3*DIM)) && (xCenter < basket.rightEdge.xCenter) && (xCenter > basket.leftEdge.xCenter)) {
            ballIsScore = true;
        }
    }

    private void checkBallIsScoreNew() {
        if ( !isCollision && !ballIsScore && isAboveBasket ){

        }
    }
}
