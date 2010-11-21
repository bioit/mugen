
package com.arexis.mugen.phenotype.phenotypemanager;
import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import java.util.Collection;


/**
 * This is the business interface for PhenotypeManager enterprise bean.
 */
public interface PhenotypeManagerRemoteBusiness {
    Collection getPhenotypes(int suid, PageManager pageManager, MugenCaller caller, FormDataManager formdata) throws ApplicationException, java.rmi.RemoteException;

    PhenotypeDTO getPhenotype(int iid, int vid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    void updatePhenotype(int iid, int vid, java.lang.String value, java.lang.String reference, java.lang.String comm, String date, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removePhenotype(int iid, int vid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getVariables(int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createPhenotype(MugenCaller caller, int vid, int iid, String comm, String ref, String date, String value, int suid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getVariables(int suid, PageManager pageManager, MugenCaller caller, FormDataManager formdata) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    VariableDTO getVariable(int vid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeVariable(int vid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateVariable(int vid, java.lang.String name, java.lang.String unit, java.lang.String type, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createVariable(java.lang.String name, java.lang.String unit, java.lang.String type, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getVariableSets(int suid, PageManager pageManager, MugenCaller caller, FormDataManager formdata) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    VariableSetDTO getVariableSet(int vsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateVariableSet(int vsid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeVariableSet(int vsid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    void createVariableSet(java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getVariablesInSet(int vsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addVarsInSet(int vsid, String[] vids, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeVarsInSet(int vsid, String[] vids, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getAllVariableSets(int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUVariables(PageManager pageManager, MugenCaller caller, FormDataManager formdata) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    UVariableDTO getUVariable(int uvid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateUVariable(int uvid, int sid, java.lang.String name, java.lang.String unit, java.lang.String type, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeUVariable(int uvid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createUVariable(java.lang.String name, java.lang.String unit, java.lang.String type, java.lang.String comm, MugenCaller caller, int sid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getAllUVariableSets(MugenCaller caller, int sid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUVariableSets(PageManager pageManager, MugenCaller caller, FormDataManager formdata) throws java.rmi.RemoteException, ApplicationException;

    UVariableSetDTO getUVariableSet(int uvsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateUVariableSet(int uvsid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeUVariableSet(int uvsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void createUVariableSet(java.lang.String name, java.lang.String comm, MugenCaller caller, int sid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUVariablesInSet(int uvsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getAllUVariables(MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addUVarsInSet(int uvsid, String[] uvids, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeUVarsInSet(int uvsid, String[] uvids, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getPhenotypeHistory(int vid, int iid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getVariableHistory(int vid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getVariableSetHistory(int vsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUVariableSetHistory(int uvsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUVariableHistory(int uvid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getAvailableVariables(int vsid, MugenCaller caller, int suid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getAvailableUVariables(int uvsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getUVariableMappings(int uvid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    void createUVariableMapping(int uvid, int vid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeUVariableMapping(int uvid, int vid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException ;

    int getNumberOfPhenotypes(FormDataManager formdata, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfVariables(FormDataManager formdata, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    int getNumberOfVariableSets(FormDataManager formdata, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfUVariables(FormDataManager formdata, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfUVariableSets(MugenCaller caller, FormDataManager formdata) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getPhenotypeFilters(MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int getNumberOfVariablesInSet(int vsid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removePhenotypesInSamplingUnit(int suid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
}
