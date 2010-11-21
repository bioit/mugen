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
        <h1>Resource File Upload</h1>    
        <form method=post enctype="multipart/form-data" action="Controller">
            <input type="hidden" name="id" value='<%=(String)request.getParameter("id")%>'/>
            <input type="hidden" name="catId" value='<%=(String)request.getParameter("catId")%>'/>
            <p>
                <table>       
                    <tr>                                  
                        <td>File</td>
                    </tr>
                    <tr>
                        <td><input type="file" name="file"></td>
                    </tr>  
                     <tr>                                  
                        <td>Name</td>
                    </tr>
                    <tr>
                        <td><input type="text" cols="35" name="name"></td>
                    </tr>  
                     <tr>
                        <td>Category</td>
                    </tr>
                    <tr>
                        <td><m:checkbox name="catid" collection="categories" idGetter="getCatId" textGetter="getCatName"/></td>
                    </tr>
                    
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"></textarea></td>
                    </tr>                                               
                    <tr>
                        <td>&nbsp</td>
                    </tr>
                    <tr>
                        <td align="right">
                            <input id="button" type="submit" name="upload" value="Upload">                        
                            <input id="button" type="submit" name="back" value="Cancel">  
                        </td>
                    </tr>                  
                </table>  
            </p>
        </form>
    </body>
</html>
