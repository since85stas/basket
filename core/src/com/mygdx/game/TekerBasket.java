package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
	
	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		AssetManager am = new AssetManager();
		Assets.instance.init(am);
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		shape.setColor(Color.BLUE);
		shape.setProjectionMatrix(batch.getProjectionMatrix());
		shape.setAutoShapeType(true);
		player = new Player(batch,((BASKET_LENGHT - FREE_THROW_DISTANCE)*DIM),(0*DIM), this, shape);
		basket = new Basket(batch, (BASKET_LENGHT*DIM), (BASKET_HEIGHT*DIM));

		int numBalls = 10;
		angle  = 55;
		angleChangePercent = 4;
		float dAngleDiapozone = angle* angleChangePercent / 100;
		dAngle      = dAngleDiapozone /numBalls;
		angle       = angle - dAngleDiapozone/2;
		initAngleStep();
//		singleThrow();
	}

	public void initAngleStep() {
		if (angle <= angle + angle*angleChangePercent/2) {
			idealTrajectory = new  TrajectoryCalc(player,basket,angle);
			initVelErrorSerie(angle);
//			initSingleThrow( angle);
			angle += dAngle;
		}
	}

	public void singleThrow() {
		idealTrajectory = new  TrajectoryCalc(player,basket,angle);
//			initVelErrorSerie(angle);
		initSingleThrow( angle);
	}


	public void initVelErrorSerie(float alfa) {
//		TrajectoryCalc trajectory =
		int numVelocPoints = 20;
		float errPercentage = 4;
		float err = 1-errPercentage/2/100;
		float dErr = errPercentage/numVelocPoints/100;
		for (int j = 0; j < numVelocPoints; j++) {

			Vector2 vel = new Vector2(idealTrajectory.getVelocity().x*err,idealTrajectory.getVelocity().y*err);
			player.throwBall(vel,alfa);
			err += dErr;
		}
	}

	public void initSingleThrow(float alfa) {
	    float err = 1f;
        Vector2 vel = new Vector2(idealTrajectory.getVelocity().x*err,idealTrajectory.getVelocity().y*err);
        player.throwBall(vel,alfa);
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
