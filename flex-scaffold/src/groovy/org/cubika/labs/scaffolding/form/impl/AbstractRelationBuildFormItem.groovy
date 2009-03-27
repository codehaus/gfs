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

import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import grails.util.BuildSettingsHolder

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
	protected def defaultTemplateGenerator = new DefaultFlexTemplateGenerator()
	protected def flexScaffoldPluginDir
	protected def ant
	protected def antProp
	protected def resolver = new PathMatchingResourcePatternResolver()
	
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
		ant = new AntBuilder()
		ant.property(environment: "env")
		def grailsHome = ant.antProject.properties."env.GRAILS_HOME"
		
		def fileProperties = resolveResources("/*/scripts/flexScaffold.properties")
		
		ant.property(file:fileProperties)
		antProp = ant.project.properties
	}
	
	/**
	 * Resolve path for pluging dir  
	 * @param pattern - String with partil path 
	 * @return a concret path
	 */
	protected def resolveResources(pattern)
	{
		try 
		{
			//if is a localplugin
			def path = resolver.getResources("file:${BuildSettingsHolder.settings?.projectPluginsDir.canonicalFile}${pattern}").file[0]
			
			if (!path)
			{
				//if is a globalplugin
				path = resolver.getResources("file:${BuildSettingsHolder.settings?.globalPluginsDir.canonicalFile}${pattern}").file[0]
			}
			
			//otherwise
			if (!path)
				throw new Exception()
				
			return path
    }
    catch (Throwable e) 
		{
			throw new Exception("${BuildSettingsHolder.settings?.projectPluginsDir.canonicalFile}${pattern} Not Found")
		}
	}
}
