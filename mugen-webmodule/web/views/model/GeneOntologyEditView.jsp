<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="geneontologydto" scope="request" type="com.arexis.mugen.model.modelmanager.GeneOntologyDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">      
        </script>          
        <title>Add gene ontology</title>
    </head>
    <body onLoad="init()">
        <h1>Edit gene ontology</h1>
        <form action="Controller" method="post">
            <input type="hidden" name="goid" value='<jsp:getProperty name="geneontologydto" property="goid"/>'/>
            <p>
                <table>
                   <tr>
                        <td>GO nr</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="linkid" size="35" value='<jsp:getProperty name="geneontologydto" property="linkid"/>'/></td>
                    </tr>   
                   <tr>
                        <td>GO name/process</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" value='<jsp:getProperty name="geneontologydto" property="name"/>'/></td>
                    </tr>                 
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="geneontologydto" property="comm"/></textarea></td>
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
