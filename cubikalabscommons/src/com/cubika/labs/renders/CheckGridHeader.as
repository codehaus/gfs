package com.cubika.labs.renders
{
    import com.cubika.labs.renders.CheckGridHeaderEvent;
    
    import flash.events.MouseEvent;
    
    import mx.collections.ICollectionView;
    import mx.collections.IViewCursor;
    import mx.controls.CheckBox;
    import mx.controls.DataGrid;
    import mx.controls.Label;
    import mx.controls.dataGridClasses.DataGridColumn;
    import mx.controls.dataGridClasses.DataGridListData;
    import mx.controls.listClasses.BaseListData;
    import mx.controls.listClasses.IDropInListItemRenderer;
    import mx.controls.listClasses.IListItemRenderer;
    import mx.core.IDataRenderer;
    import mx.core.UIComponent;
    import mx.events.DataGridEvent;
    
    /**
     * Checkbox DataGrid Header, contains a CheckBox and a Label.  
     * A click on the CheckBox is a select-all, a click on the Label is a standard sort
     */
    public class CheckGridHeader extends UIComponent implements IDataRenderer, IDropInListItemRenderer, IListItemRenderer
    {
        protected var _data:DataGridColumn;
        protected var _listData:DataGridListData;
        protected var _checkBox:CheckBox;
        protected var _label:Label;
        
        public function CheckGridHeader()
        {
            super();
            _checkBox = new CheckBox();
            _label = new Label();
        }
        
        override protected function createChildren():void
        {
            super.createChildren();
            
            addChild( _checkBox);
            //addChild( _label);
            // listen for a user click on our checkbox
            _checkBox.addEventListener( MouseEvent.CLICK, _onCheckboxClick, false, 0, true );
        }
        
        protected function _onCheckboxClick( event:MouseEvent ):void
        {
            var e:CheckGridHeaderEvent;
            
            if( _checkBox.selected )
            {
                e = new CheckGridHeaderEvent( CheckGridHeaderEvent.SELECT );
            }
            else
            {
                e = new CheckGridHeaderEvent( CheckGridHeaderEvent.UNSELECT );
            }
            e.dataGridColumn = _data;
            
            // update the data, dispatch an event
            var icv:ICollectionView = DataGrid( listData.owner ).dataProvider as ICollectionView;
            var cur:IViewCursor = icv.createCursor();
            
            while( !cur.afterLast )
            {
                if( cur.current.hasOwnProperty(_data.dataField) )
                {
                    cur.current[ _data.dataField ] = _checkBox.selected;
                }
                cur.moveNext();
            }
            // hey everyone, the data changed
            icv.refresh();
            
            // dispatch the event on the DataGrid to make listening easier
            DataGrid( listData.owner ).dispatchEvent( e );
        }
        
        override protected function updateDisplayList( _w:Number, _h:Number ):void
        {
            super.updateDisplayList( _w, _h );
            
            _checkBox.setActualSize(16,16);
            _checkBox.move(2,0);
            
            _label.setActualSize( _w - 18, _h );
            _label.move(18,0);
        }
        
        //IDataRenderer
        public function get data():Object
        {
            return _data;
        }
        public function set data(value:Object):void
        {
            _data = value as DataGridColumn;
            _label.text = DataGridListData(listData).label;
            
            // listen for any user sort gesture on the DataGrid
            DataGrid( listData.owner ).addEventListener( DataGridEvent.HEADER_RELEASE, _sortEventHandler, false, 0, true );
            _setCheckBoxSelected();
        }
        
        // user sort gesture 
        protected function _sortEventHandler(event:DataGridEvent):void
        {
            // if the user hit this column, but the mouse is over our checkbox, ignore (preventDefault, do not sort) 
            if ( event.itemRenderer == this 
                && _checkBox.getBounds( this ).contains( mouseX, mouseY ) )
            {
                event.preventDefault();
            }
        }
                
        // you'll probably want to override this in a subclass to make it more meaningful
        protected function _setCheckBoxSelected():void
        {
            var icv:ICollectionView = DataGrid( listData.owner ).dataProvider as ICollectionView;
            var cur:IViewCursor = icv.createCursor();
            var b:Boolean = true;
            
            while( !cur.afterLast && b )
            {
                b = cur.current.hasOwnProperty(_data.dataField) && cur.current[ _data.dataField ] === true;
                cur.moveNext();
            }
            _checkBox.selected = b;
        }
        
        //IDropInListItemRenderer
        public function get listData():BaseListData
        {
            return _listData;
        }
        public function set listData(value:BaseListData):void
        {
            _listData = value as DataGridListData; 
        }
    }
}