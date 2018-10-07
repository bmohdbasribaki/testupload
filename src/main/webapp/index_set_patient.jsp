<%-- 
    Document   : index_set_patient
    Created on : Aug 20, 2018, 8:24:47 AM
    Author     : basri baki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%

        String name = request.getParameter("name");
        System.out.println("name = " + name);
    %>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $(document).ready(function () {

//            var name = $('#pid').val();
            var name = "1985";
//            alert(name);
            $.ajax({
                url: "SetPatientId",
                data: {name: name},
                method: "POST",
                success:function(data){
                    alert(data);
                    window.location = data.url;
                }
            });


        });

    </script>

    <body>

        <form >
            <div class="row gutter">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">



                    <input type="text"  id="pid" class="" value="<%=name%>" /><%=name%>




                </div>
            </div>
        </form>
    </body>
</html>
