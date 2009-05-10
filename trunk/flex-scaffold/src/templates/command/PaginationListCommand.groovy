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
package command.${domainClass.propertyName}
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;
		
	import service.${domainClass.propertyName}.${className}BusinessDelegate;
	
	import event.${domainClass.propertyName}.${className}${typeName}GetPaginationEvent;
	
	import model.ApplicationModelLocator;
	import model.${domainClass.propertyName}.${className}Model;

	/**
	 * @author Ezequiel Martin Apfel
	 * @since 23-Feb-2009
	 */
	public class ${className}${typeName}GetPaginationListCommand implements ICommand, IResponder
	{
		
		private var _model:${className}Model = ApplicationModelLocator.instance.${domainClass.propertyName}Model;

		public function execute(event:CairngormEvent):void
		{
			var getEvent:${className}${typeName}GetPaginationEvent = ${className}${typeName}GetPaginationEvent(event); 
						
			new ${className}BusinessDelegate(this).paginateList(getEvent.page);

		}
		
		public function result(data:Object):void
		{
			_model.editView = false;
			_model.${typeName.toLowerCase()}page = data.result;
		}
		
		public function fault(info:Object):void
		{
			_model.editView = false;
			
			if(info.fault.rootCause)
				Alert.show(info.fault.rootCause.message,"Error");
			else
				Alert.show(info.fault.faultDetail,"Error");
		}
		
	}
}
