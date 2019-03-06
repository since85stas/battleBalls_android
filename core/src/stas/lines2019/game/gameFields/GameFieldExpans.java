package stas.lines2019.game.gameFields;

import com.badlogic.gdx.math.Vector2;

import stas.lines2019.game.Screens.GameScreenExpans;
import stas.lines2019.game.balls.BallsInfo;
import stas.lines2019.game.balls.SquareItem;
import stas.lines2019.game.balls.SquareItemExpans;

/**
 * Created by seeyo on 28.02.2019.
 */

public class GameFieldExpans extends GameField {

    public GameScreenExpans mGameScreenExpans;

//    private SquareItemExpans[][] squares;

    public GameFieldExpans (GameScreenExpans gameScreenExpans) {
        super(gameScreenExpans);
        mGameScreenExpans = gameScreenExpans;

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
//        SquareItemExpans squareItemExpans = squares[0][0];
    }

    @Override
    public BallsInfo getBallInfo() {
        BallsInfo info = new BallsInfo();
//        SquareItemExpans squareItemExpans = squares[(int) selectedBall.x][(int) selectedBall.y];
//        SquareItem squareItem = squares
        info.color = squares[(int) selectedBall.x][(int) selectedBall.y].getBallColor();
//        squares[(int) selectedBall.x][(int) selectedBall.y].
        return info;
    }
}
