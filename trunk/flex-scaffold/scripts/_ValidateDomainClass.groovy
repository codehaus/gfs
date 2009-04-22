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
 * @author Gonzalo Javier Clavell
 * @since  3-Mar-2009
 *
 */

import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU

grailsHome = Ant.project.properties."environment.GRAILS_HOME"

Ant.property(file:"${flexScaffoldPluginDir}/scripts/flexScaffold.properties")

antProp = Ant.project.properties

includeTargets << grailsScript ( "Init" )
includeTargets << grailsScript ( "Bootstrap" )

target ('validateDomainClass': "Validate domain class name")
{
	depends( checkVersion, packageApp )
	
	typeName = "Domain Class"
	promptForName()
		
	rootLoader.addURL(classesDir.toURI().toURL())
	loadApp()
	
	if (!args && args.trim() == "*")
		return
	
	if (!args && !getDomainClass(args.trim()))
	{
		println "Domain Class not exist!"
		exit(1)
	}		
}

// @return DomainClass representation if exist otherwise null
getDomainClass = 
{String name ->
  name = name.indexOf('.') > -1 ? name : GCU.getClassNameRepresentation(name)	
	def domainClass = grailsApp.getDomainClass(name)
	return domainClass
}