<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="file2" scope="request" class="com.arexis.mugen.resource.resourcemanager.FileDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>edit file</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 35);
                define('comm', 'string', 'Comment', null, 255);
            }        
        </script>         
    </head>
    <body onLoad="init()" class="content">
        <h1>edit file</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
                <table>
                    <tr>                                      
                        <td><b>Name</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" maxlength="35" value='<jsp:getProperty name="filedto" property="name"/>'/></td>
                    </tr>                   
                    <tr>
                        <td><b>Comment</b></td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="filedto" property="comm"/></textarea></td>
                    </tr>                    
                </table>
                <br>
                <p class="toolbar">
                    <input type="image" src="images/icons/save.png" name="save" value="Save" onClick="validate();return returnVal;" title="save changes">
                    <input type="image" src="images/icons/cancel.png" name="back" value="Back" title="cancel">
                </p>
        </form> 
    </body>
</html>