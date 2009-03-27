package org.cubika.labs.scaffolding.form.factory

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

import org.cubika.labs.scaffolding.form.BuildFormItem
import org.cubika.labs.scaffolding.form.impl.TextInputBuildFormItem
import org.cubika.labs.scaffolding.form.impl.TextAreaBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ComboBuildFormItem
import org.cubika.labs.scaffolding.form.impl.AutoCompleteBuildFormItem
import org.cubika.labs.scaffolding.form.impl.NumericStepperBuildFormItem
import org.cubika.labs.scaffolding.form.impl.CheckBoxBuildFormItem
import org.cubika.labs.scaffolding.form.impl.DateFieldBuildFormItem
import org.cubika.labs.scaffolding.form.impl.OneToManyBuildFormItem
import org.cubika.labs.scaffolding.form.impl.OneToOneBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ExternalOneToOneBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ExternalOneToManyBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ExternalManyToOneBuildFormItem
import org.cubika.labs.scaffolding.form.FormItemConstants as FIC
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

/**
 * Form item builder factory
 *  
 * @author Ezequiel Martin Apfel
 * @sing 19-Feb-2009
 */
class BuildFormItemFactory 
{	
	/**
	 * Create form item based on property parameter 
	 *
	 */
	static BuildFormItem createFormItem(property)
	{
		def constraint = property.domainClass.getConstrainedProperties()[property.name]
		
		if (!CVU.display(property))
			return
		
		if (constraint.widget == FIC.TEXT_INPUT)
			return new TextInputBuildFormItem(property)
		
		if (constraint.widget == FIC.TEXT_AREA)
				return new TextAreaBuildFormItem(property)
		
		if (property.type == String.class && !constraint.inList)
			return new TextInputBuildFormItem(property)
		
		if (constraint.inList && constraint.widget != FIC.AUTO_COMPLETE)
			return new ComboBuildFormItem(property)

		if (constraint.inList && constraint.widget == FIC.AUTO_COMPLETE)
			return new AutoCompleteBuildFormItem(property)
		
		if (property.type == Integer.class || property.type == Long.class || property.type == Float.class)
			return new NumericStepperBuildFormItem(property)
			
		if (property.type == Boolean.class)
			return new CheckBoxBuildFormItem(property)	
			
		if (property.type == Date.class)
			return new DateFieldBuildFormItem(property)
		
		if (property.isManyToMany())
			return
		
		def inPlace = CVU.getInPlace(constraint)
		
		if (property.isManyToOne())
			//if (!inPlace)
				return new ExternalManyToOneBuildFormItem(property)
			//else
				//return new ManyToOneBuildFormItem(property)
		
		if (property.isOneToMany())
			if (inPlace)
				return new OneToManyBuildFormItem(property)
			else
				return new ExternalOneToManyBuildFormItem(property) 
		
		if (property.isOneToOne())
			//if (inPlace)
				return new OneToOneBuildFormItem(property)
			//else
			//	return new ExternalOneToOneBuildFormItem(property)
	}
}
