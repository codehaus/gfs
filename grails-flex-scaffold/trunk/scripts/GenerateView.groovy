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
	
	//generate ItemRenderers Actions
	def actions = CVU.actions(domainClass)
	
	actions.each
	{
		if (!new File("${nameDir}/renderers").exists())
			Ant.mkdir(dir:"${nameDir}/renderers")
			
		classNameFile = "${nameDir}/renderers/${domainClass.shortName}${it}ItemRenderer.mxml"
		templateFile = "${flexScaffoldPluginDir}"+antProp.'view.actionitemrenderer'
		dftg.generateTemplate(domainClass,templateFile,classNameFile,it)
	}
	//end generate ItemRenderers Actions
	
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
	
	//GROUP VIEW
	addToGroup(domainClass)
	//GROUP VIEW END 
}

private void addToGroup(domainClass)
{
	def groupName =  CVU.groupName(domainClass)
	if (!groupName)
		return 
	
	groupName = groupName.replaceAll(" ","")
	def nameDir = antProp.'view.destdir'+"/${groupName.toLowerCase()}"
	def classNameFile = "${nameDir}/${groupName}GroupView.mxml"
	def templateFile
		
	if (!(new File(nameDir).exists()))
	{
		Ant.mkdir(dir:nameDir)
	}
		
	
	if (!(new File(classNameFile).exists()))
	{
		templateFile = "${flexScaffoldPluginDir}"+antProp.'view.groupfile'
		generateView(domainClass,templateFile,classNameFile)
	}
	
	def file =  Ant.fileset(dir: nameDir) 
	{
		include(name:classNameFile)
		contains(text: "<view${domainClass.shortName}:${domainClass.shortName}CRUDView", casesensitive: false)
	}
	
	
	if (!(file.size() > 0))
	{
		Ant.replace(file: classNameFile,
          			token: "><!--NS-->", value: "\n	xmlns:view${domainClass.shortName}=\"view.${domainClass.propertyName}.*\"><!--NS-->")
        Ant.replace(file: classNameFile,
				    token: "<!--CRUDVIEWS-->", value: "<!--CRUDVIEWS-->\n"+
					"		<view${domainClass.shortName}:${domainClass.shortName}CRUDView height=\"100%\" "+
					"label=\"{MultipleRM.getString(MultipleRM.localePrefix,'${domainClass.propertyName}.label')}\" name=\"${domainClass.propertyName}CRUDView\"/>")
	}
	
}

private void generateView(domainClass,templateFile,classNameFile)
{	
	dftg.generateTemplate(domainClass,templateFile,classNameFile)
	println "${classNameFile} Done!"
}
