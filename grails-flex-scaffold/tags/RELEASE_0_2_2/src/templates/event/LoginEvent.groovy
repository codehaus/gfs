////////////////////////////////////////////////////////////////////
// Copyright 2009 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
////////////////////////////////////////////////////////////////////

package event.gfs
{
	import com.adobe.cairngorm.control.CairngormEvent;

	import command.gfs.LoginCommand;

	import control.ApplicationController;

	public class LoginEvent extends CairngormEvent
	{
		public static const EVENT_NAME:String = "loginEvent";

		public var username:String

		public var password:String

		public function LoginEvent(username:String,password:String)
		{
			super(EVENT_NAME);

			this.username = username;
			this.password = password;

			ApplicationController.instance.registerCommand(EVENT_NAME,LoginCommand);
		}
	}
}