
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
<jsp:useBean id="groupingdto" scope="request" class="com.arexis.mugen.samplingunit.samplingunitmanager.GroupingDTO" />
<%@ page import="java.util.Collection" %>
<html>
    <head>
        <meta http-equiv="Content-Type">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View grouping</title>
    </head>
    <body>
        <h1>View Grouping</h1>
        
        <m:window name="grouping.main" state="expanded" title="&nbsp;" workflow="ViewGrouping">
            <menu>
                <m:hide privilege="GRP_W">
                    <a href="Controller?workflow=EditGrouping&gsid=#:getGsid#"><m:img name="edit"/></a>
                    <a href="Controller?workflow=RemoveGrouping&gsid=<jsp:getProperty name="groupingdto" property="gsid"/>" onClick="return confirm('Remove grouping?')"><m:img name="delete" title="Remove this grouping"/></a>
                </m:hide>
            </menu>
            <body>
                <table class="block_data">
                    <tr class="block_data">
                        <td class="block">
                            <b>Name</b><br> <jsp:getProperty name="groupingdto" property="name"/>
                        </td>
                        <td class="block" colspan="2">
                            <b>Comment</b><br><jsp:getProperty name="groupingdto" property="comm"/>
                        </td>
                    </tr>
                    <tr>                
                        <td class="block">
                            <b>User</b><br><jsp:getProperty name="groupingdto" property="user"/>
                        </td> 

                        <td class="block">
                            <b>Updated</b><br> <jsp:getProperty name="groupingdto" property="updated"/>
                        </td>
                    </tr>
                </table>
            </body>
        </m:window>
        
        <br>
        
        <m:window name="grouping.history" title="History" workflow="ViewGrouping">
            <menu>
                
            </menu>
            <body>
                <table class="block_data">
                    <tr>
                        <th colspan="4" class="data" align="center">Current data</th>
                    </tr>
                    <tr>
                        <th class="data">Name</th>
                        <th class="data">Comment</th>
                        <th class="data">User</th>
                        <th class="data">Last updated</th>
                    </tr>
                    <tr>
                        <td><jsp:getProperty name="groupingdto" property="name"/></td>
                        <td><jsp:getProperty name="groupingdto" property="comm"/></td>
                        <td><jsp:getProperty name="groupingdto" property="user"/></td>
                        <td><jsp:getProperty name="groupingdto" property="updated"/></td>
                    </tr>
                    <tr>
                        <th colspan="4" class="data" align="center">History</th>
                    </tr>  
                    <m:iterate-collection collection="history">
                        <tr class="#?alt#">
                            <td>#:getName#</td>
                            <td>#:getComm#</td>
                            <td>#:getUser#</td>
                            <td>#:getUpdated#</td>
                        </tr>
                    </m:iterate-collection>                                                       
                </table>
            </body>
        </m:window>
        <m:back/>
    </body>
</html>
