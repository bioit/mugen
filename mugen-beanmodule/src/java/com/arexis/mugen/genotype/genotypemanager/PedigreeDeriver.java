/*
 * PedigreeDeriver.java
 *
 * Created on October 27, 2005, 4:13 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;

import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.util.graph.DirectedEdge;
import com.arexis.util.graph.DirectedGraph;
import com.arexis.util.graph.Edge;
import com.arexis.util.graph.Graph;
import com.arexis.util.graph.Node;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Helper class for constructing a pedigree.
 * 
 * The class have methods that returns a pedigree as a graph and methods that only returns a collection of individuals. The reason for this is that it not always certain that graph representation is desired.
 * @author lami
 */
public class PedigreeDeriver  implements Serializable {
    private Graph g;
    private boolean depthFirst;
    
    /**
     * Creates a new instance of PedigreeDeriver
     * @param depthFirst If depth first should be used, else it will be breadth first
     */
    public PedigreeDeriver(boolean depthFirst) {
        this.depthFirst = depthFirst;
    }
    
    /**
     * Makes a graph from the individuals in the inds collection
     * @param inds The individuals to make graph of
     * @param connectedOnly If only related individuals should be part of graph
     * @throws java.lang.Exception If the graph could not be created
     * @return A graph describing the relationship between individuals
     */
    public Graph fullGraph(Collection inds, boolean connectedOnly) throws Exception {
        g = graphFromCollection(inds);
        if(connectedOnly)
            g.makeConnectedOnly();  
        
        return g;
    }
    
    /**
     * Generates a graph over the individuals originating from ind1 and ind2
     * @param inds The individuals
     * @param ind1 Parent 1
     * @param ind2 Parent 2
     * @param connectedOnly If only related individuals should be included
     * @throws java.lang.Exception If the graph could not be generated
     * @return A graph over the relation between the individuals
     */
    public Graph fullGraph(Collection inds, IndividualRemote ind1, IndividualRemote ind2, boolean connectedOnly) throws Exception {
        g = graphFromCollection(inds, ind1, ind2);
        if(connectedOnly)
            g.makeConnectedOnly();  
        
        return g;
    }      
    
    /**
     * Retrieves all individuals that originate from ind1 and ind2
     * @param inds The individuals 
     * @param ind1 Parent 1
     * @param ind2 Parent 2
     * @throws java.lang.Exception If the individuals could not be retrieved
     * @return The individuals originating from ind1 and ind2
     */
    public Collection childView(Collection inds, IndividualRemote ind1, IndividualRemote ind2) throws Exception {
        Collection c = new ArrayList();
        

        Collection fatherSide = new ArrayList();
        if(ind1 != null) {
            c.add(ind1);
            fatherSide = getAllChildren(c, inds, ind1);           
        }
            

        Collection motherSide = new ArrayList();
        if(ind2 != null){
            if(!c.contains(ind2))
                c.add(ind2);
            motherSide = getAllChildren(c, inds, ind2);
        }
            

        Iterator i = null;
        if(fatherSide != null) {
            i = fatherSide.iterator();
            while(i.hasNext()) {
                Object o = i.next();
                if(!motherSide.contains(o))
                    motherSide.add(o);
            }

            return motherSide;
        }
        else if(motherSide != null) {
            i = motherSide.iterator();
            while(i.hasNext()) {
                Object o = i.next();
                if(!fatherSide.contains(o))
                    fatherSide.add(o);
            }

            return fatherSide;
        } 

        return null;
    }
    
    /**
     * Returns a collection of individuals that originate from the individual 'ind'
     * @param inds All individuals
     * @param ind The individual to start from
     * @throws java.lang.Exception If something went wrong during the process
     * @return The individuals originating from ind
     */
    public Collection parentalView(Collection inds, IndividualRemote ind) throws Exception {
        Collection c = new ArrayList();
        c.add(ind);
        inds = getAllParents(c, inds, ind);
        
        return inds;
    }    
    
    private Collection getAllParents(Collection c, Collection inds, IndividualRemote ind) throws Exception{
        Collection parents = new ArrayList();        
        Iterator j = null;       
        IndividualRemote parent = null;                
        
        parents = ind.getParents();              

        if(!c.contains(ind))
            c.add(ind); 
 
        j = parents.iterator();
        while(j.hasNext()) {
            parent = (IndividualRemote)j.next();
            if(!depthFirst)
                addGeneration(c, parents);
            getAllParents(c, inds, parent);
        }            

        return c;        
    }
    
    private void addGeneration(Collection c, Collection parents) {
        Iterator i = parents.iterator();
        Object parent = null;
        while(i.hasNext()) {
            parent = i.next();
            if(!c.contains(parent))
                c.add(parent);
        }
    }
    
    private Collection getAllChildren(Collection c, Collection inds, IndividualRemote ind) throws Exception{        
        Collection children = new ArrayList();        
        Iterator j = null;       
        IndividualRemote child = null;                   
        children = ind.getChildren();  
        
        Collection parents = ind.getParents();
        if(!c.contains(ind)) {
            c.add(ind); 
        }
        
        j = parents.iterator();
        while(j.hasNext()) {
            IndividualRemote parent = (IndividualRemote)j.next();

            if(!c.contains(parent))
                c.add(parent);                
        }        
 
        j = children.iterator();
        while(j.hasNext()) {
            child = (IndividualRemote)j.next();

            getAllChildren(c, inds, child);
        }            

        return c;           
        
    }
    
    /**
     * Makes a graph over all individuals in the collection c
     * @param c The collection of individuals
     * @throws java.lang.Exception If the graph could not be created
     * @return A graph representation of the individuals in collection c
     */
    public Graph graphFromCollection(Collection c) throws Exception {
        Graph g = new DirectedGraph();

        Node node = null;  
        Iterator i = c.iterator();
        IndividualRemote ind = null;
        
        while(i.hasNext()) {
            ind = (IndividualRemote)i.next();

            String shape = "";
            if(ind.getSex().equals("F"))
                shape = "elipse";
            else if(ind.getSex().equals("M"))
                shape = "rectangle";            
            else
                shape = "diamond";
            
            node = new Node(ind.getIdentity(), ind.getIid(), shape);
            if(!g.containsNode(node))
                g.addNode(node);           
        }
        
        i = c.iterator();
        
        Node father = null;
        Node mother = null;
        Node child = null;
        
        while(i.hasNext()) {
            mother = null;
            father = null;
            child = null;
            ind = (IndividualRemote)i.next();
            
            child = g.getNodeById(ind.getIid());
            IndividualRemote fat = ind.getFather();
            IndividualRemote mot = ind.getMother();           
            
            if(fat != null)
                father = g.getNodeById(fat.getIid());
            if(mot != null)
                mother = g.getNodeById(mot.getIid());
            
            if(father != null && !child.equals(father)) {
                Edge edge = new DirectedEdge(father, child, "Father to");
                if(!g.containsEdge(edge))
                    g.addEdge(edge);            
            }

            if(mother != null && !child.equals(mother)) {
                Edge edge = new DirectedEdge(mother, child, "Mother to");                
                if(!g.containsEdge(edge))
                    g.addEdge(edge);                         
            }
        }
        
        return g;
    }
    
    /**
     * Makes a graph representation of the collection c, starting from ind 1 and 2.
     * @param inds A collection of individuals
     * @param ind1 The first parent
     * @param ind2 The second parent
     * @throws java.lang.Exception If the graph could not be created
     * @return A graph representation of the relationship between individuals starting with ind 1 and ind 2, i.e. a pedigree.
     */
    public Graph graphFromCollection(Collection inds, IndividualRemote ind1, IndividualRemote ind2) throws Exception {
        Graph g = new DirectedGraph();

        Node node = null;  
        Iterator i = inds.iterator();
        IndividualRemote ind = null;
        
        while(i.hasNext()) {
            ind = (IndividualRemote)i.next();
                
            String shape = "";
            if(ind.getSex().equals("F"))
                shape = "elipse";
            else if(ind.getSex().equals("M"))
                shape = "rectangle";            
            else
                shape = "diamond";
            
            node = new Node(ind.getIdentity(), ind.getIid(), shape);
            if(ind.getIid() == ind1.getIid() || ind.getIid() == ind2.getIid())
                node.setLevel(0);
            if(!g.containsNode(node))
                g.addNode(node);           
        }
        
        i = inds.iterator();
        
        Node father = null;
        Node mother = null;
        Node child = null;
        
        int level = 0;

        while(i.hasNext()) {
            level = 0;
            mother = null;
            father = null;
            child = null;
            ind = (IndividualRemote)i.next();

            child = g.getNodeById(ind.getIid());
            IndividualRemote fat = ind.getFather();
            IndividualRemote mot = ind.getMother();           
            
            if(fat != null)
                father = g.getNodeById(fat.getIid());
            if(mot != null)
                mother = g.getNodeById(mot.getIid());
            
            if(father != null && !child.equals(father)) { 
                child.setLevel(father.getLevel()+1);
                Edge edge = new DirectedEdge(father, child, "Father to");
                if(!g.containsEdge(edge)) {
                    g.addEdge(edge);    
                }
            }

            if(mother != null && !child.equals(mother)) {
                child.setLevel(mother.getLevel()+1);
                Edge edge = new DirectedEdge(mother, child, "Mother to");                
                if(!g.containsEdge(edge)) {                    
                    g.addEdge(edge);       
                }
            }    
            
            if(mother == null && mother == null && ind.getIid() != ind1.getIid() && ind.getIid() != ind2.getIid()) {
                Collection children = ind.getChildren();
                Iterator j = children.iterator();
                
                if(j.hasNext()) {
                    IndividualRemote tmp = (IndividualRemote)j.next();
                    Node n = g.getNodeById(tmp.getIid());
                    child.setLevel(n.getLevel()-1);
                }                
            }
        }
        
        return g;
    }        
 }
