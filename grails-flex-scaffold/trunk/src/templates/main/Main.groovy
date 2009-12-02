<?xml version="1.0" encoding="utf-8"?>
<mx:Application xmlns:mx="http://www.adobe.com/2006/mxml" layout="vertical"
	backgroundColor="#e2e2e2" creationComplete="doInit()">
	
	<mx:Style source="css/styles.css"/>

	<mx:Script>
		<![CDATA[
			import view.LoginView;
			import model.ApplicationModelLocator;
			import mx.managers.PopUpManager;
			import view.PrincipalView;
			import mx.containers.Box;
			
			[Bindable]
			public var appModel:ApplicationModelLocator = ApplicationModelLocator.instance;

            public var principalView:PrincipalView;

			public function doInit(): void
			{
			    //Check if security is enabled (argument compile variable)
			    GFS::security {
			    
                  var url:Object = Application.application.parameters;
                  appModel.popup = PopUpManager.createPopUp(DisplayObject(Application.application),LoginView,true) as LoginView;
                  PopUpManager.centerPopUp(appModel.popup);
                }
			}


			override protected function createChildren():void
			{
				super.createChildren();

				//Check if security is enabled (argument compile variable)
				GFS::security {

                	stack.addChild(new Box());
                }

                principalView = new PrincipalView();
                principalView.percentWidth = 100;
                principalView.percentHeight = 100;

                stack.addChild(principalView);

			}

		]]>
	</mx:Script>

	<mx:Style source="css/styles.css"/>

	<mx:ViewStack id="stack" width="100%" height="100%" selectedIndex="{appModel.currentView}"/>


</mx:Application>