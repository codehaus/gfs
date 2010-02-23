package com.cubika.labs.event
{
	import flash.events.Event;

	/**
	 * 
	 * @author ndomina
	 * This event will provide the extension name for a selected file
	 * 
	 */
	public class FileExtensionEvent extends Event
	{
		public static const EVENT_TYPE: String = "fileExtensionEvent";
		public var extensionName: String;
		
		public function FileExtensionEvent(extension:String)
		{
			this.extensionName = extension;
			super(EVENT_TYPE);
		}
	}
}