<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="straindto" scope="request" type="com.arexis.mugen.model.modelmanager.StrainDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>edit strain</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() {
                define('designation', 'string', 'Designation', 1 , 255);
                define('mgiid', 'num', 'JAX ID', 0, 35);
                define('emmaid', 'num', 'EMMA ID', 0, 35);
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <h1>edit strain</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
            <table>
            <tr>
                <td><b>Designation</b></td>
            </tr>
            <tr>
                <td>
                    <input type="text" name="designation" value="<jsp:getProperty name="straindto" property="designation"/>" maxlength="255" size="35">
                </td>
            </tr>
            <tr>
                <td><b>JAX ID</b></td>
            </tr>
            <tr>
                <td>
                    <input type="text" name="mgiid" value="<jsp:getProperty name="straindto" property="mgiId"/>" maxlength="35" size="35">
                </td>
            </tr>
            <tr>
                <td><b>EMMA ID</b></td>
            </tr>
            <tr>
                <td>
                    <input type="text" name="emmaid" value="<jsp:getProperty name="straindto" property="emmaid"/>" maxlength="35" size="35">
                </td>
            </tr>
            </table>
            <br>
            <p class="toolbar">
                <input type="image" src="images/icons/save.png" name="save" value="Save" onClick="validate();return returnVal;" title="save strain">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
        </form>
    </body>
</html>
