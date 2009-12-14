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
			
			import com.cubika.labs.utils.MultipleRM;
			
			import model.ApplicationModelLocator;
			import model.${domainClass.propertyName}.${className}Model;
			
			import event.${domainClass.propertyName}.${className}CRUDEvent;
			import event.${domainClass.propertyName}.${className}GetPaginationEvent;
			
			import vo.${domainClass.propertyName}.${className}VO;
						
			[Bindable]
			public var appModel:ApplicationModelLocator = ApplicationModelLocator.instance;
						
			[Bindable]
			private var _model:${className}Model = ApplicationModelLocator.instance.${domainClass.propertyName}Model;
			
			
			private function doInit():void
			{
				tabEnabled = true;
				new ${className}GetPaginationEvent(_model.page).dispatch();
				
				addEventListener(KeyboardEvent.KEY_UP,keyboardHandler,false,0,true);
			}
			
			private function create():void
			{
				new ${className}CRUDEvent(${className}CRUDEvent.CREATE_EVENT).dispatch()
			}
<% import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU %>			
			private function destroy():void
			{
				var aux:Array = [];
				<%	if (CVU.multiselection(domainClass))
						{
							println "for each (var item:${className}VO in dg.dataProvider)"
							println	"				{"
							println	"					if (item.selectedCheck)"
							println	"					{"
							println	"						aux.push(item)"
							println "					}"
							println	"				}"
						}
						else
						{
							println "if (dg.selectedItem)"
							println "					aux.push(dg.selectedItem)"
						}
				%>
				if (aux.length > 0)
				{
					var e:${className}CRUDEvent = new ${className}CRUDEvent(${className}CRUDEvent.DELETE_EVENT);
				
					e.vos = aux;
				
					e.dispatch();
				}
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
		]]>
	</mx:Script>
	
	<mx:VBox
		styleName="listContainer"
		width="100%"
		height="100%"
		>
		<cubikalabs:PaginationView width="100%"
			pageFilter="{_model.page}"
			event="{event.${domainClass.propertyName}.${className}GetPaginationEvent}"
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
			import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
			
			if (CVU.multiselection(domainClass))
			{
				println "				<mx:DataGridColumn itemRenderer=\"com.cubika.labs.renders.CheckItemRender\" width=\"20\" headerRenderer=\"com.cubika.labs.renders.CheckGridHeader\" sortable=\"false\" dataField=\"selectedCheck\"/>"
			}
			
			def props = FSU.getPropertiesWithoutIdentity(domainClass,true)
			props.each 
			{
					def gridcolumn = FSU.getDataGridColumn(it)
					if (gridcolumn)
						print "				$gridcolumn"
			}
			
			def actions = CVU.actions(domainClass)
			
			actions.each
			{
				println "				<mx:DataGridColumn headerText=\"{MultipleRM.getString(MultipleRM.localePrefix,'${domainClass.propertyName}.${it.toLowerCase()}')}\" sortable=\"false\" itemRenderer=\"view.${domainClass.propertyName}.renderers.${className}${it}ItemRenderer\"/>"
			}
			%>			</mx:columns>
			
		</mx:DataGrid>
	</mx:VBox>
	
	<mx:HBox width="100%"
		includeInLayout="false"
		styleName="listContainerButtons"
		y="-2">
		<mx:Button label="{MultipleRM.getString(MultipleRM.localePrefix,'generic.create')} (F2)" click="create()" styleName="orangeButton"/>
		<mx:Button label="{MultipleRM.getString(MultipleRM.localePrefix,'generic.delete')} (F4)" click="destroy()" styleName="orangeButton"/>
	</mx:HBox>
	
</mx:Canvas>