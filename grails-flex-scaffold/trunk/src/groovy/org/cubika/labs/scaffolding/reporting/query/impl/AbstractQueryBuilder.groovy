package org.cubika.labs.scaffolding.reporting.query.impl
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
import org.cubika.labs.scaffolding.reporting.ReportingBuilder
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.cubika.labs.scaffolding.reporting.query.QueryObject

/**
 * Basic query builder functionallity description, its subclasses has the responsibilty
 * of adding to stringQuery and queryParameters of the QueryObject a part of string and 
 * an entry respectively   
 */
public abstract class AbstractQueryBuilder implements ReportingBuilder
{
	/**
	 * Each from-to param in http request has its from-to values separated by
	 * this value  
	 */
	protected static final String FROM_TO_REQUEST_SEPARATOR = "_-_"
	
	/**
	 * "and" is the default concatenation for query
	 * IMPROVEMENT we need to dynamize this as a future feature
	 */
	protected static final String DEFAULT_QUERY_ASSOCIATION = " and "	
	
	/**
	 * Property name
	 */
	String propName
	
	/**
	 * String value of a key-value param 
	 */
	String valueString 
	
	/**
	 * Property type
	 */
	String propType
	
	/**
	 * Represents queryObject for current report 
	 */
	QueryObject queryObject
	
	/**
	 * Contructor
	 * Sets class members from params
	 * @param GrailsDomainClassProperty prop
	 * @param String value
	 * @param QueryObject queryObj
	 */
	AbstractQueryBuilder(prop,value,queryObj)
	{
		propName = prop.getName()
		propType = prop.getTypePropertyName()
		valueString = value
		queryObject = queryObj
	}
}
