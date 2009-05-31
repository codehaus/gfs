package org.cubika.labs.scaffolding.buttonbar

//
// Copyright 2009 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU 

/**
 * 
 * @author Gonzalo Clavell
 * @since  2-Mar-2009
 */
class ButtonBarBuilder
{
	/**
	 *
	 * @param 
	 * @return
	 */
	static String getImports(domainClass)
	{
		if(!CVU.isReportable(domainClass))
			return ""
		return "			import mx.events.ItemClickEvent"	
	}

	/**
	 *
	 * @param 
	 * @return
	 */
	static String getProperties(domainClass)
	{
		String result = "\n"
		if(CVU.isReportable(domainClass))
		{
			result =  "			//Control Panel properties\n"
			result += "			[Bindable]\n" 
			result += "			private var _controlPanelDataProvider:Array;\n" 
			result += "			//End Control Panel properties\n"
		}
		result
	}

	/**
	 *
	 * @param 
	 * @return
	 */
	static String getComponent(domainClass)
	{
		def String result = "\n"
		if(CVU.isReportable(domainClass))
		{
			result += "	  <!-- Control Panel View -->\n"
			result += "   <mx:Canvas clipContent=\"false\" height=\"0\" includeInLayout=\"false\">\n"
			result += "   <mx:ToggleButtonBar id=\"controlPanel\" y=\"-6\" x=\"6\" dataProvider=\"{_controlPanelDataProvider}\" itemClick=\"controlPanelClickHandler(event)\" styleName=\"reportingToogleButtonBar\"/>\n"
			result += "   </mx:Canvas>\n"
			result += "	  <!-- End Control Panel View -->"	
		}
		result
	}
	
	/**
	 *
	 * @param 
	 * @return
	 */
	static String getInitFunctionCall(domainClass)
	{
		def String result = ""
		if(CVU.isReportable(domainClass))
		{
			result  = "				//ControlPanel init function\n"
			result += "				initControlPanel();" 
		}
		result	
	}
	
	/**
	 *
	 * @param 
	 * @return
	 */
	static String getMethods(domainClass)
	{
		def String result = "\n"
		if(CVU.isReportable(domainClass))
		{	
			
			result  = "			//Control Panel Methods\n"+
						getInitFunctionMethod(domainClass)+
						getClickHandler(domainClass)+
						"			//End Control Panel Methods"
				
		}
		result
	}
	
	/**
	 *
	 * @param 
	 * @return
	 */
	static String getInitFunctionMethod(domainClass)
	{
		def String result = "\n"
		result += "			private function initControlPanel():void\n"
		result += "			{\n"
		result += "				_controlPanelDataProvider = new Array();\n"
		result += "				for each(var obj:Object in vs.getChildren())\n"	
		result += "				{\n"
		result += "				if(obj.name != \"${domainClass.propertyName}Edit\")\n"
		result += "					_controlPanelDataProvider.push(obj);\n"
		result += "				}\n"
		result += "			}\n"
		result		
	}
	
	/**
	 *
	 * @param 
	 * @return
	 */
	static String getClickHandler(domainClass)
	{
		def String result = "\n"
		result += "			private function controlPanelClickHandler(event:ItemClickEvent):void\n"
		result += "			{\n"
		result += "				selectedView = event.item.name;\n"
		result += "			}\n"
		result		
	}
}
