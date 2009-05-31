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


class CompanyService 
{

		static expose = ['flex-remoting']
    boolean transactional = true
		//Injected into Service
		def messageSource
		
	  def save(Company company) throws Exception
		{
			if (company.id)
			{
				def companyMerged = company.merge()
				
				if(companyMerged)
					company = companyMerged
			}
			else
			{
				company.save()
			}
			
			if(!company.hasErrors()) 
			{
          return company
      }

			throw new Exception(getMessages(company.errors))
	  }
		
		def list()
		{
			Company.list()
		}
		
		def paginateList(def pageFilter)
		{
			if (pageFilter.totalChange)
			{
				int count = Math.ceil(Company.count()/pageFilter.max);
				pageFilter.setTotal(count);
			}
			
			def list
			
			if (pageFilter.query && pageFilter.query.size() > 0)
			{	

				list = Company.findAll("from Company as c where c.logo like :search or c.name like :search or c.description like :search",
								[search:"${pageFilter.query}%"],
								pageFilter.getParams())
			}
			else
			{
				list = Company.list(pageFilter.getParams())
			}
			
			pageFilter.list = list
			pageFilter
		}
		
		def destroy(Company company)
		{
			company.delete(flush:true)
			
			if (!company.hasErrors())
				return company
			
			throw new Exception(getMessages(company.errors))
		}
		
		private def getMessages(errors) 
		{
			def request = RCH.currentRequestAttributes().getRequest()
			def locale = RCU.getLocale(request)
			
			def errorString = ""
			
			errors.allErrors.each 
			{
				errorString +=messageSource.getMessage(it,locale)+"\n"
			}
			
			errorString
		}
}
