/*
 * ImportAttributes.java
 *
 * Created on May 2, 2006, 10:45 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.alab.imp.importjob;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttributes;
import org.apache.log4j.Logger;

/**
 * key1=value1;key2=value2;...;keyN=valueN
 *
 * @author heto
 */
public class ImportAttributes extends BasicAttributes {
    
    private static Logger logger = Logger.getLogger(ImportAttributes.class);
    
    /**
     * New empty ImportAttributes
     */
    public ImportAttributes()
    {
        super();
    }
    
    public ImportAttributes(BasicAttributes bas)
    {
        NamingEnumeration ne = bas.getAll();
        try {
            while (ne.hasMore())
            {
               Attribute attr = (Attribute)ne.next();
               this.put(attr.getID(),attr.get());
            }
        } catch (NamingException ex) {
            logger.error("Failed to copy attributes", ex);
        }
        logger.debug("Copy of attributes complete. size="+this.size());
    }
    
    /** Creates a new instance of ImportAttributes */
    public ImportAttributes(String txt) {
        super();
        
        try {
            if (txt!=null) {
                String[] vals = txt.split(";");
                
                for (int i=0;i<vals.length;i++) {
                    String[] tmp = vals[i].split("=");
                    
                    String key = tmp[0];
                    String val = "";
                    if (tmp.length>1)
                        val = tmp[1];
                  
                    put(key, val);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Failed to read attribute string", e);
        }
    }
    
    public String getData()
    {
        String data = "";
        NamingEnumeration ne = this.getAll();
        try {
            while (ne.hasMore())
            {
               Attribute attr = (Attribute)ne.next();
               
               if (data.length()>0)
                   data += ";";
               data += attr.getID()+"="+attr.get();
            }
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return data;
    }
    
    
}
