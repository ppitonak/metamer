package org.richfaces.tests.metamer.model.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.google.common.collect.Iterators;

public class Country extends NamedNode implements TreeNode, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private List<Company> companies = new ArrayList<Company>();

    public Country() {
        this.setType("country");
    }

    public TreeNode getChildAt(int childIndex) {
        return companies.get(childIndex);
    }

    public int getChildCount() {
        return companies.size();
    }

    public TreeNode getParent() {
        return null;
    }

    public int getIndex(TreeNode node) {
        return companies.indexOf(node);
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public boolean isLeaf() {
        return companies.isEmpty();
    }

    public Enumeration<Company> children() {
        return Iterators.asEnumeration(companies.iterator());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public String toString() {
        return "Country [name=" + name + "]";
    }
}
