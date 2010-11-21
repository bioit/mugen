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
                    <arx:img name="edit"/></a>
                    <a href="Controller?workflow=RemovePathway&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>" onClick="return confirm('Delete pathway? WARNING! ALL SURROUNDING RELATIONAL DATA FOR THE PATHWAY WILL ALSO BE REMOVED!')"><arx:img name="delete" title="Delete this pathway"/></a>
                    <a href="Controller?workflow=ViewPathway&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>&pathway.history_display=true">
                    <arx:img name="history" title="Show pathway data history"/></a>
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
            <arx:hide-block name="pathway.history_display">
                <tr class="block">
                    <td class="block">                
                        <b>User</b><br> <a href="Controller?workflow=ViewUser&id=<jsp:getProperty name="pathway" property="userId"/>" title="View the details for this user"><jsp:getProperty name="pathway" property="userName"/></a> 
                    </td>            
                    <td class="block">
                        <b>Last updated</b><br> <jsp:getProperty name="pathway" property="ts"/>
                    </td>            
                </tr>  
            </arx:hide-block>
        </table>   
        <br>
        <table class="block">
            <tr>
                <th class="block">
                    <a href="Controller?workflow=ViewPathway&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>&pathway.process_display=true" title="Expand/Collapse this section">Processes related to pathway</a>
                    <arx:hide privilege="PATHWAY_W">
                        <a href="Controller?workflow=PathwayAssignProcess&id=<jsp:getProperty name="pathway" property="pathwayId"/>"><arx:img name="assign" title="Assign a process to this pathway"/></a>
                    </arx:hide>                    
                </th>
            </tr>
            <tr>    
                <td>
                    <arx:hide-block name="pathway.process_display">
                        <table class="block_data">
                            <tr>
                                <th class="block_data" width="45%">Process</th>
                                <th class="block_data" width="45%">Comment</th>
                                <arx:hide privilege="PATHWAY_W">
                                    <th class="block_data" width="10%">Unassign</th>
                                </arx:hide>
                            </tr>
                            <arx:iterate-collection collection="processes">
                            <tr class="data #?alt#">
                                <td width="45%"><a info="View process details" href="Controller?workflow=ViewProcess&processId=#:getProcessId#">#:getName#</a></td>
                                <td width="45%">#:getComment#</td>
                                <arx:hide privilege="PATHWAY_R">
                                    <td width="10%"><a href="Controller?workflow=PathwayUnAssignProcess&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>&processId=#:getProcessId#" onClick="return confirm('Unassign process from pathway?')"><arx:img name="unassign" title="Unassign the process from the pathway"/></a></td>
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
                    <a href="Controller?workflow=ViewPathway&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>&pathway.protein_display=true" title="Expand/Collapse this section">Proteins involved in pathway</a>
                    <arx:hide privilege="PATHWAY_W">
                        <a href="Controller?workflow=PathwayAssignProtein&id=<jsp:getProperty name="pathway" property="pathwayId"/>"><arx:img name="assign" title="Assign protein(s) to this pathway"/></a>
                    </arx:hide>                    
                </th>
            </tr>
            <tr>    
                <td>
                    <arx:hide-block name="pathway.protein_display">
                        <table class="block_data">
                            <tr>
                                <th class="block_data" width="45%">Protein</th>
                                <th class="block_data" width="45%">Comment</th>
                                <arx:hide privilege="PATHWAY_W">
                                    <th class="block_data" width="10%">Unassign</th>
                                </arx:hide>
                            </tr>
                            <arx:iterate-collection collection="proteins">
                            <tr class="data #?alt#">
                                <td width="45%"><a info="View protein details" href="Controller?workflow=ViewProtein&proteinId=#:getProteinId#">#:getName#</a></td>
                                <td width="45%">#:getComm#</td>
                                <arx:hide privilege="PATHWAY_R">
                                    <td width="10%"><a href="Controller?workflow=PathwayUnAssignProtein&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>&proteinId=#:getProteinId#" onClick="return confirm('Unassign protein from pathway?')"><arx:img name="unassign" title="Unassign the protein from the pathway"/></a></td>
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
                            <a href="Controller?workflow=PathwayAssignCategory&pathwayId=<jsp:getProperty name="pathway" property="pathwayId"/>"><arx:img name="assign" title="Assign a resource category to this pathway"/></a>
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
            <arx:back/>                
        </p> 
    </form>
    </body>
</html>
