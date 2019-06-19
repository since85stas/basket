package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.game.Const.*;
import static com.mygdx.game.Assets.*;

public class TekerBasket extends ApplicationAdapter {
	SpriteBatch batch;
//	Texture img;
//	Texture ballTexture;
//	Texture
	Ball ball;
	Player player;
	Basket basket;
	int width;
	int height;
	
	@Override
	public void create () {

//		Gdx.graphics.setContinuousRendering(true);

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		AssetManager am = new AssetManager();
		Assets.instance.init(am);
		batch = new SpriteBatch();
		player = new Player(batch,((BASKET_LENGHT - FREE_THROW_DISTANCE)*DIM),(0*DIM));

		basket = new Basket(batch, (BASKET_LENGHT*DIM), (BASKET_HEIGHT*DIM));

		TrajectoryCalc trajectory = new TrajectoryCalc(player,basket,60);

		float err = 0.989f;
		Vector2 vel = new Vector2(trajectory.getVelocity().x*err,trajectory.getVelocity().y*err);

		ball = new Ball(batch,player.getBallCenter().x,player.getBallCenter().y,vel ,basket);
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
//		batch.draw(img, 0, 0);
		ball.render(dt);
		basket.render(dt);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}


}
