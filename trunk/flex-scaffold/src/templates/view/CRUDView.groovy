<?xml version="1.0" encoding="utf-8"?>
<%
import org.cubika.labs.scaffolding.buttonbar.ButtonBarBuilder as BBBuilder
import org.cubika.labs.scaffolding.reporting.filter.ReportingFilterBuilder as RepBuilder
%>
<mx:VBox xmlns:mx="http://www.adobe.com/2006/mxml" xmlns:view="view.${domainClass.propertyName}.*"
	creationComplete="doInit()">
	
	<mx:Script>
		<![CDATA[

			import com.cubika.labs.utils.MultipleRM;
			import mx.core.Container;
			import mx.collections.ArrayCollection;
<% println BBBuilder.getImports(domainClass)%>
			
			import model.ApplicationModelLocator;
			import model.${domainClass.propertyName}.${className}Model;
			
			[Bindable]
			private var _model:${className}Model = ApplicationModelLocator.instance.${domainClass.propertyName}Model;
<% println BBBuilder.getProperties(domainClass)%>
			private var _selectedChildBeforeInit:String;
			
			private function doInit():void
			{
<%	println BBBuilder.getInitFunctionCall(domainClass)%>
				if (_selectedChildBeforeInit)
					selectedView = _selectedChildBeforeInit;
			}
<% println BBBuilder.getMethods(domainClass)%>
			private function changeHandler():void
			{
				vs.selectedChild.setFocus();
			}
			
			override public function setFocus():void
			{
				vs.selectedChild.setFocus();
			}
			
			public function set selectedView(value:String):void
			{
				if (vs)
					vs.selectedChild = Container(vs.getChildByName(value));
				else
					_selectedChildBeforeInit = value;
			}
			
		]]>
	</mx:Script>
	
	<mx:Fade id="fadeEff" duration="200"/>	
	<mx:ViewStack id="vs" selectedIndex="{_model.selectedIndexView}" change="changeHandler()" 
		width="100%" height="100%">
		
		<view:${className}ListView width="100%" height="100%" label="{MultipleRM.getString(MultipleRM.localePrefix, 'reporting.tabs.crud')}" hideEffect="fadeEff" showEffect="fadeEff" 
		name="${domainClass.propertyName}List" />

		<view:${className}EditView  height="100%" width="100%" showEffect="fadeEff" hideEffect="fadeEff"
		 name="${domainClass.propertyName}Edit" />
<%	println	RepBuilder.buildReportingViewTag(domainClass)%>	
	</mx:ViewStack>
<%	println BBBuilder.getComponent(domainClass) %>
	
</mx:VBox>
