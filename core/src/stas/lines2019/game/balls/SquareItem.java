package stas.lines2019.game.balls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import stas.lines2019.game.Screens.GameScreen;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

public class SquareItem {
    private GameScreen  gameScreen;
    private Texture textureSquare;
//    private Texture textureBall;

//    public Texture getTextureBall() {
//        return textureBall;
//    }

    public  Rectangle hitBox;

    private Vector2 position;
    private Vector2 ballPosition;

    // item dimensions
    public int width ;
    public int height;

    // ball parameters
    private boolean hasBall  = false;
    private boolean isActive = false;
    private float ballactiveTime ;
    private float ballVelocity =
            Constants.BALL_VELOCITY* Gdx.graphics.getWidth()/10;
    private float ballDeformationVelocity    =
            Constants.DEFORMATION_VELOCITY* Gdx.graphics.getWidth()/10;
    private int   ballColor   = -3   ;
    private boolean nextTurnBall = false;
    private boolean isSoundStart = false ;

    // ball types
    public boolean ballIsTough  = false;
    public boolean ballIsFreeze = false;
    public boolean ballIsColorless = false;
    public boolean ballIsBomb      = false;

    // collision parameters
    private float ballDeformation   ;
    private boolean afterCollision = false;
    private boolean stopCollision = false;

    public SquareItem (GameScreen gameScreen, int width, int height, Vector2 position ){
        this.gameScreen = gameScreen;
        this.width = width   ;
        this.height = height ;
//        textureSquare = Assets.instance.tileAssets.texture;
        this.position = position ;
        this.hitBox = new Rectangle(position.x,position.y,width,height);
        float ballPositionDel = (width - width*Constants.BALL_SIZE_RATIO)/2;
        ballPosition = new Vector2(position.x + ballPositionDel,position.y + ballPositionDel);
    }

    public void ballIsTight( boolean bol) {
        ballIsTough = bol;
    }

    public void setBallIsFreeze(boolean ballIsFreeze) {
        this.ballIsFreeze = ballIsFreeze;
    }

    public void setBallIsColorless(boolean ballIsColorless) {
        this.ballIsColorless = ballIsColorless;
    }

    public void setBallIsBomb(boolean ballIsBomb) {
        this.ballIsBomb = ballIsBomb;
    }

    public void   render (SpriteBatch batch) {
        //batch.draw(textureSquare,position.x,position.y);
//        batch.draw(textureSquare, position.x, position.y, width, height);

        if (hasBall && ballColor != -3) {
            batch.draw(getBallColorText()
                    ,ballPosition.x
                    ,ballPosition.y
                    ,width*Constants.BALL_SIZE_RATIO
                    ,height*Constants.BALL_SIZE_RATIO + ballDeformation);

            if (ballIsTough) {
                drawToughNumber(batch);
            }  else if(ballIsFreeze) {
                drawFreezeText(batch);
            } else if (ballIsColorless) {
                drawColorless(batch);
            } else if (ballIsBomb) {
                drawBomb(batch);
            }
        }

//        batch.draw(textureSquare, position.x, position.y, width, height);

        if (nextTurnBall && ballColor != -3) {
            batch.draw(getBallColorText()
                    ,ballPosition.x
                    ,ballPosition.y
                    ,width*Constants.BALL_PREVIEW_RATIO
                    ,height*Constants.BALL_PREVIEW_RATIO );
        }
    }

    public void drawToughNumber(SpriteBatch batch) {

    }

    public void drawFreezeText(SpriteBatch batch) {

    }

    public void drawColorless(SpriteBatch batch) {

    }

    public void drawBomb(SpriteBatch batch) {

    }

    public void update(float dt) {
        if (isActive && hasBall)  {
            changeBallDrawing(dt);
        }
        //if (Gdx.input.isCursorCatched())
    }

    public void changeBallDrawing(float dt ) {
        ballactiveTime += dt;

        if ( ballPosition.y - position.y <= 0 && !stopCollision ) {
            if(!isSoundStart) {
                Assets.instance.soundsBase.tookSound.play(0.2f);

                isSoundStart = true;
            }
            if (  Math.abs(ballDeformation) >= height*Constants.DEFORMATION_RATIO) {
                ballVelocity = ballVelocity*(-1);
                ballDeformationVelocity = ballDeformationVelocity*(-1);
                afterCollision = true;
            } else if (ballDeformation > 0 && afterCollision) {
                stopCollision = true;
                isSoundStart =false;
            }
            ballPosition.y = position.y;
            ballDeformation -= ballDeformationVelocity*dt;
        } else if  ( ballPosition.y + height*Constants.BALL_SIZE_RATIO - position.y
                > height - height*Constants.UPPER_OFFSET) {
            ballVelocity = ballVelocity*(-1);
            ballDeformationVelocity = ballDeformationVelocity*(-1);
            ballPosition.y = ballPosition.y -(ballVelocity)*dt;
            stopCollision = false;
            afterCollision = false;
        } else {
            ballPosition.y = ballPosition.y -(ballVelocity)*dt;
        }
    }

    public void setBallInCenter () {
        float ballPositionDel = (width - width*Constants.BALL_SIZE_RATIO)/2;
        ballPosition.x  =  position.x + ballPositionDel;
        ballPosition.y  =  position.y + ballPositionDel;
        ballDeformation = 0;
    }

//    public Texture drawBall (int ballColor) {
//        textureBall = new Texture(getBallColor(ballColor));
//        return textureBall;
//    }

    public Texture getBallColorText( ) {
        String textureName = null;
        Texture texture = null;
        switch (ballColor) {
            case 0:
                //textureName = "sphere_blue.png";
                texture = Assets.instance.blueBallAssets.texture;
                break;
            case 1:
//                textureName = "sphere_green.png";
                texture = Assets.instance.greenBallAssets.texture;
                break;
            case 2:
//                textureName = "sphere_purle.png";
                texture = Assets.instance.purpleBallAssets.texture;
                break;
            case 3:
//                textureName = "sphere_yellow.png";
                texture = Assets.instance.yellowBallAssets.texture;
                break;
            case 4:
//                textureName = "sphere_yellow.png";
                texture = Assets.instance.pinkBallAssets.texture;
                break;
            case 5:
//                textureName = "sphere_yellow.png";
                texture = Assets.instance.redBallAssets.texture;
                break;
            case 6:
//                textureName = "sphere_yellow.png";
                texture = Assets.instance.lBlueBallAssets.texture;
                break;
            case 66:
                texture = Assets.instance.colorlessBallAssets.texture;
                break;
        }
        return texture;
    }

    public Vector2 getCenterPosition () {
        return new Vector2(position.x+width/2, position.y+height/2);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setHasBall(boolean hasBall) {
        this.hasBall = hasBall;
    }

    public boolean isHasBall() {
        return hasBall;
    }

    public int getBallColor() {
        return ballColor;
    }

    public void setBallColor(int ballColor) {
        this.ballColor = ballColor;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNextTurnBall() { return nextTurnBall;  }

    public void setNextTurnBall(boolean nextTurnBall) {   this.nextTurnBall = nextTurnBall; }
}
