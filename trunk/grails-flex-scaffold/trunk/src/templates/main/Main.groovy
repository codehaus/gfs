<?xml version="1.0" encoding="utf-8"?>
<mx:Application xmlns:mx="http://www.adobe.com/2006/mxml" layout="vertical"
	backgroundColor="#e2e2e2" creationComplete="doInit()" xmlns:view="view.*">
	
	<mx:Style source="css/styles.css"/>

	<mx:Script>
		<![CDATA[
			import view.LoginView;
			import model.ApplicationModelLocator;
			import mx.managers.PopUpManager;

			[Bindable]
			public var appModel:ApplicationModelLocator = ApplicationModelLocator.instance;


			public function doInit(): void {
				var url:Object = Application.application.parameters;
				appModel.popup = PopUpManager.createPopUp(DisplayObject(Application.application),LoginView,true) as LoginView;
				PopUpManager.centerPopUp(appModel.popup);
			}

		]]>
	</mx:Script>

	<mx:Style source="css/styles.css"/>

	<mx:ViewStack id="stack" width="100%" height="100%" selectedIndex="{appModel.currentView}">
		<mx:Box/>
		<view:PrincipalView id="principalView" width="100%" height="100%"/>
	</mx:ViewStack>
	
</mx:Application>