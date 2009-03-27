<?xml version="1.0" encoding="utf-8"?>
<mx:HBox xmlns:mx="http://www.adobe.com/2006/mxml" xmlns:view="view.${domainClass.propertyName}.*"
	creationComplete="doInit()">
	
	<mx:Script>
		<![CDATA[
			
			import mx.core.Container;
			
			import model.ApplicationModelLocator;
			import model.${domainClass.propertyName}.${className}Model;
			
			[Bindable]
			private var _model:${className}Model = ApplicationModelLocator.instance.${domainClass.propertyName}Model;
			
			private var _selectedChildBeforeInit:String;
			
			private function doInit():void
			{
				if (_selectedChildBeforeInit)
					selectedView = _selectedChildBeforeInit;
			}
			
			private function changeHandler():void
			{
				vs.selectedChild.setFocus();
			}
			
			override public function setFocus():void
			{
				vs.selectedChild.setFocus();
			}
			
			public function set selectedView(value:String):void
			{
				if (vs)
					vs.selectedChild = Container(vs.getChildByName(value));
				else
					_selectedChildBeforeInit = value;
			}
			
		]]>
	</mx:Script>
	
	<mx:Fade id="fadeEff" duration="200"/>
	
	<mx:ViewStack id="vs" selectedIndex="{_model.selectedIndexView}" change="changeHandler()" 
		width="100%" height="100%">
		
		<view:${className}ListView width="100%" height="100%" name="${domainClass.propertyName}List" hideEffect="fadeEff" showEffect="fadeEff"/>

		<view:${className}EditView  height="100%" width="100%" name="${domainClass.propertyName}Edit" showEffect="fadeEff" hideEffect="fadeEff"/>

	</mx:ViewStack>
	
</mx:HBox>
