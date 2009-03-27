package org.cubika.labs.scaffolding.form.impl

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
import org.cubika.labs.scaffolding.validator.factory.BuildValidatorFactory as BVF
import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

/**
 * Gives basic functionallity for all form item builders
 *
 * @author Ezequiel Martin Apfel
 * @since  3-Feb-2009
 */
abstract class AbstractBuildFormItem implements BuildFormItem
{
	def property
	def constraint
	def validator
	
	/**
	 * Constructor
	 *
	 */
	AbstractBuildFormItem(property)
	{
		this.property = property
		constraint = property.domainClass.getConstrainedProperties()[property.name]
		validator = BVF.createValidator(constraint)
	}
	
	/**
	 * Build a FormItem 
	 * @param binding - String con la propiedad que va a estar mirando el componente
	 */
	def build(binding)
	{
		def sw = new StringWriter()
		def pw = new PrintWriter(sw)

		pw.println 	"	 <mx:FormItem label=\"${property.naturalName}\" id=\"${getFormItemID()}\" width=\"100%\">"
		
		pw.println	buildFormItemComponent(binding)
		
		pw.println 	"	 </mx:FormItem>"
		
		sw.toString()
	}

	def buildValidator()
	{
		if (validator)
		{
			return validator.build(getID(), value(), constraint)
		}
	}
	
	/**
	 * Return id.value for formItem
	 */
	String getFormAttr()
	{
		"${getID()}.${value()}"
	}
	
	/**
	 * @see #BuildFormItem
	 */
	String getFormItemID()
	{
		"fi${FSU.capitalize(property.name)}";
	}
	
	/**
	 * @see #BuildFormItem
	 */
	String getRemoveChildEditView()
	{
		if (!CVU.getEditView(constraint))
			return "<mx:RemoveChild target=\"{${getFormItemID()}}\"/>"
	}
	
	/**
	 * @see #BuildFormItem
	 */
	String getRemoveChildCreateView()
	{
		if (!CVU.getCreateView(constraint))
			return "<mx:RemoveChild target=\"{${getFormItemID()}}\"/>"
	}
	
	/**
	 * @param property - a GrailsDefaultDomainProperty
	 * @return a Child component to formItem
	 */
	abstract protected def buildFormItemComponent(property)
}
