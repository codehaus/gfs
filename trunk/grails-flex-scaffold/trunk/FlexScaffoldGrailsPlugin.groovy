import flex.messaging.MessageBroker
import flex.messaging.services.RemotingService
import flex.messaging.services.remoting.RemotingDestination
import grails.util.GrailsUtil
import org.codehaus.groovy.grails.plugins.flex.FlexUtils
import org.codehaus.groovy.grails.plugins.support.GrailsPluginUtils
import org.cubika.labs.scaffolding.security.GFSMethodSecurityInterceptor
import org.cubika.labs.scaffolding.security.SecurityAnnotationAttributes
import org.springframework.security.intercept.method.MethodDefinitionAttributes


class FlexScaffoldGrailsPlugin {
    // the plugin version
    def version = "0.2.1-beta"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = ['starkSecurity':'0.4.3']
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

	def observe = ["services"]
	def loadAfter = ['services']

    def author = "Ezequiel Martin Apfel"
    def authorEmail = "eapfel@cubika.com"
    def title = "Grails Flex Scaffold (GFS)"
    def description = '''\
        Grails Flex Scaffold (GFS) is a plugin that deals Flex code generation by
        scaffolding methodology, including support for presentation and service (integration with BlazeDS)
        layers by providing embeded data in your domain classes as original Grails Scaffolding does with Ajax and HTML'''

    // URL to the plugin's documentation
    def documentation = "http://docs.codehaus.org/display/GFS/Grails+Flex+Scaffold"

    def doWithSpring = {

        //Config Spring Security
        configureSecurity.delegate = delegate
        configureSecurity()
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
            'display-name'("MessageBrokerServlet")
            'servlet-name'("MessageBrokerServlet")

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

    //Config GFS Security via Spring Security
    def configureSecurity = {

      //TODO: Eliminar el println y poner log

      def config = application.config

      //Declare NameSpace
      //xmlns security:"http://www.springframework.org/schema/security"

      //Declate authentication manager
      //security.'authentication-manager'('alias':"authenticationManager")

      //De donde voy a sacar la seguridad
      //serviceSecureAnnotation(GFSSecurityDBAttributes)

      //
      //serviceSecureAnnotationODS(GFSMethodDefinitionAttribute) {
      //  attributes = serviceSecureAnnotation
      //}

      if (config?.gfs?.security) {

        println "Loading GFS Security"

        serviceSecureAnnotation(SecurityAnnotationAttributes)

        serviceSecureAnnotationODS(MethodDefinitionAttributes) {
            attributes = serviceSecureAnnotation
        }

        //Security Interceptor
        securityInteceptor(GFSMethodSecurityInterceptor) {

          validateConfigAttributes = false
          authenticationManager = ref('authenticationManagerAnonymous')
          accessDecisionManager = ref('accessDecisionManager')
          objectDefinitionSource = serviceSecureAnnotationODS
          throwException = true
        }
      }
      else {

        println "GFS Security disabled"
      }


    }
}
