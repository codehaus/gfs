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

package command.gfs
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;

	import event.gfs.LoginEvent;

	import model.ApplicationModelLocator;

	import mx.controls.Alert;
	import mx.managers.PopUpManager;
	import mx.messaging.config.ServerConfig;
	import mx.rpc.IResponder;


	public class LoginCommand implements ICommand, IResponder
	{
		public var appModel:ApplicationModelLocator = ApplicationModelLocator.instance;

		public function execute(event:CairngormEvent):void
		{
			var loginEvent:LoginEvent = LoginEvent(event);

			//new UsuarioBusinessDelegate(this).login(loginEvent.username,loginEvent.password)
			ServerConfig.getChannelSet("grails-amf").login(loginEvent.username,loginEvent.password).addResponder(this);
		}

		public function result(data:Object):void
		{
			//appModel.currentUser = data.result;
			PopUpManager.removePopUp(appModel.popup);
			appModel.currentView = 1;

		}

		public function fault(info:Object):void
		{
            Alert.show(info.fault.faultString,"Error");
		}
	}
}
