import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

//@FlexScaffoldProperty(labelField="name") is
//The label field is displayed in edit-view
//of the relation external
@FlexScaffoldProperty(generate="true")
class Customer 
{
	String firstName
	String lastName
	String email
	Date dateOfBirth
	Phone phone
	List addresses
	Company company
	String maritalStatus
	Integer age
	Boolean enabled
	String blog
	
	
	static reportable = [:]
	static hasMany = [addresses:Address]
	static belongsTo = Company
	
	static mapping = 
	{
		addresses lazy:false, cascade:"all-delete-orphan"
		company lazy:false, cascade:"none"
		phone lazy:false, cascade:"all"
	}
	
	static constraints = 
	{
		firstName(minSize:2,blank:false)
		lastName(maxSize:20, i18n:[es:"Apellidos"])
		dateOfBirth()
		age(range:18..99)
		email(email:true, blank:false)	
			
		//if not declared widget, the default component is a ComboBox
		maritalStatus(inList:["Single","Married","Divorce","Widower"],i18n:[es:"Estado Civil"])
			
		addresses()
			
		//if inPlace:false, a ComboBox is created in the "edit-view" 
		//of the class containing it, and it's filled with the information that makes a reference of it. 
		//Besides, it allows to create a new record from the edit-view of the referenced class through a button ("add")
		//by default inPlace is true
		company(inPlace:false, nullable:true, i18n:[es:"Empresa"])
				
		//The componente will be hidden when the form is setted CREATE_VIEW mode
		//and sets the defaultValue, in this case the value is true
		//If you wish, use the metaConstraint editView:false to hide component in EDIT_VIEW mode
		enabled(createView:false,defaultValue:'true')
		
		blog(url:true)
		phone()
	}
}
