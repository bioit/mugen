/*
 * GenotypeSpecialViewDTO.java
 *
 * Created on October 24, 2005, 1:03 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;

import com.arexis.mugen.genotype.allele.AlleleRemote;
import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Data transfer object class for genotypes. Used for the special views (parents, child and group)
 * @author lami
 */
public class GenotypeSpecialViewDTO implements Serializable {
    // Allele info for each selected marker
    private String a1, a2, a3;
    private int iid, fatherid, motherid;
    
    // Marker names...
    private String mname1, mname2, mname3;
    
    private String identity, father, mother;
    
    /**
     * Creates a new instance of GenotypeSpecialViewDTO
     * @param ind The individual for the genotype
     */
    public GenotypeSpecialViewDTO(IndividualRemote ind) {
        try {
            this.identity = ind.getIdentity();
            IndividualRemote fatherInd = ind.getFather();
            IndividualRemote motherInd = ind.getMother();
            
            // This ind dont have father or mother...default
            this.father = "-";
            this.mother = "-";
            this.iid = ind.getIid();
            this.fatherid = 0;
            this.motherid = 0;
            
            // Set father and mother values if existing
            if(fatherInd != null) {
                this.father = fatherInd.getIdentity();    
                fatherid = fatherInd.getIid();
            }
            if(motherInd != null) {                           
                this.mother = motherInd.getIdentity();
                motherid = motherInd.getIid();
            }
                
            a1 = "-";
            a2 = "-";
            a3 = "-";
            
            mname1 = "None";
            mname2 = "None";
            mname3 = "None";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    // Convenience constructor for making a 'blank' object to
    // avoid nullpointerexception in view
    /**
     * Creates a new instance of GenotypeSpecialViewDTO
     */
    public GenotypeSpecialViewDTO() {
        a1 = "";
        a2 = "";
        a3 = "";
        identity = "";
        father = "";
        mother = "";
        setMname1("None");        
        setMname2("None");        
        setMname3("None");        
    }
    
    /**
     * Returns the id of the father to the individual that the genotype is for
     * @return The father id
     */
    public int getFatherId() {
        return fatherid;
    }
    
    /**
     * Returns the id of the mother to the individual that the genotype is for
     * @return The mother id
     */
    public int getMotherId() {
        return motherid;
    }
    
    /**
     * Returns the id of the individual that the genotype is for
     * @return The individual id
     */
    public int getIid() {
        return iid;
    }
    
    /**
     * Sets the alleles for marker 1
     * @param alleles The alleles
     */
    public void setMarker1Allele(Collection alleles) {
        AlleleRemote a = null;
        if(alleles.size() > 0)
            a1 = "";        
        try {
            Iterator i = alleles.iterator();
            int ctr = 0;
            while(i.hasNext()) {
                a = (AlleleRemote)i.next();
                if(ctr == 0)
                    a1 += a.getName();
                else
                    a1 += ", "+a.getName(); 
                ctr++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }        
    
    /**
     * Sets the alleles for marker 2
     * @param alleles The alleles
     */
    public void setMarker2Allele(Collection alleles) {
        AlleleRemote a = null;
        if(alleles.size() > 0)
            a2 = "";
        try {
            Iterator i = alleles.iterator();
            int ctr = 0;
            while(i.hasNext()) {
                a = (AlleleRemote)i.next();
                if(ctr == 0)
                    a2 += a.getName();
                else
                    a2 += ", "+a.getName();    
                ctr++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets the alleles for marker 3
     * @param alleles The alleles
     */
    public void setMarker3Allele(Collection alleles) {
        AlleleRemote a = null;
        if(alleles.size() > 0)
            a3 = "";        
        try {
            Iterator i = alleles.iterator();
            int ctr = 0;
            while(i.hasNext()) {
                a = (AlleleRemote)i.next();
                if(ctr == 0)
                    a3 += a.getName();
                else
                    a3 += ", "+a.getName();
                ctr++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  

    /**
     * Returns the alleles for marker 1
     * @return The alleles for marker 1
     */
    public String getMarker1Allele() {
        return a1;
    }

    /**
     * Returns the alleles for marker 2
     * @return The alleles for marker 2
     */
    public String getMarker2Allele() {
        return a2;
    }

    /**
     * Returns the alleles for marker 3
     * @return The alleles for marker 3
     */
    public String getMarker3Allele() {
        return a3;
    }

    /**
     * Returns the identity of the individual
     * @return The identity
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * Returns the father of the individual
     * @return The father of the individual
     */
    public String getFather() {
        return father;
    }

    /**
     * Returns the mother of the individual
     * @return The mother
     */
    public String getMother() {
        return mother;
    }

    /**
     * Returns the name of the first marker
     * @return The name of the first marker
     */
    public String getMname1() {
        return mname1;
    }

    /**
     * Sets the name of the first marker
     * @param mname1 The name of the first marker
     */
    public void setMname1(String mname1) {
        this.mname1 = mname1;
    }

    /**
     * Returns the name of the second marker
     * @return The name of the second marker
     */
    public String getMname2() {
        return mname2;
    }

    /**
     * Sets the name of the second marker
     * @param mname2 The name of the second marker
     */
    public void setMname2(String mname2) {
        this.mname2 = mname2;
    }

    /**
     * Returns the name of the third marker
     * @return The name of the third marker
     */
    public String getMname3() {
        return mname3;
    }

    /**
     * Sets the name of the third marker
     * @param mname3 The name of the third marker
     */
    public void setMname3(String mname3) {
        this.mname3 = mname3;
    }
}
