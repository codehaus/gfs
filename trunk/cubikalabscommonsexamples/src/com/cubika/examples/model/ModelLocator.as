package com.cubika.examples.model
{

  	/**
  	 * @author Nicolas Rodolfo Enriquez.
  	 */ 
	[Bindable] public class ModelLocator
	{
  	
	    static private var modelLocator: ModelLocator = new ModelLocator();
    	
    
    	public var dinamicTileListModel:DinamicTileListModel = new DinamicTileListModel();
    
   		//-----------------------------------------------------------------------------------------
		// singleton method's
		//-----------------------------------------------------------------------------------------
		
		public function ModelLocator()
	    {
	    	if( modelLocator != null )
	      	{
	      		throw new Error("ModelLocator is singletton.");
	      	} 	
	    }
	    
	    static public function getInstance():ModelLocator
	    {
	    	return modelLocator;
	    }
	        
    
   		//-----------------------------------------------------------------------------------------
		// others
		//-----------------------------------------------------------------------------------------
	}
}



