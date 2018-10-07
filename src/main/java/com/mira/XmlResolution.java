/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.StreamingResolution;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author basri baki
 */
public class XmlResolution extends StreamingResolution {

    private Logger log = Logger.getLogger(XmlResolution.class);
    private String type;
    private String sid;
    private String tid;
    private String message;
    public final static String SUCCESS = "SUCCESS";
    public final static String ERROR = "ERROR";
    public final static String INVALID = "INVALID";
    public final static String REDIRECT = "REDIRECT";

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public XmlResolution(String type, String message) {
        super("text/xml");
        setType(type);
        setMessage(message);
    }

    public XmlResolution(String type) {
        super("text/xml");
        setType(type);
        setMessage(type);
    }

    @Override
    public void stream(final HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print("<data>");
            out.print("<action type=\"" + getType() + "\" sid=\"" + getSid() + "\" tid=\"" + getTid() + "\">" + getMessage() + "</action>");
            out.print("</data>");
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            out.flush();
            IOUtils.closeQuietly(out);
        }
    }
}
