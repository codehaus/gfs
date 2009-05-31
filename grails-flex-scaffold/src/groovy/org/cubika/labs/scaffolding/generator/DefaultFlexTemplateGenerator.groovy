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

package org.cubika.labs.scaffolding.generator

import groovy.text.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.commons.GrailsDomainClass;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.scaffolding.GrailsTemplateGenerator;
import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import org.codehaus.groovy.grails.commons.ApplicationHolder;

/**
 * This class is used by plugin scripts to convert templates into actionscript archives,
 * which are compiled when "grails flex-tasks" command is executed.
 * Gives support for relationship except many-to-many
 *
 * @author Ezequiel Martin Apfel
 * @since 01-Feb-2009
 */
class DefaultFlexTemplateGenerator
{

	//static final Log LOG = LogFactory.getLog(DefaultTemplateTemplateGenerator.class);
	def engine = new SimpleTemplateEngine()
	def ant = new AntBuilder()
	/**
	 * Create Flex templates
	 *
	 * @param domainClass
	 * @param templateFile			Template file to be used
	 * @param filePath				Path for result as file
	 */
	void generateTemplate(domainClass,templateFile,filePath,typeName="")
	{
		def templateText = new File(templateFile).getText()

		def binding = [domainClass: domainClass,
	                  className: domainClass.shortName,
										typeName:typeName]

		def t = engine.createTemplate(templateText)
		def out = new File(filePath)
		def w = out.newWriter()

		t.make(binding).writeTo(w)
		w.flush()
		w.close()

		//The first time throw IllegalAccessError
		//out.withWriter {w ->
		//    t.make(binding).writeTo(w)
		//}
		//LOG.info("Create ${filePath} Done!")
	}

	/**
	 * Create Flex relational templates
	 *
	 * @param domainClass
	 * @param templateFile			Template file to be used
	 * @param filePath				Path for result as file
	 */
	void generateRelationalTemplate(relationDomainClass, domainClass,templateFile,filePath,typeName="")
	{
		def templateText = new File(templateFile).getText()

		def binding = [	domainClass: domainClass,
	                  className: domainClass.shortName,
										relationDomainClass: relationDomainClass,
										typeName:typeName]

		def t = engine.createTemplate(templateText)

		def out = new File(filePath)
		def w = out.newWriter()

		t.make(binding).writeTo(w)
		w.flush()
		w.close()
	}
	
	/**
	 * Create a Simple Templates
	 */
	void generateSimpleTemplate(templateFile, filePath, artifactName,pkg,className)
	{
		def templateText = new File(templateFile).getText()
		
		def binding = [	artifactName: artifactName,
	                  className: className,
										pkg: pkg
									]

		def t = engine.createTemplate(templateText)

		def out = new File(filePath)
		def w = out.newWriter()

		t.make(binding).writeTo(w)
		w.flush()
		w.close()
	}
	
}