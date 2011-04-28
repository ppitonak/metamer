package org.richfaces.tests.metamer.ftest.richMessage;


/**
 * 
 * This should be enum for all know attributes.
 * Need to implement an "retriever" method to get 
 * values from key such as "class" by "_class" key
 *
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision$
 */
public enum RichMessageAttributes  {
    AJAX_RENDERED   ("ajaxRendered"),
    DIR             ("dir"),
    FOR             ("for"),
    LANG            ("lang"),
    TITLE           ("title"),
    CLASS           ("class"),
    STYLE           ("style"),
    STYLE_CLASS     ("styleClass"),   
    ;
    
    private String value;   
    RichMessageAttributes(String val){
        this.value = val;
    }

    @Override
    public String toString() {    
        return value;
    }    
} 

