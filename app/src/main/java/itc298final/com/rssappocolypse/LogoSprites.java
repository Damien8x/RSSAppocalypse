package itc298final.com.rssappocolypse;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class LogoSprites extends SurfaceView implements Runnable {

    ///////////////////////////////////////////////////////////////////////
    private Canvas canvas;
    private Context sprContext;
    private Paint paint;
    private Sprite[] scrollText;
    private String tmpChar;
    private String tmpString;
    private SurfaceHolder surface;
    private Thread logoThread = null;
    ///////////////////////////////////////////////////////////////////////
    private boolean increaseY  = true;
    private boolean moveScroll = true;
    private boolean moveSprite = true;
    private boolean scrollDrop = false;
    ///////////////////////////////////////////////////////////////////////
    private char[] charArray;
    ///////////////////////////////////////////////////////////////////////
    private enum Flash { BLUE, RED, GREY }
    private Flash colorMode;
    ///////////////////////////////////////////////////////////////////////
    private float logo01BaseXPosition;
    private float logo01BaseYPosition;
    private float logo02BaseXPosition;
    private float logo02BaseYPosition;
    private float maxSprite01XPosition = Constants.MAX_PORTRAIT;
    private float maxAsteroidXPosition = Constants.MAX_PORTRAIT;
    private float maxAsteroidYPosition = Constants.MAX_LANDSCAPE;
    private float maxX;
    private float scrollCurY = 384;
    ///////////////////////////////////////////////////////////////////////
    private int bkgB = Constants.DEFAULT_BKG_COLOR;
    private int bkgG = Constants.DEFAULT_BKG_COLOR;
    private int bkgR = Constants.DEFAULT_BKG_COLOR;
    private int frameLength = Constants.DEFAULT_FRAME_LENGTH;
    private int logo01HorizontalOffset;
    private int logo01VerticalOffset;
    private int logo02HorizontalOffset;
    private int logo02VerticalOffset;
    private int numberOfSprites;
    private int scrollCount = 0;
    private int textPosition = 0;
    ///////////////////////////////////////////////////////////////////////
    private int[] hSine = {
            128,131,134,137,140,143,146,149,152,155,158,162,165,167,170,
            173,176,179,182,185,188,190,193,196,198,201,203,206,208,211,
            213,215,218,220,222,224,226,228,230,232,234,235,237,238,240,
            241,243,244,245,246,248,249,250,250,251,252,253,253,254,254,
            254,255,255,255,255,255,255,255,254,254,254,253,253,252,251,
            250,250,249,248,246,245,244,243,241,240,238,237,235,234,232,
            230,228,226,224,222,220,218,215,213,211,208,206,203,201,198,
            196,193,190,188,185,182,179,176,173,170,167,165,162,158,155,
            152,149,146,143,140,137,134,131,128,124,121,118,115,112,109,
            106,103,100,97,93,90,88,85,82,79,76,73,70,67,65,62,59,57,54,
            52,49,47,44,42,40,37,35,33,31,29,27,25,23,21,20,18,17,15,14,
            12,11,10,9,7,6,5,5,4,3,2,2,1,1,1,0,0,0,0,0,0,0,1,1,1,2,2,3,
            4,5,5,6,7,9,10,11,12,14,15,17,18,20,21,23,25,27,29,31,33,35,
            37,40,42,44,47,49,52,54,57,59,62,65,67,70,73,76,79,82,85,88,
            90,93,97,100,103,106,109,112,115,118,121,124 };
    ///////////////////////////////////////////////////////////////////////
    private int[] vSine = {
            64,66,67,69,70,72,73,75,76,78,80,81,83,84,86,87,88,90,91,93,
            94,96,97,98,100,101,102,103,105,106,107,108,109,110,111,112,
            113,114,115,116,117,118,119,120,120,121,122,123,123,124,124,
            125,125,126,126,126,127,127,127,128,128,128,128,128,128,128,
            128,128,128,128,127,127,127,126,126,126,125,125,124,124,123,
            123,122,121,120,120,119,118,117,116,115,114,113,112,111,110,
            109,108,107,106,105,103,102,101,100,98,97,96,94,93,91,90,88,
            87,86,84,83,81,80,78,76,75,73,72,70,69,67,66,64,62,61,59,58,
            56,55,53,52,50,48,47,45,44,42,41,40,38,37,35,34,32,31,30,28,
            27,26,25,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,8,7,
            6,5,5,4,4,3,3,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,
            3,3,4,4,5,5,6,7,8,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,
            23,25,26,27,28,30,31,32,34,35,37,38,40,41,42,44,45,47,48,50,
            52,53,55,56,58,59,61,62 };
    ///////////////////////////////////////////////////////////////////////
    private long fps;
    private long lastFrameChangeTime = 0;
    private long currentFrameTime;
    ///////////////////////////////////////////////////////////////////////
    volatile boolean active;
    ///////////////////////////////////////////////////////////////////////

    private Sprite rss = new Sprite( this.getResources(), R.drawable.sprite_rss,
            Constants.LOGO01_FRAMES, 0, Constants.DEFAULT_ANIM_SPEED,
            0, 0, Constants.LOGO01_WIDTH, Constants.LOGO01_HEIGHT );

    private Sprite appocolypse = new Sprite( this.getResources(), R.drawable.sprite_appocalypse,
            Constants.LOGO02_FRAMES, 0, Constants.DEFAULT_ANIM_SPEED,
            0, 0, Constants.LOGO02_WIDTH, Constants.LOGO02_HEIGHT );

    private Sprite sprite01 = new Sprite( this.getResources(), R.drawable.sprite_demon,
            Constants.SPRITE01_FRAMES, 0, Constants.DEFAULT_ANIM_SPEED,
            Constants.SPRITE01_DEFAULT_X_POSITION, Constants.SPRITE01_DEFAULT_Y_POSITION,
            Constants.SPRITE01_WIDTH, Constants.SPRITE01_HEIGHT );

    private Sprite asteroid = new Sprite( this.getResources(), R.drawable.sprite_asteroid,
            Constants.ASTEROID_FRAMES, 0, Constants.ASTEROID_ANIM_SPEED,
            Constants.ASTEROID_DEFAULT_X_POSITION, Constants.ASTEROID_DEFAULT_Y_POSITION,
            Constants.ASTEROID_WIDTH, Constants.ASTEROID_HEIGHT );

    private Sprite brokenAsteroid = new Sprite( this.getResources(), R.drawable.sprite_asteroid_destroyed,
            Constants.ASTEROID2_FRAMES, 0, Constants.ASTEROID_ANIM_SPEED,
            Constants.ASTEROID_DEFAULT_X_POSITION, Constants.ASTEROID_DEFAULT_Y_POSITION,
            Constants.ASTEROID_WIDTH, Constants.ASTEROID_HEIGHT );

    ///////////////////////////////////////////////////////////////////////
    public LogoSprites( Context context ) {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        super( context );
        logo01HorizontalOffset = Constants.LOGO1_DEFAULT_HOFFSET;
        //numberOfSprites = ( numberOfSprites < 19 ) ? 19 : numberOfSprites;
        paint = new Paint();
        sprContext = context;
        surface = getHolder();

        // Set initial X and Y position for logo
        moveLogo();
        setupScrolltext();

        sprite01.activate();
        rss.activate();
        appocolypse.activate();
        asteroid.activate();

    }

    ///////////////////////////////////////////////////////////////////////
    public void colourFlash() {
    /*/////////////////////////////////////////////////////////////////////
                                                Stupid colour flash of doom
    /////////////////////////////////////////////////////////////////////*/

        if( colorMode == Flash.BLUE ) {

            bkgB = (( bkgB > 0 ) && ( bkgG < 140 )) ? ( bkgB - Constants.SUBTRACT_COLOR ) : bkgB;
            bkgG = ( bkgG != 0 ) ? ( bkgG - Constants.SUBTRACT_COLOR ) : bkgG;
            bkgR = ( bkgR != 0 ) ? ( bkgR - Constants.SUBTRACT_COLOR ) : bkgR;

        } else if( colorMode == Flash.RED ) {

            bkgB = ( bkgB != 0 ) ? ( bkgB - Constants.SUBTRACT_COLOR ) : bkgB;
            bkgG = ( bkgG != 0 ) ? ( bkgG - Constants.SUBTRACT_COLOR ) : bkgG;
            bkgR = (( bkgR > 0 ) && ( bkgG < 140 )) ? ( bkgR - Constants.SUBTRACT_COLOR ) : bkgR;

        } else if( colorMode == Flash.GREY ) {

            bkgB = ( bkgB != 0 ) ? ( bkgB - Constants.SUBTRACT_COLOR ) : bkgB;
            bkgG = ( bkgG != 0 ) ? ( bkgG - Constants.SUBTRACT_COLOR ) : bkgG;
            bkgR = ( bkgR != 0 ) ? ( bkgR - Constants.SUBTRACT_COLOR ) : bkgR;

        }

    }

    ///////////////////////////////////////////////////////////////////////
    public void drawFrame() {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        if( surface.getSurface().isValid() ) {

            // Process effect
            colourFlash();

            // Lock canvas for drawing
            canvas = surface.lockCanvas();
            canvas.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR );

            // Set background colour
            canvas.drawColor( Color.argb( Constants.ALPHA, bkgR, bkgG, bkgB ));

            // Set logo and sprite positions
            rss.setPosition();
            appocolypse.setPosition();
            sprite01.setPosition();

            for( int i = 0; i < numberOfSprites; i++ ) { scrollText[i].setPosition(); }

            if( asteroid.isActive() ) {

                asteroid.setPosition();

            } else if( brokenAsteroid.isActive() ) {

                brokenAsteroid.setPosition();

                if ( brokenAsteroid.getCurrentFrame() == 0 ) { asteroid.setPosition(); }

            }

            getCurrentFrame();

            canvas.drawBitmap( appocolypse.getBitmap(), appocolypse.getFrame(), appocolypse.getPosition(), paint );
            canvas.drawBitmap( rss.getBitmap(), rss.getFrame(), rss.getPosition(), paint );
            canvas.drawBitmap( sprite01.getBitmap(), sprite01.getFrame(), sprite01.getPosition(), paint );

            for( int i = 0; i < numberOfSprites; i++ ) { canvas.drawBitmap( scrollText[i].getBitmap(), scrollText[i].getFrame(), scrollText[i].getPosition(), paint ); }

            if( asteroid.isActive() ) {

                canvas.drawBitmap( asteroid.getBitmap(), asteroid.getFrame(), asteroid.getPosition(), paint );

            } else if( brokenAsteroid.isActive() ) {

                canvas.drawBitmap( brokenAsteroid.getBitmap(), brokenAsteroid.getFrame(), brokenAsteroid.getPosition(), paint );

                if ( brokenAsteroid.getCurrentFrame() == 0 ) {

                    // draw original asteroid sprite off screen
                    canvas.drawBitmap( asteroid.getBitmap(), asteroid.getFrame(), asteroid.getPosition(), paint );

                }

            }

            // Unlock canvas and post update
            surface.unlockCanvasAndPost( canvas );

        }

    }

    ///////////////////////////////////////////////////////////////////////
    public void endScroll() {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        asteroid.deactivate();
        brokenAsteroid.setX( asteroid.getX() );
        brokenAsteroid.setY( asteroid.getY() );
        brokenAsteroid.resetFrame();
        brokenAsteroid.setFrame();
        asteroid.setX( maxX );
        brokenAsteroid.activate();

        bkgB = Constants.DEFAULT_BKG_COLOR;
        bkgG = Constants.DEFAULT_BKG_COLOR;
        bkgR = Constants.DEFAULT_BKG_COLOR;

        colorMode = ( colorMode == Flash.BLUE ) ? Flash.GREY : Flash.BLUE;
        scrollDrop = true;

    }
    ///////////////////////////////////////////////////////////////////////
    public char getCurrentChar() {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        charArray = tmpString.toCharArray();

        if( charArray[textPosition] == '^' ) { endScroll(); }
        if( charArray[textPosition] == '%' ) { goodbye(); }

        char result = charArray[textPosition];
        textPosition++;
        return result;

    }

    ///////////////////////////////////////////////////////////////////////
    public void getCurrentFrame(){
    /*/////////////////////////////////////////////////////////////////////
                                                Animate if sprite is moving
    /////////////////////////////////////////////////////////////////////*/

        long time = System.currentTimeMillis();

        if( moveSprite ) {

            if( time > lastFrameChangeTime + frameLength ) {

                lastFrameChangeTime = time;

                if( sprite01.isActive() ) { sprite01.increaseFrame(); }

                if( brokenAsteroid.getCurrentFrame() == Constants.ASTEROID2_FRAMES-1 ) {

                    // disable broken asteroid and reset original asteroid sprite
                    brokenAsteroid.deactivate();
                    brokenAsteroid.resetFrame();
                    asteroid.hasXPositionBeenExceeded( maxAsteroidXPosition, Constants.ASTEROID_DEFAULT_X_POSITION, fps );
                    asteroid.setY( brokenAsteroid.getY() );
                    asteroid.resetFrame();
                    asteroid.setFrame();

                }

                if( brokenAsteroid.isActive() ) {

                    brokenAsteroid.setY( brokenAsteroid.getY()-1 );
                    brokenAsteroid.increaseFrame();
                    brokenAsteroid.setFrame();

                } else {

                    asteroid.setY( asteroid.getY()-1 );
                    asteroid.increaseFrame();
                    asteroid.setFrame();

                }

            }

        }

        sprite01.setFrame();

    }

    ///////////////////////////////////////////////////////////////////////
    public void goodbye() {
    /*/////////////////////////////////////////////////////////////////////
                                       "Fuck this, let's go do some crimes"
    /////////////////////////////////////////////////////////////////////*/

        synchronized ( surface ) { ((Activity) sprContext ).finish(); }

    }

    ///////////////////////////////////////////////////////////////////////
    public void moveLogo() {
    /*/////////////////////////////////////////////////////////////////////
                                               Change logo X and Y position
     TODO: calculate the position instead of relying on pre-calculated list
    /////////////////////////////////////////////////////////////////////*/

        rss.setX( logo01BaseXPosition + ( hSine[logo01HorizontalOffset] * 2 ));
        rss.setY( logo01BaseYPosition + ( vSine[logo01VerticalOffset] * 4 ));
        appocolypse.setX( logo02BaseXPosition + ( hSine[logo02HorizontalOffset] * 2 ));
        appocolypse.setY( logo02BaseYPosition + ( vSine[logo02VerticalOffset] * 4 ));

        // Increase offset
        logo01HorizontalOffset++;
        logo01VerticalOffset++;
        logo02HorizontalOffset++;
        logo02VerticalOffset++;

        // Really? No unsigned 8-bit integer? Really?! C'mon! :p
        // If offset > 255, reset to 0
        logo01HorizontalOffset = ( logo01HorizontalOffset > 255 ) ? 0 : logo01HorizontalOffset;
        logo01VerticalOffset = ( logo01VerticalOffset > 255 ) ? 0 : logo01VerticalOffset;
        logo02HorizontalOffset = ( logo02HorizontalOffset > 255 ) ? 0 : logo02HorizontalOffset;
        logo02VerticalOffset = ( logo02VerticalOffset > 255 ) ? 0 : logo02VerticalOffset;

    }

    ///////////////////////////////////////////////////////////////////////
    public void moveScrollText() {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        scrollCount++;

        if( scrollCount == Constants.SCROLL_DELAY ) {

            for ( int i = 0; i < numberOfSprites; i++ ) {

                scrollText[i].setupFont( this.getResources(), getCurrentChar() );
                float testX = scrollText[i].getX();

                if( testX == ( -Constants.SCROLL_HW*2 )) {

                    scrollText[i].setY( numberOfSprites*Constants.SCROLL_HW );
                    tmpChar = tmpString.substring(0,1);
                    tmpString = tmpString.substring(1)+tmpChar;

                } else { scrollText[i].setX( scrollText[i].getX()-Constants.SCROLL_X_DEC ); }

                scrollText[i].setY( setScrollTextY() );

            }

            scrollCount = 0;
            textPosition = 0;

        }

    }

    ///////////////////////////////////////////////////////////////////////
    public void pause() {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        active = false;

        try { logoThread.join(); }
        catch ( InterruptedException e ) { Log.e( getResources().getString(R.string.error), getResources().getString(R.string.thread) ); }

    }

    ///////////////////////////////////////////////////////////////////////
    public void resume( Context context ) {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        ScreenOrientation scr = new ScreenOrientation( context );

        if( scr.getOrientation() == true ) {

            tmpString = Constants.LANDSCAPE_BLANK + Constants.TEXT;

        } else {

            tmpString = Constants.PORTRAIT_BLANK + Constants.TEXT;

        }

        // detremine number of sprites to generate
        numberOfSprites = ( scr.getScreenWidth()/Constants.SCROLL_HW )+3;

        if( scrollText.length != numberOfSprites ) { setupScrolltext(); }

        // Set logo base position based on screen width and height
        logo01BaseXPosition = ( scr.getScreenWidth()/2 ) - Constants.LOGO01_WIDTH+64;
        logo01BaseYPosition = ( scr.getScreenHeight()/2 ) - ( Constants.LOGO01_HEIGHT*2 )-256;
        logo02BaseXPosition = ( scr.getScreenWidth()/2 ) - Constants.LOGO02_WIDTH+32;
        logo02BaseYPosition = logo01BaseYPosition+64;

        maxAsteroidXPosition = scr.getScreenWidth()+Constants.ASTEROID_WIDTH;
        maxAsteroidYPosition = scr.getScreenHeight()+Constants.ASTEROID_HEIGHT;
        maxX = scr.getScreenWidth()+Constants.ASTEROID_WIDTH;
        asteroid.setY(( scr.getScreenHeight()+Constants.ASTEROID_WIDTH )/2 );
        brokenAsteroid.setY(( scr.getScreenHeight()+Constants.ASTEROID_WIDTH )/2 );

        // Set sprite position
        maxSprite01XPosition = scr.getScreenWidth() + Constants.SPRITE01_WIDTH;
        sprite01.setY(( scr.getScreenHeight() + Constants.SPRITE01_WIDTH )/2 );

        // Red flash of doom!!!
        colorMode = Flash.RED;

        // Make it so!
        active = true;
        moveSprite = true;
        logoThread = new Thread( this );
        logoThread.start();

    }

    ///////////////////////////////////////////////////////////////////////
    public float setScrollTextY() {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        if( scrollDrop == false ) {

            if ( increaseY == true ) {

                increaseY = ( scrollCurY == Constants.SCROLL_MAX_Y ) ? false : true;
                scrollCurY += Constants.SCROLL_INC_DEC;

            } else {

                increaseY = ( scrollCurY == Constants.SCROLL_MIN_Y ) ? true : false;
                scrollCurY -= Constants.SCROLL_INC_DEC;

            }

        } else if ( scrollDrop == true ){ scrollCurY +=  Constants.SCROLL_INC_DEC; }

        return scrollCurY;

    }

    ///////////////////////////////////////////////////////////////////////
    public void setupScrolltext() {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        scrollDrop = false;
        scrollText = new Sprite[numberOfSprites];
        textPosition = 0;

        // Create scrolltext sprites and set X position
        for( int i = 0; i < numberOfSprites; i++ ) {

            scrollText[i] = new Sprite();
            scrollText[i].setX(( Constants.SCROLL_HW*i )-( Constants.SCROLL_HW*2 ));

        }

        // Set initial X and Y position for scrolltext
        moveScrollText();

    }

    ///////////////////////////////////////////////////////////////////////
    public void updateFrame() {
    /*/////////////////////////////////////////////////////////////////////
              Move logo and reset sprite position if the maximum horizontal
           position has been exceeded, otherwise continue moving the sprite
    /////////////////////////////////////////////////////////////////////*/

        if( moveSprite ) {

            if( moveScroll ) { moveScrollText(); }

            if( rss.isActive() ) { moveLogo(); moveLogo(); moveLogo(); }

            if( sprite01.isActive() ) {

                if(( scrollDrop == true ) && ( sprite01.getX() > maxSprite01XPosition )) { sprite01.deactivate(); }

                sprite01.hasXPositionBeenExceeded( maxSprite01XPosition, Constants.SPRITE01_DEFAULT_X_POSITION, fps );

            }

            if( brokenAsteroid.isActive() ) {

                brokenAsteroid.hasXPositionBeenExceeded( maxAsteroidXPosition, Constants.ASTEROID_DEFAULT_X_POSITION, fps );
                brokenAsteroid.hasYPositionBeenExceeded( maxAsteroidYPosition, Constants.ASTEROID_DEFAULT_Y_POSITION, fps );

            } else {

                asteroid.hasXPositionBeenExceeded( maxAsteroidXPosition, Constants.ASTEROID_DEFAULT_X_POSITION, fps );
                asteroid.hasYPositionBeenExceeded( maxAsteroidYPosition, Constants.ASTEROID_DEFAULT_Y_POSITION, fps );

            }

        }

    }

    ///////////////////////////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent( MotionEvent motionEvent ) {
    /*/////////////////////////////////////////////////////////////////////
                                                 Stupid screen flash effect
    /////////////////////////////////////////////////////////////////////*/

        switch ( motionEvent.getAction() & MotionEvent.ACTION_MASK ) {

            case MotionEvent.ACTION_DOWN:

                sprite01.deactivate();
                endScroll();

                break;

            case MotionEvent.ACTION_UP:

                rss.deactivate();
                appocolypse.deactivate();
                brokenAsteroid.deactivate();

                goodbye();

                break;

        }

        return true;

    }

    ///////////////////////////////////////////////////////////////////////
    @Override
    public void run() {
    /*/////////////////////////////////////////////////////////////////////
                                                              Run this shit
    /////////////////////////////////////////////////////////////////////*/

        while( active ) {

            long startFrameTime = System.currentTimeMillis();

            updateFrame();
            drawFrame();

            currentFrameTime = System.currentTimeMillis() - startFrameTime;

            if( currentFrameTime >= 1 ) { fps = 1000 / currentFrameTime; }

        }

    }

}