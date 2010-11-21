<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title>mouse pic</title>
    <link rel="stylesheet" type="text/css" href="nav.css" />
    <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
  </head>
  <body class="frameright">
  <img src="images/mouse.png" border="0">
  <%
    com.arexis.mugen.project.projectmanager.ProjectDTO project;
    project = (com.arexis.mugen.project.projectmanager.ProjectDTO)session.getAttribute("project.projectdto");
    
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
  %>
  <%--
  <div id="navcontainer">
    <ul id="navlist">
    <li id="active"><a href="Controller?workflow=Help" target="page">Help Section</a></li>
    <li id="active"><a href="Controller?workflow=ContactInfo" target="page">Contact</a></li>
    <li id="active"><a href="Controller?workflow=AboutInfo" target="page">About</a></li>
    <% if(caller.hasPrivilege("MODEL_MUGEN")){ %>
    <li id="active"><a href="Controller?workflow=RevealIcons&icons.info=true" target="help">Icon Description</a></li>
    <% } %>
    <m:hide-block name="icons.info">
    <table>
      <% if(caller.hasPrivilege("MODEL_MUGEN")){ %>
      <tr>
      <td><m:img name="add" title="Add/Create icon. Clicking this icon allows you to create or add new information"/></td>
      <td VALIGN="middle"><b>:add/create icon  </b></td>
      </tr>
      <tr>
      <td><m:img name="edit" title="Edit icon. Clicking this icon allows you to edit existing information"/></td>
      <td VALIGN="middle"><b>:edit icon  </b></td>
      </tr>
      <tr>
      <td><m:img name="delete" title="Delete/Remove icon. Clicking this icon deletes/removes information from the database."/></td>
      <td VALIGN="middle"><b>:delete/remove icon  </b></td>
      </tr>
      <tr>
      <td><m:img name="unassign" title="Unassign/Remove icon. Clicking on this icon unassigns information from a mutant strain."/></td>
      <td VALIGN="middle"><b>:unassign icon  </b></td>
      </tr>
      <tr>
      <td><m:img name="view" title="View detailed information"/></td>
      <td valign="middle"><b>:view detailed info icon  </b></td>
      </tr>
      <tr>
      <td><m:img name="bookmark_add" title="Add link icon. Clicking on this icon allows you to add a web link."/></td>
      <td valign="middle"><b>:add web link icon  </b></td>
      </tr>
      <tr>
      <td><m:img name="info" title="View help info. Clicking on this icon reveals helpful information where applicable."/></td>
      <td valign="middle"><b>:view help info icon  </b></td>
      </tr>
      <tr>
      <td><m:img name="littlebackbutton" title="The MUGEN application's back button. Once hit it will navigate you to the previously visited web-page."/></td>
      <td valign="middle"><b>:the MMMDb's back button  </b></td>
      </tr>
      <tr>
      <td><m:img name="nav_open" title="Expands all Tabs of a mutant's detailed page."/><br><m:img name="nav_close" title="Closes all Tabs of a mutant's detailed page."/></td>
      <td valign="middle"><b>:expand/close all tabs of a page  </b></td>
      </tr>
      <tr>
      <td><m:img name="littlediskette" title="Saves changes currently applied to a mutant of gene."/></td>
      <td valign="middle"><b>:saves current changes </b></td>
      </tr>
      <tr>
      <td><m:img name="littlecancel" title="Cancels current changes and returns to previous change."/></td>
      <td valign="middle"><b>:cancles current changes </b></td>
      </tr>
      
      <% } %>
    </table>
    </m:hide-block>
    </ul>
  </div>
  --%>
  </body>
</html>
