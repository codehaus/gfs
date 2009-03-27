import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import org.cubika.labs.scaffolding.generator.DefaultFlexTemplateGenerator

Ant.property(file:"${flexScaffoldPluginDir}/scripts/flexScaffold.properties")

def antProp = Ant.project.properties

generateFlexDefaultStructure =
{ Map args = [:] ->
	if (new File(antProp.'vo.destdir').exists())
		return
	
	if (!new File(antProp.'flex.basedir').exists())
		Ant.mkdir(dir:antProp.'flex.basedir')
		
	if (!new File(antProp.'vo.destdir').exists())
			Ant.mkdir(dir:antProp.'vo.destdir')
	
	if (!new File(antProp.'view.destdir').exists())
			Ant.mkdir(dir:antProp.'view.destdir')
	
	if (!new File(antProp.'service.destdir').exists())
		  Ant.mkdir(dir:antProp.'service.destdir')
		
	if (!new File(antProp.'model.destdir').exists())
			Ant.mkdir(dir:antProp.'model.destdir')
	
	if (!new File(antProp.'lib.destdir').exists())
			Ant.mkdir(dir:antProp.'lib.destdir')
	
	if (!new File(antProp.'event.destdir').exists())
			Ant.mkdir(dir:antProp.'event.destdir')
	
	if (!new File(antProp.'css.destdir').exists())
	{
			Ant.mkdir(dir:antProp.'css.destdir')
			Ant.copy(file: "${flexScaffoldPluginDir}"+antProp.'css.styleselected', tofile: antProp.'css.file', 
							 overwrite: true)
	}
	
	if (!new File(antProp.'controller.destdir').exists())
			Ant.mkdir(dir:antProp.'controller.destdir')				
		
	if (!new File(antProp.'command.destdir').exists())
			Ant.mkdir(dir:antProp.'command.destdir')
			
	if (!new File(antProp.'assets.destdir').exists())
	{
			Ant.mkdir(dir:antProp.'assets.destdir')
			
			Ant.copy(toDir:antProp.'assets.destdir'){
				fileset(dir:"${flexScaffoldPluginDir}"+antProp.'css.assetsselected')
				{
					include(name:'**')
				}
			}
	}
}
