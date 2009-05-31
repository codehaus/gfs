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
package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import command.LocaleCommand;
	
	import control.ApplicationController;

	/**
	 * 
	 * @author Ariel Gimenez
	 * @since 17-Mar-2009
	 */	
	public class LocaleEvent extends CairngormEvent
	{
		public static const EVENT_NAME:String = "localeEvent";
		
		public var prefix:String;
		
		/**
		 * 
		 * @param String prefix
		 * 
		 */		
		public function LocaleEvent(prefix:String)
		{
			super(EVENT_NAME);
			
			ApplicationController.instance.registerCommand(EVENT_NAME,LocaleCommand);
			
			this.prefix = prefix;
		}
		
	}
}