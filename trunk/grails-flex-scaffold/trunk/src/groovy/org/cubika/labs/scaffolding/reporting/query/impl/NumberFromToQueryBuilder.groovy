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

/**
 * Adds number query support to BaseFromToQueryBuilder
 * @see org.cubika.labs.scaffolding.reporting.query.impl.BaseFromToQueryBuilder
 *
 * @author Gonzalo Javier Clavell
 * @since  2-Mar-2009
 */
public class NumberFromToQueryBuilder extends BaseFromToQueryBuilder
{
	/**
	 * Constructor
	 * @see org.cubika.labs.scaffolding.reporting.query.impl.BaseFromToQueryBuilder
	 */
	NumberFromToQueryBuilder(prop,valueString,queryObject)
	{
		super(prop,valueString,queryObject)
	}
	
	/**
  * Return an instance based on string passed as params
	 * @see org.cubika.labs.scaffolding.reporting.query.impl.BaseFromToQueryBuilder
	 */
	def getValueFromValueString(string)
	{
		if(propType == "integer")
			return string.toInteger()
		if(propType == "float")
			return string.toFloat()
		if(propType == "double")
			return string.toDouble()
		if(propType == "long")
			return string.toLong()
	}
}
