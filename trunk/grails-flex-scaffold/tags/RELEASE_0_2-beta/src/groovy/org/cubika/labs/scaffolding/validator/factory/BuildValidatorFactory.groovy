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
 *
 * @author Ezequiel Martin Apfel
 * @since  3-Feb-2009
 */
package org.cubika.labs.scaffolding.validator.factory

import org.cubika.labs.scaffolding.validator.BuildValidator 
import org.cubika.labs.scaffolding.validator.impl.StringBuildValidator 
import org.cubika.labs.scaffolding.validator.impl.NumberBuildValidator 
import org.cubika.labs.scaffolding.validator.impl.EmailBuildValidator 
import org.cubika.labs.scaffolding.validator.impl.URLBuildValidator
import org.cubika.labs.scaffolding.validator.impl.CreditCardBuildValidator

/**
 * Create validator builders
 * @author Ezequiel Martin Apfel
 * @since  3-Feb-2009
 */
class BuildValidatorFactory 
{	
	/**
	 * Creates validator builder which matches with the constraint passed as a parameter
	 * Supports:
	 * 	URLBuildValidator
	 * 	StringBuildValidator
	 * 	NumberBuildValidator
	 * 	EmailBuildValidator
	 * @param contraint
	 * @return BuildValidator
	 */
	static BuildValidator createValidator(constraint)
	{
		
		//Not implemented yet
		/*if (constraint.hasAppliedConstraint("creditCard"))
					return new CreditCardBuildValidator();*/
		
		if (constraint.hasAppliedConstraint("url"))
			return new URLBuildValidator();
		
		if (constraint.maxSize != null || constraint.minSize != null || constraint.size != null)
			return new StringBuildValidator()
		
		if (constraint.hasAppliedConstraint("email"))
			return new EmailBuildValidator()
			
		if (constraint.min != null || constraint.max != null || constraint.range != null)
			return new NumberBuildValidator()
		
		if (!constraint.blank)
			return new StringBuildValidator()
	}
}
