package com.cubika.labs.controls
{
	import mx.controls.ComboBox;

	public class CBKComboBox extends ComboBox
	{
		public var dataField:String;
		
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
			{
				itemLabel = data
			}
			else if (!data.hasOwnProperty("data"))
			{
				itemLabel = data[dataField]	
			}
			else
			{
				itemLabel = data.data
			}
			var match:Boolean = false;
			for each (var item:Object in dataProvider)
			{
				if (item)
				{
					if (!item.hasOwnProperty("data"))
					{
						match = (item[dataField] == itemLabel)
					}
					else
					{
						match = (item.data == itemLabel)
					}
					
					if (match)
					{
						super.selectedItem = item;
						return
					}
				}
			}
		}
	}
}