
package com.arexis.mugen.genotype.genotypemanager;

import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.ParamDataObject;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO;
import com.arexis.util.graph.Graph;
import java.util.Collection;


/**
 * This is the business interface for GenotypeManager enterprise bean.
 */
public interface GenotypeManagerRemoteBusiness {
    Collection getGenotypes(MugenCaller caller, PageManager pageManager, ParamDataObject pdo) throws ApplicationException, java.rmi.RemoteException;

    GenotypeDTO getGenotype(int mid, int iid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getAllelesForMarker(int mid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void updateGenotype(int iid, int mid, java.lang.String raw1, java.lang.String raw2, java.lang.String reference, java.lang.String comm, int aid1, int aid2, int level, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeGenotype(int mid, int iid, MugenCaller caller, int level) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getChromosomes(int sid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getAllMarkers(MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getAllMarkersForChromosome(MugenCaller caller, int cid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;    
    
    void createGenotype(int iid, int mid, java.lang.String comm, java.lang.String raw1, java.lang.String raw2, java.lang.String ref, int aid1, int aid2, int level, int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getMarkers(PageManager pageManager, MugenCaller caller, FormDataManager fdm) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    AlleleDTO getAllele(int aid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateAllele(int aid, String comm, String name, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeAllele(int aid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createAllele(MugenCaller caller, String name, String comm, int mid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    void updateUAllele(int uaid, String comm, String name, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeUAllele(int uaid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createUAllele(MugenCaller caller, String name, String comm, int umid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;    

    MarkerDTO getMarker(int mid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateMarker(int mid, java.lang.String name, java.lang.String comm, java.lang.String p1, java.lang.String p2, double position, java.lang.String alias, int cid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int createMarker(java.lang.String name, java.lang.String comm, java.lang.String p1, java.lang.String p2, double position, java.lang.String alias, int cid, int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeUMarker(int umid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    void updateUMarker(int umid, java.lang.String name, java.lang.String comm, double position, java.lang.String alias, int cid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createUMarker(java.lang.String name, java.lang.String comm, double position, java.lang.String alias, int cid, MugenCaller caller, int sid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeMarker(int mid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;    

    Collection getMarkerSets(PageManager pageManager, MugenCaller caller, FormDataManager fdm) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getMarkerSets(int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    MarkerSetDTO getMarkerSet(int msid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateMarkerSet(int msid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createMarkerSet(java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeMarkerSet(int msid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    void updateUMarkerSet(int umsid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createUMarkerSet(java.lang.String name, java.lang.String comm, MugenCaller caller, int sid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeUMarkerSet(int umsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;    

    Collection getMarkerSetPositions(int msid, MugenCaller caller, PageManager pageManager) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateMarkerSetPosition(int msid, int mid, double position, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    PositionDTO getMarkerSetPosition(int msid, int mid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    Collection getUMarkerSetPositions(int umsid, MugenCaller caller, PageManager pageManager) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateUMarkerSetPosition(int umsid, int umid, double position, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    UPositionDTO getUMarkerSetPosition(int umsid, int umid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;    

    void addMarkerInSet(int msid, int mid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeMarkerInSet(int msid, int mid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    void addUMarkerInSet(int umsid, int umid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeUMarkerInSet(int umsid, int umid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;    

    Collection getUMarkers(PageManager pageManager, MugenCaller caller, FormDataManager formData) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
    Collection getUMarkers(MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUMarkerMapping(int umid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    UMarkerDTO getUMarker(int umid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void deleteUMarkerMapping(int umid, int mid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createUMarkerMapping(int umid, int mid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUAllelesForUMarker(int umid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    UAlleleDTO getUAllele(int uaid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUMarkerSets(PageManager pageManager, MugenCaller caller, FormDataManager formdata) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    UMarkerSetDTO getUMarkerSet(int umsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUMarkerSets(MugenCaller caller, int sid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getGenotypeHistory(int iid, int mid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getMarkerHistory(int mid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getAlleleHistory(int aid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getMarkerSetHistory(int msid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUMarkerSetHistory(int umsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUMarkerHistory(int umid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUAlleleHistory(int uaid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfGenotypes(ParamDataObject pdo, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateGenotypeLevels(MugenCaller caller, ParamDataObject pdo, String level) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfMarkers(MugenCaller caller, FormDataManager fdm) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfUMarkers(MugenCaller caller, FormDataManager formdata) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfMarkerSets(MugenCaller caller, FormDataManager fdm) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfUMarkerSets(MugenCaller caller, FormDataManager formdata) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getMarkers(MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getParentalView(int iid, int mid1, int mid2, int mid3, boolean depthFirst) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Graph getGraphForPedigree(Collection pedigree, boolean depthFirst) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getChildView(int father, int mother, int mid1, int mid2, int mid3, boolean depthFirst) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getGroupView(int gid, int mid1, int mid2, int mid3) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    MarkerDTO getMarker(Collection markers, int id) throws java.rmi.RemoteException;
    
    GroupDTO getGroup(Collection groups, int id) throws java.rmi.RemoteException;    
    
    Collection getChoosenMarkers(Collection available, Collection previous, String[] selected) throws java.rmi.RemoteException;
    
    Collection removeChoosenMarkers(Collection available, Collection previous, String[] selected) throws java.rmi.RemoteException;    

    Collection getChoosenGroups(Collection available, Collection previous, String[] selected) throws java.rmi.RemoteException;
    
    Collection removeChoosenGroups(Collection available, Collection previous, String[] selected) throws java.rmi.RemoteException;        

    int createMarkerImport(String name, String comm, String p1, String p2, String position, String alias, String chromosome, int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    boolean markerExists(String name, int suid, MugenCaller caller) throws java.rmi.RemoteException;

    int updateMarkerImport(String name, String comm, String p1, String p2, String position, String alias, String chromosome, int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    int createOrUpdateMarkerImport(String name, String comm, String p1, String p2, String position, String alias, String chromosome, int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
}
