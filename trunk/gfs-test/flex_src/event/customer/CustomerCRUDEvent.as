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
package event.customer
{
	import com.adobe.cairngorm.control.CairngormEvent;
 	
	import control.ApplicationController;
	
	import command.customer.CustomerCreateCommand;
	import command.customer.CustomerDeleteCommand;
	import command.customer.CustomerListCommand;
	import command.customer.CustomerSaveOrUpdateCommand;
	import command.customer.CustomerDeleteCommand;
	import command.customer.CustomerSelectCommand;
	
	import vo.customer.CustomerVO;

	/**
	 * @author Ezequiel Martin Apfel
	 * @since 23-Feb-2009
	 */
	public class CustomerCRUDEvent extends CairngormEvent
	{
		
		static public var LIST_EVENT:String 					= "listCustomerEvent";
		static public var SAVE_OR_UPDATE_EVENT:String = "saveCustomerEvent";
		static public var SELECT_EVENT:String 				= "selectCustomerEvent";
		static public var CREATE_EVENT:String 				= "createCustomerEvent";
		static public var DELETE_EVENT:String 				= "deleteCustomerEvent";
		
		public var vo:CustomerVO;
		
		public function CustomerCRUDEvent(eventType:String,value:CustomerVO=null)
		{
			super(eventType);
			
			registersCommands();
			
			vo = value;
		}
		
		private function registersCommands():void
		{
			ApplicationController.instance.registerCommand(LIST_EVENT,CustomerListCommand);
			ApplicationController.instance.registerCommand(SAVE_OR_UPDATE_EVENT,CustomerSaveOrUpdateCommand);
			ApplicationController.instance.registerCommand(SELECT_EVENT,CustomerSelectCommand);
			ApplicationController.instance.registerCommand(CREATE_EVENT,CustomerCreateCommand);
			ApplicationController.instance.registerCommand(DELETE_EVENT,CustomerDeleteCommand);
		}
	}
}