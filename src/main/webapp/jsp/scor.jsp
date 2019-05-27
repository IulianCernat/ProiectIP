<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="Model.dao.storage.testCaseList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <link rel="stylesheet" type="text/css" href="index.css">
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
        <div class="bottom">
            <table class="rezultate">
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
            </table>
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
                        linie[i].style.backgroundColor = "green"
                    }
                    else
                    {
                        linie[i].style.backgroundColor = "red"
                    }
                }
            </script>
        </div>
    </c:otherwise>
</c:choose><br><br>
<button a href="">Inapoi la cerinta</button>
</body>
</html>
