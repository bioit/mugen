<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Unified Marker set membership</title>
    </head>
    <body>
    <h1>Unified Marker set membership</h1>
    <form action="Controller" method="post">
        <table>
            <tr>                               
                <td>
                    Species <br><m:checkbox collection="species" name="sid" idGetter="getSid" textGetter="getName" selected='<%=fdm.getValue("sid")%>' onChange="this.form.submit();"/>              
                    <input id="button" type="submit" name="set" value="Set">
                </td>                      
                <td>                       
                    Unified Marker set<br>
                    <m:checkbox collection="umarkersets"  idGetter="getUmsid" textGetter="getName" name="umsid" selected='<%=fdm.getValue("umsid")%>' onChange="this.form.submit();"/>                                                   
                    <input id="button" type=submit name="display" value="Display">
                </td>
            </tr>
        </table>
        <hr id="ruler">
        <table>
            </tr>             
            <tr>
                <td>
                    Available markers<br>
                    <select name="available" multiple size="20">
                        <m:iterate-collection collection="available">
                            <option value="#:getUmid#" title="#:getComm#">#:getName#</option>
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
                            <option value="#:getUmid#" title="#:getUmname#">#:getUmname#</option>
                        </m:iterate-collection>
                    </select>
                </td>
            </tr>  
        </table>
    </form>    
    </body>
</html>
