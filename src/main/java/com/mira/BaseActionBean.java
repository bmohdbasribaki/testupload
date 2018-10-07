/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira;

import com.jcore.CoreJdbc;
import com.mira.database.ConnectionManager;
import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.controller.StripesFilter;
import org.apache.log4j.Logger;

/**
 *
 * @author basri baki
 */
public class BaseActionBean implements ActionBean{

    
    private ActionBeanContext context;
    private CoreJdbc cj = null;
    
    
    
    @Override
    public void setContext(ActionBeanContext context) {
         this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
       return context;
    }
    
     public Logger getLog(Class clz) {
        return Logger.getLogger(clz);
    }
     
      /**
     * request.getQueryString
     *
     * @param name
     * @return
     */
    public String getQueryString(String name) {
        String q = getContext().getRequest().getQueryString();
        String[] params = q.split("&");
        String param = null;
        String p[] = null;
        for (int x = 0; x < params.length; x++) {
            param = params[x];
            p = param.split("=");
            if (p != null) {
                if (p.length > 0) {
                    if (p[0].trim().equals(name)) {
                        return p[1];
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * initialize context
     *
     * @param request
     * @param response
     * @param servletContext
     * @return
     * @throws ServletException
     */
    public static ActionBeanContext getInstanceContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException {
        ActionBeanContext context = StripesFilter.getConfiguration().getActionBeanContextFactory().getContextInstance(request, response);
        context.setServletContext(servletContext);
        return context;
    }
    
     public Connection getConnection() {
        if(getContext() != null) {
            return ConnectionManager.getConnection(getContext().getRequest());
        }else {
//            ConnectionManager.start();
            return ConnectionManager.getConnectionMaster().getConnection();
        }
//        Connection con= null;
//        try{
//            if(getContext() != null) {
//                con =  ConnectionManager.getConnection(getContext().getRequest());
//            }else {
//                con = ConnectionManager.getConnectionMaster().getConnection();
//                try{
//                    con.commit();
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//            if(!con.getAutoCommit()){
//                con.commit();
//            }
//            con.setAutoCommit(true);
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//        return con;
    }
    
}
