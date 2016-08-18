package itc298final.com.rssappocolypse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class RSSWebView extends Activity {

    private String rssString;
    private String urlString;
    private WebView wv;

    ///////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_web );
        wv = (WebView) findViewById( R.id.webView );
    /*/////////////////////////////////////////////////////////////////////
                                Pass URL via extras or saved instance state
    /////////////////////////////////////////////////////////////////////*/

        if ( savedInstanceState == null ) {

            Bundle extras = getIntent().getExtras();

            if (extras == null) {

                rssString = null;
                urlString = null;

            } else {

                rssString = extras.getString(Constants.SAVED_RSS);
                urlString = extras.getString(Constants.SAVED_URL);

            }

        }

        if ( urlString != null ) {

            wv.loadUrl( urlString );

        } else {

            // Oh, no! Something went wrong.
            Toast.makeText( getApplicationContext(), R.string.empty_url, Toast.LENGTH_LONG ).show();
            Intent intent = new Intent( this, RSSActivity.class );
            startActivity( intent );

        }

        onDestroy();

    }

    @Override
    protected void onDestroy() {

        Intent intent = new Intent ( this, RSSListView.class );
        intent.putExtra( Constants.SAVED_RSS, rssString );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );
        super.onDestroy();

    }

}