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
<jsp:useBean id="groupdto" scope="request" class="com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO" />
<%
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)request.getSession().getAttribute("caller");
    String suidName = caller.getSuidName();
%>   
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit group</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 20);
                define('comm', 'string', 'Comment', null, 256);                
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Edit Group</h1>
        <form action="Controller" method="post">
            <p>
                <input type="hidden" name="gid" value='<jsp:getProperty name="groupdto" property="gid"/>'/>
                <table>
                    <tr>
                        <td>Sampling unit: <%=suidName%></td>
                    </tr>
                    <tr>
                        <td>Grouping: <jsp:getProperty name="groupdto" property="gsid"/></td>
                    </tr>      
                    <tr>
                        <td>Name</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" value='<jsp:getProperty name="groupdto" property="name"/>'/></td>
                    </tr>
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="groupdto" property="comm"/></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <m:save saveName="save" cancelName="back" onClick="validate();return returnVal;"/>
            </p>
        </form> 
    </body>
</html>

