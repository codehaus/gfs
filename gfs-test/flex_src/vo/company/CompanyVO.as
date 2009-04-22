
package vo.company
{

	/**
	 * Company was generated by GFS
	 */
	[Bindable]	
	[RemoteClass(alias='Company')]
	public class CompanyVO
	{
		//Identity
		public var id:Object;
		public var version:Object;
		//Properties
		public var address:String;
		public var description:String;
		public var name:String;
	}
}