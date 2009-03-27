package com.cubika.labs.date
{
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.events.TextEvent;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFieldType;
	import flash.ui.Keyboard;
	
	import mx.containers.Canvas;
	import mx.containers.HBox;
	import mx.controls.Label;
	import mx.controls.Text;
	import mx.core.UITextField;
	import mx.core.mx_internal;
	import mx.events.CalendarLayoutChangeEvent;
	import mx.events.FlexEvent;
	import mx.managers.IFocusManagerComponent;

	[Exclude(name="childIndexChange", kind="event")]
	[Exclude(name="change", kind="event")]

	use namespace mx_internal

	public class CBKDateField extends HBox implements IFocusManagerComponent
	{
		
		[Inspectable(enumeration = "dd/mm/yyyy,mm/dd/yyyy", defaultValue="dd/mm/yyyy")]
		public var formatDate:String = "dd/mm/yyyy";
		
		public var errorMessage:String = "Date is Incorrect";
		
		private var _selectedDate:Date;
		
		private var _date:UITextField;
		private var _month:UITextField;
		private var _year:UITextField;
		private var _selectedStringDate:String;
		
		private var _navDate:Object = {};
		private var _validationMap:Object = {};
		
		private var _invalidDate:Boolean = false;
		
		
		public function CBKDateField()
		{
			super();
//			setStyle("horizontalGap",0);
//			setStyle("backgroundColor","#ffffff");
//			setStyle("borderStyle","inset");
			tabEnabled = true;
		}
		
		[Bindable]
		public function set selectedDate(_value:Date):void
		{
			if (_selectedDate == _value)
				return;
				
			_selectedDate = _value;
			invalidateProperties();
		} 
		
		public function get selectedDate():Date
		{
			return _selectedDate;
		} 
		
		public function get selectedStringDate():String
		{
			return _selectedStringDate
		}
		
		[Bindable]
		public function set selectedStringDate(value:String):void
		{
			_selectedStringDate = value;
			dispatchEvent(new Event(FlexEvent.VALUE_COMMIT));
		}
		
		public function get invalidDate():Boolean
		{
			return _invalidDate;
		}
		
		public function get date():int
		{
			return Number(_date.text);
		}
		
		public function get month():int
		{
			return Number(_month.text);
		}
		
		public function get year():int
		{
			return Number(_year.text);
		}
		
		override protected function createChildren():void
		{
			super.createChildren();
			
			if (formatDate == "dd/mm/yyyy")
				createDDMMYYYYFields();
				
			if (formatDate == "mm/dd/yyyy")
				createMMDDYYYYFields();
			
			_validationMap[_date] = validateDate;
			_validationMap[_month] = validateMonth;
			_validationMap[_year] = validateYear;
			
			addEventListener(FocusEvent.KEY_FOCUS_CHANGE,focusOutValidation);
			addEventListener(FocusEvent.MOUSE_FOCUS_CHANGE,focusOutValidation);
		}
		
		override protected function commitProperties():void
		{
			super.commitProperties();
			
			if (!_selectedDate)
			{
				_selectedStringDate = "";
				
				if (_date && _month && _year)
				{
					blankFields();
					focusOutValidation();
				}
				
				return
			}	
			
			_date.text = (_selectedDate.date) < 10 ? "0"+(_selectedDate.date).toString() : (_selectedDate.date).toString();
			_month.text = (_selectedDate.month+1) < 10 ? "0"+(_selectedDate.month+1).toString() : (_selectedDate.month+1).toString();
			_year.text = _selectedDate.fullYear.toString();
			
			selectedStringDate = _month.text+"/"+_date.text+"/"+_year.text;
			
			focusOutValidation();
			dispatchEvent(new Event(FlexEvent.VALUE_COMMIT));
		}
		
		private function createDDMMYYYYFields():void
		{
			addDate();
			addLabel();
			addMonth();
			addLabel();
			addYear();
			
			_navDate[_date] = {right:_month};
			_navDate[_month] = {left:_date, right:_year};
			_navDate[_year] = {left:_month};
			
			addLabelFormat();
			
			addListeners();
		}
		
		
		private function createMMDDYYYYFields():void
		{
			addMonth();
			addLabel();
			addDate();
			addLabel();
			addYear();
			
			_navDate[_month] = {right:_date};
			_navDate[_date] = {left:_month,right:_year};
			_navDate[_year] = {left:_date};
			
			addLabelFormat();
			
			addListeners();
		}
		
		private function addListeners():void
		{
			_date.addEventListener(KeyboardEvent.KEY_UP,datesNavigationKey,false,0,true);
			_date.addEventListener(TextEvent.TEXT_INPUT,datesNavigationText,false,0,true);
			_month.addEventListener(KeyboardEvent.KEY_UP,datesNavigationKey,false,0,true);
			_month.addEventListener(TextEvent.TEXT_INPUT,datesNavigationText,false,0,true);
			_year.addEventListener(KeyboardEvent.KEY_UP,datesNavigationKey,false,0,true);
			_year.addEventListener(TextEvent.TEXT_INPUT,datesNavigationText,false,0,true);
			
			_date.addEventListener(MouseEvent.MOUSE_DOWN,datesFocus,false,0,true);
			_month.addEventListener(MouseEvent.MOUSE_DOWN,datesFocus,false,0,true);
			_year.addEventListener(MouseEvent.MOUSE_DOWN,datesFocus,false,0,true);
			
			
			//TypeError 1034 - Rompe conversion de IndexChangedEvent no sabemos donde, por lo tanto
			//agarro el change y lo estopeo
			_date.addEventListener(Event.CHANGE,bug1034FlexHandler,false,0,true);
			_month.addEventListener(Event.CHANGE,bug1034FlexHandler,false,0,true);
			_year.addEventListener(Event.CHANGE,bug1034FlexHandler,false,0,true);
		}
		
		private function bug1034FlexHandler(event:Event):void
		{
			event.stopImmediatePropagation();
		}
		
		private function blankFields():void
		{
			if (_date && _month && _year)
			{
				_date.text = "";
				_month.text = "";
				_year.text = "";
			}
		}
		
		private function addDate():void
		{
			_date = new UITextField();
			_date.type =  TextFieldType.INPUT;
			_date.restrict = "0-9";
			_date.selectable = true;
           	_date.maxChars = 2;
           	_date.autoSize = TextFieldAutoSize.LEFT;
           	_date.percentWidth = 100;
			_date.percentHeight = 100;
			
           var box:Canvas = new Canvas();
			
			box.width = 22;
			box.height = 20;
			//box.tabEnabled = true;
			//box.focusEnabled = true;
			box.addChild(_date);
			addChild(box);
			
		}
		
		private function addMonth():void
		{
			_month = new UITextField();
			_month.type =  TextFieldType.INPUT;
			_month.restrict = "0-9"
			_month.selectable = true;
           	_month.maxChars = 2;
           	_month.autoSize = TextFieldAutoSize.LEFT;
           	_month.percentWidth = 100;
			_month.percentHeight = 100;           	
           	
           	var box:Canvas = new Canvas();
			
			box.width = 22;
			box.height = 20;
			//box.tabEnabled = true;
			//box.focusEnabled = true;
			//box.focusManager = focusManager;
			box.addChild(_month);
			addChild(box);
		}
		
		private function addYear():void
		{
			_year = new UITextField();
			_year.type =  TextFieldType.INPUT;
			_year.restrict = "0-9";
			_year.selectable = true;
           	_year.maxChars = 4;
           	_year.mouseEnabled = true;
           	_year.autoSize = TextFieldAutoSize.LEFT;
           	_year.percentWidth = 100;
			_year.percentHeight = 100;

			
            var box:Canvas = new Canvas();
			
			box.width = 32;
			box.height = 20;
			//box.tabEnabled = true;
			//box.focusEnabled = true;
			//box.focusManager = focusManager;
			box.addChild(_year);
			addChild(box);	
		}
			
		private function addLabel():void
		{
			var label:Text = new Text();
			
			label.text = "/"
			label.width = 8;
			
			addChild(label);
		}
		
		private function addLabelFormat():void
		{
			var label:Label = new Label();			
			label.text = "("+formatDate.toLocaleLowerCase()+")"
			addChild(label);
		}
					
		
		private function datesNavigationKey(event:KeyboardEvent):void
		{
			if (Keyboard.LEFT == event.keyCode)
			{	
				if (event.currentTarget.caretIndex == 0)
				{
					var prev:UITextField = _navDate[event.currentTarget].left; 
					
					if (prev)
					{
						event.stopImmediatePropagation();
						prev.setFocus();
						prev.setSelection(0, prev.length)
					}
					else
					{
						event.stopImmediatePropagation();
						event.currentTarget.setFocus();
						event.currentTarget.setSelection(0, event.currentTarget.length);
					}
				}
				else
				{
					event.stopImmediatePropagation();
					event.currentTarget.setFocus();
					event.currentTarget.setSelection(0, event.currentTarget.length);
				}
			}
			
			if (Keyboard.BACKSPACE == event.keyCode)
			{
				if (event.currentTarget.caretIndex == 0 && event.currentTarget.length == 0)
				{
					var prevv:UITextField = _navDate[event.currentTarget].left; 
					
					if (prevv)
					{
						event.stopImmediatePropagation();
						prevv.setFocus();
						prevv.setSelection(0, prevv.length)
					}
				}
				else
					event.currentTarget.setSelection(0, event.currentTarget.length);
			}
			
			if (Keyboard.RIGHT == event.keyCode)	
				if (event.currentTarget.caretIndex == event.currentTarget.maxChars)
				{
					var next:UITextField = _navDate[event.currentTarget].right;
					if (next)
					{
						event.stopImmediatePropagation();
						next.setFocus();
						next.setSelection(0, next.length)
					}
					else
					{
						event.stopImmediatePropagation();
						event.currentTarget.setFocus();
						event.currentTarget.setSelection(0, event.currentTarget.length);
					}
				}
		}
		
		private function datesNavigationText(event:TextEvent):void
		{
			if (event.currentTarget.caretIndex == event.currentTarget.maxChars && 
				event.currentTarget.selectionBeginIndex == event.currentTarget.selectionEndIndex)
			{
				var next:UITextField = _navDate[event.currentTarget].right;
				if (next)
				{
					if(!_validationMap[next].call(this,Number(event.text)))
						next.text = event.text;
					next.setSelection(next.length,next.length)	
					next.setFocus();
				}
			}
			
			var func:Function = _validationMap[event.currentTarget];
			if (func.call(this,Number(event.text)))
			{
			 	event.preventDefault();
			}
			
		}
		
		private function validateDate(num:Number):Boolean
		{
			if (_date.caretIndex == 0)
			{
				if (num > 3 )
				{
					return true;
				}
			}
			
			if (_date.caretIndex == 1)
			{
				if (Number(_date.text+num.toString()) > 31 || Number(_date.text+num.toString()) == 0)
				{
					return true;
				}
			}
			
			if (_date.caretIndex == 2)
			{
				if (num > 3 )
				{
					return true;
				}
			}
			
			return false;
		}
		
		private function validateMonth(num:Number):Boolean
		{
			if (_month.caretIndex == 0)
			{
				if (num != 0 && num != 1 )
				{
					return true;
				}
			}
			
			if (_month.caretIndex == 1)
			{
				if (Number(_month.text+num.toString()) == 0 || Number(_month.text+num.toString()) > 12)
				{
					return true;
				} 
			}

			if (_month.caretIndex == 2)
			{
				if (num != 0 && num != 1 )
				{
					return true;
				}
			}
			
			return false;
		}
			
		private function validateYear(num:Number):Boolean
		{
			if (_year.caretIndex == 0)
			{
				if (num == 0)
				{
					return true;
				}
			}
			
			return false;
		}
		
		private function datesFocus(event:MouseEvent):void
		{
			if (!(event.currentTarget is UITextField))
				return;
				
			event.currentTarget.setSelection(0,event.currentTarget.length);
		}
		
		private function validate(sDate:String,date:Date):Boolean
		{
			if (Number(sDate) != date.date)
			{
				errorString = errorMessage;
				return false;
			}
			else
			{
				errorString = "";
				return true;
			}	
		}
		
		private function focusOutValidation(event:Event=null):void
		{
			var sDate:String = _month.text+"/"+_date.text+"/"+_year.text;
			
			if (_month.text == "" && _date.text == "" && _year.text == "")
			{
				errorString = "";
				selectedStringDate = "";
				mx_internal::invalidatePropertiesFlag = false;
				_invalidDate = true;
				return;
			}
			
			if (_date && _date.text != "")
				if (validate(_date.text,new Date(Date.parse(sDate))))
				{
					selectedDate = new Date(Date.parse(sDate));
					selectedStringDate = sDate;
					_invalidDate = false;
					mx_internal::invalidatePropertiesFlag = true;
					dispatchEvent(new CalendarLayoutChangeEvent("change"));
				}
				else
				{
					mx_internal::invalidatePropertiesFlag = false;
					_invalidDate = true;
				}
		}
		
		override public function setFocus():void
		{
			super.setFocus();
			
			if (formatDate == "dd/mm/yyyy" )
			{
				_date.setSelection(0,_date.length);
				_date.setFocus();
			}
			
			if (formatDate == "mm/dd/yyyy" )
			{
				_month.setSelection(0,_month.length);
				_month.setFocus();
			}
		}
		
		override public function set errorString(value:String):void
		{
			if (value == "")
			{
				this.graphics.clear();
			}
			else
			{
				graphics.clear();
				graphics.beginFill(0xff3f3f,0.8);//f80000);
				graphics.drawRect(-2,-2,this.width+4,this.height+4);
				graphics.endFill();
			}
		}
	}
}