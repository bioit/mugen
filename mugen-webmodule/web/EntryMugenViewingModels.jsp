<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%
    com.arexis.mugen.project.projectmanager.ProjectDTO project;
    project = (com.arexis.mugen.project.projectmanager.ProjectDTO)session.getAttribute("project.projectdto");
    
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <META HTTP-EQUIV="Window-target" CONTENT="_top">
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
            <frame name="page" src="Controller?workflow=ViewModels">
            <%--frame name="page" src="Welcome"--%>
            <frameset rows="265,*" FRAMEBORDER=NO FRAMESPACING=0 BORDER=0> 
                <frame name="help" src="helpframe.jsp">
                <frame name="eupage" align="center" src="euframe.jsp" SCROLLING=NO marginheight="0" marginwidth="0">
            </frameset>
        </frameset>
    </frameset>
</html>
