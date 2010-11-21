/*
 * ApplicationException.java
 *
 * Created on June 21, 2005, 3:24 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.exceptions;
import com.arexis.arxframe.ActionException;

/**
 * The class for general application exceptions that should be shown to the user
 * @author heto
 */
public class CreateException extends ApplicationException
{
    
    /**
     * Creates a new instance of DbException
     * @param message The exception message
     */
    public CreateException(String message) 
    {
        super(message);
    }   
    
    /**
     * Creates a new instance of DbException
     * @param e The underlying exception thats propageted to this exception
     * @param message The exception message
     */
    public CreateException(String message, Exception e) 
    {
        super(message);
        this.initCause(e);
    }   
}
