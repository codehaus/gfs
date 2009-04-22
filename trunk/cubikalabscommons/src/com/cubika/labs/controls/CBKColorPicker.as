package com.cubika.labs.controls
{
	import mx.controls.ColorPicker;

	public class CBKColorPicker extends ColorPicker
	{
		public function CBKColorPicker()
		{
			super();
		}
		
		public function set selectedColorHex(value:String):void
		{			
			selectedColor = new uint(value.replace("#","0x"));
		}
		
		public function get selectedColorHex():String
		{
			var str:String = selectedColor.toString(16)
			str = "#00000"+str;
			var e:int = str.length;
			return "#"+str.substring(e-6,e).toUpperCase();
		}
	}
}