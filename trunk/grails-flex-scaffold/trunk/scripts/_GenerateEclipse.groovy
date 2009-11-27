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
 * @since 03-Mar-2009
 */

import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU

grailsHome = Ant.project.properties."environment.GRAILS_HOME"

Ant.property(file:"$flexScaffoldPluginDir/scripts/flexScaffold.properties")

def antProp = Ant.project.properties

//Generate .project, .actionScriptProperties, .flexProperties to import into FlexBuilder
generateFlexBuilder =
{	Map args = [:] ->
	def servicePath = "\${DOCUMENTS}/$grailsAppName/web-app/WEB-INF/flex/services-config.xml"
	def serverRoot = "http://localhost:8080/"
	def serverRootUrl = "http://localhost:8080/$grailsAppName"
	def outputFolder = "web-app"
	def localePath = "\${DOCUMENTS}/$grailsAppName/grails-app/i18n"
	
	//@service-path@
	//@project-name@
	//@server-root@
	//@server-root-url@
	//@output-folder@
	
	if (!new File('web-app/WEB-INF/flex/services-config.xml').exists())
	{
		Ant.mkdir(dir:"web-app/WEB-INF/flex/")
		Ant.copy(file:"$flexScaffoldPluginDir/src/flex/services-config.xml",tofile:"$basedir/web-app/WEB-INF/flex/services-config.xml", overwrite:true)
	}
	else
	{
		Ant.copy(file:"$flexScaffoldPluginDir/src/flex/services-config.xml",tofile:"$basedir/web-app/WEB-INF/flex/services-config.xml", overwrite:true)
	}
	
	Ant.sequential
	{
		//Add flex-nature to .project
		copy(file: "$flexScaffoldPluginDir/src/flex/eclipse/eclipse.project", 
			tofile: "$basedir/.project", overwrite: true)
		replace(file: "$basedir/.project",
			token: "@project-name@", value: "$grailsAppName")
		
		//Add .flexProperties
		copy(file: "$flexScaffoldPluginDir/src/flex/eclipse/eclipse.flexProperties", 
			tofile: "$basedir/.flexProperties", overwrite: true)
		replace(file: "$basedir/.flexProperties",
			token: "@server-root@", value: "$serverRoot")
		replace(file: "$basedir/.flexProperties",
			token: "@server-root-url@", value: "$serverRootUrl")
		replace(file: "$basedir/.flexProperties",
			token: "@project-name@", value: "$grailsAppName")
		
			//Add .actionScriptProperties
		copy(file: "$flexScaffoldPluginDir/src/flex/eclipse/eclipse.actionScriptProperties", 
			tofile: "$basedir/.actionScriptProperties", overwrite: true)
		replace(file: "$basedir/.actionScriptProperties",
			token: "@service-path@", value: "$servicePath")
		replace(file: "$basedir/.actionScriptProperties",
		  token: "@output-folder@", value: "$outputFolder")
		replace(file: "$basedir/.actionScriptProperties",
		  token: "@server-root-url@", value: "$serverRootUrl")
		replace(file: "$basedir/.actionScriptProperties",
		  token: "@locale-path@", value: "${localePath}")		

		//Copy libs
		copy(file: "$flexScaffoldPluginDir/src/flex/libs/cubikalabscommons.swc", 
			tofile: "$basedir/flex_libs/cubikalabscommons.swc", overwrite: true)
		copy(file: "$flexScaffoldPluginDir/src/flex/libs/Cairngorm.swc", 
			tofile: "$basedir/flex_libs/Cairngorm.swc", overwrite: true)
		copy(file: "$flexScaffoldPluginDir/src/flex/libs/as3corelib.swc",
			tofile: "$basedir/flex_libs/as3corelib.swc", overwrite: true)
	}
}
