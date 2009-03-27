package com.cubika.labs.date
{
	public class DateUtils
	{
		static private var datesOfMonth:Object = {0:31,1:28,2:31,3:30,4:31,5:30,6:31,7:31,8:30,9:31,10:30,11:31}
		
		static public function getMaxDatesOfMonth(month:Number,year:Number):int
		{
			if (isNaN(month))
				return 31;
			
			if (month == 1)
				if (year && year % 4 == 0)
					return 29;
					
			return datesOfMonth[month]
		} 
	}
}