
package com.arexis.mugen.model.modelmanager;

import com.arexis.arxframe.PageManager;
import com.arexis.arxframe.io.FileDataObject;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
//import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import java.util.Collection;
import org.mugen.dtos.*;

public interface ModelManagerRemoteBusiness {
    
    //model methods
    //<editor-fold defaultstate="collapsed">
    Collection getExperimentalModels(int suid, MugenCaller caller, PageManager pageManager) throws ApplicationException, java.rmi.RemoteException;

    ExpModelDTO getExperimentalModel(int eid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    ExpModelDTO getExperimentalModel(int eid, MugenCaller caller, String superscript) throws ApplicationException, java.rmi.RemoteException;

    int getNumberOfExperimentalModels(int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getGeneticModifications(int eid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    void addHandlingFile(int eid, FileDataObject fileData, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addGenotypingFile(int eid, FileDataObject fileData, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeGenotypingFile(int eid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeHandlingFile(int eid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addGeneticModification(int eid, String name, String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    Collection getFunctionalSignificances(int eid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    void addFunctionalSignificance(String name, String comm, int eid, int fstid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    //</editor-fold>

    Collection getResearchApplications(MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    Collection getAllResearchApplications(com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    GenModDTO getGeneticModification(int gmid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getGeneOntologies(int gmid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateGeneticModification(int gmid, String name, String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeGeneticModification(int gmid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    GeneOntologyDTO getGeneOntology(int goid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addGeneOntology(int gmid, String linkid, String name, String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeGeneOntology(int goid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateGeneOntology(int goid, java.lang.String linkid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getFunctionalSignificanceTypes(MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeFunctionalSignificance(int fsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateFunctionalSignificance(int fsid, java.lang.String name, java.lang.String comm, int type, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    FunctionalSignificanceDTO getFunctionalSignificance(int fsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addFunctionalSignificanceFile(int fsid, FileDataObject fileData, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeFunctionalSignificanceFile(int fsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getGenesByModel(int eid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateGene(int gaid, java.lang.String name, java.lang.String comm, java.lang.String mgiid, java.lang.String genesymbol, java.lang.String geneexpress, java.lang.String idgene, java.lang.String idensembl, int cid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeGene(int gaid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    GeneDTO getGene(int gaid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    GeneDTO getGene(int gaid, MugenCaller caller, String superscript) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getReferences(int eid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int addLinkReference(int eid, java.lang.String name, java.lang.String comm, java.lang.String url, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int addFileReference(int eid, java.lang.String name, java.lang.String comm, FileDataObject fileData, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeReference(int eid, int refid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int createModel(int suid, java.lang.String alias, java.lang.String geneticBackground, java.lang.String availability, int type, java.lang.String researchApplications, int contact, MugenCaller caller, String comm, String desired_level) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateModel(int suid, int eid, java.lang.String alias, java.lang.String geneticBackground, java.lang.String availability, int type, java.lang.String researchApplications, int contact, MugenCaller caller, String comm, String level, String desired_level) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeModel(int eid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int addFileResource(int eid, java.lang.String name, java.lang.String comm, FileDataObject fileData, int catid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int addLinkResource(int eid, java.lang.String name, java.lang.String comm, java.lang.String url, int catid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getFunctionalSignificances(MugenCaller caller, PageManager pageManager) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfFunctionalSignificances(int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getFunctionalSignificancesForType(int fstid, MugenCaller caller, PageManager pageManager) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfSignificancesForType(int fstid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection searchByGene(java.lang.String geneName, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection searchByVariableSet(java.lang.String vsName, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection searchByResearchApplication(java.lang.String name, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getVariableSets(com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection searchByProject(java.lang.String name, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection searchByKeyword(java.lang.String keyword, com.arexis.mugen.MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;
    
    java.util.Collection searchByKeywordFast(String keyword, String[] filter, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    MugenCaller getSearchCaller() throws ApplicationException, java.rmi.RemoteException;

    Collection getGenesByProject(int pid, com.arexis.mugen.MugenCaller caller, boolean all) throws ApplicationException, java.rmi.RemoteException;
    
    Collection getUnassignedGenes(int eid, int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    int createGene(java.lang.String name, java.lang.String comm, java.lang.String mgiid, java.lang.String genesymbol, java.lang.String geneexpress, java.lang.String idgene, java.lang.String idensembl, int cid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void addGeneToModel(int gaid, int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeGeneFromModel(int gaid, int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    int createFuncSigType(java.lang.String name, java.lang.String comment, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    FunctionalSignificanceTypeDTO getFunctionalSignificanceType(int fstid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void updateFuncSigType(int fstid, java.lang.String name, java.lang.String comment, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getModelsByGene(int gid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    ResearchAppDTO getResearchApplication(int raid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    int createResearchApplication(java.lang.String name, java.lang.String comment, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void updateResearchApplication(int raid, java.lang.String name, java.lang.String comment, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeResearchApplication(int raid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getExperimentalModelsByForm(com.arexis.form.FormDataManager fdm, com.arexis.mugen.MugenCaller caller, com.arexis.arxframe.PageManager pageManager) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getStrains(MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    com.arexis.mugen.model.modelmanager.StrainDTO getStrain(int strainid, MugenCaller caller, String superscript) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getStrainTypes(MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getStrainStates(MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getGeneticBackground(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getMutationTypes(int pid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    java.util.Collection getUnassignedMutationTypes(int strainalleleid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    com.arexis.mugen.model.modelmanager.StrainDTO getStrainFromModel(int eid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    Collection getGeneticBackgroundsByProject(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void setGeneticBackgroundForModel(int eid, int dna_origin, int targeted_back, int host_back, int backcrossing_strain, String backcrosses) throws ApplicationException, java.rmi.RemoteException;

    int createGeneBackValue(java.lang.String backname, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void updateGeneBackValue(int bid, String backname, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.lang.String getGeneBackValueName(int bid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    com.arexis.mugen.model.modelmanager.GeneticBackgroundDTO getGeneticBackgroundDTO(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getBackcrossesCollection() throws java.rmi.RemoteException;

    void updateGeneticBackgroundForModel(int eid, int dna_origin, int targeted_back, int host_back, int backcrossing_strain, String backcrosses, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getStrainStatesForStrain(int strainId, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getStrainTypesForStrain(int strainId, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getAvailableStrainStatesForStrain(int strainId, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getAvailableStrainTypesForStrain(int strainId, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void addStrainAndTypeToStrain(int strainid, int typeid, int stateid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeTypeFromStrain(int strainid, int typeid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeStateFromStrain(int strainid, int stateid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void updateStrain(int id, String designation, String mgiid, String emmaid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    java.util.Collection getStrainAllelesFromStrain(int strainid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    int createStrainAllele(int strainid, String symbol, String name, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    void removeStrainAllele(int eid, int strainalleleid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    com.arexis.mugen.model.modelmanager.StrainAlleleDTO getStrainAllele(int strainalleleid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    com.arexis.mugen.model.modelmanager.StrainAlleleDTO getStrainAlleleView(int strainalleleid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getMutationTypesFromStrainAllele(int strainalleleid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void addMutationTypeToStrainAllele(int id, int strainalleleid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    String removeMutationTypeFromStrainAllele(int id, int strainalleleid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void updateStrainAllele(int eid, int strainallele, String symbol, String name, String attributes, int geneid, String mgiid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    //availability methods
    //<editor-fold defaultstate="collapsed">
    
    java.util.Collection getRepositoriesByProject(int pid) throws ApplicationException, java.rmi.RemoteException;
    
    java.util.Collection getAvailableGeneticBackgroundsByProject(int pid) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getAvailabilityForModel(int eid) throws ApplicationException, java.rmi.RemoteException;

    void addAvailabilityToModel(int eid, int rid, int aid, int stateid, int typeid) throws ApplicationException, java.rmi.RemoteException;
    
    void removeAvailabilityFromModel(int eid, int rid, int aid, int stateid, int typeid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    com.arexis.mugen.model.modelmanager.RepositoriesDTO returnRepositoryById(int rid) throws ApplicationException, java.rmi.RemoteException;
    
    void updateRepositoryName(int rid, String reponame, String repourl) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getLevelsForModel() throws java.rmi.RemoteException;
    
    int addRepository(String reponame, String repourl, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    //</editor-fold>

    java.util.Collection getMutationTypeAttributes() throws java.rmi.RemoteException;

    com.arexis.mugen.model.modelmanager.AvailableGeneticBackgroundDTO returnAvailableGeneticBackgroundById(int aid) throws ApplicationException, java.rmi.RemoteException;

    void updateAvailableGeneticBackgroundName(int aid, String avgenbackname) throws ApplicationException, java.rmi.RemoteException;

    int addAvailableGeneticBackground(String avgenbackname, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeRepository(int rid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeAvailableGeneticBackground(int aid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getParticipants(MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeFileResource(int refid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getChromosomesForSpecies(int sid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    com.arexis.mugen.adminmanager.SpeciesDTO getSpecies(int sid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getGenesByProjectForNavTag(int pid, MugenCaller caller, PageManager pageManager) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsByFormNoDelta(com.arexis.form.FormDataManager fdm, MugenCaller caller, PageManager pageManager) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getOrderByTypes() throws java.rmi.RemoteException;

    java.util.Collection getResourceTreeCollection(int eid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsForIMSR(int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsToIMSRTable(Collection models, int suid) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getGenesForTransgenicMice(int pid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getUnassignedGenesForTransgenic(int eid, int strainid, int pid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    String getIsStrainAlleleTransgenicFactor(int strainalleleid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    int getGeneAssignmentForTransgenicModel(int eid, int gaid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeGeneFromStrainAlleles(int gaid, int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void clearGeneFromStrainAllele(int strainallele) throws ApplicationException, java.rmi.RemoteException;

    void removeStrainAllelesFromGene(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsByFormForDissUpdate(com.arexis.form.FormDataManager fdm, MugenCaller caller, PageManager pageManager) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsByFormForDissUpdateNoDelta(com.arexis.form.FormDataManager fdm, MugenCaller caller, PageManager pageManager) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getParticipantNames(MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getOrderByTypes2() throws java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsForBackcrossingListGeneration(MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getUniqueExperimentalModelsForIMSR(int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsWithJAXID(int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsWithEMMAID(int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsWithJAXEMMAID(int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void updateModelTimestamp(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getStrainsByFormDataManager(com.arexis.form.FormDataManager fdm, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getSimpleLogsByFDM(com.arexis.form.FormDataManager fdm, MugenCaller caller, PageManager pageManager) throws ApplicationException, java.rmi.RemoteException;

    int getAllSimpleLogs() throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getDistinctSimpleLogActions() throws ApplicationException, java.rmi.RemoteException;

    int getMaxSimpleLogs(com.arexis.form.FormDataManager fdm, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getDistinctSimpleLogUsers() throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getStandAloneGenes(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getGenesWithEnsemblid(com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    int createUserComment(int eid, String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getUserCommentsByModel(int eid) throws ApplicationException, java.rmi.RemoteException;

    void deleteUserComment(int commid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsByPGM(PageManager pageManager) throws ApplicationException, java.rmi.RemoteException;

    int getExperimentalModelsByForm(com.arexis.form.FormDataManager fdm, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    int getExperimentalModelsByFormForDissUpdateNoDelta(com.arexis.form.FormDataManager fdm, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsByFormForDissUpdate(PageManager pageManager) throws ApplicationException, java.rmi.RemoteException;

    int getGenesByProject(int pid, MugenCaller caller, String quick) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getGenesByProjectForNavTag(PageManager pageManager, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    //questionnaire methods
    //<editor-fold defaultstate="collapsed">
    java.util.Collection getYesNos() throws java.rmi.RemoteException;

    int createQuestionnaireEntry(String name, String inst, String mail, String[] qs) throws ApplicationException, java.rmi.RemoteException;
    //</editor-fold>

    //mp terms methods
    //<editor-fold defaultstate="collapsed">
    java.util.Collection phenoParseFunction() throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection phenoParseDataRelationBuildingFunction(Collection phenoDTOs) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection phenoCollectorLevelOne() throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection phenoCollectorLowerLevel(int root_pheno_id) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection phenoTermParseFunction() throws ApplicationException, java.rmi.RemoteException;

    void addPhenoToModel(int eid, int mp01, int mp02, int mp03, int mp04, int mp05, int mp06, int mp07, int mp08, int mp09, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    void removePhenoFromModel(int eid, int mp01, int mp02, int mp03, int mp04, int mp05, int mp06, int mp07, int mp08, int mp09, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getPhenosFromModel(int eid) throws ApplicationException, java.rmi.RemoteException;

    java.lang.String downloadMP() throws ApplicationException, java.rmi.RemoteException;
    
    void updateMP() throws ApplicationException, java.rmi.RemoteException;
    
    void storeMP(updateMPDTO MPDTO) throws ApplicationException, java.rmi.RemoteException;
    
    Collection reassignMP() throws ApplicationException, java.rmi.RemoteException;
    
    java.util.Collection getModelMPs(int eid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    boolean pathsMP2(int mpid) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getPhenoPaths(int eid) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getPhenoPathsByRoot(String root) throws ApplicationException, java.rmi.RemoteException;

    void addPhenoPathToModel(int eid, int ppid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removePhenoPathFromModel(int eid, int ppid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void replaceMP(int eid, String mpold, String mpnew) throws ApplicationException, java.rmi.RemoteException;
    
    void clearinvalidMP() throws ApplicationException, java.rmi.RemoteException;
    
    void installMP() throws ApplicationException, java.rmi.RemoteException;
    
    java.util.Collection endMPs() throws ApplicationException, java.rmi.RemoteException;
    
    com.arexis.mugen.model.modelmanager.PhenoOntologyDTO getPhenoOntology(int mpid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    java.util.Collection getPhenoAltIds(int mpid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    java.util.Collection getPhenoSynonyms(int mpid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    java.util.Collection getPhenoIsAs(int mpid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    java.util.Collection getPhenoEndNodePaths(int mpid) throws ApplicationException, java.rmi.RemoteException;
    
    java.util.Collection getExperimentalModelsByMP(int potid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    void removeoldMP(int eid, String mp) throws ApplicationException, java.rmi.RemoteException;
    
    int numreassignMP() throws ApplicationException, java.rmi.RemoteException;
    
    //</editor-fold>

    java.util.Collection getExperimentalModelsByGenBackValue(int bid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    String removeGenBackValue(int bid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getExperimentalModelsByAllele(int allid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getPhenoPathsByAllele(int allid) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getPhenoPathsByGene(int gaid) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getGenesByMP(String potid) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getAllelesByGene(int gid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getContactReason() throws java.rmi.RemoteException;

    java.lang.String getProjectName() throws java.rmi.RemoteException;

    //web services methods
    //<editor-fold defaultstate="collapsed">
    
    java.lang.String[] getMugenMice() throws ApplicationException, java.rmi.RemoteException;
    
    MugenModelDTO[] getMugenMiceDTO() throws ApplicationException, java.rmi.RemoteException;
    
    MugenModelDTO[] getMugenMiceDTOByKey(String key) throws ApplicationException, java.rmi.RemoteException;
    
    MugenGeneDTO[] getMugenGenesByModel(int eid) throws ApplicationException, java.rmi.RemoteException;
    
    MugenAvailabilityDTO[] getMugenAvailabilityByModel(int eid) throws ApplicationException, java.rmi.RemoteException;
    
    MugenBackgroundDTO[] getMugenBackgroundByModel(int eid) throws ApplicationException, java.rmi.RemoteException;
    
    MugenPhenotypesDTO[] getMugenPhenotypesByModel(int eid) throws ApplicationException, java.rmi.RemoteException;
    
    java.lang.String[] getMugenMiceByEnsembl(String ensembl) throws ApplicationException, java.rmi.RemoteException;
    
    //</editor-fold>

    java.util.Collection getDeltas() throws java.rmi.RemoteException;

    //ontology methods
    //<editor-fold defaultstate="collapsed">
    void loadPATO() throws ApplicationException, java.rmi.RemoteException;
    
    void loadMA() throws ApplicationException, java.rmi.RemoteException;
    
    void loadGO() throws ApplicationException, java.rmi.RemoteException;
    
    void loadCELL() throws ApplicationException, java.rmi.RemoteException;
    
    void loadCHEBI() throws ApplicationException, java.rmi.RemoteException;
    
    void loadEMAP() throws ApplicationException, java.rmi.RemoteException;
    
    void loadDOID() throws ApplicationException, java.rmi.RemoteException;
    
    void loadIMR() throws ApplicationException, java.rmi.RemoteException;
    
    void loadMPATH() throws ApplicationException, java.rmi.RemoteException;
    
    void loadXP() throws ApplicationException, java.rmi.RemoteException;
    //</editor-fold>

}
