package com.diamantis.sotirios.fly_fly;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.drawable.AnimationDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * <p>
 * This class handles the blue jay animation.
 * There's 2 parts to the animation:
 * 1) drawable/blue_jay_animation.xml, which flaps the wings, and
 * 2) a PathAnimation object that moves the blue jay along a predefined path.
 * </p>
 */

public class BlueJayAnimation extends PathAnimation
{
    DisplayMetrics metrics;
    ImageView view;
    Path path;
    
    public BlueJayAnimation( Activity activity )
    {
        super();
        
        view = (ImageView) activity.findViewById( R.id.blue_jay_animation );
        
        ImageView click_view = (ImageView) activity.findViewById( R.id.blue_jay_animation_click );
        
        Resources resources = activity.getResources();
        metrics = resources.getDisplayMetrics();
        
        getPath();
        
        init( view, click_view, path );
        
        // The animation takes 20 seconds, from start to finish, and repeats forever.
        setDuration( 20000 );
        setRepeatCount( Animation.INFINITE );
    }

    // Plots a path for the blue jay.
    // All points are relative to the blue jay's original position, "0",
    // defined in layout/content_main.xml.
    // "1" is the blue jay's first position, and "6" is its last.
    //
    //   |                               (?)      3           2            | 
    //   |                                                                 |
    // 6 | 0        (butterfly)                  (caterpillar)             | 1
    //   |                                                                 |
    //   |          5         4                                            |
    //
    private void getPath()
    {
        path = new Path();
        
        float width = metrics.widthPixels;
        
        float x = width;
        float y = 0;
        path.moveTo( x, y );

        x = ( width / 2 ) + dipToPx( 80 );
        y = -dipToPx( 60 );
        path.lineTo( x, y );

        x -= dipToPx( 70 );
        y -= dipToPx( 10 );
        path.lineTo( x, y );

        x = ( width / 2 ) - dipToPx( 80 );
        y = dipToPx( 40 );
        path.lineTo( x, y );
        
        x -= dipToPx( 70 );
        y -= dipToPx( 10 );
        path.lineTo( x, y );

        x = -dipToPx( 70 );
        y = -dipToPx( 70 );
        path.lineTo( x, y );
    }

    // Converts a DIP (Density Independent Pixel) value to a pixel value.
    // The getPath() code uses DIP, to support multiple screen resolutions,
    // while the animation functionality expects pixels.
    private float dipToPx( float dip )
    {
        float px = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dip, metrics );
        return px;
    }

    // Starts the blue jay animation.
    // An ObjectAnimator object isn't used for this functionality because
    // it isn't available for API levels older than 11.
    public void start()
    {
        // Flap the blue jay's wings, using drawable/blue_jay_animation.xml.
        ((AnimationDrawable) view.getBackground()).start();
        
        // Send the blue jay along the path set in getPath().
        view.startAnimation( this );
    }

    // Stops the blue jay animation.
    public void stop()
    {
        ((AnimationDrawable) view.getBackground()).stop();

        view.clearAnimation();
    }    
}