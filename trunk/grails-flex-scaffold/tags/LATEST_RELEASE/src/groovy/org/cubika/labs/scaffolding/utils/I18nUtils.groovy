package org.cubika.labs.scaffolding.utils

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


import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClassProperty

import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU
import com.google.api.translate.Language
import com.google.api.translate.Translate
import org.apache.commons.lang.StringUtils

/**
 * I18n Utils
 * @author Ezequiel Martin Apfel
 * @since 24-Mar-2009
 */
class I18nUtils
{
	static def ant = new AntBuilder()
	static def resolver = new PathMatchingResourcePatternResolver()
	
	private static antProp
	
	private static Map languages
    static {
      languages = new HashMap()
      languages.put("ar",Language.ARABIC)
      languages.put("bg",Language.BULGARIAN)
      languages.put("ca",Language.CATALAN)
      languages.put("zh",Language.CHINESE)
      languages.put("zh_CN",Language.CHINESE_SIMPLIFIED)
      languages.put("zh-TW",Language.CHINESE_TRADITIONAL)
      languages.put("hr",Language.CROATIAN)
      languages.put("cs",Language.CZECH)
      languages.put("da",Language.DANISH)
      languages.put("nl",Language.DUTCH)
      languages.put("en",Language.ENGLISH)
      languages.put("tl",Language.FILIPINO)
      languages.put("fi",Language.FINNISH)
      languages.put("fr",Language.FRENCH)
      languages.put("gl",Language.GALACIAN)
      languages.put("de",Language.GERMAN)
      languages.put("el",Language.GREEK)
      languages.put("iw",Language.HEBREW)
      languages.put("hi",Language.HINDI)
      languages.put("hu",Language.HUNGARIAN)
      languages.put("id",Language.INDONESIAN)
      languages.put("it",Language.ITALIAN)
      languages.put("ja",Language.JAPANESE)
      languages.put("ko",Language.KOREAN)
      languages.put("lv",Language.LATVIAN)
      languages.put("lt",Language.LITHUANIAN)
      languages.put("mt",Language.MALTESE)
      languages.put("no",Language.NORWEGIAN)
      languages.put("pl",Language.POLISH)
      languages.put("pt_BR",Language.PORTUGESE)
			languages.put("pt",Language.PORTUGESE)
      languages.put("ro",Language.ROMANIAN)
      languages.put("ru",Language.RUSSIAN)
      languages.put("sr",Language.SERBIAN)
      languages.put("sk",Language.SLOVAK)
      languages.put("sl",Language.SLOVENIAN)
      languages.put("es",Language.SPANISH)
      languages.put("sv",Language.SWEDISH)
      languages.put("th",Language.THAI)
      languages.put("tr",Language.TURKISH)
      languages.put("uk",Language.UKRANIAN)
      languages.put("vi",Language.VIETNAMESE)
    }

	/**
	 * Returns the flex metatags for resource bundles
	 *
	 * @return String with Flex MetaTags format.
	 */
	static def getMetaTags()
	{
		def locales = getTags(getLocalesProperties('locale.default')+", "+getLocalesProperties('locale.locales'))

		def result = ""
		locales.each
		{
			def suffix = suffix(it.trim())
			
			result += "		[ResourceBundle(\"messages$suffix\")]\n"
		}
		result
	}

	/**
	 * Returns AS <code>ArrayCollection()</code> populated
	 * with locales array.
	 *
	 * @return String with locales.
	 */
	static String getLocalesCollection()
	{
		String collection = "private var localesCollection:ArrayCollection = new ArrayCollection("

		def locales = getTags(getLocalesProperties('locale.default')+", "+getLocalesProperties('locale.locales'))

		String array = ""
		
		locales.eachWithIndex 
		{it, i ->
			
			String suffix = suffix(it.trim())
			
			if (i != locales.size()-1)
			{
				array +="{label:'"+ getLanguageValue(it.trim()) +"', locale:'"+it.trim()+"', data:'messages$suffix'}, "
			}
			else
			{
				array +="{label:'"+ getLanguageValue(it.trim()) +"', locale:'"+it.trim()+"', data:'messages$suffix'}"
			}
		}
		
		array = "[$array]"
		collection = "$collection$array);"
		
		collection
	}

	/**
	 * Split String to an Array
	 *
	 * @param String delimitated with a comma
	 * @return String[]
	 */
	private static def getTags(coll)
	{
		return coll.split(",")
	}

	/**
	 * Search for avilables locales in <b>flexScaffold.properties</b> file.
	 * This read <code>locale.locales</code> value.
	 *
	 * @return String with available locales.
	 */
	static def resolveResourcesPath(fileName)
	{
      FSU.resolveResources("/*/src/resources/i18n/${fileName}.properties")
	}

	/**
	 * Esto es cualquiera
	 * TODO - refactoriza el metodo y como levanta el antprop
	 */
	static def getLocalesProperties(path)
	{	
		if (!antProp)
		{
			def fileProperties = "./grails-app/i18n/i18n.properties"//resolveResourcesPath("i18n")//
			ant.property(file:fileProperties)
			antProp = ant.project.properties
		}
		
		antProp[path]
	}

	static def getTranslateWord(word, fromLang, toLang)
	{
		try
		{
			def translatedText = Translate.translate(word, convertStringToLanguage(fromLang), convertStringToLanguage(toLang))
			return translatedText
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}


	static public String suffix(locale)
	{
		(locale == "en") ? "" : "_${locale}"
	}

  static private def convertStringToLanguage(locale)
  {
    languages[locale]
  }
  
  static private def getLanguageValue(locale)
  {
  	def strLang = convertStringToLanguage(locale).toString().toLowerCase()
  	StringUtils.capitalize(strLang.replace("Language.", "").replace("_", " "))
  }
}