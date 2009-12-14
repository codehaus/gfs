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

import org.cubika.labs.scaffolding.reporting.filter.impl.StringFilterItemBuilder

/**
 * Many to one filter code builder
 *
 * @author Gonzalo Javier Clavell
 * @since  2-Mar-2009
 */
public class ManyToOneFilterItemBuilder extends StringFilterItemBuilder
{
	/**
	 * Constructor
	 * @param GrailsDomainClassProperty
	 * @param String e.g: company.name
	 */
	ManyToOneFilterItemBuilder(property,labeledProperty)
	{
		super(property)
		propName = labeledProperty;
	}

	/**
	 * 
	 * @return String describing a many to one filter component
	 */
	def build()
	{
		"					<cubikalabs:ManyToOneFilterControl id=\"${prop.getName()}FilterItem\" styleName=\"reportingStringFilters\" mainLabel=\"{MultipleRM.getString(MultipleRM.localePrefix, '${prop.domainClass.propertyName}.${prop.name}')}\" requestParameter=\"${propName}\" textInputWidth=\"250\"/>\n"
	}			
}
