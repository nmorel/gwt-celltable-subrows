package com.github.nmorel.gwt.celltable.subrows.client.celltable;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;

/**
 * HasCell pour une cellule fille permettant d'obtenir les evenements sur cette cellule
 * 
 * @author NICOLASM
 * @param <P> Type de l'objet parent
 * @param <C> Type de l'objet fils
 * @param <V> Type de la valeur manipul�e par la cellule
 */
public class ChildCellColumn<P, C, V>
    implements HasCell<P, V>
{
    private int childIndex;

    private ChildGetter<P, C> childGetter;

    private Cell<V> cell;

    private ValueGetter<C, V> valueGetter;

    private ChildFieldUpdater<P, C, V> fieldUpdater;

    public ChildCellColumn( int childIndex, ChildGetter<P, C> childGetter, Cell<V> cell, ValueGetter<C, V> valueGetter )
    {
        this( childIndex, childGetter, cell, valueGetter, null );
    }

    public ChildCellColumn( int childIndex, ChildGetter<P, C> childGetter, Cell<V> cell, ValueGetter<C, V> valueGetter,
                            FieldUpdater<C, V> fieldUpdater )
    {
        this.childIndex = childIndex;
        this.childGetter = childGetter;
        this.cell = cell;
        this.valueGetter = valueGetter;
        this.fieldUpdater = new ChildFieldUpdater<P, C, V>( childIndex, childGetter, fieldUpdater );
    }

    @Override
    public Cell<V> getCell()
    {
        return cell;
    }

    public ValueGetter<C, V> getValueGetter()
    {
        return valueGetter;
    }

    @Override
    public FieldUpdater<P, V> getFieldUpdater()
    {
        return fieldUpdater;
    }

    @Override
    public V getValue( P parent )
    {
        return valueGetter.getValue( childGetter.getChild( parent, childIndex ) );
    }

}
