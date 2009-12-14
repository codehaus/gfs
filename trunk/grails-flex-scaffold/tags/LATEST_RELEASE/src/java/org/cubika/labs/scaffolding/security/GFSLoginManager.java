package org.cubika.labs.scaffolding.security;

import flex.messaging.FlexContext;
import flex.messaging.security.LoginManager;

public class GFSLoginManager extends LoginManager 
{

	@Override
	public void login(String arg0, Object arg1) {
		
		if (FlexContext.getUserPrincipal() != null)
			logout();
		
		super.login(arg0, arg1);
	}
	
}
