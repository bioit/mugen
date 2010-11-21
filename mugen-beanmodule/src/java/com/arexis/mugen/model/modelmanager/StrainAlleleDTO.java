package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.strain.allele.StrainAlleleRemote;
import com.arexis.mugen.model.strain.mutationtype.MutationTypeRemote;
import com.arexis.mugen.species.gene.GeneRemote;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class StrainAlleleDTO implements Serializable, Comparable {
    
    private int id;
    private String imsrid;
    private String symbol;
    private String name;
    private String attributes;
    
    private String geneName, geneSymbol;
    private int geneId;
    
    private int IsStrainAlleleTransgenic;
    
    private String mutations, mutationabbrs;
    
    private String Mgilink;
   
    public StrainAlleleDTO(StrainAlleleRemote sa) {
        try {
            id = sa.getId();
            imsrid = sa.getMgiId();
            if(imsrid.compareTo("0")==0) {
                Mgilink = "";
            } else {
                Mgilink = "<a href=\"http://www.informatics.jax.org/searches/accession_report.cgi?id=MGI%3A"+imsrid+"\" target=\"_blank\">MGI:"+imsrid+"</a>";
            }
            
            symbol = sa.getSymbol().replaceAll("<","&lt;").replaceAll(">","&gt;");
            symbol = symbol.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            name = sa.getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
            name = name.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            attributes = sa.getAttributes();
            
            GeneRemote gene = sa.getGene();
            if (gene!=null) {    
                geneName = sa.getGene().getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
                geneName = geneName.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
                geneSymbol = sa.getGene().getGenesymbol().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
                geneId = sa.getGene().getGaid();
            }
            
            // Get the names of the mutations.
            Collection mut_arr = sa.getMutationTypes();
            Iterator i = mut_arr.iterator();
            mutations = "";
            mutationabbrs = "";
            IsStrainAlleleTransgenic = 0;
            int j=0;
            while (i.hasNext()) {
                MutationTypeRemote m = (MutationTypeRemote)i.next();
                if (j!=0){
                    mutationabbrs += ", ";
                    mutations += ", ";
                }
                mutations += m.getName();
                
                if (m.getName().compareTo("transgenic")==0)
                    IsStrainAlleleTransgenic = 1;
                
                mutationabbrs += m.getAbbreviation();
                j++;
            }
                  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /** Creates a new instance of un-superscripted StrainAlleleDTO */
    public StrainAlleleDTO(StrainAlleleRemote sa, String superscript) {
        try {
            id = sa.getId();
            imsrid = sa.getMgiId();
            if(imsrid.compareTo("0")==0){
                Mgilink = "";
            } else {
                Mgilink = "<a href=\"http://www.informatics.jax.org/searches/accession_report.cgi?id=MGI%3A"+imsrid+"\" target=\"_blank\">MGI:"+imsrid+"</a>";
            }
            
            symbol = sa.getSymbol();
            
            name = sa.getName();
            
            attributes = sa.getAttributes();
            
            GeneRemote gene = sa.getGene();
            if (gene!=null) {    
                geneName = sa.getGene().getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
                geneName = geneName.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
                geneSymbol = sa.getGene().getGenesymbol().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
                geneId = sa.getGene().getGaid();
            }
            
            // Get the names of the mutations.
            Collection mut_arr = sa.getMutationTypes();
            Iterator i = mut_arr.iterator();
            mutations = "";
            mutationabbrs = "";
            IsStrainAlleleTransgenic = 0;
            int j=0;
            while (i.hasNext()) {
                MutationTypeRemote m = (MutationTypeRemote)i.next();
                if (j!=0){
                    mutationabbrs += ", ";
                    mutations += ", ";
                }
                mutations += m.getName();
                
                if (m.getName().compareTo("transgenic")==0)
                    IsStrainAlleleTransgenic = 1;
                
                mutationabbrs += m.getAbbreviation();
                j++;
            }
                  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//non superscripted constructor.
    
    public int compareTo(Object anotherObj) {
        if(!(anotherObj instanceof StrainAlleleDTO))
            throw new ClassCastException("Object is of wrong class. StrainAlleleDTO object expected but not found.");
        return getName().compareTo(((StrainAlleleDTO)anotherObj).getName());
    }
    
    public boolean equals(Object obj) {
        if (((StrainAlleleDTO)obj).id == this.id)
            return true;
        else 
            return false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMgiId() {
        return imsrid;
    }
    
    public String getMgilink() {
        return Mgilink;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getGeneName() {
        return geneName;
    }
    
    public String getGeneSymbol() {
        return geneSymbol;
    }

    public int getGeneId() {
        return geneId;
    }

    public String getMutations() {
        return mutations;
    }

    public String getAttributes() {
        return attributes;
    }

    public String getMutationabbrs() {
        return mutationabbrs;
    }

    public int getIsStrainAlleleTransgenic() {
        return IsStrainAlleleTransgenic;
    }
}
