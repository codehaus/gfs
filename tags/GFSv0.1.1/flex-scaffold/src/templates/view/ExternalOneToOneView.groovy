<?xml version="1.0" encoding="utf-8"?>
<mx:HBox xmlns:mx="http://www.adobe.com/2006/mxml" 
		xmlns:cubikalabs="http://cubikalabs.cub2k.com/2009/commons"
		verticalGap="2" verticalAlign="middle"
		creationComplete="doInit()">
	
	<mx:Script>
			<![CDATA[
				import mx.collections.ArrayCollection;

				import mx.core.Application;
				import mx.core.IFlexDisplayObject

				import mx.managers.PopUpManager;

				import event.AlternativeNavigationEvent;
				import event.${domainClass.propertyName}.${className}CRUDEvent;				
				
				import model.ApplicationModelLocator;
				import model.${domainClass.propertyName}.${domainClass.shortName}Model;
				
				import vo.${domainClass.propertyName}.${className}VO;

				[Bindable]
				private var _model:${domainClass.shortName}Model = ApplicationModelLocator.instance.${domainClass.propertyName}Model;
				
				private var _isSelected:Boolean = false;

				public function get selectedItem():${className}VO
				{
					return ${className}VO(addCombo.selectedItem);
				}
				
				public function set selectedItem(value:${className}VO):void
				{
					addCombo.selectedItem = value;
				}
				
				private function doInit():void
				{
					new ${className}CRUDEvent(${className}CRUDEvent.LIST_EVENT).dispatch();
				}
				
				private function addClickHandler():void
				{
					new ${className}CRUDEvent(${className}CRUDEvent.CREATE_EVENT).dispatch();
					new AlternativeNavigationEvent("${relationDomainClass.propertyName}.edit","${domainClass.propertyName}.edit").dispatch();
					_isSelected = true;
				}
				
				private function selectedLastIndexWhenChange():void
				{
					if (_isSelected)
					{
						_isSelected = false;
						addCombo.selectedIndex = addCombo.dataProvider.length -1;
					}
				}
				
			]]>
		</mx:Script>
		
<%
		import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU
		
		def labelField = CVU.getLabelField(domainClass)
		
		if (labelField)
		{
			println "		<cubikalabs:AddComboBox id=\"addCombo\" dataProvider=\"{_model.listNoPaged}\""+
								" addClick=\"addClickHandler()\" labelField=\"${labelField}\" dataProviderChange=\"selectedLastIndexWhenChange()\"/>"
		}
		else
		{
			println "		<cubikalabs:AddComboBox id=\"addCombo\" dataProvider=\"{_model.listNoPaged}\""+
								" addClick=\"addClickHandler()\" dataProviderChange=\"selectedLastIndexWhenChange()\"/>"
		}
%>
</mx:HBox>
