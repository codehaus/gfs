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
 * TODO
 *
 * @author Ezequiel Martin Apfel
 * @since  3-Feb-2009
 */
class OneToOneBuildFormItem extends AbstractRelationBuildFormItem
{
	
	OneToOneBuildFormItem(property)
	{
		super(property)
	}
	
	/**
	 * @see #AbstractBuildFormItem
	 */
	def buildFormItemComponent(binding)
	{
		def sw = new StringWriter()
    def pw = new PrintWriter(sw)
		
		pw.println	"		<${property.name}:${property.referencedDomainClass.shortName}OneToOneView id=\"${getID()}\" "+
								"vo=\"{${binding}}\"/>"
		
		generateViews(property)
		
		sw.toString()
	}
	
	/**
	 * @see #AbstractBuildFormItem
	 */
	String getID()
	{
		"r${FSU.capitalize(property.name)}"
	}
	
	/**
	 * @see #AbstractBuildFormItem
	 */
	String value()
	{
		"getVO()"
	}
	
	/**
	 * @see #AbstractRelationBuildFormItem
	 */
	protected void generateViews(property)
	{
		super.generateViews(property)

		def nameDir = antProp.'view.destdir'+"/${property.domainClass.propertyName}/${property.referencedDomainClass.propertyName}"
		def classNameDir = "${nameDir}/${property.referencedDomainClass.shortName}OneToOneView.mxml"
		def templateDir = resolveResources("/*"+antProp.'view.otolistfile').toString()

		if (!new File(nameDir).exists())
			new File(nameDir).mkdir()

		defaultTemplateGenerator.generateTemplate(property.referencedDomainClass,templateDir,classNameDir,property.domainClass.propertyName)
		println "${classNameDir} Done!"
		
		classNameDir = "${nameDir}/${property.referencedDomainClass.shortName}RelationEditView.mxml"
		templateDir = resolveResources("/*"+antProp.'view.relationeditfile').toString()
		
		defaultTemplateGenerator.generateTemplate(property.referencedDomainClass,templateDir,classNameDir,property.domainClass.propertyName)
		println "${classNameDir} Done!"
	}
	

}
