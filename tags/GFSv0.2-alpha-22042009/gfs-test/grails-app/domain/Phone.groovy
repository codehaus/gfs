class Phone 
{
	String number
	String type
	
	static belongsTo = Customer
	
	static constraints = 
	{
		//if not declared widget, the default 
		//component is a NumericStepper
		number(widget:"textinput") 
		type(inList:["Home","Movil"])
	}
}
