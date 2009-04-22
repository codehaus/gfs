<?xml version="1.0" encoding="utf-8"?>
<mx:TitleWindow xmlns:mx="http://www.adobe.com/2006/mxml"
	xmlns:cubikalabs="http://cubikalabs.cub2k.com/2009/commons"
	xmlns:utils="com.cubika.labs.utils.*"
	showCloseButton="true"
	close="cancel()"
	paddingTop="15"
	paddingBottom="15"
	creationComplete="doInit()" 
	title="{resourceManager.getString(appModel.localePrefix,'${domainClass.propertyName}.label')}">
	
	<mx:Script>
		<![CDATA[
				
			import mx.events.FlexEvent;	
			import mx.validators.Validator;
			
			import com.cubika.labs.utils.MultipleRM;
			
			import model.ApplicationModelLocator;

			import vo.${domainClass.propertyName}.${className}VO;	
<%
			import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU

			  def props = FSU.getPropertiesWithoutIdentity(domainClass,true)																										
																
				props.each {
					if (it.isOneToOne())
						println "			${FSU.getImport4AS3(it)}"
				}	
%>
			[Bindable]
			public var vo:${className}VO;
			
			[Bindable]
			public var appModel:ApplicationModelLocator = ApplicationModelLocator.instance;
			
			public var clickOk:Function;
			public var cancel:Function;
			
			private function saveOrUpdate():void
			{
				if (validators.length >= 0 && Validator.validateAll(validators).length == 0)
				{
					clickOk.call();
				}
			}
				
			public function getVO():${className}VO
			{
				var _vo:${className}VO = vo;					

<%
				List builders = []
				props.each 
				{
					def buildFormItem = BFIF.createFormItem(it)
					if (buildFormItem)
					{
						builders.add(buildFormItem)
						println "					_vo.${it.name} = ${buildFormItem.getFormAttr()};"
					}
				}
%>
				return _vo;
			}
				
			private function doInit():void
			{
				${builders.getAt(0).getID()}.setFocus();
				
				removeEventListener(FlexEvent.CREATION_COMPLETE,doInit);
				addEventListener(KeyboardEvent.KEY_UP,keyboardHandler,false,0,true);
			}
			
			private function keyboardHandler(e:KeyboardEvent):void
			{
				if (e.keyCode == Keyboard.ESCAPE)
					cancel();
					
				if (e.keyCode == Keyboard.ENTER)
					saveOrUpdate();
			}
				
		]]>
	</mx:Script>
	
	<mx:Array id="validators">
<%	
		builders.each { buildFormItem ->
			def validator = buildFormItem.buildValidator()
			if (validator)
				println "		${validator}"
		}
%>	</mx:Array>
	
	
	<mx:Form width="100%">
<%	import org.cubika.labs.scaffolding.form.factory.BuildFormItemFactory as BFIF
			builders.each { buildFormItem ->
					print "${buildFormItem.build("vo.${buildFormItem.property.name}")}"
			}
%>	</mx:Form>
				
	<mx:HBox width="100%" horizontalAlign="right" paddingRight="15">
		<mx:Button label="{MultipleRM.getString(MultipleRM.localePrefix,'generic.accept')}" click="saveOrUpdate()"/>
		<mx:Button label="{MultipleRM.getString(MultipleRM.localePrefix,'generic.cancel')} (ESC)" click="cancel()"/>
	</mx:HBox>
	
</mx:TitleWindow>
