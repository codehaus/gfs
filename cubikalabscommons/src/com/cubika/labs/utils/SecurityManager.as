package com.cubika.labs.utils
{
	
	public class SecurityManager
	{
		static private var _instance:SecurityManager;
		 
		private var _user:Object;
		
		/**
		 * Object with key#entity=functionality, value=roles
		 */ 
		public var permission:Object = {};
		
		public function SecurityManager(enforcer:SingletonEnforcer)
		{
		}
		
		static public function get instance():SecurityManager
		{
			if (!_instance)
				_instance = new SecurityManager(new SingletonEnforcer);
				
			return _instance;
		}
		
		public function set user(value:Object):void 
		{
			_user = value;
		} 

		public function authorizedTo(functionality:String):Boolean
		{
			if (!_user)
				return false;
			
			var item2Parse:String = permission[functionality]
			var roles:Array
			
			if (item2Parse)
			 roles = item2Parse.split(",");
			
			for each(var item:Object in roles)
			{
				if(_user.hasRole(item))
					return true;
			}
			
			return false;
		}

	}
}
class SingletonEnforcer{}