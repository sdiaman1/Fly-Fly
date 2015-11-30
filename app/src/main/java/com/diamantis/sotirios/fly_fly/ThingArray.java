package com.diamantis.sotirios.fly_fly;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * <p>
 * This class holds an array of Things,
 * which it gets from the assets/files/thing.txt file.
 * </p>
 */

public class ThingArray extends ArrayList <Thing>
{
    private int current_thing = 0;

    // Get the things from the things.txt file and put them in this array.
    public void init( Context context )
    {
        try
        {
            AssetManager assets = context.getAssets();
            InputStream input_stream = assets.open( "files/things.txt" );

            if ( input_stream != null )
            {
                InputStreamReader input_stream_reader = new InputStreamReader( input_stream );
                BufferedReader bufferedReader = new BufferedReader( input_stream_reader );
                String string = bufferedReader.readLine();
                String name;
                int image = 0;
                boolean fly = false;
                String answer;
                String sub_string;
                Thing thing = null;

                while ( string != null )
                {
                    if ( string.charAt( 0 ) != ';' )
                    {
                        // thing's name, image .png, fly (true/false), answer
                        // Dragon Fly, dragon_fly, true, Dragonflies were some of the first winged insects...
                        // Pig, pig, false, The popular saying "it will happen when pigs fly"...
                        
                        StringTokenizer tokens = new StringTokenizer( string, "," );

                        name = tokens.nextToken().trim();

                        sub_string = tokens.nextToken().trim();
                        image = context.getResources().getIdentifier( sub_string, "drawable", context.getPackageName() );

                        sub_string = tokens.nextToken().trim();
                        fly = Boolean.valueOf(sub_string);

                        // The answer may contain commas, but it's the last field on the line,
                        // so get all remaining characters.  substring( 1 ) skips the leading comma.
                        answer = tokens.nextToken( "" ).substring( 1 ).trim();

                        thing = new Thing( name, image, fly, answer );
                        add( thing );
                    }

                    string = bufferedReader.readLine();
                }

                input_stream.close();
            }
        }
        catch ( IOException e )
        {
        }
    }

    public int getCurrentThing()
    {
        return current_thing;
    }

    public void setCurrentThing( int index )
    {
        current_thing = index;
    }

    public void previous()
    {
        if ( current_thing > 0 )
        {
            current_thing--;
        }
        else
        {
            // Wrap from the first to the last array item.
            current_thing = size() - 1;
        }
    }

    public void next()
    {
        if ( current_thing < ( size() - 1 ) )
        {
            current_thing++;
        }
        else
        {
            // Wrap from the last to the first array item.
            current_thing = 0;
        }
    }

    public String getName()
    {
        return getName( current_thing );
    }

    public String getName( int index )
    {
        return get( index ).getName();
    }

    public int getImage()
    {
        return getImage( current_thing );
    }

    public int getImage( int index )
    {
        return get( index ).getImage();
    }

    public boolean getFly()
    {
        return getFly( current_thing );
    }

    public boolean getFly( int index )
    {
        return get( index ).getFly();
    }

    public String getAnswer()
    {
        return getAnswer( current_thing );
    }

    public String getAnswer( int index )
    {
        return get( index ).getAnswer();
    }
}
