package stas.lines2019.game.gameFields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.TimeUtils;

import stas.lines2019.game.Background;
import stas.lines2019.game.Screens.GameScreen;
import stas.lines2019.game.Widgets.SkillButton;
import stas.lines2019.game.balls.BallsInfo;
import stas.lines2019.game.balls.SquareItem;
import stas.lines2019.game.balls.SquareItemExpans;
import stas.lines2019.game.funcs.CheckBallLines;
import stas.lines2019.game.funcs.FindBallPath;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

import static stas.lines2019.game.util.Constants.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import javax.swing.Box;

public class GameField {
    private static final String TAG = GameField.class.getName();

    public GameScreen gameScreen;
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
    public int itemWidth;

    // game parameters
    public int fieldDimension = 9;
    public int numberOfAiBalls = 3;
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
    public Vector2 selectedBall;
    private Vector2 transportPosition;
    private Vector2[] nextTurnBallCells;

    // массив с ячейками
    public SquareItemExpans[][] squares;
    private int[][] ballColors;
    public Vector2 initPos;

    private Background background;

    // для передвижения
    private float ballMoveX;
    private float ballMoveY;

    float timePalydUntilHide;
    public float gameTime;
    public float gameTimeFullOld;
    private float ballMoveTime;
    private float achiveDrawTime;
    private int ballMoveNumber = 1;
    private int lineLong;
    private long startTurnTime;
    public  long  turnTime;

    Button rulesButton;
    Rectangle rulesHitBox;

    Button helpButton;
    Rectangle helpHitBox;

    long startTime;

    public int energy;

    private SkillButton[] skillsButtons;
    private Rectangle[]   skillButtonsHitBoxes;

    private boolean isAnySkillPressed = false;
    private boolean isTeleportPressed = false;
    private boolean isRemovePressed = false;
    private boolean isColorlessPressed = false;
    private boolean isBlockPressed = false;
    private boolean isBombPressed = false;

    public void setEnergy(int val) {
        energy = val;
    }

    public GameField() {

    }

    public GameField(GameScreen gameScreen) {

        energy = 50;

        startTime = TimeUtils.millis();

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

        createSquares();

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
        touchEffectPool = new ParticleEffectPool(touchEffect, 8, 15);
        spawnParticleEffect(-300, -100);
        addRulesButton();


        if (gameScreen.isExpansionPlayed) {
            drawSkillsButtons() ;
        }

    }

    public void initStartTime() {

        startTime = TimeUtils.millis();
        gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        timePalydUntilHide = gamePref.getFloat(Constants.PREF_TIME_PLAYED,0);
    }

    public void createSquares () {
        squares = new SquareItemExpans[fieldDimension][fieldDimension];
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                int x = (int) initPos.x + j * itemWidth;
                int y = (int) initPos.y + i * itemWidth;
                Vector2 position = new Vector2(x, y);
                squares[j][i] = new SquareItemExpans(gameScreen, itemWidth, itemWidth, position);
            }
        }
    }

    private void drawSkillsButtons() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
//        skillsButtons = new TextButton[SKILLS_NUMBERS];
        skillsButtons = new SkillButton[SKILLS_NUMBERS];
        int initPositX = (int)((width - 5*width*SKILL_BUTTON_SIZE)/2);
        int initPositY = (int)(initPos.y - (SKILL_BUTTON_SIZE*height)/1.7);

        Texture[] skillTextures = new Texture[SKILLS_NUMBERS];
        skillTextures[0] = Assets.instance.skillAssets.teleportTexture;
        skillTextures[1] = Assets.instance.skillAssets.removeTexture;
        skillTextures[2] = Assets.instance.skillAssets.colorelessTexture;
        skillTextures[3] = Assets.instance.skillAssets.blockTexture;
        skillTextures[4] = Assets.instance.skillAssets.bombTexture;

        String[] skillCosts     = new String[SKILLS_NUMBERS];
        skillCosts[0]    = Integer.toString(SKILL_TELEPORT_COST);
        skillCosts[1]    = Integer.toString(SKILL_REMOVE_COST);
        skillCosts[2]    = Integer.toString(SKILL_COLORLESS_COST);
        skillCosts[3]    = Integer.toString(SKILL_BLOCK_COST);
        skillCosts[4]    = Integer.toString(SKILL_BOMB_COST);

        skillButtonsHitBoxes = new Rectangle[SKILLS_NUMBERS];

        for (int i = 0; i < skillsButtons.length; i++) {
            SkillButton textButton = new SkillButton(skillCosts[i],Assets.instance.skinAssets.skin,
                    new TextureRegionDrawable( new TextureRegion(skillTextures[i])));
            textButton.setSize(width*SKILL_BUTTON_SIZE,width*SKILL_BUTTON_SIZE/1.5f );
//            textButton.getStyle().imageUp =
//                    new TextureRegionDrawable( new TextureRegion(skillTextures[i]) );
//            textButton.getImage().setDrawable(new TextureRegionDrawable( new TextureRegion(skillTextures[i])));
//            textButton.getImage().se
//            textButton.
            textButton.setPosition(initPositX,initPositY);
            skillButtonsHitBoxes[i] = new Rectangle(initPositX,
                    initPositY,
                    width*SKILL_BUTTON_SIZE,
                    width*SKILL_BUTTON_SIZE/1.5f);

            textButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log(TAG,"skill test");
                    return super.touchDown(event, x, y, pointer, button);
                }
            });

//            fieldStage.addActor(textButton);

            initPositX += width*SKILL_BUTTON_SIZE + width*0.01;
            skillsButtons[i] = textButton;
        }
        Gdx.app.log(TAG,"skills");
    }


    private void savePrefer() {

        gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gamePref.putBoolean(Constants.PREF_GAME_IS_PLAY, true);
        gamePref.putFloat(Constants.PREF_TIME_PLAYED, gameTime);
        gamePref.putInteger(Constants.PREF_SCORE, gameScore);
        gamePref.putInteger(Constants.PREF_TURNS, numberOfTurns);
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

    public void savePrefTime() {
        gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gamePref.putBoolean(Constants.PREF_GAME_IS_PLAY, true);
        gamePref.flush();
    }

    private void loadPrefer() {
        gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gameTimeFullOld = gamePref.getFloat(Constants.PREF_TIME_PLAYED_FULL, 0);
        gameScoreFullOld = gamePref.getInteger(Constants.PREF_SCORE_FULL, 0);
        highScores = gamePref.getInteger(Constants.PREF_HIGH_SCORE, 0);
        if (gameScreen.lineGame.findSaveGame) {
            gameTime = gamePref.getFloat(Constants.PREF_TIME_PLAYED,0);
            gameScore = (int) gamePref.getInteger(Constants.PREF_SCORE,0);
            numberOfTurns = (int) gamePref.getInteger(Constants.PREF_TURNS,0);
            Json json = new Json();
            try {
                String serializedInts = gamePref.getString(Constants.PREF_GAME_MASSIVE);
                ballColors = json.fromJson(int[][].class, serializedInts); //you need to pass the class type - be aware of it!
            } catch (Exception e) {
                Gdx.app.log(TAG, "exception getiing field", e);
            }
        }

    }



    private void checkBombsInLines(Vector2[] balls) {
        ArrayList<Vector2> initArray = new ArrayList<Vector2>(balls.length);
        boolean lineHasBomb = false;
        List<Vector2> pos = new ArrayList<Vector2>();
        for (int i = 0; i < balls.length ; i++) {
            if (squares[(int) balls[i].x][(int) balls[i].y].ballIsBomb) {
                lineHasBomb = true;
                pos.add(new Vector2(balls[i].x,balls[i].y));
            }
        }

        for (int i = 0; i < balls.length; i++) {
            initArray.add(balls[i]);
        }
        if (lineHasBomb) {
            for (int i = 0; i < pos.size(); i++) {
                Vector2 newVal =  new Vector2(pos.get(i).x -1,pos.get(i).y);
                if (pos.get(i).x != 0) initArray.add(newVal);

                newVal =  new Vector2(pos.get(i).x -1,pos.get(i).y -1);
                if (pos.get(i).x != 0 && pos.get(i).y != 0) initArray.add(newVal);

                newVal =  new Vector2(pos.get(i).x ,pos.get(i).y -1);
                if ( pos.get(i).y != 0) initArray.add(newVal);

                newVal =  new Vector2(pos.get(i).x +1,pos.get(i).y -1);
                if (pos.get(i).x != fieldDimension -1 && pos.get(i).y != 0) initArray.add(newVal);

                newVal =  new Vector2(pos.get(i).x + 1,pos.get(i).y );
                if (pos.get(i).x != fieldDimension-1 ) initArray.add(newVal);

                newVal =  new Vector2(pos.get(i).x + 1,pos.get(i).y + 1);
                if (pos.get(i).x != fieldDimension -1 && pos.get(i).y != fieldDimension -1) initArray.add(newVal);

                newVal =  new Vector2(pos.get(i).x ,pos.get(i).y + 1);
                if ( pos.get(i).y != fieldDimension-1) initArray.add(newVal);

                newVal =  new Vector2(pos.get(i).x -1,pos.get(i).y +1);
                if (pos.get(i).x != 0 && pos.get(i).y != fieldDimension -1)initArray.add(newVal);
            }
        }
        Vector2[] newArray = new Vector2[initArray.size()];
        initArray.toArray(newArray) ;
        deleteBalls(newArray );
    }

    private void deleteBalls(Vector2[] balls) {
        for (int i = 0; i < balls.length; i++) {
            float x = squares[(int) balls[i].x][(int) balls[i].y].getCenterPosition().x;
            float y = squares[(int) balls[i].x][(int) balls[i].y].getCenterPosition().y;
            spawnParticleEffect((int) x, (int) y);
            if (squares[(int) balls[i].x][(int) balls[i].y].isNextTurnBall()) {
                squares[(int) balls[i].x][(int) balls[i].y].setNextTurnBall(false);
                addNextTurnBalls();
            }
            returnSquareInitState(balls[i], true);
        }
        Assets.instance.soundsBase.explSound.play(0.2f);
    }

    public void render(SpriteBatch batch, float dt) {

        Gdx.gl.glClearColor(0.3f, 0.47f, 0.65f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(dt);

//        fieldStage.act();

        background.render(batch);

        rulesButton.draw(batch, 1);


        if (gameScreen.isExpansionPlayed) {
            for (int i = 0; i < skillsButtons.length; i++) {
                skillsButtons[i].draw(batch, 1);
            }
            helpButton.draw(batch,1);
        }

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
                    checkBombsInLines(check.getBallsInLine());
//                    deleteBalls(check.getBallsInLine());
                    lineLong = check.getNumberBallsInLine() ;
                    lineIsSet();
                } else {
                    aiTurn();
                }
                checkAchieve();
                check = new CheckBallLines(squares, numberOfColors);
                hasLine = check.startCheck();
                if (hasLine && check.getBallsInLine() != null) {
                    checkBombsInLines(check.getBallsInLine());
//                    deleteBalls(check.getBallsInLine());
                    lineIsSet();
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
        }
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);

        turnTime = TimeUtils.timeSinceMillis(startTurnTime)/1000;
        gameTime = elapsedTime / 1000 + timePalydUntilHide;
    }

    public void lineIsSet() {
        gameScore += lineLong * Constants.SCORED_PER_BALL +
                (lineLong - 5) * 4;
        addEnergy(lineLong);
        setEnergy(0);
    }

    public void addEnergy(int lineLong) {

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
//        gameTime += dt;

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
                            if (!clickPosition.equals(new Vector2(666, 665)) &&
                                    !clickPosition.equals(new Vector2(766, 765)) &&
                                    !clickPosition.equals(new Vector2(66, 77))
                                    ) {
                                // проверяем на наличие прохода для шарика
                                FindBallPath finder = new FindBallPath(squares,
                                        selectedBall,
                                        clickPosition);

                                // передаем путь до точки
                                boolean pathIsFind = finder.findPath();
                                Gdx.app.log(TAG, "pathFind =" + pathIsFind);
                                if (clickPosition.equals(selectedBall)) {
                                    isBallSelected = false;
                                    selectedBall = null;
                                    squares[(int) clickPosition.x][(int) clickPosition.y].setBallInCenter();
                                } else if (squares[(int) clickPosition.x][(int) clickPosition.y].isHasBall()) {
                                    Gdx.app.log(TAG, "clicked  to ball i,j " + clickPosition.x + " " + clickPosition.y);
                                } else if (pathIsFind || isTeleportPressed) {

                                    // получаем информацию из выбранного шара и убераем его
                                    BallsInfo info = getBallInfo();
                                    if (!isTeleportPressed) {
                                        path = finder.getPath();

                                        // получаем координаты центров ячеек
                                        ballPathCellsCoord = new Vector2[path.length];
                                        for (int i = 0; i < path.length; i++) {
                                            ballPathCellsCoord[i] = squares[(int) path[i].x][(int) path[i].y].getPosition();
                                        }

                                        ballMoveX = ballPathCellsCoord[0].x;
                                        ballMoveY = ballPathCellsCoord[0].y;
                                        textureBall = squares[(int) selectedBall.x][(int) selectedBall.y].getBallColorText();
                                        if (path.length > 1) {
                                            isDrawBallPath = true;
                                        }

                                        // переносим в другую ячейку
                                        transportPosition = clickPosition;
                                    }

                                    returnSquareInitState(new Vector2(selectedBall), true);

                                    squares[(int) clickPosition.x][(int) clickPosition.y].setHasBall(true);
//                                    squares[(int) clickPosition.x][(int) clickPosition.y].setBallColor(info.color);
                                    setTransportBallProph(clickPosition,info);
                                    if (squares[(int) clickPosition.x][(int) clickPosition.y].isNextTurnBall()) {
                                        squares[(int) clickPosition.x][(int) clickPosition.y].setNextTurnBall(false);
                                        addNextTurnBalls();
                                    }
                                    isBallSelected = false;
                                    selectedBall = null;

                                    isAnySkillPressed = false;
                                    isTeleportPressed = false;
                                }
                            }
                        } else if (!isBallSelected) {

                            // если шар еще не выбран то выбираем его
                            clickPosition = checkClickEvent(screenX, screenY);
                            if ( clickPosition != null &&
                                    !clickPosition.equals(new Vector2(666,665)) &&
                                    !clickPosition.equals(new Vector2(766,765)) &&
                                    !clickPosition.equals(new Vector2(66, 77)) &&
                                    clickPosition.x < fieldDimension && clickPosition.y < fieldDimension
                                    ) {
                                if ( squares[(int) clickPosition.x][(int) clickPosition.y].isHasBall() &&
                                        !squares[(int) clickPosition.x][(int) clickPosition.y].ballIsFreeze
                                        && (!isAnySkillPressed || isBlockPressed)) {
                                    squares[(int) clickPosition.x][(int) clickPosition.y].setActive(true);
                                    squares[(int) clickPosition.x][(int) clickPosition.y].update(dt);
                                    isBallSelected = true;
                                    selectedBall = clickPosition;
                                } else if (isAnySkillPressed) {
                                    if (isRemovePressed) {
                                        isAnySkillPressed = false;
                                        isRemovePressed   = false;
                                        returnSquareInitState(clickPosition,true);
                                    } else if (isColorlessPressed) {
                                        isAnySkillPressed    = false;
                                        isColorlessPressed   = false;
                                        squares[(int) clickPosition.x][(int) clickPosition.y].
                                                setBallIsColorless(true);
                                        squares[(int) clickPosition.x][(int) clickPosition.y].
                                                setBallColor(COLOR_COLORLEESS);
                                    } else if (isBombPressed) {
                                        isAnySkillPressed    = false;
                                        isBombPressed   = false;
                                        squares[(int) clickPosition.x][(int) clickPosition.y].
                                                setBallIsBomb(true);
//                                        squares[(int) clickPosition.x][(int) clickPosition.y].
//                                                setBallColor(COLOR_COLORLEESS);
                                    }
                                }
                            } else if (clickPosition.equals(new Vector2(666,665) ))   {
                                isInputProccActive = false;
                                gameScreen.rulesDialog();
                            } else if (clickPosition.equals(new Vector2(766,765) ))   {
                                isInputProccActive = false;
                                gameScreen.helpDialog();
                            }   else if (clickPosition.equals(new Vector2(SKILL_TELEPORT, SKILL_TELEPORT ))) {
                                if (energy >= SKILL_TELEPORT_COST) {
//                                    isAnySkillPressed = true;
                                    isTeleportPressed = true;
                                    energy -= SKILL_TELEPORT_COST;
                                }

                            } else if (clickPosition.equals(new Vector2(SKILL_REMOVE, SKILL_REMOVE ))) {
                                if (energy >= SKILL_REMOVE_COST ) {
                                    isAnySkillPressed = true;
                                    isRemovePressed = true;
                                    energy -= SKILL_REMOVE_COST;
                                }

                            } else if (clickPosition.equals(new Vector2(SKILL_COLORLESS, SKILL_COLORLESS ))) {
                                if (energy >= SKILL_COLORLESS_COST) {
                                    isAnySkillPressed = true;
                                    isColorlessPressed = true;
                                    energy -= SKILL_COLORLESS_COST;
                                }

                            } else if (clickPosition.equals(new Vector2(SKILL_BLOCK, SKILL_BLOCK ))) {
                                Gdx.app.log(TAG,"block press");
                                if (energy >= SKILL_BLOCK_COST) {
                                    isAnySkillPressed = true;
                                    isBlockPressed = true;
                                    energy -= SKILL_BLOCK_COST;
                                }

                            } else if (clickPosition.equals(new Vector2(SKILL_BOMB, SKILL_BOMB ))) {
                                if (energy >= SKILL_BOMB_COST) {
                                    isAnySkillPressed = true;
                                    isBombPressed = true;
                                    energy -= SKILL_BOMB_COST;
                                }
                            }
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    public BallsInfo getBallInfo() {
        BallsInfo info = new BallsInfo();
        info.color = squares[(int) selectedBall.x][(int) selectedBall.y].getBallColor();
        return info;
    }

    public void setTransportBallProph(Vector2 clickPosition, BallsInfo info) {
        squares[(int) clickPosition.x][(int) clickPosition.y].setBallColor(info.color);
    }

    private void addRulesButton() {
        rulesButton = new TextButton("", Assets.instance.skinAssets.skin);
        float buttX = 0;
//        float buttY = initPos.y - Constants.HUD_FONT_TITLE * Gdx.graphics.getHeight() * 1.5f;
        float buttY  = gameScreen.height - gameScreen.width*SKILL_BUTTON_SIZE/1.4f;
        rulesButton.setPosition(buttX, buttY);

        rulesButton.setSize(gameScreen.width*SKILL_BUTTON_SIZE/1.4f,
                gameScreen.width*SKILL_BUTTON_SIZE/1.4f);
        rulesButton.add(new Image(Assets.instance.iconAssets.helpTexture));
        rulesButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                gameScreen.rulesDialog();
//                rulesButton();
                return true;
            }
        });

        rulesHitBox = new Rectangle(buttX,
                buttY,
                rulesButton.getPrefWidth(),
                rulesButton.getPrefHeight()
        );

        if (gameScreen.isExpansionPlayed) {
            helpButton = new TextButton("", Assets.instance.skinAssets.skin);
            buttX = gameScreen.width - gameScreen.width*SKILL_BUTTON_SIZE/1.2f;
            buttY = gameScreen.height - gameScreen.width*SKILL_BUTTON_SIZE/1.4f;
            helpButton.setPosition(buttX, buttY);

            helpButton.setSize(gameScreen.width*SKILL_BUTTON_SIZE/1.4f,
                    gameScreen.width*SKILL_BUTTON_SIZE/1.4f);
            helpButton.add(new Image(Assets.instance.iconAssets.rulesTexture));
            helpButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                gameScreen.rulesDialog();
//                rulesButton();
                    return true;
                }
            });

            helpHitBox = new Rectangle(buttX,
                    buttY,
                    rulesButton.getPrefWidth(),
                    rulesButton.getPrefHeight()
            );
        }
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
            }
        }

        if (rulesHitBox.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
            clickPosition = new Vector2(666, 665);
        }

        if (gameScreen.isExpansionPlayed) {
            if (helpHitBox.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                clickPosition = new Vector2(766, 765);
            }
            int[] skillType = new int[SKILLS_NUMBERS];
            skillType[0] = SKILL_TELEPORT;
            skillType[1] = SKILL_REMOVE;
            skillType[2] = SKILL_COLORLESS;
            skillType[3] = SKILL_BLOCK;
            skillType[4] = SKILL_BOMB;


            for (int i = 0; i < SKILLS_NUMBERS; i++) {
                if (skillButtonsHitBoxes[i].contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                    clickPosition = new Vector2(skillType[i], skillType[i]);
                }
            }
        }

        if (clickPosition == null) {
            clickPosition = new Vector2(66,77);
        }
        return clickPosition;
    }

    public void returnSquareInitState(Vector2 click, boolean delBall) {
        SquareItemExpans item = squares[(int) click.x][(int) click.y];
        if (item.ballIsTough) {
            item.setActive(false);
            item.setBallInCenter();
            item.ballDestroy();
        } else if(item.ballIsFreeze) {
            item.setActive(false);
            item.setBallIsFreeze(false);
            item.setBallInCenter();
            item.setBallColor(-3);
            item.setHasBall(false);
        } else if (item.ballIsColorless) {
            item.setActive(false);
            item.setBallIsColorless(false);
            item.setBallInCenter();
            item.setBallColor(-3);
            item.setHasBall(false);
        } else if (item.ballIsBomb) {
            item.setActive(false);
            item.setBallIsBomb(false);
            item.setBallInCenter();
            item.setBallColor(-3);
            item.setHasBall(false);
        }
        else {
            squares[(int) click.x][(int) click.y].setActive(false);
            squares[(int) click.x][(int) click.y].setBallInCenter();
            if (delBall) {
                squares[(int) click.x][(int) click.y].setHasBall(false);
                squares[(int) click.x][(int) click.y].setBallColor(-3);
            }
        }
    }

    /* компьютер выбирает шарики и кладет их в рандомные ячейки
     */
    public void aiTurn() {
        if ( !isBlockPressed) {
            startTurnTime = TimeUtils.millis();
            if (numberOfTurns == 0) {
                getNextTurnBalls();
            }
            putBall();
            getNextTurnBalls();
            numberOfTurns++;
        } else {
            isAnySkillPressed = false;
            isBlockPressed = false;
        }
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
                    if (squares[i][j].getBallColor() == -3) {
                        throw (new IllegalArgumentException("wrong color, i=" + i + " j=" + j));
                    }
                    squares[i][j].setNextTurnBall(false);
                }
            }
        }
    }

    public void getNextTurnBalls() {
        for (int i = 0; i < numberOfAiBalls; i++) {
            Vector2[] freeSquares = checkSquares(false);
            if (freeSquares.length < 3) {
                noFreeSpace();
            }
            if (freeSquares.length != 0 ) {
                int random = MathUtils.random(0, freeSquares.length - 1);
                BallsInfo info = getNewBallInfo();
                squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                        .setBallColor(info.color);
                squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                        .setNextTurnBall(true);
            }
        }
    }

    public BallsInfo getNewBallInfo() {
        BallsInfo info = new BallsInfo();
        info.color = MathUtils.random(0, numberOfColors - 1);
        return info;
    }


    public void noFreeSpace() {
        isInputProccActive = false;
        gameScreen.gameOverDialog(true,true);
    }

    private void addNextTurnBalls() {
        Vector2[] freeSquares = checkSquares(false);
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
    public Vector2[] checkSquares(boolean hasBas) {
        ArrayList<Vector2> freeSquares = new ArrayList<Vector2>();

        // проверяем все ячейки
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++)
                if (squares[i][j].isHasBall() == hasBas) {
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
