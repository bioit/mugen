package com.arexis.mugen;

import com.arexis.arxframe.ArxLoginException;
import com.arexis.arxframe.ArxLoginForward;
import com.arexis.arxframe.Caller;
import com.arexis.arxframe.ILogin;
import com.arexis.mugen.project.projectmanager.ProjectManagerRemote;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MugenLogin implements ILogin {
    
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MugenLogin.class);
    private boolean NO_LOGIN;
    
    public MugenLogin() {
        NO_LOGIN=false;
    }
    
    public boolean validUP(HttpServletRequest request){
        boolean isvalid = true;
        
        if (request.getParameter("usr")==null || request.getParameter("pwd")==null
            || request.getParameter("usr").length()==0 || request.getParameter("pwd").length()==0
            || request.getParameter("usr").trim().length()==0 || request.getParameter("pwd").trim().length()==0
            || request.getParameter("usr").trim()==null || request.getParameter("pwd").trim()==null){
            isvalid = false;
            return isvalid;
        }
        
        return isvalid;
    }
    
    public String getLoginView(){
        return "/loginagain.html";
    }
    
    public String getFirstWorkflow(){
        return "Controller?workflow=logout";
    }
    
    public Caller doLogin(HttpServletRequest request, HttpServletResponse response) throws ArxLoginException, ArxLoginForward {
        logger.debug("MugenLogin#doLogin()");
        MugenCaller caller = new MugenCaller();
        try 
        {
            if(!validUP(request))
            {
                if(request.getSession().getAttribute("caller")==null){
                    ServiceLocator locator = ServiceLocator.getInstance();
                    ProjectManagerRemote prjManager = (ProjectManagerRemote)locator.getManager(ServiceLocator.Services.PROJECTMANAGER);
                
                    caller = prjManager.login("public", "notknown");
                    caller.updatePrivileges();
                    prjManager.log("user:"+request.getParameter("usr")+" & pwd:"+request.getParameter("pwd"), "Bad Login", "0", request.getRemoteAddr(), request.getRemoteHost());
                    return caller;
                } else {
                    return (Caller) request.getSession().getAttribute("caller");
                }
            } else {
                String usr = request.getParameter("usr");
                String pwd = request.getParameter("pwd");

                ServiceLocator locator = ServiceLocator.getInstance();
                ProjectManagerRemote prjManager = (ProjectManagerRemote)locator.getManager(ServiceLocator.Services.PROJECTMANAGER);

                caller = prjManager.login(usr, pwd);
                
                if(caller!=null){
                    caller.updatePrivileges();
                    prjManager.log("user "+caller.getName()+" logged in", "Login", caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
                    logger.debug("MugenLogin#doLogin() correct input for "+caller.getName());
                }else{
                    if(request.getSession().getAttribute("caller")==null){
                        caller = prjManager.login("public", "notknown");
                        caller.updatePrivileges();
                        prjManager.log("user:"+request.getParameter("usr")+" & pwd:"+request.getParameter("pwd"), "Bad Login", "0", request.getRemoteAddr(), request.getRemoteHost());
                        logger.debug("MugenLogin#doLogin() incorrect input [usr and/or pwd was wrong]");
                    }else{
                        return (Caller) request.getSession().getAttribute("caller");
                    }
                }
                return caller;
            }
        }
        catch (Exception e) 
        {
           throw new ArxLoginException("MugenLogin#doLogin(...): Login method failed", e);
        }
    }
}
