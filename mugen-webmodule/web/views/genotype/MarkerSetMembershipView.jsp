<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Marker set membership</title>
    </head>
    <body>
    <h1>Marker set membership</h1>
    <form action="Controller" method="post">
        <table>
            <tr>  
                <td>
                    Sampling unit <br><m:su-combobox onChange="this.form.submit();"/>                            
                    <input id="button" type=submit name="suids" value="Set">
                </td>          
                <td>
                    Chromosome <br><m:checkbox collection="chromosomes"  wildcardOption='true' idGetter="getCid" textGetter="getName" name="cid" selected='<%=fdm.getValue("cid")%>' onChange="this.form.submit();"/>                            
                    <input id="button" type=submit name="chromosomes" value="Set">
                </td>
                <td>                       
                    Marker set <br><m:checkbox collection="markersets"  idGetter="getMsid" textGetter="getName" name="msid" selected='<%=fdm.getValue("msid")%>' onChange="this.form.submit();"/>                           
                    <input id="button" type=submit name="display" value="Display">
                </td>
            </tr>          
        </table>
        <hr id="ruler">
        <table>
            <tr>
                <td>
                    Available markers<br>
                    <select name="available" multiple size="20">
                        <m:iterate-collection collection="available">
                            <option value="#:getMid#" title="#:getComm#">#:getName#</option>
                        </m:iterate-collection>
                    </select>
                </td>
                <td>
                    <input id="button" type=submit name="add" value="->"><br>
                    <input id="button" type=submit name="remove" value="<-"><br>
                </td>
                <td>
                    Included markers<br>
                    <select name="included" multiple size="20">
                        <m:iterate-collection collection="included">
                            <option value="#:getMid#" title="#:getMname#">#:getMname#</option>
                        </m:iterate-collection>
                    </select>
                </td>
            </tr>  
        </table>
    </form>    
    </body>
</html>
