<?xml version="1.0" encoding="utf-8"?>
<mx:TitleWindow xmlns:mx="http://www.adobe.com/2006/mxml" 
	xmlns:cubikalabs="http://cubikalabs.cub2k.com/2009/commons"
	layout="absolute" creationComplete="doInit()" 
	showCloseButton="true"
	close="cancel()"
	title="${typeName}">

	<mx:Script>
		<![CDATA[
			import event.${domainClass.propertyName}.${className}ExternalGetPaginationEvent;
			import event.AlternativeNavigationEvent;
			import com.cubika.labs.utils.MultipleRM;
			import mx.events.FlexEvent;
			import mx.binding.utils.BindingUtils;
			import mx.collections.ArrayCollection;
			import mx.managers.PopUpManager;
			import model.ApplicationModelLocator;
			import model.${domainClass.propertyName}.${className}Model;
			import event.${domainClass.propertyName}.${className}CRUDEvent;
			
			[Bindable]
			private var _model:${className}Model = ApplicationModelLocator.instance.${domainClass.propertyName}Model;
			private var _selectedItems:ArrayCollection;
			
			public var clickOk:Function;
			public var cancel:Function;
			public var clickNew:Function;
			
			[Bindable]
			public var allowMultipleSelection:Boolean=false;

            [Bindable]
            public var allowCreation:Boolean = true;
			
			private function doInit():void
			{
				_model.externalpage.reset();
				new ${className}ExternalGetPaginationEvent(_model.externalpage).dispatch();
				dg.setFocus();
				
				removeEventListener(FlexEvent.CREATION_COMPLETE,doInit);
				addEventListener(KeyboardEvent.KEY_UP,keyboardHandler,false,0,true);
			}
			
			private function okHandler():void
			{
				_selectedItems = new ArrayCollection(dg.selectedItems);
				clickOk.call()
			}
			
			private function cancelHandler():void
			{
				cancel.call();
			}
			
			private function keyboardHandler(e:KeyboardEvent):void
			{
				if (e.keyCode == Keyboard.ESCAPE)
					cancel();
					
				if (e.target.document.className == "PaginationLightView" && e.keyCode != Keyboard.F8 && e.keyCode != Keyboard.F2)
				{
					e.preventDefault();
					e.stopImmediatePropagation();
					return
				}	
					
				if (e.keyCode == Keyboard.ENTER || e.keyCode == Keyboard.F8)
					okHandler();
				
				if (e.keyCode == Keyboard.F2)
					newHandler();
			}
			
			private function newHandler():void
			{
				clickNew();
			}
			
			public function get selectedItems():ArrayCollection
			{
				return _selectedItems;
			}
			
		]]>
	</mx:Script>

	<mx:VBox paddingTop="2" paddingBottom="10"	paddingLeft="10" paddingRight="10">
		<cubikalabs:PaginationLightView width="100%"
					pageFilter="{_model.externalpage}" event="{event.${domainClass.propertyName}.${className}ExternalGetPaginationEvent}" paddingBottom="10"/>

		<cubikalabs:CBKDataGrid dataProvider="{_model.externalpage.list}" allowMultipleSelection="{allowMultipleSelection}" id="dg" 
		variableHeight="true" rowCount="8" doubleClickEnabled="true" doubleClick="okHandler()">
			<cubikalabs:columns>
<%			import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
				import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

					def props = FSU.getPropertiesWithoutIdentity(domainClass,true)

					props.each 
					{
							def gridcolumn = FSU.getDataGridColumn(it)
							if (gridcolumn)
								print "			$gridcolumn"
					}
					%>	</cubikalabs:columns>
		</cubikalabs:CBKDataGrid>
		
		<mx:HBox width="100%" horizontalAlign="right" paddingTop="10">
			<mx:Button label="{MultipleRM.getString(MultipleRM.localePrefix,'generic.new')} (F2)" click="newHandler()" enabled="{allowCreation}"/>
			<mx:Spacer width="100%"/>
			<mx:Button label="{MultipleRM.getString(MultipleRM.localePrefix,'generic.accept')} (F8)" click="okHandler()" enabled="{dg.selectedItems != null}"/>
			<mx:Button label="{MultipleRM.getString(MultipleRM.localePrefix,'generic.cancel')} (ESC)" click="cancelHandler()"/>
		</mx:HBox>	
	</mx:VBox>
	
	
</mx:TitleWindow>
