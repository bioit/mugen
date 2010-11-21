<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="exception" scope="request" class="java.lang.Exception"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <script defer language="JavaScript" src="javascripts/framebreak.js"></script>
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>error</title>
    </head>
    <body class="content">
    <form action="Controller" method="post">
    <%--form action="Controller?workflow=recover" name="login" method="post" target="_top"--%>
        <h1>error</h1>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
            <%--input name="submit" type="image" src="images/icons/restart.png" title="restart mugen"--%>
        </p>
        <br>
        <table>
            <tr><td>An error occured due to invalidated session parameters or an invalid request.</td></tr>
        </table>
    </form>
    </body>
</html>
