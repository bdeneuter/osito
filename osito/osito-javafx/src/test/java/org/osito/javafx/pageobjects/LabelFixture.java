package org.osito.javafx.pageobjects;

import static org.jemmy.fx.Root.ROOT;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import org.apache.commons.lang3.StringUtils;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.Lookup;
import org.osito.javafx.dialog.NodeId;

public class LabelFixture {

    private final Lookup<Label> labelLookup;

    protected LabelFixture(Lookup<Label> labelLookup) {
        this.labelLookup = labelLookup;
    }

    public static LabelFixture labelForId(NodeId id){
        @SuppressWarnings("unchecked")
        Parent<Node> parent = ROOT.lookup(Scene.class).as(Parent.class, Node.class);
        return new LabelFixture(parent.lookup(Label.class, new ByID<Label>(id.name())));
    }
    
    public static LabelFixture buttonForId(Lookup<Node> parent, NodeId id) {
        return new LabelFixture(parent.lookup(Label.class, new ByID<Label>(id.name())));
    }

    public boolean hasText(String expected) {
        String actual = labelLookup.as(Text.class).text();
        return StringUtils.equals(expected, actual);
    }

}
