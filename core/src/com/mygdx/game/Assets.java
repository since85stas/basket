package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by seeyo on 03.12.2018.
 */

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    private Assets() {
    }

    public BasketAssets basketAssets;
    public static float DIM;

    public void init(AssetManager assetManager) {

        DIM =   Gdx.graphics.getWidth()/Const.SCREEN_LENGHT;

        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load("sphere_blue.png", Texture.class);
        assetManager.load("sphere_yellow.png",Texture.class);
//        assetManager.load("explosion-large.png",Texture.class);
//        assetManager.load("crosshairs_strip6.png",Texture.class);
        assetManager.finishLoading();
        Texture ballTexture = assetManager.get("sphere_yellow.png");
        Texture basketEdgeTexture = assetManager.get("sphere_blue.png");
//        Texture brokenTexture = assetManager.get("explosion-large.png");
//        Texture crossTexture = assetManager.get("crosshairs_strip6.png");
        basketAssets = new BasketAssets(ballTexture,basketEdgeTexture);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class BasketAssets {

        public final Texture ballTexture;
        public final Texture basketEdgeTexture;

        public BasketAssets(Texture ballTexture, Texture basketEdgeTexture) {
            this.ballTexture = ballTexture;
            this.basketEdgeTexture = basketEdgeTexture;

            Gdx.app.log(TAG,"animation load");
        }
    }


}
