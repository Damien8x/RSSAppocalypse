package itc298final.com.rssappocolypse;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;

public class Sprite {

    private Bitmap sprite;
    private Rect rect;
    private RectF rectf;

    private boolean active = false;

    private float animationSpeed = 0;
    private float x = 0;
    private float y = 0;

    private int animationFrames = 0;
    private int currentFrame = 0;
    private int height = 0;
    private int width = 0;

    public Sprite() { }

    ///////////////////////////////////////////////////////////////////////
    public Sprite( Resources res, int id, int animationFrames, int currentFrame,
                   float animationSpeed, float x, float y, int width, int height ) {
    /*/////////////////////////////////////////////////////////////////////
                                                   Create  and scale sprite
                                              TODO: add angle, acceleration
    /////////////////////////////////////////////////////////////////////*/
        this.animationFrames = animationFrames;
        this.animationSpeed = animationSpeed;
        this.currentFrame = currentFrame;
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;

        sprite = BitmapFactory.decodeResource( res, id );
        sprite = Bitmap.createScaledBitmap( sprite, width * animationFrames, height, false );
        rect = new Rect( 0, 0, width, height );
        rectf = new RectF( x, y, x + width, y + height );

    }

    public void setupFont( Resources res, char chr ) {

        active = true;
        animationFrames = 30;
        animationSpeed = 0;
        currentFrame = 0;
        height = 64;
        width = 64;

        sprite = BitmapFactory.decodeResource( res, R.drawable.spr_sp );

        switch ( chr ) {

            case 'a':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_a );
                break;

            case 'b':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_b );
                break;

            case 'c':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_c );
                break;

            case 'd':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_d );
                break;

            case 'e':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_e );
                break;

            case 'f':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_f );
                break;

            case 'g':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_g );
                break;

            case 'h':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_h );
                break;

            case 'i':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_i );
                break;

            case 'j':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_j );
                break;

            case 'k':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_k );
                break;

            case 'l':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_l );
                break;

            case 'm':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_m );
                break;

            case 'n':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_n );
                break;

            case 'o':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_o );
                break;

            case 'p':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_p );
                break;

            case 'q':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_q );
                break;

            case 'r':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_r );
                break;

            case 's':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_s );
                break;

            case 't':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_t );
                break;

            case 'u':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_u );
                break;

            case 'v':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_v );
                break;

            case 'w':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_w );
                break;

            case 'x':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_x );
                break;

            case 'y':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_y );
                break;

            case 'z':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_z );
                break;

            case '!':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_ex );
                break;

            case '.':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_fs );
                break;

            case ',':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_cm );
                break;

            case ':':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_co );
                break;

            case '*':
                sprite = BitmapFactory.decodeResource( res, R.drawable.spr_moon );
                break;

        }

        sprite = Bitmap.createScaledBitmap( sprite, width, height, false );

        rect = new Rect( 0, 0, width, height );
        rectf = new RectF( x, y, x + width, y + height );

    }

    public Bitmap getBitmap() { return sprite; }

    public int getCurrentFrame() { return currentFrame; }

    public Rect getFrame() { return rect; }

    public void setFrame() {

        rect.left = currentFrame*width;
        rect.right = ( currentFrame*width ) + width;

    }

    public RectF getPosition() { return rectf; }

    public void setPosition() {

        rectf.set( (int) x, (int) y, (int) x + width, (int) y + height );

    }

    public float getX() { return x; }

    public void setX(float x) { this.x = x; }

    public float getY() { return y; }

    public void setY(float y) { this.y = y; }

    public void activate() { active = true; }

    public void deactivate() { active = false; }

    public boolean isActive() { return active; }

    ///////////////////////////////////////////////////////////////////////
    public void hasXPositionBeenExceeded( float max, int defaultX, long fps ) {
    /*/////////////////////////////////////////////////////////////////////
                 Check to see if Sprite has exceeded maximum X position and
                                  reset to the default X position if it has
    /////////////////////////////////////////////////////////////////////*/

        x = ( x > max ) ? defaultX : x + ( animationSpeed / fps );

    }

    ///////////////////////////////////////////////////////////////////////
    public void hasYPositionBeenExceeded( float max, int defaultY, long fps ) {
    /*/////////////////////////////////////////////////////////////////////
                 Check to see if Sprite has exceeded maximum X position and
                                  reset to the default X position if it has
    /////////////////////////////////////////////////////////////////////*/

        y = ( y > max ) ? defaultY : y + ( animationSpeed / fps );

    }

    public void increaseFrame() {

        currentFrame++;
        currentFrame = ( currentFrame >= animationFrames ) ? 0 : currentFrame;

    }

    public void resetFrame() { currentFrame = 0; }

}