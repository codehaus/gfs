package org.cubika.labs.scaffolding.security;

import java.security.Principal;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import flex.messaging.FlexContext;
import flex.messaging.security.AppServerLoginCommand;
import flex.messaging.security.SecurityException
import org.codehaus.groovy.grails.plugins.starksecurity.StarkConfigurationHolder;

public class GFSLoginCommand extends AppServerLoginCommand {

	private static final Logger log = Logger.getLogger(GFSLoginCommand.class);
	private AuthenticationProvider authenticationProvider;
	//private MethodRumboAuthorizator securityAuthorizator;

	public GFSLoginCommand() {
		super();
	    initAuthenticationManager();
	}

	private void initAuthenticationManager()
	{
	    authenticationProvider = getAuthenticationProvider()

	    if (authenticationProvider == null) {
	    	throw new RuntimeException("AuthenticationManager could not be found.");
	    }

	}

	public Principal doAuthentication(String username, Object credentials) {

		log.debug("Authenticating...");

		Authentication authentication = new UsernamePasswordAuthenticationToken(username, credentials);
		authentication = authenticationProvider.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		log.debug("User authenticated");

		return (Principal) authentication;
	}

	@Override
	public boolean doAuthorization(Principal principal, List roles) throws SecurityException {

		log.debug("Authorizating");

		return true;
	}

	public boolean logout(Principal principal) {
		return true;
	}

    private AuthenticationProvider getAuthenticationProvider() {

      String providerName

      ServletContext servletContext = FlexContext.getServletContext();

	  WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);

      StarkConfigurationHolder.config.starksecurity?.authenticationManagers?.each { authManager ->

        authManager.authenticationProviders?.eachWithIndex { authProvider, i ->
            if (authProvider['userDetailsService']) {
                providerName = "authenticationProvider${authProvider.userDetailsService}"
            } else {
                providerName = "ldapAuthenticationProvider${authManager.name}${i+1}"
            }
        }
      }

      context.getBean(providerName)
    }
}
