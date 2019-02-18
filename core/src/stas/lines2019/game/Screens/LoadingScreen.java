package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.util.Assets;

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
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    stateTime += delta; // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
//        TextureRegion currentFrame = flameAnimation.getKeyFrame(stateTime, true);
        
		sb.begin();
//		drawLoadingBar(currentLoadingStage * 2, currentFrame);
		sb.draw(test, 135, 250, 100,100);
		sb.end();

		if (loadManager.update() && !loadIscomp) {
			parent.prepareMainRes();
			loadIscomp =true;
		}

		if (Assets.instance.assetManager.update() && loadIscomp) {
//			Assets.instance.getMainRes();
			parent.setMainMenuScreen();
		}

//		if (parent.assMan.manager.update()) { // Load some, will return true if done loading
//			currentLoadingStage+= 1;
//            switch(currentLoadingStage){
//            case FONT:
//            	System.out.println("Loading fonts....");
//            	parent.assMan.queueAddFonts();
//            	break;
//            case PARTY:
//            	System.out.println("Loading Particle Effects....");
//            	parent.assMan.queueAddParticleEffects();
//            	break;
//            case SOUND:
//            	System.out.println("Loading Sounds....");
//            	parent.assMan.queueAddSounds();
//            	break;
//            case MUSIC:
//            	System.out.println("Loading fonts....");
//            	parent.assMan.queueAddMusic();
//            	break;
//            case 5:
//            	System.out.println("Finished");
//            	break;
//            }
//	    	if (currentLoadingStage >5){
//	    		countDown -= delta;
//	    		currentLoadingStage = 5;
//	    		if(countDown < 0){
//	    			parent.changeScreen(Box2DTutorial.MENU);
//	    		}
//            }
//        }
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
