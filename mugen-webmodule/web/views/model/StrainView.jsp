<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="straindto" scope="request" type="com.arexis.mugen.model.modelmanager.StrainDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>strain</title>
    </head>
    <body class="content">
    <h1>strain</h1>
    <p class="toolbar">
        <a href="Controller?workflow=EditStrain&strainid=<jsp:getProperty name="straindto" property="strainId"/>" title="edit strain"><img src="images/icons/editdark.png"></a>
    </p>  
    <form action="Controller" method="post">
        <table>
            <tr>
                <td><b>Designation</b></td>            
            </tr>
            <tr>
                <td><jsp:getProperty name="straindto" property="designation"/></td>
            </tr>
            <tr>
                <td><b>JAX ID</b></td>
            </tr>
            <tr>
                <td><jsp:getProperty name="straindto" property="mgiIdURL"/></td>
            </tr>
            <tr>
                <td><b>EMMA ID</b></td>
            </tr>
            <tr>
                <td><jsp:getProperty name="straindto" property="emmaidURL"/></td>
            </tr>
        </table>
        <br>
        <table width="100%">
            <tr>            
               <td><b>Related MUGEN Mouse</b></td>
            </tr>
            <tr>
            <td><a class="menu" href="Controller?workflow=ViewModel&expand_all=true&name_begins=model.block&eid=<jsp:getProperty name="straindto" property="eid"/>" title="view mouse">M<jsp:getProperty name="straindto" property="eid"/></a></td>
            </tr>
        </table>           
        <br>
        <p class="toolbar">
            <m:back/>
        </p>
    </form>
    </body>
</html>
