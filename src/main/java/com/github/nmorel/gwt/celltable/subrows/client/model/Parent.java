package com.github.nmorel.gwt.celltable.subrows.client.model;

import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

public class Parent
{
    private boolean deployed;

    private int number;

    private Date date;

    private String text;

    private List<Child> children;

    public Parent()
    {
    }

    public Parent( int number, Date date, String text, List<Child> children )
    {
        this.number = number;
        this.date = date;
        this.text = text;
        this.children = children;
    }

    public boolean isDeployed()
    {
        return deployed;
    }

    public void setDeployed( boolean deployed )
    {
        this.deployed = deployed;
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

    public List<Child> getChildren()
    {
        return children;
    }

    public void setChildren( List<Child> children )
    {
        this.children = children;
    }

    @Override
    public String toString()
    {
        return "Parent[deployed=" + deployed + ", number=" + number + ", date="
            + ( null == date ? null : DateTimeFormat.getFormat( PredefinedFormat.DATE_SHORT ).format( date ) )
            + ", text=" + text + "]";
    }

}
