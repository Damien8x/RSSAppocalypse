package itc298final.com.rssappocolypse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RSSActivity extends Activity {

    private ImageButton[] buttons = new ImageButton[Constants.NUMBER_OF_BUTTONS];

    ///////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_rss );

        for( int i = 0; i < Constants.NUMBER_OF_BUTTONS; i++ ) {

            buttons[i] = ( ImageButton ) findViewById(
                    getResources().getIdentifier( "button" + i, "id",
                            getPackageName() ));
            buttons[i].setOnClickListener( ButtonClickListener );

        }

    }

    private View.OnClickListener ButtonClickListener = new View.OnClickListener() {
        ///////////////////////////////////////////////////////////////////////
        @Override
        public void onClick( View v ) {
        /*/////////////////////////////////////////////////////////////////////
                                                       Handle button clicks
        /////////////////////////////////////////////////////////////////////*/

            Intent intent = new Intent( RSSActivity.this, RSSListView.class );

            switch ( v.getId() ) {

                case R.id.button0:

                    intent.putExtra( Constants.SAVED_RSS, Constants.URL0 );
                    startActivity( intent );
                    break;

                case R.id.button1:

                    intent.putExtra( Constants.SAVED_RSS, Constants.URL1 );
                    startActivity( intent );
                    break;

                case R.id.button2:

                    intent.putExtra( Constants.SAVED_RSS, Constants.URL2 );
                    startActivity( intent );
                    break;

                case R.id.button3:

                    intent.putExtra( Constants.SAVED_RSS, Constants.URL3 );
                    startActivity( intent );
                    break;

                case R.id.button4:

                    intent.putExtra( Constants.SAVED_RSS, Constants.URL4 );
                    startActivity( intent );
                    break;

                case R.id.button5:

                    intent.putExtra( Constants.SAVED_RSS, Constants.URL5 );
                    startActivity( intent );
                    break;

                case R.id.button6:

                    intent.putExtra( Constants.SAVED_RSS, Constants.URL6 );
                    startActivity( intent );
                    break;

                case R.id.button7:

                    intent.putExtra( Constants.SAVED_RSS, Constants.URL7 );
                    startActivity( intent );
                    break;

            }

        }

    };

    ///////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        getMenuInflater().inflate( R.menu.rss_menu, menu );
        return true;

    }

    ///////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        switch ( item.getItemId() ) {

            /*case R.id.menu_settings:

                startActivity( new Intent( this, SettingsActivity.class ));
                return true;*/

            case R.id.menu_about:

                startActivity( new Intent( this, AboutActivity.class ));
                return true;

            default:

                return super.onOptionsItemSelected( item );

        }

    }

}