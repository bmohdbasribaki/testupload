<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->


<div class="collapse navbar-collapse" id="collapse-navbar">
    <ul class="nav navbar-nav">

        <li  id="dropDownIndex">
            <a href="<%=inBasePath%>index.jsp"><i class="icon-account_circle"></i>Home</a>

        </li>
        
        <li class="dropdown" id="dropDownTabular">
            <a href="<%=inBasePath%>tTabular/tabular.jsp" class="activeColour"><i class="icon-subtitles"></i>Tabular Table </a>
<!--            <ul class="dropdown-menu">
                <li>-->
                    <!--<a href='<%=inBasePath%>tTabular/tabular.jsp'>Table</a>-->
                <!--</li>-->

            <!--</ul>-->
        </li>
        <li class="dropdown" id="dropDownGraph">
            <a href="<%=inBasePath%>aGraph/graph.jsp" class="activeColour"><i class="icon-terrain"></i>Graph </a>
<!--            <ul class="dropdown-menu">
                <li>
                    <a href='<%=inBasePath%>aGraph/graph.jsp'>Graph</a>
                </li>


            </ul>-->
        </li>
<!--        <li class="dropdown" id="dropDownMachine">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="glyphicon glyphicon-tasks"></i>Machine Learning<span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li>
                    <a href='<%=inBasePath%>mMachineLearning/machineLearning.jsp'>Table</a>
                </li>

            </ul>
        </li>-->


    </ul>
</div>
