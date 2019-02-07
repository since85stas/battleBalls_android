package stas.lines2019.game.results;

public class Achivement {

    private String  name;
    private String  description;
    private int     type;
    private int     cost;
    private int isComplete ;
    private int     crit;

    public Achivement(String name, String description, int cost, int type, int crit) {
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
    }

    //

}
