
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
<jsp:useBean id="allele" scope="request" type="com.arexis.mugen.genotype.genotypemanager.AlleleDTO" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit Allele</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 20);                
                define('comm', 'string', 'Comment', null, 256); 
            }        
        </script>         
    </head>
    <body onBody="init()">
        <h1>Edit Allele</h1>
        <form action="Controller" method="post">
            <p>
                <input type="hidden" name="aid" value='<jsp:getProperty name="allele" property="aid"/>'/>
                <input type="hidden" name="mid" value='<jsp:getProperty name="allele" property="mid"/>'/>
                <table>
                    <tr>                    
                        <td>Name</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" value='<jsp:getProperty name="allele" property="name"/>'/></td>
                    </tr>
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="allele" property="comm"/></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <input id="button" type="submit" name="save" value="Save" onClick="validate();return returnVal;">                
                <a href="Controller?workflow=ViewMarkerAlleles&mid=<jsp:getProperty name="allele" property="mid"/>"><input id="button" type="button" name="back" value="Back"></a>
                <input id="button" type="submit" name="remove" value="Remove" onClick="return confirm('Delete allele?');">
            </p>
        </form> 
    </body>
</html>
