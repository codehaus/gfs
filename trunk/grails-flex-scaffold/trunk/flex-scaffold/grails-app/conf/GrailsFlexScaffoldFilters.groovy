import org.codehaus.groovy.grails.commons.GrailsClass;
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

import org.cubika.labs.scaffolding.reporting.ReportingQueryBuilder as RQB;
import org.cubika.labs.scaffolding.utils.FlexScaffoldingUtils as FSU;

/**
 * 
 *
 * @author gclavell
 *
 */
public class GrailsFlexScaffoldFilters{
	
	def  filters = {
		doGfsDjReport(controller:'*jReport', action:'*') {
			before = {

          
				String domainClassName = FSU.capitalize(params['entity'])	
				GrailsClass domainClass = grailsApplication.getArtefactByLogicalPropertyName(DomainClassArtefactHandler.TYPE, params.entity)
				def clazz = grailsApplication.getClassForName(domainClassName)

				domainClass.getPropertyValue('reportable').dataSource = { session,params ->
					RQB.getDataSourceFromParams(clazz,domainClass,domainClassName,params)
				}
				domainClass.getPropertyValue('reportable').columns = params['columns']?.split(',')
				
				List columnTitlesList = params['columnTitles']?.split(',')
				
                if(columnTitlesList)
                {
                    Map columnTitles = new HashMap()
				
                    columnTitlesList.each{
                        def splittedValue = it.split(':')
                        def key = splittedValue[0]
                        def value = splittedValue[1]
                        columnTitles.put(key,value)
                    }
                    domainClass.getPropertyValue('reportable').columnTitles = columnTitles
                }
			}
        }
    }
}
