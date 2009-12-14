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

import org.cubika.labs.scaffolding.reporting.query.impl.AbstractQueryBuilder
import org.cubika.labs.scaffolding.reporting.query.QueryObject


/**
 * Adds from-to support to AbstractQueryBuilder
 * @see org.cubika.labs.scaffolding.reporting.query.impl.AbstractQueryBuilder
 *
 * @author Gonzalo Javier Clavell
 * @since  2-Mar-2009
 */
public class BaseFromToQueryBuilder extends AbstractQueryBuilder
{
	/**
	 * From value
	 */
	def fromValue
	
	/**
	 * To value
	 */
	def toValue

	/**
	 * From value as come in params
	 */	
	def fromValueString

	/**
	 * To value as come in params
	 */	
	def toValueString
	
	
	/**
	 * Contructor
	 * Sets fromValue and toValue from valueString passed as param 
	 * @see org.cubika.labs.scaffolding.reporting.query.impl.AbstractQueryBuilder
	 */
	BaseFromToQueryBuilder(prop,valueString,queryObject)
	{
		super(prop,valueString,queryObject)
		setFromToValues(valueString)
	}
	
	/**
	 * Adds string query to queryObject's queryString and parameters to  
  * If there is no from value or no to value builds simple query
  * e.g : x >= 10; x <= 5
  * If two values are defined builds fromToQuery
  * e.g : between 5 and 10
	 */
	def build()
	{
		if(!fromValueString || !toValueString)
		{
			buildSimpleQuery()
		}
		else
		{
			queryObject.queryParameters.add(fromValue)
			queryObject.queryParameters.add(toValue)
			queryObject.queryString += propName+" between ? and ?"+DEFAULT_QUERY_ASSOCIATION
		}
	}

 /**
  * Builds query when from value or to value are no defined
  */
	def buildSimpleQuery()
	{
  if(fromValueString)
		{	
			queryObject.queryString += propName+" >= ?"+DEFAULT_QUERY_ASSOCIATION
			queryObject.queryParameters.add(fromValue)
		}
		else if(toValueString)
		{
			queryObject.queryString += propName+" <= ?"+DEFAULT_QUERY_ASSOCIATION
			queryObject.queryParameters.add(toValue)
		}
	}
	
	/**
	 * Sets fromValueString, toValueString, fromValue, and toValue from valueString
	 *
	 */
	def setFromToValues(valueString)
	{
		def splittedValue = valueString.split(AbstractQueryBuilder.FROM_TO_REQUEST_SEPARATOR)
		fromValueString = (splittedValue[0] == "null") ? null : splittedValue[0]
  toValueString = (splittedValue[1] == "null") ? null : splittedValue[1]
  if(fromValueString)
		{
			fromValue = getValueFromValueString(fromValueString)
		}
		if(toValueString)
		{
			toValue = getValueFromValueString(toValueString)
		}
	}
	
	/**
  * Basic getValueFromValueString
  * Each type supported by query builder (subclasses) must override this method in order to return the right type 
	 * @param String string
	 * @return def describing value from string passed as parameter
	 */
	def getValueFromValueString(string)
	{
		return string
	}
}
