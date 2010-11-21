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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="arx" %>
        <title>Assign pathway</title>       
    </head>
    <body>
        <h1>Assign pathway</h1>
        <form action="Controller" method="post">
            <input type="hidden" name="id" value='<%=(String)request.getAttribute("id")%>'/>
            <p>
                <table>
                    <tr>
                        <td>
                            Pathways <br>
                            <arx:checkbox collection="pathways" name="pathwayId" idGetter="getPathwayId" textGetter="getName"/>                            
                        </td>
                    <tr>                  
                </table>
            </p>
            <p>
                <arx:save saveName="assign" cancelName="back"/>
            </p>
        </form> 
    </body>
</html>