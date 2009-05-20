package org.cubika.labs.scaffolding.form.factory

////////////////////////////////////////////////////////////////////
// Copyright 2009 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
////////////////////////////////////////////////////////////////////

import org.cubika.labs.scaffolding.form.BuildFormItem
import org.cubika.labs.scaffolding.form.impl.TextInputBuildFormItem
import org.cubika.labs.scaffolding.form.impl.TextAreaBuildFormItem
import org.cubika.labs.scaffolding.form.impl.RichTextEditorBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ComboBuildFormItem
import org.cubika.labs.scaffolding.form.impl.AutoCompleteBuildFormItem
import org.cubika.labs.scaffolding.form.impl.NumericStepperBuildFormItem
import org.cubika.labs.scaffolding.form.impl.CheckBoxBuildFormItem
import org.cubika.labs.scaffolding.form.impl.DateFieldBuildFormItem
import org.cubika.labs.scaffolding.form.impl.OneToManyBuildFormItem
import org.cubika.labs.scaffolding.form.impl.OneToOneBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ExternalOneToOneBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ExternalOneToManyBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ExternalManyToOneBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ExternalDataGridManyToOneBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ColorPickerBuildFormItem
import org.cubika.labs.scaffolding.form.impl.SliderBuildFormItem
import org.cubika.labs.scaffolding.form.impl.FileUploadBuildFormItem
import org.cubika.labs.scaffolding.form.impl.ImageUploadBuildFormItem
import org.cubika.labs.scaffolding.form.impl.SnapshotUploadBuildFormItem

import org.cubika.labs.scaffolding.form.FormItemConstants as FIC
import org.cubika.labs.scaffolding.utils.ConstraintValueUtils as CVU

/**
 * Form item builder factory
 *  
 * @author Ezequiel Martin Apfel
 * @sing 19-Feb-2009
 */
class BuildFormItemFactory 
{	
	/**
	 * Create form item based on property parameter 
	 *
	 */
	static BuildFormItem createFormItem(property)
	{
		def constraint = property.domainClass.getConstrainedProperties()[property.name]
		
		if (!CVU.display(property))
			return
		
		if (constraint.widget == FIC.IMAGE_UPLOAD)
		{
			validateWidget(property.type,String.class,constraint.widget)
			return new ImageUploadBuildFormItem(property)
		}
		
		if (constraint.widget == FIC.FILE_UPLOAD)
		{
			validateWidget(property.type,String.class,constraint.widget)
			return new FileUploadBuildFormItem(property)
		}
		
		if (constraint.widget == FIC.SNAP_UPLOAD)
		{
			validateWidget(property.type,String.class,constraint.widget)
			return new SnapshotUploadBuildFormItem(property)
		}
		
		if (constraint.widget == FIC.TEXT_INPUT)
			return new TextInputBuildFormItem(property)
		
		if (constraint.widget == FIC.TEXT_AREA)
			return new TextAreaBuildFormItem(property)
		
		if (CVU.richtext(property))
		{
			validateWidget(property.type,String.class,constraint.widget)
			return new RichTextEditorBuildFormItem(property)
		}
		
		if (constraint.widget == FIC.COLOR_PICKER)
		{
			validateWidget(property.type,String.class,constraint.widget)
			return new ColorPickerBuildFormItem(property)
		}
		
		if (constraint.widget == FIC.V_SLIDER)
		{
			return new SliderBuildFormItem(property,"V")
		}
		
		if (constraint.widget == FIC.H_SLIDER)
		{
			return new SliderBuildFormItem(property,"H")
		}
		
		if (property.type == String.class && !constraint.inList)
			return new TextInputBuildFormItem(property)
		
		if (constraint.inList && constraint.widget != FIC.AUTO_COMPLETE)
			return new ComboBuildFormItem(property)

		if (constraint.inList && constraint.widget == FIC.AUTO_COMPLETE)
			return new AutoCompleteBuildFormItem(property)
		
		if (property.type == Integer.class || property.type == Long.class || property.type == Float.class 
				|| property.type == Double.class)
			return new NumericStepperBuildFormItem(property)
			
		if (property.type == Boolean.class)
			return new CheckBoxBuildFormItem(property)	
			
		if (property.type == Date.class)
			return new DateFieldBuildFormItem(property)
		
		if (property.isManyToMany())
			return
		
		def inPlace = CVU.getInPlace(constraint)
		
		if (property.isManyToOne())
			//if (!inPlace)
				if (constraint.widget == FIC.COMBO_BOX)
					return new ExternalManyToOneBuildFormItem(property)
				else
					return new ExternalDataGridManyToOneBuildFormItem(property)
			//else
				//return new ManyToOneBuildFormItem(property)
		
		if (property.isOneToMany())
        {
			if (inPlace)
				return new OneToManyBuildFormItem(property)
			else
				return new ExternalOneToManyBuildFormItem(property) 
        }

		if (property.isOneToOne())
        {
			if (!inPlace && !property.bidirectional) //La trato como una many-to-one inPlace:false
            {
				if (constraint.widget == FIC.COMBO_BOX)
                {
                    return new ExternalManyToOneBuildFormItem(property)
                }
				else
                {
					return new ExternalDataGridManyToOneBuildFormItem(property)
                }
            }
			else if (inPlace)
            {
				return new OneToOneBuildFormItem(property)
            }
        }
	}
	
	static private void validateWidget(type,clazz,widget)
	{
		if (type != clazz)
			throw new Exception("Property must be ${clazz} to use \"${widget}\"")
	}
}
