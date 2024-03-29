package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.game.Const.*;
import static com.mygdx.game.Assets.*;

public class TekerBasket extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shape;
//	Texture img;
//	Texture ballTexture;
//	Texture

	Player player;
	Basket basket;
	int width;
	int height;

	TrajectoryCalc idealTrajectory;
	float angle;
	float angleChangePercent;
	float dAngle;

	boolean isAngle;

	@Override
	public void create () {

	    isAngle = false;

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		AssetManager am = new AssetManager();
		Assets.instance.init(am);
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		shape.setColor(Color.BLUE);
		shape.setProjectionMatrix(batch.getProjectionMatrix());
		shape.setAutoShapeType(true);
		player = new Player(batch,((BASKET_LENGHT - FREE_THROW_DISTANCE)*DIM),(0*DIM), this, shape, isAngle);
		basket = new Basket(batch, (BASKET_LENGHT*DIM), (BASKET_HEIGHT*DIM));

		int numBalls = 1;
		angle  = 45;
		dAngle = 1;
		angleChangePercent = 0;
//		float dAngleDiapozone = angle* angleChangePercent / 100;
//		dAngle      = dAngleDiapozone /numBalls;
//		angle       = angle - dAngleDiapozone/2;
		initAngleStep();
//		singleThrow();
	}

	public void initAngleStep() {

		if (angle < 80) {
			idealTrajectory = new TrajectoryCalc(player, basket, angle);
//			initVelErrorSerie(angle);
//			initSingleThrow( angle);
//			initAngleErrorSerie(angle);
			if (isAngle) {
				initAngleErrorSerie(angle);
			} else {
				initVelErrorSerie(angle);
			}
			angle += dAngle;
//			initSingleThrowAngle(44.54997f * 3.14f/180);
//			angle += dAngle;
		} else {
//			player.angleResult;

			FileHandle file = Gdx.files.local("results.dat");
			file.delete();
			for (int j = 0; j < player.angleResult.size(); j++) {
				String fiMin = String.format("%.4f",player.angleResult.get(j).fiMin);
				String fiMmax = String.format("%.4f",player.angleResult.get(j).fiMax);
				String df = String.format("%.4f",player.angleResult.get(j).dFi);
				String string = "" + player.angleResult.get(j).angle + " " + fiMin + " " +
						fiMmax + " " + df + "\n";
				file.writeString(string,true);
			}
			Gdx.app.log("end","end");
		}

	}

	public void singleThrow() {
		idealTrajectory = new  TrajectoryCalc(player,basket,angle);
//			initVelErrorSerie(angle);
//		initSingleThrow( angle);
//		initAngleErrorSerie();
	}


	public void initVelErrorSerie(float alfa) {
//		TrajectoryCalc trajectory =
		int numVelocPoints = 100;
		float errPercentage = 7;
		float err = 1-errPercentage/2/100;
		float dErr = errPercentage/numVelocPoints/100;
		for (int j = 0; j < numVelocPoints; j++) {

			Vector2 vel = new Vector2(idealTrajectory.getVelocity().x*err,idealTrajectory.getVelocity().y*err);
			player.throwBall(vel,alfa,angle,dErr,idealTrajectory.getVelocity().len());
			err += dErr;
		}
	}

	public void initAngleErrorSerie( float alfa) {
//		TrajectoryCalc trajectory =
		float angleDiapozone = 5;
		int numAnglePoints = 50 ;
		float dAngle = angleDiapozone /numAnglePoints;
		float angleInit = alfa - angleDiapozone/2;
		for (int j = 0; j < numAnglePoints; j++) {
			Vector2 vel = new Vector2( idealTrajectory.getVelocityMod()* (float) Math.cos(angleInit*3.14159/180),
					idealTrajectory.getVelocityMod()*(float)Math.sin(angleInit*3.14159/180));
			player.throwBall(vel,angleInit, angle, dAngle , idealTrajectory.getVelocity().len());
			angleInit += dAngle;
		}
	}

	public void initSingleThrowAngle(float alfa) {
		float err = 1f;
		Vector2 vel = new Vector2(idealTrajectory.getVelocity().x,idealTrajectory.getVelocity().y);
		player.throwBall(vel,alfa,angle,0,idealTrajectory.getVelocity().len());
	}

	public void initSingleThrow(float alfa) {
	    float err = 1f;
        Vector2 vel = new Vector2(idealTrajectory.getVelocity().x*err,idealTrajectory.getVelocity().y*err);
        player.throwBall(vel,alfa,angle,0,idealTrajectory.getVelocity().len());
    }

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
//		batch.draw(img, 0, 0);
		player.render(dt);
		basket.render(dt);
		batch.end();

//		shape.begin(ShapeRenderer.ShapeType.Line);
//		shape.line(new Vector2(0,0),new Vector2(100,100));
//		shape.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}


}
