package org.osito.javafx.dialog.message;

import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.osito.javafx.dialog.OkDialog;

class OkMessageDialog extends OkDialog {
		
	private Window owner;
	private MessageContent content;

	OkMessageDialog(MessageType messageType, String message) {
		this(null, messageType, message);
	}
	
	OkMessageDialog(Window owner, MessageType messageType, String message) {
		super();
		this.owner = owner;
		content = new MessageContent(messageType, message);
	}
	
	@Override
	protected void initStage(Stage stage) {
		stage.setTitle(content.getTitle());
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(owner);
	}
	
	@Override
	protected Node createContent() {
		return content;
	}
	
}
