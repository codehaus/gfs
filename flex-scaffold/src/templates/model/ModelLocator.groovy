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
package model
{
	
	/*IMPORTS*/
	
	/**
	 * @author Ezequiel Martin Apfel
	 * @since 23-Feb-2009
	 */
	public class ApplicationModelLocator
	{
		static private var _instance:ApplicationModelLocator;
		
		// Locale var
		[Bindable]
		public var localePrefix:String = 'message';
		
		/*PROPERTIES*/
		
		public function ApplicationModelLocator(enforcer:SingletonEnforcer)
		{
		}
		
		static public function get instance():ApplicationModelLocator
		{
			if (!_instance)
				_instance = new ApplicationModelLocator(new SingletonEnforcer());
			
			return _instance;
		}
		
		/*GETTERS*/
			
	}
}
class SingletonEnforcer{}