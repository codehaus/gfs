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
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.media.Camera;
	import flash.media.Video;
	import flash.utils.ByteArray;
	
	import mx.containers.Box;
	import mx.containers.Canvas;
	import mx.controls.Label;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.graphics.codec.JPEGEncoder;

	/**
 	* Takes snapshots  
 	* @author Ezequiel Martin Apfel
 	* @since 28-Apr-2009
 	*/
 	[Event(type="snapshotEvent")]
	public class CBKCameraSnapshot extends Box
	{
		static public var SNAPSHOT_EVENT:String = "snapshotEvent"
		
		public var image:ByteArray;
		
		private var _video:Video;
		
		private var _videoContent:UIComponent;
		
		private var _snapshotContent:Canvas;
		
		public function CBKCameraSnapshot()
		{
			super();
			direction = "horizontal";
			addEventListener(FlexEvent.CREATION_COMPLETE,doInit,false,0,true);
		}
		
		public function dispose():void
		{
			_video.clear();
		}
		
		private function doInit(event:FlexEvent):void
		{
			_videoContent.addEventListener(MouseEvent.CLICK,snapshotHandler,false,0,true);
		}
		
		private function createCamera():void
		{
			var camera:Camera = Camera.getCamera();
			
			_video = new Video(camera.width,camera.height);
			_video.attachCamera(camera);
		}
		
		private function snapshotHandler(event:MouseEvent):void
		{
			_snapshotContent.removeAllChildren();
			
			var snapshotHolder:UIComponent = new UIComponent();
			var snapshot:BitmapData = new BitmapData(_video.width, _video.height, true);
			var snapshotbitmap:Bitmap = new Bitmap(snapshot);
			
			snapshotbitmap.smoothing = true;
			
			snapshotHolder.addChild(snapshotbitmap);
			_snapshotContent.addChild(snapshotHolder);
			
			snapshot.draw(_video);
			
			createImage(snapshot);	
		}
		
		private function createImage(snapshot:BitmapData):void
		{
			image = new JPEGEncoder(100).encode(snapshot);
			
			dispatchEvent(new Event("snapshotEvent"));
		}
		
		override protected function createChildren():void
		{
			super.createChildren();
			
			 _videoContent = new UIComponent;
			_snapshotContent = new Canvas();
			
			createCamera();
			
			_videoContent.width = _video.width+1;
			_videoContent.height = _video.height+1;
			_videoContent.useHandCursor = true;
			_videoContent.buttonMode = true;
						
			_snapshotContent.width = _video.width;
			_snapshotContent.height = _video.height;
			
			_videoContent.addChild(_video);
			
			addChild(_videoContent);
			addChild(_snapshotContent);
		}
		
	}
}