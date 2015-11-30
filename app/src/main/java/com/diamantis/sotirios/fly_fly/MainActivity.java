package com.diamantis.sotirios.fly_fly;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * <p>
 * This class is the main activity for this app.
 * It shows a "thing" and asks the user if it flies or not.
 * The user should press on the rose with the butterfly if it flies,
 * or the rose with the caterpillar if it doesn't.
 * The user is then shown some interesting facts about the thing.
 * The answer background is painted green if the user's selection was correct,
 * else it is painted red.
 * </p>
 */

public class MainActivity extends AppCompatActivity
{
    private Animation caterpillar_icon_animation;
    private BlueJayAnimation blue_jay_animation;
    private GestureDetector gesture_detector;
    private static ThingArray things;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        things = new ThingArray();
        things.init( this );

        blue_jay_animation = new BlueJayAnimation( this );

        caterpillar_icon_animation = AnimationUtils.loadAnimation( this, R.anim.rotate_infinite );

        gesture_detector = new GestureDetector( this, new GestureDetector.SimpleOnGestureListener()
        {
            private static final int SWIPE_MIN_DISTANCE = 30;
            private static final int SWIPE_MAX_OFF_PATH = 300;
            private static final int SWIPE_THRESHOLD_VELOCITY = 10;
    
            @Override
            public boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY )
            {
                if ( Math.abs( e1.getY() - e2.getY() ) <= SWIPE_MAX_OFF_PATH )
                {
                    if (    ( ( e1.getX() - e2.getX() ) > SWIPE_MIN_DISTANCE )
                         && ( Math.abs( velocityX ) > SWIPE_THRESHOLD_VELOCITY ) )
                    {
                        // Right to left swipe.
                        onLeftSwipe();
    
                    }
                    else if (    ( ( e2.getX() - e1.getX() ) > SWIPE_MIN_DISTANCE )
                              && ( Math.abs( velocityX ) > SWIPE_THRESHOLD_VELOCITY ) )
                    {
                        // Left to right swipe.
                        onRightSwipe();
                    }
                }
    
                return false;
            }
        } );
        
        // Return false when the answer TextView is touched,
        // so that we can swipe right and left on it.
        TextView answer = (TextView) findViewById( R.id.answer );
        answer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                gesture_detector.onTouchEvent(event);
                return false;
            }
        });
    }

    // Called when the activity is becoming visible.
    @Override
    public void onResume()
    {
        super.onResume();

        showThing();
        
        blue_jay_animation.start();
    }

    // Called when the activity is going into the background.
    @Override
    public void onPause()
    {
        super.onPause();
        
        // Stop all animations, they will be resumed in onResume()
        // when the user returns to this activity.
        blue_jay_animation.stop();

        ImageView butterfly_icon = (ImageView) findViewById( R.id.butterfly_icon );
        ((AnimationDrawable) butterfly_icon.getBackground()).stop();

        ImageView caterpillar_icon = (ImageView) findViewById( R.id.caterpillar_icon );
        caterpillar_icon_animation.cancel();
        caterpillar_icon.clearAnimation();
    }
    
    // Shows the current thing.
    private void showThing()
    {
        TextView number = (TextView) findViewById( R.id.number );
        number.setText(String.valueOf(things.getCurrentThing() + 1));

        TextView thing_name = (TextView) findViewById( R.id.thing_name );
        thing_name.setText(things.getName());

        ImageView thing_image = (ImageView) findViewById( R.id.thing_image );
        thing_image.setImageResource( things.getImage() );

        setVisibility(View.INVISIBLE);
    }

    // Show or hide the question, butterfly, caterpillar, and answer views.
    // The answer is shown over the same area as the others.
    private void setVisibility( int answer_visibility )
    {
        int yes_no_visibility = View.VISIBLE;

        if ( answer_visibility == View.VISIBLE )
        {
            yes_no_visibility = View.INVISIBLE;
        }

        TextView question = (TextView) findViewById( R.id.question );
        question.setVisibility( yes_no_visibility );

        ImageView butterfly_icon = (ImageView) findViewById( R.id.butterfly_icon );
        
        if ( yes_no_visibility == View.VISIBLE )
        {
            ((AnimationDrawable) butterfly_icon.getBackground()).start();
        }
        else
        {
            ((AnimationDrawable) butterfly_icon.getBackground()).stop();
        }

        butterfly_icon.setVisibility( yes_no_visibility );

        ImageView caterpillar_icon = (ImageView) findViewById( R.id.caterpillar_icon );
        
        if ( yes_no_visibility == View.VISIBLE )
        {
            caterpillar_icon.startAnimation( caterpillar_icon_animation );
        }
        else
        {
            caterpillar_icon_animation.cancel();
            caterpillar_icon.clearAnimation();
        }

        caterpillar_icon.setVisibility(yes_no_visibility);

        TextView answer = (TextView) findViewById( R.id.answer );
        answer.setVisibility(answer_visibility);

        ImageView answer_rectangle = (ImageView) findViewById( R.id.answer_rectangle );
        answer_rectangle.setVisibility(answer_visibility);
    }

    // Called when the user clicks one of the roses, with the butterfly or caterpillar.
    public void onButterflyOrCaterpillar( View view )
    {
        boolean user_selection = false;

        if ( view.getId() == R.id.white_rose )
        {
            user_selection = true;
        }

        ImageView answer_rectangle = (ImageView) findViewById( R.id.answer_rectangle );

        // setBackgroundColor() doesn't work, so we use 2 rectangles: 1 green and 1 red.
        if ( things.getFly() == user_selection )
        {
            answer_rectangle.setImageResource( R.drawable.answer_rectangle_green );
        }
        else
        {
            answer_rectangle.setImageResource( R.drawable.answer_rectangle_red );
        }

        String flies_or_not;
        if ( things.getFly() == true )
        {
            flies_or_not = "Flies.";
        }
        else
        {
            flies_or_not = "Doesn't fly.";
        }
        
        TextView answer = (TextView) findViewById( R.id.answer );
        answer.setText( Html.fromHtml( flies_or_not + "<br/>" + things.getAnswer() + "<br/>" + "Swipe right to continue ..." ) );
        answer.setMovementMethod( LinkMovementMethod.getInstance() );

        setVisibility( View.VISIBLE );
    }

    // Called when the user clicks the blue jay animation.
    public void onBlueJay( View view )
    {
        MediaPlayer media_player = MediaPlayer.create( this, R.raw.blue_jay );
        media_player.start();
    }

    // Called when the user touches the screen.
    @Override
    public boolean onTouchEvent( MotionEvent event )
    {
        if ( gesture_detector.onTouchEvent( event ) )
        {
            return true;
        }
        else
        {
            return super.onTouchEvent( event );
        }
    }

    // Called from gesture_detector, in this class's onCreate().
    private void onLeftSwipe()
    {
        things.previous();
        showThing();
    }

    private void onRightSwipe()
    {
        things.next();
        showThing();
    }

    // Called when the user clicks the list button, in the top-right of the screen.
    // The intent's putParcelableArrayListExtra() isn't used to pass the ThingArray
    // to ThingListActivity because it copies the array.  A static ThingArray uses
    // less memory and makes it easier to share the things.
    public void onList( View view )
    {
        Intent intent = new Intent( this, ThingListActivity.class );

        startActivity( intent );
    }

    public static ThingArray getThings()
    {
        return things;
    }
}
