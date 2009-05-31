package org.cubika.labs.scaffolding.reporting.query.factory
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

import org.cubika.labs.scaffolding.reporting.query.impl.StringQueryBuilder
import org.cubika.labs.scaffolding.reporting.query.impl.ManyToOneQueryBuilder
import org.cubika.labs.scaffolding.reporting.query.impl.DateFromToQueryBuilder
import org.cubika.labs.scaffolding.reporting.query.impl.NumberFromToQueryBuilder
import org.cubika.labs.scaffolding.reporting.ReportingBuilder
import org.codehaus.groovy.grails.commons.GrailsClass 
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

/**
 * Query Builder factory
 *
 * @author Gonzalo Javier Clavell
 * @since  2-Mar-2009
 */
public class QueryBuilderFactory{
	
	/**
	 * Returns a ReportingBuilder implementation depending on prop type 
	 * @param DomainClassProperty property which is going to build as query
	 * @param String value which represents filter value 
	 * @param QueryObject instance which is going to be filled with builder result
	 * @return ReportingBuilder
	 */
	static ReportingBuilder getQueryBuilder(prop,value,queryObject)
	{
		def type = prop.getTypePropertyName()
		def labeledProperty
		
		if(prop.isManyToOne())
		{
			labeledProperty = CVU.getLabeledProperty(prop.referencedDomainClass)
			if(labeledProperty != "")
				return new ManyToOneQueryBuilder(prop,value,queryObject,labeledProperty)
		}
		else if(type == "string")
			return new StringQueryBuilder(prop,value,queryObject)
		else if(type == "date")
			return new DateFromToQueryBuilder(prop,value,queryObject)
		else if(type == "long" || type == "integer" || type == "double" || type == "float")
			return new NumberFromToQueryBuilder(prop,value,queryObject)
	}
}
