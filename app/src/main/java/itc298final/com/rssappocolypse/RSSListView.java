package itc298final.com.rssappocolypse;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RSSListView extends Activity {

    ListView listView;
    AppocolypseAdapter adapter;

    public ArrayList<ListModel> articleList = new ArrayList<>();

    private ArrayList tempTitle = new ArrayList();
    private ArrayList tempDescription = new ArrayList();
    private ArrayList tempURL = new ArrayList();

    private String rssString = null;
    private TextView feedTitleTextView;
    private XmlPullParserFactory xmlFactoryObject;

    public volatile boolean parsingComplete = true;

    ///////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list );

        if( savedInstanceState == null ) {

            Bundle extras = getIntent().getExtras();

            if( extras == null ) {

                rssString = null;

            } else {

                rssString = extras.getString( Constants.SAVED_RSS );

            }

        } else {

            rssString = ( String ) savedInstanceState.getSerializable( Constants.SAVED_RSS );

        }

        fetchXML();
        while( parsingComplete );

        feedTitleTextView = ( TextView ) findViewById( R.id.feedTitleTextView );
        feedTitleTextView.setText( Objects.toString( tempTitle.get(0)) );

        // Totally janky!
        for ( int i = 1; i < tempTitle.size(); i++ ) {

            final ListModel rssList = new ListModel();

            rssList.setTitle( Objects.toString( tempTitle.get(i) ));
            rssList.setDescription( Objects.toString( tempDescription.get(i) ));
            rssList.setURL ( Objects.toString ( tempURL.get(i-1) ));
            rssList.setRSS ( rssString );

            articleList.add( rssList );

        }

        adapter = new AppocolypseAdapter( this, articleList, getResources() );
        listView = ( ListView ) findViewById( R.id.list );
        listView.setAdapter( adapter );

    }

    ///////////////////////////////////////////////////////////////////////
    public void fetchXML() {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        Thread thread = new Thread( new Runnable() {

            @Override
            public void run() {

                try {

                    URL url = new URL( rssString );
                    HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();

                    connection.setReadTimeout( 10000 );
                    connection.setConnectTimeout( 15000 );
                    connection.setRequestMethod( "GET" );
                    connection.setDoInput( true );
                    connection.connect();

                    InputStream stream = connection.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = xmlFactoryObject.newPullParser();

                    parser.setFeature( XmlPullParser.FEATURE_PROCESS_NAMESPACES, false );
                    parser.setInput( stream, null );

                    parseXMLAndStoreIt( parser );
                    stream.close();

                } catch ( Exception e ) { Log.d( "ERROR", e.toString() ); }

            }

        });

        thread.start();

    }

    ///////////////////////////////////////////////////////////////////////
    public void parseXMLAndStoreIt( XmlPullParser parser ) {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        int event;
        String text = "";

        try {

            event = parser.getEventType();

            while ( event != XmlPullParser.END_DOCUMENT ) {

                String name = parser.getName();

                switch ( event ) {

                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:

                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if( name.equalsIgnoreCase( "title" )) { tempTitle.add( text ); }

                        else if( name.equalsIgnoreCase( "link" )) { tempURL.add( text ); }

                        else if( name.equalsIgnoreCase( "description" )) { tempDescription.add( text ); }

                        break;

                }

                event = parser.next();

            }

            parsingComplete = false;

        } catch ( Exception e ) { e.printStackTrace(); }

    }

    ///////////////////////////////////////////////////////////////////////
    @Override
    public void onPause() {
    /*/////////////////////////////////////////////////////////////////////
                  TODO: need to make sure this works when screen is rotated
    /////////////////////////////////////////////////////////////////////*/

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences( this );

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( Constants.SAVED_RSS, rssString );
        super.onPause();

    }

    ///////////////////////////////////////////////////////////////////////
    @Override
    public void onResume() {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences( this );

        rssString = sharedPreferences.getString( Constants.SAVED_RSS, null );

    }

    @Override
    protected void onDestroy() {

        Intent intent = new Intent ( this, RSSActivity.class );
        intent.putExtra( Constants.SAVED_RSS, rssString );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );
        super.onDestroy();

    }

}