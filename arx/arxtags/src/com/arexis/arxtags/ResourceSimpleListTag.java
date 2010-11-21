/*
 * ResourceListTag.java
 *
 * Created on January 19, 2006, 2:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.arxtags;

import com.arexis.arxframe.Caller;
import com.arexis.mugen.resource.resourcemanager.ResourceBranchDTO;
import com.arexis.mugen.resource.resourcemanager.ResourceDTO;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Builds HTML for handling of resources...
 *
 *
 * @author lami
 */
public class ResourceSimpleListTag extends BodyTagSupport {
    
    private Collection resourceTree;
    
    private String projectId, createWorkflow;
    
    private String editWorkflow = "EditModelResource";
    private String deleteWorkflow = "RemoveModelResource";
    
    /** Creates a new instance of ResourceListTag */
    public ResourceSimpleListTag() {
    }
    
    /**
     * Collection of resources that should be displayed
     */
    public void setResourceTreeCollection(String collectionName) {
        this.resourceTree = (Collection)pageContext.getRequest().getAttribute(collectionName);  
    }  
    
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }  
    
    public void setCreateWorkflow(String createWorkflow)
    {
        this.createWorkflow = createWorkflow;
    }
    
    
    
    
    
    /**
     * Builds the HTML code for the tag
     * @throws javax.servlet.jsp.JspException If the HTML code could not be created
     * @return The BodyTagSupport.SKIP_BODY integer value if everything went fine
     */
    public int doStartTag() throws JspException 
    {
        String data = "";
        ImageTag imageTag = new ImageTag();
        try
        {
            Caller caller = (Caller)pageContext.getSession().getAttribute("caller");
            
            data += "<table class=\"block_data\">\n";
            data += "<tr>\n";
            data += "<th class=\"data2\" width=\"30%\">Name</th>\n";
            data += "<th class=\"data2\" width=\"10%\">Type</th>\n";
            if(caller.hasPrivilege("MODEL_W") || caller.isAdmin()) {
            data += "<th class=\"data2\" width=\"20%\">user</th>\n";
            }
            if(caller.hasPrivilege("RESOURCE_W") || caller.isAdmin()) {
                data += "<th class=\"data2\" width=\"10%\">&nbsp;</th>\n";
                data += "<th class=\"data2\" width=\"10%\">&nbsp;</th>\n";
            }
            data += "</tr>\n";
            
            Iterator i = resourceTree.iterator();
            while(i.hasNext()) {
                ResourceBranchDTO branch = (ResourceBranchDTO)i.next();
                Collection resources = branch.getCatResources();
                data += "<tr>";
                data += "<td colspan=5><b>"+branch.getCatName()+"</b></td>";
                data += "</tr>";
                int ctr = 0;
                String className = "alternatingOne";
                
                Iterator j = resources.iterator();
                while(j.hasNext()) {
                    ResourceDTO resource = (ResourceDTO)j.next();
                    String type = resource.getResourceType();
                    
                    
                    if(ctr == 1) {
                        className = "alternatingTwo";
                        ctr = 0;
                    }
                    else {
                        ctr++;
                        className = "alternatingOne";
                    }
                    
                    data += "<tr class=\""+className+"\">";
                    //data += "<td width=\"20%\">&nbsp</td>";
                    
                    String target = "_blank";
                    /*
                    if(type.equalsIgnoreCase("File"))
                        target = "";
                     */
                    data += "<td width=\"30%\"><a href=\""+resource.getResourceLink()+"\" title=\""+resource.getResourceComment()+"\" target=\""+target+"\">"+resource.getResourceName()+"</a></td>";
                    data += "<td width=\"10%\">"+resource.getResourceType()+"</td>";
                    if(caller.hasPrivilege("MODEL_W") || caller.isAdmin()) {
                    data += "<td width=\"20%\"><a href=\"Controller?workflow=ViewUser&id="+resource.getUserId()+"\">"+resource.getUser()+"</a></td>";
                    }
                    if(caller.hasPrivilege("RESOURCE_W") || caller.isAdmin()) {                        
                        int resourceId = resource.getResourceId();
                        
                        
                        String editLink = "Controller?workflow="+editWorkflow+"Link&resourceId="+resourceId;
                        if(type.equalsIgnoreCase("File"))
                            editLink = "Controller?workflow="+editWorkflow+"File&resourceId="+resourceId;
                        
                        data += "<td width=\"10%\"><a class=\"menu\" href=\""+editLink+"\" title=\"edit resource\">&nbsp;[edit]</a></td>";
                        data += "<td width=\"10%\"><a class=\"menu\" href=\"Controller?workflow="+deleteWorkflow+"&resourceId="+resourceId+"\" onClick=\"return confirm('Delete resource?')\" title=\"delete resource\">&nbsp;[del]</a></td>";
                    }           
                    data += "</tr>";
                }          
            }
            
            data += "</table>";
            
            // Print the table on the page
            pageContext.getOut().print(data);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new JspTagException(e.getMessage());
        }
        
        return SKIP_BODY;
    }    
}
