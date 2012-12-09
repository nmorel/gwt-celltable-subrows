package com.github.nmorel.gwt.celltable.subrows.client.celltable;

import com.google.gwt.cell.client.FieldUpdater;

/**
 * Field updater pour les cellules filles.
 * 
 * @author NICOLASM
 * @param <P> Type de l'objet parent
 * @param <C> Type de l'objet fils
 * @param <V> Type de la valeur manipul�e par la cellule
 */
public class ChildFieldUpdater<P, C, V>
    implements FieldUpdater<P, V>
{
    private int childIndex;

    private ChildGetter<P, C> childGetter;

    private FieldUpdater<C, V> fieldUpdater;

    public ChildFieldUpdater( int childIndex, ChildGetter<P, C> childGetter, FieldUpdater<C, V> fieldUpdater )
    {
        this.childIndex = childIndex;
        this.childGetter = childGetter;
        this.fieldUpdater = fieldUpdater;
    }

    @Override
    public void update( int index, P parent, V value )
    {
        fieldUpdater.update( index, childGetter.getChild( parent, childIndex ), value );
    }
}
