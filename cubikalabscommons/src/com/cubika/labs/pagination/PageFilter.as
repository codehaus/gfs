package com.cubika.labs.pagination
{
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;
	
	[Bindable]
	[RemoteClass(alias='org.cubika.labs.scaffolding.pagination.PageFilter')]
	public class PageFilter extends EventDispatcher
	{
		public var currentPage:int = 0;
		public var list:ArrayCollection;
		public var offset:int = 0;
		public var order:String = "";
		public var query:String = "";
		public var sort:String = "";
		public var totalPage:int = 0;
		public var totalChange:Boolean = true;		
		public var max:int = 5;
		
		public function PageFilter(max:int = 5)
		{
			this.max = max;
		}
		
		public function setMax(value:int):void
		{
			if (max != value)
			{
				totalChange = true;
				max = value;
				resetOffset();
				setCurrentPage(offset);
			}
		}
		
		public function nextPage():void
		{
			if (currentPage < totalPage)
			{
				offset += max;
				setCurrentPage(offset);
			}
		}
		
		public function prevPage():void
		{
			if (currentPage < 1)
				throw new Error("No more Page")
				
			offset -= max;
			setCurrentPage(offset);
		}
		
		public function setQuery(value:String):void
		{
			if (query != value)
			{
				query = value;
				totalChange = true;
				resetOffset();
				setCurrentPage(offset);
			}
		}
		
		private function resetOffset():void
		{
			offset = 0;	
		}
		
		public function reset():void
		{
			resetOffset();
			query = "";	
		}
		
		private function setCurrentPage(value:int):void
		{
			if (value == 0)
				currentPage = 1;
			else	
				currentPage = (value/max) + 1;
		}
	}
}