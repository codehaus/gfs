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

import org.springframework.web.servlet.support.RequestContextUtils as RCU
import org.springframework.web.context.request.RequestContextHolder as RCH

<%
if(domainClass.packageName || domainClass.packageName != "")
{
    println "import ${domainClass.fullName}"
}
%>
class ${className}Service 
{

		static expose = ['flex-remoting']
    boolean transactional = true
		//Injected into Service
		def messageSource
		
	  def save(${className} ${domainClass.propertyName}) throws Exception
		{
			if (${domainClass.propertyName}.id)
			{
				def ${domainClass.propertyName}Merged = ${domainClass.propertyName}.merge()
				
				if(${domainClass.propertyName}Merged)
					${domainClass.propertyName} = ${domainClass.propertyName}Merged
			}
			else
			{
				${domainClass.propertyName}.save()
			}
			
			if(!${domainClass.propertyName}.hasErrors()) 
			{
          return ${domainClass.propertyName}
      }

			throw new Exception(getMessages(${domainClass.propertyName}.errors))
	  }
		
		def list()
		{
			${className}.list()
		}
		
		def paginateList(def pageFilter)
		{
			if (pageFilter.totalChange)
			{
				int count = Math.ceil(${className}.count()/pageFilter.max);
				pageFilter.setTotal(count);
			}
			
			def list
			
			if (pageFilter.query && pageFilter.query.size() > 0)
			{	
<% import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
				def prefix = className.getAt(0).toLowerCase()				
				def props = FSU.getPropertiesWithoutIdentity(domainClass,true)
				def query="from ${className} as ${prefix} where"
				
				def params = "\"\${pageFilter.query}%\""
				
				props.each
				{
					if (!it.isOneToOne() && !it.isOneToMany() && !it.isManyToOne() && it.type != Boolean.class && it.type != Date.class)	
					{
						query +=" ${prefix}.${it.name} like :search or"
					}
				}
				
				if (query.endsWith(" or"))
					query = query.substring(0,query.size()-3)
%>
				list = ${className}.findAll("${query}",
								[search:${params}],
								pageFilter.getParams())
			}
			else
			{
				list = ${className}.list(pageFilter.getParams())
			}
			
			pageFilter.list = list
			pageFilter
		}
		
		def destroy(def ${domainClass.propertyName}List)
		{
			${domainClass.propertyName}List.each
			{
				it.delete(flush:true)
				
				if (it.hasErrors())
					throw new Exception(getMessages(it.errors))
			}
			
			${domainClass.propertyName}List
		}
		
		private def getMessages(errors) 
		{
			def request = RCH.currentRequestAttributes().getRequest()
			def locale = RCU.getLocale(request)
			
			def errorString = ""
			
			errors.allErrors.each 
			{
				<%
					print "errorString +=messageSource.getMessage(it,locale)+\"\\n\""
				%>
			}
			
			errorString
		}
		
<%	import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU
			def actions = CVU.actions(domainClass)
			
			actions.each
			{
				println "		def ${it.toLowerCase()}(${className} ${domainClass.propertyName})"
				println "		{"
				println "			return \"add logic to ${it}\""
				println	"		}"
			}
%>
}
