<%--
    Mugen JSP View.
    
--%>

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

<%
    String description = (String)request.getAttribute("description");
    if (description==null)
        description="&nbsp;";
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>JSP Page</title>
    </head>
    <body>

    <h1>Confirmation</h1>
    
    <p><%=description%></p>    
    <p>
    <form action="Controller" method=post>
        <input id="button" type=submit name=yes value="Yes">
        <input id="button" type=submit name=no value="No">
    </form>
    </p>
    
    
    <%--
        Example to get a property
        <jsp:getProperty name="ind" property="iid"/>
    --%>
    
    </body>
</html>
