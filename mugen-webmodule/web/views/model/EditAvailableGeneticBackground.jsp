<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>edit available background</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('avgenbackname', 'string', 'Background', 1, 255);
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <h1>edit available background</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
                <table>
                    <tr>
                        <td><b>Background</b></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="avgenbackname" size="35" maxlength="255" value='<jsp:getProperty name="avgenback" property="avbackname"/>'/>
                        </td>
                    </tr>
                </table>
                <br>
            <%--p>
                <m:save saveName="create" cancelName="back" onClick="validate();return returnVal;"/>
            </p--%>
            <p class="toolbar">
                <input type="image" src="images/icons/save.png" name="create" value="Create" onClick="validate();return returnVal;" title="save background">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
        </form> 
    </body>
</html>

