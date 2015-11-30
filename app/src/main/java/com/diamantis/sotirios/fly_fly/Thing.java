package com.diamantis.sotirios.fly_fly;

/**
 * <p>
 * This class holds a thing's info.
 * A "thing" can be anything, for example, a dragon fly, or a pig, or bacteria.
 * </p>
 */

public class Thing
{
    private String name;
    private int image;
    private boolean fly;
    private String answer;

    Thing( String name_, int image_, boolean fly_, String answer_ )
    {
        name = name_;
        image = image_;
        fly = fly_;
        answer = answer_;
    };

    public String getName()
    {
        return name;
    }

    public int getImage()
    {
        return image;
    }

    public boolean getFly()
    {
        return fly;
    }

    public String getAnswer()
    {
        return answer;
    }
}
