<?xml version="1.0" encoding="utf-8"?>
<mx:Canvas xmlns:mx="http://www.adobe.com/2006/mxml"
	xmlns:cubikalabs="http://cubikalabs.cub2k.com/2009/commons"
	creationComplete="doInit()"
	clipContent="false"
	>
	<mx:Script>
		<![CDATA[
		
			import mx.collections.ArrayCollection;
			import mx.formatters.DateFormatter;
			
			import model.ApplicationModelLocator;
			import model.${domainClass.propertyName}.${className}Model;
			
			import event.${domainClass.propertyName}.${className}CRUDEvent;
			import event.${domainClass.propertyName}.Get${className}PaginationEvent;
			
			import vo.${domainClass.propertyName}.${className}VO;
						
			[Bindable]
			private var _model:${className}Model = ApplicationModelLocator.instance.${domainClass.propertyName}Model;
			
			
			private function doInit():void
			{
				tabEnabled = true;
				new Get${className}PaginationEvent(_model.page).dispatch();
				
				addEventListener(KeyboardEvent.KEY_UP,keyboardHandler,false,0,true);
			}
			
			private function create():void
			{
				new ${className}CRUDEvent(${className}CRUDEvent.CREATE_EVENT).dispatch()
			}
			
			private function destroy():void
			{
				if (dg.selectedItem)
					new ${className}CRUDEvent(${className}CRUDEvent.DELETE_EVENT,dg.selectedItem as ${className}VO).dispatch();
			}
			
			private function edit():void
			{
				if (dg.selectedItem)
					new ${className}CRUDEvent(${className}CRUDEvent.SELECT_EVENT,dg.selectedItem as ${className}VO).dispatch()
			}
			
			private function keyboardHandler(e:KeyboardEvent):void
			{
				if (e.keyCode == Keyboard.F2)
					create();
				
				if (e.keyCode == Keyboard.F3)
					edit();
				
				if (e.keyCode == Keyboard.F4)
					destroy();
					
			}

<%			
			import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
			import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

			def props = FSU.getPropertiesWithoutIdentity(domainClass,true)
			
			props.each
			{
				if (it.type == Date.class && CVU.display(it))
				{
					
					def format = CVU.dateFormat(it)
					
					println "			private function ${it.name}Formatter(item:Object, column:DataGridColumn):String"
					println "			{"
					println "				var formatter:DateFormatter = new DateFormatter();"
					println "				formatter.formatString = \"${format}\";"
					println "				return formatter.format(${className}VO(item).${it.name});"
					print		"			}"
				}
			}
%>
		]]>
	</mx:Script>
	
	<mx:VBox
		styleName="listContainer"
		width="100%"
		height="100%"
		>
		<cubikalabs:PaginationView width="100%"
			pageFilter="{_model.page}"
			event="{event.${domainClass.propertyName}.Get${className}PaginationEvent}"
			/>	
		<mx:Spacer height="0"/>
		<mx:DataGrid dataProvider="{_model.page.list}"
			width="100%" height="100%"
			id="dg" 
			itemDoubleClick="edit()"
			doubleClickEnabled="true"
			toolTip="Press F3 to edit or double click in the item of the grid">
			<mx:columns>
<%		
			props.each 
			{
					print "			${FSU.getDataGridColumn(it)}"
			}
			%>		</mx:columns>
		</mx:DataGrid>
	</mx:VBox>
	
	<mx:HBox width="100%"
		includeInLayout="false"
		styleName="listContainerButtons"
		y="-2">
		<mx:Button label="Create (F2)" click="create()" styleName="orangeButton"/>
		<mx:Button label="Delete (F4)" enabled="{dg.selectedItem}" click="destroy()" styleName="orangeButton"/>
	</mx:HBox>
	
</mx:Canvas>