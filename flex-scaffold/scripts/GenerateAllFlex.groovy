/**
 * Copyright 2009 the original author or authors.
 *
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
 *
 * @author Ezequiel Martin Apfel
 * @since 23-Feb-2009
 */

import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU
import org.cubika.labs.scaffolding.generator.DefaultFlexTemplateGenerator

grailsHome = Ant.project.properties."environment.GRAILS_HOME"

Ant.property(file:"${flexScaffoldPluginDir}/scripts/flexScaffold.properties")

def antProp = Ant.project.properties

//Public scripts
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/GenerateVo.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/GenerateView.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/GenerateEvent.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/GenerateModel.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/GenerateCommand.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/GenerateDelegate.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/GenerateService.groovy" )

//Private scripts
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_CreateFlexProperties.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateDefaults.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateEclipse.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateStructure.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_ValidateDomainClass.groovy" )



target('default': "The description of the script goes here!") 
{
  depends( validateDomainClass, generateFlexDefaultStructure, generateFlexBuilder, createFlexProperties, generateDefaults )
	
	mapGenerated = new HashMap()
	
	generateAllFlex(args.trim())
}

void generateAllFlex(name)
{
	//ValidateDomainClass method
	def domainClass = getDomainClass(name)
	
	generateVo(name)
	generateViews(name)
	generateEvents(name)
	generateCRUDCommands(name)
	generateModel(name:name,addToModel:true)
	generateDelegate(name)
	generateService(name)
	addToNavigationModel(domainClass)
	addToMain(domainClass)
	
	mapGenerated.put(domainClass,domainClass)
	
	/**
	 * TODO: Check name error and enable this method
	 */
	//checkExternals(domainClass)
	
	println "Generate Done!"
}

/**
 * Check if domainClass has a external relation (inPlace:false). 
 * if true, generateAllFlex to referencedDomainClass
 */
void checkExternals(domainClass)
{
	domainClass.properties.each
	{
		if ((it.isOneToOne() || it.isOneToMany() || it.isManyToOne()) && CVU.display(it)
			&& !CVU.getInPlace(it.domainClass.getConstrainedProperties()[it.name]) )
		{
			if (!mapGenerated.containsKey(it.referencedDomainClass))
			{
				generateAllFlex(it.referencedDomainClass.shortName)
			}
		}
	}
}

void addToNavigationModel(domainClass)
{
	if (!new File(antProp.'model.destfile').exists())
		Ant.copy(file: "${flexScaffoldPluginDir}"+antProp.'model.navigationfile', tofile: antProp.'model.destfile', overwrite: true)
	
		file =  Ant.fileset(	dir: antProp.'flex.srcdir') {
										include(name:antProp.'model.destfile')
		          			contains(text: "defaultNavigationMap[\"${domainClass.propertyName}\"] ", casesensitive: false)
							 		}

		if (file.size() > 0)
				return

		def propertyName = domainClass.propertyName;
				
		Ant.replace(file: antProp.'model.destfile',
	          token: "//DefaultNavigationMap - Not Remove", value: "//DefaultNavigationMap - Not Remove\n	"+
										"defaultNavigationMap[\"${propertyName}\"] = "+
										"{name:\"${propertyName}CRUDView\",list:\"${propertyName}List\","+
										"edit:\"${propertyName}Edit\",nav:{list:\"edit\",edit:\"list\"}};")
}

void addToMain(domainClass)
{
	if (!new File(antProp.'main.destdir').exists())
		Ant.copy(file: "${flexScaffoldPluginDir}"+antProp.'main.file', tofile: antProp.'main.destdir', overwrite: true)
	
	
	file =  Ant.fileset(	dir: antProp.'flex.srcdir') {
									include(name:"Main.mxml")
	          			contains(text: "<view${domainClass.shortName}:${domainClass.shortName}CRUDView", casesensitive: false)
						 		}
				
	if (file.size() > 0)
			return
	
	Ant.replace(file: antProp.'main.destdir',
          token: "><!--NS-->", value: "\n	xmlns:view${domainClass.shortName}=\"view.${domainClass.propertyName}.*\" "+
								 "xmlns:control${domainClass.shortName}=\"control.${domainClass.propertyName}.*\"><!--NS-->")
	Ant.replace(file: antProp.'main.destdir',
				          token: "<!--CRUDVIEWS-->", value: "<!--CRUDVIEWS-->\n		"+
									"<view${domainClass.shortName}:${domainClass.shortName}CRUDView height=\"100%\" "+
									"label=\"${domainClass.shortName}\" name=\"${domainClass.propertyName}CRUDView\"/>")
}
