package com.github.nmorel.gwt.celltable.subrows.client.celltable;

public interface ChildGetter<P, C>
{
    C getChild( P parent, int index );
}
