<?xml version="1.0" encoding="utf-8"?>
<mx:Panel xmlns:mx="http://www.adobe.com/2006/mxml" layout="vertical" title="Login" borderThicknessRight="0"
    borderThicknessLeft="0" borderThicknessBottom="0" headerColors="[0x666666,0x666666]" borderAlpha="1"
    titleStyleName="loginTitle" creationComplete="doInit()">

    <mx:Script>
      <![CDATA[
          import mx.events.FlexEvent;
          import mx.validators.Validator;
          import event.gfs.LoginEvent;
          import model.ApplicationModelLocator;
          import mx.managers.PopUpManager;

          public var appModel:ApplicationModelLocator = ApplicationModelLocator.instance;

          public function doInit():void
          {
              //Register all listeners
              btnLogin.addEventListener(MouseEvent.CLICK,login,false,0,true);
              btnCancel.addEventListener(MouseEvent.CLICK,cancel,false,0,true);
              txtUser.addEventListener(FlexEvent.ENTER,login,false,0,true);
              txtPassword.addEventListener(FlexEvent.ENTER,login,false,0,true);
          }

          public function login(event:Event): void
          {
              if (Validator.validateAll(validators).length == 0)
                  new LoginEvent(txtUser.text,txtPassword.text).dispatch();
              else
                  title = "User and Password are required"

          }

          public function cancel(event:Event): void
          {
              txtUser.text = "";
              txtPassword.text = "";
          }

      ]]>
    </mx:Script>

    <mx:Array id="validators">
        <mx:StringValidator required="true" source="{txtUser}"  property="text"/>
        <mx:StringValidator required="true" source="{txtPassword}" property="text"/>
    </mx:Array>

    <mx:Form id="loginForm">
        <mx:FormItem label="User" required="true">
            <mx:TextInput id="txtUser"/>
        </mx:FormItem>
        <mx:FormItem label="Password" required="true">
            <mx:TextInput id="txtPassword" displayAsPassword="true"/>
        </mx:FormItem>
    </mx:Form>

    <mx:ControlBar width="100%" >
        <mx:HBox id="btnContent" width="100%" horizontalAlign="right">
            <mx:Button label="Login" id="btnLogin" styleName="orangeButton" color="#FFFFFF"/>
            <mx:Button label="Cancel" id="btnCancel" styleName="orangeButton" color="#FFFFFF"/>
        </mx:HBox>
    </mx:ControlBar>

</mx:Panel>
