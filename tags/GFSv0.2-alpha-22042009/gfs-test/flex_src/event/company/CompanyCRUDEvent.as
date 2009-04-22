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
package event.company
{
	import com.adobe.cairngorm.control.CairngormEvent;
 	
	import control.ApplicationController;
	
	import command.company.CompanyCreateCommand;
	import command.company.CompanyDeleteCommand;
	import command.company.CompanyListCommand;
	import command.company.CompanySaveOrUpdateCommand;
	import command.company.CompanyDeleteCommand;
	import command.company.CompanySelectCommand;
	
	import vo.company.CompanyVO;

	/**
	 * @author Ezequiel Martin Apfel
	 * @since 23-Feb-2009
	 */
	public class CompanyCRUDEvent extends CairngormEvent
	{
		
		static public var LIST_EVENT:String 					= "listCompanyEvent";
		static public var SAVE_OR_UPDATE_EVENT:String = "saveCompanyEvent";
		static public var SELECT_EVENT:String 				= "selectCompanyEvent";
		static public var CREATE_EVENT:String 				= "createCompanyEvent";
		static public var DELETE_EVENT:String 				= "deleteCompanyEvent";
		
		public var vo:CompanyVO;
		
		public function CompanyCRUDEvent(eventType:String,value:CompanyVO=null)
		{
			super(eventType);
			
			registersCommands();
			
			vo = value;
		}
		
		private function registersCommands():void
		{
			ApplicationController.instance.registerCommand(LIST_EVENT,CompanyListCommand);
			ApplicationController.instance.registerCommand(SAVE_OR_UPDATE_EVENT,CompanySaveOrUpdateCommand);
			ApplicationController.instance.registerCommand(SELECT_EVENT,CompanySelectCommand);
			ApplicationController.instance.registerCommand(CREATE_EVENT,CompanyCreateCommand);
			ApplicationController.instance.registerCommand(DELETE_EVENT,CompanyDeleteCommand);
		}
	}
}