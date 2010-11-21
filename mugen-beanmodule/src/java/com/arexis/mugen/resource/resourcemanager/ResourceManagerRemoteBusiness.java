
package com.arexis.mugen.resource.resourcemanager;

import com.arexis.arxframe.io.FileDataObject;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.link.LinkRemote;
import com.arexis.mugen.resource.resource.ResourceRemote;
import java.util.Collection;


/**
 * This is the business interface for ResourceManager enterprise bean.
 */
public interface ResourceManagerRemoteBusiness {
    Collection getSamplingUnitFiles(int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getSamplingUnitLinks(int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    LinkRemote createLink(java.lang.String name, java.lang.String comm, java.lang.String url, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    LinkDTO getLink(int linkid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateLink(int linkid, java.lang.String name, java.lang.String url, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int addLinkToSamplingUnit(int suid, MugenCaller caller, String name, String url, String comm) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeLink(int linkid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    FileRemote saveFile(String name, String comm, com.arexis.mugen.MugenCaller caller, FileDataObject fileData) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;        

    int addFileToSamplingUnit(int suid, MugenCaller caller, FileDataObject fileData, String name, String comm) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    FileDTO getFile(int fileid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateFile(int fileid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeFile(int fileid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int addFileToGeneticModification(int gmid, String name, String comm, FileDataObject fileData, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getGeneticModificationFiles(int gmid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getResources(java.util.Collection categories, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createResourceCategory(int project, java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getResourceCategories(int pid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    ResourceCategoryDTO getResourceCategory(int categoryId, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateResourceCategory(int categoryId, String name, String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeResourceCategory(int categoryId, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    ResourceRemote createResource(int pid, java.lang.String name, java.lang.String comm, int fileId, int linkId, int catId, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    ResourceDTO getResource(int resourceId, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateResource(int resourceId, java.lang.String name, java.lang.String comm, String url, int catid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeResource(int resourceId, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    /**
     * Get the first row of a file
     */
    java.lang.String getFirstRow(int fileid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    /**
     * Get the file Remote object for this file id
     * 
     * @param fileid
     * @param caller
     * @return 
     */
    com.arexis.mugen.resource.file.FileRemote getFileObject(int fileid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    /**
     * Returns all files in the database
     * 
     * @return The files in the database
     * @throws com.arexis.mugen.exceptions.ApplicationException If the files could not be retrieved
     */
    java.util.Collection getAllFiles(MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    /**
     * Saves a file in the database
     * 
     * @param name The name of the file
     * @param contentType The content type for the file
     * @param data The data in the file
     * @return The id of the saved file
     * @throws com.arexis.mugen.exceptions.ApplicationException If the file could not be stored
     */
    int saveFile(String name, String contentType, byte[] data, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    /**
     * 
     * Get all categories from the ResourceRemote collection
     * Returns a collection of ResourceCategoryRemote
     */
    java.util.Collection getCategoriesFromResources(Collection resources) throws java.rmi.RemoteException, ApplicationException;

    /**
     * Get the resource tree from a collection of resources.
     * 
     * @param resources is a collection of ResourceRemote objects connected
     * to an object.
     * @param caller is the caller of the method
     * @return 
     * @throws com.arexis.mugen.exceptions.ApplicationException
     */
    java.util.Collection getResourceTreeCollection(Collection resources, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.io.File getDiskFile(int fileid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    com.arexis.mugen.resource.resource.ResourceRemote createResource(LinkRemote link, int catId, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
    
}
