package org.cubika.labs.scaffolding.reporting.filter

////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////
 
import 	org.cubika.labs.scaffolding.reporting.ReportingBuilder
import 	org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU
import 	org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
import 	org.cubika.labs.scaffolding.reporting.filter.factory.FilterItemBuilderFactory as FIBF

/**
 * Buildes flex filter components in scripting time 
 * @author Gonzalo Javier Clavell
 * @since 2-Mar-2009
 */
class ReportingFilterBuilder
{
	/**
	 * @param domainClass
	 * @return String containing reporting tag for CRUDView
	 */
	static String buildReportingViewTag(domainClass)
	{
		String result = "";
		if(CVU.isReportable(domainClass))
		{
			result = 	"\n 		<view:${domainClass.shortName}ReportingView "+
					"height=\"100%\" width=\"100%\" paddingLeft=\"10\" label=\"{MultipleRM.getString(MultipleRM.localePrefix, 'reporting.tabs.reporting')}\" showEffect=\"fadeEff\" hideEffect=\"fadeEff\" \n "+ 
					"			name='${domainClass.propertyName}Reporting' />"
		}
		return result
	}
	
	/**
	 *	@param domainClass
	 * @param className 
	 * @param formId 
	 * @return String containing Reporting Filter component
	 */
	static String buildReportingFilterForm(domainClass,className,formId)
	{
		def props = FSU.getPropertiesWithoutIdentity(domainClass,true);
		def result =				"				<mx:VBox id=\"${formId}\" label=\"{MultipleRM.getString(MultipleRM.localePrefix, 'reporting.accordion.labels.filter')}\" paddingLeft=\"10\" width=\"100%\" height=\"100%\" styleName=\"reportingfiltersStringContainer\">\n"
		props.each{it ->
			ReportingBuilder builder = FIBF.getFilterItemBuilder(it)   
			if(builder != null)
				result += builder.build();
		}
		result +=					"				</mx:VBox>"
		return result				
	}
}
