<%@page import="com.mira.entity.FmlcMachineLearningScore"%>
<%@page import="com.mira.bean.machineLearning.FmlcMachineLearningScoreBean"%>
<%@page import="com.jcore.CoreJdbc"%>
<%@page import="com.jcore.CoreUtil"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.mira.database.ConnectionManager"%>
<%--<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>--%>
<%@page import="com.mira.bean.graph.FgctGraphConsolidationProcessBean"%>
<%@page import="com.mira.bean.graph.FgctGraphConsolidationTableBean"%>
<%

    String patID = (String) request.getSession().getAttribute("pid");
    String userName = (String) request.getSession().getAttribute("pName");

    String inPath = request.getContextPath();
    String inBasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + inPath + "/";
    FgctGraphConsolidationTableBean fgct = new FgctGraphConsolidationTableBean();
    Connection con = ConnectionManager.getConnection();
    fgct.setCon(con);
    fgct.setPatientId(patID);

//    System.out.println(patID);
    String diag = (String) request.getSession().getAttribute("diagnosis");
    String gamet = (String) request.getSession().getAttribute("game");
    String movement = (String) request.getSession().getAttribute("movement");
    String sidet = (String) request.getSession().getAttribute("sidet");
    String fmlcId = (String) request.getSession().getAttribute("fmlcid");
    String jsonob = (String) request.getSession().getAttribute("job");
    String jsonarr = (String) request.getSession().getAttribute("jarr");
    String weakness = (String) request.getSession().getAttribute("weakness");
    if (weakness == null) {
        weakness = "0";
    }
    JSONObject job = null;
    JSONObject jarOb = null;
    JSONArray jar = null;

    if (jsonob == null) {

    } else {
        job = new JSONObject(jsonob);
    }

    if (jsonarr == null) {

    } else {
        jarOb = new JSONObject(jsonarr);
        jar = jarOb.getJSONArray("data");
    }

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
        <!-- Bootstrap CSS -->
        <link href="<%=inBasePath%>css/bootstrap.min.css" media="screen" rel="stylesheet" />

        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">

        <!-- Main CSS -->
        <link href="<%=inBasePath%>css/main.css" rel="stylesheet" media="screen" />

        <!-- Ion Icons -->
        <link href="<%=inBasePath%>fonts/icomoon/icomoon.css" rel="stylesheet" />

        <!-- Bar Indicator CSS -->
        <link href="<%=inBasePath%>js/barIndicator/barIndicator.css" rel="stylesheet" />


        <!-- C3 CSS -->
        <link href="<%=inBasePath%>css/c3/c3.css" rel="stylesheet" />

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>


        <style>
            #chartdiv {
                width		: 100%;
                height		: 450px;
                font-size	: 11px;
            }			

            input[type="date"]:before {
                content: attr(placeholder) !important;
                color: #aaa;
                margin-right: 0.5em;
            }
            input[type="date"]:focus:before,
            input[type="date"]:valid:before {
                content: "";
            }
        </style>


        <script>
            $(document).ready(function () {
                $('#gType').on('change', function () {
                    var game_type = $('#gType').val();
                    $.ajax({
                        url: "graph_movement.jsp",
                        data: {game_type: game_type},
                        method: "POST",
                        success: function (data) {
                            $('#movement_id').html(data);
                        }

                    });
                });

            });

        </script>

        <script>
            $(document).ready(function () {
                $('#ROMSuggestion').submit(function () {
                    $.ajax({
                        url: '../FmlcMachineLearningScoreServlet',
                        type: 'POST',
                        dataType: 'json',
                        data: $('#ROMSuggestion').serialize(),
                        success: function (data) {
//                            if (data.isValid) {
//                                        console.log(data.jarr)
                            $('#rslRom').html(data.rom);
                            $('#rslpoints').html(data.points);
                            $('#rslrep').html(data.rep);
                            $('#rsldiff').html(data.diff);
//                                        $('#displayName').slideDown(500);
//                            }
                        }
                    });
                    return false;

                });



            });
        </script>

    </head>

    <body>

        <!-- Loading starts -->
        <div id="loading-wrapper">
            <div id="loader">
                <div class="line1"></div>
                <div class="line2"></div>
                <div class="line3"></div>
                <div class="line4"></div>
                <div class="line5"></div>
                <div class="line6"></div>
            </div>
        </div>
        <!-- Loading ends -->

        <!-- Header starts -->
        <header>

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
                        <div class="name"><%=patID%></div>
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

                    </ul>
                </div>
                <script>document.getElementById("dropDownGraph").className = "dropdown active";
                </script>
            </nav>
            <!-- Navbar ends -->

            <!-- Dashboard wrapper starts -->
            <div class="dashboard-wrapper">

                <!-- Top bar starts -->
                <div class="top-bar clearfix">
                    <div class="row gutter">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <div class="page-title">
                                <h4>Graphs</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Top bar ends -->

                <!-- Main container starts -->

                <div class="main-container">

                    <!-- Row starts -->
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="panel sales-overview">
                                <div class="panel-heading">


                                    <form id="filterSearch" method="POST" action="../FgctGraphConsolidationProcessServlet">

                                        <input type="hidden" name="patientId" value="<%=patID%>"/>
                                        <div class="row gutter">
                                            <div class="col-lg-2 col-md-3 col-sm-4 col-xs-12">
                                                <select class="form-control" name="gameType"  id="gType">
                                                    <option value="">Choose game type</option>
                                                    <%
                                                        List list = fgct.getGameType();
                                                        Iterator it = list.iterator();
                                                        while (it.hasNext()) {
                                                            String game = it.next().toString();
                                                    %>
                                                    <option value="<%=game%>"><%=game%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-4 col-xs-12" id="movement_id">
                                                <select class="form-control" name="movement" id="move_id">
                                                    <option value="">Choose movement</option>
                                                </select>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-4 col-xs-12" id="side_id">
                                                <select class="form-control" name="side" id="side">
                                                    <option value="">Choose side</option>

                                                </select>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-4 col-xs-12" id="side_id">
                                                <select class="form-control" name="weakness" id="weakness">
                                                    <option value="">Choose Weakness</option>
                                                    <option value="1">Yes</option>
                                                    <option value="0">No</option>

                                                </select>
                                            </div>
                                            <div  id="filter-panel" class="collapse filter-panel col-lg-2 col-md-3 col-sm-4 col-xs-12">
                                                <div class="form-group"> 
                                                    <input class="form-control" type="date" name="fromDate" id="datepicker" placeholder="From"/>
                                                </div>
                                                <div class="form-group">  
                                                    <input class="form-control" type="date" name="toDate" id="datepickerto" placeholder="To"/>
                                                </div>
                                            </div>
                                            <div class="form-inline pull-right">
                                                <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#filter-panel">
                                                    <span class="glyphicon glyphicon-cog"></span> Advanced Search
                                                </button>
                                                <input type="submit" class="btn btn-info"/>
                                            </div>

                                        </div>
                                    </form>



                                </div>

                                <div class="panel-body">
                                    <div class="col-lg-4 col-md-12 col-sm-10 col-xs-10">
                                        <p class="text-info">Patient Name: <%=userName%></p>
                                        <p class="text-info">Patient Id: <%=patID%></p>
                                        <p class="text-info">Diagnosis: <%=diag%></p>

                                    </div>
                                    <div class="col-lg-4 col-md-12 col-sm-10 col-xs-10">
                                        <p class="text-info">Game: <%=gamet%></p>
                                        <p class="text-info">Movement: <%=movement%></p>
                                        <p class="text-info">Side: <%=sidet%></p>

                                    </div>
                                    <div class="col-lg-4 col-md-12 col-sm-10 col-xs-10">
                                        <p class="text-info">Weakness <%=weakness%></p>

                                    </div>
                                    <div class="row">
                                        <div class="col-lg-12 col-md-12 col-sm-10 col-xs-10">
                                            <div id="chartdiv" class="chart-height1"></div>
                                        </div>

                                    </div>
                                    <br>
                                    <br>

                                    <div class="col-lg-4 col-md-12 col-sm-10 col-xs-10">
                                        <div class="table-responsive">
                                            <table id="tResult" class="table  table-bordered table-striped" cellspacing="0" width="100%">
                                                <tbody>
                                                    <tr>
                                                        <th colspan="2">Calculated ROM</th>

                                                        <th id="rslRom" ></th>
                                                    </tr>
                                                    <tr>
                                                        <td rowspan="3">Minimum value to be achieved</td>
                                                        <td>Points</td>
                                                        <td id="rslpoints"></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Repetitions</td>
                                                        <td id="rslrep"></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Difficulty</td>
                                                        <td id="rsldiff"></td>
                                                    </tr>

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>


                                    <form id="ROMSuggestion">
                                        <div class="col-lg-3 col-md-12 col-sm-10 col-xs-10">
                                            <div class="table-responsive">
                                                <table id="autoFill" class="table no-margin" cellspacing="0" width="100%">
                                                    <tbody>
                                                        <tr>
                                                            <th></th>
                                                            <th>Please insert all values into box</th>

                                                        </tr>
                                                        <tr>
                                                            <td>Target Rom</td>
                                                            <td><input type="text" name="rom"/></td>

                                                        </tr>
                                                        <tr>
                                                            <td>Max Points</td>
                                                            <td><input type="text" name="maxpoint"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Min Points</td>
                                                            <td><input type="text" name="minpoint"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Max Difficulty</td>
                                                            <td><select name="maxdif">
                                                                    <option value="1">EASY</option>
                                                                    <option value="2">MEDIUM</option>
                                                                    <option value="3">HARD</option>

                                                                </select></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Max Reps</td>
                                                            <td><input type="text" name="maxrep"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Min Reps</td>
                                                            <td><input type="text" name="minrep"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Is it a weak side</td>
                                                            <td><input type="radio" name="isweak" value="1"/>Yes&nbsp;&nbsp;&nbsp;<input type="radio" name="isweak" value="0"/>No</td>
                                                        </tr>
                                                        <tr>
                                                            <td></td>
                                                            <td><input type="submit" name="submit"/></td>
                                                        </tr>

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </form>


                                    <div class="col-lg-4 col-md-12 col-sm-10 col-xs-10 pull-right">
                                        <div class="panel-body">
                                            <ul class="chat-list">
                                                <li class="out">
                                                    <div class="chat-img">
                                                        <img alt="Avtar" src="<%=inBasePath%>img/doctor.jpg">
                                                    </div>
                                                    <div class="chat-body">
                                                        <div class="chat-message">
                                                            <h5>Hi im your physician support</h5>
                                                            <%
                                                                FmlcMachineLearningScoreBean fmlc = new FmlcMachineLearningScoreBean();
                                                                fmlc.setCon(con);
                                                                fmlc.setPatientId(patID);
                                                                fmlc.clearTableFmlc();
                                                                fmlc.insertTableFmlc(weakness);
                                                                System.out.println("fmlcId = " + fmlcId);
                                                                List<FmlcMachineLearningScore> ls = fmlc.getDataListReference(fmlcId);
                                                                int j = 0;
                                                                String resultstMsg = "";
                                                                String suggMsg = "";
                                                                System.out.println("ls.size = " + ls.size());
                                                                while (j < ls.size()) {
                                                                    if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("easy")) {
                                                            %>
                                                            <br><p> <b>EASY STAGE</b></p>
                                                            <%
                                                            } else if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("medium")) {
                                                            %>
                                                            <br><p > <b>MEDIUM STAGE</b></p>
                                                            <%
                                                            } else if (ls.get(j).getFmlcDifficulty().equalsIgnoreCase("hard")) {
                                                            %>
                                                            <br><p > <b>HARD STAGE</b></p>
                                                            <%
                                                            } else {
                                                            %>
                                                            <br><p> <b>null</b></p>
                                                            <%
                                                                }
                                                                if (ls.get(j).getFmlcSessionPass() == 0 || ls.get(j).getFmlcSessionPass() == 10 || ls.get(j).getFmlcSessionPass() == 20 || ls.get(j).getFmlcSessionPass() == 30) {
                                                            %>
                                                            <p> <%=ls.get(j).getFmlcSessionMsg()%></p>
                                                            <%} else {%>
                                                            <p><%=ls.get(j).getFmlcScoreMsg()%></p>
                                                            <p><%=ls.get(j).getFmlcSuggestMsg()%></p>
                                                            <%}
                                                                    j++;

                                                                }
                                                            %>
                                                        </div>
                                                    </div>
                                                </li>

                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                </div>
                <!-- Main container ends -->

            </div>
            <!-- Dashboard wrapper ends -->

        </div>
        <!-- Container fluid ends -->

        <!-- Footer start -->
        <footer>
            Â© BlueMoon <span>2013-2016</span>
        </footer>
        <!-- Footer ends -->



        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!--        <script src="<%=inBasePath%>js/jquery.js"></script>

         jQuery UI 
        <script src="<%=inBasePath%>js/jquery-ui.min.js"></script>-->


        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="<%=inBasePath%>js/bootstrap.min.js"></script>

        <!-- Flot Charts -->
        <script src="<%=inBasePath%>js/flot/jquery.flot.min.js"></script>
        <script src="<%=inBasePath%>js/flot/jquery.flot.tooltip.min.js"></script>
        <script src="<%=inBasePath%>js/flot/jquery.flot.resize.min.js"></script>
        <script src="<%=inBasePath%>js/flot/jquery.flot.time.min.js"></script>

        <!-- Custom JS -->
        <script src="<%=inBasePath%>js/flot/custom/area10.js"></script>

        <!-- D3 JS -->
        <script src="<%=inBasePath%>js/d3/d3.v3.min.js"></script>

        <!-- C3 Graphs -->
        <script src="<%=inBasePath%>js/c3/c3.min.js"></script>
        <script src="<%=inBasePath%>js/c3/custom-pie-four.js"></script>
        <script src="<%=inBasePath%>js/c3/custom-bar-one.js"></script>



        <!--        Resources -->
        <script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
        <script src="https://www.amcharts.com/lib/3/serial.js"></script>
        <script src="https://www.amcharts.com/lib/3/themes/light.js"></script>
        <script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
        <link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" />

        <!--        Custom JS -->
        <script src="<%=inBasePath%>js/custom.js"></script>

        <script type="text/javascript">
                    //Datepicker
                    $(function () {
                        $("#datepicker").datepicker();
                    });
        </script>

        <script>$(function () {
                $("#datepickerto").datepicker();
            });
        </script>
        <script>


            var chartData = generateChartData();
            var aaa = <%=job%>;
//            var bbb = ;
//            console.log(bbb);
            var chart = AmCharts.makeChart("chartdiv", {
                "type": "serial",
                "theme": "light",
                "legend": {
                    "useGraphSettings": true
                },
                "dataProvider": chartData,
                "synchronizeGrid": true,
                "valueAxes": [{
                        "id": "v1",
                        "axisColor": "#2D29F3",
                        "axisThickness": 2,
                        "axisAlpha": 1,
                        "position": "left"
                    }, {
                        "id": "v2",
                        "axisColor": "#F8922D",
                        "axisThickness": 2,
                        "axisAlpha": 1,
                        //                        "offset": 40,
                        "position": "right"
                    }
                ],
                "graphs": [{
                        "valueAxis": "v1",
                        "lineColor": "#2D29F3",
                        "bullet": "round",
                        "bulletBorderThickness": 1,
                        "hideBulletsCount": 30,
                        "title": "Average Acceleration",
                        "valueField": "avgAcc",
                        "fillAlphas": 0
                    }, {
                        "valueAxis": "v1",
                        "lineColor": "#ff0000",
                        "bullet": "square",
                        "bulletBorderThickness": 1,
                        "hideBulletsCount": 30,
                        "title": "Average Deceleration",
                        "valueField": "avgDec",
                        "fillAlphas": 0
                    }, {
                        "valueAxis": "v1",
                        "lineColor": "#008000",
                        "bullet": "triangleUp",
                        "bulletBorderThickness": 2,
                        "hideBulletsCount": 30,
                        "title": "Average Distance",
                        "valueField": "avgDis",
                        "fillAlphas": 0
                    }, {
                        "valueAxis": "v2",      
                        "lineColor": "#ff00ff",
                        //                        "bullet": "triangleUp",
                        "bulletBorderThickness": 3,
                        "hideBulletsCount": 30,
                        "dashLength": 10,
                        "title": "Average Percentage",
                        "valueField": "avgPrct",
                        "fillAlphas": 0
                    }, {
                        "valueAxis": "v2",
                        "lineColor": "#090909",
                        //                        "bullet": "triangleUp",
                        "bulletBorderThickness": 2,
                        "hideBulletsCount": 30,
                        "dashLength": 3,
                        "title": "Repetations",
                        "valueField": "rep",
                        "fillAlphas": 0
                    }],
                "chartScrollbar": {},
                "chartCursor": {
                    "cursorPosition": "mouse"
                },
                "categoryField": "date",
                "categoryAxis": {
                    //                    "startOnAxis": true,
                    //                    "parseDates": true,
                    "axisColor": "#DADADA",
                    "minorGridEnabled": true,
                    "guides": [{
                            category: aaa.MINEASY,
                            toCategory: aaa.MAXEASY,
                            lineColor: "#CC0000",
                            lineAlpha: 1,
                            fillAlpha: 0.2,
                            fillColor: "#CC0000",
                            dashLength: 2,
                            inside: true,
                            labelRotation: 90,
                            label: "EASY"
                        },
                        {
                            category: aaa.MINMEDIUM,
                            toCategory: aaa.MAXMEDIUM,
                            lineColor: "#F9DD14",
                            lineAlpha: 1,
                            fillAlpha: 0.2,
                            fillColor: "#F9DD14",
                            dashLength: 2,
                            inside: true,
                            labelRotation: 90,
                            label: "MEDIUM"
                        },
                        {
                            category: aaa.MINHARD,
                            toCategory: aaa.MAXHARD,
                            lineColor: "#0E7699",
                            lineAlpha: 1,
                            fillAlpha: 0.2,
                            fillColor: "#83EBF8",
                            dashLength: 2,
                            inside: true,
                            labelRotation: 90,
                            label: "HARD"
                        }]
                }, "export": {
                    "enabled": true,
                    "position": "bottom-right"
                }
            });

            chart.addListener("dataUpdated", zoomChart);
            zoomChart();


            // generate some random data, quite different range
            function generateChartData() {
                return <%=jar%>;
            }


            function zoomChart() {
                chart.zoomToIndexes(chart.dataProvider.length - 20, chart.dataProvider.length - 1);
            }

        </script>
        <%
            CoreJdbc.closeConnection(con);
        %>
    </body>
</html>