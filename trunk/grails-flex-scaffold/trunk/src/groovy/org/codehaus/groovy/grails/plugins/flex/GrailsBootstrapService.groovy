/**
 * Copyright 2007 Marcel Overdijk
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
package org.codehaus.groovy.grails.plugins.flex;

import org.codehaus.groovy.grails.commons.*;
import org.codehaus.groovy.grails.web.util.WebUtils;
import org.cubika.labs.scaffolding.security.GFSLoginManager;
import org.cubika.labs.scaffolding.security.GFSLoginCommand;

import flex.messaging.MessageBroker;
import flex.messaging.security.LoginCommand;
import flex.messaging.config.ConfigMap;
import flex.messaging.config.SecurityConstraint;
import flex.messaging.services.AbstractBootstrapService;

public class GrailsBootstrapService extends AbstractBootstrapService {

    public void initialize(String id, ConfigMap properties) {

        MessageBroker messageBroker = getMessageBroker();

        // add spring factory if it's not yet registered
        FlexUtils.addSpringFactory(messageBroker);

        GrailsApplication application = WebUtils.lookupApplication(messageBroker.getInitServletContext());
        GrailsClass[] grailsClasses = application.getArtefacts(ServiceArtefactHandler.TYPE);

        SecurityConstraint sc = null;

        Boolean allowSecurity = false;//(Boolean) ConfigurationHolder.getConfig().get("gfsSecurity");

        if (allowSecurity) {

            initializeSecurity(messageBroker);

            //TODO: Implements security context into RemoteDestination
            sc = new SecurityConstraint();
            sc.setMethod(SecurityConstraint.CUSTOM_AUTH_METHOD);
        }

        for (int i = 0; i < grailsClasses.length; i++) {

            GrailsServiceClass serviceClass = (GrailsServiceClass) grailsClasses[i];

            if (FlexUtils.hasFlexRemotingConvention(serviceClass)) {

                FlexUtils.createRemotingDestination(messageBroker, serviceClass);
            }
        }
    }

    public void start() {}

    public void stop() {}


    /**
     * Initialize security context
     * @param messageBroker
     */
    private void initializeSecurity(MessageBroker messageBroker)
    {

      GFSLoginManager lm = new GFSLoginManager();

      LoginCommand lc = new GFSLoginCommand();

      lm.setPerClientAuthentication(false);

      lm.setLoginCommand(lc);

      lm.start();

      messageBroker.setLoginManager(lm);
	}


}
