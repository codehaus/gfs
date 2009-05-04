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

import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU

/**
 * Extends AbstractBuildFormItem adding CBKColorPicker building functionallity
 *
 * @author Ezequiel Martin Apfel
 * @since  16-Apr-2009
 */
class ColorPickerBuildFormItem  extends AbstractBuildFormItem 
{
	ColorPickerBuildFormItem(property)
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

		pw.println	"				<cubikalabs:CBKColorPicker id=\"${getID()}\" selectedColorHex=\"{${binding}}\"/>"
		
		sw.toString()
	}
	
	/**
	 * @see #AbstractBuildFormItem
	 *
	 */
	String getFormAttr()
	{
		"${getID()}.${value()}"
	}
	
	/**
	 * @see #AbstractBuildFormItem
	 *
	 */
	String getID()
	{
		"cp${FSU.capitalize(property.name)}"
	}
	
	/**
	 * @see #AbstractBuildFormItem
	 *
	 */
	String value()
	{
		"selectedColorHex"
	}
}
