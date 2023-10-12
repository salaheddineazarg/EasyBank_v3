<%--
  Created by IntelliJ IDEA.
  User: YC£
  Date: 11/10/2023
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Open Account</title>
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
<section class="w-full padding-20 bg-pricipal">
    <div class=" container d-flex-col ">
        <div class="padding-top-20 d-flex-col" style="gap: 10px">
        <h4 class="title-client">Hey 👋 Client!
            <br>
            <br>
            Let's start with your informations
        </h4>
         <p class="para-client" >1️⃣ Your privacy is our utmost priority - we never publish your personal information.</p>
         <p class="para-client">2️⃣ Check your information before submitting this form.</p>
        </div>
        <hr class="bg-black w-full margin-y-10" style="height: 1px">
        <form class="d-flex-col padding-20" method="Post" action="" >
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

            <button >Submit</button>
        </form>
    </div>

</section>
</body>
</html>
