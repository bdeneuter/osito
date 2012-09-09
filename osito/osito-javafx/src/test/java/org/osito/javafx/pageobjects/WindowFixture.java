package org.osito.javafx.pageobjects;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.jemmy.fx.Root.ROOT;
import static org.osito.javafx.pageobjects.ButtonFixture.buttonForId;
import static org.osito.javafx.pageobjects.RadioButtonFixture.radioButtonForId;
import static org.osito.javafx.pageobjects.TextFieldFixture.textFieldForId;
import static org.osito.util.Poller.aPoller;
import javafx.scene.Scene;
import javafx.stage.Window;

import org.jemmy.lookup.Lookup;
import org.osito.javafx.dialog.NodeId;
import org.osito.javafx.pageobjects.dialog.MessageDialogFixture;
import org.osito.util.Assertion;

@SuppressWarnings("unchecked")
public class WindowFixture<T extends WindowFixture<T>> {

	private Window window;
	private Lookup<Scene> sceneLookup;

	protected WindowFixture() {
		sceneLookup = ROOT.lookup(Scene.class);
		window = sceneLookup.get().getWindow();
	}

	@SuppressWarnings("rawtypes")
	public static WindowFixture<?> window() {
		return new WindowFixture();
	}

	public T assertIsShowing() {
		assertThat(window.isShowing()).isTrue();
		return (T) this;
	}

	public T clickButton(NodeId buttonId) {
		buttonForId(buttonId).click();
		return (T) this;
	}

	public T enterText(NodeId id, String text) {
		textFieldForId(id).enterText(text);
		return (T) this;
	}

	public T selectRadioButton(NodeId id) {
		radioButtonForId(id).click();
		return (T) this;
	}

	public T assertIsDisabled(NodeId id) {
		assertThat(node(id).isDisabled()).isTrue();
		return (T) this;
	}

	public T assertIsEnabled(NodeId id) {
		assertThat(node(id).isDisabled()).isFalse();
		return (T) this;
	}

	public NodeFixture node(NodeId nodeId) {
		return NodeFixture.nodeForId(nodeId.name());
	}

	public WindowFixture<T> assertIsClosed() {
		aPoller().doAssert(new Assertion() {

			@Override
			public void doAssert() throws AssertionError {
				assertThat(window.isShowing()).isFalse();
			}
		});
		return this;
	}

}
