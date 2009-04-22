<?xml version="1.0" encoding="utf-8"?>
<mx:HBox xmlns:mx="http://www.adobe.com/2006/mxml" verticalGap="2" verticalAlign="middle">
	<mx:Script>
			<![CDATA[
				import mx.collections.ArrayCollection;
				
				import com.cubika.labs.utils.MultipleRM;

				import mx.core.Application;
				import mx.core.IFlexDisplayObject

				import mx.managers.PopUpManager;
				
				import model.ApplicationModelLocator;

				import vo.${domainClass.propertyName}.${className}VO;

				[Bindable]
				public var vo:${className}VO;
				
				[Bindable]
				public var appModel:ApplicationModelLocator = ApplicationModelLocator.instance;

				private var editView:IFlexDisplayObject;

				private function showEditView():void
				{
					editView = PopUpManager.createPopUp(DisplayObject(Application.application),view.${typeName}.${domainClass.propertyName}.${className}RelationEditView,true)

					Object(editView).clickOk = editOk;
					Object(editView).cancel = cancel;
					Object(editView).vo = vo;

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

					vo = Object(editView).getVO();
				}

				private function editOk():void
				{
					PopUpManager.removePopUp(editView);

					vo = Object(editView).getVO();
				}

				private function cancel():void
				{
					PopUpManager.removePopUp(editView);
				}

				private function deleteItem():void
				{
					vo = null;
				}

				public function getVO():${className}VO
				{
					return vo;
				}
			]]>
		</mx:Script>
<%
	import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
	def props = FSU.getPropertiesWithoutIdentity(domainClass,true)
	
//	def properties = ""
//	props.eachWithIndex { it, i ->
//		if (props.length-1 > i)
//			properties +="' ${it.name}: ' +vo.${it.name}+ "
//		else
//			properties +="' ${it.name}: ' +vo.${it.name}"
//	}
%>	
	<mx:Button toolTip="{(vo) ? MultipleRM.getString(MultipleRM.localePrefix,'generic.edit') : resourceManager.getString(appModel.localePrefix,'generic.create')}"
		styleName="{(vo) ? 'editButton' : 'createButton'}"
		click="{(vo) ? showEditView() : showNewView()}"/>
	<mx:HBox styleName="oneTooneViewLabelContainer"
		visible="{vo != null}"
		>
		<%
			props.eachWithIndex { it, i ->
			if (props.length-1 > i)
			{
				println "		<mx:Label text=\"{MultipleRM.getString(MultipleRM.localePrefix,'${domainClass.propertyName}.${it.name}')}:\"/>"
				println "		<mx:Label text=\"{vo.${it.name}}\" styleName=\"dataText\"/>"
				}
			else
			{
				println "		<mx:Label text=\"{MultipleRM.getString(MultipleRM.localePrefix,'${domainClass.propertyName}.${it.name}')}:\"/>"
				println "		<mx:Label text=\"{vo.${it.name}}\" styleName=\"dataText\"/>"
				}
			}
		%>
	</mx:HBox>
</mx:HBox>
