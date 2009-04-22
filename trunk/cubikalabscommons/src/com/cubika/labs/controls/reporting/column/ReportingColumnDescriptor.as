package com.cubika.labs.controls.reporting.column
{	
	/**
	 * ReportingColumnControl use this object as ItemRenderer
	 * Deals with marshalling with grails domain class properties needed in reporting view
	 * @author Gonzalo Javier Clavell
	 * 
	 */	
	public class ReportingColumnDescriptor
	{
		//Grails Domain Class Property Marshall 
		
		
		/**	Property name e.g: company */				
		public var name:String
		/**	Property name e.g: Company */
		public var naturalName:String;
		/**	Property labeled as company.name */				
		public var parameterName:String;
		/** Class name property */
		public var className:String;
		
		//View Bindings
		/** Text setted by user */
		[Bindable]public var title:String;
		/** True if column is going to be part of Report*/
		[Bindable]public var selected:Boolean = true;
		
		/**
		 * Contructor
		 */ 
		public function ReportingColumnDescriptor(className:String, name:String,naturalName:String,parameterName:String)
		{
			this.name = name;
			this.parameterName = parameterName;
			this.naturalName = naturalName;
			this.className = className;
		}
		
		/**
		 * @return "" if not selected
		 * else
		 * "columnName," 
		 */		
		public function getColumn():String
		{
			var result:String = "";
			if(!selected) return result;
			if(parameterName != null && parameterName != "") 
				result = parameterName;
			else
				result = name;	
			return result+",";
		}

		/**
		 * @return "" if not selected
		 * else
		 * "column:columnTitle," 
		 */		
		public function getColumnTitle():String
		{
			var result:String = "";
			if(!selected) return result;
			result += getColumn();
			result = result.substr(0,result.length-1)+":";
			if(title == null || title == "") 
				result += naturalName;
			else
				result += title;
			
			return result+",";
		}
		
		/**
		 * Sets title to "" 
		 * and selected to true
		 */		
		public function reset():void
		{
			title = "";
			selected = true;
		}
	}
}