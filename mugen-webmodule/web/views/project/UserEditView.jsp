<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="user" scope="request" type="com.arexis.mugen.project.projectmanager.ProjectUserDTO" />
<%
    com.arexis.mugen.project.projectmanager.ProjectUserDTO userDto = (com.arexis.mugen.project.projectmanager.ProjectUserDTO)request.getAttribute("user");
    String rid = ""+userDto.getRid();
    String status = ""+userDto.getStatus();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>         
        <title>edit user</title>
    </head>
    <body class="content">
        <h1>edit user</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
                <input type="hidden" name="id" value='<jsp:getProperty name="user" property="id"/>'/>
                <table>
                    <tr>                    
                        <td><b>Name</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" value='<jsp:getProperty name="user" property="name"/>'/></td>
                    </tr> 
                     <tr>                    
                        <td><b>Username</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="usr" size="35" value='<jsp:getProperty name="user" property="usr"/>'/></td>
                    </tr> 
                     <tr>                    
                        <td><b>Password</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="pwd" size="35" value='<jsp:getProperty name="user" property="pwd"/>'/></td>
                    </tr>                     
                    <tr>                    
                        <td><b>E-mail</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="email" size="35" value='<jsp:getProperty name="user" property="email"/>'/></td>
                    </tr> 
                    <tr>                    
                        <td><b>Personal website</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="userLink" size="35" value='<jsp:getProperty name="user" property="userLinkName"/>'/></td>
                    </tr>                  
                    <tr>                    
                        <td><b>Role</b></td>
                    </tr>
                    <tr>
                        <td><m:checkbox collection='roles' selected="<%=rid%>" name="rid" idGetter='getRid' textGetter='getName'/></td>
                    </tr>
                    <tr>                    
                        <td><b>Status</b></td>
                    </tr>
                    <tr>
                        <td><m:status value='<%=status%>'/></td>
                    </tr>     
                    <tr>                    
                        <td><b>Research group</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="groupName" size="35" value='<jsp:getProperty name="user" property="groupName"/>'/></td>
                    </tr>                 
                    <tr>
                        <td><b>Address</b></td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="groupAddress"><jsp:getProperty name="user" property="groupAddress"/></textarea></td>
                    </tr>  
                    <tr>                    
                        <td><b>Phone</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="groupPhone" size="35" value='<jsp:getProperty name="user" property="groupPhone"/>'/></td>
                    </tr>    
                    <tr>                    
                        <td><b>Group website</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="groupLink" size="35" value='<jsp:getProperty name="user" property="groupLinkName"/>'/></td>
                    </tr>                  
                </table>
            <p class="toolbar">
                <%--m:save saveName="save" cancelName="back"/--%>
                <%--input type="image" src="images/icons/disk_blue_24.png" onClick="" value="save" title="Save" name="save" alt="save"--%>
                <%--input type="image" src="images/icons/cancel_24.png" title="Cancel" value="back" name="back"--%>
                <input type="image" src="images/icons/save.png" name="save" value="Save" onClick="validate();return returnVal;" title="save user">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
        </form>   
    </body>
</html>
