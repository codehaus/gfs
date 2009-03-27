package com.cubika.labs.controls
{
	import mx.controls.ComboBox;

	public class CBKComboBox extends ComboBox
	{
		public function CBKComboBox()
		{
			super();
		}
		
		override public function set selectedItem(data:Object):void
		{
			super.selectedItem = data;
			
			if (!data)
				return
			
			if (!dataProvider)
				return
			
			var itemLabel:Object;
			
			if ((data is String) || (data is Number) || (data is int))
				itemLabel = data
			else
				itemLabel = data[labelField]
			
				for each (var item:Object in dataProvider)
				{
					if (item)
						if (item[labelField] == itemLabel)
						{
							super.selectedItem = item;
							return
						}
				}
		}
	}
}