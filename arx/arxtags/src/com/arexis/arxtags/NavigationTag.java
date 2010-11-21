package com.arexis.arxtags;

import com.arexis.arxframe.Navigator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class NavigationTag extends BodyTagSupport {
    private String workflow;
    private Navigator navigator;
    private boolean showText;
    private int viewed;
    private int max;
    private int threshold = 5;
    
    public NavigationTag() {
        showText = false;
    }
    
    /**
     * Sets the navigator object which is used for retrieving info regarding where we are in the page view.
     * @param navigator The navigation object
     */
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
    
    /**
     * Sets the current workflow
     * @param workflow The workflow name
     */
    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }
    
    /**
     * Sets the variable for turning on/off the text displaying page info
     * @param showText The flag for displaying text
     */
    public void setShowText(boolean showText) {
        this.showText = showText;
    }
    
    
    /**
     * Creates the workflow links
     * @throws javax.servlet.jsp.JspException If the navigation panel could not be created
     * @return The BodyTagSupport.SKIP_BODY integer value if everything went fine
     */
    public int doStartTag() throws JspException {
        String data = "";
        try {
            boolean isLast = false;
            boolean isFirst = false;
            boolean onePage = false;
            navigator = (Navigator)pageContext.getSession().getAttribute("navigator");
            int currentStart = 0;
            int currentStop = 0;
            int currentPage = 0;
            int pagenumbers = 0;
            int delta = 0;
            if(navigator != null) {
                max = navigator.getPageManager().getMax();
                
                currentStart = navigator.getPageManager().getStart();
                
                viewed = navigator.getPageManager().getViewed();
                
                delta = navigator.getPageManager().getDelta();
                
                pagenumbers = navigator.getPageManager().getPages();
                
                currentPage = (currentStart + delta - 1)/delta;
                
                if(max == 0)
                    currentStart = 0;
                
                if(currentStart == 1)
                    isFirst = true;
                currentStop = navigator.getPageManager().getStop();
                
                if(currentStop >= max) {
                    currentStop = max;
                    isLast = true;
                }
                if(currentStop < navigator.getPageManager().getDelta())
                    onePage = true;
                
            }
            
            // Added style info in css, p.navtext
            data += "<p class=\"navtext\">";
            
            if(isFirst && !onePage) {
                data += "<img class=\"navimg\" src=\"images/icons/navigate_beginning_disabled.png\" border=\"none\">\n";
                data += "<img  class=\"navimg\"  src=\"images/icons/navigate_left_disabled.png\" border=\"none\">\n";
            } else if(!onePage) {
                data += "<a href=\"Controller?workflow="+workflow+"&first\"><img  class=\"navimg\"  src=\"images/icons/navigate_beginning.png\" border=\"none\" title=\"First\"></a>\n";
                data += "<a href=\"Controller?workflow="+workflow+"&prev\"><img  class=\"navimg\"  src=\"images/icons/navigate_left.png\" border=\"none\" title=\"Previous\"></a>\n";
            }
            
            if(isLast && !onePage) {
                data += "<img  class=\"navimg\"  src=\"images/icons/navigate_right_disabled.png\" border=\"none\">\n";
                data += "<img  class=\"navimg\"  src=\"images/icons/navigate_last_disabled.png\" border=\"none\">\n";
            } else if(!onePage){
                data += "<a href=\"Controller?workflow="+workflow+"&next\"><img  class=\"navimg\"  src=\"images/icons/navigate_next.png\" border=\"none\" title=\"Next\"></a>\n";
                data += "<a href=\"Controller?workflow="+workflow+"&last\"><img  class=\"navimg\"  src=\"images/icons/navigate_end.png\" border=\"none\" title=\"Last\"></a>\n";
            }
            
            if(onePage) {
                data += "<img  class=\"navimg\"  src=\"images/icons/navigate_beginning_disabled.png\" border=\"none\">\n";
                data += "<img  class=\"navimg\"  src=\"images/icons/navigate_left_disabled.png\" border=\"none\">\n";                
                data += "<img  class=\"navimg\"  src=\"images/icons/navigate_right_disabled.png\" border=\"none\">\n";
                data += "<img  class=\"navimg\"  src=\"images/icons/navigate_end_disabled.png\" border=\"none\">\n";                
            }
            
            if(pagenumbers > 0 && pagenumbers > 11 && currentPage > threshold){
                data += "|";
                if((currentPage+threshold) < pagenumbers+1){
                    for(int i=currentPage-threshold; i < currentPage+threshold+1; i++){
                        if(i==currentPage){
                            data +="<b>"+i+"</b>|\n";
                        } else{
                            data +="<a class=\"menu\" href=\"Controller?workflow="+workflow+"&page="+i+"\" title=\"go to page "+i+"\">"+i+"</a>|\n";
                        }
                    }
                } else {
                    //for(int i=1; i < 11+1; i++){
                    for(int i=pagenumbers+1-11; i < pagenumbers+1; i++){
                        if(i==currentPage){
                            data +="<b>"+i+"</b>|\n";
                        } else{
                            data +="<a class=\"menu\" href=\"Controller?workflow="+workflow+"&page="+i+"\" title=\"go to page "+i+"\">"+i+"</a>|\n";
                        }
                    }
                }
            } else {
                data += "|";
                
                if(pagenumbers > 11 && currentPage <= threshold){
                    pagenumbers = 11;
                }
                
                for(int i=1; i < pagenumbers+1; i++){
                    if(i==currentPage){
                        data +="<b>"+i+"</b>|\n";
                    } else{
                        data +="<a class=\"menu\" href=\"Controller?workflow="+workflow+"&page="+i+"\" title=\"go to page "+i+"\">"+i+"</a>|\n";
                    }
                }
            }
            
            // If the text should be shown
            if (showText)
            {    
                data += "&nbsp;&nbsp;";
                // If only one page will be displayed, show a simpler version
                if (onePage)    
                    data += "displaying <b>"+viewed+"</b> out of <b>"+max+"</b>";
                else
                    data += "displaying <b>"+currentStart+"-"+currentStop+"</b> out of <b>"+max+"</b>";
            }
            data += "</p>";
            
            // Print the table on the page
            pageContext.getOut().print(data);
            
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        
        return SKIP_BODY;
    }
}
