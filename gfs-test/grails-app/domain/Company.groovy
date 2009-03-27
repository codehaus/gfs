import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

//@FlexScaffoldProperty(labelField="name") is
//The label field is displayed in edit-view
//of the relation external
@FlexScaffoldProperty(labelField="name")
class Company 
{
	String name
	String address
	
	//One-to-Many
	static hasMany = [customers:Customer]
	
	static mapping = 
	{
		customers lazy:false, cascade:"none"
	}
	
	static constraints = 
	{
		name(blank:false)
		address(blank:false)
		customers(display:false)//Not view customer in Company's edit-view
	}
}
