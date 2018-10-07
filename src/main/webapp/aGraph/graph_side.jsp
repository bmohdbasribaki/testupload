<%-- 
    Document   : graph_movement
    Created on : Jul 27, 2018, 8:13:22 PM
    Author     : basri baki
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.mira.bean.graph.FgctGraphConsolidationTableBean"%>
<%@page import="com.jcore.CoreJdbc"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.mira.database.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

        
    </head>
    <body>
        <select class="form-control" name="side" id="sideid">
            <option>Choose side</option>
            <%

                String patID = (String) request.getSession().getAttribute("pid");
                String userName = (String) request.getSession().getAttribute("pName");
                String move = request.getParameter("move_id");
                String gtype = request.getParameter("gtype");
                System.out.println("move = " + move);
                System.out.println("gtype = " + gtype);
                FgctGraphConsolidationTableBean fgct = new FgctGraphConsolidationTableBean();
                fgct.setCon(ConnectionManager.getConnection());
                fgct.setPatientId(patID);

                List listM = fgct.getSideAction(gtype,move);
                Iterator itM = listM.iterator();
                while (itM.hasNext()) {
                    String side = itM.next().toString();
            %>
            <option value="<%=side%>"><%=side%></option>
            <%}%>


        </select>
    </body>
</html>
