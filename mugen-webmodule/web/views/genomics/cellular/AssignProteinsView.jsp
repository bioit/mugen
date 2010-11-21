<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Assign proteins</title>
    </head>
    <body>
    <h1>Assign proteins</h1>
    <form action="Controller" method="post">
        <input type="hidden" name="id" value='<%=(String)request.getAttribute("id")%>'/>
        <table>
            <tr>
                <td>
                    Available proteins<br>
                    <select name="proteins" multiple size="20">
                        <m:iterate-collection collection="proteins">
                            <option value="#:getProteinId#" title="#:getComm#">#:getName#</option>
                        </m:iterate-collection>
                    </select>                    
                </td>
            </tr>            
        </table>
        <p>
            <m:save saveName="assign" cancelName="back"/>
        </p>
    </form>    
    </body>
</html>
