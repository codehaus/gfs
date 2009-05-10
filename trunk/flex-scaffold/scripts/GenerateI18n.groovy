/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.cubika.labs.scaffolding.i18n.I18nBuilder

Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

//-----------------
Ant.property(file:"${flexScaffoldPluginDir}/scripts/flexScaffold.properties")
def antProp = Ant.project.properties
Ant.property(file:"${basedir}/grails-app/i18n/i18n.properties")
antProp = Ant.project.properties
//-----------------

//Private scripts
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_CreateFlexProperties.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateDefaults.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateEclipse.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateStructure.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_ValidateDomainClass.groovy" )

target('default': "Generates i18n messages for domain classes") 
{
	depends( validateDomainClass, generateFlexDefaultStructure, generateFlexBuilder, createFlexProperties, generateDefaults )
  
	def name = argsMap["params"][0]
		
	args = [:]
	
	args['name'] = name
	args['runHierarchy'] = false
	
	generateI18nMessages(args)
}

generateI18nMessages = { Map args = [:] ->

	mapI18nGenerated = [:]
	
	defaultLocale = antProp.'locale.default'
  
	def locales = antProp.'locale.locales'

	if (args['name'] != "*")
	{
		//If runHierarchy is true, comes from GenerateAllFlex and we don't want generate all locales
		if (args['runHierarchy'])
			locales = "$defaultLocale"
		else
			locales = "$defaultLocale, $locales"
			
		generatesI18n(getDomainClass(args['name']), locales, args['runHierarchy'])
	}
	else if (args['name'] == "*")
	{
		locales = "$defaultLocale, $locales"
		generatesAllI18n(grailsApp.domainClasses,locales)
	}
}

/**
 * Generates properties
 * @param domanClass
 * @param runHierarchy - if it's true, run all classes related with DomainClass.
 */
void generatesI18n(domainClass, locales,runHierarchy)
{
	i18nBuilder = new I18nBuilder(defaultLocale)
	locales.split(",").each
	{
		print "Building $it:"
		i18nBuilder.changeLocale(it.trim())
		generateProperties(domainClass,runHierarchy)
		//Save message{suffix}.properties
		i18nBuilder.store()
		mapI18nGenerated = [:]
		println ""
		println "Locale $it Done!"
	}
}

/**
 * Generates all classes properties
 * @param domanClass
 */
void generatesAllI18n(domainClasses, locales)
{
	i18nBuilder = new I18nBuilder(defaultLocale)
	locales.split(",").each
	{
		print "Building $it:"
		i18nBuilder.changeLocale(it.trim())
		domainClasses.each 
		{
			generateProperties(it,false)
		}
		//Save message{suffix}.properties
		i18nBuilder.store()
		println ""
		println "Locale $it Done!"
	}
}

void generateProperties(domainClass, runHierarchy=true)
{
	print " ${domainClass.shortName}"
	i18nBuilder.build(domainClass)
	
	if (runHierarchy)
	{
		mapI18nGenerated.put(domainClass,domainClass)
		generateHierarchy(domainClass)
	}
}

void generateHierarchy(domainClass)
{
	domainClass.properties.each
	{
		if (it.isOneToOne() || it.isOneToMany() || it.isManyToOne())
		{
			if (!mapI18nGenerated.containsKey(it.referencedDomainClass))
			{
				generateProperties(it.referencedDomainClass)
			}
		}
	}
}