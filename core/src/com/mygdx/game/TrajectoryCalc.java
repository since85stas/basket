package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import static com.mygdx.game.Const.*;
import static com.mygdx.game.Assets.*;

public class TrajectoryCalc {

    double  time;
    double vel;
    double alfaRad;

    public TrajectoryCalc(Player player, Basket basket, float alfa) {
        alfaRad = alfa * 3.14159 /180;
        float xB = basket.getBasketCenterInM().x - player.getBallCenterInM().x;
        float yB = basket.getBasketCenterInM().y - player.getBallCenterInM().y;
        float xBinPix = xB*DIM;

         time = Math.sqrt( (xB*Math.tan(alfaRad) - yB)/9.8*2 ) ;
         vel = xB/(Math.cos(alfaRad)*time);

         double velNew = xB/Math.cos(alfaRad)*Math.sqrt(9.8/2/(xB*Math.tan(alfaRad) - yB));
         double timeNew = xB/velNew/Math.cos(alfaRad);
         Vector2 vel = new Vector2((float) (velNew*Math.cos(alfaRad)),(float)( velNew*Math.sin(alfaRad)));
    }


    public float getTime() {
        return (float) time;
    }

    public Vector2 getVelocity() {

        return new Vector2((float) (vel*Math.cos(alfaRad)),(float)(vel*Math.sin(alfaRad)));
    }

    public float getVelocityMod () {
        return (float) vel;
    }
}
