<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="linkdto" scope="request" class="com.arexis.mugen.resource.resourcemanager.LinkDTO" />   
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>edit link</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() {
                define('name', 'string', 'Name', 1, 255);
                define('url', 'string', 'URL', 8, 255);
                define('comm', 'string', 'Comment', null, 255);
            }    
        </script>         
    </head>
    <body onLoad="init()" class="content">
        <h1>edit link</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
                <table>
                    <tr>                                      
                        <td><b>Name</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="55" maxlength="255" value='<jsp:getProperty name="linkdto" property="name"/>'/></td>
                    </tr>
                    <tr>                                      
                        <td><b>URL</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="url" size="55" maxlength="255" value='<jsp:getProperty name="linkdto" property="url"/>'/></td>
                    </tr>                    
                    <tr>
                        <td><b>Comment</b></td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="55" rows="4" name="comm"><jsp:getProperty name="linkdto" property="comm"/></textarea></td>
                    </tr>                    
                </table>
                <br>
                <p class="toolbar">
                    <input type="image" src="images/icons/save.png" name="save" value="Save" title="save changes" onClick="validate();return returnVal;">
                    <input type="image" src="images/icons/cancel.png" title="cancel" name="back" value="Back">
                </p>
        </form> 
    </body>
</html>