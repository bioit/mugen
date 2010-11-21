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
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Create Individual</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('identity', 'string', 'Identity', 1, 11);
                define('alias', 'string', 'Alias', null, 11);                
                define('birthdate', 'date', 'Birthdate'); 
                define('comm', 'string', 'Comment', null, 256); 
            }        
        </script>           
    </head>
    <body onLoad="init()">
        <h1>Create Individual</h1>
        <form action="Controller" method="post">
            <p>
                <table>
                    <tr>
                        <td>Sampling unit<br><m:su-combobox onChange="this.form.submit()"/></td>
                    </tr>                
                    <tr>
                        <td>Identity<br><input type="text" name="identity" value=''/></td>
                    </tr>
                    <tr>
                        <td>Alias<br><input type="text" name="alias" value=''/></td> 
                    </tr>
                    <tr>
                        <td>Sex<br><m:sex value='M'/></td>
                    </tr>
                    <tr>
                        <td>Status<br><m:status value='E'/></td>
                    </tr>
                    
                    <tr>
                    <td>Father<br><m:checkbox collection='males' emptyOption='true' selected='<%=(String)request.getAttribute("father")%>' name='father' idGetter='getIid' textGetter='getName'/></td>
                    </tr>
                    
                    <tr>
                    <td>Mother<br><m:checkbox collection='females' emptyOption='true' selected='<%=(String)request.getAttribute("mother")%>' name='mother' idGetter='getIid' textGetter='getName'/></td>
                    </tr>                                        
                    <tr>
                        <td>Birth date<br><input type=text name=birthdate value=''></td>
                    </tr>
                    <tr>
                        <td colspan=2>Comment: </td>
                    </tr>
                    <tr>
                        <td colspan=2><textarea type="text" cols="35" rows="6" name="comm"></textarea></td>
                    </tr>                                                                          
                </table>
            </p>
            <p>
                <m:save saveName="create" onClick="validate();return returnVal;"/>
            </p>
        </form>
    
    </body>
</html>
