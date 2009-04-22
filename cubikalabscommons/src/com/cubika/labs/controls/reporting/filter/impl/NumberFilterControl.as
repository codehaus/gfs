package com.cubika.labs.controls.reporting.filter.impl
{
	import com.cubika.labs.controls.reporting.ReportingConstants;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	
	import mx.controls.NumericStepper;
	
	public class NumberFilterControl extends AbstractFromToFilterControl
	{
		/**
		 * from-component 
		 */		
		private var _fromStepper:NumericStepper = new NumericStepper();
		
		/**
		 * to-component
		 */		
		private var _toStepper:NumericStepper = new NumericStepper(); 
		
		/**
		 * Contructor 
		 * Resets all components
		 * Adds change event listeners on from-component and to-component 
		 */
		public function NumberFilterControl()
		{
			super();
			reset();
			_fromStepper.addEventListener(Event.CHANGE, onFromStepperChange);
			_toStepper.addEventListener(Event.CHANGE, onToStepperChange);
		}
		
		/**
		 * Sets from-component and to-component minimum value
		 * @param min
		 */		
		public function set minValue(min:Number):void
		{
			_fromStepper.minimum = min;
		}

		/**
		 * Sets from-component and to-component maximum value
		 * @param max
		 */
		public function set maxValue(max:Number):void
		{
			_fromStepper.maximum = max;
		}
		
		/**
		 * Not implemented
		 * TODO
		 * @param format
		 * 
		 */		
		public function set formatString(format:String):void
		{
			//_fromStepper.formatString = format;
			//_toStepper.formatString = format;
		}
		/**
		 * Sets default from-component and to-component minimum to 0
		 * Sets default from-component and to-component maximum to 1000000
		 * Sets from-component and to-component value to null
		 */		
		override public function reset():void
		{
			_fromStepper.minimum = 0;
			_fromStepper.maximum = 1000000;
			_toStepper.minimum = 0;
			_toStepper.maximum = 1000000;
			_fromStepper.value = NaN;
			_toStepper.value = NaN;
		}
		
		/**
		 * 
		 * @return String DJReport http request
		 * If from = to
		 * e.g: price = null_-_200&
		 * If to = null
		 * e.g: birthday = 200_-_null&
		 * Else
		 * e.g: birthday = 200_-_400&
		 * 
		 * @note "_-_" is getted from ReportingConstants.FROM_TO_REQUEST_SEPARATOR 
		 */		
		override public function buildRequest():String
		{
			var result:String = requestParameter+"=";
			
			if(!_fromStepper.value && !_toStepper.value) 
				return "";
			if(!_fromStepper.value)
				result += "null"+ReportingConstants.FROM_TO_REQUEST_SEPARATOR+_toStepper.value;	
			else if(!_toStepper.value)
				result += _fromStepper.value+ReportingConstants.FROM_TO_REQUEST_SEPARATOR+"null";	
			else
				result += _fromStepper.value+ReportingConstants.FROM_TO_REQUEST_SEPARATOR+_toStepper.value;	
			return result+"&";
		}
		
		/**
		 * @private
		 * Handles from-component change event
		 * Validates from component value is not greater than to components value
		 * If validation fails, sets both value to the same (recent changed value) 
		 * @param event
		 */		
		private function onFromStepperChange(event:Event):void
		{
			if(_toStepper.value < _fromStepper.value)
				_toStepper.value = _fromStepper.value;
		}

		/**
		 * @private
		 * Handles from-component change event
		 * Sets to component rangeEnd 
		 * Validates from component value is not greater than to components value
		 * If validation fails, sets both value to the same (recent changed value) 
		 * @param event
		 */		
		private function onToStepperChange(event:Event):void
		{
			if(_fromStepper.value > _toStepper.value)
				_fromStepper.value = _toStepper.value;
		}

		/**
		 * Implemented abstract method from superclass
		 * @see com.cubika.labs.controls.reporting.filter.impl.AbstractFromToFilterControl
		 * @return DisplayObject (NumericStepper) 
		 */		
		override protected function getFromComponent():DisplayObject
		{
			return _fromStepper;
		}

		/**
		 * Implemented abstract method from superclass
		 * @see com.cubika.labs.controls.reporting.filter.impl.AbstractFromToFilterControl
		 * @return DisplayObject (NumericStepper) 
		 */		
		override protected function getToComponent():DisplayObject
		{
			return _toStepper;
		}
	}
}