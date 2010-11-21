<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
%>  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>mugen mice</title>
    </head>
    <body class="content">
    <form action="Controller" method="post">
        <h1>mugen mice</h1>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
            <input type="image" src="images/icons/reset.png" name="reset" value="reset" title="reset">
            <a href="Controller?workflow=ViewModels&models.help=true"><m:img name="info_24" title="help & info"/></a>
        </p>
        <table>
            <tr valign="bottom">
                <td>Research Applications</td>
                <td>Gene</td>
                <td>Institution</td>
                <td>Researcher</td>
                <td>Mutation Type</td>
                <td>MP Terms</td>
                <td>Items|Page</td>
                <m:hide privilege="MODEL_W" suid="<%=caller.getSuid()%>">
                <td>Dissemination</td>
                </m:hide>
            </tr>
            <tr valign="top">
                <td><m:checkbox collection="rapps" onChange="this.form.submit()" name="raid" idGetter="getId" textGetter="getName" selected="<%=fdm.getValue("raid")%>" wildcardOption="true"/></td>
                <td><m:checkbox collection="genes" onChange="this.form.submit()" name="gaid" idGetter="getGaid" textGetter="getName" selected="<%=fdm.getValue("gaid")%>" wildcardOption="true"/></td>
                <td><m:checkbox collection="participants" onChange="this.form.submit()" name="groupname" idGetter="toString" textGetter="toString" selected="<%=fdm.getValue("groupname")%>" wildcardOption="true"/></td>
                <td><m:checkbox collection="researchers" onChange="this.form.submit()" name="participantname" idGetter="toString" textGetter="toString" selected="<%=fdm.getValue("participantname")%>" wildcardOption="true"/></td>
                <td><m:checkbox collection="mutations" onChange="this.form.submit()" name="mutationtypes" idGetter="getId" textGetter="getName" selected="<%=fdm.getValue("mutationtypes")%>" wildcardOption="true"/></td>
                <td><m:checkbox collection="mpterms" onChange="this.form.submit()" name="mpterm" idGetter="getMpid" textGetter="getMpname" selected="<%=fdm.getValue("mpterm")%>" wildcardOption="true"/></td>
                <td><m:checkbox collection="deltas" onChange="this.form.submit()" name="delta" idGetter="toString" textGetter="toString" selected="<%=fdm.getValue("delta")%>"/></td>
                <m:hide privilege="MODEL_W" suid="<%=caller.getSuid()%>">
                <td><m:checkbox collection="disseminationlevels" onChange="this.form.submit()" name="disslevel" idGetter="toString" textGetter="toString" selected="<%=fdm.getValue("disslevel")%>" wildcardOption="true"/></td>
                </m:hide> 
            </tr>
        </table>
       
        <m:hide-block name="models.help">
            <table>    
                <tr>
                    <td>
                        <table class="info">
                            <td>
                                <b>Help & Info on this page:</b><br><br>
                                This is the list of MUGEN Mutant Mice that you are allowed to view. If you believe that this list is not complete it might be because of user rights limitation. In this case contact <a href="mailto:mmmdb@mugen-noe.org">the MUGEN administrator</a>.<br><br>
                                To <b>view detailed information</b> about a MUGEN Mutant Mouse you can <b>click</b> on its <b>Common Line Name</b>.<br>
                                To <b>contact the person</b> who is <b>responsible</b> for a MUGEN Mutant Mouse <b>click</b> on the <b>name</b> on the <b>Contact column</b>.<br><br>
                                To <b>refine this list</b> of MUGEN Mutant Mice you can <b>select</b> a <b>term from</b> the above <b>drop-down menus</b>. For example choosing a specific gene from the drop-down menu will result in displaying only the MUGEN Mutant Mice that are connected to this gene. 
                                In the same manner you can narrow this list by choosing specific values for all drop-down menus.
                                To minimize this help section click on the blue exclamation mark icon again.
                            </td>
                         </table>
                    </td>
                </tr>     
            </table>     
       </m:hide-block>
       
        <hr id="ruler">
            <table>
                <tr>
                    <td>
                      <m:navigation-buttons workflow="ViewModels" showText='true'/>  
                    </td>
                </tr>
            </table>
            <table class="data">
                <tr>
                    <th class="data" width="13%"><input type="image" title="Sort by MUGEN ID" src="images/version.2.0/icons/sortby_images_01_v.2.png" name="byID" value="BY ID"></th>
                    <th class="data" width="29%"><input type="image" title="Sort by Common Line Name" src="images/version.2.0/icons/sortby_images_02_v.2.png" name="byNAME" value="BY NAME"></th>
                    <th class="data" width="14%">Mutation(s)</th>
                    <th class="data" width="30%">Research Application</th>
                    <th class="data" width="14%"><input type="image" title="Sort by Date" src="images/version.2.0/icons/sortby_images_03_v.2.png" name="byDATE" value="BY DATE"></th>
                </tr>
                <m:iterate-collection collection="modelsdto">
                    <tr class="#?alt#">
                        <td width="13%">#:getAccNr#</td>
                        <td width="29%"><a href="Controller?workflow=ViewModel&expand_all=true&name_begins=model.block&eid=#:getEid#" title="view details for this mouse">#:getLineName#</a></td>
                        <td width="14%">#:getMutations#</td>
                        <td width="30%">#:getResearchAppType#</td>
                        <td width="14%">#:getTs#</td>
                    </tr>
                </m:iterate-collection>
            </table>
            <table>
                <tr>
                    <td>
                      <m:navigation-buttons workflow="ViewModels"/>  
                    </td>
                </tr>
                <m:hide privilege="MODEL_ADM" suid="<%=caller.getSuid()%>">
                <tr><td>&nbsp;</td></tr>
                <tr><td>
                    <a class="menu" href="Controller?workflow=ViewJAXModels" title="view mice with jax id">jax</a>&nbsp;|&nbsp;<a class="menu" href="Controller?workflow=ViewEMMAModels" title="view mice with emma id">emma</a>&nbsp;|&nbsp;<a class="menu" href="Controller?workflow=ViewJAXEMMAModels" title="view mice with jax & emma id">jax & emma</a>
                </td></tr>
                </m:hide>
            </table>
       </form>
    </body>
</html>
