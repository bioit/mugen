<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.Collection" %> 
<%
    java.util.Collection groupingsdto = (java.util.Collection)request.getAttribute("groupingsdto");  
    java.util.Collection samplingunitdto = (java.util.Collection)request.getAttribute("samplingunitdto");
    java.util.Collection groupdto = (java.util.Collection)request.getAttribute("groupdto");
    java.util.Collection availableInds = (java.util.Collection)request.getAttribute("availableInds");
    java.util.Collection includedInds = (java.util.Collection)request.getAttribute("includedInds");
    String noAddRem = "";
    String noGroup = "";
    String noGrouping = "";
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
    
    // Disable buttons if empty collections
    if(groupdto.size() == 0) {
        noGroup = "disabled";
        noAddRem = "disabled";
    }
    if(groupingsdto.size() == 0) {
        noGrouping = "disabled";
        noAddRem = "disabled";
        noGroup = "disabled";
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Group membership</title>
    </head>
    <body>
    <h1>Group membership</h1>
    <form action="Controller" method="post">
        <table>
            <tr>            
                <td>
                    Sampling unit <br><m:su-combobox onChange="this.form.submit();"/>
                    <input id="button" type=submit name="su" value="Set">
                </td>
                <td>                       
                    Grouping <br><m:checkbox collection="groupingsdto"  idGetter="getGsid" textGetter="getName" name="gsid" selected='<%=fdm.getValue("gsid")%>' onChange="this.form.submit();"/>
                    <input id="button" type=submit name="grps" <%=noGrouping%> value="Set">
                </td>
                <td>                       
                    Group <br><m:checkbox collection="groupdto"  idGetter="getGid" textGetter="getName" name="gid" selected='<%=fdm.getValue("gid")%>' onChange="this.form.submit();"/>                          
                </td>
                <td>
                    Sex <br><m:sex value='<%=fdm.getValue("sex")%>' wildcard="true"/>
                </td>                       
            </tr>       
            <tr>
                <td>            
                    Identity <br><input type="text" name="identity" size="15" value='<%=fdm.getValue("identity")%>'/>
                </td>            
                <td>
                    Father <br><input type="text" name="father" size="15" value='<%=fdm.getValue("father")%>'/>
                </td>              
                <td>
                    Mother <br><input type="text" name="mother" size="15" value='<%=fdm.getValue("mother")%>'/>
                </td>    
                <td>
                    Alias <br><input type="text" name="alias" size="15" value='<%=fdm.getValue("alias")%>'/>
                </td>  
            </tr>
            <tr>
                <td colspan=4 align="right">                     
                    <input id="button" type="submit" name="collect.getgroupmembershipaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>          
        </table>
        <table>
            <tr>
                <td>
                    Available individuals<br>
                    <select name="available" multiple size="20">
                        <m:iterate-collection collection="availableInds">
                            <option value="#:getIid#" title="#:getComm#">#:getIdentity#</option>
                        </m:iterate-collection>
                    </select>
                </td>
                <td>
                    <input id="button" type=submit name="add" <%=noAddRem%> value="->"><br>
                    <input id="button" type=submit name="remove" <%=noAddRem%> value="<-"><br>
                </td>
                <td>
                    Included individuals<br>
                    <select name="included" multiple size="20">
                        <m:iterate-collection collection="includedInds">
                            <option value="#:getIid#" title="#:getComm#">#:getIdentity#</option>
                        </m:iterate-collection>
                    </select>
                </td>
            </tr>  
        </table>
        <p>
            <input id="button" type=submit name=back value=Back>
        </p>
    </form>    
    </body>
</html>
