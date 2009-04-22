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
 */


 
package model.customer
{
	import mx.collections.ArrayCollection;
	import com.cubika.labs.controls.reporting.column.ReportingColumnDescriptor;
	/**
	 * @author Gonzalo Javier Clavell
	 * @since 23-Mar-2009
	 */
	[Bindable]
	public class CustomerReportingModel 
	{

		public var className:String = "Customer"; 
		public var columnDataProvider:ArrayCollection;
		
		public function CustomerReportingModel():void
		{
			var propertyArray:Array = new Array();
			propertyArray.push(new ReportingColumnDescriptor("customer", "firstName","First Name",""));
			propertyArray.push(new ReportingColumnDescriptor("customer", "lastName","Last Name",""));
			propertyArray.push(new ReportingColumnDescriptor("customer", "dateOfBirth","Date Of Birth",""));
			propertyArray.push(new ReportingColumnDescriptor("customer", "age","Age",""));
			propertyArray.push(new ReportingColumnDescriptor("customer", "email","Email",""));
			propertyArray.push(new ReportingColumnDescriptor("customer", "maritalStatus","Marital Status",""));
			propertyArray.push(new ReportingColumnDescriptor("customer", "addresses","Addresses",""));
			propertyArray.push(new ReportingColumnDescriptor("customer", "company","Company","company.name"));
			propertyArray.push(new ReportingColumnDescriptor("customer", "enabled","Enabled",""));
			propertyArray.push(new ReportingColumnDescriptor("customer", "blog","Blog",""));
			propertyArray.push(new ReportingColumnDescriptor("customer", "phone","Phone",""));

			columnDataProvider = new ArrayCollection(propertyArray);
		}
	}
}