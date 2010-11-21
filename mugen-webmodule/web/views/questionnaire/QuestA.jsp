<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>MUGEN Questionnaire on Mouse Phenotyping</title>
       <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() {
                define('q5', 'string', '[q.05]', null, 255);
                define('q7', 'string', '[q.07]', null, 255);
                define('q9', 'string', '[q.09]', null, 255);
                define('q11', 'string', '[q.11]', null, 255);
                define('q12', 'string', '[q.12]', null, 255);
                define('q21', 'string', '[q.21]', null, 255);
                define('q26', 'string', '[q.26]', null, 255);
                define('name', 'string', 'Name', 3, 55);
                define('inst', 'string', 'Institution', 3, 55);
                define('mail', 'email', 'Email');
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <form action="Controller" method="post">
        <h5>MUGEN Questionnaire on Mouse Phenotyping</h5>
        <h4>Personal Details</h4>
        <p class="quest_bar">&nbsp;</p>
            <table>
                <tr><td align="right"><b>Name:</b></td><td>&nbsp;<input name="name" size="55" maxlength="55" type="text"></td></tr>
                <tr><td align="right"><b>Institution:</b></td><td>&nbsp;<input name="inst" size="55" maxlength="55" type="text"></td></tr>
                <tr><td align="right"><b>E-mail:</b></td><td>&nbsp;<input name="mail" size="55" maxlength="55" type="text"></td></tr>
            </table>
            <br>
        <h4>Immunophenotyping of Mouse Mutants</h4>
        <p class="quest_bar">&nbsp;</p>
                <table class="quest_a">
                    <tr>
                        <td valign="top" width="5px">q|01</td><td><b>Do you immunophenotype mouse lines?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q1" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|02</td><td><b>When you analyse a new mouse mutant, do you search for a phenotype bases on your initial question of your experiment, which may be different from mutant to mutant?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q2" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|03</td><td><b>When you analyse a new mouse mutant, do you use a predefined set of protocols to characterize each new mouse mutant?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q3" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|04</td><td><b>If yes, do you make a complete assessment of the mutant?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q4" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|05</td><td><b>If yes, what do you analyse?</b></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <textarea type="text" cols="55" rows="4" name="q5"></textarea>
                        </td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|06</td><td><b>If no, do you perform specific standardized analyses based on your favourite part of the Immune system?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q6" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|07</td><td><b>If yes, what do you analyse?</b></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <textarea type="text" cols="55" rows="4" name="q7"></textarea>
                        </td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|08</td><td><b>Do you care about the mouse strain you are analysing?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q8" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|09</td><td><b>If yes, which strain do you prefer?</b></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <textarea type="text" cols="55" rows="4" name="q9"></textarea>
                        </td>
                    </tr>
                </table>
                <br>
        <h4>Equipment and Expertise used for Mouse Phenotyping</h4>
        <p class="quest_bar">&nbsp;</p>
                <table class="quest_a">
                    <tr>
                        <td valign="top" width="5px">q|10</td><td><b>Do you use flow cytometry?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q10" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|11</td><td><b>If yes, which flow cytometers do you use?</b></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <textarea type="text" cols="55" rows="4" name="q11"></textarea>
                        </td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|12</td><td><b>If yes, which software do you use?</b></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <textarea type="text" cols="55" rows="4" name="q12"></textarea>
                        </td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|13</td><td><b>Do you prepare your own reagents for staining?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q13" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|14</td><td><b>Do you capture your raw data with compensation?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q14" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|15</td><td><b>Do you prefer to store the raw data in a database?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q15" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|16</td><td><b>Do you prefer to store calculated data in a database?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q16" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|17</td><td><b>Do you store the staining protocol with you data?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q17" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|18</td><td><b>Do you capture the mouse genotype data with your data?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q18" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|19</td><td><b>Is your flow cytometric approach validated by other labs?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q19" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|20</td><td><b>Do you use Serology?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q20" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|21</td><td><b>If yes, which methods do you use?</b></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <textarea type="text" cols="55" rows="4" name="q21"></textarea>
                        </td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|22</td><td><b>Do you use Bead array assays?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q22" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|23</td><td><b>Do you use CBA kits?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q23" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|24</td><td><b>Do you use the Luminex System?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q24" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|25</td><td><b>Do you develop your own assays?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q25" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|26</td><td><b>Are your serological tests validated by other labs?</b></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <textarea type="text" cols="55" rows="4" name="q26"></textarea>
                        </td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|27</td><td><b>Do you perform pathology?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q27" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|28</td><td><b>Do you collaborate with a pathologist?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q28" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|29</td><td><b>Do you perform pathology within your group?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q29" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td>&nbsp;</td>
                        <td><b>How do you capture the data?</b></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|30</td><td><b>I keep the original slides?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q30" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|31</td><td><b>I prepare typical pictures for presentations?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q31" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|32</td><td><b>I keep the assessment of the specimens as text files?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q32" emptyOption="true"/></td>
                    </tr>
                    
                    <tr>
                        <td valign="top" width="5px">q|33</td><td><b>I use a scoring system to compare different specimens?</b>
                        <br><m:checkbox collection="yesnos"  idGetter="toString" textGetter="toString" name="q33" emptyOption="true"/></td>
                    </tr>
                </table>
                <br>
        <p class="toolbar">
                <input type="image" src="images/icons/save.png" name="create" value="Create" onClick="validate();return returnVal;" title="submit questionnaire">
                <%--input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel"--%>
        </p>
        </form> 
    </body>
</html>

