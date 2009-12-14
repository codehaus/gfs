/**
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package model.${domainClass.propertyName}
{
	import mx.collections.ArrayCollection;
	
	import vo.${domainClass.propertyName}.${className}VO;
	
	import com.cubika.labs.pagination.PageFilter;
	
	/**
	 * @author Ezequiel Martin Apfel
	 * @since 23-Feb-2009
	 */
	[Bindable]
	public class ${className}Model 
	{
		public const LIST_VIEW:int = 0;
		public const EDIT_VIEW:int = 1;
		public const EDIT:String = "editView";
		public const CREATE:String = "createView";
	
		public var editView:Boolean = false;
		public var callFromPop:String;

		public var page:PageFilter = new PageFilter();
		public var externalpage:PageFilter = new PageFilter();
		public var selected:${className}VO;
		public var selectedIndexView:int = LIST_VIEW;
		public var listNoPaged:ArrayCollection;

		public function get list():ArrayCollection
		{
			return page.list;
		}
		
		public function set list(value:ArrayCollection):void
		{
			page.list = value;
		}
		
		public function get externallist():ArrayCollection
		{
			return externalpage.list;
		}
		
		public function set externallist(value:ArrayCollection):void
		{
			externalpage.list = value;
		}
		
		public function updateList(vo:${className}VO):void
		{
			var i:int=0;
			
			if (!list)
				list = new ArrayCollection();
			
			for each (var item:${className}VO in list)
			{
				if (item.id == vo.id)
				{
					list.setItemAt(vo,i);
					return
				}
				i++;
			}
			
			list.addItem(vo);

            updateExternalList(vo);
			updateListNoPaged(vo);
		}

        public function updateExternalList(vo:${className}VO):void
		{
			var i:int=0;

			if (!externallist)
				externallist = new ArrayCollection();

			for each (var item:${className}VO in externallist)
			{
				if (item.id == vo.id)
				{
					externallist.setItemAt(vo,i);
					return
				}
				i++;
			}

			externallist.addItem(vo);
		}


		private function updateListNoPaged(vo:${className}VO):void
		{
			var i:int=0;
			
			if (!listNoPaged)
				listNoPaged = new ArrayCollection();
			
			for each (var item:${className}VO in listNoPaged)
			{
				if (item.id == vo.id)
				{
					listNoPaged.setItemAt(vo,i);
					return
				}
				i++;
			}
			listNoPaged.addItem(vo);
		}
		
		
		public function removeItems(vos:Array):void
		{
			var i:int;
			
			for each (var itemDeleted:${className}VO in vos)
			{
				i = 0;
				for each (var item:${className}VO in list)
				{
					if (item.id == itemDeleted.id)
					{
						list.removeItemAt(i);
						break;
					}
					i++;
				}
			}
			removeItem4ListNoPaged(vos);
		}
		
		private function removeItem4ListNoPaged(vos:Array):void
		{
			var i:int;
			
			for each (var itemDeleted:${className}VO in vos)
			{
				i = 0;
				for each (var item:${className}VO in listNoPaged)
				{
					if (item.id == itemDeleted.id)
					{
						listNoPaged.removeItemAt(i);
						break;
					}
					i++;
				}
			}
		}
		
		public function get state():String
		{
			return editView ? EDIT : CREATE
		}
	}
}