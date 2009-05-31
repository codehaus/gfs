package org.cubika.labs.scaffolding.reporting.filter.impl

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
import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
import org.cubika.labs.scaffolding.reporting.filter.impl.AbstractFilterItemBuilder

/**
 * Extends AbstractBuildFormItem adding textinput building functionallity
 *
 * @author Gonzalo Clavell
 * @since  5-Mar-2009
 */
class StringFilterItemBuilder extends AbstractFilterItemBuilder  
{
	/**
	 * Contructor
	 * @see org.cubika.labs.scaffolding.reporting.filter.impl.AbstractFilterItemBuilder
	 */
	 StringFilterItemBuilder(property)
	{
		super(property)
	}
	
	/**
	 * @return String describing a string filter component
	 */
	def build()
	{
		"					<cubikalabs:StringFilterControl id=\"${propName}FilterItem\" styleName=\"reportingStringFilters\" mainLabel=\"{MultipleRM.getString(MultipleRM.localePrefix, '${prop.domainClass.propertyName}.${prop.name}')}\" requestParameter=\"${propName}\" textInputWidth=\"250\"/>\n"
	}			
}
