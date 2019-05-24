<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="resources/css/navbar.css">
    <link rel="stylesheet" href="resources/css/ListaProbleme.css">

</head>
<body>
<div class="navbar">
    <a href="GetProblems?grade=9" class="DROPBTN">Probleme</a>
    <a href="html/adaugaIntrebare.html" id="logo">Adauga Problema</a>
    <a href="index.html" id="logo">My Account</a>
    <a href="html/login.html" id="logo">Log Out</a>
</div>

<br><br><br>
<jsp:useBean id="problemList" scope="request" type="org.json.JSONArray"/>

<table class="problemStyle" style="margin: auto;width: 60%;border: 3px solid #73AD21;padding: 10px;">
    <tr>
        <th>Numar Problema</th>
        <th>Nume Problema</th>
        <th>Dificultate</th>
        <th>Categorie</th>
        <th>Data Adaugarii</th>
        <th>Rezolva</th>
    </tr>
<c:forEach var="i" begin="0" end="${problemList.length() - 1}">


        <tr>
            <td> #${problemList.getJSONObject(i).getJSONObject("problem").getInt("id")}</td>
            <td> ${problemList.getJSONObject(i).getJSONObject("problem").getString("title")}</td>
            <td> ${problemList.getJSONObject(i).getJSONObject("problem").getInt("category")}</td>
            <td> ${problemList.getJSONObject(i).getJSONObject("problem").getString("difficulty")}</td>
            <td> ${problemList.getJSONObject(i).getJSONObject("problem").get("created_at").toString()}</td>
            <td> <a href="GetProblem?title=${problemList.getJSONObject(i).getJSONObject("problem").getString("title")}"> Rezolva</a></td>
        </tr>
    <br><br>
</c:forEach>
</table>

</body>
</html>
