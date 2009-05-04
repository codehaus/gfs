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
import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU

/**
 * Extends AbstractBuildFormItem adding combobox building functionallity
 *
 * @author Ezequiel Martin Apfel
 * @since  3-Feb-2009
 */
class ComboBuildFormItem extends AbstractBuildFormItem
{
	/**
	 * Constructor
	 *
	 */
	ComboBuildFormItem(property)
	{
		super(property)
	}
	
	/**
	 * @see #AbstractBuildFormItem
	 *
	 */
	def buildFormItemComponent(binding)
	{	
		def sw = new StringWriter()
		def pw = new PrintWriter(sw)

		pw.println	"				<cubikalabs:CBKComboBox id=\"${getID()}\" selectedItem=\"{${binding}}\" prompt=\"{MultipleRM.getString(MultipleRM.localePrefix,'generic.select')} ..\">"
		pw.println 	"					<cubikalabs:dataProvider>"
		pw.println 	"						<mx:ArrayCollection>"
		
		constraint.inList.each
		{
			pw.println 	"							<mx:Object data=\"$it\" label=\"{MultipleRM.getString(MultipleRM.localePrefix,'${property.domainClass.propertyName}.${property.name}.${it}')}\"/>"
		}
		
		pw.println 	"						</mx:ArrayCollection>"			
		pw.println	"					</cubikalabs:dataProvider>"
		pw.println	"				</cubikalabs:CBKComboBox>"
		
		sw.toString()
	}
	
	/**
	 * @see #AbstractBuildFormItem
	 *
	 */
	String getFormAttr()
	{
		def cast = FSU.getClassCast(property)
	
		if (cast)	
			return "!${getID()}.${value()} ? null : ${cast}(${getID()}.${value()}.data)"
		
		"!${getID()}.${value()} ? null : ${getID()}.${value().data}"
	}
	
	/**
	 * @see #AbstractBuildFormItem
	 *
	 */
	String getID()
	{
		"cb${FSU.capitalize(property.name)}"
	}
	
	/**
	 * @see #AbstractBuildFormItem
	 *
	 */
	String value()
	{
		"selectedItem"
	}
}
