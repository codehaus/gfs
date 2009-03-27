<?xml version="1.0" encoding="utf-8"?>
<mx:VBox xmlns:mx="http://www.adobe.com/2006/mxml"
	xmlns:cubikalabs="http://cubikalabs.cub2k.com/2009/commons"
	paddingTop="5">
	
	<mx:Script>
		<![CDATA[
		
			import mx.collections.ArrayCollection;
			import mx.formatters.DateFormatter;
			
			import mx.core.Application;
			import mx.core.IFlexDisplayObject
			
			import mx.managers.PopUpManager;
			
			import vo.${domainClass.propertyName}.${className}VO;
			
			[Bindable]
			public var dataProvider:ArrayCollection;
			
			private var editView:IFlexDisplayObject;
			
			private function showEditView():void
			{
				editView = PopUpManager.createPopUp(DisplayObject(Application.application),view.${typeName}.${domainClass.propertyName}.${className}RelationEditView,true)
				
				Object(editView).clickOk = editOk;
				Object(editView).cancel = cancel;
				Object(editView).vo = dg.selectedItem as ${className}VO;
				
				PopUpManager.centerPopUp(editView);
			}
			
			private function showNewView():void
			{
				editView = PopUpManager.createPopUp(DisplayObject(Application.application),view.${typeName}.${domainClass.propertyName}.${className}RelationEditView,true)
				
				Object(editView).clickOk = newOk;
				Object(editView).cancel = cancel;
				Object(editView).vo = new ${className}VO;
				
				PopUpManager.centerPopUp(editView);
			}
			
			private function newOk():void
			{
				PopUpManager.removePopUp(editView);
				
				if (!dataProvider)
					dataProvider = new ArrayCollection();
				
				dataProvider.addItem(Object(editView).getVO());
			}
			
			private function editOk():void
			{
				PopUpManager.removePopUp(editView);
				
				var _vo:${className}VO = Object(editView).getVO();
				var i:int = 0;
				
				for each (var item:Object in dataProvider)
				{
					if (item.id == _vo.id)
					{
						dataProvider.removeItemAt(i);
						dataProvider.addItemAt(_vo,i)
						return
					}	
					i++;
				}
				
			}
			
			private function cancel():void
			{
				PopUpManager.removePopUp(editView);
			}
			
			private function deleteItem():void
			{
				dataProvider.removeItemAt(dg.selectedIndex);
			}
			
			public function getVO():ArrayCollection
			{
				return dataProvider;
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
	
	<mx:HBox width="100%">
		<mx:Button toolTip="Create" click="showNewView()" styleName="createButton"/>
		<mx:Button toolTip="Delete" enabled="{dg.selectedItem}" click="deleteItem()" styleName="deleteButton"/>
		<mx:Label text="Double click for edit." styleName="commentsText" visible="{dg.height>38}"/>
	</mx:HBox>
		
	<cubikalabs:CBKDataGrid dataProvider="{dataProvider}" width="100%" height="100%" id="dg" itemDoubleClick="showEditView()" 
		doubleClickEnabled="true" variableHeight="true" rowCount="8">
		<cubikalabs:columns>
<%		import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
			
			props.each 
			{
					if (!it.isManyToOne())
						print "			${FSU.getDataGridColumn(it)}"
			}
			%>		</cubikalabs:columns>
	</cubikalabs:CBKDataGrid>
	
	
</mx:VBox>