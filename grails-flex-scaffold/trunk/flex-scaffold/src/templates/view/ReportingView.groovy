<?xml version="1.0" encoding="utf-8"?>
<%
import 	org.cubika.labs.scaffolding.reporting.filter.ReportingFilterBuilder as RepFilBuilder
import 	org.cubika.labs.scaffolding.reporting.column.ReportingColumnBuilder as RepColBuilder

String formFilterId = "${domainClass.propertyName}ReportingFilterForm"
String formColumnId = "${domainClass.propertyName}ReportingColumnForm"

%>
<mx:VBox xmlns:mx="http://www.adobe.com/2006/mxml"
	creationComplete="doInit()" height="100%" width="100%"
		xmlns:cubikalabs="http://cubikalabs.cub2k.com/2009/commons">
	
	<mx:Script>
		<![CDATA[
			import com.cubika.labs.viewers.PDFViewer;
			import flash.net.navigateToURL;
			import com.cubika.labs.utils.MultipleRM;
		    import com.cubika.labs.controls.reporting.column.ReportingColumnDescriptor;
			import com.cubika.labs.controls.reporting.filter.IBaseFilterControl
			import model.ApplicationModelLocator;
			import model.${domainClass.propertyName}.${className}ReportingModel;
			
			[Bindable]private var _model:${className}ReportingModel = ApplicationModelLocator.instance.${domainClass.propertyName}ReportingModel;
			 
			private var _reportUrl:String;
			
			private function doInit():void
			{
				initURLRequest();
				addEventListener(KeyboardEvent.KEY_UP,doKeyBoardUp,false,0,true);	
			}
			
			private function doKeyBoardUp(e:KeyboardEvent):void
			{
				if(e.keyCode == Keyboard.ENTER)
					doReport(e);	
			}

			
			private function doReport(e:Event):void
			{
				initURLRequest();
				doFilterReport();
				doColumnReport();
				pdfViewer.source = _reportUrl;
			}
			
			private function initURLRequest():void
			{
				reportUrl = "./gfsDjReport/?entity=${domainClass.propertyName}&";
			}
			
			private function doFilterReport():void
			{
				for each(var formChild:Object in ${formFilterId}.getChildren())
				{
					var filter:IBaseFilterControl = formChild as IBaseFilterControl;
					if(filter)
					{
						_reportUrl += filter.buildRequest();
					}
				}
			}

			private function doColumnReport():void
			{
				var columns:String = "columns=";
				var columnTitles:String = "columnTitles=";
				for each(var columnDescriptor:ReportingColumnDescriptor in ${formColumnId}.dataProvider)
				{
					columns += columnDescriptor.getColumn();
					columnTitles += columnDescriptor.getColumnTitle();
				}
				columns = columns.substr(0,columns.length-1);
				columnTitles = columnTitles.substr(0,columnTitles.length-1);
				_reportUrl += columns+"&"+columnTitles;
	
			}

			private function doReset(e:Event):void
			{
				doResetFilter();
				doResetColumn();
			}
			
			private function doResetFilter():void
			{
				for each(var formChild:Object in ${formFilterId}.getChildren())
				{
					var filter:IBaseFilterControl = formChild as IBaseFilterControl;
					if(filter)
					{
						filter.reset();
					}						
				}
			}
			
			private function doResetColumn():void
			{
				for each(var columnDescriptor:ReportingColumnDescriptor in ${formColumnId}.dataProvider)
				{
					if(columnDescriptor)
					{
						columnDescriptor.reset();
					}						
				}
			}
			
			private function savePdfHandler(e:Event):void
			{
				var pdfFileName:String = "CustomerReport.pdf";
				reportUrl = "./djReport/?entity=${domainClass.propertyName}&fileName="+pdfFileName;
				doFilterReport();
				doColumnReport();
				navigateToURL(new URLRequest(reportUrl),"_blank");
			}
			
			public function set reportUrl(value:String):void
			{
				_reportUrl = value;
			}
			
			public function get reportUrl():String
			{
				return _reportUrl;
			}
		]]>		
	</mx:Script>
	
	<mx:HDividedBox width="100%" height="100%">
		<mx:VBox height="100%" styleName="reportingFiltersContainer">
			<mx:Accordion id="tn" width="100%" height="100%" 
				creationPolicy="all" styleName="repotingAccordion">
<% println RepFilBuilder.buildReportingFilterForm(domainClass,className,formFilterId) %>	        
				<mx:Box label="{MultipleRM.getString(MultipleRM.localePrefix, 'reporting.accordion.labels.columns')}" width="100%" height="100%" styleName="reportingfiltersListContainer">
<% println RepColBuilder.buildReportingColumnForm(domainClass,className,formColumnId) %>
				</mx:Box>
			</mx:Accordion>
			<mx:HBox width="{tn.width}" horizontalAlign="right">
				<mx:Button id="resetFilterButton" label="{MultipleRM.getString(MultipleRM.localePrefix, 'reporting.buttons.resetfilter')}" click="doReset(event)"/>
				<mx:Button id="doReportButton" label="{MultipleRM.getString(MultipleRM.localePrefix, 'reporting.buttons.doreport')}" click="doReport(event)"/>	
			</mx:HBox>
		</mx:VBox>
		<cubikalabs:PDFViewer id="pdfViewer" width="80%" height="100%" savePdf="savePdfHandler(event)" />
	</mx:HDividedBox>
</mx:VBox>
