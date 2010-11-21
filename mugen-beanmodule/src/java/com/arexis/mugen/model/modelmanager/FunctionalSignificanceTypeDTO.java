/*
 * FunctionalSignificanceTypeDTO.java
 *
 * Created on December 16, 2005, 3:12 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemote;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote;
import com.arexis.mugen.project.user.UserRemote;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Data transfer object for functional significance types
 * @author lami
 */
public class FunctionalSignificanceTypeDTO implements Serializable {
    private int pid, fstid, userId, funcSigs;
    private String name, comm, userName, ts;
    
    /**
     * Creates a new instance of FunctionalSignificanceTypeDTO
     * @param funcSigType The functional significance
     */
    public FunctionalSignificanceTypeDTO(FunctionalSignificanceTypeRemote funcSigType) {
        try {
            
            this.pid = funcSigType.getProject().getPid();
            this.fstid = funcSigType.getFstid();
            this.name = funcSigType.getName();
            this.comm = funcSigType.getComm();
            Collection c = funcSigType.getFunctionalSignificances();
            this.funcSigs = c.size();
            
            UserRemote usr = funcSigType.getUser();            
            userName = usr.getUsr();
            userId = usr.getId();
            
            this.ts = funcSigType.getTs().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the number of functional significances that are of this type
     * @return The number of functional significances that are of this type
     */
    public String getFuncSigs() {
        if(funcSigs == 0)
            return "";
        return ""+funcSigs;
    }

    /**
     * Returns the project id for the functional significance
     * @return The project id
     */
    public int getPid() {
        return pid;
    }

    /**
     * The id of the functional significance type
     * @return The id of the functional signifiance type
     */
    public int getFstid() {
        return fstid;
    }

    /**
     * Returns the id of the user that made the last changes on the functional signifiance type
     * @return The id of the user that made the last changes on the functional signifiance type
     */
    public int getUserId() {
        return userId;
    }

    /**
     * The name of the functional signifiance type
     * @return The name of the functional signifiance type
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the comment for the functional signifiance type
     * @return The comment
     */
    public String getComm() {
        return comm;
    }

    /**
     * Returns the username of the user that made the last changes on the functional signifiance type
     * @return The username of the user that made the last changes on the functional signifiance type
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the date for when the functional signifiance type was last changed
     * @return The date for when the functional signifiance type was last changed
     */
    public String getTs() {
        return ts;
    }
    
}
