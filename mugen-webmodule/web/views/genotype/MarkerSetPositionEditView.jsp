
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
<jsp:useBean id="position" scope="request" type="com.arexis.mugen.genotype.genotypemanager.PositionDTO" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit Marker set position</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('pos', 'num', 'Position');
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Edit Marker set position</h1>
        <form action="Controller" method="post">
            <p>                                  
                Marker: <jsp:getProperty name="position" property="mname"/><br>                       
                <input type="hidden" name="mid" value='<jsp:getProperty name="position" property="mid"/>'/>
                <input type="hidden" name="msid" value='<jsp:getProperty name="position" property="msid"/>'/>
                <table>              
                    <tr>                    
                        <td>Position</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="pos" size="35" value='<jsp:getProperty name="position" property="pos"/>'/></td>
                    </tr>                                     
                </table>
            </p>
            <p>
                <input id="button" type="submit" name="save" value="Save" onClick="validate();return returnVal;">                
                <input id="button" type="submit" name="back" value="Back">
            </p>
        </form> 
    </body>
</html>
