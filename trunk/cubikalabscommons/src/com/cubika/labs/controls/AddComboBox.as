package com.cubika.labs.controls
{
	
	public class AddComboBox extends AddBase
	{
		public function AddComboBox()
		{
			super();
			control = new CBKComboBox();
			control.prompt = "Select..";	
		}
	}
}