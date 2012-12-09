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
    private C child;

    private FieldUpdater<C, V> fieldUpdater;

    public ChildFieldUpdater( C child, FieldUpdater<C, V> fieldUpdater )
    {
        this.child = child;
        this.fieldUpdater = fieldUpdater;
    }

    @Override
    public void update( int index, P parent, V value )
    {
        fieldUpdater.update( index, child, value );
    }
}
