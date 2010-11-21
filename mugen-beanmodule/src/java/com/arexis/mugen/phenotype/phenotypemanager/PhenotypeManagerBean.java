package com.arexis.mugen.phenotype.phenotypemanager;

import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.phenotype.phenotype.PhenotypePk;
import com.arexis.mugen.phenotype.phenotype.PhenotypeRemote;
import com.arexis.mugen.phenotype.phenotype.PhenotypeRemoteHome;
import com.arexis.mugen.phenotype.uvariable.UVariableRemote;
import com.arexis.mugen.phenotype.uvariable.UVariableRemoteHome;
import com.arexis.mugen.phenotype.uvariableset.UVariableSetRemote;
import com.arexis.mugen.phenotype.uvariableset.UVariableSetRemoteHome;
import com.arexis.mugen.phenotype.variable.VariableRemote;
import com.arexis.mugen.phenotype.variable.VariableRemoteHome;
import com.arexis.mugen.phenotype.variableset.VariableSetRemote;
import com.arexis.mugen.phenotype.variableset.VariableSetRemoteHome;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.ParamDataObject;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * This is the bean class for the PhenotypeManagerBean enterprise bean.
 * Created Aug 18, 2005 9:41:47 AM
 * @author lami
 */
public class PhenotypeManagerBean extends AbstractMugenBean implements javax.ejb.SessionBean, com.arexis.mugen.phenotype.phenotypemanager.PhenotypeManagerRemoteBusiness {
    private javax.ejb.SessionContext context;
    
    private VariableSetRemoteHome vsrh;
    private IndividualRemoteHome irh;
    private SamplingUnitRemoteHome surh;
    private SpeciesRemoteHome sprh;
    private PhenotypeRemoteHome prh;
    private VariableRemoteHome vrh;
    private UVariableRemoteHome uvrh;
    private UVariableSetRemoteHome uvsrh;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise bean, Web services)
    // TODO Add business methods or web service operations
    /**
     * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
     */
    public void setSessionContext(javax.ejb.SessionContext aContext) {
        context = aContext;
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate() {
        
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate() {
        
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove() {
        
    }
    // </editor-fold>
    
    /**
     * See section 7.10.3 of the EJB 2.0 specification
     * See section 7.11.3 of the EJB 2.1 specification
     */
    public void ejbCreate() {
        
        vsrh = (VariableSetRemoteHome)locator.getHome(ServiceLocator.Services.VARIABLESET);
        irh = (IndividualRemoteHome)locator.getHome(ServiceLocator.Services.INDIVIDUAL); 
        surh = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
        sprh = (SpeciesRemoteHome)locator.getHome(ServiceLocator.Services.SPECIES);
        prh = (PhenotypeRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE);
        vrh = (VariableRemoteHome)locator.getHome(ServiceLocator.Services.VARIABLE);
        uvrh = (UVariableRemoteHome)locator.getHome(ServiceLocator.Services.UVARIABLE);
        uvsrh = (UVariableSetRemoteHome)locator.getHome(ServiceLocator.Services.UVARIABLESET);
    }
    
    /**
     * Returns the filters for the phenotype
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the filters could not be retrieved
     * @return The filters for the phenotype
     */
    public Collection getPhenotypeFilters(com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        makeConnection();
        Collection filters = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT fid, name from v_filters_1 where pid = ? order by name");
            ps.setInt(1, caller.getPid());            
            
            rs = ps.executeQuery();
            while(rs.next()) {
                filters.add(new FilterDTO(rs.getInt("fid"),  0, 0, 0, rs.getString("name"), "", "",  null));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new ApplicationException("Failed to get filters.", se);
        } finally {
            releaseConnection();
        } 
        
        return filters;
    }    

    /**
     * Returns the phenotypes for the sampling unit
     * @return A collection of phenotypes data transfer objects
     * @param formdata The formdata object with filtering parameters
     * @param suid The sampling unit id
     * @param pageManager The pagemanager for the application
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the phenotypes could not be retrieved
     */
    public Collection getPhenotypes(int suid, PageManager pageManager, MugenCaller caller, FormDataManager formdata) throws ApplicationException {
        validate("PHENO_R", caller);            
        Collection dtoList = new ArrayList();
        try {
            int index = 0;
            
                        
            Collection phenotypes = prh.findByQuery(formdata, caller.getPid(), pageManager);
            Iterator itr = phenotypes.iterator();

            PhenotypeRemote ph;
            PhenotypeDTO dto;
            
            while (itr.hasNext()) {                                
                ph = (PhenotypeRemote)itr.next();
                dto = new PhenotypeDTO(ph, caller);

                // Add to array
                dtoList.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get phenotypes.", e);
        }
        return dtoList;
    }
    
    /**
     * Returns the number of phenotypes in the database given the filter parameters in the formdata object
     * @param formdata The form data to filter on
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of phenotypes could not be retrieved
     * @return The number of phenotypes in the database given the filter parameters
     */
    public int getNumberOfPhenotypes(FormDataManager formdata, MugenCaller caller) throws ApplicationException {
        validate("PHENO_R", caller);            
        Collection dtoList = new ArrayList();
        try {                                 
            return prh.getNumberOfPhenotypes(formdata, caller.getPid());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not count phenotypes.",e);
        }
    }    

    /**
     * Returns a phenotype data transfer object
     * @param iid The individual id
     * @param vid The variable id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the phenotype could not be found
     * @return A phenotype data transfer object
     */
    public PhenotypeDTO getPhenotype(int iid, int vid, MugenCaller caller) throws ApplicationException {
        validate("PHENO_R", caller);
        
        try{
            PhenotypePk pk = new PhenotypePk(vid, iid);            
            PhenotypeRemote pr = prh.findByPrimaryKey(pk);
            
            
            
            PhenotypeDTO dto = new PhenotypeDTO(pr, caller);     
            
            return dto;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve phenotype", e);
        }
    }   
    
    /**
     * Updates a phenotype
     * @param caller The current caller object
     * @param iid The individual id
     * @param vid The variable id
     * @param value The value of the phenotype
     * @param reference The reference of the phenotype
     * @param comm The comment for the phenotype
     * @param date The date for the phenotype
     * @throws com.arexis.mugen.exceptions.ApplicationException If the phenotype could not be updated
     */
    public void updatePhenotype(int iid, int vid, java.lang.String value, java.lang.String reference, java.lang.String comm, String date, MugenCaller caller) throws ApplicationException {
        validate("PHENO_W", caller);
        validate("Value", value, 20);
        validate("Reference", reference, 32);
        validate("Comment", comm, 256);
        if(date.trim().length() > 0)
            validate("Date", date, new SimpleDateFormat("yyyy-mm-dd"));
        try{
            PhenotypePk pk = new PhenotypePk(vid, iid);
            PhenotypeRemote pr = prh.findByPrimaryKey(pk);
            pr.setCaller(caller);
            pr.addHistory();
            pr.setValue(value);
            
            if(date != null && date.length() > 0){
                String[] splitted = date.split("-");                
                GregorianCalendar cal = new GregorianCalendar(
                        Integer.parseInt(splitted[0]),
                        Integer.parseInt(splitted[1])-1, // Month value is zero based! Jan = 0
                        Integer.parseInt(splitted[2])); 
                pr.setDate(new java.sql.Date(cal.getTimeInMillis()));
            }
            
            pr.setReference(reference);
            pr.setComm(comm);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update phenotype", e);
        }
    }    
    
    public void removePhenotypesInSamplingUnit(int suid, MugenCaller caller) throws ApplicationException
    {
        validate("PHENO_W", caller);
        validate("SU_W", caller);
        try
        {
            Collection phenotypes = prh.findBySamplingUnit(suid);
            Iterator i = phenotypes.iterator();
            while (i.hasNext())
            {
                PhenotypeRemote p = (PhenotypeRemote)i.next();
                p.remove();
            }    
        }        
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to remove phenotypes in sampling unit");
        }
    }        
    
    /**
     * Removes a phenotype from the database
     * @param iid The individual id
     * @param vid The variable id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the phenotype could not be removed
     */
    public void removePhenotype(int iid, int vid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("PHENO_W", caller);
        
        try{
            PhenotypePk pk = new PhenotypePk(vid, iid);
            PhenotypeRemote pr = prh.findByPrimaryKey(pk);
            pr.remove();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove phenotype", e);
        }
    }  
    
    /**
     * Returns the number of variables for the phenotype
     * @param formdata The formdata object the holds filtering parameters for the phenotype
     * @param caller The caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of variables could not be retrieved
     * @return The number of variables for the phenotype
     */
    public int getNumberOfVariables(FormDataManager formdata, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VAR_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            return vrh.getNumberOfVariables(formdata);  
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not count variables.", e);
        }
    }    

    /**
     * Returns a collection of ALL variables for the specified sampling unit
     * @param suid The sampling unit id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variables could not be retrieved
     * @return A collection of variables
     */
    public Collection getVariables(int suid, MugenCaller caller) throws ApplicationException {
        validate("VAR_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            VariableRemote vr = null;
            SpeciesRemote sr = null;
            SamplingUnitRemote sur = null;            
            VariableDTO dto = null;
            Collection variables = vrh.findAllVariables(suid);
            UserRemote ur = null;
            Iterator itr = variables.iterator();
            
            while (itr.hasNext()) {
                vr = (VariableRemote)itr.next();
                dto = new VariableDTO(vr);

                // Add to array
                dtoList.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get variables.", e);
        }
        return dtoList;
    }    
    
    /**
     * Returns a collection of variables for the specified sampling unit. The size of the collection depends on how the number of objects to display per page as selected in the project settings.
     * @return A collection of variables
     * @param formdata The formdata object
     * @param suid The sampling unit id
     * @param pageManager The page manager which handles the page navigation for a large collection of data
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variables could not be retrieved
     */
    public Collection getVariables(int suid, PageManager pageManager, com.arexis.mugen.MugenCaller caller, FormDataManager formdata) throws ApplicationException {
        validate("VAR_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            VariableRemote vr = null;
            VariableDTO dto = null;
            SpeciesRemote sr = null;
            SamplingUnitRemote sur = null;
            Collection variables = vrh.findByQuery(formdata, pageManager);
            UserRemote ur = null;
            Iterator itr = variables.iterator();
            
            while (itr.hasNext()) {
                vr = (VariableRemote)itr.next();
                dto = new VariableDTO(vr);

                // Add to array
                dtoList.add(dto);
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get variables.", e);
        }
        return dtoList;
    }         

    /**
     * Returns a specific variable
     * @param vid The variable id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable could not be retrieved
     * @return A specific variable
     */
    public VariableDTO getVariable(int vid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VAR_R", caller);
        
        try{
            VariableRemote vr = vrh.findByPrimaryKey(new Integer(vid));
            return new VariableDTO(vr);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get variable.", e);
        }        
    }    
    
    /**
     * Creates a new phenotype
     * @param caller The current caller object
     * @param vid The variable id
     * @param iid The individual id
     * @param comm The comment for the phenotype
     * @param ref The reference for the phenotype
     * @param date The date for the phenotype
     * @param value The value for the phenotype
     * @param suid The sampling unit that the phenotype should be linked to
     * @throws com.arexis.mugen.exceptions.ApplicationException If the phenotype could not be created
     */
    public void createPhenotype(com.arexis.mugen.MugenCaller caller, int vid, int iid, String comm, String ref, String date, String value, int suid) throws ApplicationException {
        validate("PHENO_W", caller);
        validate("Comment", comm, 256);
        validate("Reference", ref, 32);
        validate("Value", value, 20);
        if(date.trim().length() > 0)
            validate("Date", date, new SimpleDateFormat("yyyy-mm-dd"));
        try{        
            //IndividualRemote ir = irh.findByPrimaryKey(new Integer(iid));
            VariableRemote vr = vrh.findByPrimaryKey(new Integer(vid));
            SamplingUnitRemote sur = surh.findByPrimaryKey(new Integer(suid));
            java.sql.Date ts = null;
            
            if(date != null && date.length() > 0){
                String[] splitted = date.split("-");                
                GregorianCalendar cal = new GregorianCalendar(
                        Integer.parseInt(splitted[0]),
                        Integer.parseInt(splitted[1])-1, // Month value is zero based! Jan = 0
                        Integer.parseInt(splitted[2])); 
                ts = new java.sql.Date(cal.getTimeInMillis());
            }
            
            PhenotypeRemote pr = prh.create(iid, vr, sur,  value, ref, comm, ts, caller);            
            
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update phenotype", e);
        }
    }    

  
    /**
     * Removes a variable from the database
     * @param vid The id of the variable to be removed
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable could not be remvoved
     */
    public void removeVariable(int vid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VAR_W", caller);
        
        try{ 
            VariableRemote vr = vrh.findByPrimaryKey(new Integer(vid));                      
            vr.remove();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not delete variable", e);
        }
    }

    /**
     * Updates a variable in the database
     * @param vid The id of the variable to update
     * @param name The name of the variable
     * @param unit The unit for the variable
     * @param type The type for the variable
     * @param comm The comment for the variable
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable could not be updated
     */
    public void updateVariable(int vid, java.lang.String name, java.lang.String unit, java.lang.String type, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VAR_W", caller);
        validate("Name", name, 20);
        validate("Unit", unit, 10);
        validate("Type", type, 1);
        validate("Comment", comm, 256);
        try{ 
            VariableRemote vr = vrh.findByPrimaryKey(new Integer(vid));                      
            vr.setCaller(caller);
            vr.addHistory();
            vr.setName(name);
            vr.setUnit(unit);
            vr.setType(type);
            vr.setComm(comm);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update variable", e);
        }
    }

    /**
     * Creates a new variable
     * @param name The name of the variable
     * @param unit The unit for the variable
     * @param type The type for the variable
     * @param comm The comment for the variable
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable could not be created
     */
    public void createVariable(java.lang.String name, java.lang.String unit, java.lang.String type, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VAR_W", caller);
        validate("Name", name, 20);
        validate("Unit", unit, 10);
        validate("Type", type, 1);
        validate("Comment", comm, 256);        
        try{ 
            makeConnection();
            int vid = getIIdGenerator().getNextId(conn, "variables_seq");
            VariableRemote vr = vrh.create(vid, caller.getSuid(), name, type, unit, comm, caller);                      
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not create variable", e);
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns the number of variable sets for the phenotype
     * @param formdata The formdata to filter on
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of variable sets could not be retrieved
     * @return The number of variable sets
     */
    public int getNumberOfVariableSets(FormDataManager formdata, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VARS_R", caller);

        try {                        
            Collection variablesets = vsrh.findByQuery(formdata);
            return variablesets.size();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not count variable sets.", e);
        }
    }    
    
    /**
     * Returns a collection of variable sets for a sampling unit
     * @return A collection of variable sets
     * @param formdata The formdata to filter on
     * @param suid The sampling unit id
     * @param pageManager The current page manager object
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable sets could not be retrieved
     */
    public Collection getVariableSets(int suid, PageManager pageManager, com.arexis.mugen.MugenCaller caller, FormDataManager formdata) throws ApplicationException {
        validate("VARS_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            VariableSetRemote vr = null;
            VariableSetDTO dto = null;
            Collection variablesets = vsrh.findByQuery(formdata);
            
            Iterator itr = variablesets.iterator();
            
            int index = 0;
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            
            while (itr.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    vr = (VariableSetRemote)itr.next();
                    dto = new VariableSetDTO(vr);

                    // Add to array
                    dtoList.add(dto);
                } else {
                    // Return if we have enough data
                    if(dtoList.size() == pageManager.getDelta())
                        return dtoList;                       
                    // Skip this object. This is outside the interval
                    itr.next();
                }
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get variable sets.", e);
        }
        return dtoList;
    }    
    
    /**
     * Returns a specified variable set
     * @param vsid The variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable set could be retrived
     * @return A variable set data transfer object
     */
    public VariableSetDTO getVariableSet(int vsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VARS_R", caller);
        
        try{
            VariableSetRemote vr = vsrh.findByPrimaryKey(new Integer(vsid));
            
            return new VariableSetDTO(vr);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get variable set.", e);
        }   
    }    

    /**
     * Updates a variable set
     * @param vsid The variable set id
     * @param name The name of the variable set
     * @param comm The comment for the variable set
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable set could not be updated
     */
    public void updateVariableSet(int vsid, java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VARS_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);        
        try{ 
            VariableSetRemote vr = vsrh.findByPrimaryKey(new Integer(vsid));                      
            vr.setCaller(caller);
            vr.addHistory();
            vr.setName(name);
            vr.setComm(comm);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update variable set", e);
        }
    }

    /**
     * Removes a variable set
     * @param vsid The variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable set could not be removed
     */
    public void removeVariableSet(int vsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VARS_W", caller);
        
        try{ 
            VariableSetRemote vr = vsrh.findByPrimaryKey(new Integer(vsid));                      
            vr.remove();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not delete variable set", e);
        }
    }

    /**
     * Creates a new variable set
     * @param name The name of the variable set
     * @param comm The comment for the variable set
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable set could not be created
     */
    public void createVariableSet(java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VARS_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);         
        try{ 
            makeConnection();
            int vsid = getIIdGenerator().getNextId(conn, "variable_sets_seq");    
            VariableSetRemote vr = vsrh.create(vsid, caller.getSuid(), name, comm, caller);                      
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not create variable set", e);
        } finally {
            releaseConnection();
        }
    }

    /**
     * Retrievs all variables for the specified variable set
     * @param vsid The variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variables could not be retrieved
     * @return A collection of variables
     */
    public Collection getVariablesInSet(int vsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VARS_R", caller);
        Collection dtoList = new ArrayList();
        try {  
            VariableDTO dto = null;
                        
            UserRemote ur = null;            
            
            VariableSetRemote vsr = vsrh.findByPrimaryKey(new Integer(vsid));    
            
            Collection variables = vsr.getAllVariablesInSet();
            
            VariableRemote vr = null;
            
            SpeciesRemote sr = null;
            
            SamplingUnitRemote sur = null;                        
            
            Iterator itr = variables.iterator();
            
            while (itr.hasNext()) {
                vr = vrh.findByPrimaryKey((Integer)itr.next());
                dto = new VariableDTO(vr);

                // Add to array
                dtoList.add(dto);
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get variables in set:"+vsid, e);
        }
        return dtoList;
    }

    /**
     * Adds variables to a variable set
     * @param vids The variable ids to add
     * @param vsid The variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable could no be added to the variable set
     */
    public void addVarsInSet(int vsid, String[] vids, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VARS_W", caller);        
        try {                       
            if(vsid == 0 || vids == null || vids.length == 0)
                return;
            
            VariableSetRemote vs = vsrh.findByPrimaryKey(new Integer(vsid));
            int vid = 0;
            for(int i=0;i<vids.length;i++){
                vid = Integer.parseInt(vids[i]);                
                vs.addVariable(vid, caller);
            }
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Could not insert variable(s) in set", se);
        }
    }

    /**
     * Removes variables from a variable set
     * @param vids The variables to remove
     * @param vsid The variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable could not be removed from the variable set
     */
    public void removeVarsInSet(int vsid, String[] vids, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VARS_W", caller);               
        try {                       
            if(vsid == 0 || vids == null || vids.length == 0)
                return;
            
            VariableSetRemote vs = vsrh.findByPrimaryKey(new Integer(vsid));
            int vid = 0;
            for(int i=0;i<vids.length;i++){
                vid = Integer.parseInt(vids[i]);                
                vs.removeVariable(vid, caller);
            }
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Could not remove variable(s) from set", se);
        }            
    }
    
    /**
     * Returns the number of variables in a variable set
     * @param vsid The variable set id
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of variables in the set could be retrieved
     * @return The number of variables in a variable set
     */
    public int getNumberOfVariablesInSet(int vsid, MugenCaller caller) throws ApplicationException {
        validate("VARS_R", caller);
       
        try {          
            VariableSetRemote vr = vsrh.findByPrimaryKey(new Integer(vsid));         
            return vr.getNumberOfVariables();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not count variables in set.", e);
        }         
    }    

    /**
     * Returns all variable sets, not only the number of rows as adjusted in the project settings
     * @param suid The sampling unt id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable sets could not be retrieved
     * @return A collection of variable sets
     */
    public Collection getAllVariableSets(int suid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VARS_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            VariableSetRemote vr = null;
            VariableSetDTO dto = null;
            Collection variablesets = vsrh.findAllVariableSets(suid);            
            Iterator itr = variablesets.iterator();
            
            while (itr.hasNext()) {
                vr = (VariableSetRemote)itr.next();
                dto = new VariableSetDTO(vr);

                // Add to array
                dtoList.add(dto);
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get variable sets.", e);
        }
        return dtoList;
    }
    

    /**
     * Returns the number of unified variables given the filter parameters in the formdata object
     * @param formdata The formdata
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of unified variables could not be retrieved
     * @return The number of unified variables given the filter
     */
    public int getNumberOfUVariables(FormDataManager formdata, MugenCaller caller) throws ApplicationException {
       validate("UVAR_R", caller);
        try {          
            Collection variables = uvrh.findByQuery(formdata);
            return variables.size();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get unified variables.", e);
        }
    }    

    /**
     * Returns a number of unified variables for the specified project. The number of unified variables depends on the project settings.
     * @return A collection of unified variables
     * @param formdata The formdata to filter on
     * @param pageManager The pagemanager which handles the page navigation
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variables could not be retrieved
     */
    public Collection getUVariables(PageManager pageManager, MugenCaller caller, FormDataManager formdata) throws ApplicationException {
        validate("UVAR_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            UVariableRemote vr = null;
            UVariableDTO dto = null;
            SpeciesRemote sr = null;
            SamplingUnitRemote sur = null;
            Collection variables = uvrh.findByQuery(formdata);
            UserRemote ur = null;
            Iterator itr = variables.iterator();
            int index = 0;
            pageManager.setMax(variables.size());
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            
            while (itr.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    vr = (UVariableRemote)itr.next();

                    ur = userHome.findByPrimaryKey(new Integer(vr.getId())); 
                    sur = surh.findByPrimaryKey(new Integer(caller.getSuid()));
                    sr = sur.getSpecies();
                    dto = new UVariableDTO(vr, ur, sr);

                    // Add to array
                    dtoList.add(dto);
                } else {
                    // Return if we have enough data
                    if(dtoList.size() == pageManager.getDelta())
                        return dtoList;                       
                    // Skip this object. This is outside the interval
                    itr.next();
                }
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get unified variables.", e);
        }
        return dtoList;
    }

  
    /**
     * Returns a specific unified variable
     * @param uvid The unified variable id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable could not be retrived
     * @return A unified variable
     */
    public UVariableDTO getUVariable(int uvid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVAR_R", caller);
        
        try{
            UVariableRemote vr = uvrh.findByPrimaryKey(new Integer(uvid));
            
            UserRemote ur = userHome.findByPrimaryKey(new Integer(vr.getId()));
            
            SamplingUnitRemote sur = surh.findByPrimaryKey(new Integer(caller.getSuid()));         
            
            SpeciesRemote sr = sur.getSpecies();            
            
            return new UVariableDTO(vr, ur, sr);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get unified variable.", e);
        }  
    }

    /**
     * Updates a unified variable set
     * @param uvid The unified variable id
     * @param sid The species id
     * @param name The name
     * @param unit The unit
     * @param type The type
     * @param comm The comment
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable could not be updated
     */
    public void updateUVariable(int uvid, int sid, java.lang.String name, java.lang.String unit, java.lang.String type, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVAR_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256); 
        validate("Unit", unit, 10);
        validate("Type", type, 1);         
        try{ 
            UVariableRemote vr = uvrh.findByPrimaryKey(new Integer(uvid));                      
            vr.setCaller(caller);
            vr.addHistory();
            vr.setName(name);
            vr.setUnit(unit);
            vr.setType(type);
            vr.setComm(comm);
            vr.setSid(sid);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update unified variable", e);
        }
    }

    /**
     * Removes an unified variable
     * @param uvid The unified variable id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable could not be updated
     */
    public void removeUVariable(int uvid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVAR_W", caller);
        
        try{ 
            UVariableRemote vr = uvrh.findByPrimaryKey(new Integer(uvid));                      
            vr.remove();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not delete unified variable", e);
        }
    }

    /**
     * Creates a new unified variable set
     * @param sid The species id
     * @param name The name
     * @param unit The unit
     * @param type The type
     * @param comm The comment
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable could not be created
     */
    public void createUVariable(java.lang.String name, java.lang.String unit, java.lang.String type, java.lang.String comm, com.arexis.mugen.MugenCaller caller, int sid) throws ApplicationException {
        validate("UVAR_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256); 
        validate("Unit", unit, 10);
        validate("Type", type, 1);           
        try{ 
            makeConnection();
            int uvid = getIIdGenerator().getNextId(conn, "u_variables_seq");         
        
            UVariableRemote vr = uvrh.create(uvid, caller.getPid(), sid, name, comm, unit, type, caller);                      
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not create unified variable", e);
        } finally {
            releaseConnection();
        }
    }

    /**
     * Returns all unified variable sets for the specified species and the current project
     * @return A collection of unified variable sets
     * @param sid The species id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable sets could not be retrieved
     */
    public Collection getAllUVariableSets(com.arexis.mugen.MugenCaller caller, int sid) throws ApplicationException {
        validate("UVARS_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            UVariableSetRemote vr = null;
            UVariableSetDTO dto = null;
            
            SpeciesRemote sr = null;             

            Collection variablesets = uvsrh.findAllUVariableSets(sid, caller.getPid());
            
            UserRemote ur = null;
            
            Iterator itr = variablesets.iterator();
            
            while (itr.hasNext()) {
                vr = (UVariableSetRemote)itr.next();
                ur = userHome.findByPrimaryKey(new Integer(vr.getId())); 
                sr = sprh.findByPrimaryKey(new Integer(sid));  
                dto = new UVariableSetDTO(vr, ur, sr);

                // Add to array
                dtoList.add(dto);
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get unified variable sets.", e);
        }
        return dtoList;
    }

 
    
    /**
     * Returns the number of unified variable sets given the filter parameters in the formdata object
     * @param caller The caller
     * @param formdata The formdata object holding the filter parameters
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of unified variable sets could not be retrieved
     * @return The number of unified variable sets given the filter
     */
    public int getNumberOfUVariableSets(com.arexis.mugen.MugenCaller caller, FormDataManager formdata) throws ApplicationException {
        validate("UVARS_R", caller);

        try {          
            Collection uvariablesets = uvsrh.findByQuery(formdata);    
            return uvariablesets.size();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not count unified variable sets.", e);
        }
    }    

    /**
     * Retrieves unified variable sets for the specified species and the current project. The number of unified variable sets to be retrieved is controlled by how many rows of data that should be displayed for each page
     * @return A collection of unified variable sets
     * @param formdata The formdata to filter on
     * @param pageManager The page manager object controlling the navigtion
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable sets could not be retrieved
     */
    public Collection getUVariableSets(PageManager pageManager, com.arexis.mugen.MugenCaller caller, FormDataManager formdata) throws ApplicationException{
        validate("UVARS_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            UVariableSetRemote vr = null;
            UVariableSetDTO dto = null;
            
            SpeciesRemote sr = null;   
            
            int sid = caller.getSid();
            Collection uvariablesets = uvsrh.findByQuery(formdata);
            
            UserRemote ur = null;
            
            Iterator itr = uvariablesets.iterator();
            
            while (itr.hasNext()) {
                vr = (UVariableSetRemote)itr.next();
                ur = userHome.findByPrimaryKey(new Integer(vr.getId())); 
                
                sr = sprh.findByPrimaryKey(new Integer(sid));  
                dto = new UVariableSetDTO(vr, ur, sr);

                // Add to array
                dtoList.add(dto);
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get unified variable sets.", e);
        }
        return dtoList;
    }

    /**
     * Returns the specified unified variable set
     * @param uvsid The unified variable set
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable set could not be retrieved
     * @return The unified variable set
     */
    public UVariableSetDTO getUVariableSet(int uvsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVARS_R", caller);
        
        try{
            UVariableSetRemote vr = uvsrh.findByPrimaryKey(new Integer(uvsid));
            
            UserRemote ur = userHome.findByPrimaryKey(new Integer(vr.getId()));
            
            SamplingUnitRemote sur = surh.findByPrimaryKey(new Integer(caller.getSuid()));         
            
            SpeciesRemote sr = sur.getSpecies();            
            
            return new UVariableSetDTO(vr, ur, sr);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get unified variable set.", e);
        }   
    }

    /**
     * Updates an unified variable set
     * @param uvsid The unified variable set id
     * @param name The name of the unified variable set
     * @param comm The comment for the unified variable set
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable set could not be updated
     */
    public void updateUVariableSet(int uvsid, java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVARS_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);         
        try{ 
            UVariableSetRemote vr = uvsrh.findByPrimaryKey(new Integer(uvsid));                      
            vr.setCaller(caller);
            vr.addHistory();
            vr.setName(name);
            vr.setComm(comm);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update unified variable set", e);
        }
    }

    /**
     * Removes the specified unified variable set
     * @param uvsid The unified variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the specified unified variable set could not be removed
     */
    public void removeUVariableSet(int uvsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVARS_W", caller);
        
        try{ 
            UVariableSetRemote vr = uvsrh.findByPrimaryKey(new Integer(uvsid));                      
            vr.remove();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not delete unified variable set", e);
        }
    }

    /**
     * Creates a new unified variable set
     * @param sid The species id
     * @param name The name of the unified variable set
     * @param comm The comment for the unified variable set
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable set could not be created
     */
    public void createUVariableSet(java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller, int sid) throws ApplicationException {
        validate("UVARS_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);         
        try{ 
            makeConnection();            
            
            int uvsid = getIIdGenerator().getNextId(conn, "u_variable_sets_seq");
            
            UVariableSetRemote vr = uvsrh.create(uvsid, caller.getPid(), sid, name, comm, caller);                      
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not create unified variable set", e);
        } finally {
            releaseConnection();
        }
    }

    /**
     * Returns all unified variables for the specified unified variable set
     * @param uvsid The unified variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variables could not be retrieved
     * @return A collection of the unified variables for the specified unified variable set
     */
    public Collection getUVariablesInSet(int uvsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVARS_R", caller);
        Collection dtoList = new ArrayList();
        try {  
            UVariableDTO dto = null;
            
            UserRemote ur = null;            
                       
            UVariableSetRemote vsr = uvsrh.findByPrimaryKey(new Integer(uvsid));    
            
            Collection uvariables = vsr.getAllUVariablesInSet();
                        
            UVariableRemote vr = null;            
            
            SamplingUnitRemote sur = surh.findByPrimaryKey(new Integer(caller.getSuid()));                       
                        
            SpeciesRemote sr = sur.getSpecies();            
            
            Iterator itr = uvariables.iterator();
            
            while (itr.hasNext()) {
                vr = uvrh.findByPrimaryKey((Integer)itr.next());

                ur = userHome.findByPrimaryKey(new Integer(vr.getId())); 
                
                dto = new UVariableDTO(vr, ur, sr);

                // Add to array
                dtoList.add(dto);
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get unified variables in set:"+uvsid, e);
        }
        return dtoList;
    }

    /**
     * Returns all unified variables for the current project and species
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variables could not be retrieved
     * @return A collection of unified variables for the current project and species
     */
    public Collection getAllUVariables(com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVAR_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            UVariableRemote vr = null;
            UVariableDTO dto = null;
            SpeciesRemote sr = null;
            SamplingUnitRemote sur = null;
            Collection variables = uvrh.findAllUVariables(caller.getPid());
            UserRemote ur = null;
            Iterator itr = variables.iterator();
            
            while (itr.hasNext()) {
                vr = (UVariableRemote)itr.next();

                ur = userHome.findByPrimaryKey(new Integer(vr.getId())); 
                sur = surh.findByPrimaryKey(new Integer(caller.getSuid()));
                sr = sur.getSpecies();
                dto = new UVariableDTO(vr, ur, sr);

                // Add to array
                dtoList.add(dto);
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get unified variables.", e);
        }
        return dtoList;
    }

    /**
     * Adds unified variables to the specified unified variable set
     * @param uvids The ids of the unified variables to add
     * @param uvsid The unified variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable could not be added to the set
     */
    public void addUVarsInSet(int uvsid, String[] uvids, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVARS_W", caller);        
        try {                       
            if(uvsid == 0 || uvids == null || uvids.length == 0)
                return;
            
            UVariableSetRemote vs = uvsrh.findByPrimaryKey(new Integer(uvsid));
            int uvid = 0;
            for(int i=0;i<uvids.length;i++){
                uvid = Integer.parseInt(uvids[i]);                
                vs.addUVariable(uvid, caller);
            }
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Could not insert unified variable(s) in set", se);
        }
    }

    /**
     * Removes unified variables from an unified variable set
     * @param uvids The unified variables to remove
     * @param uvsid The unfied variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable could not be removed from the set
     */
    public void removeUVarsInSet(int uvsid, String[] uvids, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVARS_W", caller);                     
        try {                       
            if(uvsid == 0 || uvids == null || uvids.length == 0)
                return;
            
            UVariableSetRemote vs = uvsrh.findByPrimaryKey(new Integer(uvsid));
            int uvid = 0;
            for(int i=0;i<uvids.length;i++){
                uvid = Integer.parseInt(uvids[i]);                
                vs.removeUVariable(uvid, caller);
            }
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Could not remove unified variable(s) from set", se);
        }  
    }

    /**
     * Returns the history for the phenotype
     * @param vid The variable id
     * @param iid The individual id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The phenotype history
     */
    public Collection getPhenotypeHistory(int vid, int iid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("PHENO_R", caller);         
        try {
            PhenotypePk pk = new PhenotypePk(vid, iid);            
            PhenotypeRemote pr = prh.findByPrimaryKey(pk);

            return pr.getHistory();            
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find phenotype history.", se);
        }
    }

    /**
     * Returns the history for the variable
     * @param vid The variable id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The variable history
     */
    public Collection getVariableHistory(int vid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VAR_R", caller);         
        try {         
            VariableRemote vr = vrh.findByPrimaryKey(new Integer(vid));

            return vr.getHistory();            
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find variable history.", se);
        }
    }

    /**
     * Returns the variable set history
     * @param vsid The variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variable set history could not be retrieved
     * @return The variable set history
     */
    public Collection getVariableSetHistory(int vsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VARS_R", caller);         
        try {         
            VariableSetRemote vr = vsrh.findByPrimaryKey(new Integer(vsid));

            return vr.getHistory();            
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find variable set history.", se);
        }
    }

    /**
     * Returns the unified variable set history
     * @param uvsid The unified variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The unified variable set history
     */
    public Collection getUVariableSetHistory(int uvsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVARS_R", caller);         
        try {         
            UVariableSetRemote vr = uvsrh.findByPrimaryKey(new Integer(uvsid));

            return vr.getHistory();            
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find unified variable set history.", se);
        }
    }

    /**
     * Returns the unified variable history
     * @param uvid The unified variable id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified variable set history could not be retrieved
     * @return The history for the unified variable
     */
    public Collection getUVariableHistory(int uvid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVAR_R", caller);         
        try {         
            UVariableRemote vr = uvrh.findByPrimaryKey(new Integer(uvid));

            return vr.getHistory();            
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find unified variable history.", se);
        }
    }

    /**
     * Returns the variables that are not in the specified variable set
     * @param vsid The variable set id
     * @param caller The current caller object
     * @param suid The sampling unit id
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variables could not be retrieved
     * @return The variables that are not in the specified variable set
     */
    public Collection getAvailableVariables(int vsid, com.arexis.mugen.MugenCaller caller, int suid) throws ApplicationException {
        validate("VAR_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            VariableRemote vr = null;
            SpeciesRemote sr = null;
            SamplingUnitRemote sur = null;            
            VariableDTO dto = null;
            Collection variables = vrh.findByNotInVariableSet(vsid, suid);
            UserRemote ur = null;
            Iterator itr = variables.iterator();
            
            while (itr.hasNext()) {
                vr = (VariableRemote)itr.next();
                dto = new VariableDTO(vr);

                // Add to array
                dtoList.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get variables.", e);
        }
        return dtoList;
    }

    /**
     * Returns the unified variables that are not in the specified unified variable set
     * @param uvsid The unified variable set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the variables could not be retrieved
     * @return The unified variables that are not in the specified unified variable set
     */
    public Collection getAvailableUVariables(int uvsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UVAR_R", caller);
        Collection dtoList = new ArrayList();
        try {          
            UVariableRemote vr = null;
            SpeciesRemote sr = null;
            SamplingUnitRemote sur = null;            
            UVariableDTO dto = null;
            Collection variables = uvrh.findByNotInUVariableSet(uvsid, caller.getPid());
            UserRemote ur = null;
            Iterator itr = variables.iterator();
            
            while (itr.hasNext()) {
                vr = (UVariableRemote)itr.next();

                ur = userHome.findByPrimaryKey(new Integer(vr.getId()));  
                sur = surh.findByPrimaryKey(new Integer(caller.getSuid()));
                sr = sur.getSpecies();                

                dto = new UVariableDTO(vr, ur, sr);

                // Add to array
                dtoList.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get unified variables.", e);
        }
        return dtoList;
    }

    /**
     * Returns the unified variable mappings
     * @param uvid The unified variable id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the mappings could not be retrieved
     * @return The mappings for the unified variable
     */
    public Collection getUVariableMappings(int uvid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VAR_R", caller);
         
        UVariableMappingDTO dto = null;
        Collection mappings = new ArrayList();
        makeConnection();
        
        Collection arr = new ArrayList();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select suname, suid, vname, uvid, vid from v_r_uvid_vid_1 where uvid = ?");
            ps.setInt(1, uvid);

            rs = ps.executeQuery();
            
            while (rs.next()) {
                mappings.add(new UVariableMappingDTO(rs.getString("suname"), rs.getString("vname"), rs.getInt("suid"), rs.getInt("vid"), rs.getInt("uvid")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new ApplicationException("Could not get unified variable mappings.", se);
        } finally {
            releaseConnection();
        }
        return mappings;
    }

    /**
     * Creates a new mapping for the unified variable
     * @param uvid The unified variable id
     * @param vid The variable id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the mapping could not be created
     */
    public void createUVariableMapping(int uvid, int vid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("VAR_W", caller);
        makeConnection();
        
        Collection arr = new ArrayList();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("insert into r_uvid_vid (uvid, vid, ts) values (?,?,?)");
            ps.setInt(1, uvid);
            ps.setInt(2, vid);
            ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));

            ps.execute();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new ApplicationException("Could not create unified variable mappings.", se);
        } finally {
            releaseConnection();
        }
    }

    /**
     * Removes the mapping for the unified variable
     * @param uvid The unified variable id
     * @param vid The variable id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the mapping could not be removed
     */
    public void removeUVariableMapping(int uvid, int vid, com.arexis.mugen.MugenCaller caller) throws ApplicationException  {
        validate("VAR_W", caller);
        makeConnection();
        
        Collection arr = new ArrayList();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("delete from r_uvid_vid where uvid = ? and vid = ?");
            ps.setInt(1, uvid);
            ps.setInt(2, vid);

            ps.execute();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new ApplicationException("Could not remove unified variable mappings.",se);
        } finally {
            releaseConnection();
        }
    }
}
