package itc298final.com.rssappocolypse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class AppocolypseAdapter extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private ArrayList data;

    private static LayoutInflater inflater = null;

    public Resources resources;

    int index = 0;
    ListModel temp = null;

    ///////////////////////////////////////////////////////////////////////
    public AppocolypseAdapter( Activity activity, ArrayList data, Resources resources ) {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        this.activity = activity;
        this.data = data;
        this.resources = resources;

        inflater = ( LayoutInflater ) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

    }

    public int getCount() { if( data.size() <= 0 ) { return 1; } return data.size(); }

    public Object getItem( int position ) { return position; }

    public long getItemId( int position ) { return position; }

    public static class ViewHolder {

        public Button readMore;
        public TextView title;
        public TextView description;

    }

    ///////////////////////////////////////////////////////////////////////
    public View getView( int position, View convertView, ViewGroup parent ) {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        View view = convertView;
        ViewHolder holder;

        if( convertView == null ) {

            view = inflater.inflate( R.layout.list_rss, null );

            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById( R.id.itemTitleTextView );
            holder.description = (TextView) view.findViewById( R.id.descriptionTextView );
            holder.readMore = (Button) view.findViewById( R.id.readMeButton );

            view.setTag( holder );

        } else { holder = ( ViewHolder ) view.getTag(); }

        if( data.size() <= 0 ) {

            holder.title.setText( R.string.whoops );

        } else {

            temp = (ListModel) data.get( position );

            holder.title.setText( temp.getTitle() );
            holder.description.setText( temp.getDescription() );

            holder.readMore.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick( View view ) {

                    Intent intent = new Intent( view.getContext(), RSSWebView.class );
                    intent.putExtra( Constants.SAVED_URL, Objects.toString( temp.getURL() ));
                    intent.putExtra( Constants.SAVED_RSS, Objects.toString( temp.getRSS() ));
                    view.getContext().startActivity( intent );

                }

            });

            view.setOnClickListener( new OnItemClickListener( position ));

        }

        return view;

    }

    @Override
    public void onClick( View v ) { Log.v( "[ROW]", Integer.toString( index )); }

    ///////////////////////////////////////////////////////////////////////
    private class OnItemClickListener  implements View.OnClickListener {
    /*/////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////*/

        private int pos;
        OnItemClickListener( int position ){ pos = position; }

        ///////////////////////////////////////////////////////////////////////
        @Override
        public void onClick( View arg0 ) {
        /*/////////////////////////////////////////////////////////////////////
                      We're not really doing anything with this since we have a
                                                               READ MORE button
        /////////////////////////////////////////////////////////////////////*/

            Log.d( "[ITEM]", Integer.toString( pos ));

        }

    }

}