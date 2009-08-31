package com.cubika.labs.controls.security
{
	import com.cubika.labs.utils.SecurityManager;
	
	import mx.controls.Button;
	import mx.events.FlexEvent;

	public class CBKSecurityButton extends Button
	{
		private var _key:String;
		
		private var _entity:String;
		
		/**
		 *	Allows enable button from external business logic
		 **/
		private var _inmutable: Boolean = false;
		
		public function CBKSecurityButton()
		{
			super();
			addEventListener(FlexEvent.CREATION_COMPLETE,initComp,false,0, true);
		}
		
		
		
		private function initComp(event: FlexEvent):void
		{
			if (_key && _entity)
			{
				//Include user permissions. This button will be may enabled by external business logic
				this.enabled = SecurityManager.instance.authorizedTo(_key+"#"+_entity);
				_inmutable = false;			
			}
			else
			{
				//Not include user permissions. This button will not be enabled by external business logic
				this.enabled = false;
				_inmutable = true;
			}
		}
		
		override public function set enabled(value:Boolean):void
		{
			//If inmutable is true, allows external setter, otherwise set enabled property in false
		 	if (!_inmutable)
		 		super.enabled = value;
		 	else
		 		super.enabled = false;
		}
		
		public function set key(value:String):void
		{
			_key = value;	
		}
		
		public function set entity(value:String):void
		{
			_entity = value;	
		}
		
	}
}