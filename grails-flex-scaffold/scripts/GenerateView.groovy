import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import org.cubika.labs.scaffolding.generator.DefaultFlexTemplateGenerator
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU
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
	depends( validateDomainClass, generateFlexDefaultStructure, generateFlexBuilder, createFlexProperties, generateDefaults )
	generateViews(domainClass:getDomainClass(args))
}

//"Generate CRUDs views"
generateViews = 
{ Map args = [:] ->
	
	def domainClass = args["domainClass"]
	
	dftg = new DefaultFlexTemplateGenerator();
	
	def nameDir = antProp.'view.destdir'+"/${domainClass.propertyName}"
	
	if (!new File(nameDir).exists())
		Ant.mkdir(dir:nameDir)

	def classNameFile = ""
	def templateFile = ""
	
	classNameFile = "${nameDir}/${domainClass.shortName}ListView.mxml"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'view.listfile'
	generateView(domainClass,templateFile,classNameFile)
	
	classNameFile = "${nameDir}/${domainClass.shortName}EditView.mxml"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'view.editfile'
	generateView(domainClass,templateFile,classNameFile)
	
	classNameFile = "${nameDir}/${domainClass.shortName}CRUDView.mxml"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'view.crudfile'
	generateView(domainClass,templateFile,classNameFile)
	
	//generate ReportingView
	if (CVU.isReportable(domainClass))
	{
		classNameFile = "${nameDir}/${domainClass.shortName}ReportingView.mxml"
		templateFile = "${flexScaffoldPluginDir}"+antProp.'view.reporting'
		generateView(domainClass,templateFile,classNameFile)
		
		classNameFile = "${basedir}/grails-app/controllers/GfsDjReportController.groovy"
		if(!new File(classNameFile).exists())
		{
			templateFile = "${flexScaffoldPluginDir}"+antProp.'controller.reporting'
			Ant.copy(file:templateFile,tofile:classNameFile)
		}
	}
	//end generate reporting view
}

private void generateView(domainClass,templateFile,classNameFile)
{	
	dftg.generateTemplate(domainClass,templateFile,classNameFile)
	println "${classNameFile} Done!"
}
