<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="Model.dao.storage.testCaseList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
%>
<html>
<head>

    <link rel="stylesheet" type="text/css" href="resources/css/scor.css">
    <title>Scor</title>
</head>
<body>
<jsp:useBean id="problemSituation" scope="request" type="java.util.ArrayList"/>
<jsp:useBean id="totalScore" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="errors" scope="request" class="java.lang.String"/>

<c:choose>
    <c:when test = "${problemSituation.get(0).getIndex() < 0}">
        <!-- Erori de compilare -->
        <div id="compilerErrors"> ${errors}</div><br>
    </c:when>
    <c:otherwise>
        <div class="bottom" style="width:70%; 
        margin-left:15%; 
        margin-right:15%;
        margin-top:10%;">
            <table id="customers" class="rezultate">
                <tr>
                    <th>Id-ul testului</th>
                    <th>Mesaj evaluare </th>
                    <th>Scor posibil</th>
                    <th>Scor obtinut</th>
                </tr>
                <c:forEach var="i" begin="0" end="${problemSituation.size() - 1}">

                    <tr class="linieTabel" id="problemSituation">
                        <td>${problemSituation.get(i).getIndex()}</td>
                        <td class="mesaj">${problemSituation.get(i).getEvaluationMessage()}</td>
                        <td>${problemSituation.get(i).getOriginalScore()} </td>
                        <td>${problemSituation.get(i).getObtainedScore()}</td>
                    </tr>


                </c:forEach>
            </table><br>
            <div class='rezfin' id="totalScore">
                Rezultat final : <b> ${totalScore} </b>
            </div>
            <script>
                var table= document.getElementById('problemSituation')
                var linie = document.getElementsByClassName('linieTabel')
                var mesaj=document.getElementsByClassName('mesaj')
                for ( i = 0; i < mesaj.length; i ++ ) {
                    console.log(mesaj.length)
                    if (mesaj[i].innerText === "OK") {
                        linie[i].style.backgroundColor = "#BDFF89"
                    }
                    else
                    {
                        linie[i].style.backgroundColor = "#FB7474"
                    }
                }
            </script>
        </div>
    </c:otherwise>
</c:choose><br><br>
</body>
</html>
