package com.arexis.mugen.model.modelmanager;

import com.arexis.arxframe.PageManager;
import com.arexis.arxframe.io.FileDataObject;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.adminmanager.SpeciesDTO;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.ExceptionLogUtil;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.genotype.genotypemanager.ChromosomeDTO;
import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.expmodel.ExpModelRemoteHome;
import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemote;
import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemoteHome;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemoteHome;

import com.arexis.mugen.search.AlleleSearchResult;
import com.arexis.mugen.search.ModelSearchResult;
import com.arexis.mugen.search.MPSearchResult;
import com.arexis.mugen.species.gene.GeneRemote;
import com.arexis.mugen.species.gene.GeneRemoteHome;

import com.arexis.mugen.model.geneticbackground.GeneticBackgroundRemote;
import com.arexis.mugen.model.geneticbackground.GeneticBackgroundRemoteHome;
import com.arexis.mugen.model.geneticbackground.GeneticBackgroundValuesRemote;
import com.arexis.mugen.model.geneticbackground.GeneticBackgroundValuesRemoteHome;

import com.arexis.mugen.model.availability.AvailabilityPk;
import com.arexis.mugen.model.availability.AvailabilityRemote;
import com.arexis.mugen.model.availability.AvailabilityRemoteHome;
import com.arexis.mugen.model.availablegeneticbackgrounds.AvailableGeneticBackgroundRemote;
import com.arexis.mugen.model.availablegeneticbackgrounds.AvailableGeneticBackgroundRemoteHome;
import com.arexis.mugen.model.repositories.RepositoriesRemote;
import com.arexis.mugen.model.repositories.RepositoriesRemoteHome;

import com.arexis.mugen.model.geneontology.GeneOntologyRemote;
import com.arexis.mugen.model.geneontology.GeneOntologyRemoteHome;
import com.arexis.mugen.model.geneticmodification.GeneticModificationRemote;
import com.arexis.mugen.model.geneticmodification.GeneticModificationRemoteHome;
import com.arexis.mugen.model.reference.ReferenceRemote;
import com.arexis.mugen.model.reference.ReferenceRemoteHome;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemote;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemoteHome;
import com.arexis.mugen.model.strain.allele.StrainAlleleRemote;
import com.arexis.mugen.model.strain.allele.StrainAlleleRemoteHome;
import com.arexis.mugen.model.strain.mutationtype.MutationTypeRemote;
import com.arexis.mugen.model.strain.mutationtype.MutationTypeRemoteHome;
import com.arexis.mugen.model.strain.state.StrainStateRemote;
import com.arexis.mugen.model.strain.state.StrainStateRemoteHome;
import com.arexis.mugen.model.strain.strain.StrainRemote;
import com.arexis.mugen.model.strain.strain.StrainRemoteHome;
import com.arexis.mugen.model.strain.type.StrainTypeRemote;
import com.arexis.mugen.model.strain.type.StrainTypeRemoteHome;
import com.arexis.mugen.phenotype.phenotype.PhenotypeRemote;
import com.arexis.mugen.phenotype.variable.VariableRemote;
import com.arexis.mugen.phenotype.variableset.VariableSetRemote;
import com.arexis.mugen.phenotype.variableset.VariableSetRemoteHome;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.file.FileRemoteHome;
import com.arexis.mugen.resource.link.LinkRemote;
import com.arexis.mugen.resource.link.LinkRemoteHome;
import com.arexis.mugen.resource.resource.ResourceRemote;
import com.arexis.mugen.resource.resourcemanager.ResourceManagerRemote;
import com.arexis.mugen.samplingunit.expobj.ExpObj;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.search.FuncSigSearchResult;
import com.arexis.mugen.search.FuncSigTypeSearchResult;
import com.arexis.mugen.search.GenModSearchResult;
import com.arexis.mugen.search.GeneSearchResult;
import com.arexis.mugen.search.Keyword;
import com.arexis.mugen.search.ResearchApplicationSearchResult;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemoteHome;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;

import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyRemote;
import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyRemoteHome;
import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyPathRemote;
import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyPathRemoteHome;
import com.arexis.mugen.phenotype.ontology.PhenotypeXrefRemote;
import com.arexis.mugen.phenotype.ontology.PhenotypeXrefRemoteHome;
import com.arexis.mugen.phenotype.ontology.PhenotypeAltIdRemote;
import com.arexis.mugen.phenotype.ontology.PhenotypeAltIdRemoteHome;
import com.arexis.mugen.phenotype.ontology.PhenotypeSynonymRemote;
import com.arexis.mugen.phenotype.ontology.PhenotypeSynonymRemoteHome;

import com.arexis.mugen.simplelog.SimpleLogRemote;
import com.arexis.mugen.simplelog.SimpleLogRemoteHome;

import com.arexis.mugen.model.usercomments.UserCommsRemote;
import com.arexis.mugen.model.usercomments.UserCommsRemoteHome;

import java.io.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import org.apache.log4j.Logger;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

import org.mugen.dtos.*;

//ontology DTOs
import com.arexis.mugen.ontologies.pato.PatoDTO;
import com.arexis.mugen.ontologies.pato.PatoDTO_ALT;
import com.arexis.mugen.ontologies.pato.PatoDTO_ISA;
import com.arexis.mugen.ontologies.pato.PatoDTO_SUBSET;
import com.arexis.mugen.ontologies.pato.PatoDTO_SYN;
import com.arexis.mugen.ontologies.pato.PatoDTO_XREF;

import com.arexis.mugen.ontologies.ma.MaDTO;
import com.arexis.mugen.ontologies.ma.MaDTO_ALT;
import com.arexis.mugen.ontologies.ma.MaDTO_ISA;
import com.arexis.mugen.ontologies.ma.MaDTO_REL;
import com.arexis.mugen.ontologies.ma.MaDTO_SYN;
import com.arexis.mugen.ontologies.ma.MaDTO_XREF;

import com.arexis.mugen.ontologies.go.GoDTO;
import com.arexis.mugen.ontologies.go.GoDTO_ALT;
import com.arexis.mugen.ontologies.go.GoDTO_CON;
import com.arexis.mugen.ontologies.go.GoDTO_ISA;
import com.arexis.mugen.ontologies.go.GoDTO_REL;
import com.arexis.mugen.ontologies.go.GoDTO_REP;
import com.arexis.mugen.ontologies.go.GoDTO_SUBSET;
import com.arexis.mugen.ontologies.go.GoDTO_SYN;
import com.arexis.mugen.ontologies.go.GoDTO_XREF;

import com.arexis.mugen.ontologies.cell.CellDTO;
import com.arexis.mugen.ontologies.cell.CellDTO_ALT;
import com.arexis.mugen.ontologies.cell.CellDTO_CON;
import com.arexis.mugen.ontologies.cell.CellDTO_ISA;
import com.arexis.mugen.ontologies.cell.CellDTO_REL;
import com.arexis.mugen.ontologies.cell.CellDTO_REP;
import com.arexis.mugen.ontologies.cell.CellDTO_SUBSET;
import com.arexis.mugen.ontologies.cell.CellDTO_SYN;
import com.arexis.mugen.ontologies.cell.CellDTO_XREF;

import com.arexis.mugen.ontologies.chebi.ChebiDTO;
import com.arexis.mugen.ontologies.chebi.ChebiDTO_ALT;
import com.arexis.mugen.ontologies.chebi.ChebiDTO_CON;
import com.arexis.mugen.ontologies.chebi.ChebiDTO_ISA;
import com.arexis.mugen.ontologies.chebi.ChebiDTO_REL;
import com.arexis.mugen.ontologies.chebi.ChebiDTO_REP;
import com.arexis.mugen.ontologies.chebi.ChebiDTO_SUBSET;
import com.arexis.mugen.ontologies.chebi.ChebiDTO_SYN;
import com.arexis.mugen.ontologies.chebi.ChebiDTO_XREF;

import com.arexis.mugen.ontologies.emap.EmapDTO;
import com.arexis.mugen.ontologies.emap.EmapDTO_ALT;
import com.arexis.mugen.ontologies.emap.EmapDTO_CON;
import com.arexis.mugen.ontologies.emap.EmapDTO_ISA;
import com.arexis.mugen.ontologies.emap.EmapDTO_REL;
import com.arexis.mugen.ontologies.emap.EmapDTO_REP;
import com.arexis.mugen.ontologies.emap.EmapDTO_SUBSET;
import com.arexis.mugen.ontologies.emap.EmapDTO_SYN;
import com.arexis.mugen.ontologies.emap.EmapDTO_XREF;

import com.arexis.mugen.ontologies.doid.DoidDTO;
import com.arexis.mugen.ontologies.doid.DoidDTO_ALT;
import com.arexis.mugen.ontologies.doid.DoidDTO_CON;
import com.arexis.mugen.ontologies.doid.DoidDTO_ISA;
import com.arexis.mugen.ontologies.doid.DoidDTO_REL;
import com.arexis.mugen.ontologies.doid.DoidDTO_REP;
import com.arexis.mugen.ontologies.doid.DoidDTO_SUBSET;
import com.arexis.mugen.ontologies.doid.DoidDTO_SYN;
import com.arexis.mugen.ontologies.doid.DoidDTO_XREF;

import com.arexis.mugen.ontologies.imr.ImrDTO;
import com.arexis.mugen.ontologies.imr.ImrDTO_ALT;
import com.arexis.mugen.ontologies.imr.ImrDTO_CON;
import com.arexis.mugen.ontologies.imr.ImrDTO_ISA;
import com.arexis.mugen.ontologies.imr.ImrDTO_REL;
import com.arexis.mugen.ontologies.imr.ImrDTO_REP;
import com.arexis.mugen.ontologies.imr.ImrDTO_SUBSET;
import com.arexis.mugen.ontologies.imr.ImrDTO_SYN;
import com.arexis.mugen.ontologies.imr.ImrDTO_XREF;

import com.arexis.mugen.ontologies.mpath.MpathDTO;
import com.arexis.mugen.ontologies.mpath.MpathDTO_ALT;
import com.arexis.mugen.ontologies.mpath.MpathDTO_CON;
import com.arexis.mugen.ontologies.mpath.MpathDTO_ISA;
import com.arexis.mugen.ontologies.mpath.MpathDTO_REL;
import com.arexis.mugen.ontologies.mpath.MpathDTO_REP;
import com.arexis.mugen.ontologies.mpath.MpathDTO_SUBSET;
import com.arexis.mugen.ontologies.mpath.MpathDTO_SYN;
import com.arexis.mugen.ontologies.mpath.MpathDTO_XREF;

import com.arexis.mugen.ontologies.xp.XpDTO;
import com.arexis.mugen.ontologies.xp.XpDTO_REL;

public class ModelManagerBean extends AbstractMugenBean implements javax.ejb.SessionBean, com.arexis.mugen.model.modelmanager.ModelManagerRemoteBusiness {
    private javax.ejb.SessionContext context;
    
    //private params
    // <editor-fold defaultstate="collapsed">
    private ExpModelRemoteHome modelHome;
    private SamplingUnitRemoteHome samplingUnitHome;
    private ResearchApplicationRemoteHome researchAppHome;
    private ResourceManagerRemote resourceManager;
    private GeneticModificationRemoteHome genmodHome;
    private GeneOntologyRemoteHome geneOntologyHome;
    private FunctionalSignificanceRemoteHome functionalSigHome;
    private FunctionalSignificanceTypeRemoteHome functionalSigTypeHome;
    private FileRemoteHome fileHome;
    private LinkRemoteHome linkHome;
    private GeneRemoteHome geneHome;
    private ReferenceRemoteHome referenceHome;
    private UserRemoteHome userHome;
    private ProjectRemoteHome projectHome;
    private VariableSetRemoteHome variableSetHome;
    private StrainRemoteHome strainHome;
    private StrainTypeRemoteHome strainTypeHome;
    private StrainStateRemoteHome strainStateHome;
    private GeneticBackgroundRemoteHome genbackHome;
    private MutationTypeRemoteHome mutationTypeHome;
    private GeneticBackgroundValuesRemoteHome genbackValuesHome;
    private StrainAlleleRemoteHome strainAlleleHome;
    private AvailabilityRemoteHome availabilityHome;
    private AvailableGeneticBackgroundRemoteHome avgenbackHome;
    private RepositoriesRemoteHome repositoriesHome;
    private ChromosomeRemoteHome chromosomeHome;
    private SpeciesRemoteHome speciesHome;
    private PhenotypeOntologyRemoteHome phenoOntologyHome;
    private PhenotypeOntologyPathRemoteHome phenoPathHome;
    private PhenotypeXrefRemoteHome phenoXrefHome;
    private PhenotypeAltIdRemoteHome phenoAltIdHome;
    private PhenotypeSynonymRemoteHome phenoSynonymHome;
    private SimpleLogRemoteHome logHome;
    private UserCommsRemoteHome usercommHome;
    //</editor-fold>
    
    private static Logger logger = Logger.getLogger(ModelManagerBean.class);
    
    private int SimpleLogs;
    private Collection Logs;
    private int modelsTMPsize;
    private Collection modelsTMP;
    private int modelsdissTMPsize;
    private Collection modelsdissTMP;
    private int genesTMPsize;
    private Collection genesTMP;
    
    int count_syn = 1;
    int count_alt = 1;
    int count_xref = 1;
    
    int count_xp = 1;
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
    
    public void setSessionContext(javax.ejb.SessionContext aContext) {
        context = aContext;
    }
    
    public void ejbActivate() {}
    
    public void ejbPassivate() {}
    
    public void ejbRemove() {}
    
    // </editor-fold>
    
    public void ejbCreate() {
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        fileHome = (FileRemoteHome)locator.getHome(ServiceLocator.Services.FILE);
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        linkHome = (LinkRemoteHome)locator.getHome(ServiceLocator.Services.LINK);
        samplingUnitHome = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
        researchAppHome = (ResearchApplicationRemoteHome)locator.getHome(ServiceLocator.Services.RESEARCHAPPLICATION);
        genmodHome = (GeneticModificationRemoteHome)locator.getHome(ServiceLocator.Services.GENETICMODIFICATION);
        geneOntologyHome = (GeneOntologyRemoteHome)locator.getHome(ServiceLocator.Services.GENEONTOLOGY);
        functionalSigHome = (FunctionalSignificanceRemoteHome)locator.getHome(ServiceLocator.Services.FUNCTIONALSIGNIFICANCE);
        functionalSigTypeHome = (FunctionalSignificanceTypeRemoteHome)locator.getHome(ServiceLocator.Services.FUNCTIONALSIGNIFICANCETYPE);
        geneHome = (GeneRemoteHome)locator.getHome(ServiceLocator.Services.GENE);
        referenceHome = (ReferenceRemoteHome)locator.getHome(ServiceLocator.Services.REFERENCE);
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
        variableSetHome = (VariableSetRemoteHome)locator.getHome(ServiceLocator.Services.VARIABLESET);
        strainHome = (StrainRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN);
        strainTypeHome = (StrainTypeRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN_TYPE);
        strainStateHome = (StrainStateRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN_STATE);
        genbackHome = (GeneticBackgroundRemoteHome)locator.getHome(ServiceLocator.Services.GENETIC_BACKGROUND);
        mutationTypeHome = (MutationTypeRemoteHome)locator.getHome(ServiceLocator.Services.MUTATION_TYPE);
        genbackValuesHome = (GeneticBackgroundValuesRemoteHome)locator.getHome(ServiceLocator.Services.GENETIC_BACKGROUND_VALUES);
        strainAlleleHome = (StrainAlleleRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN_ALLELE);
        availabilityHome = (AvailabilityRemoteHome)locator.getHome(ServiceLocator.Services.AVAILABILITY);
        avgenbackHome = (AvailableGeneticBackgroundRemoteHome)locator.getHome(ServiceLocator.Services.AVAILABLE_GENETIC_BACKGROUNDS);
        repositoriesHome = (RepositoriesRemoteHome)locator.getHome(ServiceLocator.Services.REPOSITORIES);
        chromosomeHome = (ChromosomeRemoteHome)locator.getHome(ServiceLocator.Services.CHROMOSOME);
        resourceManager = (ResourceManagerRemote)locator.getManager(ServiceLocator.Services.RESOURCEMANAGER);         
        speciesHome = (SpeciesRemoteHome)locator.getHome(ServiceLocator.Services.SPECIES);
        phenoOntologyHome = (PhenotypeOntologyRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE_ONTOLOGY);
        phenoPathHome = (PhenotypeOntologyPathRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE_ONTOLOGY_PATH);
        phenoXrefHome = (PhenotypeXrefRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE_ONTOLOGY_XREF);
        phenoAltIdHome = (PhenotypeAltIdRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE_ONTOLOGY_ALT_ID);
        phenoSynonymHome = (PhenotypeSynonymRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE_ONTOLOGY_SYNONYM);
        logHome = (SimpleLogRemoteHome)locator.getHome(ServiceLocator.Services.SIMPLELOG);
        usercommHome = (UserCommsRemoteHome)locator.getHome(ServiceLocator.Services.USER_COMMENTS);
    }
    
    //model(s) functions
    //<editor-fold defaultstate="collapsed">
    public Collection getExperimentalModels(int suid, MugenCaller caller, PageManager pageManager) throws ApplicationException {
        try {                     
            SamplingUnitRemote samplingUnit = samplingUnitHome.findByPrimaryKey(new Integer(suid));
            
            // Fetch the remote interfaces
            Collection models = samplingUnit.getExperimentalModels();
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            // Create data transfer objects
            while (i.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
                } else {
                    // Return if we have enough data
                    if(modelsDTOs.size() == pageManager.getDelta())
                        return modelsDTOs;       
                    // Skip this object. This is outside the interval
                    i.next();
                }              
            }                        
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    
    public Collection getExperimentalModelsForBackcrossingListGeneration(MugenCaller caller) throws ApplicationException {
        try {
            Collection models = modelHome.findByBackrossingListGeneration(caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
           
            while (i.hasNext()) {
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next(), 0, "0"));
            }
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models for backcrossing list generation.");
        }
    }
    
    public Collection getExperimentalModelsForIMSR(int suid, MugenCaller caller) throws ApplicationException {
        try {
            Collection models = modelHome.findByIMSRSubmission(suid, caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            while (i.hasNext()) {
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next(), suid));              
            }                        
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    
    public Collection getUniqueExperimentalModelsForIMSR(int suid, MugenCaller caller) throws ApplicationException {
        try {
            Collection models = modelHome.findByUniqueIMSRSubmission(caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            while (i.hasNext()) {
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next(), suid));              
            }                        
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    
    public Collection getExperimentalModelsToIMSRTable(Collection models,int suid) throws ApplicationException {
        try {
            int tmpInt = 0;
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            ExpModelRemote tmpModel = null;
            ExpModelDTO tmpDTO = null;
            while (i.hasNext()) {
                tmpDTO = (ExpModelDTO)i.next();
                tmpModel = modelHome.findByPrimaryKey(new Integer(tmpDTO.getEid()));
                tmpInt = tmpModel.IMSRSubmit(tmpDTO.getEid());
                //if (tmpInt==1){
                    modelsDTOs.add(tmpDTO);
                //}
            }                        
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("getExperimentalModelsToIMSRTable failed dramatically.");
        }
    }
    
    public Collection getExperimentalModelsWithJAXID(int suid, MugenCaller caller) throws ApplicationException {
        try {
            Collection models = modelHome.findByJAXID(caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            while (i.hasNext()) {
                //modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next(), suid));              
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
            }                        
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models with a JAX ID.");
        }
    }
    
    public Collection getExperimentalModelsWithEMMAID(int suid, MugenCaller caller) throws ApplicationException {
        try {
            Collection models = modelHome.findByEMMAID(caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            while (i.hasNext()) {
                //modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next(), suid));
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
            }                        
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models with a EMMA ID.");
        }
    }
    
    public Collection getExperimentalModelsWithJAXEMMAID(int suid, MugenCaller caller) throws ApplicationException {
        try {
            Collection models = modelHome.findByJAXEMMAID(caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            while (i.hasNext()) {
                //modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next(), suid));
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
            }                        
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models with JAX & EMMA ID.");
        }
    }
    
    public Collection getExperimentalModelsByForm(FormDataManager fdm, MugenCaller caller, PageManager pageManager) throws ApplicationException {
        try {
            Collection models = modelHome.findByFormDataManager(fdm, caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while (i.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
                } else {
                    // Return if we have enough data
                    if(modelsDTOs.size() == pageManager.getDelta())
                        return modelsDTOs;
                    // Skip this object. This is outside the interval
                    i.next();
                }
            }
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    
    public int getExperimentalModelsByForm(FormDataManager fdm, MugenCaller caller) throws ApplicationException {
        try {
            Collection models = modelHome.findByFormDataManager(fdm, caller);
            this.modelsTMP = models;
            this.modelsTMPsize = modelsTMP.size();
            return modelsTMPsize;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    
    public Collection getExperimentalModelsByPGM(PageManager pageManager) throws ApplicationException {
        try {
            Collection modelsDTOs = new ArrayList();
            Iterator i = this.modelsTMP.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while (i.hasNext()) {
                
                index++;
                
                if (index>=start && index<=stop) {
                    modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
                } else {
                    // Return if we have enough data
                    if(modelsDTOs.size() == pageManager.getDelta())
                        return modelsDTOs;
                    // Skip this object. This is outside the interval
                    i.next();
                }
            }
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("getExperimentalModelsByPGM: Failed to get models.");
        }
    }
    
    public Collection getExperimentalModelsByFormNoDelta(FormDataManager fdm, MugenCaller caller, PageManager pageManager) throws ApplicationException {
        try {
            Collection models = modelHome.findByFormDataManager(fdm, caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while (i.hasNext()) {
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
            }
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
 
    public Collection getExperimentalModelsByFormForDissUpdate(FormDataManager fdm, MugenCaller caller, PageManager pageManager) throws ApplicationException {
        try {
            Collection models = modelHome.findByFormDataManagerForDissUpdate(fdm, caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while (i.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
                } else {
                    // Return if we have enough data
                    if(modelsDTOs.size() == pageManager.getDelta())
                        return modelsDTOs;
                    // Skip this object. This is outside the interval
                    i.next();
                }
            }
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    
    public Collection getExperimentalModelsByFormForDissUpdate(PageManager pageManager) throws ApplicationException {
        try {
            Collection modelsDTOs = new ArrayList();
            Iterator i = this.modelsdissTMP.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while (i.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
                } else {
                    // Return if we have enough data
                    if(modelsDTOs.size() == pageManager.getDelta())
                        return modelsDTOs;
                    // Skip this object. This is outside the interval
                    i.next();
                }
            }
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    
    public Collection getExperimentalModelsByFormForDissUpdateNoDelta(FormDataManager fdm, MugenCaller caller, PageManager pageManager) throws ApplicationException {
        try {
            Collection models = modelHome.findByFormDataManagerForDissUpdate(fdm, caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while (i.hasNext()) {
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
            }
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    
    public int getExperimentalModelsByFormForDissUpdateNoDelta(FormDataManager fdm, MugenCaller caller) throws ApplicationException {
        try {
            Collection models = modelHome.findByFormDataManagerForDissUpdate(fdm, caller);
            this.modelsdissTMP = models;
            this.modelsdissTMPsize = modelsdissTMP.size();
            return modelsdissTMPsize;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    
    public Collection getExperimentalModelsByMP(int potid, MugenCaller caller) throws ApplicationException {
        try {
            String pot = new Integer(potid).toString();
            Collection models = modelHome.findByMP(pot, caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            while (i.hasNext()) {
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));              
            }                        
            
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models by mammmalian phenotype(s).");
        }
    }
    
    public Collection getExperimentalModelsByGenBackValue(int bid, MugenCaller caller) throws ApplicationException {
        try {
            Collection models = modelHome.findByGenBackValue(bid, caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
           
            while (i.hasNext()) {
                //modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next(), 0, "0"));
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));
            }
            
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models by genetic background value.");
        }
    }
    
    public ExpModelDTO getExperimentalModel(int eid, MugenCaller caller, String superscript) throws ApplicationException {
        try {                                
            // Fetch the remote interface
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            
            // Create data transfer object
            ExpModelDTO dto = new ExpModelDTO(model, superscript);
            
            // Return the data transfer object
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get model.");
        }
    }
    
    public ExpModelDTO getExperimentalModel(int eid, MugenCaller caller) throws ApplicationException {
        try {                                
            // Fetch the remote interface
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            
            // Create data transfer object
            ExpModelDTO dto = new ExpModelDTO(model);
            
            // Return the data transfer object
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get model.");
        }
    }

    public int getNumberOfExperimentalModels(int suid, MugenCaller caller) throws ApplicationException {
        try {
            SamplingUnitRemote samplingUnit = samplingUnitHome.findByPrimaryKey(new Integer(suid));
            samplingUnit.setCaller(caller);
            return samplingUnit.getNumberOfExperimentalModels();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get number of models.");
        }
    }
    
    public int createModel(int suid, String alias, String geneticBackground, String availability, int type, String researchApplications, int contact, com.arexis.mugen.MugenCaller caller, String comm, String desired_level) throws ApplicationException {
        ExpModelRemote model = null;
        SamplingUnitRemote samplingUnit = null;
        ResearchApplicationRemote researchApplication = null;
        UserRemote contactUser = null;
        StrainRemote strain = null;
        int eid=0;
        try 
        {
            makeConnection();
            eid = getIIdGenerator().getNextId(conn, "expobj_seq");
            samplingUnit = samplingUnitHome.findByPrimaryKey(new Integer(suid));
            
            researchApplication = researchAppHome.findByPrimaryKey(new Integer(type));
            
            contactUser = userHome.findByPrimaryKey(new Integer(contact));
            
            model = modelHome.create(eid, "M"+eid, samplingUnit, caller);
            model.setCaller(caller);
            model.setAlias(alias);
            model.setComm(comm);
            model.setContact(contactUser);
            model.setAvailability(availability);
            model.setResearchApplicationText(researchApplications);
            model.setResearchApplication(researchApplication);
            model.setGeneticBackground(geneticBackground);
            
            if (desired_level.equals("Public"))
                model.setDesiredLevel(0);
            else if (desired_level.equals("Mugen"))
                model.setDesiredLevel(1);
            else if (desired_level.equals("Admin"))
                model.setDesiredLevel(2);
            
            int strainId = getIIdGenerator().getNextId(conn, "strain_seq");
            strain = strainHome.create(strainId, "", caller);
            model.setStrain(strain);
            
        }
        catch (FinderException e)
        {
            logger.error("FinderException then creating model", e);
            if (samplingUnit == null)
                throw new ApplicationException("Could not create model: Sampling Unit "+suid+" not found");
            if (researchApplication == null)
                throw new ApplicationException("Could not create model: Research Application "+type+" not found");
            if (contactUser == null)
                throw new ApplicationException("Could not create model: Contact user not found");
        }
        catch (CreateException e)
        {
            logger.error("CreateException then creating model", e);
            if (model==null)
                throw new ApplicationException("Could not create model");
            if (strain == null)
                throw new ApplicationException("Could not create strain");
        }
        catch (RemoteException e)
        {
            logger.error("remote exception", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
        catch (ApplicationException e) 
        {
            logger.error("Failed to create new model", e);
            throw new ApplicationException("Could not create new model: "+e.getMessage());            
        } 
        finally 
        {
            releaseConnection();
        }
        return eid;
    }

    public void updateModel(int suid, int eid, java.lang.String alias, java.lang.String geneticBackground, java.lang.String availability, int type, java.lang.String researchApplications, int contact, com.arexis.mugen.MugenCaller caller, String comm, String level, String desired_level) throws ApplicationException {
        validate("MODEL_W", caller);
        try {
            makeConnection();

            SamplingUnitRemote samplingUnit = samplingUnitHome.findByPrimaryKey(new Integer(suid));
            ResearchApplicationRemote researchApplication = researchAppHome.findByPrimaryKey(new Integer(type));            
            UserRemote contactUser = userHome.findByPrimaryKey(new Integer(contact));            
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            
            
            /** Check for the models sampling unit */
            Collection projects = model.getSamplingUnit().getProjects();
            validate("MODEL_W", caller, projects);
            
            model.setCaller(caller);
            model.setAlias(alias);
            model.setComm(comm);
            model.setSuid(suid);
            model.setContact(contactUser);
            model.setAvailability(availability);
            model.setResearchApplicationText(researchApplications);
            model.setResearchApplication(researchApplication);
            model.setGeneticBackground(geneticBackground);
            
            if (level.equals("Public"))
                model.setLevel(0);
            else if (level.equals("Mugen"))
                model.setLevel(1);
            else if (level.equals("Admin"))
                model.setLevel(2);
            
            if (desired_level.equals("Public"))
                model.setDesiredLevel(0);
            else if (desired_level.equals("Mugen"))
                model.setDesiredLevel(1);
            else if (desired_level.equals("Admin"))
                model.setDesiredLevel(2);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not update model\n"+e.getMessage(),e);            
        } finally {
            releaseConnection();
        } 
    }
    
    public void updateModelTimestamp(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MODEL_W", caller);
        try {
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            model.setUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not update model's timestamp\n"+e.getMessage(),e);            
        }
    }

    public void removeModel(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {           
            validate("MODEL_W", caller);
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            
            Iterator i_strainAlleles = model.getStrain().getStrainAlleles().iterator();
            while (i_strainAlleles.hasNext())
            {
                StrainAlleleRemote sta = (StrainAlleleRemote)i_strainAlleles.next();
                sta.remove();
            }    
            
            Iterator i = model.getGeneAffected().iterator();
            while (i.hasNext())
            {
                GeneRemote ga = (GeneRemote)i.next();
                model.removeGene(ga);
            }    
            model.getStrain().remove();
            model.remove();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove model\n"+e.getMessage());            
        }
    }
    
    public Collection searchModelByKeyword(Keyword keyword, MugenCaller caller) throws ApplicationException{
        Collection arr = new TreeSet();
        try
        {
            Collection models = modelHome.findByKeyword(keyword, caller);
            Iterator i = models.iterator();
            while (i.hasNext())
            {
                ExpModelRemote model = (ExpModelRemote)i.next();
                arr.add(new ModelSearchResult(model,"Controller?workflow=ViewModel&expand_all=true&name_begins=model.block&eid="+model.getEid()));
            }
        }
        catch (FinderException fe)
        {
            fe.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("failed to search by research application",e);
        }
        return arr;
    }
    
    public Collection getModelsByGene(int gid, MugenCaller caller) throws ApplicationException{
        try {                                            
            GeneRemote gene = geneHome.findByPrimaryKey(new Integer(gid));
            gene.setCaller(caller);
            Collection models = gene.getModels();
            
            Collection dtos = new ArrayList();
            Iterator i = models.iterator();
            while(i.hasNext()) {                
                dtos.add(new ExpModelDTO((ExpModelRemote)i.next()));
            }
            
            System.out.println("models.size="+models.size());
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genes affected.", e);
        }
    }
    
    public Collection getModelMPs (int eid, MugenCaller caller) throws ApplicationException {
        try {
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            Collection mps = model.getMPs();
            Collection dtos = new ArrayList();
            String[] tmp = null;
            Iterator i = mps.iterator();
            while(i.hasNext()) {
                tmp = translateMP((String[])i.next());
                dtos.add(new pathsMP(tmp[0], tmp[1]));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get mps for model.", e);
        }
    }
    
    public String[] translateMP (String[] mpseq) throws ApplicationException {
        try {
            String[] mps = new String[2];
            String mpnames = "";
            String mpids = "";
            
            for(int i=0; i < mpseq.length; i++){
                 PhenotypeOntologyRemote pheno = phenoOntologyHome.findByPrimaryKey(new Integer(mpseq[i]));
                 if(i==0){
                     mpids += mpseq[i];
                     mpnames += pheno.getName();
                 }else{
                     mpids += ">"+mpseq[i];
                     mpnames += ">"+pheno.getName();
                 }
            }
            
            mps[0] = mpids;
            mps[1] = mpnames;
            
            return mps;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to convert mp id seq in name seq.", e);
        }
    }
    
    public Collection getPhenoPaths (int eid) throws ApplicationException {
        try {
            Collection phps = phenoPathHome.findByModel(eid);
            Collection dtos = new ArrayList();
            String[] tmp = null;
            Iterator i = phps.iterator();
            while(i.hasNext()) {
                dtos.add(new pathsMP((PhenotypeOntologyPathRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get mps for model.", e);
        }
    }
    
    public Collection getPhenoPathsByRoot (String root) throws ApplicationException {
        try {
            Collection phps = phenoPathHome.findByRoot(root);
            Collection dtos = new ArrayList();
            //String[] tmp = null;
            Iterator i = phps.iterator();
            while(i.hasNext()) {
                dtos.add(new pathsMP((PhenotypeOntologyPathRemote)i.next()));
            }
            
            if(phps.isEmpty()){
                phps = phenoPathHome.findByEndNode(root);
                i = phps.iterator();
                while(i.hasNext()){
                    dtos.add(new pathsMP((PhenotypeOntologyPathRemote)i.next()));
                }
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get mps by root.", e);
        }
    }
    
    public Collection getExperimentalModelsByAllele(int allid, MugenCaller caller) throws ApplicationException {
        try {
            Collection models = modelHome.findByAllele(allid, caller);
            Collection modelsDTOs = new ArrayList();
            Iterator i = models.iterator();
            
            while (i.hasNext()) {
                modelsDTOs.add(new ExpModelDTO((ExpModelRemote)i.next()));              
            }                        
            
            // Return the data transfer objects
            return modelsDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models by allele.");
        }
    }
    //</editor-fold>
    
    //simple log methods
    //<editor-fold defaultstate="collapsed">
    public Collection getSimpleLogsByFDM(FormDataManager fdm, MugenCaller caller, PageManager pageManager) throws ApplicationException {
        try {
            //Collection logs = logHome.findByFormDataManager(fdm, caller);
            //this.SimpleLogs = logs.size();
            Collection logDTOs = new ArrayList();
            //Iterator i = logs.iterator();
            Iterator i = Logs.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while (i.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    logDTOs.add(new SimpleLogDTO((SimpleLogRemote)i.next()));
                } else {
                    // Return if we have enough data
                    if(logDTOs.size() == pageManager.getDelta())
                        return logDTOs;
                    // Skip this object. This is outside the interval
                    i.next();
                }
            }
            
            // Return the data transfer objects
            return logDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get simplelogs.");
        }
    }
    
    public int getMaxSimpleLogs(FormDataManager fdm, MugenCaller caller) throws ApplicationException {
        try{
            Collection logs = logHome.findByFormDataManager(fdm, caller);
            this.SimpleLogs = logs.size();
            this.Logs = logs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get the number of simplelogs.");
        }
        return SimpleLogs;
    }
    
    public int getAllSimpleLogs() throws ApplicationException {
        int SimpleLogs = 0;
        try {
            Collection logs = logHome.findByNoRestriction();
            SimpleLogs = logs.size();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get simplelogs.");
        }
        return SimpleLogs;
    }
    
    public Collection getDistinctSimpleLogActions() throws ApplicationException{
        makeConnection();
        Collection logactions = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select distinct mgnaction from simplelog where remotehost is not null order by mgnaction");
            result = ps.executeQuery();

            while(result.next()) {
                //if (result.getString("group_name").compareTo("PUBLIC") != 0){
                    logactions.add(result.getString("mgnaction"));
                //}
            }
        } catch (SQLException se) {
            logger.error("Cannot get simple log actions", se);
            throw new ApplicationException("Cannot get simple log actions\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return logactions;
    }
    
    public Collection getDistinctSimpleLogUsers() throws ApplicationException{
        makeConnection();
        Collection logactions = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select distinct mgnuser from simplelog where remotehost is not null and mgnuser not like '0' order by mgnuser");
            result = ps.executeQuery();

            while(result.next()) {
                logactions.add(result.getString("mgnuser"));    
            }
        } catch (SQLException se) {
            logger.error("Cannot get simple log users", se);
            throw new ApplicationException("Cannot get simple log users\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return logactions;
    }
    //</editor-fold>

    //research application(s) functions
    //<editor-fold defaultstate="collapsed">
    public Collection getResearchApplications(com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                
            Collection resapps = researchAppHome.findByProject(caller.getPid());
            Collection resappDTOs = new ArrayList();
            Iterator i = resapps.iterator();
            while(i.hasNext()) {
                resappDTOs.add(new ResearchAppDTO((ResearchApplicationRemote)i.next()));
            }
            
            return resappDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get research applications.");
        }
    }
    
    public ResearchAppDTO getResearchApplication(int raid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                
            return new ResearchAppDTO(researchAppHome.findByPrimaryKey(new Integer(raid)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get research application (raid="+raid+").");
        }
    }
    
    public Collection getAllResearchApplications(MugenCaller caller) throws ApplicationException{
        try {                                
            Collection resapps = researchAppHome.findByName("%");
            Collection resappDTOs = new ArrayList();
            Iterator i = resapps.iterator();
            while(i.hasNext()) {
                ResearchApplicationRemote ra = (ResearchApplicationRemote)i.next();
                resappDTOs.add(new ResearchAppDTO(ra));
            }
            
            return resappDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get research applications.");
        }
    }
    
    public Collection searchByResearchApplication(String name, MugenCaller caller) throws ApplicationException{
        Collection arr = new ArrayList();
        try
        {
            Collection apps = researchAppHome.findByName(name);
            Iterator ia = apps.iterator();
            while (ia.hasNext())
            {
                try
                {
                    ResearchApplicationRemote ra = (ResearchApplicationRemote)ia.next();

                    validatePid("MODEL_R", caller, ra.getProject().getPid());

                    Collection models = ra.getModels();
                    Iterator im = models.iterator();
                    while (im.hasNext())
                    {
                        ExpModelRemote model = (ExpModelRemote)im.next();
                        arr.add(new ExpModelDTO(model));
                    }
                }
                catch (PermissionDeniedException pde)
                {
                    System.out.println("ModelManagerBean#searchByResearchApplication: Info: Permission denied during search.");
                }
            }
        }
        catch (FinderException fe)
        {
            fe.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("failed to search by research application",e);
        }
        return arr;
    }
    
    public Collection searchResearchApplicationByKeyword(Keyword keyword, MugenCaller caller) throws ApplicationException{
        Collection arr = new TreeSet();
        try
        {
            Collection rapps = researchAppHome.findByKeyword(keyword, caller);
            Iterator i = rapps.iterator();
            while (i.hasNext())
            {
                ResearchApplicationRemote ra = (ResearchApplicationRemote)i.next();
                arr.add(new ResearchApplicationSearchResult(ra,"Controller?workflow=ViewResearchApp&raid="+ra.getRaid()));
            }            
        }
        catch (FinderException fe)
        {
            fe.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("failed to search keyword",e);
        }
        return arr;
    }
    
    public int createResearchApplication(String name, String comment, MugenCaller caller) throws ApplicationException{
        try
        {
            makeConnection();
            int raid = getIIdGenerator().getNextId(conn, "research_application_seq");
            researchAppHome.create(name, comment, caller.getPid() ,raid,  caller);
            return raid;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to create research application",e);
        }
        finally
        {
            releaseConnection();
        }
    }
    
    public void updateResearchApplication(int raid, String name, String comment, MugenCaller caller) throws ApplicationException{
        try
        {
            ResearchApplicationRemote rapp = researchAppHome.findByPrimaryKey(new Integer(raid));
            rapp.setCaller(caller);
            rapp.setName(name);
            rapp.setComm(comment);
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to update research application",e);
        }
    }
    
    public void removeResearchApplication(int raid, MugenCaller caller) throws ApplicationException{
        try
        {
            validate("MODEL_W", caller);
            ResearchApplicationRemote rapp = researchAppHome.findByPrimaryKey(new Integer(raid));
            rapp.remove();
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to remove research application",e);
        }
    }

    //</editor-fold>
   
    //handling+genotyping functions
    //<editor-fold defaultstate="collapsed">
    public void addHandlingFile(int eid, FileDataObject fileData, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("FILE_W", caller);
        try {
            int fileid = resourceManager.saveFile(fileData.getFileName(), "Handling instructions for eid="+eid, caller, fileData).getFileId();
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            model.setHandlingFile(fileid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add file\n"+e.getMessage());            
        }
    }        

    public void addGenotypingFile(int eid, FileDataObject fileData, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("FILE_W", caller);
        try {
            int fileid = resourceManager.saveFile(fileData.getFileName(), "Genotyping instructions for eid="+eid, caller, fileData).getFileId();
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            model.setGenotypingFile(fileid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add file\n"+e.getMessage());            
        }
    }

    public void removeGenotypingFile(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("FILE_W", caller);
        try {
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            //model.setCaller(caller);
            FileRemote file = model.getGenotypingFile();
            int fileId = file.getFileId();
            //model.setGenotypingFile(0);
            resourceManager.removeFile(fileId, caller);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove genotyping file\n"+e.getMessage());            
        }
    }

    public void removeHandlingFile(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("FILE_W", caller);
        try {
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            FileRemote file = model.getHandlingFile();
            //model.setHandlingFile(0);
            resourceManager.removeFile(file.getFileId(), caller);            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove handling file\n"+e.getMessage());            
        }
    }
    //</editor-fold>
    
    //genetic modification functions
    //<editor-fold defaultstate="collapsed">
    public Collection getGeneticModifications(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            Collection genmods = model.getGeneticModifications();
            Collection genmodDTOs = new ArrayList();
            Iterator i = genmods.iterator();
            while(i.hasNext()) {                
                genmodDTOs.add(new GenModDTO((GeneticModificationRemote)i.next()));
            }
            
            return genmodDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genetic modifications for model.");
        }
    }
    
    public GenModDTO getGeneticModification(int gmid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                
            GeneticModificationRemote genmod = genmodHome.findByPrimaryKey(new Integer(gmid));
            
            return new GenModDTO(genmod);            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genetic modification for model.");
        }
    }
    
    public void addGeneticModification(int eid, String name, String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {       
        try {
            makeConnection();
            int gmid = getIIdGenerator().getNextId(conn, "genetic_modification_seq");
            
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            genmodHome.create(gmid, name, comm, model, caller);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create a new genetic modification.");
        } finally {
            releaseConnection();
        }
    }

    public void updateGeneticModification(int gmid, String name, String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {        
        try{ 
            GeneticModificationRemote genmod = genmodHome.findByPrimaryKey(new Integer(gmid));                      
            genmod.setCaller(caller);
            genmod.setName(name);
            genmod.setComm(comm);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update genetic modification");
        }
    }

    public void removeGeneticModification(int gmid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try{ 
            validate("MODEL_W", caller);
            GeneticModificationRemote genmod = genmodHome.findByPrimaryKey(new Integer(gmid));                      
            genmod.remove();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove genetic modification");
        }
    }

    public Collection searchGeneticModificationByKeyword(Keyword keyword, MugenCaller caller) throws ApplicationException{
        Collection arr = new TreeSet();
        try {
            Collection gms = genmodHome.findByKeyword(keyword, caller);
            Iterator i = gms.iterator();
            while (i.hasNext())
            {
                GeneticModificationRemote gm = (GeneticModificationRemote)i.next();
                arr.add(new GenModSearchResult(gm,"Controller?workflow=ViewGenMod&gmid="+gm.getGmid()));
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
            throw new ApplicationException("Failed to search genetic modifications by a keyword");
        } catch (FinderException ex) {
            ex.printStackTrace();
        }
        return arr;
    }
    //</editor-fold>

    //gene ontology functions
    //<editor-fold defaultstate="collapsed">
    public Collection getGeneOntologies(int gmid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                
            GeneticModificationRemote genmod = genmodHome.findByPrimaryKey(new Integer(gmid));
            Collection ontologies = genmod.getGeneOntologies();
            Collection dtos = new ArrayList();
            Iterator i = ontologies.iterator();
            while(i.hasNext()) {                
                dtos.add(new GeneOntologyDTO((GeneOntologyRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get gene ontologies.");
        }
    }

    public GeneOntologyDTO getGeneOntology(int goid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                
            GeneOntologyRemote geneOntology = geneOntologyHome.findByPrimaryKey(new Integer(goid));
            
            return new GeneOntologyDTO(geneOntology);            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get gene ontology.");
        }
    }

    public void addGeneOntology(int gmid, String linkid, String name, String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            makeConnection();
            int goid = getIIdGenerator().getNextId(conn, "gene_ontology_seq");
            GeneticModificationRemote genmod = genmodHome.findByPrimaryKey(new Integer(gmid));

            geneOntologyHome.create(goid, linkid, name, comm, genmod, caller);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add a new gene ontology.");
        } finally {
            releaseConnection();
        }
    }

    public void removeGeneOntology(int goid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try{ 
            validate("MODEL_W", caller);
            GeneOntologyRemote genOntology = geneOntologyHome.findByPrimaryKey(new Integer(goid));                      
            genOntology.remove();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove gene ontology");
        }
    }

    public void updateGeneOntology(int goid, java.lang.String linkid, java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try{ 
            GeneOntologyRemote genOntology = geneOntologyHome.findByPrimaryKey(new Integer(goid));                         
            genOntology.setCaller(caller);
            genOntology.setName(name);
            genOntology.setComm(comm);
            genOntology.setLinkid(linkid);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update gene ontology");
        }
    }
    //</editor-fold>

    //functional significance functions
    //<editor-fold defaultstate="collapsed">
    public Collection getFunctionalSignificances(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                
            Collection funcSigs = functionalSigHome.findByModel(eid);
            Collection dtos = new ArrayList();
            Iterator i = funcSigs.iterator();
            while(i.hasNext()) {                
                dtos.add(new FunctionalSignificanceDTO((FunctionalSignificanceRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get functional significances.");
        }        
    }

    public Collection getFunctionalSignificanceTypes(com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                
            Collection funcSigTypes = functionalSigTypeHome.findByProject(caller.getPid());
            Collection dtos = new ArrayList();
            Iterator i = funcSigTypes.iterator();
            while(i.hasNext()) {                
                dtos.add(new FunctionalSignificanceTypeDTO((FunctionalSignificanceTypeRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get functional significance types.");
        }
    }
    
    public FunctionalSignificanceTypeDTO getFunctionalSignificanceType(int fstid, MugenCaller caller) throws ApplicationException{
        try {                                            
            FunctionalSignificanceTypeDTO fst = new FunctionalSignificanceTypeDTO(functionalSigTypeHome.findByPrimaryKey(new Integer(fstid)));
            return fst;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get functional significance type (fstid="+fstid+").");
        }
    }
    
    public void addFunctionalSignificance(String name, String comm, int eid, int fstid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            makeConnection();
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            FunctionalSignificanceTypeRemote funcSigType = null;
            int fsid = getIIdGenerator().getNextId(conn, "functional_significance_seq");
            
            if(fstid > 0)
                funcSigType = functionalSigTypeHome.findByPrimaryKey(new Integer(fstid));

            functionalSigHome.create(fsid, name, comm, model, caller, funcSigType);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add the functional significance.");
        } finally {
            releaseConnection();
        }
    }

    public void removeFunctionalSignificance(int fsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            validate("MODEL_W", caller);
            FunctionalSignificanceRemote funcSig = functionalSigHome.findByPrimaryKey(new Integer(fsid));
            funcSig.remove();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove the functional significance.");
        } 
    }

    public void updateFunctionalSignificance(int fsid, java.lang.String name, java.lang.String comm, int type, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            makeConnection();
            FunctionalSignificanceRemote funcSig = functionalSigHome.findByPrimaryKey(new Integer(fsid));
            FunctionalSignificanceTypeRemote funcSigType = null;
            if(type > 0)
                funcSigType = functionalSigTypeHome.findByPrimaryKey(new Integer(type));            
                
            funcSig.setCaller(caller);
            funcSig.setName(name);
            funcSig.setComm(comm);
            funcSig.setType(funcSigType);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not update the functional significance.");
        } finally {
            releaseConnection();
        }
    }

    public FunctionalSignificanceDTO getFunctionalSignificance(int fsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            makeConnection();
            FunctionalSignificanceRemote funcSig = functionalSigHome.findByPrimaryKey(new Integer(fsid));
            return new FunctionalSignificanceDTO(funcSig);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get the functional significance.");
        } finally {
            releaseConnection();
        }
    }

    public void addFunctionalSignificanceFile(int fsid, com.arexis.arxframe.io.FileDataObject fileData, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("FILE_W", caller);
        try {
            FileRemote file = resourceManager.saveFile(fileData.getFileName(), "Document for functional significance fsid="+fsid, caller, fileData);
            FunctionalSignificanceRemote funcSig = functionalSigHome.findByPrimaryKey(new Integer(fsid));
            funcSig.setFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add file\n"+e.getMessage());            
        }
    }

    public void removeFunctionalSignificanceFile(int fsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("FILE_W", caller);
        try {
            FunctionalSignificanceRemote funcSig = functionalSigHome.findByPrimaryKey(new Integer(fsid));

            FileRemote file = funcSig.getFile();
            int fileId = file.getFileId();
            resourceManager.removeFile(fileId, caller);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove file\n"+e.getMessage());            
        }
    }
    
    public Collection getFunctionalSignificances(com.arexis.mugen.MugenCaller caller, PageManager pageManager) throws ApplicationException {
        try {         
            SamplingUnitRemote samplingUnit = samplingUnitHome.findByPrimaryKey(new Integer(caller.getSuid()));
            
            Collection funcSigs = samplingUnit.getFunctionalSignificances();
            Collection dtos = new ArrayList();
            Iterator i = funcSigs.iterator();
            int start = pageManager.getStart();
            int stop = pageManager.getStop();  
            int index = 0;
            
            while(i.hasNext()) {  
                index++;
                if (index>=start && index<=stop) {
                    dtos.add(new FunctionalSignificanceDTO((FunctionalSignificanceRemote)i.next()));
                } else {
                    // Return if we have enough data
                    if(dtos.size() == pageManager.getDelta())
                        return dtos;       
                    // Skip this object. This is outside the interval
                    i.next();                    
                }
            }       
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get functional significances for sampling unit.");
        }
    }

    public int getNumberOfFunctionalSignificances(int suid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {         
            SamplingUnitRemote samplingUnit = samplingUnitHome.findByPrimaryKey(new Integer(suid));
            
            return samplingUnit.getNumberOfFunctionalSignificances();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to count functional significances for sampling unit.");
        }
    }

    public Collection getFunctionalSignificancesForType(int fstid, com.arexis.mugen.MugenCaller caller, PageManager pageManager) throws ApplicationException {
        try {                            
            FunctionalSignificanceTypeRemote funcSigType = functionalSigTypeHome.findByPrimaryKey(new Integer(fstid));
            Collection funcSigs = funcSigType.getFunctionalSignificances();
            Collection dtos = new ArrayList();
            Iterator i = funcSigs.iterator();
            int start = pageManager.getStart();
            int stop = pageManager.getStop();  
            int index = 0;
            
            while(i.hasNext()) {                
                index++;
                if (index>=start && index<=stop) {
                    dtos.add(new FunctionalSignificanceDTO((FunctionalSignificanceRemote)i.next()));
                } else {
                    // Return if we have enough data
                    if(dtos.size() == pageManager.getDelta())
                        return dtos;       
                    // Skip this object. This is outside the interval
                    i.next();                    
                }
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get functional significances.");
        }   
    }

    public int getNumberOfSignificancesForType(int fstid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                            
            FunctionalSignificanceTypeRemote funcSigType = functionalSigTypeHome.findByPrimaryKey(new Integer(fstid));
            Collection funcSigs = funcSigType.getFunctionalSignificances();
            
            return funcSigs.size();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to count functional significances.");
        }             
    }
    
    public Collection searchFunctionalSignificanceByKeyword(Keyword keyword, MugenCaller caller) throws ApplicationException{
        Collection arr = new TreeSet();
        try {
            Collection fss = functionalSigHome.findByKeyword(keyword, caller);
            Iterator i = fss.iterator();
            while (i.hasNext())
            {
                FunctionalSignificanceRemote fs = (FunctionalSignificanceRemote)i.next();
                arr.add(new FuncSigSearchResult(fs,"Controller?workflow=ViewFuncSig&fsid="+fs.getFsid()));
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
            throw new ApplicationException("Failed to search functional significances by a keyword");
        } catch (FinderException ex) {
            ex.printStackTrace();
        }
        return arr;
    }
    
    public Collection searchFunctionalSignificanceTypeByKeyword(Keyword keyword, MugenCaller caller) throws ApplicationException{
        Collection arr = new TreeSet();
        try {
            Collection fsts = functionalSigTypeHome.findByKeyword(keyword, caller);
            Iterator i = fsts.iterator();
            while (i.hasNext())
            {
                FunctionalSignificanceTypeRemote fs = (FunctionalSignificanceTypeRemote)i.next();
                arr.add(new FuncSigTypeSearchResult(fs,"Controller?workflow=ViewFuncSigType&fstid="+fs.getFstid()));
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
            throw new ApplicationException("Failed to search functional significance type by a keyword");
        } catch (FinderException ex) {
            ex.printStackTrace();
        }
        return arr;
    }
    
    public int createFuncSigType(String name, String comment, MugenCaller caller) throws ApplicationException{
        try
        {
            makeConnection();
            int fstid = getIIdGenerator().getNextId(conn, "functional_significance_type_seq");
            functionalSigTypeHome.create(fstid, name, caller.getPid(), caller);
            return fstid;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to create functional significance type",e);
        }
        finally
        {
            releaseConnection();
        }
    }
    
    public void updateFuncSigType(int fstid, String name, String comment, MugenCaller caller) throws ApplicationException{
        try
        {   
            FunctionalSignificanceTypeRemote fst = functionalSigTypeHome.findByPrimaryKey(new Integer(fstid));
            fst.setCaller(caller);
            
            ProjectRemote prj = fst.getProject();
            validatePid("MODEL_W", caller, prj.getPid());
            
            fst.setName(name);
            fst.setComm(comment);
        }
        catch (ApplicationException ae)
        {
            throw ae;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to update functional significance type",e);
        }
    }
    
    //</editor-fold>

    //gene(s) functions
    //<editor-fold defaultstate="collapsed">
    public Collection getGenesByModel(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                            
            Collection genes = geneHome.findByModel(eid);
            Collection dtos = new ArrayList();
            Iterator i = genes.iterator();
            while(i.hasNext()) {                
                dtos.add(new GeneDTO((GeneRemote)i.next(), eid));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genes affected.", e);
        }
    }
    
    public Collection getGenesWithEnsemblid(com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                            
            Collection genes = geneHome.findByEnsemblid();
            Collection dtos = new ArrayList();
            Iterator i = genes.iterator();
            while(i.hasNext()) {
                GeneDTO tmp =  new GeneDTO((GeneRemote)i.next());
                if(tmp.getIdensembl().trim().startsWith("ENS"))
                    dtos.add(tmp);
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("failed to get genes with valid ensembl ids.", e);
        }
    }
   
    public Collection getGenesByProject(int pid, MugenCaller caller, boolean all) throws ApplicationException{
        try {                                            
            Collection genes = geneHome.findByProject(pid, caller, all);
            Collection dtos = new ArrayList();
            GeneDTO tmpGene = null;
            Iterator i = genes.iterator();
            while(i.hasNext()) {
                tmpGene = new GeneDTO((GeneRemote)i.next());
                //if (tmpGene.getName().compareTo("Unknown")!=0){
                    dtos.add(tmpGene);
                //}
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genes affected.", e);
        }
    }
    
    public int getGenesByProject(int pid, MugenCaller caller, String quick) throws ApplicationException{
        try {                                            
            Collection genes = geneHome.findByProject(pid, caller, true);
            this.genesTMP = genes;
            this.genesTMPsize = genesTMP.size();
            return genesTMPsize;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genes affected.", e);
        }
    }
    
    public Collection getGenesForTransgenicMice(int pid, MugenCaller caller) throws ApplicationException{
        try {                                            
            Collection genes = geneHome.findByProject(pid, caller, true);
            Collection dtos = new ArrayList();
            GeneDTO tmpGene = null;
            Iterator i = genes.iterator();
            while(i.hasNext()) { 
                tmpGene = new GeneDTO((GeneRemote)i.next());
                if (tmpGene.getGenesymbol().compareTo("cre")==0 || tmpGene.getGenesymbol().compareTo("rtTA")==0 || tmpGene.getGenesymbol().compareTo("flp")==0){
                    dtos.add(tmpGene);
                }
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genes affected.", e);
        }
    }
    
    public Collection getStandAloneGenes(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                            
            Collection genes = geneHome.findStandAloneGenes(pid);
            Collection dtos = new ArrayList();
            Iterator i = genes.iterator();
            while(i.hasNext()) {                
                dtos.add(new GeneDTO((GeneRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genes that are not attached to a model or a strain_allele.", e);
        }
    }
    
    public int getGeneAssignmentForTransgenicModel(int eid, int gaid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                            
            Collection genes = geneHome.findByModel(eid);
            Collection dtos = new ArrayList();
            Iterator i = genes.iterator();
            while(i.hasNext()) {                
                GeneRemote tmp = (GeneRemote)i.next();
                if(tmp.getGaid()==gaid){
                    return 1;
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to check if gene for transgenic model is already assigned to model.", e);
        }
    }
    
    public Collection getGenesByProjectForNavTag(int pid, MugenCaller caller, PageManager pageManager) throws ApplicationException{
        try {                                            
            Collection genes = geneHome.findByProject(pid, caller, true);
            Collection dtos = new ArrayList();
            Iterator i = genes.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while(i.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    dtos.add(new GeneDTO((GeneRemote)i.next()));
                } else {
                    if(dtos.size() == pageManager.getDelta())
                        return dtos;
                    i.next();
                }
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genes affected.", e);
        }
    }
    
    public Collection getGenesByProjectForNavTag(PageManager pageManager, MugenCaller caller) throws ApplicationException{
        try {
            GeneRemote gene = null;
            Collection dtos = new ArrayList();
            Iterator i = this.genesTMP.iterator();
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while(i.hasNext()) {
                index++;
                
                if (index>=start && index<=stop) {
                    gene = (GeneRemote)i.next();
                    gene.setCaller(caller);
                    dtos.add(new GeneDTO(gene));
                    //dtos.add(new GeneDTO((GeneRemote)i.next()));
                }
                else {
                    if(dtos.size() == pageManager.getDelta())
                        return dtos;
                    i.next();
                }
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genes affected.", e);
        }
    }
    
    public Collection getUnassignedGenes(int eid,int pid, MugenCaller caller) throws ApplicationException{
        try {                                            
            Collection genes = geneHome.findUnassignedGenes(eid,pid);
            Collection dtos = new ArrayList();
            Iterator i = genes.iterator();
            while(i.hasNext()) {                
                dtos.add(new GeneDTO((GeneRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get the genes not affected to the model.", e);
        }
    }
    
    public Collection getUnassignedGenesForTransgenic(int eid,int strainid, int pid, MugenCaller caller) throws ApplicationException{
        try {                                            
            //Collection genes = geneHome.findUnassignedGenes(eid,pid);
            Collection genes = geneHome.findUnassignedGenesForTransgenic(eid, strainid, pid);
            Collection dtos = new ArrayList();
            Iterator i = genes.iterator();
            while(i.hasNext()) {                
                dtos.add(new GeneDTO((GeneRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get the genes not affected to the model.", e);
        }
    }

    public void addGeneToModel(int gaid, int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            GeneRemote gene = geneHome.findByPrimaryKey(new Integer(gaid));            
            model.addGene(gene);
            model.setUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add gene to this model\n"+e.getMessage(),e);            
        }
    }
    
    public void removeGeneFromModel(int gaid, int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            validate("MODEL_W", caller);
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            GeneRemote gene = geneHome.findByPrimaryKey(new Integer(gaid));            
            model.removeGene(gene);
            model.setUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove gene affected\n"+e.getMessage(),e);            
        }        
    }
    
    public int createGene(java.lang.String name, java.lang.String comm, java.lang.String mgiid, java.lang.String genesymbol, java.lang.String geneexpress, java.lang.String idgene, java.lang.String idensembl, int cid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            ChromosomeRemote chromosome = chromosomeHome.findByPrimaryKey(new Integer(cid));
            makeConnection();
            ProjectRemote project = projectHome.findByPrimaryKey(new Integer(caller.getPid()));
            int gaid = getIIdGenerator().getNextId(conn, "gene_seq");            
            GeneRemote gene = geneHome.create(gaid, name, comm, mgiid, genesymbol, geneexpress, idgene, idensembl, chromosome, project, caller);
            return gene.getGaid();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create gene affected\n"+e.getMessage(),e);            
        } finally {
            releaseConnection();
        }        
    }

    public void updateGene(int gaid, java.lang.String name, java.lang.String comm, java.lang.String mgiid, java.lang.String genesymbol, java.lang.String geneexpress, java.lang.String idgene, java.lang.String idensembl, int cid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            GeneRemote gene = geneHome.findByPrimaryKey(new Integer(gaid));
            gene.setCaller(caller);
            
            gene.setName(name);
            gene.setComm(comm);
            gene.setMgiid(mgiid);
            gene.setGenesymbol(genesymbol);
            gene.setGeneexpress(geneexpress);
            gene.setIdgene(idgene);
            gene.setIdensembl(idensembl);
            ChromosomeRemote chr = chromosomeHome.findByPrimaryKey(new Integer(cid));
            gene.setChromosome(chr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not update gene affected\n"+e.getMessage());            
        }
    }

    public void removeGene(int gaid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            validate("MODEL_W", caller);
            GeneRemote geneAffected = geneHome.findByPrimaryKey(new Integer(gaid));
            geneAffected.remove();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove gene affected.", e);            
        }
    }

    public GeneDTO getGene(int gaid, com.arexis.mugen.MugenCaller caller, String superscript) throws ApplicationException {
        try {
            GeneRemote gene = geneHome.findByPrimaryKey(new Integer(gaid));
            return new GeneDTO(gene, superscript);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get gene affected\n"+e.getMessage());            
        }
    }

    public GeneDTO getGene(int gaid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            GeneRemote gene = geneHome.findByPrimaryKey(new Integer(gaid));
            return new GeneDTO(gene);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get gene affected\n"+e.getMessage());            
        }
    }
    
    public Collection searchByGene(String geneName, MugenCaller caller) throws ApplicationException{
        Collection arr = new ArrayList();
        try
        {
            Collection genes = geneHome.findByName(geneName); 
            Iterator gi = genes.iterator();
            while (gi.hasNext())
            {
                try
                {
                    GeneRemote gene = (GeneRemote)gi.next();

                    validatePid("MODEL_R", caller, gene.getProject().getPid());

                    Collection models = gene.getModels();
                    Iterator i = models.iterator();
                    while (i.hasNext())
                    {
                        ExpModelRemote model = (ExpModelRemote)i.next();
                        arr.add(new ExpModelDTO(model));
                    }
                }
                catch (PermissionDeniedException pde)
                {
                    System.out.println("ModelManagerBean#searchByGene: Info: Permission denied on gene during search.");
                }
            }
        }
        catch (FinderException fe)
        {
            fe.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("failed to search by gene",e);
        }
        return arr;
    }
    
    public Collection searchGeneByKeyword(Keyword keyword, MugenCaller caller) throws ApplicationException{
        Collection arr = new TreeSet();
        try
        {
            Collection genes = geneHome.findByKeyword(keyword, caller);
            System.out.println("searchGeneByKeyword, keyword="+keyword.getKeyword()+", Hits="+genes.size());
            Iterator i = genes.iterator();
            while (i.hasNext())
            {
                GeneRemote gene = (GeneRemote)i.next();
                arr.add(new GeneSearchResult(gene,"Controller?workflow=ViewGene&gaid="+gene.getGaid()));
            }            
        }
        catch (FinderException fe)
        {
            fe.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("failed to search keyword",e);
        }
        return arr;
    }
    
    public Collection getPhenoPathsByGene (int gaid) throws ApplicationException {
        try {
            Collection phps = phenoPathHome.findByGene(gaid);
            Collection dtos = new ArrayList();
            String[] tmp = null;
            Iterator i = phps.iterator();
            while(i.hasNext()) {
                dtos.add(new pathsMP((PhenotypeOntologyPathRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get mps for gene.", e);
        }
    }
    
    public Collection getGenesByMP(String potid) throws ApplicationException {
        try {                                            
            Collection genes = geneHome.findByMP(potid);
            Collection dtos = new ArrayList();
            Iterator i = genes.iterator();
            while(i.hasNext()) {                
                dtos.add(new GeneDTO((GeneRemote)i.next(), potid));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genes affected.", e);
        }
    }
    //</editor-fold>
    
    //strain-allele functions
    //<editor-fold defaultstate="collapsed">
    
    public void addMutationTypeToStrainAllele(int id, int strainalleleid, MugenCaller caller) throws ApplicationException{
        try {
            StrainAlleleRemote allele = strainAlleleHome.findByPrimaryKey(new Integer(strainalleleid));
            MutationTypeRemote mut = mutationTypeHome.findByPrimaryKey(new Integer(id));
            
            allele.addMutationType(mut);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add mutation type to this strain allele\n"+e.getMessage(),e);            
        }
    }
    
    public String removeMutationTypeFromStrainAllele(int id, int strainalleleid, MugenCaller caller) throws ApplicationException{
        try {
            StrainAlleleRemote allele = strainAlleleHome.findByPrimaryKey(new Integer(strainalleleid));
            MutationTypeRemote mut = mutationTypeHome.findByPrimaryKey(new Integer(id));
            
            String mutName = mut.getName();
            
            allele.removeMutationType(mut);
            
            return mutName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add mutation type to this strain allele\n"+e.getMessage(),e);            
        }
    }
  
    public void removeGeneFromStrainAlleles(int gaid, int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            validate("MODEL_W", caller);
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            int strainId = model.getStrain().getStrainid();
            model.unassignGeneFromStrainAlleles(strainId, gaid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove gene from strain's alleles\n"+e.getMessage(),e);            
        }        
    }
    
    public void removeStrainAllelesFromGene(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            validate("MODEL_W", caller);
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            int strainId = model.getStrain().getStrainid();
            model.unassignStrainAllelesFromGene(eid, strainId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove gene from strain's alleles via strain allele's gene reassignment\n"+e.getMessage(),e);            
        }        
    }
    
    public StrainAlleleDTO getStrainAllele(int strainalleleid, MugenCaller caller) throws ApplicationException{
        try {
            return new StrainAlleleDTO(strainAlleleHome.findByPrimaryKey(new Integer(strainalleleid)), "superscript");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain alleles");
        }
    }
    
    public StrainAlleleDTO getStrainAlleleView(int strainalleleid, MugenCaller caller) throws ApplicationException{
        try {
            return new StrainAlleleDTO(strainAlleleHome.findByPrimaryKey(new Integer(strainalleleid)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain alleles");
        }
    }
    
    public Collection getMutationTypesFromStrainAllele(int strainalleleid, MugenCaller caller) throws ApplicationException{
        try {
            Collection arr = new ArrayList();
            
            StrainAlleleRemote sa = strainAlleleHome.findByPrimaryKey(new Integer(strainalleleid));
            Collection mtArr = sa.getMutationTypes();
            System.out.println("numberOfMutationTypes="+mtArr.size());
            Iterator i = mtArr.iterator();
            while (i.hasNext()) {
                arr.add(new MutationTypeDTO((MutationTypeRemote)i.next()));
            }    
            return arr;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain alleles");
        }
    }
    
    public String getIsStrainAlleleTransgenicFactor(int strainalleleid, MugenCaller caller) throws ApplicationException{
        try
        {
            Collection arr = new ArrayList();
            
            StrainAlleleRemote sa = strainAlleleHome.findByPrimaryKey(new Integer(strainalleleid));
            Collection mtArr = sa.getMutationTypes();
            Iterator i = mtArr.iterator();
            while (i.hasNext())
            {
                MutationTypeRemote tmp =(MutationTypeRemote)i.next();
                if (tmp.getName().compareTo("transgenic")==0){
                    System.out.println("IsStrainAlleleTransgenicFactor:TRUE");
                    return "1";
                }
            }
            System.out.println("IsStrainAlleleTransgenicFactor:FALSE");
            return "0";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get IsStrainAlleleTrangenicFactor");
        }
    }
    
    public int createStrainAllele(int strainid, String symbol, String name, MugenCaller caller) throws ApplicationException{
        try
        {
            makeConnection();
            int id = getIIdGenerator().getNextId(conn, "strain_allele_seq");
            StrainRemote strain = strainHome.findByPrimaryKey(new Integer(strainid));
            StrainAlleleRemote strainAllele = strainAlleleHome.create(id, symbol, name, strain, caller);
            ExpModelRemote model = modelHome.findByStrainID(strainid);
            model.setUpdate();
            return strainAllele.getId();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to add a new strain allele. ");
        }
        finally
        {
            releaseConnection();
        }
    }
    
    public void removeStrainAllele (int eid, int strainalleleid, MugenCaller caller) throws ApplicationException {
        try {
            validate("MODEL_W", caller);
            StrainAlleleRemote strainAllele = strainAlleleHome.findByPrimaryKey(new Integer(strainalleleid));
            strainAllele.remove();
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            model.setUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove strain allele.", e);            
        }
    }
    
    public void updateStrainAllele(int eid, int strainallele, String symbol, String name, String attributes, int geneid, String mgiid, MugenCaller caller) throws ApplicationException{
        try
        {
            StrainAlleleRemote allele = strainAlleleHome.findByPrimaryKey(new Integer(strainallele));
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            
            allele.setSymbol(symbol);
            allele.setName(name);
            allele.setMgiId(mgiid);
            allele.setAttributes(attributes);
            
            if (geneid!=0){
                allele.setGene(geneHome.findByPrimaryKey(new Integer(geneid)));
            }
            /*else{
                allele.setGeneToNULL(strainallele);
            }*/
            
            model.setUpdate();
                
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to add a new strain allele. ");
        }
      
    }
    
    public void clearGeneFromStrainAllele(int strainallele) throws ApplicationException{
        try
        {
            StrainAlleleRemote allele = strainAlleleHome.findByPrimaryKey(new Integer(strainallele));
            allele.setGeneToNULL(strainallele);    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to set strain allele's gene to null. ");
        }
      
    }
    
    public Collection getPhenoPathsByAllele (int allid) throws ApplicationException {
        try {
            Collection phps = phenoPathHome.findByAllele(allid);
            Collection dtos = new ArrayList();
            String[] tmp = null;
            Iterator i = phps.iterator();
            while(i.hasNext()) {
                dtos.add(new pathsMP((PhenotypeOntologyPathRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get mps for allele.", e);
        }
    }
    
    public Collection searchAlleleByKeyword(Keyword keyword, MugenCaller caller) throws ApplicationException{
        Collection arr = new TreeSet();
        try {
            Collection alleles = strainAlleleHome.findByKeyword(keyword, caller);
            Iterator i = alleles.iterator();
            while (i.hasNext()) {
                StrainAlleleRemote allele = (StrainAlleleRemote)i.next();
                arr.add(new AlleleSearchResult(allele,"Controller?workflow=ViewStrainAllele&allid="+allele.getId()));
            }
        } catch (FinderException fe) {
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("failed to search by strain allele",e);
        }
        return arr;
    }
    
    public Collection getAllelesByGene(int gid, MugenCaller caller) throws ApplicationException{
        try {                                            
            GeneRemote gene = geneHome.findByPrimaryKey(new Integer(gid));
            gene.setCaller(caller);
            Collection alleles = gene.getAlleles();
            
            Collection dtos = new ArrayList();
            Iterator i = alleles.iterator();
            while(i.hasNext()) {                
                dtos.add(new StrainAlleleDTO((StrainAlleleRemote)i.next()));
            }
            
            //System.out.println("models.size="+models.size());
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to alleles by gene.", e);
        }
    }
    //</editor-fold>
    
    //references
    //<editor-fold defaultstate="collapsed">
    public Collection getReferences(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                            
            Collection references = referenceHome.findByModel(eid);
            Collection dtos = new ArrayList();
            Iterator i = references.iterator();
            while(i.hasNext()) {   
                dtos.add(new ReferenceDTO((ReferenceRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get references.");
        }
    }

    public int addLinkReference(int eid, java.lang.String name, java.lang.String comm, java.lang.String url, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        int refid = 0;
        try {
            makeConnection();
            
            refid = getIIdGenerator().getNextId(conn, "reference_seq");
            
            ReferenceRemote reference = referenceHome.create(refid, caller.getPid(), name, comm, caller);
            LinkRemote link = resourceManager.createLink(name, comm, url, caller);
            reference.setCaller(caller);
            reference.setLink(link);
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            model.addReference(reference);
            model.setUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add link reference\n"+e.getMessage());            
        } finally {
            releaseConnection();
        }
        
        return refid;
    }

    public int addFileReference(int eid, java.lang.String name, java.lang.String comm, com.arexis.arxframe.io.FileDataObject fileData, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        int refid = 0;
        try {
            makeConnection();
            refid = getIIdGenerator().getNextId(conn, "reference_seq");
            
            ReferenceRemote reference = referenceHome.create(refid, caller.getPid(), name, comm, caller);
            FileRemote file = resourceManager.saveFile(name, comm, caller, fileData);
            reference.setCaller(caller);
            reference.setFile(file);
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            model.addReference(reference);
            model.setUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add file reference\n"+e.getMessage());            
        } finally {
            releaseConnection();
        }
        
        return refid;
    }

    public void removeReference(int eid, int refid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {            
            ReferenceRemote reference = referenceHome.findByPrimaryKey(new Integer(refid));
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            model.removeReference(reference);
            model.setUpdate();
            reference.remove();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove reference\n"+e.getMessage());            
        }
    }
    //</editor-fold>

    //resources
    //<editor-fold defaultstate="collapsed">
    public int addFileResource(int eid, java.lang.String name, java.lang.String comm, com.arexis.arxframe.io.FileDataObject fileData, int catid, MugenCaller caller) throws ApplicationException {
        int resid = 0;
        try {
            FileRemote file = resourceManager.saveFile(name, comm, caller, fileData);
            ResourceRemote resource = resourceManager.createResource(caller.getPid(), name, comm, file.getFileId(), 0, catid, caller);
            resid = resource.getResourceId();
      
            // Find and add resource to model.
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            model.addResource(resource);
            model.setUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add file resource\n"+e.getMessage());            
        }
        
        return resid;
    }
    
    public void removeFileResource(int refid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {           
            //validate("MODEL_W", caller);
            ReferenceRemote reference = referenceHome.findByPrimaryKey(new Integer(refid));
            if(reference.getLink()!=null){
                reference.getLink().remove();
            }
            if(reference.getFile()!=null){
                reference.getFile().remove();
            }
            reference.remove(); 
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove reference\n"+e.getMessage());            
        }
    }

    public int addLinkResource(int eid, java.lang.String name, java.lang.String comm, java.lang.String url, int catid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        int resid = 0;
        try {
            
            LinkRemote link = resourceManager.createLink(name, comm, url, caller);
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            
            ResourceRemote resource = resourceManager.createResource(caller.getPid(), name, comm, 0, link.getLinkId(), catid, caller);
            resid = resource.getResourceId();
            model.addResource(resource);
            model.setUpdate();
    
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add link resource\n"+e.getMessage());            
        }
        
        return resid;
    }
    //</editor-fold>
    
    //variable set methods
    //<editor-fold defaultstate="collapsed">
    public Collection searchByVariableSet(String vsName, MugenCaller caller) throws ApplicationException{
        Collection arr = new ArrayList();
        try
        {
            Collection variableSets = variableSetHome.findByName(vsName);
            Iterator ivs = variableSets.iterator();
            while (ivs.hasNext())
            {
                VariableSetRemote vs = (VariableSetRemote)ivs.next();
                
                System.out.println("VSName found "+vs.getName()+" vsid="+vs.getVsid());
                Collection variables = vs.getVariables();

                System.out.println("num of variables: "+variables.size());

                Iterator iv = variables.iterator();
                while (iv.hasNext())
                {
                    VariableRemote v = (VariableRemote)iv.next();
                    Collection phenotypes = v.getPhenotypes();
                    System.out.println("num of phenotypes: "+phenotypes.size());
                    Iterator ip = phenotypes.iterator();
                    while (ip.hasNext())
                    {
                        PhenotypeRemote p = (PhenotypeRemote)ip.next();
                        ExpObj exp = p.getExperimentalObject(caller);
                        int eid = exp.getEid();

                        try
                        {
                            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
                            
                            validateSU("MODEL_R", caller, model.getSamplingUnit().getSuid());
                            
                            arr.add(new ExpModelDTO(model));                        
                        }
                        catch (PermissionDeniedException pde)
                        {
                            System.out.println("ModelManagerBean#searchByVariableSet: Info: Permission denied during search.");
                        }
                        catch (FinderException fe)
                        {}
                    }                
                }
            }
        }
        catch (FinderException fe)
        {
            fe.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("failed to search by gene",e);
        }
        return arr;
    }
    
    public Collection getVariableSets(MugenCaller caller) throws ApplicationException{
        Collection arr = new ArrayList();
       
        try
        {
            Collection variableSets = variableSetHome.findByName("%");
            Iterator i = variableSets.iterator();
            while (i.hasNext())
            {
                VariableSetRemote vs = (VariableSetRemote)i.next();
                Collection projects = vs.getSamplingUnit().getProjects();
                
                arr.add(new VariableSetProjectDTO(vs, projects));
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to get variable sets");
        }
        
        return arr;
    }
    //</editor-fold>

    //strain(s) functions
    //<editor-fold defaultstate="collapsed">
    public Collection getStrains(MugenCaller caller) throws ApplicationException{
        try {                                
            Collection strains = strainHome.findByProject(caller.getPid(),caller);
                    
                    
            Collection strainDTOs = new ArrayList();
            Iterator i = strains.iterator();
            while(i.hasNext()) {                
                strainDTOs.add(new StrainDTO((StrainRemote)i.next()));
            }
            
            return strainDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strains.");
        }
    }
    
    public Collection getStrainsByFormDataManager(FormDataManager fdm, MugenCaller caller) throws ApplicationException{
        try {                                
            Collection strains = strainHome.findByFormDataManager(fdm, caller);
                    
            Collection strainDTOs = new ArrayList();
            Iterator i = strains.iterator();
            while(i.hasNext()) {                
                strainDTOs.add(new StrainDTO((StrainRemote)i.next()));
            }
            
            return strainDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strains by formdatamanager.");
        }
    }
    
    public StrainDTO getStrain(int strainid, MugenCaller caller, String superscript) throws ApplicationException{
        try {
            StrainRemote strain = strainHome.findByPrimaryKey(new Integer(strainid));
            return new StrainDTO(strain, superscript);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain.");
        }
    }
     
    public void updateStrain(int strainid, String designation, String mgiid, String emmaid, MugenCaller caller) throws ApplicationException{
        try {
            StrainRemote strain = strainHome.findByPrimaryKey(new Integer(strainid));
            ExpModelRemote model = modelHome.findByStrainID(strainid);
            strain.setDesignation(designation);
            if (!mgiid.equals("")){
                strain.setMgiId(mgiid);
            }else{
                strain.setMgiId("0");
            }
            
            if (!emmaid.equals("")){
                strain.setEmmaid(emmaid);
            }else{
                strain.setEmmaid("0");
            }
            model.setUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain.");
        }
    }
    
    public Collection getStrainTypes(MugenCaller caller) throws ApplicationException{
        try
        {
            Collection strainTypes = strainTypeHome.findByProject(caller.getPid(), caller);
            Collection strainTypeDTOs = new ArrayList();
            Iterator i = strainTypes.iterator();
            while (i.hasNext())
            {
                StrainTypeRemote type = (StrainTypeRemote)i.next();
                strainTypeDTOs.add(new StrainTypeDTO(type));
            }
            return strainTypeDTOs;
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain types");
        }
    }
    
    public Collection getStrainTypesForStrain(int strainId, MugenCaller caller) throws ApplicationException{
        try
        {
            StrainRemote strain = strainHome.findByPrimaryKey(new Integer(strainId));
            Collection strainTypes = strain.getTypes();
            Collection strainTypeDTOs = new ArrayList();
            Iterator i = strainTypes.iterator();
            while (i.hasNext())
            {
                StrainTypeRemote type = (StrainTypeRemote)i.next();
                strainTypeDTOs.add(new StrainTypeDTO(type));
            }
            return strainTypeDTOs;
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain types for the strain ["+strainId+"]");
        }
    }
    
    public Collection getAvailableStrainTypesForStrain(int strainId, MugenCaller caller) throws ApplicationException{
        try
        {
            Collection strainTypes = getStrainTypesForStrain(strainId, caller);
            Collection allTypes = getStrainTypes(caller); 
            
            // Get all available types
            allTypes.removeAll(strainTypes);
            
            // Return available types
            return allTypes;    
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get available strain types for the strain ["+strainId+"]");
        }
    }
    
    public Collection getStrainStates(MugenCaller caller) throws ApplicationException{
        try
        {
            Collection strainStates = strainStateHome.findByProject(caller.getPid(), caller);
            Collection strainStateDTOs = new ArrayList();
            Iterator i = strainStates.iterator();
            while (i.hasNext())
            {
                StrainStateRemote state = (StrainStateRemote)i.next();
                strainStateDTOs.add(new StrainStateDTO(state));
            }
            
            System.out.println("Number of strain states: "+strainStates.size());
            
            return strainStateDTOs;
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain types");
        }
    }
    
    public Collection getStrainStatesForStrain(int strainId, MugenCaller caller) throws ApplicationException{
        try
        {
            StrainRemote strain = strainHome.findByPrimaryKey(new Integer(strainId));
            Collection strainStates = strain.getStates();
            Collection strainStateDTOs = new ArrayList();
            Iterator i = strainStates.iterator();
            while (i.hasNext())
            {
                StrainStateRemote state = (StrainStateRemote)i.next();
                strainStateDTOs.add(new StrainStateDTO(state));
            }
 
            return strainStateDTOs;           
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain types for strain ["+strainId+"]");
        }
    }
    
    public Collection getAvailableStrainStatesForStrain(int strainId, MugenCaller caller) throws ApplicationException{
        try
        {
            Collection strainStates = getStrainStatesForStrain(strainId, caller);
            Collection allStates = getStrainStates(caller); 
            
            // Get all available states
            allStates.removeAll(strainStates);
            
            // Return available states
            return allStates;           
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get available strain states for strain ["+strainId+"]");
        }
    }
    
    public void addStrainAndTypeToStrain(int strainid, int typeid, int stateid, MugenCaller caller) throws ApplicationException{
        try
        {
            System.out.println("ModelManagerBean#addStrainAndTypeToStrain: strainid="+strainid+", typeid="+typeid+", stateid="+stateid);
            
            StrainRemote strain = strainHome.findByPrimaryKey(new Integer(strainid));
            if (stateid!=0)
                strain.addState(strainStateHome.findByPrimaryKey(new Integer(stateid)));
            if (typeid!=0)
                strain.addType(strainTypeHome.findByPrimaryKey(new Integer(typeid)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Could not add the state ["+stateid+"] and type ["+typeid+"] to this strain ["+strainid+"]");
        }
    }
    
    public void removeTypeFromStrain(int strainid, int typeid, MugenCaller caller) throws ApplicationException{
        try
        {
            validate("MUGEN_W", caller);
            StrainRemote strain = strainHome.findByPrimaryKey(new Integer(strainid));
            strain.removeType(strainTypeHome.findByPrimaryKey(new Integer(typeid)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Could not remove the type ["+typeid+"] from this strain ["+strainid+"]");
        }
    }
    
    public void removeStateFromStrain(int strainid, int stateid, MugenCaller caller) throws ApplicationException{
        try
        {
            StrainRemote strain = strainHome.findByPrimaryKey(new Integer(strainid));
            strain.removeState(strainStateHome.findByPrimaryKey(new Integer(stateid)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Could not remove the state ["+stateid+"] from this strain ["+strainid+"]");
        }
    }
    
    public Collection getStrainAllelesFromStrain(int strainid, MugenCaller caller) throws ApplicationException{
        try
        {
            Collection strainAlleles = strainAlleleHome.findByStrain(strainid, caller);
            Collection strainAlleleDTOs = new ArrayList();
            Iterator i = strainAlleles.iterator();
            while (i.hasNext())
            {
                StrainAlleleRemote allele = (StrainAlleleRemote)i.next();
                strainAlleleDTOs.add(new StrainAlleleDTO(allele));
            }
            
            System.out.println("ModelManagerBean#getStrainAllelesFromStrain: size="+strainAlleleDTOs.size());
            
            return strainAlleleDTOs;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain alleles");
        }
    }
    
    public StrainDTO getStrainFromModel(int eid, MugenCaller caller) throws ApplicationException{
       try
       {
           ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
           StrainDTO strain = new StrainDTO(model.getStrain());
           return strain;
       }
       catch (Exception e)
       {
           e.printStackTrace();
           throw new ApplicationException("Failed to get strain from the model ["+eid+"]. Model does not have a strain. Contact system administrator");
       }
   }
    //</editor-fold>
    
    //genetic background functions
    //<editor-fold defaultstate="collapsed">
   public void replaceGenBackValue(int bid) throws ApplicationException{
        makeConnection();
        PreparedStatement ps = null;
        int replacement = 105002;
        try {
            //dna_origin
            ps = conn.prepareStatement("update genetic_back set dna_origin=? where dna_origin=?");
            ps.setInt(1, replacement);
            ps.setInt(2, bid);
            ps.execute();
            
            //targeted_back
            ps = conn.prepareStatement("update genetic_back set targeted_back=? where targeted_back=?");
            ps.setInt(1, replacement);
            ps.setInt(2, bid);
            ps.execute();
            
            //host_back
            ps = conn.prepareStatement("update genetic_back set host_back=? where host_back=?");
            ps.setInt(1, replacement);
            ps.setInt(2, bid);
            ps.execute();
            
            //backcrossing_strain
            ps = conn.prepareStatement("update genetic_back set backcrossing_strain=? where backcrossing_strain=?");
            ps.setInt(1, replacement);
            ps.setInt(2, bid);
            ps.execute();

        } catch (SQLException se) {
            logger.error("Failed to replace genetic background values", se);
            throw new ApplicationException("Failed to replace genetic background values \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
   
   public String removeGenBackValue(int bid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {        
        try{
            replaceGenBackValue(bid);
            GeneticBackgroundValuesRemote genbackValue = genbackValuesHome.findByPrimaryKey(new Integer(bid));                      
            String GenBackName = genbackValue.getBackname();
            genbackValue.remove();
            return GenBackName;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove genetic background strain");
        }
    }
    
   public Collection getGeneticBackground(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                            
            Collection genBack = genbackHome.findByGeneticBackgroundModel(eid);
            Collection dtos = new ArrayList();
            Iterator i = genBack.iterator();
            while(i.hasNext()) {                
                dtos.add(new GeneticBackgroundDTO((GeneticBackgroundRemote)i.next()));
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genetic back*** for model "+eid, e);
        }
    }
   
   public GeneticBackgroundDTO getGeneticBackgroundDTO(int eid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                            
            Collection genBack = genbackHome.findByGeneticBackgroundModel(eid);
            GeneticBackgroundDTO dto = null;
            Iterator i = genBack.iterator();
            if(i.hasNext()) {                
                dto = new GeneticBackgroundDTO((GeneticBackgroundRemote)i.next());
            }
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genetic back*** for model "+eid, e);
        }
    }
   
   public Collection getGeneticBackgroundsByProject(int pid, MugenCaller caller) throws ApplicationException{
        try {                                            
            Collection genbacks = genbackValuesHome.findByProject(pid);
            Collection dtos = new ArrayList();
            Iterator i = genbacks.iterator();
            while(i.hasNext()) {                
                dtos.add(new GeneticBackgroundValuesDTO((GeneticBackgroundValuesRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genetic background information.", e);
        }
    }
    
    public void updateGeneticBackgroundForModel(int eid, int dna_origin, int targeted_back, int host_back, int backcrossing_strain, String backcrosses, com.arexis.mugen.MugenCaller caller) throws ApplicationException {        
        try{
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            //the collection returned contains only one interface. should it contain more than one then a while loop needs to be implemented.
            Collection genBack = genbackHome.findByGeneticBackgroundModel(eid);
            Iterator i = genBack.iterator();
            //if(i.hasNext()) {                
                GeneticBackgroundRemote genBackRemote = (GeneticBackgroundRemote)i.next();
            //}
            genBackRemote.setDna_origin(dna_origin);
            genBackRemote.setTargeted_back(targeted_back);
            genBackRemote.setHost_back(host_back);
            genBackRemote.setBackcrossing_strain(backcrossing_strain);
            genBackRemote.setBackcrosses(backcrosses);
            model.setUpdate();
            
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update genetic background information");
        }
    }//updateGeneBackValue
    
    public void setGeneticBackgroundForModel (int eid, int dna_origin, int targeted_back, int host_back, int backcrossing_strain, String backcrosses) throws ApplicationException{
        try
        {
            makeConnection();
            int gbid = getIIdGenerator().getNextId(conn, "genetic_back_seq");
            genbackHome.create(gbid, eid, dna_origin, targeted_back, host_back, backcrossing_strain, backcrosses);
            
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            model.setUpdate();
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to create genetic background for model ",e);
        }
        finally
        {
            releaseConnection();
        }
    }
    
    public int createGeneBackValue(java.lang.String backname, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            makeConnection();
            ProjectRemote project = projectHome.findByPrimaryKey(new Integer(caller.getPid()));
            int bid = getIIdGenerator().getNextId(conn, "genetic_back_values_seq");            
            //GeneRemote gene = geneHome.create(gaid, name, comm, mgiid, genesymbol, geneexpress, idgene, idensembl, project, caller);
            GeneticBackgroundValuesRemote genbackValues = genbackValuesHome.create(bid,backname,project);
            return genbackValues.getBid();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create genetic background strain\n"+e.getMessage(),e);            
        } finally {
            releaseConnection();
        }        
    }//createGeneBackValue
    
    public void updateGeneBackValue(int bid, String backname, com.arexis.mugen.MugenCaller caller) throws ApplicationException {        
        try{ 
            GeneticBackgroundValuesRemote genbackValues = genbackValuesHome.findByPrimaryKey(new Integer(bid));                      
            genbackValues.setBackname(backname);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update genetic background strain");
        }
    }//updateGeneBackValue
    
    public String getGeneBackValueName(int bid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {        
        try{ 
            GeneticBackgroundValuesRemote genbackValues = genbackValuesHome.findByPrimaryKey(new Integer(bid));                      
            String backname = genbackValues.getBackname();
            return backname;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get genetic background strain name for id "+bid+"\n");
        }
    }
    
    //</editor-fold>
   
    //mutation types functions
    //<editor-fold defaultstate="collapsed">
   public Collection getMutationTypes(int pid, MugenCaller caller) throws ApplicationException{
       try
       {
           Collection dtos = new ArrayList();
           Collection mutationTypes = mutationTypeHome.findByProject(pid, caller);
           Iterator i = mutationTypes.iterator();
           while (i.hasNext())
           {
               MutationTypeRemote mutationType = (MutationTypeRemote)i.next();
               dtos.add(new MutationTypeDTO(mutationType));
           }
           return dtos;
       }
       catch (Exception e)
       {
           e.printStackTrace();
           throw new ApplicationException("Failed to get mutation types", e);
       }
   }
   
   public Collection getUnassignedMutationTypes(int strainalleleid, MugenCaller caller) throws ApplicationException{
       try
       {
           Collection dtos = new ArrayList();
           //Collection mutationTypes = mutationTypeHome.findByProject(pid, caller);
           Collection mutationTypes = mutationTypeHome.findByStrainAlleleUnassignment(strainalleleid, caller);
           Iterator i = mutationTypes.iterator();
           while (i.hasNext())
           {
               MutationTypeRemote mutationType = (MutationTypeRemote)i.next();
               dtos.add(new MutationTypeDTO(mutationType));
           }
           return dtos;
       }
       catch (Exception e)
       {
           e.printStackTrace();
           throw new ApplicationException("Failed to get mutation types", e);
       }
   }
   //</editor-fold>
  
    //availability functions
    //<editor-fold defaultstate="collapsed">
    public Collection getBackcrossesCollection(){
        Collection backcrossesCollection = new ArrayList();
        for(int i=1; i<10; i++){
            backcrossesCollection.add("0"+(new Integer(i).toString()));
        }//for
        backcrossesCollection.add(">=10");
        backcrossesCollection.add("n/a");
        
        return backcrossesCollection;
    }
    
    public Collection getAvailableGeneticBackgroundsByProject(int pid) throws ApplicationException {
        try {                                            
            Collection avgenbacks = avgenbackHome.findByProject(pid);
            Collection dtos = new ArrayList();
            Iterator i = avgenbacks.iterator();
            while(i.hasNext()) {                
                dtos.add(new AvailableGeneticBackgroundDTO((AvailableGeneticBackgroundRemote)i.next()));
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get the available genetic backgrounds for the current project.", e);
        }
    }
    
    public Collection getAvailabilityForModel(int eid) throws ApplicationException{
        try {
            Collection availability = availabilityHome.findByModel(eid);
            Collection dtos = new ArrayList();
            Iterator i = availability.iterator();
            while(i.hasNext()) {                
                dtos.add(new AvailabilityDTO((AvailabilityRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get availability information for specific model.", e);
        }
    }
    
    public void addAvailabilityToModel(int eid, int rid, int aid, int stateid, int typeid) throws ApplicationException {
        try {
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            RepositoriesRemote repository = repositoriesHome.findByPrimaryKey(new Integer(rid));
            AvailableGeneticBackgroundRemote avgenback = avgenbackHome.findByPrimaryKey(new Integer(aid));
            StrainStateRemote state = strainStateHome.findByPrimaryKey(new Integer(stateid));
            StrainTypeRemote type = strainTypeHome.findByPrimaryKey(new Integer(typeid));
            
            AvailabilityRemote availability = availabilityHome.create(model, repository, avgenback, state, type);
            model.setUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to assign availability information to model");            
        }        
    }
    
    public void removeAvailabilityFromModel(int eid, int rid, int aid, int stateid, int typeid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            validate("MODEL_W", caller);
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            AvailabilityRemote availability = availabilityHome.findByPrimaryKey(new AvailabilityPk(eid, rid, aid, stateid, typeid));
            
            availability.remove();
            model.setUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove availability information\n"+e.getMessage(),e);            
        }        
    }
    //</editor-fold>
    
    //repository functions
    //<editor-fold defaultstate="collapsed">
    public Collection getRepositoriesByProject(int pid) throws ApplicationException {
        try {                                            
            Collection repositories = repositoriesHome.findByProject(pid);
            Collection dtos = new ArrayList();
            Iterator i = repositories.iterator();
            while(i.hasNext()) {                
                dtos.add(new RepositoriesDTO((RepositoriesRemote)i.next()));
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get the repositories for the current project.", e);
        }
    }
    
    public RepositoriesDTO returnRepositoryById (int rid) throws ApplicationException {
        try{
            RepositoriesRemote repository = repositoriesHome.findByPrimaryKey(new Integer(rid));
            return new RepositoriesDTO(repository);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not return repository\n"+e.getMessage(),e);            
        }  
    }
    
    public void updateRepositoryName (int rid, String reponame, String repourl) throws ApplicationException {
        try{
            RepositoriesRemote repository = repositoriesHome.findByPrimaryKey(new Integer(rid));
            repository.setReponame(reponame);
            repository.setRepourl(repourl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not update repository's name\n"+e.getMessage(),e);            
        }
    }
    
    public int addRepository (String reponame, String repourl, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            makeConnection();
            int rid = getIIdGenerator().getNextId(conn, "repositories_seq");            
            ProjectRemote project = projectHome.findByPrimaryKey(new Integer(caller.getPid()));
            RepositoriesRemote repository = repositoriesHome.create(rid, reponame, repourl, project);
            return repository.getRid();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create repository. \n"+e.getMessage(),e);            
        } finally {
            releaseConnection();
        }  
    }
    
    public void removeRepository(int rid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {           
            validate("MODEL_W", caller);
            RepositoriesRemote repository = repositoriesHome.findByPrimaryKey(new Integer(rid));
            repository.remove();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove repository \n"+e.getMessage());            
        }
    }
    //</editor-fold>
    
    //available genetic background functions
    //<editor-fold defaultstate="collapsed">
    public AvailableGeneticBackgroundDTO returnAvailableGeneticBackgroundById (int aid) throws ApplicationException {
        try{
            AvailableGeneticBackgroundRemote repository = avgenbackHome.findByPrimaryKey(new Integer(aid));
            return new AvailableGeneticBackgroundDTO(repository);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not return available genetic background. \n"+e.getMessage(),e);            
        }  
    }
    
    public void updateAvailableGeneticBackgroundName (int aid, String avgenbackname) throws ApplicationException {
        try{
            AvailableGeneticBackgroundRemote avgenback = avgenbackHome.findByPrimaryKey(new Integer(aid));
            avgenback.setAvbackname(avgenbackname);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not update repository's name\n"+e.getMessage(),e);            
        }
    }
    
    public int addAvailableGeneticBackground (String avgenbackname, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            makeConnection();
            int aid = getIIdGenerator().getNextId(conn, "available_genetic_back_seq");
            ProjectRemote project = projectHome.findByPrimaryKey(new Integer(caller.getPid()));
            AvailableGeneticBackgroundRemote avgenback = avgenbackHome.create(aid, avgenbackname, caller.getPid(), project);
            return avgenback.getAid();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create repository. \n"+e.getMessage(),e);            
        } finally {
            releaseConnection();
        }  
    }
    
    public void removeAvailableGeneticBackground(int aid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {           
            validate("MODEL_W", caller);
            AvailableGeneticBackgroundRemote avgenback = avgenbackHome.findByPrimaryKey(new Integer(aid));
            avgenback.remove();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove available genetic background. \n"+e.getMessage());            
        }
    }
    //</editor-fold>
    
    //participant related methods
    //<editor-fold defaultstate="collapsed">
    
    public Collection getParticipants(MugenCaller caller) throws ApplicationException{
        makeConnection();
        Collection participants = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select distinct group_name from users where group_name is not null and group_name not like '' and group_name not like 'MUGEN' and id in (select distinct contact from model where level<= ? ) order by group_name");
            ps.setInt(1, getCallerLevel(caller));
            result = ps.executeQuery();

            while(result.next()) {
                if (result.getString("group_name").compareTo("PUBLIC") != 0){
                    participants.add(result.getString("group_name"));
                }
            }
        } catch (SQLException se) {
            logger.error("Cannot get group names", se);
            throw new ApplicationException("Cannot get group names \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return participants;
    }
    
    public Collection getParticipantNames(MugenCaller caller) throws ApplicationException{
        makeConnection();
        Collection participants = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select distinct name from users where group_name is not null and group_name not like '' and group_name not like 'MUGEN' and id in (select distinct contact from model where level<= ? )");
            ps.setInt(1, getCallerLevel(caller));
            result = ps.executeQuery();

            while(result.next()) {
                participants.add(result.getString("name"));
            }
            
        } catch (SQLException se) {
            logger.error("Cannot get partincipant names", se);
            throw new ApplicationException("Cannot get participant names \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return participants;
    }
    //</editor-fold>
    
    //chromosome methods
    //<editor-fold defaultstate="collapsed">
    
    public Collection getChromosomesForSpecies(int sid, MugenCaller caller) throws ApplicationException {
        try {
            System.out.println("ModelManager#getChromosomesForSpecies: sid="+sid);
            SpeciesRemote species = speciesHome.findByPrimaryKey(new Integer(sid));
            Collection chromosomes = species.getChromosomes();
            
            //Collection chromosomes = chromoHome.findAllChromosomes();
            Collection dtos = new ArrayList();
            Iterator i = chromosomes.iterator();
            while(i.hasNext()) {                
                dtos.add(new ChromosomeDTO((ChromosomeRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get chromosomes.", e);
        }
    }
    
    //</editor-fold>
    
    //user comments methods
    //<editor-fold defaultstate="collapsed">
    
    public int createUserComment(int eid, String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            makeConnection();
            int commid = getIIdGenerator().getNextId(conn, "comms_seq");
            UserCommsRemote usercomm = usercommHome.create(commid, caller.getId());
            usercomm.setComm(comm);
            usercomm.connectModel(eid);
            //RepositoriesRemote repository = repositoriesHome.create(rid, reponame, repourl, project);
            return usercomm.getCommid();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not user comment. \n"+e.getMessage(),e);            
        } finally {
            releaseConnection();
        }  
    }
    
    public void deleteUserComment(int commid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            validate("MODEL_W", caller);
            UserCommsRemote usercomm = usercommHome.findByPrimaryKey(new Integer(commid));
            usercomm.remove();
            //return commid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove user comment.", e);            
        }
    } 
    
    public Collection getUserCommentsByModel(int eid) throws ApplicationException{
        try {                                            
            Collection usercomms = usercommHome.findByModel(eid);
            Collection dtos = new ArrayList();
            Iterator i = usercomms.iterator();
            while(i.hasNext()) {                
                dtos.add(new UserCommsDTO((UserCommsRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get user comments.", e);
        }
    }
    
    //</editor-fold>
    
    //misc+crucial functions
    //<editor-fold defaultstate="collapsed">
    
    public int getCallerLevel(MugenCaller caller){
        int level = 0;
        
        if (caller.hasPrivilege("MODEL_ADM")) {
            level = 2;
        } else if (caller.hasPrivilege("MODEL_MUGEN")) {
            level = 1;
        }
        return level;
    }
    
    public SpeciesDTO getSpecies(int sid, MugenCaller caller) throws ApplicationException{
        try
        {
            SpeciesRemote spc = speciesHome.findByPrimaryKey(new Integer(sid));
            return new SpeciesDTO(spc);
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ApplicationException("Failed to get species");
        } 
    }
    
    public Collection getResourceTreeCollection(int eid, MugenCaller caller) throws ApplicationException{
        System.out.println("getResourceTreeCollection");
        Collection resourceTree = new ArrayList();
        try
        {
            ExpModelRemote m = modelHome.findByPrimaryKey(new Integer(eid));
            Collection resources = m.getResources();
            
            return resourceManager.getResourceTreeCollection(resources, caller);
        }   
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get resources", e);
        }
    }
    
    public Collection searchByProject(String name, MugenCaller caller) throws ApplicationException{
        Collection arr = new TreeSet();
        try {
            Collection projects = projectHome.findByName(name, caller);
            
            Iterator iPrj = projects.iterator();
            while (iPrj.hasNext()) {
                try {
                    ProjectRemote prj = (ProjectRemote)iPrj.next();

                    validatePid("MODEL_R", caller, prj.getPid());

                    Collection samplingUnits = prj.getSamplingUnits();
                    Iterator iSu = samplingUnits.iterator();
                    while (iSu.hasNext()) {
                        SamplingUnitRemote su = (SamplingUnitRemote)iSu.next();
                        Collection models = su.getExperimentalModels();
                        Iterator iModels = models.iterator();
                        while (iModels.hasNext()) {
                            ExpModelRemote model = (ExpModelRemote)iModels.next();
                            arr.add(new ExpModelDTO(model));
                        }
                    }
                } catch (PermissionDeniedException pde) {
                    System.out.println("Notice: search by project, permission denied. Excluded from search result");
                    pde.printStackTrace();
                }
            }
        }
        catch (FinderException fe)
        {
            fe.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("failed to search by research application",e);
        }
        return arr;
    }
   
    public Collection searchByKeyword(String keyword, MugenCaller caller) throws ApplicationException{
        Collection arr = new ArrayList();
        try
        {
            //MugenCaller searchCaller = getSearchCaller();
            MugenCaller searchCaller = caller;
            Keyword key = new Keyword(keyword);
            arr.addAll(searchModelByKeyword(key, searchCaller));
            arr.addAll(searchGeneByKeyword(key, searchCaller));
            arr.addAll(searchMPByKeyword(key, searchCaller));
            //arr.addAll(searchFunctionalSignificanceByKeyword(key, caller));
            //arr.addAll(searchFunctionalSignificanceTypeByKeyword(key, caller));
            //arr.addAll(searchGeneticModificationByKeyword(key, caller));
            //arr.addAll(searchResearchApplicationByKeyword(key, caller));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("failed to search by keyword",e);
        }
        return arr;
    }
    
    public Collection searchByKeywordFast(String keyword, String [] filter, MugenCaller caller) throws ApplicationException{
        Collection arr = new ArrayList();
        try
        {
            MugenCaller searchCaller = caller;
            Keyword key = new Keyword(keyword);
            if(key.getKeyword().length() > 2 && filter != null && filter.length != 0){
                for (int i = 0; i < filter.length; i++) {
                    if(filter[i].equals("mutants"))
                        arr.addAll(searchModelByKeyword(key, searchCaller));
                    if(filter[i].equals("genes"))
                        arr.addAll(searchGeneByKeyword(key, searchCaller));
                    if(filter[i].equals("mps"))
                        arr.addAll(searchMPByKeyword(key, searchCaller));
                    if(filter[i].equals("alleles"))
                        arr.addAll(searchAlleleByKeyword(key, searchCaller));
                }
            } else if(key.getKeyword().length() > 2){
                arr.addAll(searchModelByKeyword(key, searchCaller));
                arr.addAll(searchGeneByKeyword(key, searchCaller));
                arr.addAll(searchMPByKeyword(key, searchCaller));
                arr.addAll(searchAlleleByKeyword(key, searchCaller));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("failed to search by keyword fast",e);
        }
        return arr;
    }
    
    public MugenCaller getSearchCaller() throws ApplicationException{
        try {
            UserRemote user = userHome.findByUsr("public");
            return new MugenCaller(user);
        } catch (Exception e) {
            throw new ApplicationException("Failed to get search caller. Is user public created?");
        }
    }
    //</editor-fold>
    
    //simple methods with no db connectivity
    //<editor-fold defaultstate="collapsed">
    public java.lang.String getProjectName(){
        String prjname = "MMdb";
        return prjname;
    }
    
    public Collection getContactReason(){
        Collection contactReason = new ArrayList();
        
        contactReason.add("MMdb Data Inconsistency");
        contactReason.add("MMdb Database Malfunction | Bug Report");
        contactReason.add("MMdb Howto Inquiry");
        contactReason.add("MMdb Suggestions | Comments");
        
        return contactReason;
    }
    
    public Collection getOrderByTypes(){
        Collection orderByCollection = new ArrayList();
        
        orderByCollection.add("MMMDb ID");
        orderByCollection.add("LINE NAME");
        orderByCollection.add("DATE");
        
        return orderByCollection;
    }
    
    public Collection getOrderByTypes2(){
        Collection orderByCollection = new ArrayList();
        
        orderByCollection.add("MMMDb ID");
        orderByCollection.add("DATE");
        
        return orderByCollection;
    }
    
    public Collection getMutationTypeAttributes(){
        Collection mtaCollection = new ArrayList();
        
        mtaCollection.add("CONDITIONAL");
        mtaCollection.add("INDUCIBLE");
        mtaCollection.add("CONDITIONAL+INDUCIBLE");
        mtaCollection.add("N/A");
        mtaCollection.add("OTHER");
        
        return mtaCollection;
    }
    
    public Collection getLevelsForModel(){
        Collection arr = new ArrayList();
        arr.add(new String("Public"));
        arr.add(new String("Mugen"));
        arr.add(new String("Admin"));
        return arr;
    }
    
    public Collection getYesNos(){
        Collection yesnos = new ArrayList();
        
        yesnos.add("Yes");
        yesnos.add("No");
        
        return yesnos;
    }
    
    public Collection getDeltas(){
        Collection deltas = new ArrayList();
        
        deltas.add("20");
        deltas.add("40");
        deltas.add("60");
        deltas.add("80");
        deltas.add("100");
        
        return deltas;
    }
    //</editor-fold>
    
    //phenotype methods
    //<editor-fold defaultstate="collapsed">
    public Collection phenoParseFunction() throws ApplicationException {
        try {
                FTPClient test = new FTPClient("209.222.209.2", 21);
                
                test.login("mugen", "MuG3n01");
                test.setType(FTPTransferType.ASCII);
                
                test.setConnectMode(FTPConnectMode.PASV);
                
                //System.out.println("current directory on MGI ftp server after log in is --> "+test.pwd());
                test.chdir("/pub/reports/");
                
                byte[] buf = null;
                
                if(test.connected()){
                    //which file to grab
                    buf = test.get("MPheno_OBO.ontology");
                    System.out.println("Got " + buf.length + " bytes");
                }else{
                    System.out.println("Got no bytes from requested file at all...sorry. Did not even manage to get a connection man");
                }
                
                //once everything is done close the connection
                test.quit();
            
            //create a tmp file on server
            FileOutputStream tmp = new FileOutputStream("C:/tmp/tmp.ontology");
            //write the contents of the provided file into the tmp file create on the server
            tmp.write(buf);
            //close the outputstream
            tmp.close();
            
            //get the file created on the server again...
            File re_file = new File("C:/tmp/tmp.ontology");
            ByteArrayInputStream input_tmp = new ByteArrayInputStream(buf);
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(re_file));
            
            String line = null;
            
            PhenoParseDTO phenoDTO = new PhenoParseDTO();
            Collection phenoDTOs = new ArrayList();
            
            //tmp collections
            Collection ExistingXrefs = new ArrayList();
            Collection ExistingAltIds = new ArrayList();
            Collection ExistingSynonyms = new ArrayList();
            
            PhenotypeOntologyRemote po = null;
            
            //tmp integers 
            int xref_tmp_id=0;
            int alt_id_tmp_id=0;
            int synonym_tmp_id=0;
            
            //various control parameters
            int xref_exists=0;
            int alt_id_exists=0;
            int synonym_exists=0;
            int i = 0;
            String is_obs = "";
            
            //open the connection to the database
            makeConnection();
            
            while ((line = input.readLine()) != null){
            //while (i<200){
                //line = input.readLine();
                if(line.compareTo("[Term]")!=0){
                    phenoDTO.IdentifyLine(line);
                }else{
                    
                    if(phenoDTO.getPhenoId()!=""){
                        
                        if(phenoDTO.getIsObsolete()!=""){
                            is_obs = phenoDTO.getIsObsolete();
                        }else{
                            is_obs = "0";
                        }
                        
                        //create the ontology
                        po = phenoOntologyHome.create(new Integer(phenoDTO.getPhenoId()).intValue(), phenoDTO.getPhenoName(), phenoDTO.getPhenoDef(), phenoDTO.getPhenoDefRef(), new Integer(is_obs).intValue());
                        po.setComm(phenoDTO.getComm());
                        
                        //create the xref if applicable
                        if(!phenoDTO.getXrefs().isEmpty()){
                            //if xref(s) exist get the existing xrefs in the db
                            ExistingXrefs = phenoXrefHome.findByNoRestriction();
                            //get the iterator for this
                            Iterator j = phenoDTO.getXrefs().iterator();
                            //iterate through Xrefs of this term
                             while(j.hasNext()){
                                //get me the DTO
                                PhenoXrefDTO tmp_xref =(PhenoXrefDTO)j.next();
                                //if the existing xref(s) are not null...
                                if(!ExistingXrefs.isEmpty()){
                                    //...iterate through the collection of existing xref(s)
                                    Iterator x = ExistingXrefs.iterator();
                                    while(x.hasNext()){
                                        //get an Xref obj
                                        PhenotypeXrefRemote some_xref_obj = (PhenotypeXrefRemote)x.next();
                                        //if an existing xref with same name as one xref of the term is the same
                                        if(some_xref_obj.getXref().compareTo(tmp_xref.getXref())==0){
                                            xref_exists = 1;
                                            break;
                                        }
                                        xref_exists = 0;
                                    }
                                }else{
                                    xref_exists = 0;
                                }
                                
                                if(xref_exists==0){
                                  xref_tmp_id = getIIdGenerator().getNextId(conn, "pheno_xref_seq");
                                  phenoXrefHome.create(xref_tmp_id, tmp_xref.getXref());
                                }
                            }
                            xref_exists = 0;
                        }//if
                        
                        //create the alt_id if applicable
                        if(!phenoDTO.getAltIds().isEmpty()){
                            ExistingAltIds = phenoAltIdHome.findByNoRestriction();
                            Iterator k = phenoDTO.getAltIds().iterator();
                             while(k.hasNext()){
                                PhenoAltIdDTO tmp_alt_id =(PhenoAltIdDTO)k.next();
                                if(!ExistingAltIds.isEmpty()){
                                    Iterator y = ExistingAltIds.iterator();
                                    while(y.hasNext()){
                                        PhenotypeAltIdRemote some_alt_id_obj = (PhenotypeAltIdRemote)y.next();
                                        if(some_alt_id_obj.getAltId().compareTo(tmp_alt_id.getAltId())==0){
                                            alt_id_exists = 1;
                                            break;
                                        }
                                        alt_id_exists = 0;
                                    }
                                }else{
                                    alt_id_exists = 0;
                                }
                                
                                if(alt_id_exists==0){
                                  alt_id_tmp_id = getIIdGenerator().getNextId(conn, "pheno_alt_id_seq");
                                  phenoAltIdHome.create(alt_id_tmp_id, tmp_alt_id.getAltId());
                                }
                            }
                            alt_id_exists = 0;
                        }//if
                        
                        //create the synonyms if applicable
                        if(!phenoDTO.getSynonyms().isEmpty()){
                            ExistingSynonyms = phenoSynonymHome.findByNoRestriction();
                            Iterator l = phenoDTO.getSynonyms().iterator();
                             while(l.hasNext()){
                                PhenoSynonymDTO tmp_synonym =(PhenoSynonymDTO)l.next();
                                if(!ExistingSynonyms.isEmpty()){
                                    Iterator z = ExistingSynonyms.iterator();
                                    while(z.hasNext()){
                                        PhenotypeSynonymRemote some_synonym_obj = (PhenotypeSynonymRemote)z.next();
                                        if(some_synonym_obj.getSynonym().compareTo(tmp_synonym.getSynonym())==0){
                                            synonym_exists = 1;
                                            break;
                                        }
                                        synonym_exists = 0;
                                    }
                                }else{
                                    synonym_exists = 0;
                                }
                                
                                if(synonym_exists==0){
                                  synonym_tmp_id = getIIdGenerator().getNextId(conn, "pheno_synonym_seq");
                                  phenoSynonymHome.create(synonym_tmp_id, tmp_synonym.getSynonym());
                                }
                            }
                            synonym_exists = 0;
                        }//if
                    }
                    
                     
                    phenoDTOs.add(phenoDTO);
                    phenoDTO = new PhenoParseDTO();
                    i++;
                }
            }//while
            
            //-----to be tested---
            //System.out.println("<<<-----------------------------------------FINISHED LOADING MPTERMS----------------------------------------->>>");
            phenoDTOs = phenoParseDataRelationBuildingFunction(phenoDTOs);
            return phenoDTOs;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not parse phenotype ontology file.\n"+e.getMessage());            
        }finally{
            releaseConnection();
        }
    }
    
    public Collection phenoParseDataRelationBuildingFunction(Collection phenoDTOs) throws ApplicationException {
        try {
            Iterator i = phenoDTOs.iterator();
            
            while(i.hasNext()){
                PhenoParseDTO phenoDTO = (PhenoParseDTO)i.next();
                
                if(!phenoDTO.getXrefs().isEmpty()){
                    Iterator j = phenoDTO.getXrefs().iterator();
                    while(j.hasNext()){
                        //po.findByPrimaryKey(new Integer(phenoDTO.getPhenoId())).addXref(px.findByXrefName((PhenoXrefDTO)j.next()).getXref());
                        System.out.println("Adding Xref Relationship --> Pheno ID is-->"+new Integer(phenoDTO.getPhenoIdAsInt()));
                        PhenotypeOntologyRemote po_tmp = phenoOntologyHome.findByPrimaryKey(new Integer(phenoDTO.getPhenoIdAsInt()));
                        PhenotypeXrefRemote px_tmp = phenoXrefHome.findByXrefName(((PhenoXrefDTO)j.next()).getXref());
                        po_tmp.addXref(px_tmp);
                    }
                }//if
                
                //add relation between MP Terms and AltIds
                if(!phenoDTO.getAltIds().isEmpty()){
                    Iterator k = phenoDTO.getAltIds().iterator();
                    while(k.hasNext()){
                        System.out.println("Adding AltId Relationship --> Pheno ID is -->"+new Integer(phenoDTO.getPhenoIdAsInt()));
                        PhenotypeOntologyRemote po_tmp = phenoOntologyHome.findByPrimaryKey(new Integer(phenoDTO.getPhenoIdAsInt()));
                        PhenotypeAltIdRemote pa_tmp = phenoAltIdHome.findByAltId(((PhenoAltIdDTO)k.next()).getAltId());
                        po_tmp.addAltId(pa_tmp);
                    }
                }//if
                
                //add relation between MP Terms and Synonyms
                if(!phenoDTO.getSynonyms().isEmpty()){
                    Iterator l = phenoDTO.getSynonyms().iterator();
                    while(l.hasNext()){
                        System.out.println("Adding Synonym Relationship --> Pheno ID is-->"+new Integer(phenoDTO.getPhenoIdAsInt()));
                        PhenotypeOntologyRemote po_tmp = phenoOntologyHome.findByPrimaryKey(new Integer(phenoDTO.getPhenoIdAsInt()));
                        PhenoSynonymDTO yap = (PhenoSynonymDTO)l.next();
                        //PhenotypeSynonymRemote ps_tmp = phenoSynonymHome.findBySynonym(((PhenoSynonymDTO)l.next()).getSynonym());
                        //po_tmp.addSynonym(ps_tmp, ((PhenoSynonymDTO)l.next()).getAttribute());
                        PhenotypeSynonymRemote ps_tmp = phenoSynonymHome.findBySynonym(yap.getSynonym());
                        po_tmp.addSynonym(ps_tmp, yap.getAttribute());
                    }
                }//if
                
                //add is_a(s)
                if(!phenoDTO.getIsAs().isEmpty()){
                    Iterator m = phenoDTO.getIsAs().iterator();
                    while(m.hasNext()){
                        PhenoIsADTO tmp_is_a =(PhenoIsADTO)m.next();
                        System.out.println("Adding IS_A Relationship --> Pheno ID is-->"+new Integer(phenoDTO.getPhenoIdAsInt()));
                        PhenotypeOntologyRemote po_tmp = phenoOntologyHome.findByPrimaryKey(new Integer(phenoDTO.getPhenoIdAsInt()));
                        po_tmp.addIsA(new Integer(tmp_is_a.getIsA()).intValue());
                    }
                }//if
            }
            
            return phenoDTOs;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add relationships between collected phenotype ontology terms.\n"+e.getMessage());            
        }
    }
    
    public Collection phenoCollectorLevelOne()throws ApplicationException {
        try {                                            
            Collection mps = phenoOntologyHome.findByLevelOne();
            Collection dtos = new ArrayList();
            Iterator i = mps.iterator();
            while(i.hasNext()) {                
                dtos.add(new PhenoOntologyDTO((PhenotypeOntologyRemote)i.next()));
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get the level one mp terms.", e);
        }
    }
    
    public Collection phenoCollectorLowerLevel(int root_pheno_id)throws ApplicationException {
        try {                                            
            Collection mps = phenoOntologyHome.findByLowerLevel(root_pheno_id);
            Collection dtos = new ArrayList();
            Iterator i = mps.iterator();
            while(i.hasNext()) {                
                dtos.add(new PhenoOntologyDTO((PhenotypeOntologyRemote)i.next()));
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get the LOWER level mp terms.", e);
        }
    }
    
    public Collection phenoTermParseFunction() throws ApplicationException {
        try {
                FTPClient test = new FTPClient("209.222.209.2", 21);
                
                test.login("mugen", "MuG3n01");
                test.setType(FTPTransferType.ASCII);
                
                test.setConnectMode(FTPConnectMode.PASV);
                
                test.chdir("/pub/reports/");
                
                byte[] buf = null;
                
                if(test.connected()){
                    //which file to grab
                    buf = test.get("MPheno_OBO.ontology");
                    System.out.println("Got " + buf.length + " bytes");
                }else{
                    System.out.println("Got no bytes from requested file at all...sorry. Did not even manage to get a connection man");
                }
                
                test.quit();
            
            FileOutputStream tmp = new FileOutputStream("C:/tmp/mp_terms_tmp.ontology");
            tmp.write(buf);
            tmp.close();
            
            File re_file = new File("C:/tmp/mp_terms_tmp.ontology");
            ByteArrayInputStream input_tmp = new ByteArrayInputStream(buf);
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(re_file));
            
            String line = null;
            String sb_term = null;
            int is_first_term = 1;
            int is_in_collection = 0;
             
            Collection phenoDTOs = new ArrayList();
            
            while ((line = input.readLine()) != null){
            
                if(line.compareTo("[Term]")!=0 && line.length()!=0 && line.compareTo("[Typedef]")!=0){
                    sb_term = line.substring(0, line.indexOf(":"));
                    
                    if(!phenoDTOs.isEmpty()){
                       Iterator i = phenoDTOs.iterator();
                       while(i.hasNext()){
                           PhenoTermParseDTO term_parse_tmp = (PhenoTermParseDTO)i.next();
                           if(term_parse_tmp.getPhenoTerm().compareTo(sb_term)==0){
                               is_in_collection = 1;
                               break;
                           }else{
                               is_in_collection = 0;
                           }
                       }
                       
                       if(is_in_collection==0){
                           PhenoTermParseDTO some_new_mp_term = new PhenoTermParseDTO(sb_term);
                           phenoDTOs.add(some_new_mp_term);
                       }
                       
                    }else{
                        PhenoTermParseDTO first_mp_term = new PhenoTermParseDTO(line.substring(0, line.indexOf(":")));
                        phenoDTOs.add(first_mp_term);
                        is_first_term = 0;
                    }
                }
            }//while
            
            return phenoDTOs;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not parse phenotype ontology file to check mp term categories.\n"+e.getMessage());            
        }
    }
    
    public void addPhenoToModel(int eid, int mp01, int mp02, int mp03, int mp04, int mp05, int mp06, int mp07, int mp08, int mp09, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            model.addPhenoOntology(mp01, mp02, mp03, mp04, mp05, mp06, mp07, mp08, mp09);
            model.setUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add phenotype ontology to this model\n"+e.getMessage(),e);            
        }
    }
    
    public void removePhenoFromModel(int eid, int mp01, int mp02, int mp03, int mp04, int mp05, int mp06, int mp07, int mp08, int mp09, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            validate("MODEL_W", caller);
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            //GeneRemote gene = geneHome.findByPrimaryKey(new Integer(gaid));            
            model.removePhenoOntology(mp01, mp02, mp03, mp04, mp05, mp06, mp07, mp08, mp09);
            model.setUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove phenotype ontology\n"+e.getMessage(),e);            
        }        
    }
    
    public Collection getPhenosFromModel (int eid)throws ApplicationException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select mp01, mp02, mp03, mp04, mp05, mp06, mp07, mp08, mp09 from pheno_model_r where eid=?");
            ps.setInt(1,eid);
            result = ps.executeQuery();
            
            while (result.next()) {
                PhenotypeOntologyRemote mp00 = phenoOntologyHome.findByPrimaryKey(new Integer(result.getInt("mp01")));
                //System.out.println("mp00 in collection ->"+mp00.getName());
                PhenotypeOntologyRemote mp01 = phenoOntologyHome.findByPrimaryKey(new Integer(result.getInt("mp02")));
                //System.out.println("mp01 in collection ->"+mp01.getName());
                PhenotypeOntologyRemote mp02 = phenoOntologyHome.findByPrimaryKey(new Integer(result.getInt("mp03")));
                //System.out.println("mp02 in collection ->"+mp02.getName());
                PhenotypeOntologyRemote mp03 = phenoOntologyHome.findByPrimaryKey(new Integer(result.getInt("mp04")));
                //System.out.println("mp03 in collection ->"+mp03.getName());
                PhenotypeOntologyRemote mp04 = phenoOntologyHome.findByPrimaryKey(new Integer(result.getInt("mp05")));
                //System.out.println("mp04 in collection ->"+mp04.getName());
                PhenotypeOntologyRemote mp05 = phenoOntologyHome.findByPrimaryKey(new Integer(result.getInt("mp06")));
                //System.out.println("mp05 in collection ->"+mp05.getName());
                PhenotypeOntologyRemote mp06 = phenoOntologyHome.findByPrimaryKey(new Integer(result.getInt("mp07")));
                //System.out.println("mp06 in collection ->"+mp06.getName());
                PhenotypeOntologyRemote mp07 = phenoOntologyHome.findByPrimaryKey(new Integer(result.getInt("mp08")));
                //System.out.println("mp07 in collection ->"+mp07.getName());
                PhenotypeOntologyRemote mp08 = phenoOntologyHome.findByPrimaryKey(new Integer(result.getInt("mp09")));
                //System.out.println("mp08 in collection ->"+mp08.getName());
                
                arr.add(new PhenoAssignDTO(mp00,mp01,mp02,mp03,mp04,mp05,mp06,mp07,mp08));
            }
        } catch (Exception e) {
            throw new ApplicationException("Cannot find assigned phenotype ontologies for model "+eid+" \n");
        } finally {
            releaseConnection();
        }
        return arr;
    }
    //</editor-fold>
    
    //mp methods
    //<editor-fold defaultstate="collapsed">
    
    public java.lang.String downloadMP() throws ApplicationException {
        try {
            String localMP = "C:/tmp/tmp.ontology";
            
            FTPClient test = new FTPClient("209.222.209.2", 21);
            
            test.login("mugen", "MuG3n01");
            test.setType(FTPTransferType.ASCII);
            
            test.setConnectMode(FTPConnectMode.PASV);
            test.chdir("/pub/reports/");
            
            byte[] buf = null;
            
            if(test.connected()){
                buf = test.get("MPheno_OBO.ontology");
                System.out.println("Got " + buf.length + " bytes");
            }else{
                System.out.println("Got no bytes from requested file at all...sorry. Did not even manage to get a connection man");
            }

            //once everything is done close the connection
            test.quit();
            
            //create a tmp file on server
            //FileOutputStream tmp = new FileOutputStream("C:/tmp/tmp.ontology");
            FileOutputStream tmp = new FileOutputStream(localMP);
            //write the contents of the provided file into the tmp file create on the server
            tmp.write(buf);
            //close the outputstream
            tmp.close();
            
            return localMP;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("downloadMP: could not get MP file from MGI server.\n"+e.getMessage());            
        }
    }
    
    public void updateMP() throws ApplicationException {
        try {
            File re_file = new File(downloadMP());
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(re_file));
            
            String line = null;
            updateMPDTO MPDTO = new updateMPDTO();
            
            cleanTMP();
            
            while ((line = input.readLine()) != null){
                if(line.compareTo("[Term]")!=0){
                    MPDTO.IdentifyLine(line);
                }else{
                    if(!MPDTO.getPhenoId().equals("")){
                        storeMP(MPDTO);
                    }
                    MPDTO = null;
                    MPDTO = new updateMPDTO();
                }
            }
            
            //rebuild the tmp.pheno_paths_ table
            pathsMP(1);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("updateMP: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void storeMP(updateMPDTO MPDTO) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        updateMPDTO_isa isa = null;
        updateMPDTO_syn syn = null;
        updateMPDTO_alt alt = null;
        updateMPDTO_xref xref = null;
        
        try {
            
            //pheno ontology parameters
            ps = conn.prepareStatement("insert into tmp.pheno_ontology_ (id, name, def, def_ref, is_obsolete, comm) values (?,?,?,?,?,?)");
            ps.setInt(1, MPDTO.getPhenoIdAsInt());
            ps.setString(2, MPDTO.getPhenoName());
            ps.setString(3, MPDTO.getPhenoDef());
            ps.setString(4, MPDTO.getPhenoDefRef());
            ps.setInt(5, MPDTO.getIsObsoleteAsInt());
            ps.setString(6, MPDTO.getComm());
            ps.execute();
            
            //is a(s)
            Collection is_as = MPDTO.getIsAs();
            Iterator i = is_as.iterator();
           
            while (i.hasNext()) {
                isa = (updateMPDTO_isa)i.next();
                ps = conn.prepareStatement("insert into tmp.pheno_is_a_ (id_b, id_a) values (?,?)");
                ps.setInt(1, MPDTO.getPhenoIdAsInt());
                ps.setInt(2, isa.getIsAInt());
                ps.execute();
            }
            
            //synonym(s)
            Collection syns = MPDTO.getSynonyms();
            Iterator k = syns.iterator();
            
            while (k.hasNext()) {
                syn = (updateMPDTO_syn)k.next();
                ps = conn.prepareStatement("select id from tmp.pheno_synonym_ where synonym like ?");
                ps.setString(1, syn.getSynonym());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into tmp.pheno_synonym_ (id, synonym) values (?,?)");
                    ps.setInt(1, count_syn);
                    ps.setString(2, syn.getSynonym());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into tmp.pheno_synonym_r_ (pheno_id, pheno_synonym, attribute) values (?,?,?)");
                    ps.setInt(1, MPDTO.getPhenoIdAsInt());
                    ps.setInt(2, count_syn);
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                    
                    count_syn++;
                } else {
                    ps = conn.prepareStatement("insert into tmp.pheno_synonym_r_ (pheno_id, pheno_synonym, attribute) values (?,?,?)");
                    ps.setInt(1, MPDTO.getPhenoIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                }
            }
            
            //alt id(s)
            Collection alts = MPDTO.getAltIds();
            Iterator l = alts.iterator();
            
            while (l.hasNext()) {
                alt = (updateMPDTO_alt)l.next();
                ps = conn.prepareStatement("select id from tmp.pheno_alt_id_ where alt_id like ?");
                ps.setString(1, alt.getAltId());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into tmp.pheno_alt_id_ (id, alt_id) values (?,?)");
                    ps.setInt(1, count_alt);
                    ps.setString(2, alt.getAltId());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into tmp.pheno_alt_id_r_ (pheno_id, pheno_alt_id) values (?,?)");
                    ps.setInt(1, MPDTO.getPhenoIdAsInt());
                    ps.setInt(2, count_alt);
                    ps.execute();
                    
                    count_alt++;
                } else {
                    ps = conn.prepareStatement("insert into tmp.pheno_alt_id_r_ (pheno_id, pheno_alt_id) values (?,?)");
                    ps.setInt(1, MPDTO.getPhenoIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //xref(s)
            Collection xrefs = MPDTO.getXrefs();
            Iterator m = xrefs.iterator();
            
            while (m.hasNext()) {
                xref = (updateMPDTO_xref)m.next();
                ps = conn.prepareStatement("select id from tmp.pheno_xref_ where xref like ?");
                ps.setString(1, xref.getXref());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into tmp.pheno_xref_ (id, xref) values (?,?)");
                    ps.setInt(1, count_xref);
                    ps.setString(2, xref.getXref());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into tmp.pheno_xref_r_ (pheno_id, xref_id) values (?,?)");
                    ps.setInt(1, MPDTO.getPhenoIdAsInt());
                    ps.setInt(2, count_xref);
                    ps.execute();
                    
                    count_xref++;
                } else {
                    ps = conn.prepareStatement("insert into tmp.pheno_xref_r_ (pheno_id, xref_id) values (?,?)");
                    ps.setInt(1, MPDTO.getPhenoIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
        } catch (SQLException se) {
            logger.error("storeMP: failed to perform.", se);
            throw new ApplicationException("storeMP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void cleanTMP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("delete from tmp.pheno_ontology_");
            ps.execute();
            
            ps = conn.prepareStatement("delete from tmp.pheno_is_a_");
            ps.execute();
            
            ps = conn.prepareStatement("delete from tmp.pheno_synonym_");
            ps.execute();
            
            ps = conn.prepareStatement("delete from tmp.pheno_synonym_r_");
            ps.execute();
            
            ps = conn.prepareStatement("delete from tmp.pheno_alt_id_");
            ps.execute();
            
            ps = conn.prepareStatement("delete from tmp.pheno_alt_id_r_");
            ps.execute();
            
            ps = conn.prepareStatement("delete from tmp.pheno_xref_");
            ps.execute();
            
            ps = conn.prepareStatement("delete from tmp.pheno_xref_r_");
            ps.execute();
            
            ps = conn.prepareStatement("delete from tmp.pheno_paths_");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanTMP: failed to perform.", se);
            throw new ApplicationException("cleanTMP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public Collection reassignMP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_ = null;
        
        String [] mps = null;
        
        Collection altmps = new ArrayList();
        
        try {
            
            ps = conn.prepareStatement("select distinct eid from pheno_model_r_");
            result = ps.executeQuery();
            
            while(result.next()){
                ps = conn.prepareStatement("select mp from pheno_model_r_ where eid = ? and go = 0");
                ps.setInt(1, result.getInt("eid"));
                result_ = ps.executeQuery();
                
                while(result_.next()){
                    mps = result_.getString("mp").split(">");
                    if(validateMP(mps, result.getInt("eid"))){
                        logger.debug("mps for model "+result.getInt("eid")+" are ok");
                    } else {
                        logger.debug("mps for model "+result.getInt("eid")+" are not ok");
                        altmps.add(alternateMP(result_.getString("mp"), result.getInt("eid")));
                        return altmps;
                    }
                }
            }
            
            return altmps;
            
        } catch (SQLException se) {
            logger.error("reassignMP: failed to perform.", se);
            throw new ApplicationException("reassignMP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public int numreassignMP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_ = null;
        
        String [] mps = null;
        
        int nummps = 0;
        try {
            
            ps = conn.prepareStatement("select distinct eid from pheno_model_r_");
            result = ps.executeQuery();
            
            while(result.next()){
                ps = conn.prepareStatement("select mp from pheno_model_r_ where eid = ? and go = 0");
                ps.setInt(1, result.getInt("eid"));
                result_ = ps.executeQuery();
                
                while(result_.next()){
                    mps = result_.getString("mp").split(">");
                    if(validateMP(mps, result.getInt("eid"))){
                        //logger.debug("mps for model "+result.getInt("eid")+" are ok");
                    } else {
                        nummps++;
                        //logger.debug("mps for model "+result.getInt("eid")+" are not ok");
                    }
                }
            }
            
            return nummps;
            
        } catch (SQLException se) {
            logger.error("reassignMP: failed to perform.", se);
            throw new ApplicationException("reassignMP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void clearinvalidMP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_ = null;
        
        String [] mps = null;
        
        Collection altmps = new ArrayList();
        
        try {
            
            ps = conn.prepareStatement("select distinct eid from pheno_model_r_");
            result = ps.executeQuery();
            
            while(result.next()){
                ps = conn.prepareStatement("select mp from pheno_model_r_ where eid = ? and go = 0");
                ps.setInt(1, result.getInt("eid"));
                result_ = ps.executeQuery();
                
                while(result_.next()){
                    mps = result_.getString("mp").split(">");
                    if(validateMP(mps, result.getInt("eid"))){
                        //do nothing
                    } else {
                        ps = conn.prepareStatement("delete from pheno_model_r_ where eid = ? and mp like ? and go = 0");
                        ps.setInt(1, result.getInt("eid"));
                        ps.setString(2, result_.getString("mp"));
                        ps.execute();
                    }
                }
            }
            
            ps = conn.prepareStatement("update pheno_model_r_ set go=0 where go=1");
            ps.execute();
            
            //return altmps;
            
        } catch (SQLException se) {
            logger.error("clearinvalidMP: failed to perform.", se);
            throw new ApplicationException("clearinvalidMP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void removeoldMP(int eid, String mp) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("delete from pheno_model_r_ where eid = ? and mp like ? ");
            ps.setInt(1, eid);
            ps.setString(2, mp);
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("removeoldMP: failed to perform.", se);
            throw new ApplicationException("removeoldMP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void installMP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            //delete data from the public schema
            ps = conn.prepareStatement("delete from pheno_alt_id_r");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pheno_alt_id");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pheno_synonym_r");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pheno_synonym");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pheno_xref_r");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pheno_xref");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pheno_is_a");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pheno_paths");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pheno_ontology");
            ps.execute();
            
            //start to copy data from the tmp schema to the public schema
            ps = conn.prepareStatement("INSERT INTO pheno_ontology (SELECT id, name, def, def_ref, is_obsolete, comm FROM tmp.pheno_ontology_)");
            ps.execute();
            
            ps = conn.prepareStatement("INSERT INTO pheno_paths (SELECT ppid, path FROM tmp.pheno_paths_)");
            ps.execute();
            
            ps = conn.prepareStatement("INSERT INTO pheno_is_a (SELECT id_a, id_b FROM tmp.pheno_is_a_)");
            ps.execute();
            
            ps = conn.prepareStatement("INSERT INTO pheno_xref (SELECT id, xref FROM tmp.pheno_xref_)");
            ps.execute();
            
            ps = conn.prepareStatement("INSERT INTO pheno_xref_r (SELECT pheno_id, xref_id FROM tmp.pheno_xref_r_)");
            ps.execute();
            
            ps = conn.prepareStatement("INSERT INTO pheno_synonym (SELECT id, synonym FROM tmp.pheno_synonym_)");
            ps.execute();
            
            ps = conn.prepareStatement("INSERT INTO pheno_synonym_r (SELECT pheno_id, pheno_synonym, attribute FROM tmp.pheno_synonym_r_)");
            ps.execute();
            
            ps = conn.prepareStatement("INSERT INTO pheno_alt_id (SELECT id, alt_id FROM tmp.pheno_alt_id_)");
            ps.execute();
            
            ps = conn.prepareStatement("INSERT INTO pheno_alt_id_r (SELECT pheno_id, pheno_alt_id FROM tmp.pheno_alt_id_r_)");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("installMP: failed to perform.", se);
            throw new ApplicationException("installMP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public boolean validateMP(String[] mps, int eid) throws ApplicationException {
        boolean reply = true;
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_ = null;
        
        try {
            for(int i=0; i < mps.length; i++){
                
                ps = conn.prepareStatement("select a.name as mpname, b.name as mpname_ from pheno_ontology a, tmp.pheno_ontology_ b where a.id=b.id and a.id=?");
                ps.setInt(1, new Integer(mps[i]).intValue());
                result = ps.executeQuery();
                
                if(result.next()){
                    if(result.getString("mpname").compareToIgnoreCase(result.getString("mpname_"))!=0){
                        return reply = false;
                    }
                } else {
                    return reply = false;
                }
            }
            
            return  reply;
            
        } catch (SQLException se) {
            logger.error("validateMP: failed to perform.", se);
            throw new ApplicationException("validateMP: failed to perform.\n"+se.getMessage());
        }
    }
    
    public boolean suggestMP(String[] mps, int mpsindex, int eid) throws ApplicationException {
        boolean reply = true;
        
        String mppathold = "";
        String mppathnew = "";
        //construct the old mp path
        for(int i = 0; i < mps.length; i++){
            mppathold = mps[i] + ">" + mppathold;
        }
        //construct the steady (unchanged) part of the new path
        for(int i = mpsindex+1; i < mps.length; i++){
            mppathnew = mps[i] + ">" + mppathnew;
        }
        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            //for()
            ps = conn.prepareStatement("select id from tmp.pheno_ontology_ where name in (select name from pheno_ontology where id = ?)");
            ps.setInt(1, new Integer(mps[mpsindex]).intValue());
            result = ps.executeQuery();
            
            //found the same mp term with new id
            if(result.next()){
                //if problematic mp is not the last up the route
                if(mpsindex > 0){
                    //get all the 
                    ps = conn.prepareStatement("select id_a from tmp.pheno_is_a_ where id_b = ?");
                    ps.setInt(1, result.getInt("id"));
                    result = ps.executeQuery();
                    //found is_a mps for the new id+now i have to double check their names.
                    while(result.next()){
                        
                    }
                }
            }
            
            return  reply;
            
        } catch (SQLException se) {
            logger.error("suggestMP: failed to perform.", se);
            throw new ApplicationException("suggestMP: failed to perform.\n"+se.getMessage());
        }
    }
    
    public pathsMP alternateMP(String mp, int eid) throws ApplicationException {
        boolean reply = true;
        
        pathsMP paths = new pathsMP(eid, mp, translateMP(mp));
        
        String [] mps = mp.split(">");
        String endmp = mps[mps.length-1];
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_ = null;
        
        try {
            ps = conn.prepareStatement("select a.name as mpname, b.name as mpname_ from pheno_ontology a, tmp.pheno_ontology_ b where a.id=b.id and a.id=?");
            ps.setInt(1, new Integer(endmp).intValue());
            result = ps.executeQuery();
            if(result.next()){
                if(result.getString("mpname").compareToIgnoreCase(result.getString("mpname_"))==0){
                    ps = conn.prepareStatement("select path from tmp.pheno_paths_ where path like ?");
                    //ps.setString(1, "%>"+endmp+"%");
                    ps.setString(1, "%>"+endmp);
                    result_ = ps.executeQuery();
                    
                    while(result_.next()){
                        paths.addAlternativePath(result_.getString("path"));
                        paths.addAlternativePathTrans(translateTMP(result_.getString("path")));
                    }
                    
                }else{
                    ps = conn.prepareStatement("select name from pheno_ontology where id = ?");
                    ps.setInt(1, new Integer(endmp).intValue());
                    result_ = ps.executeQuery();
                    result_.next();
                    String endmpname = result_.getString("name");
                    
                    String [] endmpelements = endmpname.split(" ");
                    
                    String sql = "select id from tmp.pheno_ontology_";
                    
                    for(int i = 0; i < endmpelements.length; i++){
                        if(i==0){
                            sql += " where name like '%"+endmpelements[i]+"%' ";
                        } else {
                            sql += "and name like '%"+endmpelements[i]+"%' ";
                        }
                    }
                    
                    ps = conn.prepareStatement(sql);
                    result_ = ps.executeQuery();
                    
                    while(result_.next()){
                        ps = conn.prepareStatement("select path from tmp.pheno_paths_ where path like ?");
                        //ps.setString(1, "%>"+new Integer(result_.getInt("id")).toString()+"%");
                        ps.setString(1, "%>"+new Integer(result_.getInt("id")).toString());
                        result = ps.executeQuery();
                        
                        while(result.next()){
                            paths.addAlternativePath(result.getString("path"));
                            paths.addAlternativePathTrans(translateTMP(result.getString("path")));
                        }
                    }
                    
                }
            } else {
                ps = conn.prepareStatement("select name from pheno_ontology where id = ?");
                ps.setInt(1, new Integer(endmp).intValue());
                result_ = ps.executeQuery();
                
                String enmpname = "";
                
                while(result_.next())
                    enmpname = result_.getString("name");
                
                String [] endmpelements = enmpname.split(" ");
                    
                String sql = "select id from tmp.pheno_ontology_";
                
                for(int i = 0; i < endmpelements.length; i++){
                    if(i==0){
                        sql += " where name like '%"+endmpelements[i]+"%' ";
                    } else {
                        sql += "and name like '%"+endmpelements[i]+"%' ";
                    }
                }
                
                ps = conn.prepareStatement(sql);
                result_ = ps.executeQuery();
                
                //new staff
                if(!result_.next()) {
                    sql = "select id from tmp.pheno_ontology_";
                    
                    for(int i = 0; i < endmpelements.length; i++){ 
                        if(i==0){
                            sql += " where name like '%"+endmpelements[i]+"%' ";
                        } else {
                            sql += "or name like '%"+endmpelements[i]+"%' ";
                        }
                    }
                    
                    ps = conn.prepareStatement(sql);
                    result_ = ps.executeQuery();
                } else {
                    sql = "select id from tmp.pheno_ontology_";
                    
                    for(int i = 0; i < endmpelements.length; i++){ 
                        if(i==0){
                            sql += " where name like '%"+endmpelements[i]+"%' ";
                        } else {
                            sql += "and name like '%"+endmpelements[i]+"%' ";
                        }
                    }
                    
                    ps = conn.prepareStatement(sql);
                    result_ = ps.executeQuery();
                }
                //new staff

                while(result_.next()){
                    ps = conn.prepareStatement("select path from tmp.pheno_paths_ where path like ?");
                    //ps.setString(1, "%>"+new Integer(result_.getInt("id")).toString()+"%");
                    ps.setString(1, "%>"+new Integer(result_.getInt("id")).toString());
                    result = ps.executeQuery();
                    
                    while(result.next()){
                        paths.addAlternativePath(result.getString("path"));
                        paths.addAlternativePathTrans(translateTMP(result.getString("path")));
                    }
                }
                
            }
            
            paths.constructAltpathstr();
            logger.error("completed path object creation+population. alternateMP will return.");
            return paths;
            
        } catch (SQLException se) {
            logger.error("alternateMP: failed to perform.", se);
            throw new ApplicationException("alternateMP: failed to perform.\n"+se.getMessage());
        }
    }
    
    public String translateMP (String mpseq) throws ApplicationException {
        try {
            String[] mps_ = mpseq.split(">");
            String mpnames = "";
            
            for(int i=1; i < mps_.length; i++){
                 PhenotypeOntologyRemote pheno = phenoOntologyHome.findByPrimaryKey(new Integer(mps_[i]));
                 if(i==1){
                     mpnames += pheno.getName();
                 }else{
                     mpnames += ">"+pheno.getName();
                 }
            }
            
            return mpnames;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to convert mp id seq in name seq.", e);
        }
    }
    
    public String translateTMP (String mps_) throws ApplicationException {
        
        String[] mps = mps_.split(">");
        String mpnames = "";
        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            
            for(int i=1; i < mps.length; i++){
                
                ps = conn.prepareStatement("select name from tmp.pheno_ontology_ where id = ?");
                ps.setInt(1, new Integer(mps[i]).intValue());
                result = ps.executeQuery();
                
                result.next();
                
                if(i==1){
                    mpnames += result.getString("name");
                }else{
                    mpnames += ">"+result.getString("name");
                }
            }
            
            return mpnames;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to convert mp id seq in name seq from tmp schema.", e);
        }
    }
    
    public void replaceMP(int eid, String mpold, String mpnew) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        
        try {
            
            ps = conn.prepareStatement("delete from pheno_model_r_ where eid = ? and mp like ?");
            ps.setInt(1, eid);
            ps.setString(2, mpold);
            ps.execute();
            
            ps = conn.prepareStatement("insert into pheno_model_r_ (eid, mp, go) values (?,?, 1)");
            ps.setInt(1, eid);
            ps.setString(2, mpnew);
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("replaceMP: failed to perform.", se);
            throw new ApplicationException("replaceMP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public boolean namecheckMP(int mpid) throws ApplicationException {
        boolean reply = true;
        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        String[] mpparts = null;
        String sql = "";
        
        try {
            //get the name of the changed mp
            ps = conn.prepareStatement("select name from pheno_ontology where id = ?");
            ps.setInt(1, mpid);
            result = ps.executeQuery();
            mpparts = result.getString("name").split(" ");
            
            sql = "select id from tmp.pheno_ontology_ where ";
            
            for(int i = 0; i < mpparts.length; i++){
                sql += "name is like '%"+mpparts[i]+"%' ";
                
                if(i!=(mpparts.length-1)){
                    sql += "and ";
                }
            }
            
            ps = conn.prepareStatement(sql);
            result = ps.executeQuery();
            
            while(result.next()){
                
            }
            
            return  reply;
            
        } catch (SQLException se) {
            logger.error("namecheckMP: failed to perform.", se);
            throw new ApplicationException("namecheckMP: failed to perform.\n"+se.getMessage());
        }
    }
    
    public boolean pathsMP(int mpid) throws ApplicationException {
        makeConnection();
        
        boolean reply = true;
        
        String path = new Integer(mpid).toString();
        String path_ = "";
        String [] pathnodes = null;
        
        int nodestep = 1;
        int nodetmp = 0;
        int ppid = 1;
        boolean doit = true;
        
        PreparedStatement ps = null;
        PreparedStatement ps_ = null;
        PreparedStatement ins = null;
        ResultSet result = null;
        ResultSet result_ = null;
        
        Collection nodes = new ArrayList();
        Collection paths = new ArrayList();
        
        try {
            ps = conn.prepareStatement("select id_b from tmp.pheno_is_a_ where id_a = ? order by id_b");
            ps.setInt(1, mpid);
            result = ps.executeQuery();
            
            while(result.next()){
                
                if(doit){
                    path += ">"+new Integer(result.getInt("id_b")).toString();
                    //new 
                    ins = conn.prepareStatement("insert into tmp.pheno_paths_ (ppid, path) values (?, ?)");
                    ins.setInt(1, ppid);
                    ins.setString(2, path);
                    ins.execute();
                    ppid++;
                    //
                    //logger.debug("[0] "+path);
                    nodetmp = result.getInt("id_b");
                    ps = conn.prepareStatement("select id_b from tmp.pheno_is_a_ where id_a = ? order by id_b");
                    ps.setInt(1, result.getInt("id_b"));
                    result = ps.executeQuery();
                
                    if(result.next()){
                        nodestep = result.getInt("id_b");
                        
                        ps_ = conn.prepareStatement("select id_b from tmp.pheno_is_a_ where id_a = ? order by id_b");
                        ps_.setInt(1, result.getInt("id_b"));
                        result_ = ps_.executeQuery();
                        
                        if(result_.next()){
                            path += ">"+new Integer(result.getInt("id_b")).toString();
                            //new add
                            ins = conn.prepareStatement("insert into tmp.pheno_paths_ (ppid, path) values (?, ?)");
                            ins.setInt(1, ppid);
                            ins.setString(2, path);
                            ins.execute();
                            ppid++;
                            //logger.debug("[1] "+path);
                            //
                            result = ps_.executeQuery();
                        } else {
                            path_ = path + ">"+ new Integer(result.getInt("id_b")).toString();
                            nodestep = result.getInt("id_b");
                            
                            ins = conn.prepareStatement("insert into tmp.pheno_paths_ (ppid, path) values (?, ?)");
                            ins.setInt(1, ppid);
                            ins.setString(2, path_);
                            ins.execute();
                            ppid++;
                            //logger.debug("[2] "+path_);
                            
                            ps = conn.prepareStatement("select id_b from tmp.pheno_is_a_ where id_a = ? and id_b > ? order by id_b");
                            ps.setInt(1, nodetmp);
                            ps.setInt(2, nodestep);
                            result = ps.executeQuery();
                            
                            while(!result.next()){
                                nodetmp = new Integer(path.substring(path.substring(0, path.lastIndexOf(">")).lastIndexOf(">")+1, path.lastIndexOf(">"))).intValue();
                                nodestep = new Integer(path.substring(path.lastIndexOf(">")+1)).intValue();
                                ps = conn.prepareStatement("select id_b from tmp.pheno_is_a_ where id_a = ? and id_b > ? order by id_b");
                                ps.setInt(1, nodetmp);
                                ps.setInt(2, nodestep);
                                result = ps.executeQuery();
                                path = path.substring(0, path.lastIndexOf(">"));
                            }
                            
                            result = ps.executeQuery();
                        }
                    } else {
                        nodetmp = new Integer(path.substring(path.substring(0, path.lastIndexOf(">")).lastIndexOf(">")+1, path.lastIndexOf(">"))).intValue();
                        nodestep = new Integer(path.substring(path.lastIndexOf(">")+1)).intValue();
                        
                        int dummy = nodestep;
                        //logger.debug("(2)path added to collection "+path);
                        /*
                        ins = conn.prepareStatement("insert into tmp.pheno_paths_ (ppid, path) values (?, ?)");
                        ins.setInt(1, ppid);
                        ins.setString(2, path);
                        ins.execute();
                        ppid++;
                        logger.debug("[3] "+path);
                         */
                        pathnodes = path.split(">");
                        
                        ps = conn.prepareStatement("select id_b from tmp.pheno_is_a_ where id_a = ? and id_b > ? order by id_b");
                        ps.setInt(1, nodetmp);
                        ps.setInt(2, nodestep);
                        result = ps.executeQuery();
                        
                        int depth = 2;
                        while(!result.next() && depth <= pathnodes.length){
                            nodetmp = new Integer(pathnodes[pathnodes.length-depth]).intValue();
                            nodestep = new Integer(pathnodes[pathnodes.length-depth+1]).intValue();
                            ps = conn.prepareStatement("select id_b from tmp.pheno_is_a_ where id_a = ? and id_b > ? order by id_b");
                            ps.setInt(1, nodetmp);
                            ps.setInt(2, nodestep);
                            path = path.substring(0, path.lastIndexOf(">"));
                            result = ps.executeQuery();
                            depth++;
                        }
                        
                        if(dummy == nodestep){
                            path = path.substring(0, path.lastIndexOf(">"));
                        }
                        
                        result = ps.executeQuery();
                        
                    }
                }//doit
            }
            
            return  reply;
            
        } catch (SQLException se) {
            logger.error("pathsMP: failed to perform.", se);
            throw new ApplicationException("pathsMP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public boolean pathsMP2(int mpid) throws ApplicationException {
        makeConnection();
        boolean reply = true;
        
        String path = new Integer(mpid).toString();
        String path_ = "";
        String [] pathnodes = null;
        
        int nodestep = 1;
        int nodetmp = 0;
        int ppid = 1;
        boolean doit = true;
        
        PreparedStatement ps = null;
        PreparedStatement ps_ = null;
        PreparedStatement ins = null;
        ResultSet result = null;
        ResultSet result_ = null;
        
        Collection nodes = new ArrayList();
        Collection paths = new ArrayList();
        
        try {
            ps = conn.prepareStatement("select id_b from pheno_is_a where id_a = ? order by id_b");
            ps.setInt(1, mpid);
            result = ps.executeQuery();
            
            while(result.next()){
                
                if(doit){
                    path += ">"+new Integer(result.getInt("id_b")).toString();
                    //new 
                    ins = conn.prepareStatement("insert into pheno_paths (ppid, path) values (?, ?)");
                    ins.setInt(1, ppid);
                    ins.setString(2, path);
                    ins.execute();
                    ppid++;
                    //
                    logger.debug("[0] "+path);
                    nodetmp = result.getInt("id_b");
                    ps = conn.prepareStatement("select id_b from pheno_is_a where id_a = ? order by id_b");
                    ps.setInt(1, result.getInt("id_b"));
                    result = ps.executeQuery();
                
                    if(result.next()){
                        nodestep = result.getInt("id_b");
                        
                        ps_ = conn.prepareStatement("select id_b from pheno_is_a where id_a = ? order by id_b");
                        ps_.setInt(1, result.getInt("id_b"));
                        result_ = ps_.executeQuery();
                        
                        if(result_.next()){
                            path += ">"+new Integer(result.getInt("id_b")).toString();
                            //new add
                            ins = conn.prepareStatement("insert into pheno_paths (ppid, path) values (?, ?)");
                            ins.setInt(1, ppid);
                            ins.setString(2, path);
                            ins.execute();
                            ppid++;
                            logger.debug("[1] "+path);
                            //
                            result = ps_.executeQuery();
                        } else {
                            path_ = path + ">"+ new Integer(result.getInt("id_b")).toString();
                            nodestep = result.getInt("id_b");
                            
                            ins = conn.prepareStatement("insert into pheno_paths (ppid, path) values (?, ?)");
                            ins.setInt(1, ppid);
                            ins.setString(2, path_);
                            ins.execute();
                            ppid++;
                            logger.debug("[2] "+path_);
                            
                            ps = conn.prepareStatement("select id_b from pheno_is_a where id_a = ? and id_b > ? order by id_b");
                            ps.setInt(1, nodetmp);
                            ps.setInt(2, nodestep);
                            result = ps.executeQuery();
                            
                            while(!result.next()){
                                nodetmp = new Integer(path.substring(path.substring(0, path.lastIndexOf(">")).lastIndexOf(">")+1, path.lastIndexOf(">"))).intValue();
                                nodestep = new Integer(path.substring(path.lastIndexOf(">")+1)).intValue();
                                ps = conn.prepareStatement("select id_b from pheno_is_a where id_a = ? and id_b > ? order by id_b");
                                ps.setInt(1, nodetmp);
                                ps.setInt(2, nodestep);
                                result = ps.executeQuery();
                                path = path.substring(0, path.lastIndexOf(">"));
                                logger.debug("[2.1] "+path+" nodetmp "+nodetmp+" nodestep "+nodestep);
                            }
                            
                            result = ps.executeQuery();
                        }
                    } else {
                        nodetmp = new Integer(path.substring(path.substring(0, path.lastIndexOf(">")).lastIndexOf(">")+1, path.lastIndexOf(">"))).intValue();
                        nodestep = new Integer(path.substring(path.lastIndexOf(">")+1)).intValue();
                        
                        int dummy = nodestep;
                        
                        pathnodes = path.split(">");
                        
                        ps = conn.prepareStatement("select id_b from pheno_is_a where id_a = ? and id_b > ? order by id_b");
                        ps.setInt(1, nodetmp);
                        ps.setInt(2, nodestep);
                        result = ps.executeQuery();
                        
                        int depth = 2;
                        while(!result.next() && depth <= pathnodes.length){
                            nodetmp = new Integer(pathnodes[pathnodes.length-depth]).intValue();
                            nodestep = new Integer(pathnodes[pathnodes.length-depth+1]).intValue();
                            ps = conn.prepareStatement("select id_b from pheno_is_a where id_a = ? and id_b > ? order by id_b");
                            ps.setInt(1, nodetmp);
                            ps.setInt(2, nodestep);
                            path = path.substring(0, path.lastIndexOf(">"));
                            logger.debug("[3.1] "+path+" nodetmp "+nodetmp+" nodestep "+nodestep);
                            result = ps.executeQuery();
                            depth++;
                        }
                        
                        if(dummy == nodestep){
                            path = path.substring(0, path.lastIndexOf(">"));
                        }
                        
                        result = ps.executeQuery();
                        
                    }
                }//doit
            }
            
            return  reply;
            
        } catch (SQLException se) {
            logger.error("pathsMP2: failed to perform.", se);
            throw new ApplicationException("pathsMP2: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void addPhenoPathToModel(int eid, int ppid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            PhenotypeOntologyPathRemote pop = phenoPathHome.findByPrimaryKey(new Integer(ppid));
            model.addPhenoOntologyPath(pop);
            //model.addPhenoOntology(mp01, mp02, mp03, mp04, mp05, mp06, mp07, mp08, mp09);
            model.setUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add phenotype ontology path to this model\n"+e.getMessage(),e);            
        }
    }
    
    public void removePhenoPathFromModel(int eid, int ppid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            validate("MODEL_W", caller);
            ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
            PhenotypeOntologyPathRemote pop = phenoPathHome.findByPrimaryKey(new Integer(ppid));
            model.removePhenoOntologyPath(pop);
            model.setUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove phenotype ontology path\n"+e.getMessage(),e);            
        }        
    }
    
    public Collection endMPs() throws ApplicationException {
        try {
            makeConnection();
            
            PreparedStatement ps = null;
            ResultSet result = null;
            
            Collection mps = new ArrayList();
            String mpnames = "";
            String mpids = "";
            
            ps = conn.prepareStatement("select distinct mp from pheno_model_r_");
            result = ps.executeQuery();
            
            while(result.next()){
                String [] mpseq = result.getString("mp").split(">");
                PhenotypeOntologyRemote pheno = phenoOntologyHome.findByPrimaryKey(new Integer(mpseq[mpseq.length-1]));
                mps.add(new pathsMP(pheno));
            }
            
            return mps;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to convert mp id seq in name seq.", e);
        } finally {
            releaseConnection();
        }
    }
    
    public Collection searchMPByKeyword(Keyword keyword, MugenCaller caller) throws ApplicationException {
        Collection arr = new TreeSet();
        try {
            Collection mps = phenoOntologyHome.findByKeyword(keyword, caller);
            Iterator i = mps.iterator();
            
            while (i.hasNext()) {
                PhenotypeOntologyRemote mp = (PhenotypeOntologyRemote)i.next();
                arr.add(new MPSearchResult(mp,"Controller?workflow=ViewPhenotypeOntology&potid="+mp.getId()));
            }
        } catch (FinderException fe) {
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("failed to search by phenotype ontology term",e);
        }
        return arr;
    }
    
    public PhenoOntologyDTO getPhenoOntology(int mpid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            PhenotypeOntologyRemote pot = phenoOntologyHome.findByPrimaryKey(new Integer(mpid));
            return new PhenoOntologyDTO(pot);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not phenotype ontology term\n"+e.getMessage());            
        }
    }
    
    public Collection getPhenoAltIds(int mpid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            Collection potalt = phenoAltIdHome.findByPhenoId(mpid);
            Collection dtos = new ArrayList();
            Iterator i = potalt.iterator();
            while(i.hasNext()) {                
                dtos.add(new PhenoAltIdDTO((PhenotypeAltIdRemote)i.next()));
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not phenotype ontology alt ids\n"+e.getMessage());            
        }
    }
    
    public Collection getPhenoSynonyms(int mpid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {
            Collection potsyn = phenoSynonymHome.findByPhenoId(mpid);
            Collection dtos = new ArrayList();
            Iterator i = potsyn.iterator();
            while(i.hasNext()) {                
                //dtos.add(new PhenoAltIdDTO((PhenotypeAltIdRemote)i.next()));
                PhenotypeSynonymRemote tmp = (PhenotypeSynonymRemote)i.next();
                String attribute = getPhenoSynonymAttribute(mpid, tmp.getId());
                dtos.add(new PhenoSynonymDTO(tmp.getSynonym(), attribute));
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not phenotype ontology alt ids\n"+e.getMessage());            
        }
    }
    
    public String getPhenoSynonymAttribute(int mpid, int synid) throws ApplicationException {
        try {
            makeConnection();
            
            PreparedStatement ps = null;
            ResultSet result = null;
            
            String attribute = "";
            
            ps = conn.prepareStatement("select attribute from pheno_synonym_r where pheno_id = ? and pheno_synonym = ?");
            ps.setInt(1, mpid);
            ps.setInt(2, synid);
            result = ps.executeQuery();
            
            while(result.next()){
                attribute = result.getString("attribute")+" []";
            }
            
            return attribute;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get synonym attribute.", e);
        } finally {
            releaseConnection();
        }
    }
    
    public Collection getPhenoIsAs(int mpid, MugenCaller caller) throws ApplicationException {
        Collection arr = new ArrayList();
        try {
            Collection mps = phenoOntologyHome.findByIsA(mpid);
            Iterator i = mps.iterator();
            
            while (i.hasNext()) {
                arr.add(new PhenoOntologyDTO((PhenotypeOntologyRemote)i.next()));
            }
        } catch (FinderException fe) {
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("failed to get is_a(s)",e);
        }
        return arr;
    }
    
    public Collection getPhenoEndNodePaths (int mpid) throws ApplicationException {
        try {
            Collection phps = phenoPathHome.findByEndNode(new Integer(mpid).toString());
            Collection dtos = new ArrayList();
            String[] tmp = null;
            Iterator i = phps.iterator();
            while(i.hasNext()) {
                dtos.add(new pathsMP((PhenotypeOntologyPathRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get end-node paths.", e);
        }
    }
    
    //</editor-fold>
    
    //pato methods
    //<editor-fold defaultstate="collapsed">
    public void loadPATO() throws ApplicationException {
        try {
            File file = new File("C:/zouberakis_/_/pato/obo.files/quality.obo");
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(file));
            
            String line = null;
            //updateMPDTO MPDTO = new updateMPDTO();
            PatoDTO pato = new PatoDTO();
            
            //drop all FK constraints in schema PATO
            dropconPATO();
            //delete all data in schema PATO
            cleanPATO();
            
            while ((line = input.readLine()) != null){
                if(line.compareTo("[Term]")!=0){
                    pato.IdentifyLine(line);
                }else{
                    if(!pato.getId().equals("")){
                        storePATO(pato);
                    }
                    pato = null;
                    pato = new PatoDTO();
                }
            }
            
            //reconstruct all FK constraints
            restoreconPATO();
            
            //rebuild the tmp.pheno_paths_ table
            //pathsMP(1);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("loadPATO: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void cleanPATO() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("delete from pato.pato_term");
            ps.execute();
        
            ps = conn.prepareStatement("delete from pato.pato_alt_id");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pato.pato_alt_id_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from pato.pato_is_a");
            ps.execute();
        
            ps = conn.prepareStatement("delete from pato.pato_syn");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pato.pato_syn_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from pato.pato_xref");
            ps.execute();
            
            ps = conn.prepareStatement("delete from pato.pato_xref_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from pato.pato_subsets");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanPATO: failed to perform.", se);
            throw new ApplicationException("cleanPATO: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void dropconPATO() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE pato.pato_alt_id_r DROP CONSTRAINT pato_alt_id_r_pato_fk;" +
                    "ALTER TABLE pato.pato_alt_id_r DROP CONSTRAINT pato_alt_id_r_alt_id_fk;" +
                    "ALTER TABLE pato.pato_is_a DROP CONSTRAINT pato_is_a_fk_a;" +
                    "ALTER TABLE pato.pato_is_a DROP CONSTRAINT pato_is_a_fk_b;" +
                    "ALTER TABLE pato.pato_syn_r DROP CONSTRAINT pato_syn_r_pato_fk;" +
                    "ALTER TABLE pato.pato_syn_r DROP CONSTRAINT pato_syn_r_syn_fk;" +
                    "ALTER TABLE pato.pato_xref_r DROP CONSTRAINT pato_xref_r_pato_fk;" +
                    "ALTER TABLE pato.pato_xref_r DROP CONSTRAINT pato_xref_r_xref_fk;" +
                    "ALTER TABLE pato.pato_subsets DROP CONSTRAINT pato_subsets_fk;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("dropconPATO: failed to drop constraints for schema PATO.", se);
            throw new ApplicationException("dropconPATO: failed to drop constraints for schema PATO.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void restoreconPATO() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE pato.pato_alt_id_r ADD " +
                    "CONSTRAINT pato_alt_id_r_alt_id_fk FOREIGN KEY " +
                    "(pato_alt_id) REFERENCES pato.pato_alt_id (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE pato.pato_alt_id_r ADD CONSTRAINT " +
                    "pato_alt_id_r_pato_fk FOREIGN KEY (pato_id)REFERENCES " +
                    "pato.pato_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE pato.pato_is_a ADD CONSTRAINT pato_is_a_fk_a " +
                    "FOREIGN KEY (id_a) REFERENCES pato.pato_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE pato.pato_is_a ADD CONSTRAINT pato_is_a_fk_b " +
                    "FOREIGN KEY (id_b) REFERENCES pato.pato_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE pato.pato_syn_r ADD CONSTRAINT " +
                    "pato_syn_r_pato_fk FOREIGN KEY (pato_id) REFERENCES " +
                    "pato.pato_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE pato.pato_syn_r ADD CONSTRAINT " +
                    "pato_syn_r_syn_fk FOREIGN KEY (pato_syn) REFERENCES " +
                    "pato.pato_syn (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE pato.pato_xref_r ADD CONSTRAINT " +
                    "pato_xref_r_pato_fk FOREIGN KEY (pato_id) REFERENCES " +
                    "pato.pato_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE pato.pato_xref_r ADD CONSTRAINT " +
                    "pato_xref_r_xref_fk FOREIGN KEY (xref_id) REFERENCES " +
                    "pato.pato_xref (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE pato.pato_subsets ADD CONSTRAINT " +
                    "pato_subsets_fk FOREIGN KEY (pato_id) REFERENCES " +
                    "pato.pato_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("restoreconPATO: failed to restore constraints for schema PATO.", se);
            throw new ApplicationException("restoreconPATO: failed to restore constraints for schema PATO.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void storePATO(PatoDTO pato) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        PatoDTO_ISA isa = null;
        PatoDTO_SYN syn = null;
        PatoDTO_ALT alt = null;
        PatoDTO_XREF xref = null;
        PatoDTO_SUBSET subset = null;
        
        try {
            
            //pheno ontology parameters
            ps = conn.prepareStatement("insert into pato.pato_term (id, name, def, def_ref, is_obsolete, comm) values (?,?,?,?,?,?)");
            ps.setInt(1, pato.getIdAsInt());
            ps.setString(2, pato.getName());
            ps.setString(3, pato.getDef());
            ps.setString(4, pato.getDefRef());
            ps.setInt(5, pato.getIsObsoleteAsInt());
            ps.setString(6, pato.getComm());
            ps.execute();
            
            //is a(s)
            Collection is_as = pato.getIsAs();
            Iterator i = is_as.iterator();
           
            while (i.hasNext()) {
                isa = (PatoDTO_ISA)i.next();
                ps = conn.prepareStatement("insert into pato.pato_is_a (id_b, id_a) values (?,?)");
                ps.setInt(1, pato.getIdAsInt());
                ps.setInt(2, isa.getIsAInt());
                ps.execute();
            }
            
            //synonym(s)
            Collection syns = pato.getSynonyms();
            Iterator k = syns.iterator();
            
            while (k.hasNext()) {
                syn = (PatoDTO_SYN)k.next();
                ps = conn.prepareStatement("select id from pato.pato_syn where synonym like ?");
                ps.setString(1, syn.getSynonym());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into pato.pato_syn (id, synonym) values (?,?)");
                    ps.setInt(1, count_syn);
                    ps.setString(2, syn.getSynonym());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into pato.pato_syn_r (pato_id, pato_syn, attribute) values (?,?,?)");
                    ps.setInt(1, pato.getIdAsInt());
                    ps.setInt(2, count_syn);
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                    
                    count_syn++;
                } else {
                    ps = conn.prepareStatement("insert into pato.pato_syn_r (pato_id, pato_syn, attribute) values (?,?,?)");
                    ps.setInt(1, pato.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                }
            }
            
            //alt id(s)
            Collection alts = pato.getAltIds();
            Iterator l = alts.iterator();
            
            while (l.hasNext()) {
                alt = (PatoDTO_ALT)l.next();
                ps = conn.prepareStatement("select id from pato.pato_alt_id where alt_id like ?");
                ps.setString(1, alt.getAltId());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into pato.pato_alt_id (id, alt_id) values (?,?)");
                    ps.setInt(1, count_alt);
                    ps.setString(2, alt.getAltId());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into pato.pato_alt_id_r (pato_id, pato_alt_id) values (?,?)");
                    ps.setInt(1, pato.getIdAsInt());
                    ps.setInt(2, count_alt);
                    ps.execute();
                    
                    count_alt++;
                } else {
                    ps = conn.prepareStatement("insert into pato.pato_alt_id_r (pato_id, pato_alt_id) values (?,?)");
                    ps.setInt(1, pato.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //xref(s)
            Collection xrefs = pato.getXrefs();
            Iterator m = xrefs.iterator();
            
            while (m.hasNext()) {
                xref = (PatoDTO_XREF)m.next();
                ps = conn.prepareStatement("select id from pato.pato_xref where xref like ?");
                ps.setString(1, xref.getXref());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into pato.pato_xref (id, xref) values (?,?)");
                    ps.setInt(1, count_xref);
                    ps.setString(2, xref.getXref());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into pato.pato_xref_r (pato_id, xref_id) values (?,?)");
                    ps.setInt(1, pato.getIdAsInt());
                    ps.setInt(2, count_xref);
                    ps.execute();
                    
                    count_xref++;
                } else {
                    ps = conn.prepareStatement("insert into pato.pato_xref_r (pato_id, xref_id) values (?,?)");
                    ps.setInt(1, pato.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //subsets(s)
            Collection subsets = pato.getSubsets();
            Iterator n = subsets.iterator();
           
            while (n.hasNext()) {
                subset = (PatoDTO_SUBSET)n.next();
                ps = conn.prepareStatement("insert into pato.pato_subsets (pato_id, subset) values (?,?)");
                ps.setInt(1, pato.getIdAsInt());
                ps.setString(2, subset.getSubset());
                ps.execute();
            }
            
        } catch (SQLException se) {
            logger.error("storePATO: failed to perform.", se);
            throw new ApplicationException("storePATO: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    //</editor-fold>
    
    //ma methods
    //<editor-fold defaultstate="collapsed">
    public void loadMA() throws ApplicationException {
        try {
            File file = new File("C:/zouberakis_/_/pato/obo.files/adult_mouse_anatomy.obo");
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(file));
            
            String line = null;
            MaDTO ma = new MaDTO();
            
            //drop all FK constraints in schema MA
            dropconMA();
            //delete all data in schema MA
            cleanMA();
            
            while ((line = input.readLine()) != null){
                if(line.compareTo("[Term]")!=0){
                    ma.IdentifyLine(line);
                }else{
                    if(!ma.getId().equals("")){
                        storeMA(ma);
                    }
                    ma = null;
                    ma = new MaDTO();
                }
            }
            
            //reconstruct all FK constraints
            restoreconMA();
            
            //rebuild the tmp.pheno_paths_ table
            //pathsMP(1);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("loadMA: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void cleanMA() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("delete from ma.ma_term");
            ps.execute();
        
            ps = conn.prepareStatement("delete from ma.ma_alt_id");
            ps.execute();
            
            ps = conn.prepareStatement("delete from ma.ma_alt_id_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from ma.ma_is_a");
            ps.execute();
        
            ps = conn.prepareStatement("delete from ma.ma_syn");
            ps.execute();
            
            ps = conn.prepareStatement("delete from ma.ma_syn_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from ma.ma_xref");
            ps.execute();
            
            ps = conn.prepareStatement("delete from ma.ma_xref_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from ma.ma_relationships");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanMA: failed to perform.", se);
            throw new ApplicationException("cleanMA: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void dropconMA() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE ma.ma_alt_id_r DROP CONSTRAINT ma_alt_id_r_alt_id_fk;" +
                    "ALTER TABLE ma.ma_alt_id_r DROP CONSTRAINT ma_alt_id_r_ma_fk;" +
                    "ALTER TABLE ma.ma_is_a DROP CONSTRAINT ma_is_a_fk_a;" +
                    "ALTER TABLE ma.ma_is_a DROP CONSTRAINT ma_is_a_fk_b;" +
                    "ALTER TABLE ma.ma_relationships DROP CONSTRAINT ma_relationships_a_fk;" +
                    "ALTER TABLE ma.ma_relationships DROP CONSTRAINT ma_relationships_b_fk;" +
                    "ALTER TABLE ma.ma_syn_r DROP CONSTRAINT ma_syn_r_ma_fk;" +
                    "ALTER TABLE ma.ma_syn_r DROP CONSTRAINT ma_syn_r_syn_fk;" +
                    "ALTER TABLE ma.ma_xref_r DROP CONSTRAINT ma_xref_r_ma_fk;" +
                    "ALTER TABLE ma.ma_xref_r DROP CONSTRAINT ma_xref_r_xref_fk;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("dropconMA: failed to drop constraints for schema MA.", se);
            throw new ApplicationException("dropconMA: failed to drop constraints for schema MA.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void restoreconMA() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE ma.ma_alt_id_r ADD " +
                    "CONSTRAINT ma_alt_id_r_alt_id_fk FOREIGN KEY (ma_alt_id) " +
                    "REFERENCES ma.ma_alt_id (id) MATCH SIMPLE ON UPDATE NO " +
                    "ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE ma.ma_alt_id_r ADD CONSTRAINT " +
                    "ma_alt_id_r_ma_fk FOREIGN KEY (ma_id) REFERENCES " +
                    "ma.ma_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE ma.ma_is_a ADD CONSTRAINT ma_is_a_fk_a " +
                    "FOREIGN KEY (id_a) REFERENCES ma.ma_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE ma.ma_is_a ADD CONSTRAINT ma_is_a_fk_b " +
                    "FOREIGN KEY (id_b) REFERENCES ma.ma_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE ma.ma_relationships ADD CONSTRAINT " +
                    "ma_relationships_a_fk FOREIGN KEY (id_a) REFERENCES " +
                    "ma.ma_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON" +
                    " DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE ma.ma_relationships ADD CONSTRAINT " +
                    "ma_relationships_b_fk FOREIGN KEY (id_b) REFERENCES " +
                    "ma.ma_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE ma.ma_syn_r ADD CONSTRAINT ma_syn_r_ma_fk " +
                    "FOREIGN KEY (ma_id) REFERENCES ma.ma_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE ma.ma_syn_r ADD CONSTRAINT ma_syn_r_syn_fk " +
                    "FOREIGN KEY (ma_syn) REFERENCES ma.ma_syn (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE ma.ma_xref_r ADD CONSTRAINT ma_xref_r_ma_fk " +
                    "FOREIGN KEY (ma_id) REFERENCES ma.ma_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE ma.ma_xref_r ADD CONSTRAINT " +
                    "ma_xref_r_xref_fk FOREIGN KEY (xref_id) REFERENCES " +
                    "ma.ma_xref (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("restoreconMA: failed to restore constraints for schema MA.", se);
            throw new ApplicationException("restoreconMA: failed to restore constraints for schema MA.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void storeMA(MaDTO ma) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        MaDTO_ISA isa = null;
        MaDTO_SYN syn = null;
        MaDTO_ALT alt = null;
        MaDTO_XREF xref = null;
        MaDTO_REL rel = null;
        
        try {
            
            //pheno ontology parameters
            ps = conn.prepareStatement("insert into ma.ma_term (id, name, def, def_ref, is_obsolete, comm) values (?,?,?,?,?,?)");
            ps.setInt(1, ma.getIdAsInt());
            ps.setString(2, ma.getName());
            ps.setString(3, ma.getDef());
            ps.setString(4, ma.getDefRef());
            ps.setInt(5, ma.getIsObsoleteAsInt());
            ps.setString(6, ma.getComm());
            ps.execute();
            
            //is a(s)
            Collection is_as = ma.getIsAs();
            Iterator i = is_as.iterator();
           
            while (i.hasNext()) {
                isa = (MaDTO_ISA)i.next();
                ps = conn.prepareStatement("insert into ma.ma_is_a (id_b, id_a) values (?,?)");
                ps.setInt(1, ma.getIdAsInt());
                ps.setInt(2, isa.getIsAInt());
                ps.execute();
            }
            
            //synonym(s)
            Collection syns = ma.getSynonyms();
            Iterator k = syns.iterator();
            
            while (k.hasNext()) {
                syn = (MaDTO_SYN)k.next();
                ps = conn.prepareStatement("select id from ma.ma_syn where synonym like ?");
                ps.setString(1, syn.getSynonym());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into ma.ma_syn (id, synonym) values (?,?)");
                    ps.setInt(1, count_syn);
                    ps.setString(2, syn.getSynonym());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into ma.ma_syn_r (ma_id, ma_syn, attribute) values (?,?,?)");
                    ps.setInt(1, ma.getIdAsInt());
                    ps.setInt(2, count_syn);
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                    
                    count_syn++;
                } else {
                    ps = conn.prepareStatement("insert into ma.ma_syn_r (ma_id, ma_syn, attribute) values (?,?,?)");
                    ps.setInt(1, ma.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                }
            }
            
            //alt id(s)
            Collection alts = ma.getAltIds();
            Iterator l = alts.iterator();
            
            while (l.hasNext()) {
                alt = (MaDTO_ALT)l.next();
                ps = conn.prepareStatement("select id from ma.ma_alt_id where alt_id like ?");
                ps.setString(1, alt.getAltId());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into ma.ma_alt_id (id, alt_id) values (?,?)");
                    ps.setInt(1, count_alt);
                    ps.setString(2, alt.getAltId());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into ma.ma_alt_id_r (ma_id, ma_alt_id) values (?,?)");
                    ps.setInt(1, ma.getIdAsInt());
                    ps.setInt(2, count_alt);
                    ps.execute();
                    
                    count_alt++;
                } else {
                    ps = conn.prepareStatement("insert into ma.ma_alt_id_r (ma_id, ma_alt_id) values (?,?)");
                    ps.setInt(1, ma.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //xref(s)
            Collection xrefs = ma.getXrefs();
            Iterator m = xrefs.iterator();
            
            while (m.hasNext()) {
                xref = (MaDTO_XREF)m.next();
                ps = conn.prepareStatement("select id from ma.ma_xref where xref like ?");
                ps.setString(1, xref.getXref());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into ma.ma_xref (id, xref) values (?,?)");
                    ps.setInt(1, count_xref);
                    ps.setString(2, xref.getXref());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into ma.ma_xref_r (ma_id, xref_id) values (?,?)");
                    ps.setInt(1, ma.getIdAsInt());
                    ps.setInt(2, count_xref);
                    ps.execute();
                    
                    count_xref++;
                } else {
                    ps = conn.prepareStatement("insert into ma.ma_xref_r (ma_id, xref_id) values (?,?)");
                    ps.setInt(1, ma.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //relationship(s)
            Collection rels = ma.getRelationships();
            Iterator n = rels.iterator();
           
            while (n.hasNext()) {
                rel = (MaDTO_REL)n.next();
                ps = conn.prepareStatement("insert into ma.ma_relationships (id_a, relation, id_b) values (?,?,?)");
                ps.setInt(1, ma.getIdAsInt());
                ps.setString(2, rel.getRelation());
                ps.setInt(3, rel.getIdB());
                ps.execute();
            }
            
        } catch (SQLException se) {
            logger.error("storeMA: failed to perform.", se);
            throw new ApplicationException("storeMA: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    //</editor-fold>
    
    //go methods
    //<editor-fold defaultstate="collapsed">
    public void loadGO() throws ApplicationException {
        try {
            File file = new File("C:/zouberakis/projects/pato/docs/obo.files/biological_process.obo");
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(file));
            
            String line = null;
            GoDTO go = new GoDTO();
            
            //drop all FK constraints in schema GO
            dropconGO();
            //delete all data in schema GO
            cleanGO();
            
            while ((line = input.readLine()) != null){
                if(line.compareTo("[Term]")!=0 && line.compareTo("[Typedef]")!=0){
                    go.IdentifyLine(line);
                }else{
                    if(!go.getId().equals("") || line.compareTo("[Typedef]")==0){
                        storeGO(go);
                    }
                    go = null;
                    go = new GoDTO();
                }
            }
            
            //reconstruct all FK constraints
            restoreconGO();
            
            //rebuild the tmp.pheno_paths_ table
            //pathsMP(1);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("loadGO: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void cleanGO() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("delete from go.go_term");
            ps.execute();
        
            ps = conn.prepareStatement("delete from go.go_alt_id");
            ps.execute();
            
            ps = conn.prepareStatement("delete from go.go_alt_id_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from go.go_is_a");
            ps.execute();
        
            ps = conn.prepareStatement("delete from go.go_syn");
            ps.execute();
            
            ps = conn.prepareStatement("delete from go.go_syn_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from go.go_xref");
            ps.execute();
            
            ps = conn.prepareStatement("delete from go.go_xref_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from go.go_relationships");
            ps.execute();
            
            ps = conn.prepareStatement("delete from go.go_subsets");
            ps.execute();
            
            ps = conn.prepareStatement("delete from go.go_consider");
            ps.execute();
            
            ps = conn.prepareStatement("delete from go.go_replaced_by");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanGO: failed to perform.", se);
            throw new ApplicationException("cleanGO: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void dropconGO() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE go.go_alt_id_r DROP CONSTRAINT go_alt_id_r_alt_id_fk;" +
                    "ALTER TABLE go.go_alt_id_r DROP CONSTRAINT go_alt_id_r_go_fk;" +
                    "ALTER TABLE go.go_is_a DROP CONSTRAINT go_is_a_fk_a;" +
                    "ALTER TABLE go.go_is_a DROP CONSTRAINT go_is_a_fk_b;" +
                    "ALTER TABLE go.go_relationships DROP CONSTRAINT go_relationships_a_fk;" +
                    "ALTER TABLE go.go_relationships DROP CONSTRAINT go_relationships_b_fk;" +
                    "ALTER TABLE go.go_syn_r DROP CONSTRAINT go_syn_r_go_fk;" +
                    "ALTER TABLE go.go_syn_r DROP CONSTRAINT go_syn_r_syn_fk;" +
                    "ALTER TABLE go.go_xref_r DROP CONSTRAINT go_xref_r_go_fk;" +
                    "ALTER TABLE go.go_xref_r DROP CONSTRAINT go_xref_r_xref_fk;" +
                    "ALTER TABLE go.go_consider DROP CONSTRAINT go_consider_fk_a;" +
                    "ALTER TABLE go.go_consider DROP CONSTRAINT go_consider_fk_b;" +
                    "ALTER TABLE go.go_replaced_by DROP CONSTRAINT go_replaced_by_fk_a;" +
                    "ALTER TABLE go.go_replaced_by DROP CONSTRAINT go_replaced_by_fk_b;" +
                    "ALTER TABLE go.go_subsets DROP CONSTRAINT go_subsets_fk;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("dropconGO: failed to drop constraints for schema GO.", se);
            throw new ApplicationException("dropconGO: failed to drop constraints for schema GO.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void restoreconGO() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE go.go_alt_id_r ADD " +
                    "CONSTRAINT go_alt_id_r_alt_id_fk FOREIGN KEY (go_alt_id) " +
                    "REFERENCES go.go_alt_id (id) MATCH SIMPLE ON UPDATE NO " +
                    "ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_alt_id_r ADD CONSTRAINT " +
                    "go_alt_id_r_go_fk FOREIGN KEY (go_id) REFERENCES " +
                    "go.go_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_is_a ADD CONSTRAINT go_is_a_fk_a " +
                    "FOREIGN KEY (id_a) REFERENCES go.go_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_is_a ADD CONSTRAINT go_is_a_fk_b " +
                    "FOREIGN KEY (id_b) REFERENCES go.go_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_relationships ADD CONSTRAINT " +
                    "go_relationships_a_fk FOREIGN KEY (id_a) REFERENCES " +
                    "go.go_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON" +
                    " DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_relationships ADD CONSTRAINT " +
                    "go_relationships_b_fk FOREIGN KEY (id_b) REFERENCES " +
                    "go.go_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_syn_r ADD CONSTRAINT go_syn_r_go_fk " +
                    "FOREIGN KEY (go_id) REFERENCES go.go_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_syn_r ADD CONSTRAINT go_syn_r_syn_fk " +
                    "FOREIGN KEY (go_syn) REFERENCES go.go_syn (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_xref_r ADD CONSTRAINT go_xref_r_go_fk " +
                    "FOREIGN KEY (go_id) REFERENCES go.go_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_xref_r ADD CONSTRAINT " +
                    "go_xref_r_xref_fk FOREIGN KEY (xref_id) REFERENCES " +
                    "go.go_xref (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_subsets ADD CONSTRAINT " +
                    "go_subsets_fk FOREIGN KEY (go_id) REFERENCES " +
                    "go.go_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_consider ADD CONSTRAINT " +
                    "go_consider_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "go.go_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_consider ADD CONSTRAINT " +
                    "go_consider_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "go.go_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_replaced_by ADD CONSTRAINT " +
                    "go_replaced_by_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "go.go_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE go.go_replaced_by ADD CONSTRAINT " +
                    "go_replaced_by_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "go.go_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("restoreconMA: failed to restore constraints for schema MA.", se);
            throw new ApplicationException("restoreconMA: failed to restore constraints for schema MA.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void storeGO(GoDTO go) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_tmp = null;
        GoDTO_ISA isa = null;
        GoDTO_SYN syn = null;
        GoDTO_ALT alt = null;
        GoDTO_XREF xref = null;
        GoDTO_REL rel = null;
        GoDTO_SUBSET sub = null;
        GoDTO_REP rep = null;
        GoDTO_CON con = null;
        
        
        try {
            
            //pheno ontology parameters
            ps = conn.prepareStatement("insert into go.go_term (id, name, def, def_ref, is_obsolete, comm, namespace) values (?,?,?,?,?,?,?)");
            ps.setInt(1, go.getIdAsInt());
            ps.setString(2, go.getName());
            ps.setString(3, go.getDef());
            ps.setString(4, go.getDefRef());
            ps.setInt(5, go.getIsObsoleteAsInt());
            ps.setString(6, go.getComm());
            ps.setString(7, go.getNamespace());
            ps.execute();
            
            //is a(s)
            Collection is_as = go.getIsAs();
            Iterator i = is_as.iterator();
           
            while (i.hasNext()) {
                isa = (GoDTO_ISA)i.next();
                ps = conn.prepareStatement("insert into go.go_is_a (id_b, id_a) values (?,?)");
                ps.setInt(1, go.getIdAsInt());
                ps.setInt(2, isa.getIsAInt());
                ps.execute();
            }
            
            //synonym(s)
            Collection syns = go.getSynonyms();
            Iterator k = syns.iterator();
            
            while (k.hasNext()) {
                syn = (GoDTO_SYN)k.next();
                
                //System.out.println(">>>working on go term: "+go.getIdAsInt()+" and synonym: "+syn.getSynonym());
                
                ps = conn.prepareStatement("select id from go.go_syn where lower(synonym) like ?");
                ps.setString(1, syn.getSynonym().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into go.go_syn (id, synonym) values (?,?)");
                    ps.setInt(1, count_syn);
                    ps.setString(2, syn.getSynonym());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into go.go_syn_r (go_id, go_syn, attribute) values (?,?,?)");
                    ps.setInt(1, go.getIdAsInt());
                    ps.setInt(2, count_syn);
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                    
                    count_syn++;
                } else {
                    
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select go_id from go.go_syn_r where go_id = ? and go_syn = ? and lower(attribute) like ?");
                    ps.setInt(1, go.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.setString(3, syn.getAttribute());
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into go.go_syn_r (go_id, go_syn, attribute) values (?,?,?)");
                        ps.setInt(1, go.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.setString(3, syn.getAttribute());
                        ps.execute();
                    }
                    
                }
            }
            
            //alt id(s)
            Collection alts = go.getAltIds();
            Iterator l = alts.iterator();
            
            while (l.hasNext()) {
                alt = (GoDTO_ALT)l.next();
                ps = conn.prepareStatement("select id from go.go_alt_id where lower(alt_id) like ?");
                ps.setString(1, alt.getAltId().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into go.go_alt_id (id, alt_id) values (?,?)");
                    ps.setInt(1, count_alt);
                    ps.setString(2, alt.getAltId());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into go.go_alt_id_r (go_id, go_alt_id) values (?,?)");
                    ps.setInt(1, go.getIdAsInt());
                    ps.setInt(2, count_alt);
                    ps.execute();
                    
                    count_alt++;
                } else {
                    ps = conn.prepareStatement("insert into go.go_alt_id_r (go_id, go_alt_id) values (?,?)");
                    ps.setInt(1, go.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //xref(s)
            Collection xrefs = go.getXrefs();
            Iterator m = xrefs.iterator();
            
            while (m.hasNext()) {
                xref = (GoDTO_XREF)m.next();
                ps = conn.prepareStatement("select id from go.go_xref where lower(xref) like ?");
                ps.setString(1, xref.getXref().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into go.go_xref (id, xref) values (?,?)");
                    ps.setInt(1, count_xref);
                    ps.setString(2, xref.getXref());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into go.go_xref_r (go_id, xref_id) values (?,?)");
                    ps.setInt(1, go.getIdAsInt());
                    ps.setInt(2, count_xref);
                    ps.execute();
                    
                    count_xref++;
                } else {
                    ps = conn.prepareStatement("insert into go.go_xref_r (go_id, xref_id) values (?,?)");
                    ps.setInt(1, go.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //relationship(s)
            Collection rels = go.getRelationships();
            Iterator n = rels.iterator();
           
            while (n.hasNext()) {
                rel = (GoDTO_REL)n.next();
                ps = conn.prepareStatement("insert into go.go_relationships (id_a, relation, id_b) values (?,?,?)");
                ps.setInt(1, go.getIdAsInt());
                ps.setString(2, rel.getRelation());
                ps.setInt(3, rel.getIdB());
                ps.execute();
            }
            
            //subset(s)
            Collection subsets = go.getSubsets();
            Iterator o = subsets.iterator();
           
            while (o.hasNext()) {
                sub = (GoDTO_SUBSET)o.next();
                ps = conn.prepareStatement("insert into go.go_subsets (go_id, subset) values (?,?)");
                ps.setInt(1, go.getIdAsInt());
                ps.setString(2, sub.getSubset());
                ps.execute();
            }
            
            //consider(s)
            Collection cons = go.getConsiders();
            Iterator p = cons.iterator();
           
            while (p.hasNext()) {
                con = (GoDTO_CON)p.next();
                ps = conn.prepareStatement("insert into go.go_consider (id_b, id_a) values (?,?)");
                ps.setInt(1, go.getIdAsInt());
                ps.setInt(2, con.getConsiderInt());
                ps.execute();
            }
            
            //replaced_by(s)
            Collection reps = go.getReplaced_bys();
            Iterator q = reps.iterator();
           
            while (q.hasNext()) {
                rep = (GoDTO_REP)q.next();
                ps = conn.prepareStatement("insert into go.go_replaced_by (id_b, id_a) values (?,?)");
                ps.setInt(1, go.getIdAsInt());
                ps.setInt(2, rep.getReplacedInt());
                ps.execute();
            }
            
        } catch (SQLException se) {
            logger.error("storeGO: failed to perform.", se);
            throw new ApplicationException("storeGO: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    //</editor-fold>
    
    //cell methods
    //<editor-fold defaultstate="collapsed">
    public void loadCELL() throws ApplicationException {
        try {
            File file = new File("C:/zouberakis/projects/pato/docs/obo.files/cell.obo");
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(file));
            
            String line = null;
            CellDTO cell = new CellDTO();
            
            //drop all FK constraints in schema GO
            dropconCELL();
            //delete all data in schema GO
            cleanCELL();
            
            while ((line = input.readLine()) != null){
                if(line.compareTo("[Term]")!=0 && line.compareTo("[Typedef]")!=0){
                    cell.IdentifyLine(line);
                }else{
                    if(!cell.getId().equals("") || line.compareTo("[Typedef]")==0){
                        storeCELL(cell);
                    }
                    cell = null;
                    cell = new CellDTO();
                }
            }
            
            //reconstruct all FK constraints
            restoreconCELL();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("loadCELL: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void cleanCELL() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("delete from cl.cl_term");
            ps.execute();
        
            ps = conn.prepareStatement("delete from cl.cl_alt_id");
            ps.execute();
            
            ps = conn.prepareStatement("delete from cl.cl_alt_id_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from cl.cl_is_a");
            ps.execute();
        
            ps = conn.prepareStatement("delete from cl.cl_syn");
            ps.execute();
            
            ps = conn.prepareStatement("delete from cl.cl_syn_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from cl.cl_xref");
            ps.execute();
            
            ps = conn.prepareStatement("delete from cl.cl_xref_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from cl.cl_relationships");
            ps.execute();
            
            ps = conn.prepareStatement("delete from cl.cl_subsets");
            ps.execute();
            
            ps = conn.prepareStatement("delete from cl.cl_consider");
            ps.execute();
            
            ps = conn.prepareStatement("delete from cl.cl_replaced_by");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanCELL: failed to perform.", se);
            throw new ApplicationException("cleanCELL: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void dropconCELL() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE cl.cl_alt_id_r DROP CONSTRAINT cl_alt_id_r_alt_id_fk;" +
                    "ALTER TABLE cl.cl_alt_id_r DROP CONSTRAINT cl_alt_id_r_cl_fk;" +
                    "ALTER TABLE cl.cl_is_a DROP CONSTRAINT cl_is_a_fk_a;" +
                    "ALTER TABLE cl.cl_is_a DROP CONSTRAINT cl_is_a_fk_b;" +
                    "ALTER TABLE cl.cl_relationships DROP CONSTRAINT cl_relationships_a_fk;" +
                    "ALTER TABLE cl.cl_relationships DROP CONSTRAINT cl_relationships_b_fk;" +
                    "ALTER TABLE cl.cl_syn_r DROP CONSTRAINT cl_syn_r_cl_fk;" +
                    "ALTER TABLE cl.cl_syn_r DROP CONSTRAINT cl_syn_r_syn_fk;" +
                    "ALTER TABLE cl.cl_xref_r DROP CONSTRAINT cl_xref_r_cl_fk;" +
                    "ALTER TABLE cl.cl_xref_r DROP CONSTRAINT cl_xref_r_xref_fk;" +
                    "ALTER TABLE cl.cl_consider DROP CONSTRAINT cl_consider_fk_a;" +
                    "ALTER TABLE cl.cl_consider DROP CONSTRAINT cl_consider_fk_b;" +
                    "ALTER TABLE cl.cl_replaced_by DROP CONSTRAINT cl_replaced_by_fk_a;" +
                    "ALTER TABLE cl.cl_replaced_by DROP CONSTRAINT cl_replaced_by_fk_b;" +
                    "ALTER TABLE cl.cl_subsets DROP CONSTRAINT cl_subsets_fk;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("dropconCELL: failed to drop constraints for schema CELL.", se);
            throw new ApplicationException("dropconCELL: failed to drop constraints for schema CELL.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void restoreconCELL() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE cl.cl_alt_id_r ADD " +
                    "CONSTRAINT cl_alt_id_r_alt_id_fk FOREIGN KEY (cl_alt_id) " +
                    "REFERENCES cl.cl_alt_id (id) MATCH SIMPLE ON UPDATE NO " +
                    "ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_alt_id_r ADD CONSTRAINT " +
                    "cl_alt_id_r_cl_fk FOREIGN KEY (cl_id) REFERENCES " +
                    "cl.cl_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_is_a ADD CONSTRAINT cl_is_a_fk_a " +
                    "FOREIGN KEY (id_a) REFERENCES cl.cl_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_is_a ADD CONSTRAINT cl_is_a_fk_b " +
                    "FOREIGN KEY (id_b) REFERENCES cl.cl_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_relationships ADD CONSTRAINT " +
                    "cl_relationships_a_fk FOREIGN KEY (id_a) REFERENCES " +
                    "cl.cl_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON" +
                    " DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_relationships ADD CONSTRAINT " +
                    "cl_relationships_b_fk FOREIGN KEY (id_b) REFERENCES " +
                    "cl.cl_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_syn_r ADD CONSTRAINT cl_syn_r_cl_fk " +
                    "FOREIGN KEY (cl_id) REFERENCES cl.cl_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_syn_r ADD CONSTRAINT cl_syn_r_syn_fk " +
                    "FOREIGN KEY (cl_syn) REFERENCES cl.cl_syn (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_xref_r ADD CONSTRAINT cl_xref_r_cl_fk " +
                    "FOREIGN KEY (cl_id) REFERENCES cl.cl_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_xref_r ADD CONSTRAINT " +
                    "cl_xref_r_xref_fk FOREIGN KEY (xref_id) REFERENCES " +
                    "cl.cl_xref (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_subsets ADD CONSTRAINT " +
                    "cl_subsets_fk FOREIGN KEY (cl_id) REFERENCES " +
                    "cl.cl_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_consider ADD CONSTRAINT " +
                    "cl_consider_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "cl.cl_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_consider ADD CONSTRAINT " +
                    "cl_consider_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "cl.cl_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_replaced_by ADD CONSTRAINT " +
                    "cl_replaced_by_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "cl.cl_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE cl.cl_replaced_by ADD CONSTRAINT " +
                    "cl_replaced_by_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "cl.cl_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("restoreconCELL: failed to restore constraints for schema CELL.", se);
            throw new ApplicationException("restoreconCELL: failed to restore constraints for schema CELL.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void storeCELL(CellDTO cl) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_tmp = null;
        CellDTO_ISA isa = null;
        CellDTO_SYN syn = null;
        CellDTO_ALT alt = null;
        CellDTO_XREF xref = null;
        CellDTO_REL rel = null;
        CellDTO_SUBSET sub = null;
        CellDTO_REP rep = null;
        CellDTO_CON con = null;
        
        
        try {
            
            //pheno ontology parameters
            //ps = conn.prepareStatement("insert into cl.cl_term (id, name, def, def_ref, is_obsolete, comm, namespace) values (?,?,?,?,?,?,?)");
            ps = conn.prepareStatement("insert into cl.cl_term (id, name, def, def_ref, is_obsolete, comm) values (?,?,?,?,?,?)");
            ps.setInt(1, cl.getIdAsInt());
            ps.setString(2, cl.getName());
            ps.setString(3, cl.getDef());
            ps.setString(4, cl.getDefRef());
            ps.setInt(5, cl.getIsObsoleteAsInt());
            ps.setString(6, cl.getComm());
            //ps.setString(7, cl.getNamespace());
            ps.execute();
            
            //is a(s)
            Collection is_as = cl.getIsAs();
            Iterator i = is_as.iterator();
           
            while (i.hasNext()) {
                isa = (CellDTO_ISA)i.next();
                ps = conn.prepareStatement("insert into cl.cl_is_a (id_b, id_a) values (?,?)");
                ps.setInt(1, cl.getIdAsInt());
                ps.setInt(2, isa.getIsAInt());
                ps.execute();
            }
            
            //synonym(s)
            Collection syns = cl.getSynonyms();
            Iterator k = syns.iterator();
            
            while (k.hasNext()) {
                syn = (CellDTO_SYN)k.next();
                
                //System.out.println(">>>working on cl term: "+cl.getIdAsInt()+" and synonym: "+syn.getSynonym());
                
                ps = conn.prepareStatement("select id from cl.cl_syn where lower(synonym) like ?");
                ps.setString(1, syn.getSynonym().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into cl.cl_syn (id, synonym) values (?,?)");
                    ps.setInt(1, count_syn);
                    ps.setString(2, syn.getSynonym());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into cl.cl_syn_r (cl_id, cl_syn, attribute) values (?,?,?)");
                    ps.setInt(1, cl.getIdAsInt());
                    ps.setInt(2, count_syn);
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                    
                    count_syn++;
                } else {
                    
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select cl_id from cl.cl_syn_r where cl_id = ? and cl_syn = ? and lower(attribute) like ?");
                    ps.setInt(1, cl.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.setString(3, syn.getAttribute());
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into cl.cl_syn_r (cl_id, cl_syn, attribute) values (?,?,?)");
                        ps.setInt(1, cl.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.setString(3, syn.getAttribute());
                        ps.execute();
                    }
                    
                }
            }
            
            //alt id(s)
            Collection alts = cl.getAltIds();
            Iterator l = alts.iterator();
            
            while (l.hasNext()) {
                alt = (CellDTO_ALT)l.next();
                ps = conn.prepareStatement("select id from cl.cl_alt_id where lower(alt_id) like ?");
                ps.setString(1, alt.getAltId().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into cl.cl_alt_id (id, alt_id) values (?,?)");
                    ps.setInt(1, count_alt);
                    ps.setString(2, alt.getAltId());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into cl.cl_alt_id_r (cl_id, cl_alt_id) values (?,?)");
                    ps.setInt(1, cl.getIdAsInt());
                    ps.setInt(2, count_alt);
                    ps.execute();
                    
                    count_alt++;
                } else {
                    ps = conn.prepareStatement("insert into cl.cl_alt_id_r (cl_id, cl_alt_id) values (?,?)");
                    ps.setInt(1, cl.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //xref(s)
            Collection xrefs = cl.getXrefs();
            Iterator m = xrefs.iterator();
            
            while (m.hasNext()) {
                xref = (CellDTO_XREF)m.next();
                ps = conn.prepareStatement("select id from cl.cl_xref where lower(xref) like ?");
                ps.setString(1, xref.getXref().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into cl.cl_xref (id, xref) values (?,?)");
                    ps.setInt(1, count_xref);
                    ps.setString(2, xref.getXref());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into cl.cl_xref_r (cl_id, xref_id) values (?,?)");
                    ps.setInt(1, cl.getIdAsInt());
                    ps.setInt(2, count_xref);
                    ps.execute();
                    
                    count_xref++;
                } else {
                    ps = conn.prepareStatement("insert into cl.cl_xref_r (cl_id, xref_id) values (?,?)");
                    ps.setInt(1, cl.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //relationship(s)
            Collection rels = cl.getRelationships();
            Iterator n = rels.iterator();
           
            while (n.hasNext()) {
                rel = (CellDTO_REL)n.next();
                ps = conn.prepareStatement("insert into cl.cl_relationships (id_a, relation, id_b) values (?,?,?)");
                ps.setInt(1, cl.getIdAsInt());
                ps.setString(2, rel.getRelation());
                ps.setInt(3, rel.getIdB());
                ps.execute();
            }
            
            //subset(s)
            Collection subsets = cl.getSubsets();
            Iterator o = subsets.iterator();
           
            while (o.hasNext()) {
                sub = (CellDTO_SUBSET)o.next();
                ps = conn.prepareStatement("insert into cl.cl_subsets (cl_id, subset) values (?,?)");
                ps.setInt(1, cl.getIdAsInt());
                ps.setString(2, sub.getSubset());
                ps.execute();
            }
            
            //consider(s)
            Collection cons = cl.getConsiders();
            Iterator p = cons.iterator();
           
            while (p.hasNext()) {
                con = (CellDTO_CON)p.next();
                ps = conn.prepareStatement("insert into cl.cl_consider (id_b, id_a) values (?,?)");
                ps.setInt(1, cl.getIdAsInt());
                ps.setInt(2, con.getConsiderInt());
                ps.execute();
            }
            
            //replaced_by(s)
            Collection reps = cl.getReplaced_bys();
            Iterator q = reps.iterator();
           
            while (q.hasNext()) {
                rep = (CellDTO_REP)q.next();
                ps = conn.prepareStatement("insert into cl.cl_replaced_by (id_b, id_a) values (?,?)");
                ps.setInt(1, cl.getIdAsInt());
                ps.setInt(2, rep.getReplacedInt());
                ps.execute();
            }
            
        } catch (SQLException se) {
            logger.error("storeCELL: failed to perform.", se);
            throw new ApplicationException("storeCELL: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    //</editor-fold>
    
    //chebi methods
    //<editor-fold defaultstate="collapsed">
    public void loadCHEBI() throws ApplicationException {
        try {
            File file = new File("C:/zouberakis/projects/pato/docs/obo.files/chebi.obo");
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(file));
            
            String line = null;
            ChebiDTO chebi = new ChebiDTO();
            
            //drop all FK constraints in schema CHEBI
            dropconCHEBI();
            //delete all data in schema CHEBI
            cleanCHEBI();
            
            while ((line = input.readLine()) != null){
                
                if(line.compareToIgnoreCase("[Typedef]")==0){
                    if(!chebi.getId().equals("")){
                        storeCHEBI(chebi);
                    }
                    break;
                }
                
                if(line.compareTo("[Term]")!=0){
                    chebi.IdentifyLine(line);
                }else{
                    if(!chebi.getId().equals("")){
                        storeCHEBI(chebi);
                    }
                    chebi = null;
                    chebi = new ChebiDTO();
                }
            }
            
            //reconstruct all FK constraints
            restoreconCHEBI();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("loadCHEBI: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void cleanCHEBI() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("delete from chebi.chebi_term");
            ps.execute();
        
            ps = conn.prepareStatement("delete from chebi.chebi_alt_id");
            ps.execute();
            
            ps = conn.prepareStatement("delete from chebi.chebi_alt_id_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from chebi.chebi_is_a");
            ps.execute();
        
            ps = conn.prepareStatement("delete from chebi.chebi_syn");
            ps.execute();
            
            ps = conn.prepareStatement("delete from chebi.chebi_syn_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from chebi.chebi_xref");
            ps.execute();
            
            ps = conn.prepareStatement("delete from chebi.chebi_xref_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from chebi.chebi_relationships");
            ps.execute();
            
            ps = conn.prepareStatement("delete from chebi.chebi_subsets");
            ps.execute();
            
            ps = conn.prepareStatement("delete from chebi.chebi_consider");
            ps.execute();
            
            ps = conn.prepareStatement("delete from chebi.chebi_replaced_by");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanCHEBI: failed to perform.", se);
            throw new ApplicationException("cleanCHEBI: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void dropconCHEBI() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE chebi.chebi_alt_id_r DROP CONSTRAINT chebi_alt_id_r_alt_id_fk;" +
                    "ALTER TABLE chebi.chebi_alt_id_r DROP CONSTRAINT chebi_alt_id_r_chebi_fk;" +
                    "ALTER TABLE chebi.chebi_is_a DROP CONSTRAINT chebi_is_a_fk_a;" +
                    "ALTER TABLE chebi.chebi_is_a DROP CONSTRAINT chebi_is_a_fk_b;" +
                    "ALTER TABLE chebi.chebi_relationships DROP CONSTRAINT chebi_relationships_a_fk;" +
                    "ALTER TABLE chebi.chebi_relationships DROP CONSTRAINT chebi_relationships_b_fk;" +
                    "ALTER TABLE chebi.chebi_syn_r DROP CONSTRAINT chebi_syn_r_chebi_fk;" +
                    "ALTER TABLE chebi.chebi_syn_r DROP CONSTRAINT chebi_syn_r_syn_fk;" +
                    "ALTER TABLE chebi.chebi_xref_r DROP CONSTRAINT chebi_xref_r_chebi_fk;" +
                    "ALTER TABLE chebi.chebi_xref_r DROP CONSTRAINT chebi_xref_r_xref_fk;" +
                    "ALTER TABLE chebi.chebi_consider DROP CONSTRAINT chebi_consider_fk_a;" +
                    "ALTER TABLE chebi.chebi_consider DROP CONSTRAINT chebi_consider_fk_b;" +
                    "ALTER TABLE chebi.chebi_replaced_by DROP CONSTRAINT chebi_replaced_by_fk_a;" +
                    "ALTER TABLE chebi.chebi_replaced_by DROP CONSTRAINT chebi_replaced_by_fk_b;" +
                    "ALTER TABLE chebi.chebi_subsets DROP CONSTRAINT chebi_subsets_fk;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("dropconCHEBI: failed to drop constraints for schema CHEBI.", se);
            throw new ApplicationException("dropconCHEBI: failed to drop constraints for schema CHEBI.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void restoreconCHEBI() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE chebi.chebi_alt_id_r ADD " +
                    "CONSTRAINT chebi_alt_id_r_alt_id_fk FOREIGN KEY (chebi_alt_id) " +
                    "REFERENCES chebi.chebi_alt_id (id) MATCH SIMPLE ON UPDATE NO " +
                    "ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_alt_id_r ADD CONSTRAINT " +
                    "chebi_alt_id_r_chebi_fk FOREIGN KEY (chebi_id) REFERENCES " +
                    "chebi.chebi_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_is_a ADD CONSTRAINT chebi_is_a_fk_a " +
                    "FOREIGN KEY (id_a) REFERENCES chebi.chebi_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_is_a ADD CONSTRAINT chebi_is_a_fk_b " +
                    "FOREIGN KEY (id_b) REFERENCES chebi.chebi_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_relationships ADD CONSTRAINT " +
                    "chebi_relationships_a_fk FOREIGN KEY (id_a) REFERENCES " +
                    "chebi.chebi_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON" +
                    " DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_relationships ADD CONSTRAINT " +
                    "chebi_relationships_b_fk FOREIGN KEY (id_b) REFERENCES " +
                    "chebi.chebi_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_syn_r ADD CONSTRAINT chebi_syn_r_chebi_fk " +
                    "FOREIGN KEY (chebi_id) REFERENCES chebi.chebi_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_syn_r ADD CONSTRAINT chebi_syn_r_syn_fk " +
                    "FOREIGN KEY (chebi_syn) REFERENCES chebi.chebi_syn (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_xref_r ADD CONSTRAINT chebi_xref_r_chebi_fk " +
                    "FOREIGN KEY (chebi_id) REFERENCES chebi.chebi_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_xref_r ADD CONSTRAINT " +
                    "chebi_xref_r_xref_fk FOREIGN KEY (xref_id) REFERENCES " +
                    "chebi.chebi_xref (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_subsets ADD CONSTRAINT " +
                    "chebi_subsets_fk FOREIGN KEY (chebi_id) REFERENCES " +
                    "chebi.chebi_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_consider ADD CONSTRAINT " +
                    "chebi_consider_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "chebi.chebi_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_consider ADD CONSTRAINT " +
                    "chebi_consider_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "chebi.chebi_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_replaced_by ADD CONSTRAINT " +
                    "chebi_replaced_by_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "chebi.chebi_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE chebi.chebi_replaced_by ADD CONSTRAINT " +
                    "chebi_replaced_by_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "chebi.chebi_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("restoreconCHEBI: failed to restore constraints for schema CHEBI.", se);
            throw new ApplicationException("restoreconCHEBI: failed to restore constraints for schema CHEBI.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void storeCHEBI(ChebiDTO chebi) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_tmp = null;
        ChebiDTO_ISA isa = null;
        ChebiDTO_SYN syn = null;
        ChebiDTO_ALT alt = null;
        ChebiDTO_XREF xref = null;
        ChebiDTO_REL rel = null;
        ChebiDTO_SUBSET sub = null;
        ChebiDTO_REP rep = null;
        ChebiDTO_CON con = null;
        
        
        try {
            
            //pheno ontology parameters
            ps = conn.prepareStatement("insert into chebi.chebi_term (id, name, def, def_ref, is_obsolete, comm) values (?,?,?,?,?,?)");
            ps.setInt(1, chebi.getIdAsInt());
            ps.setString(2, chebi.getName());
            ps.setString(3, chebi.getDef());
            ps.setString(4, chebi.getDefRef());
            ps.setInt(5, chebi.getIsObsoleteAsInt());
            ps.setString(6, chebi.getComm());
            ps.execute();
            
            //is a(s)
            Collection is_as = chebi.getIsAs();
            Iterator i = is_as.iterator();
           
            while (i.hasNext()) {
                isa = (ChebiDTO_ISA)i.next();
                
                System.out.println(">>>working on chebi term: "+chebi.getIdAsInt()+" and is_a: "+isa.getIsA());
                
                ps = conn.prepareStatement("select id_b from chebi.chebi_is_a where id_b = ? and id_a = ?");
                ps.setInt(1, chebi.getIdAsInt());
                ps.setInt(2, isa.getIsAInt());
                result_tmp = ps.executeQuery();
                
                if(!result_tmp.next()){
                    ps = conn.prepareStatement("insert into chebi.chebi_is_a (id_b, id_a) values (?,?)");
                    ps.setInt(1, chebi.getIdAsInt());
                    ps.setInt(2, isa.getIsAInt());
                    ps.execute();
                }
                
            }
            
            //synonym(s)
            Collection syns = chebi.getSynonyms();
            Iterator k = syns.iterator();
            
            while (k.hasNext()) {
                syn = (ChebiDTO_SYN)k.next();
                
                System.out.println(">>>working on chebi term: "+chebi.getIdAsInt()+" and synonym: "+syn.getSynonym());
                
                ps = conn.prepareStatement("select id from chebi.chebi_syn where lower(synonym) like ?");
                ps.setString(1, syn.getSynonym().toLowerCase().replaceAll("\\\\","~"));
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into chebi.chebi_syn (id, synonym) values (?,?)");
                    ps.setInt(1, count_syn);
                    ps.setString(2, syn.getSynonym().replaceAll("\\\\","~"));
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into chebi.chebi_syn_r (chebi_id, chebi_syn, attribute) values (?,?,?)");
                    ps.setInt(1, chebi.getIdAsInt());
                    ps.setInt(2, count_syn);
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                    
                    count_syn++;
                } else {
                    
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select chebi_id from chebi.chebi_syn_r where chebi_id = ? and chebi_syn = ? and lower(attribute) like ?");
                    ps.setInt(1, chebi.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.setString(3, syn.getAttribute());
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into chebi.chebi_syn_r (chebi_id, chebi_syn, attribute) values (?,?,?)");
                        ps.setInt(1, chebi.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.setString(3, syn.getAttribute());
                        ps.execute();
                    }
                    
                }
            }
            
            //alt id(s)
            Collection alts = chebi.getAltIds();
            Iterator l = alts.iterator();
            
            while (l.hasNext()) {
                alt = (ChebiDTO_ALT)l.next();
                ps = conn.prepareStatement("select id from chebi.chebi_alt_id where lower(alt_id) like ?");
                ps.setString(1, alt.getAltId().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into chebi.chebi_alt_id (id, alt_id) values (?,?)");
                    ps.setInt(1, count_alt);
                    ps.setString(2, alt.getAltId());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into chebi.chebi_alt_id_r (chebi_id, chebi_alt_id) values (?,?)");
                    ps.setInt(1, chebi.getIdAsInt());
                    ps.setInt(2, count_alt);
                    ps.execute();
                    
                    count_alt++;
                } else {
                    ps = conn.prepareStatement("insert into chebi.chebi_alt_id_r (chebi_id, chebi_alt_id) values (?,?)");
                    ps.setInt(1, chebi.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //xref(s)
            Collection xrefs = chebi.getXrefs();
            Iterator m = xrefs.iterator();
            
            while (m.hasNext()) {
                xref = (ChebiDTO_XREF)m.next();
                
                System.out.println(">>>working on chebi term: "+chebi.getIdAsInt()+" and xref: "+xref.getXref());
                
                ps = conn.prepareStatement("select id from chebi.chebi_xref where lower(xref) like ?");
                ps.setString(1, xref.getXref().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into chebi.chebi_xref (id, xref) values (?,?)");
                    ps.setInt(1, count_xref);
                    ps.setString(2, xref.getXref());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into chebi.chebi_xref_r (chebi_id, xref_id) values (?,?)");
                    ps.setInt(1, chebi.getIdAsInt());
                    ps.setInt(2, count_xref);
                    ps.execute();
                    
                    count_xref++;
                } else {
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select chebi_id from chebi.chebi_xref_r where chebi_id = ? and xref_id = ?");
                    ps.setInt(1, chebi.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into chebi.chebi_xref_r (chebi_id, xref_id) values (?,?)");
                        ps.setInt(1, chebi.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.execute();
                    }
                }
            }
            
            //relationship(s)
            Collection rels = chebi.getRelationships();
            Iterator n = rels.iterator();
           
            while (n.hasNext()) {
                rel = (ChebiDTO_REL)n.next();
                
                System.out.println(">>>working on chebi term: "+chebi.getIdAsInt()+" and relationship: "+rel.getRelation());
                
                ps = conn.prepareStatement("insert into chebi.chebi_relationships (id_a, relation, id_b) values (?,?,?)");
                ps.setInt(1, chebi.getIdAsInt());
                ps.setString(2, rel.getRelation());
                ps.setInt(3, rel.getIdB());
                ps.execute();
            }
            
            //subset(s)
            Collection subsets = chebi.getSubsets();
            Iterator o = subsets.iterator();
           
            while (o.hasNext()) {
                sub = (ChebiDTO_SUBSET)o.next();
                ps = conn.prepareStatement("insert into chebi.chebi_subsets (chebi_id, subset) values (?,?)");
                ps.setInt(1, chebi.getIdAsInt());
                ps.setString(2, sub.getSubset());
                ps.execute();
            }
            
            //consider(s)
            Collection cons = chebi.getConsiders();
            Iterator p = cons.iterator();
           
            while (p.hasNext()) {
                con = (ChebiDTO_CON)p.next();
                ps = conn.prepareStatement("insert into chebi.chebi_consider (id_b, id_a) values (?,?)");
                ps.setInt(1, chebi.getIdAsInt());
                ps.setInt(2, con.getConsiderInt());
                ps.execute();
            }
            
            //replaced_by(s)
            Collection reps = chebi.getReplaced_bys();
            Iterator q = reps.iterator();
           
            while (q.hasNext()) {
                rep = (ChebiDTO_REP)q.next();
                ps = conn.prepareStatement("insert into chebi.chebi_replaced_by (id_b, id_a) values (?,?)");
                ps.setInt(1, chebi.getIdAsInt());
                ps.setInt(2, rep.getReplacedInt());
                ps.execute();
            }
            
        } catch (SQLException se) {
            logger.error("storeCELL: failed to perform.", se);
            throw new ApplicationException("storeCHEBI: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    //</editor-fold>
    
    //emap methods
    //<editor-fold defaultstate="collapsed">
    public void loadEMAP() throws ApplicationException {
        try {
            File file = new File("C:/zouberakis/projects/pato/docs/obo.files/emap.obo");
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(file));
            
            String line = null;
            EmapDTO emap = new EmapDTO();
            
            //drop all FK constraints in schema EMAP
            dropconEMAP();
            //delete all data in schema EMAP
            cleanEMAP();
            
            while ((line = input.readLine()) != null){
                
                if(line.compareToIgnoreCase("[Typedef]")==0){
                    if(!emap.getId().equals("")){
                        storeEMAP(emap);
                    }
                    break;
                }
                
                if(line.compareTo("[Term]")!=0){
                    emap.IdentifyLine(line);
                }else{
                    if(!emap.getId().equals("")){
                        storeEMAP(emap);
                    }
                    emap = null;
                    emap = new EmapDTO();
                }
            }
            
            //reconstruct all FK constraints
            restoreconEMAP();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("loadEMAP: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void cleanEMAP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("delete from emap.emap_term");
            ps.execute();
        
            ps = conn.prepareStatement("delete from emap.emap_alt_id");
            ps.execute();
            
            ps = conn.prepareStatement("delete from emap.emap_alt_id_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from emap.emap_is_a");
            ps.execute();
        
            ps = conn.prepareStatement("delete from emap.emap_syn");
            ps.execute();
            
            ps = conn.prepareStatement("delete from emap.emap_syn_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from emap.emap_xref");
            ps.execute();
            
            ps = conn.prepareStatement("delete from emap.emap_xref_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from emap.emap_relationships");
            ps.execute();
            
            ps = conn.prepareStatement("delete from emap.emap_subsets");
            ps.execute();
            
            ps = conn.prepareStatement("delete from emap.emap_consider");
            ps.execute();
            
            ps = conn.prepareStatement("delete from emap.emap_replaced_by");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanEMAP: failed to perform.", se);
            throw new ApplicationException("cleanEMAP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void dropconEMAP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE emap.emap_alt_id_r DROP CONSTRAINT emap_alt_id_r_alt_id_fk;" +
                    "ALTER TABLE emap.emap_alt_id_r DROP CONSTRAINT emap_alt_id_r_emap_fk;" +
                    "ALTER TABLE emap.emap_is_a DROP CONSTRAINT emap_is_a_fk_a;" +
                    "ALTER TABLE emap.emap_is_a DROP CONSTRAINT emap_is_a_fk_b;" +
                    "ALTER TABLE emap.emap_relationships DROP CONSTRAINT emap_relationships_a_fk;" +
                    "ALTER TABLE emap.emap_relationships DROP CONSTRAINT emap_relationships_b_fk;" +
                    "ALTER TABLE emap.emap_syn_r DROP CONSTRAINT emap_syn_r_emap_fk;" +
                    "ALTER TABLE emap.emap_syn_r DROP CONSTRAINT emap_syn_r_syn_fk;" +
                    "ALTER TABLE emap.emap_xref_r DROP CONSTRAINT emap_xref_r_emap_fk;" +
                    "ALTER TABLE emap.emap_xref_r DROP CONSTRAINT emap_xref_r_xref_fk;" +
                    "ALTER TABLE emap.emap_consider DROP CONSTRAINT emap_consider_fk_a;" +
                    "ALTER TABLE emap.emap_consider DROP CONSTRAINT emap_consider_fk_b;" +
                    "ALTER TABLE emap.emap_replaced_by DROP CONSTRAINT emap_replaced_by_fk_a;" +
                    "ALTER TABLE emap.emap_replaced_by DROP CONSTRAINT emap_replaced_by_fk_b;" +
                    "ALTER TABLE emap.emap_subsets DROP CONSTRAINT emap_subsets_fk;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("dropconEMAP: failed to drop constraints for schema EMAP.", se);
            throw new ApplicationException("dropconEMAP: failed to drop constraints for schema EMAP.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void restoreconEMAP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE emap.emap_alt_id_r ADD " +
                    "CONSTRAINT emap_alt_id_r_alt_id_fk FOREIGN KEY (emap_alt_id) " +
                    "REFERENCES emap.emap_alt_id (id) MATCH SIMPLE ON UPDATE NO " +
                    "ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_alt_id_r ADD CONSTRAINT " +
                    "emap_alt_id_r_emap_fk FOREIGN KEY (emap_id) REFERENCES " +
                    "emap.emap_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_is_a ADD CONSTRAINT emap_is_a_fk_a " +
                    "FOREIGN KEY (id_a) REFERENCES emap.emap_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_is_a ADD CONSTRAINT emap_is_a_fk_b " +
                    "FOREIGN KEY (id_b) REFERENCES emap.emap_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_relationships ADD CONSTRAINT " +
                    "emap_relationships_a_fk FOREIGN KEY (id_a) REFERENCES " +
                    "emap.emap_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON" +
                    " DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_relationships ADD CONSTRAINT " +
                    "emap_relationships_b_fk FOREIGN KEY (id_b) REFERENCES " +
                    "emap.emap_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_syn_r ADD CONSTRAINT emap_syn_r_emap_fk " +
                    "FOREIGN KEY (emap_id) REFERENCES emap.emap_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_syn_r ADD CONSTRAINT emap_syn_r_syn_fk " +
                    "FOREIGN KEY (emap_syn) REFERENCES emap.emap_syn (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_xref_r ADD CONSTRAINT emap_xref_r_emap_fk " +
                    "FOREIGN KEY (emap_id) REFERENCES emap.emap_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_xref_r ADD CONSTRAINT " +
                    "emap_xref_r_xref_fk FOREIGN KEY (xref_id) REFERENCES " +
                    "emap.emap_xref (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_subsets ADD CONSTRAINT " +
                    "emap_subsets_fk FOREIGN KEY (emap_id) REFERENCES " +
                    "emap.emap_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_consider ADD CONSTRAINT " +
                    "emap_consider_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "emap.emap_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_consider ADD CONSTRAINT " +
                    "emap_consider_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "emap.emap_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_replaced_by ADD CONSTRAINT " +
                    "emap_replaced_by_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "emap.emap_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE emap.emap_replaced_by ADD CONSTRAINT " +
                    "emap_replaced_by_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "emap.emap_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("restoreconEMAP: failed to restore constraints for schema EMAP.", se);
            throw new ApplicationException("restoreconEMAP: failed to restore constraints for schema EMAP.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void storeEMAP(EmapDTO emap) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_tmp = null;
        EmapDTO_ISA isa = null;
        EmapDTO_SYN syn = null;
        EmapDTO_ALT alt = null;
        EmapDTO_XREF xref = null;
        EmapDTO_REL rel = null;
        EmapDTO_SUBSET sub = null;
        EmapDTO_REP rep = null;
        EmapDTO_CON con = null;
        
        
        try {
            
            //pheno ontology parameters
            ps = conn.prepareStatement("insert into emap.emap_term (id, name, def, def_ref, is_obsolete, comm) values (?,?,?,?,?,?)");
            ps.setInt(1, emap.getIdAsInt());
            ps.setString(2, emap.getName());
            ps.setString(3, emap.getDef());
            ps.setString(4, emap.getDefRef());
            ps.setInt(5, emap.getIsObsoleteAsInt());
            ps.setString(6, emap.getComm());
            ps.execute();
            
            //is a(s)
            Collection is_as = emap.getIsAs();
            Iterator i = is_as.iterator();
           
            while (i.hasNext()) {
                isa = (EmapDTO_ISA)i.next();
                
                System.out.println(">>>working on emap term: "+emap.getIdAsInt()+" and is_a: "+isa.getIsA());
                
                ps = conn.prepareStatement("select id_b from emap.emap_is_a where id_b = ? and id_a = ?");
                ps.setInt(1, emap.getIdAsInt());
                ps.setInt(2, isa.getIsAInt());
                result_tmp = ps.executeQuery();
                
                if(!result_tmp.next()){
                    ps = conn.prepareStatement("insert into emap.emap_is_a (id_b, id_a) values (?,?)");
                    ps.setInt(1, emap.getIdAsInt());
                    ps.setInt(2, isa.getIsAInt());
                    ps.execute();
                }
                
            }
            
            //synonym(s)
            Collection syns = emap.getSynonyms();
            Iterator k = syns.iterator();
            
            while (k.hasNext()) {
                syn = (EmapDTO_SYN)k.next();
                
                System.out.println(">>>working on emap term: "+emap.getIdAsInt()+" and synonym: "+syn.getSynonym());
                
                ps = conn.prepareStatement("select id from emap.emap_syn where lower(synonym) like ?");
                ps.setString(1, syn.getSynonym().toLowerCase().replaceAll("\\\\","~"));
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into emap.emap_syn (id, synonym) values (?,?)");
                    ps.setInt(1, count_syn);
                    ps.setString(2, syn.getSynonym().replaceAll("\\\\","~"));
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into emap.emap_syn_r (emap_id, emap_syn, attribute) values (?,?,?)");
                    ps.setInt(1, emap.getIdAsInt());
                    ps.setInt(2, count_syn);
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                    
                    count_syn++;
                } else {
                    
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select emap_id from emap.emap_syn_r where emap_id = ? and emap_syn = ? and lower(attribute) like ?");
                    ps.setInt(1, emap.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.setString(3, syn.getAttribute());
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into emap.emap_syn_r (emap_id, emap_syn, attribute) values (?,?,?)");
                        ps.setInt(1, emap.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.setString(3, syn.getAttribute());
                        ps.execute();
                    }
                    
                }
            }
            
            //alt id(s)
            Collection alts = emap.getAltIds();
            Iterator l = alts.iterator();
            
            while (l.hasNext()) {
                alt = (EmapDTO_ALT)l.next();
                ps = conn.prepareStatement("select id from emap.emap_alt_id where lower(alt_id) like ?");
                ps.setString(1, alt.getAltId().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into emap.emap_alt_id (id, alt_id) values (?,?)");
                    ps.setInt(1, count_alt);
                    ps.setString(2, alt.getAltId());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into emap.emap_alt_id_r (emap_id, emap_alt_id) values (?,?)");
                    ps.setInt(1, emap.getIdAsInt());
                    ps.setInt(2, count_alt);
                    ps.execute();
                    
                    count_alt++;
                } else {
                    ps = conn.prepareStatement("insert into emap.emap_alt_id_r (emap_id, emap_alt_id) values (?,?)");
                    ps.setInt(1, emap.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //xref(s)
            Collection xrefs = emap.getXrefs();
            Iterator m = xrefs.iterator();
            
            while (m.hasNext()) {
                xref = (EmapDTO_XREF)m.next();
                
                System.out.println(">>>working on emap term: "+emap.getIdAsInt()+" and xref: "+xref.getXref());
                
                ps = conn.prepareStatement("select id from emap.emap_xref where lower(xref) like ?");
                ps.setString(1, xref.getXref().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into emap.emap_xref (id, xref) values (?,?)");
                    ps.setInt(1, count_xref);
                    ps.setString(2, xref.getXref());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into emap.emap_xref_r (emap_id, xref_id) values (?,?)");
                    ps.setInt(1, emap.getIdAsInt());
                    ps.setInt(2, count_xref);
                    ps.execute();
                    
                    count_xref++;
                } else {
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select emap_id from emap.emap_xref_r where emap_id = ? and xref_id = ?");
                    ps.setInt(1, emap.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into emap.emap_xref_r (emap_id, xref_id) values (?,?)");
                        ps.setInt(1, emap.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.execute();
                    }
                }
            }
            
            //relationship(s)
            Collection rels = emap.getRelationships();
            Iterator n = rels.iterator();
           
            while (n.hasNext()) {
                rel = (EmapDTO_REL)n.next();
                
                System.out.println(">>>working on emap term: "+emap.getIdAsInt()+" and relationship: "+rel.getRelation());
                
                ps = conn.prepareStatement("insert into emap.emap_relationships (id_a, relation, id_b) values (?,?,?)");
                ps.setInt(1, emap.getIdAsInt());
                ps.setString(2, rel.getRelation());
                ps.setInt(3, rel.getIdB());
                ps.execute();
            }
            
            //subset(s)
            Collection subsets = emap.getSubsets();
            Iterator o = subsets.iterator();
           
            while (o.hasNext()) {
                sub = (EmapDTO_SUBSET)o.next();
                ps = conn.prepareStatement("insert into emap.emap_subsets (emap_id, subset) values (?,?)");
                ps.setInt(1, emap.getIdAsInt());
                ps.setString(2, sub.getSubset());
                ps.execute();
            }
            
            //consider(s)
            Collection cons = emap.getConsiders();
            Iterator p = cons.iterator();
           
            while (p.hasNext()) {
                con = (EmapDTO_CON)p.next();
                ps = conn.prepareStatement("insert into emap.emap_consider (id_b, id_a) values (?,?)");
                ps.setInt(1, emap.getIdAsInt());
                ps.setInt(2, con.getConsiderInt());
                ps.execute();
            }
            
            //replaced_by(s)
            Collection reps = emap.getReplaced_bys();
            Iterator q = reps.iterator();
           
            while (q.hasNext()) {
                rep = (EmapDTO_REP)q.next();
                ps = conn.prepareStatement("insert into emap.emap_replaced_by (id_b, id_a) values (?,?)");
                ps.setInt(1, emap.getIdAsInt());
                ps.setInt(2, rep.getReplacedInt());
                ps.execute();
            }
            
        } catch (SQLException se) {
            logger.error("storeEMAP: failed to perform.", se);
            throw new ApplicationException("storeEMAP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    //</editor-fold>
    
    //doid methods
    //<editor-fold defaultstate="collapsed">
    public void loadDOID() throws ApplicationException {
        try {
            File file = new File("C:/zouberakis/projects/pato/docs/obo.files/human_disease.obo");
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(file));
            
            String line = null;
            DoidDTO doid = new DoidDTO();
            
            //drop all FK constraints in schema DOID
            dropconDOID();
            //delete all data in schema DOID
            cleanDOID();
            
            while ((line = input.readLine()) != null){
                
                if(line.compareToIgnoreCase("[Typedef]")==0){
                    if(!doid.getId().equals("")){
                        storeDOID(doid);
                    }
                    break;
                }
                
                if(line.compareTo("[Term]")!=0){
                    doid.IdentifyLine(line);
                }else{
                    if(!doid.getId().equals("")){
                        storeDOID(doid);
                    }
                    doid = null;
                    doid = new DoidDTO();
                }
            }
            
            //reconstruct all FK constraints
            restoreconDOID();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("loadDOID: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void cleanDOID() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("delete from doid.doid_term");
            ps.execute();
        
            ps = conn.prepareStatement("delete from doid.doid_alt_id");
            ps.execute();
            
            ps = conn.prepareStatement("delete from doid.doid_alt_id_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from doid.doid_is_a");
            ps.execute();
        
            ps = conn.prepareStatement("delete from doid.doid_syn");
            ps.execute();
            
            ps = conn.prepareStatement("delete from doid.doid_syn_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from doid.doid_xref");
            ps.execute();
            
            ps = conn.prepareStatement("delete from doid.doid_xref_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from doid.doid_relationships");
            ps.execute();
            
            ps = conn.prepareStatement("delete from doid.doid_subsets");
            ps.execute();
            
            ps = conn.prepareStatement("delete from doid.doid_consider");
            ps.execute();
            
            ps = conn.prepareStatement("delete from doid.doid_replaced_by");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanDOID: failed to perform.", se);
            throw new ApplicationException("cleanDOID: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void dropconDOID() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE doid.doid_alt_id_r DROP CONSTRAINT doid_alt_id_r_alt_id_fk;" +
                    "ALTER TABLE doid.doid_alt_id_r DROP CONSTRAINT doid_alt_id_r_doid_fk;" +
                    "ALTER TABLE doid.doid_is_a DROP CONSTRAINT doid_is_a_fk_a;" +
                    "ALTER TABLE doid.doid_is_a DROP CONSTRAINT doid_is_a_fk_b;" +
                    "ALTER TABLE doid.doid_relationships DROP CONSTRAINT doid_relationships_a_fk;" +
                    "ALTER TABLE doid.doid_relationships DROP CONSTRAINT doid_relationships_b_fk;" +
                    "ALTER TABLE doid.doid_syn_r DROP CONSTRAINT doid_syn_r_doid_fk;" +
                    "ALTER TABLE doid.doid_syn_r DROP CONSTRAINT doid_syn_r_syn_fk;" +
                    "ALTER TABLE doid.doid_xref_r DROP CONSTRAINT doid_xref_r_doid_fk;" +
                    "ALTER TABLE doid.doid_xref_r DROP CONSTRAINT doid_xref_r_xref_fk;" +
                    "ALTER TABLE doid.doid_consider DROP CONSTRAINT doid_consider_fk_a;" +
                    "ALTER TABLE doid.doid_consider DROP CONSTRAINT doid_consider_fk_b;" +
                    "ALTER TABLE doid.doid_replaced_by DROP CONSTRAINT doid_replaced_by_fk_a;" +
                    "ALTER TABLE doid.doid_replaced_by DROP CONSTRAINT doid_replaced_by_fk_b;" +
                    "ALTER TABLE doid.doid_subsets DROP CONSTRAINT doid_subsets_fk;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("dropconDOID: failed to drop constraints for schema DOID.", se);
            throw new ApplicationException("dropconDOID: failed to drop constraints for schema DOID.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void restoreconDOID() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE doid.doid_alt_id_r ADD " +
                    "CONSTRAINT doid_alt_id_r_alt_id_fk FOREIGN KEY (doid_alt_id) " +
                    "REFERENCES doid.doid_alt_id (id) MATCH SIMPLE ON UPDATE NO " +
                    "ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_alt_id_r ADD CONSTRAINT " +
                    "doid_alt_id_r_doid_fk FOREIGN KEY (doid_id) REFERENCES " +
                    "doid.doid_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_is_a ADD CONSTRAINT doid_is_a_fk_a " +
                    "FOREIGN KEY (id_a) REFERENCES doid.doid_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_is_a ADD CONSTRAINT doid_is_a_fk_b " +
                    "FOREIGN KEY (id_b) REFERENCES doid.doid_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_relationships ADD CONSTRAINT " +
                    "doid_relationships_a_fk FOREIGN KEY (id_a) REFERENCES " +
                    "doid.doid_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON" +
                    " DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_relationships ADD CONSTRAINT " +
                    "doid_relationships_b_fk FOREIGN KEY (id_b) REFERENCES " +
                    "doid.doid_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_syn_r ADD CONSTRAINT doid_syn_r_doid_fk " +
                    "FOREIGN KEY (doid_id) REFERENCES doid.doid_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_syn_r ADD CONSTRAINT doid_syn_r_syn_fk " +
                    "FOREIGN KEY (doid_syn) REFERENCES doid.doid_syn (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_xref_r ADD CONSTRAINT doid_xref_r_doid_fk " +
                    "FOREIGN KEY (doid_id) REFERENCES doid.doid_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_xref_r ADD CONSTRAINT " +
                    "doid_xref_r_xref_fk FOREIGN KEY (xref_id) REFERENCES " +
                    "doid.doid_xref (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_subsets ADD CONSTRAINT " +
                    "doid_subsets_fk FOREIGN KEY (doid_id) REFERENCES " +
                    "doid.doid_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_consider ADD CONSTRAINT " +
                    "doid_consider_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "doid.doid_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_consider ADD CONSTRAINT " +
                    "doid_consider_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "doid.doid_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_replaced_by ADD CONSTRAINT " +
                    "doid_replaced_by_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "doid.doid_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE doid.doid_replaced_by ADD CONSTRAINT " +
                    "doid_replaced_by_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "doid.doid_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("restoreconDOID: failed to restore constraints for schema DOID.", se);
            throw new ApplicationException("restoreconDOID: failed to restore constraints for schema DOID.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void storeDOID(DoidDTO doid) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_tmp = null;
        DoidDTO_ISA isa = null;
        DoidDTO_SYN syn = null;
        DoidDTO_ALT alt = null;
        DoidDTO_XREF xref = null;
        DoidDTO_REL rel = null;
        DoidDTO_SUBSET sub = null;
        DoidDTO_REP rep = null;
        DoidDTO_CON con = null;
        
        
        try {
            
            //pheno ontology parameters
            ps = conn.prepareStatement("insert into doid.doid_term (id, name, def, def_ref, is_obsolete, comm) values (?,?,?,?,?,?)");
            ps.setInt(1, doid.getIdAsInt());
            ps.setString(2, doid.getName());
            ps.setString(3, doid.getDef());
            ps.setString(4, doid.getDefRef());
            ps.setInt(5, doid.getIsObsoleteAsInt());
            ps.setString(6, doid.getComm());
            ps.execute();
            
            //is a(s)
            Collection is_as = doid.getIsAs();
            Iterator i = is_as.iterator();
           
            while (i.hasNext()) {
                isa = (DoidDTO_ISA)i.next();
                
                System.out.println(">>>working on doid term: "+doid.getIdAsInt()+" and is_a: "+isa.getIsA());
                
                ps = conn.prepareStatement("select id_b from doid.doid_is_a where id_b = ? and id_a = ?");
                ps.setInt(1, doid.getIdAsInt());
                ps.setInt(2, isa.getIsAInt());
                result_tmp = ps.executeQuery();
                
                if(!result_tmp.next()){
                    ps = conn.prepareStatement("insert into doid.doid_is_a (id_b, id_a) values (?,?)");
                    ps.setInt(1, doid.getIdAsInt());
                    ps.setInt(2, isa.getIsAInt());
                    ps.execute();
                }
                
            }
            
            //synonym(s)
            Collection syns = doid.getSynonyms();
            Iterator k = syns.iterator();
            
            while (k.hasNext()) {
                syn = (DoidDTO_SYN)k.next();
                
                System.out.println(">>>working on doid term: "+doid.getIdAsInt()+" and synonym: "+syn.getSynonym());
                
                ps = conn.prepareStatement("select id from doid.doid_syn where lower(synonym) like ?");
                ps.setString(1, syn.getSynonym().toLowerCase().replaceAll("\\\\","~"));
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into doid.doid_syn (id, synonym) values (?,?)");
                    ps.setInt(1, count_syn);
                    ps.setString(2, syn.getSynonym().replaceAll("\\\\","~"));
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into doid.doid_syn_r (doid_id, doid_syn, attribute) values (?,?,?)");
                    ps.setInt(1, doid.getIdAsInt());
                    ps.setInt(2, count_syn);
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                    
                    count_syn++;
                } else {
                    
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select doid_id from doid.doid_syn_r where doid_id = ? and doid_syn = ? and lower(attribute) like ?");
                    ps.setInt(1, doid.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.setString(3, syn.getAttribute());
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into doid.doid_syn_r (doid_id, doid_syn, attribute) values (?,?,?)");
                        ps.setInt(1, doid.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.setString(3, syn.getAttribute());
                        ps.execute();
                    }
                    
                }
            }
            
            //alt id(s)
            Collection alts = doid.getAltIds();
            Iterator l = alts.iterator();
            
            while (l.hasNext()) {
                alt = (DoidDTO_ALT)l.next();
                ps = conn.prepareStatement("select id from doid.doid_alt_id where lower(alt_id) like ?");
                ps.setString(1, alt.getAltId().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into doid.doid_alt_id (id, alt_id) values (?,?)");
                    ps.setInt(1, count_alt);
                    ps.setString(2, alt.getAltId());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into doid.doid_alt_id_r (doid_id, doid_alt_id) values (?,?)");
                    ps.setInt(1, doid.getIdAsInt());
                    ps.setInt(2, count_alt);
                    ps.execute();
                    
                    count_alt++;
                } else {
                    ps = conn.prepareStatement("insert into doid.doid_alt_id_r (doid_id, doid_alt_id) values (?,?)");
                    ps.setInt(1, doid.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //xref(s)
            Collection xrefs = doid.getXrefs();
            Iterator m = xrefs.iterator();
            
            while (m.hasNext()) {
                xref = (DoidDTO_XREF)m.next();
                
                System.out.println(">>>working on doid term: "+doid.getIdAsInt()+" and xref: "+xref.getXref());
                
                ps = conn.prepareStatement("select id from doid.doid_xref where lower(xref) like ?");
                ps.setString(1, xref.getXref().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into doid.doid_xref (id, xref) values (?,?)");
                    ps.setInt(1, count_xref);
                    ps.setString(2, xref.getXref());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into doid.doid_xref_r (doid_id, xref_id) values (?,?)");
                    ps.setInt(1, doid.getIdAsInt());
                    ps.setInt(2, count_xref);
                    ps.execute();
                    
                    count_xref++;
                } else {
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select doid_id from doid.doid_xref_r where doid_id = ? and xref_id = ?");
                    ps.setInt(1, doid.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into doid.doid_xref_r (doid_id, xref_id) values (?,?)");
                        ps.setInt(1, doid.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.execute();
                    }
                }
            }
            
            //relationship(s)
            Collection rels = doid.getRelationships();
            Iterator n = rels.iterator();
           
            while (n.hasNext()) {
                rel = (DoidDTO_REL)n.next();
                
                System.out.println(">>>working on doid term: "+doid.getIdAsInt()+" and relationship: "+rel.getRelation());
                
                ps = conn.prepareStatement("insert into doid.doid_relationships (id_a, relation, id_b) values (?,?,?)");
                ps.setInt(1, doid.getIdAsInt());
                ps.setString(2, rel.getRelation());
                ps.setInt(3, rel.getIdB());
                ps.execute();
            }
            
            //subset(s)
            Collection subsets = doid.getSubsets();
            Iterator o = subsets.iterator();
           
            while (o.hasNext()) {
                sub = (DoidDTO_SUBSET)o.next();
                ps = conn.prepareStatement("insert into doid.doid_subsets (doid_id, subset) values (?,?)");
                ps.setInt(1, doid.getIdAsInt());
                ps.setString(2, sub.getSubset());
                ps.execute();
            }
            
            //consider(s)
            Collection cons = doid.getConsiders();
            Iterator p = cons.iterator();
           
            while (p.hasNext()) {
                con = (DoidDTO_CON)p.next();
                ps = conn.prepareStatement("insert into doid.doid_consider (id_b, id_a) values (?,?)");
                ps.setInt(1, doid.getIdAsInt());
                ps.setInt(2, con.getConsiderInt());
                ps.execute();
            }
            
            //replaced_by(s)
            Collection reps = doid.getReplaced_bys();
            Iterator q = reps.iterator();
           
            while (q.hasNext()) {
                rep = (DoidDTO_REP)q.next();
                ps = conn.prepareStatement("insert into doid.doid_replaced_by (id_b, id_a) values (?,?)");
                ps.setInt(1, doid.getIdAsInt());
                ps.setInt(2, rep.getReplacedInt());
                ps.execute();
            }
            
        } catch (SQLException se) {
            logger.error("storeDOID: failed to perform.", se);
            throw new ApplicationException("storeDOID: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    //</editor-fold>
    
    //imr methods
    //<editor-fold defaultstate="collapsed">
    public void loadIMR() throws ApplicationException {
        try {
            File file = new File("C:/zouberakis/projects/pato/docs/obo.files/molecule_role.obo");
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(file));
            
            String line = null;
            ImrDTO imr = new ImrDTO();
            
            //drop all FK constraints in schema IMR
            dropconIMR();
            //delete all data in schema IMR
            cleanIMR();
            
            while ((line = input.readLine()) != null){
                
                if(line.compareToIgnoreCase("[Typedef]")==0){
                    if(!imr.getId().equals("")){
                        storeIMR(imr);
                    }
                    break;
                }
                
                if(line.compareTo("[Term]")!=0){
                    imr.IdentifyLine(line);
                }else{
                    if(!imr.getId().equals("")){
                        storeIMR(imr);
                    }
                    imr = null;
                    imr = new ImrDTO();
                }
            }
            
            //reconstruct all FK constraints
            restoreconIMR();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("loadIMR: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void cleanIMR() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("delete from imr.imr_term");
            ps.execute();
        
            ps = conn.prepareStatement("delete from imr.imr_alt_id");
            ps.execute();
            
            ps = conn.prepareStatement("delete from imr.imr_alt_id_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from imr.imr_is_a");
            ps.execute();
        
            ps = conn.prepareStatement("delete from imr.imr_syn");
            ps.execute();
            
            ps = conn.prepareStatement("delete from imr.imr_syn_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from imr.imr_xref");
            ps.execute();
            
            ps = conn.prepareStatement("delete from imr.imr_xref_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from imr.imr_relationships");
            ps.execute();
            
            ps = conn.prepareStatement("delete from imr.imr_subsets");
            ps.execute();
            
            ps = conn.prepareStatement("delete from imr.imr_consider");
            ps.execute();
            
            ps = conn.prepareStatement("delete from imr.imr_replaced_by");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanIMR: failed to perform.", se);
            throw new ApplicationException("cleanIMR: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void dropconIMR() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE imr.imr_alt_id_r DROP CONSTRAINT imr_alt_id_r_alt_id_fk;" +
                    "ALTER TABLE imr.imr_alt_id_r DROP CONSTRAINT imr_alt_id_r_imr_fk;" +
                    "ALTER TABLE imr.imr_is_a DROP CONSTRAINT imr_is_a_fk_a;" +
                    "ALTER TABLE imr.imr_is_a DROP CONSTRAINT imr_is_a_fk_b;" +
                    "ALTER TABLE imr.imr_relationships DROP CONSTRAINT imr_relationships_a_fk;" +
                    "ALTER TABLE imr.imr_relationships DROP CONSTRAINT imr_relationships_b_fk;" +
                    "ALTER TABLE imr.imr_syn_r DROP CONSTRAINT imr_syn_r_imr_fk;" +
                    "ALTER TABLE imr.imr_syn_r DROP CONSTRAINT imr_syn_r_syn_fk;" +
                    "ALTER TABLE imr.imr_xref_r DROP CONSTRAINT imr_xref_r_imr_fk;" +
                    "ALTER TABLE imr.imr_xref_r DROP CONSTRAINT imr_xref_r_xref_fk;" +
                    "ALTER TABLE imr.imr_consider DROP CONSTRAINT imr_consider_fk_a;" +
                    "ALTER TABLE imr.imr_consider DROP CONSTRAINT imr_consider_fk_b;" +
                    "ALTER TABLE imr.imr_replaced_by DROP CONSTRAINT imr_replaced_by_fk_a;" +
                    "ALTER TABLE imr.imr_replaced_by DROP CONSTRAINT imr_replaced_by_fk_b;" +
                    "ALTER TABLE imr.imr_subsets DROP CONSTRAINT imr_subsets_fk;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("dropconIMR: failed to drop constraints for schema IMR.", se);
            throw new ApplicationException("dropconIMR: failed to drop constraints for schema IMR.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void restoreconIMR() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE imr.imr_alt_id_r ADD " +
                    "CONSTRAINT imr_alt_id_r_alt_id_fk FOREIGN KEY (imr_alt_id) " +
                    "REFERENCES imr.imr_alt_id (id) MATCH SIMPLE ON UPDATE NO " +
                    "ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_alt_id_r ADD CONSTRAINT " +
                    "imr_alt_id_r_imr_fk FOREIGN KEY (imr_id) REFERENCES " +
                    "imr.imr_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_is_a ADD CONSTRAINT imr_is_a_fk_a " +
                    "FOREIGN KEY (id_a) REFERENCES imr.imr_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_is_a ADD CONSTRAINT imr_is_a_fk_b " +
                    "FOREIGN KEY (id_b) REFERENCES imr.imr_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_relationships ADD CONSTRAINT " +
                    "imr_relationships_a_fk FOREIGN KEY (id_a) REFERENCES " +
                    "imr.imr_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON" +
                    " DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_relationships ADD CONSTRAINT " +
                    "imr_relationships_b_fk FOREIGN KEY (id_b) REFERENCES " +
                    "imr.imr_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_syn_r ADD CONSTRAINT imr_syn_r_imr_fk " +
                    "FOREIGN KEY (imr_id) REFERENCES imr.imr_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_syn_r ADD CONSTRAINT imr_syn_r_syn_fk " +
                    "FOREIGN KEY (imr_syn) REFERENCES imr.imr_syn (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_xref_r ADD CONSTRAINT imr_xref_r_imr_fk " +
                    "FOREIGN KEY (imr_id) REFERENCES imr.imr_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_xref_r ADD CONSTRAINT " +
                    "imr_xref_r_xref_fk FOREIGN KEY (xref_id) REFERENCES " +
                    "imr.imr_xref (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_subsets ADD CONSTRAINT " +
                    "imr_subsets_fk FOREIGN KEY (imr_id) REFERENCES " +
                    "imr.imr_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_consider ADD CONSTRAINT " +
                    "imr_consider_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "imr.imr_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_consider ADD CONSTRAINT " +
                    "imr_consider_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "imr.imr_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_replaced_by ADD CONSTRAINT " +
                    "imr_replaced_by_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "imr.imr_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE imr.imr_replaced_by ADD CONSTRAINT " +
                    "imr_replaced_by_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "imr.imr_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("restoreconIMR: failed to restore constraints for schema IMR.", se);
            throw new ApplicationException("restoreconIMR: failed to restore constraints for schema IMR.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void storeIMR(ImrDTO imr) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_tmp = null;
        ImrDTO_ISA isa = null;
        ImrDTO_SYN syn = null;
        ImrDTO_ALT alt = null;
        ImrDTO_XREF xref = null;
        ImrDTO_REL rel = null;
        ImrDTO_SUBSET sub = null;
        ImrDTO_REP rep = null;
        ImrDTO_CON con = null;
        
        
        try {
            
            //pheno ontology parameters
            ps = conn.prepareStatement("insert into imr.imr_term (id, name, def, def_ref, is_obsolete, comm) values (?,?,?,?,?,?)");
            ps.setInt(1, imr.getIdAsInt());
            ps.setString(2, imr.getName());
            ps.setString(3, imr.getDef());
            ps.setString(4, imr.getDefRef());
            ps.setInt(5, imr.getIsObsoleteAsInt());
            ps.setString(6, imr.getComm());
            ps.execute();
            
            //is a(s)
            Collection is_as = imr.getIsAs();
            Iterator i = is_as.iterator();
           
            while (i.hasNext()) {
                isa = (ImrDTO_ISA)i.next();
                
                System.out.println(">>>working on imr term: "+imr.getIdAsInt()+" and is_a: "+isa.getIsA());
                
                ps = conn.prepareStatement("select id_b from imr.imr_is_a where id_b = ? and id_a = ?");
                ps.setInt(1, imr.getIdAsInt());
                ps.setInt(2, isa.getIsAInt());
                result_tmp = ps.executeQuery();
                
                if(!result_tmp.next()){
                    ps = conn.prepareStatement("insert into imr.imr_is_a (id_b, id_a) values (?,?)");
                    ps.setInt(1, imr.getIdAsInt());
                    ps.setInt(2, isa.getIsAInt());
                    ps.execute();
                }
                
            }
            
            //synonym(s)
            Collection syns = imr.getSynonyms();
            Iterator k = syns.iterator();
            
            while (k.hasNext()) {
                syn = (ImrDTO_SYN)k.next();
                
                System.out.println(">>>working on imr term: "+imr.getIdAsInt()+" and synonym: "+syn.getSynonym());
                
                ps = conn.prepareStatement("select id from imr.imr_syn where lower(synonym) like ?");
                ps.setString(1, syn.getSynonym().toLowerCase().replaceAll("\\\\","~"));
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into imr.imr_syn (id, synonym) values (?,?)");
                    ps.setInt(1, count_syn);
                    ps.setString(2, syn.getSynonym().replaceAll("\\\\","~"));
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into imr.imr_syn_r (imr_id, imr_syn, attribute) values (?,?,?)");
                    ps.setInt(1, imr.getIdAsInt());
                    ps.setInt(2, count_syn);
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                    
                    count_syn++;
                } else {
                    
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select imr_id from imr.imr_syn_r where imr_id = ? and imr_syn = ? and lower(attribute) like ?");
                    ps.setInt(1, imr.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.setString(3, syn.getAttribute());
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into imr.imr_syn_r (imr_id, imr_syn, attribute) values (?,?,?)");
                        ps.setInt(1, imr.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.setString(3, syn.getAttribute());
                        ps.execute();
                    }
                    
                }
            }
            
            //alt id(s)
            Collection alts = imr.getAltIds();
            Iterator l = alts.iterator();
            
            while (l.hasNext()) {
                alt = (ImrDTO_ALT)l.next();
                ps = conn.prepareStatement("select id from imr.imr_alt_id where lower(alt_id) like ?");
                ps.setString(1, alt.getAltId().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into imr.imr_alt_id (id, alt_id) values (?,?)");
                    ps.setInt(1, count_alt);
                    ps.setString(2, alt.getAltId());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into imr.imr_alt_id_r (imr_id, imr_alt_id) values (?,?)");
                    ps.setInt(1, imr.getIdAsInt());
                    ps.setInt(2, count_alt);
                    ps.execute();
                    
                    count_alt++;
                } else {
                    ps = conn.prepareStatement("insert into imr.imr_alt_id_r (imr_id, imr_alt_id) values (?,?)");
                    ps.setInt(1, imr.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //xref(s)
            Collection xrefs = imr.getXrefs();
            Iterator m = xrefs.iterator();
            
            while (m.hasNext()) {
                xref = (ImrDTO_XREF)m.next();
                
                System.out.println(">>>working on imr term: "+imr.getIdAsInt()+" and xref: "+xref.getXref());
                
                ps = conn.prepareStatement("select id from imr.imr_xref where lower(xref) like ?");
                ps.setString(1, xref.getXref().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into imr.imr_xref (id, xref) values (?,?)");
                    ps.setInt(1, count_xref);
                    ps.setString(2, xref.getXref());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into imr.imr_xref_r (imr_id, xref_id) values (?,?)");
                    ps.setInt(1, imr.getIdAsInt());
                    ps.setInt(2, count_xref);
                    ps.execute();
                    
                    count_xref++;
                } else {
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select imr_id from imr.imr_xref_r where imr_id = ? and xref_id = ?");
                    ps.setInt(1, imr.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into imr.imr_xref_r (imr_id, xref_id) values (?,?)");
                        ps.setInt(1, imr.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.execute();
                    }
                }
            }
            
            //relationship(s)
            Collection rels = imr.getRelationships();
            Iterator n = rels.iterator();
           
            while (n.hasNext()) {
                rel = (ImrDTO_REL)n.next();
                
                System.out.println(">>>working on imr term: "+imr.getIdAsInt()+" and relationship: "+rel.getRelation());
                
                ps = conn.prepareStatement("insert into imr.imr_relationships (id_a, relation, id_b) values (?,?,?)");
                ps.setInt(1, imr.getIdAsInt());
                ps.setString(2, rel.getRelation());
                ps.setInt(3, rel.getIdB());
                ps.execute();
            }
            
            //subset(s)
            Collection subsets = imr.getSubsets();
            Iterator o = subsets.iterator();
           
            while (o.hasNext()) {
                sub = (ImrDTO_SUBSET)o.next();
                ps = conn.prepareStatement("insert into imr.imr_subsets (imr_id, subset) values (?,?)");
                ps.setInt(1, imr.getIdAsInt());
                ps.setString(2, sub.getSubset());
                ps.execute();
            }
            
            //consider(s)
            Collection cons = imr.getConsiders();
            Iterator p = cons.iterator();
           
            while (p.hasNext()) {
                con = (ImrDTO_CON)p.next();
                ps = conn.prepareStatement("insert into imr.imr_consider (id_b, id_a) values (?,?)");
                ps.setInt(1, imr.getIdAsInt());
                ps.setInt(2, con.getConsiderInt());
                ps.execute();
            }
            
            //replaced_by(s)
            Collection reps = imr.getReplaced_bys();
            Iterator q = reps.iterator();
           
            while (q.hasNext()) {
                rep = (ImrDTO_REP)q.next();
                ps = conn.prepareStatement("insert into imr.imr_replaced_by (id_b, id_a) values (?,?)");
                ps.setInt(1, imr.getIdAsInt());
                ps.setInt(2, rep.getReplacedInt());
                ps.execute();
            }
            
        } catch (SQLException se) {
            logger.error("storeIMR: failed to perform.", se);
            throw new ApplicationException("storeIMR: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    //</editor-fold>
    
    //mpath methods
    //<editor-fold defaultstate="collapsed">
    public void loadMPATH() throws ApplicationException {
        try {
            File file = new File("C:/zouberakis/projects/pato/docs/obo.files/mouse_pathology.obo");
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(file));
            
            String line = null;
            MpathDTO mpath = new MpathDTO();
            
            //drop all FK constraints in schema MPATH
            dropconMPATH();
            //delete all data in schema IMR
            cleanMPATH();
            
            while ((line = input.readLine()) != null){
                
                if(line.compareToIgnoreCase("[Typedef]")==0){
                    if(!mpath.getId().equals("")){
                        storeMPATH(mpath);
                    }
                    break;
                }
                
                if(line.compareTo("[Term]")!=0){
                    mpath.IdentifyLine(line);
                }else{
                    if(!mpath.getId().equals("")){
                        storeMPATH(mpath);
                    }
                    mpath = null;
                    mpath = new MpathDTO();
                }
            }
            
            //reconstruct all FK constraints
            restoreconMPATH();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("loadMPATH: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void cleanMPATH() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("delete from mpath.mpath_term");
            ps.execute();
        
            ps = conn.prepareStatement("delete from mpath.mpath_alt_id");
            ps.execute();
            
            ps = conn.prepareStatement("delete from mpath.mpath_alt_id_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from mpath.mpath_is_a");
            ps.execute();
        
            ps = conn.prepareStatement("delete from mpath.mpath_syn");
            ps.execute();
            
            ps = conn.prepareStatement("delete from mpath.mpath_syn_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from mpath.mpath_xref");
            ps.execute();
            
            ps = conn.prepareStatement("delete from mpath.mpath_xref_r");
            ps.execute();
        
            ps = conn.prepareStatement("delete from mpath.mpath_relationships");
            ps.execute();
            
            ps = conn.prepareStatement("delete from mpath.mpath_subsets");
            ps.execute();
            
            ps = conn.prepareStatement("delete from mpath.mpath_consider");
            ps.execute();
            
            ps = conn.prepareStatement("delete from mpath.mpath_replaced_by");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanMPATH: failed to perform.", se);
            throw new ApplicationException("cleanMPATH: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void dropconMPATH() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE mpath.mpath_alt_id_r DROP CONSTRAINT mpath_alt_id_r_alt_id_fk;" +
                    "ALTER TABLE mpath.mpath_alt_id_r DROP CONSTRAINT mpath_alt_id_r_mpath_fk;" +
                    "ALTER TABLE mpath.mpath_is_a DROP CONSTRAINT mpath_is_a_fk_a;" +
                    "ALTER TABLE mpath.mpath_is_a DROP CONSTRAINT mpath_is_a_fk_b;" +
                    "ALTER TABLE mpath.mpath_relationships DROP CONSTRAINT mpath_relationships_a_fk;" +
                    "ALTER TABLE mpath.mpath_relationships DROP CONSTRAINT mpath_relationships_b_fk;" +
                    "ALTER TABLE mpath.mpath_syn_r DROP CONSTRAINT mpath_syn_r_mpath_fk;" +
                    "ALTER TABLE mpath.mpath_syn_r DROP CONSTRAINT mpath_syn_r_syn_fk;" +
                    "ALTER TABLE mpath.mpath_xref_r DROP CONSTRAINT mpath_xref_r_mpath_fk;" +
                    "ALTER TABLE mpath.mpath_xref_r DROP CONSTRAINT mpath_xref_r_xref_fk;" +
                    "ALTER TABLE mpath.mpath_consider DROP CONSTRAINT mpath_consider_fk_a;" +
                    "ALTER TABLE mpath.mpath_consider DROP CONSTRAINT mpath_consider_fk_b;" +
                    "ALTER TABLE mpath.mpath_replaced_by DROP CONSTRAINT mpath_replaced_by_fk_a;" +
                    "ALTER TABLE mpath.mpath_replaced_by DROP CONSTRAINT mpath_replaced_by_fk_b;" +
                    "ALTER TABLE mpath.mpath_subsets DROP CONSTRAINT mpath_subsets_fk;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("dropconMPATH: failed to drop constraints for schema MPATH.", se);
            throw new ApplicationException("dropconMPATH: failed to drop constraints for schema MPATH.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void restoreconMPATH() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE mpath.mpath_alt_id_r ADD " +
                    "CONSTRAINT mpath_alt_id_r_alt_id_fk FOREIGN KEY (mpath_alt_id) " +
                    "REFERENCES mpath.mpath_alt_id (id) MATCH SIMPLE ON UPDATE NO " +
                    "ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_alt_id_r ADD CONSTRAINT " +
                    "mpath_alt_id_r_mpath_fk FOREIGN KEY (mpath_id) REFERENCES " +
                    "mpath.mpath_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_is_a ADD CONSTRAINT mpath_is_a_fk_a " +
                    "FOREIGN KEY (id_a) REFERENCES mpath.mpath_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_is_a ADD CONSTRAINT mpath_is_a_fk_b " +
                    "FOREIGN KEY (id_b) REFERENCES mpath.mpath_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_relationships ADD CONSTRAINT " +
                    "mpath_relationships_a_fk FOREIGN KEY (id_a) REFERENCES " +
                    "mpath.mpath_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON" +
                    " DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_relationships ADD CONSTRAINT " +
                    "mpath_relationships_b_fk FOREIGN KEY (id_b) REFERENCES " +
                    "mpath.mpath_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_syn_r ADD CONSTRAINT mpath_syn_r_mpath_fk " +
                    "FOREIGN KEY (mpath_id) REFERENCES mpath.mpath_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_syn_r ADD CONSTRAINT mpath_syn_r_syn_fk " +
                    "FOREIGN KEY (mpath_syn) REFERENCES mpath.mpath_syn (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_xref_r ADD CONSTRAINT mpath_xref_r_mpath_fk " +
                    "FOREIGN KEY (mpath_id) REFERENCES mpath.mpath_term (id) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_xref_r ADD CONSTRAINT " +
                    "mpath_xref_r_xref_fk FOREIGN KEY (xref_id) REFERENCES " +
                    "mpath.mpath_xref (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_subsets ADD CONSTRAINT " +
                    "mpath_subsets_fk FOREIGN KEY (mpath_id) REFERENCES " +
                    "mpath.mpath_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_consider ADD CONSTRAINT " +
                    "mpath_consider_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "mpath.mpath_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_consider ADD CONSTRAINT " +
                    "mpath_consider_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "mpath.mpath_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_replaced_by ADD CONSTRAINT " +
                    "mpath_replaced_by_fk_a FOREIGN KEY (id_a) REFERENCES " +
                    "mpath.mpath_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE mpath.mpath_replaced_by ADD CONSTRAINT " +
                    "mpath_replaced_by_fk_b FOREIGN KEY (id_b) REFERENCES " +
                    "mpath.mpath_term (id) MATCH SIMPLE ON UPDATE NO ACTION ON " +
                    "DELETE CASCADE;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("restoreconMPATH: failed to restore constraints for schema MPATH.", se);
            throw new ApplicationException("restoreconMPATH: failed to restore constraints for schema MPATH.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void storeMPATH(MpathDTO mpath) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_tmp = null;
        MpathDTO_ISA isa = null;
        MpathDTO_SYN syn = null;
        MpathDTO_ALT alt = null;
        MpathDTO_XREF xref = null;
        MpathDTO_REL rel = null;
        MpathDTO_SUBSET sub = null;
        MpathDTO_REP rep = null;
        MpathDTO_CON con = null;
        
        
        try {
            
            //pheno ontology parameters
            ps = conn.prepareStatement("insert into mpath.mpath_term (id, name, def, def_ref, is_obsolete, comm) values (?,?,?,?,?,?)");
            ps.setInt(1, mpath.getIdAsInt());
            ps.setString(2, mpath.getName());
            ps.setString(3, mpath.getDef());
            ps.setString(4, mpath.getDefRef());
            ps.setInt(5, mpath.getIsObsoleteAsInt());
            ps.setString(6, mpath.getComm());
            ps.execute();
            
            //is a(s)
            Collection is_as = mpath.getIsAs();
            Iterator i = is_as.iterator();
           
            while (i.hasNext()) {
                isa = (MpathDTO_ISA)i.next();
                
                System.out.println(">>>working on mpath term: "+mpath.getIdAsInt()+" and is_a: "+isa.getIsA());
                
                ps = conn.prepareStatement("select id_b from mpath.mpath_is_a where id_b = ? and id_a = ?");
                ps.setInt(1, mpath.getIdAsInt());
                ps.setInt(2, isa.getIsAInt());
                result_tmp = ps.executeQuery();
                
                if(!result_tmp.next()){
                    ps = conn.prepareStatement("insert into mpath.mpath_is_a (id_b, id_a) values (?,?)");
                    ps.setInt(1, mpath.getIdAsInt());
                    ps.setInt(2, isa.getIsAInt());
                    ps.execute();
                }
                
            }
            
            //synonym(s)
            Collection syns = mpath.getSynonyms();
            Iterator k = syns.iterator();
            
            while (k.hasNext()) {
                syn = (MpathDTO_SYN)k.next();
                
                System.out.println(">>>working on mpath term: "+mpath.getIdAsInt()+" and synonym: "+syn.getSynonym());
                
                ps = conn.prepareStatement("select id from mpath.mpath_syn where lower(synonym) like ?");
                ps.setString(1, syn.getSynonym().toLowerCase().replaceAll("\\\\","~"));
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into mpath.mpath_syn (id, synonym) values (?,?)");
                    ps.setInt(1, count_syn);
                    ps.setString(2, syn.getSynonym().replaceAll("\\\\","~"));
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into mpath.mpath_syn_r (mpath_id, mpath_syn, attribute) values (?,?,?)");
                    ps.setInt(1, mpath.getIdAsInt());
                    ps.setInt(2, count_syn);
                    ps.setString(3, syn.getAttribute());
                    ps.execute();
                    
                    count_syn++;
                } else {
                    
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select mpath_id from mpath.mpath_syn_r where mpath_id = ? and mpath_syn = ? and lower(attribute) like ?");
                    ps.setInt(1, mpath.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.setString(3, syn.getAttribute());
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into mpath.mpath_syn_r (mpath_id, mpath_syn, attribute) values (?,?,?)");
                        ps.setInt(1, mpath.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.setString(3, syn.getAttribute());
                        ps.execute();
                    }
                    
                }
            }
            
            //alt id(s)
            Collection alts = mpath.getAltIds();
            Iterator l = alts.iterator();
            
            while (l.hasNext()) {
                alt = (MpathDTO_ALT)l.next();
                ps = conn.prepareStatement("select id from mpath.mpath_alt_id where lower(alt_id) like ?");
                ps.setString(1, alt.getAltId().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into mpath.mpath_alt_id (id, alt_id) values (?,?)");
                    ps.setInt(1, count_alt);
                    ps.setString(2, alt.getAltId());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into mpath.mpath_alt_id_r (mpath_id, mpath_alt_id) values (?,?)");
                    ps.setInt(1, mpath.getIdAsInt());
                    ps.setInt(2, count_alt);
                    ps.execute();
                    
                    count_alt++;
                } else {
                    ps = conn.prepareStatement("insert into mpath.mpath_alt_id_r (mpath_id, mpath_alt_id) values (?,?)");
                    ps.setInt(1, mpath.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    ps.execute();
                }
            }
            
            //xref(s)
            Collection xrefs = mpath.getXrefs();
            Iterator m = xrefs.iterator();
            
            while (m.hasNext()) {
                xref = (MpathDTO_XREF)m.next();
                
                System.out.println(">>>working on mpath term: "+mpath.getIdAsInt()+" and xref: "+xref.getXref());
                
                ps = conn.prepareStatement("select id from mpath.mpath_xref where lower(xref) like ?");
                ps.setString(1, xref.getXref().toLowerCase());
                result = ps.executeQuery();
                if(!result.next()){
                    ps = conn.prepareStatement("insert into mpath.mpath_xref (id, xref) values (?,?)");
                    ps.setInt(1, count_xref);
                    ps.setString(2, xref.getXref());
                    ps.execute();
                    
                    ps = conn.prepareStatement("insert into mpath.mpath_xref_r (mpath_id, xref_id) values (?,?)");
                    ps.setInt(1, mpath.getIdAsInt());
                    ps.setInt(2, count_xref);
                    ps.execute();
                    
                    count_xref++;
                } else {
                    //cheching for double synonyms with double identical attributes
                    ps = conn.prepareStatement("select mpath_id from mpath.mpath_xref_r where mpath_id = ? and xref_id = ?");
                    ps.setInt(1, mpath.getIdAsInt());
                    ps.setInt(2, result.getInt("id"));
                    result_tmp = ps.executeQuery();
                    
                    if(!result_tmp.next()){
                        ps = conn.prepareStatement("insert into mpath.mpath_xref_r (mpath_id, xref_id) values (?,?)");
                        ps.setInt(1, mpath.getIdAsInt());
                        ps.setInt(2, result.getInt("id"));
                        ps.execute();
                    }
                }
            }
            
            //relationship(s)
            Collection rels = mpath.getRelationships();
            Iterator n = rels.iterator();
           
            while (n.hasNext()) {
                rel = (MpathDTO_REL)n.next();
                
                System.out.println(">>>working on mpath term: "+mpath.getIdAsInt()+" and relationship: "+rel.getRelation());
                
                ps = conn.prepareStatement("insert into mpath.mpath_relationships (id_a, relation, id_b) values (?,?,?)");
                ps.setInt(1, mpath.getIdAsInt());
                ps.setString(2, rel.getRelation());
                ps.setInt(3, rel.getIdB());
                ps.execute();
            }
            
            //subset(s)
            Collection subsets = mpath.getSubsets();
            Iterator o = subsets.iterator();
           
            while (o.hasNext()) {
                sub = (MpathDTO_SUBSET)o.next();
                ps = conn.prepareStatement("insert into mpath.mpath_subsets (mpath_id, subset) values (?,?)");
                ps.setInt(1, mpath.getIdAsInt());
                ps.setString(2, sub.getSubset());
                ps.execute();
            }
            
            //consider(s)
            Collection cons = mpath.getConsiders();
            Iterator p = cons.iterator();
           
            while (p.hasNext()) {
                con = (MpathDTO_CON)p.next();
                ps = conn.prepareStatement("insert into mpath.mpath_consider (id_b, id_a) values (?,?)");
                ps.setInt(1, mpath.getIdAsInt());
                ps.setInt(2, con.getConsiderInt());
                ps.execute();
            }
            
            //replaced_by(s)
            Collection reps = mpath.getReplaced_bys();
            Iterator q = reps.iterator();
           
            while (q.hasNext()) {
                rep = (MpathDTO_REP)q.next();
                ps = conn.prepareStatement("insert into mpath.mpath_replaced_by (id_b, id_a) values (?,?)");
                ps.setInt(1, mpath.getIdAsInt());
                ps.setInt(2, rep.getReplacedInt());
                ps.execute();
            }
            
        } catch (SQLException se) {
            logger.error("storeMAPTH: failed to perform.", se);
            throw new ApplicationException("storeMPATH: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    //</editor-fold>
    
    //mp_xp methods
    //<editor-fold defaultstate="collapsed">
    public void loadXP() throws ApplicationException {
        try {
            File file = new File("C:/zouberakis/projects/pato/docs/obo.files/mammalian_phenotype_xp.obo");
            
            BufferedReader input = null;
            input = new BufferedReader(new FileReader(file));
            
            String line = null;
            XpDTO xp = new XpDTO();
            
            //drop all FK constraints in schema XP
            dropconXP();
            //delete all data in schema XP
            cleanXP();
            
            while ((line = input.readLine()) != null){
                
                if(line.compareToIgnoreCase("[Typedef]")==0){
                    if(!xp.getMpid().equals("")){
                        storeXP(xp);
                    }
                    break;
                }
                
                if(line.compareTo("[Term]")!=0 && !line.startsWith("!")){
                    xp.IdentifyLine(line);
                }else{
                    if(!xp.getMpid().equals("")){
                        storeXP(xp);
                    }
                    xp = null;
                    xp = new XpDTO();
                }
            }
            
            //reconstruct all FK constraints
            restoreconXP();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("loadXP: failed to perform.\n"+e.getMessage());            
        }
    }
    
    public void cleanXP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("delete from xp.xp_mp_r");
            ps.execute();
            
            ps = conn.prepareStatement("delete from xp.xp_term");
            ps.execute();
            
        } catch (SQLException se) {
            logger.error("cleanXP: failed to perform.", se);
            throw new ApplicationException("cleanXP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void dropconXP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE xp.xp_mp_r DROP CONSTRAINT mp_fk;" +
                    "ALTER TABLE xp.xp_mp_r DROP CONSTRAINT xp_fk;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("dropconXP: failed to drop constraints for schema XP.", se);
            throw new ApplicationException("dropconXP: failed to drop constraints for schema XP.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void restoreconXP() throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            
            ps = conn.prepareStatement("ALTER TABLE xp.xp_mp_r ADD CONSTRAINT " +
                    "mp_fk FOREIGN KEY (mpid) " +
                    "REFERENCES pheno_ontology (id) MATCH SIMPLE " +
                    "ON UPDATE NO ACTION ON DELETE CASCADE;" +
                    "" +
                    "ALTER TABLE xp.xp_mp_r ADD CONSTRAINT xp_fk " +
                    "FOREIGN KEY (xpid) REFERENCES xp.xp_term (xpid) MATCH " +
                    "SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;");
            ps.execute();
        
            
        } catch (SQLException se) {
            logger.error("restoreconXP: failed to restore constraints for schema XP.", se);
            throw new ApplicationException("restoreconXP: failed to restore constraints for schema XP.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void storeXP(XpDTO xp) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        ResultSet result_tmp = null;
        XpDTO_REL rel = null;
        
        int count_xp_tmp;
        boolean insert_xp = true;
        
        try {
            
            ps = conn.prepareStatement("select id from pheno_ontology where id = ?");
            ps.setInt(1, xp.getMpidAsInt());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
            
                Collection rels = xp.getRelationships();
                Iterator n = rels.iterator();
           
                while (n.hasNext()) {
                    rel = (XpDTO_REL)n.next();
                
                    System.out.println(">>>working on mp term: "+xp.getMpidAsInt()+" and relationship: "+rel.getRelation()+" with term "+rel.getSch()+":"+rel.getSchid());
                
                    ps = conn.prepareStatement("select xpid from xp.xp_term where sch = ? and schid = ? and relation = ?");
                    ps.setString(1, rel.getSch());
                    ps.setInt(2, rel.getSchid());
                    ps.setString(3, rel.getRelation());
                    rs = ps.executeQuery();
                
                    if(rs.next()){
                        count_xp_tmp = rs.getInt("xpid");
                        insert_xp = false;
                    } else {
                        count_xp_tmp = count_xp;
                        count_xp++;
                        insert_xp = true;
                    }
                
                    if(insert_xp){
                        ps = conn.prepareStatement("insert into xp.xp_term (xpid, sch, schid, relation) values (?,?,?,?)");
                        ps.setInt(1, count_xp_tmp);
                        ps.setString(2, rel.getSch());
                        ps.setInt(3, rel.getSchid());
                        ps.setString(4, rel.getRelation());
                        ps.execute();
                    }
                
                    ps = conn.prepareStatement("insert into xp.xp_mp_r (mpid, xpid, apporder) values (?,?,?)");
                    ps.setInt(1, xp.getMpidAsInt());
                    ps.setInt(2, count_xp_tmp);
                    ps.setInt(3, rel.getApporder());
                    ps.execute();
                }
            }//if
            
        } catch (SQLException se) {
            logger.error("storeXP: failed to perform.", se);
            throw new ApplicationException("storeXP: failed to perform.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    //</editor-fold>
    
    //questionnaire methods (no ejbs involved yet)
    //<editor-fold defaultstate="collapsed">
    
    public int createQuestionnaireEntry(String name, String inst, String mail, String [] qs) throws ApplicationException{
        try
        {
            makeConnection();
            
            int qid = getIIdGenerator().getNextId(conn, "qa_seq");
            
            PreparedStatement ps = conn.prepareStatement("insert into quest_a (qid, name, inst, mail) values (?,?,?,?)");
            ps.setInt(1, qid);
            ps.setString(2, name);
            ps.setString(3, inst);
            ps.setString(4, mail);
            ps.execute();
            
            String sql = "";
            
            for(int i = 1; i <= qs.length; i++){
                sql = "update quest_a set q"+i+"='"+qs[i-1]+"' where qid="+qid;
                //System.out.println(sql);
                PreparedStatement ps_ = conn.prepareStatement(sql);
                ps_.execute();
            }
            
            return qid;
        } catch (Exception e) {
            throw new ApplicationException("Failed to store questionnaire.",e);
        } finally {
            releaseConnection();
        }
    }
    //</editor-fold>
    
    //web services methods
    //<editor-fold defaultstate="collapsed">
    public String [] getMugenMice() throws ApplicationException {
        try {
            Collection models = modelHome.findByWebServiceRequest();
            String [] modelsws = new String [models.size()];
            Iterator i = models.iterator();
            ExpModelRemote tmp = null;
            int index = 0;
            while (i.hasNext()) {
                tmp = (ExpModelRemote)i.next();
                modelsws[index] = "<tr><td>"+tmp.getEid()+"</td><td>"+tmp.getAlias()+"</td></tr>";
                index++;
            }
            return modelsws;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    
    public MugenModelDTO [] getMugenMiceDTO() throws ApplicationException {
        try {
            Collection models = modelHome.findByWebServiceRequest();
            MugenModelDTO [] modelsws = new MugenModelDTO [models.size()];
            Iterator i = models.iterator();
            
            MugenModelDTO tmp = null;
            int index = 0;
            while (i.hasNext()) {
                ExpModelRemote model = ((ExpModelRemote)i.next());
                tmp = new MugenModelDTO();
                tmp.setEid(model.getEid());
                tmp.setAcc(model.getIdentity());
                tmp.setLine(model.getAlias());
                tmp.setDesignation(model.getStrain().getDesignation().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>"));
                tmp.setMutations(model.getMutationTypesForModel());
                modelsws[index] = tmp;
                index++;
            }
            return modelsws;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("WS:failed to get models.");
        }
    }
    
    public MugenModelDTO [] getMugenMiceDTOByKey(String key) throws ApplicationException {
        try {
            Collection models = modelHome.findByWebServiceKeywordRequest(key);
            MugenModelDTO [] modelsws = new MugenModelDTO [models.size()];
            Iterator i = models.iterator();
            
            MugenModelDTO tmp = null;
            int index = 0;
            while (i.hasNext()) {
                ExpModelRemote model = ((ExpModelRemote)i.next());
                tmp = new MugenModelDTO();
                tmp.setEid(model.getEid());
                tmp.setAcc(model.getIdentity());
                tmp.setLine(model.getAlias());
                tmp.setDesignation(model.getStrain().getDesignation());
                tmp.setMutations(model.getMutationTypesForModel());
                modelsws[index] = tmp;
                index++;
            }
            return modelsws;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("WS:failed to get models.");
        }
    }
    
    public MugenGeneDTO [] getMugenGenesByModel(int eid) throws ApplicationException {
        try {
            Collection genes = geneHome.findByModel(eid);
            MugenGeneDTO [] genesws = new MugenGeneDTO [genes.size()];
            Iterator i = genes.iterator();
            
            MugenGeneDTO tmp = null;
            int index = 0;
            while (i.hasNext()) {
                GeneRemote gene = ((GeneRemote)i.next());
                tmp = new MugenGeneDTO();
                tmp.setGid(gene.getGaid());
                tmp.setName(gene.getName());
                tmp.setSymbol(gene.getGenesymbol());
                tmp.setChromosome(gene.getChromosome().getName());
                tmp.setMgiid(gene.getMgiid().trim());
                genesws[index] = tmp;
                index++;
            }
            return genesws;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("WS:failed to get genes.");
        }
    }
    
    public MugenAvailabilityDTO [] getMugenAvailabilityByModel(int eid) throws ApplicationException {
        try {
            Collection avs = availabilityHome.findByModel(eid);
            MugenAvailabilityDTO [] avsws = new MugenAvailabilityDTO [avs.size()];
            Iterator i = avs.iterator();
            
            MugenAvailabilityDTO tmp = null;
            int index = 0;
            while (i.hasNext()) {
                AvailabilityRemote av = ((AvailabilityRemote)i.next());
                tmp = new MugenAvailabilityDTO();
                tmp.setRepository(av.getRepositoryName());
                tmp.setRepositorylink(av.getRepositoryURL());
                tmp.setBackground(av.getAvailableGeneticBackgroundName());
                tmp.setState(av.getStateName());
                tmp.setType(av.getTypeName());
                avsws[index] = tmp;
                index++;
            }
            return avsws;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("WS:failed to get availability info.");
        }
    }
    
    public MugenBackgroundDTO [] getMugenBackgroundByModel(int eid) throws ApplicationException {
        try {
            Collection backs = genbackHome.findByGeneticBackgroundModel(eid);
            MugenBackgroundDTO [] backsws = new MugenBackgroundDTO [backs.size()];
            Iterator i = backs.iterator();
            
            MugenBackgroundDTO tmp = null;
            int index = 0;
            while (i.hasNext()) {
                GeneticBackgroundDTO back = new GeneticBackgroundDTO((GeneticBackgroundRemote)i.next());
                tmp = new MugenBackgroundDTO();
                tmp.setDna(back.getDna_origin_namess());
                tmp.setTarget(back.getTargeted_back_namess());
                tmp.setHost(back.getHost_back_namess());
                tmp.setBackcross(back.getBackcrossing_strain_namess());
                tmp.setBackcrosses(back.getBackcrosses());
                backsws[index] = tmp;
                index++;
            }
            return backsws;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("WS:failed to get genetic background info.");
        }
    }
    
    public MugenPhenotypesDTO[] getMugenPhenotypesByModel (int eid) throws ApplicationException {
        try {
            Collection phps = phenoPathHome.findByModel(eid);
            MugenPhenotypesDTO[] phenos = new MugenPhenotypesDTO[phps.size()];
            Iterator i = phps.iterator();
            
            MugenPhenotypesDTO pheno = null;
            int index = 0;
            while(i.hasNext()) {
                pathsMP tmp = new pathsMP((PhenotypeOntologyPathRemote)i.next());
                pheno = new MugenPhenotypesDTO();
                pheno.setPhenotype(tmp.getMpnameshort());
                phenos[index] = pheno;
                index++;
            }
            
            return phenos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("WS: failed to get mps for model.", e);
        }
    }
    
    public String [] getMugenMiceByEnsembl(String ensembl) throws ApplicationException {
        try {
            Collection models = modelHome.findByWebServiceEnsemblRequest(ensembl);
            String [] modelsws = new String [models.size()];
            Iterator i = models.iterator();
            ExpModelRemote tmp = null;
            int index = 0;
            while (i.hasNext()) {
                tmp = (ExpModelRemote)i.next();
                //modelsws[index] = "http://195.251.21.2/mugen/DirectView?workflow=ViewModelDirect&expand_all=true&name_begins=model.block&eid="+tmp.getEid();
                modelsws[index] = "<a href=\"http://195.251.21.2/mugen/DirectView?workflow=ViewModelDirect&expand_all=true&name_begins=model.block&eid="+tmp.getEid()+"\" target=\"_blank\" title=\"view mugen mouse\">"+tmp.getStrain().getDesignation().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>")+"</a>";
                index++;
            }
            return modelsws;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get models.");
        }
    }
    //</editor-fold>

}


