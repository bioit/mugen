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
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");  
    String currentFid = (String)request.getAttribute("currentFid");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Genotype filter</title>
    </head>
    <body onLoad="init()">
        <h1>Genotype status</h1>
        <form action="Controller" method="post">
            <table> 
                <td>
                    Filter <br>
                    <m:checkbox collection="filters" name="fid" idGetter="getFid" textGetter="getName" selected='<%=currentFid%>'/>
                </td>
                <td>Sampling unit <br>
                    <m:su-combobox onChange="this.form.submit()"/>
                </td>                                 
                <td>
                    Marker set <br>
                    <m:checkbox collection="markersets" name="msid" idGetter="getMsid" textGetter="getName" />
                    <input id="button" type="submit" name="display" value="Display">
                </td>  
            </table>
            <hr id="ruler">
            <p>
            </p>
        </form> 
    </body>
</html>
