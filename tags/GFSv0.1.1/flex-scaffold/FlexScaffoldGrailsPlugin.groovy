import flex.messaging.MessageBroker
import flex.messaging.services.RemotingService
import flex.messaging.services.remoting.RemotingDestination
import grails.util.GrailsUtil
import org.codehaus.groovy.grails.plugins.flex.FlexUtils
import org.codehaus.groovy.grails.plugins.support.GrailsPluginUtils

class FlexScaffoldGrailsPlugin {
    // the plugin version
    def version = "0.1.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

		def observe = ["services"]

    // TODO Fill in these fields
    def author = "Ezequiel Martin Apfel"
    def authorEmail = "eapfel@cubika.com"
    def title = "Grails Flex Scaffold (GFS)"
    def description = "Provide scaffolding with flex"

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/flex-scaffold"

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def doWithWebDescriptor = { xml ->
      
			def useContextClassLoader = (application.config.flatten().containsKey("flex.use.context.classloader") ? application.config.flex.use.context.classloader : true)	
 			
			// listeners
      def listeners = xml.listener
      listeners[listeners.size() - 1] + {
          listener {
              'listener-class'("flex.messaging.HttpFlexSession")
          }
      }

			// servlets
      def servlets = xml.servlet
      servlets[servlets.size() - 1] + {
				servlet {
            'servlet-name'("MessageBrokerServlet")
            'display-name'("MessageBrokerServlet")
            'servlet-class'("flex.messaging.MessageBrokerServlet")
            'init-param' {
                'param-name'("services.configuration.file")
                'param-value'("/WEB-INF/flex/services-config.xml")
            }
            'init-param' {
                'param-name'("flex.write.path")
                'param-value'("/WEB-INF/flex")
            }
            'init-param' {
                'param-name'("useContextClassLoader")
                'param-value'(useContextClassLoader)
            }
            'load-on-startup'("1")
        }
			}

			// servlet mappings
      def servletMappings = xml.'servlet-mapping'
      servletMappings[servletMappings.size() - 1] + {
          'servlet-mapping' {
              'servlet-name'("MessageBrokerServlet")
              'url-pattern'("/messagebroker/*")
          }
			}
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def onChange = { event ->
        if (event.source) { 
            def serviceClass = application.getServiceClass(event.source?.name)
            if (FlexUtils.hasFlexRemotingConvention(serviceClass)) {
                def messageBroker = MessageBroker.getMessageBroker(null)
                def remotingService = FlexUtils.getGrailsRemotingService(messageBroker)
                if (!remotingService.getDestination(serviceClass.propertyName)) {
                    FlexUtils.createRemotingDestination(messageBroker, serviceClass)
                }
            }
        }
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
