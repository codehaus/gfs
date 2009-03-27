package com.cubika.labs.controls
{
	import flash.display.Sprite;
	import flash.events.Event;
	
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.events.CollectionEvent;
	import mx.events.CollectionEventKind;
	
	/**
	 * 19/02EPA - Permite cambiar los colores de de las filas
	 * 
	 * @author Ezequiel "pelado" Apfel
	 * 
	 */	
	public class CBKDataGrid extends DataGrid
	{	
		public var variableHeight:Boolean = false;
		public var maxRowCount:int = 8;
		public var variableColor:Boolean = false;
		
		public function CBKDataGrid()
		{
			super();
		}
		
		override protected function drawRowBackground(s:Sprite, rowIndex:int, y:Number, height:Number, color:uint, dataIndex:int):void
		{
			if (!variableColor)
				return
				
			var _color:uint = NaN;
			if (dataProvider && dataProvider.length > 0 && dataProvider.length > dataIndex)
				_color =  dataProvider[dataIndex].color;
			
			super.drawRowBackground(s,rowIndex,y,height,_color == 0 ? color : _color, dataIndex);
		}
		
		override protected function drawColumnBackground(s:Sprite, columnIndex:int, color:uint, column:DataGridColumn):void
		{
			super.drawColumnBackground(s, columnIndex, color, column);
		}
		
		override public function set dataProvider(value:Object):void
		{
			super.dataProvider = value;
			
			if (dataProvider && dataProvider.length > 1 && variableHeight)
			{
				alpha = 1;
				height = (dataProvider.length+1)*24;
			}
			
			if(dataProvider && dataProvider.length < 1 && variableHeight)
			{
				height = 0; 
				alpha = 0;
			}
		}
		
		override protected function collectionChangeHandler(event:Event):void
		{
			super.collectionChangeHandler(event);
			
			if (!variableHeight)
				return
				
			var kind:String = CollectionEvent(event).kind;	
			
			switch(kind)
			{
				case CollectionEventKind.ADD	: if (maxRowCount > dataProvider.length)
												  { 
														height = (dataProvider.length+1)*24;
														alpha = 1;
												  }
													break;
				case CollectionEventKind.REMOVE	: if (height > 22) 
													{
														height -= 23;
														
														if(dataProvider.length == 0)
														{
															alpha = 0;
															height = 0;																			
														}
													}
													break;
				case CollectionEventKind.RESET	: 
												{
													height = (dataProvider.length+1)*23;
													
													if(dataProvider.length > 0)
													alpha = 1;
												}
												break;
			}
		}
	}
}