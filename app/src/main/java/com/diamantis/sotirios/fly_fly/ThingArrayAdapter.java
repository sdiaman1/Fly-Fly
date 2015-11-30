package com.diamantis.sotirios.fly_fly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * <p>
 * This class populates the thing list rows, defined in layout/things_list_row.xml,
 * with information from the ThingArray.
 * It helps ThingListActivity to display the things list.
 * </p>
 */

public class ThingArrayAdapter extends ArrayAdapter <Thing>
{
    private ThingArray things;

    public ThingArrayAdapter( Context context, ThingArray things_ )
    {
        super( context, R.layout.things_list_row, things_ );
        things = things_;
    }

    // Called to display each row in the things list.
    // "position" is the list's (zero-indexed) row than needs to be displayed.
    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        View view = convertView;

        // If the view is null, inflate (render and show) it.
        if ( view == null )
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate( R.layout.things_list_row, null, true );
        }

        TextView number = (TextView) view.findViewById( R.id.list_row_number );
        number.setText( String.valueOf( position + 1 ) );

        TextView name = (TextView) view.findViewById( R.id.list_row_name );
        name.setText( things.getName( position ) );

        ImageView image = (ImageView) view.findViewById( R.id.list_row_image );
        image.setImageResource( things.getImage( position ) );

        return view;
    }
}