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
	
	import event.${domainClass.propertyName}.${className}${typeName}Event;
	
	import model.ApplicationModelLocator;

  import command.gfs.AbstractNavigationCommand;

	/**
	 * @author Ezequiel Martin Apfel
	 * @since 19-Jun-2009
	 */
	public class ${className}${typeName}Command implements ICommand,IResponder
	{

		public function execute(event:CairngormEvent):void
		{
			var e:${className}${typeName}Event = ${className}${typeName}Event(event); 

			new ${className}BusinessDelegate(this).${typeName.toLowerCase()}(e.vo);
		}
		
		public function result(data:Object):void
		{
			Alert.show(data.result);
		}
		
		public function fault(info:Object):void
		{
     	if(info.fault.rootCause)
      	Alert.show(info.fault.rootCause.message,"Error");
			else
      	Alert.show(info.fault.faultDetail,"Error");
		}
	}
}
