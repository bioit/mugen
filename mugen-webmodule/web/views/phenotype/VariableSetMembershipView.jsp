<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Variable set membership</title>
    </head>
    <body>
    <h1>Variable set membership</h1>
    <form action="Controller" method="post">
        <hr id="ruler">
        <table>
            <tr>
                <td>
                    Available variables<br>
                    <select name="available" multiple size="20">
                        <m:iterate-collection collection="availableVars">
                            <option value="#:getVid#" title="#:getComm#">#:getName#</option>
                        </m:iterate-collection>
                    </select>
                </td>
                <td>
                    <input id="button" type=submit name="add" value="->"><br>
                    <input id="button" type=submit name="remove" value="<-"><br>
                </td>
                <td>
                    Included variables<br>
                    <select name="included" multiple size="20">
                        <m:iterate-collection collection="includedVars">
                            <option value="#:getVid#" title="#:getComm#">#:getName#</option>
                        </m:iterate-collection>
                    </select>
                </td>
            </tr>  
        </table>
    </form>    
    </body>
</html>
