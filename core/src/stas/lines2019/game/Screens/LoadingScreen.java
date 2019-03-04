package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.results.AchivementsList;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.GifDecoder;

public class LoadingScreen implements Screen {
	private LinesGame parent;
	private SpriteBatch sb;
	private TextureAtlas atlas;
	private AtlasRegion title;
	private Animation flameAnimation;
	private boolean loadIscomp;
	Texture test;
	
	public final int IMAGE = 0;		// loading images
	public final int FONT = 1;		// loading fonts
	public final int PARTY = 2;		// loading particle effects
	public final int SOUND = 3;		// loading sounds
	public final int MUSIC = 4;		// loading music

	private final float ICON_WIDTH  = 0.4f*Gdx.graphics.getWidth();
	private final float ICON_HEIGHT = ICON_WIDTH*0.7518f;
	
	private int currentLoadingStage = 0;
	
	// timer for exiting loading screen
	public float countDown = 1f;
	private float stateTime;
	private AtlasRegion dash;

	AssetManager loadManager;
	Animation<TextureRegion> animation;

	float elapsed;
	private float loadinfTime = 3;
	private long startTime;
	private long endTime;
	
	public LoadingScreen(LinesGame box2dTutorial){
		parent = box2dTutorial;
		sb = new SpriteBatch();
//		sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
	}
	
	@Override
	public void show() {
		loadManager = new AssetManager();

		Assets.instance.initLoadingImages(loadManager);

		// load loading images and wait until finished

		// get images used to display loading progress

		test = loadManager.get("mini_star.png");
//
//		flameAnimation = new Animation(0.07f, atlas.findRegions("flames/flames"), PlayMode.LOOP);
		
		// initiate queueing of images but don't start loading

		System.out.println("Loading images....");
		
		stateTime = 0f;
	}
	
	private void drawLoadingBar(int stage, TextureRegion currentFrame){
		for(int i = 0; i < stage;i++){
			sb.draw(currentFrame, 50 + (i * 50), 150, 50, 50);
			sb.draw(dash, 35 + (i * 50), 140, 80, 80); 
		}
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.902f,0.871f,0.863f,1);
//		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    stateTime += delta; // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
		//        TextureRegion currentFrame = flameAnimation.getKeyFrame(stateTime, true);
        


		if (loadManager.update() && !loadIscomp ) {
			parent.prepareMainRes();
			loadIscomp =true;
			startTime = TimeUtils.millis();
		}

		sb.begin();

		if (loadIscomp) {
//			endTime = TimeUtils.timeSinceMillis(startTime)/1000;
			elapsed = TimeUtils.timeSinceMillis(startTime)/1000;
			TextureRegion frame = Assets.instance.loadAssets.loadAnimation.getKeyFrame(stateTime,true);
			float iconYPos =Gdx.graphics.getHeight()/2 + 0.1f*Gdx.graphics.getHeight();
			sb.draw(frame,
					(Gdx.graphics.getWidth() - ICON_WIDTH)/2,
					iconYPos,
					ICON_WIDTH,
					ICON_HEIGHT
			);
		}

		sb.end();

		if (Assets.instance.assetManager.update() && loadIscomp && elapsed > 4) {
			Assets.instance.getMainRes();

			parent.setMainMenuScreen();
		}



	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		sb.dispose();
		// TODO Auto-generated method stub
		
	}

}
