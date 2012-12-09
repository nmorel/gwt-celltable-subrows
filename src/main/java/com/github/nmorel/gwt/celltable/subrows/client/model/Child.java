package com.github.nmorel.gwt.celltable.subrows.client.model;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

public class Child
{
    private int number;

    private Date date;

    private String text;

    public Child()
    {
    }

    public Child( int number, Date date, String text )
    {
        this.number = number;
        this.date = date;
        this.text = text;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber( int number )
    {
        this.number = number;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "Child[number=" + number + ", date="
            + ( null == date ? null : DateTimeFormat.getFormat( PredefinedFormat.DATE_SHORT ).format( date ) )
            + ", text=" + text + "]";
    }

}
