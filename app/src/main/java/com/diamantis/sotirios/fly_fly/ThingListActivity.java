package com.diamantis.sotirios.fly_fly;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.app.ListActivity;

/**
 * <p>
 * This class displays a list of things.  The user can click on any thing
 * in this list, to select it as the current thing, and return to the main activity.
 * </p>
 */

public class ThingListActivity extends ListActivity
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.things_list );

        ThingArray things = MainActivity.getThings();

        ThingArrayAdapter adapter = new ThingArrayAdapter( this, things );

        setListAdapter( adapter );

        ListView list = getListView();
        
        list.setSelection( things.getCurrentThing() );
    }

    // Called when an item in the list is clicked.
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id)
    {
        super.onListItemClick(list, view, position, id);

        MainActivity.getThings().setCurrentThing( position );

        // Return to the main activity.
        finish();
    }
}