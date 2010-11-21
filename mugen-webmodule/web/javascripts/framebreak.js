function frameBreak() { 
    if(top.location.href == window.location.href){
        window.parent.location.href = "mde.jsp";
    } 
}

window.onload = frameBreak();