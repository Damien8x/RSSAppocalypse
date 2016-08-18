package itc298final.com.rssappocolypse;

public class Constants {

    final public static float DEFAULT_ANIM_SPEED = 255;

    final public static int MAX_LANDSCAPE = 2048;
    final public static int MAX_PORTRAIT = MAX_LANDSCAPE / 2;
    final public static int ALPHA = 224;
    final public static int DEFAULT_FRAME_LENGTH = 100;
    final public static int SUBTRACT_COLOR = 16;
    final public static int DEFAULT_BKG_COLOR = SUBTRACT_COLOR * 15;

    // [ MATH ] /////////////////////////////////
    final public static float PI = 3.1415926535897932384626433832795f;

    // [ RSS ] //////////////////////////////////
    final public static int NUMBER_OF_BUTTONS = 8;

    // [ SPRITES ] //////////////////////////////
    final public static float ASTEROID_ANIM_SPEED = 255;
    final public static float SCROLL_X_DEC = -0.50f;

    final public static int ASTEROID_DEFAULT_X_POSITION = -216;
    final public static int ASTEROID_DEFAULT_Y_POSITION = -256;
    final public static int ASTEROID_FRAMES = 29;
    final public static int ASTEROID_HEIGHT = 92;
    final public static int ASTEROID_WIDTH = 109;
    final public static int ASTEROID2_FRAMES = 5;

    final public static int LOGO1_DEFAULT_HOFFSET = 0x45;
    final public static int LOGO01_FRAMES = 1;
    final public static int LOGO01_HEIGHT = 96;
    final public static int LOGO01_WIDTH = 392;

    final public static int LOGO02_FRAMES = 1;
    final public static int LOGO02_HEIGHT = 96;
    final public static int LOGO02_WIDTH = 700;

    final public static int SCROLL_DELAY = 2;
    final public static int SCROLL_HW = 64;
    final public static int SCROLL_MAX_Y = 456;
    final public static int SCROLL_MIN_Y = 200;
    final public static int SCROLL_INC_DEC = 4;

    final public static int SPRITE01_DEFAULT_X_POSITION = -216;
    final public static int SPRITE01_DEFAULT_Y_POSITION = 0;
    final public static int SPRITE01_FRAMES = 2;
    final public static int SPRITE01_HEIGHT = 212;
    final public static int SPRITE01_WIDTH = 216;

    // [ STRINGS ] //////////////////////////////
    final public static String LANDSCAPE_BLANK = "                               ";
    final public static String PORTRAIT_BLANK = "                     ";
    final public static String TEXT =
            "scrumm master:    * marcus *    " +
            "programming:    * cory * damien *    " +
            "design:    * roman *    " +
            "scrolltext font:    * wildcop *    " +
            "asteroid graphics:    * damon czanik *    " +
            "^                         %";
    final public static String SAVED_RSS = "SAVED_RSS";
    final public static String SAVED_URL = "SAVED_URL";

    // [ URL ] //////////////////////////////////
    final public static String URL0 = "https://www.nasa.gov/rss/dyn/breaking_news.rss"; //breaking news
    final public static String URL1 = "https://www.nasa.gov/rss/dyn/educationnews.rss"; //education
    final public static String URL2 = "https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss"; //image of the day
    final public static String URL3 = "https://www.nasa.gov/rss/dyn/solar_system.rss"; // solar system and beyond
    final public static String URL4 = "https://www.nasa.gov/rss/dyn/mission_pages/kepler/news/kepler-newsandfeatures-RSS.rss"; //keplar mission
    final public static String URL5 = "https://www.nasa.gov/rss/dyn/chandra_images.rss"; //chandra mission
    final public static String URL6 = "https://www.nasa.gov/rss/dyn/solar_system.rss"; //solar system and beyond
    final public static String URL7 = "https://www.nasa.gov/rss/dyn/ames_news.rss"; //nasa reseach center

}