<%--
  Created by IntelliJ IDEA.
  User: Iulian
  Date: 20.05.2019
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Problema</title>
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
<jsp:useBean id="problem" scope="request" type="org.json.JSONObject"/>
<div>

    <p> Id : ${problem.getInt("id")}</p>
    <p> Categorie: ${problem.getInt("category")}</p>
    <p> Dificultate : ${problem.getString("difficulty")}</p>
    <p> Titlu: ${problem.getString("title")}</p>
    <div>
        Exemplu
        <p> Input: ${problem.getString("test_in")}</p>
        <p> Output: ${problem.getString("test_out")}</p>
    </div>
</div>

</body>
</html>
