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
    String mess = (String)request.getAttribute("mess");
    if(mess == null)
        mess = "";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>        
        <title>Create Unified Variable mapping</title>
    </head>
    <body>
        <h1>Create Unified Variable mapping</h1>  
        <p>Mappings will be created when pressing the button. Press 'Back' when done.</p>
        <form action="Controller" method="post">
        <input type="hidden" name="uvid" value='<%=(String)request.getParameter("uvid")%>'>
        <table>
        <tr>
            <td>Samplingunit</td>
            <td>
                 <m:su-combobox onChange="this.form.submit();"/>
            </td>
            <td>
                <input id="button" type="submit" name="set" value="Set"> 
            </td>
        </tr>
        <tr>
            <td>Variable</td>
            <td>
                 <m:checkbox collection="variables" selected='<%=(String)request.getParameter("vid")%>' idGetter="getVid" textGetter="getName" name="vid"/>
            </td>
        </tr>        
        </table>
        <p>                                                
            <input id="button" type="submit" name="back" value="Back"> 
            <input id="button" type="submit" name="save" value="Create"> 
        </p>     
        <p><%=mess%></p>
        </form>  
    </body>
</html>
