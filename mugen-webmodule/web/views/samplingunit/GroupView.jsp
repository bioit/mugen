
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="groupdto" scope="request" class="com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO" />
<%@ page import="java.util.Collection" %>
<%
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)request.getSession().getAttribute("caller");
    String suidName = caller.getSuidName();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View group unit</title>
    </head>
    <body>
        <h1>View group</h1>
        
        <m:window name="group.main" state="expanded" title="&nbsp;" workflow="ViewGroup">
            <menu>
                <m:hide privilege="GRP_W">           
                    <a href="Controller?workflow=EditGroup&gid=<jsp:getProperty name="groupdto" property="gid"/>"><m:img name="edit"/></a>
                    <a href="Controller?workflow=RemoveGroup&gid=<jsp:getProperty name="groupdto" property="gid"/>" onClick="return confirm('Remove group?')"><m:img name="delete" title="Remove this group"/></a>
                </m:hide>
            </menu>
            <body>
                <table class="window">
                    <tr>
                        <td>
                            <b>Name</b><br> <jsp:getProperty name="groupdto" property="name"/>
                        </td>
                        <td colspan="2">
                            <b>Comment</b><br><jsp:getProperty name="groupdto" property="comm"/>
                        </td>
                    </tr>
                    <tr>                
                        <td>
                            <b>User</b><br><jsp:getProperty name="groupdto" property="user"/>
                        </td> 

                        <td>
                            <b>Updated</b><br> <jsp:getProperty name="groupdto" property="updated"/>
                        </td>
                    </tr>
                </table>
            </body>
        </m:window>
        
        
        <br>
        
        
        <m:window name="group.history" title="History" workflow="ViewGroup">
            <menu>
            </menu>
            <body>
                <table class="window">                     
                    <tr>
                        <th colspan="4" class="window" align="center">Current data</th>
                    </tr>
                    <tr>
                        <th class="window">Name</th>
                        <th class="window">Comment</th>
                        <th class="window">User</th>
                        <th class="window">Last updated</th>
                    </tr>
                    <tr>
                        <td><jsp:getProperty name="groupdto" property="name"/></td>
                        <td><jsp:getProperty name="groupdto" property="comm"/></td>
                        <td><jsp:getProperty name="groupdto" property="user"/></td>
                        <td><jsp:getProperty name="groupdto" property="date"/></td>
                    </tr>
                    <tr>
                        <th colspan="4" class="window" align="center">History</th>
                    </tr>                    
                    <m:iterate-collection collection="grouphistory">
                        <tr class="#?alt#">
                            <td>#:getName#</td>
                            <td>#:getComm#</td>
                            <td>#:getUser#</td>
                            <td>#:getDate#</td>
                        </tr>
                    </m:iterate-collection>                     
                </table>
            </body>
        </m:window>
        <m:back/>
    </body>
</html>
