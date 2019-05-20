<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %><%--
  Created by IntelliJ IDEA.
  User: Iulian
  Date: 19.05.2019
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="resources/css/navbar.css">

</head>
<body>
<div class="navbar">
    <a href="../index.html" id="logo">LOGO</a>
    <div class="dropdown">
        <button class="dropbtn" onclick="#">PROBLEME
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="problema.html">Clasa a IX-a</a>
            <a href="problema.html">Clasa a X-a</a>
            <a href="problema.html">Clasa a XI-a</a>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">RESURSE
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="ResurseIX.html">Clasa a IX-a</a>
            <a href="ResurseX.html">Clasa a X-a</a>
            <a href="ResurseXI.html">Clasa a XI-a</a>

        </div>
    </div>
    <a href="adaugaIntrebare.html" id="logo">Adauga Problema</a>
    <a href="../index.html" id="logo">My Account</a>
    <a href="login.html" id="logo">Log Out</a>
</div>
<br><br><br>
<jsp:useBean id="problemList" scope="request" type="org.json.JSONArray"/>
<c:forEach var="i" begin="0" end="${problemList.length() - 1}">
    <div class="problemStyle" style="margin: auto;width: 60%;border: 3px solid #73AD21;padding: 10px;">
        Problema numarul : ${i} este :
        Id-ul : #${problemList.getJSONObject(i).getJSONObject("problem").getInt("id")}
        <br>
        <a href="GetProblem?title=${problemList.getJSONObject(i).getJSONObject("problem").getString("title")}">
            Titlu : ${problemList.getJSONObject(i).getJSONObject("problem").getString("title")}
        </a>
        <br>
        Categorie: ${problemList.getJSONObject(i).getJSONObject("problem").getInt("category")}
        <br>
        Dificultate : ${problemList.getJSONObject(i).getJSONObject("problem").getString("difficulty")}
        <br>
        Data adaugarii: ${problemList.getJSONObject(i).getJSONObject("problem").get("created_at").toString()}
    </div>
    <br><br>
</c:forEach>

</body>
</html>
