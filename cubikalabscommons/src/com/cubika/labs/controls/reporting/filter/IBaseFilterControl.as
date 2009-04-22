package com.cubika.labs.controls.reporting.filter
{
	/**
	 * Defines access for data needed in a filter control
	 * @author Gonzalo Javier Clavell
	 * @since 2-Mar-2009
	 */	
	public interface IBaseFilterControl
	{
		function set requestParameter(param:String):void
		function set mainLabel(label:String):void
		function buildRequest():String
		function reset():void
	}
}