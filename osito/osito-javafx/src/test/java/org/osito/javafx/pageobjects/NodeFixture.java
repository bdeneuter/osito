package org.osito.javafx.pageobjects;

import static org.jemmy.fx.Root.ROOT;
import javafx.scene.Node;
import javafx.scene.Scene;

import org.jemmy.Point;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;

public class NodeFixture {

    private final Lookup<Node> nodeLookup;

    protected NodeFixture(Lookup<Node> nodeLookup) {
        this.nodeLookup = nodeLookup;
    }

    public static NodeFixture nodeForId(String id){
        @SuppressWarnings("unchecked")
        Parent<Node> parent = ROOT.lookup(Scene.class).as(Parent.class, Node.class);
        return new NodeFixture(parent.lookup(Node.class, new ByID<Node>(id)));
    }
    
    public NodeFixture pressMouse() {
        return pressMouse(0, 0);
    }
    
    public NodeFixture pressMouse(int x, int y) {
        Mouse node = nodeLookup.as(Mouse.class);
        node.move(new Point(-x, -y));
        node.press();
        return this;
    }
    
    public boolean isVisible() {
        return nodeLookup.get().isVisible();
    }
    
    public boolean isDisabled() {
        return nodeLookup.get().isDisabled();
    }

    public NodeFixture releaseMouse() {
        nodeLookup.as(Mouse.class).release();
        return this;
    }

    public NodeFixture moveMouse(int deltaX, int deltaY) {
        nodeLookup.as(Mouse.class).move(new Point(deltaX, deltaY));
        return this;
    }

    public NodeFixture pushKey(KeyboardButton button) {
        nodeLookup.as(Keyboard.class).pushKey(button);
        return this;
    }

    public NodeFixture scrollUp() {
        nodeLookup.as(Mouse.class).turnWheel(-1);
        return this;
    }

    public NodeFixture scrollDown() {
        nodeLookup.as(Mouse.class).turnWheel(1);
        return this;
    }
    
}
