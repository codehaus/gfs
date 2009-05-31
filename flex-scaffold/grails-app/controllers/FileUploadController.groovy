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

import org.apache.commons.io.IOUtils
import java.util.prefs.Base64

/**
 * Gets logict to upload files
 *
 * @author Ezequiel Martin Apfel
 * @since  28-Apr-2009
 */

class FileUploadController 
{
	
	def index = 
	{	  
		String path = grailsApplication.config.filepath ? "${grailsApplication.config.filepath}/" : "" 
	
		def file = request.getFile("uploadfile")
		
    
		if(!file.empty) 
		{
			try 
			{
				def filename = "${path}${request.getParameter('Filename')}".trim()
        file.transferTo(new File(filename))
				
				def output = response.getOutputStream();
				output.println(filename)
				output.flush()		
			}
			catch (Exception e)
			{
				e.printStackTrace()
			}
    }
    else 
		{
        flash.message = 'file cannot be empty'
    }
	}
	
	def snapshot = 
	{
		String path = grailsApplication.config.filepath ? "${grailsApplication.config.filepath}/" : "" 
		
		String filename = "${path}${params.name}".trim()

		try 
		{
			def image = Base64.base64ToByteArray(params.image)

			File f = new File(filename)
			FileOutputStream fos = new FileOutputStream(f)
		
			fos.write(image)
			fos.flush()
			fos.close()

			def output = response.getOutputStream();
			output.println(filename)
			output.flush()
		}
		catch (Exception e)
		{
			e.printStackTrace()
		}
	}
	
	def get = 
	{
		String filePath = params.filePath.trim()
		
		def file = new File(filePath)
		
		if (file.exists())
		{
			try 
			{
				def input = new FileInputStream(file)
				def output = response.getOutputStream()
			
				IOUtils.copy(input,output)
			
				input.close()
				output.flush()
				output.close()
			}
			catch (Exception e)
			{
				e.printStackTrace()
			}
		}
	}
	
	def delete = 
	{
		String filePath = params.filePath.trim()
		try
		{
			if (new File(filePath).delete())
			{
				response.sendError(200,"Done!")
			}
		}
		catch (Exception e)
		{
			e.printStackTrace()
		}
	}
}