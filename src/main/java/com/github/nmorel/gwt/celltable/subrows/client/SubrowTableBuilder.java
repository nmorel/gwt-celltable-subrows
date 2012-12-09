package com.github.nmorel.gwt.celltable.subrows.client;

import java.util.Date;
import java.util.logging.Logger;

import com.github.nmorel.gwt.celltable.subrows.client.celltable.ChildCellColumn;
import com.github.nmorel.gwt.celltable.subrows.client.celltable.ChildGetter;
import com.github.nmorel.gwt.celltable.subrows.client.celltable.MyDefaultCellTableBuilder;
import com.github.nmorel.gwt.celltable.subrows.client.celltable.ValueGetter;
import com.github.nmorel.gwt.celltable.subrows.client.model.Child;
import com.github.nmorel.gwt.celltable.subrows.client.model.Parent;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.dom.builder.shared.DivBuilder;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.dom.client.Style.OutlineStyle;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.view.client.SelectionModel;

public class SubrowTableBuilder
    extends MyDefaultCellTableBuilder<Parent>
{
    private static Logger logger = Logger.getLogger( "" );

    private ChildGetter<Parent, Child> childGetter = new ChildGetter<Parent, Child>() {

        @Override
        public Child getChild( Parent parent, int index )
        {
            return parent.getChildren().get( index );
        }
    };

    private ValueGetter<Child, Date> dateValueGetter = new ValueGetter<Child, Date>() {
        @Override
        public Date getValue( Child row )
        {
            return row.getDate();
        }
    };

    private ValueGetter<Child, String> textValueGetter = new ValueGetter<Child, String>() {
        @Override
        public String getValue( Child row )
        {
            return row.getText();
        }
    };

    public SubrowTableBuilder( AbstractCellTable<Parent> cellTable )
    {
        super( cellTable );
    }

    @Override
    public void buildRowImpl( Parent rowValue, int absRowIndex )
    {
        buildDefaultRow( rowValue, absRowIndex );
        buildChildrenRow( rowValue, absRowIndex );
    }

    private void buildChildrenRow( Parent parent, int absRowIndex )
    {
        if ( parent.isDeployed() )
        {
            // Calculate the row styles.
            SelectionModel<? super Parent> selectionModel = cellTable.getSelectionModel();
            boolean isSelected =
                ( selectionModel == null || parent == null ) ? false : selectionModel.isSelected( parent );
            boolean isEven = absRowIndex % 2 == 0;
            StringBuilder trClasses = new StringBuilder( isEven ? evenRowStyle : oddRowStyle );
            if ( isSelected )
            {
                trClasses.append( selectedRowStyle );
            }

            int i = 0;
            for ( Child child : parent.getChildren() )
            {
                buildChildRow( parent, i++, child, isEven, isSelected, trClasses );
            }
        }
    }

    protected void buildChildRow( Parent parent, int subrowIndex, Child child, boolean isEven, boolean isSelected,
                                  StringBuilder trClasses )
    {
        // only way to get subindex without modifying the abstractcellbuilder
        Context baseContext = createContext( 0 );

        // Build the row.
        TableRowBuilder tr = startRow();
        tr.className( trClasses.toString() );

        String evenOrOddStyle = isEven ? evenCellStyle : oddCellStyle;

        /*
         * deployable column
         */
        TableCellBuilder td = initChildTd( tr, evenOrOddStyle, 0, isSelected );
        DivBuilder div = td.startDiv();
        div.style().outlineStyle( OutlineStyle.NONE ).endStyle();
        // Empty cell
        div.endDiv();
        td.endTD();

        /*
         * number column
         */
        Context context = new Context( baseContext.getIndex(), 1, child, baseContext.getSubIndex() );
        td = initChildTd( tr, evenOrOddStyle, 1, isSelected );
        div = td.startDiv();
        div.style().outlineStyle( OutlineStyle.NONE ).endStyle();
        SafeHtmlBuilder sb = new SafeHtmlBuilder();
        createNumberCell().render( context, child.getNumber(), sb );
        div.html( sb.toSafeHtml() );
        div.endDiv();
        td.endTD();

        /*
         * date column
         */
        context = new Context( baseContext.getIndex(), 2, child, baseContext.getSubIndex() );
        td = initChildTd( tr, evenOrOddStyle, 2, isSelected );
        div = td.startDiv();
        div.style().outlineStyle( OutlineStyle.NONE ).endStyle();
        ChildCellColumn<Parent, Child, Date> dateColumn =
            new ChildCellColumn<Parent, Child, Date>( subrowIndex, childGetter, createDateCell(), dateValueGetter,
                new FieldUpdater<Child, Date>() {
                    @Override
                    public void update( int index, Child object, Date value )
                    {
                        object.setDate( value );
                        logger.info( "Updated child date : " + object );
                    }
                } );
        renderCell( div, context, dateColumn, parent );
        div.endDiv();
        td.endTD();

        /*
         * text column
         */
        context = new Context( baseContext.getIndex(), 3, child, baseContext.getSubIndex() );
        td = initChildTd( tr, evenOrOddStyle, 3, isSelected );
        div = td.startDiv();
        div.style().outlineStyle( OutlineStyle.NONE ).endStyle();
        ChildCellColumn<Parent, Child, String> textColumn =
            new ChildCellColumn<Parent, Child, String>( subrowIndex, childGetter, createTextCell(), textValueGetter,
                new FieldUpdater<Child, String>() {
                    @Override
                    public void update( int index, Child object, String value )
                    {
                        object.setText( value );
                        logger.info( "Updated child text : " + object );
                    }
                } );
        renderCell( div, context, textColumn, parent );
        div.endDiv();
        td.endTD();

        // End the row.
        tr.endTR();
    }

    private TableCellBuilder initChildTd( TableRowBuilder tr, String evenOrOddStyle, int columnIndex, boolean isSelected )
    {
        Column<Parent, ?> column = cellTable.getColumn( columnIndex );
        HorizontalAlignmentConstant hAlign = column.getHorizontalAlignment();
        VerticalAlignmentConstant vAlign = column.getVerticalAlignment();
        StringBuilder tdClasses = new StringBuilder( cellStyle );
        tdClasses.append( evenOrOddStyle );
        if ( columnIndex == 0 )
        {
            tdClasses.append( firstColumnStyle );
        }
        if ( isSelected )
        {
            tdClasses.append( selectedCellStyle );
        }
        if ( columnIndex + 1 == cellTable.getColumnCount() )
        {
            tdClasses.append( lastColumnStyle );
        }
        TableCellBuilder td = tr.startTD();
        if ( hAlign != null )
        {
            td.align( hAlign.getTextAlignString() );
        }
        if ( vAlign != null )
        {
            td.vAlign( vAlign.getVerticalAlignString() );
        }
        td.className( tdClasses.toString() );
        return td;
    }

    /*
     * The cells are not singleton because the AbstractEditableCell has a map containing view data and the key is the
     * same for main row and sub row : the Parent.
     */

    public NumberCell createNumberCell()
    {
        return new NumberCell();
    }

    public DatePickerCell createDateCell()
    {
        return new DatePickerCell( DateTimeFormat.getFormat( PredefinedFormat.DATE_SHORT ) );
    }

    public EditTextCell createTextCell()
    {
        return new EditTextCell();
    }

}
