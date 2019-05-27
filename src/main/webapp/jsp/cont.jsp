<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(session.getAttribute("username") == null) response.sendRedirect("../index.html");
    response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
    response.setHeader("Pragma","no-cache");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../resources/css/navbar.css">
    <link rel="stylesheet" href="../resources/css/adaugaIntrebare.css">
    <link rel="stylesheet" href="../resources/css/ListaProbleme.css">
    <title>PbInfo</title>
</head>

<body>


<div class="navbar">
    <div class="dropdown">
        <button class="dropbtn" onclick="#"> PROBLEMEE
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href=".././GetProblems?grade=9">Clasa a IX-a</a>
            <a href=".././GetProblems?grade=10">Clasa a X-a</a>
            <a href=".././GetProblems?grade=11">Clasa a XI-a</a>
        </div>
    </div>
    <div class="dropdown">


    </div>
    <a href="adaugaIntrebare.html" id="logo">ADAUGA PROBLEMA</a>
    <a href="cont.html" id="logo">CONTUL MEU</a>
    <a href="../jsp/logout.jsp" id="logo">DECONECTARE</a>
</div>
<form method="POST">
    <table class="problemStyle" style="margin: auto;width: 60%;border: 3px solid #73AD21;padding: 10px;">
        <tr>
            <th>Username</th>
            <th>Email</th>
            <th>Nr. probleme rezolvate</th>
            <th>Nr. probleme adaugate</th>
            <th>Punctaj total</th>
        </tr>
        <c:forEach var="i" begin="0" end="${problemList.length() - 1}">


            <tr>
                <td> #${problemList.getJSONObject(i).getJSONObject("").getInt("")}</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <br><br>
        </c:forEach>
    </table>


    <table class="problemStyle" style="margin: auto;width: 60%;border: 3px solid #73AD21;padding: 10px;">
        <tr>
            <th>Titlu probleme rezolvate</th>
            <th>Punctaj problema</th>
        </tr>
        <c:forEach var="i" begin="0" end="${problemList.length() - 1}">


            <tr>
                <td></td>
                <td></td>
            </tr>
            <br><br>
        </c:forEach>
    </table>
</form>
</body>
<!--
<script>
  function getCategoryProblems(category) {
  var xmhr = new XMLHttpRequest();
  xmhr.onreadystatechange = function() {

  }
  xhr.open('GET', 'GetProblems', true);
  xhr.send("grade=" + category);}
</script>
-->
</html>

