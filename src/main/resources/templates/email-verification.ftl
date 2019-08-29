<!DOCTYPE html>
<html>
<head>
    <title>Apirus</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Lato" />
    <link rel="shortcut icon" href="https://www.sumitchouksey.com/images/apirus-logo.png"/>
    <style type="text/css">
        body{
            font-family: Lato;
        }
        a, a:hover{
            color: #0052cc;
            text-decoration: none;
        }
        b{
            font-size: 16px;
        }
        p{
            font-size: 14px;
        }
        span{
            font-weight: bold;
        }
        .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
            border: none;
        }
        .button {
            display: inline-block;
            border-radius: 5px;
            background-color: green;
            border: none;
            color: #FFFFFF;
            text-align: center;
            font-size: 10px;
            padding: 10px;
            width: 120px;
            transition: all 0.5s;
            cursor: pointer;
        }

        .button span {
            cursor: pointer;
            display: inline-block;
            position: relative;
            transition: 0.5s;
        }

        .button span:after {
            content: '\00bb';
            position: absolute;
            opacity: 0;
            top: 0;
            right: -20px;
            transition: 0.5s;
        }

        .button:hover span {
            padding-right: 25px;
        }

        .button:hover span:after {
            opacity: 1;
            right: 0;
        }
    </style>
</head>
<body>
<table class="table" align="center" style="width: 50%;">
    <tbody>
    <tr>
        <td align="center">
            <img src="https://www.sumitchouksey.com/images/apirus.png" height="70" width="180"> <h2 style="font-family: Lato">Apirus</h2>
        </td>
    </tr>
    <tr>
        <td align="left">
            <b>Hi ${name},</b>
        </td>
    </tr>
    <tr>
        <td align="left">
            <p>You are almost done! Make sure you verify your email address and we'll finish setting accout for you.
                <br>Thanks you for choosing Apirus!
            </p>
            <!-- <button class="button" onclick="verifyUrl()" id ="verifyButton" value='${verifyUrl}' style="vertical-align:middle"><span>Yes, verify me ! </span></button>-->
            <a  href='${verifyUrl}' class="button" style="vertical-align:middle">Yes, verify me !</a><br><br>
            <!-- <hr /> -->
        </td>
    </tr>
    <tr>
        <td style="border-top: 1px #ccc solid;">
            <p>Please <a href="#">contact us</a> with any questions, we're always happy to help.</p>
            <p style="padding: 10px 0px;">Cheers,<br /><b>Apirus</b></p>
        </td>
    </tr>
    <tr>
        <td align="center">
            <p style="color: #707070; padding-top: 5px;">
                Copyright 2018 Apirus All rights reserved
            </p>
            <p>
                <img src="https://www.sumitchouksey.com/images/apirus-logo.png" height="30" width="40">
            </p>
        </td>
    </tr>
    </tbody>
</table>
<script>
    function verifyUrl() {
        this.location.href=document.getElementById("verifyButton").value;
    }
</script>
</body>
</html>