package stas.lines2019.game.results;

import com.badlogic.gdx.Gdx;
import stas.lines2019.game.LinesGame;
import stas.lines2019.game.util.Constants;
import stas.lines2019.game.util.ConstantsAchiveEng;

public class AchivementsList {

    private static String TAG = AchivementsList.class.getName();

    private LinesGame game;

    public static Achivement[] achivements;

//    public static AchivementsList achivementsInstance = new AchivementsList();

    public AchivementsList(LinesGame game) {
        this.game = game;
    }

    public boolean generateAchivemnets() {
        boolean isOk = true;

        achivements = new Achivement[ConstantsAchiveEng.NUM_ACHIVEMENTS];

        int a1 = ConstantsAchiveEng.achievmentsName.length;
        int a2 = ConstantsAchiveEng.achievementsDescr.length;
        int a3 = ConstantsAchiveEng.achivementsCost.length;
        int a4 = ConstantsAchiveEng.achivementsType.length;
        int a5 = ConstantsAchiveEng.achivementsCriteria.length;

//        if ( a1 == a2 == a3 == a4 == a5  )
        for (int i = 0; i < achivements.length ; i++) {
            try {
                achivements[i] = new Achivement(
                        game,
                        ConstantsAchiveEng.achievmentsName[i],
                        ConstantsAchiveEng.achievementsDescr[i],
                        ConstantsAchiveEng.achivementsCost[i],
                        ConstantsAchiveEng.achivementsType[i],
                        ConstantsAchiveEng.achivementsCriteria[i]
                );
            } catch (Exception e) {
                Gdx.app.log(TAG,"Achivm except ",e);
            }
        }

        return isOk;
    }

    public boolean checkAchivements() {
        boolean achivementComplete = false;

        for (int i = 0; i < achivements.length; i++) {
            switch (achivements[i].getType()) {
                case ConstantsAchiveEng.TYPE_SCORE:
                    int score = game.getGameScreen().gameField.getGameScore();
                    if (score > achivements[i].getCrit() &&
                            achivements[i].isComplete() != 1)  {
                        achivements[i].setComplete(1);
                        achivementComplete = true;
                    }
                    break;
                case  ConstantsAchiveEng.TYPE_TIME_SINGLE:
                    if (game.getGameScreen().gameField.gameTime > achivements[i].getCrit() &&
                            achivements[i].isComplete() != 1 )  {
                        achivements[i].setComplete(1);
                        achivementComplete = true;
                    }
                    break;
                case  ConstantsAchiveEng.TYPE_TIME_OVERALL:
                    if (game.getGameScreen().gameField.gameTimeFullOld > achivements[i].getCrit() &&
                            achivements[i].isComplete() != 1)  {
                        achivements[i].setComplete(1);
                        achivementComplete = true;
                    }
                    break;
                case ConstantsAchiveEng.TYPE_SCORE_OVERALL:
                    int scoreFull = game.getGameScreen().gameField.getGameScoreFullOld();
                    if (scoreFull > achivements[i].getCrit() &&
                            achivements[i].isComplete() != 1)  {
                        achivements[i].setComplete(1);
                        achivementComplete = true;
                    }
                    break;
                case  ConstantsAchiveEng.TYPE_TURNS:
                    if (game.getGameScreen().gameField.getNumberOfTurns() > achivements[i].getCrit() &&
                            achivements[i].isComplete() != 1)  {
                        achivements[i].setComplete(1);
                        achivementComplete = true;
                    }
                    break;
                case  ConstantsAchiveEng.TYPE_LINES_SIZE:
                    if (game.getGameScreen().gameField.getLineLong() > achivements[i].getCrit() -1 &&
                            achivements[i].isComplete() != 1)  {
                        achivements[i].setComplete(1);
                        achivementComplete = true;
                    }
                    break;
            }
        }

        return achivementComplete;
    }

    public void setAchieveSurv(int survDiff) {
        for (int i = 0; i < achivements.length ; i++) {
            if (achivements[i].getType() == ConstantsAchiveEng.TYPE_SURVIVE && achivements[i].isComplete() != 1) {
                if (achivements[i].getCrit() == survDiff && achivements[i].isComplete() != 1) {
                    achivements[i].setComplete(1);

                }
            }
        }
    }

    public int[] getAchievCompArray() {
        int[] array = new int[achivements.length];
        for (int i = 0; i < achivements.length; i++) {
            array[i] = achivements[i].isComplete();
        }
        return array;
    }

    public int getCompleteAchievsNumber() {
        int array =0;
        for (int i = 0; i < achivements.length; i++) {
            if (achivements[i].isComplete() == 1) {
                array++;
            }
        }
        return array;
    }

    public String[] getAchievDescrArray() {
        String[] array = new String[achivements.length];
        for (int i = 0; i < achivements.length; i++) {
            array[i] = achivements[i].getDescription();
        }
        return array;
    }

    public  Achivement[] getAchivements() {
        return achivements;
    }

}
