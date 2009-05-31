import ar.com.fdvs.dj.core.DynamicJasperHelper
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager
import ar.com.fdvs.dj.domain.ColumnProperty
import ar.com.fdvs.dj.domain.DJCalculation
import ar.com.fdvs.dj.domain.DynamicReport
import ar.com.fdvs.dj.domain.DynamicReportOptions
import ar.com.fdvs.dj.domain.Style
import ar.com.fdvs.dj.domain.constants.HorizontalAlign
import ar.com.fdvs.dj.domain.entities.DJGroup
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn
import ar.com.fdvs.dj.domain.entities.columns.SimpleColumn
import ar.com.fdvs.dj.output.ReportWriter
import ar.com.fdvs.dj.output.ReportWriterFactory
import grails.util.GrailsUtil
import net.sf.jasperreports.engine.JRDataSource
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter
import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.codehaus.groovy.grails.commons.GrailsClass
import ar.com.fdvs.dj.domain.entities.DJGroupVariable

import org.apache.commons.io.IOUtils;
import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;

import ar.com.fdvs.dj.output.*
import net.sf.jasperreports.engine.*


import com.adobe.acrobat.PDFDocument;  
import com.adobe.acrobat.*;  
import com.adobe.acrobat.gui.*;  
import com.adobe.acrobat.util.*;  
import com.adobe.acrobat.sidecar.*;  
import javax.imageio.*;  
import javax.swing.*;  
import java.io.*;  
import java.awt.*;  
import java.awt.image.*; 

import org.cubika.labs.scaffolding.response.ReportingResponseWrapper


class GfsDjReportController extends DjReportController
{
	final static String PDF_TEMP_FILE_NAME = "report.pdf"
	final static String JPG_TEMP_FILE_NAME = "report.jpg"
    final static String DEFAULT_REPORT_FORMAT = "jpg"

    DynamicReport report
    def columns
    def columnNames
    def groupColumns
    def items
    def reportFormat
    def exporter

    File pdfTempFile
    File imgTempFile

    ReportingResponseWrapper responseWrapper

    def pdfStream

    def doReport(def domainClass, def config, def params, def request, def response) {
        responseWrapper = new ReportingResponseWrapper()
        super.doReport(domainClass, config, params, request, responseWrapper)//(responseWrapper)
        imgTempFile = new File(JPG_TEMP_FILE_NAME)
		pdfTempFile = new File(PDF_TEMP_FILE_NAME)
        doExportReportToPDF()
        convertToImage()
        loadImageToResponseAndReset(response)
    }

     def doExportReportToPDF()
     {
        //Generates Pdf temporal file2
        
        def output = new FileOutputStream(pdfTempFile)
        def input = new ByteArrayInputStream(responseWrapper.toByteArray())

        IOUtils.copy(input,output)

       // input.close()
        output.flush()
        output.close()
    }


    private void convertToImage() {
		JFrame dummyFrame = new JFrame();

		PDFDocument pdfDocument = new PDFDocument(pdfTempFile)
		FloatPoint cropBoxSize = pdfDocument.getPageSize(0)//ITERAR  
		int width = 1024  
		int height = 1448
		dummyFrame.setVisible(true)
		BufferedImage img = new java.awt.image.BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB) 
		float hScale = (float)((width ) / cropBoxSize.x)  
		float vScale = (float)((height) / cropBoxSize.y)  
		float scale = (float)Math.min(hScale, vScale)  
		int w = (int)(cropBoxSize.x * scale)  
		int h = (int)(cropBoxSize.y * scale)  
    
		Image osImage = dummyFrame.createImage(w, h);  
		AffineTransform transform = new AffineTransform(scale, 0, 0, scale, 0, 0);  

		Graphics g=img.getGraphics();  
		g.setColor(java.awt.Color.white);  
		g.fillRect(0,0,width,height);  
    
		pdfDocument.drawPage(0, img, transform, null, dummyFrame);  
		ImageIO.write(img,DEFAULT_REPORT_FORMAT,imgTempFile);
		dummyFrame.setVisible(false);
	 } 

    def loadImageToResponseAndReset(def response)
    {
        response.setContentType("image/jpeg")
        response.setContentLength(Integer.parseInt(imgTempFile.size().toString()))
        def out = response.getOutputStream()
        IOUtils.copy(new FileInputStream(imgTempFile),out)
        //out.flush()
        //out.close()
        
        imgTempFile.delete();
        pdfTempFile.delete();
    }

}
