package org.richfaces.tests.metamer.model.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.google.common.collect.Iterators;

public class Company extends NamedNode implements TreeNode, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private List<CompactDisc> compactDiscs = new ArrayList<CompactDisc>();

    private Country country;

    public Company() {
        this.setType("company");
    }

    public TreeNode getChildAt(int childIndex) {
        return compactDiscs.get(childIndex);
    }

    public int getChildCount() {
        return compactDiscs.size();
    }

    public TreeNode getParent() {
        return country;
    }

    public void setParent(Country country) {
        this.country = country;
    }

    public int getIndex(TreeNode node) {
        return compactDiscs.indexOf(node);
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public boolean isLeaf() {
        return compactDiscs.isEmpty();
    }

    public Enumeration children() {
        return Iterators.asEnumeration(compactDiscs.iterator());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<CompactDisc> getCds() {
        return compactDiscs;
    }

}
