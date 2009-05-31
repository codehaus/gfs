/**
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

<%
import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU
%>
 
package model.${domainClass.propertyName}
{
	import mx.collections.ArrayCollection;
	import com.cubika.labs.controls.reporting.column.ReportingColumnDescriptor;
	/**
	 * @author Gonzalo Javier Clavell
	 * @since 23-Mar-2009
	 */
	[Bindable]
	public class ${className}ReportingModel 
	{

		public var className:String = "${domainClass.naturalName}"; 
		public var columnDataProvider:ArrayCollection;
		
		public function ${className}ReportingModel():void
		{
			var propertyArray:Array = new Array();
<%
			def props = FSU.getPropertiesWithoutIdentity(domainClass,true)
			props.each
			{
				String requestParameter = ""
				def domainClassProperty = it.getReferencedDomainClass();
				if(domainClassProperty != null)
					requestParameter = CVU.getLabeledProperty(domainClassProperty)	
				println("			propertyArray.push(new ReportingColumnDescriptor(\"${domainClass.propertyName}\", \"${it.name}\",\"${it.naturalName}\",\"${requestParameter}\"));")				
			}
%>
			columnDataProvider = new ArrayCollection(propertyArray);
		}
	}
}