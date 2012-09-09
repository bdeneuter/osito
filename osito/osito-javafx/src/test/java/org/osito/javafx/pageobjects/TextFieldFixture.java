package org.osito.javafx.pageobjects;

import static org.jemmy.fx.Root.ROOT;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import org.apache.commons.lang3.StringUtils;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.Lookup;
import org.osito.javafx.dialog.NodeId;

public class TextFieldFixture {

    private final Lookup<TextField> textField;

    public TextFieldFixture(Lookup<TextField> textField) {
        this.textField = textField;
    }

    public static TextFieldFixture textFieldForId(NodeId id) {
        @SuppressWarnings("unchecked")
        Parent<Node> parent = ROOT.lookup(Scene.class).as(Parent.class, Node.class);
        return new TextFieldFixture(parent.lookup(TextField.class, new ByID<TextField>(id.name())));
    }

    public TextFieldFixture enterText(String text) {
        textField.as(Text.class).type(text);
        return this;
    }
    
    public boolean hasText(String expected) {
        String actual = textField.as(Text.class).text();
        return StringUtils.equals(expected, actual);
    }   
}
