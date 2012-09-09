package org.osito.javafx.pageobjects;

import static org.jemmy.fx.Root.ROOT;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;

import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.osito.javafx.dialog.NodeId;

public class RadioButtonFixture {

    private final Lookup<RadioButton> radioButton;
    
    public RadioButtonFixture(Lookup<RadioButton> radioButton) {
        this.radioButton = radioButton;
    }
    
    public static RadioButtonFixture radioButtonForId(NodeId id) {
        @SuppressWarnings("unchecked")
        Parent<Node> parent = ROOT.lookup(Scene.class).as(Parent.class, Node.class);
        return new RadioButtonFixture(parent.lookup(RadioButton.class, new ByID<RadioButton>(id.name())));
    }
    
    public RadioButtonFixture click() {
        radioButton.as(Mouse.class).click();
        return this;
    } 

}
