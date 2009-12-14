package org.cubika.labs.scaffolding.reporting.column
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


/**
 * Builds flex component for Reporting Columns edition
 *
 * @author Gonzalo Javier Clavell
 * @since  10-Mar-2009
 */
public class ReportingColumnBuilder
{
	static String buildReportingColumnForm(domainClass,className,formId)
	{
		def result = "				<mx:List id=\"${formId}\" styleName=\"reportingList\" itemRenderer=\"com.cubika.labs.controls.reporting.column.ReportingColumnControl\"\n"
			result +="					dataProvider=\"{_model.columnDataProvider}\" dragEnabled=\"true\" dropEnabled=\"true\" dragMoveEnabled=\"true\" paddingLeft=\"20\" width=\"100%\" height=\"100%\" backgroundAlpha=\"0\"/>"
		result				
	}
}
