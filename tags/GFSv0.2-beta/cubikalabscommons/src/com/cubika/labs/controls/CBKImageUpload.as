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
	import flash.display.Loader;
	import flash.events.DataEvent;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	
	import mx.containers.Canvas;
	import mx.controls.Image;
	import mx.events.FlexEvent;
	
	
	/**
 	* Gets logic to upload images and show  
 	* @author Ezequiel Martin Apfel
 	* @since 28-Apr-2009
 	*/
	public class CBKImageUpload extends CBKUploadBase
	{
		protected var image:Image;
		
		public function CBKImageUpload()
		{
			super();
		}
		
		override protected function doInit(event:FlexEvent):void
		{
			super.doInit(event);
			
			//image.addEventListener(MouseEvent.MOUSE_OVER,rolloverHandler,false,0,true);
			//image.addEventListener(MouseEvent.MOUSE_OUT,rolloutHandler,false,0,true);		
		}
		
		/* private function rolloverHandler(event:MouseEvent):void
		{
			deleteButton.visible = true;	
		}
		
		private function rolloutHandler(event:MouseEvent):void
		{
			trace("target: ",event.target,"currentTarget: ",event.currentTarget);
			deleteButton.visible = false;	
		} */
		
		private function getImage():void
		{
			var request:URLRequest = new URLRequest();
			var sendVars:URLVariables = new URLVariables();
			var urlLoader:Loader = new Loader()
			
			sendVars.filePath = _filePath;
			
			request.method = URLRequestMethod.POST;
			request.url = url+"/get"
			request.data = sendVars;
			
			urlLoader.contentLoaderInfo.addEventListener(Event.COMPLETE,imageCompleteHandler);
			urlLoader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, imageErrorHandler);
			
			urlLoader.load(request)
		}
		
		private function imageCompleteHandler(event:Event):void
		{
			trace("imageCompleteHandler");
			image.source = event.currentTarget.bytes
			image.alpha = 1;
			
			load = true;
			changeButton(load);
		}
		
		protected function imageErrorHandler(event:IOErrorEvent):void
		{
			trace("imageErrorHandler: ",event.text);	
		}
		
		override protected function deleteCompleteHandler(event:Event):void
		{
			super.deleteCompleteHandler(event);
			image.alpha = 0.5;
			image.source = null;
		}
		
		override protected function completeDataHandler(event:DataEvent):void
		{
			super.completeDataHandler(event);
			image.alpha = 1;
			getImage();
		}
		
		override protected function createChildren():void
		{
			super.createChildren();
			
			image = new Image();
			image.focusEnabled = false;
			image.width = 160;
			image.height = 120;
			image.alpha = 0.5;
			
			_progress.setStyle("trackHeight",120);
			
			var content:Canvas = new Canvas();
			
			content.addChild(_progress);
			content.addChild(image);
			
			content.addEventListener(FlexEvent.CREATION_COMPLETE,contentComplete,false,0,true);
			
			addChild(content);
			addChild(button);
		}
		
		private function contentComplete(event:FlexEvent):void
		{
			invalidateDisplayList();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			
			//deleteButton.visible = false;
			deleteButton.x = image.width + 2;
			deleteButton.y = image.height - deleteButton.height;
			button.x = image.width + 2;
			button.y = image.height - button.height;
		}
		
		override protected function commitProperties():void
		{
			if (filePath && filePath != "")
			{
				getImage();
			}
			else
			{
				image.source = null;
				load = false;
				changeButton(load);
			}
		}
	}
}