<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>create strain allele</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('symbol', 'string', 'allele symbol',1 , 255);
                define('name', 'string', 'allele name',1 , 255);
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <h1>create strain allele</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
            <table>
                <tr>
                    <td><b>Allele Name</b></td>
                </tr>
                <tr>
                    <td><input type="text" name="name" size="35" maxlength="255"></td>
                </tr>
                <tr>
                    <td><b>Allele Symbol</b></td>
                </tr>
                <tr>
                    <td><input type="text" name="symbol" size="35" maxlength="255"></td>
                </tr>
                <tr>
                    <td><b>Mutation Type</b></td>
                </tr>
                <tr>
                    <td><m:checkbox collection="types" idGetter="getMutantid" textGetter="getName" name="type"/></td>
                </tr>
            </table>
            <br>
            <p class="toolbar">
                <input type="image" src="images/icons/save.png" name="save" value="Save" onClick="validate();return returnVal;" title="save allele">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
        </form>
    </body>
</html>
