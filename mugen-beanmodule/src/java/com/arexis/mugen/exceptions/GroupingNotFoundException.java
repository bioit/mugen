/*
 * GroupingNotFoundException.java
 *
 * Created on November 2, 2006, 10:07 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.mugen.exceptions;

/**
 *
 * @author se22519
 */
public class GroupingNotFoundException extends ApplicationException
{
    
    /** Creates a new instance of GroupingNotFoundException */
    public GroupingNotFoundException(String msg, Exception e)
    {
        super(msg, e);
    }
    
    public GroupingNotFoundException(String msg)
    {
        super(msg);
    }
    
}
