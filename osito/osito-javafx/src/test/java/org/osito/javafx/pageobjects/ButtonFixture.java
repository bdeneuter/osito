package org.osito.javafx.pageobjects;

import static org.jemmy.fx.Root.ROOT;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import org.apache.commons.lang3.StringUtils;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.Lookup;
import org.osito.javafx.dialog.NodeId;

public class ButtonFixture {

    private final Lookup<Button> buttonLookup;

    protected ButtonFixture(Lookup<Button> buttonLookup) {
        this.buttonLookup = buttonLookup;
    }

    public static ButtonFixture buttonForId(NodeId id){
        @SuppressWarnings("unchecked")
        Parent<Node> parent = ROOT.lookup(Scene.class).as(Parent.class, Node.class);
        return new ButtonFixture(parent.lookup(Button.class, new ByID<Button>(id.name())));
    }
    
    public static ButtonFixture buttonForId(Lookup<Node> parent, NodeId id) {
        return new ButtonFixture(parent.lookup(Button.class, new ByID<Button>(id.name())));
    }
    
    public ButtonFixture click() {
        buttonLookup.as(Mouse.class).click();
        return this;
    }

    public boolean hasLabel(String expected) {
        String actual = buttonLookup.as(Text.class).text();
        return StringUtils.equals(expected, actual);
    }

}
