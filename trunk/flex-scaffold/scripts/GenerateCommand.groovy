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

	depends( validateDomainClass, generateFlexDefaultStructure, generateFlexBuilder, 				createFlexProperties, generateDefaults )

	generateCRUDCommands(args.trim())

}

target(generateCRUDCommands: "Generate CRUD Commands") 
{
	
	def domainClass = getDomainClass(args)

  dftg = new DefaultFlexTemplateGenerator();

	def nameDir = antProp.'command.destdir'+"/${domainClass.propertyName}"
	
	if (!new File(nameDir).exists())
		Ant.mkdir(dir:nameDir)

	def classNameFile = ""
	def templateFile = ""

	classNameFile = "${nameDir}/${domainClass.shortName}CreateCommand.as"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'command.createfile'
	generateCommand(domainClass,templateFile,classNameFile)
	
	classNameFile = "${nameDir}/${domainClass.shortName}DeleteCommand.as"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'command.deletefile'
	generateCommand(domainClass,templateFile,classNameFile)
	
	classNameFile = "${nameDir}/${domainClass.shortName}ListCommand.as"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'command.listfile'
	generateCommand(domainClass,templateFile,classNameFile)
	
	classNameFile = "${nameDir}/${domainClass.shortName}GetPaginationListCommand.as"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'command.paginationlistfile'
	generateCommand(domainClass,templateFile,classNameFile)
	
	classNameFile = "${nameDir}/${domainClass.shortName}SaveOrUpdateCommand.as"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'command.saveorupdatefile'
	generateCommand(domainClass,templateFile,classNameFile)
	
	classNameFile = "${nameDir}/${domainClass.shortName}SelectCommand.as"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'command.selectfile'
	generateCommand(domainClass,templateFile,classNameFile)
}

private void generateCommand(domainClass,templateFile,classNameFile)
{	
	dftg.generateTemplate(domainClass,templateFile,classNameFile)
	println "${classNameFile} Done!"
}
