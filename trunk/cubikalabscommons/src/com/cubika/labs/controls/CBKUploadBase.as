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
	import com.cubika.labs.event.FileExtensionEvent;
	
	import flash.events.DataEvent;
	import flash.events.Event;
	import flash.events.HTTPStatusEvent;
	import flash.events.IOErrorEvent;
	import flash.events.MouseEvent;
	import flash.events.ProgressEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.FileFilter;
	import flash.net.FileReference;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.controls.Alert;
	import mx.controls.Button;
	import mx.controls.ProgressBar;
	import mx.controls.ProgressBarLabelPlacement;
	import mx.events.FlexEvent;
	import mx.utils.StringUtil;
	
	/**
 	* Gets logic to upload files  
 	* @author Ezequiel Martin Apfel
 	* @since 28-Apr-2009
 	*/
 	[Event(name="maxFileExceed")]
 	[Event(name="fileExtensionEvent", type="com.cubika.labs.event.FileExtensionEvent")]
	public class CBKUploadBase extends Canvas
	{
		public var button:Button;
		
		public var deleteButton:Button;
		
		public var url:String;
		
		public var autoload:Boolean = false;
		
		//Max File Size to upload
		public var maxFileSize:Number;
		
		protected var _fileFilters:FileFilter;
		
		protected var _fileReference:FileReference;
		
		protected var _filePath:String;
		
		protected var _progress:ProgressBar;
		
		protected var load:Boolean = false;
		
		public function CBKUploadBase()
		{
			addEventListener(FlexEvent.CREATION_COMPLETE,doInit,false,0,true);
		}
		
		public function set fileFilters(value:String):void
		{
			//if (!_fileFilters)
				_fileFilters = new FileFilter("Files Allowed",value);
		}
		
		
		public function get filePath():String
		{
			return _filePath;
		}
		
		public function set filePath(value:String):void
		{
			_filePath = value;		
			
			invalidateProperties();
		}
		
		protected function doInit(event:FlexEvent):void
		{
			button.addEventListener(MouseEvent.CLICK,findHandler,false,0,true);
			deleteButton.addEventListener(MouseEvent.CLICK,deleteHandler,false,0,true);
		}
		
		private function createFileReferenceAndRegisterHandlers():void
		{
			_fileReference = new FileReference();
			_fileReference.addEventListener(ProgressEvent.PROGRESS, progressHandler,false,0,true);
            _fileReference.addEventListener(Event.COMPLETE, completeHandler,false,0,true);
            _fileReference.addEventListener(IOErrorEvent.IO_ERROR, errorHandler,false,0,true);
            _fileReference.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler,false,0,true);
            _fileReference.addEventListener(DataEvent.UPLOAD_COMPLETE_DATA, completeDataHandler,false,0,true);
            _fileReference.addEventListener(Event.SELECT,selectFileHandler,false,0,true)
            _fileReference.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler,false,0,true);
            
		}

		private function httpStatusHandler(event:HTTPStatusEvent):void
		{
			trace("httpStatus ",event.status);
		}
		
		protected function progressHandler(event:ProgressEvent):void
		{
			var numPerc:Number = Math.round((event.bytesLoaded / event.bytesTotal) * 100);
			
			_progress.setProgress(numPerc,100);
			
			trace("progress: ",numPerc); 
		}
		
		protected function completeHandler(event:Event):void
		{
			trace("complete");
			populateFileExtension();
			load = true;
		}
		
		private function errorHandler(event:IOErrorEvent):void
		{
			button.enabled = true;
			throw new Error(event.text);
			trace("error: ",event.text);
		}
		
		private function securityErrorHandler(event:SecurityErrorEvent):void
		{
			button.enabled = true;
			throw new Error(event.text);
			trace("securityError: ",event.text);
		}
		
		protected function findHandler(event:MouseEvent):void
		{
			createFileReferenceAndRegisterHandlers();
			
			if (_fileFilters)
				_fileReference.browse([_fileFilters]);
			else
				_fileReference.browse();
		}
		
		protected function completeDataHandler(event:DataEvent):void
		{
			trace("completeData: ",event.data);
			
			_filePath = StringUtil.trim(String(event.data));
			
			changeButton(load);	
			//Misterioso error con el foco y el commitproperties, por eso esto va aca	
			callLater(deleteButton.setFocus);
		}
		
		
		protected function changeButton(value:Boolean):void 
		{
			if (value)
			{
				removeChildAt(1);
				addChild(deleteButton);
				load = false;
				deleteButton.enabled = true;
				_progress.setProgress(100,100);
			}
			else
			{
				removeChildAt(1);
				addChild(button);
				button.enabled = true;
				_progress.setProgress(0,100);
			}
			
			invalidateDisplayList();
		}
		
		private function selectFileHandler(event:Event):void
		{
			trace("selectFileHandler")
			if (isNaN(maxFileSize))
			{
				upload();
				return
			}
			
			if ((_fileReference.size/1024) <= maxFileSize)
			{
				upload();
				return
			}
			
			dispatchEvent(new Event("maxFileExceed"));
			
		}
		
		private function deleteHandler(event:MouseEvent):void
		{
			var request:URLRequest = new URLRequest();
			var sendVars:URLVariables = new URLVariables();
			var urlLoader:URLLoader = new URLLoader()
			
			sendVars.filePath = _filePath;
			
			request.method = URLRequestMethod.POST;
			request.url = url+"/delete"
			request.data = sendVars;
			
			
			urlLoader.addEventListener(Event.COMPLETE,deleteCompleteHandler,false,0,true);
			urlLoader.addEventListener(IOErrorEvent.IO_ERROR, deleteErrorHandler,false,0,true);
			
			urlLoader.load(request)
			deleteButton.enabled = false;
		}
		
		/**
		 * Return the file size	 
		 * @return 
		 * 
		 */		
		public function get size():Number
		{
			if (!_fileReference)
				return 0;
				
			return _fileReference.size;
		} 
		
		protected function deleteCompleteHandler(event:Event):void
		{
			trace("deleteCompleteHandler")
			load = false;
			changeButton(load);
			//Misterioso error con el foco y el commitproperties, por eso esto va aca
			callLater(button.setFocus);
		}
		
		private function deleteErrorHandler(event:IOErrorEvent):void
		{
			trace("deleteErrorHandler: ",event.text);
			deleteButton.enabled = true;
		}
		
		protected function upload():void
		{
			if (!url || url == "")
			{
				
				throw new Error("URL must be declared");
			}
		
			var request:URLRequest = new URLRequest();

			request.method = URLRequestMethod.POST;
			request.url = url;
			
			//File upload es la variable en donde el controller recibe el file
			_fileReference.upload(request,"uploadfile");

			button.enabled = false;
		}
		
		override protected function createChildren():void
		{
			super.createChildren();
			
			deleteButton = new Button();
			deleteButton.styleName = "eliminateButton"
			
			
			button = new Button();
			button.styleName = "loadButton"
			
			_progress = new ProgressBar();
			_progress.mode = "manual";
			_progress.focusEnabled = false;
			_progress.labelPlacement = ProgressBarLabelPlacement.CENTER;
			_progress.label = "";
			_progress.percentWidth = 100;
		}
		
		private function populateFileExtension():void
		{
			if(_fileReference.name)
			{
				var chunks: ArrayCollection = new ArrayCollection(_fileReference.name.split("."));
				var extValue: String = chunks.getItemAt(chunks.length - 1).toString();
				var extensionEvent: FileExtensionEvent = new FileExtensionEvent(extValue);
				dispatchEvent(extensionEvent);
			}
			
		}

	}
}