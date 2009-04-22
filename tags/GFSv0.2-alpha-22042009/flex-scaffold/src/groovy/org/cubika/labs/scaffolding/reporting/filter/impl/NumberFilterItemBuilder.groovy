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

import org.cubika.labs.scaffolding.reporting.filter.impl.AbstractFilterItemBuilder


/**
 * Number filter code builder
 *
 * @author Gonzalo Javier Clavell
 * @since  2-Mar-2009
 */
public class NumberFilterItemBuilder extends AbstractFilterItemBuilder{

	/**
	 * Contructor
	 * @see org.cubika.labs.scaffolding.reporting.filter.impl.AbstractFilterItemBuilder
	 */
	NumberFilterItemBuilder(property)
	{
		super(property)
	}
	
	/**
	 * @return String describing a number filter component
	 */
	def build()
	{
			"					<cubikalabs:NumberFilterControl id=\"${propName}FilterItem\" styleName=\"reportingStringFilters\" mainLabel=\"{MultipleRM.getString(MultipleRM.localePrefix, '${prop.domainClass.propertyName}.${prop.name}')}\" fromLabel=\"{MultipleRM.getString(MultipleRM.localePrefix, 'generic.from')}\" toLabel=\"{MultipleRM.getString(MultipleRM.localePrefix, 'generic.to')}\" requestParameter=\"${propName}\"/>\n"
	}
}
