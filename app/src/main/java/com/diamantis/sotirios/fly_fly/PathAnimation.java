package com.diamantis.sotirios.fly_fly;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * <p>
 * This class moves the blue jay along a predefined path, set in BlueJayAnimation.
 * During the entire animation, the blue jay remains at its original location,
 * defined in layout/content_main.xml, it's just drawn in another location.
 * To make the animation clickable we could use setLayoutParams()
 * to modify the blue jay's position, but that shows it squished
 * when it's coming-in from off-screen, so we use a separate view,
 * that's transparent and on top of the blue jay, and can be clicked.
 * </p>
 */

public class PathAnimation extends Animation
{
    private View view;
    private View click_view;
    private PathMeasure measure;
    private float[] pos = new float[2];

    public PathAnimation()
    {
    }

    public void init( View view_, View click_view_, Path path )
    {
        view = view_;
        click_view = click_view_;
        measure = new PathMeasure( path, false );
    }

    @Override
    protected void applyTransformation( float interpolatedTime, Transformation t )
    {
        measure.getPosTan( measure.getLength() * interpolatedTime, pos, null );
        t.getMatrix().setTranslate( pos[0], pos[1] );

        android.view.ViewGroup.MarginLayoutParams params = (android.view.ViewGroup.MarginLayoutParams) click_view.getLayoutParams();
        params.leftMargin = (int) pos[0];
        params.topMargin = (int) pos[1];
        click_view.setLayoutParams( params );
    }
}