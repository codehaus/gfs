package com.cubika.labs.validator
{
	import mx.validators.StringValidator;
	import mx.validators.ValidationResult;

	public class EqualsStringValidator extends StringValidator
	{
		public var anotherSource:Object;
		
		public function EqualsStringValidator()
		{
			super();
		}
		
		
		override protected function doValidation(value:Object):Array
		{
			if (!anotherSource)
				return []
				
			var results:Array = super.doValidation(value);
			results.concat(super.doValidation(anotherSource[property]));
			
			if (value != anotherSource[property])
				results.push(new ValidationResult(true,"","","Fields are not equals"));
			
			return results;
		}
	}
}