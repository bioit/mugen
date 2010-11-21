<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>contact</title>
       <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() {
                define('name', 'string', 'Name', 5, 255);
                define('mail', 'email', 'E-mail');
                define('message', 'string', 'Message', 5, 255);
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <h1>contact</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
                <table>
                    <tr>                                  
                        <td><b>Name:</b>&nbsp;</td>
                        <td><input type="text" name="name" size="35" maxlength="255"/></td>
                    </tr>
                    <tr><td colspan="2">&nbsp;</td></tr>
                    <tr>                                  
                        <td><b>E-mail:</b>&nbsp;</td>
                        <td><input type="text" name="mail" size="35" maxlength="255"/></td>
                    </tr>
                    <tr><td colspan="2">&nbsp;</td></tr>
                    <tr>                                  
                        <td><b>Subject:</b>&nbsp;</td>
                        <td><m:checkbox style="230px" collection="reasons" idGetter="toString" textGetter="toString" name="reason"/></td>
                    </tr>
                    <tr><td colspan="2">&nbsp;</td></tr>
                    <tr>                                  
                        <td valign="top"><b>Message:</b>&nbsp;</td>
                        <td>
                            <textarea type="text" cols="35" rows="10" name="message"></textarea>
                        </td>
                    </tr>
                </table>
                <br>
                <p class="toolbar">
                    <input type="image" src="images/icons/go.png" name="create" value="Create" onClick="validate();return returnVal;" title="go on">
                    <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
                </p>
        </form> 
    </body>
</html>

