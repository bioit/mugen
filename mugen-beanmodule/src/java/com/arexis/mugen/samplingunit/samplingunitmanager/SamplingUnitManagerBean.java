package com.arexis.mugen.samplingunit.samplingunitmanager;
import com.arexis.arxframe.PageManager;
import com.arexis.arxframe.io.FileDataObject;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.CallerIsNullException;
import com.arexis.mugen.exceptions.ExceptionLogUtil;
import com.arexis.mugen.exceptions.GroupNotFoundException;
import com.arexis.mugen.exceptions.GroupingNotFoundException;
import com.arexis.mugen.exceptions.IllegalDateException;
import com.arexis.mugen.exceptions.IndividualExistsException;
import com.arexis.mugen.exceptions.IndividualNotFoundException;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.exceptions.SamplingUnitNotFoundException;
import com.arexis.mugen.genotype.genotypemanager.PedigreeDeriver;
import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.modelmanager.ModelManagerRemote;
import com.arexis.mugen.phenotype.phenotypemanager.PhenotypeManagerRemote;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.ParamDataObject;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.resource.link.LinkRemote;
import com.arexis.mugen.resource.resource.ResourceRemote;
import com.arexis.mugen.resource.resource.ResourceRemoteHome;
import com.arexis.mugen.resource.resourcemanager.ResourceManagerRemote;
import com.arexis.mugen.samplingunit.group.GroupRemote;
import com.arexis.mugen.samplingunit.group.GroupRemoteHome;
import com.arexis.mugen.samplingunit.grouping.GroupingRemote;
import com.arexis.mugen.samplingunit.grouping.GroupingRemoteHome;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import com.arexis.util.graph.Graph;
import com.arexis.util.graph.Node;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator; 
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import org.apache.log4j.Logger;

/**
 * This is the bean class for the SamplingUnitManagerBean enterprise bean.
 * Created Jul 14, 2005 3:33:33 PM
 * @author heto
 */
public class SamplingUnitManagerBean extends AbstractMugenBean implements javax.ejb.SessionBean, com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemoteBusiness {
    private javax.ejb.SessionContext context;
    private SamplingUnitRemoteHome surh;
    private GroupingRemoteHome grgh;
    private GroupRemoteHome grh;
    private IndividualRemoteHome irh;
    private SpeciesRemoteHome sprh;
    private UserRemoteHome urh;
    private ProjectRemoteHome projectHome;
    private ResourceRemoteHome resourceHome;
    
    private ModelManagerRemote modelManager;
    private PhenotypeManagerRemote phenotypeManager;
    private ResourceManagerRemote resourceManager;

    private ExpModelRemote o;
    
    private static Logger logger = Logger.getLogger(SamplingUnitManagerBean.class);
    
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
        surh = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
        grgh = (GroupingRemoteHome)locator.getHome(ServiceLocator.Services.GROUPING);
        grh = (GroupRemoteHome)locator.getHome(ServiceLocator.Services.GROUP);
        irh = (IndividualRemoteHome)locator.getHome(ServiceLocator.Services.INDIVIDUAL);
        sprh = (SpeciesRemoteHome)locator.getHome(ServiceLocator.Services.SPECIES);
        urh = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
        resourceHome = (ResourceRemoteHome)locator.getHome(ServiceLocator.Services.RESOURCE);
        
        modelManager = (ModelManagerRemote)locator.getManager(ServiceLocator.Services.MODELMANAGER);
        phenotypeManager = (PhenotypeManagerRemote)locator.getManager(ServiceLocator.Services.PHENOTYPEMANAGER);
        resourceManager = (ResourceManagerRemote)locator.getManager(ServiceLocator.Services.RESOURCEMANAGER);
    }
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
    
    /**
     * Returns a collection of sampling units.
     * @param pid The project id
     * @param caller The current caller object for the work session
     * @throws com.arexis.mugen.exceptions.ApplicationException If the sampling units could not be retrieved.
     * @return A collection of sampling units for the project
     */
    public Collection getSamplingUnits(int pid, MugenCaller caller) throws ApplicationException {
        validate("SU_R", caller);
        Collection arr = new ArrayList();
        try {
            Collection samplingunits = surh.findByProject(pid);
            Iterator i = samplingunits.iterator();
            SamplingUnitRemote su;
            while (i.hasNext()) {
                su = (SamplingUnitRemote)i.next();
                arr.add(new SamplingUnitDTO(su.getSuid(), su.getName(), su.getSpecies().getSid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Error getting sampling units", e);
        }
        return arr;
    }
    
    /**
     * Checks if the sampling units contains any erroneus individuals with respect to birthdays etc.
     * @param suid The sampling unit id
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the check failed
     * @return The erroenus individuals
     */
    public Collection checkSamplingUnit(int suid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        Collection arr = new ArrayList();
        try {
            Collection individuals = irh.findBySamplingUnit(suid);
            SUCheckDTO dto = null;

            int status = 0;
            
            Iterator i = individuals.iterator();
            IndividualRemote ir;
            while (i.hasNext()) {
                ir = (IndividualRemote)i.next();
                status = ir.checkIndividual();
                if(status != 0) {
                    dto = new SUCheckDTO(ir.getIdentity());  
                 
                    if ((status & 1) == 1)
                       dto.setFNotMale("X");
                    else if ((status & 16) == 16)
                       dto.setFDisabled("X");
                    else if ((status & 2) == 2)
                       dto.setFTooYoung("X");
                    else if ((status & 4) == 4)
                       dto.setMNotFemale("X");
                    else if ((status & 32) == 32)
                       dto.setMDisabled("X");
                    else if ((status & 8) == 8)
                       dto.setMTooYoung("X");
                    
                    arr.add(dto);
                }    
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Error getting sampling units", e);
        }
        return arr;
    }    
    
    /**
     * Returns the number of sampling units for the specified species
     * @return The number of sampling units for the specified species
     * @param qdo The parameters to filter on
     * @param sid The species id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the operation failed
     */
    public int getNumberOfSamplingUnits(int sid, com.arexis.mugen.MugenCaller caller, ParamDataObject qdo) throws ApplicationException {
        validate("SU_R", caller);
        makeConnection();
        
        Statement st = null;    
        int num = 0;
        try {
            String sql = "select count(suid) as num from v_sampling_units_3 where pid = '"+caller.getPid()+"' ";

            String extendSQL = buildQueryConditions(qdo);
            System.out.println(sql+extendSQL);
            st = conn.createStatement();
            ResultSet result = st.executeQuery(sql+extendSQL);         
            
            if (result.next()) {
                num = result.getInt("num");               
            }           
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to count sampling units."+se.getMessage(), se);
        } finally {
            releaseConnection();
        }
        
        return num;
    }    
    
    /**
     * Returns the groupings for the current project.
     * @param suid The sampling unit id for the groupings.
     * @param pageManager The page display handler.
     * @param caller The user caller object.
     * @throws com.arexis.mugen.exceptions.ApplicationException In case anything went wrong when retrieving the groupings.
     * @return The groupings for the project.
     */
    public Collection getGroupings(int suid, PageManager pageManager, MugenCaller caller) throws ApplicationException {
        validate("GRP_R", caller);
        Collection grData = new ArrayList();
        try {
            int index = 0;
            Collection groupings = grgh.findBySamplingUnit(suid, caller);
            Iterator itr = groupings.iterator();

            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            
            GroupingRemote gr;
            Collection groups;
            GroupingDTO i;
            
            while (itr.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    gr = (GroupingRemote)itr.next();
                    
                    groups = grh.findByGrouping(gr.getGsid(), caller, suid);
                    
                    i = new GroupingDTO(gr, caller, groups.size());
                    
                    // Add to array
                    grData.add(i);
                } else {
                    // Return if we have enough data
                    if(grData.size() == pageManager.getDelta())
                        return grData;       
                    // Skip this object. This is outside the interval
                    itr.next();
                }              
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get groupings. suid="+suid, e);
        }
        return grData;
    }
    
    /**
     * Returns the groupings for the current project.
     * @param suid The sampling unit id for the groupings.
     * @param caller The user caller object.
     * @throws com.arexis.mugen.exceptions.ApplicationException In case anything went wrong when retrieving the groupings.
     * @return The groupings for the project.
     */
    public Collection getGroupings(int suid, MugenCaller caller) throws ApplicationException {
        validate("GRP_R", caller);
        Collection grData = new ArrayList();
        try {
            Collection groupings = grgh.findBySamplingUnit(suid, caller);
            Iterator itr = groupings.iterator();
            GroupingRemote gr;
            Collection groups;
            GroupingDTO i;
            
            while (itr.hasNext()) {               
                gr = (GroupingRemote)itr.next();

                groups = grh.findByGrouping(gr.getGsid(), caller, suid);

                i = new GroupingDTO(gr, caller, groups.size());

                // Add to array
                grData.add(i);           
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get groupings. suid="+suid, e);
        }
        return grData;
    }  
    
    /**
     * Returns a specific grouping.
     * @param gsid The id of the grouping to retrive.
     * @param caller The user caller object.
     * @throws com.arexis.mugen.exceptions.ApplicationException If the grouping could not be retrived.
     * @return A data transfer object containing the grouping.
     */
    public GroupingDTO getGrouping(int gsid, MugenCaller caller) throws ApplicationException {
        validate("GRP_R", caller);
        GroupingDTO gdto = null;
        try {           
            GroupingRemote gr = grgh.findByPrimaryKey(new Integer(gsid));
            gr.setCaller(caller);
            gdto = new GroupingDTO(gr, caller, 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get grouping. gsid="+gsid, e);
        }
        return gdto;
    }
    
    public GroupingDTO getGroupingByName(String name, int suid, MugenCaller caller) throws ApplicationException {
        validate("GRP_R", caller);
        GroupingDTO gdto = null;
        try {           
            GroupingRemote gr = grgh.findByNameAndSamplingUnit(name, suid, caller);
            gr.setCaller(caller);
            gdto = new GroupingDTO(gr, caller, 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get grouping. ", e);
        }
        return gdto;
    }
    
    /**
     * Returns the number of groupings that are in the samplling unit
     * @param suid The sampling unit id
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of groupings could not be retrieved
     * @return The number of groupings in the sampling unit
     */
    public int getNumberOfGroupings(int suid) throws ApplicationException {
        try {           
            SamplingUnitRemote su = surh.findByPrimaryKey(new Integer(suid));

            return su.getNumberOfGroupings();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not count groupings.", e);
        }
    }    
    
    /**
     * Creates a new grouping
     * @param name The name of the grouping
     * @param comm The comment for the grouping
     * @param caller The caller for the current work session
     * @param suid The current sampling unit id
     * @throws com.arexis.mugen.exceptions.ApplicationException If the grouping could not be created
     * @return The id of the new grouping
     */
    public int createGrouping(String name, String comm, MugenCaller caller, int suid) throws ApplicationException {
        validate("GRP_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);        
        int gsid = 0;
        try {
            makeConnection();
            gsid = getIIdGenerator().getNextId(conn, "groupings_seq");
            releaseConnection();
            
            SamplingUnitRemote sur  = surh.findByPrimaryKey(new Integer(suid));
            sur.setCaller(caller);

            GroupingRemote grp = grgh.create(gsid, name, comm, sur, caller);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create a new grouping.", e);
        }
        return gsid;
    }
    
    /**
     * Updates a grouping
     * @param gsid The grouping id
     * @param name The (new?) name of the grouping
     * @param comm The (new?) comment for the grouping
     * @param caller The caller for the current work session
     * @throws com.arexis.mugen.exceptions.ApplicationException If the grouping could not be updated
     */
    public void updateGrouping(int gsid, String name, String comm, MugenCaller caller) throws ApplicationException {
        validate("GRP_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);        
        try{
            GroupingRemote group  = grgh.findByPrimaryKey(new Integer(gsid));
            group.setCaller(caller);
            group.addHistory();
            group.setName(name);
            group.setComm(comm);
        }catch(Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not update grouping with gsid="+gsid, e);
        }
    }
    
    /**
     * Removes a grouping
     * @param gsid The grouping id
     * @param caller The caller for the current work session
     * @throws com.arexis.mugen.exceptions.ApplicationException If the grouping could not be removed
     */
    public void removeGrouping(int gsid, MugenCaller caller) throws ApplicationException {
        validate("GRP_W", caller);
        try{
            GroupingRemote grouping  = grgh.findByPrimaryKey(new Integer(gsid));
            Collection groups = grouping.getGroups(caller);
            GroupRemote group = null;
            
            Iterator i = groups.iterator();
            Iterator j = null;
            Collection inds = null;
            IndividualRemote ind = null;
            
            while(i.hasNext()) {
                group = (GroupRemote)i.next();
                inds = group.getIndividuals();
                j = inds.iterator();
                while(j.hasNext()) {
                    ind = (IndividualRemote)j.next();
                    removeIndInGroup(group.getGid(), ind.getIid(), caller);
                }
                group.remove();
            }
            grouping.remove();
        }catch(Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove grouping", e);
        }
    }
    
    /**
     * Retrieves a collection of groups belonging to the grouping argument
     * @return A collection of groups belonging to the specified grouping
     * @param suid The sampling unit id
     * @param gsid The grouping id
     * @param pageManager The page navigation handler object
     * @param caller The caller for the current work session
     * @throws com.arexis.mugen.exceptions.ApplicationException If the groups could not be retrieved
     */
    public Collection getGroups(int gsid, PageManager pageManager, com.arexis.mugen.MugenCaller caller, int suid) throws ApplicationException {
        validate("GRP_R", caller);
        Collection grData = new ArrayList();
        try {
            int index = 0;
            Collection groups = grh.findByGrouping(gsid, caller, suid);

            GroupingRemote gr = grgh.findByPrimaryKey(new Integer(gsid));
            gr.setCaller(caller);
            
            Iterator itr = groups.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            
            GroupRemote group;
            GroupDTO i;
            
            while (itr.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    group = (GroupRemote)itr.next();
                    group.setCaller(caller);
                    i = new GroupDTO(group, gr, caller);
                    grData.add(i);
                } else {
                    // Return if we have enough data
                    if(grData.size() == pageManager.getDelta())
                        return grData;                      
                    // Skip this object. This is outside the interval
                    itr.next();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get groups. gsid="+gsid, e);
        }
        
        return grData;
    }
    
    /**
     * Retrieves a collection of groups belonging to the grouping argument
     * @return A collection of groups belonging to the specified grouping
     * @param suid The sampling unit id
     * @param gsid The grouping id
     * @param caller The caller for the current work session
     * @throws com.arexis.mugen.exceptions.ApplicationException If the groups could not be retrieved
     */
    public Collection getGroups(int gsid, com.arexis.mugen.MugenCaller caller, int suid) throws ApplicationException {
        validate("GRP_R", caller);
        Collection grData = new ArrayList();
        try {
            Collection groups = grh.findByGrouping(gsid, caller, suid);
            GroupingRemote gr = grgh.findByPrimaryKey(new Integer(gsid));
            gr.setCaller(caller);
            
            Iterator itr = groups.iterator();
            
            GroupRemote group = null;
            GroupDTO i = null;            
            
            while (itr.hasNext()) {
                group = (GroupRemote)itr.next();
                group.setCaller(caller);
                i = new GroupDTO(group, gr, caller);
                grData.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get groups. gsid="+gsid, e);
        }
        
        return grData;
    }    
    
    /**
     * Retrieves the number of groups for the grouping
     * @param gsid The grouping id
     * @param caller The caller for the current work session
     * @throws com.arexis.mugen.exceptions.ApplicationException If the data could not be collected
     * @return The number of groups for the grouping
     */
    public int getNumberOfGroups(int gsid, MugenCaller caller) throws ApplicationException {
        int groups = 0;
        try{
            GroupingRemote gs = grgh.findByPrimaryKey(new Integer(gsid));
            groups = gs.getNumberOfGroups();
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Failed to get number of groups", e);
        }
        
        return groups;
    }
    
    /**
     * Retrieves the group history
     * @return A collection of group dto's
     * @param gid The group id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the groups history could not be found
     */    
    public Collection getGroupHistory(int gid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("GRP_R", caller);
        try {            
            GroupRemote gr = grh.findByPrimaryKey(new Integer(gid));
            gr.setCaller(caller);
            return gr.getHistory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get group history. gid="+gid, e);
        }
    }    
    
    /**
     * Retrieves a group with the specified group id
     * @param gid The group id
     * @param caller The caller object for the current work session
     * @throws com.arexis.mugen.exceptions.ApplicationException If the group could not be retrived
     * @return A group
     */
    public GroupDTO getGroup(int gid, com.arexis.mugen.MugenCaller caller) throws ApplicationException{
        validate("GRP_R", caller);
        GroupDTO gdto = null;
        try {           
            GroupRemote gr = grh.findByPrimaryKey(new Integer(gid));
            gr.setCaller(caller);
            GroupingRemote gri = gr.getGrouping();
            gri.setCaller(caller);
            
            gdto = new GroupDTO(gr, gri, caller);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get group. gid="+gid, e);
        }
        return gdto;
    }
    

    /**
     * Creates a new group
     * @return The id of the new group
     * @param name The name of the group
     * @param comm The comment for the group
     * @param caller The current caller object
     * @param gsid The groupings id that this group should belong to
     * @throws com.arexis.mugen.exceptions.ApplicationException If the group could not be created
     */
    public int createGroup(String name, String comm, com.arexis.mugen.MugenCaller caller, int gsid) throws ApplicationException {
        validate("GRP_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);        
        int gid = 0;
        try {
            makeConnection();
            gid = getIIdGenerator().getNextId(conn, "groups_seq");
            releaseConnection();
            
            GroupingRemote gri = grgh.findByPrimaryKey(new Integer(gsid));
            gri.setCaller(caller);
            GroupRemote gr  = grh.create(gid, name, gri, comm, caller);
            
        }
        catch (FinderException e)
        {
            logger.error("Failed to find grouping", e);
            throw new GroupingNotFoundException("Could not find grouping");
        }
        catch (CreateException e)
        {
            throw new ApplicationException("Could not create a new group.", e);
        }
        catch (RemoteException e)
        {
            logger.error("Remote Exception");
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
       
        return gid;
    }
    
    /**
     * Removes the group with the specified group id
     * @param gid The group id
     * @param caller The caller object for the current work session
     * @throws com.arexis.mugen.exceptions.ApplicationException If the group could not be removed
     */
    public void removeGroup(int gid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("GRP_W", caller);
        try{
            GroupRemote group  = grh.findByPrimaryKey(new Integer(gid));
            
            group.remove();
        }catch(Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove group with gid="+gid, e);
        }
    }
    
    /**
     * Updated the specified group
     * @param gid The group id
     * @param name The (new?) name
     * @param comm The (new?) comment
     * @param caller The caller object for the current work session
     * @throws com.arexis.mugen.exceptions.ApplicationException If the group could not be updated
     */
    public void updateGroup(int gid, java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("GRP_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);        
        try{            
            GroupRemote group  = grh.findByPrimaryKey(new Integer(gid));
            group.setCaller(caller);
            group.addHistory();
            group.setName(name);
            group.setComm(comm);
        }catch(Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not update group with gid="+gid, e);
        }
    }
    
    /**
     * Retrieves the samplingunits that exist for the current species and project
     * @return A collection of sampling unit dto's
     * @param qdo Filtering parameters
     * @param sid The species id
     * @param pageManager The pagemanager object handling data page browsing info
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the sampling units could not be found
     */
    public Collection getSamplingUnits(MugenCaller caller, PageManager pageManager, ParamDataObject qdo, int sid) throws ApplicationException {
        validate("SU_R", caller);
        makeConnection();
        
        Collection arr = new ArrayList();
        
        Statement st = null;    
        
        try {
            String sql = "select * from v_sampling_units_3 where pid = '"+caller.getPid()+"' and sid = "+sid;

            String extendSQL = buildQueryConditions(qdo);

            st = conn.createStatement();
            ResultSet result = st.executeQuery(sql+extendSQL); 
            
            SamplingUnitDTO dto = null;
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;         
            
            while (result.next()) {
                index++;
                
                if (index>=start && index<=stop) {
                    
                    SamplingUnitRemote su = surh.findByPrimaryKey(new Integer(result.getInt("suid")));
                    su.setCaller(caller);
                    dto = new SamplingUnitDTO(su);
                    
                    /*
                    dto = new SamplingUnitDTO(result.getInt("suid"), result.getString("name"));
                    dto.setComm(result.getString("comm"));
                    dto.setStatus(result.getString("status"));
                    dto.setTs(result.getDate("ts"));
                    dto.setUsr(result.getString("usr"));
                    dto.setInds(result.getInt("inds"));
                    */

                    arr.add(dto);
                } else {                    
                    result.next();
                }                
            }           
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find sampling units."+se.getMessage(), se);
        } finally {
            releaseConnection();
        }
        return arr;
    } 
    
    /**
     * Retrieves the samplingunits history
     * @return A collection of sampling unit dto's
     * @param suid The sampling unit id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the sampling units could not be found
     */
    public Collection getSamplingUnitHistory(MugenCaller caller, int suid) throws ApplicationException {
        validate("SU_R", caller);         
        try {
            SamplingUnitRemote sur = surh.findByPrimaryKey(new Integer(suid));
            sur.setCaller(caller);
            return sur.getHistory();            
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find sampling units history.", se);
        }
    } 
    
    /**
     * Retrieves the grouping history
     * @return A collection of grouping dto's
     * @param gsid The grouping id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the grouping history could not be found
     */    
    public Collection getGroupingHistory(int gsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("GRP_R", caller);         
        try {
            GroupingRemote gr = grgh.findByPrimaryKey(new Integer(gsid));
            gr.setCaller(caller);
            return gr.getHistory();            
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find grouping history.", se);
        }
    }    
    
    /**
     * Creates a new sampling unit
     * @return The id of the new sampling unit
     * @param pid the project the sampling unit should be connected to
     * @param name The name of the sampling unit
     * @param comm The comment for the sampling unit
     * @param status The status of the sampling unit
     * @param caller The current caller object
     * @param sid The species id for which this sampling unit belongs to
     * @throws com.arexis.mugen.exceptions.ApplicationException If the sampling unit could not be created
     */
    public int createSamplingUnit(String name, String comm, String status, int sid, int pid, MugenCaller caller) throws ApplicationException {
        int suid = 0;
        validate("SU_W", caller);        
        validate("Name", name, 20);
        validate("Comment", comm, 256);            
        
        try {
            SamplingUnitRemote tmp = null;
            try
            {
                tmp = surh.findByName(name, caller);
            } catch (FinderException ex)
            {
                //ex.printStackTrace();
                tmp = null;
            }
            if (tmp!=null)
                throw new ApplicationException("Sampling unit name is not unique. Please find another name.");
            
            
            
            
            makeConnection();
            suid = getIIdGenerator().getNextId(conn, "sampling_units_seq");
            
            SpeciesRemote sr = sprh.findByPrimaryKey(new Integer(sid));
            SamplingUnitRemote sur  = surh.create(new Integer(suid), name, comm, sr, caller);
            
            ProjectRemote p = projectHome.findByPrimaryKey(new Integer(pid));
            p.addSamplingUnit(sur);
            
            
            
        } 
        catch (ApplicationException e)
        {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create a new sampling unit.", e);
        } finally {
            releaseConnection();
        }
        
        return suid;
    }
    
    
    
    /**
     * Returns the default sampling unit. This depends on the pid in the caller 
     * object.
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the default sampling unit could not be retrieved
     * @return The default sampling unit
     */
    public SamplingUnitDTO getDefaultSamplingUnit(MugenCaller caller) throws ApplicationException
    {
        if (caller==null)
            throw new CallerIsNullException("Caller is not set");
        validate("SU_R", caller);        
        SamplingUnitDTO dto = null;                
        try {
            SamplingUnitRemote su;
            Collection arr = surh.findByProject(caller.getPid());
            Iterator i = arr.iterator();
            if (i.hasNext())
            {
                su = (SamplingUnitRemote)i.next();
                su.setCaller(caller);
                dto = new SamplingUnitDTO(su);
            }
            else
            {
                ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(caller.getPid()));
                Collection species = prj.getSpecies();
                Iterator is = species.iterator();
                SpeciesRemote s = null;
                if (is.hasNext())
                    s = (SpeciesRemote)is.next();
                else
                    throw new ApplicationException("No species assigned to this project.");
                
                
                caller.setSid(s.getSid());
                    
                logger.debug("Default sampling unit not found. Auto creating default sampling unit");
                int suid = createSamplingUnit(prj.getName()+".default", "", "E", caller.getSid(), caller.getPid(), caller);
                su = surh.findByPrimaryKey(new Integer(suid));
                dto = new SamplingUnitDTO(su);
            }    
        }
        catch (RemoteException e)
        {
            logger.error("RemoteException", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
        catch (FinderException e)
        {
            logger.error("Failed to find sampling unit", e);
            throw new ApplicationException("Unable to get default sampling unit.", e);
        }
        /*
        catch (Exception se) 
        {
            logger.error("cannot get default sampling unit", se);
            throw new ApplicationException("Unable to find sampling unit", se);
        }
         */
        return dto;
    }

    /**
     * Method for retrieval of a single sample unit (DTO)
     * @param caller The current caller object
     * @param suid The sampling unit id of the unit to retrieve
     * @throws com.arexis.mugen.exceptions.ApplicationException If the sampling unit could not be retrieved
     * @return A Sampling Unit Data transfer object (DTO)
     */
    public SamplingUnitDTO getSamplingUnit(com.arexis.mugen.MugenCaller caller, int suid) throws ApplicationException {
        validate("SU_R", caller);        
        SamplingUnitDTO dto = null;                
        try {
            SamplingUnitRemote su  = surh.findByPrimaryKey(new Integer(suid));
            dto = new SamplingUnitDTO(su);
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find sampling unit", se);
        }
        return dto;
    }

    /**
     * Updates the information about a sampling unit
     * @param suid The id of the sampling unit to update
     * @param name The (new?) name of the sampling unit
     * @param comm The (new?) comment for the sampling unit
     * @param status The (new?) status of the sampling unit
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the sampling unit could not be updated
     */
    public void updateSamplingUnit(int suid, java.lang.String name, java.lang.String comm, java.lang.String status, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("SU_W", caller);        
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        try{
            SamplingUnitRemote su  = surh.findByPrimaryKey(new Integer(suid));
            
            su.setCaller(caller);
            
            su.addHistory();

            su.setName(name);
            su.setComm(comm);
            su.setStatus(status);
        }
        catch (FinderException e)
        {
            logger.error("Update failed, Sampling unit was not found.",e);
            throw new SamplingUnitNotFoundException("Update failed, Sampling unit was not found.", e);
        }
        catch (RemoteException e)
        {
            logger.error("Update failed. Remote exception",e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
    }

    /**
     * Removes a sampling unit
     * @param suid The id of the sampling unit to remove
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the sampling unit could not be retrieved
     */
    public void removeSamplingUnit(int suid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("SU_W", caller);     
        validate("IND_W", caller);
        validate("GRP_W", caller);
        validate("MODEL_W", caller);
        try{
            
            phenotypeManager.removePhenotypesInSamplingUnit(suid, caller);
            
            SamplingUnitRemote su  = surh.findByPrimaryKey(new Integer(suid));
            Iterator inds = su.getIndividuals().iterator();
            while (inds.hasNext())
            {
                IndividualRemote ind = (IndividualRemote)inds.next();
                ind.remove();
            }
            
            
            Iterator models = su.getExperimentalModels().iterator();
            while (models.hasNext())
            {
                ExpModelRemote m = (ExpModelRemote)models.next();
                modelManager.removeModel(m.getEid(), caller);
                //m.remove();
            }
            
            Iterator groupings = su.getGroupings().iterator();
            while (groupings.hasNext())
            {
                GroupingRemote grps = (GroupingRemote)groupings.next();
                grps.remove();
            }
            
            
            
            su.remove();
        }catch(Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove sampling unit with suid="+suid, e);
        }
    }

    /**
     * Method for retrieval of all species for a project
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If something went wrong during the retrieval
     * @return A collection of species remote interfaces
     */
    public Collection getSpeciesForProject(int pid, MugenCaller caller) throws ApplicationException {
        
        if (pid!=caller.getPid() && !caller.isAdmin())
            throw new PermissionDeniedException("User is not allowed to get species.");
        
        validate("SU_R", caller);        
        
        Collection species = new ArrayList();
        try{
            SpeciesRemote sr;
            Collection keys = sprh.findByProject(pid); //findAllSpecies(pid);

            Iterator itr = keys.iterator();            
            
            while(itr.hasNext()){      

                 sr = (SpeciesRemote)itr.next();

                 species.add(sr);
            }
        }catch(Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get species", e);
        }
        
        return species;
    }

    /**
     * Returns the history for the individual
     * @param iid The individual id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The history for the individual
     */
    public Collection getIndHistory(int iid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("IND_R", caller);
        try {
            IndividualRemote ir = irh.findByPrimaryKey(new Integer(iid));
            ir.setCaller(caller);
            return ir.getHistory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get individual history. iid="+iid, e);
        }
    }
    
    /**
     * Adds the specified individual to the specified group
     * @param gid The group to add to
     * @param iid The individual to add
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individual could not be added
     */
    public void addIndInGroup(int gid, int iid, MugenCaller caller) throws ApplicationException {
        IndividualRemote indRem = null;
        GroupRemote grRem = null;
        try{
            indRem = irh.findByPrimaryKey(new Integer(iid));
            grRem = grh.findByPrimaryKey(new Integer(gid));
            grRem.setCaller(caller);
            grRem.addIndividual(indRem);
        }
        catch (FinderException e)
        {
            logger.error("find exception", e);
            if (indRem == null)
                throw new IndividualNotFoundException("Cannot find individual "+iid);
            if (grRem == null)
                throw new GroupNotFoundException("Cannot find group "+gid);
        }
        catch (RemoteException e)
        {
            logger.error("Remote exception", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
    }
    
    public void createGroupInfo(String groupingName, String groupName, int suid, String identity, MugenCaller caller) throws ApplicationException
    {
        //System.out.println("groupingName="+groupingName+", groupName="+groupName+", suid="+suid+", identity="+identity);
        SamplingUnitRemote su = null;
        IndividualRemote ind = null;
        try{
            su = surh.findByPrimaryKey(new Integer(suid));
            
            int gsid = 0;
            try
            {
                //System.out.println("Find Grouping");
                GroupingRemote grouping = null;
                grouping = grgh.findByNameAndSamplingUnit(groupingName, suid, caller);
                gsid = grouping.getGsid();
            } 
            catch (FinderException ex)
            {
                //System.out.println("Create Grouping");
                gsid = createGrouping(groupingName, "", caller, su.getSuid());
                //grouping = grgh.findByPrimaryKey(new Integer(gsid));
            }
            
            int gid = 0;
            try
            {
                //System.out.println("Find Group");
                GroupRemote group = null;
                group = grh.findByNameAndGrouping(groupName, gsid, caller);
                gid = group.getGid();
            }
            catch (FinderException ex)
            {
                //System.out.println("Create Group");
                gid = createGroup(groupName, "", caller, gsid);
                //group = grh.findByPrimaryKey(new Integer(gid));
            }
            
            //System.out.println("Find individual");
            ind = irh.findByIdentityAndSamplingUnit(identity, su.getSuid(), caller);
            
            //System.out.println("Add to group");
            addIndInGroup(gid, ind.getIid(), caller);
            
            //System.out.println("Done group info");
            
        }
        catch (FinderException e)
        {
            logger.error("Finder exception su or ind is not found",e);
            if (su == null)
                throw new SamplingUnitNotFoundException("Sampling unit not found",e);
            else if (ind == null)
                throw new IndividualNotFoundException("Individual not found", e);
        }
        catch (RemoteException e)
        {
            logger.error("Remote error", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
    }
    
    public boolean individualExists(String identity, int suid, MugenCaller caller)
    {
        try
        {
            IndividualRemote i = irh.findByIdentityAndSamplingUnit(identity, suid, caller);
            if (i!=null)
                return true;
        } 
        catch (RemoteException e)
        {
            logger.error("Remote exception", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
        catch (FinderException e)
        {
        }
        return false;
    }
    
    public int createNewIndividual(int suid, MugenCaller caller, String identity, String alias, String sex, String father, String mother, String birthdate, String comm) throws ApplicationException 
    {
        int eid = 0;
        try
        {
            logger.debug("CreateNewIndividual: "+ suid+"\n"+caller+"\n"+ identity+"\n"+ alias+"\n"+ sex +"\n"+ father +"\n"+ mother+
                    "\n"+birthdate+"\n"+comm);
            
            
            IndividualRemote tmp = null;
            int fatherid = 0;
            if (father!=null && !father.equals(""))
            {
                tmp = irh.findByIdentityAndSamplingUnit(father, suid, caller);
                fatherid = tmp.getEid();
            }
            
            int motherid = 0;
            if (mother!=null && !mother.equals(""))
            {
                tmp = irh.findByIdentityAndSamplingUnit(mother, suid, caller);
                motherid = tmp.getEid();
            }
            eid = createNewIndividual(suid, caller, identity, alias, sex, fatherid, motherid, birthdate, comm);
            
            logger.debug("CreateNewIndividual ended");
            return eid;
        }
        catch (FinderException e)
        {
            //e.printStackTrace();
            //throw new EJBException("Failed to find parents.", e);
            throw new ApplicationException("Failed to find parents",e);
        }
        catch (RemoteException e)
        {
            logger.error("Remote Exception occured", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
    }
    
    
    /**
     * Creates a new individual
     * @return The id of the created individual
     * @param identity The identity
     * @param alias The alias
     * @param sex The sex
     * @param father The father id
     * @param mother The mother id
     * @param birthdate The birthdate
     * @param comm The comment
     * @param suid The sampling unit id which the individual should belong to
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individual could not be created
     */
    public int createNewIndividual(int suid, MugenCaller caller, String identity, String alias, String sex, int father, int mother, String birthdate, String comm) throws ApplicationException {
        logger.debug("createNewIndividual started");
        
        
        
        validate("IND_W", caller);
        validate("Identity", identity, 11);
        validate("Sex", sex, 1);  
        validate("Alias", alias, 11);
        if(birthdate != null)
            validate("Birthdate", birthdate, new SimpleDateFormat("yyyy-mm-dd"));
        validate("Comment", comm, 256);
        int eid = 0;
        try {
            GregorianCalendar cal = null;
            if(birthdate != null) {
                
                if (!birthdate.matches("\\d{4}-\\d{2}-\\d{2}"))
                    throw new IllegalDateException("Date is not valid. Valid format is YYYY-MM-DD");
                
                String[] splitted = birthdate.split("-"); 
                
                int year = Integer.parseInt(splitted[0]);
                int month = Integer.parseInt(splitted[1]);
                int day = Integer.parseInt(splitted[2]);
                cal = new GregorianCalendar(year, month-1, day); // Month value is zero based! Jan = 0    
            }
            makeConnection();
            eid = getIIdGenerator().getNextId(conn, "expobj_seq");
            
            
            logger.error("Next eid is "+eid);
            
            SamplingUnitRemote sur  = surh.findByPrimaryKey(new Integer(suid));
            IndividualRemote ind  = irh.create(eid, identity, sur, caller);
            
            logger.debug("Individual created: "+ind);
            
            logger.debug("rollbackOnly:"+this.context.getRollbackOnly());
            
            ind.setCaller(caller);
            if(birthdate != null)
                ind.setBirthDate(new java.sql.Date(cal.getTimeInMillis()));              
            ind.setAlias(alias);
            ind.setSex(sex);
            ind.setStatus("E");
            ind.setComm(comm);
            
            if(father > 0)
                ind.setFatherId(father);
            if(mother > 0)
                ind.setMotherId(mother);             
           
            logger.debug("createNewIndividual ended");
            logger.debug("rollbackOnly:"+this.context.getRollbackOnly());
        }
        catch (DuplicateKeyException e)
        {
            logger.error("Individual already exits", e);
            throw new IndividualExistsException("Could not create individual, individual already exists!", e);
        }
        catch (CreateException e)
        {
            logger.error("Create failed", e);
            //throw new EJBException("Could not create a new individual, Maybe already exists?", e);
            throw new ApplicationException("Could not create a new individual", e);
        }
        catch (FinderException e)
        {
            logger.error("Failed to find sampling unit", e);
            throw new SamplingUnitNotFoundException("Cannot find sampling unit", e);
        }
        catch (RemoteException e)
        {
            logger.error("Remote exception occured", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
        catch (ApplicationException e)
        {
            logger.error("Failed to create individual", e);
            throw new ApplicationException("Failed to create individual", e);
        }
        catch (Exception e)
        {
            logger.error("Unknown exception", e);
            throw new ApplicationException("Could not create individual");
        }
        finally
        {
            releaseConnection();
        }        
        logger.debug("rollbackOnly:"+this.context.getRollbackOnly());
        logger.debug("createNewIndividual returned eid="+eid);
        return eid;
    }
    
    /**
     * Create or Update an individual. If the individual does not exist, create 
     * it. If the individual does exists, update the values. 
     * This is used in the import system.
     * @param suid is the sampling unit id
     * @param caller 
     * @param identity 
     * @param alias 
     * @param sex 
     * @param father 
     * @param mother 
     * @param birthdate 
     * @param comm 
     * @throws com.arexis.mugen.exceptions.ApplicationException 
     * @return iid
     */
    public int createOrUpdateIndividual(int suid, MugenCaller caller, String identity, String alias, String sex, String father, String mother, String birthdate, String comm) throws ApplicationException 
    {
        logger.info("createOrUpdate called");
        int iid = 0;
        try
        {
            iid = updateIndividualFromIdentity(suid, identity, alias, sex, "E", father, mother, birthdate, comm, caller);
            logger.info("update done");
        }
        catch (IndividualNotFoundException e)
        {
            iid = createNewIndividual(suid, caller, identity, alias, sex, father, mother, birthdate, comm);
            logger.info("create done", e);
        }
        return iid;
    }
    
    /**
     * 
     * @param suid 
     * @param identity 
     * @param alias 
     * @param sex 
     * @param status 
     * @param father 
     * @param mother 
     * @param birthdate 
     * @param comm 
     * @param caller 
     * @throws com.arexis.mugen.exceptions.ApplicationException 
     * @throws com.arexis.mugen.exceptions.IndividualNotFoundException if the individual to update is not found
     * @return 
     */
    public int updateIndividualFromIdentity(int suid, String identity, java.lang.String alias, java.lang.String sex, java.lang.String status, String father, String mother, java.lang.String birthdate, String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException, IndividualNotFoundException
    {
        SamplingUnitRemote sur = null;
        IndividualRemote ind = null;
        try
        {
            sur  = surh.findByPrimaryKey(new Integer(suid));
            ind  = irh.findByIdentityAndSamplingUnit(identity, suid, caller);
            
            int fatherid=0;
            int motherid=0;
            if (father!=null && !father.equals(""))
                 fatherid = irh.findByIdentityAndSamplingUnit(father,suid,caller).getIid();
            if (mother!=null && !mother.equals(""))
                 motherid = irh.findByIdentityAndSamplingUnit(mother, suid, caller).getIid();
            
            updateIndividual(ind.getIid(), ind.getIdentity(), alias, sex, status, fatherid, motherid, birthdate, comm, caller);
            return ind.getIid();
        }
        catch (FinderException e)
        {
            if (sur == null)
                throw new SamplingUnitNotFoundException("Sampling unit not found. Update failed.", e);
            else if (ind == null)
                throw new IndividualNotFoundException("Individual was not found. Update failed.", e);
        }
        catch (RemoteException r)
        {
            throw ExceptionLogUtil.createLoggableEJBException(r);
        }
        return -1;
    }
    
    /**
     * Updates an individual
     * @param iid The individual id
     * @param identity The identity
     * @param alias The alias of the individual
     * @param sex The sex of the individual
     * @param status The status
     * @param father The id of the father
     * @param mother The id of the mother
     * @param birthdate The birthdate
     * @param comm The comment
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individual could not be updated
     */
    public void updateIndividual(int iid, java.lang.String identity, java.lang.String alias, java.lang.String sex, java.lang.String status, int father, int mother, java.lang.String birthdate, String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("IND_W", caller);
        validate("Identity", identity, 11);
        validate("Sex", sex, 1);  
        validate("Alias", alias, 11);
        if(birthdate != null)
            validate("Birthdate", birthdate, new SimpleDateFormat("yyyy-mm-dd"));
        validate("Comment", comm, 256);

        try {
            GregorianCalendar cal = null;
             
            if(birthdate != null) {
                String[] splitted = birthdate.split("-"); 
                if(splitted.length != 3)
                    throw new IllegalDateException("Date is not valid. Valid date is in format yyyy-mm-dd");            
                try{
                    int year = Integer.parseInt(splitted[0]);
                    int month = Integer.parseInt(splitted[1]);
                    int day = Integer.parseInt(splitted[2]);
                    cal = new GregorianCalendar(year, month-1, day); // Month value is zerobased! Jan = 0                               
                } catch (NumberFormatException nfe) {
                    throw new IllegalDateException("Date is not valid. Valid date is in format yyyy-mm-dd", nfe);
                }    
            }
               
            IndividualRemote ind  = irh.findByPrimaryKey(new Integer(iid));
            ind.setCaller(caller);
            ind.addHistory();
            ind.setIdentity(identity);
            if(birthdate != null)
                ind.setBirthDate(new java.sql.Date(cal.getTimeInMillis()));              
            ind.setAlias(alias);
            ind.setSex(sex);
            ind.setStatus("E");
            ind.setComm(comm);
            
            if(father > 0)
                ind.setFatherId(father);
            if(mother > 0)
                ind.setMotherId(mother);             
        } 
        catch (FinderException e) 
        {
            logger.error("Individual not found in db", e);
            throw new IndividualNotFoundException("Could not update individual, not found in database", e);
        }
        catch (RemoteException e)
        {
            logger.error("Remote exception", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
        
    }
    
    /**
     * Removed an individual
     * @param iid The id of the individual to remove
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individual could not be removed
     */
    public void removeIndividual(int iid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("IND_W", caller);
        try {
            IndividualRemote ir  = irh.findByPrimaryKey(new Integer(iid));
            ir.remove();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove individual.", e);
        }
    }
    
    /**
     * Retrieves individuals that belong to a certain group.
     * @return A collection of individuals
     * @param caller The current caller object
     * @param gid The group the individuals should belong to
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individuals could not be retrieved
     */
    public Collection getIndividualsByGroup(int gid, MugenCaller caller) throws ApplicationException {
        validate("GRP_R", caller);
        Collection indData = new ArrayList();
        try {
            Collection individuals = irh.findByGroup(gid);
            Iterator itr = individuals.iterator();
            IndividualRemote ind;
            ParentDTO father, mother;
            UserRemote ur;
            IndividualDTO i;
            
            while (itr.hasNext()) {
                ind = (IndividualRemote)itr.next();
                
                father = null;
                mother = null;
                
                if (ind.getFather()!=null)
                    father = new ParentDTO(ind.getFather());
                if (ind.getMother()!=null)
                    mother = new ParentDTO(ind.getMother());
                
                ur = ind.getUser();
                
                i = new IndividualDTO(ind.getIid(), ind.getIdentity(),
                        ind.getAlias(), ind.getSex(), ind.getBirthDate().toString(),
                        father, mother, ur.getUsr(), ind.getTs().toString(), ind.getStatus(), ind.getComm());
                
                // Add to array
                indData.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get individuals for group. gid="+gid, e);
        }
        return indData;
    }
    
    /**
     * Removes the specified individual from the specified group
     * @param gid The group to remove from
     * @param iid The individual to remove
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individual could not be removed
     */
    public void removeIndInGroup(int gid, int iid, MugenCaller caller) throws ApplicationException {
        try{
            IndividualRemote indRem = irh.findByPrimaryKey(new Integer(iid));
            GroupRemote grRem = grh.findByPrimaryKey(new Integer(gid));
            grRem.removeIndividual(indRem);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove individual "+iid+" from group "+gid, e);
        }
    }  
    
    /**
     * Fetches the number of individuals depending on the query filter
     * @return The number of individuals found through the conditioned query as defined by the user
     * @param formdata The parameters to filter on
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of individuals could not be retrieved
     */
    public int getNumberOfIndividuals(FormDataManager formdata, MugenCaller caller) throws ApplicationException{
        validate("IND_R", caller);        
        try {        
            return irh.getNumberOfIndividuals(formdata);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ApplicationException)
                throw (ApplicationException)e;
            else            
                throw new ApplicationException("Could not count individuals.", e);
        }          
    }    
    
    /**
     * Returns the number of individuals in a sampling unit
     * @param suid The sampling unit id
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of individuals could not be retrieved
     * @return The number of individuals in a sampling unit
     */
    public int getNumberOfIndividuals(int suid) throws ApplicationException {
        try {
            return ((SamplingUnitRemote)surh.findByPrimaryKey(new Integer(suid))).getNumberOfIndividuals();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not count individuals.", e);
        } 
    }    
    
    /**
     * Returns the individuals in the sampling unit
     * @return A collection of individual data transfer objects
     * @param formdata The formdata with filtering parameters
     * @param caller The current caller object
     * @param suid The sampling unit id
     * @param pageManager The page navigation object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individuals could not be retrieved
     */
    public Collection getIndividuals(int suid, PageManager pageManager, MugenCaller caller, FormDataManager formdata) throws ApplicationException {
        validate("IND_R", caller);
        Collection indData = new ArrayList();
        try {
            Collection individuals = irh.findByQuery(formdata, pageManager);
            Iterator itr = individuals.iterator();           

            ParentDTO father, mother;
            IndividualRemote ind;
            UserRemote ur;
            IndividualDTO i;
            
            while (itr.hasNext()) {
                ind = (IndividualRemote)itr.next();

                father = null;
                mother = null;

                ur = ind.getUser();

                if (ind.getFather()!=null)
                    father = new ParentDTO(ind.getFather());
                if (ind.getMother()!=null)
                    mother = new ParentDTO(ind.getMother());


                i = new IndividualDTO(ind.getIid(), ind.getIdentity(),
                        ind.getAlias(), ind.getSex(), ind.getBirthDate().toString(),
                        father, mother, ur.getUsr(), ind.getTs().toString(), ind.getStatus(), ind.getComm());

                // Add to array
                indData.add(i);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ApplicationException)
                throw (ApplicationException)e;
            else
                throw new ApplicationException("Could not get individuals. suid="+suid, e);
        }
        return indData;
    }
    
    /**
     * Returns a specific individual
     * @return An individual data transfer object with data regarding the individual
     * @param caller The current caller object
     * @param iid The id of the individual to retrieve
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individual could not be retrieved
     */
    public IndividualDTO getIndividual(int iid, MugenCaller caller) throws ApplicationException {
        validate("IND_R", caller);
        IndividualDTO i = null;
        try {
            IndividualRemote ind = irh.findByPrimaryKey(new Integer(iid));
            
            ParentDTO father = null;
            ParentDTO mother = null;
            
            if (ind.getFather()!=null)
                father = new ParentDTO(ind.getFather());
            if (ind.getMother()!=null)
                mother = new ParentDTO(ind.getMother());
            
            UserRemote ur = ind.getUser();
            
            i = new IndividualDTO(ind.getIid(), ind.getIdentity(),
                    ind.getAlias(), ind.getSex(), ind.getBirthDate().toString(),
                    father, mother, ur.getUsr(), ind.getTs().toString(), ind.getStatus(), ind.getComm());
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get individuals. iid="+iid, e);
        }
        
        return i;
    }
    
    /**
     * A collection of ParentDTO objects
     * @param suid The sampling unit id for which the males should belong to
     * @throws com.arexis.mugen.exceptions.ApplicationException If the males could not be retrieved
     * @return A collection of parent data transfer objects
     */
    public Collection getMales(int suid) throws ApplicationException {
        Collection arr = new ArrayList();
        try {
            Collection inds = irh.findMale(suid);
            Iterator i = inds.iterator();
            IndividualRemote ind;
            ParentDTO p;
            
            while (i.hasNext()) {
                ind = (IndividualRemote)i.next();
                p = new ParentDTO(ind);
                arr.add(p);
            }
        } catch (Exception e) {
            throw new ApplicationException("Unable to get males in the sampling unit ["+suid+"]", e);
        }
        return arr;
    }
    
    /**
     * Returns the females belonging to the sampling unit passed along as argument
     * @param suid The sampling unit id to which the females should belong
     * @throws com.arexis.mugen.exceptions.ApplicationException If the females could not be retrieved
     * @return A collection of parent data transfer objects
     */
    public Collection getFemales(int suid) throws ApplicationException {
        Collection arr = new ArrayList();
        try {
            Collection inds = irh.findFemale(suid);
            Iterator i = inds.iterator();
            IndividualRemote ind;
            ParentDTO p;
            
            while (i.hasNext()) {
                ind = (IndividualRemote)i.next();
                p = new ParentDTO(ind);
                arr.add(p);
            }
        } catch (Exception e) {
            throw new ApplicationException("Unable to get females in the sampling unit ["+suid+"]", e);
        }
        return arr;
    }
    
    /**
     * Returns a collection of individuals belonging to the sampling unit
     * @return A collection of individuals
     * @param caller The current caller object
     * @param suid The sampling unit which the individuals belong to
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individuals could not be retrieved
     */
    public Collection getIndividuals(int suid, MugenCaller caller) throws ApplicationException {
        validate("IND_R", caller);
        Collection indData = new ArrayList();
        try {
            Collection individuals = irh.findBySamplingUnit(suid);
            Iterator itr = individuals.iterator();
            ParentDTO father, mother;
            IndividualRemote ind;
            UserRemote ur;
            IndividualDTO i;
            
            while (itr.hasNext()) {
                ind = (IndividualRemote)itr.next();
                
                father = null;
                mother = null;
                
                if (ind.getFather()!=null)
                    father = new ParentDTO(ind.getFather());
                if (ind.getMother()!=null)
                    mother = new ParentDTO(ind.getMother());
                
                ur = ind.getUser();
                
                if (ind==null)
                    throw new ApplicationException("Individual not found error");
                if (ind.getBirthDate()==null)
                    throw new ApplicationException("Birthdate is null");
                if (ind.getTs()==null)
                    throw new ApplicationException("TS is null");
                
                i = new IndividualDTO(ind.getIid(), ind.getIdentity(),
                        ind.getAlias(), ind.getSex(), ind.getBirthDate().toString(),
                        father, mother, ur.getUsr(), ind.getTs().toString(), ind.getStatus(), ind.getComm());
                
                // Add to array
                indData.add(i);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("Failed to get individual", e);
            throw new ApplicationException("Could not get individuals. suid="+suid, e);
        }
        return indData;
    }    
    

    /**
     * Returns all individuals that are not in the specified group
     * @param suid The sampling unit id
     * @param gid The id of the group that the individual should NOT be in
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individuals could not be retrieved
     * @return The individuals that are not members of the specified group
     */
    public Collection getIndividualsNotInGroup(int suid, int gid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("IND_R", caller);
        Collection indData = new ArrayList();
        try {
            Collection individuals = irh.findByNonGroup(gid, suid);
            Iterator itr = individuals.iterator();
            IndividualRemote ind;
            ParentDTO father, mother;
            UserRemote ur;
            IndividualDTO i;
            
            while (itr.hasNext()) {
                ind = (IndividualRemote)itr.next();
                
                father = null;
                mother = null;
                
                if (ind.getFather()!=null)
                    father = new ParentDTO(ind.getFather());
                if (ind.getMother()!=null)
                    mother = new ParentDTO(ind.getMother());
                
                ur = ind.getUser();
                
                i = new IndividualDTO(ind.getIid(), ind.getIdentity(),
                        ind.getAlias(), ind.getSex(), ind.getBirthDate().toString(),
                        father, mother, ur.getUsr(), ind.getTs().toString(), ind.getStatus(), ind.getComm());
                
                // Add to array
                indData.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get individuals for group. gid="+gid, e);
        }
        return indData;
    }       

    /**
     * Creates a family tree using groupings and groups
     * @param fid The father id
     * @param mid The mother id
     * @param name The name of the grouping
     * @param comm The comment of the grouping
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the family tree could not be created
     */
    public void createFamilyGrouping(int fid, int mid, String name, String comm, MugenCaller caller) throws ApplicationException {
        validate("GRP_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);        
        int gsid = 0;
        try {
            makeConnection();
            gsid = getIIdGenerator().getNextId(conn, "groupings_seq");
            SamplingUnitRemote sur = surh.findByPrimaryKey(new Integer(caller.getSuid()));
            sur.setCaller(caller);

            // Create the grouping
            GroupingRemote grp = grgh.create(gsid, name, comm, sur, caller);
            if(grp == null)
                throw new ApplicationException("Failed at step 1 in create grouping, groups from family");
            
            // Derive the information regarding family relations           
            PedigreeDeriver pd = new PedigreeDeriver(false);
            IndividualRemote father = irh.findByPrimaryKey(new Integer(fid));
            IndividualRemote mother = irh.findByPrimaryKey(new Integer(mid));
            Collection inds = sur.getIndividuals();
            inds = pd.childView(inds, father, mother);
            
            // Make graph with nodes keeping track of their depth
            Graph g = pd.fullGraph(inds, father, mother, true);
            ArrayList nodes = g.getNodes();
            Iterator i = nodes.iterator();
            
            // Find out how many groups to create
            int maxLevel = 0;
            while(i.hasNext()) {
                Node n = (Node)i.next();
                if(n.getLevel() > maxLevel)
                    maxLevel = n.getLevel();                
            }
            
            // Store group id's in array for later retrieval when adding individuals
            int[] gids = new int[maxLevel+1];
            String generation = "00";
            // Create the groups
            for(int j=0;j<maxLevel+1;j++) {
                int gid = getIIdGenerator().getNextId(conn, "groups_seq");
                
                // Store group id's in array for later retrieval when adding individuals
                gids[j] = gid;
                
                // Padding for generation number
                if(j < 10)
                    generation = "00";
                else if(j < 100)
                    generation = "0";
                else
                    generation = "";
                
                // Create the group
                grh.create(gid, "F"+generation+j, grp, "Auto-created from family information. Started from father:"+father.getIdentity()+", mother:"+mother.getIdentity(), caller);
            }                        
            
            // Insert the individuals
            i = nodes.iterator();
            while(i.hasNext()) {
                Node n = (Node)i.next();
                addIndInGroup(gids[n.getLevel()], n.getId(), caller);
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create a new grouping.", e);
        } finally {
            releaseConnection();
        }
    }

    /**
     * Returns the sampling units for a species and project
     * @param pid The project id
     * @param sid The species id
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the sampling units could not be retrieved
     * @return The sampling units for the species and project
     */
    public Collection getSamplingUnits(int pid, int sid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("SU_R", caller);
        Collection arr = new ArrayList();
        try {
            Collection samplingunits = surh.findByProjectSpecies(pid, sid);
            Iterator i = samplingunits.iterator();
            SamplingUnitRemote su;
            while (i.hasNext()) {
                su = (SamplingUnitRemote)i.next();
                arr.add(new SamplingUnitDTO(su.getSuid(), su.getName(), su.getSpecies().getSid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Error getting sampling units", e);
        }
        return arr;
    }
    
    /**
     * Get two collections of individuals to a membership view.
     * First collection is the members for a group
     * Second collection is the remaining individuals filtered by the ParamDataObject
     * @return an array (size=2) of Collections. First is indcluded inds, second is other inds.
     * @param formdata The form parameters
     * @param gid is the group the individuals is member of
     * @param caller is the user making the call
     * @throws com.arexis.mugen.exceptions.ApplicationException if getting the collections fails.
     */
    public Collection[] getGroupMembership(int gid, FormDataManager formdata, MugenCaller caller) throws ApplicationException {
        try {
            
            Collection inds = irh.findByQuery(formdata);
            Collection indsDTO = new ArrayList();
            Iterator i = inds.iterator();
            while (i.hasNext())
            {
                IndividualDTO ind = new IndividualDTO((IndividualRemote)i.next());
                indsDTO.add(ind);
            }
            
            Collection includedInds = getIndividualsByGroup(gid, caller);
                                    
            indsDTO.removeAll(includedInds);
            
            Collection[] out = new Collection[2];
            out[0] = includedInds;
            out[1] = indsDTO;
            
            return out;
        } catch (Exception e) {
            throw new ApplicationException("Error getting individuals", e);
        }          
    }

    /**
     * Returns the experimental objects for a sampling unit. This returns all
     * individuals and all models.
     * @param suid The sampling unit id
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the experimental objects could not be retrieved
     * @return The experimental objects for the sampling unit
     */
    public Collection getExperimentalObjects(int suid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        
        if (!caller.hasPrivilege("IND_R") && !caller.hasPrivilege("MODEL_R"))
            throw new PermissionDeniedException("Permission denied then getting objects. Privilege IND_R or MODEL_R are needed");
        
        Collection dto = new ArrayList();
        try {
            SamplingUnitRemote samplingUnit = surh.findByPrimaryKey(new Integer(suid));
            samplingUnit.setCaller(caller);
            Collection expObjects = samplingUnit.getExperimentalObjects();
            Iterator itr = expObjects.iterator();

            while (itr.hasNext()) {                
                // Add to array
                Object o = itr.next();

                if(o instanceof IndividualRemote) 
                {
                    IndividualRemote expObj = (IndividualRemote)o;
                    dto.add(new ExperimentalObjectDTO(expObj.getEid(), expObj.getIdentity(), expObj.getUser().getUsr(), expObj.getUser().getId(), expObj.getAlias(), expObj.getComm(), expObj.getTs().toString()));
                } 
                else if(o instanceof ExpModelRemote) 
                {
                    ExpModelRemote expObj = (ExpModelRemote)o;
                    dto.add(new ExperimentalObjectDTO(expObj.getEid(), expObj.getIdentity(), expObj.getUser().getUsr(), expObj.getUser().getId(), expObj.getAlias(), expObj.getComm(), expObj.getTs().toString()));
                }               
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get experimental objects. suid="+suid, e);
        }
        return dto;
    }
    
    /**
     * Get the resource tree for all resources connected to the sampling unit
     * with the suid provided.
     * @param suid is the sampling unit id
     * @param caller is the caller of this method.
     * @throws com.arexis.mugen.exceptions.ApplicationException 
     * @return a collection of ResourceBranchDTO
     */
    public Collection getResourceTreeCollection(int suid, MugenCaller caller) throws ApplicationException
    {
        System.out.println("getResourceTreeCollection");
        Collection resourceTree = new ArrayList();
        try
        {
            SamplingUnitRemote su = surh.findByPrimaryKey(new Integer(suid));
            Collection resources = su.getResources();
            
            return resourceManager.getResourceTreeCollection(resources, caller);
        }   
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get resources", e);
        }
    }
    
    public void addLinkResource(String name, String comm, String url, int category, int suid, MugenCaller caller) throws ApplicationException
    {
        validate("RESOURCE_W", caller);
        System.out.println("SamplingUnitManager#addLinkResource start");
        try 
        {
            int resourceId = 0;
            ResourceRemote res = null;
            SamplingUnitRemote su = surh.findByPrimaryKey(new Integer(suid));
           
            // Store the link
            LinkRemote link = resourceManager.createLink(name, comm, url, caller);
            
            // Register the link as a resource
            res = resourceManager.createResource(link, category, caller);  
            su.addResource(res);
        } 
        catch (Exception e) 
        {
            logger.error("Failed to add resource", e);
            throw new ApplicationException("Could not add resource to sampling unit", e);            
        }
        System.out.println("ProjectManager#addResource end");
    }
    
    public void addResource(java.lang.String type, int category, int project, java.lang.String name, java.lang.String comm, FileDataObject fileData, com.arexis.mugen.MugenCaller caller, String url, int suid) throws ApplicationException {
        validate("RESOURCE_W", caller);
        System.out.println("SamplingUnitManager#addResource start");
        try 
        {
            int resourceId = 0;
            ResourceRemote res = null;
            SamplingUnitRemote su = surh.findByPrimaryKey(new Integer(suid));
            if(type.equalsIgnoreCase("file")) {
                // Store the file
                int fileId = resourceManager.saveFile(fileData.getFileName(), comm, caller, fileData).getFileId();
                // Register the file as a resource
                res = resourceManager.createResource(project, name, comm, fileId, 0, category, caller);
                su.addResource(res);
            }
            else if(type.equalsIgnoreCase("weblink")) {
                // Store the link
                int linkid = resourceManager.createLink(name, comm, url, caller).getLinkId();
                // Register the link as a resource
                res = resourceManager.createResource(project, name, comm, 0, linkid, category, caller);  
                su.addResource(res);
            }
            else 
                throw new ApplicationException("Unknown type");
      
            
        } 
        catch (Exception e) 
        {
            logger.error("Failed to add resource", e);
            throw new ApplicationException("Could not add resource to sampling unit", e);            
        }
        System.out.println("ProjectManager#addResource end");
    }
    
    //----------DUDE_TEST
    public void createSampleImport(){
        
    }
}
