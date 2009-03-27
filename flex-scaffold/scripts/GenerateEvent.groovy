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
	depends( validateDomainClass, generateFlexDefaultStructure, generateFlexBuilder,	createFlexProperties, generateDefaults )
	generateEvents(args.trim())
}

target(generateEvents: "Generate Event") 
{
	def domainClass = getDomainClass(args)
  
	dftg = new DefaultFlexTemplateGenerator();

	def nameDir = antProp.'event.destdir'+"/${domainClass.propertyName}"
	
	if (!new File(nameDir).exists())
		Ant.mkdir(dir:nameDir)

	def classNameFile = ""
	def templateFile = ""
	
	classNameFile = "${nameDir}/${domainClass.shortName}CRUDEvent.as"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'event.crudfile'
	generateEvent(domainClass,templateFile,classNameFile)

	classNameFile = "${nameDir}/Get${domainClass.shortName}PaginationEvent.as"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'event.paginationfile'
	generateEvent(domainClass,templateFile,classNameFile)	
}

private void generateEvent(domainClass,templateFile,classNameFile)
{	
	dftg.generateTemplate(domainClass,templateFile,classNameFile)
	println "${classNameFile} Done!"
}
