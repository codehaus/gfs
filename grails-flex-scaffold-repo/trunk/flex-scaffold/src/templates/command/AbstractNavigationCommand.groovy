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
package command.gfs
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;

	import event.DefaultNavigationEvent;

	import mx.controls.Alert;
	import mx.core.Application;
	import mx.core.IFlexDisplayObject;
	import mx.managers.PopUpManager;
	import mx.rpc.IResponder;

	public class AbstractNavigationCommand implements ICommand, IResponder
	{
		protected var _model:Object;

        protected var _navigateKey:String;

		public function execute(event:CairngormEvent):void
		{
			throw new Error("execute must be override ");
		}

		public function result(data:Object):void
		{
			doResult(data);

            if (_navigateKey)
                navigate(_navigateKey)
		}

		public function fault(info:Object):void
		{
			if(info.fault.rootCause)
            Alert.show(info.fault.rootCause.message,"Error");
			else
            Alert.show(info.fault.faultDetail,"Error");
			doFault(info)
		}

        protected function navigate(value:String):void
        {
            if(!_model.callFromPop)
            {
                new DefaultNavigationEvent(value).dispatch();
            }
			else
			{
				var obj:Object = Application.application.systemManager.topLevelSystemManager.getChildByName(_model.callFromPop);
				PopUpManager.removePopUp(IFlexDisplayObject(obj));
				_model.callFromPop = null;
			}
        }

		protected function doResult(data:Object):void {throw new Error("doResult must be override ")}

		protected function doFault(data:Object):void {throw new Error("doFault must be override ")}
    }
}