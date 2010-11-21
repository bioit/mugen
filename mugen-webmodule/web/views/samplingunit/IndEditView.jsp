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
<jsp:useBean id="ind" scope="request" class="com.arexis.mugen.samplingunit.samplingunitmanager.IndividualDTO" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit Individual</title>
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
        <h1>Edit Individual</h1>
        <form action="Controller">
            <p>
                <input type="hidden" name="iid" value='<jsp:getProperty name="ind" property="iid"/>'/>
                <table>
                    <tr>
                        <td>Identity:</td>
                        <td><input type="text" name="identity" value='<jsp:getProperty name="ind" property="identity"/>'/></td>
                    </tr>
                    <tr>
                        <td>Alias:</td>
                        <td><input type="text" name="alias" value='<jsp:getProperty name="ind" property="alias"/>'/></td>
                    </tr>
                    <tr>
                        <td>Sex:</td>
                        <td><input type="text" name="sex" value='<jsp:getProperty name="ind" property="sex"/>'/></td>
                    </tr>
                    <tr>
                        <td>Status:</td>
                        <td>
                            <m:status value='<%=ind.getStatus()%>' />
                        </td>
                    </tr>                    
                    <tr>
                        <td>Father:</td>                                                            
                        <td> <m:checkbox collection='males' emptyOption='true' selected='<%=(String)request.getAttribute("father")%>' name='father' idGetter='getIid' textGetter='getName'/></td>
                    </tr>                    
                    <tr>
                        <td>Mother:</td>
                        <td> <m:checkbox collection='females' emptyOption='true' selected='<%=(String)request.getAttribute("mother")%>' name='mother' idGetter='getIid' textGetter='getName'/></td>
                    </tr>                                        
                    <tr>
                        <td>Birth date:</td>
                        <td><input type=text name=birthdate value='<jsp:getProperty name="ind" property="birthDate"/>'></td>
                    </tr>
                    <tr>
                        <td colspan=2>Comment:</td>
                    </tr>
                    <tr>
                        <td colspan=2><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="ind" property="comm"/></textarea></td>
                    </tr>                                                                          
                </table>
            </p>
            <p>
                <m:save saveName="update" onClick="validate();return returnVal;"/>    
            </p>
        </form>   
    </body>
</html>
