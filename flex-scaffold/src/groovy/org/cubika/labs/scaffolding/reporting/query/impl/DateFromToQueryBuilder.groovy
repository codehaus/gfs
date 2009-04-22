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

import org.cubika.labs.scaffolding.reporting.query.impl.BaseFromToQueryBuilder
import org.cubika.labs.scaffolding.reporting.query.QueryObject
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Adds date query support to BaseFromToQueryBuilder
 *
 * @author Gonzalo Javier Clavell
 * @since  2-Mar-2009
 */
public class DateFromToQueryBuilder extends BaseFromToQueryBuilder
{
	def year
	def month
	def day
	
	/**
	 * Contructor
	 * @see org.cubika.labs.scaffolding.reporting.query.impl.BaseFromToQueryBuilder
	 */
	DateFromToQueryBuilder(prop,valueString,queryObject)
	{
		super(prop,valueString,queryObject)
	}
	
	/**
  * Return Date instance based on date string passed as params  
	 * @see org.cubika.labs.scaffolding.reporting.query.impl.BaseFromToQueryBuilder
	 */
	def getValueFromValueString(string)
	{
		def List splittedString = string.split("-")
		day = Integer.valueOf(splittedString[0]).intValue()
		month = Integer.valueOf(splittedString[1]).intValue()
		year = Integer.valueOf(splittedString[2]).intValue()
		def instance = Calendar.getInstance()
		instance.set(year,month,day)
		return instance.getTime()
	}
}
