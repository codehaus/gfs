package com.cubika.labs.event
{
	import flash.events.Event;

	/**
	 * 
	 * @author Ezequiel Martin Apfel
	 * @since 24-Feb-2009
	 */	
	public class DataProviderEvent extends Event
	{
		public static const DATA_PROVIDER_CHANGE:String = "dataProviderChange";
		
		public function DataProviderEvent()
		{
			super(DATA_PROVIDER_CHANGE);
		}
		
	}
}