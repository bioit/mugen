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
<jsp:useBean id="phenotypedto" scope="request" type="com.arexis.mugen.phenotype.phenotypemanager.PhenotypeDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit phenotype</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('value', 'string', 'Value', 1, 20);                
                define('date', 'date', 'Date'); 
                define('reference', 'string', 'Reference', null, 32); 
                define('comm', 'string', 'Comment', null, 256); 
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Edit Phenotype</h1>
        <form action="Controller" method="post">
            <p>
                <input type="hidden" name="vid" value='<jsp:getProperty name="phenotypedto" property="vid"/>'/>
                <input type="hidden" name="iid" value='<jsp:getProperty name="phenotypedto" property="iid"/>'/>
                <table>
                    <tr>
                        <td>Species: <jsp:getProperty name="phenotypedto" property="speciesname"/></td>
                    <tr>
                    <tr>
                        <td>Sampling Unit: <jsp:getProperty name="phenotypedto" property="suname"/></td>
                    <tr>                                      
                        <td>Variable: <jsp:getProperty name="phenotypedto" property="variable"/></td>
                    </tr>
                    <tr>                                      
                        <td>Type: <jsp:getProperty name="phenotypedto" property="type"/></td>
                    </tr>   
                    <tr>                                      
                        <td>Identity: <jsp:getProperty name="phenotypedto" property="identity"/><jsp:getProperty name="phenotypedto" property="accNr"/></td>
                    </tr> 
                    <tr>                                      
                        <td>Last updated by: <jsp:getProperty name="phenotypedto" property="user"/>, <jsp:getProperty name="phenotypedto" property="updated"/></td>                        
                    </tr>   
                    <tr>
                        <td>Value</td>
                    </tr>               
                    <tr>
                        <td><input type="text" name="value" size="35" value='<jsp:getProperty name="phenotypedto" property="value"/>'/></td>
                    </tr>
                    <tr>
                        <td>Date</td>
                    </tr>               
                    <tr>
                        <td><input type="text" name="date" size="35" value='<jsp:getProperty name="phenotypedto" property="date"/>'/></td>
                    </tr>    
                    <tr>
                        <td>Reference</td>
                    </tr>               
                    <tr>
                        <td><input type="text" name="reference" size="35" value='<jsp:getProperty name="phenotypedto" property="reference"/>'/></td>
                    </tr>                
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="phenotypedto" property="comm"/></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <m:save saveName="save" onClick="validate();return returnVal;"/>
            </p>
        </form> 
    </body>
</html>
