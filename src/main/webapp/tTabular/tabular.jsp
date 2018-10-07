
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mira.database.ConnectionManager"%>
<%@page import="com.jcore.CoreJdbc"%>
<%@page import="com.mira.entity.FgctGraphConsolidationTable"%>
<%@page import="java.util.List"%>
<%@page import="com.mira.bean.graph.FgctGraphConsolidationTableBean"%>
<%
    String inPath = request.getContextPath();
    String inBasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + inPath + "/";
    
     String prevUrl = request.getHeader("Referer");
    if(!(prevUrl == null)){
    if (!prevUrl.contains("MiraProject")) {
        session.setAttribute("previousUrl", prevUrl);
    }}

    
    String prePage = (String) request.getSession().getAttribute("previousUrl");
    String name = request.getParameter("name");
    
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
    
    String pid = (String) request.getSession().getAttribute("pid");
    String userName = (String) request.getSession().getAttribute("pName");
   
    session.setAttribute("fmlcId", null);
    session.setAttribute("job", null);
    session.setAttribute("jarr", null);
    session.setAttribute("sidet", null);
    session.setAttribute("gamet", null);

    System.out.println("pid tabular = " + pid);
%>
<!DOCTYPE html>
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
        <link href="<%=inBasePath%>css/bootstrap.min.css" rel="stylesheet" media="screen" />

        <!-- Main CSS -->
        <link href="<%=inBasePath%>css/main.css" rel="stylesheet" media="screen" />

        <!-- Ion Icons -->
        <link href="<%=inBasePath%>fonts/icomoon/icomoon.css" rel="stylesheet" />

        <!-- Data Tables -->
        <link rel="stylesheet" href="<%=inBasePath%>css/datatables/dataTables.bs.min.css">
        <link rel="stylesheet" href="<%=inBasePath%>css/datatables/autoFill.bs.min.css">
        <link rel="stylesheet" href="<%=inBasePath%>css/datatables/fixedHeader.bs.css">


    </head>

    <body>

        <!-- Header starts -->
        <header>

            <!-- Logo starts -->
            <!-- Logo starts -->
            <a href="<%=inBasePath%>index.jsp" class="logo">
                <img src="<%=inBasePath%>img/miraHealthCare.png" alt="Mira Health-Care" />
            </a>
            <!-- Logo ends -->

            <!-- Header actions starts -->

            <!-- Header actions ends -->
            <ul id="header-actions" class="clearfix">
                <!-- Header actions starts -->
                <li class="list-box user-admin dropdown">
                    <div class="admin-details">
                        <div class="name"><%=pid%></div>
                        <div class="name">Current User : <%=userName%></div>
                    </div>
                    <a id="drop4" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-account_circle"></i>
                    </a>
                </li>
            </ul>
        </header>
        <!-- Header ends -->

        <!-- Container fluid starts -->
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
                <div class="collapse navbar-collapse" id="collapse-navbar">
                    <ul class="nav navbar-nav">

<!--                        <li  id="dropDownIndex">
                            <a href="<%=inBasePath%>index.jsp"><i class="icon-account_circle"></i>Home</a>

                        </li>-->
                        <li class="dropdown" id="dropDownTabular">
                            <a href="<%=inBasePath%>tTabular/tabular.jsp" class="dropdown-toggle" ><i class="icon-subtitles"></i>Tabular Table</a>
                            <!--                            <ul class="dropdown-menu">
                                                            <li>
                                                                <a href='<%=inBasePath%>tTabular/tabular.jsp'>Table</a>
                                                            </li>
                            
                                                        </ul>-->
                        </li>
                        <li class="dropdown" id="dropDownGraph">
                            <a href="<%=inBasePath%>aGraph/graph.jsp" ><i class="icon-terrain"></i>Graph </a>
                            <!--                            <ul class="dropdown-menu">
                                                            <li>
                                                                <a href='<%=inBasePath%>aGraph/graph.jsp'>Graph</a>
                                                            </li>
                            
                            
                                                        </ul>-->
                        </li>

                        <!--                        <li class="dropdown" id="dropDownMachine">
                                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="glyphicon glyphicon-tasks"></i>Machine Language <span class="caret"></span></a>
                                                    <ul class="dropdown-menu">
                                                        <li>
                                                            <a href='<%=inBasePath%>mMachineLearning/machineLearning.jsp'>Table</a>
                                                        </li>
                        
                                                    </ul>
                                                </li>-->

                    </ul>
                </div>
                <script>document.getElementById("dropDownTabular").className = "dropdown active";</script>
            </nav>

            <!-- Dashboard wrapper starts -->
            <div class="dashboard-wrapper">

                <!-- Top bar starts -->
                <div class="top-bar clearfix">
                    <div class="row gutter">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <div class="page-title">
                                <h4>Data Tables</h4>
                            </div>
                        </div>

                    </div>
                </div>
                <!-- Top bar ends -->

                <!-- Main container starts -->
                <div class="main-container">


                    <!-- Row ends -->


                    <!-- Row ends -->
                    <jsp:include page="tabularView" />
                    <!-- Row starts -->
                    <div class="row gutter">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="panel panel-blue">
                                <div class="panel-heading">
                                    <h4>Auto Fill DataTable</h4>
                                </div>
                                <div class="panel-body">
                                    <div class="table-responsive">
                                        <table id="autoFill" class="table table-striped table-bordered no-margin" cellspacing="0" width="100%">
                                            <thead>
                                                <tr>
                                                    <th>Reference No</th>
                                                    <th>Patient Id</th>
                                                    <th>Patient Name</th>
                                                    <th>Session Id</th>
                                                    <th>Session Date</th>
                                                    <th>Game</th>
                                                    <th>Movement</th>
                                                    <th>Side</th>
                                                    <th>Average Acceleration</th>
                                                    <th>Average Deceleration</th>
                                                    <th>Average Percentage</th>
                                                    <th>Average Speed</th>
                                                    <th>Difficulty</th>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <th>Reference No</th>
                                                    <th>Patient Id</th>
                                                    <th>Patient Name</th>
                                                    <th>Session Id</th>
                                                    <th>Session Date</th>
                                                    <th>Game</th>
                                                    <th>Movement</th>
                                                    <th>Side</th>
                                                    <th>Average Acceleration</th>
                                                    <th>Average Deceleration</th>
                                                    <th>Average Percentage</th>
                                                    <th>Average Speed</th>
                                                    <th>Difficulty</th>
                                                </tr>
                                            </tfoot>
                                            <tbody>
                                                <%
                                                    FgctGraphConsolidationTableBean fgct = new FgctGraphConsolidationTableBean();
                                                    List<FgctGraphConsolidationTable> ls = fgct.getDataList(pid);
                                                    int i = 0;
                                                    while (i < ls.size()) {

                                                %>
                                                <tr>
                                                    <td><%=ls.get(i).getFgct_reference_no()%></td>
                                                    <td><%=ls.get(i).getFgct_patient_id()%></td>
                                                    <td><%=ls.get(i).getFgct_patient_name()%></td>
                                                    <td><%=ls.get(i).getFgct_session_id()%></td>
                                                    <td><%=ls.get(i).getFgct_date()%></td>
                                                    <td><%=ls.get(i).getFgct_game()%></td>
                                                    <td><%=ls.get(i).getFgct_movement()%></td>
                                                    <td><%=ls.get(i).getFgct_side()%></td>
                                                    <td><%=ls.get(i).getFgct_avg_acc()%></td>
                                                    <td><%=ls.get(i).getFgct_avg_dec()%></td>
                                                    <td><%=ls.get(i).getFgct_avg_percentage()%></td>
                                                    <td><%=ls.get(i).getFgct_avg_speed()%></td>
                                                    <td><%=ls.get(i).getFgct_difficulty()%></td>
                                                </tr>
                                                <%i++;
                                                    }%>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Row ends -->



                </div>
                <!-- Main container ends -->

            </div>
            <!-- Dashboard wrapper ends -->

        </div>
        <!-- Container fluid ends -->

        <!-- Footer start -->
        <footer>
            Mira Project <span>2018</span>
        </footer>
        <!-- Footer ends -->

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="<%=inBasePath%>js/jquery.js"></script>

        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="<%=inBasePath%>js/bootstrap.min.js"></script>

        <!-- jquery ScrollUp JS -->
        <script src="<%=inBasePath%>js/scrollup/jquery.scrollUp.js"></script>

        <!-- Sparkline Graphs -->
        <script src="<%=inBasePath%>js/sparkline/sparkline.js"></script>
        <script src="<%=inBasePath%>js/sparkline/custom-sparkline.js"></script>

        <!-- DataBars JS -->
        <script src="<%=inBasePath%>js/databars/jquery.databar.js"></script>
        <script src="<%=inBasePath%>js/databars/custom-databars.js"></script>

        <!-- Data Tables -->
        <script src="<%=inBasePath%>js/datatables/dataTables.min.js"></script>
        <script src="<%=inBasePath%>js/datatables/dataTables.bootstrap.min.js"></script>
        <script src="<%=inBasePath%>js/datatables/autoFill.min.js"></script>
        <script src="<%=inBasePath%>js/datatables/autoFill.bootstrap.min.js"></script>
        <script src="<%=inBasePath%>js/datatables/fixedHeader.min.js"></script>
        <script src="<%=inBasePath%>js/datatables/custom-datatables.js"></script>

        <!-- Custom JS -->
        <script src="<%=inBasePath%>js/custom.js"></script>
    </body>
</html>