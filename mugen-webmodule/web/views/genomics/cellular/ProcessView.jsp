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

    <h1>Details for process '<jsp:getProperty name="process" property="name"/>'</h1>
    <form action="Controller" method="post">
        <input type="hidden" name="processId" value='<jsp:getProperty name="process" property="processId"/>'/>
         <table class="block">

            <th class="block" colspan="2">
                <arx:hide privilege="PROCESS_W">
                    <a href="Controller?workflow=EditProcess&processId=<jsp:getProperty name="process" property="processId"/>">
                    <arx:img name="edit"/></a>
                    <a href="Controller?workflow=RemoveProcess&processId=<jsp:getProperty name="process" property="processId"/>" onClick="return confirm('Delete process? WARNING! ALL SURROUNDING RELATIONAL DATA FOR THE PROCESS WILL ALSO BE REMOVED!')"><arx:img name="delete" title="Delete this process"/></a>
                    <a href="Controller?workflow=ViewProcess&processId=<jsp:getProperty name="process" property="processId"/>&process.history_display=true">
                    <arx:img name="history" title="Show process data history"/></a>                    
                </arx:hide>
                &nbsp;
            </th>
            <tr class="block">
                <td class="block">
                    <b>Name</b><br> <jsp:getProperty name="process" property="name"/>
                </td>   
                <td class="block">
                    <b>Species</b><br> <jsp:getProperty name="process" property="species"/>
                </td>         
            </tr>
            <tr colspan="2">
                <td class="block">
                    <b>Comment</b><br><jsp:getProperty name='process' property='comment'/>
                </td>           
            </tr>
            <arx:hide-block name="process.history_display">
                <tr class="block">
                    <td class="block">                
                        <b>User</b><br> <a href="Controller?workflow=ViewUser&id=<jsp:getProperty name="process" property="userId"/>" title="View the details for this user"><jsp:getProperty name="process" property="userName"/></a> 
                    </td>            
                    <td class="block">
                        <b>Last updated</b><br> <jsp:getProperty name="process" property="ts"/>
                    </td>            
                </tr>           
            </arx:hide-block>
        </table>   
        <br>
        <table class="block">
            <tr>
                <th class="block">
                    <a href="Controller?workflow=ViewProcess&processId=<jsp:getProperty name="process" property="processId"/>&process.pathway_display=true" title="Expand/Collapse this section">Pathways in process</a>
                    <arx:hide privilege="PROCESS_W">
                        <a href="Controller?workflow=ProcessAssignPathway&id=<jsp:getProperty name="process" property="processId"/>"><arx:img name="assign" title="Assign a pathway to this process"/></a>
                    </arx:hide>                    
                </th>
            </tr>
            <tr>    
                <td>
                    <arx:hide-block name="process.pathway_display">
                        <table class="block_data">
                            <tr>
                                <th class="block_data" width="45%">Pathway</th>
                                <th class="block_data" width="45%">Comment</th>
                                <arx:hide privilege="PROCESS_W">
                                    <th class="block_data" width="10%">Unassign</th>
                                </arx:hide>
                            </tr>
                            <arx:iterate-collection collection="pathways">
                            <tr class="data #?alt#">
                                <td width="45%"><a info="View pathway details" href="Controller?workflow=ViewPathway&pathwayId=#:getPathwayId#">#:getName#</a></td>
                                <td width="45%">#:getComment#</td>
                                <arx:hide privilege="PROCESS_R">
                                    <td width="10%"><a href="Controller?workflow=ProcessUnAssignPathway&processId=<jsp:getProperty name="process" property="processId"/>&pathwayId=#:getPathwayId#" onClick="return confirm('Unassign pathway from process?')"><arx:img name="unassign" title="Unassign the pathway from this process"/></a></td>
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
                    <a href="Controller?workflow=ViewProcess&processId=<jsp:getProperty name="process" property="processId"/>&process.protein_display=true" title="Expand/Collapse this section">Proteins involved in process</a>
                    <arx:hide privilege="PROCESS_W">
                        <a href="Controller?workflow=ProcessAssignProtein&id=<jsp:getProperty name="process" property="processId"/>"><arx:img name="assign" title="Assign protein(s) to this process"/></a>
                    </arx:hide>                    
                </th>
            </tr>
            <tr>    
                <td>
                    <arx:hide-block name="process.protein_display">
                        <table class="block_data">
                            <tr>
                                <th class="block_data" width="45%">Protein</th>
                                <th class="block_data" width="45%">Comment</th>
                                <arx:hide privilege="PROCESS_W">
                                    <th class="block_data" width="10%">Unassign</th>
                                </arx:hide>
                            </tr>
                            <arx:iterate-collection collection="proteins">
                            <tr class="data #?alt#">
                                <td width="45%"><a info="View protein details" href="Controller?workflow=ViewProtein&proteinId=#:getProteinId#">#:getName#</a></td>
                                <td width="45%">#:getComm#</td>
                                <arx:hide privilege="PROCESS_R">
                                    <td width="10%"><a href="Controller?workflow=ProcessUnAssignProtein&processId=<jsp:getProperty name="process" property="processId"/>&proteinId=#:getProteinId#" onClick="return confirm('Unassign protein from process?')"><arx:img name="unassign" title="Unassign the protein from the process"/></a></td>
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
                    <a href="Controller?workflow=ViewProcess&process.res_display=true&processId=<jsp:getProperty name="process" property="processId"/>" title="Expand/Collapse this section">Resources for process</a>
                    <arx:hide privilege="RESOURCE_W">                            
                            <a href="Controller?workflow=ProcessAssignCategory&processId=<jsp:getProperty name="process" property="processId"/>"><arx:img name="assign" title="Assign a resource category to this process"/></a>
                    </arx:hide>                                    
                </th>
            </tr>
            <tr>    
                <td>
                    <arx:hide-block name="process.res_display">
                        <arx:resource-list categories="categoriesAndResources" moduleId='<%=(String)request.getAttribute("processId")%>' deleteWorkflow="RemoveProcessResource" editWorkflow="EditProcessResource" createLinkWorkflow="CreateProcessLinkResource" createFileWorkflow="CreateProcessFileResource" categoryUnAssignWorkflow="ProcessUnAssignCategory"/>
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
