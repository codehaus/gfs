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
package org.cubika.labs.scaffolding.utils

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClassProperty
import org.cubika.labs.scaffolding.form.FormItemConstants as FIC

/**
 * ConstraintValueUtils is used to give support constraint values or metaconstraint
 * values  declared into DomainClass
 * 
 * @author Ezequiel Martin Apfel
 * @since 23-Feb-2009
 *
 */

class ConstraintValueUtils 
{
	/**
	 * Gets metaConstraint inPlace
	 * @param constraint
	 * @return Boolean - if inPlace is setted return value (true or false). Otherwise 
	 * 						return default value false
	 */
	static def getInPlace(constraint)
	{
		def inPlace = constraint.getMetaConstraintValue(FIC.IN_PLACE)
		if (inPlace == null)
			inPlace = true
		
		inPlace
	}
	
	/**
	 * Gets metaConstraint createView
	 * @param constraint
	 * @return Boolean - if createView is setted return value (true or false). Otherwise
	 * 						return default value false
	 */
	static def getCreateView(constraint)
	{
		def createView = constraint.getMetaConstraintValue("createView")
		if (createView == null)
			createView = true
		
		createView
	}
	
	/**
	 * Gets metaConstraint editView
	 * @param constraint
	 * @return Boolean - if editView is setted return value (true or false). Otherwise 
	 * 						return default value false
	 */
	static def getEditView(constraint)
	{
		def editView = constraint.getMetaConstraintValue("editView")
		if (editView == null)
			editView = true
		
		editView
	}
	
	/**
	 * Gets annotation labelField
	 * @param domainClass 	- DefaultGrailsDomainClass
	 * @return String 		- if labelField is setted return value otherwise blank String 
	 */
	static def getLabelField(domainClass)
	{
		def annotation = domainClass.getClazz().getAnnotation(org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty)
		
		if (annotation != null)
			return annotation.labelField()
	}
	
	/**
	 * Gets constraint displayName
	 * @param property - DefaultGrailsDomainClassProperty
	 * @return Boolean
	 */
	static def display(DefaultGrailsDomainClassProperty property)
	{
		def constraint = property.domainClass.getConstrainedProperties()[property.name]
		
		constraint.display
	}
	
	/**
	 * Gets metaConstraint defautlValue
	 * @param property - DefaultGrailsDomainClassProperty
	 * @return String - defaultValue if setted. Otherwise blank String
	 */
	static def defaultValue(DefaultGrailsDomainClassProperty property)
	{
		def constraint = property.domainClass.getConstrainedProperties()[property.name]
		
		def defaultValue = constraint.getMetaConstraintValue("defaultValue")
		
		if (defaultValue == null)
			defaultValue = ""
		else
			defaultValue = " = ${defaultValue}"	
			
		defaultValue
	}
	
	/**
	 * Gets metaConstrarint dateFormat 
	 * @param property - DefaultGrailsDomainClassProperty
	 * @return String - dateFormate if setted. otherwise	default value DD/MM/YYYY
	 */
	static def dateFormat(DefaultGrailsDomainClassProperty property)
	{
		def constraint = property.domainClass.getConstrainedProperties()[property.name]
		
		def dateFormat = constraint.getMetaConstraintValue("dateFormat")
		
		if (dateFormat == null)
			dateFormat = "DD/MM/YYYY"
			
		dateFormat
	}
}
