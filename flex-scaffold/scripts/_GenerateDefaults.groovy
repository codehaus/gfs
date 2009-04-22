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
 * @since 24-Feb-2009
 */

import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU

grailsHome = Ant.project.properties."environment.GRAILS_HOME"

Ant.property(file:"${flexScaffoldPluginDir}/scripts/flexScaffold.properties")

def antProp = Ant.project.properties

generateDefaults =
{ Map args = [:] ->
	generateNavigation()
	generateLocales()
	generateBusinessDelegate()
	generateController()
}

void generateNavigation()
{
	def nameDir = antProp.'event.destdir'
	def classNameDir = "${nameDir}/DefaultNavigationEvent.as";
	def templateDir = "${flexScaffoldPluginDir}"+antProp.'event.defaultnavigationfile'
	
	if (new File(classNameDir).exists())
		return
	
	Ant.copy(file:templateDir, tofile:classNameDir)
	println "${classNameDir} Done!"
	
	nameDir = antProp.'command.destdir'
	classNameDir = "${nameDir}/DefaultNavigationCommand.as";
	templateDir = "${flexScaffoldPluginDir}"+antProp.'command.defaultnavigationfile'
	
	Ant.copy(file:templateDir, tofile:classNameDir)
	println "${classNameDir} Done!"
	
	nameDir = antProp.'event.destdir'
	classNameDir = "${nameDir}/AlternativeNavigationEvent.as";
	templateDir = "${flexScaffoldPluginDir}"+antProp.'event.alternatenavigationfile'
	
	Ant.copy(file:templateDir, tofile:classNameDir)
	println "${classNameDir} Done!"
	
	nameDir = antProp.'command.destdir'
	classNameDir = "${nameDir}/AlternativeNavigationCommand.as";
	templateDir = "${flexScaffoldPluginDir}"+antProp.'command.alternatenavigationfile'
	
	Ant.copy(file:templateDir, tofile:classNameDir)
	println "${classNameDir} Done!"
}

void generateLocales()
{
	def nameDir = antProp.'event.destdir'
	def classNameDir = "${nameDir}/LocaleEvent.as";
	def templateDir = "${flexScaffoldPluginDir}"+antProp.'event.localefile'
	
	Ant.copy(file:templateDir, tofile:classNameDir)
	println "${classNameDir} Done!"
	
	nameDir = antProp.'command.destdir'
	classNameDir = "${nameDir}/LocaleCommand.as";
	templateDir = "${flexScaffoldPluginDir}"+antProp.'command.localefile'
	
	Ant.copy(file:templateDir, tofile:classNameDir)
	println "${classNameDir} Done!"
}

void generateBusinessDelegate()
{
	def nameDir = antProp.'service.destdir'
	def classNameDir = "${nameDir}/BusinessDelegate.as";
	def templateDir = "${flexScaffoldPluginDir}"+antProp.'delegate.businessfile'
	
	if (new File(classNameDir).exists())
		return
	
	Ant.copy(file:templateDir, tofile:classNameDir)
	println "${classNameDir} Done!"
}

void generateController()
{
	def nameDir = antProp.'controller.destdir'
	def classNameDir = "${nameDir}/ApplicationController.as";
	def templateDir = "${flexScaffoldPluginDir}"+antProp.'controller.applicationfile'
	
	if (new File(classNameDir).exists())
		return
	
	Ant.copy(file:templateDir, tofile:classNameDir)
	println "${classNameDir} Done!"
}
