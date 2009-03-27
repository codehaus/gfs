<?xml version="1.0" encoding="utf-8"?>

<!-- Not remove tags CONTROLLERS CRUDVIEWS NS VIEWINDEX because they're used to build main -->

<mx:Application xmlns:mx="http://www.adobe.com/2006/mxml" layout="vertical"
	backgroundColor="#e2e2e2"><!--NS-->
	
	<mx:Script>
		<![CDATA[
			
			import mx.core.Container;
		
			private function changeTab():void
			{
				tn.selectedChild.setFocus();
			}
			
			public function set selectedView(value:String):void
			{
				tn.selectedIndex = this[value]
				tn.selectedChild.setFocus();
			}
			
			public function getView(view:String):Object
			{
				tn.selectedChild = Container(tn.getChildByName(view));
				return tn.getChildByName(view);
			}
			
		]]>
	</mx:Script>
	
	<mx:Style source="css/styles.css"/>
		
	<!-- HEADER -->
	<mx:Canvas id="headerContainer" width="100%"
		clipContent="false"
		height="40">
		<mx:Image id="imgLogo" 
			y="14"
			x="10"
			source="assets/logo.png"/>
	
		<mx:Canvas id="initContainer"
			x="{headerContainer.width-initContainer.width-10}"
			y="5">
			<mx:Label id="lblGfs" text="GFS" styleName="gfsTitle"/>
			<mx:Label y="2" x="2" text="GFS" alpha="0.5" styleName="gfsTitle"/>
			<mx:Label id="lblSub" x="{lblGfs.width + 2}"
				y="9"
				text="grails-flex-scaffold"
				styleName="gfsSubTitle"/>
		</mx:Canvas>
	</mx:Canvas>
		
	<mx:TabNavigator id="tn" width="100%" height="100%" paddingTop="5" change="changeTab()">
		<!--CRUDVIEWS-->
	</mx:TabNavigator>
	
</mx:Application>