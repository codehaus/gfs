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
package service
{
	import mx.rpc.IResponder;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	import com.cubika.labs.pagination.PageFilter;
	
	/**
	 * @author Ezequiel Martin Apfel
	 * @since 23-Feb-2009
	 */
	public class BusinessDelegate
	{
		
		private var _responder:IResponder;
		
		public function BusinessDelegate(responder:IResponder)
		{
			_responder = responder;
		}
		
		public function list():void
		{
			getService().list().addResponder(_responder);
		}
		
		public function paginateList(page:PageFilter):void
		{
			getService().paginateList(page).addResponder(_responder);
		}
		
		public function save(value:Object):void
		{
			getService().save(value).addResponder(_responder);
		}
		
		public function destroy(value:Object):void
		{
			getService().destroy(value).addResponder(_responder);
		}

		protected function getService():RemoteObject
		{
			return null;
		}

	}
}