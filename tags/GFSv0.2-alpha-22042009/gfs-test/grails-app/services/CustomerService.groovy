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

class CustomerService 
{

		static expose = ['flex-remoting']
    boolean transactional = true
		//Injected into Service
		def messageSource
		
	  def save(Customer customer) throws Exception
		{
			if (customer.id)
			{
				
				//def aPhone = Phone.get(customer.phone.id)
				//aPhone.properties = customer.phone.properties
				
				//aPhone.save()
				
				//customer.phone = aPhone
				//customer.refresh()
				def customerMerged = customer.merge()

				if(customerMerged)
					customer = customerMerged

			}
			else
			{
				customer.save(flush:true)
			}
			
			if(!customer.hasErrors()) 
			{
          return customer
      }

			throw new Exception(getMessages(customer.errors))
	  }
		
		def list()
		{
			Customer.list()
		}
		
		def paginateList(def pageFilter)
		{
			if (pageFilter.totalChange)
			{
				int count = Math.ceil(Customer.count()/pageFilter.max);
				pageFilter.setTotal(count);
			}
			
			def list
			
			if (pageFilter.query && pageFilter.query.size() > 0)
			{	

				list = Customer.findAll("from Customer as c where c.firstName like :search or c.lastName like :search or c.age like :search or c.email like :search or c.maritalStatus like :search or c.blog like :search",
								[search:"${pageFilter.query}%"],
								pageFilter.getParams())
			}
			else
			{
				list = Customer.list(pageFilter.getParams())
			}
			
			pageFilter.list = list
			pageFilter
		}
		
		def destroy(Customer customer)
		{
			customer.delete(flush:true)
			
			if (!customer.hasErrors())
				return customer
			
			throw new Exception(getMessages(customer.errors))
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
