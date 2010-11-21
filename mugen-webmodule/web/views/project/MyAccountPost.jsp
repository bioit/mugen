<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="caller" scope="session" type="com.arexis.mugen.MugenCaller"/>
<% String result = (String) request.getSession().getAttribute("passchange");%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>my account post</title>
    </head>
    <body class="content">
        <h1>my account for <jsp:getProperty name="caller" property="name"/></h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method=post>
            <table>
                <tr>
                    <td><b><%=result%></b></td>
                </tr>
            </table>
        </form>
    </body>
</html>
