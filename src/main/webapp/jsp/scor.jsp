
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="Model.dao.storage.testCaseList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Scor</title>
</head>
<body>
<jsp:useBean id="problemSituation" scope="request" type="java.util.ArrayList"/>
<jsp:useBean id="totalScore" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="errors" scope="request" class="java.lang.String"/>

<!-- Erori de compilare -->
<div id="compilerErrors"> ${errors}</div>

<c:if test = "${problemSituation.get(0).getIndex() > 0}">
    <div id="problemSituation">
<c:forEach var="i" begin="0" end="${problemSituation.size() - 1}">
    <p>


    Id-ul testului : ${problemSituation.get(i).getIndex()} |
    Mesaj Evaluare : ${problemSituation.get(i).getEvaluationMessage()} |
    Scor posibil : ${problemSituation.get(i).getOriginalScore()} |
    Scor obtinut : ${problemSituation.get(i).getObtainedScore()} |
</p>
</c:forEach>
</div>

<!--Scor total -->
<div id="totalScore">  Scor total : ${totalScore}  </div>
</c:if>

</body>
</html>
