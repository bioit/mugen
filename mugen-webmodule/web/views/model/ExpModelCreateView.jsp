<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>create mugen mouse</title>
       <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() {
                define('alias', 'string', 'Line name', 1, 255);
                define('comm', 'string', 'Authors comments', null, 255);                
                define('researchAppsText', 'string', 'Research Application Comments', null, 255); 
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <h1>create mugen mouse</h1>
        <p class="toolbar">
        <a href="Controller?workflow=CreateModel&model.help=true"><m:img name="info_24" title="help & info"/></a>
        </p>
        <form action="Controller" method="post">
                <%--info table--%>
                <m:hide-block name="model.help">
                <table>    
                    <tr>
                        <td>
                            <table class="info">
                                <td>
                                    <b>Help & Info on this page:</b><br><br>
                                    <b>Line name:</b> Type the common line name for the new MUGEN mutant mouse you are about to create. All special characters, including numbers are allowed.<br>
                                    <b>Research application type:</b> Choose the research application type for the new strain. You have three options: MODEL OF HUMAN DISEASE, MODEL OF IMMUNE PROCESSES and TRANSGENIC TOOL.<br>
                                    <b>Research applications comments:</b> If you would like to add information relevant to the research applications for this new strain you can do it on this text field. Simply type your research application comments here.<br>
                                    <b>Contact:</b> Here you should choose the person who is responsible for this new strain. This person will also be the one to contact if a visitor of the MUGEN database has further requirements concerning this specific strain.<br>
                                    <b>Authors comments:</b> Should you need to add a comment to this new strain you can type it here. Just type whatever kind of information you would like to add to this mutant.<br>
                                    <b>Saving your data: </b> Once you're done with entering data you can click on the save icon (the blue diskette icon) to store the new strain in the MUGEN database. If you are having second thoughts you can always click on the cancel icon (the blue x icon) to return to the previously visited web-page without storing data in the database.<br>
                                </td>
                            </table>
                        </td>
                    </tr>     
                </table>     
                </m:hide-block>
                <table>
                    <tr>                                  
                        <td><b>Line name</b></td>                            
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="alias" size="35" maxlength="255"/>
                            <input type="hidden" name="geneticBackground" value="n.a." />
                            <input type="hidden" name="availability" value="n.a." />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <m:hide-collection collection="samplingunits" limit="1">
                                <b>Sampling unit</b><br>
                            </m:hide-collection>
                            <m:su-combobox hideEmpty="yes"/>
                        </td>
                    </tr>               
                    <tr>                                  
                        <td><b>Research Application type</b></td>
                    </tr>                    
                    <tr>
                        <td>
                            <m:checkbox collection="researchApps"  idGetter="getRaid" textGetter="getName" name="raid"/>
                        </td>
                    </tr>
                    <tr>                                  
                        <td><b>Research Application Comments</b></td>
                    </tr>                    
                    <tr>
                        <td>
                            <textarea type="text" cols="35" rows="6" name="researchAppsText"></textarea>
                        </td>
                    </tr> 
                    <tr>
                        <td><b>Contact</b></td>
                    </tr>                    
                    <tr>
                        <td>
                            <m:checkbox collection="users" idGetter="getId" textGetter="getName" name="contactId" selected="<%=(String)request.getAttribute("userid")%>"/>
                        </td>
                    </tr>                                          
                    <tr>
                        <td><b>Authors comments</b></td>
                    </tr>                    
                    <tr>
                        <td>
                            <textarea type="text" cols="35" rows="6" name="comm"></textarea>                            
                        </td>
                    </tr>
                    <tr>
                        <td><b>Desired Dissemination Level</b></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="desired_levels" idGetter="toString" textGetter="toString" name="desired_level"/>
                        </td>
                    </tr>
                </table>
                <br>
            <p class="toolbar">
                <input type="image" src="images/icons/save.png" name="create" value="Create" onClick="validate();return returnVal;" title="create mouse">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
        </form> 
    </body>
</html>

