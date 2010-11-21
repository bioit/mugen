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
<jsp:useBean id="filedto" scope="request" class="com.arexis.mugen.resource.resourcemanager.FileDTO" />   
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit group</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 50);
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Edit file</h1>
        <form action="Controller" method="post">
            <p>
                <table>
                    <tr>                                      
                        <td>Name</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" value='<jsp:getProperty name="filedto" property="name"/>'/></td>
                    </tr>                   
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="filedto" property="comm"/></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <m:save saveName="save" cancelName="back"/>
            </p>
        </form> 
    </body>
</html>