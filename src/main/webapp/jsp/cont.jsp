<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("username") == null) response.sendRedirect("../index.html");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="resources/css/navbar.css">
    <link rel="stylesheet" href="resources/css/adaugaIntrebare.css">
    <link rel="stylesheet" href="resources/css/ListaProbleme.css">
    <title>PbInfo?</title>
</head>

<body>


<jsp:useBean id="userProfile" scope="request" type="org.json.JSONObject"/>
<jsp:useBean id="solvedProblems" scope="request" type="org.json.JSONArray"/>
<jsp:useBean id="triedProblems" scope="request" type="org.json.JSONArray"/>


<div class="navbar">
    <div class="dropdown">
        <button class="dropbtn" onclick="#"> PROBLEMEE
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="GetProblems?grade=9">Clasa a IX-a</a>
            <a href="GetProblems?grade=10">Clasa a X-a</a>
            <a href="GetProblems?grade=11">Clasa a XI-a</a>
        </div>
    </div>
    <div class="dropdown">


    </div>
    <a href="html/adaugaIntrebare.html" id="logo">ADAUGA PROBLEMA</a>
    <a href="GetUserProfile"  id="logo">CONTUL MEU</a>
    <a href="logout.jsp" id="logo">DECONECTARE</a>
</div>

<table class="problemStyle" style="margin: auto;width: 60%;border: 3px solid #73AD21;padding: 10px;">
    <tr>
        <th></th>
        <th>Email</th>
        <th>Nr. probleme rezolvate</th>
        <th>Nr. probleme adaugate</th>
        <th>Punctaj total</th>
    </tr>
    <tr>
        <td>${userProfile.get("username")}</td>
        <td>${userProfile.get("email")}</td>
        <td>${userProfile.get("solved_problems_no")}</td>
        <td>${userProfile.get("uploaded_problems_no")}</td>
        <td>${userProfile.get("points_no")}</td>
    </tr>
    <br><br>
</table>
<br><br>


<table class="problemStyle" style="margin: auto;width: 60%;border: 3px solid #73AD21;padding: 10px;">
    <tr>
        <th>Titlu probleme rezolvate</th>
        <th>Punctaj problema</th>
    </tr>
    <c:choose>
        <c:when test="${solvedProblems.length() != 0}">
            <c:forEach var="i" begin="0" end="${solvedProblems.length() - 1}">
                <tr>
                    <td>${solvedProblems.getJSONObject(i).getJSONObject("problem").get("title")}</td>
                    <td>${solvedProblems.getJSONObject(i).getJSONObject("problem").get("points")}</td>
                </tr>
                <br><br>
            </c:forEach>
        </c:when>
    </c:choose>
</table>

<br><br>
<table class="problemStyle" style="margin: auto;width: 60%;border: 3px solid #73AD21;padding: 10px;">
    <tr>
        <th>Titlu probleme incercate</th>
        <th>Punctaj problema</th>
    </tr>
    <c:choose>
        <c:when test="${triedProblems.length() != 0}">
            <c:forEach var="i" begin="0" end="${solvedProblems.length() - 1}">
                <tr>
                    <td>${solvedProblems.getJSONObject(i).getJSONObject("problem").get("title")}</td>
                    <td>${solvedProblems.getJSONObject(i).getJSONObject("problem").get("points")}</td>
                </tr>
                <br><br>
            </c:forEach>
        </c:when>
    </c:choose>
</table>

</body>

</html>

