<%@page contentType="text/html"%>

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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>JSP Page</title>
    </head>
    <body>
        <h1>File Upload</h1>    
        <form method=post enctype="multipart/form-data" action="Controller">
            <input type="hidden" name="id" value='<%=(String)request.getAttribute("id")%>'/>
            <p>
                <table>       
                    <tr>                                  
                        <td>File</td>
                    </tr>
                    <tr>
                        <td><input type="file" name="file"></td>
                    </tr>                                                
                    <tr>
                        <td>&nbsp</td>
                    </tr>
                    <tr>
                        <td align="right">
                            <input id="button" type="submit" name="back" value="Back">  
                            <input id="button" type="submit" name="upload" value="Upload">
                        </td>
                    </tr>                  
                </table>  
            </p>
        </form>
    </body>
</html>
