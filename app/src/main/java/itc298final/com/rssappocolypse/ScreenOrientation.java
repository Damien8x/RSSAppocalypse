package itc298final.com.rssappocolypse;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class ScreenOrientation {

    private int screenHeight;
    private int screenWidth;
    private enum Mode { PORTRAIT, LANDSCAPE }
    private Mode orientation;

    public ScreenOrientation( Context context ) {

        WindowManager wm = ( WindowManager )
                context.getSystemService( Context.WINDOW_SERVICE );
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize( size );

        // Determine orientation
        orientation = ( size.x > size.y ) ? Mode.LANDSCAPE : Mode.PORTRAIT;
        String str = ( size.x > size.y ) ? context.getResources().getString( R.string.landscape ) : context.getResources().getString( R.string.portrait ) ;
        screenHeight = size.y;
        screenWidth = size.x;

        Log.d( context.getResources().getString( R.string.resolution ),
                Integer.toString(screenWidth) + "x" + Integer.toString(screenHeight) +
                " " + str);

    }

    public int getScreenHeight() { return screenHeight; }

    public int getScreenWidth() { return screenWidth; }

    public boolean getOrientation() {

        if( orientation == Mode.LANDSCAPE ) { return true; } else { return false; }

    }

}