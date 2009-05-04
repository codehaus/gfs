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
package com.cubika.labs.pops
{
	import com.cubika.labs.controls.CBKCameraSnapshot;
	import com.cubika.labs.utils.MultipleRM;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.utils.ByteArray;
	
	import mx.containers.HBox;
	import mx.containers.TitleWindow;
	import mx.containers.VBox;
	import mx.controls.Button;
	import mx.controls.Label;
	import mx.events.CloseEvent;
	import mx.events.FlexEvent;
	import mx.managers.PopUpManager;


	[Event(type="okSnapshot")]
	[Event(type="cancelSnapshot")]
	/**
 	* Show CBKCameraSnapshot  
 	* @author Ezequiel Martin Apfel
 	* @since 28-Apr-2009
 	*/
	public class SnapshotPopUp extends TitleWindow
	{
		public static var OK_SNAP_EVENT:String = "okSnapshot";
		
		public static var CANCEL_SNAP_EVENT:String = "cancelSnapshot";
		
		public var image:ByteArray;
		
		private var _cameraSnapshot:CBKCameraSnapshot;
		
		private var _okButton:Button;
		
		private var _noButton:Button;
		
		public function SnapshotPopUp()
		{
			super();
			
			showCloseButton=true;
			
			title = MultipleRM.getSuppliedString(MultipleRM.localePrefix,"snapshot.popup.title","Take a picture");
			
			addEventListener(FlexEvent.CREATION_COMPLETE,doInit,false,0,true);
		}
		
		private function doInit(event:FlexEvent):void
		{
			addEventListener(CloseEvent.CLOSE,closeHandler,false,0,true);	
			_okButton.addEventListener(MouseEvent.CLICK,okHandler,false,0,true);
			_noButton.addEventListener(MouseEvent.CLICK,closeHandler,false,0,true);
			_cameraSnapshot.addEventListener(CBKCameraSnapshot.SNAPSHOT_EVENT,snapshotHandler,false,0,true);
		}
		
		private function snapshotHandler(event:Event):void
		{
			_okButton.enabled = true;
		}
		
		private function okHandler(event:MouseEvent):void
		{
			image = _cameraSnapshot.image;	
			dispatchEvent(new Event("okSnapshot"));
			
			_cameraSnapshot.dispose();
			
			PopUpManager.removePopUp(this);
		}
		
		private function closeHandler(event:Event):void
		{
			PopUpManager.removePopUp(this);
			
			_cameraSnapshot = null;
			
			dispatchEvent(new Event("cancelSnapshot"));
		}
		
		
		override protected function createChildren():void
		{
			super.createChildren();
			
			_cameraSnapshot = new CBKCameraSnapshot();
			_okButton = new Button();
			_noButton = new Button();
			
			_okButton.label = MultipleRM.getSuppliedString(MultipleRM.localePrefix,"generic.accept","Accept");
			_noButton.label = MultipleRM.getSuppliedString(MultipleRM.localePrefix,"generic.cancel","Cancel");
			
			_okButton.enabled = false;
			
			var buttonContent:HBox = new HBox();
			buttonContent.percentWidth = 100;
			buttonContent.percentHeight = 100;
			buttonContent.setStyle("horizontalAlign","right");
			
			buttonContent.addChild(_okButton);
			buttonContent.addChild(_noButton);
			
			
			var descriptionContent:VBox = new VBox();
			
			var lbComment:Label = new Label();
			lbComment.styleName = "commentsText";
			lbComment.text = MultipleRM.getSuppliedString(MultipleRM.localePrefix,"generic.snaptshot.comment","Click on picture to get photo");
			

			
			descriptionContent.addChild(_cameraSnapshot);
			descriptionContent.addChild(lbComment);
			
			addChild(descriptionContent);
			addChild(buttonContent);
		}
		
	}
}