package stas.lines2019.game.util;

public class Constants {

    public Constants () {

    }

    // константы экрана

    // ограничение на шаг по времени
    public static final float TIME_STEP = 0.01f;

    // константы для ячейки
    public static float BALL_SIZE_RATIO = 0.7f;
    public static float BALL_PREVIEW_RATIO = 0.4f;
    public static float BALL_VELOCITY = 40.f ;
    public static float DEFORMATION_VELOCITY = 30f;
    public static float DEFORMATION_RATIO = 0.2f;
    public static float UPPER_OFFSET = 0.1f;

    // константы для движения шарика
    public static float MOVE_VEL = 0.01f;

    // константы для игры
    public static float SCORED_PER_BALL = 1.f;
    public static float ACHIEVE_DRAW_TIME = 4;

    // направления прохода
    public final static int LEFT = 0;
    public final static int RIGHT = 1;
    public final static int UP = 2;
    public final static int DOWN = 3;

    // константы для сохр настроек
    public static final String PREF_ACHIEV = "preference_achieve";
    public static final String PREF_GAME   = "preference_game"     ;
    public static final String PREF_TIME_PLAYED = "time_played"  ;
    public static final String PREF_TIME_PLAYED_FULL = "time_full"  ;
    public static final String PREF_ACHIEV_MASSIVE = "achievements";
    public static final String PREF_GAME_IS_PLAY   = "game_is_play";
    public static final String PREF_GAME_MASSIVE   = "game_massive";
    public static final String PREF_SCORE   = "game_score";
    public static final String PREF_SCORE_FULL   = "game_score";
    public static final String PREF_TURNS   = "game_turns";

    // константы для размеров гаме HUD
    public static final float HUD_OFFSET = 0.05f;
    public static final float HUD_ITEM_VERT_SIZE = 0.1f;
    public static final float HUD_ITEM_HOR_SIZE = 0.3f;

    public static final float BUTTONS_HEIGHT = 0.1f;
    public static final float BUTTONS_WIDTH = 0.7f;
    public static final float TITLE_UPPER_OFFSET = 0.1f;
    public static final float BUTTONS_UPPER_OFFSET = 0.3f;
    public static final float BUTTONS_BETWEEN_SPACE = 0.05f;

    public static final float HUD_FONT_SMALL = 0.02f;
    public static final float HUD_FONT_IN_DIALOG = 0.03f;
    public static final float HUD_FONT_IN_GAME = 0.04f;
    public static final float HUD_FONT_TITLE = 0.04f;
    public static final float HUD_FONT_HUGE = 0.08f;

    // константы для шариков на основн экране
    public static int MENU_BALLS_INIT_NUMBERS = 30;
    public static int MENU_BALLS_INIT_ADD = 2;
    public static float MENU_BALLS_VEL_MIN = 50.f;
    public static float MENU_BALLS_VEL_MAX = 90.f;
    public static float MENU_BALLS_VEL_RANGE = 50.f;
}
