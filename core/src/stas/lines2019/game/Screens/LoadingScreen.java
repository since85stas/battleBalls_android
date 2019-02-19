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
		sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
	}
	
	@Override
	public void show() {
		loadManager = new AssetManager();

		Assets.instance.initLoadingImages(loadManager);

		// load loading images and wait until finished

		// get images used to display loading progress

		test = loadManager.get("mini_star.png");
//		animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,
//				Gdx.files.internal("main.gif").read());
//		atlas = loadManager.get("loading.pack");
//		title = atlas.findRegion("staying-alight-logo");
//		dash = atlas.findRegion("loading-dash");
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
		elapsed += Gdx.graphics.getDeltaTime();
		startTime = TimeUtils.millis();
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    stateTime += delta; // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
//        TextureRegion currentFrame = flameAnimation.getKeyFrame(stateTime, true);
        
		sb.begin();
//		sb.draw(animation.getKeyFrame(elapsed),
//				Gdx.graphics.getWidth()/2,
//				Gdx.graphics.getHeight()/2);
//		drawLoadingBar(currentLoadingStage * 2, currentFrame);
		sb.draw(test,
				Gdx.graphics.getWidth()/2 - 120/2,
				Gdx.graphics.getHeight()/2,
				120,
				120);
		sb.end();

		if (loadManager.update() && !loadIscomp) {
			parent.prepareMainRes();
			loadIscomp =true;
		}

		if (Assets.instance.assetManager.update() && loadIscomp) {
//			Assets.instance.getMainRes();
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
