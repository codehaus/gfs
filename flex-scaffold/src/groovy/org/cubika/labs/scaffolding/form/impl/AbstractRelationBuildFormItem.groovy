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
package org.cubika.labs.scaffolding.form.impl

import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
import org.cubika.labs.scaffolding.form.BuildFormItem
import org.cubika.labs.scaffolding.generator.DefaultFlexTemplateGenerator

/**
 * Extends basic AbstractBuildFormItem functionallity to support relational 
 * component building
 *
 * @author Ezequiel Martin Apfel
 * @since  3-Feb-2009
 */
abstract class AbstractRelationBuildFormItem extends AbstractBuildFormItem
{	
	static private def viewMaps = [:]
	
	protected def defaultTemplateGenerator = new DefaultFlexTemplateGenerator()
	protected def flexScaffoldPluginDir
	protected def ant
	protected def antProp
	
	/**
	 * Constructor
	 *
	 */
	AbstractRelationBuildFormItem(property) 
	{
		super(property)
	}
	
	/**
	 * Generate relation views
	 * @param property - a GrailsDefaultDomainProperty
	 */
	protected void generateViews(property)
	{
		String key = property.referencedDomainClass.toString()
		
		if (!viewMaps.containsKey("$key#this"))
		{
			//Set DomainClass into map to avoid die cycle
				
			viewMaps.put("$key#this",property.referencedDomainClass)
			
			ant = new AntBuilder()
			ant.property(environment: "env")
			def grailsHome = ant.antProject.properties."env.GRAILS_HOME"
		
			def fileProperties = FSU.resolveResources("/*/scripts/flexScaffold.properties")
		
			ant.property(file:fileProperties)
			antProp = ant.project.properties
			
			generateInnerViews(property)
		}
	}
	
	abstract protected void generateInnerViews(property)
}
