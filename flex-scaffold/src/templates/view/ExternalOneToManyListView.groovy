<?xml version="1.0" encoding="utf-8"?>
<mx:VBox xmlns:mx="http://www.adobe.com/2006/mxml"
xmlns:cubikalabs="http://cubikalabs.cub2k.com/2009/commons"
paddingTop="5">
	
<mx:Script>
<![CDATA[
        import view.${domainClass.propertyName}.external.${className}PopSelect;
        import event.AlternativeNavigationEvent;
        import event.${domainClass.propertyName}.${className}CRUDEvent;
        import view.${domainClass.propertyName}.${className}EditView;

        import mx.events.CloseEvent;
        import mx.containers.TitleWindow;
        import mx.collections.ArrayCollection;
        import mx.formatters.DateFormatter;
        import com.cubika.labs.utils.MultipleRM;
			
        import mx.core.Application;
        import mx.core.IFlexDisplayObject
			
        import mx.managers.PopUpManager;
			
        import vo.${domainClass.propertyName}.${className}VO;
			
        [Bindable]
        public var dataProvider:ArrayCollection;
						
        private var editView:IFlexDisplayObject;

        private var pop:IFlexDisplayObject;
			
        private function showEditView():void
        {
            create${className}EditView("edit");

            new ${className}CRUDEvent(${className}CRUDEvent.SELECT_EVENT,${className}VO(dg.selectedItem),pop.name).dispatch();

            PopUpManager.centerPopUp(pop);
        }


        private function closePop(event:CloseEvent):void
        {
            PopUpManager.removePopUp(pop);
        }

        private function showNewView():void
        {
            editView =  PopUpManager.createPopUp(DisplayObject(Application.application),${className}PopSelect,true)
				
            Object(editView).clickOk = addOk;
            Object(editView).cancel = cancel;
            Object(editView).clickNew = newOk;
            Object(editView).allowMultipleSelection = true;

            PopUpManager.centerPopUp(editView);
        }
			
        private function addOk():void
        {
            var selecteds:ArrayCollection = ArrayCollection(Object(editView).selectedItems);
            var aux:Array = [], some:Boolean;
				
            if (dataProvider)
            aux = dataProvider.toArray();

            for each (var item:${className}VO in selecteds)
            {
                some = false;
                for each (var it:${className}VO in aux)
                {
                    if (item.id == it.id)
                    {
                        some = true
                        break;
                    }
                }
					
                if (!some)
                {
                    aux.push(item);
                }
            }


            dataProvider = new ArrayCollection(aux);
            PopUpManager.removePopUp(editView);
        }
			
        private function newOk():void
        {
            create${className}EditView("create");
            
            new ${className}CRUDEvent(${className}CRUDEvent.CREATE_EVENT,null,pop.name).dispatch();

            PopUpManager.centerPopUp(pop);
        }

        private function create${className}EditView(mode:String):void
        {
            var edit:${className}EditView = new ${className}EditView();

            pop = PopUpManager.createPopUp(DisplayObject(Application.application),TitleWindow,true);

            TitleWindow(pop).showCloseButton = true;
            TitleWindow(pop).title = MultipleRM.getString(MultipleRM.localePrefix,'${domainClass.propertyName}.'+mode);
            TitleWindow(pop).addEventListener(CloseEvent.CLOSE,closePop,false,0,true);
            Object(pop).addChild(edit);
            pop.name = Object(pop).toString();
        }

        private function cancel():void
        {
            PopUpManager.removePopUp(editView);
        }
			
        private function deleteItem():void
        {
            dataProvider.removeItemAt(dg.selectedIndex);
        }
			
        public function get selectedItems():ArrayCollection
        {
            return dataProvider;
        }

				
    ]]>
</mx:Script>
	
<mx:HBox width="100%"  verticalAlign="middle">
<mx:Button toolTip="{MultipleRM.getString(MultipleRM.localePrefix,'generic.create')}" click="showNewView()" styleName="createButton"/>
<mx:Button toolTip="{MultipleRM.getString(MultipleRM.localePrefix,'generic.delete')}" enabled="{dg.selectedItem}" click="deleteItem()" styleName="deleteButton"/>
<mx:Label text="{MultipleRM.getString(MultipleRM.localePrefix,'generic.doubleClickEdit')}." styleName="commentsText" visible="{dg.height>38}"/>
</mx:HBox>
		
<cubikalabs:CBKDataGrid dataProvider="{dataProvider}" width="100%" height="100%" id="dg" itemDoubleClick="showEditView()" 
doubleClickEnabled="true" variableHeight="true" rowCount="8">
<cubikalabs:columns>
<%			import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

def props = FSU.getPropertiesWithoutIdentity(domainClass,true)

props.each 
{
def gridcolumn = FSU.getDataGridColumn(it)
if (gridcolumn)
print "				$gridcolumn"
}
%>		</cubikalabs:columns>
</cubikalabs:CBKDataGrid>
	
	
</mx:VBox>