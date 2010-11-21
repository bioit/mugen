/*
 * IdGenerator.java
 *
 * Created on June 29, 2005, 9:59 AM
 *
 */

package com.arexis.mugen.id;

import com.arexis.mugen.exceptions.DbException;
import java.sql.Connection;

/**
 * The interface for handling the generation of identifiers in the database. 
 * Databases handles the id generation and sequences with unstandardized syntax. 
 * This incapsulates the db implementaion in mugen.
 * @author heto
 */
public interface IdGenerator 
{
    /**
     * Get the next identifier
     * @param conn The connection to use then getting the id
     * @param sequenceName The sequence name for the table. This is a name that identifies the
     * table.
     * @throws com.arexis.mugen.exceptions.DbException if something goes wrong
     * @return an integer value.. This is not complete..
     */
    public int getNextId(Connection conn, String sequenceName) throws DbException;
}
