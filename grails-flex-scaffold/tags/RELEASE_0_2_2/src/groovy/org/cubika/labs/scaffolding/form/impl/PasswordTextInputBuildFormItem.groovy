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
 * Extends AbstractBuildFormItem adding 2 textinput to password building functionallity
 * User: Ezequiel Martin Apfel
 * @since: 26-11-2009
 * 
 */
public class PasswordTextInputBuildFormItem extends AbstractBuildFormItem {


  public PasswordTextInputBuildFormItem(property) {
    super(property)   
  }

  /**
   * Build a FormItem
   * @param binding - String con la propiedad que va a estar mirando el componente
   */
  def build(binding) {

    def sw = new StringWriter()
    def pw = new PrintWriter(sw)

    pw.println 	"	 		<mx:FormItem label=\"{MultipleRM.getString(MultipleRM.localePrefix,'${property.domainClass.propertyName}.${property.name}')}\" id=\"${getFormItemID()}\" width=\"100%\">"

    pw.print	buildFormItemComponent(binding)

    pw.println 	"	 		</mx:FormItem>"

    pw.println 	"	 		<mx:FormItem label=\"{MultipleRM.getString(MultipleRM.localePrefix,'${property.domainClass.propertyName}.${property.name}')}\" width=\"100%\">"

    pw.print	buildFormItemComponent(binding,"txtRePassword")

    pw.println 	"	 		</mx:FormItem>"

    sw.toString()
  }

  def buildValidator()
	{
		if (validator)
		{
			return validator.build("txt${FSU.capitalize(property.name)}", "text", constraint)
		}
	}

  protected buildFormItemComponent(def binding) {

     buildFormItemComponent(binding, "txt${FSU.capitalize(property.name)}")
  }

  protected buildFormItemComponent(def binding, def id) {

    def sw = new StringWriter()
    def pw = new PrintWriter(sw)

    pw.println	"				<mx:TextInput id=\"${id}\" text=\"{${binding}}\" width=\"217\" displayAsPassword=\"true\"/>"

    sw.toString()
  }

  public String getID() {
    "SHA256.hashToBase64(txt${FSU.capitalize(property.name)}"
  }

  public String value() {
    "text)"
  }

}