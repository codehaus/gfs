package com.cubika.labs.controls.reporting.filter.impl
{
	import com.cubika.labs.controls.reporting.filter.IFromToFilterControl;
	
	import flash.display.DisplayObject;
	
	import mx.containers.HBox;
	import mx.controls.Label;
	import com.cubika.labs.utils.MultipleRM;
	/**
	 * 
	 * @author Gonzalo Javier Clavell
	 * @since 2-Mar-2009
	 */	
	public class AbstractFromToFilterControl extends AbstractBaseFilterControl implements IFromToFilterControl
	{
		/**
		 * From label component
		 */		
		private var _fromLabel:Label = new Label();
		
		/**
		 * To label component
		 */		
		private var _toLabel:Label = new Label();
		
		/**
		 * from-to component container
		 */		
		private var _hBox:HBox = new HBox();
		
		/**
		 * Contructor 
		 * Sets texts for from-to label
		 * Adds from-to labels as from-to container children
		 * Initialize a template method that adds from-to component as children
		 */		
		public function AbstractFromToFilterControl()
		{
			super();
			_fromLabel.text = MultipleRM.getSuppliedString(MultipleRM.localePrefix,"reporting.filter.label.from","from");
			_toLabel.text =	MultipleRM.getSuppliedString(MultipleRM.localePrefix,"reporting.filter.label.to","to");
			_hBox.addChild(_fromLabel);
			_hBox.addChild(getFromComponent());
			_hBox.addChild(_toLabel);
			_hBox.addChild(getToComponent());
			addChild(_hBox);
		}
		
		/**
		 * Abstract method 
		 * @return DisplayObject from component
		 */		
		protected function getFromComponent():DisplayObject
		{
			throw new Error("getFromComponent must be implemented");
		}
		
		/**
		 * Abstract method 
		 * @return DisplayObject to component
		 */
		protected function getToComponent():DisplayObject
		{
			throw new Error("getToComponent must be implemented");
		}
		
		/**
		 * Sets from label text 
		 * @param lab
		 */		
		public function set fromLabel(lab:String):void
		{
			_fromLabel.text = lab;
		}

		/**
		 * Sets to label text 
		 * @param lab
		 */		
		public function set toLabel(lab:String):void
		{
			_toLabel.text = lab;
		}
	}
}