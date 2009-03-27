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
 * Extends AbstractRelationBuildFormItem adding in place many to one builder
 * functionallity
 * @author Ezequiel Martin Apfel
 * @since  3-Feb-2009
 */
class OneToManyBuildFormItem  extends AbstractRelationBuildFormItem
{	
	
	/**
	 * Contructor
	 *
	 */
	OneToManyBuildFormItem(property)
	{
		super(property)
	}
	
	/**
	 * @see #AbstractRelationBuildFormItem
	 */
	def buildFormItemComponent(binding)
	{
		def sw = new StringWriter()
		def pw = new PrintWriter(sw)
		
		pw.println	"		<${property.name}:${property.referencedDomainClass.shortName}OneToManyListView id=\"${getID()}\" "+
								"dataProvider=\"{${binding}}\"/>"
		
		generateViews(property)
		
		sw.toString()
	}
	
	/**
	 * @see #AbstractRelationBuildFormItem
	 */
	String getID()
	{
		"r${FSU.capitalize(property.name)}"
	}
	
	/**
	 * @see #AbstractRelationBuildFormItem
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
		def classNameDir = "${nameDir}/${property.referencedDomainClass.shortName}OneToManyListView.mxml"
		def templateDir = resolveResources("/*"+antProp.'view.otmlistfile').toString()

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
