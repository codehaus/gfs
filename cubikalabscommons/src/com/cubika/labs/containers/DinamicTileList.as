package com.cubika.labs.containers
{
	import com.cubika.labs.interfaces.IDinamicTileListRender;
	
	import flash.display.DisplayObject;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.collections.IViewCursor;
	import mx.collections.ListCollectionView;
	import mx.collections.XMLListCollection;
	import mx.containers.Tile;
	import mx.core.IFactory;
	import mx.events.CollectionEvent;
	import mx.events.CollectionEventKind;
	
	/**
	 * @author Nicolas Rodolfo Enriquez.
	 * @since 12-Mar-2009.
	 * 
	 * This componenten works with two lists, one that provides 
	 * the entities parametricas, and other one that provides the selection 
	 * of the same one. The componete uses of the property equalsProperty to 
	 * communicate the action(share) of selection to the itemRenderer or desseleccion.
	 */ 
	public class DinamicTileList extends Tile
	{


		//------------------------------------------------------------------------------------------------------
	    //  const
	    //------------------------------------------------------------------------------------------------------
		
		//------------------------------------------------------------------------------------------------------
	    //  properties
	    //------------------------------------------------------------------------------------------------------

		/**
		 * inner list proveedora de templates.
		 */ 
		private var _dataProvider:ListCollectionView;
		
		/**
		 * items seleccionados en la lista.
		 */ 
		private var _selectedItems:IList;

		/**
		 * item seleccionados en la lista,
		 * si esta este no funciona los seleccionados.
		 */ 
		private var _selectedItem:Object;
	
		/**
		 *  @private
		 *  Storage for the itemRenderer property.
		 */
		private var _itemRenderer:IFactory;
	
		/**
		 * se le espesifica al compontente 
		 */ 
	    private var _equalsProperty:String = null;
		
		
		
		public function DinamicTileList()
		{
			super();
		}


		//------------------------------------------------------------------------------------------------------
	    //  equalsProperty
	    //------------------------------------------------------------------------------------------------------
	    
	    [Inspectable(category="Data", defaultValue="undefined")]

		public function get equalsProperty():String
		{
			return _equalsProperty;
		}

		public function set equalsProperty(value:String):void
		{
			_equalsProperty = value;
		}
		
	
		//------------------------------------------------------------------------------------------------------
	    //  dataProvider
	    //------------------------------------------------------------------------------------------------------
		
	    [Inspectable(category="Data", defaultValue="undefined")]
	     
	    public function get dataProvider():Object
	    {    
	        return _dataProvider;
	    }
	
	    /**
	     *  @private
	     * 
	     *  Settea el data provider. La idea es guardarlo y agregarle
	     *  un listener del evento CollectionChange, el hadler sera 
	     *  collectionChangeHandler que hace la logica del agregado,
	     *  reseteo o removido de items.  
	     */
	    public function set dataProvider(value:Object):void
	    {   
	    	/*
	    	evita la re-renderizacion si la drt esta dentro de states
	    	que son continuanmente estanciados.
	    	*/
	    	if (value == null || value == _dataProvider ) return;
	    	
	    	//comentario luego
	    	if (!value)
	    	{
	    		value = new ArrayCollection();
	    	}
	    		
 	        var event:CollectionEvent = new CollectionEvent(CollectionEvent.COLLECTION_CHANGE);
	        event.kind = CollectionEventKind.RESET;
	        
	        if (_dataProvider)
	        {
	        	// es una nueva coleccion, saco los escuchas a la vieja para el GC.
	 	        _dataProvider.removeEventListener(CollectionEvent.COLLECTION_CHANGE, dpChangeHandler);
	        }
	
	        if (value is Array)
	        {
	            _dataProvider = new ArrayCollection(value as Array);
	            event.items = value as Array;
	        }
	        else if (value is IList)
	        {
	            _dataProvider = new ListCollectionView(IList(value));
	            event.items = IList(value).toArray();
	        }
		    else if (value is XMLList)
	        {
	            _dataProvider = new XMLListCollection(value as XMLList);
	            event.items = _dataProvider.toArray();
	        }
	        else if (value is XML)
	        {
	            var xl:XMLList = new XMLList();
	            xl += value;
	            _dataProvider = new XMLListCollection(xl);
	           event.items = _dataProvider.toArray();
	        }
	        else
	        {
	            var tmp:Array = [];
	            if (value != null)
	                tmp.push(value);
	            _dataProvider = new ArrayCollection(tmp);
	            event.items = tmp;
	        }

			// agrego escuchas, los saque antes de instanciar el data provider para q el GC pueda
			// limpiarlo si se trata de un nuevo data provider, asi no quedan inalcanzable el objeto.
        	_dataProvider.addEventListener(CollectionEvent.COLLECTION_CHANGE, dpChangeHandler, false, 0, true);
			
			// IMPORTANTE: forzo la carga inicial con los datos iniciales del data provider
			dpChangeHandler(event);
	    }


		//------------------------------------------------------------------------------------------------------
		//  itemRenderer
		//------------------------------------------------------------------------------------------------------
		
		[Inspectable(category="Data", defaultValue="undefined")]
		
		/**
		 *  The custom item renderer for the control.
		 */
		public function get itemRenderer():IFactory
		{
		    return _itemRenderer;
		}
		
		/**
		 *  @private
		 */
		public function set itemRenderer(value:IFactory):void
		{
		    _itemRenderer = value;
		}

		//------------------------------------------------------------------------------------------------------
		//  selectedItems & index
		//------------------------------------------------------------------------------------------------------	
		
		[Inspectable(category="Data", defaultValue="undefined")]
		
		/**
		 *  .
		 */
		public function get selectedItems():Object
		{		
		    return _selectedItems;
		}
		
		/**
		 *  @private
		 */
		public function set selectedItems(value:Object):void
		{
			if (value == null)
			{
				unselectAll();
				return;
			} 
			    	
	    	//comentario luego
	    	if (!value)
	    	{
	    		value = new ArrayCollection();
	    	}
	    		
 	        var event:CollectionEvent = new CollectionEvent(CollectionEvent.COLLECTION_CHANGE);
	        event.kind = CollectionEventKind.RESET;
	        
	        if (_selectedItems)
	        {
	        	// es una nueva coleccion, saco los escuchas a la vieja para el GC.
	 	        _selectedItems.removeEventListener(CollectionEvent.COLLECTION_CHANGE, selectedItemsChangeHandler);
	        }
	
	        if (value is Array)
	        {
	            _selectedItems = new ArrayCollection(value as Array);
	            event.items = value as Array;
	        }
	        else if (value is IList)
	        {
	            _selectedItems = new ListCollectionView(IList(value));
	            event.items = _selectedItems.toArray();
	        }
	        else if (value is XMLList)
	        {
	            _selectedItems = new XMLListCollection(value as XMLList);
	            event.items = _selectedItems.toArray();
	        }
	        else if (value is XML)
	        {
	            var xl:XMLList = new XMLList();
	            xl += value;
	            _selectedItems = new XMLListCollection(xl);
	            event.items = _selectedItems.toArray();
	        }
	        else
	        {
	            var tmp:Array = [];
	            if (value != null)
	                tmp.push(value);
	            _selectedItems = new ArrayCollection(tmp);
	            event.items = tmp;
	        }

			// agrego escuchas, los saque antes de instanciar el data provider para q el GC pueda
			// limpiarlo si se trata de un nuevo data provider, asi no quedan inalcanzable el objeto.
        	_selectedItems.addEventListener(CollectionEvent.COLLECTION_CHANGE, selectedItemsChangeHandler, false, 0, true);
			
			// IMPORTANTE: forzo la carga inicial con los datos iniciales del data provider
			selectedItemsChangeHandler(event)
		}
		
		//------------------------------------------------------------------------------------------------------
		//  selectedItems & index
		//------------------------------------------------------------------------------------------------------	
		
		[Inspectable(category="Data", defaultValue="undefined")]
		
		/**
		 * 
		 */
		public function get selectedItem():Object
		{		
		    return _selectedItem;
		}

		public function set selectedItem(value:Object):void
		{		
		    _selectedItem = value;
		    
		    unselectAll();
		    
		    changeITRState(value, true);
		}
		
		//------------------------------------------------------------------------------------------------------
		//  event handlers.
		//------------------------------------------------------------------------------------------------------	

		/**
		 * 	Importante funcion, escucha los cambios en el data provider, 
		 *  si hay nuevo objeto (event.kind = "add") crea su item renderer 
		 *  lo asocia al objeto y lo agrega. Si el CollectionEvent lleva un 
		 *  kind "remove" es un objeto que fue removido del dataProvider por 
		 *  lo tanto lo elimino de la lista. Si el kind es reset, remuevo 
		 *  todos los renderers y lo recargo con la nueva info.	
		 */ 
		private function dpChangeHandler(event:CollectionEvent):void
		{

			var itemsChangeIterator:IViewCursor; 
			var kind:String = event.kind;
			
			itemsChangeIterator = new ArrayCollection(event.items).createCursor();
			
			switch (kind)
			{
				case CollectionEventKind.ADD: 	
				{	
					do
					{
						addItem(itemsChangeIterator.current);
					} 
					while (itemsChangeIterator.moveNext())
					
					break;
				} 		
				case CollectionEventKind.REMOVE:
				{
					do
					{
						removerItem(itemsChangeIterator.current);
					} 
					while (itemsChangeIterator.moveNext())	
					break;
				}
				case CollectionEventKind.RESET:	
				{
					removeAllChildren();
					
					if (event.items.length > 0)
					{
						do
						{
							addItem(itemsChangeIterator.current);
						} 
						while (itemsChangeIterator.moveNext())						
					}
					break;
				}
			}

		}
		
		private function selectedItemsChangeHandler(event:CollectionEvent):void
		{
			
			var itemsChangeIterator:IViewCursor; 
			var kind:String = event.kind;
			
			itemsChangeIterator = new ArrayCollection(event.items).createCursor();
			
			switch (kind)
			{
				case CollectionEventKind.ADD: 	
				{	
					do
					{
						changeITRState(itemsChangeIterator.current, true);
					} 
					while (itemsChangeIterator.moveNext())
					
					break;
				} 		
				case CollectionEventKind.REMOVE:
				{
					do
					{
						changeITRState(itemsChangeIterator.current, false);
					} 
					while (itemsChangeIterator.moveNext())	
					break;
				}
				case CollectionEventKind.RESET:	
				{
					unselectAll();

					if (event.items.length > 0)
					{
						do
						{
							changeITRState(itemsChangeIterator.current, true);
						} 
						while (itemsChangeIterator.moveNext())						
					}
					break;
				}
				
			}

		}
		
		//------------------------------------------------------------------------------------------------------
		//  add & remove.
		//------------------------------------------------------------------------------------------------------		
		
		/**
		 *  @protected logica para agregar un item.
		 *  Basicamente crea un renderer mediante una factory
		 *  y le pega la data. 
		 */
		protected function addItem(_data:Object):void
		{
	        if (_data == null) return;
	        
	        var item:IDinamicTileListRender = _itemRenderer.newInstance();
           
            item.owner = this;
            item.data = _data;
						
           	addChild(DisplayObject(item));
		}
		
		/**
		 *  @private logica para remover un item.
		 *  Itera el verctor de hijos comparando la data
		 *  del renderer con la el objeto removido del 
		 *  dataProvider, cuando coinciden se elimina el renderer.
		 */
		private function removerItem(_data:Object):void
		{	
			for each (var item:Object in getChildren())
			{
				if (item.data === _data)
				{
					//TODO: Eliminar el error de llamador secundario
					try
					{
						removeChild(item as DisplayObject);
					}
					catch (e:Error)
					{
						trace("fix " + _data);
					}
					break;
				}			
			}
		}
				
		
		//------------------------------------------------------------------------------------------------------
		//  selected & unselected Items.
		//------------------------------------------------------------------------------------------------------	
				
		private function unselectAll():void
		{
			var childs:Array = getChildren();
			
			for each (var itr:IDinamicTileListRender in childs)
			{
				itr.selectedRenderer(false, null);
			}
		}
		
		private function changeITRState(data:Object, selected:Boolean):void
		{
			var childs:Array = getChildren();
			
			for each (var itr:IDinamicTileListRender in childs)
			{
				if (_equalsProperty)
				{
					if (itr.data[_equalsProperty] == data[_equalsProperty])
					{
						itr.selectedRenderer(selected, data);
					}
				}
				else
				{
					if (itr.data === data)
					{
						itr.selectedRenderer(selected, data);
					}					
				}
			}
		}

	}
}
