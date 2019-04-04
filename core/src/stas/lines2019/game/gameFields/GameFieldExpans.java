package stas.lines2019.game.gameFields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import stas.lines2019.game.Screens.GameScreenExpans;
import stas.lines2019.game.balls.BallsInfo;
import stas.lines2019.game.balls.SquareItem;
import stas.lines2019.game.balls.SquareItemExpans;
import stas.lines2019.game.util.Constants;
//import stas.lines2019.game.util.Constants;

import static stas.lines2019.game.util.Constants.*;

/**
 * Created by seeyo on 28.02.2019.
 */

public class GameFieldExpans extends GameField {

    private final int  MAX_TOUGH = 3;

    private float[] ballsWeights;

    public int energy;

//    public GameScreenExpans mGameScreenExpans;

//    private SquareItemExpans[][] squares;

    public GameFieldExpans (GameScreenExpans gameScreenExpans) {
        super(gameScreenExpans);
        getInitBallWeights();
//        energy = 50;=ZE_WEIGHT;
    }

    @Override
    public void createSquares() {
        squares = new SquareItemExpans[fieldDimension][fieldDimension];
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                int x = (int) initPos.x + j * itemWidth;
                int y = (int) initPos.y + i * itemWidth;
                Vector2 position = new Vector2(x, y);
                squares[j][i] = new SquareItemExpans(gameScreen, itemWidth, itemWidth, position);
            }
        }

//        mGameScreenExpans = gameScreenExpans;
//        ballsWeights = new float[Constants.BALLS_TYPES_NUM];
        ballsWeights = getInitBallWeights();
//        SquareItemExpans squareItemExpans = squares[0][0];
    }

    private float[] getInitBallWeights() {

        float[] weights = new float[Constants.BALLS_TYPES_NUM];
        weights[0] = Constants.NORMAL_WEIGHT;
        weights[1] = Constants.TIGHT_WEIGHT;
        weights[2] = Constants.FREEZE_WEIGHT;
        weights[3] = Constants.COLORLESS_WEIGHT;
        weights[4] = BOMB_WEIGHT;

        updateBallWeights(100);
        updateBallWeights(200);
        updateBallWeights(500);
        updateBallWeights(1000);
        return weights;
    }

    private void updateBallWeights(int turns) {
//        int turns = getNumberOfTurns();

        float newWeight = (float) (TIGHT_WEIGHT + Math.atan(180*turns/1000/Math.PI)/1.4915);
//        float[] initWeights = getInitBallWeights();
    }


    @Override
    public BallsInfo getBallInfo() {
        BallsInfo info = new BallsInfo();
        SquareItemExpans squareItemExpans = squares[(int) selectedBall.x][(int) selectedBall.y];
//        SquareItem squareItem = squares
        info.color = squareItemExpans.getBallColor();
        info.ballIsTough = squareItemExpans.ballIsTough;
        if(info.ballIsTough) {
            info.toughtness = squareItemExpans.toughtness;
        }
        info.isFreeze = squareItemExpans.ballIsFreeze;
        info.isColorless = squareItemExpans.ballIsColorless;
        info.isBomb      = squareItemExpans.ballIsBomb;
        return info;
    }

    @Override
    public void setTransportBallProph(Vector2 clickPosition, BallsInfo info) {
        if (info.color == -3) {
            throw new IllegalArgumentException("Expes Wrong color ");
        }
        squares[(int) clickPosition.x][(int) clickPosition.y].setBallColor(info.color);
        if (info.ballIsTough) {
            squares[(int) clickPosition.x][(int) clickPosition.y].ballIsTough = true;
            squares[(int) clickPosition.x][(int) clickPosition.y].setToughtness(info.toughtness);
        }
        if(info.isColorless) {
            squares[(int) clickPosition.x][(int) clickPosition.y].setBallIsColorless(true);
        }
        if (info.isBomb) {
            squares[(int) clickPosition.x][(int) clickPosition.y].setBallIsBomb(true);
        }

    }

    @Override
    public void getNextTurnBalls() {
        for (int i = 0; i < numberOfAiBalls; i++) {
            Vector2[] freeSquares = checkSquares(false);
            if (freeSquares.length < 3) {
                noFreeSpace();
            }
            if (freeSquares.length != 0 ) {
                int random = MathUtils.random(0, freeSquares.length - 1);
                int ballType = getNextBallType();
                BallsInfo info = getNewBallInfo();
                if (info.color == -3) {
                    throw new IllegalArgumentException("Expes Get Wrong color ");
                }
                if (ballType == Constants.TYPE_NORMAL) {
                    squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                            .setBallColor(info.color);
                } else if (ballType == Constants.TYPE_TIGHT) {
                    squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                            .setBallColor(info.color);
                    squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                            .ballIsTight(true);
                    squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                            .toughtness = info.toughtness;
                } else if(ballType == Constants.TYPE_FREEZE) {
                    squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                            .setBallColor(info.color);
                    squares[(int) freeSquares[random].x][(int) freeSquares[random].y].
                            setBallIsFreeze(true);
                } else if (ballType == Constants.TYPE_COLORLESS) {
                    squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                            .setBallColor(Constants.COLOR_COLORLEESS );
                    squares[(int) freeSquares[random].x][(int) freeSquares[random].y].
                            setBallIsColorless(true);
                } else if (ballType == TYPE_BOMB) {
                    squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                            .setBallColor(info.color);
                    squares[(int) freeSquares[random].x][(int) freeSquares[random].y].
                            setBallIsBomb(true);
                }
                else {
                    Gdx.app.log("expens","wrong ball type");
                }

                squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                        .setNextTurnBall(true);
            }
        }
    }

    private int getNextBallType() {
        float[] ballsRoll = new float[ballsWeights.length];
        for (int i = 0; i < ballsWeights.length; i++) {
            ballsRoll[i] = MathUtils.random(0,ballsWeights[i]);
        }
        int num = 0;
        for (int j = 1; j < ballsRoll.length; j++) {
            if (ballsRoll[j] > ballsRoll[num] ) {
                num = j;
            }
        }
        return num;
    }

//    @Override
//    public void addEnergy(int lineLong) {
//        energy += ENERGY_BY_BALL*lineLong;
//        if (energy > 100) {
//            energy = 100;
//        }
//    }

    @Override
    public void setEnergy(int val) {
        super.setEnergy(energy);
    }

    @Override
    public BallsInfo getNewBallInfo() {
        BallsInfo info = new BallsInfo();
        info.color = MathUtils.random(0, numberOfColors - 1);
        info.toughtness = MathUtils.random(2, MAX_TOUGH);
        return info;
    }
}
