package org.cubika.labs.scaffolding.validator

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
 * Declares build method for all validator classes
 *
 * @author Ezequiel Martin Apfel
 * @since  3-Feb-2009
 */
interface BuildValidator 
{
	/**
	 * Builds validator that matches with constraint param
	 *	@param id			- validator component id 
	 *	@param value		- value to validate
	 *	@param constraint	- defines validator builder type
	 * @return String 	- validator mxml code string 
	 */
	def build(id, value, constraint)
}
