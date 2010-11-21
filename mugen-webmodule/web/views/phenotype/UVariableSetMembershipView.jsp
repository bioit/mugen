<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");             
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller"); 
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Unified Variable Set membership</title>
    </head>
    <body>
    <h1>Unified Variable Set membership</h1>
    <form action="Controller" method="post">
        <table>
            <tr>     
                <td>
                    Species <br><m:checkbox collection="species"  idGetter="getSid" textGetter="getName" name="sid" selected='<%=caller.getSid()%>' onChange="this.form.submit();"/>                            
                    <input id="button" type=submit name="sids" value="Set">
                </td>          
                <td>                       
                    Unified Variable set <br><m:checkbox collection="uvariablesets"  idGetter="getUvsid" textGetter="getName" name="uvsid" selected='<%=fdm.getValue("uvsid")%>' onChange="this.form.submit();"/>
                    <input id="button" type=submit name="display" value="Display">
                <td>
            </tr> 
        </table>
        <hr id="ruler">
        <table>
            <tr>
                <td>
                    Available unified variables<br>
                    <select name="available" multiple size="20">
                        <m:iterate-collection collection="availableUvars">
                            <option value="#:getUvid#" title="#:getComm#">#:getName#</option>
                        </m:iterate-collection>
                    </select>
                </td>
                <td>
                    <input id="button" type=submit name="add" value="->"><br>
                    <input id="button" type=submit name="remove" value="<-"><br>
                </td>
                <td>
                    Included unified variables<br>
                    <select name="included" multiple size="20">
                        <m:iterate-collection collection="includedUvars">
                            <option value="#:getUvid#" title="#:getComm#">#:getName#</option>
                        </m:iterate-collection>
                    </select>
                </td>
            </tr>  
        </table>
        <p>
        <input id="button" type="submit" name="back" value="Back">
        </p>
    </form>    
    </body>
</html>
