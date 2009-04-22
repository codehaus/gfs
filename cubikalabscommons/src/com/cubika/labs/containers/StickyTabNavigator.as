package com.cubika.labs.containers
{
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.utils.Timer;
	
	import mx.containers.TabNavigator;
	import mx.controls.Button;
	import mx.core.Container;
	import mx.managers.PopUpManager;

	/**
	 * @author Nicolas Rodolfo Enriquez.
	 * @since 01-Abr-2009.
	 * 
	 * This component adds functionality to the already known Tab Navigator. 
	 * With you side a functional tool tip when the pointer of the mouse 
	 * positions on a particular tab. The tool tip can be positioned in 4 different
	 * places relative to the tab in focus and appear with a predetermined delay.
	 *  
	 */
	public class StickyTabNavigator extends TabNavigator
	{
		
		private var _dataToToolTip:Object = {};
		
		private var _myMousePoint:Point;

		private var _toolTipInstance:Container;
		
		private var _actualTab:DisplayObject;
		
		private var _showToolTip:Boolean;

		private var _timer:Timer;
		
		private var _delayActivate:Boolean;
		
		private var _toolTipPlacement:String;
		
		
		
		public function StickyTabNavigator()
		{
			super();
			
			_timer = new Timer(0);
			
			_myMousePoint = new Point();
			
			_delayActivate = false;
			
			_toolTipPlacement = "rigth-button";
		}
		

		//----------------------------------------------------------------------------		
		// Tool Tip Placent
		//----------------------------------------------------------------------------		

   		[Inspectable(category="General", 
   			enumeration="rigth-top,left-top,rigth-button,left-button", defaultValue="rigth-button")]
		public function set toolTipPlacement(value:String):void
		{	
			_toolTipPlacement = value;		
		}

		public function get toolTipPlacement():String
		{
			return _toolTipPlacement;
		}


		//----------------------------------------------------------------------------		
		// Tool Tip Delay
		//----------------------------------------------------------------------------		

		public function set toolTipDelay(value:Number):void
		{	
			if (value <= 0)
			{
				_delayActivate = false;
			}
			else
			{
				_delayActivate = true;
				_timer.delay = value;		
			}
			
		}

		public function get toolTipDelay():Number
		{
			return _timer.delay;
		}


		//----------------------------------------------------------------------------		
		// Tool Tip Renderer
		//----------------------------------------------------------------------------		

		private var _toolTipRenderer:Class;
		
		public function get toolTipRenderer():Class
		{
			return _toolTipRenderer;
		} 
		
		public function set toolTipRenderer(component:Class):void
		{
			if (_toolTipInstance)
			{
				_toolTipInstance.removeEventListener(MouseEvent.MOUSE_OUT, onMouseToolTipOut);
				PopUpManager.removePopUp(_toolTipInstance);

				createRender();
			}
			
			_toolTipRenderer = component;			
		}
		
		
		//----------------------------------------------------------------------------		
		//	Override Functions
		//----------------------------------------------------------------------------		
				
		override protected function createChildren():void
		{
			super.createChildren();		
									
			tabBar.addEventListener(MouseEvent.MOUSE_OVER, onMouseOver, false, 0, true);
			tabBar.addEventListener(MouseEvent.MOUSE_OUT, onMouseOut, false, 0, true);
			
			_timer.addEventListener(TimerEvent.TIMER, showToolTip, false, 0, true);				
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		
			
			/**
			 * TODO: poner la funcionalidad de mostrar y cerrar en el updateDisplayList!!
			 */ 
			 
			if (_toolTipRenderer == null || _toolTipInstance == null) return;
	
			if (_showToolTip)
			{
				var currentTarget:DisplayObject = getComponentOnMouseHitArea();
							
				if (currentTarget == null) return;
			
		        var showPoint:Point = getShowPointWithPlacement(currentTarget);
								
				_toolTipInstance.data = _dataToToolTip;
	
				_toolTipInstance.move(showPoint.x, showPoint.y);
				
				_toolTipInstance.visible = true;			
			}
			else
			{
				_toolTipInstance.visible = false;			
			} 
		}


		//----------------------------------------------------------------------------		
		// Mouse Event Handlers.
		//----------------------------------------------------------------------------				
		
		private function onMouseOver(event:MouseEvent):void
		{
			if (_toolTipInstance == null)
			{
				createRender();
			}
			
			if (_delayActivate)
			{
				_timer.start();
			}
			else
			{
				showToolTip();
			}
		}

		private function onMouseOut(event:MouseEvent):void
		{		
			var point:Point = new Point(mouseX, mouseY);
						
			point = this.localToGlobal(point);
			
			if (_toolTipInstance && !_toolTipInstance.hitTestPoint(point.x, point.y))
			{
				_showToolTip = false;
			    invalidateDisplayList();
			}
			
			_timer.reset();
		}
		
		private function onMouseToolTipOut(event:MouseEvent):void
		{
			var point:Point = new Point(mouseX, mouseY);
						
			if (_toolTipInstance && !_actualTab.hitTestPoint(point.x, point.y))
			{
				_showToolTip = false;
			    invalidateDisplayList();
			}
		
			_timer.reset();
		}
		
		private function showToolTip(event:TimerEvent = null):void
		{
			_showToolTip = true;
			
		    invalidateDisplayList();
		}	
		
		
		//----------------------------------------------------------------------------		
		// Internal method's.
		//----------------------------------------------------------------------------		
		
		private function getShowPointWithPlacement(target:DisplayObject):Point
		{
			var showPoint:Point = new Point();
			
			showPoint.x = target.x;
			showPoint.y = target.y;
			
			showPoint = this.localToGlobal(showPoint);

			// rigth-top,left-top,rigth-button,left-button
			switch (_toolTipPlacement)
			{
				case 'rigth-top':
				{
		 			showPoint.x += (2*target.width)/3 + Number(super.getStyle('tabOffset'));
					showPoint.y -= _toolTipInstance.height;
					showPoint.y += target.height/2 
					break;
				}
				case 'rigth-button':
				{
		 			showPoint.x += (2*target.width)/3;
					showPoint.y += target.height/2;
					break;
				}
				case 'left-top':
				{
					showPoint.x -= _toolTipInstance.width;
					showPoint.x += (1*target.width)/3;
					showPoint.y -= _toolTipInstance.height;
					showPoint.y += target.height/2 
					break;
				}
				case 'left-button':
				{
					showPoint.x -= _toolTipInstance.width;
					showPoint.x += (1*target.width)/3;
					showPoint.y += target.height/2;
					break;
				}
			}			
			
			return showPoint;
		}
		
		private function createRender():void
		{							
			_toolTipInstance = PopUpManager.createPopUp(this, _toolTipRenderer) as Container;
			_toolTipInstance.visible = false;
			_toolTipInstance.addEventListener(MouseEvent.ROLL_OUT, onMouseToolTipOut, false, 0, true);
		}
		
		public function getComponentOnMouseHitArea():DisplayObject
		{
			var tab:Button; 

			var point:Point = new Point(mouseX, mouseY);
						
			point = this.localToGlobal(point);

			_myMousePoint.x = point.x;
			_myMousePoint.y = point.y;

			for (var i:int = 0; i<numChildren; i++)
			{		
				tab = getTabAt(i);
				
				if (tab.hitTestPoint(point.x, point.y, true))	
				{
					//trace ('lo encontre!!!');
					_actualTab = tab;
					_dataToToolTip.tabIndex = i;
					_dataToToolTip.label = tab.label;
					_dataToToolTip.tab = tab;
					_dataToToolTip.tabChildren = getChildAt(i);

					return tab;
				}
			} 
			
			return null;
		}
	}
}