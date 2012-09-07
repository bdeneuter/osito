package org.osito.javafx.dialog.message;

import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import org.osito.javafx.MessageKey;
import org.osito.javafx.dialog.OkCancelDialog;

class OkCancelMessageDialog extends OkCancelDialog<OkCancelMessagePresentationModel> {
		
	private Window owner;
	private MessageContent content;
	
	OkCancelMessageDialog(MessageType messageType, MessageKey messageKey, OptionType optionType, Callback<Option, ?> callback) {
		this(null, messageType, messageKey, optionType, callback);
	}
	
	OkCancelMessageDialog(Window owner, MessageType messageType, MessageKey messageKey, OptionType optionType, Callback<Option, ?> callback) {
	    super(new OkCancelMessagePresentationModel(callback), optionType);
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
