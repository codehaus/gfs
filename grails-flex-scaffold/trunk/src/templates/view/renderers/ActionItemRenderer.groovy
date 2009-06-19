<mx:Canvas xmlns:mx="http://www.adobe.com/2006/mxml" horizontalScrollPolicy="off" verticalScrollPolicy="off"
	paddingLeft="2" paddingRight="2">
	
	<mx:Script>
		<![CDATA[
		
			import event.${domainClass.propertyName}.${className}${typeName}Event;
			
			import com.cubika.labs.utils.MultipleRM;
		
			private function clickHandler():void
			{
				new ${className}${typeName}Event(data).dispatch()
			}
		]]>
	</mx:Script>
	
	<mx:Button label="{MultipleRM.getString(MultipleRM.localePrefix,'${domainClass.propertyName}.${typeName.toLowerCase()}')}" click="clickHandler()" height="20"/>
</mx:Canvas>