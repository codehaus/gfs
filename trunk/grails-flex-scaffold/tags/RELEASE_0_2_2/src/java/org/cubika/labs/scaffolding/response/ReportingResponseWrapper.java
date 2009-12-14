/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cubika.labs.scaffolding.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.mock.web.DelegatingServletOutputStream;

/**
 *
 * @author gclavell
 */
public class ReportingResponseWrapper implements HttpServletResponse {

    private DelegatingServletOutputStream dsos;
    private ByteArrayOutputStream byteOS;
    private PrintWriter pWriter;

    public ReportingResponseWrapper() {
        byteOS = new ByteArrayOutputStream();
        dsos = new DelegatingServletOutputStream(byteOS);
        pWriter = new PrintWriter(dsos);
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return dsos;
    }

    public byte[] toByteArray() {
        return byteOS.toByteArray();
    }

    public void flushBuffer() throws IOException {
        dsos.flush();
    }

    public PrintWriter getWriter() throws IOException {
        return pWriter;
    }

    public void addCookie(Cookie arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsHeader(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String encodeURL(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String encodeRedirectURL(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String encodeUrl(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String encodeRedirectUrl(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendError(int arg0, String arg1) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendError(int arg0) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendRedirect(String arg0) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDateHeader(String arg0, long arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addDateHeader(String arg0, long arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setHeader(String arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addHeader(String arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setIntHeader(String arg0, int arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addIntHeader(String arg0, int arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setStatus(int arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setStatus(int arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getCharacterEncoding() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getContentType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCharacterEncoding(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setContentLength(int arg0) {
        //
    }

    public void setContentType(String arg0) {
        //
    }

    public void setBufferSize(int arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getBufferSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resetBuffer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isCommitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLocale(Locale arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Locale getLocale() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   

}
