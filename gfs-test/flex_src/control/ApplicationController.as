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
package control
{
	import com.adobe.cairngorm.control.FrontController;
	
	/**
	 * @author Ezequiel Martin Apfel
	 * @since 23-Feb-2009
	 */
	public class ApplicationController extends FrontController
	{
		private static var _instance:ApplicationController;
		
		public function ApplicationController(enforcer:SingletonEnforcer)
		{
			super();
		}
		
		public static function get instance():ApplicationController
		{
			if (!_instance)
				_instance = new ApplicationController(new SingletonEnforcer);
			return _instance;
		}
		
		public function registerCommand(eventName:String,command:Class):void
		{
			if (!isRegister(command))
				addCommand(eventName,command);
		}
		
		private function isRegister(command:Class):Boolean
		{
			for each (var cmd:Class in commands)
			{
				if (cmd == command)
					return true;	
			}
			
			return false;
		}
		
		
	}
}
class SingletonEnforcer{}