class Address 
{
	String street
	String zip

	static constraints = 
	{
		street(widget:'textarea')
		zip(widget:'textinput')
  }
}
