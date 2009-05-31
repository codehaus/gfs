package com.cubika.labs.controls.reporting.filter.impl
{
	import com.cubika.labs.controls.reporting.filter.IBaseFilterControl;
	
	import mx.containers.VBox;
	import mx.controls.Label;
	
	/**
	 * Defines basic functionallity for filter controls
	 * @author Gonzalo Javier Clavell
	 * @since 2-Mar-2009
	 */	
	public class AbstractBaseFilterControl extends VBox implements IBaseFilterControl
	{
		/**
		 * Main label component 
		 */		
		private var _label:Label;
		
		/**
		 * Parameter that is going to be the "key" of key-value
		 * at building time
 		 */		
		protected var _requestParameter:String;
		
		/**
		 * Contructor 
		 */		
		public function AbstractBaseFilterControl()
		{
			super();
			_label = new Label();
			addChild(_label);
		}
		
		/**
		 * Sets main label text
		 * @param lab
		 */				
		public function set mainLabel(lab:String):void
		{
			_label.text = lab;
		}
		
		/**
		 * Sets request parameter
		 * @param param
		 */		
		public function set requestParameter(param:String):void
		{
			_requestParameter = param;
		}
		
		/**
		 * @return String requestParameter 
		 */		
		public function get requestParameter():String
		{
			return _requestParameter;
		}
		
		/**
		 * This method must be implemented by subclasses
		 * the return value is going to be added to DJReport http request 
		 * @return String e.g: "name=Gonzalo&"
		 */		
		public function buildRequest():String
		{
			throw new Error("buildRequest must be implemented");
		}
		
		/**
		 * This method must be implemented by subclasses
		 * Resets control
		 */		
		public function reset():void
		{
			throw new Error("reset must be implemented");
		}
	}
}