package org.richfaces.tests.metamer.model.tree;

import java.io.Serializable;

import javax.swing.tree.TreeNode;

public abstract class NamedNode implements TreeNode, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
