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

def addToModel = true

target('default': "") 
{
	depends( validateDomainClass, generateFlexDefaultStructure, generateFlexBuilder, createFlexProperties, generateDefaults )
	
	generateModel(args.trim())
}


generateModel = { Map args = [:] ->
//target(generateModel: "Generate Domain Model for domainClass")
//{	Map arguments ->
	
	addToModel = args["addToModel"]
	
	def domainClass = getDomainClass(args["name"])
	
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
}

private void addToModelLocator(addToModel,domainClass)
{
	if(!addToModel)
		return
	
	def modelDestDir = antProp.'model.destdir'+"/ApplicationModelLocator.as"
	
	if (!new File(modelDestDir).exists())
		Ant.copy(file: "${flexScaffoldPluginDir}"+antProp.'model.locatorfile', 
						 tofile: modelDestDir, overwrite: true)
	
	
	file =  Ant.fileset(	dir: antProp.'model.destdir') {
									include(name:"ApplicationModelLocator.as")
	          			contains(text: "import model.${domainClass.propertyName}.${domainClass.shortName}Model;", 
													 casesensitive: false)
						 		}
				
	if (file.size() > 0)
			return
	
	Ant.replace(file: modelDestDir,
          token: "/*IMPORTS*/", value: "/*IMPORTS*/\n	"+
								 "import model.${domainClass.propertyName}.${domainClass.shortName}Model;")
	
	Ant.replace(file: modelDestDir,
          token: "/*PROPERTIES*/", value: "/*PROPERTIES*/\n		"+
								 "private var _${domainClass.propertyName}Model:${domainClass.shortName}Model;")
	
	Ant.replace(file: modelDestDir,
				          token: "/*GETTERS*/", value: "/*GETTERS*/\n		"+
									"public function get ${domainClass.propertyName}Model():${domainClass.shortName}Model\n		{\n			"+
									"if (!_${domainClass.propertyName}Model)\n				_${domainClass.propertyName}Model = "+
									"new ${domainClass.shortName}Model();\n			"+
									"return _${domainClass.propertyName}Model\n		}")
}
