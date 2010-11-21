<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>add mutation type</title>
    </head>
    <body class="content">
        <h1>add mutation type</h1>
        <p class="toolbar">&nbsp;</p>  
        <form action="Controller" method="post">
            <table>
                <tr>
                    <td><b>Mutation Type</b></td>
                </tr>
                <tr>
                    <td><m:checkbox collection="types" emptyOption="false" idGetter="getMutantid" textGetter="getName" name="type"/> </td>
                </tr>
            </table>
            <br>
            <p class="toolbar">
                <input type="image" src="images/icons/add.png" name="add" value="Save" onClick="validate();return returnVal;" title="add mutation type">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
        </form>
    </body>
</html>
