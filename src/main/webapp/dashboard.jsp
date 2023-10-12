<%--
  Created by IntelliJ IDEA.
  User: YC£
  Date: 11/10/2023
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>Easy Bank</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/CostumeCss.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Marcellus+SC&family=Montserrat:ital,wght@0,100;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,400;1,600;1,700;1,800;1,900&family=Poppins:ital,wght@0,300;0,400;0,600;0,700;0,800;0,900;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">

</head>
<body>
<nav class="navBar">
    <div class="logo">
        Easybank
    </div>

    <div class="btn_list">
        <ul class="ulNavbar" >
            <li class="list"><a class="alink"  href="#">Home</a></li>
            <li class="list"><a class="alink" href="#"> About us</a></li>
            <li class="list"><a class="alink" href="#">Contact</a></li>

        </ul>
        <button class="BtnOpenAcc">Open Account</button>
    </div>
</nav>

  <section class="w-full table-area">
     <div class="container-table d-flex-col  align-items-center" style="gap: 28px">
    <div class="table-rowP margin-x-auto ">
        <span>Code</span>
        <span>First Name</span>
        <span>Last Name</span>
        <span>N°Phone</span>
        <span>Date Of Birth</span>
        <span>Adresse</span>
     </div>
       <c:forEach var="entry" items="${clientMap}">
         <div class="table-row ">
             <span class="column-info">{$entry.value}</span>
            <span class="column-info" >First Name</span>
             <span class="column-info">Last Name</span>
             <span class="column-info">N°Phone</span>
             <span class="column-info">Date Of Birth</span>
             <span>Adresse</span>
         </div>


      </div>
  </section>

<form class="d-flex-col padding-20 position-absolute" method="POST" action=dashboard>
    <div class="d-flex-col">
        <label class="label-input color-black">Your First Name*</label>
        <input class="inputs padding-10" type="text" value="" placeholder="First Name">
    </div>
    <div class="d-flex-col">
        <label class="label-input color-black">Your Last Name*</label>
        <input class="inputs padding-10" type="text" value="" placeholder="Last Name">
    </div>
    <div class="d-flex-col">
        <label class="label-input color-black">Your N°Phone*</label>
        <input class="inputs padding-10" type="tel" value="" placeholder="+212696102690">
    </div>
    <div class="d-flex-col">
        <label class="label-input color-black">Your Birth Day*</label>
        <input class="inputs padding-10" type="date" value="" placeholder="01/01/1900">
    </div>
    <div class="d-flex-col">
        <label class="label-input color-black">Your Adresse*</label>
        <input class="inputs padding-10" type="text" value="" placeholder=" Your Adresse">
    </div>

    <button type="submit" class="padding-10 color-white bg-blue ">Submit</button>
</form>

</body>
</html>
