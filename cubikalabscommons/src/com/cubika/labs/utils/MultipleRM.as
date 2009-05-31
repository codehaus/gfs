package com.cubika.labs.utils
{
	import flash.events.EventDispatcher;
	
	import mx.resources.IResourceManager;
	import mx.resources.ResourceManager;
	
	[Bindable]
	public class MultipleRM extends EventDispatcher
	{
		private static var resourceManager:IResourceManager = ResourceManager.getInstance();
		
		public static var localePrefix:String;

		
		public static function getString(bundleName:String, resourceName:String,
								parameters:Array = null):String
		{
			var rmr:String = resourceManager.getString(bundleName, resourceName, parameters); 
			
			if(!rmr || rmr == '')
				return resourceManager.getString("messages", resourceName, parameters);
				
			return rmr;
		}
		
		public static function getSuppliedString(bundleName:String, 
												resourceName:String,
												defaultName:String):String
		{
			var rmr:String = resourceManager.getString(bundleName, resourceName); 
			
			if(!rmr || rmr == '')
				return defaultName;
				
			return rmr;
		}
	}
}