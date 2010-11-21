<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="funcsig" scope="request" type="com.arexis.mugen.model.modelmanager.FunctionalSignificanceDTO" />   
<%
    com.arexis.mugen.model.modelmanager.FunctionalSignificanceDTO dto = (com.arexis.mugen.model.modelmanager.FunctionalSignificanceDTO)request.getAttribute("funcsig");
    String typeId = ""+dto.getTypeId();
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">       
        </script>          
        <title>Add functional significance</title>
    </head>
    <body onLoad="init()">
        <h1>Edit functional significance</h1>
        <form action="Controller" method="post">
            <p>
                <table>
                   <tr>
                        <td>Name</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" value="<jsp:getProperty name="funcsig" property="name"/>"/></td>
                    </tr>                  
                   <tr>
                        <td>Type</td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="types" name="type" idGetter="getFstid" textGetter="getName" selected="<%= typeId%>"/>
                        </td>
                    </tr>                                      
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="funcsig" property="comm"/></textarea></td>
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
