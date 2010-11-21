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
    
    String noAddRem = "";
    String noGroup = "";
    String noGrouping = "";
    String noOfAvGroups = (String)request.getAttribute("noOfAvGroups");
    String noOfGroupings = (String)request.getAttribute("noOfGroupings");
    // Disable buttons if empty collections
    if(Integer.parseInt(noOfAvGroups) == 0) {
        noGroup = "disabled";
        noAddRem = "disabled";
    }
    if(Integer.parseInt(noOfGroupings) == 0) {
        noGrouping = "disabled";
        noAddRem = "disabled";
        noGroup = "disabled";
    }    
    
    String checkBox = (String)request.getAttribute("checkbox");
    if(checkBox == null)
        checkBox = "";
    String markerChecked = "";
    String markerSetChecked = "checked";
    
    if(checkBox.equals("markers")) {
        markerChecked = "checked";
        markerSetChecked = "";
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Genotype inheritance check</title>
    </head>
    <body>
    <h1>Genotype inheritance check</h1>
    <form action="Controller" method="post">
        <table>
            <tr>  
                <td>
                    Sampling unit <br><m:su-combobox onChange="this.form.submit()"/>                            
                    <input id="button" type=submit name="set_su" value="Set">
                </td> 
            </tr>
        </table>        
        <hr id="ruler200" align="left">
        <p>
            Check Inheritance for the following markers:
        </p>        
        <table>
            <tr>
                <td valign="top">                       
                    <input type="radio" <%= markerSetChecked%> name="marker" value="markerset">Marker set <br>
                        <m:checkbox collection="markersets"  idGetter="getMsid" textGetter="getName" name="msid" selected='<%=(String)request.getParameter("msid")%>' onChange="this.form.submit();"/>                           
                    <input id="button" type=submit name="set" value="Set">
                </td>
                <td valign="top">
                    <input type="radio" <%= markerChecked%> name="marker" value="markers">Markers<br>
                    <select name="availableMarkers" multiple size="10">
                        <m:iterate-collection collection="availableMarkers">
                            <option value="#:getMid#" title="#:getComm#">#:getName#</option>
                        </m:iterate-collection>
                    </select>
                </td>
                <td>
                    <input id="button" type=submit name="addMarker" value="->"><br>
                    <input id="button" type=submit name="removeMarker" value="<-"><br>
                </td>
                <td>
                    Choosen markers<br>
                    <select name="includedMarkers" multiple size="10">
                        <m:iterate-collection collection="includedMarkers">
                            <option value="#:getMid#" title="#:getComm#">#:getName#</option>
                        </m:iterate-collection>
                    </select>
                </td>
            </tr>  
        </table>
        <hr id="ruler" width="100">
        <p>
            Calculate segregation for the following groups:
        </p>
        <table>
            <tr>
                <td valign="top">                       
                    Grouping <br><m:checkbox collection="groupings"  idGetter="getGsid" textGetter="getName" name="gsid" selected='<%=(String)request.getAttribute("gsid")%>' onChange="this.form.submit();"/>
                    <input id="button" type=submit name="grps" <%=noGrouping%> value="Set">
                </td>
                <td valign="top">
                    Available groups<br>
                    <select name="availableGroups" multiple size="10">
                        <m:iterate-collection collection="availableGroups">
                            <option value="#:getGid#" title="#:getComm#">#:getName#</option>
                        </m:iterate-collection>
                    </select>
                </td>
                <td>
                    <input id="button" type=submit name="addGroup" value="->"><br>
                    <input id="button" type=submit name="removeGroup" value="<-"><br>
                </td>
                <td>
                    Choosen groups<br>
                    <select name="includedGroups" multiple size="10">
                        <m:iterate-collection collection="includedGroups">
                            <option value="#:getGid#" title="#:getComm#">#:getName#</option>
                        </m:iterate-collection>
                    </select>
                </td>
            </tr>  
        </table>   
        <hr id="ruler" width="100">
        <table>
            <td align="right">
                <input id="button" type="submit" name="check" value="Check">
            </td>
        </table>
    </form>    
    </body>
</html>
