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

import org.cubika.labs.scaffolding.reporting.query.impl.StringQueryBuilder
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

/**
 * Many to one query builder works the same as StringQueryBuilder
 * The diference is that propName has dotted format(e.g:"Company.name")
 * @author Gonzalo Javier Clavell
 * @since  2-Mar-2009
 */
public class ManyToOneQueryBuilder extends StringQueryBuilder
{
	/**
	 * @param String labeledProperty (e.g:"Company.name")
	 * @see org.cubika.labs.scaffolding.reporting.query.impl.StringQueryBuilder
	 */
	ManyToOneQueryBuilder(prop,valueString,queryObject,labeledProperty)
	{
		super(prop,valueString,queryObject)
		propName = labeledProperty
	}
}
