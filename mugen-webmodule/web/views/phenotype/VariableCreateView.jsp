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
<jsp:useBean id="types" scope="request" type="com.arexis.mugen.phenotype.phenotypemanager.TypeDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Create Variable</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 20);                
                define('unit', 'string', 'Unit', null, 10); 
                define('comm', 'string', 'Comment', null, 256); 
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Create Variable</h1>
        <form action="Controller" method="post">
            <p>
                <table>
                    <tr>
                        <td>Sampling unit <br>
                            <m:su-combobox/>
                        </td>
                    </tr>                
                    <tr>
                        <td>Name</td>
                    </tr>               
                    <tr>
                        <td><input type="text" name="name" size="35"/></td>
                    </tr>
                    <tr>
                        <td>Unit</td>
                    </tr>               
                    <tr>
                        <td><input type="text" name="unit" size="35"/></td>
                    </tr>    
                    <tr>
                        <td>
                            Type <br>
                            <m:type value='<%=types.getType()%>' />
                        </td>
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
                <m:save saveName="new" cancelName="back" onClick="validate();return returnVal;"/>
            </p>
        </form> 
    </body>
</html>