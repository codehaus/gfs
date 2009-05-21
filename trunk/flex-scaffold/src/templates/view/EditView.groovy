<?xml version="1.0" encoding="utf-8"?><%
import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
def props = FSU.getPropertiesWithoutIdentity(domainClass,true)%>
<mx:VBox xmlns:mx="http://www.adobe.com/2006/mxml"
	xmlns:utils="com.cubika.labs.utils.*"
	xmlns:cubikalabs="http://cubikalabs.cub2k.com/2009/commons"
	creationComplete="doInit()"
	paddingLeft="10" paddingTop="10" paddingRight="10"
	horizontalAlign="center"<% println " ${FSU.getNameSpace(props)}>" %>
	
	<mx:Script>
		<![CDATA[
			
			import mx.binding.utils.BindingUtils;
			
			import com.cubika.labs.utils.MultipleRM;
			
			import event.DefaultNavigationEvent;
			import event.${domainClass.propertyName}.${className}CRUDEvent;
				
			import model.ApplicationModelLocator;
			import model.${domainClass.propertyName}.${className}Model;
				
			import mx.validators.Validator;

			import vo.${domainClass.propertyName}.${className}VO;	
<%												
				props.each {
					def imp = FSU.getImport4AS3(it)
					
					if ((it.isOneToOne() || it.isManyToOne()) && imp)
						println "			${imp}"
				}	
%>
			[Bindable]
			public var appModel:ApplicationModelLocator = ApplicationModelLocator.instance;
			
			[Bindable]
			private var _model:${className}Model = <% println "ApplicationModelLocator.instance.${domainClass.propertyName}Model;" %>
				
			private function saveOrUpdate():void
			{
				if (validators.length >= 0 && Validator.validateAll(validators).length == 0)
				{
					var _vo:${className}VO = _model.selected;	
					
					if (!_vo)
						_vo = new ${className}VO();				

<%							import org.cubika.labs.scaffolding.form.factory.BuildFormItemFactory as BFIF
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
					new ${className}CRUDEvent(${className}CRUDEvent.SAVE_OR_UPDATE_EVENT,_vo).dispatch();
				}
			}
			
			private function cancel():void
			{
				new ${className}CRUDEvent(${className}CRUDEvent.CANCEL_EVENT).dispatch();
			}

			private function doInit():void
			{		
                if (_model.callFromPop)
				{
					statePopup();
				}

				addEventListener(KeyboardEvent.KEY_UP,keyboardHandler,false,0,true);
				BindingUtils.bindSetter(changeState,_model,"editView");
				setFocus();
        	}
			
			override public function setFocus():void
			{
				if (${builders.getAt(0).getID()})
					${builders.getAt(0).getID()}.setFocus();
			}
			
			private function statePopup():void
			{
				formContainer.styleName = "";
				formContainer.removeChild(lb${className});
				form.setStyle("paddingTop",0);
				form.setStyle("paddingBottom",10);
				form.setStyle("paddingLeft",0);
				form.setStyle("paddingRight",0);
				setStyle("paddingTop",2);
				setStyle("paddingBottom",10);
				setStyle("paddingLeft",10);
				setStyle("paddingRight",10);
			}

			private function keyboardHandler(e:KeyboardEvent):void
			{
				if (e.keyCode == Keyboard.ESCAPE)
					cancel();
				
				if (e.keyCode == Keyboard.F8)
					saveOrUpdate();
			}
				
			private function changeState(editView:Boolean):void
			{
				currentState = _model.state;
			}
				
		]]>
	</mx:Script>
	
	<mx:states>
		<mx:State name="createView">
<%	
		builders.each { buildFormItem ->
			def removeChild = buildFormItem.getRemoveChildCreateView()
			if (removeChild)
				println "			${removeChild}"
		}
%>	
		</mx:State>		
		<mx:State name="editView">
<%	
		builders.each { buildFormItem ->
			def removeChild = buildFormItem.getRemoveChildEditView()
			if (removeChild)
				println "			${removeChild}"
		}
%>
		</mx:State>
	</mx:states>
	
	<mx:Array id="validators">
<%	
		builders.each { buildFormItem ->
			def validator = buildFormItem.buildValidator()
			if (validator)
				println "		${validator}"
		}
%>	</mx:Array>
	
	<mx:VBox id="formContainer" styleName="formContainer">
		<mx:Label text="{MultipleRM.getString(MultipleRM.localePrefix,'${domainClass.propertyName}.label')}" styleName="titleForm" width="100%" id="lb${className}"/>
		<mx:Form width="100%" id="form">
<%	
			builders.each { buildFormItem ->
					print "${buildFormItem.build("_model.selected.${buildFormItem.property.name}")}"
			}
%>		</mx:Form>
				
		<mx:HBox width="100%" horizontalAlign="right">
			<mx:Button label="{MultipleRM.getString(MultipleRM.localePrefix,'generic.submit')} (F8)" click="saveOrUpdate()"/>
			<mx:Button label="{MultipleRM.getString(MultipleRM.localePrefix,'generic.cancel')} (ESC)" click="cancel()"/>
		</mx:HBox>
	</mx:VBox>

</mx:VBox>
