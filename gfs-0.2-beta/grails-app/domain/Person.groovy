class Person 
{
	String firstName
	String lastName
	String email
	String personalPhoto
	
	static hasMany = [addresses:Address]

	static mapping =
	{
		addresses lazy:false, cascade:'all-delete-orphan'
	}

  static constraints = 
	{
		personalPhoto(widget:"snapshotupload")
		firstName(blank:false)
		lastName(blank:false)
		email(email:true)
		addresses()
	}
}
