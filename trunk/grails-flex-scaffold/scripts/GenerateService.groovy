import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import org.cubika.labs.scaffolding.generator.DefaultFlexTemplateGenerator

grailsHome = Ant.project.properties."environment.GRAILS_HOME"

Ant.property(file:"${flexScaffoldPluginDir}/scripts/flexScaffold.properties")

def antProp = Ant.project.properties

//Private scripts
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_CreateFlexProperties.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateDefaults.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateEclipse.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateStructure.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_ValidateDomainClass.groovy" )

target('default': "") 
{
	depends(validateDomainClass, generateFlexDefaultStructure, generateFlexBuilder, createFlexProperties, generateDefaults )
	generateService(domainClass:getDomainClass(args))
}

//Generate Domain Delegate
generateService =
{ Map args = [:] ->
	
	def domainClass = args["domainClass"]
  
	dftg = new DefaultFlexTemplateGenerator();
	
	def classNameFile = ""
	def templateFile = ""
	
	classNameFile = "./grails-app/services/${domainClass.shortName}Service.groovy"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'service.file'
	
	if (new File(classNameFile).exists())
		return
	
	dftg.generateTemplate(domainClass,templateFile,classNameFile)
	println "${classNameFile} Done!"
}
