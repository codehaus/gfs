class Address 
{
	String street
	Integer	number
	String zip
	String observation
	Customer customer
	
	static constraints = 
	{
		street(blank:false)		
		//if not declared widget, the default 
		//component is a NumericStepper
		number(widget:"textinput") 
		zip(blank:false)
		observation(widget:"textarea")
		//Not view customer in Address' edit-view	
		customer(display:false)	
	}
}
