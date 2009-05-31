import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

@FlexScaffoldProperty(generate="true")
class Customer extends Person
{
	Company company
	
	static mapping =
	{
		company lazy:false, cascade:"none", fetch:"join"
	}

    static constraints = {
			company(inPlace:false)
    }
}
