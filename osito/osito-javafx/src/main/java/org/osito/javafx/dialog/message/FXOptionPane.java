package org.osito.javafx.dialog.message;

import static org.osito.javafx.CallbackFuture.callbackFuture;
import static org.osito.javafx.dialog.message.MessageType.QUESTION;
import javafx.application.Platform;
import javafx.stage.Window;
import javafx.util.Callback;

import org.osito.javafx.CallbackFuture;
import org.osito.javafx.MessageKey;
import org.osito.javafx.MessageService;

public class FXOptionPane {

    public static Option showConfirmDialog(MessageKey message, OptionType optionType) {
        return showConfirmDialog(null, message, optionType);
    }
    
    public static void showConfirmDialog(Window owner, MessageKey message, OptionType optionType, Callback<Option, ?> callback) {
    	new OkCancelMessageDialog(QUESTION, message, optionType, callback).showDialog();
    }
    
    public static Option showConfirmDialog(Window owner, MessageKey message, OptionType optionType) {
    	if (Platform.isFxApplicationThread()) {
    		throw new IllegalStateException("This method blocks and can not be called from the FX application thread.");
    	}
        CallbackFuture<Option> callbackFuture = callbackFuture();
        new OkCancelMessageDialog(QUESTION, message, optionType, callbackFuture).showDialog();
        return callbackFuture.get();
    }
    
    public static void showMessageDialog(MessageKey message, MessageType messageType) {
        showMessageDialog(null, message, messageType);
    }
    
    public static void showMessageDialog(Window owner, MessageKey message, MessageType messageType) {
        new OkMessageDialog(messageType, message, MessageService.messageService()).showDialog();
    }

}
