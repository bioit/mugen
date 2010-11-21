<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Functional Significances</title>
    </head>
    <body>         
        <h1>Functional Significances</h1>    
        <form action="Controller" method="post">
        <table>
            <tr>
                <td>Sampling unit <br>
                    <m:su-combobox onChange="this.form.submit()"/>
                </td>
            <tr>
                <td align="left">                    
                    <input id="button" type="submit" name="display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>
            <tr>
                <td>
                    <br><%=request.getAttribute("message")%>
                <td>            
            </tr>
        </table>     
        <hr id="ruler">  
        <m:navigation-buttons workflow="ViewFuncSigs" showText='true'/> 
            <table class="data">
                <tr>
                    <th class="data" width="20%">Name</th>
                    <th class="data" width="10%">Type</th>
                    <th class="data" width="10%">Model</th>
                    <th class="data" width="30%">Comment</th>
                    <th class="data" width="10%">User</th>
                    <th class="data" width="10%">Updated</th>
                    <th class="data" width="10%">Details</th>
                </tr>
                <m:iterate-collection collection="funcsigs">
                    <tr class="#?alt#">
                        <td width="20%">#:getName#</td>
                        <td width="10%">#:getTypeName#</td>
                        <td width="10%"><a href="Controller?workflow=ViewModel&eid=#:getEid#" title="View the details for the model which this functional significance is linked to">#:getModelName#</a></td>
                        <td width="30%">#:getComm#</td>
                        <td width="10%"><a href="Controller?workflow=ViewUser&id=#:getUserId#" title="View the details for this user">#:getUser#</a></td>
                        <td width="10%">#:getTs#</td>
                        <td width="10%"><a href="Controller?workflow=ViewFuncSig&fsid=#:getFsid#" onClick="alert('Details page currently disabled from this view due to technical issues. \n\nPlease use the model link and then access the entry from there instead.'); return false;"><m:img name="view" title="View the details for this entry"/></a></td>
                    </tr>
                </m:iterate-collection>  
            </table> 
       <m:navigation-buttons workflow="ViewFuncSigs"/>
       </form>
    </body>
</html>
