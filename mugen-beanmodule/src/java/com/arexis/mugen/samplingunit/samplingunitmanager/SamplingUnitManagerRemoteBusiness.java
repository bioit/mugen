
package com.arexis.mugen.samplingunit.samplingunitmanager;
import com.arexis.arxframe.PageManager;
import com.arexis.arxframe.io.FileDataObject;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.IndividualNotFoundException;
import com.arexis.mugen.project.ParamDataObject;
import java.util.Collection;

/**
 * This is the business interface for SamplingUnitManager enterprise bean.
 */
public interface SamplingUnitManagerRemoteBusiness {
    Collection getSamplingUnits(int pid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    Collection getGroupings(int suid, PageManager page, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;    
    
    GroupingDTO getGrouping(int gsid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;        
    
    int createGrouping(String name, String comm, MugenCaller caller, int suid) throws ApplicationException, java.rmi.RemoteException;
    
    int createSamplingUnit(String name, String comm, String status, int sid, int pid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    void updateGrouping(int gsid, String name, String comm, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeGrouping(int gsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    //Collection getGroups(Collection keys) throws java.rmi.RemoteException, ApplicationException;

    Collection getGroups(int gsid, PageManager pageManager, MugenCaller caller, int suid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfGroups(int gsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    GroupDTO getGroup(int gid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    void removeGroup(int gid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateGroup(int gid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getSamplingUnits(MugenCaller caller, PageManager pageManager, ParamDataObject qdo, int sid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    Collection getSamplingUnitHistory(MugenCaller caller, int suid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    SamplingUnitDTO getSamplingUnit(MugenCaller caller, int suid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateSamplingUnit(int suid, java.lang.String name, java.lang.String comm, java.lang.String status, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeSamplingUnit(int suid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException ;

    Collection getSpeciesForProject(int pid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int createGroup(String name, String comm, MugenCaller caller, int gsid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getGroupingHistory(int gsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getGroupHistory(int gid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getIndividuals(int suid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    Collection getIndividuals(int suid, PageManager pageManager, MugenCaller caller, FormDataManager formdata) throws java.rmi.RemoteException, ApplicationException;

    IndividualDTO getIndividual(int iid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    Collection getMales(int suid) throws ApplicationException, java.rmi.RemoteException;

    Collection getFemales(int suid) throws java.rmi.RemoteException, ApplicationException;

    int createNewIndividual(int suid, MugenCaller caller, String identity, String alias, String sex, int father, int mother, String birthdate, String comm) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeIndividual(int iid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getIndividualsByGroup(int gid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeIndInGroup(int gid, int iid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addIndInGroup(int gid, int iid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getIndHistory(int iid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;    

    Collection getGroupings(int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getGroups(int gsid, MugenCaller caller, int suid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getIndividualsNotInGroup(int suid, int gid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfSamplingUnits(int sid, MugenCaller caller, ParamDataObject qdo) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfIndividuals(FormDataManager formdata, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    Collection checkSamplingUnit(int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfIndividuals(int suid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfGroupings(int suid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateIndividual(int iid, java.lang.String identity, java.lang.String alias, java.lang.String sex, java.lang.String status, int father, int mother, java.lang.String birthdate, String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createFamilyGrouping(int fid, int mid, String name, String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getSamplingUnits(int pid, int sid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    /**
     * Get two collections of individuals to a membership view.
     * First collection is the members for a group
     * Second collection is the remaining individuals filtered by the ParamDataObject
     * 
     * @param gid is the group the individuals is member of 
     * @param pdo the query object to filter available individuals.
     * @param caller is the user making the call
     * @throws com.arexis.mugen.exceptions.ApplicationException if getting the collections fails.
     * @return an array (size=2) of Collections. First is indcluded inds, second is other inds.
     */
    Collection[] getGroupMembership(int gid, FormDataManager formdata, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    SamplingUnitDTO getDefaultSamplingUnit(com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getExperimentalObjects(int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int updateIndividualFromIdentity(int suid, String identity, java.lang.String alias, java.lang.String sex, java.lang.String status, String father, String mother, java.lang.String birthdate, String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException, IndividualNotFoundException, java.rmi.RemoteException;

    com.arexis.mugen.samplingunit.samplingunitmanager.GroupingDTO getGroupingByName(String name, int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void createGroupInfo(String groupingName, String groupName, int suid, String identity, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    int createNewIndividual(int suid, MugenCaller caller, String identity, String alias, String sex, String father, String mother, String birthdate, String comm) throws ApplicationException, java.rmi.RemoteException;

    java.util.Collection getResourceTreeCollection(int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void addResource(java.lang.String type, int category, int project, java.lang.String name, java.lang.String comm, FileDataObject fileData, com.arexis.mugen.MugenCaller caller, String url, int suid) throws ApplicationException, java.rmi.RemoteException;

    /**
     * Create or Update an individual. If the individual does not exist, create 
     * it. If the individual does exists, update the values. 
     * This is used in the import system.
     * 
     * @param suid is the sampling unit id
     * @param caller
     * @param identity
     * @param alias
     * @param sex
     * @param father
     * @param mother
     * @param birthdate
     * @param comm
     * @return iid
     * @throws com.arexis.mugen.exceptions.ApplicationException
     */
    int createOrUpdateIndividual(int suid, MugenCaller caller, String identity, String alias, String sex, String father, String mother, String birthdate, String comm) throws ApplicationException, java.rmi.RemoteException;

    boolean individualExists(String identity, int suid, MugenCaller caller) throws java.rmi.RemoteException;

    void addLinkResource(String name, String comm, String url, int category, int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
}
