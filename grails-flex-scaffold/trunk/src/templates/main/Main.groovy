<?xml version="1.0" encoding="utf-8"?>

<!-- Not remove tags CONTROLLERS CRUDVIEWS NS VIEWINDEX RB RBC because they're used to build main -->

<mx:Application xmlns:mx="http://www.adobe.com/2006/mxml" layout="vertical"
	backgroundColor="#e2e2e2" creationComplete="doInit()"><!--NS-->
	
	<mx:Metadata>
		[ResourceBundle("messages")]
	</mx:Metadata>
	
	<mx:Script>
		<![CDATA[
			
			import mx.core.Container;
			import mx.containers.Box;
			import mx.collections.ArrayCollection;
			
			import event.LocaleEvent;
			import model.ApplicationModelLocator;
			
			import com.cubika.labs.utils.MultipleRM;
			
			[Bindable]
			private var localesCollection:ArrayCollection = new ArrayCollection();
			
			private var asianCharacters:Object = {};
			
			private var backgroundBox:Box;
			
			[Bindable]
			public var appModel:ApplicationModelLocator = ApplicationModelLocator.instance;
			
			private static var FUNCTION_GETLOCALE:String = 
			"document.insertScript = function ()" +
			"{ "+
			 "if (document.getLocale==null)" + 
			 "{ " +
			   "getLocale = function ()" + 
			   "{ "+	
			     "if ( navigator )"+ 
				 "{ "+
			 	  "if ( navigator.language )"+ 
			 	  "{ "+
			  		"return navigator.language; "+
			 	  "} "+
			 	  "else if ( navigator.browserLanguage )"+ 
			 	  "{ "+
			  	   "return navigator.browserLanguage; "+
			 	  "} "+
			 	  "else if ( navigator.systemLanguage )"+ 
			 	  "{ "+
			  	   "return navigator.systemLanguage; "+
			 	  "} "+
			 	  "else if ( navigator.userLanguage )"+ 
			 	  "{ "+
			  	   "return navigator.userLanguage; "+
			 	  "} "+
				 "} "+ 
			    "} " + 
			   "} " + 
			  "}";
		
			private function changeTab():void
			{
				tn.selectedChild.setFocus();
			}
			
			private function changeLocaleHandler(e:Event):void
			{
				new LocaleEvent(cbChangeLocale.selectedItem.data).dispatch();
				
				if(asianCharacters[cbChangeLocale.selectedItem.data])
				{
					this.setStyle("fontFamily", "Arial");
				}
				else
				{
					this.setStyle("fontFamily", "main");
				}
			}
			
			public function set selectedView(value:String):void
			{
				tn.selectedIndex = this[value]
				tn.selectedChild.setFocus();
			}
			
			public function getView(v:String):Object
			{
				var nav:Array = v.split("#");
			
				trace("ahhh")
				
				if (nav.length == 1)
				{
					tn.selectedChild = Container(tn.getChildByName(v));
					return tn.getChildByName(v);	
				}
				else
				{
					tn.selectedChild = Container(tn.getChildByName(nav[0]));
					return Object(tn.selectedChild).getView(nav[1]);
				}
					
				nav.length == 1
				
				/*contenido original
				 * tn.selectedChild = Container(tn.getChildByName(view));
				return tn.getChildByName(view);*/
			}
			
			override protected function createChildren():void
			{
				super.createChildren();
				ExternalInterface.call(FUNCTION_GETLOCALE);
			}
			
			private function doInit():void
			{
				addBg();
			
				var locale:String = ExternalInterface.call("getLocale");
				
				asianCharacters = {messages_ja:'messages_ja', messages_ru:'messages_ru', messages_zh:'messages_zh', messages_th:'messages_th', messages_zh_CN:'messages_zh_CN'};
				
				if (locale)
				{
					locale = locale.split("-")[0]
				
					for each (var item:Object in localesCollection)
					{
						if (item.locale == locale)
						{
							new LocaleEvent(item.data).dispatch();
							cbChangeLocale.selectedItem = item;
							return	
						}
					}
				}
				
				new LocaleEvent("messages").dispatch();
				cbChangeLocale.selectedIndex = 0;
			}
			
			// Only for layout purpose
			private function addBg():void
			{
				backgroundBox = new Box();
				backgroundBox.width = screen.width;
				backgroundBox.height = 100;
				backgroundBox.setStyle("backgroundColor", 0x666666);
				this.rawChildren.addChildAt(backgroundBox, 2);
			}
			
			override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
			{
				super.updateDisplayList(unscaledWidth, unscaledHeight);
				if(backgroundBox)
					backgroundBox.y = screen.height - backgroundBox.height;
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
	<mx:HBox width="100%" styleName="translateComboContainer">
		<mx:ComboBox id="cbChangeLocale"
		dataProvider="{localesCollection}"
		labelField="label"
		change="changeLocaleHandler(event)" valueCommit="changeLocaleHandler(event)"
		/>
	</mx:HBox>
</mx:Application>