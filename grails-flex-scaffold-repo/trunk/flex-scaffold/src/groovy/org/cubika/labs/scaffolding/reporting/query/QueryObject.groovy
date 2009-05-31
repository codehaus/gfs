package org.cubika.labs.scaffolding.reporting.query
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
 * This class represents a parametrized hql query like.
 * e.g: 	Company.findAll("from Company where name=? and date=?",[name,date...])
 * 		Company.findAll(queryObject.queryString,queryObject.queryParameters)
 * @author Gonzalo Javier Clavell
 * @since  2-Mar-2009
 */
public class QueryObject{

	/**
	 * A string representing hql query
	 */
	String queryString

	/**
	 * A list representing query parameters for this.queryString
	 */
	List queryParameters
	
	QueryObject ()
	{
		queryString = ""
		queryParameters = new ArrayList()
 }
}
