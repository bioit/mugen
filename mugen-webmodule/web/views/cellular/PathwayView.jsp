<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="arx" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>JSP Page</title>
    </head>
    <body>

    <h1>Details for pathway '<jsp:getProperty name="pathway" property="name"/>'</h1>
    <form action="Controller" method="post">
        <input type="hidden" name="pathwayId" value='<jsp:getProperty name="pathway" property="pathwayId"/>'/>
         <table class="block">

            <th class="block" colspan="2">
                <arx:hide privilege="PATHWAY_W">
                    <a href="Controller?workflow=EditPathway&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>">
                    <m:img name="edit"/></a>
                    <a href="Controller?workflow=RemovePathway&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>" onClick="return confirm('Delete pathway? WARNING! ALL SURROUNDING RELATIONAL DATA FOR THE PATHWAY WILL ALSO BE REMOVED!')"><m:img name="delete" title="Delete this pathway"/></a>
                </arx:hide>
                &nbsp;
            </th>
            <tr class="block">
                <td class="block">
                    <b>Name</b><br> <jsp:getProperty name="pathway" property="name"/>
                </td>   
                <td class="block">
                    <b>Species</b><br> <jsp:getProperty name="pathway" property="species"/>
                </td>         
            </tr>
            <tr colspan="2">
                <td class="block">
                    <b>Comment</b><br><jsp:getProperty name='pathway' property='comment'/>
                </td>           
            </tr>
            <tr class="block">
                <td class="block">                
                    <b>User</b><br> <a href="Controller?workflow=ViewUser&id=<jsp:getProperty name="pathway" property="userId"/>" title="View the details for this user"><jsp:getProperty name="pathway" property="userName"/></a> 
                </td>            
                <td class="block">
                    <b>Last updated</b><br> <jsp:getProperty name="pathway" property="ts"/>
                </td>            
            </tr>           
        </table>   
        <br>
        <table class="block">
            <tr>
                <th class="block">
                    <a href="Controller?workflow=ViewPathway&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>&pathway.process_display=true" title="Expand/Collapse this section">Processes related to pathway</a>
                    <arx:hide privilege="PATHWAY_W">
                        <a href="Controller?workflow=PathwayAssignProcess&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>"><m:img name="add" title="Add a process to this pathway"/></a>
                    </arx:hide>                    
                </th>
            </tr>
            <tr>    
                <td>
                    <arx:hide-block name="pathway.process_display">
                        <table class="block_data">
                            <tr>
                                <th class="data" width="35%">Process</th>
                                <th class="data" width="35%">Comment</th>
                                <arx:hide privilege="PROCESS_W">
                                    <th class="data" width="30%">Remove from pathway</th>
                                </arx:hide>
                            </tr>
                            <arx:iterate-collection collection="processes">
                            <tr class="data #?alt#">
                                <td width="35%"><a info="View process details" href="Controller?workflow=ViewProcess&processId=#:getProcessId#">#:getName#</a></td>
                                <td width="35%">#:getComment#</td>
                                <arx:hide privilege="PATHWAY_R">
                                    <td width="30%"><a href="Controller?workflow=PathwayUnAssignProcess&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>&processId=#:getProcessId#" onClick="return confirm('Unassign process from pathway?')"><m:img name="delete"/></a></td>
                                </arx:hide>
                            </tr>
                            </arx:iterate-collection>
                        </table>
                    </arx:hide-block>
                </td>
            </tr>
        </table>
        <br>
        <table class="block">
            <tr>
                <th class="block">
                    <a href="Controller?workflow=ViewPathway&pathway.res_display=true&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>" title="Expand/Collapse this section">Resources for pathway</a>
                    <arx:hide privilege="RESOURCE_W">                            
                            <a href="Controller?workflow=PathwayAssignCategory&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>"><m:img name="add" title="Assign a resource category to this pathway"/></a>
                    </arx:hide>                                    
                </th>
            </tr>
            <tr>    
                <td>
                    <arx:hide-block name="pathway.res_display">
                        <arx:resource-list categories="categoriesAndResources" moduleId='<%=(String)request.getAttribute("pathwayId")%>' deleteWorkflow="RemovePathwayResource" editWorkflow="EditPathwayResource" createLinkWorkflow="CreatePathwayLinkResource" createFileWorkflow="CreatePathwayFileResource" categoryUnAssignWorkflow="PathwayUnAssignCategory"/>
                    </arx:hide-block>
                </td>
            </tr>
        </table>   

        <p>
            <input id="button" type="submit" name="back" value="Back">                
        </p> 
    </form>
    </body>
</html>
