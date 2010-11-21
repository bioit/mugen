
package com.arexis.mugen.model.expmodel;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.model.reference.ReferenceRemote;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemote;
import com.arexis.mugen.model.strain.strain.StrainRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.resource.ResourceRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyPathRemote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * This is the business interface for ExpModel enterprise bean.
 */
public interface ExpModelRemoteBusiness {
    UserRemote getContact() throws java.rmi.RemoteException;

    void setContact(com.arexis.mugen.project.user.UserRemote usr) throws java.rmi.RemoteException;

    void setAvailability(java.lang.String availability) throws java.rmi.RemoteException;

    String getAvailability() throws java.rmi.RemoteException;

    String getResearchApplicationText() throws java.rmi.RemoteException;

    void setResearchApplicationText(java.lang.String researchApplicationText) throws java.rmi.RemoteException;

    Collection getGeneticModifications() throws java.rmi.RemoteException;

    String getGeneticBackground() throws java.rmi.RemoteException;

    void setGeneticBackground(java.lang.String geneticBackground) throws java.rmi.RemoteException;

    ResearchApplicationRemote getResearchApplication() throws java.rmi.RemoteException;

    void setResearchApplication(com.arexis.mugen.model.researchapplication.ResearchApplicationRemote ra) throws RemoteException;

    SamplingUnitRemote getSamplingUnit() throws java.rmi.RemoteException, ApplicationException;
    
    void setCaller(MugenCaller caller) throws java.rmi.RemoteException, PermissionDeniedException;    
    
    String getStatus() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    void setStatus(java.lang.String status) throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    String getIdentity() throws java.rmi.RemoteException;

    void setIdentity(java.lang.String identity) throws java.rmi.RemoteException;

    String getAlias() throws java.rmi.RemoteException;

    void setAlias(java.lang.String alias) throws java.rmi.RemoteException;    
    
    int getEid() throws java.rmi.RemoteException;   
    
    void setSuid(int suid) throws java.rmi.RemoteException;   

    FileRemote getGenotypingFile() throws java.rmi.RemoteException;

    FileRemote getHandlingFile() throws java.rmi.RemoteException;

    void setGenotypingFile(int fileid) throws java.rmi.RemoteException;

    void setHandlingFile(int fileid) throws java.rmi.RemoteException;

    Collection getFunctionalSignificance() throws java.rmi.RemoteException;

    Collection getGeneAffected() throws java.rmi.RemoteException;

    void addReference(com.arexis.mugen.model.reference.ReferenceRemote ref) throws ApplicationException, java.rmi.RemoteException;

    Collection getReferences() throws java.rmi.RemoteException;

    void removeReference(ReferenceRemote reference) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addResource(ResourceRemote res) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfPhenotypes() throws ApplicationException, java.rmi.RemoteException;

    void addGene(com.arexis.mugen.species.gene.GeneRemote gene) throws ApplicationException, java.rmi.RemoteException;

    void removeGene(com.arexis.mugen.species.gene.GeneRemote gene) throws ApplicationException, java.rmi.RemoteException;

    com.arexis.mugen.model.strain.strain.StrainRemote getStrain() throws ApplicationException, java.rmi.RemoteException;

    void setStrain(StrainRemote strain) throws java.rmi.RemoteException;

    int getLevel() throws java.rmi.RemoteException;

    void setLevel(int level) throws java.rmi.RemoteException;

    java.lang.String getMutationTypesForModel() throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getResources() throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getAvailabilityForModel(int eid) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getStrainAlleleInfo() throws ApplicationException, java.rmi.RemoteException;

    int IMSRSubmit(int eid) throws ApplicationException, java.rmi.RemoteException;

    int getMutationDistinctionParameter() throws ApplicationException, java.rmi.RemoteException;

    void unassignGeneFromStrainAlleles(int strainId, int geneId) throws ApplicationException, java.rmi.RemoteException;

    void unassignStrainAllelesFromGene(int eid, int strainid) throws ApplicationException, java.rmi.RemoteException;

    int getDesiredLevel() throws java.rmi.RemoteException;

    void setDesiredLevel(int desired_level) throws java.rmi.RemoteException;

    java.util.Collection getGeneticBackgroundInfo() throws ApplicationException, java.rmi.RemoteException;

    void addPhenoOntology(int mp01, int mp02, int mp03, int mp04, int mp05, int mp06, int mp07, int mp08, int mp09) throws ApplicationException, java.rmi.RemoteException;

    void removePhenoOntology(int mp01, int mp02, int mp03, int mp04, int mp05, int mp06, int mp07, int mp08, int mp09) throws ApplicationException, java.rmi.RemoteException;

    void setUpdate() throws java.rmi.RemoteException;

    java.util.Collection getMPs() throws ApplicationException, java.rmi.RemoteException;

    void addPhenoOntologyPath(PhenotypeOntologyPathRemote pop) throws ApplicationException, java.rmi.RemoteException;

    void removePhenoOntologyPath(PhenotypeOntologyPathRemote pop) throws ApplicationException, java.rmi.RemoteException;
}
