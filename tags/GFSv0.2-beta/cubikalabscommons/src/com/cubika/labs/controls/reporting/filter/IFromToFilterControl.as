package com.cubika.labs.controls.reporting.filter 
{
	/**
	 * Defines access for labels (from-to)
	 * @author Gonzalo Javier Clavell
	 * @since 2-Mar-2009
	 */	
	public interface IFromToFilterControl extends IBaseFilterControl
	{
		function set fromLabel(label:String):void
		function set toLabel(label:String):void
	}
}