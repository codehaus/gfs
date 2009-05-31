package assets.programmatic
{
	import mx.skins.RectangularBorder;
	import flash.display.GradientType;
	import flash.geom.Matrix;
	
	import mx.graphics.RectangularDropShadow;
	
	
	public class FourRoundedCorners extends RectangularBorder
	{
		private var dropShadow:RectangularDropShadow;
		
		private var dropShadowEnabled:Boolean = true;
		
		private var cornerRadius:Number;
		
		public function FourRoundedCorners()
		{
			super();
		}
		
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);
			
			// User-defined styles.
			var backgroundAlpha:Number = getStyle("backgroundAlpha");		
			var backgroundColor:Number = getStyle("backgroundColor");
			var borderColor:uint = getStyle("borderColor");
			var shadowColor:uint = getStyle("shadowColor");
//			var cornerRadius:Number = getStyle("cornerRadius");
			cornerRadius = getStyle("cornerRadius");
			var fillAlphas:Array = getStyle("fillAlphas");
			var fillColors:Array = getStyle("fillColors");
			
			var focusRoundedCorners:Array = getStyle("focusRoundedCorners");
			
			var gradientType:String = getStyle("gradientType");
			var angle:Number = getStyle("angle");
			var focalPointRatio:Number = getStyle("focalPointRatio");
			
			dropShadowEnabled = getStyle("dropShadowEnabled");
			
			
			

			graphics.clear();

			// VAlores default
			if (fillColors == null)
				fillColors = [0xEEEEEE, 0x999999];
			
			if (fillAlphas == null)
				fillAlphas = [1, 1];
			
			if (gradientType == "" || gradientType == null)
				gradientType = GradientType.LINEAR;
			
			if (isNaN(angle))
				angle = 90;
				
			if (isNaN(focalPointRatio))
				focalPointRatio = 0.5;
				
			
			var matrix:Matrix = new Matrix();
			matrix.createGradientBox(w, h, angle * Math.PI / 180);
			
			graphics.beginGradientFill(gradientType, fillColors, fillAlphas, [0, 255] , matrix, "pad", "rgb", focalPointRatio);
			drawRoundRect
            (
                0, 0, w, h, 
                /* {tl: 0, tr: cornerRadius, bl: cornerRadius, br: cornerRadius} */roundedBorders(focusRoundedCorners), 
                backgroundColor, backgroundAlpha
            );
			
			graphics.endFill();
				
        
            // Shadow

			if(dropShadowEnabled)
			{
	             if (!dropShadow)
	                dropShadow = new RectangularDropShadow();
	            
	            dropShadow.distance = 4;
	            dropShadow.angle = 45;
	            dropShadow.color = 0x333333;
	            dropShadow.alpha = 0.4;
	            
	            var obj:Object = roundedBorders(focusRoundedCorners);
	            dropShadow.tlRadius = obj.tl;//0;
	            dropShadow.trRadius = obj.tr;
	            dropShadow.blRadius = obj.bl;
	            dropShadow.brRadius = obj.br;
	            
	            dropShadow.drawShadow(graphics, 0, 0, w, h); 
			}
			
			
			

		}
		
		
		private function roundedBorders(value:Array):Object
		{
			var o:Object = new Object();			
			o.tl = 0;
			o.tr = 0;
			o.bl = 0;
			o.br = 0;
			
			for each (var property:String in value)
			{
				// o[property] = cornerRadius;
				if(property == "tl")
					o.tl = cornerRadius;
				
				if(property == "tr")
					o.tr = cornerRadius;
				
				if(property == "bl")
					o.bl = cornerRadius;
				
				if(property == "br")
					o.br = cornerRadius;
				
			}

			return o;
		}

		
	}
}