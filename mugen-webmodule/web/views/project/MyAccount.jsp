<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="caller" scope="session" type="com.arexis.mugen.MugenCaller"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>my account</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('old', 'string', 'Old Password', 1, 35);
                define('p1', 'string', 'New Password', 4, 35);
                define('p2', 'string', 'Retyped New Password', 4, 35);
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <h1>my account for <jsp:getProperty name="caller" property="name"/></h1>
        <p class="toolbar">
            <a href="Controller?workflow=ViewMyAccount&pass.help=true"><m:img name="info_24" title="help & info"/></a>
        </p>
        <form action="Controller" method=post>
            <m:hide-block name="pass.help">
                <table>    
                    <tr>
                        <td>
                            <table class="info">
                                <td>
                                    <b>Password Tips</b><br><br>
                                    - A good password should be more than six characters long.<br>
                                    - A good password will have a mix of lowercase and uppercase characters, numbers, and punctuation marks.<br>
                                    - A password should NOT be written down on paper!<br><br>
                                    <b>Example</b><br><br>
                                    - Use some sort of sentence: <b>"The forest is green only in the summer"</b><br>
                                    - Take the first letters of the words: <b>tfigoits</b><br>
                                    - Make some capitilized: <b>tFiGoits</b><br>
                                    - Add some numbers: <b>tFiG4oits</b><br>
                                    - Add some punctuation mark: <b>tFiG4oi%ts</b><br><br>
                                </td>
                            </table>
                        </td>
                    </tr>     
                </table>     
            </m:hide-block>
            <table>
                <tr>
                    <td align="right"><b>Old Password:</b></td>
                    <td><input name="old" type="password" value="" size="35" maxlength="35"></td>
                </tr>
                <tr>
                    <td align="right"><b>New Password:</b></td>
                    <td><input name="p1" type="password" value=""></td>
                </tr>
                <tr>
                    <td align="right"><b>Retype New Password:</b></td>
                    <td><input name="p2" type="password" value=""></td>
                </tr>
            </table>
            <br>
            <p class="toolbar">
                <input type="image" src="images/icons/save.png" name="save" value="Save" onClick="validate();return returnVal;" title="save new password">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
        </form>
    </body>
</html>
