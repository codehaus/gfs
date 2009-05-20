package org.cubika.labs.scaffolding.i18n

////////////////////////////////////////////////////////////////////
// Copyright 2009 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
////////////////////////////////////////////////////////////////////

import groovy.util.ConfigSlurper

import org.cubika.labs.scaffolding.utils.I18nUtils
import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

/**
 * Generates message[_locale].properties of i18n
 * @author Ezequiel Martin Apfel
 * @sing 24-Mar-2009
 */
class I18nBuilder
{
	private final String PROPERTY_SEPARATOR = ":"
	private final String END_LINE_PROPERTIES = ":)" 
	
	//Domain Class
	def domainClass
	//Locale selected
	def locale
	//Default local (set into i18n.properties)
  def defaultLocale
		
	//DefaultMessage - default-message[_locale].properties
	private def defaultMessage
	//Properties' map loaded 
	private Map mapProperties = [:]

	I18nBuilder(defaultLocale)
	{
		this.defaultLocale = defaultLocale
	}

	/**
	 * Changes locale, fills message and properties for a new locale
	 * @param locale - String (en, es, .., etc)
	 */
	void changeLocale(String locale)
	{
		this.locale = locale	
		fillDefaultMessages()
		fillProperties()
	}
	
	/**
	 * Builds properties' map, then It's stored in the .properites file
	 * @param domainClass - a DomainClass
	 */
	void build(domainClass)
	{
		if (!locale)
			throw new Exception("Locale must be setted, Use i18nBuilder.changeLocale(locale) to generated message")
	
		this.domainClass = domainClass
		
		def defaultLocaleMessage = getDefaultLocaleMessage()
		def transalateDomainProperties = transalateDomainProperties()
						
		defaultLocaleMessage.each
		{
			mapProperties.put(it.key,it.value)
		}

		transalateDomainProperties.each
		{
			mapProperties.put(it.key,it.value)
		}
	}
	
	/**
	 * Save properties' map in .properties
	 */
	void store()
	{
		def out = new File(getFilePath())
		def treeMap = new TreeMap(mapProperties)
		def oldPrefix = ""
		
		def writer = out.newWriter("UTF-8")
		
	  treeMap.each {
			//no anda me funciono el split, por eso indexOf
			def prefix = it.key.indexOf(".")
			
			if (prefix > -1)
			{
				prefix = it.key.substring(0,prefix)

				if (oldPrefix == prefix)
				{
					writer << "${it}\n"
				}
				else
				{
					writer << "#############################\n"
					writer << "#$prefix\n"
					writer << "${it}\n"
					oldPrefix = prefix
				}
			}
		}  
		
		writer.flush()
		writer.close()
		
		clear()
	}

	/**
	 * Gets defaultMessage for the locale, if not exists gets the message's file 
	 * for the default locale and translate it via google
	 */
	def getDefaultLocaleMessage()
	{
		def defaultTranslate = ""
	  if (!defaultMessage)
		{
			fillDefaultMessages(false)
			
			
			defaultTranslate = translate(defaultMessage)
			
		}
		
		if (defaultTranslate == "")
		{
			defaultMessage.each {defaultTranslate += it.toString()}
		}
		
		defaultTranslate = unmarshallGoogle(defaultTranslate)				
			
		replaceDomainClassProperty(defaultTranslate)
	}

	/**
	 * Translates DomainClass' properties if isn't a defaultLocale via google
	 * 
	 */
	private def transalateDomainProperties()
	{
		//Armo el mapa para mandar a google, solo con las propiedades que no tiene i18n:[] declarado
		//en la constraint para el locale que esto traduciendo.
		//las que no las pongo en otro array
		def propertiesToTranslate = []
		def propertiesNoTranslate = ""
		def props = FSU.getPropertiesWithoutIdentity(domainClass)
		
		props.each
		{
			def constraint = it.domainClass.getConstrainedProperties()[it.name]
			//If sets locale in static constriants = { aProperties(i18n:[]) }
			def messageProperty = CVU.i18n(it,locale)
			
			if (messageProperty) //valido las constraint
			{
				propertiesNoTranslate += marshallProperty(it,messageProperty)
				if (constraint?.inList)
				{
					constraint.inList.each
					{item ->
						propertiesToTranslate.add("${it.domainClass.propertyName}.${it.name}.${item}$PROPERTY_SEPARATOR${item}$END_LINE_PROPERTIES")
					}
				}
			}
			else
			{
				propertiesToTranslate.add(marshallProperty(it))
				if (constraint?.inList)
				{
					constraint.inList.each
					{item ->
						propertiesToTranslate.add("${it.domainClass.propertyName}.${it.name}.${item}$PROPERTY_SEPARATOR${item}$END_LINE_PROPERTIES")
					}
				}
			}
		}

		def propertiesTranslate = "" 
		if (defaultLocale != locale)
		{
      propertiesTranslate = translate(propertiesToTranslate)
		}
		else
		{
			propertiesToTranslate.each { propertiesTranslate += it.toString()}
		}
		
		propertiesTranslate += propertiesNoTranslate
		
		unmarshallGoogle(propertiesTranslate)
	}

	/**
	 * Fills default message
	 */
	private def fillDefaultMessages(existMessage=true)
	{
			def suffix
			
			if (existMessage)
				suffix = I18nUtils.suffix(locale)
			else
				suffix = I18nUtils.suffix(defaultLocale)
			
      def String filePath = I18nUtils.resolveResourcesPath("default-messages${suffix}")

      if (filePath)
      {
        if (new File(filePath).exists())
				{	
					def props = new org.cubika.labs.scaffolding.utils.UTFProperties()

					props.load(new File(filePath));
	      	defaultMessage = marshallMap(new ConfigSlurper(grails.util.GrailsUtil.getEnvironment()).parse(props).flatten())
				}
 	  	}
      else
      {
				print " Default message doesn't exists for $locale, GFS will use default messages of $defaultLocale in order to translate it "
      }
      
	}

	/**
	 * Fills properties of grails-app/i18n/message[_locale].properties
	 */
	private void fillProperties()
	{
		def filePath = getFilePath()
		
		if (new File(filePath).exists())	
		{
			def props = new org.cubika.labs.scaffolding.utils.UTFProperties()
						
			props.load(new File(filePath));	
			
			mapProperties = new ConfigSlurper(grails.util.GrailsUtil.getEnvironment()).parse(props).flatten()	
		}
		else
		{
			mapProperties = [:]
		}
	}

	/**
	 * Marshalls property that will be sent to google
	 * @param properties - DomainClassProperites
	 * @param message - String representing the value of property
	 */
	private String marshallProperty(property,message)
	{
		def key = "${property.domainClass.propertyName}.${property.name}"
		def value = "${message}"
		
		"11${key}11$PROPERTY_SEPARATOR${value}$END_LINE_PROPERTIES"
	}
	
	/**
	 * Marshalls property that will be sent to google
	 * @param properties - DomainClassProperites
	 */
	private String marshallProperty(property)
	{
		def key = "${property.domainClass.propertyName}.${property.name}"
		def value = "${property.naturalName}"
		
		"11${key}11$PROPERTY_SEPARATOR${value}$END_LINE_PROPERTIES"
	}
	
	/**
	 * Marshalls properties' map
	 * @param map - Map
	 */
	private def marshallMap(map)
	{
		def aux = []
		
		map.each {
    	aux.add("11${it.key}11$PROPERTY_SEPARATOR${it.value}$END_LINE_PROPERTIES")
		}
		
		aux
	}

	/**
	 * Unmarshalls message recived from google translator
	 * @param properites - String of properties translated
	 */
	private def unmarshallGoogle(properties)
	{
	  
	  def unmarshalProperties = [:]
		
		def endLinePattern = ":[)]+"
		def separatorPattern = PROPERTY_SEPARATOR
		
		def rightBracket = ")"
		def leftBracket = "("
		
    properties = properties.replace("）",")")
    properties = properties.replace("：",":")
    properties = properties.replace("：）",":)")
    properties = properties.replace("： ）",":)")
   	properties = properties.replace("：  ）",":)")
		properties = properties.replace("}",")")	
		properties = properties.replace(":}",":)")
		properties = properties.replace(": }",":)")
		properties = properties.replace(": )",":)")

    def iterator = properties.split(endLinePattern)

    iterator.each
    {
    	if (it)
      {
        it = it.replace(leftBracket, "{")
        it = it.replace(rightBracket, "}")
        
        it = it.trim()
					
        def keyValue = it.split(separatorPattern)
        if (keyValue.size() == 2)
            unmarshalProperties.put(keyValue[0].trim().replaceAll("11","").replaceAll(" ",""),keyValue[1].trim())
      }
		}
		
		unmarshalProperties
	}

	/**
	 * Translates via google translator
	 * @param message - String[] will be sent to google to translate
	 */
	private String translate(message)
	{
		String result = ""
		String bufferMessage = ""
		int j = 9
		try
		{
			message.eachWithIndex 
			{it, i ->
			
				if (i <= j)
				{
					bufferMessage += it.toString()
				}
				else
				{
					bufferMessage += it.toString()
					result += I18nUtils.getTranslateWord (bufferMessage, defaultLocale, locale)
					bufferMessage = ""
					j += 9
				}
			}
			if (bufferMessage != "")
			{
				result += I18nUtils.getTranslateWord (bufferMessage, defaultLocale, locale)
			}
		}
		catch(Exception e)
		{
			e.printStackTrace()
			println "Not Google Translator Connection"
		}
		
		result
	}

	/**
	 * Replace tag domainClass of the properites' file
	 * @param message - String 
	 */
  private def replaceDomainClassProperty(message)
  {
		def aux = [:]

        String domainClassToTranslate = domainClass.naturalName
		String domainClassTranslated


        //Start workaround bug GRAILS-4164 open
        String pkg = domainClass.packageName

        if (pkg && pkg != "")
        {
            pkg = pkg.replace('.','')
            pkg = pkg[0].toUpperCase()+pkg[1..-1]+" "
            domainClassToTranslate = domainClassToTranslate.replace(pkg,"")
        }
        domainClassTranslated = domainClassToTranslate
        //End workaround bug GRAILS-4164 open


		if (defaultLocale != locale)
			domainClassTranslated = translate(domainClassToTranslate)
		
		if (domainClassTranslated=="")
			domainClassTranslated = domainClassToTranslate
			
		message.each 
		{
			if (it.key && it.value)	
				aux.put(it.key.replace("domainClass", domainClass.propertyName),it.value.replace("domainClass", domainClassTranslated))//))
		}
	
		aux
  }

	/**
	 * Gets file path for .properties
	 */
	private String getFilePath()
	{
		def filePath
		
		if (locale == "en")	
			filePath = "grails-app/i18n/messages.properties"
		else
			filePath = "grails-app/i18n/messages_${locale}.properties"
			
		filePath
	}
	
	/**
	 * Clears default message
	 */
	private void clear()
	{
		defaultMessage = null
	}
}