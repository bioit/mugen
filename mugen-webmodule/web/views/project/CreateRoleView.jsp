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
<%--
Example of a jsp:useBean:
<jsp:useBean id="ind" scope="request" class="com.arexis.mugen.samplingunit.individualmanager.IndividualDO"/>

--%>
<jsp:useBean id="role" scope="request" type="com.arexis.mugen.project.projectmanager.RoleDTO"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <script language="JavaScript" src="validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 20);
                define('comm', 'string', 'Comment', null, 255);                
            }        
        </script>          
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>JSP Page</title>
    </head>
    <body onLoad="init()">

    <h1>Create Role</h1>
    
    <form action="Controller" method=post>
        <input type="hidden" name="rid" value="<%=role.getRid()%>">
        <table>
        <tr>
            <td>
                Name<br>
                <input type=text name="name" value="<%=role.getName()%>">
            </td>
        </tr>
        <tr>
            <td>
                Comment<br>
                <textarea type="text" cols="35" rows="6" name="comm"></textarea>
            </td>
        </tr>
        </table>
            <p>
                <m:save saveName="create" cancelName="back"/>
            </p>
        
    </form>

    </body>
</html>
