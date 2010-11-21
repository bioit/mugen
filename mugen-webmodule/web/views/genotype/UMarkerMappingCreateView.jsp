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
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>        
        <title>Create Unified Marker mapping</title>
    </head>
    <body>
        <h1>Create Unified Marker mapping</h1>  
        <form action="Controller" method="post">
        <input type="hidden" name="umid" value='<%=fdm.getValue("umid")%>'>
        <table>
        <tr>
            <td>Chromosome</td>
            <td>
                 <m:checkbox collection="chromosomes"  selected='<%=fdm.getValue("cid")%>' idGetter="getCid" textGetter="getName" name="cid" onChange="this.form.submit()"/>
            </td>
            <td>
                <input id="button" type="submit" name="set" value="Set"> 
            </td>
        </tr>
        <tr>
            <td>Marker</td>
            <td>
                 <m:checkbox collection="markers"  selected='<%=fdm.getValue("mid")%>' idGetter="getMid" textGetter="getName" name="mid"/>
            </td>
        </tr>        
        </table>
        <p>                                                
            <input id="button" type="submit" name="back" value="Back">  
            <input id="button" type="submit" name="create" value="Create">            
        </p>          
        </form>  
    </body>
</html>
