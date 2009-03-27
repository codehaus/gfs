package com.cubika.labs.event
{
	import flash.events.Event;

	public class AddClickEvent extends Event
	{
		public static const ADD_CLICK_EVENT:String = "addClick"; 
		
		public function AddClickEvent()
		{
			super(ADD_CLICK_EVENT);
		}
		
	}
}