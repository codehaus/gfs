package assets.programmatic
{
	import flash.display.GradientType;
	import flash.geom.Matrix;
	
	import mx.graphics.RectangularDropShadow;
	import mx.skins.RectangularBorder;
	
	
	public class ToogleButtonSkin extends RectangularBorder
	{
		
		private var dropShadow:RectangularDropShadow;
		
		private var dropShadowEnabled:Boolean = true;
		
		private var cornerRadius:Number;
		
		private var matrix:Matrix;
		
		
		public function ToogleButtonSkin()
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
			cornerRadius = getStyle("cornerRadius");
			var fillAlphas:Array = getStyle("fillAlphas");
			var fillColors:Array = getStyle("fillColors");
			
			var focusRoundedCorners:Array = getStyle("focusRoundedCorners");
			
			var gradientType:String = getStyle("gradientType");
			var angle:Number = getStyle("angle");
			var focalPointRatio:Number = getStyle("focalPointRatio");
			
			dropShadowEnabled = getStyle("dropShadowEnabled");
			
			// Alpha para selectedSkin
			var selectedAlpha:Array =
							[ Math.max( 0, fillAlphas[0] - 0.5),
							  Math.max( 0, fillAlphas[1] - 0.5) ];
			

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
	            dropShadow.tlRadius = obj.tl;
	            dropShadow.trRadius = obj.tr;
	            dropShadow.blRadius = obj.bl;
	            dropShadow.brRadius = obj.br;
	            
	            dropShadow.drawShadow(graphics, 0, 0, w, h); 
			}
			
			switch (name)
			{
				case "upSkin":
				case "overSkin":
				{
					matrix = new Matrix();
					matrix.createGradientBox(w, h, angle * Math.PI / 180);
					
					graphics.beginGradientFill(gradientType, 
											fillColors, 
											selectedAlpha, 
											[0, 255] , 
											matrix, 
											"pad", 
											"rgb", 
											focalPointRatio);
					drawRoundRect
		            (
		                0, 0, w, h, 
		               roundedBorders(focusRoundedCorners), 
		                backgroundColor, backgroundAlpha
		            );
					
					graphics.endFill();
					
					break;
				}
		
		
				case "disabledSkin":
				{
	   				var disFillColors:Array = [ fillColors[0], fillColors[1] ];
	
	   				var disFillAlphas:Array =
						[ Math.max( 0, fillAlphas[0] - 0.15),
						  Math.max( 0, fillAlphas[1] - 0.15) ];
				
				
					// tab fill
					drawRoundRect(
						1, 1, w - 2, h - 2, roundedBorders(focusRoundedCorners),
						disFillColors, disFillAlphas,
						verticalGradientMatrix(0, 2, w - 2, h - 2));
				
						// outer edge
//						drawRoundRect(
//							0, 0, w, h - 1, roundedBorders(focusRoundedCorners),
//							[ derStyles.borderColorDrk1, borderColor], 1,
//							verticalGradientMatrix(0, 0, w, h - 6));
						
					break;
				}
					
				case "selectedUpSkin":
				case "selectedDownSkin":
				case "selectedOverSkin":
				{
					
					graphics.beginGradientFill(gradientType, fillColors, fillAlphas, [0, 255] , matrix, "pad", "rgb", focalPointRatio);
					drawRoundRect
		            (
		                0, 0, w, h, 
		               roundedBorders(focusRoundedCorners), 
		                backgroundColor, backgroundAlpha
		            );
					
					graphics.endFill();
			        
		
					break;
				}
					
				case "downSkin":
				case "selectedDisabledSkin":
				{
					
					matrix = new Matrix();
					matrix.createGradientBox(w, h, angle * Math.PI / 180);
					
					graphics.beginGradientFill(gradientType, fillColors, fillAlphas, [0, 255] , matrix, "pad", "rgb", focalPointRatio);
					drawRoundRect
		            (
		                0, 0, w, h, 
		               roundedBorders(focusRoundedCorners), 
		                backgroundColor, backgroundAlpha
		            );
					
					graphics.endFill();
			        
					break;
				}
			}
		}
			
			
		protected function roundedBorders(value:Array):Object
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