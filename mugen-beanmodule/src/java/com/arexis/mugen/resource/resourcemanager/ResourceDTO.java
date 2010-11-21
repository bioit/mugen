/*
 * ResourceDTO.java
 *
 * Created on January 18, 2006, 2:36 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.resource.resourcemanager;

import com.arexis.mugen.resource.resource.ResourceRemote;

/**
 *
 * @author lami
 */
public class ResourceDTO {
    private String resourceName, resourceLink, resourceType, resourceComment, user;
    private int resourceId, userId;
    private int categoryId;
    private String categoryName;
    
    /** Creates a new instance of ResourceDTO */
    public ResourceDTO(ResourceRemote resource) {
        try {
            resourceName = resource.getName();
            resourceLink = resource.getResourceLink();
            resourceType = resource.getResourceType();
            resourceId = resource.getResourceId();
            resourceComment = resource.getComment();
            user = resource.getUser().getUsr();
            userId = resource.getUser().getId();
            
            categoryId = resource.getResourceCategory().getResourceCategoryId();
            categoryName = resource.getResourceCategory().getName();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getUser() {
        return user;
    }
    
    public String getUserId() {
        return ""+userId;
    }    

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getResourceId() {
        return resourceId;
    }
    
    public String getResourceComment() {
        return resourceComment;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
}
