package com.cubika.labs.interfaces
{
	import flash.events.IEventDispatcher;
	
	import mx.core.IDataRenderer;
	import mx.core.IFlexDisplayObject;
	import mx.core.IUIComponent;
	import mx.managers.ILayoutManagerClient;
	import mx.styles.ISimpleStyleClient;
	
	/**
	 * @author Nicolas Rodolfo Enriquez.
	 * @since 12-Mar-2009. 
	 */ 
	public interface IDinamicTileListRender extends IDataRenderer, IEventDispatcher,
										   			   IFlexDisplayObject,
										   			   ILayoutManagerClient,
										   			   ISimpleStyleClient, IUIComponent
	{
		
		
		
		/**
		 * Interfaz de comunicacion entre la DinamicTileList y sus renderers.
		 * 
		 * @param select - True si el objeto debe ser seleccionado, false en caso contrario.
		 * @param selectedObject - Objeto del dataProvider de la DinamicTileList que machea
		 * 						   con el objeto entoncontrado en la lista de items seleccionados
		 * 						   mediante la equalsProperty o en su defecto por instancia.
		 */ 
		function selectedRenderer(selected:Boolean, selectedObject:Object):void;

		
	}
}