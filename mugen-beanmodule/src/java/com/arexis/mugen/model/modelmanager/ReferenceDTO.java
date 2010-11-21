/*
 * ReferenceDTO.java
 *
 * Created on December 19, 2005, 9:54 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.reference.ReferenceRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.link.LinkRemote;
import java.io.Serializable;

/**
 * Data transfer object for a reference
 * @author lami
 */
public class ReferenceDTO implements Serializable {
    private String name, comm, resource, userName, ts, edit, type, target;
    private int linkid, fileid, refid, userId;
    
    /**
     * Creates a new instance of ReferenceDTO
     * @param reference The reference
     */
    public ReferenceDTO(ReferenceRemote reference) {
        try {
            FileRemote file = reference.getFile();
            LinkRemote link = reference.getLink();                
            
            if(file != null) {
                this.name = file.getName();
                this.comm = file.getComm();
                this.fileid = file.getFileId();
                linkid = 0;
                this.edit = "Controller?workflow=EditModelFileReference&fileid="+fileid;
                this.resource = "Controller?workflow=ViewFile&fileid="+fileid;
                type = "Document";
                target = "";
                
            } else if(link != null) {
                this.name = link.getName();
                this.comm = link.getComment();
                this.linkid = link.getLinkId();
                fileid = 0;
                this.resource = link.getUrl();                
                this.edit = "Controller?workflow=EditModelLinkReference&linkid="+linkid;
                type = "Webblink";
                target = "_blank";
            }
            
            UserRemote user = reference.getUser();
            this.userName = user.getUsr();
            this.userId = user.getId();
                        
            this.ts = reference.getTs().toString();
            this.refid = reference.getRefid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns an URL to the edit action
     * @return An URL linking to the edit action
     */
    public String getEdit() {
        return edit;
    }
    
    /**
     * Returns the target frame to open the reference in
     * @return The target frame to open the reference in
     */
    public String getTarget() {
        return target;
    }
    
    /**
     * The reference link URL
     * @return The reference URL
     */
    public String getResource() {
        return resource;
    }
    
    /**
     * Returns the type of reference, either 'Document' or 'Weblink'
     * @return The type of reference, either 'Document' or 'Weblink'
     */
    public String getType() {
        return type;        
    }
    
    /**
     * The name of the reference
     * @return The name of the reference
     */
    public String getName() {
        return name;
    }
    
    /**
     * The comment for the reference
     * @return The comment for the reference
     */
    public String getComm() {
        return comm;
    }
    
    /**
     * The id of the link
     * @return The id of the link
     */
    public int getLinkId() {
        return linkid;
    }
    
    /**
     * The id of the file
     * @return The id of the file
     */
    public int getFileId() {
        return fileid;
    }
    
    /**
     * The id of the reference
     * @return The id of the reference
     */
    public int getRefid() {
        return refid;
    }
    
    /**
     * The username of the user that made the last changes on the reference
     * @return The username of the user that made the last changes on the reference
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * Returns the id of the user that made the last changes
     * @return The id of the user that made the last changes on the reference
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * Returns the date for when the reference was last modified
     * @return The date for the last modification of the reference
     */
    public String getTs() {
        return ts;
    }
}
