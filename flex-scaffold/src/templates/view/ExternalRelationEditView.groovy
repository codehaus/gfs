<?xml version="1.0" encoding="utf-8"?>
<mx:TitleWindow xmlns:mx="http://www.adobe.com/2006/mxml"
	xmlns:utils="com.cubika.labs.utils.*"
	xmlns:cubikalabs="http://cubikalabs.cub2k.com/2009/commons"
	showCloseButton="true"
	close="cancel()"
	paddingTop="15"
	paddingBottom="15"
	creationComplete="doInit()">
	
	<mx:Script>
		<![CDATA[
				
			import mx.events.FlexEvent;	
			import mx.validators.Validator;

			import event.AlternativeNavigationEvent;
			import event.${domainClass.propertyName}.${className}CRUDEvent;
			
			import model.${domainClass.propertyName}.${className}Model;	
			import model.ApplicationModelLocator;	
			
			import vo.${domainClass.propertyName}.${className}VO;	
			
			

			[Bindable]
			public var vo:${className}VO;
			
			[Bindable]
			private var _model:${className}Model = ApplicationModelLocator.instance.${domainClass.propertyName}Model;
			
			public var clickOk:Function;
			public var cancel:Function;
			
			private function saveOrUpdate():void
			{
				clickOk.call();
			}
				
			public function getVO():${className}VO
			{
				return addCombo.selectedItem as ${className}VO;
			}
				
			private function doInit():void
			{
				addCombo.setFocus();
				
				removeEventListener(FlexEvent.CREATION_COMPLETE,doInit);
				addEventListener(KeyboardEvent.KEY_UP,keyboardHandler,false,0,true);
				
				if (!_model.listNoPaged || _model.listNoPaged.length < 0)
					new ${className}CRUDEvent(${className}CRUDEvent.LIST_EVENT).dispatch();
				
			}
			
			private function keyboardHandler(e:KeyboardEvent):void
			{
				if (e.keyCode == Keyboard.ESCAPE)
					cancel();
					
				if (e.keyCode == Keyboard.ENTER)
					saveOrUpdate();
			}
			
			private function addClickHandler():void
			{
				new ${className}CRUDEvent(${className}CRUDEvent.CREATE_EVENT).dispatch();
				new AlternativeNavigationEvent("${relationDomainClass.propertyName}.edit","${domainClass.propertyName}.edit").dispatch();
				cancel();
			}
				
		]]>
	</mx:Script>
	
	<mx:Form width="100%">
		<mx:FormItem label="${domainClass.naturalName}" >
<%
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

def labelField = CVU.getLabelField(domainClass)

if (labelField)
{
	println "			<cubikalabs:AddComboBox id=\"addCombo\" dataProvider=\"{_model.listNoPaged}\""+
						" addClick=\"addClickHandler()\" labelField=\"${labelField}\""+
						" selectedItem=\"{vo}\"/>"
}
else
{
	println "			<cubikalabs:AddComboBox id=\"addCombo\" dataProvider=\"{_model.listNoPaged}\""+
						" addClick=\"addClickHandler()\""+
						" selectedItem=\"{vo}\"/>"
}	
%>
		</mx:FormItem>
  </mx:Form>
				
	<mx:HBox width="100%" horizontalAlign="right" paddingRight="15">
		<mx:Button label="Aceptar" click="saveOrUpdate()"/>
		<mx:Button label="Cancelar (ESC)" click="cancel()"/>
	</mx:HBox>
	
</mx:TitleWindow>