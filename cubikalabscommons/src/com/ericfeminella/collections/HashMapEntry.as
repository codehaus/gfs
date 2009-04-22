package com.ericfeminella.collections
{
	public class HashMapEntry implements IHashMapEntry
	{
		private var _key:*;
		private var _value:*;
		
		public function HashMapEntry(key:*,value:*)
		{
			
		}

		public function set key(keyObj:*):void
		{
			_key = keyObj;	
		}
		
		public function get key():*
		{
			return _key;
		}
		
		public function set value(valueObj:*):void
		{
			_value = valueObj;
		}
		
		public function get value():*
		{
			return _value;
		}
		
	}
}