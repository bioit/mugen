/*
 * FunctionalSignificanceDTO.java
 *
 * Created on December 16, 2005, 2:41 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemote;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.resource.file.FileRemote;
import java.io.Serializable;

/**
 * Data transfer object for a functional significance
 * @author lami
 */
public class FunctionalSignificanceDTO implements Serializable {
    private String name, comm, user, ts, fileName, typeName, modelName;
    private int fsid, userId, fileId, fstid, eid, suid;
    
    /**
     * Creates a new instance of FunctionalSignificanceDTO
     * @param funcSig The functional significance
     */
    public FunctionalSignificanceDTO(FunctionalSignificanceRemote funcSig) {
        try {
            this.name = funcSig.getName();
            this.comm = funcSig.getComm();
            UserRemote usr = funcSig.getUser();
            this.user = usr.getUsr();
            this.userId = usr.getId();
            this.ts = funcSig.getTs().toString();
            this.fsid = funcSig.getFsid();
            this.fstid = 0;
            ExpModelRemote model = funcSig.getModel();
            this.eid = model.getEid();
            this.modelName = model.getIdentity();
            this.suid = model.getSamplingUnit().getSuid();
            
            FileRemote file = funcSig.getFile();
            if(file != null) {
                this.fileName = file.getName();
                this.fileId = file.getFileId();
            }
            else
                fileName = "";
            FunctionalSignificanceTypeRemote type = funcSig.getType();
            if(type != null) {
                typeName = type.getName();
                fstid = type.getFstid();
            }
            else
                typeName = "";
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the id of the model that the functional significance belongs to
     * @return The model id
     */
    public int getEid() {
        return eid;
    }
    
    /**
     * Returns the name of the model that the functional significance belongs to
     * @return The name of the model
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Returns the name of the functional significance
     * @return The name of the functional significance
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the comment for the functional significance
     * @return The comment
     */
    public String getComm() {
        return comm;
    }

    /**
     * Returns the username of the user that made the last changes on the functional significance
     * @return The username of the user that made the last changes on the functional significance
     */
    public String getUser() {
        return user;
    }

    /**
     * Returns the date for when the functional signifiance was last changed
     * @return The date for when the functional significance was last changed
     */
    public String getTs() {
        return ts;
    }

    /**
     * Returns the filename for the attached file (or blank if none attached)
     * @return The filename
     */
    public String getFileName() {
        if(fileName == null || fileName.length() == 0)
            fileName = "";
        return fileName;
    }

    /**
     * Returns the name of the type of functional significance this is
     * @return The name of the functional significance type
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Returns the id of the functional significance
     * @return The id of the functional significance
     */
    public int getFsid() {
        return fsid;
    }
    
    /**
     * Returns the id of the functional signifiance type
     * @return The functional significance id
     */
    public int getTypeId() {
        return fstid;
    }    

    /**
     * Returns the id of the user that made the last changes on the functional significance
     * @return The id of the user that made the last changes on the functional significance
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the id for the file attached to the functional significance
     * @return The id of the attached file
     */
    public int getFileId() {
        return fileId;
    }
    
    public int getSuid()
    {
        return suid;
    }
}
