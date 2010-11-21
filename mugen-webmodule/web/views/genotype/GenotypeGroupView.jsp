<%
    
    String header1 = (String)request.getAttribute("header1");
    String header2 = (String)request.getAttribute("header2");
    String header3 = (String)request.getAttribute("header3"); 
    
    if(header1 == null || header1.length() == 0)
        header1 = "None";
    if(header2 == null || header2.length() == 0)
        header2 = "None";
    if(header3 == null || header3.length() == 0)
        header3 = "None";         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Genotype filter</title>
    </head>
    <body onLoad="init()">
        <h1>Genotype group view</h1>
        <form action="Controller" method="post">
            <table> 
            <tr>
                <td>Sampling unit <br>
                    <m:su-combobox onChange="this.form.submit()"/>
                    <input id="button" type="submit" name="set" value="Set">
                </td>                                 
                <td>
                    Grouping <br>
                    <m:checkbox collection="groupings" name="gsid" idGetter="getGsid" textGetter="getName" selected='<%=(String)request.getAttribute("gsid")%>' onChange="this.form.submit()"/>
                </td> 
                <td>
                    Group <br>
                    <m:checkbox collection="groups" name="gid" idGetter="getGid" textGetter="getName" selected='<%=(String)request.getAttribute("gid")%>' />
                </td>     
            </tr>
            <tr>
                <td>
                    Marker 1 <br>
                    <m:checkbox collection="markers" emptyOption="true" name="mid1" idGetter="getMid" textGetter="getName" />
                </td> 
                <td>
                    Marker 2 <br>
                    <m:checkbox collection="markers" emptyOption="true" name="mid2" idGetter="getMid" textGetter="getName" />
                </td> 
                <td>
                    Marker 3 <br>
                    <m:checkbox collection="markers" emptyOption="true" name="mid3" idGetter="getMid" textGetter="getName" />
                    <input id="button" type="submit" name="display" value="Display">
                </td>                 
            </tr>
            </table>
            <hr id="ruler">
            <table class="data">
                <tr>
                    <th class="data">Identity</th>
                    <th class="data">Father</th>
                    <th class="data">Mother</th>
                    <th class="data"><%=header1%></th>
                    <th class="data"><%=header2%></th>
                    <th class="data"><%=header3%></th>
                </tr>
                <m:iterate-collection collection="pedigree">
                <tr class="#?alt#">
                    <td>#:getIdentity#</td>
                    <td>#:getFather#</td>
                    <td>#:getMother#</td>
                    <td>#:getMname1#</td>
                    <td>#:getMname2#</td>
                    <td>#:getMname3#</td>
                </tr>
                </m:iterate-collection>      
            </table>
            <hr id="ruler">                
            <p>Export pedigree: 
            <a href="Controller?workflow=ExportPedigree&format=gml">GML</a> |
            <a href="Controller?workflow=ExportPedigree&format=xgml">XGML</a> | GXL | GraphML
            <!-- <a href="Controller?workflow=ExportPedigree&format=gxl">GXL</a> |        
            <a href="Controller?workflow=ExportPedigree&format=graphml">GraphML</a>            
            -->
            </p>
        </form> 
    </body>
</html>
