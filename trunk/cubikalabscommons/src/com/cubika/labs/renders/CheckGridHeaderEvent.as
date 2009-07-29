package com.cubika.labs.renders
{
    import flash.events.Event;
    
    import mx.controls.dataGridClasses.DataGridColumn;

    public class CheckGridHeaderEvent extends Event
    {
        public static const SELECT:String = 'CheckBoxDgHdrRendererEventSELECT';
        public static const UNSELECT:String = 'CheckBoxDgHdrRendererEventUNSELECT';
        
        public var dataGridColumn:DataGridColumn;
        
        public function CheckGridHeaderEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false)
        {
            super(type, bubbles, cancelable);
        }
        override public function clone():Event
        {
            var e:CheckGridHeaderEvent = new CheckGridHeaderEvent( type, bubbles, cancelable );
            e.dataGridColumn = dataGridColumn;
            return e;
        }
    }
}