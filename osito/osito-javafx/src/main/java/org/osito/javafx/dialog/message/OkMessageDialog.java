package org.osito.javafx.dialog.message;

import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.osito.javafx.MessageKey;
import org.osito.javafx.MessageService;
import org.osito.javafx.dialog.OkDialog;

class OkMessageDialog extends OkDialog {
		
	private Window owner;
	private MessageContent content;

	OkMessageDialog(MessageType messageType, MessageKey messageKey, MessageService messageService) {
		this(null, messageType, messageKey, messageService);
	}
	
	OkMessageDialog(Window owner, MessageType messageType, MessageKey messageKey, MessageService messageService) {
		super(messageService);
		this.owner = owner;
		content = new MessageContent(messageType, messageKey);
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
