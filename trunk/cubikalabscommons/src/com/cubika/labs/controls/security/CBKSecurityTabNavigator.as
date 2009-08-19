package com.cubika.labs.controls.security
{
	import com.cubika.labs.utils.SecurityManager;
	
	import flash.display.DisplayObject;
	
	import mx.collections.ArrayCollection;
	import mx.containers.TabNavigator;
	import mx.core.ContainerCreationPolicy;

	public class CBKSecurityTabNavigator extends TabNavigator
	{
		private var _dataProvider:ArrayCollection;
		
		public function CBKSecurityTabNavigator()
		{
			super();
			//addEventListener(FlexEvent.CREATION_COMPLETE,doInit,false,0,true);
			creationPolicy = ContainerCreationPolicy.QUEUED;
			
		}
		
		public function set dataProvider(value:ArrayCollection):void
		{
			if (!value)
				return;
			
			_dataProvider = value;
				
			var obj:Object;
			
			for each (var item:Object in _dataProvider)
			{
				if (SecurityManager.instance.authorizedTo(item.key+"#"+item.entity))
				{
					obj = new item.clazz();
					obj.name = item.name;
					obj.label = item.label;
					obj.percentHeight = item.percentHeight;
				
					addChild(obj as DisplayObject);
				}
			}
		}
		
	}
}