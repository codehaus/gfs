package com.cubika.labs.controls.reporting.filter.impl
{
	import com.cubika.labs.controls.reporting.ReportingConstants;
	
	import flash.display.DisplayObject;
	
	import mx.controls.DateField;
	import mx.events.CalendarLayoutChangeEvent;
	
	/**
	 * Adds to AbstractFromToFilterControl Date's functionallity
	 * @author Gonzalo Javier Clavell
	 * @since 2-Mar-2009
	 */		
	public class DateFilterControl extends AbstractFromToFilterControl
	{
		/**
		 * from-component
		 */		
		private var _fromDate:DateField = new DateField();
		
		/**
		 * to-component
		 */
		private var _toDate:DateField = new DateField();
		
		/**
		 * Contructor 
		 * Resets all components
		 * Sets default string format "DD/MM/YYYY"
		 * Adds change event listeners on from-component and to-component 
		 */
		public function DateFilterControl()
		{
			super();
			reset();
			formatString = "DD/MM/YYYY";
			_fromDate.addEventListener(CalendarLayoutChangeEvent.CHANGE, onFromDateChange);
			_toDate.addEventListener(CalendarLayoutChangeEvent.CHANGE, onToDateChange);
		}
		
		/**
		 * Sets from-component and to-component format 
		 * @param format
		 */		
		public function set formatString(format:String):void
		{
			_fromDate.formatString = format;
			_toDate.formatString = format;
		}
		
		/**
		 * Sets from-component and to-component to initial state 
		 * selectedDate = null
		 * disabledRanges = new Array() 
		 */		
		override public function reset():void
		{
			_fromDate.selectedDate = null;
			_toDate.selectedDate = null;
			_fromDate.disabledRanges = new Array();
			_toDate.disabledRanges = new Array();
		}
		
		/**
		 * 
		 * @return String DJReport http request
		 * If from = null
		 * e.g: birthday = null_-_2-4-1986&
		 * If to = null
		 * e.g: birthday = 2-4-1986_-_null&
		 * Else
		 * e.g: birthday = 2-4-1986_-_2-8-1986&
		 * 
		 * @note "_-_" is getted from ReportingConstants.FROM_TO_REQUEST_SEPARATOR 
		 */		
		override public function buildRequest():String
		{
			var result:String = requestParameter+"=";
			
			if(_fromDate.selectedDate == null && _toDate.selectedDate == null) 
				return "";
			else if(_fromDate.selectedDate == null) 
				result += "null"+ReportingConstants.FROM_TO_REQUEST_SEPARATOR+dateToRequest(_toDate.selectedDate)
			else if(_toDate.selectedDate == null)
				result += dateToRequest(_fromDate.selectedDate)+ReportingConstants.FROM_TO_REQUEST_SEPARATOR+"null"
			else
				result += dateToRequest(_fromDate.selectedDate)+ReportingConstants.FROM_TO_REQUEST_SEPARATOR+dateToRequest(_toDate.selectedDate);	
			
			return result+"&";
		}

		/**
		 * @private 
		 * @param date
		 * @return String like 2-4-1986
		 */		
		private function dateToRequest(date:Date):String
		{
			var result:String = "";
			result += date.date+"-";
			result += date.month+1+"-";
			result += date.fullYear; 
			return result;
		}
		
		/**
		 * @private
 		 * Handles from-component change event
		 * Sets to-component rangeEnd 
		 * @param event Event.CHANGE
		 */		
		private function onFromDateChange(event:CalendarLayoutChangeEvent):void
		{
			_toDate.disabledRanges = new Array({rangeEnd:_fromDate.selectedDate})
		}

		/**
		 * @private
		 * Handles to component change event
		 * Sets from component rangeStart 
		 * @param event Event.CHANGE
		 */		
		private function onToDateChange(event:CalendarLayoutChangeEvent):void
		{
			_fromDate.disabledRanges = new Array({rangeStart:_toDate.selectedDate})
		}

		/**
		 * Implemented abstract method from superclass
		 * @see com.cubika.labs.controls.reporting.filter.impl.AbstractFromToFilterControl
		 * @return DisplayObject (DateField) 
		 */		
		override protected function getFromComponent():DisplayObject
		{
			return _fromDate;
		}
		
		/**
		 * Implemented abstract method from superclass
		 * @see com.cubika.labs.controls.reporting.filter.impl.AbstractFromToFilterControl
		 * @return DisplayObject (DateField) 
		 */		
		override protected function getToComponent():DisplayObject
		{
			return _toDate;
		}
		
		/**
		 * Enables or not from-component and to-component year navigator  
		 * @param boolean
		 */		
		public function set yearNavigationEnabled(boolean:Boolean):void
		{
			_toDate.yearNavigationEnabled = true;
			_fromDate.yearNavigationEnabled = true;	
		}
	}
}