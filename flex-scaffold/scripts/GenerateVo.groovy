import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU
import org.cubika.labs.scaffolding.generator.DefaultFlexTemplateGenerator
import org.codehaus.groovy.grails.commons.GrailsDomainClass

grailsHome = Ant.project.properties."environment.GRAILS_HOME"

Ant.property(file:"${flexScaffoldPluginDir}/scripts/flexScaffold.properties")

antProp = Ant.project.properties

//Private scripts
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_CreateFlexProperties.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateDefaults.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateEclipse.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_GenerateStructure.groovy" )
includeTargets << new File ( "${flexScaffoldPluginDir}/scripts/_ValidateDomainClass.groovy" )

target('default': "") 
{
	depends( validateDomainClass, generateFlexDefaultStructure, generateFlexBuilder, createFlexProperties, generateDefaults )
	
	generateVo(domainClass:getDomainClass(args))
}

//"Create Value Object for AS3"
generateVo = 
{ Map args = [:] ->
	
	dftg = new DefaultFlexTemplateGenerator();
	
	//ValidateDomainClass method
	def domainClass = args["domainClass"]
  
	mapVOGenerated = [:]
	mapVOHierarchyGenerated = [:]
	
	generateVO(domainClass)
}


void generateVO(domainClass)
{
	def nameDir = antProp.'vo.destdir'+"/${domainClass.propertyName}";
	def classNameDir = "${nameDir}/${domainClass.shortName}VO.as";
	def templateDir = "${flexScaffoldPluginDir}"+antProp.'vo.file';
	
	if (!new File(nameDir).exists())
		Ant.mkdir(dir:nameDir)
	
	//if (new File(classNameDir).exists())
		//Ant.input(addProperty: "${classNameDir}.overwrite", message: "${classNameDir} already exists. Overwrite? [Y/n]")
    //if (Ant.antProject.properties."${classNameDir}.overwrite" == "n")
    //    return
	
	dftg.generateTemplate(domainClass,templateDir,classNameDir)
	println "${classNameDir} Done!"
	
	mapVOGenerated.put(domainClass,domainClass)
	mapVOHierarchyGenerated.put(domainClass,domainClass)
	
	generateRelations(domainClass)
	generateHierarchy(domainClass)
}

void generateHierarchy(domainClass)
{
	Class superClass = domainClass.getClazz().getSuperclass();

	while (!superClass.equals(Object.class) && !superClass.equals(GroovyObject.class)) 
	{
  	GrailsDomainClass gdc = (GrailsDomainClass) grailsApp.getDomainClass(superClass.getName())

    if (gdc == null || gdc.getSubClasses() == null)
		{
    	println "did not find superclass names when mapping inheritance...."	
      break
    }

		if (!mapVOGenerated.containsKey(gdc))
	 		generateVO(gdc)
	
		superClass = superClass.getSuperclass()	
   }
}

void generateRelations(domainClass)
{
	domainClass.properties.each
	{
		if ((it.isOneToOne() || it.isOneToMany() || it.isManyToOne()) && CVU.display(it))
		{
			if (!mapVOGenerated.containsKey(it.referencedDomainClass))
			{
				generateVO(it.referencedDomainClass)
			}
		}
	}
}