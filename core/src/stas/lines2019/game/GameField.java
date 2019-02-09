package stas.lines2019.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.TimeUtils;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import stas.lines2019.game.Screens.GameScreen;
import stas.lines2019.game.funcs.CheckBallLines;
import stas.lines2019.game.funcs.FindBallPath;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;


import java.util.ArrayList;
import java.util.Hashtable;

public class GameField {
    private static final String TAG = GameField.class.getName();

    private GameScreen gameScreen;
    private Texture textureBall;
    private ShapeRenderer shapeRenderer;
    private Preferences gamePref;

    private ParticleEffectPool touchEffectPool;
    Array<ParticleEffectPool.PooledEffect> effects = new Array<ParticleEffectPool.PooledEffect>();

    private Vector2 position;

    // путь до точки назанчения
    private Vector2[] path;
    private Vector2[] ballPathCellsCoord;

    // item dimensions
    private int itemWidth;

    // game parameters
    private int fieldDimension = 9;
    private int numberOfAiBalls = 3;
    public int numberOfColors = 7;
    private int numberOfTurns;
    private int gameScore;
    private int gameScoreFullOld;
    private int highScores;

    // turnParameters
    private boolean isBallSelected = false;
    private boolean isDrawBallPath = false;
    private boolean achieveUnlock = false;
    private boolean drawNewAchievment = false;
    private boolean isInputProccActive = false;
    private Vector2 selectedBall;
    private Vector2 transportPosition;
    private Vector2[] nextTurnBallCells;

    // массив с ячейками
    private SquareItem[][] squares;
    private int[][] ballColors;
    public Vector2 initPos;

    private Background background;

    // для передвижения
    private float ballMoveX;
    private float ballMoveY;

    public float gameTime;
    public float gameTimeFullOld;
    private float ballMoveTime;
    private float achiveDrawTime;
    private int ballMoveNumber = 1;
    private int lineLong;

    Button rulesButton;
    Rectangle rulesHitBox;


    public GameField(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        shapeRenderer = new ShapeRenderer();
//        GDXDialogs dialogs = GDXDialogsSystem.install();
        isInputProccActive = true;

        Gdx.gl.glClearColor(67, 99, 135, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int screenWidth = Gdx.graphics.getWidth();
        int screnHeight = Gdx.graphics.getHeight();

        itemWidth = (int) (screenWidth / fieldDimension);

        initPos = new Vector2(0, screnHeight - screenWidth - gameScreen.lableItemHeight);
        background = new Background(initPos,
                screenWidth,
                screnHeight,
                fieldDimension);

        squares = new SquareItem[fieldDimension][fieldDimension];
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                int x = (int) initPos.x + j * itemWidth;
                int y = (int) initPos.y + i * itemWidth;
                Vector2 position = new Vector2(x, y);
                squares[j][i] = new SquareItem(gameScreen, itemWidth, itemWidth, position);
            }
        }

        loadPrefer();
        if (gameScreen.lineGame.findSaveGame) {

            for (int i = 0; i < fieldDimension; i++) {
                for (int j = 0; j < fieldDimension; j++) {
                    if (ballColors[i][j] > 0) {
                        squares[i][j].setHasBall(true);
                        squares[i][j].setBallColor(ballColors[i][j]);
//                        squares[i][j].
                    }
                }
            }
        }

        nextTurnBallCells = new Vector2[numberOfAiBalls];
        aiTurn();

        // определяем эффекты
        ParticleEffect touchEffect = new ParticleEffect();
        touchEffect.load(Gdx.files.internal("fire3.p"), Gdx.files.internal(""));
        touchEffect.setEmittersCleanUpBlendFunction(true);
        touchEffectPool = new ParticleEffectPool(touchEffect, 5, 9);
        spawnParticleEffect(-300, -100);
        addRulesButton();
//        addFakeBalls(9,0,0,6,1);
    }

    private void savePrefer() {

        gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gamePref.putBoolean(Constants.PREF_GAME_IS_PLAY, true);
        gamePref.putFloat(Constants.PREF_TIME_PLAYED, gameTime);
        gamePref.putFloat(Constants.PREF_SCORE, gameScore);
        gamePref.putFloat(Constants.PREF_TURNS, numberOfTurns);
        gamePref.putFloat(Constants.PREF_TIME_PLAYED_FULL, gameTime + gameTimeFullOld);
        gamePref.putInteger(Constants.PREF_SCORE_FULL, gameScore + gameScoreFullOld);
        if (gameScore > highScores) {
            highScores = gameScore;
            gamePref.putInteger(Constants.PREF_HIGH_SCORE, gameScore);
        }
        Hashtable<String, String> hashTable = new Hashtable<String, String>();
        Json json = new Json();
        int[][] ballColors = new int[fieldDimension][fieldDimension];
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                int color = squares[i][j].getBallColor();
                ballColors[i][j] = squares[i][j].getBallColor();
            }
        }
        hashTable.put(Constants.PREF_GAME_MASSIVE, json.toJson(ballColors)); //here you are serializing the array
        gamePref.put(hashTable);
        gamePref.flush();
    }

    private void loadPrefer() {
        gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gameTimeFullOld = gamePref.getFloat(Constants.PREF_TIME_PLAYED_FULL, 0);
        gameScoreFullOld = gamePref.getInteger(Constants.PREF_SCORE_FULL, 0);

        highScores = gamePref.getInteger(Constants.PREF_HIGH_SCORE, 0);
        if (gameScreen.lineGame.findSaveGame) {
            gameTime = gamePref.getFloat(Constants.PREF_TIME_PLAYED);
            gameScore = (int) gamePref.getFloat(Constants.PREF_SCORE);
            numberOfTurns = (int) gamePref.getFloat(Constants.PREF_TURNS);
            Json json = new Json();
            String serializedInts = gamePref.getString(Constants.PREF_GAME_MASSIVE);
            try {
                ballColors = json.fromJson(int[][].class, serializedInts); //you need to pass the class type - be aware of it!
            } catch (Exception e) {
                Gdx.app.log(TAG, "exception getiing field", e);
            }
        }


    }

    private void deleteBalls(Vector2[] balls) {

        for (int i = 0; i < balls.length; i++) {

            float x = squares[(int) balls[i].x][(int) balls[i].y].getCenterPosition().x;
            float y = squares[(int) balls[i].x][(int) balls[i].y].getCenterPosition().y;
            spawnParticleEffect((int) x, (int) y);
            returnSquareInitState(balls[i], true);
        }
    }

    public void render(SpriteBatch batch, float dt) {
        Gdx.gl.glClearColor(0.3f, 0.47f, 0.65f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(dt);

        rulesButton.draw(batch, 1);

        long time1 = TimeUtils.millis();

        background.render(batch);

        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                //if(isBallSelected && selectedBall!=null&& squares[i][j].isHasBall()) {
                squares[i][j].render(batch);
                //}
            }
        }

        if (effects.size > 0) {
            Gdx.app.log(TAG, "has effect");
            for (int i = effects.size - 1; i >= 0; i--) {
                ParticleEffectPool.PooledEffect effect = effects.get(i);
                effect.draw(batch, dt);
                if (effect.isComplete()) {
                    effect.free();
                    effects.removeIndex(i);
                }
            }
        }

        if (path != null && path.length != 0 && isDrawBallPath) {
            ballMoveTime += dt;

            batch.draw(textureBall,
                    ballMoveX,
                    ballMoveY,
                    itemWidth * Constants.BALL_SIZE_RATIO,
                    itemWidth * Constants.BALL_SIZE_RATIO);

            updateMove(dt);
            if (ballMoveTime > 1) {
                isDrawBallPath = false;
                ballMoveNumber = 1;
            }
            if (!isDrawBallPath) {
                squares[(int) transportPosition.x][(int) transportPosition.y].setHasBall(true);
                ballMoveTime = 0;
                // проверяем на наличие составленных линий
                CheckBallLines check = new CheckBallLines(squares, numberOfColors);
                boolean hasLine = check.startCheck();
                if (hasLine && check.getBallsInLine() != null) {
                    deleteBalls(check.getBallsInLine());
                    lineLong = check.getNumberBallsInLine();
                    gameScore += lineLong * Constants.SCORED_PER_BALL;
                } else {
                    aiTurn();
                }
                checkAchieve();
                check = new CheckBallLines(squares, numberOfColors);
                hasLine = check.startCheck();
                if (hasLine && check.getBallsInLine() != null) {
                    deleteBalls(check.getBallsInLine());
                    gameScore += check.getNumberBallsInLine() * Constants.SCORED_PER_BALL +
                            (check.getNumberBallsInLine() - 5) * 4;
                }
            }
            Gdx.app.log("Move", "move time=" + ballMoveTime);
        }

        BitmapFont achieveFont = Assets.instance.skinAssets.skin.getFont("game-font");

        if (drawNewAchievment) {
            achiveDrawTime += dt;
            if (achiveDrawTime > Constants.ACHIEVE_DRAW_TIME) {
                drawNewAchievment = false;
                achiveDrawTime = 0;
            }
            int starSize = Assets.instance.starAssets.texture.getWidth();
            achieveFont.draw(batch,
                    "new achievement",
                    Gdx.graphics.getWidth() / 2 - starSize * 2f,
                    initPos.y - Constants.HUD_OFFSET * Gdx.graphics.getHeight());


            batch.draw(Assets.instance.starAssets.texture,
                    Gdx.graphics.getWidth() / 2 - starSize * 3f,
                    initPos.y - Constants.HUD_OFFSET * Gdx.graphics.getHeight() - starSize / 2,
                    starSize + starSize / 5 * MathUtils.sin(gameTime),
                    starSize + starSize / 5 * MathUtils.sin(gameTime)
            );

//            batch.draw()

        }
    }

    private void spawnParticleEffect(int x, int y) {
        ParticleEffectPool.PooledEffect effect = touchEffectPool.obtain();
//        effect.setPosition(x, Gdx.graphics.getHeight() - y);
        effect.setPosition(x, y);
        effects.add(effect);
    }

    private float[] vector2ArrayToFloatArray(Vector2[] dots) {
        float[] floatDots = new float[dots.length * 2];
        int i = 0;
        for (Vector2 dot : dots) {
            floatDots[i++] = dot.x;
            floatDots[i++] = dot.y;
        }
        return floatDots;
    }

    public void update(final float dt) {

        // время игры
        gameTime += dt;

        if (isBallSelected && selectedBall != null) {
            squares[(int) selectedBall.x][(int) selectedBall.y].update(dt);
        }
        if (isInputProccActive) {
            Gdx.input.setInputProcessor(new InputAdapter() {
                @Override
                public boolean keyDown(int keycode) {
                    if (keycode == Input.Keys.BACK) {
                        isInputProccActive = false;
                        gameScreen.showExitDialog();
                        // Respond to the back button click here
//                    dispose();
//                    gameScreen.lineGame.setMainMenuScreen();
                        return true;
                    }
                    return false;
                }

                public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                    Vector2 clickPosition = null;
//                spawnParticleEffect(screenX,screenY);
                    if (button == Input.Buttons.LEFT) {

                        if (isBallSelected) {
                            // если шар уже выбран то проверяем куда потом нажали
                            clickPosition = checkClickEvent(screenX, screenY);
                            if (!clickPosition.equals(new Vector2(666, 665))) {
                                // проверяем на наличие прохода для шарика
                                FindBallPath finder = new FindBallPath(squares,
                                        selectedBall,
                                        clickPosition);

                                // передаем путь до точки
                                boolean pathIsFind = finder.findPath();
                                Gdx.app.log(TAG, "pathFind =" + pathIsFind);
                                if (clickPosition.equals(selectedBall)) {
                                    returnSquareInitState(clickPosition, false);

                                    isBallSelected = false;
                                    selectedBall = null;
                                } else if (squares[(int) clickPosition.x][(int) clickPosition.y].isHasBall()) {
                                    Gdx.app.log(TAG, "clicked  to ball i,j " + clickPosition.x + " " + clickPosition.y);
                                } else if (pathIsFind) {
                                    // получаем информацию из выбранного шара и убераем его
                                    int color = squares[(int) selectedBall.x][(int) selectedBall.y].getBallColor();

                                    path = finder.getPath();

                                    // получаем координаты центров ячеек
                                    ballPathCellsCoord = new Vector2[path.length];
                                    for (int i = 0; i < path.length; i++) {
                                        ballPathCellsCoord[i] = squares[(int) path[i].x][(int) path[i].y].getPosition();
                                    }

                                    ballMoveX = ballPathCellsCoord[0].x;
                                    ballMoveY = ballPathCellsCoord[0].y;
                                    textureBall = squares[(int) selectedBall.x][(int) selectedBall.y].getBallColorText();
                                    returnSquareInitState(new Vector2(selectedBall), true);

                                    if (path.length > 1) {
                                        isDrawBallPath = true;
                                    }

                                    // переносим в другую ячейку
                                    transportPosition = clickPosition;

                                    squares[(int) clickPosition.x][(int) clickPosition.y].setBallColor(color);
                                    if (squares[(int) clickPosition.x][(int) clickPosition.y].isNextTurnBall()) {
                                        addNextTurnBalls();
                                    }
                                    isBallSelected = false;
                                    selectedBall = null;
                                }
                            }
                        } else if (!isBallSelected) {

                            // если шар еще не выбран то выбираем его
                            clickPosition = checkClickEvent(screenX, screenY);
                            if (clickPosition != null && !clickPosition.equals(new Vector2(666,665))) {
                                if (squares[(int) clickPosition.x][(int) clickPosition.y].isHasBall()) {
                                    squares[(int) clickPosition.x][(int) clickPosition.y].setActive(true);
                                    squares[(int) clickPosition.x][(int) clickPosition.y].update(dt);
                                    isBallSelected = true;
                                    selectedBall = clickPosition;
                                }
                            } else if (clickPosition.equals(new Vector2(666,665) ))   {
                                gameScreen.rulesDialog();
                            }
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private void addRulesButton() {
        rulesButton = new TextButton("Rules", Assets.instance.skinAssets.skin);
        rulesButton.setPosition(0,
                initPos.y - Constants.HUD_FONT_TITLE * Gdx.graphics.getHeight() * 1.5f);
        rulesButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                gameScreen.rulesDialog();
//                rulesButton();
                return true;
            }
        });

        rulesHitBox = new Rectangle(0,
                initPos.y - Constants.HUD_FONT_TITLE * Gdx.graphics.getHeight() * 1.5f,
                rulesButton.getPrefWidth(),
                rulesButton.getPrefHeight()
        );
    }

    private void updateMove(float dt) {

        Gdx.app.log(TAG, "ballMove " + ballMoveNumber + " pathSize=" + path.length);
        if (path.length == 1) {
            Gdx.app.log(TAG, "path[0].x path[0].y " + path[0].x + " " + path[0].y);
        }

        float dx = path[ballMoveNumber].x - path[ballMoveNumber - 1].x;
        float dy = path[ballMoveNumber].y - path[ballMoveNumber - 1].y;

        if (Math.abs(dx) > 0) {
            ballMoveX += dx / Math.abs(dx) * Constants.MOVE_VEL * dt;
        } else if (Math.abs(dy) > 0) {
            ballMoveY += dy / Math.abs(dy) * Constants.MOVE_VEL * dt;
        }

        if (Math.abs(ballMoveX) > itemWidth || Math.abs(ballMoveY) > itemWidth) {
            ballMoveNumber++;
            if (ballMoveNumber < path.length - 1) {
                ballMoveX = ballPathCellsCoord[ballMoveNumber].x;
                ballMoveY = ballPathCellsCoord[ballMoveNumber].y;
            } else {
                isDrawBallPath = false;
                ballMoveNumber = 1;
            }
        }
    }

    public int getGameScore() {
        return gameScore;
    }

    public float getGameTime() {
        return gameTime;
    }

    public Vector2 checkClickEvent(int screenX, int screenY) {
        Vector2 clickPosition = null;
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {

                //проверяем попал ли щелчок в ячейку и помещяем туда шарик
                if (squares[i][j].hitBox.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                    clickPosition = new Vector2(i, j);
//                    aiTurn();
                }
                //squares[i][j].update(dt);
            }
        }
        if (rulesHitBox.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
            clickPosition = new Vector2(666, 665);
        }

        return clickPosition;
    }

    private void returnSquareInitState(Vector2 click, boolean delBall) {
        squares[(int) click.x][(int) click.y].setActive(false);
        squares[(int) click.x][(int) click.y].setBallInCenter();
        if (delBall) {
            squares[(int) click.x][(int) click.y].setHasBall(false);
            squares[(int) click.x][(int) click.y].setBallColor(-3);
        }
    }

    /* компьютер выбирает шарики и кладет их в рандомные ячейки
     */
    public void aiTurn() {
        if (numberOfTurns == 0) {
            getNextTurnBalls();
        }
        putBall();
        getNextTurnBalls();
        numberOfTurns++;
    }

    public void checkAchieve() {
        if (numberOfTurns != 0) {
            savePrefer();
//            loadPrefer();
            achieveUnlock = gameScreen.lineGame.achivementsList.checkAchivements();
            if (achieveUnlock) {
                drawNewAchievment = true;
                gameScreen.lineGame.saveAchieve();
                gameScreen.lineGame.loadAchieve();
                achievmentUnlocked();
            }
        }
    }

    private void achievmentUnlocked() {

    }

    private void putBall() {
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                if (squares[i][j].isNextTurnBall()) {
                    squares[i][j].setHasBall(true);
                    squares[i][j].setNextTurnBall(false);
                }
            }
        }
    }

    private void getNextTurnBalls() {
        for (int i = 0; i < numberOfAiBalls; i++) {
            Vector2[] freeSquares = checkSquares();
            if (freeSquares.length < 3) {
                isInputProccActive = false;
                gameScreen.gameOverDialog();
            }
            int random = MathUtils.random(0, freeSquares.length - 1);

            squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                    .setBallColor(MathUtils.random(0, numberOfColors - 1));
            squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                    .setNextTurnBall(true);
        }
    }

    private void addNextTurnBalls() {
        Vector2[] freeSquares = checkSquares();

//        if (freeSquares.length < 3) {
//            gameScreen.gameOverDialog();
//        }

        int random = MathUtils.random(0, freeSquares.length - 1);

        squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                .setBallColor(MathUtils.random(0, numberOfColors - 1));
        squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                .setNextTurnBall(true);
    }

    /*
        Игрок по щелчку выбирает шарик с которым взаимодействовать
     */
    public void playerTurn() {

    }

    /*  проверяем из всех ячеек где нет шариков получаем список таких ячеек в виде String[]

     */
    public Vector2[] checkSquares() {
        ArrayList<Vector2> freeSquares = new ArrayList<Vector2>();

        // проверяем все ячейки
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++)
                if (squares[i][j].isHasBall() == false) {
                    freeSquares.add(new Vector2(i, j));
                }
        }

        // отдаем массив из свободных ячеек
        Vector2[] freeOut = freeSquares.toArray(new Vector2[freeSquares.size()]);
        return freeOut;
    }


    private void addFakeBalls(int number, int color, int positX, int positY, int direction) {

        if (direction == 0) {
            for (int i = 0; i < number; i++) {

                squares[positX][positY + i]
                        .setBallColor(color);
                squares[positX][positY + i]
                        .setHasBall(true);
            }
        } else if (direction == 1) {
            for (int i = 0; i < number; i++) {

                squares[positX + i][positY]
                        .setBallColor(color);
                squares[positX + i][positY]
                        .setHasBall(true);
            }
        }
    }

//    public void dispose() {
//        gamePref.putFloat(Constants.PREF_TIME_PLAYED_FULL,gameTime + gameTimeFullOld);
//        gamePref.putFloat(Constants.PREF_SCORE_FULL,gameScore + gameScoreFullOld);
//        gamePref.flush();
//    }

    public int getGameScoreFullOld() {
        return gameScoreFullOld;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public int getLineLong() {
        return lineLong;
    }

    public int getHighScores() {
        return highScores;
    }

    public void setInputProccActive(boolean inputProccActive) {
        isInputProccActive = inputProccActive;
    }
}
