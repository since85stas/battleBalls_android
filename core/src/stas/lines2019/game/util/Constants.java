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
    public static float BALL_VELOCITY = 1f ;
    public static float DEFORMATION_VELOCITY = 0.9f;
    public static float DEFORMATION_RATIO = 0.2f;
    public static float UPPER_OFFSET = 0.1f;
    public static float NUMBER_RATIO = 0.4f;

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
    public static final String PREF_ACHIEV    = "preference_achieve";
    public static final String PREF_GAME      = "preference_game"     ;
    public static final String PREF_SURVIVE   = "survive_game"     ;
    public static final String PREF_TIME_PLAYED = "time_played"  ;
    public static final String PREF_TIME_PLAYED_FULL = "time_full"  ;
    public static final String PREF_ACHIEV_MASSIVE   = "achievements";
    public static final String PREF_GAME_IS_PLAY     = "game_is_play";
    public static final String PREF_GAME_MASSIVE     = "game_massive";
    public static final String PREF_SCORE            = "game_score";
    public static final String PREF_SCORE_FULL       = "game_score";
    public static final String PREF_TURNS            = "game_turns";
    public static final String PREF_HIGH_SCORE       = "high_score";
    public static final String PREF_SURV_EASY  = "easy_game"       ;
    public static final String PREF_SURV_NORM  = "normal_game"     ;
    public static final String PREF_SURV_HARD  = "hard_game"       ;
    public static final String PREF_SURV_NIGHT  = "night_game"     ;
    public static final String PREF_SURV_ENDL  = "ENDL_game"       ;

    // константы для размеров гаме HUD
    public static final float HUD_OFFSET = 0.05f;
    public static final float HUD_ITEM_VERT_SIZE = 0.1f;
    public static final float HUD_ITEM_HOR_SIZE = 0.3f;

    public static final float BUTTONS_HEIGHT = 0.07f;
    public static final float BUTTONS_WIDTH = 0.9f;
    public static final float TITLE_UPPER_OFFSET = 0.07f;
    public static final float BUTTONS_UPPER_OFFSET = 0.2f;
    public static final float BUTTONS_BETWEEN_SPACE = 0.03f;

    public static final float HUD_FONT_SMALL = 0.02f;
    public static final float HUD_FONT_IN_DIALOG = 0.03f;
    public static final float HUD_FONT_IN_GAME = 0.04f;
    public static final float HUD_FONT_MAIN_MENU = 0.04f;
    public static final float HUD_FONT_TITLE = 0.04f;
    public static final float HUD_FONT_HUGE = 0.09f;
    public static final float HUD_FONT_INBALLS = 1/9/3f;

    public static final float ACHIEVE_WIDTH = 0.9f;
    public static final float ACHIEVE_HEIGHT = 0.08f;

    // константы для шариков на основн экране
    public static int MENU_BALLS_INIT_NUMBERS = 30;
    public static int GAME_BALLS_INIT_NUMBERS = 10;
    public static int MENU_BALLS_INIT_ADD = 2;
    public static float MENU_BALLS_VEL_MIN = 100.f;
    public static float MENU_BALLS_VEL_MAX = 170.f;
    public static float MENU_BALLS_VEL_RANGE = 80.f;

    // констатны сложности уровней
    public static final int  NUM_DIFFICULTIES = 5;
    public static final int DIFFICULT_EASY  = -3;
    public static final int DIFFICULT_NORMAL  = -4;
    public static final int DIFFICULT_HARD  = -5;
    public static final int DIFFICULT_NIGHTMARE  = -6;
    public static final int DIFFICULT_ENDLESS  = -7;

    public static final String PREF_DIFFICULT_EASY  = "-3";
    public static final String PREF_DIFFICULT_NORMAL  = "-4";
    public static final String PREF_DIFFICULT_HARD  = "-5";
    public static final String PREF_DIFFICULT_NIGHTMARE  = "-6";
    public static final String PREF_DIFFICULT_ENDLESS  = "-7";

    public static final String STARS_NUMBER = "stars_number";

    // ball types
    public static final int  BALLS_TYPES_NUM = 2;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_TIGHT = 1;
    public static final int TYPE_FREEZE = 2;

    public static final float NORMAL_WEIGHT = 1.f;
    public static final float TIGHT_WEIGHT = 0.5f;
    public static final float FREEZE_WEIGHT = 0.1f;

    //
    public static final String SURV_GAME_IS_BOUGHT = "surv_buy";
    public static final String GAME_OPENS = "menu opens";

    public static final String APP_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyp3Sr3lWNIQc61" +
            "P40nAEKbjHpiF17CIM+m7gbqLih10sJiiC6Wg0ndR3chOS8dBZjxgkthZgSq54CaE6Wa0sPpKxAlsoIMuh5hxD" +
            "bhjzTI+VceeudZEZC+kheol9fVcJG+KgzAFxsLdQJf8mNV4HJ9hiO9Vo5VAEB1tZmI/J03uGpSdrdDzciPQcu" +
            "pYHM+AoNSytdmE1YC0Bl1KL2VapY+Ir6ziegQFVodj19LBx7ga8Lk2akECeYsnWLGslzQ67WkcWDpE0lBRRqe" +
            "Z0qo20Qe46Mo714o7aaviQnvQt+8d3gdNt4QOOIZqnFoP2VHJBBX1Fzzg90Ll8eW6CoH+ZNQIDAQAB";

    public static final String FRIEND_VERSION = "friend_edition";

}
