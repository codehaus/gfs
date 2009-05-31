import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

@FlexScaffoldProperty(generate="true")
class Company 
{
	String name
	String description
	String logo

	static reportable = [:]
	
	static constraints = 
	{
		logo(widget:'imageupload')
		name(blank:false)
		description(widget:'textarea')
  }
}
