package com.arexis.mugen.servicelocator;

import com.arexis.mugen.adminmanager.AdminManagerRemoteHome;
import com.arexis.mugen.resource.file.FileRemoteHome;
import com.arexis.mugen.export.ExportManagerRemoteHome;
import com.arexis.mugen.export.filter.GQLFilterRemoteHome;
import com.arexis.mugen.genotype.allele.AlleleRemoteHome;
import com.arexis.mugen.genotype.genotype.GenotypeRemoteHome;
import com.arexis.mugen.genotype.genotypemanager.GenotypeManagerRemoteHome;
import com.arexis.mugen.genotype.marker.MarkerRemoteHome;
import com.arexis.mugen.genotype.markerset.MarkerSetRemoteHome;
import com.arexis.mugen.genotype.uallele.UAlleleRemoteHome;
import com.arexis.mugen.genotype.umarker.UMarkerRemoteHome;
import com.arexis.mugen.genotype.umarkerset.UMarkerSetRemoteHome;
import com.arexis.mugen.model.expmodel.ExpModelRemoteHome;
import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemoteHome;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemoteHome;
import com.arexis.mugen.species.gene.GeneRemoteHome;
import com.arexis.mugen.model.geneontology.GeneOntologyRemoteHome;
import com.arexis.mugen.model.geneticmodification.GeneticModificationRemoteHome;
import com.arexis.mugen.model.modelmanager.ModelManagerRemoteHome;
import com.arexis.mugen.model.reference.ReferenceRemoteHome;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemoteHome;
import com.arexis.mugen.model.strain.allele.StrainAlleleRemoteHome;
import com.arexis.mugen.model.strain.mutationtype.MutationTypeRemoteHome;
import com.arexis.mugen.model.strain.state.StrainStateRemoteHome;
import com.arexis.mugen.model.strain.strain.StrainRemoteHome;
import com.arexis.mugen.model.strain.type.StrainTypeRemoteHome;
import com.arexis.mugen.phenotype.phenotype.PhenotypeRemoteHome;
import com.arexis.mugen.phenotype.phenotypemanager.PhenotypeManagerRemoteHome;
import com.arexis.mugen.phenotype.uvariable.UVariableRemoteHome;
import com.arexis.mugen.phenotype.uvariableset.UVariableSetRemoteHome;
import com.arexis.mugen.phenotype.variable.VariableRemoteHome;
import com.arexis.mugen.phenotype.variableset.VariableSetRemoteHome;
import com.arexis.mugen.project.privilege.PrivilegeRemoteHome;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.project.projectmanager.ProjectManagerRemoteHome;
import com.arexis.mugen.project.role.RoleRemoteHome;
import com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemoteHome;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.resource.link.LinkRemoteHome;
import com.arexis.mugen.resource.resource.ResourceRemoteHome;
import com.arexis.mugen.resource.resourcecategory.ResourceCategoryRemoteHome;
import com.arexis.mugen.resource.resourcemanager.ResourceManagerRemoteHome;
import com.arexis.mugen.samplingunit.group.GroupRemoteHome;
import com.arexis.mugen.samplingunit.grouping.GroupingRemoteHome;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemoteHome;
import com.arexis.mugen.simplelog.SimpleLogRemoteHome;
import com.arexis.mugen.species.chromosome.ChromosomeRemoteHome;
import com.arexis.mugen.species.lmarker.LMarkerRemoteHome;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import java.lang.reflect.Method;
import java.util.HashMap;
import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import com.arexis.mugen.model.geneticbackground.GeneticBackgroundRemoteHome;
import com.arexis.mugen.model.geneticbackground.GeneticBackgroundValuesRemoteHome;
import com.arexis.mugen.model.availability.AvailabilityRemoteHome;
import com.arexis.mugen.model.availablegeneticbackgrounds.AvailableGeneticBackgroundRemoteHome;
import com.arexis.mugen.model.repositories.RepositoriesRemoteHome;

import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyRemoteHome;
import com.arexis.mugen.phenotype.ontology.PhenotypeXrefRemoteHome;
import com.arexis.mugen.phenotype.ontology.PhenotypeAltIdRemoteHome;
import com.arexis.mugen.phenotype.ontology.PhenotypeSynonymRemoteHome;
import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyPathRemoteHome;

import com.arexis.mugen.model.usercomments.UserCommsRemoteHome;
import com.arexis.mugen.model.usercomments.UserCommsRemote;

public class ServiceLocator {
    // singleton's private instance
    private static ServiceLocator me;
    private static int totalTime;
    
    static {
        me = new ServiceLocator();
        totalTime=0;
        cache = new HashMap();
    }
    
    private ServiceLocator() {}
    
    // returns the Service Locator instance
    static public ServiceLocator getInstance() {
        return me;
    }
    
    
    // Services Constants Inner Class - service objects
    public class Services {
        final public static int PROJECT  = 0;
        final public static int SAMPLINGUNIT = 1;
        final public static int USER = 2;
        final public static int INDIVIDUAL = 3;
        final public static int GENOTYPE = 4;
        final public static int ALLELE = 5;
        final public static int MARKER = 6;
        final public static int MARKERSET = 7;
        final public static int UMARKERSET = 8;
        final public static int UMARKER = 9;
        final public static int UALLELE = 10;
        final public static int CHROMOSOME = 11;
        final public static int SPECIES = 12;
        final public static int FILE = 13;
        final public static int GROUPING = 14;
        final public static int PHENOTYPE = 15;
        final public static int PHENOTYPEMANAGER = 16;
        final public static int SAMPLINGUNITMANAGER = 17;
        final public static int GENOTYPEMANAGER = 18;
        final public static int PROJECTMANAGER = 19;
        
        
        
        final public static int LMARKER = 21;
        final public static int VARIABLE= 22;
        final public static int ROLE= 23;
        final public static int SECURITYPRINCIPLE = 24;
        final public static int PRIVILEGE = 25;
        final public static int VARIABLESET = 26;
        final public static int UVARIABLE = 27;
        final public static int UVARIABLESET = 28;

        
        
        final public static int GROUP = 30;
        final public static int SAMPLE = 31;
        final public static int EXPORTMANAGER = 32;
        final public static int GQLFILTER = 33;
        final public static int LINK = 34;
        final public static int RESOURCEMANAGER = 35;
        final public static int EXPMODEL = 36;
        final public static int GENETICMODIFICATION = 37;
        final public static int RESEARCHAPPLICATION = 38;
        final public static int MODELMANAGER = 39;
        final public static int GENEONTOLOGY = 40;
        final public static int FUNCTIONALSIGNIFICANCETYPE = 41;
        final public static int FUNCTIONALSIGNIFICANCE = 42;
        final public static int GENE = 43;
        final public static int REFERENCE = 44;
        final public static int SIMPLELOG = 45;
        final public static int ADMINMANAGER = 46;
        final public static int RESOURCE = 47;
        final public static int RESOURCECATEGORY = 48;
        final public static int PATHWAY = 49;
        final public static int PROCESS = 50;
        final public static int CELLULARMANAGER = 51;
        final public static int PROTEINMANAGER = 52;
        final public static int PROTEIN = 53;
        final public static int PROTEINFAMILY = 54;
        final public static int PROTEINCOMPLEX = 55;
        final public static int STRAIN = 56;
        final public static int STRAIN_TYPE = 57;
        final public static int STRAIN_STATE = 58;
        final public static int STRAIN_ALLELE = 59;
        final public static int MUTATION_TYPE = 60;
        final public static int GENETIC_BACKGROUND = 61;
        final public static int GENETIC_BACKGROUND_VALUES = 62;
        final public static int REPOSITORIES = 63;
        final public static int AVAILABLE_GENETIC_BACKGROUNDS = 64;
        final public static int AVAILABILITY = 65;
        
        final public static int PHENOTYPE_ONTOLOGY = 66;
        final public static int PHENOTYPE_ONTOLOGY_XREF = 67;
        final public static int PHENOTYPE_ONTOLOGY_ALT_ID = 68;
        final public static int PHENOTYPE_ONTOLOGY_SYNONYM = 69;
        final public static int PHENOTYPE_ONTOLOGY_PATH = 71;
        
        final public static int USER_COMMENTS = 70;
        
    }
    
    // Project EJB related constants
    final static Class  PROJECT_CLASS = ProjectRemoteHome.class;
    final static String PROJECT_NAME  = "ejb/ProjectBean";
    
    final static Class  SU_CLASS = SamplingUnitRemoteHome.class;
    final static String SU_NAME  = "ejb/SamplingUnitBean";
    
    final static Class  SUMANAGER_CLASS = SamplingUnitManagerRemoteHome.class;
    final static String SUMANAGER_NAME  = "ejb/SamplingUnitManagerBean";
    
    final static Class USER_CLASS = UserRemoteHome.class;
    final static String USER_NAME = "ejb/UserBean";
    
    final static Class INDIVIDUAL_CLASS = IndividualRemoteHome.class;
    final static String INDIVIDUAL_NAME = "ejb/IndividualBean";
    
    final static Class GENOTYPE_CLASS = GenotypeRemoteHome.class;
    final static String GENOTYPE_NAME = "ejb/GenotypeBean";
    
    final static Class ALLELE_CLASS = AlleleRemoteHome.class;
    final static String ALLELE_NAME = "ejb/AlleleBean";
    
    final static Class MARKER_CLASS = MarkerRemoteHome.class;
    final static String MARKER_NAME = "ejb/MarkerBean";
    
    final static Class MARKERSET_CLASS = MarkerSetRemoteHome.class;
    final static String MARKERSET_NAME = "ejb/MarkerSetBean";
    
    final static Class UMARKERSET_CLASS = UMarkerSetRemoteHome.class;
    final static String UMARKERSET_NAME = "ejb/UMarkerSetBean";
    
    final static Class UMARKER_CLASS = UMarkerRemoteHome.class;
    final static String UMARKER_NAME = "ejb/UMarkerBean";
    
    final static Class UALLELE_CLASS = UAlleleRemoteHome.class;
    final static String UALLELE_NAME = "ejb/UAlleleBean";
    
    final static Class CHROMOSOME_CLASS = ChromosomeRemoteHome.class;
    final static String CHROMOSOME_NAME = "ejb/ChromosomeBean";
    
    final static Class SPECIES_CLASS = SpeciesRemoteHome.class;
    final static String SPECIES_NAME = "ejb/SpeciesBean";
            
    final static Class FILE_CLASS = FileRemoteHome.class;
    final static String FILE_NAME = "ejb/FileBean";
    
    final static Class GROUPING_CLASS = GroupingRemoteHome.class;
    final static String GROUPING_NAME = "ejb/GroupingBean";
    
    final static Class PHENOTYPE_CLASS = PhenotypeRemoteHome.class;
    final static String PHENOTYPE_NAME = "ejb/PhenotypeBean";
    
    final static Class PHENOTYPEMANAGER_CLASS = PhenotypeManagerRemoteHome.class;
    final static String PHENOTYPEMANAGER_NAME = "ejb/PhenotypeManagerBean";
    
    final static Class GENOTYPEMANAGER_CLASS = GenotypeManagerRemoteHome.class;
    final static String GENOTYPEMANAGER_NAME = "ejb/GenotypeManagerBean";
    
    final static Class PROJECTMANAGER_CLASS = ProjectManagerRemoteHome.class;
    final static String PROJECTMANAGER_NAME = "ejb/ProjectManagerBean";
    
    final static Class LMARKER_CLASS = LMarkerRemoteHome.class;
    final static String LMARKER_NAME = "ejb/LMarkerBean";
    
    final static Class VARIABLE_CLASS = VariableRemoteHome.class;
    final static String VARIABLE_NAME = "ejb/VariableBean";
            
    final static Class ROLE_CLASS = RoleRemoteHome.class;
    final static String ROLE_NAME = "ejb/RoleBean";
    
    final static Class SECURITYPRINCIPLE_CLASS = SecurityPrincipleRemoteHome.class;
    final static String SECURITYPRINCIPLE_NAME = "ejb/SecurityPrincipleBean";
    
    final static Class PRIVILEGE_CLASS = PrivilegeRemoteHome.class;
    final static String PRIVILEGE_NAME = "ejb/PrivilegeBean";
    
    final static Class VARIABLESET_CLASS = VariableSetRemoteHome.class;
    final static String VARIABLESET_NAME = "ejb/VariableSetBean";
    
    final static Class UVARIABLESET_CLASS = UVariableSetRemoteHome.class;
    final static String UVARIABLESET_NAME = "ejb/UVariableSetBean";
    
    final static Class UVARIABLE_CLASS = UVariableRemoteHome.class;
    final static String UVARIABLE_NAME = "ejb/UVariableBean";
    
    final static Class GROUP_CLASS = GroupRemoteHome.class;
    final static String GROUP_NAME = "ejb/GroupBean";
    
    final static Class EXPORTMANAGER_CLASS = ExportManagerRemoteHome.class;
    final static String EXPORTMANAGER_NAME = "ejb/ExportManagerBean";  
    
    final static Class GQLFILTER_CLASS = GQLFilterRemoteHome.class;
    final static String GQLFILTER_NAME = "ejb/GQLFilterBean";      
    
    final static Class LINK_CLASS = LinkRemoteHome.class;
    final static String LINK_NAME = "ejb/LinkBean";      
    
    final static Class RESOURCEMANAGER_CLASS = ResourceManagerRemoteHome.class;
    final static String RESOURCEMANAGER_NAME = "ejb/ResourceManagerBean";       

    final static Class EXPMODEL_CLASS = ExpModelRemoteHome.class;
    final static String EXPMODEL_NAME = "ejb/ExpModelBean";     
    
    final static Class GENETICMODIFICATION_CLASS = GeneticModificationRemoteHome.class;
    final static String GENETICMODIFICATION_NAME = "ejb/GeneticModificationBean";
    
    
    final static Class RESEARCHAPPLICATION_CLASS = ResearchApplicationRemoteHome.class;
    final static String RESEARCHAPPLICATION_NAME = "ejb/ResearchApplicationBean";
    
    final static Class MODELMANAGER_CLASS = ModelManagerRemoteHome.class;
    final static String MODELMANAGER_NAME = "ejb/ModelManagerBean";    
    
    final static Class GENEONTOLOGY_CLASS = GeneOntologyRemoteHome.class;
    final static String GENEONTOLOGY_NAME = "ejb/GeneOntologyBean";
    
    final static Class FUNCTIONALSIGNIFICANCETYPE_CLASS = FunctionalSignificanceTypeRemoteHome.class;
    final static String FUNCTIONALSIGNIFICANCETYPE_NAME = "ejb/FunctionalSignificanceTypeBean";
    
    final static Class FUNCTIONALSIGNIFICANCE_CLASS = FunctionalSignificanceRemoteHome.class;
    final static String FUNCTIONALSIGNIFICANCE_NAME = "ejb/FunctionalSignificanceBean";
    
    final static Class GENE_CLASS = GeneRemoteHome.class;
    final static String GENE_NAME = "ejb/GeneBean";
    
    final static Class REFERENCE_CLASS = ReferenceRemoteHome.class;
    final static String REFERENCE_NAME = "ejb/ReferenceBean";
    
    final static Class SIMPLELOG_CLASS = SimpleLogRemoteHome.class;
    final static String SIMPLELOG_NAME = "ejb/SimpleLogBean";
    
    final static Class ADMINMANAGER_CLASS = AdminManagerRemoteHome.class;
    final static String ADMINMANAGER_NAME = "ejb/AdminManagerBean";
    
    final static Class RESOURCE_CLASS = ResourceRemoteHome.class;
    final static String RESOURCE_NAME = "ejb/ResourceBean";
    
    final static Class RESOURCECATEGORY_CLASS = ResourceCategoryRemoteHome.class;
    final static String RESOURCECATEGORY_NAME = "ejb/ResourceCategoryBean";   
    
    final static Class STRAIN_CLASS = StrainRemoteHome.class;
    final static String STRAIN_NAME = "ejb/StrainBean"; 
    
    final static Class STRAIN_TYPE_CLASS = StrainTypeRemoteHome.class;
    final static String STRAIN_TYPE_NAME = "ejb/StrainTypeBean"; 
    
    final static Class STRAIN_STATE_CLASS = StrainStateRemoteHome.class;
    final static String STRAIN_STATE_NAME = "ejb/StrainStateBean"; 
    
    final static Class STRAIN_ALLELE_CLASS = StrainAlleleRemoteHome.class;
    final static String STRAIN_ALLELE_NAME = "ejb/StrainAlleleBean"; 
    
    final static Class MUTATION_TYPE_CLASS = MutationTypeRemoteHome.class;
    final static String MUTATION_TYPE_NAME = "ejb/MutationTypeBean"; 
    
    final static Class GENETIC_BACKGROUND_CLASS = GeneticBackgroundRemoteHome.class;
    final static String GENETIC_BACKGROUND_NAME = "ejb/GeneticBackgroundBean";
    
    final static Class GENETIC_BACKGROUND_VALUES_CLASS = GeneticBackgroundValuesRemoteHome.class;
    final static String GENETIC_BACKGROUND_VALUES_NAME = "ejb/GeneticBackgroundValuesBean";
    
    final static Class REPOSITORIES_CLASS = RepositoriesRemoteHome.class;
    final static String REPOSITORIES_NAME = "ejb/RepositoriesBean";
    
    final static Class AVAILABLE_GENETIC_BACKGROUNDS_CLASS = AvailableGeneticBackgroundRemoteHome.class;
    final static String AVAILABLE_GENETIC_BACKGROUNDS_NAME = "ejb/AvailableGeneticBackgroundBean";
   
    final static Class AVAILABILITY_CLASS = AvailabilityRemoteHome.class;
    final static String AVAILABILITY_NAME = "ejb/AvailabilityBean";
    
    final static Class PHENOTYPE_ONTOLOGY_CLASS = PhenotypeOntologyRemoteHome.class;
    final static String PHENOTYPE_ONTOLOGY_NAME = "ejb/PhenotypeOntologyBean";
    
    final static Class PHENOTYPE_ONTOLOGY_XREF_CLASS = PhenotypeXrefRemoteHome.class;
    final static String PHENOTYPE_ONTOLOGY_XREF_NAME = "ejb/PhenotypeXrefBean";
    
    final static Class PHENOTYPE_ONTOLOGY_ALT_ID_CLASS = PhenotypeAltIdRemoteHome.class;
    final static String PHENOTYPE_ONTOLOGY_ALT_ID_NAME = "ejb/PhenotypeAltIdBean";
    
    final static Class PHENOTYPE_ONTOLOGY_SYNONYM_CLASS = PhenotypeSynonymRemoteHome.class;
    final static String PHENOTYPE_ONTOLOGY_SYNONYM_NAME = "ejb/PhenotypeSynonymBean";
    
    final static Class PHENOTYPE_ONTOLOGY_PATH_CLASS = PhenotypeOntologyPathRemoteHome.class;
    final static String PHENOTYPE_ONTOLOGY_PATH_NAME = "ejb/PhenotypeOntologyPathBean";
    
    final static Class USER_COMMENTS_CLASS = UserCommsRemoteHome.class;
    final static String USER_COMMENTS_NAME = "ejb/UserCommsBean";
    
    private static EJBHome cacheProject;
    
    // Returns the Class for the required service
    static private Class getServiceClass(int service){
        switch( service ) {
            case Services.PROJECT:
                return PROJECT_CLASS;
            case Services.SAMPLINGUNIT:
                return SU_CLASS;
            case Services.USER:
                return USER_CLASS;
            case Services.INDIVIDUAL:
                return INDIVIDUAL_CLASS;
            case Services.GENOTYPE:
                return GENOTYPE_CLASS;
            case Services.ALLELE:
                return ALLELE_CLASS;
            case Services.MARKER:
                return MARKER_CLASS;
            case Services.MARKERSET:
                return MARKERSET_CLASS;
           case Services.UMARKERSET:
                return UMARKERSET_CLASS;
            case Services.UMARKER:
                return UMARKER_CLASS;
            case Services.UALLELE:
                return UALLELE_CLASS;
            case Services.CHROMOSOME:
                return CHROMOSOME_CLASS;
            case Services.SPECIES:
                return SPECIES_CLASS;
            case Services.FILE:
                return FILE_CLASS;
            case Services.GROUPING:
                return GROUPING_CLASS;
            case Services.PHENOTYPE:
                return PHENOTYPE_CLASS;
            case Services.PHENOTYPEMANAGER:
                return PHENOTYPEMANAGER_CLASS;
            case Services.SAMPLINGUNITMANAGER:
                return SUMANAGER_CLASS;
            case Services.GENOTYPEMANAGER:
                return GENOTYPEMANAGER_CLASS;
            case Services.PROJECTMANAGER:
                return PROJECTMANAGER_CLASS;                     
            case Services.LMARKER:
                return LMARKER_CLASS;
            case Services.VARIABLE:
                return VARIABLE_CLASS;
            case Services.ROLE:
                return ROLE_CLASS;
            case Services.SECURITYPRINCIPLE:
                return SECURITYPRINCIPLE_CLASS;
            case Services.PRIVILEGE:
                return PRIVILEGE_CLASS;
            case Services.UVARIABLE:
                return UVARIABLE_CLASS;
            case Services.UVARIABLESET:
                return UVARIABLESET_CLASS;
            case Services.VARIABLESET:
                return VARIABLESET_CLASS;
            case Services.GROUP:
                return GROUP_CLASS;
            case Services.EXPORTMANAGER:
                return EXPORTMANAGER_CLASS;   
            case Services.GQLFILTER:
                return GQLFILTER_CLASS;       
            case Services.LINK:
                return LINK_CLASS;    
            case Services.RESOURCEMANAGER:
                return RESOURCEMANAGER_CLASS;
            case Services.EXPMODEL:
                return EXPMODEL_CLASS;    
            case Services.GENETICMODIFICATION:
                return GENETICMODIFICATION_CLASS;                
            case Services.RESEARCHAPPLICATION:
                return RESEARCHAPPLICATION_CLASS;
            case Services.MODELMANAGER:
                return MODELMANAGER_CLASS;     
            case Services.GENEONTOLOGY:
                return GENEONTOLOGY_CLASS;
            case Services.FUNCTIONALSIGNIFICANCETYPE:
                return FUNCTIONALSIGNIFICANCETYPE_CLASS;
            case Services.FUNCTIONALSIGNIFICANCE:
                return FUNCTIONALSIGNIFICANCE_CLASS;
            case Services.GENE:
                return GENE_CLASS;
            case Services.REFERENCE:
                return REFERENCE_CLASS;    
            case Services.SIMPLELOG:
                return SIMPLELOG_CLASS;
            case Services.ADMINMANAGER:
                return ADMINMANAGER_CLASS;
            case Services.RESOURCE:
                return RESOURCE_CLASS;
            case Services.RESOURCECATEGORY:
                return RESOURCECATEGORY_CLASS;     
            case Services.STRAIN:
                return STRAIN_CLASS;
            case Services.STRAIN_TYPE:
                return STRAIN_TYPE_CLASS;    
            case Services.STRAIN_STATE:
                return STRAIN_STATE_CLASS; 
            case Services.STRAIN_ALLELE:
                return STRAIN_ALLELE_CLASS;
            case Services.MUTATION_TYPE:
                return MUTATION_TYPE_CLASS;
            case Services.GENETIC_BACKGROUND:
                return GENETIC_BACKGROUND_CLASS;
            case Services.GENETIC_BACKGROUND_VALUES:
                return GENETIC_BACKGROUND_VALUES_CLASS;
            case Services.REPOSITORIES:
                return REPOSITORIES_CLASS;
            case Services.AVAILABLE_GENETIC_BACKGROUNDS:
                return AVAILABLE_GENETIC_BACKGROUNDS_CLASS;
            case Services.AVAILABILITY:
                return AVAILABILITY_CLASS;
            case Services.PHENOTYPE_ONTOLOGY:
                return PHENOTYPE_ONTOLOGY_CLASS;
            case Services.PHENOTYPE_ONTOLOGY_XREF:
                return PHENOTYPE_ONTOLOGY_XREF_CLASS;
            case Services.PHENOTYPE_ONTOLOGY_ALT_ID:
                return PHENOTYPE_ONTOLOGY_ALT_ID_CLASS;
            case Services.PHENOTYPE_ONTOLOGY_SYNONYM:
                return PHENOTYPE_ONTOLOGY_SYNONYM_CLASS;
            case Services.PHENOTYPE_ONTOLOGY_PATH:
                return PHENOTYPE_ONTOLOGY_PATH_CLASS;
            case Services.USER_COMMENTS:
                return USER_COMMENTS_CLASS;
        }
        return null;
    }
    
    // returns the JNDI name for the required service
    static private String getServiceName(int service){
        switch( service ) {
            case Services.PROJECT:
                return PROJECT_NAME;
            case Services.SAMPLINGUNIT:
                return SU_NAME;
            case Services.USER:
                return USER_NAME;
            case Services.INDIVIDUAL:
                return INDIVIDUAL_NAME;
            case Services.GENOTYPE:
                return GENOTYPE_NAME;
            case Services.ALLELE:
                return ALLELE_NAME;
            case Services.MARKER:
                return MARKER_NAME;
            case Services.MARKERSET:
                return MARKERSET_NAME;
            case Services.UMARKERSET:
                return UMARKERSET_NAME;
            case Services.UMARKER:
                return UMARKER_NAME;
            case Services.UALLELE:
                return UALLELE_NAME;
            case Services.CHROMOSOME:
                return CHROMOSOME_NAME;
            case Services.SPECIES:
                return SPECIES_NAME;
            case Services.FILE:
                return FILE_NAME;
            case Services.GROUPING:
                return GROUPING_NAME;
            case Services.PHENOTYPE:
                return PHENOTYPE_NAME;
            case Services.PHENOTYPEMANAGER:
                return PHENOTYPEMANAGER_NAME;
            case Services.SAMPLINGUNITMANAGER:
                return SUMANAGER_NAME;
            case Services.GENOTYPEMANAGER:
                return GENOTYPEMANAGER_NAME;
            case Services.PROJECTMANAGER:
                return PROJECTMANAGER_NAME;
            case Services.LMARKER:
                return LMARKER_NAME;
            case Services.VARIABLE:
                return VARIABLE_NAME;
            case Services.ROLE:
                return ROLE_NAME;
            case Services.SECURITYPRINCIPLE:
                return SECURITYPRINCIPLE_NAME;
            case Services.PRIVILEGE:
                return PRIVILEGE_NAME;
            case Services.UVARIABLE:
                return UVARIABLE_NAME;
            case Services.UVARIABLESET:
                return UVARIABLESET_NAME;
            case Services.VARIABLESET:
                return VARIABLESET_NAME;
            case Services.GROUP:
                return GROUP_NAME;
            case Services.EXPORTMANAGER:
                return EXPORTMANAGER_NAME;     
            case Services.GQLFILTER:
                return GQLFILTER_NAME;   
            case Services.LINK:
                return LINK_NAME;   
            case Services.RESOURCEMANAGER:
                return RESOURCEMANAGER_NAME;   
           case Services.EXPMODEL:
                return EXPMODEL_NAME;
            case Services.GENETICMODIFICATION:
                return GENETICMODIFICATION_NAME;
            case Services.RESEARCHAPPLICATION:
                return RESEARCHAPPLICATION_NAME;
            case Services.MODELMANAGER:
                return MODELMANAGER_NAME;  
            case Services.GENEONTOLOGY:
                return GENEONTOLOGY_NAME;
            case Services.FUNCTIONALSIGNIFICANCETYPE:
                return FUNCTIONALSIGNIFICANCETYPE_NAME;
            case Services.FUNCTIONALSIGNIFICANCE:
                return FUNCTIONALSIGNIFICANCE_NAME;
            case Services.GENE:
                return GENE_NAME;
            case Services.REFERENCE:
                return REFERENCE_NAME;
            case Services.SIMPLELOG:
                return SIMPLELOG_NAME;
            case Services.ADMINMANAGER:
                return ADMINMANAGER_NAME;
            case Services.RESOURCE:
                return RESOURCE_NAME;
            case Services.RESOURCECATEGORY:
                return RESOURCECATEGORY_NAME; 
            case Services.STRAIN:
                return STRAIN_NAME;
            case Services.STRAIN_TYPE:
                return STRAIN_TYPE_NAME;    
            case Services.STRAIN_STATE:
                return STRAIN_STATE_NAME;    
            case Services.STRAIN_ALLELE:
                return STRAIN_ALLELE_NAME;
            case Services.MUTATION_TYPE:
                return MUTATION_TYPE_NAME;
            case Services.GENETIC_BACKGROUND:
                return GENETIC_BACKGROUND_NAME;
            case Services.GENETIC_BACKGROUND_VALUES:
                return GENETIC_BACKGROUND_VALUES_NAME;
            case Services.REPOSITORIES:
                return REPOSITORIES_NAME;
            case Services.AVAILABLE_GENETIC_BACKGROUNDS:
                return AVAILABLE_GENETIC_BACKGROUNDS_NAME;
            case Services.AVAILABILITY:
                return AVAILABILITY_NAME;
            case Services.PHENOTYPE_ONTOLOGY:
                return PHENOTYPE_ONTOLOGY_NAME;
            case Services.PHENOTYPE_ONTOLOGY_XREF:
                return PHENOTYPE_ONTOLOGY_XREF_NAME;
            case Services.PHENOTYPE_ONTOLOGY_ALT_ID:
                return PHENOTYPE_ONTOLOGY_ALT_ID_NAME;
            case Services.PHENOTYPE_ONTOLOGY_SYNONYM:
                return PHENOTYPE_ONTOLOGY_SYNONYM_NAME;
            case Services.PHENOTYPE_ONTOLOGY_PATH:
                return PHENOTYPE_ONTOLOGY_PATH_NAME;
            case Services.USER_COMMENTS:
                return USER_COMMENTS_NAME;
        }
        return null;
    }
    
    private static HashMap cache;
    
    private EJBHome getFromCache(int service) {
        EJBHome home = null;
        return (EJBHome)cache.get(new Integer(service));
    }
    
    private void addToCache(int service, EJBHome home) {
        if (!cache.containsKey(new Integer(service)))
            cache.put(new Integer(service), home);        
    }
    
    
  /**
   * gets the EJBHome for the given service using the
   * JNDI name and the Class for the EJBHome
   */
    public EJBHome getHome( int s ) throws ServiceLocatorException {
        //long t1 = System.currentTimeMillis();
        EJBHome home = null;
        try {
            Context initial  = new InitialContext();
            
            home = getFromCache(s);
            if (home==null)
            {
                // Look up using the service name from
                // defined constant
                Object objref =
                        initial.lookup(getServiceName(s));

                // Narrow using the EJBHome Class from
                // defined constant
                Object obj = PortableRemoteObject.narrow(
                        objref, getServiceClass(s));
                home = (EJBHome)obj;
                
                // Add this home to cache
                addToCache(s, home);
            }
        } catch( NamingException ex ) {
            ex.printStackTrace();
            throw new ServiceLocatorException("Naming error");
        } catch( Exception ex ) {
            ex.printStackTrace();
            throw new ServiceLocatorException("getHome failed");
        }

        //long t2 = System.currentTimeMillis();
        //totalTime+=t2-t1;
        //System.out.println("ServiceLocator#getHome("+getServiceName(s)+") time="+(t2-t1)+", Total="+totalTime);
        return home;
    }
    
    public Object getManager(int s) throws ServiceLocatorException {
        Object o = null;
        try
        {
            EJBHome tmp = getHome(s);
            Method m = tmp.getClass().getMethod("create", null);
            o = m.invoke(tmp, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ServiceLocatorException(e.getMessage());
        }
        return o;
    }
}

