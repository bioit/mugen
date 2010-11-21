<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%
   
   //very very important cache issue
    // Set to expire far in the past.
    response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
    // Set standard HTTP/1.1 no-cache headers.
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    // Set standard HTTP/1.0 no-cache header.
    response.setHeader("Pragma", "no-cache");
    
    com.arexis.mugen.project.projectmanager.ProjectDTO project;
    project = (com.arexis.mugen.project.projectmanager.ProjectDTO)session.getAttribute("project.projectdto");
    
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
    
    String RQ = (String) request.getSession().getAttribute("RQ");
    
    if (RQ == null || RQ.equals("")){
        RQ = "Controller?workflow=swiftbegin";
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <META HTTP-EQUIV="Window-target" CONTENT="_top">
        <meta name="description" content="The MUGEN Mouse Database (MMdb) is a virtual repository of murine models of immune processes and immunological diseases." />
	<meta name="keywords" content="MUGEN Mouse Database, MMdb, mouse mutant, transgenic, virtual repository, immunological disease, mouse models, mouse database, human disease, immune system, mice" />
        <title>mugen database environment</title>
        <link rel="SHORTCUT ICON" href="images/favicon.ico" type="image/x-icon">
    </head>
    
    <frameset rows="55,24,*" FRAMEBORDER=NO FRAMESPACING=0 BORDER=0>
        <frame name="top" src="top.jsp" SCROLLING=NO>
        <frame name="test" src="CloseWindowController.jsp" SCROLLING=NO>
        <frameset cols="145,*,145" FRAMEBORDER=NO FRAMESPACING=0 BORDER=0>
            <frameset rows="*,200" FRAMEBORDER=NO FRAMESPACING=0 BORDER=0>
                <frame name="nav" src="Menu">
                <frame name="linx" src="Logit" SCROLLING=NO marginheight="0" marginwidth="0">
            </frameset>
            <frame name="page" src="<%=RQ%>">
            <frameset rows="265,*" FRAMEBORDER=NO FRAMESPACING=0 BORDER=0> 
                <frame name="help" src="helpframe.jsp">
                <frame name="eupage" align="center" src="euframe.jsp" SCROLLING=NO marginheight="0" marginwidth="0">
            </frameset>
        </frameset>
    </frameset>
</html>
