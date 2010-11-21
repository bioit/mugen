/*
 * GeneOntologyDTO.java
 *
 * Created on December 15, 2005, 1:20 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.model.modelmanager;
import com.arexis.mugen.model.geneontology.GeneOntologyRemote;
import com.arexis.mugen.project.user.UserRemote;
import java.io.Serializable;

/**
 * Data transfer object for the gene ontology
 * @author lami
 */
public class GeneOntologyDTO implements Serializable {
    private String name, comm, linkid, user, ts;
    private int goid, userId, gmid;
    
    /**
     * Creates a new instance of GeneOntologyDTO
     * @param geneOntology The gene ontology
     */
    public GeneOntologyDTO(GeneOntologyRemote geneOntology) {
        try {
            this.name = geneOntology.getName();
            this.comm = geneOntology.getComm();
            UserRemote user = geneOntology.getUser();
            this.userId = user.getId();
            this.user = user.getUsr();
            this.ts = geneOntology.getTs().toString();
            this.goid = geneOntology.getGoid();
            this.linkid = geneOntology.getLinkid();
            this.gmid = geneOntology.getGeneticModification().getGmid();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the name of the gene ontology
     * @return The name of the gene ontology
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the comment for the gene ontology
     * @return The comment for the gene ontology
     */
    public String getComm() {
        return comm;
    }

    /**
     * Returns the username of the user that made the last changes on the gene ontology
     * @return The username of the user that made the last chanages on the gene ontology
     */
    public String getUser() {
        return user;
    }
    
    /**
     * Returns the id of the link for the gene ontology
     * @return The id of the gene ontology link
     */
    public String getLinkid() {
        if(linkid.startsWith("GO:"))
            return linkid;
        else
            return "GO:"+linkid;
    }    

    /**
     * Returns the date for when the gene ontology was last modified
     * @return The date for when the gene ontology was last modified
     */
    public String getTs() {
        return ts;
    }

    /**
     * Returns the id of the gene ontology
     * @return The id of the gene ontology
     */
    public int getGoid() {
        return goid;
    }
    
    /**
     * Returns the genetic modification id for the gene ontology
     * @return The genetic modification id
     */
    public int getGmid() {
        return gmid;
    }    

    /**
     * The id of the user that made the last changes on the gene ontology
     * @return The id of the user that made the last changes on the gene ontology
     */
    public int getUserId() {
        return userId;
    }
    
}
