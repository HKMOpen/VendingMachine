package com.hkm.gogosushi.effect3;

/**
 * Created by hesk on 4/12/2015.
 */
import java.io.Serializable;

/**
 * @author Ludovic ROLAND
 * @since 2014.12.20
 */
public final class Photo
        implements Serializable
{

    private static final long serialVersionUID = 1L;

    public final String name;

    public final String image;

    public Photo(String name, String image)
    {
        this.name = name;
        this.image = image;
    }

}