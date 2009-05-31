package org.cubika.labs.scaffolding.reporting.filter.factory

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

import org.cubika.labs.scaffolding.reporting.filter.impl.DateFilterItemBuilder
import org.cubika.labs.scaffolding.reporting.filter.impl.StringFilterItemBuilder
import org.cubika.labs.scaffolding.reporting.filter.impl.NumberFilterItemBuilder
import org.cubika.labs.scaffolding.reporting.filter.impl.ManyToOneFilterItemBuilder
import org.cubika.labs.scaffolding.reporting.ReportingBuilder
import org.codehaus.groovy.grails.commons.GrailsClass 
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

/**
 * Filter item builder factory
 * 
 * @author Gonzalo Clavell
 * @sing 16-Mar-2009
 */
class FilterItemBuilderFactory 
{
	/**
	 * @param property
	 * @return ReportingBuilder corresponding to property type
	 */
	static ReportingBuilder getFilterItemBuilder(property)
	{
		String type = property.getTypePropertyName()
		
		//Many-To-One
		if (property.isManyToOne())
		{
			def propertyDomainClass = property.referencedDomainClass 
			def labeledProperty = CVU.getLabeledProperty(propertyDomainClass)
			if(labeledProperty != null)
			{
				return new ManyToOneFilterItemBuilder(property,labeledProperty)
			}
		}	
		//Native Types
		if(type == "string")
			return new StringFilterItemBuilder(property)
		if(type == "date")
			return new DateFilterItemBuilder(property)
		if(type == "long" || type == "integer" || type == "double" || type == "float")
			return new NumberFilterItemBuilder(property)
	}
}
