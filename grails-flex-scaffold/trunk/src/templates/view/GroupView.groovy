<?xml version="1.0" encoding="utf-8"?>
<mx:VBox xmlns:mx="http://www.adobe.com/2006/mxml" xmlns:usuarioExterno="view.usuarioExterno.*" 
	creationComplete="doInit()" ><!--NS-->
	
	<mx:Script>
	<![CDATA[
	
	    import mx.core.Container;
		import mx.events.ItemClickEvent;
		import com.cubika.labs.utils.MultipleRM;
		
		[Bindable]
		private var _controlPanelDataProvider:Array;
		
		private var _selectedChildBeforeInit:String;
		
		private function doInit():void
		{
			initControlPanel();
			
			if (_selectedChildBeforeInit)
				selectedView = _selectedChildBeforeInit;
		}
		
		private function initControlPanel():void
		{
			_controlPanelDataProvider = tn.getChildren();
		}
		
		public function set selectedView(value:String):void
		{
			if (tn)
			{
				tn.selectedChild = this[value];
				tn.selectedChild.setFocus();
			}
			else
				_selectedChildBeforeInit = value;
		}
		
		public function getView(v:String):Object
		{
			tn.selectedChild = Container(tn.getChildByName(v));
			return tn.getChildByName(v);
		}
		
		public function controlPanelClickHandler(event:ItemClickEvent):void
		{
			tn.selectedIndex = controlPanel.selectedIndex;
		}
		
	]]>
	</mx:Script>
	
	<mx:ViewStack width="100%" height="100%" id="tn">
		<!--CRUDVIEWS-->
	</mx:ViewStack>

	<mx:Canvas clipContent="false" height="0" includeInLayout="false">
		<mx:ToggleButtonBar id="controlPanel" y="-6" x="6" dataProvider="{tn}" 
			itemClick="controlPanelClickHandler(event)" styleName="reportingToogleButtonBar"/>
	</mx:Canvas>
	
</mx:VBox>