package stas.lines2019.game.results;

import stas.lines2019.game.LinesGame;

public class Achivement {

    private LinesGame game;
    private String  name;
    private String  description;
    private int     type;
    private int     cost;
    private int isComplete ;
    private int     crit;

    public Achivement(LinesGame game,String name, String description, int cost, int type, int crit) {
        this.game = game;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.type = type;
        this.crit = crit;
        this.isComplete = 0;
    }

    // геттеры
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int isComplete() {
        return isComplete;
    }
    public int getCrit() {
        return crit;
    }
    public int getType() {
        return type;
    }

    // сеттеры
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setComplete(int complete) {
        isComplete = complete;
        game.numberOfStars++;
        game.saveAchieve();
    }

    public void initCompleteAchievs(int complete) {
        isComplete = complete;
    }

    //

}
