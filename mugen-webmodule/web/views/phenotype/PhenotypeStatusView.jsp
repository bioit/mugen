<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Phenotype filter</title>
    </head>
    <body>
        <h1>Phenotype status</h1>
        <form action="Controller" method="post">
            <table> 
                <td>
                    Filter <br>
                    <m:checkbox collection="filters" name="fid" idGetter="getFid" textGetter="getName" selected='<%=(String)request.getAttribute("currentFid")%>'/>
                </td>
                <td>Sampling unit <br>
                    <m:su-combobox onChange="this.form.submit()"/>
                </td>                                 
                <td>
                    Variable set <br>
                    <m:checkbox collection="variablesets" name="vsid" idGetter="getVsid" textGetter="getName" />
                    <input id="button" type="submit" name="display" value="Display">
                </td>  
            </table>
            <hr id="ruler">
        </form> 
    </body>
</html>
