package com.cubika.labs.controls
{
	import com.adobe.flex.extras.controls.AutoComplete;
	
	/**
	 * 
	 * @author Ezequiel
	 * @since 25-Feb-2009 
	 */	
	public class AddAutoComplete extends AddBase
	{
		public function AddAutoComplete()
		{
			super();
			control = new AutoComplete();
		}
		
	}
}