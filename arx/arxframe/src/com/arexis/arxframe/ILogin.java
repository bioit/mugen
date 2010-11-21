/*
 * ILogin.java
 *
 * Created on October 31, 2005, 11:20 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.arxframe;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author heto
 */
public interface ILogin {
    
     /**
      * Handle login. 
      * 
      * Fist call:
      * If user has not logged in yet ArxLoginForward is used to 
      * forward user to the login view. 
      *
      * Second call: 
      * The user and password values are entered. If login is correct, Caller object is returned.
      * If it fails, the ArxLoginException is thrown to show an error page.
      *
      * @return Caller object with user info.
      * @param request 
      * @param response 
      * @throws com.arexis.arxframe.ArxLoginForward is thrown then controller should redirect to the login page. Login page should be entered with the url message on exception object.
      * @throws com.arexis.arxframe.ArxLoginException is thrown if something goes wrong, login failed, or denied
      */
     public Caller doLogin(HttpServletRequest request, 
            HttpServletResponse response) throws ArxLoginForward, ArxLoginException;
     
     public String getLoginView();
     
     public String getFirstWorkflow();
}
