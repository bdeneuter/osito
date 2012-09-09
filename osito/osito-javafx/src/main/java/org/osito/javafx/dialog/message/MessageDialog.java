package org.osito.javafx.dialog.message;

import static javafx.geometry.Pos.CENTER;
import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import org.osito.javafx.dialog.OkCancelDialog;

class MessageDialog extends OkCancelDialog {
		
	private Window owner;
	private MessageContent content;
	
	MessageDialog(MessageType messageType, String message, OptionType optionType, Callback<Option, ?> callback) {
		this(null, messageType, message, optionType, callback);
	}
	
	MessageDialog(Window owner, MessageType messageType, String message, OptionType optionType, Callback<Option, ?> callback) {
	    super(new MessageDialogModel(callback, optionType));
		this.owner = owner;
		content = new MessageContent(messageType, message);
		setButtonAlignment(CENTER);
	}
	
	@Override
	protected void initializeStage(Stage stage) {
		stage.setTitle(content.getTitle());
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(owner);
		stage.setResizable(false);
	}
	
	@Override
	protected Node createContent() {
		return content;
	}
	
}
