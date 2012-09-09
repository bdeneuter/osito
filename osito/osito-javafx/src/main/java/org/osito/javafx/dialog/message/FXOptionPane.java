package org.osito.javafx.dialog.message;

import static org.osito.javafx.dialog.message.MessageType.QUESTION;
import javafx.stage.Window;
import javafx.util.Callback;

public class FXOptionPane {

    public static void showConfirmDialog(String message, OptionType optionType, Callback<Option, ?> callback) {
        showConfirmDialog(null, message, optionType, callback);
    }
    
    public static void showConfirmDialog(Window owner, String message, OptionType optionType, Callback<Option, ?> callback) {
    	new MessageDialog(QUESTION, message, optionType, callback).showDialog();
    }
    
    public static void showMessageDialog(String message, MessageType messageType) {
        showMessageDialog(null, message, messageType);
    }
    
    public static void showMessageDialog(Window owner, String message, MessageType messageType) {
        new OkMessageDialog(messageType, message).showDialog();
    }

}
