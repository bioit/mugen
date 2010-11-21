
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
<jsp:useBean id="umarker" scope="request" type="com.arexis.mugen.genotype.genotypemanager.UMarkerDTO" />
<%    
    com.arexis.mugen.genotype.genotypemanager.UMarkerDTO dto = (com.arexis.mugen.genotype.genotypemanager.UMarkerDTO)request.getAttribute("umarker");
    String cid = ""+dto.getCid();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit Unified Marker</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 20);
                define('comm', 'string', 'Comment', null, 256); 
                define('alias', 'string', 'Alias', null, 20); 
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Edit Unified Marker</h1>
        <form action="Controller" method="post">
            <p>
                Species: <jsp:getProperty name="umarker" property="species"/><br>                     
                <input type="hidden" name="umid" value='<jsp:getProperty name="umarker" property="umid"/>'/>
                <table>
                    <tr>                                    
                        <td>Chromosome <br>
                            <m:checkbox collection="chromosomes" name="chromosome" idGetter="getCid" textGetter="getName" selected='<%=cid%>'/>
                        </td>                    
                    </tr>                
                    <tr>                    
                        <td>Name</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" value='<jsp:getProperty name="umarker" property="name"/>'/></td>
                    </tr>
                    <tr>                    
                        <td>Alias</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="alias" size="35" value='<jsp:getProperty name="umarker" property="alias"/>'/></td>
                    </tr>        
                    <tr>                    
                        <td>Position</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="position" size="35" value='<jsp:getProperty name="umarker" property="position"/>'/></td>
                    </tr>                            
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="umarker" property="comm"/></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <input id="button" type="submit" name="save" value="Save" onClick="validate();return returnVal;">                
                <input id="button" type="submit" name="back" value="Back">
                <input id="button" type="submit" name="remove" value="Remove" onClick="return confirm('Delete unified marker?');">
            </p>
        </form> 
    </body>
</html>
