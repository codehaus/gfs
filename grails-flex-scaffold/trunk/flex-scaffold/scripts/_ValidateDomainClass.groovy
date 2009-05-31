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

import grails.util.GrailsNameUtils as GNU
import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU

grailsHome = Ant.project.properties."environment.GRAILS_HOME"

Ant.property(file:"${flexScaffoldPluginDir}/scripts/flexScaffold.properties")

antProp = Ant.project.properties

includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsCreateArtifacts")
includeTargets << grailsScript("_GrailsBootstrap")

target ('validateDomainClass': "Validate domain class name")
{
	depends(checkVersion, parseArguments, packageApp)
    promptForName(type: "Domain Class")

	rootLoader.addURL(classesDir.toURI().toURL())
	loadApp()
    //Adds Grails Application to FlexScaffoldUtils
    FSU.grailsApplication = grailsApp
  try 
	{
      def name = argsMap["params"][0]
      if (!name || name == "*") 
			{
          return
      }
      else if (name && !getDomainClass(name))
			{
				println "Domain Class not exist!"
				exit(1)
			}
  }
  catch(Exception e) 
	{
      logError("Error running validate domain class", e)
      exit(1)
  }	
}

// @return DomainClass representation if exist otherwise null
getDomainClass = 
{String name ->
  	name = name.indexOf('.') > -1 ? name : GNU.getClassNameRepresentation(name)	
	def domainClass = grailsApp.getDomainClass(name)
	return domainClass
}