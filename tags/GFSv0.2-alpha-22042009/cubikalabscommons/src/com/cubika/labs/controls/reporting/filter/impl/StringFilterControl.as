package com.cubika.labs.controls.reporting.filter.impl
{
	import mx.controls.TextInput;

	/**
	 * Adds to AbstractFromToFilterControl String's functionallity
	 * @author Gonzalo Javier Clavell
	 * @since 2-Mar-2009
	 */		
	public class StringFilterControl extends AbstractBaseFilterControl
	{
		/**
		 * string-component 
		 */		
		private var _textInput:TextInput;
		
		/**
		 * string-component width 
		 */		
		private var _textInputWidth:Number;
		
		/**
		 * Contructor 
		 * Initialize string-component and adds it as a child
		 */		
		public function StringFilterControl()
		{
			super();
			_textInput = new TextInput();
			addChild(_textInput);
		}

		/**
		 * @return String DJReport http request
		 * If string-component = "" --> "" 
		 * else --> e.g: "name=Gonzalo"
		 */		
		override public function buildRequest():String
		{
			var result:String = ""; 
			if(_textInput.text != "")
				result = requestParameter+"="+_textInput.text+"&";
			return result;
		}
		
		/**
		 * Resets text input value ("")
		 */ 
		override public function reset():void
		{
			_textInput.text = "";
		}
		
		//getters and setters
		/**
		 * @return TextInput  
		 */		
		public function get textInput():TextInput
		{
			return _textInput;
		}
		
		/**
		 * Sets textInputWidth, and string-component width
		 */		
		public function set textInputWidth(value:Number):void
		{
			_textInputWidth = value;
			_textInput.width = _textInputWidth;
		}
	}
}