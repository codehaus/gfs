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
			if (!data)
				return
			
			if (!dataProvider)
				return
			
			var itemLabel:Object;
			
			if ((data is String) || (data is Number) || (data is int))
			{
				itemLabel = data
				dataField = "data"
			}
			else if (!data.hasOwnProperty("data"))
			{
				itemLabel = data[labelField]
				dataField = labelField;	
			}
			else
			{
				itemLabel = data.data
				dataField = "data";
			}
			var match:Boolean = false;
			for each (var item:Object in dataProvider)
			{
				if (item)
				{
					if ((item is String) || (item is Number) || (item is int))
					{
						if (item == itemLabel)
						{
							super.selectedItem = item;
							return
						}	
					}
					else if (item[dataField] == itemLabel)
					{
						super.selectedItem = item;
						return
					}
					/* if (!item.hasOwnProperty("data"))
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
					} */
				}
			}
			super.selectedItem = data;
		}
	}
}