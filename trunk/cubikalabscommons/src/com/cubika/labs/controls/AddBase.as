package com.cubika.labs.controls
{
	import com.cubika.labs.event.AddClickEvent;
	import com.cubika.labs.event.DataProviderEvent;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.containers.HBox;
	import mx.controls.Button;
	import mx.events.CollectionEvent;
	import mx.events.FlexEvent;
	
	[Event(name="addClick",type="com.cubika.labs.event.AddClickEvent")]
	[Event(name="dataProviderChange",type="com.cubika.labs.event.DataProviderEvent")]
	[Event(name="change")]
	
	public class AddBase extends HBox
	{
		public var hydratate:Boolean = true;
		
		private var button:Button = new Button();
		private var waitingSetItem:Boolean = false;
		private var waitingSelectedItem:*;
		
		protected var control:Object;
		
		public function AddBase()
		{
			super();
			setStyle("horizontalGap",1);	
			addEventListener(FlexEvent.CREATION_COMPLETE,doInit,false,0,true);
		}
		
		
		override protected function createChildren():void
		{
			super.createChildren();
			
			button.label = "add";
			
			addChild(DisplayObject(control));
			addChild(button);
			control.addEventListener(Event.CHANGE,changeControl,false,0,true);
		}
		
		[Bindable]
		public function set dataProvider(value:Object):void
		{
			control.dataProvider = value;
			
			if (value)
			{
				control.dataProvider.addEventListener(CollectionEvent.COLLECTION_CHANGE,collectionChange,false,0,true);
				var event:CollectionEvent = new CollectionEvent(CollectionEvent.COLLECTION_CHANGE);
				collectionChange(event);
			}
			
		}
		
		public function get dataProvider():Object
		{
			return control.dataProvider
		}
		
		[Bindable]
		public function set selectedItem(value:Object):void
		{
			if (!dataProvider || dataProvider.length == 0 || !dataProvider[0])
			{
				waitingSetItem = true;
				waitingSelectedItem = value;
			}
			
			control.selectedItem = value;
		}
		
		public function get selectedItem():Object
		{
			return control.selectedItem;
		}
		
		[Bindable]
		public function set selectedIndex(value:int):void
		{
			control.selectedIndex = value;
		}
		
		public function get selectedIndex():int
		{
			return control.selectedIndex;
		}
		
		public function get labelField():String
		{
			return control.labelField;
		}
		
		[Bindable]
		public function set labelField(value:String):void
		{
			control.labelField = value;
		}
		
		private function doInit(event:Event):void
		{
			event.stopImmediatePropagation();
			button.addEventListener(MouseEvent.CLICK,clickHandler,false,0,true);	
		}
		
		private function clickHandler(event:MouseEvent):void
		{
			dispatchEvent(new AddClickEvent());	
		}
		
		private function collectionChange(event:CollectionEvent):void
		{
			if (waitingSetItem)
			{
				waitingSetItem = false;
				selectedItem = waitingSelectedItem;
			}
			
			dispatchEvent(new DataProviderEvent());
		}
		
		private function changeControl(event:Event):void
		{
			dispatchEvent(new Event(Event.CHANGE));	
		}
	}
}