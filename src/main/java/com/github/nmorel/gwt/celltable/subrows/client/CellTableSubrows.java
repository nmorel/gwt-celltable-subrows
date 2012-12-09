package com.github.nmorel.gwt.celltable.subrows.client;

import java.util.Date;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Logger;

import com.github.nmorel.gwt.celltable.subrows.client.model.Generator;
import com.github.nmorel.gwt.celltable.subrows.client.model.Parent;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CellTableSubrows
    implements EntryPoint
{
    private static Logger logger = Logger.getLogger( "" );

    // initialize logger with custom formatter
    static
    {
        Handler handlers[] = Logger.getLogger( "" ).getHandlers();
        for ( Handler handler : handlers )
        {
            handler.setFormatter( new CustomLogFormatter( true ) );
        }
    }

    private static Binder uiBinder = GWT.create( Binder.class );

    interface Binder
        extends UiBinder<Widget, CellTableSubrows>
    {
    }

    @UiField( provided = true )
    DataGrid<Parent> table;

    private SubrowTableBuilder tableBuilder;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad()
    {
        table = new DataGrid<Parent>( Integer.MAX_VALUE, GWT.<CellTableResources> create( CellTableResources.class ) );
        tableBuilder = new SubrowTableBuilder( table );
        table.setTableBuilder( tableBuilder );

        createDeployableColumn();
        createNumberColumn();
        createDateColumn();
        createTextColumn();

        List<Parent> datas = Generator.generate();

        ListDataProvider<Parent> dataProvider = new ListDataProvider<Parent>( datas );
        dataProvider.addDataDisplay( table );

        // not pretty but working...
        PopupPanel logPopupPanel = ( (PopupPanel) RootPanel.get().getWidget( 0 ) );
        logPopupPanel.setStyleName( "" );
        RootPanel.get().clear();
        RootPanel.get().add( uiBinder.createAndBindUi( this ) );
        ( (VerticalPanel) logPopupPanel.getWidget() ).getWidget( 1 ).setPixelSize( 600, 200 );
        RootPanel.get().add( logPopupPanel );
        logPopupPanel.setPopupPosition( 15, 500 );
    }

    private void createDeployableColumn()
    {
        Column<Parent, String> column = new Column<Parent, String>( new ClickableTextCell() ) {

            @Override
            public String getValue( Parent object )
            {
                if ( object.getChildren().isEmpty() )
                {
                    return null;
                }
                else
                {
                    return object.isDeployed() ? "-" : "+";
                }
            }
        };
        column.setFieldUpdater( new FieldUpdater<Parent, String>() {

            @Override
            public void update( int index, Parent object, String value )
            {
                if ( !object.getChildren().isEmpty() )
                {
                    object.setDeployed( !object.isDeployed() );
                    table.redrawRow( index );
                    if ( object.isDeployed() )
                    {
                        logger.info( "Show children's parent : " + object );
                    }
                    else
                    {
                        logger.info( "Hide children's parent : " + object );
                    }
                }
            }
        } );
        table.addColumn( column );
        table.setColumnWidth( column, 30, Unit.PX );
    }

    private void createNumberColumn()
    {
        Column<Parent, ?> column = new Column<Parent, Number>( tableBuilder.getNumberCell() ) {

            @Override
            public Number getValue( Parent object )
            {
                return object.getNumber();
            }
        };
        column.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );
        table.addColumn( column, "Number" );
        table.setColumnWidth( column, 75, Unit.PX );
    }

    private void createDateColumn()
    {
        Column<Parent, Date> column = new Column<Parent, Date>( tableBuilder.getDateCell() ) {

            @Override
            public Date getValue( Parent object )
            {
                return object.getDate();
            }
        };
        column.setFieldUpdater( new FieldUpdater<Parent, Date>() {

            @Override
            public void update( int index, Parent object, Date value )
            {
                object.setDate( value );
                logger.info( "Updated parent date : " + object );
            }
        } );
        table.addColumn( column, "Date" );
        table.setColumnWidth( column, 120, Unit.PX );
    }

    private void createTextColumn()
    {
        Column<Parent, String> column = new Column<Parent, String>( tableBuilder.getTextCell() ) {

            @Override
            public String getValue( Parent object )
            {
                return object.getText();
            }
        };
        column.setFieldUpdater( new FieldUpdater<Parent, String>() {

            @Override
            public void update( int index, Parent object, String value )
            {
                object.setText( value );
                logger.info( "Updated parent text : " + object );
            }
        } );
        table.addColumn( column, "Text" );
    }
}
