<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="user" scope="request" type="com.arexis.mugen.project.projectmanager.UserDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>view user</title>
    </head>
    <body class="content">
        <h1>contact information for <jsp:getProperty name="user" property="name"/></h1>
        <p class="toolbar">
        <m:hide privilege="PROJECT_ADM">
            <a href="Controller?workflow=EditUser&id=<jsp:getProperty name="user" property="id"/>" title="edit this user"><img src="images/icons/editdark.png"></a>
        </m:hide>
        &nbsp;
        </p>
        <form action="Controller" method=post>
        <table class="block">
            <tr>
                <td valign="top">
                    <table>
                        <tr>
                            <td><b>Name</b></td>
                        </tr>
                        <tr>
                            <td><jsp:getProperty name="user" property="name"/></td>
                        </tr>
                        <tr>
                            <td><b>E-mail</b></td>
                        </tr>
                        <tr>
                            <td><a href="mailto:<jsp:getProperty name='user' property='email'/>"><jsp:getProperty name='user' property='email'/></a></td>
                        </tr>
                        <tr>
                            <td><b>Personal website</b></td>           
                        </tr>
                        <tr>
                            <td><a href="<jsp:getProperty name='user' property='userLinkUrl'/>" target="_blank"><jsp:getProperty name='user' property='userLinkName'/></a></td>
                        </tr>
                    </table>
                </td>
                <td valign="top">
                    <table>
                        <tr>
                            <td><b>Research group</b></td>
                        </tr>
                        <tr>
                            <td><jsp:getProperty name="user" property="groupName"/></td>
                        </tr>
                        <tr>
                            <td><b>Group website</b></td>
                        </tr>
                        <tr>
                            <td><a href="<jsp:getProperty name='user' property='groupLinkUrl'/>" target="_blank"><jsp:getProperty name='user' property='groupLinkName'/></a></td>
                        </tr>
                        <tr>
                            <td><b>Group address</b></td>
                        </tr>
                        <tr>
                            <td><jsp:getProperty name="user" property="groupAddress"/></td>
                        </tr>
                        <tr>
                            <td><b>Group phone</b></td>
                        </tr>
                        <tr>
                            <td><jsp:getProperty name="user" property="groupPhone"/></td>
                        </tr>
                    </table>
                </td>
            </tr>         
        </table>
        <br>
        <p class="toolbar">
        <m:back/>
        </p> 
        </form>
    </body>
</html>
