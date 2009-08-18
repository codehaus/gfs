package com.cubika.labs.controls.security
{
	import com.cubika.labs.utils.SecurityManager;
	
	import mx.controls.Button;

	public class CBKSecurityButton extends Button
	{
		private var _key:String;
		
		private var _entity:String;
		
		public function CBKSecurityButton()
		{
			super();
		}
		
		
		public function set key(value:String):void
		{
			_key = value;	
		}
		
		public function set entity(value:String):void
		{
			_entity = value;	
		}
		
		override public function get enabled():Boolean
		{
			if (_key && _entity)
				return SecurityManager.instance.authorizedTo(_key+"#"+_entity);
			else
				return super.enabled;
		}
	}
}