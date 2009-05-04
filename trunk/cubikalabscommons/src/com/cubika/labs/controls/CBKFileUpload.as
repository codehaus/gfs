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
	import flash.events.Event;
	
	import mx.containers.Canvas;
	import mx.controls.TextInput;
	import mx.events.FlexEvent;
	
	/**
 	* Gets logic to upload files  
 	* @author Ezequiel Martin Apfel
 	* @since 28-Apr-2009
 	*/
	public class CBKFileUpload extends CBKUploadBase
	{
		public var input:TextInput;
		
		public function CBKFileUpload()
		{
			super();
		}
		
		override protected function doInit(event:FlexEvent):void
		{
			super.doInit(event);
		}
		
		private function loadFile():void
		{
			//por el momento solo pega el filepath
			if (filePath && filePath != "")
			{
				load = true;
				input.text = filePath;
				changeButton(load);
			}
			else
			{
				input.text = "";
				load = false;
				changeButton(load);
			}
		}
		
		override protected function upload():void
		{
			super.upload();
			
			input.text = _fileReference.name;
		}
		
		
		override protected function deleteCompleteHandler(event:Event):void
		{
			super.deleteCompleteHandler(event);
			
			input.text = "";
		}
		
		override protected function createChildren():void
		{
			super.createChildren();
			
			input = new TextInput();
			input.focusEnabled = false;
			input.alpha = 0.5;
			input.editable = false;
			
			_progress.setStyle("trackHeight",23);
			
			var content:Canvas = new Canvas();
			
			content.addChild(_progress);
			content.addChild(input);
			
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
			
			deleteButton.x = input.width+2;
			button.x = input.width+2;
		}
		
		override protected function commitProperties():void
		{
			loadFile();
		}
	}
}