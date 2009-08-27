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
		 * esta variable se agrega para que el boton no pueda ser habilitado
		 * en caso de que los permisos no lo permitan pero el workflow de Link
		 * si lo haga
		 * */
		public var inmutable: Boolean = false;
		
		public function CBKSecurityButton()
		{
			super();
			addEventListener(FlexEvent.CREATION_COMPLETE,initComp,false,0, true);
		}
		
		
		//en el creation complete  habilitamos el boton segun corresponda
		private function initComp(event: FlexEvent):void
		{
			if (_key && _entity)
			{
				this.enabled = SecurityManager.instance.authorizedTo(_key+"#"+_entity);
				this.inmutable = false;			
			}
			else
			{
				
				this.inmutable = true;//significa que el workflow de link no podra cambiar este btn
				this.enabled = false;
			}
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