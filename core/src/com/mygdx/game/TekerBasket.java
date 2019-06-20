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

	Player player;
	Basket basket;
	int width;
	int height;
	
	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		AssetManager am = new AssetManager();
		Assets.instance.init(am);
		batch = new SpriteBatch();
		player = new Player(batch,((BASKET_LENGHT - FREE_THROW_DISTANCE)*DIM),(0*DIM), this);
		basket = new Basket(batch, (BASKET_LENGHT*DIM), (BASKET_HEIGHT*DIM));

		int numBalls = 1;
		float alfa = 59;
		float dAlfa = 10;
		for (int i = 0; i < numBalls; i++) {
			TrajectoryCalc trajectory = new TrajectoryCalc(player,basket,alfa);
			float err = 1f;
			Vector2 vel = new Vector2(trajectory.getVelocity().x*err,trajectory.getVelocity().y*err);
			player.throwBall(vel,alfa);
			alfa += dAlfa/numBalls;
		}

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
	}

	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}


}
