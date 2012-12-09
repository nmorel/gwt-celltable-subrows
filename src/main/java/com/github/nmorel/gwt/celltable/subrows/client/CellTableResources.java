package com.github.nmorel.gwt.celltable.subrows.client;

import com.google.gwt.user.cellview.client.DataGrid.Resources;
import com.google.gwt.user.cellview.client.DataGrid.Style;

public interface CellTableResources
    extends Resources
{
    public interface CellTableStyle
        extends Style
    {

    }

    @Source( { Style.DEFAULT_CSS, "cellTable.css" } )
    CellTableStyle dataGridStyle();
}
