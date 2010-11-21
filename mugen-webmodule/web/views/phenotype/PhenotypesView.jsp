<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Phenotypes</title>
    </head>
    <body>         
        <h1>Phenotypes</h1>             
        <form action="Controller" method="post">
        <table>
            <tr>
                <td>Sampling unit <br>
                    <m:su-combobox/>
                </td>
                <td>            
                    Identity <br><input type="text" name="identity" size="15" value='<%=fdm.getValue("identity")%>'/>
                </td>
                <td>            
                    Variable <br><input type="text" name="name" size="15" value='<%=fdm.getValue("name")%>'/>
                </td>
            </tr>  
            <tr>
                <td colspan=3 align="right">                    
                    <input id="button" type="submit" name="collect.getphenotypesaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>
        </table>                    
        <hr id="ruler">         
        <m:navigation-buttons workflow="ViewPhenotypes" showText='true'/> 
        <table class="data">
        <tr>
            <th class="data">Identity</th>            
            <th class="data">Accession nr</th>
            <th class="data">Variable</th>
            <th class="data">Value</th>
            <th class="data">Reference</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Details</th>
        </tr>
        <m:iterate-collection collection="phenotypesdto">
        <tr class="#?alt#">
            <td><a href="Controller?workflow=ViewIndividual&iid=#:getEid#">#:getIdentity#</a></td>
            <td><a href="Controller?workflow=ViewModel&eid=#:getEid#">#:getAccNr#</a></td>
            <td><a href="Controller?workflow=ViewVariable&vid=#:getVid#">#:getVariable#</a></td>
            <td>#:getValue#</td>
            <td>#:getReference#</td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>
            <td><a href="Controller?workflow=ViewPhenotype&vid=#:getVid#&iid=#:getIid#"><m:img name="view"/></a></td>
        </tr>
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewPhenotypes"/>
       </form>
    </body>
</html>
