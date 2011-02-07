package org.richfaces.tests.metamer.ftest.model;

import static org.jboss.test.selenium.locator.Attribute.CLASS;
import static org.jboss.test.selenium.locator.reference.ReferencedLocator.ref;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;

public class PanelMenu extends AbstractModel<JQueryLocator> {

    AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    private ReferencedLocator<JQueryLocator> topItems = ref(root, "> rf-pm-top-itm");
    private ReferencedLocator<JQueryLocator> topGroups = ref(root, "> rf-pm-top-gr");

    public PanelMenu(JQueryLocator root) {
        super(root);
    }

    public int getItemCount() {
        return selenium.getCount(topItems);
    }

    public int getGroupCount() {
        return selenium.getCount(topGroups);
    }

    public Iterable<Item> getAllItems() {
        return new ModelIterable<JQueryLocator, Item>(topItems.getAllOccurrences(), Item.class);
    }

    public Iterable<Group> getAllGroups() {
        return new ModelIterable<JQueryLocator, Group>(topGroups.getAllOccurrences(), Group.class);
    }

    public Item getItem(int index) {
        return new Item(topItems.getNthOccurence(index));
    }

    public Group getGroup(int index) {
        return new Group(topGroups.getNthOccurence(index));
    }

    public Item getAnyTopItem() {
        return new Item(topItems.getReferenced());
    }

    public Group getAnyTopGroup() {
        return new Group(topGroups.getReferenced());
    }

    public class Group extends AbstractModel<JQueryLocator> {
        ReferencedLocator<JQueryLocator> label = ref(root, "> table > tbody > tr > td[class*=rf-][class*=-gr-lbl]");
        ReferencedLocator<JQueryLocator> leftIcon = ref(root, "> table > tbody > tr > td[class*=rf-][class*=-gr-ico]");
        ReferencedLocator<JQueryLocator> rightIcon = ref(root,
            "div[class*=rf-][class*=-gr-hdr] > table > tbody > tr > td[class*=rf-][class*=-itm-gr-ico]");

        private ReferencedLocator<JQueryLocator> items = ref(root, "> rf-pm-itm");
        private ReferencedLocator<JQueryLocator> groups = ref(root, "> rf-pm-gr");

        public Group(JQueryLocator root) {
            super(root);
        }

        public int getItemCount() {
            return selenium.getCount(items);
        }

        public int getGroupCount() {
            return selenium.getCount(groups);
        }

        public Iterable<Item> getAllItems() {
            return new ModelIterable<JQueryLocator, Item>(items.getAllOccurrences(), Item.class);
        }

        public Item getItem(int index) {
            return new Item(items.getNthOccurence(index));
        }

        public Iterable<Group> getAllGroups() {
            return new ModelIterable<JQueryLocator, Group>(groups.getAllOccurrences(), Group.class);
        }

        public Group getGroup(int index) {
            return new Group(groups.getNthOccurence(index));
        }

        public Item getAnyItem() {
            return new Item(items.getReferenced());
        }

        public Group getAnyGroup() {
            return new Group(groups.getReferenced());
        }

        public boolean isSelected() {
            return selenium.getAttribute(this.getAttribute(CLASS)).contains("-sel");
        }
        
        public Icon getLeftIcon() {
            return new Icon(leftIcon.getReferenced());
        }

        public Icon getRightIcon() {
            return new Icon(rightIcon.getReferenced());
        }

        public Label getLabel() {
            return new Label(label.getReferenced());
        }
    }

    public class Item extends AbstractModel<JQueryLocator> {
        ReferencedLocator<JQueryLocator> label = ref(root, "> table > tbody > tr > td[class*=rf-][class*=-itm-lbl]");
        ReferencedLocator<JQueryLocator> leftIcon = ref(root, "> table > tbody > tr > td[class*=rf-][class*=-itm-ico]");
        ReferencedLocator<JQueryLocator> rightIcon = ref(root,
            "> table > tbody > tr > td[class*=rf-][class*=-itm-exp-ico]");

        public Item(JQueryLocator root) {
            super(root);
        }

        public boolean isSelected() {
            return selenium.getAttribute(this.getAttribute(CLASS)).contains("-sel");
        }

        public boolean isHovered() {
            return selenium.getAttribute(this.getAttribute(CLASS)).contains("-hov");
        }

        public Icon getLeftIcon() {
            return new Icon(leftIcon.getReferenced());
        }

        public Icon getRightIcon() {
            return new Icon(rightIcon.getReferenced());
        }

        public Label getLabel() {
            return new Label(label.getReferenced());
        }
    }

    public class Icon extends AbstractModel<JQueryLocator> {

        public Icon(JQueryLocator root) {
            super(root);
        }

    }

    public class Label extends AbstractModel<JQueryLocator> {

        public Label(JQueryLocator root) {
            super(root);
        }

    }

}
