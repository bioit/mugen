<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>
<%@ page import="java.util.Collection" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="phenotypedto" scope="request" type="com.arexis.mugen.phenotype.phenotypemanager.PhenotypeDTO" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="test.css" />
    <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
    <title>View Phenotype</title>
</head>
<body>
<h1>View Phenotype</h1>
<form action="Controller" method="post">
    
    <m:window name="phenotype.main" state="expanded" title="General informaion" workflow="ViewPhenotype&vid=<%=phenotypedto.getVid()%>&iid=<%phenotypedto.getIid()%>">
        <menu>
            <m:hide privilege="PHENO_R">
                <a href="Controller?workflow=EditPhenotype&vid=<jsp:getProperty name="phenotypedto" property="vid"/>&iid=<jsp:getProperty name="phenotypedto" property="iid"/>"><m:img name="edit" title="Edit this model"/></a>
                <a href="Controller?workflow=RemovePhenotype&vid=<jsp:getProperty name="phenotypedto" property="vid"/>&iid=<jsp:getProperty name="phenotypedto" property="iid"/>" title="Delete this model" onClick="return confirm('Remove model? PLEASE NOTE: ALL INFORMATION SUCH AS FILES ETC. CONNECTED TO THE MODEL WILL ALSO BE REMOVED!!!')"><m:img name="delete"/></a>
            </m:hide>
            &nbsp;
            <a href="Controller?workflow=ViewPhenotype&vid=<jsp:getProperty name="phenotypedto" property="vid"/>&iid=<jsp:getProperty name="phenotypedto" property="iid"/>&phenotype.history=true" title="Expand/Collapse history information"><m:img name="history"/></a>
            
        </menu>
        <body>
            <table class="block">
                
                <tr class="block">
                    <td class="block">
                        <b>Identity / Accession nr</b><br> 
                        <jsp:getProperty name="phenotypedto" property="identity"/>
                        <jsp:getProperty name="phenotypedto" property="identity"/>
                    </td>
                    <td class="block">
                        <b>Species</b><br>
                        <jsp:getProperty name="phenotypedto" property="speciesname"/>
                    </td>
                    <td class="block">
                        <b>Sampling Unit</b><br>
                        <jsp:getProperty name="phenotypedto" property="suname"/>
                    </td>
                </tr>
                
                <tr>
                <td colspan=3>
                    <b>Comment</b><br>
                    <jsp:getProperty name="phenotypedto" property="comm"/>
                </td>
                
                <tr class="block">
                    <td class="block">
                        <b>Value</b><br> 
                        <jsp:getProperty name="phenotypedto" property="value"/>
                    </td>
                    <td class="block">
                        <b>Variable</b><br> 
                        <jsp:getProperty name="phenotypedto" property="variable"/>
                    </td>
                    <td class="block">
                        <b>Type</b><br> 
                        <jsp:getProperty name="phenotypedto" property="type"/>
                    </td>
                </tr>
            </table>
        </body>
    </m:window>
    
    <m:window title="History" name="phenotype.history" workflow="ViewPhenotype">
        <menu>
            
        </menu>
        <body>
            <table class="data">  
                <tr>
                    <th colspan="6" class="data" align="center">Current data</th>
                </tr>
                <tr>                  
                    <th class="data">Value</th>
                    <th class="data">Date</th>
                    <th class="data">Comment</th>
                    <th class="data">Reference</th>
                    <th class="data">User</th>
                    <th class="data">Last updated</th>
                </tr>
                <tr>
                    <td><jsp:getProperty name="phenotypedto" property="value"/></td>
                    <td><jsp:getProperty name="phenotypedto" property="date"/></td>
                    <td><jsp:getProperty name="phenotypedto" property="comm"/></td>
                    <td><jsp:getProperty name="phenotypedto" property="reference"/></td>
                    <td><a href="Controller?workflow=ViewUser&id=<jsp:getProperty name="phenotypedto" property="userId"/>" title="View the details for this user"><jsp:getProperty name="phenotypedto" property="user"/></a></td>
                    <td><jsp:getProperty name="phenotypedto" property="updated"/></td>                     
                </tr>
                <tr>
                    <th colspan="6" class="data" align="center">History</th>
                </tr>                    
                <m:iterate-collection collection="history">
                    
                    <tr class="#?alt#">
                        <td>#:getValue#</td>
                        <td>#:getDate#</td>
                        <td>#:getComm#</td>
                        <td>#:getReference#</td>
                        <td><a href="Controller?workflow=ViewUser&id=#:getUserId#" title="View the details for this user">#:getUser#</a></td>
                        <td>#:getUpdated#</td>
                    </tr>
                </m:iterate-collection>                      
            </table>
        </body>
    </m:window>
    
    
    
    
    <p>         
        <m:back/>
    </p>
</form> 
</body>
</html>
