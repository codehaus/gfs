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
package event.${domainClass.propertyName}
{	
  import com.adobe.cairngorm.control.CairngormEvent;
	
	import control.ApplicationController;
	
	import command.${domainClass.propertyName}.${className}GetPaginationListCommand;
	
	import com.cubika.labs.pagination.PageFilter;
	
	
	
	/**
	 * @author Ezequiel Martin Apfel
	 * @since 23-Feb-2009
	 */
	public class Get${className}PaginationEvent extends CairngormEvent
	{
		
		static public const EVENT_NAME:String = "get${className}PaginationEvent";
		
		public var page:PageFilter;
		
		public function Get${className}PaginationEvent(_pageFilter:PageFilter)
		{
			super(EVENT_NAME);
			
			ApplicationController.instance.registerCommand(EVENT_NAME,${className}GetPaginationListCommand);
			
			page = _pageFilter;
		}

	}
}