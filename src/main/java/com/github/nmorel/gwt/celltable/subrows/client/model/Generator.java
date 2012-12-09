package com.github.nmorel.gwt.celltable.subrows.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.Random;

@SuppressWarnings( "deprecation" )
public final class Generator
{
    public static List<Parent> generate()
    {
        List<Parent> result = new ArrayList<Parent>();

        for ( int i = 0; i < 20; i++ )
        {
            int number = Random.nextInt( 50 );
            Parent deploy =
                new Parent( number, new Date( 112, 0, 1 - number ), "Parent #" + ( i + 1 ), generateChildren() );
            result.add( deploy );
        }

        return result;
    }

    public static List<Child> generateChildren()
    {
        List<Child> result = new ArrayList<Child>();

        int max = Random.nextInt( 4 );
        for ( int i = 0; i < max; i++ )
        {
            int number = Random.nextInt( 50 );
            Child deploy = new Child( number, new Date( 112, 0, 1 - number ), "Child #" + ( i + 1 ) );
            result.add( deploy );
        }

        return result;
    }
}
