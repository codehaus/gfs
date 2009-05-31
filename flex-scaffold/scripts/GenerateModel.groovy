import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU
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

def addToModel = true

target('default': "") 
{
	depends( validateDomainClass, generateFlexDefaultStructure, generateFlexBuilder, createFlexProperties, generateDefaults )
	
	generateModel(domainClass:getDomainClass(args.trim()))
}

//"Generate Domain Model for domainClass"
generateModel = { Map args = [:] ->
	
	addToModel = args["addToModel"]
	
	def domainClass = args["domainClass"]
	
	dftg = new DefaultFlexTemplateGenerator();
	
	def nameDir = antProp.'model.destdir'+"/${domainClass.propertyName}"
	
	if (!new File(nameDir).exists())
		Ant.mkdir(dir:nameDir)

	def classNameFile = ""
	def templateFile = ""
	
	classNameFile = "${nameDir}/${domainClass.shortName}Model.as"
	templateFile = "${flexScaffoldPluginDir}"+antProp.'model.domainfile'
	dftg.generateTemplate(domainClass,templateFile,classNameFile)
	println "${classNameFile} Done!"
	addToModelLocator(addToModel,domainClass)
	
	if(CVU.isReportable(domainClass))
	{
		classNameFile = "${nameDir}/${domainClass.shortName}ReportingModel.as"
		templateFile = "${flexScaffoldPluginDir}"+antProp.'model.reportingfile'
		dftg.generateTemplate(domainClass,templateFile,classNameFile)
		addToModelLocator(addToModel,domainClass,"Reporting")
		println "${classNameFile} Done!"

	}
}

private void addToModelLocator(addToModel,domainClass, prefix="")
{
	if(!addToModel)
		return
	
	def modelDestDir = antProp.'model.destdir'+"/ApplicationModelLocator.as"
	
	if (!new File(modelDestDir).exists())
		Ant.copy(file: "${flexScaffoldPluginDir}"+antProp.'model.locatorfile', 
						 tofile: modelDestDir, overwrite: true)
	
	
	file =  Ant.fileset(	dir: antProp.'model.destdir') {
									include(name:"ApplicationModelLocator.as")
	          			contains(text: "import model.${domainClass.propertyName}.${domainClass.shortName}${prefix}Model;", 
													 casesensitive: false)
						 		}
				
	if (file.size() > 0)
			return
	
	Ant.replace(file: modelDestDir,
          token: "/*IMPORTS*/", value: "/*IMPORTS*/\n	"+
								 "import model.${domainClass.propertyName}.${domainClass.shortName}${prefix}Model;")
	
	Ant.replace(file: modelDestDir,
          token: "/*PROPERTIES*/", value: "/*PROPERTIES*/\n		"+
								 "private var _${domainClass.propertyName}${prefix}Model:${domainClass.shortName}${prefix}Model;")
	
	Ant.replace(file: modelDestDir,
				          token: "/*GETTERS*/", value: "/*GETTERS*/\n		"+
									"public function get ${domainClass.propertyName}${prefix}Model():${domainClass.shortName}${prefix}Model\n		{\n			"+
									"if (!_${domainClass.propertyName}${prefix}Model)\n				_${domainClass.propertyName}${prefix}Model = "+
									"new ${domainClass.shortName}${prefix}Model();\n			"+
									"return _${domainClass.propertyName}${prefix}Model\n		}")
}
