
<%@page import="java.util.Iterator"%>
<%@page import="com.jcore.CoreUtil"%>
<%@page import="com.mira.entity.FmlcMachineLearningScore"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.mira.database.ConnectionManager"%>
<%@page import="com.mira.bean.machineLearning.FmlcMachineLearningScoreBean"%>
<%@page import="com.mira.entity.FgctGraphConsolidationTable"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.mira.bean.graph.FgctGraphConsolidationTableBean"%>
<%
    String inPath = request.getContextPath();
    String inBasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + inPath + "/";
    String pid = (String) request.getSession().getAttribute("pid");
    System.out.println("pid tabular = " + pid);
    String userName = (String) request.getSession().getAttribute("pName");
    if (userName.isEmpty()) {
        userName = "No currrent user selected";
    }

    FmlcMachineLearningScoreBean fmlc = new FmlcMachineLearningScoreBean();
    fmlc.setCon(ConnectionManager.getConnection());
    fmlc.setPatientId(pid);
    fmlc.clearTableFmlc();
    fmlc.insertTableFmlc();
    Map<String, Object[]> map = fmlc.getFmlcGroupRef();


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

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="collapse-navbar">
                    <ul class="nav navbar-nav">

                        <li  id="dropDownIndex">
                            <a href="<%=inBasePath%>index.jsp"><i class="icon-account_circle"></i>Home</a>

                        </li>
                        <li class="dropdown" id="dropDownGraph">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="icon-terrain"></i>Graph <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href='<%=inBasePath%>aGraph/graph.jsp'>Graph</a>
                                </li>

                            </ul>
                        </li>
                        <li class="dropdown" id="dropDownTabular">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="icon-subtitles"></i>Tabular Table <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href='<%=inBasePath%>tTabular/tabular.jsp'>Table</a>
                                </li>

                            </ul>
                        </li>
                        <li class="dropdown" id="dropDownMachine">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="glyphicon glyphicon-tasks"></i>Machine Language <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href='<%=inBasePath%>mMachineLearning/machineLearning.jsp'>Data presentations and suggestions</a>
                                </li>

                            </ul>
                        </li>

                    </ul>
                </div>
                <script>document.getElementById("dropDownMachine").className = "dropdown active";</script>
            </nav>

            <!-- Dashboard wrapper starts -->
            <div class="dashboard-wrapper">

                <!-- Top bar starts -->
                <div class="top-bar clearfix">
                    <div class="row gutter">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <div class="page-title">
                                <h4>Machine Learning</h4>
                            </div>
                        </div>

                    </div>
                </div>
                <!-- Top bar ends -->

                <!-- Main container starts -->
                <div class="main-container">

                    <!-- Row starts -->
                    <%
                        Iterator<String> it2 = map.keySet().iterator();
                        while (it2.hasNext()) {
                            String subCode = it2.next();
                            Object[] o = map.get(subCode);

                    %>
                    <div class="row gutter">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="invoice">
                                <div class="panel" style="background-color: #F7F4F4">

                                    <div class="panel-body">

                                        <br />

                                        <!-- Row starts -->
                                        <div class="row gutter">
                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                <address>
                                                    <h4>Game : <%=o[0]%></h4>
                                                    <abbr>Movement : <%=o[1]%></abbr><br>
                                                    <abbr>Side : <%=o[2]%></abbr>

                                                </address>
                                            </div>

                                        </div>
                                        <!-- Row ends -->

                                        <br />

                                        <!-- Row starts -->
                                        <div class="row gutter">
                                            <div class="col-md-12">
                                                <div class="table-responsive">
                                                    <table class="table table-bordered">
                                                        <thead>
                                                            <tr>
                                                                <th style="width:10%">Difficulty</th>
                                                                <th style="width:10%">Number Sessions</th>
                                                                <th style="width:10%">Average Acceleration</th>
                                                                <th style="width:10%">Average Speed</th>
                                                                <th style="width:10%">Average Percentage</th>
                                                                <th style="width:10%">Total Score</th>
                                                                <th style="width:20%">Result</th>
                                                                <th style="width:20%">Suggestion</th>

                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <%
                                                                List<FmlcMachineLearningScore> ls = fmlc.getDataList(pid);
                                                                int j = 0;
                                                                String resultstMsg = "";
                                                                String suggMsg = "";
                                                                String kkk = subCode;
                                                                while (j < ls.size()) {
                                                                    if (kkk.equals(ls.get(j).getFmlcReference().toString())) {

                                                                        if (ls.get(j).getFmlcScorePass() == 1) {
                                                                            if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("easy")) {
                                                                                resultstMsg = "Pass.This patient is eligible to play in MEDIUM stage ";
                                                                            } else if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("medium")) {
                                                                                resultstMsg = "Pass.This patient is eligible to play in HARD stage ";
                                                                            } else {
                                                                                resultstMsg = "Pass.This patient has pass the HARD stage";
                                                                            }

                                                                            suggMsg = "Pass";
                                                                        } else {
                                                                            double minPass = 0;

                                                                            if (ls.get(j).getFmlcSide().equalsIgnoreCase("left")) {
                                                                                if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("easy")) {
                                                                                    if (ls.get(j).getFmlcTaaPass() == 0) {
                                                                                        minPass = 0.4684;
                                                                                    } else if (ls.get(j).getFmlcTapPass() == 0) {
                                                                                        minPass = 31.531;
                                                                                    } else if (ls.get(j).getFmlcTasPass() == 0) {
                                                                                        minPass = 0.0653;
                                                                                    }
                                                                                } else if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("medium")) {
                                                                                    if (ls.get(j).getFmlcTaaPass() == 0) {
                                                                                        minPass = 0.5364;
                                                                                    } else if (ls.get(j).getFmlcTapPass() == 0) {
                                                                                        minPass = 33.603;
                                                                                    } else if (ls.get(j).getFmlcTasPass() == 0) {
                                                                                        minPass = 0.0728;
                                                                                    }
                                                                                } else if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("hard")) {
                                                                                    if (ls.get(j).getFmlcTaaPass() == 0) {
                                                                                        minPass = 0.7441;
                                                                                    } else if (ls.get(j).getFmlcTapPass() == 0) {
                                                                                        minPass = 49.979;
                                                                                    } else if (ls.get(j).getFmlcTasPass() == 0) {
                                                                                        minPass = 0.0931;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("easy")) {
                                                                                    if (ls.get(j).getFmlcTaaPass() == 0) {
                                                                                        minPass = 0.6548;
                                                                                    } else if (ls.get(j).getFmlcTapPass() == 0) {
                                                                                        minPass = 25.425;
                                                                                    } else if (ls.get(j).getFmlcTasPass() == 0) {
                                                                                        minPass = 0.0903;
                                                                                    }
                                                                                } else if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("medium")) {
                                                                                    if (ls.get(j).getFmlcTaaPass() == 0) {
                                                                                        minPass = 0.638;
                                                                                    } else if (ls.get(j).getFmlcTapPass() == 0) {
                                                                                        minPass = 29.6729;
                                                                                    } else if (ls.get(j).getFmlcTasPass() == 0) {
                                                                                        minPass = 0.084;
                                                                                    }
                                                                                } else if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("hard")) {
                                                                                    if (ls.get(j).getFmlcTaaPass() == 0) {
                                                                                        minPass = 0.7861;
                                                                                    } else if (ls.get(j).getFmlcTapPass() == 0) {
                                                                                        minPass = 38.635;
                                                                                    } else if (ls.get(j).getFmlcTasPass() == 0) {
                                                                                        minPass = 0.0979;
                                                                                    }
                                                                                }
                                                                            }

                                                                            resultstMsg = "This patient score did not pass the minimum score pass .<br/> Min score pass 0.6";
                                                                            if (ls.get(j).getFmlcTaaPass() == 0) {
                                                                                suggMsg = "This patient needs to improve the ACCELERATION score."
                                                                                        + "</br> Current score = (" + CoreUtil.roundMath(CoreUtil.toDouble(ls.get(j).getFmlcTotalAvgAcc()), 2) + ")"
                                                                                        + "</br> Min score pass = " + minPass + "";
                                                                            } else if (ls.get(j).getFmlcTapPass() == 0) {
                                                                                suggMsg = "This patient needs to improve the AVERAGE score."
                                                                                        + "</br> Current score = (" + CoreUtil.roundMath(CoreUtil.toDouble(ls.get(j).getFmlcTotalAvgPercentage()), 2) + ")"
                                                                                        + "</br> Min score pass = (" + minPass + ")";
                                                                            } else if (ls.get(j).getFmlcTasPass() == 0) {
                                                                                suggMsg = "This patient needs to improve the SPEED score."
                                                                                        + "</br> Current score = (" + CoreUtil.roundMath(CoreUtil.toDouble(ls.get(j).getFmlcTotalAvgSpeed()), 2) + ")"
                                                                                        + "</br> Min score pass = (" + minPass + ")";
                                                                            } else {
                                                                                suggMsg = "PASS";
                                                                            }
                                                                        }

                                                                        if (ls.get(j).getFmlcSessionPass() == 0) {%>
                                                            <tr bgcolor="#F78975">
                                                                <td><%=ls.get(j).getFmlcDifficulty()%></td>
                                                                <td colspan="8">MORE SESSIONS NEEDED</td>
                                                            </tr>
                                                            <%
                                                            } else {
                                                            %>
                                                            <tr bgcolor="white">
                                                                <td><%=ls.get(j).getFmlcDifficulty()%></td>
                                                                <td><%=ls.get(j).getFmlcTotalSessionNo()%></td>
                                                                <td><%=CoreUtil.roundMath(CoreUtil.toDouble(ls.get(j).getFmlcTotalAvgAcc()), 2)%></td>
                                                                <td><%=CoreUtil.roundMath(CoreUtil.toDouble(ls.get(j).getFmlcTotalAvgSpeed()), 2)%></td>
                                                                <td><%=CoreUtil.roundMath(CoreUtil.toDouble(ls.get(j).getFmlcTotalAvgPercentage()), 2)%></td>
                                                                <td><%=CoreUtil.roundMath(CoreUtil.toDouble(ls.get(j).getFmlcTotalScore()), 2)%></td>
                                                                <td><%=resultstMsg%></td>
                                                                <td><%=suggMsg%></td>

                                                            </tr>
                                                            <%

                                                                        }
                                                                    }
                                                                    j++;

                                                                }%>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Row ends -->


                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <%
                        }%>
                    <!-- Row ends -->



                </div>
                <!-- Main container ends -->

            </div>
            <!-- Dashboard wrapper ends -->

        </div>
        <!-- Container fluid ends -->

        <!-- Footer start -->
        <footer>
            © BlueMoon <span>2013-2016</span>
        </footer>
        <!-- Footer ends -->

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="<%=inBasePath%>js/jquery.js"></script>

        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="<%=inBasePath%>js/bootstrap.min.js"></script>

        <!-- jquery ScrollUp JS -->
        <script src="<%=inBasePath%>js/scrollup/jquery.scrollUp.js"></script>


        <!-- Custom JS -->
        <script src="<%=inBasePath%>js/custom.js"></script>



    </body>
</html>