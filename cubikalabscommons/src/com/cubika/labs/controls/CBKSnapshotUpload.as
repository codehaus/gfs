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
package com.cubika.labs.controls
{
	import com.cubika.labs.pops.SnapshotPopUp;
	import com.dynamicflash.util.Base64;
	
	import flash.display.DisplayObject;
	import flash.events.DataEvent;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.MouseEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	import flash.utils.ByteArray;
	
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.managers.PopUpManager;
	
	/**
 	* Gets logic to upload snapshots  
 	* @author Ezequiel Martin Apfel
 	* @since 28-Apr-2009
 	*/
	public class CBKSnapshotUpload extends CBKImageUpload
	{
		private var _popUp:SnapshotPopUp;
		private var _imageArray:ByteArray;
		private var _snapshotName:String;
		
		public function CBKSnapshotUpload()
		{
			super();
		}
		
		private function okHandler(event:Event):void
		{
			_imageArray = _popUp.image;
			upload();
		}
		
		override protected function upload():void
		{
			if (!url || url == "")
			{
				Alert.show("URL must be declared");
				return
			}
		
			var request:URLRequest = new URLRequest();
			var sendVars:URLVariables = new URLVariables();
			var loader:URLLoader = new URLLoader();
			
			_snapshotName = "snapshot_"+new Date().getTime()+".jpg";
			
			sendVars.name = _snapshotName;  
        	          	  
        	sendVars.image = Base64.encodeByteArray(_imageArray);
			
			
			request.url = url+"/snapshot";  
        	request.method = URLRequestMethod.POST;
        	request.data = sendVars;  
        	  
        	loader.addEventListener(Event.COMPLETE, completeHandler,false,0,true);
        	loader.addEventListener(IOErrorEvent.IO_ERROR,imageErrorHandler,false,0,true);
        	
        	loader.load(request);  

			button.enabled = false;
		}
		
		override protected function createChildren():void
		{
			super.createChildren();
			button.styleName = "cameraButton"	
		}
		
		override protected function completeHandler(event:Event):void
		{
			super.completeHandler(event);
			
			var e:DataEvent = new DataEvent(DataEvent.UPLOAD_COMPLETE_DATA);
			e.data = event.target.data;
			
			completeDataHandler(e);
		}
		
		override protected function findHandler(event:MouseEvent):void
		{
			_popUp = PopUpManager.createPopUp(Application.application as DisplayObject, SnapshotPopUp,true) as SnapshotPopUp;
			
			PopUpManager.centerPopUp(_popUp);	
			
			_popUp.addEventListener(SnapshotPopUp.OK_SNAP_EVENT,okHandler,false,0,true);
		}
		
	}
}