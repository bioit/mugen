
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
<jsp:useBean id="uallele" scope="request" type="com.arexis.mugen.genotype.genotypemanager.UAlleleDTO" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit Unified Allele</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 20);
                define('comm', 'string', 'Comment', null, 256); 
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Edit Unified Allele</h1>
        <form action="Controller" method="post">
            <p>
                <input type="hidden" name="uaid" value='<jsp:getProperty name="uallele" property="uaid"/>'/>
                <input type="hidden" name="umid" value='<jsp:getProperty name="uallele" property="umid"/>'/>
                <table>
                    <tr>                    
                        <td>Name</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" value='<jsp:getProperty name="uallele" property="name"/>'/></td>
                    </tr>
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="uallele" property="comm"/></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <input id="button" type="submit" name="save" value="Save" onClick="validate();return returnVal;">                
                <a href="Controller?workflow=ViewUMarkerAlleles&umid=<jsp:getProperty name="uallele" property="umid"/>"><input id="button" type="button" name="back" value="Back"></a>
                <input id="button" type="submit" name="remove" value="Remove" onClick="return confirm('Delete unified allele?');">
            </p>
        </form> 
    </body>
</html>
