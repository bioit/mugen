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

    <h1>Details for protein '<jsp:getProperty name="protein" property="name"/>'</h1>
    <form action="Controller" method="post">
        <input type="hidden" name="proteinId" value='<jsp:getProperty name="protein" property="proteinId"/>'/>
         <table class="block">

            <th class="block" colspan="4">
                <arx:hide privilege="PROTEIN_W">
                    <a href="Controller?workflow=EditProtein&proteinId=<jsp:getProperty name="protein" property="proteinId"/>">
                    <arx:img name="edit"/></a>
                    <a href="Controller?workflow=RemoveProtein&proteinId=<jsp:getProperty name="protein" property="proteinId"/>" onClick="return confirm('Delete protein? WARNING! ALL SURROUNDING RELATIONAL DATA FOR THE PROTEIN WILL ALSO BE REMOVED!')">
                    <arx:img name="delete" title="Delete this protein"/></a>
                    <a href="Controller?workflow=ViewProtein&proteinId=<jsp:getProperty name="protein" property="proteinId"/>&protein.history_display=true">
                    <arx:img name="history" title="Show protein data history"/></a>
                </arx:hide>
                &nbsp;
            </th>
            <tr class="block">
                <td class="block" width="25%">
                    <b>Name</b><br> <jsp:getProperty name="protein" property="name"/>
                </td>  
             <td class="block" width="25%">
                    <b>Family</b><br> 
                    <jsp:getProperty name="protein" property="familyName"/>
                </td>                  
                <td class="block" width="25%">
                    <b>Gene</b><br>                    
                     <jsp:getProperty name="protein" property="geneName"/>
                </td>  
                <td class="block" width="25%">
                    <b>Species</b><br> <jsp:getProperty name="protein" property="speciesName"/>
                </td>         
            </tr>
            <tr class="block">
                <td class="block" colspan="4">
                    <b>System involvement </b><br> <jsp:getProperty name="protein" property="numProcesses"/>
                    process(es) and <jsp:getProperty name="protein" property="numPathways"/> pathway(s)
                </td>          
            </tr>            
            <tr colspan="3">
                <td class="block">
                    <b>Comment</b><br><jsp:getProperty name='protein' property='comm'/>
                </td>           
            </tr>
            <arx:hide-block name="protein.history_display">
                <tr class="block">
                    <td class="block">                
                        <b>User</b><br> <a href="Controller?workflow=ViewUser&id=<jsp:getProperty name="protein" property="userId"/>" title="View the details for this user"><jsp:getProperty name="protein" property="userName"/></a> 
                    </td>            
                    <td class="block" colspan="3">
                        <b>Last updated</b><br> <jsp:getProperty name="protein" property="ts"/>
                    </td>            
                </tr>           
            </arx:hide-block>
        </table> 
        <br>
        <table class="block">
            <tr>
                <th class="block">
                    <a href="Controller?workflow=ViewProtein&proteinId=<jsp:getProperty name="protein" property="proteinId"/>&protein.process_display=true" title="Expand/Collapse this section">Processes involving this protein</a>
                    <arx:hide privilege="PROTEIN_W">
                        <a href="Controller?workflow=ProteinAssignProcess&id=<jsp:getProperty name="protein" property="proteinId"/>"><arx:img name="assign" title="Assign a process to this protein"/></a>
                    </arx:hide>                    
                </th>
            </tr>
            <tr>    
                <td>
                    <arx:hide-block name="protein.process_display">
                        <table class="block_data">
                            <tr>
                                <th class="block_data" width="45%">Process</th>
                                <th class="block_data" width="45%">Comment</th>
                                <arx:hide privilege="PROCESS_W">
                                    <th class="block_data" width="10%">Unassign</th>
                                </arx:hide>
                            </tr>
                            <arx:iterate-collection collection="processes">
                            <tr class="data #?alt#">
                                <td width="45%"><a info="View process details" href="Controller?workflow=ViewProcess&processId=#:getProcessId#">#:getName#</a></td>
                                <td width="45%">#:getComment#</td>
                                <arx:hide privilege="PROTEIN_R">
                                    <td width="10%"><a href="Controller?workflow=ProteinUnAssignProcess&proteinId=<jsp:getProperty name="protein" property="proteinId"/>&processId=#:getProcessId#" onClick="return confirm('Unassign process from protein?')"><arx:img name="unassign" title="Unassign the process from the protein"/></a></td>
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
                    <a href="Controller?workflow=ViewProtein&proteinId=<jsp:getProperty name="protein" property="proteinId"/>&protein.pathway_display=true" title="Expand/Collapse this section">Pathways involving this protein</a>
                    <arx:hide privilege="PROTEIN_W">
                        <a href="Controller?workflow=ProteinAssignPathway&id=<jsp:getProperty name="protein" property="proteinId"/>"><arx:img name="assign" title="Assign a pathway to this protein"/></a>
                    </arx:hide>                    
                </th>
            </tr>
            <tr>    
                <td>
                    <arx:hide-block name="protein.pathway_display">
                        <table class="block_data">
                            <tr>
                                <th class="block_data" width="45%">Pathway</th>
                                <th class="block_data" width="45%">Comment</th>
                                <arx:hide privilege="PROTEIN_W">
                                    <th class="block_data" width="10%">Unassign</th>
                                </arx:hide>
                            </tr>
                            <arx:iterate-collection collection="pathways">
                            <tr class="data #?alt#">
                                <td width="45%"><a info="View pathway details" href="Controller?workflow=ViewPathway&pathwayId=#:getPathwayId#">#:getName#</a></td>
                                <td width="45%">#:getComment#</td>
                                <arx:hide privilege="PROTEIN_R">
                                    <td width="10%"><a href="Controller?workflow=ProteinUnAssignPathway&proteinId=<jsp:getProperty name="protein" property="proteinId"/>&pathwayId=#:getPathwayId#" onClick="return confirm('Unassign pathway from protein?')"><arx:img name="unassign" title="Unassign the pathway from this protein"/></a></td>
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
                    <a href="Controller?workflow=ViewProtein&protein.res_display=true&proteinId=<jsp:getProperty name="protein" property="proteinId"/>" title="Expand/Collapse this section">Resources for protein</a>
                    <arx:hide privilege="RESOURCE_W">                            
                            <a href="Controller?workflow=ProteinAssignCategory&proteinId=<jsp:getProperty name="protein" property="proteinId"/>"><arx:img name="assign" title="Assign a resource category to this protein"/></a>
                    </arx:hide>                                    
                </th>
            </tr>
            <tr>    
                <td>
                    <arx:hide-block name="protein.res_display">
                        <arx:resource-list categories="categoriesAndResources" moduleId='<%=(String)request.getAttribute("proteinId")%>' deleteWorkflow="RemoveProteinResource" editWorkflow="EditProteinResource" createLinkWorkflow="CreateProteinLinkResource" createFileWorkflow="CreateProteinFileResource" categoryUnAssignWorkflow="ProteinUnAssignCategory"/>
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
