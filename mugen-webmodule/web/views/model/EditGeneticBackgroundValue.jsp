<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>edit genetic background</title>
       <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('backname', 'string', 'Background',1 , 255);
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <form action="Controller" method="post">
            <h1>edit genetic background</h1>
            <p class="toolbar">&nbsp;</p>
                <table>
                    <tr>
                        <td><b>Background</b></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="backname" size="35" maxlength="255" value='<%=request.getAttribute("backname")%>'/>
                        </td>
                    </tr>
                </table>
                <br>
            <%--p>
                <m:save/>
            </p--%>
                <p class="toolbar">
                    <input type="image" src="images/icons/save.png" name="create" value="Save" onClick="validate();return returnVal;" title="save background">
                    <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
                </p>
        </form> 
    </body>
</html>

