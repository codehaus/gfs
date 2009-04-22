package com.cubika.labs.controls.reporting.filter.impl
{
	import com.cubika.labs.controls.reporting.ReportingConstants;
	
	/**
	 * Overrides StringFilterControl functionality 
	 * in order to builds many-to-one DJReport http request like
	 * e.g: company~name=cubika
	 * @author Gonzalo Javier Clavell
	 * @since 2-Mar-2009
	 */
	public class ManyToOneFilterControl extends StringFilterControl
	{
		public function ManyToOneFilterControl()
		{
			super();
		}
		
		/**
		 * Builds many-to-one DJReport http request
		 * @return String 	empty if text input text == "" 
		 * 					if not e.g: company~name=cubika 
		 * 
		 * @note "~" is from ReportingConstants.RELATIONAL_REQUEST_DOT_REPLACE 
		 */		
		override public function buildRequest():String
		{
			var result:String = ""; 
			if(textInput.text == "")return result;
			
			var splittedParam:Array = requestParameter.split("."); 
			result = splittedParam[0]+ReportingConstants.RELATIONAL_REQUEST_DOT_REPLACE+splittedParam[1]+"="+textInput.text;
			return result+"&";			
		}
	}
}