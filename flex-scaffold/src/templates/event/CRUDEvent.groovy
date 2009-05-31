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
	
	import command.${domainClass.propertyName}.${className}CreateCommand;
	import command.${domainClass.propertyName}.${className}DeleteCommand;
	import command.${domainClass.propertyName}.${className}ListCommand;
	import command.${domainClass.propertyName}.${className}SaveOrUpdateCommand;
	import command.${domainClass.propertyName}.${className}DeleteCommand;
    import command.${domainClass.propertyName}.${className}CancelCommand;
	import command.${domainClass.propertyName}.${className}SelectCommand;
	
	import vo.${domainClass.propertyName}.${className}VO;

	/**
	 * @author Ezequiel Martin Apfel
	 * @since 23-Feb-2009
	 */
	public class ${className}CRUDEvent extends CairngormEvent
	{
		
		static public var LIST_EVENT:String 				= "list${className}Event";
		static public var SAVE_OR_UPDATE_EVENT:String       = "save${className}Event";
		static public var SELECT_EVENT:String 				= "select${className}Event";
		static public var CREATE_EVENT:String 				= "create${className}Event";
		static public var DELETE_EVENT:String 				= "delete${className}Event";
        static public var CANCEL_EVENT:String 				= "cancel${className}Event";
		
		public var vo:${className}VO;

        public var popUpName:String;


		public function ${className}CRUDEvent(eventType:String,value:${className}VO=null,popName:String = null)
		{
			super(eventType);
			
			registersCommands();
			
            vo = value;
            popUpName = popName;
		}
		
		private function registersCommands():void
		{
			ApplicationController.instance.registerCommand(LIST_EVENT,${className}ListCommand);
			ApplicationController.instance.registerCommand(SAVE_OR_UPDATE_EVENT,${className}SaveOrUpdateCommand);
			ApplicationController.instance.registerCommand(SELECT_EVENT,${className}SelectCommand);
			ApplicationController.instance.registerCommand(CREATE_EVENT,${className}CreateCommand);
			ApplicationController.instance.registerCommand(DELETE_EVENT,${className}DeleteCommand);
            ApplicationController.instance.registerCommand(CANCEL_EVENT,${className}CancelCommand);
		}
	}
}