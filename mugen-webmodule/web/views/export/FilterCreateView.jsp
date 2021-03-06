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
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)request.getSession().getAttribute("caller");
    String sid = (String)request.getAttribute("sid");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="arx" %>
        
        <title>Edit group</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 20);
                define('comm', 'string', 'Comment', null, 256);         
                define('expression', 'string', 'GQL Expression', null, 2000);
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Create Filter</h1>
        <form action="Controller" method="post">
            <p>
                <table>
                    <tr>
                        <td>Species <br>
                            <m:checkbox collection="species"selected="<%=sid%>" name="sid" idGetter="getSid" textGetter="getName" onChange="this.form.submit()"/>
                        </td>
                        <td>Name<br>
                            <input type="text" name="name" size="35" value=''/>
                        </td>   
                        <td>Comment<br>
                            <input type="text" name="comm" size="35" value=''/>
                        </td>                 
                    </tr>
                    <tr>
                        <td colspan="3">
                            GQL Expression<br>
                            <textarea type="text" cols="80" rows="6" name="expression"></textarea>
                        </td>
                    </tr>                      
                </table>
            </p>
            <p>
                <input id="button" type="submit" name="back" value="Back">
                <arx:su-combobox/>                                    
                <input id="button" type="submit" name="create" value="Create" onClick="validate();return returnVal;">                                                
            </p>
        </form> 
    </body>
</html>

