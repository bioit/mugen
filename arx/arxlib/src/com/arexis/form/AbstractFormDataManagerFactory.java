/*
 * AbstractFormDataManagerFactory.java
 *
 * Created on November 24, 2005, 8:47 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.form;
import java.util.ArrayList;


/**
 * Factory clas for creation of FormDataManagers. The form data managers are created with default values suitable for the specific type of manager.
 * @author lami
 */
public abstract class AbstractFormDataManagerFactory {  
    public final static int WEB_FORM = 0;
    public final static int SWING_FORM = 1;
    
    protected static ArrayList names;
    
    /**
     * Creates a new instance of AbstractFormDataManagerFactory 
     */
    public AbstractFormDataManagerFactory() {
    }
    
    
    /**
     * Returns the name of a specific WebFormDataManager instance created by the factory.
     * @param id The id of the instance to get, eg. AbstractFormDataManagerFactory.GENOTYPE
     * @return The name of the instance
     */
    public static String getInstanceName(int id) {
        if(names == null || id > names.size())
            return "unknownName";
        
        return (String)names.get(id);
    }
    
    /**
     * Creates a new FormDataManager instance with suitable default values.
     * @param name The type of WebFormDataManager to create
     * @throws com.arexis.arxframe.ActionException If the the name could not be recognized
     * @return A WebFormDataManager object with default values as specified by the type of WebFormDataManager to build
     */
    public abstract FormDataManager createInstance(int name, int type) throws FormDataException;
}
