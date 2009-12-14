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
package service.${domainClass.propertyName}
{
	import service.BusinessDelegate;
	import mx.rpc.IResponder;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	/**
	 * @author Ezequiel Martin Apfel
	 * @since 23-Feb-2009
	 */
	public class ${className}BusinessDelegate extends BusinessDelegate
	{
		static private var _service:RemoteObject;
				
		public function ${className}BusinessDelegate(responder:IResponder)
		{
			super(responder);
		}
		
		override protected function getService():RemoteObject
		{
			if (!_service)
			{
				_service = new RemoteObject("${domainClass.propertyName}Service");
				_service.showBusyCursor = true;
				_service.concurrency = "multiple"
			}
			
			return _service;
		}
		<% import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU
			def actions = CVU.actions(domainClass)

			actions.each
			{
				println "		public function ${it.toLowerCase()}(vo:Object):void"
				println "		{"
				println "			getService().${it.toLowerCase()}(vo).addResponder(_responder);"
					print	"		}"
			}
				
		%>
	}
}