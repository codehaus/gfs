<?xml version="1.0" encoding="utf-8"?>
<mx:VBox xmlns:mx="http://www.adobe.com/2006/mxml"
	xmlns:cubikalabs="http://cubikalabs.cub2k.com/2009/commons"
	paddingTop="5">
	
	<mx:Script>
		<![CDATA[
			import event.AlternativeNavigationEvent;
			import event.${domainClass.propertyName}.${className}CRUDEvent;
			import vo.${domainClass.propertyName}.${className}VO;
			import view.${domainClass.propertyName}.external.${className}PopSelect;
			
			import mx.collections.ArrayCollection;
			import mx.core.Application;
			import mx.core.IFlexDisplayObject
			import mx.managers.PopUpManager;
			
			import com.cubika.labs.utils.MultipleRM;
						
			private var editView:IFlexDisplayObject;
			
			private function showEditView():void
			{
				new ${className}CRUDEvent(${className}CRUDEvent.SELECT_EVENT,${className}VO(dg.selectedItem)).dispatch();
				new AlternativeNavigationEvent("${relationDomainClass.propertyName}.edit","${domainClass.propertyName}.edit").dispatch();
			}
			
			private function showNewView():void
			{
				editView =  PopUpManager.createPopUp(DisplayObject(Application.application),${className}PopSelect,true)
				
				Object(editView).clickOk = addOk;
				Object(editView).cancel = cancel;
				Object(editView).clickNew = newOk;
				Object(editView).allowMultipleSelection = false;

				PopUpManager.centerPopUp(editView);
			}
			
			private function addOk():void
			{
				if (Object(editView).selectedItems && Object(editView).selectedItems.length >= 1)
				{
					dg.dataProvider.removeAll();
					dg.dataProvider.addItem(Object(editView).selectedItems.getItemAt(0))
				}
				PopUpManager.removePopUp(editView);
			}
			
			private function newOk():void
			{
				PopUpManager.removePopUp(editView);
				new ${className}CRUDEvent(${className}CRUDEvent.CREATE_EVENT,${className}VO(dg.selectedItem)).dispatch();
				new AlternativeNavigationEvent("${relationDomainClass.propertyName}.edit","${domainClass.propertyName}.edit").dispatch();
			}
			
			private function cancel():void
			{
				PopUpManager.removePopUp(editView);
			}
			
			private function deleteItem():void
			{
				dg.dataProvider.removeItemAt(0);
			}
			
			public function get selectedItem():${className}VO
			{
				var valueObject:${className}VO;
				
				if (dg.dataProvider.length >= 1)
					valueObject = dg.dataProvider.getItemAt(0)

				return valueObject;
			}
			
			public function set selectedItem(value:${className}VO):void
			{
				if (!dg.dataProvider)
					dg.dataProvider = new ArrayCollection();
				
				dg.dataProvider.removeAll()
				
				if (value)
					dg.dataProvider.addItem(value);
			}	
		]]>
	</mx:Script>
	
	<mx:HBox width="100%"  verticalAlign="middle">
		<mx:Button toolTip="{MultipleRM.getString(MultipleRM.localePrefix,'generic.create')}" click="showNewView()" styleName="createButton" enabled="{dg.dataProvider.length &lt; 1}"/>
		<mx:Button toolTip="{MultipleRM.getString(MultipleRM.localePrefix,'generic.delete')}" enabled="{dg.dataProvider.length == 1}" click="deleteItem()" styleName="deleteButton"/>
		<mx:Label text="{MultipleRM.getString(MultipleRM.localePrefix,'generic.doubleClickEdit')}." styleName="commentsText" visible="{dg.height>38}"/>
	</mx:HBox>
		
		<cubikalabs:CBKDataGrid width="100%" height="100%" id="dg" itemDoubleClick="showEditView()" 
			doubleClickEnabled="true" variableHeight="true" rowCount="1">
			<cubikalabs:columns>
<%			import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
				import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

				def props = FSU.getPropertiesWithoutIdentity(domainClass,true)

				props.each 
				{
						def gridcolumn = FSU.getDataGridColumn(it)
						if (gridcolumn)
							print "				$gridcolumn"
				}
				%>		</cubikalabs:columns>
		</cubikalabs:CBKDataGrid>
	
	
</mx:VBox>