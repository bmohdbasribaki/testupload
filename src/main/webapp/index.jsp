

<%@page import="java.sql.ResultSet"%>
<%@page import="com.jcore.CoreJdbc"%>
<%@page import="com.jcore.CoreUtil"%>
<%@page import="com.mira.database.ConnectionManager"%>
<%
    String inPath = request.getContextPath();
    String inBasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + inPath + "/";
//    ConnectionManager.getConnection();
    String result = CoreUtil.toString(request.getAttribute("result"));
    String prevUrl = request.getHeader("Referer");
    if(!(prevUrl == null)){
    if (!prevUrl.contains("MiraProject")) {
        session.setAttribute("previousUrl", prevUrl);
    }}

    System.out.println("prevUrl = " + prevUrl);
    
    String prePage = (String) request.getSession().getAttribute("previousUrl");
    String name = request.getParameter("name");
    System.out.println("name = " + name);

    if (!(name == null)) {
        CoreJdbc cj = new CoreJdbc(ConnectionManager.getConnection());
        String sql = "Select distinct(fgct_patient_name) from fgct_graph_consolidation_table where fgct_patient_id = '" + name + "'";
        System.out.println("sql = " + sql);
        ResultSet rs = cj.query(sql);
        String patientName = "";
        if (rs.next()) {
            patientName = rs.getString("fgct_patient_name");

        }
        System.out.println("patientName = " + patientName);
        session.setAttribute("pid", name);
        session.setAttribute("pName", patientName);

        cj.close();
    }

    String user = (String) request.getSession().getAttribute("pid");
    String userName = (String) request.getSession().getAttribute("pName");

    session.setAttribute("fmlcId", null);
    session.setAttribute("job", null);
    session.setAttribute("jarr", null);
    session.setAttribute("sidet", null);
    session.setAttribute("gamet", null);

    String status = "SUCCESS";
    if (result.equals("")) {
        status = "";
        result = "";
    } else if (!result.equals("SUCCESS")) {
        status = "FAILURE";
    } else {
        status = "SUCCESS";
        result = "";
    }
%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="description" content="Mira-Health Care" />
        <meta name="author" content="Srinu Basava" />
        <link rel="shortcut icon" href="<%=inBasePath%>img/healtIco.ico">
        <title>Mira - Health Project</title>

        <!-- Bootstrap CSS -->
        <link href="<%=inBasePath%>css/bootstrap.min.css" media="screen" rel="stylesheet" />


        <!-- Main CSS -->
        <link href="<%=inBasePath%>css/main.css" rel="stylesheet" media="screen" />

        <!-- Ion Icons -->
        <link href="<%=inBasePath%>fonts/icomoon/icomoon.css" rel="stylesheet" />

        <!-- C3 CSS -->
        <link href="<%=inBasePath%>css/c3/c3.css" rel="stylesheet" rel="stylesheet" />

        <!-- Circliful CSS -->
        <link href="<%=inBasePath%>css/circliful/circliful.css" rel="stylesheet" />

        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>

            var statusJ = "<%=status%>";
//            alert(statusJ);
            if (statusJ == 'SUCCESS') {

                $(function ()
                {
                    $("#dialog-message").dialog({
                        modal: true,
                        buttons: {
                            Ok: function () {
                                $(this).dialog("close");
                            }
                        }
                    });
                });
            }

        </script>


        <!--        <script>
                    $(document).ready(function () {
                       
        //                    var name = $('#gType').val();
                            var name = "1985";
                            $.ajax({
                                url: "SetPatientId",
                                data: {name: name},
                                method: "POST",
                                success: function (data) {
                                    alert(name);
                                }
        
                            });
                        
        
                    });
        
                </script>-->

    </head>

    <body>

        <!-- Header starts -->
        <header>

            <!-- Logo starts -->
            <a href="<%=inBasePath%>index.jsp" class="logo">
                <img src="<%=inBasePath%>img/miraHealthCare.png" alt="Mira Health-Care" />
            </a>
            <!-- Logo ends -->
            <ul id="header-actions" class="clearfix">
                <!-- Header actions starts -->
                <li class="list-box user-admin dropdown">
                    <div class="admin-details">
                        <div class="name"><%=user%></div>
                        <div class="name">Current User : <%=userName%></div>
                    </div>
                    <a id="drop4" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-account_circle"></i>
                    </a>
                </li>
            </ul>

        </header>
        <!-- Header ends -->

        <!-- Container fluid Starts -->
        <div class="container-fluid">

            <!-- Navbar starts -->
            <nav class="navbar navbar-default">
                <div class="navbar-header">
                    <span class="navbar-text">Menu</span>
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#collapse-navbar" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="form-group">
                    <a href="<%=prePage%>" class="btn btn-primary btnPrevious pull-right">Back</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <%@include file="mMenu/menuTop.jsp" %>
                <script>document.getElementById("dropDownIndex").className = "dropdown active";</script>
            </nav>
            <!-- Navbar ends -->

            <!-- Dashboard wrapper starts -->
            <div class="dashboard-wrapper">

                <!-- Top bar starts -->
                <div class="top-bar clearfix">
                    <div class="row gutter">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <div class="page-title">
                                <h4>Dashboard</h4>
                            </div>
                        </div>

                    </div>
                </div>
                <!-- Top bar ends -->

                <!-- Main container starts -->
                <div class="main-container">

                    <!-- Row starts -->
                    <div class="row gutter">
                        <div class="col-lg-3 col-md-3 col-sm-6">
                            <div class="mini-widget">

                            </div>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-6">
                            <div class="mini-widget red">

                            </div>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-6">
                            <div class="mini-widget grey">

                            </div>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-6">
                            <div class="mini-widget grey">

                            </div>
                        </div>
                    </div>

                    <form method="POST" action="upload" enctype="multipart/form-data">
                        <div class="row gutter">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">


                                <div class="card">
                                    <div class="card-header">File Browser</div>
                                    <div class="card-body">
                                        <label class="file">

                                            <input type="file"  name="file" id="file" class=""  accept=".xlsx,.xlsm"/></br>
                                            <input type="submit" value="Upload" name="upload" id="upload"/>
                                        </label>
                                    </div>
                                </div>


                            </div>
                        </div>
                    </form>
                    <div class="row gutter">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="panel panel-blue">
                                <div class="panel-body">
                                    <div class="col-lg-7 col-md-12 col-sm-12 col-xs-12">


                                        <img src="<%=inBasePath%>img/dataformat.PNG" alt="Italian Trulli">

                                    </div>

                                    <div class="col-lg-5 col-md-12 col-sm-12 col-xs-12">
                                        <ul class="message-wrapper">
                                            <li class="in">
                                                <span class="empty-avatar blue">1</span>
                                                <div class="message">
                                                    <span class="arrow"></span>
                                                    <a href="#" class="name">File format</a>
                                                    <!--<span class="date-time">Jan 18th, 2017</span>-->
                                                    <div class="body">
                                                        Please select excel file in the format of extention <span>.xlsx or .xlms</span> only.This system will not accept  <span>CSV</span> format."
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="out">
                                                <span class="empty-avatar green">2</span>
                                                <div class="message">
                                                    <span class="arrow"></span>
                                                    <a href="#" class="name">Column title</a>
                                                    <!--<span class="date-time"></span>-->
                                                    <div class="body">
                                                        Data must be starts at the first row without the column title.
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="in">
                                                <span class="empty-avatar red">3</span>
                                                <div class="message">
                                                    <span class="arrow"></span>
                                                    <a href="#" class="name">Others</a>
                                                    <!--<span class="date-time">Jan 10th, 2017</span>-->
                                                    <div class="body">
                                                        Do not leave the first column data empty or system will throw an error message.
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>

                    <!--                    <form >
                                            <div class="row gutter">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    
                    
                                                    <div class="card">
                                                        <div class="card-header">Set patient id</div>
                                                        <div class="card-body">
                                                            <label class="file">
                                                                <input type="text"  name="pid" id="" class="" value="<%=name%>"  placeholder=""/>
                                                                <input type="submit"  name="Save" />
                    
                                                            </label>
                                                        </div>
                                                    </div>
                    
                    
                                                </div>
                                            </div>
                                        </form>-->

                    <%
                        if (status.equalsIgnoreCase("success")) {%>
                    <div  id = "dialog-message" title = "Upload complete"> 
                        <p>
                            <span class="ui-icon ui-icon-circle-check" style = "float:left; margin:0 7px 50px 0;">
                            </span> Your file have successfully uploaded to the Server.
                        </p>


                    </div>
                    <!-- Main container ends -->
                    <%}%>
                </div>
                <!-- Dashboard Wrapper End -->

            </div>
            <!-- Container fluid ends -->



            <!-- Footer Start -->
            <footer>
                Mira Project <span>2018</span>
            </footer>
            <!-- Footer end -->

            <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
            <!--<script src="<%=inBasePath%>js/jquery.js"></script>-->

            <!-- Include all compiled plugins (below), or include individual files as needed -->
            <script src="<%=inBasePath%>js/bootstrap.min.js"></script>


            jquery ScrollUp JS 
            <script src="<%=inBasePath%>js/scrollup/jquery.scrollUp.js"></script>



            Custom JS 
            <script src="<%=inBasePath%>js/custom.js"></script>		
    </body>
</html>