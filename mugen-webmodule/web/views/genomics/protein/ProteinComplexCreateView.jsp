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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="arx" %>
        <title>Create Protein Complex</title>       
    </head>
    <body>
        <h1>Create Protein Complex</h1>
        <form action="Controller" method="post">
            <p>
                <table>
                    <tr>
                        <td>Species<br>
                            <arx:checkbox collection="species" selected='<%=(String)request.getAttribute("sid")%>' name='speciesId' idGetter='getSid' textGetter='getName'/>
                        </td>              
                    </tr>
                    <tr>
                        <td>Name</td>
                    </tr>               
                    <tr>
                        <td><input type="text" name="name" size="35"/></td>
                    </tr>                         
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <arx:save saveName="create" cancelName="back"/>
            </p>
        </form> 
    </body>
</html>
