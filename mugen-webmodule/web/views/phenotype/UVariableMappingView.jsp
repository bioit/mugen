<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.Collection" %>
<%
    String uvid = (String)request.getParameter("uvid");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Variable Mappings</title>
    </head>
    <body>         
        <h1>Unified Variable Mappings</h1>
        <form action="Controller">
        <table class="data" method="post">
            <tr>
                <th class="data">Sampling Unit</th>
                <th class="data">Variable</th>
                <th class="data">Delete</th>
            </tr>
            <m:iterate-collection collection="uvarmappings">

            <tr class="#?alt#">
                <td><a href="Controller?workflow=ViewSamplingUnit&suid=#:getSuid#">#:getSuname#</a></td>
                <td><a href="Controller?workflow=ViewVariable&vid=#:getVid#">#:getVarname#</a></td>
                <td><a href="Controller?workflow=DeleteUVariableMapping&uvid=#:getUvid#&vid=#:getVid#" onClick="return confirm('Delete mapping?');"><m:img name="delete"/></a></td>
            </tr>

            </m:iterate-collection>
            </table> 
            <p>
            <input id="button" type="submit" name="back" value="Back">
                <a href="Controller?workflow=CreateUVariableMapping&uvid=<%=uvid%>">
                    <input id="button"  type="button" name="create" value="Create"></a> 
            </p>
        </form>
    </body>
</html>
