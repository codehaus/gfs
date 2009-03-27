/**
 * @author Bill Bejeck 6/30/2008
 *
 * Used to generate required Flex artifacts for deployment
 * Usage:
 *       $ grails flex-tasks wrapper - will generate a gsp wrapper file to load the flash player, by default file named index.gsp and placed in web-apps
 *       $ grails flex-tasks compile - will compile flex code into a swf file for deployment, by default swf file is placed in web-apps
 *       $ grails flex-tasks - will run all of the above tasks
 */

Ant.property(environment:"env")   
grailsHome = Ant.project.properties."env.GRAILS_HOME"
basedir = Ant.project.properties.basedir

Ant.property(file:"flex.properties")
def antProp = Ant.project.properties

def mxmlFile = antProp.'mxml.file'
def outputFile = antProp.'output.file'

def FLEX_HOME = Ant.project.properties."env.FLEX_HOME"
//Set FLEX_HOME as property because otherwise mxmlc won't perform
Ant.property(name:"FLEX_HOME",value:FLEX_HOME)

Ant.taskdef (name:'mxmlc', classname:'flex.ant.MxmlcTask', classpath:"$FLEX_HOME/ant/lib/flexTasks.jar")
Ant.taskdef (name:'gspWrapper', classname:'flex.ant.HtmlWrapperTask', classpath:"$FLEX_HOME/ant/lib/flexTasks.jar")

args = System.getProperty("grails.cli.args")

target('default':'Can choose to create gsp wrapper file or compile flex to swf or run both')
{
	
	if (!FLEX_HOME)
	{
		println ""
		println "//////////////// ERROR ///////////////////////////"
		println "//FLEX_HOME must be declarated as enviroment var//"
		println "//////////////////////////////////////////////////"
		println ""
		exit(1)
	}
		
	println "Flex Home: $FLEX_HOME"
	
	if(args)
	{
		if (File.separator == "\\") //Because windows ......
		{
			flexHome = flexHome.replace("\\","/")
		}
	
		if(args.equals('wrapper'))
		{
			wrap()
		}
		else if(args.equals('compile'))
		{
			compile()
		}
		else
		{
		 	println("'"+args+"'  not a valid option")	 
		}
	}
	else
	{
		runAll()
	}
 
	if (!new File("web-app/assets").exists())
 	{
 		Ant.mkdir(dir:"web-app/assets")
 	}	
 
 	Ant.copy(toDir:"web-app/assets")
 	{
		fileset(dir:"flex_src/assets")
		{
			include(name:'**')
		}
 	}

 println "Flex compile Done!"
}

target(runAll:"Runs all Flex tasks")
{
	depends(compile,wrap)
}

target(clean:"Deletes the previous SWF file")
{
		Ant.delete(file:outputFile)
}

target(compile:'Compile Flex project into SWF file')
{		
	Ant.mxmlc( file:mxmlFile,
	           output:outputFile,
	           incremental:antProp.incremental,
	           'actionscript-file-encoding':antProp.encoding,
						 'show-unused-type-selector-warnings' : false,
						 services:"web-app/WEB-INF/flex/services-config.xml",
						 debug:antProp.debug,
						 'context-root':antProp.'context-root',
			       'keep-generated-actionscript':false) 
						 {
			     		'load-config'(filename:"$FLEX_HOME/frameworks/flex-config.xml")
			     		'compiler.library-path'(dir:"$FLEX_HOME/frameworks", append:"true")
							{
				      	include(name:"libs")
				        include(name:"../bundles/{locale}")
				  		}
							'compiler.include-libraries'(dir:"$basedir/flex_libs",append:"true")
							{
								include(name:'Cairngorm.swc')
								include(name:'cubikalabscommons.swc')
							}
							'source-path'('path-element':"$FLEX_HOME/frameworks")
	           }
	
	//Ant.delete(file:"${outputFile}.cache")
}

target(cleanWrap:"Clean up wrapper, wrapper related files")
{
	Ant.delete(file:"web-app/AC_OETags.js")
	Ant.delete(file:"${antProp.output}/${antProp.file}")
	Ant.delete(file:"web-app/playerProductInstall.swf")
	Ant.delete(dir:"web-app/history")
}

target(wrap:"Creates a gsp wrapper to load the flash player")
{
  depends(cleanWrap)
	Ant.gspWrapper(title:antProp.title,
	               file:antProp.file, 
	               height:antProp.height,
	               width:antProp.width,
	               bgcolor:antProp.bgcolor,
	               application:antProp.application,
	               swf:antProp.swf,
	               'version-major':antProp.'version-major',
	               'version-minor':antProp.'version-minor',
	               'version-revision':antProp.'version-revision',
	               history:antProp.history,
	               template:antProp.template,
	               output:antProp.output)
	
	Ant.copy(file:"${antProp.output}/${antProp.file}" , tofile:"grails-app/views/${antProp.file}", overwrite:true)
}