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
        

    </head>
    <body>
        <select class="form-control" name="movement" id="movement">
            <option>Choose movement</option>
            <%
                String patID = (String) request.getSession().getAttribute("pid");
                String userName = (String) request.getSession().getAttribute("pName");
                String gtype = request.getParameter("game_type");
                FgctGraphConsolidationTableBean fgct = new FgctGraphConsolidationTableBean();
                fgct.setCon(ConnectionManager.getConnection());
                fgct.setPatientId(patID);

                List listM = fgct.getMovement(gtype);
                Iterator itM = listM.iterator();
                while (itM.hasNext()) {
                    String move = itM.next().toString();
            %>
            <option value="<%=move%>"><%=move%></option>
            <%}%>


        </select>
    </body>
</html>

        <script>
            $(document).ready(function () {
                $('#movement').on('change', function () {
                    var move_id = $('#movement').val();
                    $.ajax({
                        url: "graph_side.jsp",
                        data: {move_id: move_id,gtype:"<%=gtype%>"},
                        method: "POST",
                        success: function (data) {
                            $("#side_id").html(data);
                        }

                    });
                });

            });

        </script>
