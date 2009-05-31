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


class AddressService 
{

		static expose = ['flex-remoting']
    boolean transactional = true
		//Injected into Service
		def messageSource
		
	  def save(Address address) throws Exception
		{
			if (address.id)
			{
				def addressMerged = address.merge()
				
				if(addressMerged)
					address = addressMerged
			}
			else
			{
				address.save()
			}
			
			if(!address.hasErrors()) 
			{
          return address
      }

			throw new Exception(getMessages(address.errors))
	  }
		
		def list()
		{
			Address.list()
		}
		
		def paginateList(def pageFilter)
		{
			if (pageFilter.totalChange)
			{
				int count = Math.ceil(Address.count()/pageFilter.max);
				pageFilter.setTotal(count);
			}
			
			def list
			
			if (pageFilter.query && pageFilter.query.size() > 0)
			{	

				list = Address.findAll("from Address as a where a.street like :search or a.zip like :search",
								[search:"${pageFilter.query}%"],
								pageFilter.getParams())
			}
			else
			{
				list = Address.list(pageFilter.getParams())
			}
			
			pageFilter.list = list
			pageFilter
		}
		
		def destroy(Address address)
		{
			address.delete(flush:true)
			
			if (!address.hasErrors())
				return address
			
			throw new Exception(getMessages(address.errors))
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
