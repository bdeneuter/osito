package org.osito.javafx.dialog.message;

import static javafx.geometry.Pos.CENTER;
import static org.osito.javafx.dialog.MessageKey.CANCEL;
import static org.osito.javafx.dialog.MessageKey.NO;
import static org.osito.javafx.dialog.MessageKey.OK;
import static org.osito.javafx.dialog.MessageKey.YES;
import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import org.osito.javafx.dialog.MessageKey;
import org.osito.javafx.dialog.OkCancelDialog;

class MessageDialog extends OkCancelDialog {
		
	private Window owner;
	private MessageContent content;
	private OptionType optionType;
	
	MessageDialog(MessageType messageType, String message, OptionType optionType, Callback<Option, ?> callback) {
		this(null, messageType, message, optionType, callback);
	}
	
	MessageDialog(Window owner, MessageType messageType, String message, OptionType optionType, Callback<Option, ?> callback) {
	    super(new MessageDialogModel(callback, optionType));
		this.owner = owner;
		this.optionType = optionType;
		content = new MessageContent(messageType, message);
		setButtonAlignment(CENTER);
	}
	
	@Override
	protected MessageKey getOkLabel() {
		switch (optionType) {
		case YES_NO_OPTION:
			return YES;
		default:
			return OK;
		}
	}
	
	@Override
	protected MessageKey getCancelLabel() {
		switch (optionType) {
		case YES_NO_OPTION:
			return NO;
		default:
			return CANCEL;
		}
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
